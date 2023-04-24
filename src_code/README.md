# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.18 - 1.19
* The neuron object is reconstructed, and the weight field is added to the perceptron object and provided to the
  perceptron for management.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ActivationFunction;
import zhao.algorithmMagic.core.model.Perceptron;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {
        // 构建初始权重向量
        DoubleVector W = DoubleVector.parse(20, 18, 18, 18);
        // 获取到神经元感知机对象 这里分别设置激活函数与神经元对应的权重向量
        Perceptron perceptron = Perceptron.parse(ActivationFunction.SIGMOID, W);
        // 生成一个 X 向量
        DoubleVector parseX = DoubleVector.parse(1, 2, 3, 4);
        // 通过感知机对象进行计算
        Cell<Double> function = perceptron.function(parseX);
        System.out.println(function);
        // 通过感知机对象进行反向求导
        System.out.println(perceptron.backFunction(function.getDoubleValue()));
    }
}
```

* The linear neural network is reconstructed, taking the loss function and learning rate as the direct variables of
  parameter adjustment, and taking the output of the first network layer as the back-propagation input of the first
  layer, so that it has more powerful convergence.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.io.File;
import java.util.Arrays;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {
        // 构建初始权重向量
        DoubleVector W = DoubleVector.parse(20, 18, 18);
        // 获取到线性神经网络模型
        LNeuralNetwork lNeuralNetwork = ASModel.L_NEURAL_NETWORK;
        // 设置学习率 为 0.4
        lNeuralNetwork.setArg(LNeuralNetwork.LEARNING_RATE, SingletonCell.$(0.4));
        // 设置激活函数为 LEAKY_RE_LU 同时在这里设置该神经元使用的权重对象
        lNeuralNetwork.setArg(LNeuralNetwork.PERCEPTRON, SingletonCell.$(Perceptron.parse(ActivationFunction.RELU, W)));
        // 设置学习次数 为 12 * 目标数
        lNeuralNetwork.setArg(LNeuralNetwork.LEARN_COUNT, SingletonCell.$(12));
        // 设置目标数值
        lNeuralNetwork.setArg(
                LNeuralNetwork.TARGET,
                // 假设这里是5组数据对应的结果
                SingletonCell.$(new double[]{300, 210, 340, 400, 500})
        );
        // 构建被学习的数据 由此数据推导结果 找到每一组数据中 3 个参数之间的数学模型
        DoubleVector X1 = DoubleVector.parse(100, 50, 50);
        DoubleVector X2 = DoubleVector.parse(80, 50, 50);
        DoubleVector X3 = DoubleVector.parse(120, 50, 50);
        DoubleVector x4 = DoubleVector.parse(100, 100, 100);
        DoubleVector x5 = DoubleVector.parse(150, 100, 100);

        // 实例化出附加 Task 任务对象
        LNeuralNetwork.TaskConsumer taskConsumer = (loss, g, weight) -> {
            // 在这里打印出每一次训练的信息
            System.out.println("损失函数 = " + loss);
            System.out.println("计算梯度 = " + g);
            System.out.println("权重参数 = " + Arrays.toString(weight) + '\n');
        };
        // 训练出模型 TODO 在这里指定出每一次训练时的附加任务
        NumberModel model = lNeuralNetwork.function(taskConsumer, X1, X2, X3, x4, x5, W);
        System.out.println(model);

        // TODO 接下来开始使用模型进行一些测试
        // 向模型中传递一些数值
        Double function1 = model.function(new Double[]{100.0, 50.0, 50.0});
        // 打印计算出来的结果
        System.out.println(function1);
        // 再一次传递一些数值
        Double function2 = model.function(new Double[]{150.0, 100.0, 100.0});
        // 打印计算出来的结果
        System.out.println(function2);

        // TODO 确定模型可用，将模型保存
        ASModel.Utils.write(new File("C:\\Users\\Liming\\Desktop\\fsDownload\\MytModel.as"), model);
    }
}
```

### Version update date : 2023-04-09