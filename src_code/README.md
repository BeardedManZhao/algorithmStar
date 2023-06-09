# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.20 - 1.21
* *The creation function implementation of Sparse matrix object is rewritten, so that the coordinates in its parameters
  can be unrestricted, and interval values can be set at will within the range of positive integers.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {

    public static void main(String[] args) {

        // 创建出矩阵对象
        DoubleMatrix matrix1 = DoubleMatrix.sparse(
                // 这里的每一个数组代表的就是一个元素 后面的两个元素代表的是坐标
                new double[]{1, 0, 0},
                new double[]{2, 1, 1},
                new double[]{3, 5, 2}
        );
        IntegerMatrix matrix2 = IntegerMatrix.sparse(
                new int[]{10, 0, 0},
                new int[]{20, 1, 2},
                new int[]{30, 3, 2}
        );
        // 打印出矩阵
        System.out.println(matrix1);
        System.out.println(matrix2);
    }

}
```

* Both integer and floating-point vector objects support the random generation of vectors.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.Random;

public class MAIN1 {
    public static void main(String[] args) {
        Random random = new Random();
        // 随机创建 10 个元素的整形向量
        IntegerVector random1 = IntegerVector.random(10, random);
        DoubleVector random2 = DoubleVector.random(10, random);
        // 打印出其中的数据
        System.out.println(random1);
        System.out.println(random2);
    }
}
```

* Fixed the sub matrix extraction operation for floating-point matrix data types, which encountered extraction
  exceptions when the x-axis was 0 in previous versions. This issue was fixed in version 1.21.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 随机生成 9 行 5 列 的矩阵
        DoubleMatrix random = DoubleMatrix.random(5, 9, 22);
        System.out.println(random);
        // 提取出其中从 (0, 1) 到 (4, 5) 的子矩阵对象
        DoubleMatrix mat = random.extractMat(0, 1, 4, 5);
        System.out.println(mat);
    }
}
```

### Version update date : xx xx-xx-xx