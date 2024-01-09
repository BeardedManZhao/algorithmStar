# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:
* Framework version: 1.27 - 1.28
* New component for calculating fraction operands, supporting the creation and calculation of fraction operands

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.HelpFactory;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取帮助信息工厂类
        final HelpFactory helpFactory = AlgorithmStar.helpFactory();
        // 下载帮助文档 到 C:\Users\zhao\Desktop\fsdownload 目录中
        final String path = helpFactory.saveHelpFile(
                HelpFactory.ALL,
                "C:\\Users\\zhao\\Desktop\\fsdownload"
        );
        System.out.println("文件已保存到：" + path);
    }
}
```

### Version update date : 2024-01-09