package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一个图片转换成为像素矩阵
        ColorMatrix parse = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg");
        System.out.println(parse);

        // 将图片输出 首先获取到绘图集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                // 在这里随意起一个名称
                "image",
                // 在这里设置启动器，该启动器传递的就是需要绘制的矩阵对象与绘制的路径
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\test3.jpg")
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }

        // 将像素矩阵进行上下反转
        ColorMatrix colors1 = parse.reverseBT(false);
        ImageRenderingIntegrator image1 = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(colors1, "C:\\Users\\Liming\\Desktop\\fsdownload\\test4.jpg")
        );
        if (image1.run()) {
            System.out.println("ok!!!");
        }

        // 再一次将像素矩阵进行左右反转
        ColorMatrix colors2 = parse.reverseBT(false);
        ImageRenderingIntegrator image2 = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(colors2, "C:\\Users\\Liming\\Desktop\\fsdownload\\test5.jpg")
        );
        if (image2.run()) {
            System.out.println("ok!!!");
        }
    }
}