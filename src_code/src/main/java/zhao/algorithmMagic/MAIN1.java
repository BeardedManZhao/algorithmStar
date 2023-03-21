package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalSeries;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空 DF 对象 以 name 列作为行主键索引
        FDataFrame create = FDataFrame.select(FieldCell.parse("name", "sex", "phoneNum", "salary"), 1);
        // 插入一些数据
        DataFrame insert = create.insert(
                FinalSeries.parse("zhao1", "M", "110xxxxxxxx", "30000"),
                FinalSeries.parse("tang2", "W", "120xxxxxxxx", "30000"),
                FinalSeries.parse("yang3", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("zhao4", "M", "120xxxxxxxx", "30000"),
                FinalSeries.parse("tang5", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("yang6", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("zhao7", "M", "120xxxxxxxx", "30000"),
                FinalSeries.parse("tang8", "W", "110xxxxxxxx", "30000"),
                FinalSeries.parse("yang9", "W", "110xxxxxxxx", "30000")
        );
        // 查看数据集
        System.out.println(insert.desc());
        // 输出表的HTML
        insert.into_outHtml("C:\\Users\\Liming\\Desktop\\fsdownload\\res11234.html", "myTable");
    }
}