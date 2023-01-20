package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.util.ArrayList;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些相关联的数据，本次要求将与年龄相关联的数据全部删掉
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new int[]{1, 1, 1},
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0},
                new int[]{1, 1, 0},
                new int[]{0, 1, 0}
        );
        System.out.println(columnDoubleMatrix);
        // 构建一些事件过滤器
        // 有钱选项为1
        ArrayIntegerFiltering arrayIntegerFiltering1 = v -> v[2] == 1;
        // 身高选项为1
        ArrayIntegerFiltering arrayIntegerFiltering2 = v -> v[1] == 1;
        // 颜值选项为1
        ArrayIntegerFiltering arrayIntegerFiltering3 = v -> v[0] == 1;
        System.out.println(arrayIntegerFiltering1);
        System.out.println(arrayIntegerFiltering2);
        System.out.println(arrayIntegerFiltering3);
        // 获取到决策树
        DecisionTree d = DecisionTree.getInstance("d");
        // 设置精准模式
        d.setAccurate(true);
        // 设置中心字段索引
        d.setGroupIndex(0);
        // 开始计算最优方案
        ArrayList<ArrayIntegerFiltering> decision = d.decision(
                columnDoubleMatrix, arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3, arrayIntegerFiltering3
        );
        // 将最优方案传递给决策树执行，并接收返回的结果
        String s = d.executeGetString(columnDoubleMatrix.toArrays(), decision);
        System.out.println(s);
    }
}
