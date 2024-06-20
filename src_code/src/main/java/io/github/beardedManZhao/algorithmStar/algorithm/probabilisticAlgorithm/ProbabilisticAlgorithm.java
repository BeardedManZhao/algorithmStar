package io.github.beardedManZhao.algorithmStar.algorithm.probabilisticAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayDoubleFiltering;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayIntegerFiltering;

/**
 * 概率计算组件的所有抽象，包含概率计算组件中最基本的一些计算函数，所有的概率计算组件都应实现本抽象类。
 * <p>
 * All abstractions of probability calculation components include some of the most basic calculation functions of probability calculation components. All probability calculation components should implement this abstract class.
 */
public abstract class ProbabilisticAlgorithm implements OperationAlgorithm {
    protected final Logger logger;
    protected final String AlgorithmName;

    protected ProbabilisticAlgorithm(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = LoggerFactory.getLogger(algorithmName);
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public final String getAlgorithmName() {
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
    public final boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
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
    public abstract double[] estimateGetFraction(
            IntegerMatrix integerMatrix,
            ArrayIntegerFiltering StatisticCondition1,
            ArrayIntegerFiltering StatisticCondition2
    );

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
    public abstract double[] estimateGetFraction(
            DoubleMatrix doubleMatrix,
            ArrayDoubleFiltering StatisticCondition1,
            ArrayDoubleFiltering StatisticCondition2
    );

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param integerMatrix       需要被统计的矩阵对象。
     *                            <p>
     *                            The matrix object to be counted.
     * @param StatisticCondition1 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个元素是联合事件中的一个处理逻辑实现。
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element is a processing logic implementation in the joint event.
     * @param StatisticCondition2 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个元素是联合事件中的一个处理逻辑实现。
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element is a processing logic implementation in the joint event.
     * @return P(A | B)这个事件概率计算结果中的概率数值，取值范围为 [0, 1]。
     * <p>
     * P (A | B) is the probability value in the event probability calculation result. The value range is [0,1].
     */
    public final double estimate(
            IntegerMatrix integerMatrix,
            ArrayIntegerFiltering StatisticCondition1,
            ArrayIntegerFiltering StatisticCondition2
    ) {
        double[] doubles = estimateGetFraction(integerMatrix, StatisticCondition1, StatisticCondition2);
        return doubles[0] / doubles[1];
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param doubleMatrix        需要被统计的矩阵对象。
     *                            <p>
     *                            The matrix object to be counted.
     * @param StatisticCondition1 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个元素是联合事件中的一个处理逻辑实现。
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element is a processing logic implementation in the joint event.
     * @param StatisticCondition2 在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个元素是联合事件中的一个处理逻辑实现。
     *                            <p>
     *                            When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element is a processing logic implementation in the joint event.
     * @return P(A | B)这个事件概率计算结果中的概率数值，取值范围为 [0, 1]。
     * <p>
     * P (A | B) is the probability value in the event probability calculation result. The value range is [0,1].
     */
    public final double estimate(
            DoubleMatrix doubleMatrix,
            ArrayDoubleFiltering StatisticCondition1,
            ArrayDoubleFiltering StatisticCondition2
    ) {
        double[] doubles = estimateGetFraction(doubleMatrix, StatisticCondition1, StatisticCondition2);
        return doubles[0] / doubles[1];
    }
}
