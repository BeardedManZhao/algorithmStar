# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.15 - x.xx
* Remove the outdated normalization function from the data preprocessing component and use pretreatment instead.
* In the linear normalization calculation component, the problem that the normalization result of the reshaping vector
  is inaccurate was fixed.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.normalization.LinearNormalization;
import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个向量对象
        IntegerVector parse1 = IntegerVector.parse(15, 9, 8, 7, 6, 7, 8, 9, 10);
        DoubleVector parse2 = DoubleVector.parse(10.5, 9, 8, 7, 6, 7, 8, 9, 10);
        // 获取到数据预处理组件 线性归一化 并设置其归一化的最大值与最小值
        LinearNormalization line = LinearNormalization
                .getInstance("line")
                .setMin(-5).setMax(5);
        // 获取到数据预处理组件，序列标准化
        Z_ScoreNormalization z_score = Z_ScoreNormalization.getInstance("z_Score");
        // 打印序列线性归一化的结果向量
        System.out.println(line.pretreatment(parse1));
        System.out.println(line.pretreatment(parse2));
        // 打印线性诡异话的结果向量
        System.out.println(z_score.pretreatment(parse1));
        System.out.println(z_score.pretreatment(parse2));
    }
}
```

### Version update date : xx xx-xx-xx