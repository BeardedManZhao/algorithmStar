# OperationAlgorithm

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm-Chinese.md)

## introduce

Algorithm is a module between operands and integrator in this framework, such as Euclidean, Rankine metric, etc., all
belong to one algorithm, currently there are distance algorithm, difference algorithm, normalization, generation
algorithm, etc.

In the example on the homepage of the framework, it is an implementation of a generation algorithm. The coordinates are
provided to the algorithm, and the algorithm automatically predicts and generates relevant personnel contact data, and
the integrator processes the results of the algorithm.

## API usage

There are many algorithms in the framework, and different algorithms have different effects. Several representative
algorithms are selected for demonstration below. The use of other algorithms is not much different. Some of the
architecture diagrams in the algorithm module are shown below.
![image](https://user-images.githubusercontent.com/113756063/195986247-5f3c65ec-27f8-4149-8349-ccca0f29766d.png)

### distance algorithm

The distance algorithm is mainly to study and calculate the distance between two coordinates. The famous Euclid is an
example. The use of the distance algorithm is as follows

```java
import zhao.algorithmMagic.algorithm.EuclideanMetric;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Build two coordinates
        DoubleCoordinateTwo doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 3);
        DoubleCoordinateTwo doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 5);
        // Get the Euclidean algorithm
        EuclideanMetric<IntegerCoordinateTwo, DoubleCoordinateTwo> e = EuclideanMetric.getInstance("E");
        // Calculate the distance between two coordinates
        System.out.println(e.getTrueDistance(doubleCoordinateTwo1, doubleCoordinateTwo2));
    }
}
```

### Difference algorithm

顾名思义就是比较两个值之间的差异，在这里比较具有代表性的就是汉明，它能够将两者之间的差异通过异或计算出来，对于统计不同之处的具体数量的需求，性能优秀！

```java
import zhao.algorithmMagic.algorithm.HammingDistance;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Get Hamming's Algorithm
        HammingDistance<IntegerCoordinateTwo, DoubleCoordinateTwo> h = HammingDistance.getInstance("H");
        // Count the total number of differences between two strings
        System.out.println(h.getMinimumNumberOfReplacements("Hello world!", "Hello algorithmStar"));
    }
}
```

### sequence normalization

It is beneficial to amplify or reduce the changes of certain dimensions by changing the data with very extreme
dimensions to a data sequence within a certain range in the same proportion and so on.

```java
import zhao.algorithmMagic.algorithm.normalization.LinearNormalization;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // build a sequence
        DoubleVector parse = DoubleVector.parse(1, 2, 3, 100);
        // Get the linear normalization algorithm
        LinearNormalization l = LinearNormalization.getInstance("L");
        // Normalize the above vector
        DoubleVector doubleVector = l.NormalizedSequence(parse);
        // print the normalized vector
        System.out.println(doubleVector);
    }
}
```

### path generation

It is a new algorithm developed by me. It is mainly used to automatically analyze the relationship between coordinates
and coordinates. If possible, adding a new coordinate will generate some related new connections. This algorithm is used
in the homepage to conduct a personal and Relationship between people inferred!

```java
import zhao.algorithmMagic.algorithm.LingYuZhaoCoordinateNet;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Construct the coordinates of three persons A B C
        DoubleCoordinateMany A = new DoubleCoordinateMany(1, 0);
        DoubleCoordinateMany B = new DoubleCoordinateMany(1, 3);
        DoubleCoordinateMany C = new DoubleCoordinateMany(2, 5);
        // Get the coordinate network generation algorithm
        LingYuZhaoCoordinateNet l = LingYuZhaoCoordinateNet.getInstance("L");
        // Add the relationship that A actively knows B to the network
        l.addRoute(DoubleConsanguinityRoute.parse("A -> B", A, B));
        // Add the relationship that A actively knows C to the network
        l.addRoute(DoubleConsanguinityRoute.parse("A -> C", A, C));
        // A takes the initiative to know B and then takes the initiative to know C, so it is very likely that B knows C, print whether the relationship between B and C will be generated
        System.out.println(l.get("B -> C"));
        // Print the relationship between C and B
        System.out.println(l.get("C -> B"));
        /*------------------------------------------------------------------------
                                        operation result
          B(DoubleCoordinateMany=[1.0, 3.0]) -> C(DoubleCoordinateMany=[2.0, 5.0])
          null
         ------------------------------------------------------------------------*/
    }
}
```

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm-Chinese.md)
