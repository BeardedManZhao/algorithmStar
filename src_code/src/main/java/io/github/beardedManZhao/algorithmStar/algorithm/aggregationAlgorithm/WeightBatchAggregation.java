package io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm;

/**
 * 加权批聚合算法的抽象类，继承此类的聚合算法组件将会支持加权计算与聚合计算两种方式，在这两种计算方式的影响下，能够衍生实现出更多的计算需求与功能。
 * <p>
 * This is an abstract class of weighted batch aggregation algorithm. The aggregation algorithm component inheriting this class will support weighted calculation and aggregation calculation. Under the influence of these two calculation methods, more computing requirements and functions can be derived and implemented.
 *
 * @author zhao
 */
public abstract class WeightBatchAggregation extends BatchAggregation implements WeightAggregation {

    protected final static String ERROR = "权重参数设置错误，您的权重参数元素数量不应小于序列中的元素数量。\nThe weight parameter is set incorrectly. The number of your weight parameter elements should not be less than the number of elements in the sequence.";

    protected WeightBatchAggregation(String name) {
        super(name);
    }


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
    @Override
    public double calculation(double... doubles) {
        return calculation(null, doubles);
    }

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
    @Override
    public int calculation(int... doubles) {
        return calculation(null, doubles);
    }
}
