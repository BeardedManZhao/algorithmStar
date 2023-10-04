# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1. 25-1. 26
* For many serialization operations, serialization can be achieved through component methods. The IO component library
  has already integrated components for serialization object IO, and the following is an example.

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

* Serializing through simple functions in DF objects_ In the file series of functions, some optimizations have been
  made, which can receive a Boolean type parameter at the end to represent whether to use serialized output.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.io.InputObject;
import zhao.algorithmMagic.io.InputObjectBuilder;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SFDataFrame;

import java.io.FileInputStream;
import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) throws IOException {
        // 创建一个序列化数据输入组件
        final InputComponent inputObject = InputObject.builder()
                .addInputArg(InputObjectBuilder.IN_STREAM, new FinalCell<>(new FileInputStream("C:\\Users\\zhao\\Downloads\\test\\res")))
                .create();
        // 从其中获取到 DF 对象
        final DataFrame dataFrame = SFDataFrame.builder(inputObject);
        // 查看其中的数据 并通过最新的 简洁函数来实现序列化输出
        dataFrame
                // TODO into 函数的结尾参数为 true 代表二进制输出
                .into_outfile("C:\\Users\\zhao\\Downloads\\test\\res1", true)
                // 查看内容
                .show();
        // TODO 当然 into 函数的使用方式与之前一样，只是可以选择性的在结尾加上 true / false
        //  如果最后不加布尔数值或者为 false 代表就是使用文本的方式来输出
        dataFrame
                .into_outfile("C:\\Users\\zhao\\Downloads\\test\\res2")
                .into_outfile("C:\\Users\\zhao\\Downloads\\test\\res3", ",")
                .into_outfile("C:\\Users\\zhao\\Downloads\\test\\res4", false);
    }
}
```

### Version update date : xx xx-xx-xx