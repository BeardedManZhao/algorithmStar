package zhao.algorithmMagic.algorithm.aggregationAlgorithm;

/**
 * 权重聚合计算组件的接口抽象，在该抽象中，包含带有权重的计算方式，通过权重的调整实现强悍的功能
 * 权重在此类中是不受限制的任何数值，在不同数值的影响下，结果数值也会发生变化，一般来说，一个数值x被加权的结果为 (x * 权重值)
 * <p>
 * The interface abstraction of the weight aggregation calculation component, in which the calculation method with weight is included, and powerful functions are realized through weight adjustment
 * <p>
 * The weight is any unlimited value in this category. Under the influence of different values, the result value will also change. Generally speaking, the weighted result of a value x is (x * weight value)
 */
public interface WeightAggregation extends AggregationAlgorithm {

    /**
     * 带有权重的方式将一个序列中所有的元素进行聚合计算，并将结果返回
     * <p>
     * The method with weight aggregates all elements in a sequence and returns the results
     *
     * @param Weight  权重数组，其中每一个元素都代表一个权重数值，在序列中与权重数值的索引相同的元素将会受到权重数值的影响，权重数值的区间不受限制!!!
     *                <p>
     *                Weight array, in which each element represents a weight value. Elements with the same index as the weight value in the sequence will be affected by the weight value, and the range of the weight value is unlimited!!!
     * @param doubles 需要被聚合的数据组成的序列，其中每一个元素都是计算时候的一部分。
     *                <p>
     *                A sequence of data to be aggregated, in which each element is a part of the calculation.
     * @return 在加权计算的影响下，计算出来的聚合结果。
     * <p>
     * The aggregation result calculated under the influence of weighted calculation.
     */
    double calculation(double[] Weight, double... doubles);

    /**
     * 带有权重的方式将一个序列中所有的元素进行聚合计算，并将结果返回
     * <p>
     * The method with weight aggregates all elements in a sequence and returns the results
     *
     * @param Weight 权重数组，其中每一个元素都代表一个权重数值，在序列中与权重数值的索引相同的元素将会受到权重数值的影响，权重数值的区间不受限制!!!
     *               <p>
     *               Weight array, in which each element represents a weight value. Elements with the same index as the weight value in the sequence will be affected by the weight value, and the range of the weight value is unlimited!!!
     * @param ints   需要被聚合的数据组成的序列，其中每一个元素都是计算时候的一部分。
     *               <p>
     *               A sequence of data to be aggregated, in which each element is a part of the calculation.
     * @return 在加权计算的影响下，计算出来的聚合结果。
     * <p>
     * The aggregation result calculated under the influence of weighted calculation.
     */
    int calculation(double[] Weight, int... ints);
}
