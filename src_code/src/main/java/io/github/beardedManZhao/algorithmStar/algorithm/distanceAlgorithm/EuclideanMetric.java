package io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.Coordinate;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.FloatingPointCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.RangeVector;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;

/**
 * Java类于 2022/10/10 11:32:08 创建
 * <p>
 * 欧几里得度量（euclidean metric）（也称欧氏距离）是一个通常采用的距离定义，指在m维空间中两个点之间的真实距离，或者向量的自然长度（即该点到原点的距离）。
 * <p>
 * 在二维和三维空间中的欧氏距离就是两点之间的实际距离。
 * <p>
 * Euclidean metric (also called Euclidean distance) is a commonly used definition of distance, which refers to the true distance between two points in m-dimensional space, or the natural length of a vector (that is, the point to the origin) the distance.
 * <p>
 * Euclidean distance in 2D and 3D space is the actual distance between two points.
 *
 * @param <I> 本类中参与运算的整数坐标的类型，您需要在此类种指定该类可以运算的整形坐标
 *            <p>
 *            The type of integer coordinates involved in the operation in this class, you need to specify the integer coordinates that this class can operate on in this class.
 * @param <D> 本类中参与运算的浮点坐标的类型，您需要在此类种指定该类可以运算的浮点坐标
 *            <p>
 *            The type of floating-point coordinates involved in the operation in this class. You need to specify the floating-point coordinates that this class can operate on.
 * @author LingYuZhao
 */
public class EuclideanMetric<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm, RangeDistance {
    protected final String AlgorithmName;

    protected EuclideanMetric() {
        this.AlgorithmName = "EuclideanMetric";
    }

    protected EuclideanMetric(String algorithmName) {
        this.AlgorithmName = algorithmName;
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @param <II> 该算法用来处理的整形坐标是什么数据类型
     *             <p>
     *             What data type is the integer coordinate used by this algorithm?
     * @param <DD> 该算法用来处理的浮点坐标是什么数据类型
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> EuclideanMetric<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof EuclideanMetric<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EuclideanMetric类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the EuclideanMetric type. Please redefine a name for this algorithm.");
            }
        } else {
            EuclideanMetric<II, DD> euclideanMetric = new EuclideanMetric<>(Name);
            OperationAlgorithmManager.getInstance().register(euclideanMetric);
            return euclideanMetric;
        }
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    /**
     * 使用一个向量计算真实距离，具体实现请参阅 api node
     * <p>
     * Use a vector to calculate the true distance, see the api node for the specific implementation
     *
     * @param doubleVector 被计算的向量
     *                     <p>
     *                     Calculated vector
     * @return 该向量始末坐标的欧几里德距离
     * <p>
     * 将函数做了一个变换, 使其能够兼容向量的计算, 欧几里德其本身就是始末坐标的差值进行的计算
     * <p>
     * The function is transformed to make it compatible with the calculation of vectors. Euclid itself is the calculation of the difference between the beginning and end coordinates.
     */
    public double getTrueDistance(DoubleVector doubleVector) {
        double res = 0;
        for (double v : doubleVector.toArray()) {
            res += ASMath.Power2(v);
        }
        return Math.sqrt(res);
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
     * 多维空间之中，自身坐标点到原点的距离，这里是多维空间，所以是每一个维度的坐标点都会被进行2次方
     * <p>
     * In the multidimensional space, the distance from its own coordinate point to the origin, here is a multidimensional space, so the coordinate points of each dimension will be squared
     *
     * @param iFloatingPointCoordinates 一个被计算的多维度坐标，其中的坐标点所处空间位置的坐标描述是无数个的
     *                                  <p>
     *                                  A multidimensional coordinate, in which the coordinate description of the spatial position of the coordinate point is infinite
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates) {
        double res = 0;
        for (double d : iFloatingPointCoordinates.toArray()) {
            res += d * d;
        }
        return Math.sqrt(res);
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
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinates) {
        int res = 0;
        for (int d : integerCoordinates.toArray()) {
            res += ASMath.Power2(d);
        }
        return Math.sqrt(res);
    }

    /**
     * 多维空间之中，计算两个整形多维坐标之间的欧式距离。
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two integer multidimensional coordinates.
     *
     * @param integerCoordinateMany1 整形坐标1
     * @param integerCoordinateMany2 整形坐标2
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinateMany1, IntegerCoordinates<I> integerCoordinateMany2) {
        int res = 0;
        for (int i : integerCoordinateMany1.extend().diff(integerCoordinateMany2.extend()).toArray()) {
            res += ASMath.Power2(i);
        }
        return Math.sqrt(res);
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
    public double getTrueDistance(FloatingPointCoordinates<D> doubleCoordinateMany1, FloatingPointCoordinates<D> doubleCoordinateMany2) {
        int res = 0;
        for (double i : doubleCoordinateMany1.diff(doubleCoordinateMany2.extend()).toArray()) {
            res += (int) ASMath.Power2(i);
        }
        return Math.sqrt(res);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute 需要被计算的路线对象
     *                                 <p>
     *                                 The route object that needs to be calculated
     * @return ...
     */
    @Override
    public double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        return getTrueDistance(doubleConsanguinityRoute.getStartingCoordinate().toArray(), doubleConsanguinityRoute.getEndPointCoordinate().toArray());
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
        if (doubles1.length == doubles2.length) {
            double res = 0;
            for (int i = 0; i < doubles1.length; i++) {
                res += ASMath.Power2(doubles1[i] - doubles2[i]);
            }
            return Math.sqrt(res);
        } else {
            throw new OperatorOperationException("计算度量发生错误，在计算欧几里得度量时，请使用两个长度相同的序列样本!!!\n" +
                    "An error occurred in calculating the metric. Please use two sequence samples with the same length when calculating Euclidean metric!!!\n" +
                    "v1.length = " + doubles1.length + '\t' + "v2.length = " + doubles2.length);
        }
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
        if (ints1.length == ints2.length) {
            int res = 0;
            for (int i = 0; i < ints1.length; i++) {
                res += ASMath.Power2(ints1[i] - ints2[i]);
            }
            return Math.sqrt(res);
        } else {
            throw new OperatorOperationException("计算度量发生错误，在计算欧几里得度量时，请使用两个长度相同的序列样本!!!\n" +
                    "An error occurred in calculating the metric. Please use two sequence samples with the same length when calculating Euclidean metric!!!\n" +
                    "v1.length = " + ints1.length + '\t' + "v2.length = " + ints2.length);
        }
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute2D 需要被计算的路线对象
     *                                   <p>
     *                                   The route object that needs to be calculated
     * @return 路线中始末点之间的欧几里德距离
     * <p>
     * Euclidean distance between start and end points in a route
     */
    @Override
    public double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        return getTrueDistance(doubleConsanguinityRoute2D.getStartingCoordinate().toArray(), doubleConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute 需要被计算的路线对象
     *                                  <p>
     *                                  The route object that needs to be calculated
     * @return Euclidean distance between start and end points in a route
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute) {
        return getTrueDistance(integerConsanguinityRoute.getStartingCoordinate().toArray(), integerConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute2D 需要被计算的路线对象
     *                                    <p>
     *                                    The route object that needs to be calculated
     * @return Euclidean distance between start and end points in a route
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return getTrueDistance(integerConsanguinityRoute2D.getStartingCoordinate().toArray(), integerConsanguinityRoute2D.getEndPointCoordinate().toArray());
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
        int res = 0;
        for (int[] ints : integerMatrix1.diff(matrix2)) {
            for (int anInt : ints) {
                res += ASMath.Power2(anInt);
            }
        }
        return Math.sqrt(res);
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
        double res = 0;
        for (double[] ints : matrix1.diff(matrix2)) {
            for (double anInt : ints) {
                res += ASMath.Power2(anInt);
            }
        }
        return Math.sqrt(res);
    }

    /**
     * 计算向量距离原点的距离。
     *
     * @param rangeDistance 需要被计算的向量。
     * @return 计算出来的距离结果数值。
     */
    @Override
    public double getTrueDistance(RangeVector<?, ?, ?, ?> rangeDistance) {
        return rangeDistance.moduleLength();
    }
}
