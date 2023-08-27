# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.22 - 1.23
* 针对图像处理库的 parse 函数，支持针对 File 文件对象进行解析，不需要再通过旧版本中的转换来实现了。

```java
package zhao.algorithmMagic;


import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.HashColorMatrix;
import zhao.algorithmMagic.operands.matrix.ImageMatrix;
import zhao.algorithmMagic.utils.ASIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个需要被读取的文件对象
        final File file = new File("C:\\Users\\zhao\\Desktop\\wifi.bmp");

        // TODO 旧版本中的操作
        try {
            // 首先通过 file 对象获取到图像数组
            final Color[][] colors = ASIO.parseImageGetColorArray(file);
            // 通过 AS 的基本图像处理库解析 File 对象
            ColorMatrix.parse(colors).show("old_colorMat", 200, 100);
            // 通过 AS 的哈希图像处理库解析 File 对象
            HashColorMatrix.parse(colors).show("old_HashMat", 200, 100);
            // 通过 AS 的图像矩阵处理库解析 File 对象
            ImageMatrix.parse(colors).show("old_imageMat", 200, 100);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // TODO 新版本中的操作 可以直接将 File 对象传递进来
        // 通过 AS 的基本图像处理库解析 File 对象
        ColorMatrix.parse(file).show("colorMat", 200, 100);
        // 通过 AS 的哈希图像处理库解析 File 对象
        HashColorMatrix.parse(file).show("HashMat", 200, 100);
        // 通过 AS 的图像矩阵处理库解析 File 对象
        ImageMatrix.parse(file).show("imageMat", 200, 100);
    }
}
```

* 图像处理库中的灰度图像解析函数支持读取尺寸的设置。

```java
package zhao.algorithmMagic;


import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.HashColorMatrix;
import zhao.algorithmMagic.operands.matrix.ImageMatrix;
import zhao.algorithmMagic.utils.ASIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个需要被读取的文件对象
        final File file = new File("C:\\Users\\zhao\\Desktop\\wifi.bmp");
        // 通过 AS 的基本图像处理库解析 File 对象 TODO 在这里以及后面的所有灰度解析函数中都设置了尺寸
        ColorMatrix.parseGrayscale(file, 200, 100).show("colorMat");
        // 通过 AS 的哈希图像处理库解析 File 对象
        HashColorMatrix.parseGrayscale(file, 200, 100).show("HashMat");
        // 通过 AS 的图像矩阵处理库解析 File 对象
        ImageMatrix.parseGrayscale(file, 200, 100).show("imageMat");
    }
}
```

### Version update date : 2023-08-27