# AS 机器学习库案例集

## 机器视觉类案例

### 获取图像边缘线

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
        // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
        parse1.erode(3, 3, false, Color.WHITE);
        // 查看临时结果
        parse1.show("image1");
        parse2.show("image2");
        // 将两个矩阵进行做差运算，并查看做差之后的图像
        (parse1.diff(parse2)).show("结果图像");
    }
}
```

### 描绘出图像边缘

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取一张图像的像素矩阵
        ColorMatrix colors = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 将图像拷贝一份出来
        ColorMatrix parse1 = ColorMatrix.parse(colors.copyToNewArrays());
        // 将 parse1 进行二值化 // 请注意阈值
        parse1.globalBinary(ColorMatrix._G_, 60, 0, 0xffffff);
        // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
        ColorMatrix parse2 = parse1.erode(2, 2, true, Color.BLACK);
        // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
        parse1.erode(2, 2, false, Color.WHITE);
        // 查看临时结果
        parse1.show("image1");
        parse2.show("image2");
        // 将两个矩阵进行做差运算，并查看做差之后的图像
        ColorMatrix resImage1 = parse1.diff(parse2);
        resImage1.show("轮廓图像");
        // 将轮廓绘制到原图像中 并查看绘制之后的图像
        ColorMatrix resImage2 = colors.agg(resImage1, ColorMatrix.COLOR_SUM_REGULATE);
        // 查看结果数据
        resImage2.show("绘制之后的结果图像");
    }
}
```

### 图像矩阵轮廓检测

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.RectangleMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix resImage1, parse1;
        {
            // 获取一张图像的像素矩阵
            ColorMatrix colors = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test3.bmp");
            // 将图像拷贝一份出来
            parse1 = ColorMatrix.parse(colors.copyToNewArrays());
            // 将结果二值化
            parse1.globalBinary(ColorMatrix._G_, 150, 0xffffff, 0);
            // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
            ColorMatrix parse2 = parse1.erode(3, 3, true, Color.BLACK);
            // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
            parse1.erode(2, 2, false, Color.WHITE);
            // 查看临时结果
            parse1.show("image1");
            parse2.show("image2");
            // 将两个矩阵进行做差运算，并查看做差之后的图像
            resImage1 = parse1.diff(parse2);
            resImage1.show("轮廓图像");
        }
        // 开始提取白色轮廓线内的面积
        System.out.print("轮廓内面积 = ");
        System.out.println(resImage1.contourArea(ColorMatrix.WHITE));
        // 将轮廓线转化成矩形轮廓
        ColorMatrix parse = RectangleMatrix.parse(resImage1, Color.GREEN);
        parse.show("矩形轮廓");
        System.out.print(parse);
        // 将矩形轮廓添加到原矩阵 这里的规则是，如果颜色数值为 0xff000000 就不合并
        ColorMatrix res = parse1.agg(parse, (inputType1, inputType2) -> inputType2.getRGB() == 0xff000000 ? inputType1 : inputType2);
        res.show("结果");
    }
}
```

### 图像模板匹配

- 人脸识别 通过人脸模板匹配曼哈顿相似系数，进而得出人脸识别结果，在这里我们需要使用以下的图像作为样本。

![YB](https://user-images.githubusercontent.com/113756063/230775389-4477aad4-795c-47c2-a946-0afeadafad44.jpg)

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;
import java.util.Map;


public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1, colorMatrix2;
        {        // 将图像与样本读取进来
            colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\YB.bmp");
            colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test22.jpg");
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 0);
            // 开始进行模板匹配 并返回最匹配的结果数值，在这里返回的就是所有匹配的结果数据，key为匹配系数  value为匹配结果
            Map.Entry<Double, IntegerCoordinateTwo> matching = temp.templateMatching(
                    ManhattanDistance.getInstance("MAN"),
                    colorMatrix1,
                    ColorMatrix._G_,
                    10,
                    false
            );
            // 开始进行绘制 在这里首先获取到坐标数据
            IntegerCoordinateTwo coordinateTwo = matching.getValue();
            System.out.print("匹配系数 = ");
            System.out.println(matching.getKey());
            colorMatrix2.drawRectangle(
                    coordinateTwo,
                    new IntegerCoordinateTwo(coordinateTwo.getX() + colorMatrix1.getColCount(), coordinateTwo.getY() + colorMatrix1.getRowCount()),
                    Color.MAGENTA
            );
        }
        colorMatrix1.show("人脸样本");
        colorMatrix2.show("识别结果");
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1, colorMatrix2;
        {        // 将图像与样本读取进来
            colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\YB.bmp");
            colorMatrix2 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\test22.jpg");
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 15);
            temp.erode(2, 2, false);
            temp.show("temp");
            // 开始进行模板匹配 并返回最匹配的结果数值，在这里返回的就是所有匹配的结果数据
            // 其中每一个元素都是一个匹配到的符合条件的结果信息对象 key为匹配系数  value为匹配结果
            ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> entries = temp.templateMatching(
                    // 计算相似度需要使用的计算组件
                    ManhattanDistance.getInstance("MAN"),
                    // 需要被计算的矩阵对象
                    colorMatrix1,
                    // 计算时需要使用的颜色通道
                    ColorMatrix._G_,
                    // 计算时需要使用的卷积步长
                    16,
                    // 相似度阈值设定条件
                    v -> v < 1200000
            );
            // 将所有的边框绘制到原图中
            for (Map.Entry<Double, IntegerCoordinateTwo> matching : entries) {
                // 开始进行绘制 在这里首先获取到坐标数据
                IntegerCoordinateTwo coordinateTwo = matching.getValue();
                System.out.print("匹配系数 = ");
                System.out.println(matching.getKey());
                colorMatrix2.drawRectangle(
                        coordinateTwo,
                        new IntegerCoordinateTwo(coordinateTwo.getX() + colorMatrix1.getColCount(), coordinateTwo.getY() + colorMatrix1.getRowCount()),
                        Color.MAGENTA
                );
            }
        }
        colorMatrix1.show("人脸样本");
        colorMatrix2.show("识别结果");
    }
}
```

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.awt.*;
import java.util.Map;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix colorMatrix1, colorMatrix2;
        {
            // 获取到摄像头输入设备
            InputComponent inputComponent = InputCamera.builder()
                    // 要使用的摄像头的名字 索引 或def默认，我们这里使用的是 def 代表使用默认摄像头
                    .addInputArg(InputCameraBuilder.Camera_Index, new FinalCell<>("def"))
                    // 要使用的拍摄图像格式
                    .addInputArg(InputCameraBuilder.Image_Format, new FinalCell<>("JPG"))
                    // 图像尺寸 这里的数值是 WebcamResolution 枚举类的属性字段 VGA
                    .addInputArg(InputCameraBuilder.CUSTOM_VIEW_SIZES, new FinalCell<>("VGA"))
                    .create();
            // 将图像与样本读取进来
            colorMatrix1 = ColorMatrix.parse("C:\\Users\\liming\\Desktop\\fsdownload\\YB.bmp");
            colorMatrix2 = ColorMatrix.parse(inputComponent);
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 20);
            temp.erode(2, 2, false);
            temp.show("temp");
            // 开始进行模板匹配 并返回最匹配的结果数值，在这里返回的就是所有匹配的结果数据，key为匹配系数  value为匹配结果
            Map.Entry<Double, IntegerCoordinateTwo> matching = temp.templateMatching(
                    ManhattanDistance.getInstance("MAN"),
                    colorMatrix1,
                    ColorMatrix._G_,
                    10,
                    false
            );
            // 开始进行绘制 在这里首先获取到坐标数据
            IntegerCoordinateTwo coordinateTwo = matching.getValue();
            System.out.print("匹配系数 = ");
            System.out.println(matching.getKey());
            colorMatrix2.drawRectangle(
                    coordinateTwo,
                    new IntegerCoordinateTwo(coordinateTwo.getX() + colorMatrix1.getColCount(), coordinateTwo.getY() + colorMatrix1.getRowCount()),
                    Color.MAGENTA
            );
        }
        colorMatrix1.show("人脸样本");
        colorMatrix2.show("识别结果");
    }
}
```

### 指定文字识别

这里展示的是通过模板匹配操作，圈出图像中的指定数字的字符，图像数据集与图像的处理代码如下所示。

- 图像数据集 这里有所有数字的图像数据以及被处理的图像。

![1](https://user-images.githubusercontent.com/113756063/229441539-03d9adb6-4883-4c2c-a86d-e008b2a620d6.jpg)
![2](https://user-images.githubusercontent.com/113756063/229441568-a273d88e-a411-4d70-907b-67084ec404a8.jpg)
![3](https://user-images.githubusercontent.com/113756063/229441606-fa1f7195-0bbb-4c86-91dc-c0b7d3267850.jpg)
![4](https://user-images.githubusercontent.com/113756063/229441641-f0e01024-df8a-432f-a028-462327beb3c0.jpg)
![5](https://user-images.githubusercontent.com/113756063/229441669-ae0b3a20-fbf0-447f-bf76-20ea84016751.jpg)
![6](https://user-images.githubusercontent.com/113756063/229441694-c80357da-865e-40ec-815b-7dcebe743897.jpg)
![7](https://user-images.githubusercontent.com/113756063/229441706-5ae85394-ae91-4d77-85d8-5c088693277b.jpg)
![8](https://user-images.githubusercontent.com/113756063/229441723-c51b2a73-c007-436e-9a5c-e841a5312424.jpg)
![9](https://user-images.githubusercontent.com/113756063/229441430-7b9b8ded-b207-4415-b5a0-0b3e594d7846.jpg)
![test4](https://user-images.githubusercontent.com/113756063/229441864-ec1770d5-1154-4e9c-837e-a4acfc5fb259.jpg)

- 图像识别代码，通过下面的代码实现了图像中数字 4 的圈出操作。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        HashMap<Integer, ColorMatrix> hashMap = new HashMap<>(0b1100);
        // 将样本通过网络读取进来
        {
            hashMap.put(1, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441539-03d9adb6-4883-4c2c-a86d-e008b2a620d6.jpg")));
            hashMap.put(2, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441568-a273d88e-a411-4d70-907b-67084ec404a8.jpg")));
            hashMap.put(3, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441606-fa1f7195-0bbb-4c86-91dc-c0b7d3267850.jpg")));
            hashMap.put(4, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441641-f0e01024-df8a-432f-a028-462327beb3c0.jpg")));
            hashMap.put(5, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441669-ae0b3a20-fbf0-447f-bf76-20ea84016751.jpg")));
            hashMap.put(6, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441694-c80357da-865e-40ec-815b-7dcebe743897.jpg")));
            hashMap.put(7, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441706-5ae85394-ae91-4d77-85d8-5c088693277b.jpg")));
            hashMap.put(8, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441723-c51b2a73-c007-436e-9a5c-e841a5312424.jpg")));
            hashMap.put(9, ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441430-7b9b8ded-b207-4415-b5a0-0b3e594d7846.jpg")));
        }
        // 将需要被进行数字提取的图像通过网络获取到
        ColorMatrix parse = ColorMatrix.parse(new URL("https://user-images.githubusercontent.com/113756063/229441864-ec1770d5-1154-4e9c-837e-a4acfc5fb259.jpg"));
        // 将原矩阵复制一份，稍后用于结果展示
        ColorMatrix parse1 = ColorMatrix.parse(parse.copyToNewArrays());
        // 将需要被处理的图像进行二值化，使其颜色不会干扰匹配
        parse.localBinary(ColorMatrix._G_, 4, 0xffffff, 0, -15);
        parse.show("二值化结果");
        // 在这里获取到 4 数值对应的样本
        ColorMatrix temp = hashMap.get(4);
        temp.show("模板数据");
        // 开始进行模板匹配操作，该操作将会返回所有度量系数小于 3500 的子图像左上角坐标
        ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> matching = parse.templateMatching(
                ManhattanDistance.getInstance("MAN"),
                temp,
                ColorMatrix._G_,
                1,
                v -> {
                    System.out.println(v);
                    return v < 5000;
                }
        );
        // 将所有的轮廓绘制到用于展示的矩阵对象上。
        for (Map.Entry<Double, IntegerCoordinateTwo> doubleIntegerCoordinateTwoEntry : matching) {
            IntegerCoordinateTwo value = doubleIntegerCoordinateTwoEntry.getValue();
            parse1.drawRectangle(
                    value,
                    new IntegerCoordinateTwo(value.getX() + temp.getColCount() - 1, value.getY() + temp.getRowCount() - 1),
                    Color.MAGENTA
            );
        }
        // 查看结果
        parse1.show("res");
    }
}
```

### 图像放大案例

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.util.HashMap;


public class MAIN1 {
    public static void main(String[] args) {
        // 被处理图像路径
        String dataPath = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_33.jpg";
        ColorMatrix colorMatrix1 = ColorMatrix.parse(dataPath);
        colorMatrix1.show("src");
        // 将矩阵变换 首先需要创建出不同模式中需要的配置信息 这里是反转和拉伸矩阵的配置
        HashMap<String, Cell<?>> pro = new HashMap<>();
        // 拉伸矩阵时指定拉伸倍数
        pro.put("times", new FinalCell<>(2));
        // 开始左右拉伸
        ColorMatrix converter = colorMatrix1.converter(ColorMatrix.SLIT_LR, pro);
        // 将左右拉伸后的结果再进行上下拉伸，然后查看图像
        converter.converter(ColorMatrix.SLIT_BT, pro).show("res");
    }
}
```

## 数据计算类案例

### Series 转 Stream

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.*;

import java.util.stream.Stream;


public class MAIN1 {
    public static void main(String[] args) {
        // 手动创建一个DF对象
        DataFrame dataFrame = FDataFrame.select(
                FieldCell.parse("name", "sex", "age"), 0
        );
        // 插入一行数据
        dataFrame.insert(
                FinalSeries.parse("zhao1", "男", "19"),
                FinalSeries.parse("zhao2", "女", "18"),
                FinalSeries.parse("zhao3", "女", "20")
        );
        System.out.println(dataFrame);
        // 将其中的第二行数据转换成Java的Stream对象
        Series cells = dataFrame.toList().get(1);
        Stream<Cell<?>> cellStream = cells.toStream();
        // 查看对象
        cellStream.forEach(System.out::println);
    }
}
```

## 机器学习类案例
