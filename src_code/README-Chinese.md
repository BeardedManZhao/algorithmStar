# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.18 - 1.19
* 神经元对象进行重构，权重字段添加至感知机对象中，提供给感知机进行管理。

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

* 线性神经网络进行重构，将损失函数与学习率作为参数调整的直接变量，将第一网络层的输出作为第一层的反向传播输入，使得其具有更加强大的收敛性。

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

* 支持图像读取时进行图像尺寸的变换操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws MalformedURLException {
        // 获取到图像对象 在这里使用变换读取
        ColorMatrix parse = ColorMatrix.parse(
                // 要读取图像的 链接
                new URL("https://user-images.githubusercontent.com/113756063/231062649-34268530-801a-4520-81ae-176936a3a981.jpg"),
                // 设置变换操作之后的图像大小 第一个是宽度 第二个是高度
                200, 100
        );
        // 查看变换结果
        parse.show("image");
    }
}
```

* 支持图像矩阵对象的图像卷积神经网络计算分类模型。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleLayerCNNModel;
import zhao.algorithmMagic.core.model.Perceptron;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.util.ArrayList;
import java.util.HashMap;

public class MAIN1 {
  // 在 main 函数中进行模型的保存和读取以及使用
  public static void main(String[] args) {
    // 指定图尺寸
    int w = 91, h = 87;
    // 准备目标数据样本，代表不同的类别（有多少个类别就有多少个神经元）
    IntegerMatrixSpace WX = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母样本X.jpg", w, h);
    IntegerMatrixSpace WY = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母样本Y.jpg", w, h);
    IntegerMatrixSpace WZ = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母样本A.jpg", w, h);

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
    // 设置 附加任务 二值化操作 由于我们是对 G 通道进行的权重较大
    singleLayerCnnModel.setTransformation(colorMatrix -> {
      colorMatrix.globalBinary(ColorMatrix._G_, 200, 0x010501, 0);
      return colorMatrix;
    });

    // 将所有的目标类别添加到神经网络中
    singleLayerCnnModel.addTarget("X类别", WX);
    singleLayerCnnModel.addTarget("Y类别", WY);
    singleLayerCnnModel.addTarget("A类别", WZ);

    // 开始启动 CNN 在这里指定所有需要被分类的图形组
    HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> function = singleLayerCnnModel.function(
            IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母1.jpg", w, h),
            IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母2.jpg", w, h),
            IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\字母3.jpg", w, h)
    );
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
```

### Version update date : xx xx-xx-xx