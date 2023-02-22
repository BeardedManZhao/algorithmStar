package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 加载矩阵
        ColorMatrix parse = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_2.jpg");
        System.out.println(parse.getColCount());
        System.out.println(parse.getRowCount());
        long start = System.currentTimeMillis();
        parse = parse.transpose(); // 修改之前的矩阵转置耗时 87 毫秒
        System.out.println(System.currentTimeMillis() - start);
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\res1.jpg", 1)
        );
        image.run();
    }
}