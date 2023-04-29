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