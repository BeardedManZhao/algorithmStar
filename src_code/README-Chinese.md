# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.20 - 1.22
* 修复1.20以及之前版本中的scala插件编译器依赖问题，在1.20以及1.20之前的版本中，scala类没有被正常包含，1.21以及此版本之后已修复。
* 针对稀疏矩阵对象的创建函数实现进行了重写，使得其参数中的坐标能够不受限制，在正整数范围内将可以随意的设置区间的数值。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {

    public static void main(String[] args) {

        // 创建出矩阵对象
        DoubleMatrix matrix1 = DoubleMatrix.sparse(
                // 这里的每一个数组代表的就是一个元素 后面的两个元素代表的是坐标
                new double[]{1, 0, 0},
                new double[]{2, 1, 1},
                new double[]{3, 5, 2}
        );
        IntegerMatrix matrix2 = IntegerMatrix.sparse(
                new int[]{10, 0, 0},
                new int[]{20, 1, 2},
                new int[]{30, 3, 2}
        );
        // 打印出矩阵
        System.out.println(matrix1);
        System.out.println(matrix2);
    }

}
```

* 整形与浮点向量对象都支持随机生成向量的方式。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.Random;

public class MAIN1 {
    public static void main(String[] args) {
        Random random = new Random();
        // 随机创建 10 个元素的整形向量
        IntegerVector random1 = IntegerVector.random(10, random);
        DoubleVector random2 = DoubleVector.random(10, random);
        // 打印出其中的数据
        System.out.println(random1);
        System.out.println(random2);
    }
}
```

* 修复浮点矩阵数据类型的子矩阵提取操作，其在旧版本中的x轴为0时会发生提取异常的情况，1.21版本中该问题被修复。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 随机生成 9 行 5 列 的矩阵
        DoubleMatrix random = DoubleMatrix.random(5, 9, 22);
        System.out.println(random);
        // 提取出其中从 (0, 1) 到 (4, 5) 的子矩阵对象
        DoubleMatrix mat = random.extractMat(0, 1, 4, 5);
        System.out.println(mat);
    }
}
```

* 针对矩阵空间对象中的内积与乘积操作进行了实现，其在1.21版本以及之后的版本都进行了实现与支持。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到矩阵对象
        final DoubleMatrix matrix1 = DoubleMatrix.parse(
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 10, 11, 12}
        );
        final DoubleMatrix matrix2 = DoubleMatrix.parse(matrix1.toArrays().clone());
        // 将矩阵对象叠加三次 封装成为矩阵空间对象
        final DoubleMatrixSpace space1 = DoubleMatrixSpace.parse(matrix1, matrix1, matrix1);
        final DoubleMatrixSpace space2 = DoubleMatrixSpace.parse(matrix2, matrix2, matrix2);
        // 计算内积
        final Double aDouble = space1.innerProduct(space2);
        // 计算外积
        final DoubleMatrixSpace multiply = space1.multiply(space2);
        System.out.println(aDouble);
        System.out.println(multiply);
    }
}
```

* 新增 Image Matrix 数据对象，其代表的是一个图像的矩阵，是Color Matrix 的子类实现，此类具有更多的图像矩阵的计算操作以及更加优秀的性能。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ImageMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 使用 ImageMatrix 对象读取一张图
        ImageMatrix parse1 = ImageMatrix.parseGrayscale(url);
        parse1.show("image");
        // 将此图像矩阵进行重设 TODO 在该类中独有的操作
        ImageMatrix colors = parse1.reSize(100, 100);
        // 查看图像
        colors.show("缩放之后的图像");
    }
}
```

* 针对图像矩阵类的对象，其能够单独修改指定坐标的像素，使用Color对象。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ImageMatrix;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 使用 ImageMatrix 对象读取一张图
        ImageMatrix colors = ImageMatrix.parseGrayscale(url);
        // 将缩放之后的图像中 (0, 15) .... (100, 150) 坐标处的像素更改为 粉色
        for (int y = 0; y <= 100; y++) {
            for (int x = 1; x <= 150; x++) {
                // TODO 在这里调用了 set 函数修改像素
                colors.set(x, y, Color.MAGENTA);
            }
        }
        // 保存图像
        colors.save("D:\\liming\\Project\\My_Book\\项目笔记\\TensorFlow\\res.jpg");
    }
}
```

* Image 矩阵对象支持进行矩阵对象到 Image 类的转换操作，使得其能够更加轻松实现转换操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ImageMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 使用 ImageMatrix 对象读取一张图
        ImageMatrix colors = ImageMatrix.parseGrayscale(url);
        // 将缩放之后的 Image 对象直接提取出来
        Image image = colors.toImage();
        // 将缩放之后的 Image 对象拷贝提取出来
        BufferedImage bufferedImage = colors.copyToNewImage();
    }
}
```

* 针对图像在内存中占用过多的情况，我们可以使用 HashColorMatrix 对象来进行创建，此类能够实现相同像素点的存储与复用，降低了内存的占用。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.HashColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 在获取图像之前打印下缓冲区中的像素数量
        System.out.println("加载图像之前的缓冲区像素数量 = " + HashColorMatrix.getHashColorLength());
        // 使用 ImageMatrix 对象读取一张图
        ColorMatrix colors = HashColorMatrix.parse(url);
        // 打印图中的像素数量 以及 展示图
        System.out.println("图像中包含的所有像素数量 = " + colors.getNumberOfDimensions());
        colors.show("win");
        // 在获取图像之后打印下缓冲区中的像素数量 这个数量就是加载一张图像使用到的像素数量
        // 缓冲区中的元素将会被其它的图复用。
        System.out.println("加载图像之后的缓冲区像素数量 = " + HashColorMatrix.getHashColorLength());
    }
}
```

* 针对 AS 库中的 DataFrame 对象 的列数据更新操作，支持使用字符串指定列名称，更加方便。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.table.SingletonSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个 DF 对象
        FDataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name", "age"), 1
        );
        // 插入多行数据
        dataFrame
                .insert("1", "zhao", "19")
                .insert("2", "tang", "20");
        // 查询出 zhao 与 tang 对应的行
        System.out.println(dataFrame.selectRow("zhao", "tang"));
        // 将当前行的 age 列 + 1 并查看更新后的 DF 表
        dataFrame.updateCol(
                // TODO 针对列数据的更新操作，支持字符串指定列名称了
                "age",
                v -> SingletonCell.$(v.getIntValue() + 1)
        ).show();
    }
}
```

* 针对 DF 对象的查看操作，提供了来源的显示，其在显示出来数据内容之后，不仅仅会显示表，还会显示出表数据的来源。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonSeries;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MAIN1 {
    public static void main(String[] args) throws IOException {
        // 创建一个 DF 对象
        FDataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name", "age"), 1
        );
        // 插入多行数据
        dataFrame
                .insert("1", "zhao", "19")
                .insert("2", "tang", "20");
        // 使用 toString 打印出 df 对象的数值
        System.out.print(dataFrame);
        // 使用 show 函数打印出 df 对象的数值
        dataFrame.show();
        // 使用数据缓冲区对象打印出 df 对象的数值
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        dataFrame.show(bufferedWriter);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
```

* 针对没有任何数据的 DF 数据对象，其desc在新版本中是可以正常使用的，针对不存在的数据将会使用'---'进行填充。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个 DF 对象
        DataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name", "age"), 1
        );
        dataFrame.desc().show();
    }
}
```

* 针对图像的卷积操作，其中需要的卷积核在 AS 库中有了内置的实现，卷积操作能够更加简洁方便。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.HashColorMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.Kernel;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 准备被读取图像的 URL
        final URL url = new URL("https://img-blog.csdnimg.cn/img_convert/e4d7330af33b768ccfad3fe821042a6a.png");
        // 获取到图像
        final ColorMatrix parse = HashColorMatrix.parse(url);
        // 转换成为矩阵空间
        final IntegerMatrixSpace integerMatrixSpace = parse.toIntRGBSpace(
                ColorMatrix._R_, ColorMatrix._G_, ColorMatrix._B_
        );
        // 开始进行 3x3 的均值卷积 并查看卷积结果
        integerMatrixSpace.foldingAndSumRGB(3, 3, Kernel.AVG).show("avg");
        // 开始进行 3x3 的 SobelX 卷积 并查看卷积结果
        integerMatrixSpace.foldingAndSumRGB(3, 3, Kernel.SobelX).show("SobelX");
    }
}
```

### Version update date : xx xx-xx-xx