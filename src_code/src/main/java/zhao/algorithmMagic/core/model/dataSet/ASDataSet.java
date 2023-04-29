package zhao.algorithmMagic.core.model.dataSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    Logger LOGGER = LoggerFactory.getLogger("ASDataSet");

    /**
     * 获取到训练的时候使用的图像样本。
     */
    static IntegerMatrixSpace[] getData(int w, int h, String... urls) throws MalformedURLException {
        IntegerMatrixSpace[] integerMatrixSpaces = new IntegerMatrixSpace[urls.length];
        int index = -1;
        for (String url : urls) {
            LOGGER.info("downLoad train image => " + url);
            integerMatrixSpaces[++index] = IntegerMatrixSpace.parse(new URL(url), w, h);
        }
        return integerMatrixSpaces;
    }

    /**
     * 获取到权重数据样本
     */
    static List<KeyValue<String, IntegerMatrixSpace>> getImageWeight(int w, int h, List<KeyValue<String, String>> nameAndUrl) throws MalformedURLException {
        ArrayList<KeyValue<String, IntegerMatrixSpace>> list = new ArrayList<>(nameAndUrl.size() + 2);
        for (KeyValue<String, String> value : nameAndUrl) {
            LOGGER.info("downLoad category image => category[" + value.getKey() + "]\tURL = " + value.getValue());
            list.add(new KeyValue<>(value.getKey(), IntegerMatrixSpace.parse(new URL(value.getValue()), w, h)));
        }
        return list;
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
    IntegerMatrixSpace[] getImageX_train();

    /**
     * 获取到用于训练的 Y 数据集 并将其中的数据样本以训练模型能够接收的方式返回，
     * <p>
     * Obtain the Y dataset for training and return the data samples in a way that the training model can receive,
     *
     * @return 一般是最终的得分向量，其中每一个元素与数据集相互对应。
     */
    DoubleVector[] getImageY_train();

    /**
     * 获取到 X 的数据样本对象，在某些针对文本的数据集中支持该操作。
     *
     * @return 用于回任务的数据样本，在这里是针对回归任务中的 X 训练数据集。
     */
    DoubleVector[] getLineX_train();

    /**
     * 获取到 Y 的数据样本对象，在某些针对文本的数据集中支持该操作。
     *
     * @return 用于回归任务的数据样本，在这里是针对胡贵任务中的 Y 目标数据集。
     */
    DoubleVector getLineY_train();

    /**
     * 获取到所有 Y 数据对应的初始权重。
     *
     * @return 所有 Y 对应的初始权重。
     */
    List<KeyValue<String, IntegerMatrixSpace>> getImageWeight();

    enum Load {
        LETTER {
            @Override
            public ASDataSet load(int w, int h) {
                return new Letter(w, h);
            }
        };

        /**
         * 将数据集加载出来
         *
         * @param w 数据集中的单个数据样本的宽度。
         * @param h 数据集中的单个数据样本的高度。
         * @return 数据集对应的对象
         */
        public abstract ASDataSet load(int w, int h);
    }
}
