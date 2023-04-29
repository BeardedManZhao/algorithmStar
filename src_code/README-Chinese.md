# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.19 - 1.20
* 移除冗余的
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
        // 设置学习率 为 0.2
        lNeuralNetwork.setArg(LNeuralNetwork.LEARNING_RATE, SingletonCell.$(0.2));
        // 设置激活函数为 LEAKY_RE_LU 同时在这里设置该神经元使用的权重对象
        lNeuralNetwork.setArg(LNeuralNetwork.PERCEPTRON, SingletonCell.$(Perceptron.parse(ActivationFunction.RELU, W)));
        // 设置学习次数 为 24 * 目标数
        lNeuralNetwork.setArg(LNeuralNetwork.LEARN_COUNT, SingletonCell.$(24));
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

* 支持图像卷积神经网络进行图像分类训练，能够实现图像分类模型的训练，其会返回训练好的模型，模型能够被保存到磁盘。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;
import zhao.algorithmMagic.core.model.dataSet.ASDataSet;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.io.File;
import java.util.Arrays;

public class MAIN1 {

    public static void main(String[] args) {

        // 指定图尺寸
        int w = 91, h = 87;

        // 准备 CNN 神经网络模型
        SingleLayerCNNModel singleLayerCnnModel = ASModel.SINGLE_LAYER_CNN_MODEL;
        // 设置学习率
        singleLayerCnnModel.setLearningRate(0.1f);
        // 设置训练次数
        singleLayerCnnModel.setLearnCount(200);
        // 设置激活函数
        singleLayerCnnModel.setActivationFunction(ActivationFunction.LEAKY_RE_LU);
        // 设置损失函数
        singleLayerCnnModel.setLossFunction(LossFunction.MAE);

        // 准备卷积核，目标为突出图形轮廓
        DoubleMatrix parse = DoubleMatrix.parse(
                new double[]{-1, -1, -1},
                new double[]{-1, 8, -1},
                new double[]{-1, -1, -1}
        );
        DoubleMatrixSpace core = DoubleMatrixSpace.parse(parse, parse, parse);
        // 设置 卷积核
        singleLayerCnnModel.setArg(SingleLayerCNNModel.KERNEL, new FinalCell<>(core));
        // 设置 附加任务 池化 然后进行二值化操作 TODO 注意 如果需要模型的保存，请使用 Class 的方式进行设置，使用 lambda 将会导致模型无法反序列化
        // 如果不需要，此处可以不进行设置
        singleLayerCnnModel.setTransformation(
                new PoolBinaryTfTask(2, 1, true, 50, 0x011001, 0x010101, ColorMatrix._R_)
        );

        // 获取到字母数据集
        ASDataSet load = ASDataSet.Load.LETTER.load(w, h);
        // 将目标数值与权重设置到网络
        singleLayerCnnModel.setWeight(load.getImageY_train(), load.getImageWeight());

        // 准备训练时的附加任务 打印信息
        SingleLayerCNNModel.TaskConsumer taskConsumer = (loss, g, weight1) -> {
            System.out.println("\n损失函数 = " + loss);
            System.out.println("梯度数据 = " + Arrays.toString(g));
        };

        // 训练出结果模型
        long start = System.currentTimeMillis();
        ClassificationModel<IntegerMatrixSpace> model = singleLayerCnnModel.function(taskConsumer, load.getImageX_train());
        System.out.println("训练模型完成，耗时：" + (System.currentTimeMillis() - start));
        // 保存模型
        ASModel.Utils.write(new File("C:\\Users\\Liming\\Desktop\\fsDownload\\MytModel.as"), model);


        // 提供一个新图 开始进行测试
        IntegerMatrixSpace parse1 = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsDownload\\字母5.jpg", w, h);
        // 放到模型中 获取到结果
        KeyValue<String[], DoubleVector[]> function = model.function(new IntegerMatrixSpace[]{parse1});
        // 提取结果向量
        DoubleVector[] value = function.getValue();
        // 由于被分类的图像对象只有一个，因此直接查看 0 索引的数据就好 这里是一个向量，其中每一个索引代表对应索引的类别得分值
        System.out.println(value[0]);
        // 查看向量中不同维度对应的类别
        System.out.println(Arrays.toString(function.getKey()));
        System.out.println("当前图像类别 = " + function.getKey()[ASMath.findMaxIndex(value[0].toArray())]);
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.ClassificationModel;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.io.File;
import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) {
        ClassificationModel<IntegerMatrixSpace> model = ASClass.transform(
                ASModel.Utils.read(new File("C:\\Users\\Liming\\Desktop\\fsDownload\\MytModel.as"))
        );
        // 提供一个新图 开始进行测试
        IntegerMatrixSpace parse1 = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsDownload\\字母4.jpg", 91, 87);
        // 放到模型中 获取到结果
        KeyValue<String[], DoubleVector[]> function = model.function(new IntegerMatrixSpace[]{parse1});
        // 提取结果向量
        DoubleVector[] value = function.getValue();
        // 由于被分类的图像对象只有一个，因此直接查看 0 索引的数据就好 这里是一个向量，其中每一个索引代表对应索引的类别得分值
        System.out.println(value[0]);
        // 查看向量中不同维度对应的类别
        String[] key = function.getKey();
        System.out.println(Arrays.toString(key));
        // 根据索引查看当前图像分类得分最大值对应的类别
        System.out.println("当前图像属于 " + key[ASMath.findMaxIndex(value[0].toArray())]);
    }
}
```

### Version update date : xx xx-xx-xx