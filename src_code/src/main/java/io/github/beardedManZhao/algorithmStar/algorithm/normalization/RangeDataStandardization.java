package io.github.beardedManZhao.algorithmStar.algorithm.normalization;


import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.FastRangeDoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.FastRangeIntegerVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;

/**
 * 数据预处理计算组件，其中包含了与数据预处理相关的所有计算函数。
 * <p>
 * Data preprocessing calculation component, which contains all calculation functions related to data preprocessing.
 *
 * @author zhao
 */
public interface RangeDataStandardization {

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param fastRangeIntegerVector 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                               <p>
     *                               The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    IntegerVector pretreatment(FastRangeIntegerVector fastRangeIntegerVector);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param fastRangeDoubleVector 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                              <p>
     *                              The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    DoubleVector pretreatment(FastRangeDoubleVector fastRangeDoubleVector);
}
