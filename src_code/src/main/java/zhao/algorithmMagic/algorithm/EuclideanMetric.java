package zhao.algorithmMagic.algorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.IntegerCoordinates;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

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
public class EuclideanMetric<I extends IntegerCoordinates<?>, D extends FloatingPointCoordinates<?>> implements OperationAlgorithm {
    protected final Logger logger;
    protected final String AlgorithmName;

    protected EuclideanMetric() {
        this.AlgorithmName = "EuclideanMetric";
        this.logger = Logger.getLogger("EuclideanMetric");
    }

    protected EuclideanMetric(String algorithmName) {
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
    public static <II extends IntegerCoordinates<?>, DD extends FloatingPointCoordinates<?>> EuclideanMetric<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof EuclideanMetric<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EuclideanMetric类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
            }
        } else {
            EuclideanMetric<II, DD> euclideanMetric = new EuclideanMetric<>(Name);
            OperationAlgorithmManager.getInstance().register(euclideanMetric);
            return euclideanMetric;
        }
    }

    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
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
        logger.info("√ ⁿ∑₁( coordinate² )");
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
        logger.info("√ ⁿ∑₁( coordinate² )");
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
        logger.info("√ ⁿ∑₁( " + integerCoordinateMany1 + " - " + integerCoordinateMany2 + ").map(d -> d²)");
        int res = 0;
        for (int i : integerCoordinateMany1.diff(integerCoordinateMany2.expand()).toArray()) {
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
        logger.info("√ ⁿ∑₁( " + doubleCoordinateMany1 + " - " + doubleCoordinateMany2 + ").map(d -> d²)");
        int res = 0;
        for (double i : doubleCoordinateMany1.diff(doubleCoordinateMany2.expand()).toArray()) {
            res += ASMath.Power2(i);
        }
        return Math.sqrt(res);
    }
}
