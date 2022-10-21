package zhao.algorithmMagic;

import zhao.algorithmMagic.Integrator.IncrementalLearning;
import zhao.algorithmMagic.Integrator.launcher.IncrementalLearningLauncher;
import zhao.algorithmMagic.Integrator.launcher.Launcher;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 配置工资和年龄的向量序列
        DoubleVector age = DoubleVector.parse(18, 19, 20, 30, 50);
        DoubleVector money = DoubleVector.parse(8000, 10000, 10000, 15000, 500);

        // 获取到集成器
        IncrementalLearning a = new IncrementalLearning("A", new IncrementalLearningLauncher() {
            @Override
            public double run(double Yn, double Xn, double $) {
                return $ * Xn;
            }

            @Override
            public Launcher<?> expand() {
                return this;
            }
        });
        // 设置集成器的θ回归区间
        a.setStartingValue(1).setTerminationValue(100);

        // 计算出来工资与年龄的关系
        System.out.println("工资 约为 年龄 * " + a.run(money, age));
    }
}
