# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.28 - 1.29
* Fix the issue of incorrect printed data when performing non copy inversion operations in vectors. This is because the
  data inside the vector is not automatically refreshed. Version 1.29 has resolved this issue!

```java
package com.zhao;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.VectorFactory;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN {
    public static void main(String[] args) {
        // 创建向量对象
        final VectorFactory vectorFactory = AlgorithmStar.vectorFactory();
        final IntegerVector integerVector = vectorFactory.parseVector(1, 2, 43, 4);
        // 向量整体反转 这里的参数代表的就是是否需要在拷贝的新向量中进行转换 1.28 以及 1.28 之前的版本
        // 打印出的字符串不太正确 1.29版本中会修复此问题
        System.out.println(integerVector.reverseLR(false));
    }
}
```

* For matrix factories, some creation methods have been added, allowing you to directly parse sparse matrices through
  the factory class. Of course, this operation is also supported in versions before 1.29!!!

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.MatrixFactory;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        final MatrixFactory matrixFactory = AlgorithmStar.matrixFactory();
        // 通过 工厂类 以及稀疏矩阵的方式创建两个矩阵
        final IntegerMatrix integerMatrix = matrixFactory.sparseMatrix(
                // 在坐标 (2,3) 的位置创建一个元素 1
                new int[]{1, 2, 3},
                // 在坐标 (1,2) 的位置创建一个元素 2
                new int[]{2, 1, 2}
        );
        final DoubleMatrix doubleMatrix = matrixFactory.sparseMatrix(
                // 在坐标 (2,3) 的位置创建一个元素 1
                new double[]{1, 2, 3},
                // 在坐标 (1,2) 的位置创建一个元素 2
                new double[]{2, 1, 2}
        );
        System.out.println(integerMatrix);
        System.out.println(doubleMatrix);
    }
}
```

* You can also use a matrix factory to fill and randomly create a matrix.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.MatrixFactory;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        final MatrixFactory matrixFactory = AlgorithmStar.matrixFactory();
        // 通过 工厂类 以及稀疏矩阵的方式创建两个矩阵 4行5列 元素全是 2
        final IntegerMatrix integerMatrix = matrixFactory.fill(2, 4, 5);
        final DoubleMatrix doubleMatrix = matrixFactory.fill(2.0, 4, 5);
        System.out.println(integerMatrix);
        System.out.println(doubleMatrix);
        // 使用随机的方式创建矩阵
        final IntegerMatrix integerMatrix1 = matrixFactory.randomGetInt(4, 5, 100);
        final DoubleMatrix doubleMatrix1 = matrixFactory.randomGetDouble(4, 5, 100);
        System.out.println(integerMatrix1);
        System.out.println(doubleMatrix1);
    }
}

```

### Version update date : 2024-01-13