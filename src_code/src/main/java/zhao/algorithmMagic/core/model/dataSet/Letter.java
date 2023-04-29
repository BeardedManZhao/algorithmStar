package zhao.algorithmMagic.core.model.dataSet;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 字母数据集类，通过该类可加载到来自网络中的字母数据集。
 *
 * Alphabet dataset class, which can be loaded into alphabet datasets from the network.
 * @author 赵凌宇
 * 2023/4/29 7:48
 */
public class Letter implements ASDataSet {

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
    public Letter(int w, int h) {
        this.w = w;
        this.h = h;
        // 将所有字母的标准权重样本下载下来并提供给映射存储
        try {
            for (KeyValue<String, IntegerMatrixSpace> kv : ASDataSet.getImageWeight(
                    w, h,
                    // 标准初始权重 URL 样本
                    Arrays.asList(
                            new KeyValue<>("A", "https://user-images.githubusercontent.com/113756063/234247437-32e5b5ff-0baf-4637-805c-27472f07fd17.jpg"),
                            new KeyValue<>("X", "https://user-images.githubusercontent.com/113756063/234247472-1df7f892-89b5-467f-9d8d-eb397b92c6ce.jpg"),
                            new KeyValue<>("Y", "https://user-images.githubusercontent.com/113756063/234247497-0a329b5d-a15d-451f-abf7-abdc1655b77d.jpg"),
                            new KeyValue<>("Z", "https://user-images.githubusercontent.com/113756063/235150408-233cc477-c97d-48a1-b39e-ff9a497ea9ff.jpg")
                    )
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
    public IntegerMatrixSpace[] getImageX_train() {
        try {
            return ASDataSet.getData(
                    w, h,
                    // A
                    "https://user-images.githubusercontent.com/113756063/234247437-32e5b5ff-0baf-4637-805c-27472f07fd17.jpg",
                    "https://user-images.githubusercontent.com/113756063/234247630-46319338-b8e6-47bf-9918-4b734e665cf9.jpg",
                    // X
                    "https://user-images.githubusercontent.com/113756063/234247472-1df7f892-89b5-467f-9d8d-eb397b92c6ce.jpg",
                    "https://user-images.githubusercontent.com/113756063/235150686-c4b84e78-1b24-409d-a26f-6a860ed104d8.jpg",
                    "https://user-images.githubusercontent.com/113756063/234247550-01777cee-493a-420f-8665-da31e60a1cbe.jpg",
                    // Y
                    "https://user-images.githubusercontent.com/113756063/234247497-0a329b5d-a15d-451f-abf7-abdc1655b77d.jpg",
                    "https://user-images.githubusercontent.com/113756063/235150464-9d082c41-ae06-4ee7-a680-59e62cd55b10.jpg",
                    // Z
                    "https://user-images.githubusercontent.com/113756063/235150408-233cc477-c97d-48a1-b39e-ff9a497ea9ff.jpg",
                    "https://user-images.githubusercontent.com/113756063/235150989-e71036bd-f8bb-43b9-b566-e3131827d7ac.jpg"
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
    public DoubleVector[] getImageY_train() {
        DoubleVector A = DoubleVector.parse(1, 1, 0, 0, 0, 0, 0, 0, 0);
        DoubleVector X = DoubleVector.parse(0, 0, 1, 1, 1, 0, 0, 0, 0);
        DoubleVector Y = DoubleVector.parse(0, 0, 0, 0, 0, 1, 1, 0, 0);
        DoubleVector Z = DoubleVector.parse(0, 0, 0, 0, 0, 0, 0, 1, 1);
        return new DoubleVector[]{
                A, A,
                X, X, X,
                Y, Y,
                Z, Z
        };
    }

    /**
     * 获取到 X 的数据样本对象，在某些针对文本的数据集中支持该操作。
     *
     * @return 用于回任务的数据样本，在这里是针对回归任务中的 X 训练数据集。
     */
    @Override
    public DoubleVector[] getLineX_train() {
        throw new UnsupportedOperationException("不支持字母数据集 to 向量");
    }

    /**
     * 获取到 Y 的数据样本对象，在某些针对文本的数据集中支持该操作。
     *
     * @return 用于回归任务的数据样本，在这里是针对胡贵任务中的 Y 目标数据集。
     */
    @Override
    public DoubleVector getLineY_train() {
        throw new UnsupportedOperationException("不支持字母数据集 to 向量");
    }

    @Override
    public List<KeyValue<String, IntegerMatrixSpace>> getImageWeight() {
        return Arrays.asList(
                new KeyValue<>("A类别", this.hashMap.get("A")),
                new KeyValue<>("A类别", this.hashMap.get("A")),

                new KeyValue<>("X类别", this.hashMap.get("X")),
                new KeyValue<>("X类别", this.hashMap.get("X")),
                new KeyValue<>("X类别", this.hashMap.get("X")),

                new KeyValue<>("Y类别", this.hashMap.get("Y")),
                new KeyValue<>("Y类别", this.hashMap.get("Y")),

                new KeyValue<>("Z类别", this.hashMap.get("Z")),
                new KeyValue<>("Z类别", this.hashMap.get("Z"))
        );
    }
}
