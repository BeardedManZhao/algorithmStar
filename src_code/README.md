# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.13 - xxx

* It supports the acquisition of the original array for the reshaping vector. You can use toArray to obtain the array
  object of the vector or matrix.

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
        System.out.println(avg.calculation(doubles, doubleVector.toDoubleArray()));
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

* Added feature extraction component

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

* Add feature selection function for all matrices, which can remove the specified number of dimension data according to
  the specified percentage, and return a new matrix

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
* The calculation function featureSelection is added to the matrix data object to remove redundant dimensions
 
* Added the calculation function deleteRelatedDimensions for the matrix data object to remove the relevant dimensions of the specified dimension
 
* A new matrix type is added to the matrix system. The matrix object ColumnIntegerMatrix with row and column fields
### Version update date : XX XX-XX-XX