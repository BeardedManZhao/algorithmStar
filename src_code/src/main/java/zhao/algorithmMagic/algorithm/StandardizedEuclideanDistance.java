package zhao.algorithmMagic.algorithm;

import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.DependentAlgorithmNameLibrary;

/**
 * Java类于 2022/10/13 10:30:50 创建
 * <p>
 * 标准化欧几里得度量，先将每一个维度的量进行标准化，然后再进行欧几里得计算
 *
 * @param <I> 本类中参与运算的整数坐标的类型，您需要在此类种指定该类可以运算的整形坐标
 *            <p>
 *            The type of integer coordinates involved in the operation in this class, you need to specify the integer coordinates that this class can operate on in this class.
 * @param <D> 本类中参与运算的浮点坐标的类型，您需要在此类种指定该类可以运算的浮点坐标
 *            <p>
 *            The type of floating-point coordinates involved in the operation in this class. You need to specify the floating-point coordinates that this class can operate on.
 * @author LingYuZhao
 */
public class StandardizedEuclideanDistance<I extends IntegerCoordinates<?>, D extends FloatingPointCoordinates<?>> extends EuclideanMetric<I, D> {

    private final EuclideanMetric<IntegerCoordinateMany, DoubleCoordinateMany> euclideanMetric = EuclideanMetric.getInstance(DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC_NAME);

    protected StandardizedEuclideanDistance() {
        super();
    }

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
    public static <II extends IntegerCoordinates<?>, DD extends FloatingPointCoordinates<?>> StandardizedEuclideanDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof StandardizedEuclideanDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EuclideanMetric类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
            }
        } else {
            StandardizedEuclideanDistance<II, DD> standardizedEuclideanDistance = new StandardizedEuclideanDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(standardizedEuclideanDistance);
            return standardizedEuclideanDistance;
        }
    }

    public EuclideanMetric<IntegerCoordinateMany, DoubleCoordinateMany> toEuclideanMetric() {
        if (this.euclideanMetric != null) {
            return this.euclideanMetric;
        } else {
            String euclideanMetricName = DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC_NAME;
            logger.warn("名称为：[" + euclideanMetricName + "]的算法被删除了，为了避免异常，标准化欧几里德算法模块所需的欧几里德算法依赖，已经直接绑定到OperationAlgorithmManager，如果您想要避免该警告，请您在不删除[EuclideanMetric]算法的前提下重启程序！\n" +
                    "The algorithm named: [" + euclideanMetricName + "] has been removed. To avoid exceptions, the Euclidean algorithm dependencies required by the standardized Euclidean algorithm module have been directly bound to the Operation Algorithm Manager, if you want to avoid the warning , please restart the program without deleting the [" + euclideanMetricName + "] algorithm!");
            return EuclideanMetric.getInstance(euclideanMetricName);
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
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates) {
        double[] doubles1 = iFloatingPointCoordinates.toArray();
        double[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        logger.info("√ ⁿ∑₁( (iFloatingPointCoordinates(n) / StandardSequence(n))² )");
        return this.euclideanMetric.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
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
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinates) {
        int[] doubles1 = integerCoordinates.toArray();
        int[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        logger.info("√ ⁿ∑₁( (integerCoordinates(n) / StandardSequence(n))² )");
        return this.euclideanMetric.getTrueDistance(new IntegerCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
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
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinateMany1, IntegerCoordinates<I> integerCoordinateMany2) {
        logger.info("√ ⁿ∑₁( " + integerCoordinateMany1 + " - " + integerCoordinateMany2 + ").map(i -> (i / StandardSequence)²)");
        int[] doubles1 = integerCoordinateMany1.diff(integerCoordinateMany2.expand()).toArray();
        int[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return this.euclideanMetric.getTrueDistance(new IntegerCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
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
    public double getTrueDistance(FloatingPointCoordinates<D> doubleCoordinateMany1, FloatingPointCoordinates<D> doubleCoordinateMany2) {
        logger.info("√ ⁿ∑₁( " + doubleCoordinateMany1 + " - " + doubleCoordinateMany2 + ").map(d -> (d / StandardSequence)²)");
        double[] doubles1 = doubleCoordinateMany1.diff(doubleCoordinateMany2.expand()).toArray();
        double[] doublesZ = Z_ScoreNormalization.StandardizedSequence(doubles1);
        return this.euclideanMetric.getTrueDistance(new DoubleCoordinateMany(DivideByNormalization(doubles1, doublesZ)));
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
