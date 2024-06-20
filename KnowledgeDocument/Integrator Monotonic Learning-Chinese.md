# 集成器-单调学习

- Switch
  to [English document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrator%20Monotonic%20Learning.md)

## 介绍

创建于1.1版本

该集成器适用于模型的计算，通过大量的数据样本，与设定的初始模型与递增区间就可以自动进行单调递增运算，在1.1版本中，该集成器投入使用，需要首先实现该集成器对应的启动器接口，在启动器接口中主要做的事情就是设置初始的模型，模型中包含的模型变量会在单调递增区间中不断迭代赋值计算结果，并将该结果保存到一个集合中，结果集会被用于和其正确的结果进行匹配，取出最相似的结果数值，并通过哈希索引将该数值对应的模型变量值获取到。

## 使用示例

集成器的使用需要一个名为"IncrementalLearningLauncher"启动器，该启动器是一个接口，在其中主要做的就是设置初始模型的逻辑，通过该逻辑进行结果数值的相似度检查，最终获取到最合适的θ数值，构建出来最合适的模型。

```java
import io.github.beardedManZhao.algorithmStar.integrator.IncrementalLearning;
import io.github.beardedManZhao.algorithmStar.integrator.iauncher.IncrementalLearningLauncher;
import io.github.beardedManZhao.algorithmStar.integrator.iauncher.Launcher;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
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
                // 单调学习中会将 θ 加或乘 递增步长数值，来进行递增学习
                .setIncrementalParameter(true, 10);

        // 计算出来工资与年龄的关系
        System.out.println("工资 约为 年龄 * " + incrementalLearning.run(money, age));
    }
}
```

<hr>

- Switch
  to [English document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrator%20Monotonic%20Learning.md)
