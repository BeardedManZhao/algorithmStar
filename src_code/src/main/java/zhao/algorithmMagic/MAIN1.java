package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;


public class MAIN1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        ColorMatrix colorMatrix1, colorMatrix2;
        {        // 将图像与样本读取进来
            colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\YB.bmp");
            colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test22.jpg");
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 0);
            // 开始进行模板匹配
            IntegerCoordinateTwo man = temp.templateMatching(
                    ManhattanDistance.getInstance("MAN"),
                    colorMatrix1,
                    ColorMatrix._G_,
                    10,
                    false
            );
            // 开始进行绘制
            colorMatrix2.drawRectangle(
                    man,
                    new IntegerCoordinateTwo(man.getX() + colorMatrix1.getColCount(), man.getY() + colorMatrix1.getRowCount()),
                    Color.MAGENTA
            );
        }
        colorMatrix1.show("人脸样本");
        colorMatrix2.show("识别结果");
    }
}