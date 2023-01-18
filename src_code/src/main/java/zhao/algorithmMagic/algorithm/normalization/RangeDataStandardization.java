package zhao.algorithmMagic.algorithm.normalization;


import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.FastRangeDoubleVector;
import zhao.algorithmMagic.operands.vector.FastRangeIntegerVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

/**
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
    IntegerVector NormalizedSequence(FastRangeIntegerVector fastRangeIntegerVector);

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
    DoubleVector NormalizedSequence(FastRangeDoubleVector fastRangeDoubleVector);
}
