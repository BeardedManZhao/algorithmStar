package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.modelAlgorithm.LinearRegression;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 创建一个矩阵对象，其中包含一些数据，现在需要找到最块的筛选路线，并使用此路线将数据进行一次获取
        ColumnDoubleMatrix columnDoubleMatrix = ColumnDoubleMatrix.parse(
                new String[]{"x", "y"},
                null,
                new double[]{1, 50},
                new double[]{2, 100},
                new double[]{3, 150},
                new double[]{4, 200}
        );
        // 获取到线性回归
        LinearRegression line = LinearRegression.getInstance("line");
        // 开始计算线性回归 计算x 与 y 之间的关系 其中 x 为自变量  y 为因变量
        // 设置自变量的列编号
        line.setFeatureIndex(0);
        // 设置因变量的列编号
        line.setTargetIndex(1);
        // 计算出回归系数与结果值
        double[] doubles = line.modelInference(columnDoubleMatrix);
        // 获取到线性回归计算之后的权重数组，并将权重数组插入到公式打印出来
        System.out.println("数据特征：");
        System.out.println("y = x * " + doubles[0] + " + " + doubles[1]);
    }
}
