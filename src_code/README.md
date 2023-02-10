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

* 新增Color矩阵对象，其专对于矩阵绘图而设计，在绘图的时候有良好表现。

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
        // 反转矩阵中的所有颜色
        parse = parse.colorReversal();
        // 再一次打印矩阵
        System.out.println(parse);
    }
}
```

* 您可以使用Color矩阵对象进行图像的绘制，代码如下所示。

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
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一个图片转换成为像素矩阵
        ColorMatrix parse = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg");
        System.out.println(parse);
        // 也可以将图片转换为整形矩阵
        IntegerMatrix integerMatrix = IntegerMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.jpg", false);
        System.out.println(integerMatrix);
        // 将图片输出 首先获取到绘图集成器
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\Liming\\Desktop\\fsdownload\\test3.jpg")
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

### Version update date : xx xx-xx-xx