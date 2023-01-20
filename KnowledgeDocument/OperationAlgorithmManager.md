# OperationAlgorithmManager

-

切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithmManager-Chinese.md)

### 介绍

In this framework, the relevant algorithms can be obtained by using getInstance. Each algorithm corresponds to this
name, and this name will be stored in the hash set together with the algorithm, which can be stored once and used
multiple times.

You can repeatedly obtain an algorithm, which is in line with object reuse, and you can also use the manager to continue
unified management, and obtain them by the name of the algorithm. The operation of obtaining the algorithm through the
manager is as follows. Of course, if you Using the "get Instance" provided in the algorithm class works best.

### 示例

The operation algorithm manager itself is a globally unique class. You can obtain this class through a singleton. When
you obtain this class, you can obtain your target algorithm object through the algorithm manager!

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.EuclideanMetric;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        /*
         The algorithm is obtained through the static method in the algorithm. 
         At this time, if E does not exist, then the algorithm itself has been automatically registered in the manager
         */
        EuclideanMetric<IntegerCoordinateTwo, DoubleCoordinateTwo> e = EuclideanMetric.getInstance("E");

        // Get the Euclidean algorithm object through the manager
        // First get the globally unique object of the manager
        OperationAlgorithmManager operationAlgorithmManager = OperationAlgorithmManager.getInstance();
        // Get the algorithm named E in the manager
        OperationAlgorithm e1 = operationAlgorithmManager.get("E");
        // Delete the algorithm named E in the manager
        operationAlgorithmManager.unRegister("E");
    }
}

```

-

切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithmManager-Chinese.md)
