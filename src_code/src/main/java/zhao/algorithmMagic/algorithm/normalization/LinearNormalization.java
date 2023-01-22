package zhao.algorithmMagic.algorithm.normalization;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.FastRangeDoubleVector;
import zhao.algorithmMagic.operands.vector.FastRangeIntegerVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/14 17:37:54 创建
 * <p>
 * 线性归一化可以说是更容易且灵活 的归一化技术。 它通常被称为“max-min”归一化，它允许分析人员获取集合中最大 x 值和最小 x 值之间的差值，并建立一个基数。
 * <p>
 * Linear normalization is arguably an easier and more flexible normalization technique. Often referred to as "max-min" normalization, it allows the analyst to take the difference between the largest and smallest x-values in a set and establish a cardinality.
 *
 * @author zhao
 */
public class LinearNormalization extends DataStandardization implements RangeDataStandardization {

    private double max = 1;
    private double min = 0;

    protected LinearNormalization(String algorithmName) {
        super(algorithmName);
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
    public static LinearNormalization getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof LinearNormalization) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于LinearNormalization类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the LinearNormalization type. Please redefine a name for this algorithm.");
            }
        } else {
            LinearNormalization linearNormalization = new LinearNormalization(Name);
            OperationAlgorithmManager.getInstance().register(linearNormalization);
            return linearNormalization;
        }
    }

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：Xn = （Xn - min(x)）/ max(x) - min(x)
     * <p>
     * To normalize a multidimensional sequence, the normalization method is to operate on each value in the sequence v: x = (x - min(x)) max(x) - min(x)
     *
     * @param max1    归一化之后的每一个元素的允许的最小值
     * @param min1    归一化之后的每一个元素的允许的最大值
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static double[] normalization(double[] doubles, double min1, double max1) {
        // 计算最大值最小值
        double[] doubles1 = ASMath.MaxAndMin(doubles);
        double max = doubles1[0b0];
        double min = doubles1[0b1];
        // 开始将序列标准化
        return normalization(doubles, min, max, min1, max1);
    }

    /**
     * 将一个多维序列归一化，在该函数中可以指定归一化之后的结果区间数值
     *
     * @param doubles 需要被归一化的序列
     * @param min     向量序列中的最大值
     * @param max     向量序列中的最小值
     * @param max1    归一化之后的每一个元素的允许的最小值
     * @param min1    归一化之后的每一个元素的允许的最大值
     * @return 归一化之后的新向量数组对象。
     */
    public static double[] normalization(double[] doubles, double min, double max, double min1, double max1) {
        double[] res = new double[doubles.length];
        // 判断是否需要归一化，不需要就原样返回
        if (max <= max1 && min >= min1) {
            System.arraycopy(doubles, 0, res, 0, res.length);
            return res;
        }
        // 如果需要就开始归一化
        double MAX_MIN = max - min;
        double MAX_MIN1 = max1 - min1;
        for (int i = 0; i < res.length; i++) {
            res[i] = ((doubles[i] - min) / MAX_MIN) * MAX_MIN1 + min1;
        }
        return res;
    }

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：Xn = （Xn - min(x)）/ max(x) - min(x)
     * <p>
     * To normalize a multidimensional sequence, the normalization method is to operate on each value in the sequence v: x = (x - min(x)) max(x) - min(x)
     *
     * @param max1 归一化之后的每一个元素的允许的最小值
     * @param min1 归一化之后的每一个元素的允许的最大值
     * @param ints 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static int[] normalization(int[] ints, int min1, int max1) {
        int[] doubles1 = ASMath.MaxAndMin(ints);
        int max = doubles1[0b0];
        int min = doubles1[0b1];
        // 开始将序列标准化
        return normalization(doubles1, min, max, min1, max1);
    }

    /**
     * 将一个多维序列归一化，在该函数中可以指定归一化之后的结果区间数值
     *
     * @param ints 需要被归一化的序列
     * @param min  向量序列中的最大值
     * @param max  向量序列中的最小值
     * @param max1 归一化之后的每一个元素的允许的最小值
     * @param min1 归一化之后的每一个元素的允许的最大值
     * @return 归一化之后的新向量对象数组
     */
    public static int[] normalization(int[] ints, int min, int max, int min1, int max1) {
        int[] res = new int[ints.length];
        // 判断是否需要归一化，不需要就原样返回
        if (max <= max1 && min >= min1) {
            System.arraycopy(ints, 0, res, 0, res.length);
            return res;
        }
        // 如果需要就开始归一化
        int MAX_MIN = max - min;
        int MAX_MIN1 = max1 - min1;
        for (int i = 0; i < res.length; i++) {
            res[i] = ((ints[i] - min) / MAX_MIN) * MAX_MIN1 + min1;
        }
        return res;
    }

    /**
     * 设置归一化之后的序列最大值
     *
     * @param max 最大值数值
     * @return 链式调用
     */
    public LinearNormalization setMax(double max) {
        this.max = max;
        return this;
    }

    /**
     * 设置归一化之后的序列最小值
     *
     * @param min 最小值数值
     * @return 链式调用
     */
    public LinearNormalization setMin(double min) {
        this.min = min;
        return this;
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param v 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *          <p>
     *          The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public FloatingPointCoordinates<DoubleCoordinateMany> NormalizedSequence(DoubleCoordinateMany v) {
        return new DoubleCoordinateMany(normalization(v.toArray(), min, max));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param v 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *          <p>
     *          The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public IntegerCoordinates<IntegerCoordinateMany> NormalizedSequence(IntegerCoordinateMany v) {
        return new IntegerCoordinateMany(normalization(v.toArray(), (int) min, (int) max));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param doubleVector 需要被标准化的向量
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public DoubleVector NormalizedSequence(DoubleVector doubleVector) {
        return new DoubleVector(normalization(doubleVector.toArray(), min, max));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param integerVector 需要被标准化的向量
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public IntegerVector NormalizedSequence(IntegerVector integerVector) {
        return new IntegerVector(normalization(integerVector.toArray(), (int) min, (int) max));
    }

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
    @Override
    public IntegerVector NormalizedSequence(FastRangeIntegerVector fastRangeIntegerVector) {
        return NormalizedSequence(fastRangeIntegerVector.toVector());
    }

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
    @Override
    public DoubleVector NormalizedSequence(FastRangeDoubleVector fastRangeDoubleVector) {
        return NormalizedSequence(fastRangeDoubleVector.toVector());
    }
}
