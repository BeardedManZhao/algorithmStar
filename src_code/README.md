# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.18 - 1.19
* Fix the startup log header data of the library, correct its header to AlgorithmStar-Java.
* The transformation calculation function of the image matrix implements new scaling logic, which can perform
  unidirectional scaling operations according to specified multiples. Below is a code example that reduces the original
  image to half of its original size.

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

* Add an SFDataFrame object, in which all cells are unique in memory. When there is a large amount of the same data in
  the DataFrame, SFDataFrame can save a lot of space in memory, and its usage is completely consistent with FDataFrame.

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

* For queries with only one row or column field data, a Series object will be directly returned to facilitate more
  operations on sequence objects.

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

* Support the acquisition of static SF DF objects through the portal class of the AS library.

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

* Pure numerical matrix objects support random data generation operations and can effectively achieve data random
  generation.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;


public class MAIN1 {
    public static void main(String[] args) {
        // 随机生成一个 3列 4行 的整数矩阵对象
        IntegerMatrix random1 = IntegerMatrix.random(3, 4, 1024);
        // 随机生成一个 3列 4行 的浮点矩阵对象
        DoubleMatrix random2 = DoubleMatrix.random(3, 4, 1024);
        // 随机生成一个 120x100 的图像矩阵对象
        ColorMatrix random3 = ColorMatrix.random(120, 100, 1024);
        // 查看结果
        System.out.println(random1);
        System.out.println(random2);
        random3.show("random3");
    }
}
```

* Add a printer device data output object that can provide data to the specified printer and perform other operations
  through this object.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.OutputComponent;
import zhao.algorithmMagic.io.PrintServiceOutput;
import zhao.algorithmMagic.io.PrintServiceOutputBuilder;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;

import javax.print.DocFlavor;
import javax.print.attribute.HashPrintRequestAttributeSet;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\zhao\\Pictures\\Screenshots\\屏幕截图_20230116_163341.png");
        // 获取到打印机设备数据输出对象
        HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
        OutputComponent outputComponent = PrintServiceOutput.builder()
                // 设置打印的数据的格式 这里是数组的JPG图像
                .addOutputArg(PrintServiceOutputBuilder.DOC_FLAVOR, new FinalCell<>(DocFlavor.BYTE_ARRAY.JPEG))
                // 设置打印的属性配置集合
                .addOutputArg(PrintServiceOutputBuilder.ATTR, new FinalCell<>(hashPrintRequestAttributeSet))
                // 设置打印机的名称
                .addOutputArg(PrintServiceOutputBuilder.PRINT_SERVER_NAME, new FinalCell<>("HPFF15E0 (HP DeskJet 2700 series)"))
                // 创建出打印机设备数据输入对象
                .create();
        // 开始查看图像并将图像输出
        colorMatrix.show("image", 1024, 512);
        colorMatrix.save(outputComponent);
    }
}
```

* The image matrix object supports a single channel color extraction operation, which will return an image matrix object
  with better performance.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\person1.jpg");
        // 获取到图像中的红色层
        ColorMatrix colorChannel = colorMatrix.getColorChannel(ColorMatrix._R_);
        colorChannel.show("red");
        // 获取到图像中的蓝色层
        colorChannel = colorMatrix.getColorChannel(ColorMatrix._G_);
        colorChannel.show("green");
        // 获取到图像中的蓝色层
        colorChannel = colorMatrix.getColorChannel(ColorMatrix._B_);
        colorChannel.show("blue");
    }
}
```

* The image matrix object supports multi-layer extraction operations for multiple color channels, which will overlay all
  color channels into the image space.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.block.ColorMatrixSpace;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\person1.jpg");
        // 接下来的函数中 TODO Blue色通道将会自动加上 因此不需要进行 + 符号的追加
        // 获取到蓝色颜色通道组成的图像空间 如果只提取蓝色可以进行下面操作
        ColorMatrixSpace colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._B_);
        colorMatrices.show("image1", colorMatrix.getColCount(), colorMatrices.getRowCount());
        // 获取到由红和蓝颜色颜色通道层组成的图像空间
        colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._R_);
        colorMatrices.show("image2", colorMatrix.getColCount(), colorMatrices.getRowCount());
        // 获取到由所有颜色通道层组成的图像空间 因此就是 红 + 绿 就可以实现全部提取了（注意，单独进行 红+绿 提取暂不支持）
        colorMatrices = colorMatrix.toRGBSpace(ColorMatrix._R_ + ColorMatrix._G_);
        colorMatrices.show("image3", colorMatrix.getColCount(), colorMatrices.getRowCount());
    }
}
```

* Support the conversion of image matrix objects to integer matrix space objects, and support the calculation operation
  of specifying the number of channels.
* The convolution kernel in the convolution calculation operation function of the reshaping matrix space supports matrix
  objects composed of double matrix data types.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\person1.jpg");
        // 提取出 红色 绿色 蓝色 通道叠加成为的新整形矩阵空间对象
        IntegerMatrixSpace integerMatrices = colorMatrix.toIntRGBSpace(ColorMatrix._R_, ColorMatrix._G_, ColorMatrix._B_);
        // 准备一个 double 卷积核 该卷积核的左右为求均值计算。
        double r = 1 / 9.0;
        System.out.println(r);
        DoubleMatrix doubleMatrix = AlgorithmStar.parseDoubleMat(
                new double[]{r, r, r},
                new double[]{r, r, r},
                new double[]{r, r, r}
        );
        // 将卷积核矩阵空间对象计算出来
        DoubleMatrixSpace core = DoubleMatrixSpace.parse(doubleMatrix, doubleMatrix, doubleMatrix);
        // 开始进行图像的卷积操作
        ColorMatrix colorMatrix1 = integerMatrices.foldingAndSumRGB(3, 3, core);
        // 查看结果图像
        colorMatrix1.show("image");
    }
}
```

* Add the AS model package, which provides many built-in and well implemented models that can reduce code development
  under certain circumstances. The model objects can be directly passed to AlgorithmStar for calculation, including
  single threaded and multi threaded calculation implementations.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleTargetContour;
import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到摄像头设备
        InputComponent inputComponent = InputCamera.builder()
                .addInputArg(InputCameraBuilder.Camera_Index, SingletonCell.$(0))
                .addInputArg(InputCameraBuilder.Image_Format, SingletonCell.$("JPG"))
                .create();
        // 开始将三个图像矩形拍摄出来
        ColorMatrix[] colorMatrices = new ColorMatrix[3];
        for (int i = 0; i < 3; i++) {
            colorMatrices[i] = AlgorithmStar.parseImage(inputComponent, true);
        }
        // 获取到目标数据样本 这里是人脸轮廓
        ColorMatrix yb = AlgorithmStar.parseImage(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 获取到单目标矩形模型
        SingleTargetContour singleTargetContour = ASModel.SINGLE_TARGET_CONTOUR;
        // 设置需要被用于做为目标的图像矩阵 由于样本不常用，因此在这里没有使用单例单元格，因为单例单元格会保存数据对象
        singleTargetContour.setArg(SingleTargetContour.TARGET, new FinalCell<>(yb));
        // 设置本次绘制识别使用二值化操作，贴近样本
        singleTargetContour.setArg(SingleTargetContour.isBinary, SingletonCell.$(true));
        // 设置矩形轮廓的颜色对象
        singleTargetContour.setArg(SingleTargetContour.OUTLINE_COLOR, SingletonCell.$(new Color(255, 0, 245)));
        long startTime = System.currentTimeMillis();
        // 开始单线程绘制并查看
        for (ColorMatrix colorMatrix : AlgorithmStar.model(singleTargetContour, colorMatrices)) {
            colorMatrix.show("image");
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleTargetContour;
import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到摄像头设备
        InputComponent inputComponent = InputCamera.builder()
                .addInputArg(InputCameraBuilder.Camera_Index, SingletonCell.$(0))
                .addInputArg(InputCameraBuilder.Image_Format, SingletonCell.$("JPG"))
                .create();
        // 开始将三个图像矩形拍摄出来
        ColorMatrix[] colorMatrices = new ColorMatrix[3];
        for (int i = 0; i < 3; i++) {
            colorMatrices[i] = AlgorithmStar.parseImage(inputComponent, true);
        }
        // 获取到目标数据样本 这里是人脸轮廓
        ColorMatrix yb = AlgorithmStar.parseImage(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 获取到单目标矩形模型
        SingleTargetContour singleTargetContour = ASModel.SINGLE_TARGET_CONTOUR;
        // 设置需要被用于做为目标的图像矩阵 由于样本不常用，因此在这里没有使用单例单元格，因为单例单元格会保存数据对象
        singleTargetContour.setArg(SingleTargetContour.TARGET, new FinalCell<>(yb));
        // 设置本次绘制识别使用二值化操作，贴近样本
        singleTargetContour.setArg(SingleTargetContour.isBinary, SingletonCell.$(true));
        // 设置矩形轮廓的颜色对象
        singleTargetContour.setArg(SingleTargetContour.OUTLINE_COLOR, SingletonCell.$(new Color(255, 0, 245)));
        long startTime = System.currentTimeMillis();
        // 开始多线程绘制并查看
        for (ColorMatrix colorMatrix : AlgorithmStar.modelConcurrency(singleTargetContour, colorMatrices)) {
            colorMatrix.show("image");
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}
```

* In the AS model library, an image classification model is added, which supports the supervised learning classification
  model of multiple images. The model also supports both single thread and multithreaded computing operations.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleTargetContour;
import zhao.algorithmMagic.core.model.TarImageClassification;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到 人脸样本
        ColorMatrix person = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 获取到 小猫样本
        ColorMatrix cat = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/232627018-1ab647ff-38f7-408c-a3df-365028463152.jpg")
        );


        // 获取到需要被识别的图像
        ColorMatrix parse1 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/231062649-34268530-801a-4520-81ae-176936a3a981.jpg")
        );
        ColorMatrix parse2 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/232627057-a49d6958-d608-4d44-b309-f63454998aaa.jpg")
        );

        // 获取到图像分类模型
        TarImageClassification tarImageClassification = ASModel.TAR_IMAGE_CLASSIFICATION;
        // 设置人脸类型标签样本
        tarImageClassification.setArg("person", new FinalCell<>(person));
        // 设置小猫类型标签样本
        tarImageClassification.setArg("cat", new FinalCell<>(cat));
        // 设置启用二值化操作
        TarImageClassification.SINGLE_TARGET_CONTOUR.setArg(SingleTargetContour.isBinary, SingletonCell.$(true));

        ColorMatrix[] colorMatrices = {parse1, parse2};
        // 需要注意的是，当指定了二值化操作之后，传递进去的图像矩阵将会被更改 因此在这里使用模型的时候使用克隆出来的矩阵数组
        ColorMatrix[] clone = {ColorMatrix.parse(parse1.copyToNewArrays()), ColorMatrix.parse(parse2.copyToNewArrays())};
        long start = System.currentTimeMillis();
        // 开始计算并迭代计算出来的类别集合，进行结果查看
        for (Map.Entry<String, ArrayList<Integer>> entry : AlgorithmStar.model(tarImageClassification, clone).entrySet()) {
            String k = entry.getKey() + "类别";
            for (int index : entry.getValue()) {
                colorMatrices[index].show(k);
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.SingleTargetContour;
import zhao.algorithmMagic.core.model.TarImageClassification;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到 人脸样本
        ColorMatrix person = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 获取到 小猫样本
        ColorMatrix cat = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/232627018-1ab647ff-38f7-408c-a3df-365028463152.jpg")
        );


        // 获取到需要被识别的图像
        ColorMatrix parse1 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/231062649-34268530-801a-4520-81ae-176936a3a981.jpg")
        );
        ColorMatrix parse2 = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/232627057-a49d6958-d608-4d44-b309-f63454998aaa.jpg")
        );

        // 获取到图像分类模型
        TarImageClassification tarImageClassification = ASModel.TAR_IMAGE_CLASSIFICATION;
        // 设置人脸类型标签样本
        tarImageClassification.setArg("person", new FinalCell<>(person));
        // 设置小猫类型标签样本
        tarImageClassification.setArg("cat", new FinalCell<>(cat));
        // 设置启用二值化操作
        TarImageClassification.SINGLE_TARGET_CONTOUR.setArg(SingleTargetContour.isBinary, SingletonCell.$(true));

        ColorMatrix[] colorMatrices = {parse1, parse2};
        // 需要注意的是，当指定了二值化操作之后，传递进去的图像矩阵将会被更改 因此在这里使用模型的时候使用克隆出来的矩阵数组
        ColorMatrix[] clone = {ColorMatrix.parse(parse1.copyToNewArrays()), ColorMatrix.parse(parse2.copyToNewArrays())};
        long start = System.currentTimeMillis();
        // 开始计算并迭代计算出来的类别集合，进行结果查看
        for (Map.Entry<String, ArrayList<Integer>> entry : AlgorithmStar.modelConcurrency(tarImageClassification, clone).entrySet()) {
            String k = entry.getKey() + "类别";
            for (int index : entry.getValue()) {
                colorMatrices[index].show(k);
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
```

* Support the save and reload operations of AS model objects, which can effectively save the processed model.

```java
package zhao.algorithmMagic;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.utils.ASClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws MalformedURLException {
        // 获取到 人脸样本
        ColorMatrix person = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 将自定义实现模型类加载进来
        MyModel myModel1 = new MyModel();
        // 将模型对象保存到磁盘
        File file = new File("C:\\Users\\Liming\\Desktop\\fsdownload\\MytModel.as");
        ASModel.Utils.write(file, myModel1);
        // 将模型对象重新加载进磁盘
        MyModel myModel2 = ASClass.transform(ASModel.Utils.read(file));
        // 使用模型处理图像
        ColorMatrix model = AlgorithmStar.model(myModel2, new ColorMatrix[]{person});
        // 查看结果图像
        model.show("res");
    }

    /**
     * 这里是实现出来的自定义 图像卷积池化 模型对象
     */
    public static class MyModel implements ASModel<String, ColorMatrix, ColorMatrix> {

        private int width = 3, height = 3;

        @Override
        public void setArg(String key, @NotNull Cell<?> value) {
            switch (key) {
                case "w" -> width = value.getIntValue();
                case "h" -> height = value.getIntValue();
                default -> System.out.println("不认识的参数：" + key);
            }
        }

        @Override
        public ColorMatrix function(ColorMatrix[] input) {
            // 转换成为像素矩阵空间
            IntegerMatrixSpace integerMatrices = input[0].toIntRGBSpace(ColorMatrix._R_, ColorMatrix._G_, ColorMatrix._B_);
            // 构造一个卷积核 这里是将从右到左的边界提取出来的卷积核
            IntegerMatrix parse = IntegerMatrix.parse(
                    new int[]{-1, 0, 1},
                    new int[]{-1, 0, 1},
                    new int[]{-1, 0, 1}
            );
            IntegerMatrixSpace core = IntegerMatrixSpace.parse(parse, parse, parse);
            // 将人脸样本进行卷积
            ColorMatrix colorMatrix = integerMatrices.foldingAndSumRGB(width, height, core);
            // 将人脸样本进行 R G B 三种通道的最大值取值 池化
            return colorMatrix.pooling(width, height, ColorMatrix.POOL_RGB_OBO_MAX);
        }

        @Override
        public ColorMatrix functionConcurrency(ColorMatrix[] input) {
            System.out.println("暂不支持多线程计算。");
            return null;
        }
    }
}
```

* Support the pooling calculation operation of image matrices, whose functional parameters receive pooling convolutional
  kernels and pooling operation implementation objects.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.net.MalformedURLException;
import java.net.URL;


public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws MalformedURLException {
        // 获取到 人脸样本
        ColorMatrix person = ColorMatrix.parse(
                new URL("https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg")
        );
        // 查看原图
        person.show("person");
        // 将人脸图像进行池化操作 指定 3x3 的卷积核 以及 R G B 三种颜色的最大池化操作
        ColorMatrix res1 = person.pooling(3, 3, ColorMatrix.POOL_RGB_MAX);
        // 将人脸图像进行池化操作 指定 3x3 的卷积核 以及 RGB数值的最大池化操作
        ColorMatrix res2 = person.pooling(3, 3, ColorMatrix.POOL_RGB_OBO_MAX);
        // 查看结果图像
        res1.show("res1");
        res2.show("res2");
    }
}
```

* Add a linear neural network regression model in ASModel, which has stronger accuracy and simpler API calls, returning
  a model object that can be saved.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.io.File;
import java.net.MalformedURLException;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {
        // 获取到线性神经网络模型
        LinearNeuralNetwork linearNeuralNetwork = ASModel.LINEAR_NEURAL_NETWORK;
        // 设置学习率 为 0.01
        linearNeuralNetwork.setArg(LinearNeuralNetwork.LEARNING_RATE, SingletonCell.$(0.01));
        // 设置激活函数为 LEAKY_RE_LU
        linearNeuralNetwork.setArg(LinearNeuralNetwork.PERCEPTRON, SingletonCell.$(Perceptron.parse(ActivationFunction.LEAKY_RE_LU)));
        // 设置学习次数 为 570 * 目标数
        linearNeuralNetwork.setArg(LinearNeuralNetwork.LEARN_COUNT, SingletonCell.$(570));
        // 设置目标数值
        linearNeuralNetwork.setArg(
                LinearNeuralNetwork.TARGET,
                // 假设这里是5组数据对应的结果
                SingletonCell.$(new double[]{300, 210, 340, 400, 500})
        );
        // 构建被学习的数据 由此数据推导结果 找到每一组数据中 3 个参数之间的数学模型
        DoubleVector X1 = DoubleVector.parse(100, 50, 50);
        DoubleVector X2 = DoubleVector.parse(80, 50, 50);
        DoubleVector X3 = DoubleVector.parse(120, 50, 50);
        DoubleVector x4 = DoubleVector.parse(100, 100, 100);
        DoubleVector x5 = DoubleVector.parse(150, 100, 100);
        // 构建初始权重向量
        DoubleVector W = DoubleVector.parse(20, 18, 18);
        // 训练出模型
        NumberModel model = linearNeuralNetwork.function(X1, X2, X3, x4, x5, W);

        // TODO 接下来开始使用模型进行一些测试
        // 向模型中传递一些数值
        Double function1 = model.function(new Double[]{100.0, 50.0, 50.0});
        // 打印计算出来的结果
        System.out.println(function1);
        // 再一次传递一些数值
        Double function2 = model.function(new Double[]{150.0, 100.0, 100.0});
        // 打印计算出来的结果
        System.out.println(function2);

        // TODO 确定模型可用，将模型保存
        ASModel.Utils.write(new File("C:\\Users\\Liming\\Desktop\\fsdownload\\MytModel.as"), model);
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;

import java.io.File;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {
        // 从磁盘中加载刚刚保存的模型
        ASModel<Integer, Double, Double> asModel = ASModel.Utils.read(new File("C:\\Users\\Liming\\Desktop\\fsdownload\\MytModel.as"));
        // 构造一组 自变量数据
        Double[] doubles = {150.0, 100.0, 100.0};
        // 计算这组数据在模型中计算后如果，查看是否达到预测效果  预期结果是 500
        // 因为刚刚的训练数据中 数据的公共公式为 f(x) = x1 * 2 + x2 * 1 + x2 * 1
        Double function = asModel.function(doubles);
        System.out.println(function);
        // 打印模型公式
        System.out.println(asModel);
    }
}
```

### Version update date : 2023-04-09