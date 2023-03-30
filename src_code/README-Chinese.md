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

### Version update date : xx xx-xx-xx