package zhao.algorithmMagic.algorithm.normalization;

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/13 12:39:17 创建
 *
 * @author 4
 */
public class Z_ScoreNormalization extends DataStandardization {

    /**
     * 将一个多维序列标准化，标准化手段就是序列v中的每一个数值进行运算：x = （x - v的均值）/ 标准差
     *
     * @param doubles 需要被标准化的一个序列
     * @return 标准化的结果
     */
    public static double[] StandardizedSequence(double[] doubles) {
        double[] res = new double[doubles.length];
        double avg = ASMath.avg(doubles);
        // 计算标准差 = 对方差进行算数平方根
        double sqrt = Math.sqrt(ASMath.variance(doubles));
        for (int i = 0; i < res.length; i++) {
            double x = doubles[i];
            res[i] = (x - avg) / sqrt;
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
        int avg = ASMath.avg(doubles);
        // 计算标准差 = 对方差进行算数平方根
        double sqrt = Math.sqrt(ASMath.variance(doubles));
        for (int i = 0; i < res.length; i++) {
            double x = doubles[i];
            res[i] = (int) ((x - avg) / sqrt);
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
     * @param doubleVector@return v的标准化样式
     *                            <p>
     *                            Normalized style of v
     */
    @Override
    public DoubleVector NormalizedSequence(DoubleVector doubleVector) {
        return new DoubleVector(StandardizedSequence(doubleVector.toArray()));
    }
}
