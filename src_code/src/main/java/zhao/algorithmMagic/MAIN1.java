package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.FieldCell;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
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
                .execute()
                // 为列起别名
                .select(
                        FieldCell.$("id"),
                        FieldCell.$("name").as("名称"),
                        FieldCell.$("sex").as("性别")
                )
                // 将性别列进行转换，男生为1 女生为0
                .updateCol(FieldCell.$("性别"), cell -> new FinalCell<>(cell.getStringValue().equals("男") ? 1 : 0))
                // 将行主键数值为ZLY的数据行中的所有单元格替换成为数据 405
                .updateRow("ZLY", cell -> new FinalCell<>(405));

        // 打印出表中的行主键名称为 405 的数据行
        System.out.println(execute1.selectRow("405"));
        long start = System.currentTimeMillis();
        // 将表转换成为一个整形矩阵对象，该操作会将DF对象中的所有数值试图转换成为 col.count()*3 的矩阵对象
        IntegerMatrix parse = IntegerMatrix.parse(execute1, execute1.count().getIntValue(), 3);
        System.out.println(IntegerVector.parse(parse.getArrayByColIndex(2)));
        System.out.print("处理耗时（MS）：");
        System.out.println(System.currentTimeMillis() - start);
    }
}
