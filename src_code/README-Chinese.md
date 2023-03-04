# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.15 - 1.16
* 将集成器的名称修改为“Integrator”。
* 提供卷积函数的计算支持，能够通过卷积函数将特征放大同时缩小图像矩阵的元素数量。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.integrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

public class MAIN1 {
    public static void main(String[] args) {
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_1.jpg";
        IntegerMatrix integerMatrix;
        {
            // 设置权重
            IntegerMatrix weight = IntegerMatrix.parse(
                    new int[]{0, -1},
                    new int[]{-1, 2},
                    new int[]{0, 0}
            );
            // 读取图像并获取到三通道矩阵空间
            IntegerMatrixSpace parse = IntegerMatrixSpace.parse(s1);
            // 对图像进行卷积，获取三个色彩通道的矩阵空间的和
            integerMatrix = parse.foldingAndSum(2, 3, IntegerMatrixSpace.parse(weight, weight, weight));
        }
        // 输出图片1的卷积图像文件
        ImageRenderingIntegrator image = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(integerMatrix, "C:\\Users\\Liming\\Desktop\\fsdownload\\res12.jpg", 1)
        );
        if (image.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

* 图像矩阵支持直接通过 show 函数在屏幕中显示出图像。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) throws InterruptedException {
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_1.jpg";
        ColorMatrix parse = ColorMatrix.parse(s1);
        parse.show("image");
        Thread.sleep(1024);
        parse.colorReversal(false);
        parse.show("image");
    }
}
```

* 优化随机打乱函数 shuffle 中的最大随机打乱次数算法，使得其不会出现越界异常。
* 优化通过列名随机访问矩阵中数据的函数逻辑，采用hash进行地址的映射。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;

import java.util.Arrays;
import java.util.Random;

public class MAIN1 {
    public static void main(String[] args) {
        // 构建一份矩阵数据
        double[][] data = new double[][]{
                new double[]{10, 11, 14, 10, 100},
                new double[]{11, 11, 14, 10, 100},
                new double[]{25, 20, 28, 20, 100},
                new double[]{26, 20, 28, 20, 100}
        };
        // 将矩阵数据使用指定列与行名称的方式创建出来
        ColumnDoubleMatrix columnDoubleMatrix = ColumnDoubleMatrix.parse(
                new String[]{"col1", "col2", "col3", "col4", "col5"},
                new String[]{"row1", "row2", "row3", "row4"},
                data
        );
        // 使用不创建新矩阵的方式打乱其中的数据顺序 且最多打乱 10 次
        columnDoubleMatrix.shuffle(new Random(), false, 10);
        // 打印出矩阵数据
        System.out.println(columnDoubleMatrix);
        // 打印出矩阵中的 col2 列 在新版中该函数采取哈希实现 速度提升很明显
        System.out.println(Arrays.toString(columnDoubleMatrix.getArrayByColName("col2")));
        // 打印出矩阵中的 row2 行 在新版中该函数采取哈希实现 速度提升很明显
        System.out.println(Arrays.toString(columnDoubleMatrix.getArrayByRowName("row2")));
    }
}
```

* 支持图像的快捷保存函数的调用操作，使得图像文件的保存变得更简单。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        String s1 = "C:\\Users\\zhao\\Desktop\\fsdownload\\微信图片_2.jpg";
        // 获取到图像矩阵对象
        ColorMatrix parse = ColorMatrix.parse(s1);
        // 将图像在原图像的基础上进行颜色反转操作
        parse.colorReversal(false);
        // 查看颜色反转之后的图像
        parse.show("image1");
        // 输出反转之后的图像
        parse.save("C:\\Users\\zhao\\Desktop\\fsdownload\\res123.jpg");
    }
}
```

### Version update date : xx xx-xx-xx