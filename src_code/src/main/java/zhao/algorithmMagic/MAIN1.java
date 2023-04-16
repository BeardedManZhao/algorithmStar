package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.block.ColorMatrixSpace;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\person1.jpg");
        // 接下来的函数中 TODO Blue色通道将会自动加上 因此不需要进行 + 符号的追加
        // 获取到蓝色颜色通道组成的图像空间 如果只提取蓝色可以进行下面操作
        ColorMatrixSpace colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._B_);
        colorMatrices.show("image1", colorMatrix.getColCount(), colorMatrices.getRowCount());
        // 获取到由红和蓝颜色颜色通道层组成的图像空间
        colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._R_);
        colorMatrices.show("image2", colorMatrix.getColCount(), colorMatrices.getRowCount());
        // 获取到由所有颜色通道层组成的图像空间 因此就是 红 + 绿 就可以实现全部提取了（注意，单独进行 红+绿 提取暂不支持）
        colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._R_ + ColorMatrix._G_);
        colorMatrices.show("image3", colorMatrix.getColCount(), colorMatrices.getRowCount());
    }
}