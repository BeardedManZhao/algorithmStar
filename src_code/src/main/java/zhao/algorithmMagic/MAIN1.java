package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 准备图像的URL对象
        URL url = new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png");
        // 解析URL获取到矩阵
        ColorMatrix parse = ColorMatrix.parse(url);
        // 查看图像
        parse.show("image");
    }
}
