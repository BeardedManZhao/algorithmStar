package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) throws InterruptedException {
        String s1 = "C:\\Users\\zhao\\Desktop\\fsdownload\\微信图片_2.jpg";
        // 获取到图像矩阵对象
        ColorMatrix parse = ColorMatrix.parse(s1);
        // 将图像在原图像的基础上进行颜色反转操作
        parse.colorReversal(false);
        // 查看颜色反转之后的图像
        parse.show("image1");
        // 输出反转之后的图像
        parse.save("C:\\Users\\zhao\\Desktop\\fsdownload\\res123.jpg");
    }
}
