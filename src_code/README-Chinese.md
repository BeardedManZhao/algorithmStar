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
        singleLayerCnnModel.setLearnCount(300);
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
        singleLayerCnnModel.setWeight(load.getY_train(), load.getImageWeight());

        // 准备训练时的附加任务 打印信息
        SingleLayerCNNModel.TaskConsumer taskConsumer = (loss, g, weight1) -> {
            System.out.println("\n损失函数 = " + loss);
            System.out.println("梯度数据 = " + Arrays.toString(g));
        };

        // 训练出结果模型
        long start = System.currentTimeMillis();
        ClassificationModel<IntegerMatrixSpace> model = singleLayerCnnModel.function(taskConsumer, load.getX_train());
        System.out.println("训练模型完成，耗时：" + (System.currentTimeMillis() - start));
        // 保存模型
        ASModel.Utils.write(new File("C:\\Users\\Liming\\Desktop\\fsDownload\\MyModel.as"), model);


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
                ASModel.Utils.read(new File("C:\\Users\\Liming\\Desktop\\fsDownload\\MyModel.as"))
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

* 能够通过本地文件数据集进行神经网络的训练，该操作将会使得本地文件路径中的目录作为类别，目录中的文件作为训练数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;
import zhao.algorithmMagic.core.model.dataSet.ASDataSet;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {

        // 指定图尺寸
        int w = 91, h = 87;

        // 准备 CNN 神经网络模型
        SingleLayerCNNModel singleLayerCnnModel = ASModel.SINGLE_LAYER_CNN_MODEL;
        // 设置学习率
        singleLayerCnnModel.setLearningRate(0.01f);
        // 设置训练次数
        singleLayerCnnModel.setLearnCount(500);
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
        ASDataSet dataSet = ASDataSet.Load.FILE_DTR.load(w, h, "C:\\Users\\zhao\\Desktop\\out");
        // 将类别和目标数值设置到网络
        singleLayerCnnModel.setWeight(dataSet.getY_train(), dataSet.getImageWeight());
        // 训练出结果模型
        long start = System.currentTimeMillis();
        ClassificationModel<IntegerMatrixSpace> model = singleLayerCnnModel.function(dataSet.getX_train());
        System.out.println("训练模型完成，耗时：" + (System.currentTimeMillis() - start));
        // 保存模型
        ASModel.Utils.write(new File("MyModel.as"), model);
        System.out.println("模型保存成功，您可以开始使用模型了!!!");
    }
}
```

* 针对复数操作数对象，其具有了更加贴近Java原生数值类型的操作，实现了Number类，下面是一个实现。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.ComplexNumber;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到复数对象
        ComplexNumber parse = ComplexNumber.parse("1.2 + 0i");
        // 获取到复数的各个部分
        System.out.println(parse);
        System.out.println(parse.getReal());
        System.out.println(parse.getImaginary());
        // 使用复数进行一个简单的计算操作
        ComplexNumber divide = parse.divide(parse);
        System.out.println(divide);
        // 转换成为实数
        System.out.println(parse.doubleValue());
    }
}
```

* 所有操作数的加减函数都支持拓展维度的方式来进行计算，意味着其现在已经允许矩阵与数值或向量计算等操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到整形与浮点矩阵对象
        DoubleMatrix doubleMatrix = DoubleMatrix.parse(
                new double[]{1.5, 2.5, 3.5},
                new double[]{3.5, 4.5, 5.5},
                new double[]{7.5, 8.5, 9.5}
        );

        IntegerMatrix integerMatrix = IntegerMatrix.parse(
                new int[]{1, 2, 3},
                new int[]{3, 4, 5},
                new int[]{7, 8, 9}
        );

        // 获取到两个向量
        IntegerVector integerVector = IntegerVector.parse(10, 20, 30);
        DoubleVector doubleVector = DoubleVector.parse(10.5, 20.5, 30.5);

        // 使用矩阵与向量之间进行计算
        System.out.println(doubleMatrix.add(doubleVector));
        System.out.println(integerMatrix.add(integerVector));

        // 矩阵与数值之间的计算
        System.out.println(doubleMatrix.add(10));
        System.out.println(integerMatrix.add(10));
        System.out.println(doubleMatrix.diff(10));
        System.out.println(integerMatrix.diff(10));

        // 向量与数值之间的计算
        System.out.println(doubleVector.add(10));
        System.out.println(integerVector.add(10));
        System.out.println(doubleVector.diff(10));
        System.out.println(integerVector.diff(10));
    }
}
```

* 矩阵对象支持维度的重设操作，其能够按照指定的新维度来进行维度的检查与重新排布操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备矩阵对象
        DoubleMatrix doubleMatrix = DoubleMatrix.parse(
                new double[]{1.5, 2.5, 3.5, 4.5},
                new double[]{5.5, 6.5, 7.5, 8.5}
        );

        IntegerMatrix integerMatrix = IntegerMatrix.parse(
                new int[]{1, 2, 3, 4, 5, 10},
                new int[]{5, 6, 7, 8, 9, 10}
        );

        // 开始进行维度重设
        System.out.println(doubleMatrix.reShape(4, 2));
        System.out.println(integerMatrix.reShape(3, 4));
        System.out.println(doubleMatrix.reShape(8, 1));
        System.out.println(integerMatrix.reShape(6, 2));
    }
}
```

* 矩阵空间对象已支持维度重设的函数操作，其接收一个3维度信息，并进行维度检查与重设。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备矩阵对象
        DoubleMatrix doubleMatrix = DoubleMatrix.parse(
                new double[]{1.5, 2.5, 3.5, 4.5},
                new double[]{5.5, 6.5, 7.5, 8.5}
        );

        IntegerMatrix integerMatrix = IntegerMatrix.parse(
                new int[]{1, 2, 3, 4, 5, 10},
                new int[]{5, 6, 7, 8, 9, 10}
        );
        // 准备矩阵空间
        DoubleMatrixSpace doubleMatrices = DoubleMatrixSpace.parse(
                doubleMatrix, doubleMatrix, doubleMatrix
        );

        IntegerMatrixSpace integerMatrices = IntegerMatrixSpace.parse(
                integerMatrix, integerMatrix, integerMatrix
        );
        // 开始进行维度重设操作
        System.out.println(integerMatrices.reShape(1, 2, 18));
        System.out.println(doubleMatrices.reShape(1, 3, 8));
    }
}
```

* 针对图像矩阵开始支持均值池化操作，其能够通过简单的常量设置均值池化，提高灵活性。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.SingletonCell;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到相机设备数据输入组件
        InputComponent jpeg = InputCamera.builder()
                .addInputArg(InputCameraBuilder.Camera_Index, SingletonCell.$(0))
                .addInputArg(InputCameraBuilder.Image_Format, SingletonCell.$_String("JPEG"))
                .create();
        // 读取一张图
        ColorMatrix colorMatrix = ColorMatrix.parse(jpeg);
        // 显示原图
        colorMatrix.show("原图");
        // 开始池化 在新版本中 新增了均值池化与分通道均值池化两种模式
        // 首先是均值池化 指定 3x3 的局部池化方案
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_MEAN).show("均值池化");
        // 然后是分通道池化
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_OBO_MEAN).show("分通道均值池化");
    }
}
```

### Version update date : xx xx-xx-xx