# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.17 - x.xx
* 为图像矩阵添加diff函数的实现支持，同时为该矩阵对象添加强大的求差聚合计算功能。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取一张图像的像素矩阵
        ColorMatrix parse1 = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 将 parse1 进行二值化
        parse1.localBinary(ColorMatrix._G_, 30, 0, 0xffffff, 1);
        // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
        ColorMatrix parse2 = parse1.erode(3, 3, true, Color.BLACK);
        // 将两个矩阵进行做差运算，并查看做差之后的图像 该函数等同于取余做差
        (parse1.diff(parse2)).show("结果图像1");
        // 将两个矩阵进行 agg 函数的做差运算，这里使用的是 规整做差
        (parse1.agg(parse2, ColorMatrix.COLOR_DIFF_REGULATE)).show("结果图像2");
    }
}
```

* 为图像矩阵添加了一种聚合实现逻辑，差值绝对值，在做差之后返回绝对值。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个图像矩阵
        ColorMatrix parse = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test3.bmp");
        // 为图像矩阵进行腐蚀和膨胀操作
        // 黑色作为背景色腐蚀
        ColorMatrix erode1 = parse.erode(3, 3, true);
        // 白色作为背景色膨胀
        ColorMatrix erode2 = parse.erode(3, 3, true, ColorMatrix.WHITE);
        // 将膨胀的矩阵对象 使用agg函数 减去 腐蚀的矩阵对象
        (erode2.agg(erode1, ColorMatrix.COLOR_DIFF_ABS)).show("image");
    }
}
```

* 修正二维数组拷贝时，只在数组层深拷贝问题（二维度数组中的一维数组没有进行深拷贝）现在已经重新设计，使得深拷贝操作开始重新生效。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取一张图像的像素矩阵
        ColorMatrix parse1 = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        Color[][] colors = parse1.copyToNewArrays();
    }
}
```

* 新增矩阵内运算函数，该函数能够实现矩阵中所有坐标元素的计算操作，下面是所有支持的计算操作，并返回一个计算之后的矩阵对象。

| 计算常量                      | 效果                      | 作用             |
|---------------------------|-------------------------|----------------|
| CALCULATE_GRADIENT_RL     | mid = right - left      | 计算图像在左右的梯度。    |
| CALCULATE_GRADIENT_LH     | mid = low - high        | 计算图像在上下的梯度。    |
| CALCULATE_GRADIENT_RL_ABS | mid = abs(right - left) | 计算图像在左右的梯度绝对值。 |
| CALCULATE_GRADIENT_LH_ABS | mid = abs(low - high)   | 计算图像在上下的梯度绝对值。 |

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix calculate1;
        ColorMatrix calculate2;
        {
            // 创建一个图像矩阵
            ColorMatrix parse = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test3.bmp");
            // 进行左右的梯度计算
            parse.calculate(ColorMatrix.CALCULATE_GRADIENT_RL, true).show("image1");
            // 进行上下的梯度计算
            parse.calculate(ColorMatrix.CALCULATE_GRADIENT_LH, true).show("image2");
            // 进行左右的绝对值梯度计算
            calculate1 = parse.calculate(ColorMatrix.CALCULATE_GRADIENT_RL_ABS, true);
            calculate1.show("image3");
            // 进行上下的绝对值梯度计算
            calculate2 = parse.calculate(ColorMatrix.CALCULATE_GRADIENT_LH_ABS, true);
            calculate2.show("image4");
        }
        // 进行两幅图像的加法合并 并打印新矩阵
        (calculate1.add(calculate2)).show("image");
    }
}
```

* DF 对象的 select * 被支持，使用 * 做查询占位符将不会进行任何运算，而是直接返回数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空的 DataFrame 对象
        FDataFrame dataFrame = FDataFrame.select(
                FieldCell.parse("id", "name", "sex", "age"), 1
        );
        // 手动插入数据
        dataFrame.insert(
                FinalSeries.parse("1", "zhao", "M", "19"),
                FinalSeries.parse("2", "tang", "W", "18"),
                FinalSeries.parse("3", "yang", "W", "20"),
                FinalSeries.parse("4", "shen", "W", "19")
        );
        // 打印出 select * 的数据
        System.out.println(
                dataFrame.select("*")
        );
    }
}
```

* 图像矩阵 agg 函数，新增矩阵坐标相对应坐标的颜色数值相乘的计算逻辑实现。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        Color[] colors = {Color.CYAN, Color.GREEN, Color.PINK, Color.red};
        // 手动创建图像矩阵
        ColorMatrix parse = ColorMatrix.parse(
                colors, colors, colors, colors
        );
        // 查看图像矩阵
        parse.show("image", 100, 100);
        // 将图像中的所有像素与自身矩阵进行规整乘法计算
        ColorMatrix agg1 = parse.agg(parse, ColorMatrix.COLOR_MULTIPLY_REGULATE);
        // 将图像中的所有像素与自身矩阵进行取余乘法计算
        ColorMatrix agg2 = parse.agg(parse, ColorMatrix.COLOR_MULTIPLY_REMAINDER);
        // 查看两个图像数据
        agg1.show("image1", 100, 100);
        agg2.show("image2", 100, 100);
    }
}
```

* 支持图像轮廓的面积计算，在计算的时候以像素作为计算单位，轮廓颜色支持自定义。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix resImage1;
        {
            // 获取一张图像的像素矩阵
            ColorMatrix colors = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
            // 将图像拷贝一份出来
            ColorMatrix parse1 = ColorMatrix.parse(colors.copyToNewArrays());
            // 将 parse1 进行二值化 // 请注意阈值
            parse1.globalBinary(ColorMatrix._G_, 60, 0, 0xffffff);
            // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
            ColorMatrix parse2 = parse1.erode(2, 2, true, Color.BLACK);
            // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
            parse1.erode(2, 2, false, Color.WHITE);
            // 查看临时结果
            parse1.show("image1");
            parse2.show("image2");
            // 将两个矩阵进行做差运算，并查看做差之后的图像
            resImage1 = parse1.diff(parse2);
            resImage1.show("轮廓图像");
        }
        // 查看结果数据
        resImage1.show("绘制之后的结果图像");
        // 开始提取白色轮廓线内的面积
        System.out.print("轮廓内面积 = ");
        System.out.println(resImage1.contourArea(ColorMatrix.WHITE));
    }
}
```

* 支持轮廓外接矩形的操作，在该函数的帮助下，图像中的所有轮廓将被统一的圈中，成为一个大矩阵。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.RectangleMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix parse1;
        {
            // 获取一张图像的像素矩阵
            ColorMatrix colors = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3.bmp");
            // 将图像拷贝一份出来
            parse1 = ColorMatrix.parse(colors.copyToNewArrays());
            // 将结果二值化
            parse1.globalBinary(ColorMatrix._G_, 150, 0xffffff, 0);
        }
        // 将轮廓线转化成矩形轮廓
        ColorMatrix parse = RectangleMatrix.parse(parse1, Color.GREEN);
        parse.show("二值化后的图像");
        System.out.print(parse);
        // 将矩形轮廓添加到原矩阵 这里的规则是，如果颜色数值为 0xff000000 就不合并
        ColorMatrix res = parse1.agg(
                // 需要被进行添加的矩阵对象
                parse,
                // 添加的逻辑 这里使用的是将非黑色的颜色像素直接覆盖 因为除了黑色以外的像素都是轮廓所需
                (inputType1, inputType2) -> inputType2.getRGB() == 0xff000000 ? inputType1 : inputType2
        );
        res.show("结果");
    }
}
```

* 支持矩阵相似函数的计算，在这里您可以按照如下的示例进行矩形相似度的计算。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.StandardizedEuclideanDistance;
import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到三个图像矩阵的颜色RGB数值
        IntegerMatrix parse1, parse2, parse3;
        {
            parse1 = IntegerMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3.bmp");
            parse2 = IntegerMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test32.bmp");
            parse3 = IntegerMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test33.bmp");
        }
        // 计算出三个图像的相似度系数
        AlgorithmStar<Object, Object> algorithmStar = AlgorithmStar.getInstance();
        System.out.println(algorithmStar.getTrueDistance(ManhattanDistance.getInstance("MAN"), parse1, parse2));
        System.out.println(algorithmStar.getTrueDistance(ManhattanDistance.getInstance("MAN"), parse1, parse3));

        // 将三个图像矩阵的所有数值转换成为double类型，获取到Double矩阵
        DoubleMatrix parse11, parse22, parse33;
        {
            parse11 = DoubleMatrix.parse(parse1);
            parse22 = DoubleMatrix.parse(parse2);
            parse33 = DoubleMatrix.parse(parse3);
        }
        // 计算出三个图像的相似度系数
        System.out.println(algorithmStar.getTrueDistance(ManhattanDistance.getInstance("MAN"), parse11, parse22));
        System.out.println(algorithmStar.getTrueDistance(ManhattanDistance.getInstance("MAN"), parse11, parse33));

        // TODO 值得注意的是 标准化欧几里得暂不支持这类操作的计算 这一块会报错
        double se = algorithmStar.getTrueDistance(StandardizedEuclideanDistance.getInstance2("SE"), parse11, parse22);
        System.out.println(se);
    }
}
```

* 支持图像矩阵中的规则图形（矩形）的绘制，绘制过程将不会产生冗余的计算计算数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1;
        // 将图像与样本读取进来
        colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3.bmp");
        // 将图像矩阵绘制到原矩阵中，并查看结果
        colorMatrix1.drawRectangle(
                // 矩形的左上角坐标
                new IntegerCoordinateTwo(40, 30),
                // 矩形的右下角坐标
                new IntegerCoordinateTwo(140, 130),
                // 矩形边框的颜色对象
                Color.MAGENTA
        );
        colorMatrix1.show("res");
    }
}
```

* 支持图像矩阵的模板匹配操作，其中卷积核大小为模板大小，当模板与图像矩形的宽度一致的时候会使用优化算法。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1, colorMatrix2;
        // 将图像与样本读取进来
        colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3.bmp");
        colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test3YB.bmp");
        // 使用模板匹配 获取到 colorMat1 中 与 colorMat2 最相近的子矩阵
        IntegerCoordinateTwo topLeft = colorMatrix1.templateMatching(
                // 相似度计算组件
                ManhattanDistance.getInstance("MAN"),
                // 模板图像
                colorMatrix2,
                // 需要被计算的颜色通道
                ColorMatrix._G_,
                // 相似度越小 匹配度越大
                false
        );
        System.out.println(topLeft);
    }
}
```

### Version update date : xx xx-xx-xx