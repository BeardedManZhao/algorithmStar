package zhao.algorithmMagic.algorithm.aggregationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;

/**
 * 聚合算法接口，是所有聚合算法的父类，所有的聚合算法偶读应该实现此接口。
 * <p>
 * The aggregation algorithm interface is the parent of all aggregation algorithms. All aggregation algorithm even reads should implement this interface.
 *
 * @author zhao
 */
public interface AggregationAlgorithm extends OperationAlgorithm {
    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param doubles 需要被聚合的所有元素组成的数组
     *                <p>
     *                An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    double calculation(double... doubles);


    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param doubles 需要被聚合的所有元素组成的数组
     *                <p>
     *                An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    int calculation(int... doubles);

}
