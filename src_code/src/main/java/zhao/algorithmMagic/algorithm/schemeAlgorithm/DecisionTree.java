package zhao.algorithmMagic.algorithm.schemeAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 决策树计算组件，具有着强大的决策数计算功能，能够将一些过滤事件按照熵计算出一定的顺序排序好。
 * <p>
 * The decision tree calculation component has powerful decision number calculation function, and can sort some filtering events in a certain order according to the entropy calculation.
 *
 * @author zhao
 */
public class DecisionTree extends SchemeAlgorithm {

    private final static OperatorOperationException OUT_OF_RANGE = new OperatorOperationException("The grouping index is out of range!!!");
    private int groupIndex = 0;
    private boolean accurate = false;

    protected DecisionTree(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static DecisionTree getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof DecisionTree) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于DecisionTree类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the DecisionTree type. Please redefine a name for this algorithm.");
            }
        } else {
            DecisionTree DecisionTree = new DecisionTree(Name);
            OperationAlgorithmManager.getInstance().register(DecisionTree);
            return DecisionTree;
        }
    }

    /**
     * 设置在进行决策计算时的中心字段，在进行决策计算的时候将会以中心字段为基准开始分组，建议使用比较均匀的值字段作为中心字段。
     * <p>
     * Set the center field during decision calculation. The group will start based on the center field during decision calculation. It is recommended to use a relatively uniform value field as the center field.
     *
     * @param groupIndex 中心字段对应的索引值，从0开始
     *                   <p>
     *                   Index value corresponding to the center field, starting from 0
     */
    public final void setGroupIndex(int groupIndex) {
        if (groupIndex < 0) throw OUT_OF_RANGE;
        this.groupIndex = groupIndex;
    }

    /**
     * 决策查询模式，如果设置为true 代表使用精准查询，在这种查询下，数据也会随着查询进度而改变，模拟实际的递归树，但是会有一些额外的计算开销。
     * <p>
     * If the decision query mode is set to true, it means accurate query is used. In this case, the data will also change with the query progress, simulating the actual recursive tree, but there will be some additional calculation overhead.
     *
     * @param accurate 如果设置为true 代表开启精准决策查询，反之将使用普通查询。
     *                 <p>
     *                 If it is set to true, it means that accurate decision query will be enabled, otherwise, ordinary query will be used.
     */
    public final void setAccurate(boolean accurate) {
        this.accurate = accurate;
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
    @Override
    public ArrayList<ArrayDoubleFiltering> decision(double[][] ints, int logBase, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decision(ints, logBase, new ArrayList<>(Arrays.asList(arrayDoubleFiltering)), arrayDoubleFiltering.length);
    }

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
    @Override
    public ArrayList<ArrayIntegerFiltering> decision(int[][] ints, int logBase, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decision(ints, logBase, new ArrayList<>(Arrays.asList(arrayIntegerFiltering)), arrayIntegerFiltering.length);
    }

    /**
     * 执行决策方案，并记录结果，同时生成过滤数据详情
     *
     * @param ints      需要被进行决策项的数据样本
     * @param arrayList 决策方案
     * @return 执行结果，是数据过滤的操作过程
     */
    public String executeGetString(int[][] ints, ArrayList<ArrayIntegerFiltering> arrayList) {
        StringBuilder stringBuilder = new StringBuilder(10 + (arrayList.size() << 2));
        ArrayList<int[]> data = new ArrayList<>(Arrays.asList(ints));
        int count = 0;
        for (ArrayIntegerFiltering arrayIntegerFiltering : arrayList) {
            // 开始进行提取
            stringBuilder.append("\n* >>> Tier ").append(++count).append(" Decision\n");
            StringBuilder stringBuilder1 = new StringBuilder();
            ArrayList<int[]> deleteList = new ArrayList<>();
            for (int[] anInt : data) {
                if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                    // 标记为真
                    stringBuilder.append("True  => ").append(Arrays.toString(anInt)).append('\n');
                } else {
                    // 标记为假
                    stringBuilder1.append("False => ").append(Arrays.toString(anInt)).append('\n');
                    deleteList.add(anInt);
                }
            }
            // 清空假值
            data.removeAll(deleteList);
            // 真假合并
            stringBuilder.append(stringBuilder1);
        }

        return stringBuilder.toString();
    }

    /**
     * 执行决策方案，并记录结果，同时生成dot图源代码
     *
     * @param ints      需要被进行决策项的数据样本
     * @param arrayList 决策方案
     * @return 执行结果，是dot图的源代码。
     */
    public String executeGetString(double[][] ints, ArrayList<ArrayDoubleFiltering> arrayList) {
        StringBuilder stringBuilder = new StringBuilder(10 + (arrayList.size() << 2));
        ArrayList<double[]> data = new ArrayList<>(Arrays.asList(ints));
        int count = 0;
        for (ArrayDoubleFiltering arrayIntegerFiltering : arrayList) {
            // 开始进行提取
            stringBuilder.append("\n* >>> Tier ").append(++count).append(" Decision\n");
            StringBuilder stringBuilder1 = new StringBuilder();
            ArrayList<double[]> deleteList = new ArrayList<>();
            for (double[] anInt : data) {
                if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                    // 标记为真
                    stringBuilder.append("True  => ").append(Arrays.toString(anInt)).append('\n');
                } else {
                    // 标记为假
                    stringBuilder1.append("False => ").append(Arrays.toString(anInt)).append('\n');
                    deleteList.add(anInt);
                }
            }
            // 清空假值
            data.removeAll(deleteList);
            // 真假合并
            stringBuilder.append(stringBuilder1);
        }

        return stringBuilder.toString();
    }

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
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    private ArrayList<ArrayDoubleFiltering> decision(double[][] ints, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering, int max) {
        ArrayList<ArrayDoubleFiltering> res = new ArrayList<>(arrayIntegerFiltering.size() + 4);
        // 计算出样本中的信息熵 以 groupIndex列为分组索引
        double h = ASMath.entropy(ints, logBase, groupIndex);
        if (accurate) {
            ArrayList<double[]> arrayList = new ArrayList<>(Arrays.asList(ints));
            while (arrayList.size() != 0) {
                double max_value = Double.MIN_VALUE;
                ArrayDoubleFiltering arrayDoubleFiltering_max = null;
                // 开始迭代每一个方案数据对象
                for (ArrayDoubleFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropyAndDelete(arrayList, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayDoubleFiltering_max = doubleFiltering;
                    }
                }
                if (arrayDoubleFiltering_max == null) return res;
                // 将本轮筛选效果最明显的组件添加到res中
                res.add(arrayDoubleFiltering_max);
                arrayIntegerFiltering.remove(arrayDoubleFiltering_max);
            }
        } else {
            while (res.size() < max) {
                double max_value = Double.MIN_VALUE;
                ArrayDoubleFiltering arrayDoubleFiltering_max = null;
                // 开始迭代每一个方案数据对象
                for (ArrayDoubleFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropy(ints, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayDoubleFiltering_max = doubleFiltering;
                    }
                }
                if (arrayDoubleFiltering_max == null) return res;
                // 将本轮筛选效果最明显的组件添加到res中
                res.add(arrayDoubleFiltering_max);
                arrayIntegerFiltering.remove(arrayDoubleFiltering_max);
            }
        }
        return res;
    }

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
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过决策树算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the decision tree algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    private ArrayList<ArrayIntegerFiltering> decision(int[][] ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering, int max) {
        ArrayList<ArrayIntegerFiltering> res = new ArrayList<>(arrayIntegerFiltering.size() + 4);
        // 计算出样本中的信息熵 以 groupIndex列为分组索引
        double h = ASMath.entropy(ints, logBase, groupIndex);
        if (accurate) {
            ArrayList<int[]> arrayList = new ArrayList<>(Arrays.asList(ints));
            while (arrayList.size() != 0) {
                double max_value = Double.MIN_VALUE;
                ArrayIntegerFiltering arrayIntegerFiltering_max = null;
                // 开始迭代每一个方案数据对象
                for (ArrayIntegerFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropyAndDelete(arrayList, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayIntegerFiltering_max = doubleFiltering;
                    }
                }
                if (arrayIntegerFiltering_max == null) return res;
                // 将本轮筛选效果最明显的组件添加到res中
                res.add(arrayIntegerFiltering_max);
                arrayIntegerFiltering.remove(arrayIntegerFiltering_max);
            }
        } else {
            while (res.size() < max) {
                double max_value = Double.MIN_VALUE;
                ArrayIntegerFiltering arrayDoubleFiltering_max = null;
                // 开始迭代每一个方案数据对象
                for (ArrayIntegerFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropy(ints, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayDoubleFiltering_max = doubleFiltering;
                    }
                }
                if (arrayDoubleFiltering_max == null) return res;
                // 将本轮筛选效果最明显的组件添加到res中
                res.add(arrayDoubleFiltering_max);
                arrayIntegerFiltering.remove(arrayDoubleFiltering_max);
            }
        }
        return res;
    }
}
