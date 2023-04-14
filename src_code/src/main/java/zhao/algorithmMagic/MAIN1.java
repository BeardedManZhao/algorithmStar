package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.io.InputByStream;
import zhao.algorithmMagic.io.InputByStreamBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MAIN1 {
    public static void main(String[] args) throws SQLException {
        /* *****************************************************
         * TODO 从数据流中读取到数据
         * *****************************************************/
        // 开始将所有的参数配置到设备对象中，构建出数据输入设备
        InputComponent inputComponent = InputByStream.builder()
                .addInputArg(InputByStreamBuilder.INPUT_STREAM, new FinalCell<>(System.in))
                .addInputArg(InputByStreamBuilder.CHARSET, new FinalCell<>("utf-8"))
                .addInputArg(InputByStreamBuilder.SEP, new FinalCell<>(','))
                .addInputArg(InputByStreamBuilder.PK, new FinalCell<>(1))
                .addInputArg(InputByStreamBuilder.ROW_LEN, new FinalCell<>(3))
                .create();

        // TODO 开始通过 algorithmStar 构建出DataFrame对象 这里是通过文件 数据库 数据输入组件 来进行构建
        File file = new File("C:\\Users\\zhao\\Downloads\\test.csv");
        DataFrame dataFrame1 = AlgorithmStar.parseSDF(file).setSep(',')
                .create("year", "month", "day", "week", "temp_2", "temp_1", "average", "actual", "friend")
                .execute();
        dataFrame1.show();

        DataFrame dataFrame2 = AlgorithmStar.parseSDF(inputComponent, false);
        dataFrame2.show();

        Connection connection = DriverManager.getConnection("");
        DataFrame dataFrame3 = AlgorithmStar.parseSDF(connection)
                .from("xxx")
                .execute();
        dataFrame3.show();
    }
}