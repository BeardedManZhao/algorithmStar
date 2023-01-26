package zhao.algorithmMagic.algorithm.differenceAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/19 11:38:33 创建
 * 布雷柯蒂斯距离（Bray Curtis Distance）主要用于生态学和环境科学，计算坐标之间的距离。该距离取值在[ 0 , 1 ] [0,1][0,1]之间，也可以用来计算样本之间的差异。
 * <p>
 * Bray Curtis Distance is mainly used in ecology and environmental science to calculate the distance between coordinates. The distance is between [ 0 , 1 ] [0,1][0,1] and can also be used to calculate the difference between samples.
 *
 * @param <I> 该距离计算组件能够支持计算的整形坐标数据类型，这是一个泛型。
 * @param <D> 该距离计算组件能够支持计算的浮点坐标数据类型，这是一个泛型。
 * @author zhao
 */
public class BrayCurtisDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DifferenceAlgorithm<D> {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected BrayCurtisDistance() {
        this.AlgorithmName = "BrayCurtisDistance";
        this.logger = LoggerFactory.getLogger("BrayCurtisDistance");
    }

    protected BrayCurtisDistance(String AlgorithmName) {
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
     *             <p>
     *             What data type is the floating point coordinate used by this algorithm
     * @return 您需要的算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> BrayCurtisDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof BrayCurtisDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于BrayCurtisDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the BrayCurtisDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            BrayCurtisDistance<II, DD> brayCurtisDistance = new BrayCurtisDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(brayCurtisDistance);
            return brayCurtisDistance;
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
     * 计算两个事物之间从差异系数百分比
     * <p>
     * Calculate the percentage difference from the coefficient of difference between two things
     *
     * @param value1 差异参数1
     *               <p>
     *               Difference parameter 1
     * @param value2 差异参数2
     *               <p>
     *               Difference parameter 2
     * @return 差异系数
     * <p>
     * Coefficient of Difference
     */
    @Override
    public double getDifferenceRatio(D value1, D value2) {
        int numberOfDimensions1 = value1.getNumberOfDimensions();
        int numberOfDimensions2 = value2.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            if (OperationAlgorithmManager.PrintCalculationComponentLog) {
                logger.info("ⁿ∑₁ |(DV1(n) - DV2(n))| / ⁿ∑₁ DV1(n) + ⁿ∑₁ DV2(n)");
            }
            return getDifferenceRatio(value1.toArray(), value2.toArray());
        } else {
            throw new OperatorOperationException("BrayCurtisDistance算法运算出现错误，在进行两个坐标之间差异系数运算的时候，发现坐标的维度不同。\n" +
                    "There is an error in the Bray Curtis Distance algorithm operation. When the difference coefficient between the two coordinates is calculated, it is found that the dimensions of the coordinates are different.\n" +
                    "Number Of Dimensions => value1[" + numberOfDimensions1 + "]  value2[" + numberOfDimensions2 + "]");
        }
    }

    /**
     * 计算两个事物之间从差异系数百分比
     * <p>
     * Calculate the percentage difference from the coefficient of difference between two things
     *
     * @param value1 差异参数1
     *               <p>
     *               Difference parameter 1
     * @param value2 差异参数2
     *               <p>
     *               Difference parameter 2
     * @return 差异系数
     * <p>
     * Coefficient of Difference
     */
    public double getDifferenceRatio(I value1, I value2) {
        int numberOfDimensions1 = value1.getNumberOfDimensions();
        int numberOfDimensions2 = value2.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            if (OperationAlgorithmManager.PrintCalculationComponentLog) {
                logger.info("ⁿ∑₁ |(IV1(n) - IV2(n))| / ⁿ∑₁ IV1(n) + ⁿ∑₁ IV2(n)");
            }
            return getDifferenceRatio(value1.toArray(), value2.toArray());
        } else {
            throw new OperatorOperationException("BrayCurtisDistance算法运算出现错误，在进行两个坐标之间差异系数运算的时候，发现坐标的维度不同。\n" +
                    "There is an error in the Bray Curtis Distance algorithm operation. When the difference coefficient between the two coordinates is calculated, it is found that the dimensions of the coordinates are different.\n" +
                    "Number Of Dimensions => value1[" + numberOfDimensions1 + "]  value2[" + numberOfDimensions2 + "]");
        }
    }

    /**
     * 计算两个数组之间的布雷柯蒂斯距离
     * <p>
     * Calculate the BrayCurtisDistance between two arrays
     *
     * @param doubles1 计算差异数组1
     *                 <p>
     *                 Calculate difference array 1
     * @param doubles2 计算差异数组2
     *                 <p>
     *                 Calculate difference array 2
     * @return Coefficient of Difference
     */
    public double getDifferenceRatio(double[] doubles1, double[] doubles2) {
        double molecule = 0;
        double denominator = 0;
        for (int i = 0; i < doubles1.length; i++) {
            double v1 = doubles1[i];
            double v2 = doubles2[i];
            molecule += ASMath.absoluteValue(v1 - v2);
            denominator += v1 + v2;
        }
        if (OperationAlgorithmManager.PrintCalculationComponentLog) {
            logger.info("(ⁿ∑₁ |(V1(n) - V2(n))| / ⁿ∑₁ V1(n) + ⁿ∑₁ V2(n)) = (" + molecule + " / " + denominator + ")");
        }
        return molecule >= denominator ? 1 : molecule / denominator;
    }

    /**
     * 计算两个数组之间的布雷柯蒂斯距离
     * <p>
     * Calculate the BrayCurtisDistance between two arrays
     *
     * @param ints1 Calculate difference array 1
     * @param ints2 Calculate difference array 2
     * @return Coefficient of Difference
     */
    public double getDifferenceRatio(int[] ints1, int[] ints2) {
        int molecule = 0;
        int denominator = 0;
        for (int i = 0; i < ints1.length; i++) {
            int v1 = ints1[i];
            int v2 = ints2[i];
            molecule += ASMath.absoluteValue(v1 - v2);
            denominator += v1 + v2;
        }
        if (OperationAlgorithmManager.PrintCalculationComponentLog) {
            logger.info("(ⁿ∑₁ |(V1(n) - V2(n))| / ⁿ∑₁ V1(n) + ⁿ∑₁ V2(n)) = (" + molecule + " / " + denominator + ")");
        }
        return molecule >= denominator ? 1 : molecule / (double) denominator;
    }
}
