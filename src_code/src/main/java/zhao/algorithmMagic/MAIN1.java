package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.RandomForest;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;

import java.util.ArrayList;
import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些相关联的数据，本次要求将与年龄相关联的数据全部删掉
        ColumnDoubleMatrix columnDoubleMatrix = ColumnDoubleMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new double[]{1, 1, 1},
                new double[]{1, 0, 1},
                new double[]{0, 1, 0},
                new double[]{0, 0, 0},
                new double[]{0, 1, 0},
                new double[]{1, 0, 0},
                new double[]{1, 1, 0},
                new double[]{1, 1, 12},
                new double[]{0, 1, 0}
        );
        // 构建一些事件过滤器
        // 有钱选项为1
        ArrayDoubleFiltering arrayDoubleFiltering1 = v -> v[2] == 1;
        // 身高选项为1
        ArrayDoubleFiltering arrayDoubleFiltering2 = v -> v[1] == 1;
        // 颜值选项为1
        ArrayDoubleFiltering arrayDoubleFiltering3 = v -> v[0] == 1;
        System.out.println(arrayDoubleFiltering1);
        System.out.println(arrayDoubleFiltering2);
        System.out.println(arrayDoubleFiltering3);
        // 使用随机森林计算组件进行决策思维图绘制
        String s = RandomForest.executeGetString(
                columnDoubleMatrix.toArrays(),
                new ArrayList<>(Arrays.asList(arrayDoubleFiltering1, arrayDoubleFiltering2, arrayDoubleFiltering3)),
                false, true, 22, 3, 3);
        System.out.println(s);
    }
}