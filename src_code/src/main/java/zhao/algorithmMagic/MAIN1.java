package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一个图片转换成为像素矩阵
        ColorMatrix parse = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg");
        System.out.println(parse);
        // 也可以将图片转换为整形矩阵
        IntegerMatrix integerMatrix = IntegerMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg");
        System.out.println(integerMatrix);
        // 将图片输出 首先获取到绘图集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\test3.jpg")
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}