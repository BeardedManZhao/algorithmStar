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