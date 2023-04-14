# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.18 - 1.19
* 图像矩阵的变换计算函数实现新增缩放逻辑，能够按照指定的倍数进行单方向的缩放操作，下面是一个将原图像缩小为原来的 1/2 的代码示例。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到用于测试的图像
        ColorMatrix colorMatrix = AlgorithmStar.parseImage(
                new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png")
        );
        // 将图像进行备份
        ColorMatrix parse = ColorMatrix.parse(colorMatrix.copyToNewArrays());
        // 准备缩放需要的配置文件，开始进行缩放
        HashMap<String, Cell<?>> map = new HashMap<>();
        // 在这里指定缩放的倍数为2
        map.put("times", new FinalCell<>(2));
        // 将备份的图像进行横向缩放
        ColorMatrix converter1 = parse.converter(ColorMatrix.SCALE_LR, map);
        converter1.show("image1");
        // 再一次进行纵向缩放
        ColorMatrix converter2 = converter1.converter(ColorMatrix.SCALE_BT, map);
        // 开始对比原图像与新图像的差距
        colorMatrix.show("src");
        converter2.show("res");
    }
}
```

* 新增SFDataFrame对象，在该对象中，所有的单元格在内存中都是唯一的，当 DataFrame 中有大量相同数据的情况下，SFDataFrame能够为内存节省大量空间，其使用方式与 FDataFrame完全一致。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.*;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到数据输入组件
        InputComponent inputComponent = InputByStream.builder()
                .addInputArg(InputByStreamBuilder.INPUT_STREAM, SingletonCell.$(System.in))
                .addInputArg(InputByStreamBuilder.CHARSET, SingletonCell.$("utf-8"))
                .addInputArg(InputByStreamBuilder.PK, SingletonCell.$(1))
                .addInputArg(InputByStreamBuilder.ROW_LEN, SingletonCell.$(3))
                .addInputArg(InputByStreamBuilder.SEP, SingletonCell.$(','))
                .create();
        // 开始通过数据输入组件获取到 DF 对象 输入数据如下所示 其中的性别列有两个数据是一样的
        /*
            id,name,sex,age
            1,zhao,M,19
            2,tang,W,21
            3,yang,W,18
        */
        // 提取到最后两行数据 从索引1开始提取2行。
        DataFrame dataFrame = SFDataFrame.builder(inputComponent).limit(1, 2);
        // 打印其中的数据
        dataFrame.show();
        // 提取出 性别列 中的数据 并判断其中的两个 W 是否属于同一个内存
        Series sex = dataFrame.select(FieldCell.$("sex"));
        System.out.println(sex.getCell(0) == sex.getCell(1));
    }
}
```

* DataFrame 对象的insert函数支持字符串类型数据的直接新增操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.Series;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) {

        // 提取到最后两行数据 从索引1开始提取2行。
        DataFrame dataFrame = SFDataFrame
                .select(FieldCell.parse("id", "name", "sex", "age"), 1)
                .insert("1", "zhao", "M", "19")
                .insert("2", "tang", "W", "20")
                .insert("3", "yang", "W", "18");
        // 提取出 性别列 中的数据 并判断其中的两个 W 是否属于同一个内存
        Series sex = dataFrame.select(FieldCell.$("sex"));
        System.out.println(sex);
        System.out.println(sex.getCell(1) == sex.getCell(2));
    }
}
```

* 针对仅有一个行或列字段数据的查询，将直接返回一个 Series 对象，以便于进行更多针对序列对象的操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个 DF 数据对象
        DataFrame dataFrame = FDataFrame.select(
                        FieldCell.parse("id", "name", "age", "sex"), 1
                )
                // 向其中添加一些数据
                .insert(FinalSeries.parse("1", "zhao", "19", "M"))
                .insert(FinalSeries.parse("2", "yang", "18", "W"))
                .insert(FinalSeries.parse("3", "tang", "21", "W"))
                // 改别名
                .select(
                        FieldCell.$("name").as("名字"),
                        FieldCell.$("sex").as("性别")
                );
        // 查询出列 name 的数据
        Series name = dataFrame.select(FieldCell.$("名字"));
        System.out.println(name);
        // 查询出 主键字段数值为 zhao 的行
        Series row = dataFrame.selectRow("zhao");
        System.out.println(row);
    }
}
```

* 支持通过 AS库 的门户类进行静态的 SF DF 对象的获取。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        /* *****************************************************
         * TODO 从数据流中读取到数据
         * *****************************************************/
        // 开始将所有的参数配置到设备对象中，构建出数据输入设备
        InputComponent inputComponent = InputByStream.builder()
                .addInputArg(InputByStreamBuilder.INPUT_STREAM, new FinalCell<>(System.in))
                .addInputArg(InputByStreamBuilder.CHARSET, new FinalCell<>("utf-8"))
                .addInputArg(InputByStreamBuilder.SEP, new FinalCell<>(','))
                .addInputArg(InputByStreamBuilder.PK, new FinalCell<>(1))
                .addInputArg(InputByStreamBuilder.ROW_LEN, new FinalCell<>(3))
                .create();

        // TODO 开始通过 algorithmStar 构建出DataFrame对象 这里是通过文件 数据库 数据输入组件 来进行构建
        File file = new File("C:\\Users\\zhao\\Downloads\\test.csv");
        DataFrame dataFrame1 = AlgorithmStar.parseSDF(file).setSep(',')
                .create("year", "month", "day", "week", "temp_2", "temp_1", "average", "actual", "friend")
                .execute();
        dataFrame1.show();

        DataFrame dataFrame2 = AlgorithmStar.parseSDF(inputComponent, false);
        dataFrame2.show();

        Connection connection = DriverManager.getConnection("");
        DataFrame dataFrame3 = AlgorithmStar.parseSDF(connection)
                .from("xxx")
                .execute();
        dataFrame3.show();
    }
}
```

### Version update date : xx xx-xx-xx