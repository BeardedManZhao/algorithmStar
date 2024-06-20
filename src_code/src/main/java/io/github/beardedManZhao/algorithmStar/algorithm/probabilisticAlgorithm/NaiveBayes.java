package io.github.beardedManZhao.algorithmStar.algorithm.probabilisticAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayDoubleFiltering;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayIntegerFiltering;

/**
 * 朴素贝叶斯计算组件，该组件属于概率计算组件，能够将您所希望计算出来的事物概率计算出来。
 * <p>
 * Naive Bayesian calculation component, which is a probability calculation component, can calculate the probability of things you want to calculate.
 *
 * @author zhao
 */
public class NaiveBayes extends ProbabilisticAlgorithm {

    protected NaiveBayes(String algorithmName) {
        super(algorithmName);
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
    public static NaiveBayes getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof NaiveBayes) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于NaiveBayes类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the NaiveBayes type. Please redefine a name for this algorithm.");
            }
        } else {
            NaiveBayes NaiveBayes = new NaiveBayes(Name);
            OperationAlgorithmManager.getInstance().register(NaiveBayes);
            return NaiveBayes;
        }
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param integerMatrix       需要被统计的矩阵对象。
     *                            <p>
     *                            The matrix object to be counted.
     * @param StatisticCondition1 在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的分子值 与 分母值组合成的数组，其中索引0为分子 1为分母。
     * <p>
     * P (A | B) This is an array of numerator and denominator values in the event probability calculation results, where index 0 is numerator and 1 is denominator.
     */
    @Override
    public double[] estimateGetFraction(IntegerMatrix integerMatrix, ArrayIntegerFiltering StatisticCondition1, ArrayIntegerFiltering StatisticCondition2) {
        int[][] doubles = integerMatrix.toArrays();
        // 事件计数器
        double countA = doubles.length, countB = doubles.length;
        int doublesA = 0;
        int doublesB = 0;
        // 迭代每一行，并传递给每一个事件进行处理
        for (int[] doubles1 : doubles) {
            // 本行数据 是否满足 B 事件
            if (StatisticCondition2.isComplianceEvents(doubles1)) {
                ++doublesB;
            }
            if (StatisticCondition1.isComplianceEvents(doubles1)) {
                // 本行数据 是否满足 A 事件
                ++doublesA;
            }
        }
        // 开始汇总PAS PBS
        double PAs = doublesA / countA, PBs = doublesB / countB;
        // 开始计算朴素贝叶斯
        double min = PBs - 0.0000001;
        return new double[]{Math.min((min > 0 ? min : 0), PAs * PAs), PBs == 0 ? 1 : PBs};
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param doubleMatrix        需要被统计的矩阵对象。
     *                            <p>
     *                            The matrix object to be counted.
     * @param StatisticCondition1 在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的分子值 与 分母值组合成的数组，其中索引0为分子 1为分母。
     * <p>
     * P (A | B) This is an array of numerator and denominator values in the event probability calculation results, where index 0 is numerator and 1 is denominator.
     */
    @Override
    public double[] estimateGetFraction(DoubleMatrix doubleMatrix, ArrayDoubleFiltering StatisticCondition1, ArrayDoubleFiltering StatisticCondition2) {
        double[][] doubles = doubleMatrix.toArrays();
        // 事件计数器
        double countA = doubles.length, countB = doubles.length;
        int doublesA = 0;
        int doublesB = 0;
        // 迭代每一行，并传递给每一个事件进行处理
        for (double[] doubles1 : doubles) {
            // 本行数据 是否满足 B 事件
            if (StatisticCondition2.isComplianceEvents(doubles1)) {
                ++doublesB;
            }
            if (StatisticCondition1.isComplianceEvents(doubles1)) {
                // 本行数据 是否满足 A 事件
                ++doublesA;
            }
        }
        // 开始汇总PAS PBS
        double PAs = doublesA / countA;
        double PBs = doublesB / countB;
        // 开始计算朴素贝叶斯
        double min = PBs - 0.0000001;
        return new double[]{Math.min((min > 0 ? min : 0), PAs * PAs), PBs == 0 ? 1 : PBs};
    }
}
