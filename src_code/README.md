# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.17 - x.xx
* Add implementation support for the diff function to the image matrix, and add powerful subtractive aggregation
  calculation functions to the matrix object.

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

* An aggregation implementation logic has been added to the image matrix, where the absolute value of the difference is
  returned after the difference is made.

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

* When fixing the problem of copying two-dimensional arrays, only deep copies were made at the array level (
  one-dimensional arrays in two-dimensional arrays did not undergo deep copies). This has now been redesigned to make
  the deep copy operation effective again.

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

| Calculating Constants     | effect                  | use                                                                             |
|---------------------------|-------------------------|---------------------------------------------------------------------------------|
| CALCULATE_GRADIENT_RL     | mid = right - left      | Calculate the gradient of the image on the left and right.                      |
| CALCULATE_GRADIENT_LH     | mid = low - high        | Calculate the gradient of the image up and down.                                |
| CALCULATE_GRADIENT_RL_ABS | mid = abs(right - left) | Calculate the absolute gradient value of the image at the left and right sides. |
| CALCULATE_GRADIENT_LH_ABS | mid = abs(low - high)   | Calculate the absolute gradient value of the image at the top and bottom.       |

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

* The DF object's select * is supported. Using * as a query placeholder will not perform any operations, but will
  directly return data.

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

* In a color value quadrature calculation scheme, the processing logic is to take the current value of% 256 as the current color value if the value exceeds the range.

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
### Version update date : xx xx-xx-xx