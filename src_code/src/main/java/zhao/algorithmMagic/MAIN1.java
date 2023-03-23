package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.sql.SQLException;


public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 将一些图像文件转换成为一个图像矩阵对象
        ColorMatrix colorMatrix1 = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test2.bmp");
        // 对图像进行二值化
        colorMatrix1.globalBinary(ColorMatrix._G_, 100 , 0xffffff, 0);
        colorMatrix1.show("腐蚀之前的 image");
        // 开始对图像矩阵进行腐蚀操作
        colorMatrix1.erode(2, 2, false).show("腐蚀之后的 image");
    }
}