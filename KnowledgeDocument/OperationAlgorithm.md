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
algorithms are selected for demonstration below. The use of other algorithms is not much different. Some architecture
diagrams in the algorithm module are shown below.
![image](https://user-images.githubusercontent.com/113756063/195986247-5f3c65ec-27f8-4149-8349-ccca0f29766d.png)

### distance algorithm

The distance algorithm is mainly to study and calculate the distance between two coordinates. The famous Euclid is an
example. The use of the distance algorithm is as follows

- List of distance calculation components

| Calculation component type                                                                       | Supported versions | function                                  |
|--------------------------------------------------------------------------------------------------|--------------------|-------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.EuclideanMetric               | v1.0               | Calculate Euclidean distance              |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.CanberraDistance              | v1.0               | Calculate Canberra distance               |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.ChebyshevDistance             | v1.0               | Calculate Chebyshev distance              |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.CosineDistance                | v1.0               | Calculate vector cosine measure           |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.HausdorffDistance             | v1.0               | Calculate Hausdorff distance              |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.ManhattanDistance             | v1.0               | Calculate Manhattan distance              |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.MinkowskiDistance             | v1.0               | Calculate Minkowski distance              |
| io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.StandardizedEuclideanDistance | v1.0               | Calculate standardized Euclidean measures |

```java
import io.github.beardedManZhao.algorithmStar.algorithm.EuclideanMetric;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateTwo;

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

As the name implies, it is to compare the differences between two values. Hanming is a typical one here. It can
calculate the differences between the two by XOR, and has excellent performance for counting the specific quantity of
differences!

- List of variance calculation components

| Calculation component type                                                                         | Supported versions | function                                                                   |
|----------------------------------------------------------------------------------------------------|--------------------|----------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm.BrayCurtisDistance            | v1.0               | Calculate the Brecurtis coefficient of difference between two data samples |
| io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm.DiceCoefficient               | v1.0               | Calculate the Dice difference coefficient between two data samples         |
| io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm.EditDistance                  | v1.0               | Calculate the minimum number of edits between two data samples             |
| io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm.HammingDistance               | v1.0               | Calculate the Hamming difference coefficient between two data samples      |
| io.github.beardedManZhao.algorithmStar.algorithm.differenceAlgorithm.JacquardSimilarityCoefficient | v1.0               | Calculate the Jakad similarity coefficient between two data samples        |

```java
import io.github.beardedManZhao.algorithmStar.algorithm.HammingDistance;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateTwo;

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

### Feature preprocessing

The process of transforming feature data into feature data more suitable for the algorithm model through some
transformation functions, for example, normalization can change some data with extreme dimensions to data series within
a certain range by means of equal proportion, which is conducive to enlarging or reducing the changes of some
dimensions.

- Standardized calculation component list

| Calculation component type                                                          | Supported versions | function                                                                           |
|-------------------------------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.normalization.LinearNormalization  | v1.0               | Linear normalization of a vector data sample                                       |
| io.github.beardedManZhao.algorithmStar.algorithm.normalization.Z_ScoreNormalization | v1.0               | Standardize the positive and negative uniform distribution of a vector data sample |

```java
import io.github.beardedManZhao.algorithmStar.algorithm.normalization.LinearNormalization;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;

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
        DoubleVector doubleVector = l.pretreatment(parse);
        // print the normalized vector
        System.out.println(doubleVector);
    }
}
```

### path generation

It is a new algorithm developed by me. It is mainly used to automatically analyze the relationship between coordinates
and coordinates. If possible, adding a new coordinate will generate some related new connections. This algorithm is used
in the homepage to conduct a personal and Relationship between people inferred!

- 路径推导算法列表

| Calculation component type                                                                 | Supported versions | function                                                                                                              |
|--------------------------------------------------------------------------------------------|--------------------|-----------------------------------------------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.Dijkstra              | v1.0               | Calculate the minimum distance in a route website                                                                     |
| io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.Dijkstra2D            | v1.0               | Calculate the minimum distance in a route website                                                                     |
| io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.DirectionalDijkstra2D | v1.0               | Calculate the minimum distance in a route website                                                                     |
| io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.ZhaoCoordinateNet     | v1.0               | Calculate the potential contact of a route website and generate the corresponding route object into the route network |
| io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D   | v1.0               | Calculate the potential contact of a route website and generate the corresponding route object into the route network |

```java
import io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Construct the coordinates of three persons A B C
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(1, 0);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(1, 3);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(2, 5);
        // Get the coordinate network generation algorithm
        ZhaoCoordinateNet2D l = ZhaoCoordinateNet2D.getInstance("L");
        // Add the relationship that A actively knows B to the network
        l.addRoute(DoubleConsanguinityRoute2D.parse("A -> B", A, B));
        // Add the relationship that A actively knows C to the network
        l.addRoute(DoubleConsanguinityRoute2D.parse("A -> C", A, C));
        // A takes the initiative to know B and then takes the initiative to know C, so it is very likely that B knows C, print whether the relationship between B and C will be generated
        System.out.println(l.get("B -> C"));
        // Print the relationship between C and B
        System.out.println(l.get("C -> B"));
        /*------------------------------------------------------------------------
                                        operation result
            B(1.0,3.0) -> C(2.0,5.0)
            null
         ------------------------------------------------------------------------*/
    }
}
```

### Aggregate Calculation

Aggregation calculation component, which is supported since version 1.14, is an operation algorithm component dedicated
to aggregation calculation of multiple elements. There are various aggregation operation algorithms in the library.
Various aggregation calculation components are described in the following table!

- Aggregate Calculation Component List

| Calculation component type                                                                | Supported versions | function                                                               |
|-------------------------------------------------------------------------------------------|--------------------|------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm.ExtremumAggregation | v1.14              | Calculate the extreme value of some values                             |
| io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm.WeightedAverage     | v1.14              | Calculate the weighted average of some values                          |
| io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm.ModularOperation    | v1.14              | Calculate the module length after aggregation of one or more sequences |

This time, the extreme value calculation of numerical value is taken as an example to show the functions of aggregation
components

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm.ExtremumAggregation;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;

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

### feature extraction

The feature extraction component, which began to appear after version 1.14, focuses on transforming the features of some
data into data that can be processed by computers, such as vectors.

- Feature extraction component list

| Calculation component type                                                               | Supported versions | function                                                           |
|------------------------------------------------------------------------------------------|--------------------|--------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.DictFeatureExtraction | v1.14              | Extract the sub-dictionary features of some string data            |
| io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.WordFrequency         | v1.14              | Extract the feature of word frequency vector from some string data |

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.DictFeatureExtraction;
import io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.WordFrequency;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;

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

### Feature classification

Classification component, as the name implies, classifies according to the difference between the target sample and the
sample to be processed. There are different classification functions in different classification components. For
specific classification components, see the following example.

- Feature classification component list

| Calculation component type                                                                         | Supported versions | function                                                                                                    |
|----------------------------------------------------------------------------------------------------|--------------------|-------------------------------------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm.UDFDistanceClassification | v1.14              | Calculate and classify the distance by manually passing in the category sample                              |
| io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm.KnnClassification         | v1.14              | K-nearest neighbor algorithm is used to calculate the distance of the nearest K features and classify them. |
| io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm.KMeans                    | v1.15              | K-means algorithm is used for unsupervised calculation and unsupervised classification.                     |

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm.KnnClassification;
import io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm.UDFDistanceClassification;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;

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

### Probability calculation and classification

Probability calculation is also a classification component in nature, such as naive Bayesian algorithm. All probability
calculation components need to pass two user-defined event filter objects. It is a P (A | B) style calculation result.
The first event filter in the calculation function plays the role of A event, and the second event filter plays the role
of B event.

- List of probability calculation components

| Calculation component type                                                         | Supported versions | function                                                                                       |
|------------------------------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.probabilisticAlgorithm.NaiveBayes | v1.14              | Calculate the probability value of the event like "P (A B)" with a small amount of calculation |

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.probabilisticAlgorithm.NaiveBayes;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayIntegerFiltering;

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

- Display of calculation results

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

### Decision calculation component

The decision algorithm was added to the AS database in version 1.14. It is an excellent algorithm for optimal
arrangement or optimization of multiple processing schemes. There are algorithms such as decision trees in the database.
Next is the usage of decision trees.

- Decision calculation component list

| Calculation component type                                                    | Supported versions | function                                                                                                                                                         |
|-------------------------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.schemeAlgorithm.DecisionTree | v1.14              | The decision tree calculation component calculates the most efficient filtering path and arranges the event processing functions passed in according to the path |
| io.github.beardedManZhao.algorithmStar.algorithm.schemeAlgorithm.RandomForest | v1.15              | Random forest computing component, randomly distributed samples automatically select the best solution                                                           |

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.schemeAlgorithm.DecisionTree;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.filter.ArrayIntegerFiltering;

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
        String s = DecisionTree.executeGetString(columnDoubleMatrix.toArrays(), decision);
        System.out.println(s);
    }
}
```

```mermaid
graph TB
    AllData -- io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$3/668386784 - 7b23ec81 = true --> TrueData1[Int True Node No.1<br>Remaining quantity = 4<br>Remaining percentage = 50.0%<br>]
    AllData -. io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$3/668386784 - 7b23ec81 = false .-> FalseData1[Int False Node No.1<br>Removal quantity = 4<br>Removal percentage = 50.0%<br>]
    TrueData1 -- io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$1/1607521710 - 4f3f5b24 = true --> TrueData2[Int True Node No.2<br>Remaining quantity = 2<br>Remaining percentage = 50.0%<br>]
    TrueData1 -. io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$1/1607521710 - 4f3f5b24 = false .-> FalseData2[Int False Node No.2<br>Removal quantity = 2<br>Removal percentage = 50.0%<br>]
    TrueData2 -- io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$2/2129789493 - 15aeb7ab = true --> TrueData3[Int True Node No.3<br>Remaining quantity = 1<br>Remaining percentage = 50.0%<br>]
    TrueData2 -. io . github . beardedManZhao . algorithmStar . MAIN1$$Lambda$2/2129789493 - 15aeb7ab = false .-> FalseData3[Int False Node No.3<br>Removal quantity = 1<br>Removal percentage = 50.0%<br>]
```

### Model calculation component

In addition to using integrators for model calculation, algorithms can also be used to calculate many models in the AS
library. The existing model calculation components are shown here, and the unary linear regression algorithm component
is used for an API demonstration example.

- Model calculation component list

| Calculation component type                                                               | Supported versions | function                                                                        |
|------------------------------------------------------------------------------------------|--------------------|---------------------------------------------------------------------------------|
| io.github.beardedManZhao.algorithmStar.algorithm.modelAlgorithm.LinearRegression         | v1.15              | The calculation component can realize fast linear regression calculation        |
| io.github.beardedManZhao.algorithmStar.algorithm.modelAlgorithm.MultipleLinearRegression | v1.15              | The calculation component can perform multiple linear regression of linear data |

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.modelAlgorithm.LinearRegression;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些数据，现在需要找到最块的筛选路线，并使用此路线将数据进行一次获取
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"x", "y"},
                null,
                new int[]{1, 50},
                new int[]{2, 100},
                new int[]{3, 150},
                new int[]{4, 200}
        );
        // 获取到线性回归
        LinearRegression line = LinearRegression.getInstance("line");
        // 开始计算线性回归 计算x 与 y 之间的关系 其中 x 为自变量  y 为因变量
        // 设置自变量的列编号
        line.setFeatureIndex(0);
        // 设置因变量的列编号
        line.setTargetIndex(1);
        // 计算出回归系数与结果值
        double[] doubles = line.modelInference(columnDoubleMatrix);
        // 获取到线性回归计算之后的权重数组，并将权重数组插入到公式打印出来
        System.out.println("数据特征：");
        System.out.println("y = x * " + doubles[0] + " + " + doubles[1]);
    }
}

```

```
[INFO][OperationAlgorithmManager][23-01-24:09]] : register OperationAlgorithm:line
数据特征：
y = x * 50.0 + 0.0
```

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/OperationAlgorithm-Chinese.md)

<hr>

### warning：In version 1.0, please avoid calling " getTrueDistance (DoubleVector doubleVector) " as far as possible, because this function is unreasonable. In later versions, this function is deleted!!!! A more appropriate function will be used instead.
