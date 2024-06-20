package io.github.beardedManZhao.algorithmStar.operands.table;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.utils.ASStr;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * FDF 数据对象的构建类，该类主要的作用就是在读取层面进行数据的处理并获取到数据对象的位置。
 *
 * @author 赵凌宇
 * 2023/3/8 11:26
 */
public class FDataFrameBuilder implements DataFrameBuilder {

    protected final Connection connection;
    protected final StringBuilder colNamesSQL;
    protected final StringBuilder whereClauseSQL;
    protected final Mode inMode;
    protected final BufferedReader bufferedReader;
    protected Condition whereFS;
    protected String Table;
    protected Series colNames;
    protected char sep = ',';

    protected int primaryIndex = 0;

    public FDataFrameBuilder(File file) {
        this.inMode = Mode.FILESYSTEM;
        this.connection = null;
        this.colNamesSQL = null;
        this.whereClauseSQL = null;
        try {
            this.bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new OperatorOperationException(e);
        }
    }

    public FDataFrameBuilder(Connection connection) {
        this.connection = connection;
        this.inMode = Mode.SQL;
        this.bufferedReader = null;
        this.colNamesSQL = new StringBuilder();
        this.whereClauseSQL = new StringBuilder();
    }

    /**
     * 获取到数据读取模式。
     *
     * @return 数据读取模式对象。
     */
    @Override
    public Mode getInMode() {
        return this.inMode;
    }

    public FDataFrameBuilder setColNames(String[] colNames) {
        FieldCell[] fieldCells = new FieldCell[colNames.length];
        int index = -1;
        for (String colNamesF : colNames) {
            fieldCells[++index] = FieldCell.$(colNamesF);
        }
        this.colNames = new FinalSeries(fieldCells);
        return this;
    }

    /**
     * 设置读取数据时需要使用的分隔符。在读取一个文件对象的时候会有效果。
     *
     * @param sep 指定的数据分隔符，该分隔符将会在读取的时候被调用与获取，最终计算出符合条件的数据行对象。
     * @return 链式调用
     */
    @Override
    public FDataFrameBuilder setSep(char sep) {
        this.sep = sep;
        return this;
    }

    /**
     * 将指定的数据查询出来
     *
     * @param colNames 所有需要被查询的列数据。
     * @return 链式调用
     */
    @Override
    public DataFrameBuilder create(String... colNames) {
        if (inMode == Mode.SQL && colNames.length >= 1 && Objects.equals(colNames[0], "*")) {
            if (colNames.length == 1) return this;
            else
                throw new OperatorOperationException("如果您在SQL模式下使用了通配符，请确保您的create中只有通配符列。\nIf you use wildcards in SQL mode, make sure that you have only wildcard columns in your create.");
        }
        if (this.colNamesSQL != null) {
            this.colNamesSQL.append("select ");
            for (String colName : colNames) {
                this.colNamesSQL.append(colName)
                        .append(',');
            }
            this.colNamesSQL.deleteCharAt(this.colNamesSQL.length() - 1);
        }
        return this.setColNames(colNames);
    }

    /**
     * 在创建时指定主键索引列，该操作的指定将可以有效地加快各种计算的操作。
     *
     * @param colName 需要被作为主键索引的字段名称。
     * @return 链式调用。
     */
    @Override
    public DataFrameBuilder primaryKey(String colName) {
        if (this.colNames != null) {
            int index = -1;
            for (Cell<?> name : this.colNames) {
                ++index;
                if (Objects.equals(colName, name.getStringValue())) return primaryKey(index);
            }
            throw new OperatorOperationException("Don't know lie: " + colName);
        } else
            throw new OperatorOperationException("Please call the create function to set the column data first.\nIf you are using a wildcard, use the index to set the primary key.");
    }

    /**
     * 在创建时指定主键索引列，该操作的指定将可以有效加快各种计算的操作。
     *
     * @param colIndex 需要被作为主键索引的字段索引值，
     * @return 链式调用。
     */
    @Override
    public DataFrameBuilder primaryKey(int colIndex) {
        this.primaryIndex = colIndex;
        return this;
    }

    /**
     * 将指定的表或其它路径信息作为本次读取数据的来源。
     *
     * @param table 数据来源信息
     * @return 链式调用
     */
    @Override
    public DataFrameBuilder from(String table) {
        if (this.inMode == Mode.SQL) {
            this.Table = table;
        } else {
            throw new OperatorOperationException("在文件系统模式下的查询将无法设置文件来源。\nQueries in file system mode will not be able to set the file source.\n ERROR => " + table);
        }
        return this;
    }

    /**
     * 在进行数据获取时需要进行的过滤条件。需要注意的是，如果当前读取模式为 关系型 SQL 那么该函数才可用。
     * <p>
     * The filter conditions required for data acquisition. Note that this function is only available if the current read mode is relational SQL.
     *
     * @param whereClause SQL过滤时需要使用的过滤语句。
     *                    <p>
     *                    The filter statement required for SQL filtering.
     * @return 链式调用。
     */
    @Override
    public DataFrameBuilder where(String whereClause) {
        if (this.whereClauseSQL != null) {
            this.whereClauseSQL.append("where ").append(whereClause);
        } else {
            throw new OperatorOperationException("在文件系统模式下的查询将无法设置字符串的where子句。\nQueries in file system mode will not be able to set the where clause of the string.\n ERROR => " + whereClause);
        }
        return this;
    }

    /**
     * 在进行文件系统数据获取的啥时候需要使用的过滤条件，需要注意的时，如果当前的读取模式为 文件系统 那么该函数才可用。
     * <p>
     * The filter conditions to be used when obtaining file system data should be noted that this function is only available if the current read mode is file system.
     *
     * @param whereClause 文件系统数据读取时的需要使用的过滤语句。
     *                    <p>
     *                    The filter statement to be used when reading file system data.
     * @return 链式调用
     */
    @Override
    public DataFrameBuilder where(Condition whereClause) {
        if (this.inMode == Mode.FILESYSTEM) {
            this.whereFS = whereClause;
        } else {
            throw new OperatorOperationException("在SQL模式下的查询将无法设置Condition对象的where子句。\nQueries in SQL mode will not be able to set the where clause of the Condition object.\n ERROR => " + whereClause);
        }
        return this;
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
                assert this.connection != null;
                PreparedStatement preparedStatement;
                if (this.colNamesSQL != null && this.colNames != null) {
                    preparedStatement = this.connection.prepareStatement(colNamesSQL + " from " + Table + ' ' + whereClauseSQL);
                } else {
                    preparedStatement = this.connection.prepareStatement("select * from " + Table + ' ' + whereClauseSQL);
                    ResultSetMetaData metaData = preparedStatement.getMetaData();
                    String[] fields = new String[metaData.getColumnCount()];
                    for (int i = 0; i < fields.length; ) {
                        fields[i] = metaData.getColumnName(++i);
                    }
                    this.colNames = FieldCell.parse(fields);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                int columnCount = resultSet.getMetaData().getColumnCount();
                ArrayList<Series> arrayList = new ArrayList<>();
                while (resultSet.next()) {
                    ArrayList<Cell<Object>> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(new FinalCell<>(resultSet.getString(i)));
                    }
                    arrayList.add(new FinalSeries(row.toArray(new Cell[0])));
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
                        FinalSeries parse = FinalSeries.parse(ASStr.splitByChar(bufferedReader.readLine(), sep));
                        if (whereFS.isComplianceEvents(parse)) {
                            arrayList.add(parse);
                        }
                    }
                } else {
                    while (bufferedReader.ready()) {
                        FinalSeries parse = FinalSeries.parse(ASStr.splitByChar(bufferedReader.readLine(), sep));
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
