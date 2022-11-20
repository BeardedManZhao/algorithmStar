package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.*;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/10 19:02:36 创建
 * 出租车几何或曼哈顿距离（Manhattan Distance）是由十九世纪的赫尔曼·闵可夫斯基所创词汇 ，是种使用在几何度量空间的几何学用语，用以标明两个点在标准坐标系上的绝对轴距总和。
 * <p>
 * 曼哈顿度量所计算的也是两点之间的距离，但是不同的是距离并不是直线，而是折线
 * <p>
 * Taxi geometry or Manhattan Distance is a term coined by Hermann Minkowski in the 19th century and is a geometric term used in geometric metric spaces to indicate two points on a standard coordinate system The absolute wheelbase sum of .
 * <p>
 * The Manhattan metric also calculates the distance between two points, but the difference is that the distance is not a straight line, but a polyline
 *
 * @param <I> 本类中参与运算的整数坐标的类型，您需要在此类种指定该类可以运算的整形坐标
 *            <p>
 *            The type of integer coordinates involved in the operation in this class, you need to specify the integer coordinates that this class can operate on in this class.
 * @param <D> 本类中参与运算的浮点坐标的类型，您需要在此类种指定该类可以运算的浮点坐标
 *            <p>
 *            The type of floating-point coordinates involved in the operation in this class. You need to specify the floating-point coordinates that this class can operate on.
 * @author LingYuZhao
 */
public class ManhattanDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected ManhattanDistance() {
        this.AlgorithmName = "ManhattanDistance";
        this.logger = LoggerFactory.getLogger("ManhattanDistance");
    }

    protected ManhattanDistance(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = LoggerFactory.getLogger(algorithmName);
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
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> ManhattanDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ManhattanDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于ManhattanDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the ManhattanDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            ManhattanDistance<II, DD> manhattanDistance = new ManhattanDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(manhattanDistance);
            return manhattanDistance;
        }
    }

    /**
     * 获取到坐标点到原点的真实曼哈顿距离
     * <p>
     * Get the true Manhattan distance from the coordinate point to the origin
     *
     * @param iFloatingPointCoordinates 被计算的坐标点
     *                                  <p>
     *                                  Calculated coordinates
     * @return 该坐标点到原点的真实兰氏距离
     * <p>
     * Manhattan distances from the coordinate point to the origin
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates) {
        logger.info("ⁿ∑₁ (|COORDINATE(n) - 0|)");
        double res = 0;
        for (double v : iFloatingPointCoordinates.toArray()) {
            res += ASMath.absoluteValue(v);
        }
        return res;
    }

    /**
     * 获取到坐标点到原点的真实曼哈顿距离
     * <p>
     * Get the true Manhattan distance from the coordinate point to the origin
     *
     * @param integerCoordinates 被计算的坐标点
     *                           <p>
     *                           Calculated coordinates
     * @return 该坐标点到原点的真实兰氏距离
     * <p>
     * Manhattan distances from the coordinate point to the origin
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinates) {
        logger.info("ⁿ∑₁ (|COORDINATE(n) - 0|)");
        double res = 0;
        for (double v : integerCoordinates.toArray()) {
            res += ASMath.absoluteValue(v);
        }
        return res;
    }

    /**
     * 在多维空间之内，计算两个点之间的曼哈顿距离。
     * <p>
     * Within a multidimensional space, calculate the Manhattan distance between two points.
     *
     * @param floatingPointCoordinate1 多维空间中的第一个坐标点
     *                                 <p>
     *                                 The first coordinate point in multidimensional space.
     * @param floatingPointCoordinate2 多维空间中的第二个坐标点
     *                                 <p>
     *                                 Second coordinate point in multidimensional space.
     * @return 两个多维坐标点之间的曼哈顿距离。
     * <p>
     * Manhattan distances between two multidimensional coordinate points.
     */
    public double getTrueDistance(FloatingPointCoordinates<D> floatingPointCoordinate1, FloatingPointCoordinates<D> floatingPointCoordinate2) {
        double res = 0;
        logger.info("√ ⁿ∑₁( " + floatingPointCoordinate1 + " - " + floatingPointCoordinate2 + ").map(d -> |d|)");
        for (double d : floatingPointCoordinate1.diff(floatingPointCoordinate2.extend()).toArray()) {
            res += ASMath.absoluteValue(d);
        }
        return res;
    }

    /**
     * 在多维空间之内，计算两个点之间的曼哈顿距离。
     * <p>
     * Within a multidimensional space, calculate the Manhattan distance between two points.
     *
     * @param integerCoordinate1 多维空间中的第一个坐标点
     *                           <p>
     *                           The first coordinate point in multidimensional space.
     * @param integerCoordinate2 多维空间中的第二个坐标点
     *                           <p>
     *                           Second coordinate point in multidimensional space.
     * @return 两个多维坐标点之间的曼哈顿距离。
     * <p>
     * Manhattan distances between two multidimensional coordinate points.
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinate1, IntegerCoordinates<I> integerCoordinate2) {
        int res = 0;
        logger.info("√ ⁿ∑₁( " + integerCoordinate1 + " - " + integerCoordinate2 + ").map(d -> |d|)");
        for (int d : (integerCoordinate1.extend().diff(integerCoordinate2.extend())).toArray()) {
            res += ASMath.absoluteValue(d);
        }
        return res;
    }

    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return AlgorithmName;
    }

    /**
     * 使用一个向量计算真实距离，具体实现请参阅 api node
     * <p>
     * Use a vector to calculate the true distance, see the api node for the specific implementation
     *
     * @param doubleVector 被计算的向量
     *                     <p>
     *                     Calculated vector
     * @return 该向量中始末坐标的曼哈顿距离
     * <p>
     * 将函数做了一个变换, 使其能够兼容向量的计算, 曼哈顿度量其本身就是始末坐标的差值进行的计算
     * The function is transformed to make it compatible with the calculation of vectors. The Manhattan metric itself is the calculation of the difference between the start and end coordinates.
     */
    public double getTrueDistance(DoubleVector doubleVector) {
        double res = 0;
        for (double v : doubleVector.toArray()) {
            res += ASMath.absoluteValue(v);
        }
        return res;
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
        double[] doubles = new DoubleCoordinateMany(doubles1).diff(new DoubleCoordinateMany(doubles2)).toArray();
        logger.info("√ ⁿ∑₁( Xn - Yn )²");
        double res = 0;
        for (double aDouble : doubles) {
            res += ASMath.absoluteValue(aDouble);
        }
        return res;
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return ...
     */
    @Override
    public double getTrueDistance(int[] ints1, int[] ints2) {
        int[] ints = new IntegerCoordinateMany(ints1).diff(new IntegerCoordinateMany(ints2)).toArray();
        logger.info("√ ⁿ∑₁( Xn - Yn )²");
        int res = 0;
        for (int anInt : ints) {
            res += ASMath.absoluteValue(anInt);
        }
        return res;
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute2D 需要被计算的路线对象
     *                                   <p>
     *                                   The route object that needs to be calculated
     * @return ...
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
     * @return ...
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
     * @return ...
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return getTrueDistance(integerConsanguinityRoute2D.getStartingCoordinate().toArray(), integerConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }
}
