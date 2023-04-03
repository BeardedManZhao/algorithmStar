package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到一个图像矩形对象
        ColorMatrix parse1 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/229441864-ec1770d5-1154-4e9c-837e-a4acfc5fb259.jpg")
        );
        // 将图像矩阵对象拷贝出一份并进行颜色反转
        ColorMatrix parse2 = ColorMatrix.parse(parse1.copyToNewArrays()).colorReversal(false);
        // 将两个图像矩阵进行合并操作，并展示出上下左右合并的结果图像
        // 这里是左右合并
        ColorMatrix colorMatrixLR = parse1.append(parse2, true);
        // 这里是上下合并
        ColorMatrix colorMatrixTB = parse1.append(parse2, false);
        // 查看结果图像
        colorMatrixLR.show("LR");
        colorMatrixTB.show("TB");
    }
}