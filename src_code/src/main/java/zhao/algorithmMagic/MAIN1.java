package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

import java.sql.SQLException;


public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 将一些图像文件转换成为一个图像矩阵对象
        IntegerMatrixSpace colorMatrix1 = IntegerMatrixSpace.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test2.bmp");
        // 准备一个卷积核
        IntegerMatrix parse = IntegerMatrix.parse(
                new int[]{1, 1, 1},
                new int[]{1, 1, 1},
                new int[]{1, 1, 1}
        );
        IntegerMatrixSpace core1 = IntegerMatrixSpace.parse(
                parse, parse, parse
        );
        IntegerMatrix colors = colorMatrix1.foldingAndSum(3, 3, core1);
        ColorMatrix.parse(colors, true).show("image");
    }
}