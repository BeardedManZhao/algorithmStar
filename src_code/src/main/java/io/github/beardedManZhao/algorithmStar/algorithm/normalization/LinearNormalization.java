package io.github.beardedManZhao.algorithmStar.algorithm.normalization;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.FloatingPointCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.FastRangeDoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.FastRangeIntegerVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;

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
    public static double[] pretreatment(double[] doubles, double min1, double max1) {
        // 计算最大值最小值
        double[] doubles1 = ASMath.MaxAndMin(doubles);
        double max = doubles1[0b0];
        double min = doubles1[0b1];
        // 开始将序列标准化
        return pretreatment(doubles, min, max, min1, max1);
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
    public static double[] pretreatment(double[] doubles, double min, double max, double min1, double max1) {
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
    public static int[] pretreatment(int[] ints, int min1, int max1) {
        int[] doubles1 = ASMath.MaxAndMin(ints);
        int max = doubles1[0b0];
        int min = doubles1[0b1];
        // 开始将序列标准化
        return pretreatment(ints, min, max, min1, max1);
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
    public static int[] pretreatment(int[] ints, int min, int max, int min1, int max1) {
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
            res[i] = (int) (((ints[i] - min) / (double) MAX_MIN) * MAX_MIN1 + min1);
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
    public static int[][] pretreatment(int[][] ints, int min1, int max1) {
        int[] doubles1 = ASMath.MaxAndMin(ints);
        int max = doubles1[0b0];
        int min = doubles1[0b1];
        // 开始将序列标准化
        return pretreatment(ints, min, max, min1, max1);
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
    public static int[][] pretreatment(int[][] ints, int min, int max, int min1, int max1) {
        int[][] res1 = new int[ints.length][ints[0].length];
        // 判断是否需要归一化，不需要就原样返回
        if (max <= max1 && min >= min1) {
            return ASClass.array2DCopy(ints, res1);
        }
        // 如果需要就开始归一化
        int MAX_MIN = max - min;
        int MAX_MIN1 = max1 - min1;
        int y = -1;
        for (int[] res : ints) {
            int[] ints1 = res1[++y];
            for (int i = 0; i < res.length; i++) {
                ints1[i] = (int) (((res[i] - min) / (double) MAX_MIN) * MAX_MIN1 + min1);
            }
        }
        return res1;
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
    public static double[][] pretreatment(double[][] doubles, double min1, double max1) {
        // 计算最大值最小值
        double[] doubles1 = ASMath.MaxAndMin(doubles);
        // 开始将序列标准化
        return pretreatment(doubles, doubles1[0b1], doubles1[0b0], min1, max1);
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
    public static double[][] pretreatment(double[][] ints, double min, double max, double min1, double max1) {
        double[][] res1 = new double[ints.length][ints[0].length];
        // 判断是否需要归一化，不需要就原样返回
        if (max <= max1 && min >= min1) {
            return ASClass.array2DCopy(ints, res1);
        }
        // 如果需要就开始归一化
        double MAX_MIN = max - min;
        double MAX_MIN1 = max1 - min1;
        int y = -1;
        for (double[] res : ints) {
            double[] doubles = res1[++y];
            for (int i = 0; i < res.length; i++) {
                doubles[i] = ((res[i] - min) / MAX_MIN) * MAX_MIN1 + min1;
            }
        }
        return res1;
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
    public FloatingPointCoordinates<DoubleCoordinateMany> pretreatment(DoubleCoordinateMany v) {
        return new DoubleCoordinateMany(pretreatment(v.toArray(), min, max));
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
    public IntegerCoordinates<IntegerCoordinateMany> pretreatment(IntegerCoordinateMany v) {
        return new IntegerCoordinateMany(pretreatment(v.toArray(), (int) min, (int) max));
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
    public DoubleVector pretreatment(DoubleVector doubleVector) {
        return new DoubleVector(pretreatment(doubleVector.toArray(), min, max));
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
    public IntegerVector pretreatment(IntegerVector integerVector) {
        return new IntegerVector(pretreatment(integerVector.toArray(), (int) min, (int) max));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param doubleMatrix 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                     <p>
     *                     The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public DoubleMatrix pretreatment(DoubleMatrix doubleMatrix) {
        return DoubleMatrix.parse(pretreatment(doubleMatrix.toArrays(), min, max));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param integerMatrix 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                      <p>
     *                      The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public IntegerMatrix pretreatment(IntegerMatrix integerMatrix) {
        return IntegerMatrix.parse(pretreatment(integerMatrix.toArrays(), (int) min, (int) max));
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
    public IntegerVector pretreatment(FastRangeIntegerVector fastRangeIntegerVector) {
        return pretreatment(fastRangeIntegerVector.toVector());
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
    public DoubleVector pretreatment(FastRangeDoubleVector fastRangeDoubleVector) {
        return pretreatment(fastRangeDoubleVector.toVector());
    }
}
