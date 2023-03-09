package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) throws InterruptedException, SQLException {
//         准备一份数据文件的对象
//        File file1 = new File("D:\\liming\\Project\\My_Book\\项目笔记\\Hive数据仓库\\资料\\02-进阶篇材料\\stu_per.txt");
        // 准备数据库连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.4:38243/big_data", "root", "liming7887");
        // 将数据库数据对象加载成 FDF
        DataFrame execute = FDataFrame.builder(connection)
                .create("*")
                .from("brand_info")
                .primaryKey(1)
                .execute();
        // 打印出 FDF 中的数据
        System.out.println(execute);
        // 正序打印出 FDF 中 手机号大于 1.5138641134E10 的 前 3 行数据 同时将其导出
        DataFrame select = execute
                .select("brand_name", "telephone")
                .where(v -> v.getCell(1).getDoubleValue() > 1.5138641134E10)
                .sort("telephone")
                .limit(3);
        System.out.println(select.into_outfile("C:\\Users\\Liming\\Desktop\\fsdownload\\res1.csv"));
        // 打印存储 FDF 中的数据行数
        System.out.println(execute.count());
        // 打印出其中的信息
        System.out.println(execute.desc());
        // 获取到 刘晨 的信息 由于 name 列是主键，因此可以直接通过这里获取到数据
        System.out.println(execute.selectRow("华为"));
    }
}
