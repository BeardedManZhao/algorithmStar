package io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm;

import io.github.beardedManZhao.algorithmStar.operands.vector.RangeVector;

public interface RangeDistance {
    /**
     * 计算向量距离原点的距离。
     *
     * @param rangeDistance 需要被计算的向量。
     * @return 计算出来的距离结果数值。
     */
    double getTrueDistance(RangeVector<?, ?, ?, ?> rangeDistance);
}
