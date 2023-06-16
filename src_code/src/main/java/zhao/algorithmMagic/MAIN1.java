package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonSeries;

import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) throws IOException {
        // 创建一个 DF 对象
        DataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name", "age"), 1
        );
        dataFrame.desc().show();
    }
}