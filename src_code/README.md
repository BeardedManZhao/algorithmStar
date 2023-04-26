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

* An image convolutional neural network computational classification model that supports image matrix objects.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.Perceptron;
import zhao.algorithmMagic.core.model.SingleLayerCNNModel;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 指定图尺寸
        int w = 91, h = 87;
        // 准备目标数据样本，代表不同的类别（有多少个类别就有多少个神经元）
        IntegerMatrixSpace WX = IntegerMatrixSpace.parse(
                new URL("https://user-images.githubusercontent.com/113756063/234247472-1df7f892-89b5-467f-9d8d-eb397b92c6ce.jpg"), w, h
        );
        IntegerMatrixSpace WY = IntegerMatrixSpace.parse(
                new URL("https://user-images.githubusercontent.com/113756063/234247497-0a329b5d-a15d-451f-abf7-abdc1655b77d.jpg"), w, h
        );
        IntegerMatrixSpace WZ = IntegerMatrixSpace.parse(
                new URL("https://user-images.githubusercontent.com/113756063/234247437-32e5b5ff-0baf-4637-805c-27472f07fd17.jpg"), w, h
        );

        // 准备 CNN 神经网络模型
        SingleLayerCNNModel singleLayerCnnModel = ASModel.SINGLE_LAYER_CNN_MODEL;

        // 准备卷积核，目标为突出图形轮廓
        DoubleMatrix parse = DoubleMatrix.parse(
                new double[]{-1, -1, -1},
                new double[]{-1, 8, -1},
                new double[]{-1, -1, -1}
        );
        DoubleMatrixSpace core = DoubleMatrixSpace.parse(parse, parse, parse);
        // 设置 卷积核
        singleLayerCnnModel.setArg(SingleLayerCNNModel.KERNEL, new FinalCell<>(core));
        // 设置 附加任务 池化 然后进行二值化操作
        singleLayerCnnModel.setTransformation(colorMatrix -> {
            ColorMatrix pooling = colorMatrix.pooling(2, 2, ColorMatrix.POOL_RGB_OBO_MAX);
            pooling.globalBinary(ColorMatrix._R_, 50, 0xffffff, 0);
            pooling.show("pool");
            return pooling;
        });

        // 将所有的目标类别添加到神经网络中
        singleLayerCnnModel.addTarget("X类别", WX);
        singleLayerCnnModel.addTarget("Y类别", WY);
        singleLayerCnnModel.addTarget("A类别", WZ);

        // 开始启动 CNN 在这里指定所有需要被分类的图形组
        {
            long start = System.currentTimeMillis();
            HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> function = singleLayerCnnModel.function(
                    IntegerMatrixSpace.parse(
                            new URL("https://user-images.githubusercontent.com/113756063/234247607-bfc59b79-bc6a-4ff1-992c-7ab1e9fd0116.jpg"), w, h
                    ),
                    IntegerMatrixSpace.parse(
                            new URL("https://user-images.githubusercontent.com/113756063/234247550-01777cee-493a-420f-8665-da31e60a1cbe.jpg"), w, h
                    ),
                    IntegerMatrixSpace.parse(
                            new URL("https://user-images.githubusercontent.com/113756063/234247630-46319338-b8e6-47bf-9918-4b734e665cf9.jpg"), w, h
                    )
            );
            System.out.println("分类完成，耗时 = " + (System.currentTimeMillis() - start));
            // 查看分类结果
            function.forEach((k, v) -> {
                // 获取到当前类别
                String name = k.getName();
                // 将当前类别的所有图像打印出来
                for (IntegerMatrixSpace integerMatrices : v) {
                    ColorMatrix.parse(integerMatrices).show(name);
                }
            });
        }
    }
}
```

### Version update date : 2023-04-09