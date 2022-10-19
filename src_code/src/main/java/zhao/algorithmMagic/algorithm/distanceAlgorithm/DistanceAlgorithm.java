package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * 距离算法的接口,计算两点之间的具体坐标距离
 * <p>
 * The interface of the distance algorithm, which calculates the specific coordinate distance between two points
 */
public interface DistanceAlgorithm extends OperationAlgorithm {

    /**
     * 使用一个向量计算真实距离，具体实现请参阅 api node
     * <p>
     * Use a vector to calculate the true distance, see the api node for the specific implementation
     *
     * @param doubleVector 被计算的向量
     *                     <p>
     *                     Calculated vector
     * @return ...
     */
    double getTrueDistance(DoubleVector doubleVector);

}
