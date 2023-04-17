package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleTargetContour;
import zhao.algorithmMagic.core.model.TarImageClassification;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到 人脸样本
        ColorMatrix person = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 获取到 小猫样本
        ColorMatrix cat = ColorMatrix.parse(
                "C:\\Users\\Liming\\Desktop\\fsdownload\\catHead1.jpg"
        );


        // 获取到需要被识别的图像
        ColorMatrix parse1 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/231062649-34268530-801a-4520-81ae-176936a3a981.jpg")
        );
        ColorMatrix parse2 = ColorMatrix.parse(
                "C:\\Users\\Liming\\Desktop\\fsdownload\\cat.jpg"
        );

        // 获取到图像分类模型
        TarImageClassification tarImageClassification = ASModel.TAR_IMAGE_CLASSIFICATION;
        // 设置人脸类型标签样本
        tarImageClassification.setArg("person", new FinalCell<>(person));
        // 设置小猫类型标签样本
        tarImageClassification.setArg("cat", new FinalCell<>(cat));
        // 设置启用二值化操作
        TarImageClassification.SINGLE_TARGET_CONTOUR.setArg(SingleTargetContour.isBinary, SingletonCell.$(true));

        ColorMatrix[] colorMatrices = {parse1, parse2};
        // 需要注意的是，当指定了二值化操作之后，传递进去的图像矩阵将会被更改 因此在这里使用模型的时候使用克隆出来的矩阵数组
        ColorMatrix[] clone = {ColorMatrix.parse(parse1.copyToNewArrays()), ColorMatrix.parse(parse2.copyToNewArrays())};
        long start = System.currentTimeMillis();
        // 开始计算并迭代计算出来的类别集合，进行结果查看
        for (Map.Entry<String, ArrayList<Integer>> entry : AlgorithmStar.modelConcurrency(tarImageClassification, clone).entrySet()) {
            String k = entry.getKey() + "类别";
            for (int index : entry.getValue()) {
                colorMatrices[index].show(k);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}