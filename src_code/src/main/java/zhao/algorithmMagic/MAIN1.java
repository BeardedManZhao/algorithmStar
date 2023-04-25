package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleLayerCNNModel;
import zhao.algorithmMagic.core.model.Perceptron;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MAIN1 {
    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws MalformedURLException {
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