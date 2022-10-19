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

/**
 * Java类于 2022/10/12 11:16:37 创建
 * <p>
 * 闵可夫斯基距离 (Minkowski Distance)，也被称为 闵氏距离。它不仅仅是一种距离，而是将多个距离公式（曼哈顿距离、欧式距离、切比雪夫距离）总结成为的一个公式。
 * <p>
 * Minkowski Distance, also known as Minkowski Distance. It is not just a distance, but a formula that summarizes multiple distance formulas (Manhattan distance, Euclidean distance, Chebyshev distance).
 *
 * @param <I> 本类中参与运算的整数坐标的类型，您需要在此类种指定该类可以运算的整形坐标
 *            <p>
 *            The type of integer coordinates involved in the operation in this class, you need to specify the integer coordinates that this class can operate on in this class.
 * @param <D> 本类中参与运算的浮点坐标的类型，您需要在此类种指定该类可以运算的浮点坐标
 *            <p>
 *            The type of floating-point coordinates involved in the operation in this class. You need to specify the floating-point coordinates that this class can operate on.
 * @author LingYuZhao
 */
public class MinkowskiDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;
    protected int $P;

    protected MinkowskiDistance() {
        this.AlgorithmName = "MinkowskiDistance";
        this.logger = Logger.getLogger("MinkowskiDistance");
    }

    protected MinkowskiDistance(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = Logger.getLogger(algorithmName);
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
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> MinkowskiDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof MinkowskiDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于MinkowskiDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the MinkowskiDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            MinkowskiDistance<II, DD> minkowskiDistance = new MinkowskiDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(minkowskiDistance);
            return minkowskiDistance;
        }
    }

    /**
     * @return 获取到闵可夫斯基距离的参数
     * <p>
     * Get parameters for the Minkowski distance
     */
    public int get$P() {
        return $P;
    }

    /**
     * @param $P 设置闵可夫斯基距离的参数，在欧几里德和曼哈顿的度量中，唯一不同的就是一个参数 P 这个参数为2的时候是德式距离，为1的时候是曼哈顿距离，闵可夫斯基距离就是这两种距离的一个总结！
     *           <p>
     *           Set the parameters of the Minkowski distance. The only difference between the Euclid and Manhattan metrics is a parameter P. When this parameter is 2, it is the German distance, when it is 1, it is the Manhattan distance, and the Minkowski distance is these two. A summary of the distance!
     */
    public void set$P(int $P) {
        this.$P = $P;
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
        logger.info("ⁿ∑₁( coordinate^" + $P + " )^" + "1/" + $P);
        for (double d : iFloatingPointCoordinates.toArray()) {
            res += Math.pow(d, $P);
        }
        return ParameterCombination(res);
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
        logger.info("ⁿ∑₁( coordinate^" + $P + " )^" + "1/" + $P);
        for (int d : integerCoordinates.toArray()) {
            res += Math.pow(d, $P);
        }
        return ParameterCombination(res);
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
        logger.info("(ⁿ∑₁( " + integerCoordinateMany1 + " - " + integerCoordinateMany2 + ").map(d -> d²)) ^ 1/" + $P);
        int res = 0;
        for (int i : integerCoordinateMany1.extend().diff(integerCoordinateMany2.extend()).toArray()) {
            res += Math.pow(i, $P);
        }
        return ParameterCombination(res);
    }

    /**
     * 多维空间之中，两个浮点坐标之间的距离
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two double multidimensional coordinates.
     *
     * @param floatingPointCoordinate1 整形坐标1
     * @param floatingPointCoordinate2 整形坐标2
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(FloatingPointCoordinates<D> floatingPointCoordinate1, FloatingPointCoordinates<D> floatingPointCoordinate2) {
        logger.info("(ⁿ∑₁( " + floatingPointCoordinate1 + " - " + floatingPointCoordinate2 + ").map(d -> d²)) ^ 1/" + $P);
        int res = 0;
        for (double i : floatingPointCoordinate1.diff(floatingPointCoordinate2.extend()).toArray()) {
            res += Math.pow(i, $P);
        }
        return ParameterCombination(res);
    }

    private double ParameterCombination(double res) {
        if (this.$P == 2) {
            return Math.sqrt(res);
        } else {
            return Math.pow(res, 1.0 / $P);
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
     * @return 向量中始末坐标的闵可夫斯基距离
     * @apiNote 闵可夫斯基距离是总结的曼哈顿与欧几里德, 其本身通过一个P变量控制所有算法, 因此本质上也是向量的计算
     */
    @Override
    public double getTrueDistance(DoubleVector doubleVector) {
        double res = 0;
        for (double v : doubleVector.toArray()) {
            res += Math.pow(v, $P);
        }
        return ParameterCombination(res);
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

    @Override
    public String toString() {
        return "(ⁿ∑₁|x1-x2 |^" + this.$P + ")^1/" + $P;
    }
}
