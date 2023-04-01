package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1, colorMatrix2;
        // 将图像与样本读取进来
        colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3.bmp");
        colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3YB.bmp");
        // 使用模板匹配 获取到 colorMat1 中 与 colorMat2 最相近的子矩阵
        IntegerCoordinateTwo topLeft = colorMatrix1.templateMatching(
                // 相似度计算组件
                ManhattanDistance.getInstance("MAN"),
                // 模板图像
                colorMatrix2,
                // 需要被计算的颜色通道
                ColorMatrix._G_,
                // 相似度越小 匹配度越大
                false
        );
        System.out.println(topLeft);
    }
}