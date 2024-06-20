package io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;

/**
 * 计算两个非向量等事物的差异的算法
 * <p>
 * Algorithms for computing the difference of two things that are not vectors, etc.
 *
 * @param <value> 需要计算的事务类型。
 *                <p>
 *                The type of transaction that needs to be calculated.
 */
public interface DifferenceAlgorithm<value> extends OperationAlgorithm {

    /**
     * 计算两个事物之间的差异系数百分比
     * <p>
     * Calculate the percentage difference the coefficient of difference between two things
     *
     * @param value1 差异参数1
     * @param value2 差异参数2
     * @return 差异系数
     */
    double getDifferenceRatio(value value1, value value2);

}
