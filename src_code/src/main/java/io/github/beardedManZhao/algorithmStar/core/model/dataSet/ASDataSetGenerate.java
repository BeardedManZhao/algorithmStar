package io.github.beardedManZhao.algorithmStar.core.model.dataSet;

import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

/**
 * 数据集生成器，最推荐的数据集获取组件，该组件将会以最适合当前训练数据过程的方式，一个样本一个样本的进行训练操作。
 * <p>
 * Dataset generator, the most recommended dataset acquisition component, will perform training operations one sample at a time in the most suitable way for the current training data process.
 *
 * @param <outType> 当前数据集组件支持的数据组件类型。
 *                  <p>
 *                  The data component types supported by the current dataset component.
 */
public interface ASDataSetGenerate<outType> extends Iterable<KeyValue<KeyValue<String, outType>, outType[]>> {

    /**
     * 直接获取到当前数据集中所有的权重的名称。
     *
     * @return 类别名称向量。
     */
    String[] getNames();

}
