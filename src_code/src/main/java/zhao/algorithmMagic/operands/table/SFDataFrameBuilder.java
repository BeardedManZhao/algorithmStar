package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASStr;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * 单例DF数据对象的建造者类，由该类建造出每一个元素都是单例单元格对象的 DF 数据对象。
 *
 * @author 赵凌宇
 * 2023/4/12 18:42
 */
public class SFDataFrameBuilder extends FDataFrameBuilder {

    public SFDataFrameBuilder(File file) {
        super(file);
    }

    public SFDataFrameBuilder(Connection connection) {
        super(connection);
    }

    /**
     * 准备好数据的查询之后指定查询。
     *
     * @return 经过查询之后返回的数据对象。
     */
    @Override
    public DataFrame execute() {
        if (this.getInMode() == Mode.SQL) {
            try {
                assert super.connection != null;
                PreparedStatement preparedStatement;
                if (this.colNamesSQL != null && this.colNames != null) {
                    preparedStatement = this.connection.prepareStatement(colNamesSQL + " from " + Table + ' ' + whereClauseSQL);
                } else {
                    preparedStatement = this.connection.prepareStatement("select * from " + Table + ' ' + whereClauseSQL);
                    ResultSetMetaData metaData = preparedStatement.getMetaData();
                    String[] fields = new String[metaData.getColumnCount()];
                    for (int i = 0; i < fields.length; )
                        fields[i] = metaData.getColumnName(++i);
                    this.colNames = SingletonSeries.parse(fields);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                int columnCount = resultSet.getMetaData().getColumnCount();
                ArrayList<Series> arrayList = new ArrayList<>();
                while (resultSet.next()) {
                    ArrayList<Cell<?>> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(SingletonCell.$(resultSet.getString(i)));
                    }
                    arrayList.add(SingletonSeries.parse(row.toArray(new Cell[0])));
                }
                return new FDataFrame(this.colNames, primaryIndex, arrayList)
                        .refreshField(true, true);
            } catch (SQLException e) {
                throw new OperatorOperationException(e);
            }
        } else {
            ArrayList<Series> arrayList = new ArrayList<>();
            try {
                assert bufferedReader != null;
                if (whereFS != null) {
                    while (bufferedReader.ready()) {
                        SingletonSeries parse = SingletonSeries.parse(ASStr.splitByChar(bufferedReader.readLine(), sep));
                        if (whereFS.isComplianceEvents(parse)) {
                            arrayList.add(parse);
                        }
                    }
                } else {
                    while (bufferedReader.ready()) {
                        SingletonSeries parse = SingletonSeries.parse(ASStr.splitByChar(bufferedReader.readLine(), sep));
                        arrayList.add(parse);
                    }
                }
                return new FDataFrame(this.colNames, primaryIndex, arrayList)
                        .refreshField(true, true);
            } catch (IOException e) {
                throw new OperatorOperationException(e);
            }
        }
    }
}
