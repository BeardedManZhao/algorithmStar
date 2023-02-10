package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个矩阵对象
        IntegerMatrix parse = IntegerMatrix.parse(
                new int[]{0xffffff, 0xfffffe, 0xfffffd, 0xfffffc, 0xfffffb},
                new int[]{0xddffff, 0xddfffe, 0xddfffd, 0xddfffc, 0xddfffb},
                new int[]{0xccffff, 0xccfffe, 0xccfffd, 0xccfffc, 0xccfffb},
                new int[]{0xbbffff, 0xbbfffe, 0xbbfffd, 0xbbfffc, 0xbbfffb},
                new int[]{0xffffff, 0xfffffe, 0xfffffd, 0xfffffc, 0xfffffb},
                new int[]{0xffaaaa, 0xfaaffe, 0xfaaffd, 0xfaaffc, 0x000000}
        );
        // 获取到图像绘制集成器
        ImageRenderingIntegrator integrator = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\test1.jpg")
        );
        // 开始绘制图像
        if (integrator.run()) {
            System.out.println("ok!!!");
        }
    }
}