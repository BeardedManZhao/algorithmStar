package zhao.algorithmMagic.algorithm.normalization;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.vector.DoubleVector;
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
public class LinearNormalization extends DataStandardization {

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
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
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
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static double[] StandardizedSequence(double[] doubles) {
        double[] res = new double[doubles.length];
        // 计算最大值最小值
        double[] doubles1 = ASMath.MaxAndMin(doubles);
        double max = doubles1[0b0];
        double min = doubles1[0b1];
        double MAX_MIN = max - min;
        // 开始将序列标准化
        for (int i = 0; i < res.length; i++) {
            res[i] = (doubles[i] - min) / MAX_MIN;
        }
        return res;
    }

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：Xn = （Xn - min(x)）/ max(x) - min(x)
     * <p>
     * To normalize a multidimensional sequence, the normalization method is to operate on each value in the sequence v: x = (x - min(x)) max(x) - min(x)
     *
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static int[] StandardizedSequence(int[] doubles) {
        int[] res = new int[doubles.length];
        int[] doubles1 = ASMath.MaxAndMin(doubles);
        int max = doubles1[0b0];
        int min = doubles1[0b1];
        int MAX_MIN = max - min;
        // 开始将序列标准化
        for (int i = 0; i < res.length; i++) {
            res[i] = (doubles[i] - min) / MAX_MIN;
        }
        return res;
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
        return new DoubleCoordinateMany(StandardizedSequence(v.toArray()));
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
        return new IntegerCoordinateMany(StandardizedSequence(v.toArray()));
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
        return new DoubleVector(StandardizedSequence(doubleVector.toArray()));
    }
}
