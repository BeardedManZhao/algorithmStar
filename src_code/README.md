# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.18 - 1.19
* The transformation calculation function of the image matrix implements new scaling logic, which can perform
  unidirectional scaling operations according to specified multiples. Below is a code example that reduces the original
  image to half of its original size.

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

* Add an SFDataFrame object, in which all cells are unique in memory. When there is a large amount of the same data in the DataFrame, SFDataFrame can save a lot of space in memory, and its usage is completely consistent with FDataFrame.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.*;

import java.net.MalformedURLException;

public class MAIN1 {
  public static void main(String[] args) {
    // 获取到数据输入组件
    InputComponent inputComponent = InputByStream.builder()
            .addInputArg(InputByStreamBuilder.INPUT_STREAM, SingletonCell.$(System.in))
            .addInputArg(InputByStreamBuilder.CHARSET, SingletonCell.$("utf-8"))
            .addInputArg(InputByStreamBuilder.PK, SingletonCell.$(1))
            .addInputArg(InputByStreamBuilder.ROW_LEN, SingletonCell.$(3))
            .addInputArg(InputByStreamBuilder.SEP, SingletonCell.$(','))
            .create();
    // 开始通过数据输入组件获取到 DF 对象 输入数据如下所示 其中的性别列有两个数据是一样的
        /*
            id,name,sex,age
            1,zhao,M,19
            2,tang,W,21
            3,yang,W,18
        */
    // 提取到最后两行数据 从索引1开始提取2行。
    DataFrame dataFrame = SFDataFrame.builder(inputComponent).limit(1, 2);
    // 打印其中的数据
    dataFrame.show();
    // 提取出 性别列 中的数据 并判断其中的两个 W 是否属于同一个内存
    Series sex = dataFrame.select(FieldCell.$("sex"));
    System.out.println(sex.getCell(0) == sex.getCell(1));
  }
}
```
### Version update date : 2023-04-09