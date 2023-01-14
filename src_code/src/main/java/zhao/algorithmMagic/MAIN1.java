package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

import java.util.Arrays;

public final class MAIN1 {
    public static void main(String[] args) {
        // 准备一个矩阵 其中存储的是鸟的数据样本
        ColumnIntegerMatrix parse1 = ColumnIntegerMatrix.parse(
                new String[]{"1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d"}, // 样本来源地区编号
                new String[]{"羽毛", "羽毛的颜色", "种族"}, // 样本统计的三种维度
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        );
        // 开始进行特征选择 去除掉其中的 40% 的维度
        ColumnIntegerMatrix integerMatrix1 = parse1.featureSelection(0.4);
        System.out.println(Arrays.toString(integerMatrix1.getArrayByRowName("种族")));
        System.out.println(Arrays.toString(integerMatrix1.getArrayByColName("1d")));

        // 准备一个新矩阵
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                null,
                new String[]{"用户编号", "购买物品数量", "购买时间", "物品类别"},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
                new int[]{10, 10, 10, 20, 10, 20, 20, 23},
                new int[]{2023, 2022, 2021, 2022, 2021, 2022, 2022, 2023},
                new int[]{4, 5, 3, 2, 1, 7, 8, 6}
        );
        System.out.println(parse);
        // 开始将矩阵中的数据进行与购买时间相关系的维度删掉
        ColumnIntegerMatrix columnIntegerMatrix = parse.deleteRelatedDimensions(2, 0.8, 1);
        System.out.println(columnIntegerMatrix);
    }
}
