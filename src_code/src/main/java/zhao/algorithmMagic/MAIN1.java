package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 设置需要计算相似度的图片文件地址
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_1.jpg"; // 被对比的图像文件
        String s2 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_2.jpg"; // 相似
        String s3 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_3.jpg"; // 不相似

        // 将图片文件转换成两个图像灰度像素矩阵
        ColorMatrix parse1 = ColorMatrix.parseGrayscale(s1);
        ColorMatrix parse2 = ColorMatrix.parseGrayscale(s2);
        ColorMatrix parse3 = ColorMatrix.parseGrayscale(s3);

        {
            // 将灰度像素矩阵扁平化获取到向量对象 用于进行度量的计算
            IntegerVector parse11 = IntegerVector.parseGrayscale(parse1);
            IntegerVector parse22 = IntegerVector.parseGrayscale(parse2);
            IntegerVector parse33 = IntegerVector.parseGrayscale(parse3);

            // 使用曼哈顿度量计算出两个图片的相似度
            // (由于图片中的RGB值很大，因此使用欧几里德进行平方值时会发生精度溢出问题，选择曼哈顿可避免这种情况)
            double d1_2 = ManhattanDistance.getInstance("Man").getTrueDistance(parse11.toArray(), parse22.toArray());
            double d1_3 = ManhattanDistance.getInstance("Man").getTrueDistance(parse11.toArray(), parse33.toArray());

            // 打印距离 距离越大 相似度越低
            System.out.println("图片1与图片2对比的距离 = " + d1_2);
            System.out.println("图片1与图片3对比的距离 = " + d1_3);
        }

        // 输出图片1的灰度图像文件
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse1, "C:\\Users\\Liming\\Desktop\\fsdownload\\res1.jpg", 1)
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}