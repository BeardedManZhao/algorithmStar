package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public final class MAIN1 {
    public static void main(String[] args) {
        // 获取到图像
        ColorMatrix srcImage = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\华为.bmp");
        // 调整亮度
        srcImage.dimming(0.75f);
        // 调整对比度 增加 10 对比度属性
        srcImage.contrast(10);
        // 提取图像中的子图像 从(20, 79) 到(335, 156)
        ColorMatrix subColor = srcImage.extractImage(20, 79, 335, 156);
        // 将这一部分的颜色反转
        subColor.colorReversal(false);
        // 将处理好的局部子图像合并到原图像
        srcImage.merge(subColor, 20, 79);
        // 输出图像
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(srcImage, "C:\\Users\\Liming\\Desktop\\华为1.jpg", 1)
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}