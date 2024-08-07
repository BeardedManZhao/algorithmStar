package io.github.beardedManZhao.algorithmStar.operands.table;

/**
 * 数据集构建类，由该类将数据集构建出来
 *
 * @author 赵凌宇
 * 2023/3/8 11:03
 */
public interface DataFrameBuilder {

    /**
     * 获取到数据读取模式。
     *
     * @return 数据读取模式对象。
     */
    Mode getInMode();

    /**
     * 设置读取数据时需要使用的分隔符。在读取一个文件对象的时候会有效果。
     *
     * @param sep 指定的数据分隔符，该分隔符将会在读取的时候被调用与获取，最终计算出符合条件的数据行对象。
     * @return 链式调用
     */
    FDataFrameBuilder setSep(char sep);

    /**
     * 将指定的数据查询出来
     *
     * @param colNames 所有需要被查询的列数据。
     * @return 链式调用
     */
    DataFrameBuilder create(String... colNames);

    /**
     * 在创建时指定主键索引列，该操作的指定将可以有效加快各种计算的操作。
     *
     * @param colName 需要被作为主键索引的字段名称。
     * @return 链式调用。
     */
    DataFrameBuilder primaryKey(String colName);

    /**
     * 在创建时指定主键索引列，该操作的指定将可以有效加快各种计算的操作。
     *
     * @param colIndex 需要被作为主键索引的字段索引值，
     * @return 链式调用。
     */
    DataFrameBuilder primaryKey(int colIndex);

    /**
     * 将指定的表或其它路径信息作为本次读取数据的来源。
     *
     * @param tableOrPath 数据来源信息
     * @return 链式调用
     */
    DataFrameBuilder from(String tableOrPath);

    /**
     * 在进行数据获取时需要进行的过滤条件。需要注意的是，如果当前读取模式为 关系型 SQL 那么该函数才可用。
     *
     * @param whereClause SQL过滤时需要使用的过滤语句。
     * @return 链式调用。
     */
    DataFrameBuilder where(String whereClause);

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
    DataFrameBuilder where(Condition whereClause);

    /**
     * 准备好数据的查询之后指定查询。
     *
     * @return 经过查询之后返回的数据对象。
     */
    DataFrame execute();
}
