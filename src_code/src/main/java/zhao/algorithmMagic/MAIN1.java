package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.table.SingletonSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个 DF 对象
        FDataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name", "age"), 1
        );
        // 插入一行数据
        dataFrame.insert("1", "zhao", "19");
        // 查询出 zhao 对应的行
        System.out.println(dataFrame.selectRow("zhao"));
        // 将当前行的 age 列 + 1
        dataFrame.updateCol(
                // TODO 针对列数据的更新操作，支持字符串指定列名称了
                "age",
                v -> SingletonCell.$(v.getIntValue() + 1)
        ).show();
    }
}