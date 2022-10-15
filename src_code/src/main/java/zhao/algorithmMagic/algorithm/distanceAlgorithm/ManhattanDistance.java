package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
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
public class ManhattanDistance<I extends IntegerCoordinates<?>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected ManhattanDistance() {
        this.AlgorithmName = "ManhattanDistance";
        this.logger = Logger.getLogger("ManhattanDistance");
    }

    protected ManhattanDistance(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = Logger.getLogger(algorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static <II extends IntegerCoordinates<?>, DD extends FloatingPointCoordinates<?>> ManhattanDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ManhattanDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EuclideanMetric类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
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
     * Manhattan distance from the coordinate point to the origin
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
     * Manhattan distance from the coordinate point to the origin
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
        for (double d : floatingPointCoordinate1.diff(floatingPointCoordinate2.expand()).toArray()) {
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
        for (int d : (integerCoordinate1.diff(integerCoordinate2.expand())).toArray()) {
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
     * @apiNote 将函数做了一个变换, 使其能够兼容向量的计算, 曼哈顿度量其本身就是始末坐标的差值进行的计算
     * The function is transformed to make it compatible with the calculation of vectors. The Manhattan metric itself is the calculation of the difference between the start and end coordinates.
     */
    @Override
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
}
