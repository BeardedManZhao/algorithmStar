# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.16 - x.xx
* Change the name of the integrator to Integrator.
* It provides the calculation support of convolution function, which can enlarge the features and reduce the number of
  elements in the image matrix.

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

* The image matrix supports displaying images on the screen directly through the show function.

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

* Optimize the algorithm of maximum random scrambling times in the random scrambling function shuffle, so that it will
  not have out-of-bounds exceptions.
* Optimize the function logic of random access to data in the matrix through column names, and use hash to map
  addresses.

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

* It supports the call operation of the quick save function of the image, making the saving of the image file easier.

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

* The "SaveMatrix" interface is added. All matrices that implement this interface can be saved in structured or
  unstructured way.

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

* A data analysis library is added to the AS library, which uses SQL styles to process data. Through this library, data
  in the database can be easily obtained into memory for processing.

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

* Use FDataFrame to load the data in the database and file.

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

* Limit FDF by line name

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

* Support the operations of aliasing columns and adding data rows.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;

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
        // 按照性别分组，计算出男女生人数
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
        );
    }
}
```

* Capable of functional update of row or column data for all values in DF data set objects, and supports conversion from
  DF data objects to matrices.

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

* Support for obtaining image related data through a web URL object and converting it into an image matrix.

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

* It supports color binarization and regularization coverage, and can display more image features or remove more
  redundant features by specifying the color values of the channel.

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

* Supports the construction of image ASCII images. You can quickly construct an image in ASCII, but please ensure that
  the image size is small enough.

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

* Ability to manually create an empty DataFrame object and independently manipulate the data therein.

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

* Support for local binarization operations, which can effectively achieve image binarization processing. Compared to
  global binarization, functions are more flexible.

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

* DF object data supports saving as HTML files, enabling effective automated page rendering operations.

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
### Version update date : xx xx-xx-xx