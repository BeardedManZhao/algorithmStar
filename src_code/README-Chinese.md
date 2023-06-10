# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.20 - 1.21
* 修复1.20以及之前版本中的scala插件编译器依赖问题，在1.20以及1.20之前的版本中，scala类没有被正常包含，1.21以及此版本之后已修复。
* 针对稀疏矩阵对象的创建函数实现进行了重写，使得其参数中的坐标能够不受限制，在正整数范围内将可以随意的设置区间的数值。

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

* 整形与浮点向量对象都支持随机生成向量的方式。

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

* 修复浮点矩阵数据类型的子矩阵提取操作，其在旧版本中的x轴为0时会发生提取异常的情况，1.21版本中该问题被修复。

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

* 针对矩阵空间对象中的内积与乘积操作进行了实现，其在1.21版本以及之后的版本都进行了实现与支持。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到矩阵对象
        final DoubleMatrix matrix1 = DoubleMatrix.parse(
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 10, 11, 12}
        );
        final DoubleMatrix matrix2 = DoubleMatrix.parse(matrix1.toArrays().clone());
        // 将矩阵对象叠加三次 封装成为矩阵空间对象
        final DoubleMatrixSpace space1 = DoubleMatrixSpace.parse(matrix1, matrix1, matrix1);
        final DoubleMatrixSpace space2 = DoubleMatrixSpace.parse(matrix2, matrix2, matrix2);
        // 计算内积
        final Double aDouble = space1.innerProduct(space2);
        // 计算外积
        final DoubleMatrixSpace multiply = space1.multiply(space2);
        System.out.println(aDouble);
        System.out.println(multiply);
    }
}
```

* 针对 吗，

### Version update date : xx xx-xx-xx