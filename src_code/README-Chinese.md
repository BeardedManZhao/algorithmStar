# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.25 - 1.26
* 针对诸多的序列化操作，可以通过组件的方式来实现序列化，在IO组件库中已经集成了针对序列化对象IO的组件，下面就是一个示例。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.*;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonSeries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MAIN1 {
  public static void main(String[] args) throws IOException {
//        System.out.println(OperationAlgorithmManager.VERSION);
//        if (args.length > 0) {
//            ASDynamicLibrary.addDllDir(new File(args[0]));
//            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
//        } else {
//            System.out.println("感谢您的使用。");
//        }


    // 创建一个对象输出组件 TODO 首先需要创建对应的数据输出流
    final OutputStream outputStream = new FileOutputStream("C:\\Users\\zhao\\Downloads\\test\\res");
    // 然后开始构建组件
    final OutputComponent outputComponent = OutputObject.builder()
            // 在这里将数据流装载进去
            .addOutputArg(OutputObjectBuilder.OUT_STREAM, new FinalCell<>(outputStream))
            .create();
    // 启动组件
    if (outputComponent.open()) {
      // 如果启动成功就创建一个 DF 对象
      final FDataFrame select = SFDataFrame.select(
              SingletonSeries.parse("name", "age"), 1
      );
      select.insert("zhao", "20").insert("tang", "22")
              // 使用组件将 DF 对象输出
              .into_outComponent(outputComponent);
    }
    // 使用完毕就关闭组件
    outputComponent.close();

    // 当然，TODO 您也可以通过 InputObject 对象来实现反序列化。
    //      如果您不习惯通过组件实现，也可以通过Java中的序列化方式来实现。
    final InputComponent inputObject = InputObject.builder()
            .addInputArg(InputObjectBuilder.IN_STREAM, new FinalCell<>(new FileInputStream("")))
            .create();
  }
}


```

### Version update date : xx xx-xx-xx