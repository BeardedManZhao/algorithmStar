package zhao.algorithmMagic;
import zhao.algorithmMagic.operands.table.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个空的 DataFrame 对象
        FDataFrame select = FDataFrame.select(
                FieldCell.parse("id", "name", "sex", "age"), 1
        );
        // 手动插入数据
        select.insert(
                FinalSeries.parse("1", "zhao", "M", "19"),
                FinalSeries.parse("2", "tang", "W", "18"),
                FinalSeries.parse("3", "yang", "W", "20"),
                FinalSeries.parse("4", "shen", "W", "19")
        );
        // 将数据的HTML网页表格输出到磁盘中
        select.into_outHtml("C:\\Users\\zhao\\Desktop\\out\\res.html", "数据表名称");
    }
}