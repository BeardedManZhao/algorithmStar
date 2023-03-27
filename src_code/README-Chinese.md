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

* 修正二维数组拷贝时，只在在数组层深拷贝问题（二维度数组中的一维数组没有进行深拷贝）现在已经重新设计，使得深拷贝操作开始重新生效。

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