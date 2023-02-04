package zhao.algorithmMagic;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        int[][] ints = {
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 1, 40, 1, 1, 60, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        };
        // 准备一个矩阵 其中存储的是鸟的数据样本
        IntegerMatrix parse1 = ColumnIntegerMatrix.parse(
                new String[]{"1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d"}, // 样本来源地区编号
                new String[]{"羽毛", "字段占位", "羽毛的颜色", "种族"}, // 样本统计的三种维度
                ints
        );
        // 加载动态库 TODO 动态库加载之后，在遇到能够使用DLL动态库进行计算的组件时将会使用DLL计算
        ASDynamicLibrary.addDllDir(new File("D:\\MyGitHub\\algorithmStar\\AsLib"));
        // 开始进行特征清洗 去除掉与其中第4行 正相关系数区间达到 [0.8, 1] 的维度
        IntegerMatrix integerMatrix = parse1.deleteRelatedDimensions(3, 0.8, 1);
        System.out.println(integerMatrix);
    }
}