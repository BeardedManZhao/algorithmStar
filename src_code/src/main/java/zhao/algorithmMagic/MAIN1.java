package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.ImageMatrix;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.awt.*;
import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println(OperationAlgorithmManager.VERSION);
//        if (args.length > 0) {
//            ASDynamicLibrary.addDllDir(new File(args[0]));
//            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
//        } else {
//            System.out.println("感谢您的使用。");
//        }

        Color color = new Color(239, 216, 194);

        // 实例化图片
        ColorMatrix parse = ImageMatrix.parse("C:\\Users\\zhao\\Desktop\\Test\\无标题.jpg");
        parse.show("原图");

        // 多次进行滤波
        for (int i = 0; i < 32; i++) {
            System.out.println("开始第" + i + "次处理");
            // 进行颜色替换 将 标记的颜色做为被替换的颜色
            parse = parse.colorReplace(
                    color,
                    (Transformation<ColorMatrix, Color>) colors -> {
                        // 使用 RGB 的均值做为替换颜色
                        return new Color(
                                (int) colors.avg(ColorMatrix._R_),
                                (int) colors.avg(ColorMatrix._G_),
                                (int) colors.avg(ColorMatrix._B_)
                        );
                    },
                    512,
                    32,
                    new IntegerCoordinateTwo(453, 195),
                    false
            );
            // 进行被处理颜色的切换 因为现在颜色已经变化了 所以将水印部分的新的值计算出来
            final ColorMatrix colorMatrix = parse.extractImage(453, 195, parse.getColCount(), parse.getRowCount());
            color = new Color(
                    (int) colorMatrix.avg(ColorMatrix._R_),
                    (int) colorMatrix.avg(ColorMatrix._G_),
                    (int) colorMatrix.avg(ColorMatrix._B_)
            );
        }

        // 查看替换之后的图
        parse.show("tempRes");
        // 此时水印只有很浅的一个痕迹 对水印部分进行几次滤波
        ColorMatrix temp = null;
        for (int i = 0; i < 2; i++) {
            temp = parse.extractImageSrc(195, parse.getRowCount() - 1).boxBlur(false);
        }
        // 滤波结果合并
        parse.merge(temp, 0, 195);
        // 再次查看
        parse.show("res");
        parse.save("C:\\Users\\zhao\\Desktop\\Test\\无标题1.jpg");
    }
}
