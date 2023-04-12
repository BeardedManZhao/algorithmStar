package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.*;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) throws MalformedURLException {
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
