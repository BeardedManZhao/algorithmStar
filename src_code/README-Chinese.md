# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.28 - 1.29
* 修复在进行向量中的不拷贝反转操作时，打印出来的数据不正确问题，这是由于向量内部的数据没有自动刷新，1.29 版本已解决此问题!

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