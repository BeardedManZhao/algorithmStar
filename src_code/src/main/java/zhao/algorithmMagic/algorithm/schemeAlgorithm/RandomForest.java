package zhao.algorithmMagic.algorithm.schemeAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.ASStr;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 随机森林计算组件，此组件依赖决策树计算组件，能够实现从诸多决策树中找到最优解。
 * <p>
 * The random forest computing component, which relies on the decision tree computing component, can find the optimal solution from many decision trees.
 *
 * @author zhao
 */
public class RandomForest extends DecisionTree {

    /**
     * 节点分割器正则，其中的流程图生成代码需要使用该正则。
     */
    protected final static Pattern PATTERN = Pattern.compile("[.-]->\\s+");

    protected long seed = 1024;

    protected RandomForest(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static RandomForest getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof RandomForest) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于RandomForest类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the RandomForest type. Please redefine a name for this algorithm.");
            }
        } else {
            RandomForest RandomForest = new RandomForest(Name);
            OperationAlgorithmManager.getInstance().register(RandomForest);
            return RandomForest;
        }
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
    public static String executeGetString(int[][] ints, ArrayList<ArrayIntegerFiltering> arrayList, boolean isLR, boolean isDetailed, int seed, int layer, int rowOfLayer) {
        // 将矩阵使用随机算法分开
        IntegerMatrixSpace doubleMatrixBlock = ASMath.shuffleAndSplit(ints, seed, layer, rowOfLayer);
        // 对每一个矩阵使用决策树计算，最终将所有的树合并到一个根节点
        StringBuilder stringBuilder = new StringBuilder(isLR ? "graph LR\n" : "graph TB\n");
        int count = -1;
        for (IntegerMatrix integerMatrix : doubleMatrixBlock) {
            String[] splitByChar = ASStr.splitByChar(DecisionTree.executeGetString(integerMatrix.toArrays(), arrayList, isLR, isDetailed), '\n');
            stringBuilder.append("\nroot == ")
                    .append("double Tree").append(" ==> ")
                    .append(++count).append("AllData").append('\n');
            exGetString(stringBuilder, count, splitByChar);
        }
        return stringBuilder.toString();
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
     * @param seed       随机森林中每一棵决策树所分配任务的随机种子。
     *                   <p>
     *                   The random seeds of the tasks assigned to each decision tree in the random forest.
     * @param layer      您希望在计算的时候建立出来几个决策树。
     *                   <p>
     *                   You want to build several decision trees during calculation.
     * @param rowOfLayer 您希望每一个决策树处理多少条数据。
     *                   <p>
     *                   How many pieces of data do you want each decision tree to process.
     * @return 执行结果，是数据过滤的操作过程，其是一个Markdown流程图代码的字符串，您可以将其以 Markdown 语法解析出流程图！
     * <p>
     * The execution result is the operation process of data filtering. It is a string of Markdown flowchart code. You can parse it out of the flowchart with Markdown syntax!
     */
    public static String executeGetString(double[][] doubles, ArrayList<ArrayDoubleFiltering> arrayList, boolean isLR, boolean isDetailed, int seed, int layer, int rowOfLayer) {
        // 将矩阵使用随机算法分开
        DoubleMatrixSpace doubleMatrixBlock = ASMath.shuffleAndSplit(doubles, seed, layer, rowOfLayer);
        // 对每一个矩阵使用决策树计算，最终将所有的树合并到一个根节点
        StringBuilder stringBuilder = new StringBuilder(isLR ? "graph LR\n" : "graph TB\n");
        int count = -1;
        for (DoubleMatrix doubleMatrix : doubleMatrixBlock) {
            String[] splitByChar = ASStr.splitByChar(DecisionTree.executeGetString(doubleMatrix.toArrays(), arrayList, isLR, isDetailed), '\n');
            stringBuilder.append("\nroot == ")
                    .append("double Tree").append(" ==> ")
                    .append(++count).append("AllData").append('\n');
            exGetString(stringBuilder, count, splitByChar);
        }
        return stringBuilder.toString();
    }

    // 将树结合起来
    protected static void exGetString(StringBuilder stringBuilder, int count, String[] splitByChar) {
        for (int i = 1, splitByCharLength = splitByChar.length; i < splitByCharLength; i++) {
            String s = splitByChar[i];
            Matcher matcher = PATTERN.matcher(s);
            // 在这里修改每一个节点
            if (matcher.find()) {
                stringBuilder
                        .append(count)
                        .append(s, 0, matcher.start())
                        .append(matcher.group())
                        .append(count)
                        .append(s.substring(matcher.end()))
                        .append('\n');
            }
        }
    }

    /**
     * 设置拆分矩阵块时需要调用的随机种子
     *
     * @param seed 随机种子数值。
     */
    public void setSeed(long seed) {
        this.seed = seed;
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
        return decision(ints, logBase, 2, ints.length >> 1, arrayDoubleFiltering);
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
        return decision(ints, logBase, 2, ints.length >> 1, arrayIntegerFiltering);
    }

    /**
     * 通过随机森林计算出多中个解决方案，并获取到最优解答。
     * <p>
     * Several solutions are calculated through random forest and the optimal solution is obtained.
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(double[][] ints, int logBase, int layer, int rowOfLayer, ArrayDoubleFiltering... arrayDoubleFiltering) {
        // 为每一个决策打编号
        HashMap<ArrayDoubleFiltering, String> hashMap = new HashMap<>(arrayDoubleFiltering.length + 10);
        ArrayList<ArrayDoubleFiltering> arrayDoubleFilteringSet = new ArrayList<>(hashMap.keySet());
        {
            int count = -1;
            for (ArrayDoubleFiltering doubleFiltering : arrayDoubleFiltering) {
                hashMap.put(doubleFiltering, String.valueOf(++count));
            }
        }
        // 创建计数集合 其中key是决策编号按照顺序拼接的值，value是计数值
        HashMap<String, Integer> hashMap1 = new HashMap<>();
        // 创建结果决策方案集合 其中key是是决策编号拼接的值，value是决策结果
        HashMap<String, ArrayList<ArrayDoubleFiltering>> hashMap2 = new HashMap<>();
        // 将矩阵使用随机算法拆分成矩阵块
        DoubleMatrixSpace doubleMatrixBlock = ASMath.shuffleAndSplit(ints, seed, layer, rowOfLayer);
        for (DoubleMatrix doubleMatrix : doubleMatrixBlock.toArrays()) {
            // 将每一个矩阵块的决策树结果的编号拼接值添加到计数集合中
            String key;
            ArrayList<ArrayDoubleFiltering> arrayList = new ArrayList<>();
            {
                StringBuilder stringBuilder = new StringBuilder();
                for (ArrayDoubleFiltering doubleFiltering : super.decision(doubleMatrix.toArrays(), logBase, arrayDoubleFilteringSet, groupIndex)) {
                    stringBuilder.append(hashMap.get(doubleFiltering));
                }
                key = stringBuilder.toString();
            }
            // 开始计数
            hashMap1.put(key, hashMap1.getOrDefault(key, 0) + 1);
            // 开始添加待选结果
            hashMap2.put(key, arrayList);
        }
        // 获取到次数最多的结果编号
        String Num = null;
        int maxCount = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : hashMap1.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value > maxCount) {
                Num = key;
                maxCount = value;
            }
        }
        // 返回最终结果对应的方案
        return hashMap2.get(Num);
    }

    /**
     * 通过随机森林计算出多中个解决方案，并获取到最优解答。
     * <p>
     * Several solutions are calculated through random forest and the optimal solution is obtained.
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(int[][] ints, int logBase, int layer, int rowOfLayer, ArrayIntegerFiltering... arrayDoubleFiltering) {
        // 为每一个决策打编号
        HashMap<ArrayIntegerFiltering, String> hashMap = new HashMap<>(arrayDoubleFiltering.length + 10);
        ArrayList<ArrayIntegerFiltering> arrayDoubleFilteringSet = new ArrayList<>(hashMap.keySet());
        {
            int count = -1;
            for (ArrayIntegerFiltering doubleFiltering : arrayDoubleFiltering) {
                hashMap.put(doubleFiltering, String.valueOf(++count));
            }
        }
        // 创建计数集合 其中key是决策编号按照顺序拼接的值，value是计数值
        HashMap<String, Integer> hashMap1 = new HashMap<>();
        // 创建结果决策方案集合 其中key是是决策编号拼接的值，value是决策结果
        HashMap<String, ArrayList<ArrayIntegerFiltering>> hashMap2 = new HashMap<>();
        // 将矩阵使用随机算法拆分成矩阵块
        IntegerMatrixSpace doubleMatrixBlock = ASMath.shuffleAndSplit(ints, seed, layer, rowOfLayer);
        for (IntegerMatrix doubleMatrix : doubleMatrixBlock.toArrays()) {
            // 将每一个矩阵块的决策树结果的编号拼接值添加到计数集合中
            String key;
            ArrayList<ArrayIntegerFiltering> arrayList = new ArrayList<>();
            {
                StringBuilder stringBuilder = new StringBuilder();
                for (ArrayIntegerFiltering doubleFiltering : super.decision(doubleMatrix.toArrays(), logBase, arrayDoubleFilteringSet, groupIndex)) {
                    stringBuilder.append(hashMap.get(doubleFiltering));
                }
                key = stringBuilder.toString();
            }
            // 开始计数
            hashMap1.put(key, hashMap1.getOrDefault(key, 0) + 1);
            // 开始添加待选结果
            hashMap2.put(key, arrayList);
        }
        // 获取到次数最多的结果编号
        String Num = null;
        int maxCount = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : hashMap1.entrySet()) {
            Integer value = entry.getValue();
            if (value > maxCount) {
                Num = entry.getKey();
                maxCount = value;
            }
        }
        // 返回最终结果对应的方案
        return hashMap2.get(Num);
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
    @Override
    public IntegerMatrix decisionAndGet(int[][] ints, int logBase, List<ArrayIntegerFiltering> arrayIntegerFiltering) {
        return super.decisionAndGet(ints, logBase, arrayIntegerFiltering);
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
    @Override
    public DoubleMatrix decisionAndGet(double[][] doubles, int logBase, List<ArrayDoubleFiltering> arrayIntegerFiltering) {
        return super.decisionAndGet(doubles, logBase, arrayIntegerFiltering);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param integerMatrix        当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    public IntegerMatrix decisionAndGet(IntegerMatrix integerMatrix, int logBase, int layer, int rowOfLayer, ArrayIntegerFiltering... arrayDoubleFiltering) {
        return decisionAndGet(integerMatrix.toArrays(), logBase, layer, rowOfLayer, arrayDoubleFiltering);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param doubleMatrix         当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    public DoubleMatrix decisionAndGet(DoubleMatrix doubleMatrix, int logBase, int layer, int rowOfLayer, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decisionAndGet(doubleMatrix.toArrays(), logBase, layer, rowOfLayer, arrayDoubleFiltering);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected final IntegerMatrix decisionAndGet(int[][] ints, int logBase, int layer, int rowOfLayer, ArrayIntegerFiltering... arrayDoubleFiltering) {
        List<ArrayIntegerFiltering> arrayIntegerFiltering1 = Arrays.asList(arrayDoubleFiltering);
        // 首先开始构造一个结果存储空间，其中存储每一颗树的结果值
        IntegerMatrix[] integerMatrices = new IntegerMatrix[layer];
        // 将矩阵使用随机算法分开
        IntegerMatrixSpace integerMatrixBlock = ASMath.shuffleAndSplit(ints, seed, layer, rowOfLayer);
        // 开始进行每棵树的结果计算
        {
            int count = -1;
            for (IntegerMatrix integerMatrix : integerMatrixBlock.toArrays()) {
                integerMatrices[++count] = super.decisionAndGet(integerMatrix, logBase, new ArrayList<>(arrayIntegerFiltering1));
            }
        }
        // 计算每一个结果的无向差之和，并获取到无向差的个数统计 其中key是无向差值，value是无向差出现次数
        HashMap<Double, Integer> hashMap = new HashMap<>();
        // 每一个无向差之和结果对应的随机森林结果
        HashMap<Double, IntegerMatrix> hashMap1 = new HashMap<>();
        for (IntegerMatrix integerMatrix : integerMatrices) {
            double sum = 0;
            for (int[] toArray : integerMatrix.toArrays()) {
                sum += ASMath.undirectedDifference(toArray);
            }
            // 统计无向差结果
            hashMap.put(sum, hashMap.getOrDefault(sum, 0) + 1);
            hashMap1.put(sum, integerMatrix);
        }
        // 找到出现次数最多出现的无向差
        double key_Max = Double.MIN_VALUE;
        int value_Max = Integer.MIN_VALUE;
        for (Map.Entry<Double, Integer> entry : hashMap.entrySet()) {
            double key = entry.getKey();
            int value = entry.getValue();
            if (value_Max < value) {
                key_Max = key;
                value_Max = value;
            }
        }
        // 将出现次数最多的结果返回
        return hashMap1.get(key_Max);
    }

    /**
     * 使用决策树的精准决策模式，获取到本次数据过滤之后的结果矩阵数值。
     *
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param layer                您希望在计算的时候建立出来几个决策树。
     *                             <p>
     *                             You want to build several decision trees during calculation.
     * @param rowOfLayer           您希望每一个决策树处理多少条数据。
     *                             <p>
     *                             How many pieces of data do you want each decision tree to process.
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 由决策树使用精准模式进行决策选择之后，符合条件的所有数据行组成的矩阵对象。
     * <p>
     * A matrix object composed of all data rows that meet the criteria after the decision tree uses the precise mode for decision selection.
     */
    protected final DoubleMatrix decisionAndGet(double[][] ints, int logBase, int layer, int rowOfLayer, ArrayDoubleFiltering... arrayDoubleFiltering) {
        List<ArrayDoubleFiltering> arrayIntegerFiltering1 = Arrays.asList(arrayDoubleFiltering);
        // 首先开始构造一个结果存储空间，其中存储每一颗树的结果值
        DoubleMatrix[] doubleMatrices = new DoubleMatrix[layer];
        // 将矩阵使用随机算法分开
        DoubleMatrixSpace doubleMatrixBlock = ASMath.shuffleAndSplit(ints, seed, layer, rowOfLayer);
        // 开始进行每棵树的结果计算
        {
            int count = -1;
            for (DoubleMatrix integerMatrix : doubleMatrixBlock.toArrays()) {
                doubleMatrices[++count] = super.decisionAndGet(integerMatrix, logBase, new ArrayList<>(arrayIntegerFiltering1));
            }
        }
        // 计算每一个结果的无向差之和，并获取到无向差的个数统计 其中key是无向差值，value是无向差出现次数
        HashMap<Double, Integer> hashMap = new HashMap<>();
        // 每一个无向差之和结果对应的随机森林结果
        HashMap<Double, DoubleMatrix> hashMap1 = new HashMap<>();
        for (DoubleMatrix doubleMatrix : doubleMatrices) {
            double sum = 0;
            for (double[] toArray : doubleMatrix.toArrays()) {
                sum += ASMath.undirectedDifference(toArray);
            }
            // 统计无向差结果
            hashMap.put(sum, hashMap.getOrDefault(sum, 0) + 1);
            hashMap1.put(sum, doubleMatrix);
        }
        // 找到出现次数最多出现的无向差
        int value_Max = Integer.MIN_VALUE;
        double key_Max = Double.MIN_VALUE;
        for (Map.Entry<Double, Integer> entry : hashMap.entrySet()) {
            int value = entry.getValue();
            if (value_Max < value) {
                key_Max = entry.getKey();
                value_Max = value;
            }
        }
        // 将出现次数最多的结果返回
        return hashMap1.get(key_Max);
    }
}
