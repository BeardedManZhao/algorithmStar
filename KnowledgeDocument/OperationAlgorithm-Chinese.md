# 操作算法

- Switch
  to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm.md)

## 介绍

算法是本框架中介于操作数与集成器之间的一个模块，例如欧几里德，兰氏度量等，都属于一个算法，目前主要有距离算法，差异算法，归一化，生成算法等。

在框架主页中的示例中，是一个生成算法的实现，将坐标提供给算法，算法自动地预测并生成有关的人员联系数据，由集成器去对算法的结果进行处理。

## API使用

在框架中有许多的算法，不同算法有不同效果，下面挑选出来了几个具有代表性的算法进行演示，其余算法的使用相差不大，算法模块中的部分架构图如下所示
![image](https://user-images.githubusercontent.com/113756063/195986247-5f3c65ec-27f8-4149-8349-ccca0f29766d.png)

### 距离算法

距离算法主要就是针对两个坐标之间的距离进行研究与计算，著名的欧几里德为例，距离算法的使用如下所示

```java
import zhao.algorithmMagic.algorithm.EuclideanMetric;
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
        // 获取到欧几里德算法
        EuclideanMetric<IntegerCoordinateTwo, DoubleCoordinateTwo> e = EuclideanMetric.getInstance("E");
        // 计算两个坐标之间的距离
        System.out.println(e.getTrueDistance(doubleCoordinateTwo1, doubleCoordinateTwo2));
    }
}
```

### 差异算法

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
        // 获取汉明算法
        HammingDistance<IntegerCoordinateTwo, DoubleCoordinateTwo> h = HammingDistance.getInstance("H");
        // 计算两个字符串之间的差异处总数
        System.out.println(h.getMinimumNumberOfReplacements("Hello world!", "Hello algorithmStar"));
    }
}
```

### 序列归一化

将某些维度很偏激的数据，进行同等比例等方式改变到一定区间之内的数据序列，有利于放大或缩小某些维度的变化等。

```java
import zhao.algorithmMagic.algorithm.normalization.LinearNormalization;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建一个序列
        DoubleVector parse = DoubleVector.parse(1, 2, 3, 100);
        // 获取到线性归一化算法
        LinearNormalization l = LinearNormalization.getInstance("L");
        // 对上面的向量进行归一化
        DoubleVector doubleVector = l.NormalizedSequence(parse);
        // 打印归一化之后的向量
        System.out.println(doubleVector);
    }
}
```

### 路径生成

是我开发的一种新算法，主要用来自动解析坐标与坐标之间的联系，如果有可能的话，新增一个坐标就会生成一些有关的新联系，主页中使用这个算法进行了一个人与人之间的关系推断！

```java
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建A B C三个人的坐标
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(1, 0);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(1, 3);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(2, 5);
        // 获取到坐标网生成算法
        ZhaoCoordinateNet2D l = ZhaoCoordinateNet2D.getInstance("L");
        // 将 A 主动认识 B的关系添加到网络中
        l.addRoute(DoubleConsanguinityRoute2D.parse("A -> B", A, B));
        // 将 A 主动认识 C的关系添加到网络中
        l.addRoute(DoubleConsanguinityRoute2D.parse("A -> C", A, C));
        // A 主动认识 B 然后主动认识C 所以很有可能 B 认识了 C ，打印一下 B 指向 C的关系是否会生成
        System.out.println(l.get("B -> C"));
        // 打印一下 C 指向 B 的关系
        System.out.println(l.get("C -> B"));
        /*------------------------------------------------------------------------
                                        运行结果
            B(1.0,3.0) -> C(2.0,5.0)
            null
         ------------------------------------------------------------------------*/
    }
}
```

### 聚合计算
聚合计算组件，于 1.14 版本之后开始支持使用，是专用于多个元素聚合计算的操作算法组件，在库中有各类聚合操作算法，各类聚合计算组件在下面的表格中进行了相关介绍！

- 聚合计算组件列表

| 计算组件类型                                                                 | 支持版本 | 功能                 |
|------------------------------------------------------------------------|------|--------------------|
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.ExtremumAggregation | v1.0 | 计算一些数值的极值          |
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightedAverage     | v1.0 | 计算一些数值的加权平均数       |
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.ModularOperation    | v1.0 | 计算一个序列或多个序列聚合之后的模长 |

本次以数值的极值计算为例，展示聚合组件的功能
```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.aggregationAlgorithm.ExtremumAggregation;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 聚合计算组件 极值计算
        ExtremumAggregation extremum = ExtremumAggregation.getInstance("Extremum");
        // 构建一个向量
        DoubleVector doubleVector = DoubleVector.parse(6, 2, 4, 5, 1, 9, 6, 19, 45);
        // 设置获取最小值
        extremum.setMode(ExtremumAggregation.MIN);
        // 开始计算并打印最小值
        System.out.println(extremum.calculation(doubleVector));
        // 设置获取最大值
        extremum.setMode(ExtremumAggregation.MAX);
        // 开始计算并打印最大值
        System.out.println(extremum.calculation(doubleVector));
        // 设置获取奇数最小值
        extremum.setMode(ExtremumAggregation.ODD_MIN);
        System.out.println(extremum.calculation(doubleVector));
        // 设置获取偶数最大值
        extremum.setMode(ExtremumAggregation.EVEN_MAX);
        System.out.println(extremum.calculation(doubleVector));
    }
}
```

- Switch
  to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm.md)

<hr>

### warning：在1.0版本中，请您尽量避免“getTrueDistance(DoubleVector doubleVector)”的调用，因为该函数是不合理的存在，在后面的版本中，该函数被删除！！！！会使用更加合适的函数作为替代。
