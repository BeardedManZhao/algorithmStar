package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MAIN1 {
    public static void main(String[] args) throws InterruptedException, SQLException, IOException {
        // 准备文件对象
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\res1.csv");
        // 使用 FDF 加载文件
        DataFrame execute1 = FDataFrame.builder(file)
                // 文件对象的读取需要指定文本分隔符
                .setSep(',')
                // 文件对象需要指定好列名称，不能使用 * 这里代表的不是查询，而是创建一个DF的列字段
                .create("id", "name", "sex")
                // 文件对象的主键指定允许使用列名称
                .primaryKey("name")
                // 执行查询
                .execute();
        // 按照性别分组，计算出男女生人数
        long start = System.currentTimeMillis();
        System.out.println(
                execute1
                        // 指定查询的列，并起别名
                        .select(FieldCell.$("sex").as("性别"))
                        // 按照 sex 分组
                        .groupBy("性别")
                        // 进行 组内的计数
                        .count()
                        // 指定查询的列 并起别名
                        .select(
                                FieldCell.$("性别"),
                                FieldCell.$("count()").as("人数")
                        )
        );
        System.out.print("处理耗时（MS）：");
        System.out.println(System.currentTimeMillis() - start);
    }
}
