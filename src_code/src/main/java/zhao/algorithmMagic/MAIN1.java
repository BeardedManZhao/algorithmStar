package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MAIN1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        ColorMatrix colorMatrix1, colorMatrix2;
        {        // 将图像与样本读取进来
            colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\YB.bmp");
            colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test22.jpg");
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 15);
            temp.erode(2, 2, false);
            temp.show("temp");
            // 开始进行模板匹配 并返回最匹配的结果数值，在这里返回的就是所有匹配的结果数据
            // 其中每一个元素都是一个匹配到的符合条件的结果信息对象 key为匹配系数  value为匹配结果
            ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> entries = temp.templateMatching(
                    // 计算相似度需要使用的计算组件
                    ManhattanDistance.getInstance("MAN"),
                    // 需要被计算的矩阵对象
                    colorMatrix1,
                    // 计算时需要使用的颜色通道
                    ColorMatrix._G_,
                    // 计算时需要使用的卷积步长
                    16,
                    // 相似度阈值设定条件
                    v -> v < 1200000
            );
            // 将所有的边框绘制到原图中
            for (Map.Entry<Double, IntegerCoordinateTwo> matching : entries) {
                // 开始进行绘制 在这里首先获取到坐标数据
                IntegerCoordinateTwo coordinateTwo = matching.getValue();
                System.out.print("匹配系数 = ");
                System.out.println(matching.getKey());
                colorMatrix2.drawRectangle(
                        coordinateTwo,
                        new IntegerCoordinateTwo(coordinateTwo.getX() + colorMatrix1.getColCount(), coordinateTwo.getY() + colorMatrix1.getRowCount()),
                        Color.MAGENTA
                );
            }
        }
        colorMatrix1.show("人脸样本");
        colorMatrix2.show("识别结果");
    }
}