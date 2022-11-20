package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/12 22:00:43 创建
 * <p>
 * 切比雪夫距离，是一种计算两点之间最短距离的度量算法，简单来说其实就是将所有坐标轴上的位移计算出来，然后取出最大值
 * 这个最大值就是两点之间的最短距离，之所以是最大值，是因为这里的移动距离，绝对不可以小于任何一个坐标变化量，但是又因为取最短距离，所以就直接返回这个最大值！
 * <p>
 * Chebyshev distance is a measurement algorithm for calculating the shortest distance between two points. In simple terms, it is actually calculating the displacements on all coordinate axes, and then taking out the maximum value. The maximum value is the shortest distance between two points. The reason why it is the maximum value is that the moving distance here cannot be less than any coordinate change, but because the shortest distance is taken, the maximum value is returned directly!
 *
 * @param <I> 本类中参与运算的整数坐标的类型，您需要在此类种指定该类可以运算的整形坐标
 *            <p>
 *            The type of integer coordinates involved in the operation in this class, you need to specify the integer coordinates that this class can operate on in this class.
 * @param <D> 本类中参与运算的浮点坐标的类型，您需要在此类种指定该类可以运算的浮点坐标
 *            <p>
 *            The type of floating-point coordinates involved in the operation in this class. You need to specify the floating-point coordinates that this class can operate on.
 * @author LingYuZhao
 */
public class ChebyshevDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected ChebyshevDistance() {
        this.AlgorithmName = "ChebyshevDistance";
        this.logger = LoggerFactory.getLogger("ChebyshevDistance");
    }

    protected ChebyshevDistance(String AlgorithmName) {
        this.logger = LoggerFactory.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @param <II> 该算法用来处理的整形坐标是什么数据类型
     *             <p>
     *             What data type is the integer coordinate used by this algorithm?
     * @param <DD> 该算法用来处理的浮点坐标是什么数据类型
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> ChebyshevDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ChebyshevDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于ChebyshevDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ChebyshevDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            ChebyshevDistance<II, DD> ChebyshevDistance = new ChebyshevDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(ChebyshevDistance);
            return ChebyshevDistance;
        }
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    /**
     * 获取到坐标点到原点的真实切比雪夫距离，该距离是两点之间的最小距离
     * <p>
     * Get the true Chebyshev distance from the coordinate point to the origin, which is the minimum distance between the two points
     *
     * @param iFloatingPointCoordinates 被计算的坐标点
     *                                  <p>
     *                                  Calculated coordinates
     * @return 该坐标点到原点的真实切比雪夫距离
     * <p>
     * The true Chebyshev distance from this coordinate point to the origin
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates) {
        double res = 0;
        logger.info("MAX(|0 - coordinate|)");
        for (double d : iFloatingPointCoordinates.toArray()) {
            double v = ASMath.absoluteValue(d);
            if (v > res) res = v;
        }
        return res;
    }

    /**
     * 获取到坐标点到原点的真实切比雪夫距离，该距离是两点之间的最小距离
     * <p>
     * Get the true Chebyshev distance from the coordinate point to the origin, which is the minimum distance between the two points
     *
     * @param integerCoordinates 被计算的坐标点
     *                           <p>
     *                           Calculated coordinates
     * @return 该坐标点到原点的真实切比雪夫距离
     * <p>
     * The true Chebyshev distance from this coordinate point to the origin
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinates) {
        int res = 0;
        logger.info("MAX(|0 - coordinate|)");
        for (int d : integerCoordinates.toArray()) {
            int v = ASMath.absoluteValue(d);
            if (v > res) res = v;
        }
        return res;
    }

    /**
     * 获取到两个坐标点之间的切比雪夫距离，也是两点之间的最短距离。
     * <p>
     * Obtain the Chebyshev distance between two coordinate points, which is also the shortest distance between two points.
     *
     * @param integerCoordinate1 起始坐标点
     *                           <p>
     *                           starting point
     * @param integerCoordinate2 终止坐标点
     *                           <p>
     *                           end point
     * @return 两个坐标之间的最短距离
     * <p>
     * shortest distance between two coordinates
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinate1, IntegerCoordinates<I> integerCoordinate2) {
        int res = 0;
        logger.info("MAX(|coordinate1 - coordinate2|)");
        for (int i : integerCoordinate1.extend().diff(integerCoordinate2.extend()).toArray()) {
            int v = ASMath.absoluteValue(i);
            if (v > res) res = v;
        }
        return res;
    }

    /**
     * 获取到两个坐标点之间的切比雪夫距离，也是两点之间的最短距离。
     * <p>
     * Obtain the Chebyshev distance between two coordinate points, which is also the shortest distance between two points.
     *
     * @param iFloatingPointCoordinates1 起始坐标点
     *                                   <p>
     *                                   starting point
     * @param iFloatingPointCoordinates2 终止坐标点
     *                                   <p>
     *                                   end point
     * @return 两个坐标之间的最短距离
     * <p>
     * shortest distance between two coordinates
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates1, FloatingPointCoordinates<D> iFloatingPointCoordinates2) {
        double res = 0;
        logger.info("MAX(|coordinate1 - coordinate2|)");
        for (double i : iFloatingPointCoordinates1.diff(iFloatingPointCoordinates2.extend()).toArray()) {
            double v = ASMath.absoluteValue(i);
            if (v > res) res = v;
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
     * @return 两个序列之间的切比雪夫距离
     */
    @Override
    public double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        return getTrueDistance(doubleConsanguinityRoute.getStartingCoordinate().toArray(), doubleConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return 两个序列之间的切比雪夫距离
     */
    @Override
    public double getTrueDistance(double[] doubles1, double[] doubles2) {
        if (doubles1.length == doubles2.length) {
            double res = 0;
            for (int i = 0; i < doubles1.length; i++) {
                double v = ASMath.absoluteValue(doubles1[i] - doubles2[i]);
                if (res < v) {
                    res = v;
                }
            }
            return res;
        } else {
            throw new OperatorOperationException("您在Chebyshev Distance中的计算发生了错误，原因是您传入的两个序列/坐标的维度数量不一致！！！\n" +
                    "Your calculation in Chebyshev Distance went wrong because the two sequence coordinates you passed in did not have the same number of dimensions! ! !" +
                    "number of dimensions => value1[" + doubles1.length + "]   value2[" + doubles2.length + "]");
        }
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return 该路线始末坐标点之间的切比雪夫距离
     */
    @Override
    public double getTrueDistance(int[] ints1, int[] ints2) {
        if (ints1.length == ints2.length) {
            int res = 0;
            for (int i = 0; i < ints1.length; i++) {
                int v = ASMath.absoluteValue(ints1[i] - ints2[i]);
                if (res < v) {
                    res = v;
                }
            }
            return res;
        } else {
            throw new OperatorOperationException("您在Chebyshev Distance中的计算发生了错误，原因是您传入的两个序列/坐标的维度数量不一致！！！\n" +
                    "Your calculation in Chebyshev Distance went wrong because the two sequence coordinates you passed in did not have the same number of dimensions! ! !" +
                    "number of dimensions => value1[" + ints1.length + "]   value2[" + ints2.length + "]");
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
     * @return 该路线始末坐标点之间的切比雪夫距离
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
     * @return 该路线始末坐标点之间的切比雪夫距离
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
     * @return 该路线始末坐标点之间的切比雪夫距离
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return getTrueDistance(integerConsanguinityRoute2D.getStartingCoordinate().toArray(), integerConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }
}
