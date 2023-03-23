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
        // 解析URL获取到图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse(url);
        // 解析URL获取到图像的灰度矩阵
        ColorMatrix parse2 = ColorMatrix.parseGrayscale(url);
        // 查看图像
        parse1.show("image");
        parse2.show("image");
    }
}
```

* 支持进行色彩二值化规整覆盖，能够通过指定通道的色彩数值，显示出更多的图像特征，或去除更多的冗余特征。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 准备图像的URL对象
        URL url = new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png");
        // 解析URL获取到图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse(url);
        // 将 URL 图像矩阵中所有 G 通道颜色数值大于 40 的颜色变更为黑色，反之变更为白色
        // 在这里由于选择了 G 通道 因此 绿色越深 越有可能变为白色
        parse1.globalBinary(ColorMatrix._G_, 40, 0, 0xffffff);
        // 也可以使用其它颜色通道进行色彩的调整
        parse1.globalBinary(ColorMatrix._R_, 40, 0, 0xffffff);
        parse1.globalBinary(ColorMatrix._B_, 40, 0, 0xffffff);
        // 查看结果图像
        parse1.show("image");
    }
}
```

* 支持进行图像 ASCII 图的构造，您可以快速的将一个图片的 ASCII 构造出来，不过请您确保图像尺寸足够小。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 准备图像的URL对象
        URL url = new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png");
        // 解析URL获取到图像矩阵
        ColorMatrix parse1 = ColorMatrix.parse(url);
        // 查看结果图像
        parse1.show("image");
        // 输出图像的 ASCII 数值，输出规则为  G 通道颜色数值 大于 40 的 输出符号 'A' 其它输出符号 ' '
        parse1.save(
                new File("C:\\Users\\zhao\\Desktop\\fsdownload\\res.txt"),
                ColorMatrix._G_, 40, 'A', ' '
        );
    }
}
```

* 能够手动创建一个空的 DataFrame 对象，并自主操作其中的数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空 DF 对象 以 name 列作为行主键索引
        FDataFrame create = FDataFrame.select(FieldCell.parse("name", "sex", "phoneNum"), 1);
        // 插入一些数据
        DataFrame insert = create.insert(
                FinalSeries.parse("zhao", "M", "110xxxxxxxx"),
                FinalSeries.parse("tang", "W", "110xxxxxxxx"),
                FinalSeries.parse("yang", "M", "110xxxxxxxx")
        );
        // 查看数据集
        System.out.println(insert);
        // 将其中的 name 列 sex 列 查询
        System.out.println(
                insert.select(
                        FieldCell.$("name").as("AllName"),
                        FieldCell.$("sex").as("AllSex")
                )
        );
    }
}
```

* 支持在 group 的时候指定 where子句，使得计算效率大大增强

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalSeries;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空 DF 对象 以 name 列作为行主键索引
        FDataFrame create = FDataFrame.select(FieldCell.parse("name", "sex", "phoneNum"), 1);
        // 插入一些数据
        DataFrame insert = create.insert(
                FinalSeries.parse("zhao1", "M", "110xxxxxxxx"),
                FinalSeries.parse("tang2", "W", "120xxxxxxxx"),
                FinalSeries.parse("yang3", "W", "110xxxxxxxx"),
                FinalSeries.parse("zhao4", "M", "120xxxxxxxx"),
                FinalSeries.parse("tang5", "W", "110xxxxxxxx"),
                FinalSeries.parse("yang6", "W", "110xxxxxxxx"),
                FinalSeries.parse("zhao7", "M", "120xxxxxxxx"),
                FinalSeries.parse("tang8", "W", "110xxxxxxxx"),
                FinalSeries.parse("yang9", "W", "110xxxxxxxx")
        );
        // 查看数据集
        System.out.println(insert);
        // 将其中手机号前三位不为 120 的数据行按照其中的 sex 分组 在这里直接使用分组时过滤即可
        System.out.println(
                insert.groupBy("sex", v -> {
                    // 获取到手机号的字符串
                    String s = v.getCell(2).toString();
                    // 判断前 3 个字符是否为 120 （是否以 120 开头） 如果是就不添加
                    return !s.startsWith("120");
                }).count()
        );
    }
}
```

* 支持局部二值化操作，能够有效的实现图像的二值化处理，相较于全局二值化，函数更加灵活。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到图像矩阵
        ColorMatrix parse = ColorMatrix.parse(new URL("https://img-blog.csdnimg.cn/img_convert/5765bdab08ef6e117d434e7e225b9013.png"));
        parse.show("image");
        System.out.println("ok!!!");
        System.out.println("长 = " + parse.getRowCount());
        System.out.println("宽 = " + parse.getColCount());
        // 将图像进行局部二值化
        parse.localBinary(
                // 指定本次二值化选择的颜色通道
                ColorMatrix._G_,
                // 指定本次二值化选出的局部图像矩阵数量
                100,
                // 指定本次二值化中局部矩阵中大于局部阈值的颜色编码
                0xffffff,
                // 指定本次二值化中局部矩阵中小于局部阈值的颜色编码
                0,
                // 指定本次二值化中局部阈值生成后要进行的微调数值，这里是降低20个阈值数值
                -30
        );
        // 查看结果数据
        parse.show("image");
    }
}
```

* DF对象数据支持保存为 HTML 文件，能够实现有效的自动化页面绘制操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空 DF 对象 以 name 列作为行主键索引
        FDataFrame create = FDataFrame.select(FieldCell.parse("name", "sex", "phoneNum", "salary"), 1);
        // 插入一些数据
        DataFrame insert = create.insert(
                FinalSeries.parse("zhao1", "M", "110xxxxxxxx", "30000"),
                FinalSeries.parse("tang2", "W", "120xxxxxxxx", "30000"),
                FinalSeries.parse("yang3", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("zhao4", "M", "120xxxxxxxx", "30000"),
                FinalSeries.parse("tang5", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("yang6", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("zhao7", "M", "120xxxxxxxx", "30000"),
                FinalSeries.parse("tang8", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("yang9", "W", "110xxxxxxxx", "30000")
        );
        // 查看数据集
        System.out.println(insert.desc());
        // 输出表的HTML
        insert.into_outHtml("C:\\Users\\Liming\\Desktop\\fsdownload\\res11234.html", "myTable");
    }
}
```

* 图像开始支持 add 函数，在add函数的操作下，可以实现图像函数的重叠合并！

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一些图像文件转换成为一个图像矩阵对象
        ColorMatrix colorMatrix1 = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test.bmp");
        ColorMatrix colorMatrix2 = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 将 colorMatrix2 + colorMatrix1 的结果图像展示出来
        (colorMatrix1.add(colorMatrix2)).show("image");
    }
}
```

* 支持自定义聚合以及内置数据方案的聚合操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 将一些图像文件转换成为一个图像矩阵对象
        ColorMatrix colorMatrix1 = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test.bmp");
        ColorMatrix colorMatrix2 = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 使用 agg 函数以及内置的计算方案进行两个矩阵的合并
        // 下面函数中的第二个形参代表的就是矩阵中元素的聚合逻辑，这里使用的是求和，并对越界颜色数值进行规整的逻辑实现
        colorMatrix1.agg(colorMatrix2, ColorMatrix.COLOR_SUM_REGULATE).show("image");
    }
}
```

* 图像矩阵支持腐蚀运算函数，在该函数中可以去除掉冗余特征数据。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.sql.SQLException;


public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 将一些图像文件转换成为一个图像矩阵对象
        ColorMatrix colorMatrix1 = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test2.bmp");
        // 对图像进行二值化
        colorMatrix1.globalBinary(ColorMatrix._G_, 100 , 0xffffff, 0);
        colorMatrix1.show("腐蚀之前的 image");
        // 开始对图像矩阵进行腐蚀操作
        colorMatrix1.erode(2, 2, false).show("腐蚀之后的 image");
    }
}
```

### Version update date : xx xx-xx-xx