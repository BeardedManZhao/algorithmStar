# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.14 - x.xx
* 新增随机森林计算组件，随机森林相较于决策树，可以让工作变的轻松，但计算量很大，因为森林中有很多决策树。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.algorithm.schemeAlgorithm.RandomForest;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些数据，现在需要找到最块的筛选路线，并使用此路线将数据进行一次获取
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new int[]{1, 1, 1},
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0},
                new int[]{1, 1, 9},
                new int[]{0, 1, 0},
                new int[]{2, 1, 0},
                new int[]{0, 1, 0},
                new int[]{1, 4, 0},
                new int[]{0, 1, 1},
                new int[]{5, 1, 1},
                new int[]{0, 1, 0},
                new int[]{7, 0, 1},
                new int[]{1, 1, 1},
                new int[]{7, 0, 3},
                new int[]{7, 10, 0},
                new int[]{5, 1, 2},
                new int[]{5, 1, 2}
        );
        // 构建条件
        ArrayIntegerFiltering arrayIntegerFiltering1 = v -> v[1] == 0;
        ArrayIntegerFiltering arrayIntegerFiltering2 = v -> v[0] == 7;
        ArrayIntegerFiltering arrayIntegerFiltering3 = v -> v[2] == 3;

        // 开始进行决策树结果获取
        DecisionTree decisionTree = DecisionTree.getInstance("DecisionTree");
        IntegerMatrix integerMatrix = decisionTree.decisionAndGet(
                // 设置需要被处理的矩阵对象以及调用熵运算时使用的log底数
                columnDoubleMatrix, 2,
                // 设置运行时的过滤事件
                arrayIntegerFiltering2, arrayIntegerFiltering1, arrayIntegerFiltering3
        );
        System.out.println(integerMatrix);

        // 开始进行随机森林结果获取
        RandomForest randomForest = RandomForest.getInstance("RandomForest");
        IntegerMatrix integerMatrix1 = randomForest.decisionAndGet(
                // 设置需要被处理的矩阵对象以及调用熵运算时使用的log底数，同时设置了森林中的决策树数量3，以及决策树负责的样本量7
                columnDoubleMatrix, 2, 3, 19,
                // 设置运行时的过滤事件
                arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3
        );
        System.out.println(integerMatrix1);
    }
}
```

* 决策计算组件现在实现了方案计算与结果计算的合并，通过函数 decisionAndGet 可以在决策分析的同时获取到结果数值。
* 能够调用一元线性回归计算组件进行模型的推测

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.modelAlgorithm.LinearRegression;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

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

* 优化文档
* 为诸多操作数提供浅拷贝构造函数
* 支持通过稀疏坐标数据组成的数组直接构建矩阵对象

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个二维数组，其中每一个元素代表一个坐标点的位置与值
        int[][] ints = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new int[]{1, 1, 1}, new int[]{2, 2, 1}, new int[]{3, 2, 2}
        };
        // 根据稀疏坐标点描述创建矩阵对象
        IntegerMatrix sparse1 = IntegerMatrix.sparse(ints);
        // 打印矩阵
        System.out.println(sparse1);

        // 浮点矩阵也是可以这样使用的
        // 首先准备稀疏矩阵数据
        double[][] doubles = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new double[]{1, 1, 1}, new double[]{2, 2, 1}, new double[]{3, 2, 2}
        };
        // 创建矩阵
        DoubleMatrix sparse2 = DoubleMatrix.sparse(doubles);
        // 打印矩阵
        System.out.println(sparse2);
    }
}
```

### Version update date : XX XX-XX-XX