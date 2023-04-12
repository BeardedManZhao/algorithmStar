# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.18 - 1.19
* 图像矩阵的变换计算函数实现新增缩放逻辑，能够按照指定的倍数进行单方向的缩放操作，下面是一个将原图像缩小为原来的 1/2 的代码示例。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
        // 获取到用于测试的图像
        ColorMatrix colorMatrix = AlgorithmStar.parseImage(
                new URL("https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png")
        );
        // 将图像进行备份
        ColorMatrix parse = ColorMatrix.parse(colorMatrix.copyToNewArrays());
        // 准备缩放需要的配置文件，开始进行缩放
        HashMap<String, Cell<?>> map = new HashMap<>();
        // 在这里指定缩放的倍数为2
        map.put("times", new FinalCell<>(2));
        // 将备份的图像进行横向缩放
        ColorMatrix converter1 = parse.converter(ColorMatrix.SCALE_LR, map);
        converter1.show("image1");
        // 再一次进行纵向缩放
        ColorMatrix converter2 = converter1.converter(ColorMatrix.SCALE_BT, map);
        // 开始对比原图像与新图像的差距
        colorMatrix.show("src");
        converter2.show("res");
    }
}
```

### Version update date : xx xx-xx-xx