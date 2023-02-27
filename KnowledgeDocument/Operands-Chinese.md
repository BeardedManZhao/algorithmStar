# 操作数

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Operands.md)

操作数就是可以被操作的数，在本框架的设计中，考虑到学习成本等因素，将操作符设计为数学操作符那样，可以进行运算符号的操作，这样更加易于理解，还可以减少您在开发时调用本框架的代码量！

- 操作数模块类图（部分）
  ![image](https://user-images.githubusercontent.com/113756063/195615625-880cdabc-ce61-4f90-b27c-0ba71c25a150.png)

- 1.1版操作数结构关系图（全部）
  ![image](https://user-images.githubusercontent.com/113756063/197778711-e496d7d3-17b9-4788-9427-2e6ec655b5e2.png)

## 坐标

- 介绍

  图计算中极其重要的一部分，在一个维度空间中定位一个坐标点，坐标在此框架中可以实现向量的构造，共同的父类是"Operands"，是向量的组成，框架内包含的坐标类型，以及其使用方式如下所示

### 浮点坐标

浮点坐标中有二维坐标，三维坐标，多维坐标，下面演示的就是三种不同坐标的获取，坐标轴分量值的查阅，同类型坐标的计算。

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取两个浮点二维坐标
        DoubleCoordinateTwo doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 2);
        DoubleCoordinateTwo doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 2);
        // 打印两个坐标
        System.out.println(doubleCoordinateTwo1);
        System.out.println(doubleCoordinateTwo2);
        // 打印两个坐标的不同轴数据
        System.out.println("doubleCoordinateTwo1: x=" + doubleCoordinateTwo1.getX() + "  y=" + doubleCoordinateTwo1.getY());
        System.out.println("doubleCoordinateTwo2: x=" + doubleCoordinateTwo2.getX() + "  y=" + doubleCoordinateTwo2.getY());
        // 计算两个浮点二维坐标的和与差
        System.out.println(doubleCoordinateTwo1.add(doubleCoordinateTwo2));
        System.out.println(doubleCoordinateTwo1.diff(doubleCoordinateTwo2));

        //----------------------------------------------------------//

        // 获取两个浮点三维坐标
        DoubleCoordinateThree doubleCoordinateThree1 = new DoubleCoordinateThree(1, 2, 3);
        DoubleCoordinateThree doubleCoordinateThree2 = new DoubleCoordinateThree(1, 2, 4);
        // 打印两个坐标
        System.out.println(doubleCoordinateThree1);
        System.out.println(doubleCoordinateThree2);
        // 打印两个坐标的不同轴数据
        System.out.println("doubleCoordinateTwo1: x=" + doubleCoordinateThree1.getX() + "  y=" + doubleCoordinateThree1.getY() + "  z=" + doubleCoordinateThree1.getZ());
        System.out.println("doubleCoordinateTwo2: x=" + doubleCoordinateThree2.getX() + "  y=" + doubleCoordinateThree2.getY() + "  z=" + doubleCoordinateThree1.getZ());
        // 计算两个浮点三维坐标的和与差
        System.out.println(doubleCoordinateThree1.add(doubleCoordinateThree2));
        System.out.println(doubleCoordinateThree1.diff(doubleCoordinateThree2));

        //----------------------------------------------------------//

        // 获取两个浮点三维坐标
        DoubleCoordinateMany doubleCoordinateMany1 = new DoubleCoordinateMany(1, 2, 3, 4, 5);
        DoubleCoordinateMany doubleCoordinateMany2 = new DoubleCoordinateMany(1, 2, 4, 3, 2);
        // 打印两个坐标
        System.out.println(doubleCoordinateMany1);
        System.out.println(doubleCoordinateMany2);
        // 针对多维坐标，我们就没有办法逐一获取了，但是可以使用一个所有坐标都具有的方法，就是获取到一个坐标序列数组
        double[] doubles1 = doubleCoordinateMany1.toArray();
        double[] doubles2 = doubleCoordinateMany2.toArray();
        for (int i = 0; i < doubles1.length; i++) {
            System.out.println("The " + i + " coordinate of double Coordinate Many 1:" + doubles1[i]);
            System.out.println("The " + i + " coordinate of double Coordinate Many 2:" + doubles2[i]);
        }
        // 计算两个浮点三维坐标的和与差
        System.out.println(doubleCoordinateThree1.add(doubleCoordinateThree2));
        System.out.println(doubleCoordinateThree1.diff(doubleCoordinateThree2));
    }
}
```

- 上面演示的就是浮点坐标的Java语法形式，其实这种语法在scala中表现的可能更加突出，例如下面这种坐标之间的运算，可以使用中缀表达式，更加贴切数学的操作符！

```scala
// scala api

//import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo TODO 从1.12版本之后，包结构变更，请使用下方的导包

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo

object MAIN {
  def main(args: Array[String]): Unit = {
    // 获取两个浮点二维坐标
    val doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 2)
    val doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 2)
    // 累加
    val doubleCoordinateTwo_add = doubleCoordinateTwo1 add doubleCoordinateTwo2 add doubleCoordinateTwo1
    // 累减
    val doubleCoordinateTwo_diff = doubleCoordinateTwo1 diff doubleCoordinateTwo2 diff doubleCoordinateTwo1
    // 加减混合
    val doubleCoordinateTwo = doubleCoordinateTwo_add diff doubleCoordinateTwo_diff add doubleCoordinateTwo_add
    println(doubleCoordinateTwo)
  }
}
```

### 整数坐标

整数坐标的语法与浮点坐标大致上是相同的，因为都属于坐标，拥有共同的父类，变化的仅仅只有坐标对象的名称！

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取两个整数二维坐标
        IntegerCoordinateTwo IntegerCoordinateTwo1 = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo IntegerCoordinateTwo2 = new IntegerCoordinateTwo(1, 2);
        // 打印两个坐标
        System.out.println(IntegerCoordinateTwo1);
        System.out.println(IntegerCoordinateTwo2);
        // 打印两个坐标的不同轴数据
        System.out.println("IntegerCoordinateTwo1: x=" + IntegerCoordinateTwo1.getX() + "  y=" + IntegerCoordinateTwo1.getY());
        System.out.println("IntegerCoordinateTwo2: x=" + IntegerCoordinateTwo2.getX() + "  y=" + IntegerCoordinateTwo2.getY());
        // 计算两个整数二维坐标的和与差
        System.out.println(IntegerCoordinateTwo1.add(IntegerCoordinateTwo2));
        System.out.println(IntegerCoordinateTwo1.diff(IntegerCoordinateTwo2));

        //----------------------------------------------------------//

        // 获取两个整数三维坐标
        IntegerCoordinateThree IntegerCoordinateThree1 = new IntegerCoordinateThree(1, 2, 3);
        IntegerCoordinateThree IntegerCoordinateThree2 = new IntegerCoordinateThree(1, 2, 4);
        // 打印两个坐标
        System.out.println(IntegerCoordinateThree1);
        System.out.println(IntegerCoordinateThree2);
        // 打印两个坐标的不同轴数据
        System.out.println("IntegerCoordinateTwo1: x=" + IntegerCoordinateThree1.getX() + "  y=" + IntegerCoordinateThree1.getY() + "  z=" + IntegerCoordinateThree1.getZ());
        System.out.println("IntegerCoordinateTwo2: x=" + IntegerCoordinateThree2.getX() + "  y=" + IntegerCoordinateThree2.getY() + "  z=" + IntegerCoordinateThree1.getZ());
        // 计算两个整数三维坐标的和与差
        System.out.println(IntegerCoordinateThree1.add(IntegerCoordinateThree2));
        System.out.println(IntegerCoordinateThree1.diff(IntegerCoordinateThree2));

        //----------------------------------------------------------//

        // 获取两个整数三维坐标
        IntegerCoordinateMany IntegerCoordinateMany1 = new IntegerCoordinateMany(1, 2, 3, 4, 5);
        IntegerCoordinateMany IntegerCoordinateMany2 = new IntegerCoordinateMany(1, 2, 4, 3, 2);
        // 打印两个坐标
        System.out.println(IntegerCoordinateMany1);
        System.out.println(IntegerCoordinateMany2);
        // 针对多维坐标，我们就没有办法逐一获取了，但是可以使用一个所有坐标都具有的方法，就是获取到一个坐标序列数组
        int[] doubles1 = IntegerCoordinateMany1.toArray();
        int[] doubles2 = IntegerCoordinateMany2.toArray();
        for (int i = 0; i < doubles1.length; i++) {
            System.out.println("The " + i + " coordinate of Integer Coordinate Many 1:" + doubles1[i]);
            System.out.println("The " + i + " coordinate of Integer Coordinate Many 2:" + doubles2[i]);
        }
        // 计算两个整数三维坐标的和与差
        System.out.println(IntegerCoordinateThree1.add(IntegerCoordinateThree2));
        System.out.println(IntegerCoordinateThree1.diff(IntegerCoordinateThree2));
    }
}
```

## 坐标线路

坐标线路对象是一个有起始与终止坐标点的线路对象，其可以表示为从起始坐标到终止坐标的线路，是基于坐标对象而实现出来的一种操作数对象，接下来将展示坐标线路的通用函数。

- 接口中的函数

| 函数名称                    | 函数返回值              | 函数作用                      |
|-------------------------|--------------------|---------------------------|
| expand()                | ImplementationType | 将一个父类身份的对象，具体化成为子类的对象。    |
| getEndPointCoordinate() | CoordinateType     | 获取到线路对象中的终止点坐标。           |
| getStartingCoordinate() | CoordinateType     | 获取到线路对象中的起始点坐标。           |
| getNumberOfDimensions() | int                | 获取到线路中始末坐标的维度数量。          |
| toDoubleVector()        | DoubleVector       | 获取到起始点坐标与终止点坐标所构造出来的向量对象。 |

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

public class Test {

    public static void main(String[] args) {
        // 获取到一些坐标
        IntegerCoordinateTwo A = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo B = new IntegerCoordinateTwo(1, 20);
        IntegerCoordinateTwo C = new IntegerCoordinateTwo(20, 2);
        // 构建三条线路
        IntegerConsanguinityRoute2D AB = IntegerConsanguinityRoute2D.parse("A -> B", A, B);
        IntegerConsanguinityRoute2D BC = IntegerConsanguinityRoute2D.parse("B -> C", B, C);
        IntegerConsanguinityRoute2D CA = IntegerConsanguinityRoute2D.parse("C -> A", C, A);
        // 将三条线路进行计算
        IntegerConsanguinityRoute2D AB_BC = AB.add(BC);
        IntegerConsanguinityRoute2D AB_CA = AB.add(CA);
        // 打印计算结果
        System.out.println(AB_BC);
        System.out.println(AB_CA);
    }
}
```

## 坐标网络

坐标网络正如名称所示，其是一个网络，由多条线路对象构建成的网络，在在这条网络当中，将会准确的记录着路线之间的关系。

- 接口中的函数

| 函数名称                                            | 函数返回值              | 函数作用                         |
|-------------------------------------------------|--------------------|------------------------------|
| expand()                                        | ImplementationType | 将一个父类身份的对象，具体化成为子类的对象。       |
| addRoute(RouteType routeType)                   | Boolean            | 将一条线路对象添加到网络中，添加成功添加则返回true。 |
| containsKeyFromRoute2DHashMap(String RouteName) | Boolean            | 判断一条线路是否存在与线路网络中，如哦存在返回true。 |
| getNetDataSet()                                 | HashSet<RouteType> | 获取等到所有线路对象的集合。               |
| getRouteCount()                                 | int                | 获取到当前网络对象中的线路对象数量。           |

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRoute2DNet;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

public class Test {

    public static void main(String[] args) {
        // 获取到一些坐标
        IntegerCoordinateTwo A = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo B = new IntegerCoordinateTwo(1, 20);
        IntegerCoordinateTwo C = new IntegerCoordinateTwo(20, 2);
        // 构建三条线路
        IntegerConsanguinityRoute2D AB = IntegerConsanguinityRoute2D.parse("A -> B", A, B);
        IntegerConsanguinityRoute2D BC = IntegerConsanguinityRoute2D.parse("B -> C", B, C);
        IntegerConsanguinityRoute2D CA = IntegerConsanguinityRoute2D.parse("C -> A", C, A);
        // 获取到一张线路网络，并将三条线路添加进去
        IntegerRoute2DNet parse = IntegerRoute2DNet.parse(AB, BC, CA);
        // 从线路网络中提取出来 BC 线路
        System.out.println(parse.getRouteFromHashMap("B -> C"));
    }
}
```

## 向量

向量是两个多维坐标点之间的距离，在框架中能够用于矩阵的构造，以及各种操作算法，重要性和坐标同等，构造方式大致如下所示

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 通过赋值获取到一个向量
        DoubleVector parse1 = DoubleVector.parse(1, 2, 3);
        // 通过坐标获取到一个向量（维度不限）
        DoubleVector parse2 = DoubleVector.parse(new DoubleCoordinateThree(0, 0, 1), new DoubleCoordinateThree(0, 0, 2));
        // 获取到向量的模
        System.out.println(parse1.moduleLength());
        System.out.println(parse2.moduleLength());
        // 获取到两个向量的 加 减 乘（向量积，外积） 数量积（内积）TODO 注意两个向量的维度数量要一致！否则运算会抛出OperandConversionException
        DoubleVector add = parse1.add(parse2);
        DoubleVector diff = parse1.diff(parse2);
        DoubleVector multiply = parse1.multiply(parse2);
        Double aDouble = parse1.innerProduct(parse2);
    }
}
```

## 矩阵

矩阵类似很多个向量的叠加，矩阵的构造相对于向量与坐标是比较庞大的，但是内部的结构处理考虑到了这个问题，使用基元与对象复用来降低了性能消耗，本框架中矩阵不仅仅包含数值型矩阵，还包含一个复数矩阵，下面就是操作演示！

### 数值矩阵

数值矩阵是一种特征矩阵，其中存储的是一个个的基元数值数据类型，在机器学习库中支持整形与双精度浮点型的数值矩阵对象，在不同的场景下有不同的使用时机。

```java
// Java api

import zhao.algorithmMagic.operands.ComplexNumber;
import zhao.algorithmMagic.operands.matrix.ComplexNumberMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 通过数组构建一个浮点数值矩阵
        DoubleMatrix parse1 = DoubleMatrix.parse(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        DoubleMatrix parse2 = DoubleMatrix.parse(new double[]{7, 8, 9}, new double[]{10, 11, 12});
        // 计算矩阵
        DoubleMatrix add = parse1.add(parse2);
        DoubleMatrix diff = parse1.diff(parse2);
        DoubleMatrix multiply = parse1.multiply(parse2);
        Double complexNumber = parse1.innerProduct(parse2);
        // 通过行列值 获取矩阵数据
        double v = parse1.get(1, 2);
        // 直接打印矩阵
        System.out.println(parse1);

        //----------------------------------------------------------//

        // 通过复数公式构建一个复数矩阵，其中每一个数组就是矩阵的一行
        ComplexNumberMatrix parse11 = ComplexNumberMatrix.parse(
                new String[]{"1 + 2i", "3 + 4i"}, new String[]{"5 - 6i", "7 - 8i"}
        );
        // 通过复数对象构建一个复数矩阵，其中每一个数组就是矩阵的一行
        ComplexNumberMatrix parse22 = ComplexNumberMatrix.parse(
                new ComplexNumber[]{ComplexNumber.parse(1, 2), ComplexNumber.parse(3, 4)},
                new ComplexNumber[]{ComplexNumber.parse(5, -6), ComplexNumber.parse(7, -8)}
        );
        // 计算矩阵
        ComplexNumberMatrix add1 = parse11.add(parse22);
        ComplexNumberMatrix diff1 = parse11.diff(parse22);
        ComplexNumberMatrix multiply1 = parse11.multiply(parse22);
        ComplexNumber complexNumber1 = parse11.innerProduct(parse22);
        // 复数矩阵的特有运算 共轭
        ComplexNumberMatrix conjugate = parse11.conjugate();
        // 通过行列值 获取矩阵数据
        ComplexNumber complexNumber2 = parse11.get(1, 2);
        // 直接打印矩阵
        System.out.println(parse11);
    }
}
```

### 图像矩阵

图像矩阵是一种存储着图像像素点的矩阵对象，在矩阵中的每一个坐标都是一个像素，其中每一个像素的数据类型为Color，您可以调用诸多的内置图像矩阵计算函数，也可以通过 toArrays 函数直接操作图像中的每一个像素。

```java
// Java api
package core;

import zhao.algorithmMagic.lntegrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.Color;

public class MAIN {
    public static void main(String[] args) {
        // 获取到图像灰度矩阵
        ColorMatrix parse = ColorMatrix.parseGrayscale("C:\\Users\\zhao\\Desktop\\fsdownload\\微信图片_6.jpg");
        // 将图像中所有不属于深色的像素换为白色
        Color color = new Color(ColorMatrix.WHITE_NUM);
        for (Color[] colors : parse.toArrays()) {
            for (int i = 0; i < colors.length; i++) {
                int green = colors[i].getGreen();
                // 这里就是分界线 深色分界线的数值需要根据图像进行设置
                // 数值越小，去除的噪音越多，但数值过小也可能导致数据的损失
                if (green > 100) {
                    colors[i] = color;
                }
            }
        }
        // 将图像的亮度降低一点
        parse.dimming(0.7f);
        // 将图像的对比度增强
        parse.contrast(50);
        // 输出图像
        ImageRenderingIntegrator imageRenderingIntegrator = new ImageRenderingIntegrator(
                "image",
                new ImageRenderingMarLauncher<>(parse, "C:\\Users\\zhao\\Desktop\\fsdownload\\res_6.jpg", 1)
        );
        if (imageRenderingIntegrator.run()) {
            System.out.println("ok!!!");
        }
    }
}
```

## 复数

如您所见，矩阵中的共轭运算需要使用到复数矩阵，那么复数矩阵的组成正式由复数组成的，复数的父类继承中同样也包含"Operands",意味着复数也可以支持像操作符那样进行加减等操作，下面就是具体示例！

```java
// Java api

import zhao.algorithmMagic.operands.ComplexNumber;

public class MAIN1 {
    public static void main(String[] args) {
        // 通过设置实部  虚部的数值，获取到一个复数
        ComplexNumber parse1 = ComplexNumber.parse(1, 2);
        // 通过复数的公式，获取到一个复数
        ComplexNumber parse2 = ComplexNumber.parse("1 + 2i");
        // 查阅复数的值，这里两种方式都可以查询到！如果是应用于后期的计算，第一种更合适，但是第二种会将复数公式展示出来
        System.out.println(parse1.getReal() + " + " + parse1.getImaginary() + "i");
        System.out.println(parse1);
        // 运算
        ComplexNumber add = parse1.add(parse2);
        ComplexNumber diff = parse1.diff(parse2);
        ComplexNumber multiply = parse1.multiply(parse2);
        ComplexNumber divide = parse1.divide(parse2);
        // 复数特有运算，共轭
        ComplexNumber conjugate = parse1.conjugate();
    }
}
```

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Operands.md)

<hr>

#### date: 2022-10-15

#### update date：2023-02-27
