package io.github.beardedManZhao.algorithmStar.core.model.dataSet;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.block.IntegerMatrixSpace;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 从文件目录中加载数据的操作，在该操作中需要保证目录中包含每一个类别的文件夹子，在不同的目录中存储的就是不同类别对应的图像。
 * <p>
 * The operation of loading data from a file directory requires ensuring that the directory contains file folders for each category, and that images corresponding to different categories are stored in different directories.
 *
 * @author zhao
 */
public class ImageDirLoad implements ASDataSet {

    private final HashMap<String, IntegerMatrixSpace[]> hashMap = new HashMap<>();
    private final ArrayList<String> names1 = new ArrayList<>(), names2 = new ArrayList<>();
    private int xCount = 0;

    public ImageDirLoad(int w, int h, File fileDir) {
        File[] files = fileDir.listFiles();
        if (files == null) {
            throw new OperatorOperationException("您指定的路径不存在或者其不是一个目录。");
        }
        for (File dir : files) {
            String absolutePath = dir.getAbsolutePath();
            String[] dirName = dir.list();
            String name = dir.getName();
            if (dirName != null) {
                names1.add(name);
                IntegerMatrixSpace[] integerMatrixSpaces = new IntegerMatrixSpace[dirName.length];
                int index = -1;
                for (String s : dirName) {
                    ++xCount;
                    names2.add(name);
                    integerMatrixSpaces[++index] = IntegerMatrixSpace.parse(absolutePath + '/' + s, w, h);
                }
                hashMap.put(name, integerMatrixSpaces);
            }
        }
    }

    /**
     * 获取到用于训练的 X 数据集 并将其中的数据样本以训练模型能够接收的方式返回。
     * <p>
     * Obtain the X dataset for training and return the data samples in a way that the training model can receive.
     *
     * @return 一般是图像数据样本，这里每一个元素对应的都是一个图像的三原色矩阵空间。
     * <p>
     * Generally, it is an image data sample, where each element corresponds to the three primary color matrix space of an image.
     */
    @Override
    public IntegerMatrixSpace[] getX_train() {
        IntegerMatrixSpace[] integerMatrixSpaces = new IntegerMatrixSpace[xCount];
        int index = 0;
        for (String name : this.names1) {
            IntegerMatrixSpace[] integerMatrixSpaces1 = this.hashMap.get(name);
            System.arraycopy(integerMatrixSpaces1, 0, integerMatrixSpaces, index, integerMatrixSpaces1.length);
            index += integerMatrixSpaces1.length;
        }
        return integerMatrixSpaces;
    }

    /**
     * 获取到用于训练的 Y 数据集 并将其中的数据样本以训练模型能够接收的方式返回，
     * <p>
     * Obtain the Y dataset for training and return the data samples in a way that the training model can receive,
     *
     * @return 一般是最终的得分向量，其中每一个元素与数据集相互对应。
     */
    @Override
    public DoubleVector[] getY_train() {
        DoubleVector[] doubleVectors = new DoubleVector[this.names2.size()];
        int index = -1, index2 = -1;
        double[] temp = new double[this.names2.size()];
        for (String s : this.names2.toArray(new String[0])) {
            int length = this.hashMap.get(s).length;
            for (int i = ++index; i < length; i++) {
                temp[i] = 1;
            }
            doubleVectors[++index2] = DoubleVector.parse(temp.clone());
        }
        return doubleVectors;
    }

    /**
     * 获取到所有 Y 数据对应的初始权重。
     *
     * @return 所有 Y 对应的初始权重。
     */
    @Override
    public List<KeyValue<String, IntegerMatrixSpace>> getImageWeight() {
        ArrayList<KeyValue<String, IntegerMatrixSpace>> arrayList = new ArrayList<>();
        for (String name : this.names2) {
            arrayList.add(
                    new KeyValue<>(name, this.hashMap.get(name)[0])
            );
        }
        return arrayList;
    }
}
