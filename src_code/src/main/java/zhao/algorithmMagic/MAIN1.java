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
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println(OperationAlgorithmManager.VERSION);
//        if (args.length > 0) {
//            ASDynamicLibrary.addDllDir(new File(args[0]));
//            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
//        } else {
//            System.out.println("感谢您的使用。");
//        }


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
