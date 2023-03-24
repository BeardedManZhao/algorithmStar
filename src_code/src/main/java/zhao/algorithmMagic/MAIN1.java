package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FinalSeries;

import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        // 创建一个空的 DataFrame 对象
        FDataFrame select = FDataFrame.select(
                FinalSeries.parse("id", "name", "sex", "age"), 1
        );
        // 手动插入数据
        select.insert(
                FinalSeries.parse("1", "zhao", "M", "19"),
                FinalSeries.parse("1", "tang", "W", "18"),
                FinalSeries.parse("1", "yang", "W", "20")
        );

        System.out.println(select);
    }
}