package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ImageMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 使用 ImageMatrix 对象读取一张图
        ImageMatrix parse1 = ImageMatrix.parseGrayscale(url);
        parse1.show("image");
        // 将此图像矩阵进行重设
        ImageMatrix colors = parse1.reSize(100, 100);
        // 查看图像
        colors.show("缩放之后的图像");
    }
}