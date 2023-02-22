# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.15 - x.xx
* Remove the outdated normalization function from the data preprocessing component and use pretreatment instead.
* In the linear normalization calculation component, the problem that the normalization result of the reshaping vector
  is inaccurate was fixed.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.normalization.LinearNormalization;
import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个向量对象
        IntegerVector parse1 = IntegerVector.parse(15, 9, 8, 7, 6, 7, 8, 9, 10);
        DoubleVector parse2 = DoubleVector.parse(10.5, 9, 8, 7, 6, 7, 8, 9, 10);
        // 获取到数据预处理组件 线性归一化 并设置其归一化的最大值与最小值
        LinearNormalization line = LinearNormalization
                .getInstance("line")
                .setMin(-5).setMax(5);
        // 获取到数据预处理组件，序列标准化
        Z_ScoreNormalization z_score = Z_ScoreNormalization.getInstance("z_Score");
        // 打印序列线性归一化的结果向量
        System.out.println(line.pretreatment(parse1));
        System.out.println(line.pretreatment(parse2));
        // 打印线性归一化的结果向量
        System.out.println(z_score.pretreatment(parse1));
        System.out.println(z_score.pretreatment(parse2));
    }
}
```

* The new matrix drawing function is an integrator, which can treat each element of an integer matrix as the color value
  of a pixel or pixel block, and draw the image!

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个矩阵对象
        IntegerMatrix parse = IntegerMatrix.parse(
                new int[]{0xffffff, 0xfffffe, 0xfffffd, 0xfffffc, 0xfffffb},
                new int[]{0xddffff, 0xddfffe, 0xddfffd, 0xddfffc, 0xddfffb},
                new int[]{0xccffff, 0xccfffe, 0xccfffd, 0xccfffc, 0xccfffb},
                new int[]{0xbbffff, 0xbbfffe, 0xbbfffd, 0xbbfffc, 0xbbfffb},
                new int[]{0xffffff, 0xfffffe, 0xfffffd, 0xfffffc, 0xfffffb},
                new int[]{0xffaaaa, 0xfaaffe, 0xfaaffd, 0xfaaffc, 0x000000}
        );
        // 获取到图像绘制集成器
        ImageRenderingIntegrator integrator = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg")
        );
        // 开始绘制图像
        if (integrator.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

* Add a new Color matrix object, which is designed for matrix drawing and has good performance when drawing. It can
  perform color reversal and image rotation operations.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        Color color1 = new Color(0xffffff);
        Color color2 = new Color(0xffffcc);
        Color color3 = new Color(0xffffaa);
        Color color4 = new Color(0xffff00);
        Color color5 = new Color(0xffaa00);
        Color color6 = new Color(0xbbfff);
        // 创建一个矩阵对象，其中包含一些像素的颜色
        ColorMatrix parse = ColorMatrix.parse(
                new Color[]{color1, color2, color3, color4, color5, color6},
                new Color[]{color1, color2, color3, color4, color5, color6},
                new Color[]{color1, color2, color3, color4, color5, color6}
        );
        // 打印矩阵对象
        System.out.println(parse);
        // 反转矩阵中的所有颜色 形参位置传递false 代表反转操作直接作用于源矩阵中
        parse = parse.colorReversal(false);
        // 左右反转图像中的像素坐标
        parse = parse.reverseLR(false);
        // 上下反转图像中的像素坐标
        parse = parse.reverseBT(false);
        // 修改指定坐标（0,0）的像素点的Color值为 0
        parse.setPixels(0, 0, new Color(0));
        // 再一次打印矩阵
        System.out.println(parse);
    }
}
```

* You can use the Color matrix object and the drawing integrator to draw images. The code is shown below.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        Color color1 = new Color(0xffffff);
        Color color2 = new Color(0xffffcc);
        Color color3 = new Color(0xffffaa);
        Color color4 = new Color(0xffff00);
        Color color5 = new Color(0xffaa00);
        Color color6 = new Color(0xbbfff);
        // 创建一个矩阵对象，其中包含一些像素的颜色
        ColorMatrix parse = ColorMatrix.parse(
                new Color[]{color1, color2, color3, color4, color5, color6},
                new Color[]{color1, color2, color3, color4, color5, color6},
                new Color[]{color1, color2, color3, color4, color5, color6}
        );
        // 反转矩阵中的颜色
        parse = parse.colorReversal();
        // 获取到图像绘制器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                // 传递一个集成器的名称
                "image",
                // 传递一个集成器所需的启动器，其中第一个元素就是颜色矩阵
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\test.jpg")
        );
        // 开始运行
        boolean run = image.run();
        if (run) {
            System.out.println("ok!!!");
        } else {
            System.out.println("error!!!");
        }
    }
}
```

* The matrix data can be obtained from an image file. At present, two objects, namely the shape matrix and the color
  pixel matrix, can be obtained from the image.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一个图片转换成为像素矩阵
        ColorMatrix parse = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg");
        System.out.println(parse);

        // 将图片输出 首先获取到绘图集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                // 在这里随意起一个名称
                "image",
                // 在这里设置启动器，该启动器传递的就是需要绘制的矩阵对象与绘制的路径
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\test3.jpg")
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }

        // 将像素矩阵进行上下反转
        ColorMatrix colors1 = parse.reverseBT(false);
        ImageRenderingIntegrator image1 = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(colors1, "C:\\Users\\Liming\\Desktop\\fsdownload\\test4.jpg")
        );
        if (image1.run()) {
            System.out.println("ok!!!");
        }

        // 再一次将像素矩阵进行左右反转
        ColorMatrix colors2 = parse.reverseBT(false);
        ImageRenderingIntegrator image2 = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(colors2, "C:\\Users\\Liming\\Desktop\\fsdownload\\test5.jpg")
        );
        if (image2.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

* It supports directly loading an image file into a grayscale image, which can better process and calculate various
  algorithms.

```java
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
```

* The pixel color matrix supports the use of boxes to blur images.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 设置需要计算相似度的图片文件地址
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_1.jpg"; // 被对比的图像文件
        // 将图片文件转换成图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse(s1);
        // 多次进行方框模糊 （图像越大，所需时间与内存就更高）
        long start = System.currentTimeMillis();
        for (int i = 40; i > 0; i--) {
            parse1.boxBlur(false);
        }
        System.out.println("模糊完成，耗时 = " + (System.currentTimeMillis() - start));
        // 输出图片1的图像文件
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse1.boxBlur(false), "C:\\Users\\Liming\\Desktop\\fsdownload\\res1.jpg", 1)
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

* Support the conversion of pixel matrix and shaping matrix, and also support the calculation of pixel matrix according
  to different color modes, which can effectively reduce redundant calculation.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 设置需要被读取的图像文件路径
        String path = "C:\\Users\\zhao\\Downloads\\test.png";
        // 使用Color矩阵解析图片，获取到图像的像素矩阵
        ColorMatrix colors = ColorMatrix.parse(path);
        System.out.print("当前图像的像素色彩模式 = ");
        System.out.println(colors.isGrayscale() ? "灰度" : "彩色");

        /*
         将像素矩阵的色彩模式强制转换
         这里由于转换之前是调用的彩色图像读取，因此转换之后会标记为灰度图像
         需要注意的是这里并不会真正变成灰度图 而是在后面的操作中尽量采用灰度图像的方式处理矩阵数据
        */
        colors.forcedColorChange();
        System.out.print("当前图像的像素色彩模式 = ");
        System.out.println(colors.isGrayscale() ? "灰度" : "彩色");

        // 强制转换像素的矩阵，可以影响到像素矩阵到整形矩阵之间的转换，会按照不同的模式使用不同的逻辑转换像素矩阵
        IntegerMatrix ints = colors.toIntegerMatrix();
        // 获取到图像绘制集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                // 在这里传递需要被绘制的矩阵对象与绘制的新图像路径 以及 图像绘制的像素倍率
                new ImageRenderingMarLauncher<>(ints, "C:\\Users\\zhao\\Downloads\\test1.png", 1)
        );
        // 开始运行
        image.run();
    }
}
```

* The name of the data preprocessing function in the AlgorithmStar portal class has been changed.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
  public static void main(String[] args) {
    // 准备一个向量
    DoubleVector doubleVector = DoubleVector.parse(1, 2, 3, 4, 5);
    // 使用 AlgorithmStar 门户进行数据标准化
    AlgorithmStar<Object, Object> algorithmStar = AlgorithmStar.getInstance();
    // 调用标准化函数
    DoubleVector doubleVector1 = algorithmStar.pretreatment(Z_ScoreNormalization.getInstance("z"), doubleVector);
    // 打印结果数据
    System.out.println(doubleVector1);
    // 取消 AlgorithmStar 门户的使用
    AlgorithmStar.clear();
  }
}
```

### Version update date : xx xx-xx-xx