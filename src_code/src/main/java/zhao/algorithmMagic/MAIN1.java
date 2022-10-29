package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.IncrementalLearning;
import zhao.algorithmMagic.integrator.iauncher.IncrementalLearningLauncher;
import zhao.algorithmMagic.integrator.iauncher.Launcher;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // TODO 预测一个模型
        // 配置工资和年龄的向量序列，本次我们要找到 money 和 age之间的关系
        DoubleVector age = DoubleVector.parse(18, 19, 20, 30, 50);
        DoubleVector money = DoubleVector.parse(18000, 19000, 20000, 30000, 50000);

        // 获取到集成器
        IncrementalLearning incrementalLearning = new IncrementalLearning("A", new IncrementalLearningLauncher() {
            // 设置初始模型 这里的模型是 money(Yn) = θ($) * age(Xn) 其中的 θ 就是单调计算的主角
            @Override
            public double run(double Yn, double Xn, double $) {
                return $ * Xn;
            }

            @Override
            public Launcher<?> expand() {
                return this;
            }
        });

        incrementalLearning
                // 设置集成器的θ回归区间
                .setStartingValue(1).setTerminationValue(10000)
                // 设置集成器的单调递增参数 第一个参数是递增模式（使用等差递增(false)还是等比递增(true)）第二个参数是递增步长。
                // 这里是使用的递增求和，等差为10的方式进行数据集的训练与模型的计算
                .setIncrementalParameter(false, 10);

        // 计算出来工资与年龄的关系
        System.out.println("工资 约为 年龄 * " + incrementalLearning.run(money, age));
    }
}
