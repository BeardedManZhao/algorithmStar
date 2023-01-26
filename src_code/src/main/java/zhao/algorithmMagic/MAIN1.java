package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个二维数组，其中每一个元素代表一个坐标点的位置与值
        int[][] ints = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new int[]{1, 1, 1}, new int[]{2, 2, 1}, new int[]{3, 2, 2}
        };
        // 根据稀疏坐标点描述创建矩阵对象
        IntegerMatrix sparse1 = IntegerMatrix.sparse(ints);
        // 打印矩阵
        System.out.println(sparse1);

        // 浮点矩阵也是可以这样使用的
        // 首先准备稀疏矩阵数据
        double[][] doubles = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new double[]{1, 1, 1}, new double[]{2, 2, 1}, new double[]{3, 2, 2}
        };
        // 创建矩阵
        DoubleMatrix sparse2 = DoubleMatrix.sparse(doubles);
        // 打印矩阵
        System.out.println(sparse2);
    }
}
