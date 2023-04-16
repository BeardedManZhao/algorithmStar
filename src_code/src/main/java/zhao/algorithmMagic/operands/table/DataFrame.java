package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.io.OutputComponent;
import zhao.algorithmMagic.operands.Operands;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

/**
 * 该数据类型是一种表结构的数据类型，其是所有表数据的父类，其应能够实现诸多的计算函数。
 * <p>
 * This data type is a table structure data type, which is the parent class of all table data. It should be able to implement many calculation functions.
 *
 * @author 赵凌宇
 * 2023/3/8 10:46
 */
public interface DataFrame extends AggDataFrameData, Iterable<Series>, Serializable, Operands<DataFrame> {

    FDataFrame refreshField(boolean rr, boolean rc);

    /**
     * 获取到当前表中的字段对象。
     * <p>
     * Gets the field object in the current table.
     *
     * @return 当前数据集中的字段行序列对象。
     * <p>
     * The field row sequence object in the current dataset.
     */
    Series getFields();

    /**
     * 获取到当前表中的字段信息与表信息。
     * <p>
     * Get the field information and table information in the current table.
     *
     * @return 当前数据表中的所有字段信息与表信息组成的新数据表。
     * <p>
     * A new data table composed of all field information and table information in the current data table.
     */
    DataFrame desc();

    /**
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colNames 所有需要被获取的列数据，可以使用 * 代替。
     *                 <p>
     *                 All column data to be obtained can be replaced by *.
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    DataFrame select(String... colNames);

    /**
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colNames 所有需要被获取的列数据，需要注意的是，在这里不允许使用 * 哦！
     *                 <p>
     *                 For all column data to be obtained, please note that * is not allowed here!
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    DataFrame select(FieldCell... colNames);

    /**
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colNames 所有需要被获取的列数据，可以使用 * 代替。
     *                 <p>
     *                 All column data to be obtained can be replaced by *.
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    Series select(String colNames);

    /**
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colNames 所有需要被获取的列数据，需要注意的是，在这里不允许使用 * 哦！
     *                 <p>
     *                 For all column data to be obtained, please note that * is not allowed here!
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    Series select(FieldCell colNames);

    /**
     * 查询当前表中的指定行字段数据。
     *
     * @param rowNames 需要被获取到的数据行的行名称。
     * @return 查询出指定行数据的DF表
     * <p>
     * Find the DF table of the specified row data.
     */
    DataFrame selectRow(String... rowNames);

    /**
     * 查询当前表中的指定行字段数据。
     *
     * @param rowName 需要被获取到的数据行的行名称。
     * @return 查询出指定行数据的DF表
     * <p>
     * Find the DF table of the specified row data.
     */
    Series selectRow(String rowName);

    /**
     * 将指定的符合条件的数据获取到。
     *
     * @param whereClause 条件判断逻辑实现。
     * @return 符合条件的数据系列组合成的新数据表。
     */
    DataFrame where(Condition whereClause);

    /**
     * 将数据按照指定的列字段进行排序，排序方式为正序排序。
     *
     * @param colNames 需要被排序的所有列字段数据
     * @return 判读之后的新 DF 数据对象。
     */
    DataFrame sort(String... colNames);

    /**
     * 将数据按照指定的列字段进行排序，排序方式为正序排序。
     *
     * @param colNames 需要被排序的所有列字段数据
     * @param asc      排序方式，如果指定为 true 代表按照排序，反之代表按照倒序排序。
     * @return 判读之后的新 DF 数据对象。
     */
    DataFrame sort(String[] colNames, boolean asc);

    /**
     * 将数据按照指定的列字段分组 并返回分组之后的新结果数据集。
     *
     * @param colName 需要被用来进行分组的字段数据
     * @return 分组之后的数据对象
     */
    GroupDataFrameData groupBy(String colName);

    /**
     * 将符合条件的数据行按照指定的列字段分组 并返回分组之后的新结果数据集。
     *
     * @param colName     需要被用来进行分组的字段数据
     * @param whereClause 在分组的时候进行一次 where 的过滤操作
     * @return 分组之后的数据对象
     */
    GroupDataFrameData groupBy(String colName, Condition whereClause);

    /**
     * 将数据集进行切片操作，并返回经过切片之后的新数据集结果对象。
     * <p>
     * Slice the dataset and return the new dataset result object after slicing.
     *
     * @param topN 切片操作时，获取到原数据集终的前 N 行数据。
     * @return 经过了切片之后的新数据集对象。
     */
    DataFrame limit(int topN);

    /**
     * 将数据集进行切片操作，并返回经过切片之后的新数据集结果对象。
     * <p>
     * Slice the dataset and return the new dataset result object after slicing.
     *
     * @param start 切片操作进行的时候，指定切片开始的索引位 从 0 开始计算。
     * @param len   此次切片需要切出来的数据行数。
     * @return 从 start 索引开始切分 len 行数据之后的新数据集。
     */
    DataFrame limit(int start, int len);

    /**
     * 将数据集进行切片操作，并返回经过切片之后的新数据集结果对象。
     * <p>
     * Slice the dataset and return the new dataset result object after slicing.
     *
     * @param startRowName 切分数据开始位置的行主键名称，请注意这里是表主键位。
     *                     <p>
     *                     The name of the row primary key at the beginning of the split data. Please note that this is the table primary key
     * @param EndRowName   需要被切分的截止行主键名称，请注意这里是表主键位。
     *                     <p>
     *                     The primary key name of the end row to be split. Please note that this is the table primary key
     * @return 经过切分之后返回的新数据集
     */
    DataFrame limit(String startRowName, String EndRowName);

    /**
     * 将一个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    DataFrame insert(Series rowSeries);

    /**
     * 将一个数据行插入表中。
     * <p>
     * Insert a data row into the table.
     *
     * @param value 需要被插入的数据行，可以接收多个字符串，如果字符串符合数值规则，则将会被转换成为数值单元格。
     *              <p>
     *              The data row that needs to be inserted can receive multiple strings. If the string conforms to numerical rules, it will be converted into numerical cells.
     * @return 插入数据之后的 DF 数据对象。
     * <p>
     * DF data object after inserting data.
     */
    DataFrame insert(String... value);

    /**
     * 将多个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    DataFrame insert(Series... rowSeries);

    /**
     * 以当前数据集为基准，添加一列新数据，并将添加列数据之后的 Data Frame 对象返回出来。
     * <p>
     * Based on the current dataset, add a new column of data, and return the Data Frame objects after adding the column data.
     *
     * @param fieldName      需要被添加的列数据所对应的列名称，要求不得与已有的DataFrame字段名称重名！
     *                       <p>
     *                       The column name corresponding to the column data to be added must not duplicate the existing DataFrame field name!
     * @param transformation 在添加的列新数据的过程中，提供一个新数据的生成函数，函数中的参数是每行数据的系列对象，您可以根据行数据生成新数值，也可以根据自己的规则生成新数据。
     *                       <p>
     *                       During the process of adding new data for a column, a new data generation function is provided. The parameters in the function are a series of objects for each row of data. You can generate new values based on the row data or generate new data based on your own rules.
     * @return 添加了列字段与列数据之后的DataFrame对象。
     * <p>
     * The DataFrame object after adding column fields and column data.
     */
    DataFrame insertColGetNew(FieldCell fieldName, Transformation<Series, Cell<?>> transformation);

    /**
     * 将一列字段对应的所有数据按照指定的函数进行更新。
     * <p>
     * Update all data corresponding to a column of fields according to the specified function.
     *
     * @param fieldCell      需要被提取的列字段名称。
     *                       <p>
     *                       The name of the column field to be extracted.
     * @param transformation 列数据更新逻辑实现，在这里传递进来的是被修改的列数据字段。
     *                       <p>
     *                       Column data update logic implementation, where the modified column data field is passed in.
     * @return 更新之后的DF数据对象。
     * <p>
     * DF data object after update.
     */
    DataFrame updateCol(FieldCell fieldCell, Transformation<Cell<?>, Cell<?>> transformation);

    /**
     * 将一行字段对应的所有数据按照指定的函数进行更新。
     * <p>
     * Update all data corresponding to a column of fields according to the specified function.
     *
     * @param rowName        需要被提取的列字段名称。
     *                       <p>
     *                       The name of the column field to be extracted.
     * @param transformation 列数据更新逻辑实现，在这里传递进来的是被修改的列数据字段。
     *                       <p>
     *                       Column data update logic implementation, where the modified column data field is passed in.
     * @return 更新之后的DF数据对象。
     * <p>
     * DF data object after update.
     */
    DataFrame updateRow(String rowName, Transformation<Cell<?>, Cell<?>> transformation);

    /**
     * 将计算结果输出到指定的目录的文本文件中。
     * <p>
     * Output the calculation results to a text file in the specified directory.
     *
     * @param outPath 需要被输出的目录
     * @return 输出之后会返回数据集本身，不会终止调用。
     * <p>
     * After output, the data set itself will be returned and the call will not be terminated.
     */
    DataFrame into_outfile(String outPath);

    /**
     * 将计算结果输出到指定的目录的文本文件中。
     * <p>
     * Output the calculation results to a text file in the specified directory.
     *
     * @param outPath 需要被输出的目录.
     * @param sep     在输出的时候需要使用的指定的文件单元格分隔符字符串。
     * @return 输出之后会返回数据集本身，不会终止调用。
     * <p>
     * After output, the data set itself will be returned and the call will not be terminated.
     */
    DataFrame into_outfile(String outPath, String sep);

    DataFrame into_outHTMLStream(BufferedWriter bufferedWriter, String tableName);

    /**
     * 将计算结果以 HTML 表格的格式输出到指定目录的文本文件中。
     *
     * @param outPath   输出的目标文件所在的路径
     * @param tableName 输出的HTML中的表名称。
     * @return 输出之后会返回数据集本身，不会终止调用
     */
    DataFrame into_outHtml(String outPath, String tableName);

    /**
     * 将计算结果提供给指定的数据输出组件进行数据的输出操作，
     *
     * @param outputComponent 输出数据需要使用的数据输出组件
     * @return 输出之后会返回数据集本身，不会终止调用。
     * <p>
     * After output, the data set itself will be returned and the call will not be terminated.
     */
    DataFrame into_outComponent(OutputComponent outputComponent);

    /**
     * 默认方式查看 DF 数据对象中的数据。
     * <p>
     * The default method is to view data in DF data objects.
     */
    void show();

    /**
     * 指定数据流的方式查看 DF 数据对象中的数据，该操作将会使得 DF 数据被输出到数据流中。
     * <p>
     * Specify the method of viewing data in the DF data object through a data stream, which will cause the DF data to be output into the data stream.
     *
     * @param bufferedWriter 需要传递数据的数据流对象。
     */
    void show(BufferedWriter bufferedWriter);

    void show(PrintStream printStream);

    /**
     * 将DF对象中的所有数据转换成为一个list容器。
     * <p>
     * Converts all data in a DF object into a list container.
     *
     * @return 返回一个包含所有行系列的list容器，在这里的容器是浅拷贝出来的，不会有过多的冗余占用。
     * <p>
     * Returns a list container that contains all row series. Here, the container is lightly copied, without excessive redundancy.
     */
    List<Series> toList();
}
