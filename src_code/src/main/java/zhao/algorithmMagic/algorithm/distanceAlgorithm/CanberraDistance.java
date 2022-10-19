package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/14 12:47:02 创建
 * <p>
 * 兰氏距离（Lance and Williams Distance）又称为堪培拉距离（Canberra Distance），被认为曼哈顿距离（Manhattan Distance）的加权版本。通常兰氏距离对于接近于0（大于等于0）的值的变化非常敏感。
 * <p>
 * The Lance and Williams Distance, also known as the Canberra Distance, is considered a weighted version of the Manhattan Distance. Usually the Rankine distance is very sensitive to changes in values close to 0 (greater than or equal to 0).
 *
 * @author zhao
 */
public class CanberraDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected CanberraDistance() {
        this.AlgorithmName = "CanberraDistance";
        this.logger = Logger.getLogger("CanberraDistance");
    }

    protected CanberraDistance(String AlgorithmName) {
        this.logger = Logger.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
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
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> CanberraDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof CanberraDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于CanberraDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the CanberraDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            CanberraDistance<II, DD> canberraDistance = new CanberraDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(canberraDistance);
            return canberraDistance;
        }
    }

    /**
     * 获取到坐标点到原点的真实兰氏距离
     * <p>
     * Get the true Rankine distance from the coordinate point to the origin
     *
     * @param iFloatingPointCoordinates 被计算的坐标点
     *                                  <p>
     *                                  Calculated coordinates
     * @return 该坐标点到原点的真实兰氏距离
     * <p>
     * distance from the coordinate point to the origin
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates) {
        logger.info("ⁿ∑₁ ((|COORDINATE(n) - 0|) / (|COORDINATE(n)| + |0|))");
        return iFloatingPointCoordinates.getNumberOfDimensions();
    }

    /**
     * 获取到坐标点到原点的真实兰氏距离
     * <p>
     * Get the true Rankine distance from the coordinate point to the origin
     *
     * @param integerCoordinates 被计算的坐标点
     *                           <p>
     *                           Calculated coordinates
     * @return 该坐标点到原点的真实兰氏距离
     * <p>
     * distance from the coordinate point to the origin
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinates) {
        logger.info("ⁿ∑₁ ((|COORDINATE(n) - 0|) / (|COORDINATE(n)| + |0|))");
        return integerCoordinates.getNumberOfDimensions();
    }

    /**
     * 获取到两个坐标点之间的兰氏距离。
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
        logger.info("ⁿ∑₁ ((|COORDINATE1(n) - COORDINATE2(n)|) / (|COORDINATE1(n)| + |COORDINATE2(n)|))");
        int[] ints1 = integerCoordinate1.extend().diff(integerCoordinate2.extend()).toArray();
        int[] ints2 = integerCoordinate1.extend().add(integerCoordinate2.extend()).toArray();
        double res = 0;
        for (int i = 0; i < ints1.length; i++) {
            res += (double) ASMath.absoluteValue(ints1[i] - ints2[i]) / ASMath.absoluteValue(ints1[i] + ints2[i]);
        }
        return res;
    }

    /**
     * 获取到两个坐标点之间的兰氏距离。
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
        logger.info("ⁿ∑₁ ((|COORDINATE1(n) - COORDINATE2(n)|) / (|COORDINATE1(n)| + |COORDINATE2(n)|))");
        double[] doubles1 = iFloatingPointCoordinates1.diff(iFloatingPointCoordinates2.extend()).toArray();
        double[] doubles2 = iFloatingPointCoordinates1.add(iFloatingPointCoordinates2.extend()).toArray();
        double res = 0;
        for (int i = 0; i < doubles1.length; i++) {
            res += ASMath.absoluteValue(doubles1[i] - doubles2[i]) / (ASMath.absoluteValue(doubles1[i]) + ASMath.absoluteValue(doubles2[i]));
        }
        return res;
    }

    /**
     * 提取向量中起始点与终止点坐标的最短距离
     * <p>
     * Extract the shortest distance between the start point and end point coordinates in a vector
     *
     * @param doubleVector 被计算的向量
     *                     <p>
     *                     Calculated vector
     * @return 向量中起始点与终止点坐标的最短距离
     * <p>
     * The shortest distance between the start point and end point coordinates in the vector
     */
    @Override
    public double getTrueDistance(DoubleVector doubleVector) {
        logger.info("ⁿ∑₁ (doubleVector(n))");
        double res = 0;
        for (double v : doubleVector.toArray()) {
            res += ASMath.absoluteValue(v);
        }
        return res;
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
