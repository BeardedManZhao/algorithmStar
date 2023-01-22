# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.12_1.13-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.14 - x.xx
* The random forest calculation component is added. Compared with the decision tree, the random forest can make the work
  easier, but the calculation is large because there are many decision trees in the forest.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.algorithm.schemeAlgorithm.RandomForest;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

public class MAIN1 {
    public static void main(String[] args) throws CloneNotSupportedException {
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

* The decision calculation component now realizes the combination of scheme calculation and result calculation. The
  result value can be obtained at the same time of decision analysis through the function decisionAndGet.

### Version update date : XX XX-XX-XX