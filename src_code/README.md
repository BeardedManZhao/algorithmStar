# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.16 - x.xx
* Change the name of the integrator to Integrator.
* It provides the calculation support of convolution function, which can enlarge the features and reduce the number of
  elements in the image matrix.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.integrator.launcher.ImageRenderingMarLauncher;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

public class MAIN1 {
  public static void main(String[] args) {
    String s1 = "C:\\Users\\Liming\\Desktop\\fsdownload\\微信图片_1.jpg";
    IntegerMatrix integerMatrix;
    {
      // 设置权重
      IntegerMatrix weight = IntegerMatrix.parse(
              new int[]{0, -1},
              new int[]{-1, 2},
              new int[]{0, 0}
      );
      // 读取图像并获取到三通道矩阵空间
      IntegerMatrixSpace parse = IntegerMatrixSpace.parse(s1);
      // 对图像进行卷积，获取三个色彩通道的矩阵空间的和
      integerMatrix = parse.foldingAndSum(2, 3, IntegerMatrixSpace.parse(weight, weight, weight));
    }
    // 输出图片1的卷积图像文件
    ImageRenderingIntegrator image = new ImageRenderingIntegrator(
            "image",
            new ImageRenderingMarLauncher<>(integerMatrix, "C:\\Users\\Liming\\Desktop\\fsdownload\\res12.jpg", 1)
    );
    if (image.run()) {
      System.out.println("ok!!!");
    }
  }
}
```

### Version update date : xx xx-xx-xx