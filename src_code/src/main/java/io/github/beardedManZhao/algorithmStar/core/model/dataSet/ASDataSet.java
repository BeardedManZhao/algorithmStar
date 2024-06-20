package io.github.beardedManZhao.algorithmStar.core.model.dataSet;

import io.github.beardedManZhao.algorithmStar.operands.matrix.block.IntegerMatrixSpace;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

import java.io.File;
import java.util.List;

/**
 * 数据集类接口，通常是用于数据集获取的操纵方式。
 * <p>
 * A dataset class interface is typically a manipulation method used for dataset retrieval.
 *
 * @author 赵凌宇
 * 2023/4/29 7:35
 */
public interface ASDataSet {

    /**
     * 获取到用于训练的 X 数据集 并将其中的数据样本以训练模型能够接收的方式返回。
     * <p>
     * Obtain the X dataset for training and return the data samples in a way that the training model can receive.
     *
     * @return 一般是图像数据样本，这里每一个元素对应的都是一个图像的三原色矩阵空间。
     * <p>
     * Generally, it is an image data sample, where each element corresponds to the three primary color matrix space of an image.
     */
    IntegerMatrixSpace[] getX_train();

    /**
     * 获取到用于训练的 Y 数据集 并将其中的数据样本以训练模型能够接收的方式返回，
     * <p>
     * Obtain the Y dataset for training and return the data samples in a way that the training model can receive,
     *
     * @return 一般是最终的得分向量，其中每一个元素与数据集相互对应。
     */
    DoubleVector[] getY_train();

    /**
     * 获取到所有 Y 数据对应的初始权重。
     *
     * @return 所有 Y 对应的初始权重。
     */
    List<KeyValue<String, IntegerMatrixSpace>> getImageWeight();

    enum Load {

        /**
         * 从网络中获取到字母数据集并返回数据集对应的操作对象的函数。
         */
        LETTER {
            @Override
            public ASDataSet load(int w, int h, String... args) {
                return new LetterLoad(w, h);
            }
        },

        /**
         * 从文件目录中获取到需要被训练的数据，其会自动的将目录作为类别名称进行训练操作。
         */
        FILE_DTR {
            @Override
            public ASDataSet load(int w, int h, String... args) {
                return new ImageDirLoad(w, h, new File(args[0]));
            }
        };

        /**
         * 将数据集加载出来
         *
         * @param w    数据集中的单个数据样本的宽度。
         * @param h    数据集中的单个数据样本的高度。
         * @param args 数据加载的其它参数数据。
         * @return 数据集对应的对象
         */
        public abstract ASDataSet load(int w, int h, String... args);
    }

    enum Generate {
        LETTER {
            @Override
            public ASDataSetGenerate<IntegerMatrixSpace> generate(int w, int h, String... args) {
                return new LetterGenerate(w, h);
            }
        };

        public abstract ASDataSetGenerate<IntegerMatrixSpace> generate(int w, int h, String... args);
    }
}
