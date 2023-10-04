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
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
