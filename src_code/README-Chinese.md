# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- 重要更新！！！！
- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.13 - xxx
* 为整形向量提供了原数组的获取支持，可以使用toArray获取到向量或矩阵的数组对象。
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
        System.out.println(avg.calculation(doubles, doubleVector.toDoubleArray()));
    }
}
```

* 增加了特征提取组件

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

```
[INFO][dict][23-01-12:10]] : ColumnIntegerMatrix extract(String[] data)
------------MatrixStart-----------
cat	dog	turtle	fish	
[1, 0, 0, 0]
[0, 1, 0, 0]
[0, 0, 1, 0]
[0, 0, 0, 1]
[1, 0, 0, 0]
------------MatrixEnd------------

[ 0 0 1 0 0 ]	turtle
[ 1 0 0 0 1 ]	cat
[ 0 0 0 1 0 ]	fish
[ 0 1 0 0 0 ]	dog
================================================
[INFO][OperationAlgorithmManager][23-01-12:10]] : register OperationAlgorithm:word
------------MatrixStart-----------
I love you, Because you are beautiful.	I need you. Because I'm trapped	rowColName
[1, 0]	love
[0, 1]	trapped
[0, 1]	need
[1, 1]	I
[0, 1]	I'm
[1, 0]	beautiful
[1, 1]	Because
[1, 0]	are
[2, 1]	you
------------MatrixEnd------------
```

* 为所有的矩阵增加特征选择函数，能够按照指定的百分比去除指定数量的维度数据，并返回一个新矩阵

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public final class MAIN1 {
    public static void main(String[] args) {
        // 准备一个矩阵
        IntegerMatrix parse = IntegerMatrix.parse(
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        );
        // 开始进行特征选择 去除掉其中的 40% 的维度
        IntegerMatrix integerMatrix = parse.featureSelection(0.4);
        System.out.println(integerMatrix);

        // 准备一个矩阵 其中存储的是鸟的数据样本
        IntegerMatrix parse1 = ColumnIntegerMatrix.parse(
                new String[]{"1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d"}, // 样本来源地区编号
                new String[]{"羽毛", "羽毛的颜色", "种族"}, // 样本统计的三种维度
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        );
        // 开始进行特征选择 去除掉其中的 40% 的维度
        IntegerMatrix integerMatrix1 = parse1.featureSelection(0.4);
        System.out.println(integerMatrix1);
    }
}
```

```
------------MatrixStart-----------
[10, 20, 30, 40, 50, 60, 70, 80, 90]
[1, 2, 3, 4, 5, 6, 7, 8, 9]
------------MatrixEnd------------

------------MatrixStart-----------
1d  2d  3d  4d  5d  6d  7d  8d  9d  rowColName
[10, 20, 30, 40, 50, 60, 70, 80, 90]	种族
[1, 2, 3, 4, 5, 6, 7, 8, 9]	      羽毛的颜色
------------MatrixEnd------------
```

* 为矩阵数据对象增加了计算函数 featureSelection 用于去除冗余的维度
* 为矩阵数据对象增加了计算函数 deleteRelatedDimensions 用于去除指定维度的相关维度
* 为矩阵体系中增加了一个新的矩阵类型，带有行列字段的矩阵对象 ColumnIntegerMatrix
* 增加了分类计算组件，例如KNN

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KnnClassification;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

public final class MAIN1 {
    public static void main(String[] args) {
        // 开始进行近邻计算
        KnnClassification knn = KnnClassification.getInstance("knn");
        HashMap<String, ArrayList<IntegerVector>> classification = knn.classification(
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
        classification.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));


        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                new String[]{"科1", "科2", "科3", "科4", "科5", "科6"},
                new String[]{"张三", "李四", "王五", "赵六", "鲁迅", "甲X", "黄X"},
                new int[]{120, 120, 120, 100, 100, 100},
                new int[]{80, 30, 80, 45, 67, 89},    // 未知类别
                new int[]{59, 59, 59, 59, 59, 59},
                new int[]{110, 100, 120, 90, 80, 90}, // 未知类别
                new int[]{100, 100, 100, 90, 90, 90}, // 未知类别
                new int[]{90, 90, 90, 80, 80, 80},
                new int[]{60, 60, 60, 60, 60, 60}
        );
        System.out.println(parse);
        // 开始进行近邻计算
        HashMap<String, ArrayList<IntegerVector>> classification1 = knn.classification(
                new String[]{"优秀", "?", "不及格", "?", "?", "良好", "及格"},
                parse
        );
        // 开始打印类别
        classification1.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));
    }
}
```

```
[INFO][OperationAlgorithmManager][23-01-16:07]] : register OperationAlgorithm:EuclideanMetric
[INFO][OperationAlgorithmManager][23-01-16:07]] : register OperationAlgorithm:knn
类别：爱情动作	[[ 181 90 ]]
类别：爱情	[[ 18 90 ]]
------------MatrixStart-----------
科1  科2  科3  科4  科5  科6	rowColName
[120, 120, 120, 100, 100, 100]	张三
[80, 30, 80, 45, 67, 89]	李四
[59, 59, 59, 59, 59, 59]	王五
[110, 100, 120, 90, 80, 90]	赵六
[100, 100, 100, 90, 90, 90]	鲁迅
[90, 90, 90, 80, 80, 80]	甲X
[60, 60, 60, 60, 60, 60]	黄X
------------MatrixEnd------------

类别：不及格	[[ 80 30 80 45 67 89 ]]
类别：优秀	[[ 110 100 120 90 80 90 ]]
类别：良好	[[ 100 100 100 90 90 90 ]]
```

* 增加了区间型向量，这种向量具有轻量快速的特点，能够满足大数据量下的计算需求。
* 增加了门户类 zhao.algorithmMagic.core.AlgorithmStar ，可以通过该类进行所有计算函数的调用，当然，也可直接使用计算组件中的计算函数进行计算。
* 增加了概率算法。

### Version update date : XX XX-XX-XX