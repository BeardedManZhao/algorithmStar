package io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.vector.RangeVector;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.filter.NumericalFilteringAndMAXorMIN;

/**
 * 最值计算组件，在该组件中能够对序列中的元素进行最值的聚合计算，提取出所有元素中的极值，需要注意的是，该组件支持极大与极小数值的计算，需要调用setMode函数来将极值计算模式进行变更。
 * <p>
 * The maximum value calculation component, in which the maximum value of the elements in the sequence can be aggregated and calculated, and the extreme values of all elements can be extracted. It should be noted that this component supports the calculation of maximum and minimum values, and the setMode function needs to be called to change the extreme value calculation mode.
 *
 * @author zhao
 */
public class ExtremumAggregation extends BatchAggregation implements RangeAggregation {

    /**
     * 普通最小值
     */
    public final static NumericalFilteringAndMAXorMIN MIN = new NumericalFilteringAndMAXorMIN(false) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return true;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return true;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return true;
        }
    };

    /**
     * 普通最大值
     */
    public final static NumericalFilteringAndMAXorMIN MAX = new NumericalFilteringAndMAXorMIN(true) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return true;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return true;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return true;
        }
    };

    /**
     * 奇数中的最小值
     */
    public final static NumericalFilteringAndMAXorMIN ODD_MIN = new NumericalFilteringAndMAXorMIN(false) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return v.doubleValue() % 2 != 0;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return (v - (v >> 1 << 1)) != 0;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return v % 2 != 0;
        }
    };

    /**
     * 奇数中的最大值
     */
    public final static NumericalFilteringAndMAXorMIN ODD_MAX = new NumericalFilteringAndMAXorMIN(true) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return v.doubleValue() % 2 != 0;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return (v - (v >> 1 << 1)) != 0;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return v % 2 != 0;
        }
    };

    /**
     * 偶数中的最小值
     */
    public final static NumericalFilteringAndMAXorMIN EVEN_MIN = new NumericalFilteringAndMAXorMIN(false) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return v.doubleValue() % 2 == 0;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return (v - (v >> 1 << 1)) == 0;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return v % 2 == 0;
        }
    };

    /**
     * 偶数中的最大值
     */
    public final static NumericalFilteringAndMAXorMIN EVEN_MAX = new NumericalFilteringAndMAXorMIN(true) {
        @Override
        public boolean isComplianceEvents(Number v) {
            return v.doubleValue() % 2 == 0;
        }

        @Override
        public boolean isComplianceEvents(int v) {
            return (v - (v >> 1 << 1)) == 0;
        }

        @Override
        public boolean isComplianceEvents(double v) {
            return v % 2 == 0;
        }
    };

    private final static OperatorOperationException OPERATOR_OPERATION_EXCEPTION = new OperatorOperationException("被聚合的数组长度不能为0!!!!\nThe aggregated array length cannot be 0!!!!");
    private NumericalFilteringAndMAXorMIN mode = MAX;

    protected ExtremumAggregation(String name) {
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
    public static ExtremumAggregation getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ExtremumAggregation) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于ExtremumAggregation类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ExtremumAggregation type. Please redefine a name for this algorithm.");
            }
        } else {
            ExtremumAggregation ExtremumAggregation = new ExtremumAggregation(Name);
            OperationAlgorithmManager.getInstance().register(ExtremumAggregation);
            return ExtremumAggregation;
        }
    }

    /**
     * 设置最值提取模式，您可以在 ExtremumAggregation 类中获取到对应的 mode 编码，默认是提取最大值。
     * <p>
     * Set the maximum extraction mode. You can get the corresponding mode code in the ExtremumAggregation class. The default is to extract the maximum value.
     *
     * @param mode 最值提取模式编号，在 ExtremumAggregation 中有静态变量可以引用，
     *             <p>
     *             The maximum extraction mode number. There are static variables that can be referenced in ExtremumAggregation.
     */
    public void setMode(NumericalFilteringAndMAXorMIN mode) {
        this.mode = mode;
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
        if (doubles.length == 0) {
            throw OPERATOR_OPERATION_EXCEPTION;
        }
        if (mode == MIN) {
            return doubles[ASMath.findMinIndex(doubles)];
        } else if (mode == MAX) {
            return doubles[ASMath.findMaxIndex(doubles)];
        }
        return ASMath.MaxOrMin(doubles, mode.isMax, mode);
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param ints 需要被聚合的所有元素组成的数组
     *             <p>
     *             An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    @Override
    public int calculation(int... ints) {
        if (ints.length == 0) throw OPERATOR_OPERATION_EXCEPTION;
        if (mode.equals(MAX)) return ints[ASMath.findMaxIndex(ints)];
        else if (mode == MIN) return ints[ASMath.findMinIndex(ints)];
        return ASMath.MaxOrMin(ints, mode.isMax, mode);
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
        if (mode.equals(MAX)) {
            return rangeVector.getRangeEnd().doubleValue();
        } else if (mode.equals(MIN)) {
            return rangeVector.getRangeStart().doubleValue();
        } else {
            if (mode.isMax) {
                // 查看最大值是否满足过滤器条件
                double v = rangeVector.getRangeEnd().doubleValue();
                if (mode.isComplianceEvents(v)) {
                    // 如果满足就直接返回
                    return v;
                } else {
                    // 如果不满足就直接返回上一个数值
                    return v - 1;
                }
            } else {
                // 查看最小值是否满足过滤器条件
                double v = rangeVector.getRangeStart().doubleValue();
                if (mode.isComplianceEvents(v)) {
                    // 如果满足就直接返回
                    return v;
                } else {
                    // 如果不满足就直接返回下一个数值
                    return v + 1;
                }
            }
        }
    }
}
