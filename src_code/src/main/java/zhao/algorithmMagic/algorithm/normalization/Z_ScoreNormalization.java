package zhao.algorithmMagic.algorithm.normalization;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.core.ASDynamicLibrary;
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
 * Java类于 2022/10/13 12:39:17 创建
 * <p>
 * Z_ScoreNormalization,针对序列的标准化有着巨大的效果，能够将数值按照正负均匀分配。
 * <p>
 * Z Score Normalization, which has a huge effect on the normalization of sequences, can evenly distribute the values according to positive and negative.
 *
 * @author zhao
 */
public class Z_ScoreNormalization extends DataStandardization implements RangeDataStandardization {

    protected Z_ScoreNormalization(String algorithmName) {
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
    public static Z_ScoreNormalization getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof LinearNormalization) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于LinearNormalization类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the LinearNormalization type. Please redefine a name for this algorithm.");
            }
        } else {
            Z_ScoreNormalization z_scoreNormalization = new Z_ScoreNormalization(Name);
            OperationAlgorithmManager.getInstance().register(z_scoreNormalization);
            return z_scoreNormalization;
        }
    }

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：x = （x - v的均值）/ 标准差
     *
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static double[] StandardizedSequence(double[] doubles) {
        double[] res = new double[doubles.length];
        if (ASDynamicLibrary.isUseC()) {
            // 计算标准差 = 对方差进行算数平方根
            double sqrt = Math.sqrt(ASMath.variance(doubles)), avg = ASMath.avg_C(doubles.length, doubles);
            for (int i = 0; i < res.length; i++) {
                double x = doubles[i];
                res[i] = (int) ((x - avg) / sqrt);
            }
        } else {
            double avg = ASMath.avg(doubles);
            // 计算标准差 = 对方差进行算数平方根
            double sqrt = Math.sqrt(ASMath.variance(doubles));
            for (int i = 0; i < res.length; i++) {
                double x = doubles[i];
                res[i] = (int) ((x - avg) / sqrt);
            }
        }
        return res;
    }

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：x = （x - v的均值）/ 标准差
     *
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static int[] StandardizedSequence(int[] doubles) {
        int[] res = new int[doubles.length];
        if (ASDynamicLibrary.isUseC()) {
            double avg = ASMath.avg_C(doubles.length, doubles);
            // 计算标准差 = 对方差进行算数平方根
            double sqrt = Math.sqrt(ASMath.variance(doubles));
            for (int i = 0; i < res.length; i++) {
                double x = doubles[i];
                res[i] = (int) ((x - avg) / sqrt);
            }
        } else {
            int avg = ASMath.avg(doubles);
            // 计算标准差 = 对方差进行算数平方根
            double sqrt = Math.sqrt(ASMath.variance(doubles));
            for (int i = 0; i < res.length; i++) {
                double x = doubles[i];
                res[i] = (int) ((x - avg) / sqrt);
            }
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
    public FloatingPointCoordinates<DoubleCoordinateMany> pretreatment(DoubleCoordinateMany v) {
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
    public IntegerCoordinates<IntegerCoordinateMany> pretreatment(IntegerCoordinateMany v) {
        return new IntegerCoordinateMany(StandardizedSequence(v.toArray()));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param doubleVector 需要被标准化的向量序列
     *                     <p>
     *                     sequence of vectors to be normalized
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public DoubleVector pretreatment(DoubleVector doubleVector) {
        return new DoubleVector(StandardizedSequence(doubleVector.toArray()));
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param integerVector 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                      <p>
     *                      The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    @Override
    public IntegerVector pretreatment(IntegerVector integerVector) {
        return new IntegerVector(StandardizedSequence(integerVector.toArray()));
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
        return pretreatment(fastRangeIntegerVector);
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
        return pretreatment(fastRangeDoubleVector);
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
