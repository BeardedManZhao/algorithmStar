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

### Version update date : xx xx-xx-xx