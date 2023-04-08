package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.io.IOException;


public class MAIN1 {
    public static void main(String[] args) throws IOException {
        /* *****************************************************
         * TODO 从数据流中读取到数据 并构建 DF 对象
         * *****************************************************/
        // 开始将所有的参数配置到设备对象中，构建出数据输入设备
        InputComponent inputComponent = InputByStream.builder()
                // 准备数据输入流，在这里准备的是终端数据输入流
                .addInputArg(InputByStreamBuilder.INPUT_STREAM, new FinalCell<>(System.in))
                // 设置数据输入的字符集
                .addInputArg(InputByStreamBuilder.CHARSET, new FinalCell<>("utf-8"))
                // 由于 DF 数据加载是结构化模式加载，因此需要设置数据输入的分隔符
                .addInputArg(InputByStreamBuilder.SEP, new FinalCell<>(','))
                // 由于 DF 数据对象有主键功能，因此需要指定主键索引编号 这里是从0开始的索引
                .addInputArg(InputByStreamBuilder.PK, new FinalCell<>(1))
                // 设置本次数据要输入的行数量 代表我们要输入 3 行数据
                .addInputArg(InputByStreamBuilder.ROW_LEN, new FinalCell<>(3))
                .create();
        // 开始进行数据的加载 需要注意的是，由于我们使用的是终端数据流，因此不需要框架来关闭数据流，需要指定isOC为false
        DataFrame builder = FDataFrame.builder(inputComponent, true);
        builder.show();
    }
}
