package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到图像矩阵
        ColorMatrix parse = ColorMatrix.parse(new URL("https://img-blog.csdnimg.cn/img_convert/5765bdab08ef6e117d434e7e225b9013.png"));
        parse.show("image");
        System.out.println("ok!!!");
        System.out.println("长 = " + parse.getRowCount());
        System.out.println("宽 = " + parse.getColCount());
        // 将图像进行局部二值化
        parse.localBinary(
                // 指定本次二值化选择的颜色通道
                ColorMatrix._G_,
                // 指定本次二值化选出的局部图像矩阵数量
                100,
                // 指定本次二值化中局部矩阵中大于局部阈值的颜色编码
                0xffffff,
                // 指定本次二值化中局部矩阵中小于局部阈值的颜色编码
                0,
                // 指定本次二值化中局部阈值生成后要进行的微调数值，这里是降低20个阈值数值
                -30
        );
        // 查看结果数据
        parse.show("image");
    }
}
