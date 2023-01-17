package zhao.algorithmMagic.algorithm.aggregationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.vector.RangeVector;
import zhao.algorithmMagic.utils.ASClass;

/**
 * 加权平均值计算组件，在默认情况下就是不加权计算平均数，如有权重需求，可以调用 ” calculation(double[] Weight, double... doubles) “ 并设置权重参数。
 * <p>
 * The weighted average calculation component is not weighted by default. If there is a weight requirement, you can call "calculation (double [] Weight, double... double)" and set the weight parameters.
 *
 * @author zhao
 */
public class WeightedAverage extends WeightBatchAggregation implements RangeAggregation {

    protected WeightedAverage(String name) {
        super(name);
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static WeightedAverage getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof WeightedAverage) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于WeightedAverage类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the WeightedAverage type. Please redefine a name for this algorithm.");
            }
        } else {
            WeightedAverage WeightedAverage = new WeightedAverage(Name);
            OperationAlgorithmManager.getInstance().register(WeightedAverage);
            return WeightedAverage;
        }
    }

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
    @Override
    public double calculation(double[] Weight, double... doubles) {
        double res = 0;
        if (Weight == null) {
            // 代表不加权
            for (double aDouble : doubles) {
                res += aDouble;
            }
        } else {
            if (Weight.length < doubles.length) {
                throw new OperatorOperationException(ERROR + "\nWeight.length=[" + Weight.length + "]\tvector.length=[" + doubles.length + "]");
            }
            // 代表加权计算数值
            for (int i = 0; i < doubles.length; i++) {
                res += doubles[i] * Weight[i];
            }
        }
        return res / doubles.length;
    }

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
    @Override
    public int calculation(double[] Weight, int... ints) {
        int res = 0;
        if (Weight == null) {
            // 代表不加权
            for (int aDouble : ints) {
                res += aDouble;
            }
        } else {
            try {
                // 代表加权计算数值
                for (int i = 0; i < ints.length; i++) {
                    res += ints[i] * Weight[i];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new OperatorOperationException(ERROR + "\nWeight.length=[" + Weight.length + "]\tvector.length=[" + ints.length + "]", e);
            }
        }
        return res / ints.length;
    }

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
    @Override
    public double calculation(RangeVector<?, ?, ?, ?> rangeVector) {
        return rangeVector.getRangeSum().doubleValue() / rangeVector.size();
    }
}
