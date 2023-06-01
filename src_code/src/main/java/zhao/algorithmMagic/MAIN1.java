package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 读取一张图 208x208
        ColorMatrix colorMatrix = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png"),
                208, 208
        );

        // 查看原图
        colorMatrix.show("原图");

        // 将图进行维度重设 宽度缩小为1/2 高度 * 2
        colorMatrix.reShape(208 << 1, 208 >> 1).show("结果图像");
    }
}