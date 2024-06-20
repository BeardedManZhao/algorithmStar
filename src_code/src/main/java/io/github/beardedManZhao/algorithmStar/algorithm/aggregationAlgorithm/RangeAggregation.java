package io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm;

import io.github.beardedManZhao.algorithmStar.operands.vector.RangeVector;

public interface RangeAggregation {
    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param rangeVector 需要被聚合的所有元素组成的数组
     *                    <p>
     *                    An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    double calculation(RangeVector<?, ?, ?, ?> rangeVector);
}
