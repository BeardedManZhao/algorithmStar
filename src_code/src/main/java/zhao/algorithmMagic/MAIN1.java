package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightedAverage;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 聚合计算组件 加权平均值
        WeightedAverage avg = WeightedAverage.getInstance("avg");
        // 构建一个向量
        DoubleVector doubleVector = DoubleVector.parse(20, 10, 40);
        // 计算平均值并打印
        System.out.println(avg.calculation(doubleVector));
        // 设置权重数组 这样的设置使得 10 这个数值在本次计算中的影响占比会比较高
        double[] doubles = {1, 2, 1};
        // 计算加权平均值并打印
        System.out.println(avg.calculation(doubles, doubleVector.toArray()));
    }
}