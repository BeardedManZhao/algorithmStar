package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        Color[] colors = {Color.CYAN, Color.GREEN, Color.PINK, Color.red};
        // 手动创建图像矩阵
        ColorMatrix parse = ColorMatrix.parse(
                colors, colors, colors, colors
        );
        // 查看图像矩阵
        System.out.println(parse);
        // 将图像中的所有像素与自身矩阵进行规整乘法计算
        ColorMatrix agg1 = parse.agg(parse, ColorMatrix.COLOR_MULTIPLY_REGULATE);
        // 将图像中的所有像素与自身矩阵进行取余乘法计算
        ColorMatrix agg2 = parse.agg(parse, ColorMatrix.COLOR_MULTIPLY_REMAINDER);
        // 查看两个图像数据
        System.out.println(agg1);
        System.out.println(agg2);
    }
}