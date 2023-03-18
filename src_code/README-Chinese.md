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

* 增加了 "SaveMatrix" 接口，实现了该接口的一切矩阵都可以使用结构化或非结构化的保存。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_2.jpg";
        // 获取到图像矩阵对象
        ColorMatrix parse = ColorMatrix.parse(s1);
        // 输出图像RGB文本数据
        parse.save("C:\\Users\\Liming\\Desktop\\fsdownload\\res1234.csv", ',');
    }
}
```

* AS库中增加了数据分析库，使用SQL风格处理数据，通过该库可以方便的将数据库中的数据获取到内存中进行处理。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 准备数据库连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.101:38243/tq_SCHOOL", "liming", "liming7887");
        // 将数据库数据对象加载成 FDF
        DataFrame execute = FDataFrame.builder(connection) // 指定数据库连接
                .create("*") // 指定查询字段
                .from("STU_FIVE") // 指定查询表
                .primaryKey(0) // 指定内存AS表的主键（AS会自动建立行索引）数据库查询需要使用索引指定哦！
                .execute();// 开始查询
        // 打印出 FDF 中的数据
        System.out.println(execute);

        // 开始查询 FDF
        // 正序打印出 FDF 中 所有name长度为3的人员中男女生的人数 打印出前 3 行数据
        DataFrame select = execute
                .select("name", "sex") // 查询其中的 name sex 列
                .where(v -> v.getCell(0).getStringValue().length() == 3) // 获取到其中的名字长度为 3 的数据行
                .groupBy("sex") // 按照 sex 列分组
                .count() // 将每一组进行统计
                .sort("count()") // 按照统计结果进行正序排序
                .limit(3); // 获取最多前3行数据
        System.out.println(select.into_outfile("C:\\Users\\zhao\\Desktop\\fsdownload\\res1.csv"));
        // 打印存储 FDF 中的数据行数
        System.out.println("数据行数 = " + execute.count());
        // 打印出其中的信息
        System.out.println(execute.desc());
        // 获取到 赵凌宇 的信息 由于 name 列是主键，因此可以直接通过这里获取到数据
        System.out.println(execute.selectRow("赵凌宇"));
    }
}
```

* 使用 FDataFrame 加载数据库与文件中的数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 准备数据库连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.101:38243/tq_SCHOOL", "liming", "liming7887");
        // 将数据库数据对象加载成 FDF
        DataFrame execute = FDataFrame.builder(connection) // 指定数据库连接
                .create("*") // 指定查询字段
                .from("STU_FIVE") // 指定查询表
                .where(" name = '赵凌宇' ") // 指定查询条件
                .primaryKey(0) // 指定内存AS表的主键（AS会自动建立行索引）数据库查询需要使用索引指定哦！
                .execute();// 开始查询
        // 打印出 FDF 中的数据
        System.out.println(execute);
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备文件对象
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\res1.csv");
        // 使用 FDF 加载文件
        DataFrame execute1 = FDataFrame.builder(file)
                // 文件对象的读取需要指定文本分隔符
                .setSep(',')
                // 文件对象需要指定好列名称，不能使用 * 这里代表的不是查询，而是创建一个DF的列字段
                .create("id", "name", "sex")
                // 文件对象需要使用lambda表达式进行数据的过滤
                .where(v -> Objects.equals(v.getCell(1).getStringValue(), "赵凌宇"))
                // 文件对象的主键指定允许使用列名称
                .primaryKey("name")
                // 执行查询
                .execute();
        // 打印出结果数据
        System.out.println(execute1);
    }
}
  ```

* 对FDF按照行名称进行limit操作

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;

import java.io.File;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备文件对象
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\res1.csv");
        // 使用 FDF 加载文件
        DataFrame execute1 = FDataFrame.builder(file)
                // 文件对象的读取需要指定文本分隔符
                .setSep(',')
                // 文件对象需要指定好列名称，不能使用 * 这里代表的不是查询，而是创建一个DF的列字段
                .create("id", "name", "sex")
                // 文件对象的主键指定允许使用列名称
                .primaryKey("name")
                // 执行查询
                .execute();
        // 打印出结果数据 这里打印出从 赵 到 贾 之间的数据行
        System.out.println(execute1.limit("赵", "贾"));
    }
}
```

* 支持为列取别名以及新增数据行的操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalSeries;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备文件对象
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\res1.csv");
        // 使用 FDF 加载文件
        DataFrame execute1 = FDataFrame.builder(file)
                // 文件对象的读取需要指定文本分隔符
                .setSep(',')
                // 文件对象需要指定好列名称，不能使用 * 这里代表的不是查询，而是创建一个DF的列字段
                .create("id", "name", "sex")
                // 文件对象的主键指定允许使用列名称
                .primaryKey("name")
                // 执行查询
                .execute();
        // 按照性别分组，计算出男女生人数
        long start = System.currentTimeMillis();
        System.out.println(
                execute1
                        // 指定查询的列，并起别名
                        .select(FieldCell.$("sex").as("性别"))
                        // 按照 sex 分组
                        .groupBy("性别")
                        // 进行 组内的计数
                        .count()
                        // 指定查询的列 并起别名
                        .select(
                                FieldCell.$("性别"),
                                FieldCell.$("count()").as("人数")
                        )
                        // 新增一行数据
                        .insert(FinalSeries.parse("新", "10"))
        );
        System.out.print("处理耗时（MS）：");
        System.out.println(System.currentTimeMillis() - start);
    }
}

```

* 能够针对DF数据集对象中的所有数值进行行或列数据的函数式更新，同时支持DF数据对象到矩阵之间的转换。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备文件对象
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\res1.csv");
        // 使用 FDF 加载文件
        DataFrame execute1 = FDataFrame.builder(file)
                // 文件对象的读取需要指定文本分隔符
                .setSep(',')
                // 文件对象需要指定好列名称，不能使用 * 这里代表的不是查询，而是创建一个DF的列字段
                .create("id", "name", "sex")
                // 文件对象的主键指定允许使用列名称
                .primaryKey("name")
                // 执行查询
                .execute()
                // 为列起别名
                .select(
                        FieldCell.$("id"),
                        FieldCell.$("name").as("名称"),
                        FieldCell.$("sex").as("性别")
                )
                // 将性别列进行转换，男生为1 女生为0
                .updateCol(FieldCell.$("性别"), cell -> new FinalCell<>(cell.getStringValue().equals("男") ? 1 : 0))
                // 将行主键数值为ZLY的数据行中的所有单元格替换成为数据 405
                .updateRow("ZLY", cell -> new FinalCell<>(405));

        // 打印出表中的行主键名称为 405 的数据行
        System.out.println(execute1.selectRow("405"));
        long start = System.currentTimeMillis();
        // 将表转换成为一个整形矩阵对象，该操作会将DF对象中的所有数值试图转换成为 col.count()*3 的矩阵对象
        IntegerMatrix parse = IntegerMatrix.parse(execute1, execute1.count().getIntValue(), 3);
        System.out.println(IntegerVector.parse(parse.getArrayByColIndex(2)));
        System.out.print("处理耗时（MS）：");
        System.out.println(System.currentTimeMillis() - start);
    }
}
```

* 支持通过一个网络 URL 对象获取到有关图像的数据，并将其转换成为一个图像矩阵。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 准备图像的URL对象
        URL url = new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png");
        // 解析URL获取到矩阵
        ColorMatrix parse = ColorMatrix.parse(url);
        // 查看图像
        parse.show("image");
    }
}
```

### Version update date : xx xx-xx-xx