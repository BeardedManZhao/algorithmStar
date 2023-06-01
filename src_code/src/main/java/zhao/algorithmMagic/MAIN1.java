package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 读取一张图 并将其规整到 208x208 的尺寸
        ColorMatrix colorMatrix = ColorMatrix.parse(
                "C:\\Users\\Liming\\Downloads\\194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png",
                208, 208
        );
        // 显示原图
        colorMatrix.show("原图");
        // 开始池化 在新版本中 新增了均值池化与分通道均值池化两种模式
        // 首先是均值池化 指定 3x3 的局部池化方案
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_MEAN).show("均值池化");
        // 然后是分通道池化
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_OBO_MEAN).show("分通道均值池化");
    }
}