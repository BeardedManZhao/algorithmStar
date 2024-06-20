package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASClass;

/**
 * Java类于 2022/10/13 10:30:50 创建
 * <p>
 * 标准化欧几里得度量，先将每一个维度的量进行标准化，然后再进行欧几里得计算
 *
 * @author LingYuZhao
 */
public class StandardizedEuclideanDistance extends EuclideanMetric<IntegerCoordinateMany, DoubleCoordinateMany> {

    protected StandardizedEuclideanDistance(String algorithmName) {
        super(algorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static StandardizedEuclideanDistance getInstance2(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof StandardizedEuclideanDistance) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于StandardizedEuclideanDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the StandardizedEuclideanDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            StandardizedEuclideanDistance standardizedEuclideanDistance = new StandardizedEuclideanDistance(Name);
            OperationAlgorithmManager.getInstance().register(standardizedEuclideanDistance);
            return standardizedEuclideanDistance;
        }
    }

    /**
     * 多维空间之中，自身坐标点到原点的距离，这里是多维空间，所以是每一个维度的坐标点都会被进行2次方
     * <p>
     * In the multidimensional space, the distance from its own coordinate point to the origin, here is a multidimensional space, so the coordinate points of each dimension will be squared
     *
     * @param iFloatingPointCoordinates 一个被计算的多维度坐标，其中的坐标点所处空间位置的坐标描述是无数个的
     *                                  <p>
     *                                  A multidimensional coordinate, in which the coordinate description of the spatial position of the coordinate point is infinite
     * @return Euclidean distance from its own coordinates to the origin
     */
    @Override
    public double getTrueDistance(FloatingPointCoordinates<DoubleCoordinateMany> iFloatingPointCoordinates) {
        double[] doubles1 = iFloatingPointCoordinates.toArray();
        double[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return super.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
    }

    /**
     * 非标准序列的每一个数值 对 标准序列的每一个数值进行除法
     *
     * @param NonstandardSequence 非标准序列
     * @param StandardSequence    标准序列
     * @return 标准欧几里德需要的新标准序列
     */
    private double[] DivideByNormalization(double[] NonstandardSequence, double[] StandardSequence) {
        double[] res = new double[NonstandardSequence.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = NonstandardSequence[i] / StandardSequence[i];
        }
        return res;
    }

    /**
     * 非标准序列的每一个数值 对 标准序列的每一个数值进行除法
     *
     * @param NonstandardSequence 非标准序列
     * @param StandardSequence    标准序列
     * @return 标准欧几里德需要的新标准序列
     */
    private int[] DivideByNormalization(int[] NonstandardSequence, int[] StandardSequence) {
        int[] res = new int[NonstandardSequence.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = NonstandardSequence[i] / StandardSequence[i];
        }
        return res;
    }

    /**
     * 多维空间之中，自身坐标点到原点的距离，这里是多维空间，所以是每一个维度的坐标点都会被进行2次方
     * <p>
     * In the multidimensional space, the distance from its own coordinate point to the origin, here is a multidimensional space, so the coordinate points of each dimension will be squared
     *
     * @param integerCoordinates 一个被计算的多维度坐标，其中的坐标点所处空间位置的坐标描述是无数个的
     *                           <p>
     *                           A multidimensional coordinate, in which the coordinate description of the spatial position of the coordinate point is infinite
     * @return Euclidean distance from its own coordinates to the origin
     */
    @Override
    public double getTrueDistance(IntegerCoordinates<IntegerCoordinateMany> integerCoordinates) {
        int[] doubles1 = integerCoordinates.toArray();
        int[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return super.getTrueDistance(new IntegerCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
    }

    /**
     * 多维空间之中，计算两个整形多维坐标之间的欧式距离。
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two integer multidimensional coordinates.
     * TODO
     *
     * @param integerCoordinateMany1 整形坐标1
     * @param integerCoordinateMany2 整形坐标2
     * @return True Euclidean distance between two points
     */
    @Override
    public double getTrueDistance(IntegerCoordinates<IntegerCoordinateMany> integerCoordinateMany1, IntegerCoordinates<IntegerCoordinateMany> integerCoordinateMany2) {
        int[] doubles1 = integerCoordinateMany1.extend().diff(integerCoordinateMany2.extend()).toArray();
        int[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return super.getTrueDistance(new IntegerCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
    }

    /**
     * 多维空间之中，两个浮点坐标之间的距离
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two double multidimensional coordinates.
     *
     * @param doubleCoordinateMany1 整形坐标1
     * @param doubleCoordinateMany2 整形坐标2
     * @return True Euclidean distance between two points
     */
    @Override
    public double getTrueDistance(FloatingPointCoordinates<DoubleCoordinateMany> doubleCoordinateMany1, FloatingPointCoordinates<DoubleCoordinateMany> doubleCoordinateMany2) {
        double[] doubles1 = doubleCoordinateMany1.diff(doubleCoordinateMany2.extend()).toArray();
        double[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return super.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
    }

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    @Override
    public boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return ...
     */
    @Override
    public double getTrueDistance(double[] doubles1, double[] doubles2) {
        double[] doubles11 = new DoubleCoordinateMany(doubles1).diff(new DoubleCoordinateMany(doubles2)).toArray();
        double[] doubles22 = Z_ScoreNormalization.StandardizedSequence(doubles11);
        return super.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles11, doubles22)));
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return 两个序列之间的欧几里德距离
     * <p>
     * Euclidean distance between two sequences
     */
    @Override
    public double getTrueDistance(int[] ints1, int[] ints2) {
        double[] doubles11 = new DoubleCoordinateMany(ints1).diff(new DoubleCoordinateMany(ints2)).toArray();
        double[] doubles22 = Z_ScoreNormalization.StandardizedSequence(doubles11);
        return super.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles11, doubles22)));
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param integerMatrix1 需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @param matrix2        需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(IntegerMatrix integerMatrix1, IntegerMatrix matrix2) {
        throw new UnsupportedOperationException("The matrix does not currently support serialization operations");
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param matrix1 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @param matrix2 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(DoubleMatrix matrix1, DoubleMatrix matrix2) {
        throw new UnsupportedOperationException("The matrix does not currently support serialization operations");
    }
}
