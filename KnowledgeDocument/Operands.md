# Operands

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Operands-Chinese.md)

Operands are numbers that can be manipulated. In the design of this framework, taking into account factors such as
learning costs, the operators are designed as mathematical operators, which can operate on symbols, which is easier to
understand and reduces your The amount of code that calls this framework during development!

- Operand Module Class Diagram (part)
  ![image](https://user-images.githubusercontent.com/113756063/195615625-880cdabc-ce61-4f90-b27c-0ba71c25a150.png)

- Version 1.1 Operand Structure Diagram (All)
  ![image](https://user-images.githubusercontent.com/113756063/197778711-e496d7d3-17b9-4788-9427-2e6ec655b5e2.png)

## coordinate

- introduce

An extremely important part of graph computing is to locate a coordinate point in a dimensional space. The coordinate
can realize the construction of a vector in this framework. The common parent class is "Operands", which is the
composition of the vector. and how it is used is shown below

### floating point coordinates

There are two-dimensional coordinates, three-dimensional coordinates, and multi-dimensional coordinates in
floating-point coordinates. The following demonstrates the acquisition of three different coordinates, the reference of
coordinate axis component values, and the calculation of the same type of coordinates.

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;

public class MAIN1 {
    public static void main(String[] args) {
        // Get two floating point 2D coordinates
        DoubleCoordinateTwo doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 2);
        DoubleCoordinateTwo doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 2);
        // print two coordinates
        System.out.println(doubleCoordinateTwo1);
        System.out.println(doubleCoordinateTwo2);
        // Print different axis data for two coordinates
        System.out.println("doubleCoordinateTwo1: x=" + doubleCoordinateTwo1.getX() + "  y=" + doubleCoordinateTwo1.getY());
        System.out.println("doubleCoordinateTwo2: x=" + doubleCoordinateTwo2.getX() + "  y=" + doubleCoordinateTwo2.getY());
        // 计算两个浮点二维坐标的和与差
        System.out.println(doubleCoordinateTwo1.add(doubleCoordinateTwo2));
        System.out.println(doubleCoordinateTwo1.diff(doubleCoordinateTwo2));

        //----------------------------------------------------------//

        // Get two floating point 3D coordinates
        DoubleCoordinateThree doubleCoordinateThree1 = new DoubleCoordinateThree(1, 2, 3);
        DoubleCoordinateThree doubleCoordinateThree2 = new DoubleCoordinateThree(1, 2, 4);
        // print two coordinates
        System.out.println(doubleCoordinateThree1);
        System.out.println(doubleCoordinateThree2);
        // Print different axis data for two coordinates
        System.out.println("doubleCoordinateTwo1: x=" + doubleCoordinateThree1.getX() + "  y=" + doubleCoordinateThree1.getY() + "  z=" + doubleCoordinateThree1.getZ());
        System.out.println("doubleCoordinateTwo2: x=" + doubleCoordinateThree2.getX() + "  y=" + doubleCoordinateThree2.getY() + "  z=" + doubleCoordinateThree1.getZ());
        // Calculate the sum and difference of two floating point 3D coordinates
        System.out.println(doubleCoordinateThree1.add(doubleCoordinateThree2));
        System.out.println(doubleCoordinateThree1.diff(doubleCoordinateThree2));

        //----------------------------------------------------------//

        // Get two floating point 3D coordinates
        DoubleCoordinateMany doubleCoordinateMany1 = new DoubleCoordinateMany(1, 2, 3, 4, 5);
        DoubleCoordinateMany doubleCoordinateMany2 = new DoubleCoordinateMany(1, 2, 4, 3, 2);
        // print two coordinates
        System.out.println(doubleCoordinateMany1);
        System.out.println(doubleCoordinateMany2);
        // For multidimensional coordinates, we have no way to obtain them one by one, but we can use a method that all coordinates have, that is, to obtain an array of coordinate sequences
        double[] doubles1 = doubleCoordinateMany1.toArray();
        double[] doubles2 = doubleCoordinateMany2.toArray();
        for (int i = 0; i < doubles1.length; i++) {
            System.out.println("The " + i + " coordinate of double Coordinate Many 1:" + doubles1[i]);
            System.out.println("The " + i + " coordinate of double Coordinate Many 2:" + doubles2[i]);
        }
        // Calculate the sum and difference of two floating point 3D coordinates
        System.out.println(doubleCoordinateThree1.add(doubleCoordinateThree2));
        System.out.println(doubleCoordinateThree1.diff(doubleCoordinateThree2));
    }
}
```

- The above is the Java syntax of floating-point coordinates. In fact, this syntax may be more prominent in scala. For
  example, the following operations between coordinates can use infix expressions, which are more appropriate
  mathematical operators!

```scala
// scala api

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo

object MAIN {
  def main(args: Array[String]): Unit = {
    // Get two floating point 2D coordinates
    val doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 2)
    val doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 2)
    // 累加 accumulate
    val doubleCoordinateTwo_add = doubleCoordinateTwo1 add doubleCoordinateTwo2 add doubleCoordinateTwo1
    // 累减 Cumulative diff
    val doubleCoordinateTwo_diff = doubleCoordinateTwo1 diff doubleCoordinateTwo2 diff doubleCoordinateTwo1
    // 加减混合 Addition and subtraction mix
    val doubleCoordinateTwo = doubleCoordinateTwo_add diff doubleCoordinateTwo_diff add doubleCoordinateTwo_add
    println(doubleCoordinateTwo)
  }
}
```

### integer coordinates

The syntax of integer coordinates is roughly the same as that of floating-point coordinates, because they all belong to
coordinates and have a common parent class, and only the name of the coordinate object changes!

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

public class MAIN1 {
    public static void main(String[] args) {
        // Get two integer 2D coordinates
        IntegerCoordinateTwo IntegerCoordinateTwo1 = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo IntegerCoordinateTwo2 = new IntegerCoordinateTwo(1, 2);
        // print two coordinates
        System.out.println(IntegerCoordinateTwo1);
        System.out.println(IntegerCoordinateTwo2);
        // Print different axis data for two coordinates
        System.out.println("IntegerCoordinateTwo1: x=" + IntegerCoordinateTwo1.getX() + "  y=" + IntegerCoordinateTwo1.getY());
        System.out.println("IntegerCoordinateTwo2: x=" + IntegerCoordinateTwo2.getX() + "  y=" + IntegerCoordinateTwo2.getY());
        // Calculate the sum and difference of two integer 2D coordinates
        System.out.println(IntegerCoordinateTwo1.add(IntegerCoordinateTwo2));
        System.out.println(IntegerCoordinateTwo1.diff(IntegerCoordinateTwo2));

        //----------------------------------------------------------//

        // Get two integer 3D coordinates
        IntegerCoordinateThree IntegerCoordinateThree1 = new IntegerCoordinateThree(1, 2, 3);
        IntegerCoordinateThree IntegerCoordinateThree2 = new IntegerCoordinateThree(1, 2, 4);
        // print two coordinates
        System.out.println(IntegerCoordinateThree1);
        System.out.println(IntegerCoordinateThree2);
        // Print different axis data for two coordinates
        System.out.println("IntegerCoordinateTwo1: x=" + IntegerCoordinateThree1.getX() + "  y=" + IntegerCoordinateThree1.getY() + "  z=" + IntegerCoordinateThree1.getZ());
        System.out.println("IntegerCoordinateTwo2: x=" + IntegerCoordinateThree2.getX() + "  y=" + IntegerCoordinateThree2.getY() + "  z=" + IntegerCoordinateThree1.getZ());
        // Calculate the sum and difference of two integer 3D coordinates
        System.out.println(IntegerCoordinateThree1.add(IntegerCoordinateThree2));
        System.out.println(IntegerCoordinateThree1.diff(IntegerCoordinateThree2));

        //----------------------------------------------------------//

        // Get two integer multidimensional coordinates
        IntegerCoordinateMany IntegerCoordinateMany1 = new IntegerCoordinateMany(1, 2, 3, 4, 5);
        IntegerCoordinateMany IntegerCoordinateMany2 = new IntegerCoordinateMany(1, 2, 4, 3, 2);
        // print two coordinates
        System.out.println(IntegerCoordinateMany1);
        System.out.println(IntegerCoordinateMany2);
        // For multi-dimensional coordinates, we have no way to obtain them one by one, but we can use a method that all coordinates have, that is, to obtain an array of coordinate sequences.
        int[] doubles1 = IntegerCoordinateMany1.toArray();
        int[] doubles2 = IntegerCoordinateMany2.toArray();
        for (int i = 0; i < doubles1.length; i++) {
            System.out.println("The " + i + " coordinate of Integer Coordinate Many 1:" + doubles1[i]);
            System.out.println("The " + i + " coordinate of Integer Coordinate Many 2:" + doubles2[i]);
        }
        // Calculate the sum and difference of two integer multidimensional coordinates
        System.out.println(IntegerCoordinateThree1.add(IntegerCoordinateThree2));
        System.out.println(IntegerCoordinateThree1.diff(IntegerCoordinateThree2));
    }
}
```

## 坐标线路

A coordinate line object is a line object with start and end coordinate points. It can be expressed as a line from start
coordinate to end coordinate. It is an operand object implemented based on the coordinate object. Next, we will show the
general functions of coordinate lines.

- Functions in the interface

| 函数名称                    | 函数返回值              | 函数作用                                                                                                  |
|-------------------------|--------------------|-------------------------------------------------------------------------------------------------------|
| expand()                | ImplementationType | The object with the identity of a parent class is materialized into an object of a subclass.          |
| getEndPointCoordinate() | CoordinateType     | Gets the end point coordinates in the circuit object.                                                 |
| getStartingCoordinate() | CoordinateType     | Gets the starting point coordinates in the circuit object.                                            |
| getNumberOfDimensions() | int                | Gets the dimension quantity of the starting and ending coordinates in the circuit.                    |
| toDoubleVector()        | DoubleVector       | Get the vector object constructed by the starting point coordinates and the ending point coordinates。 |

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

public class Test {

    public static void main(String[] args) {
        // Get some coordinates
        IntegerCoordinateTwo A = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo B = new IntegerCoordinateTwo(1, 20);
        IntegerCoordinateTwo C = new IntegerCoordinateTwo(20, 2);
        // Build three lines
        IntegerConsanguinityRoute2D AB = IntegerConsanguinityRoute2D.parse("A -> B", A, B);
        IntegerConsanguinityRoute2D BC = IntegerConsanguinityRoute2D.parse("B -> C", B, C);
        IntegerConsanguinityRoute2D CA = IntegerConsanguinityRoute2D.parse("C -> A", C, A);
        // Calculate the three lines
        IntegerConsanguinityRoute2D AB_BC = AB.add(BC);
        IntegerConsanguinityRoute2D AB_CA = AB.add(CA);
        // Print calculation results
        System.out.println(AB_BC);
        System.out.println(AB_CA);
    }
}
```

## 坐标网络

坐标网络正如名称所示，其是一个网络，由多条线路对象构建成的网络，在在这条网络当中，将会准确的记录着路线之间的关系。

- Functions in the interface

| 函数名称                                            | 函数返回值              | 函数作用                                                                                         |
|-------------------------------------------------|--------------------|----------------------------------------------------------------------------------------------|
| expand()                                        | ImplementationType | The object with the identity of a parent class is materialized into an object of a subclass. |
| addRoute(RouteType routeType)                   | Boolean            | Add a line object to the network, and return true if it is added successfully.               |
| containsKeyFromRoute2DHashMap(String RouteName) | Boolean            | Judge whether a line exists in the line network. If yes, return true.                        |
| getNetDataSet()                                 | HashSet<RouteType> | Get the collection of all circuit objects.                                                   |
| getRouteCount()                                 | int                | Gets the number of circuit objects in the current network object.                            |

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRoute2DNet;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

public class Test {

    public static void main(String[] args) {
        // Get some coordinates
        IntegerCoordinateTwo A = new IntegerCoordinateTwo(1, 2);
        IntegerCoordinateTwo B = new IntegerCoordinateTwo(1, 20);
        IntegerCoordinateTwo C = new IntegerCoordinateTwo(20, 2);
        // Build three lines
        IntegerConsanguinityRoute2D AB = IntegerConsanguinityRoute2D.parse("A -> B", A, B);
        IntegerConsanguinityRoute2D BC = IntegerConsanguinityRoute2D.parse("B -> C", B, C);
        IntegerConsanguinityRoute2D CA = IntegerConsanguinityRoute2D.parse("C -> A", C, A);
        // Get a line network and add three lines
        IntegerRoute2DNet parse = IntegerRoute2DNet.parse(AB, BC, CA);
        // Extract "BC" route from line network
        System.out.println(parse.getRouteFromHashMap("B -> C"));
    }
}
```

## vector

A vector is the distance between two multidimensional coordinate points. It can be used for matrix construction and
various operation algorithms in the framework. The importance and coordinates are the same. The construction method is
roughly as follows.

```java
// Java api

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // Get a vector by assignment
        DoubleVector parse1 = DoubleVector.parse(1, 2, 3);
        // Get a vector by coordinates (unlimited dimensions)
        DoubleVector parse2 = DoubleVector.parse(new DoubleCoordinateThree(0, 0, 1), new DoubleCoordinateThree(0, 0, 2));
        // get the modulo of the vector
        System.out.println(parse1.moduleLength());
        System.out.println(parse2.moduleLength());
        // Get the addition, subtraction, multiplication of two vectors (vector product, outer product) Quantity product (inner product) TODO Note that the number of dimensions of the two vectors must be the same! Otherwise the operation will throw an OperandConversionException
        DoubleVector add = parse1.add(parse2);
        DoubleVector diff = parse1.diff(parse2);
        DoubleVector multiply = parse1.multiply(parse2);
        Double aDouble = parse1.innerProduct(parse2);
    }
}
```

## matrix

The matrix is similar to the superposition of many vectors. The structure of the matrix is relatively large compared to
the vector and coordinates. However, the internal structure processing takes this problem into account. The use of
primitives and objects reuse reduces performance consumption. In this framework, the matrix is not only Contains a
numeric matrix and a complex matrix. The following is a demonstration of the operation!

### Numerical matrix

Numeric matrix is a kind of characteristic matrix, in which the primitive numerical data types are stored. In the
machine learning library, integer and double-precision floating-point numerical matrix objects are supported, and have
different use opportunities in different scenarios.

```java
// Java api

import zhao.algorithmMagic.operands.ComplexNumber;
import zhao.algorithmMagic.operands.matrix.ComplexNumberMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // Build a matrix of floating point values from an array
        DoubleMatrix parse1 = DoubleMatrix.parse(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        DoubleMatrix parse2 = DoubleMatrix.parse(new double[]{7, 8, 9}, new double[]{10, 11, 12});
        // Computational matrix
        DoubleMatrix add = parse1.add(parse2);
        DoubleMatrix diff = parse1.diff(parse2);
        DoubleMatrix multiply = parse1.multiply(parse2);
        Double complexNumber = parse1.innerProduct(parse2);
        // Get matrix data by row and column values
        double v = parse1.get(1, 2);
        // print matrix directly
        System.out.println(parse1);

        //----------------------------------------------------------//

        // Construct a matrix of complex numbers by complex formula, where each array is a row of the matrix
        ComplexNumberMatrix parse11 = ComplexNumberMatrix.parse(
                new String[]{"1 + 2i", "3 + 4i"}, new String[]{"5 - 6i", "7 - 8i"}
        );
        // Constructs a complex matrix from complex objects, where each array is a row of the matrix
        ComplexNumberMatrix parse22 = ComplexNumberMatrix.parse(
                new ComplexNumber[]{ComplexNumber.parse(1, 2), ComplexNumber.parse(3, 4)},
                new ComplexNumber[]{ComplexNumber.parse(5, -6), ComplexNumber.parse(7, -8)}
        );
        // Computational matrix
        ComplexNumberMatrix add1 = parse11.add(parse22);
        ComplexNumberMatrix diff1 = parse11.diff(parse22);
        ComplexNumberMatrix multiply1 = parse11.multiply(parse22);
        ComplexNumber complexNumber1 = parse11.innerProduct(parse22);
        // Unique operations on complex matrices Conjugate
        ComplexNumberMatrix conjugate = parse11.conjugate();
        // Get matrix data by row and column values
        ComplexNumber complexNumber2 = parse11.get(1, 2);
        // print matrix directly
        System.out.println(parse11);
    }
}
```

### Image matrix

An image matrix is a matrix object that stores image pixel points. Each coordinate in the matrix is a pixel, and the
data type of each pixel is Color. You can call many built-in image matrix calculation functions, or directly operate
each pixel in the image through the toArrays function.

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

## ComplexNumber

As you can see, the conjugate operation in the matrix needs to use the complex number matrix, then the composition of
the complex number matrix is officially composed of complex numbers, and the parent class inheritance of complex numbers
also includes "Operands", which means that complex numbers can also support operators like In that way, operations such
as addition and subtraction are performed, and the following is a specific example!

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
## Table

Table is the data object used for data analysis in the AS database. Its representation is a table with row and column indexes, which can realize convenient data processing tasks. Data can be loaded and processed in the AS database through the DataFrame object.

### DataFrameBuilder & DataFrame

DataFrameBuilder and DataFrame are used for data loading and data analysis respectively. In the process of data loading, a DataFrame can be constructed through DataFameBuilder data object fast and easy to understand functions, and the DataFrame can be used for data processing.
DataFrame is called "DF" for short. In the data processing stage, many functions are designed in SQL style, which can effectively reduce learning costs and focus on more important things. Next, we will show the basic use of DataFrameBuilder.

#### Load data using FDataFrame
-Read Database
  In the AS database, you can load data into an FDataFrame data object, which can realize basic data reading and data processing functions, and effective data control. You can load data in the database into an FDataFrame. Next is the code example about database data loading.
    -It should be noted that when reading the database, please import the JDBC driver class in the project.

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
-Read file system
  For the reading of the file system, FDataFrame can easily read the local file system without relying on any third-party library. Next, we will implement the specific steps!

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
#### Comprehensive case

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
            .agg(series -> {
              StringBuilder stringBuilder = new StringBuilder();
              // 在这里合并名字
              for (Series cells : series) {
                stringBuilder.append(cells.getCell(0).getStringValue()).append(' ');
              }
              return new FinalSeries(
                      // 第一列是名字合并的字符串
                      new FinalCell<>(stringBuilder.toString()),
                      // 第二列是性别字段，也是分组字段，这里的所有Series的性别字段都是一样的，因此取第一个Series的第二列就好
                      new FinalCell<>(series.get(0).getCell(1))
              );
            });// 将每一组的人员名字合并 性别原样输出
    // 打印出结果
    System.out.println(select);
  }
}

```

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Operands-Chinese.md)

<hr>

#### date: 2022-10-15

#### update date：2023-02-27
