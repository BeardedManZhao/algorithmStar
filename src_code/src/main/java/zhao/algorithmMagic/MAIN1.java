package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.HashColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 在获取图像之前打印下缓冲区中的像素数量
        System.out.println("加载图像之前的缓冲区像素数量 = " + HashColorMatrix.getHashColorLength());
        // 使用 ImageMatrix 对象读取一张图
        ColorMatrix colors = HashColorMatrix.parse(url);
        // 打印图中的像素数量 以及 展示图
        System.out.println("图像中包含的所有像素数量 = " + colors.getNumberOfDimensions());
        colors.show("win");
        // 在获取图像之后打印下缓冲区中的像素数量 这个数量就是加载一张图像使用到的像素数量
        // 缓冲区中的元素将会被其它的图复用。
        System.out.println("加载图像之后的缓冲区像素数量 = " + HashColorMatrix.getHashColorLength());
    }
}