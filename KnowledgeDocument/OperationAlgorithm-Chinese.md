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

距离算法主要就是针对两个坐标之间的距离进行研究与计算，著名的欧几里德为例，距离算法的使用如下所示，其它计算组件的使用方式与欧几里得相同，其它组件在下面也有介绍。

- 距离计算组件列表

| 计算组件类型                                                                        | 支持版本 | 功能          |
|-------------------------------------------------------------------------------|------|-------------|
| zhao.algorithmMagic.algorithm.distanceAlgorithm.EuclideanMetric               | v1.0 | 计算欧几里得距离    |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.CanberraDistance              | v1.0 | 计算堪培拉距离     |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.ChebyshevDistance             | v1.0 | 计算切比雪夫距离    |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance                | v1.0 | 计算向量余弦度量    |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.HausdorffDistance             | v1.0 | 计算豪斯多夫距离    |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance             | v1.0 | 计算曼哈顿距离     |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.MinkowskiDistance             | v1.0 | 计算闵可夫斯基距离   |
| zhao.algorithmMagic.algorithm.distanceAlgorithm.StandardizedEuclideanDistance | v1.0 | 计算标准化欧几里得度量 |

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

- 差异计算组件列表

| 计算组件类型                                                                         | 支持版本 | 功能                   |
|--------------------------------------------------------------------------------|------|----------------------|
| zhao.algorithmMagic.algorithm.differenceAlgorithm.BrayCurtisDistance           | v1.0 | 计算两个数据样本之间的布雷柯蒂斯差异系数 |
| zhao.algorithmMagic.algorithm.differenceAlgorithm.DiceCoefficient              | v1.0 | 计算两个数据样本之间的Dice差异系数  |
| zhao.algorithmMagic.algorithm.differenceAlgorithm.EditDistance                 | v1.0 | 计算两个数据样本之间的最小编辑次数    |
| zhao.algorithmMagic.algorithm.differenceAlgorithm.HammingDistance              | v1.0 | 计算两个数据样本之间的汉明差异系数    |
| zhao.algorithmMagic.algorithm.differenceAlgorithm.JaccardSimilarityCoefficient | v1.0 | 计算两个数据样本之间的杰卡德相似系数   |

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

### 特征预处理

通过⼀些转换函数将特征数据转换成更加适合算法模型的特征数据过程，例如归一化可以将某些维度很偏激的数据，进行同等比例等方式改变到一定区间之内的数据序列，有利于放大或缩小某些维度的变化等。

- 预处理计算组件列表

| 计算组件类型                                                           | 支持版本 | 功能                    |
|------------------------------------------------------------------|------|-----------------------|
| zhao.algorithmMagic.algorithm.normalization.LinearNormalization  | v1.0 | 将一个向量数据样本进行线性归一化      |
| zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization | v1.0 | 将一个向量数据样本进行正负均匀分配的标准化 |

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

是一种新路线推导算法，其中包含很多中路线算法，主要用来自动解析坐标与坐标之间的联系，如果有可能的话，新增一个坐标就会生成一些有关的新联系，主页中使用这个算法进行了一个人与人之间的关系推断！

- 路径推导算法列表

| 计算组件类型                                                                  | 支持版本 | 功能                           |
|-------------------------------------------------------------------------|------|------------------------------|
| zhao.algorithmMagic.algorithm.generatingAlgorithm.Dijkstra              | v1.0 | 计算一个路线网站中的最小距离               |
| zhao.algorithmMagic.algorithm.generatingAlgorithm.Dijkstra2D            | v1.0 | 计算一个路线网站中的最小距离               |
| zhao.algorithmMagic.algorithm.generatingAlgorithm.DirectionalDijkstra2D | v1.0 | 计算一个路线网站中的最小距离               |
| zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet     | v1.0 | 计算一个路线网站潜在联系，并生成对应的路线对象到路线网中 |
| zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D   | v1.0 | 计算一个路线网站潜在联系，并生成对应的路线对象到路线网中 |

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

| 计算组件类型                                                                 | 支持版本  | 功能                 |
|------------------------------------------------------------------------|-------|--------------------|
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.ExtremumAggregation | v1.14 | 计算一些数值的极值          |
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightedAverage     | v1.14 | 计算一些数值的加权平均数       |
| zhao.algorithmMagic.algorithm.aggregationAlgorithm.ModularOperation    | v1.14 | 计算一个序列或多个序列聚合之后的模长 |

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

### 特征提取

特征提取组件，于1.14版本之后开始出现，专注于将某些数据的特征转换成为计算机能够处理的数据，例如向量等。

- 特征提取组件列表

| 计算组件类型                                                                | 支持版本  | 功能                 |
|-----------------------------------------------------------------------|-------|--------------------|
| zhao.algorithmMagic.algorithm.featureExtraction.DictFeatureExtraction | v1.14 | 对一些字符串数据进行子典特征提取   |
| zhao.algorithmMagic.algorithm.featureExtraction.WordFrequency         | v1.14 | 对一些字符串数据进行词频向量特征提取 |

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.featureExtraction.DictFeatureExtraction;
import zhao.algorithmMagic.algorithm.featureExtraction.WordFrequency;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到字典特征提取组件
        DictFeatureExtraction dict = DictFeatureExtraction.getInstance("dict");
        // 构造一个需要被提取的数组
        String[] strings = {
                "cat", "dog", "turtle", "fish", "cat"
        };
        // 开始提取特征矩阵
        ColumnIntegerMatrix extract = dict.extract(strings);
        // 打印矩阵
        System.out.println(extract);
        // 打印矩阵的hashMap形式
        extract.toHashMap().forEach((key, value) -> System.out.println(value.toString() + '\t' + key));

        System.out.println("================================================");

        // 获取到词频特征提取组件
        WordFrequency word = WordFrequency.getInstance("word");
        // 构建一些被统计的文本
        String[] data = {
                "I love you, Because you are beautiful.",
                "I need you. Because I'm trapped"
        };
        // 开始统计
        ColumnIntegerMatrix extract1 = word.extract(data);
        // 打印结果
        System.out.println(extract1);
    }
}
```

### 特征分类

分类组件，顾名思义根据目标样本与待处理样本之间的差距进行分类行为，在不同的分类组件中有不同的分类函数实现，具体的分类组件，请参阅下面的示例

- 特征分类组件列表

| 计算组件类型                                                                          | 支持版本  | 功能                           |
|---------------------------------------------------------------------------------|-------|------------------------------|
| zhao.algorithmMagic.algorithm.classificationAlgorithm.UDFDistanceClassification | v1.14 | 利用手动传入类别样本的方式，进行距离计算并分类      |
| zhao.algorithmMagic.algorithm.classificationAlgorithm.KnnClassification         | v1.14 | 利用K 近邻算法将最近的K个特征进行距离计算，进行分类。 |

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KnnClassification;
import zhao.algorithmMagic.algorithm.classificationAlgorithm.UDFDistanceClassification;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

public final class MAIN1 {
    public static void main(String[] args) {
        // TODO 使用自定义距离分类计算组件进行分类
        int[] ints1 = {101, 10};
        int[] ints2 = {1, 81};
        int[] ints3 = {101, 81};
        // 准备一个矩阵，其中行字段名就是目标类别，列字段就是目标特征
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                new String[]{"打人", "接吻"},
                new String[]{"动作", "爱情", "爱情动作", "未知1", "未知2"},
                ints1, ints2, ints3,
                new int[]{18, 90},
                new int[]{181, 90}
        );
        // 先打印一下需要分类的特征矩阵
        System.out.println(parse);
        // 开始构建类别样本
        HashMap<String, int[]> hashMap = new HashMap<>();
        hashMap.put("动作", ints1);
        hashMap.put("爱情", ints2);
        hashMap.put("爱情动作", ints3);
        // 开始计算类别，先获取到KNN计算组件
        UDFDistanceClassification udf = UDFDistanceClassification.getInstance("udf");
        HashMap<String, ArrayList<IntegerVector>> classification = udf.classification(parse, hashMap);
        // 开始打印类别
        classification.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));


        // TODO 使用KNN近邻计算组件进行分类
        KnnClassification knn = KnnClassification.getInstance("knn");
        HashMap<String, ArrayList<IntegerVector>> classification1 = knn.classification(
                // 这里代表类别名称 ? 是没有确定的类别
                new String[]{"动作", "?", "爱情", "?", "爱情动作"},
                // 这里是所有需要被确定的类别数据行
                // 值得注意的是，需要保证类别名称数量和数据行数量一致
                new int[][]{
                        // 其中的第一列代表打斗次数 第二列代表接吻次数
                        new int[]{101, 10},
                        new int[]{18, 90},
                        new int[]{1, 81},
                        new int[]{181, 90},
                        new int[]{101, 81}
                }
        );
        // 开始打印类别
        classification1.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));
    }
}
```

```
------------MatrixStart-----------
打人	接吻	rowColName
[101, 10]	动作
[1, 81]	爱情
[101, 81]	爱情动作
[18, 90]	未知1
[181, 90]	未知2
------------MatrixEnd------------
[INFO][OperationAlgorithmManager][23-01-16:08]] : register OperationAlgorithm:EuclideanMetric
[INFO][OperationAlgorithmManager][23-01-16:08]] : register OperationAlgorithm:udf
类别：爱情动作	[[ 101 81 ], [ 181 90 ]]
类别：爱情	[[ 1 81 ], [ 18 90 ]]
类别：动作	[[ 101 10 ]]
[INFO][OperationAlgorithmManager][23-01-16:08]] : An operation algorithm was obtained:EuclideanMetric
[INFO][OperationAlgorithmManager][23-01-16:08]] : register OperationAlgorithm:knn
类别：爱情动作	[[ 181 90 ]]
类别：爱情	[[ 18 90 ]]
```

### 概率计算与分类

概率计算其本质也属于分类组件，例如朴素贝叶斯算法等，所有的概率计算组件都需要传递自定义的两个事件过滤对象，它是一种P(A|B)样式的计算结果，计算函数中的第一个事件过滤器扮演A事件，第二个事件过滤器扮演B事件

- 概率计算组件列表

| 计算组件类型                                                          | 支持版本  | 功能                             |
|-----------------------------------------------------------------|-------|--------------------------------|
| zhao.algorithmMagic.algorithm.probabilisticAlgorithm.NaiveBayes | v1.14 | 通过较小的计算量计算出形如”P(A\B)“事件发生的概率数值 |

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.probabilisticAlgorithm.NaiveBayes;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

public final class MAIN1 {
    public static void main(String[] args) {
        String[] strings1 = {"职业", "体型", "喜欢"};
        // 准备一个数据矩阵
        // 职业：1-程序员  2-产品  3-美工
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                strings1,
                new String[]{"N1", "N2", "N3", "N4", "N5", "N6", "N7", "N8", "N9", "N10"},
                new int[]{1, 1, 0},
                new int[]{2, 0, 1},
                new int[]{1, 0, 1},
                new int[]{1, 1, 1},
                new int[]{3, 0, 0},
                new int[]{3, 1, 0},
                new int[]{2, 0, 1},
                new int[]{2, 1, 1},
                new int[]{2, 1, 0},
                new int[]{2, 1, 0}
        );
        System.out.println(parse);
        // 打乱样本 删除原先的矩阵，并打印新矩阵
        parse = parse.shuffle(22);
        System.out.println(parse);
        // 开始获取朴素贝叶斯算法 计算目标：在自己是产品同时超重的情况下，被喜欢的概率 P(被喜欢|产品,超重)
        NaiveBayes bayes = NaiveBayes.getInstance("bayes");
        // 构造事件A 自己被喜欢
        ArrayIntegerFiltering arrayIntegerFilteringA = v -> v[2] == 1;
        // 构造事件B 自己是产品，同时超重
        ArrayIntegerFiltering arrayIntegerFilteringB = v -> v[0] == 2 && v[1] == 1;
        // 开始计算结果
        System.out.println(bayes.estimate(parse, arrayIntegerFilteringA, arrayIntegerFilteringB));
    }
}
```

- 计算结果展示

```
------------IntegerMatrixStart-----------
职业	体型	喜欢	rowColName
[1, 1, 0]	N1
[2, 0, 1]	N2
[1, 0, 1]	N3
[1, 1, 1]	N4
[3, 0, 0]	N5
[3, 1, 0]	N6
[2, 0, 1]	N7
[2, 1, 1]	N8
[2, 1, 0]	N9
[2, 1, 0]	N10
------------IntegerMatrixEnd------------

------------IntegerMatrixStart-----------
职业	体型	喜欢	rowColName
[1, 0, 1]	N3
[2, 0, 1]	N2
[1, 1, 1]	N4
[3, 0, 0]	N5
[3, 1, 0]	N6
[2, 1, 0]	N9
[2, 0, 1]	N7
[2, 1, 0]	N10
[1, 1, 0]	N1
[2, 1, 1]	N8
------------IntegerMatrixEnd------------

[INFO][OperationAlgorithmManager][23-01-19:12]] : register OperationAlgorithm:bayes
0.8333333333333334

进程已结束,退出代码0
```

### 决策计算组件

决策算法抽象类，是针对多种处理方案进行最优排列或优化等的优秀算法，在库中有着决策树等算法的实现，接下来就是展示的有关决策树的使用方式

- 决策计算组件列表

| 计算组件类型                                                     | 支持版本  | 功能                                         |
|------------------------------------------------------------|-------|--------------------------------------------|
| zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree | v1.14 | 决策树计算组件，计算出最有效率的筛选路径，并按照路径将传递进来的事件处理函数进行排列 |

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.util.ArrayList;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些相关联的数据，本次要求将与年龄相关联的数据全部删掉
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new int[]{1, 1, 1},
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0},
                new int[]{1, 1, 0},
                new int[]{0, 1, 0}
        );
        System.out.println(columnDoubleMatrix);
        // 构建一些事件过滤器
        // 有钱选项为1
        ArrayIntegerFiltering arrayIntegerFiltering1 = v -> v[2] == 1;
        // 身高选项为1
        ArrayIntegerFiltering arrayIntegerFiltering2 = v -> v[1] == 1;
        // 颜值选项为1
        ArrayIntegerFiltering arrayIntegerFiltering3 = v -> v[0] == 1;
        System.out.println(arrayIntegerFiltering1);
        System.out.println(arrayIntegerFiltering2);
        System.out.println(arrayIntegerFiltering3);
        // 获取到决策树
        DecisionTree d = DecisionTree.getInstance("d");
        // 设置精准模式
        d.setAccurate(true);
        // 设置中心字段索引
        d.setGroupIndex(0);
        // 开始计算最优方案
        ArrayList<ArrayIntegerFiltering> decision = d.decision(columnDoubleMatrix, arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3);
        // 将最优方案传递给决策树执行，并接收返回的结果
        String s = d.executeGetString(columnDoubleMatrix.toArrays(), decision);
        System.out.println(s);
    }
}
```

```
------------IntegerMatrixStart-----------
颜值	身高	有钱	
[1, 1, 1]
[1, 0, 1]
[0, 1, 0]
[0, 0, 0]
[0, 1, 0]
[1, 0, 0]
[1, 1, 0]
[0, 1, 0]
------------IntegerMatrixEnd------------

zhao.algorithmMagic.MAIN1$$Lambda$1/8468976@372a00
zhao.algorithmMagic.MAIN1$$Lambda$2/26887603@dd8dc3
zhao.algorithmMagic.MAIN1$$Lambda$3/10069385@103e736
[INFO][OperationAlgorithmManager][23-01-20:09]] : +============================== Welcome to [mathematical expression] ==============================+
[INFO][OperationAlgorithmManager][23-01-20:09]] : + 	Start time Fri Jan 20 21:17:44 CST 2023
[INFO][OperationAlgorithmManager][23-01-20:09]] : + 	version: 1.13
[INFO][OperationAlgorithmManager][23-01-20:09]] : + 	Calculation component manager initialized successfully
[INFO][OperationAlgorithmManager][23-01-20:09]] : + 	For more information, see: https://github.com/BeardedManZhao/algorithmStar.git
[INFO][OperationAlgorithmManager][23-01-20:09]] : +--------------------------------------------------------------------------------------------------+
[INFO][OperationAlgorithmManager][23-01-20:09]] : register OperationAlgorithm:d

* >>> Tier 1 Decision
True => [1, 1, 1]
True => [1, 0, 1]
True => [1, 0, 0]
True => [1, 1, 0]
False => [0, 1, 0]
False => [0, 0, 0]
False => [0, 1, 0]
False => [0, 1, 0]

* >>> Tier 2 Decision
True => [1, 1, 1]
True => [1, 0, 1]
False => [1, 0, 0]
False => [1, 1, 0]

* >>> Tier 3 Decision
True => [1, 1, 1]
False => [1, 0, 1]


进程已结束,退出代码0

```

- Switch
  to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm.md)

<hr>

### warning：在1.0版本中，请您尽量避免“getTrueDistance(DoubleVector doubleVector)”的调用，因为该函数是不合理的存在，在后面的版本中，该函数被删除！！！！会使用更加合适的函数作为替代。
