package io.github.beardedManZhao.algorithmStar.algorithm.schemeAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayDoubleFiltering;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayIntegerFiltering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 决策算法抽象类，是针对多种处理方案进行最优排列或优化等的优秀算法，所有的决策算法都应实现与本抽象类。
 * <p>
 * The abstract class of decision algorithm is an excellent algorithm for optimal arrangement or optimization of multiple processing schemes. All decision algorithms should be implemented with this abstract class.
 *
 * @author 赵凌宇
 */
public abstract class SchemeAlgorithm implements OperationAlgorithm {

    protected final String AlgorithmName;

    protected SchemeAlgorithm(String AlgorithmName) {
        this.AlgorithmName = AlgorithmName;
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
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param doubleMatrix         当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(DoubleMatrix doubleMatrix, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decision(doubleMatrix.toArrays(), 2, arrayDoubleFiltering);
    }


    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param integerMatrix         当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(IntegerMatrix integerMatrix, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decision(integerMatrix.toArrays(), 2, arrayIntegerFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(double[][] ints, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decision(ints, 2, arrayDoubleFiltering);
    }


    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(int[][] ints, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decision(ints, 2, arrayIntegerFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public abstract ArrayList<ArrayDoubleFiltering> decision(double[][] ints, int logBase, ArrayDoubleFiltering... arrayDoubleFiltering);

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public abstract ArrayList<ArrayIntegerFiltering> decision(int[][] ints, int logBase, ArrayIntegerFiltering... arrayIntegerFiltering);

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    public final IntegerMatrix decisionAndGet(IntegerMatrix ints, int logBase, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decisionAndGet(ints.toArrays(), logBase, new ArrayList<>(Arrays.asList(arrayIntegerFiltering)));
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param doubles               当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    public final DoubleMatrix decisionAndGet(DoubleMatrix doubles, int logBase, ArrayDoubleFiltering... arrayIntegerFiltering) {
        return decisionAndGet(doubles.toArrays(), logBase, new ArrayList<>(Arrays.asList(arrayIntegerFiltering)));
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected final IntegerMatrix decisionAndGet(IntegerMatrix ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering) {
        return decisionAndGet(ints.toArrays(), logBase, arrayIntegerFiltering);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param doubles               当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected final DoubleMatrix decisionAndGet(DoubleMatrix doubles, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering) {
        return decisionAndGet(doubles.toArrays(), logBase, arrayIntegerFiltering);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected abstract IntegerMatrix decisionAndGet(int[][] ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering);

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param doubles               当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected abstract DoubleMatrix decisionAndGet(double[][] doubles, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering);
}
