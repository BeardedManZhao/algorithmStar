# 操作算法管理者

- Switch
  to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithmManager.md)

### 介绍

在本框架中，有关算法的获取可以使用 getInstance获取到，每一个算法对应这一个名字，这个名字会与算法一起进入到哈希集合中进行存储，一次存储多次使用。

您可以重复的获取一个算法，符合对象复用的同时，还可以使用管理者继续统一的管理，通过算法的名称去获取到他们，通过管理者获取到算法的操作如下所示，当然，如果您使用算法类中提供的"getInstance"效果是最好的。

### 示例

操作算法管理者本身就是一个全局唯一的类，您可以通过单例的方式获取到这个类，在您获取到这个类的时候，就可以通过算法管理者获取到您的目标算法对象了！

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.EuclideanMetric;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建两个坐标
        DoubleCoordinateTwo doubleCoordinateTwo1 = new DoubleCoordinateTwo(1, 3);
        DoubleCoordinateTwo doubleCoordinateTwo2 = new DoubleCoordinateTwo(1, 5);

        // 通过算法中的静态方法，获取到算法，这个时候，如果E不存在，那么这个算法本身就已经自动注册到了管理者中
        EuclideanMetric<IntegerCoordinateTwo, DoubleCoordinateTwo> e = EuclideanMetric.getInstance("E");

        // 通过管理者获取到欧几里德算法对象
        // 首先获取到管理者全局唯一的对象
        OperationAlgorithmManager operationAlgorithmManager = OperationAlgorithmManager.getInstance();
        // 获取到管理者中 名称为 E 的算法
        OperationAlgorithm e1 = operationAlgorithmManager.get("E");
        // 删除管理者中，名称为 E 的算法
        operationAlgorithmManager.unRegister("E");
    }
}

```

- Switch
  to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithmManager.md)
