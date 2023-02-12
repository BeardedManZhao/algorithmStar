package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 设置需要计算相似度的图片文件地址
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_3.jpg"; // 被对比的图像文件
        // 将图片文件转换成图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse(s1);
        // 将图像颜色反转
        long start = System.currentTimeMillis();
        parse1.colorReversal(false);
        System.out.println("模糊完成，耗时 = " + (System.currentTimeMillis() - start));
        // 输出图片1的图像文件
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse1.boxBlur(false), "C:\\Users\\Liming\\Desktop\\fsdownload\\res1.jpg", 1)
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}