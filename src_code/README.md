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

### Version update date : 2024-01-09