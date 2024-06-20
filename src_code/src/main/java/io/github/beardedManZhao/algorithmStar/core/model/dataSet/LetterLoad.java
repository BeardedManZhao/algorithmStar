package io.github.beardedManZhao.algorithmStar.core.model.dataSet;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.block.IntegerMatrixSpace;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 字母数据集类，通过该类可加载到来自网络中的字母数据集。
 * <p>
 * Alphabet dataset class, which can be loaded into alphabet datasets from the network.
 *
 * @author 赵凌宇
 * 2023/4/29 7:48
 */
public class LetterLoad implements ASDataSet {

    private final HashMap<String, IntegerMatrixSpace> hashMap = new HashMap<>(
            28
    );

    private final int w, h;

    /**
     * 每一个图像的初始大小
     *
     * @param w 初始宽度
     * @param h 初始高度
     */
    public LetterLoad(int w, int h) {
        this.w = w;
        this.h = h;
        // 将所有字母的标准权重样本下载下来并提供给映射存储
        try {
            for (KeyValue<String, IntegerMatrixSpace> kv : Share.getImageWeight(
                    w, h,
                    // 标准初始权重 URL 样本
                    Share.INIT_LETTER_WEIGHT
            )) {
                hashMap.put(kv.getKey(), kv.getValue());
            }
        } catch (MalformedURLException e) {
            throw new OperatorOperationException("无法下载数据集!!!\nUnable to download dataset!!!", e);
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
        try {
            return Share.getData(
                    w, h,
                    Share.INIT_LETTER_NAME_X.get("A"),
                    Share.INIT_LETTER_NAME_X.get("B"),
                    Share.INIT_LETTER_NAME_X.get("C"),
                    Share.INIT_LETTER_NAME_X.get("X"),
                    Share.INIT_LETTER_NAME_X.get("Y"),
                    Share.INIT_LETTER_NAME_X.get("Z")
            );
        } catch (MalformedURLException e) {
            throw new OperatorOperationException("无法下载数据集!!!\nUnable to download dataset!!!", e);
        }
    }

    /**
     * 获取到用于训练的 Y 数据集 并将其中的数据样本以训练模型能够接收的方式返回，
     * <p>
     * Obtain the Y dataset for training and return the data samples in a way that the training model can receive,
     *
     * @return 一般是图像数据样本，这里没一个元素对应的都是一个图像的三原色矩阵空间，每一个元素与 X 训练集中的对应索引的元素相互对应。
     * <p>
     * Generally, it is an image data sample, where each element corresponds to the three primary color matrix space of an image, and each element corresponds to the corresponding index element in the X training set.
     */
    @Override
    public DoubleVector[] getY_train() {
        DoubleVector A = DoubleVector.parse(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        DoubleVector B = DoubleVector.parse(0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        DoubleVector C = DoubleVector.parse(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0);
        DoubleVector X = DoubleVector.parse(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0);
        DoubleVector Y = DoubleVector.parse(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0);
        DoubleVector Z = DoubleVector.parse(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
        return new DoubleVector[]{
                A, A,
                B, B,
                C, C, C,
                X, X, X,
                Y, Y,
                Z, Z, Z
        };
    }

    @Override
    public List<KeyValue<String, IntegerMatrixSpace>> getImageWeight() {
        return Arrays.asList(
                new KeyValue<>("A类别", this.hashMap.get("A")),
                new KeyValue<>("A类别", this.hashMap.get("A")),

                new KeyValue<>("B类别", this.hashMap.get("B")),
                new KeyValue<>("B类别", this.hashMap.get("B")),

                new KeyValue<>("C类别", this.hashMap.get("C")),
                new KeyValue<>("C类别", this.hashMap.get("C")),
                new KeyValue<>("C类别", this.hashMap.get("C")),

                new KeyValue<>("X类别", this.hashMap.get("X")),
                new KeyValue<>("X类别", this.hashMap.get("X")),
                new KeyValue<>("X类别", this.hashMap.get("X")),

                new KeyValue<>("Y类别", this.hashMap.get("Y")),
                new KeyValue<>("Y类别", this.hashMap.get("Y")),

                new KeyValue<>("Z类别", this.hashMap.get("Z")),
                new KeyValue<>("Z类别", this.hashMap.get("Z")),
                new KeyValue<>("Z类别", this.hashMap.get("Z"))
        );
    }
}
