# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

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

### Version update date : XX XX-XX-XX