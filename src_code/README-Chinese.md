# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.13 - xxx
* 为整形向量提供了原数组的获取支持，可以使用toIntArray获取到整形向量的数组对象。
* 为整形向量之间的计算进行了优化，减少了在内部的toArray方法的调用，避免冗余。
* 针对路线对象与复数对象的字符串解析的逻辑进行了优化，减少操作的数量
* 针对计算组件的计算日志打印，这是一个需要巨大性能的操作，因此在本次更新中，您可以手动干预计算日志的打印情况，具体操作方式如下所示。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.operands.vector.Vector;

public class MAIN1 {
    public static void main(String[] args) {
        // 打开日志输出 TODO 这是可选的，如果您不想要无用的计算日志，您可以直接在这里设置为false或是不进行该参数的设置，该参数默认为false
        OperationAlgorithmManager.PrintCalculationComponentLog = true;
        CosineDistance<Vector<DoubleVector, IntegerVector>> zhao = CosineDistance.getInstance("zhao");
        double trueDistance = zhao.getTrueDistance(DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0), DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0));
        System.out.println(trueDistance);
    }
}
```

* 增加了聚合计算模块，其中包含诸多聚合类计算组件，下面是有关加权平均值聚合运算的示例

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightedAverage;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 聚合计算组件 加权平均值
        WeightedAverage avg = WeightedAverage.getInstance("avg");
        // 构建一个向量
        DoubleVector doubleVector = DoubleVector.parse(20, 10, 40);
        // 计算平均值并打印
        System.out.println(avg.calculation(doubleVector));
        // 设置权重数组 这样的设置使得 10 这个数值在本次计算中的影响占比会比较高
        double[] doubles = {1, 2, 1};
        // 计算加权平均值并打印
        System.out.println(avg.calculation(doubles, doubleVector.toArray()));
    }
}
```

### Version update date : XX XX-XX-XX