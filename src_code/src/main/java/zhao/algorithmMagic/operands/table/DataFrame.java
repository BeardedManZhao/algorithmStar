package zhao.algorithmMagic.operands.table;

import java.io.Serializable;

/**
 * 该数据类型是一种表结构的数据类型，其是所有表数据的父类，其应能够实现诸多的计算函数。
 * <p>
 * This data type is a table structure data type, which is the parent class of all table data. It should be able to implement many calculation functions.
 *
 * @author 赵凌宇
 * 2023/3/8 10:46
 */
public interface DataFrame extends AggDataFrameData, Iterable<Series>, Serializable {

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
     * 查询当前表中的指定行字段数据。
     *
     * @param rowNames 需要被获取到的数据行的行名称。
     * @return 查询出指定行数据的DF表
     * <p>
     * Find the DF table of the specified row data.
     */
    DataFrame selectRow(String... rowNames);

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
     * 将多个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    DataFrame insert(Series... rowSeries);

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
}
