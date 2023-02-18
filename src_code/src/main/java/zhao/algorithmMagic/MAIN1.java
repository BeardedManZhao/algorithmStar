package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 设置需要被读取的图像文件路径
        String path = "C:\\Users\\zhao\\Downloads\\test.png";
        // 使用Color矩阵解析图片，获取到图像的像素矩阵
        ColorMatrix colors = ColorMatrix.parse(path);
        System.out.print("当前图像的像素色彩模式 = ");
        System.out.println(colors.isGrayscale() ? "灰度" : "彩色");

        /*
         将像素矩阵的色彩模式强制转换
         这里由于转换之前是调用的彩色图像读取，因此转换之后会标记为灰度图像
         需要注意的是这里并不会真正变成灰度图 而是在后面的操作中尽量采用灰度图像的方式处理矩阵数据
        */
        colors.forcedColorChange();
        System.out.print("当前图像的像素色彩模式 = ");
        System.out.println(colors.isGrayscale() ? "灰度" : "彩色");

        // 强制转换像素的矩阵，可以影响到像素矩阵到整形矩阵之间的转换，会按照不同的模式使用不同的逻辑转换像素矩阵
        IntegerMatrix ints = colors.toIntegerMatrix();
        // 获取到图像绘制集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                // 在这里传递需要被绘制的矩阵对象与绘制的新图像路径 以及 图像绘制的像素倍率
                new ImageRenderingMarLauncher<>(ints, "C:\\Users\\zhao\\Downloads\\test1.png", 1)
        );
        // 开始运行
        image.run();
    }
}