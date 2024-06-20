package zhao.algorithmMagic.algorithm.differenceAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Set;

/**
 * Java类于 2022/10/22 16:41:41 创建
 * <p>
 * Dice系数（Dice Coefficient）用于度量两个集合的相似性，因为可以把字符串理解为一种集合，因此Dice系数也会用于度量字符串的相似性.
 * <p>
 * Dice Coefficients are used to measure the similarity of two sets. Because strings can be understood as a set, Dice Coefficients are also used to measure the similarity of strings
 *
 * @author 4
 */
public class DiceCoefficient<ElementType> implements DifferenceAlgorithm<Set<ElementType>> {


    protected final String AlgorithmName;

    protected DiceCoefficient() {
        this.AlgorithmName = "DiceCoefficient";
    }

    protected DiceCoefficient(String AlgorithmName) {
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @param <E>  该算法计算集合中的元素类型。
     *             <p>
     *             The algorithm computes the type of elements in a collection.
     * @return 您需要的算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static <E> DiceCoefficient<E> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof DiceCoefficient<?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于DiceCoefficient类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the DiceCoefficient type. Please redefine a name for this algorithm.");
            }
        } else {
            DiceCoefficient<E> DiceCoefficient = new DiceCoefficient<>(Name);
            OperationAlgorithmManager.getInstance().register(DiceCoefficient);
            return DiceCoefficient;
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
     * @param value2 差异参数2
     * @return 差异系数
     */
    @Override
    public double getDifferenceRatio(Set<ElementType> value1, Set<ElementType> value2) {
        // 计算出来交集的长度
        int size1 = ASMath.intersection(value1, value2).size();
        // 计算出来两个集合总共的长度
        int size2 = value1.size() + value2.size();
        // 2 * 交集的长度 / 并集的长度
        return (size1 << 1) / (double) size2;
    }

    /**
     * 计算两个事物之间从差异系数百分比
     * <p>
     * Calculate the percentage difference from the coefficient of difference between two things
     *
     * @param value1 差异参数1
     * @param value2 差异参数2
     * @return 差异系数
     */
    public double getDifferenceRatio(String value1, String value2) {
        // 计算出来交集的长度
        int size1 = ASMath.intersection(value1, value2).length();
        // 计算出来两个集合总共的长度
        int size2 = value1.length() + value2.length();
        // 2 * 交集的长度 / 并集的长度
        return (size1 << 1) / (double) size2;
    }
}
