package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.io.File;
import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 解析URL获取到图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test.bmp");
        // 输出图像的 ASCII 数值，输出规则为 G 通道颜色数值 大于 40 的 输出符号 'A' 其它输出符号 ' '
        parse1.save(
                new File("C:\\Users\\zhao\\Desktop\\fsdownload\\res.txt"),
                ColorMatrix._G_, 220, ' ', '-'
        );
    }
}
