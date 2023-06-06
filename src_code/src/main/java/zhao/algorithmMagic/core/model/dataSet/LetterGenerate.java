package zhao.algorithmMagic.core.model.dataSet;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 字母图像数据生成器，其中包含所有数据集对应的目标与样本 URL 直接以生成操作的方式从网络中获取到数据，能够有效降低内存中数据集的占用。
 * <p>
 * The letter image data generator, which includes the target and sample URLs corresponding to all datasets, directly obtains data from the network through generation operations, effectively reducing the footprint of the in memory dataset.
 *
 * @author zhao
 */
public class LetterGenerate implements ASDataSetGenerate<IntegerMatrixSpace> {

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
    public LetterGenerate(int w, int h) {
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
     * 直接获取到当前数据集中所有的权重的名称。
     *
     * @return 类别名称向量。
     */
    @Override
    public String[] getNames() {
        return Share.INIT_LETTER_NAMES;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<KeyValue<KeyValue<String, IntegerMatrixSpace>, IntegerMatrixSpace[]>> iterator() {
        Iterator<KeyValue<String, String>> iterator = Share.INIT_LETTER_WEIGHT.iterator();
        return new Iterator<KeyValue<KeyValue<String, IntegerMatrixSpace>, IntegerMatrixSpace[]>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public KeyValue<KeyValue<String, IntegerMatrixSpace>, IntegerMatrixSpace[]> next() {
                KeyValue<String, String> next = iterator.next();
                String key = next.getKey();
                try {
                    return new KeyValue<>(
                            new KeyValue<>(key, hashMap.get(key)), Share.getData(w, h, Share.INIT_LETTER_NAME_X.get(key))
                    );
                } catch (MalformedURLException e) {
                    throw new OperatorOperationException(e);
                }
            }
        };
    }
}
