package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.modelAlgorithm.MultipleLinearRegression;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备矩阵
        DoubleMatrix parse1 = DoubleMatrix.parse(
                new double[]{1, 2, 12},
                new double[]{5, 7, 48},
                new double[]{8, 9, 68}
        );
        // 获取到线性回归组件
        MultipleLinearRegression multipleLinearRegression1 = MultipleLinearRegression.getInstance("MultipleLinearRegression");
        // 设置回归学习率
        multipleLinearRegression1.setLearningRate(0.2f);
        // 开始计算回归模型
        // 参数解释： 第 2 索引列作为目标值     parse1 是计算的数据矩阵     为每列提供初始的权重      权重可修改1
        double[] doubles1 = multipleLinearRegression1.modelInference(2, parse1, new double[]{15, 15}, false);
        System.out.println(Arrays.toString(doubles1));
        // 打印结果
        System.out.println("(x1 * " + doubles1[0] + ") + (x2 * " + doubles1[1] + ") + " + doubles1[2] + " = y");

        // 准备矩阵
        IntegerMatrix parse2 = IntegerMatrix.parse(
                new int[]{1, 12, 2},
                new int[]{5, 48, 7},
                new int[]{8, 68, 9}
        );
        // 获取到线性回归组件
        MultipleLinearRegression multipleLinearRegression2 = MultipleLinearRegression.getInstance("MultipleLinearRegression");
        // 设置回归学习率
        multipleLinearRegression2.setLearningRate(0.2f);
        // 开始计算回归模型
        // 参数解释： 第 1 索引列作为目标值     parse2 是计算的数据矩阵     为每列提供初始的权重      权重可修改
        double[] doubles2 = multipleLinearRegression2.modelInference(1, parse2, new double[]{15, 15}, false);
        System.out.println(Arrays.toString(doubles2));
        // 打印结果
        System.out.println("(x1 * " + doubles2[0] + ") + (x3 * " + doubles2[1] + ") + " + doubles2[2] + " = y");
    }
}