package zhao.algorithmMagic.algorithm.schemeAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.ASStr;
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

    protected final static OperatorOperationException OUT_OF_RANGE = new OperatorOperationException("The grouping index is out of range!!!");
    protected int groupIndex = 0;
    protected boolean accurate = false;

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
     * 执行决策方案，并将结果以Markdown流程图代码的方式返回出去，其可以被用来生成各种决策树执行流程图。
     * <p>
     * Execute the decision scheme and return the results in the form of Markdown flowchart code, which can be used to generate various decision tree execution flowchart.
     *
     * @param ints      需要被进行决策项的数据样本
     *                  <p>
     *                  Data sample of decision items to be made
     * @param arrayList 决策方案列表，其中每一个元素是一个事件过滤器，其代表的就是节点之间的过滤通道，在流程图中将会以过滤器的toString返回值作为通道名称！
     *                  <p>
     *                  In the decision scheme list, each element is an event filter, which represents the filtering channel between nodes. In the flow chart, the return value of the filter toString will be used as the channel name!
     * @return 执行结果，是数据过滤的操作过程，其是一个Markdown流程图代码的字符串，您可以将其以 Markdown 语法解析出流程图！
     * <p>
     * The execution result is the operation process of data filtering. It is a string of Markdown flowchart code. You can parse it out of the flowchart with Markdown syntax!
     */
    public static String executeGetString(int[][] ints, ArrayList<ArrayIntegerFiltering> arrayList) {
        return executeGetString(ints, arrayList, false, false);
    }


    /**
     * 执行决策方案，并将结果以Markdown流程图代码的方式返回出去，其可以被用来生成各种决策树执行流程图。
     * <p>
     * Execute the decision scheme and return the results in the form of Markdown flowchart code, which can be used to generate various decision tree execution flowchart.
     *
     * @param ints       需要被进行决策项的数据样本
     *                   <p>
     *                   Data sample of decision items to be made
     * @param arrayList  决策方案列表，其中每一个元素是一个事件过滤器，其代表的就是节点之间的过滤通道，在流程图中将会以过滤器的toString返回值作为通道名称！
     *                   <p>
     *                   In the decision scheme list, each element is an event filter, which represents the filtering channel between nodes. In the flow chart, the return value of the filter toString will be used as the channel name!
     * @param isLR       流程图是否以左右的布局排版，如果设置为true代表从左到右排版，反之则代表从上到下排版。
     *                   <p>
     *                   Whether the flow chart is typeset in left and right layout. If set to true, it means typesetting from left to right, otherwise it means typesetting from top to bottom.
     * @param isDetailed 流程图中的节点显示设置，如果设置为true，在节点位置将显示所有的数据，如果设置为false 节点显示概述信息。
     *                   <p>
     *                   The node display settings in the flowchart. If set to true, all data will be displayed at the node location. If set to false, the node will display overview information.
     * @return 执行结果，是数据过滤的操作过程，其是一个Markdown流程图代码的字符串，您可以将其以 Markdown 语法解析出流程图！
     * <p>
     * The execution result is the operation process of data filtering. It is a string of Markdown flowchart code. You can parse it out of the flowchart with Markdown syntax!
     */
    public static String executeGetString(int[][] ints, ArrayList<ArrayIntegerFiltering> arrayList, boolean isLR, boolean isDetailed) {
        StringBuilder stringBuilder = new StringBuilder(Math.max(10 + (arrayList.size() << 4), 100));
        ArrayList<int[]> data = new ArrayList<>(Arrays.asList(ints));
        int count = 0;
        stringBuilder.append(isLR ? "graph LR\n" : "graph TB\n");
        String back = "AllData";
        if (isDetailed) {
            for (ArrayIntegerFiltering arrayIntegerFiltering : arrayList) {
                // 获取当前决策的字符串编号
                String filter = ASStr.replaceCharFirst(arrayIntegerFiltering.toString(), '@', '-');
                // 开始进行提取 准备真假列表
                StringBuilder stringBuilder1 = new StringBuilder(back);
                StringBuilder stringBuilder2 = new StringBuilder(back);
                // 更新当前节点的名称
                back = "TrueData" + (++count);
                stringBuilder2.append(" -. ").append(filter).append("=false .-> FalseData").append(count).append('[');
                stringBuilder1.append(" -- ").append(filter).append("=true --> ").append(back).append('[');
                ArrayList<int[]> deleteList = new ArrayList<>();
                for (int[] anInt : data) {
                    if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                        // 标记为真 这里生成真的所有数据
                        stringBuilder1.append(ASStr.arrayToMarkdownStr(anInt)).append("<br>");
                    } else {
                        // 标记为假
                        stringBuilder2.append(ASStr.arrayToMarkdownStr(anInt)).append("<br>");
                        deleteList.add(anInt);
                    }
                }
                // 清空假值
                data.removeAll(deleteList);
                // 真假合并
                stringBuilder.append(stringBuilder1).append(']').append('\n').append(stringBuilder2).append(']').append('\n');
            }
        } else {
            for (ArrayIntegerFiltering arrayIntegerFiltering : arrayList) {
                // 获取当前决策的字符串编号
                String filter = ASStr.replaceCharFirst(arrayIntegerFiltering.toString(), '@', '-');
                // 开始进行提取 准备真假列表
                StringBuilder stringBuilder1 = new StringBuilder(back), stringBuilder2 = new StringBuilder(back);
                // 更新当前节点的名称
                back = "TrueData" + (++count);
                stringBuilder2.append(" -. ").append(filter).append("=false .-> FalseData").append(count).append('[');
                stringBuilder1.append(" -- ").append(filter).append("=true --> ").append(back).append('[');
                int okCount = 0;
                ArrayList<int[]> deleteList = new ArrayList<>();
                for (int[] anInt : data) {
                    if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                        // 标记为真 这里生成真的所有数据
                        ++okCount;
                    } else {
                        // 标记为假
                        deleteList.add(anInt);
                    }
                }
                int size = deleteList.size();
                boolean b = size != 0;
                {
                    final double RemainP = (okCount / (double) data.size()) * 100;
                    stringBuilder1
                            .append("Int True Node No.").append(count).append("<br>")
                            .append("Remaining quantity = ").append(okCount).append("<br>")
                            .append("Remaining percentage = ").append(RemainP).append("%<br>");
                    if (b) {
                        stringBuilder2
                                .append("Int False Node No.").append(count).append("<br>")
                                .append("Removal quantity = ").append(size).append("<br>")
                                .append("Removal percentage = ").append(100 - RemainP).append("%<br>");
                    }
                }
                if (b) {
                    // 清空假值
                    data.removeAll(deleteList);
                }
                // 真假合并
                stringBuilder.append(stringBuilder1).append(']').append('\n');
                if (b) {
                    stringBuilder.append(stringBuilder2).append(']').append('\n');
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 执行决策方案，并将结果以Markdown流程图代码的方式返回出去，其可以被用来生成各种决策树执行流程图。
     * <p>
     * Execute the decision scheme and return the results in the form of Markdown flowchart code, which can be used to generate various decision tree execution flowchart.
     *
     * @param doubles   需要被进行决策项的数据样本
     *                  <p>
     *                  Data sample of decision items to be made
     * @param arrayList 决策方案列表，其中每一个元素是一个事件过滤器，其代表的就是节点之间的过滤通道，在流程图中将会以过滤器的toString返回值作为通道名称！
     *                  <p>
     *                  In the decision scheme list, each element is an event filter, which represents the filtering channel between nodes. In the flow chart, the return value of the filter toString will be used as the channel name!
     * @return 执行结果，是数据过滤的操作过程，其是一个Markdown流程图代码的字符串，您可以将其以 Markdown 语法解析出流程图！
     * <p>
     * The execution result is the operation process of data filtering. It is a string of Markdown flowchart code. You can parse it out of the flowchart with Markdown syntax!
     */
    public static String executeGetString(double[][] doubles, ArrayList<ArrayDoubleFiltering> arrayList) {
        return executeGetString(doubles, arrayList, false, false);
    }


    /**
     * 执行决策方案，并将结果以Markdown流程图代码的方式返回出去，其可以被用来生成各种决策树执行流程图。
     * <p>
     * Execute the decision scheme and return the results in the form of Markdown flowchart code, which can be used to generate various decision tree execution flowchart.
     *
     * @param doubles    需要被进行决策项的数据样本
     *                   <p>
     *                   Data sample of decision items to be made
     * @param arrayList  决策方案列表，其中每一个元素是一个事件过滤器，其代表的就是节点之间的过滤通道，在流程图中将会以过滤器的toString返回值作为通道名称！
     *                   <p>
     *                   In the decision scheme list, each element is an event filter, which represents the filtering channel between nodes. In the flow chart, the return value of the filter toString will be used as the channel name!
     * @param isLR       流程图是否以左右的布局排版，如果设置为true代表从左到右排版，反之则代表从上到下排版。
     *                   <p>
     *                   Whether the flow chart is typeset in left and right layout. If set to true, it means typesetting from left to right, otherwise it means typesetting from top to bottom.
     * @param isDetailed 流程图中的节点显示设置，如果设置为true，在节点位置将显示所有的数据，如果设置为false 节点显示概述信息。
     *                   <p>
     *                   The node display settings in the flowchart. If set to true, all data will be displayed at the node location. If set to false, the node will display overview information.
     * @return 执行结果，是数据过滤的操作过程，其是一个Markdown流程图代码的字符串，您可以将其以 Markdown 语法解析出流程图！
     * <p>
     * The execution result is the operation process of data filtering. It is a string of Markdown flowchart code. You can parse it out of the flowchart with Markdown syntax!
     */
    public static String executeGetString(double[][] doubles, ArrayList<ArrayDoubleFiltering> arrayList, boolean isLR, boolean isDetailed) {
        StringBuilder stringBuilder = new StringBuilder(Math.max(10 + (arrayList.size() << 4), 100));
        ArrayList<double[]> data = new ArrayList<>(Arrays.asList(doubles));
        int count = 0;
        stringBuilder.append(isLR ? "graph LR\n" : "graph TB\n");
        String back = "AllData";
        if (isDetailed) {
            for (ArrayDoubleFiltering arrayDoubleFiltering : arrayList) {
                // 获取当前决策的字符串编号
                final String filter = ASStr.replaceCharFirst(arrayDoubleFiltering.toString(), '@', '-');
                // 开始进行提取 准备真假列表
                final StringBuilder stringBuilder1 = new StringBuilder(back);
                final StringBuilder stringBuilder2 = new StringBuilder(back);
                // 更新当前节点的名称
                back = "TrueData" + (++count);
                stringBuilder2.append(" -. ").append(filter).append("=false .-> FalseData").append(count).append('[');
                stringBuilder1.append(" -- ").append(filter).append("=true --> ").append(back).append('[');
                ArrayList<double[]> deleteList = new ArrayList<>();
                for (double[] doubles1 : data) {
                    if (arrayDoubleFiltering.isComplianceEvents(doubles1)) {
                        // 标记为真 这里生成真的所有数据
                        stringBuilder1.append(ASStr.arrayToMarkdownStr(doubles1)).append("<br>");
                    } else {
                        // 标记为假
                        stringBuilder2.append(ASStr.arrayToMarkdownStr(doubles1)).append("<br>");
                        deleteList.add(doubles1);
                    }
                }
                if (deleteList.size() != 0) {
                    // 清空假值
                    data.removeAll(deleteList);
                    // 真假合并
                    stringBuilder.append(stringBuilder1).append(']').append('\n').append(stringBuilder2).append(']').append('\n');
                } else {
                    stringBuilder.append(stringBuilder1).append(']').append('\n');
                }
            }
        } else {
            for (ArrayDoubleFiltering arrayIntegerFiltering : arrayList) {
                // 获取当前决策的字符串编号
                String filter = ASStr.replaceCharFirst(arrayIntegerFiltering.toString(), '@', '-');
                // 开始进行提取 准备真假列表
                final StringBuilder stringBuilder1 = new StringBuilder(back), stringBuilder2 = new StringBuilder(back);
                // 更新当前节点的名称
                back = "TrueData" + (++count);
                stringBuilder2.append(" -. ").append(filter).append("=false .-> FalseData").append(count).append('[');
                stringBuilder1.append(" -- ").append(filter).append("=true --> ").append(back).append('[');
                int okCount = 0;
                ArrayList<double[]> deleteList = new ArrayList<>();
                for (double[] anInt : data) {
                    if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                        // 标记为真 这里生成真的所有数据
                        ++okCount;
                    } else {
                        // 标记为假
                        deleteList.add(anInt);
                    }
                }
                int size = deleteList.size();
                boolean b = size != 0;
                {
                    final double RemainP = (okCount / (double) data.size()) * 100;
                    stringBuilder1
                            .append("Double True Node No.").append(count).append("<br>")
                            .append("Remaining quantity = ").append(okCount).append("<br>")
                            .append("Remaining percentage = ").append(RemainP).append("%<br>");
                    if (b) {
                        stringBuilder2
                                .append("Double False Node No.").append(count).append("<br>")
                                .append("Removal quantity = ").append(size).append("<br>")
                                .append("Removal percentage = ").append(100 - RemainP).append("%<br>");
                    }
                }
                if (b) {
                    // 清空假值
                    data.removeAll(deleteList);
                }
                // 真假合并
                stringBuilder.append(stringBuilder1).append(']').append('\n');
                if (b) {
                    stringBuilder.append(stringBuilder2).append(']').append('\n');
                }
            }
        }
        return stringBuilder.toString();
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
    protected ArrayList<ArrayDoubleFiltering> decision(double[][] ints, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering, int max) {
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
    protected ArrayList<ArrayIntegerFiltering> decision(int[][] ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering, int max) {
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
    public IntegerMatrix decisionAndGet(int[][] ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering) {
        // 计算出样本中的信息熵 以 groupIndex列为分组索引
        double h = ASMath.entropy(ints, logBase, groupIndex);
        ArrayList<int[]> arrayList = new ArrayList<>(Arrays.asList(ints));
        while (arrayList.size() != 0) {
            ArrayIntegerFiltering arrayIntegerFiltering_max = null;
            {
                double max_value = Double.MIN_VALUE;
                // 开始迭代每一个方案数据对象
                for (ArrayIntegerFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropyAndDelete(arrayList, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayIntegerFiltering_max = doubleFiltering;
                    }
                }
            }
            if (arrayIntegerFiltering_max != null) {
                arrayIntegerFiltering.remove(arrayIntegerFiltering_max);
                // 使用本层最有效的组件进行数据过滤
                ArrayList<int[]> deleteList = new ArrayList<>();
                for (int[] ints1 : arrayList) {
                    if (!arrayIntegerFiltering_max.isComplianceEvents(ints1)) {
                        deleteList.add(ints1);
                    }
                }
                arrayList.removeAll(deleteList);
            } else break;
        }
        return IntegerMatrix.parse(arrayList.size() != 0 ? arrayList.toArray(new int[0][]) : new int[1][1]);
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
    public DoubleMatrix decisionAndGet(double[][] doubles, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering) {
        // 计算出样本中的信息熵 以 groupIndex列为分组索引
        double h = ASMath.entropy(doubles, logBase, groupIndex);
        ArrayList<double[]> arrayList = new ArrayList<>(Arrays.asList(doubles));
        while (arrayList.size() != 0) {
            ArrayDoubleFiltering arrayIntegerFiltering_max = null;
            {
                double max_value = Double.MIN_VALUE;
                // 开始迭代每一个方案数据对象
                for (ArrayDoubleFiltering doubleFiltering : arrayIntegerFiltering) {
                    // 计算出当前事件的最大信息增益
                    double temp = h - ASMath.entropyAndDelete(arrayList, logBase, doubleFiltering);
                    if (temp > max_value) {
                        max_value = temp;
                        arrayIntegerFiltering_max = doubleFiltering;
                    }
                }
            }
            if (arrayIntegerFiltering_max != null) {
                arrayIntegerFiltering.remove(arrayIntegerFiltering_max);
                // 使用本层最有效的组件进行数据过滤
                ArrayList<double[]> deleteList = new ArrayList<>();
                for (double[] doubles1 : arrayList) {
                    if (!arrayIntegerFiltering_max.isComplianceEvents(doubles1)) {
                        deleteList.add(doubles1);
                    }
                }
                arrayList.removeAll(deleteList);
            } else break;
        }
        return DoubleMatrix.parse(arrayList.size() != 0 ? arrayList.toArray(new double[0][]) : new double[1][1]);
    }
}
