# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.13 - xxx

* It provides support for obtaining the original array for the integer vector. You can use toIntArray to obtain the
  array object of the integer vector.

* It optimizes the calculation between the integer vectors, reduces the internal call to the toArray method, and avoids
  redundancy.

* The string parsing logic of route objects and complex objects is optimized to reduce the number of operations

* For the printing of calculation logs of calculation components, this is an operation that requires huge performance.
  Therefore, in this update, you can manually intervene in the printing of calculation logs, as shown below.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.operands.vector.Vector;

public class MAIN1 {
    public static void main(String[] args) {
        // Open log output 
        // TODO This is optional. 
        //  If you do not want useless calculation logs, you can directly set it to false here or do not set this parameter, 
        //  which is false by default
        OperationAlgorithmManager.PrintCalculationComponentLog = true;
        CosineDistance<Vector<DoubleVector, IntegerVector>> zhao = CosineDistance.getInstance("zhao");
        double trueDistance = zhao.getTrueDistance(DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0), DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0));
        System.out.println(trueDistance);
    }
}
```

* The aggregation calculation module is added, which includes many aggregation calculation components. The following is
  an example of weighted average aggregation operation

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightedAverage;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // Aggregate calculation component weighted average
        WeightedAverage avg = WeightedAverage.getInstance("avg");
        // Build a vector
        DoubleVector doubleVector = DoubleVector.parse(20, 10, 40);
        // Calculate the average value and print it
        System.out.println(avg.calculation(doubleVector));
        // The setting of weight array makes the influence of 10 in this calculation higher
        double[] doubles = {1, 2, 1};
        // Calculate weighted average and print
        System.out.println(avg.calculation(doubles, doubleVector.toArray()));
    }
}
```

### Version update date : XX XX-XX-XX