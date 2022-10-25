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

import zhao.algorithmMagic.operands.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.DoubleCoordinateTwo;

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
        // For multi-dimensional coordinates, we have no way to obtain them one by one, but we can use a method that all coordinates have, that is, to obtain an array of coordinate sequences
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

import zhao.algorithmMagic.operands.DoubleCoordinateTwo

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

import zhao.algorithmMagic.operands.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.IntegerCoordinateThree;
import zhao.algorithmMagic.operands.IntegerCoordinateTwo;

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

## vector

A vector is the distance between two multi-dimensional coordinate points. It can be used for matrix construction and
various operation algorithms in the framework. The importance and coordinates are the same. The construction method is
roughly as follows.

```java
// Java api

import zhao.algorithmMagic.operands.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.DoubleVector;

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

```java
// Java api

import zhao.algorithmMagic.operands.ComplexNumber;
import zhao.algorithmMagic.operands.ComplexNumberMatrix;
import zhao.algorithmMagic.operands.DoubleMatrix;

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

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Operands-Chinese.md)

<hr>

#### date: 2022-10-15
