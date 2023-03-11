package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

/**
 * Final数据集对象，在该对象中，被添加之后的数据将是不可变的数据类型，在这些数据类型中您可以直接使用DSL编程将数据进行处理。
 * <p>
 * Final data set object. In this object, the added data will be immutable data types. In these data types, you can directly use DSL programming to process the data.
 *
 * @author 赵凌宇
 * 2023/3/8 11:26
 */
public class FDataFrame implements DataFrame {

    private final List<Series> list;
    private final Series colNameRow;
    private final int primaryIndex;
    private final HashMap<String, Integer> colHashMap;
    private final HashMap<String, Integer> rowHashMap;

    public FDataFrame(Series colNameRow, int primaryIndex, Series... series) {
        this.primaryIndex = primaryIndex;
        list = Arrays.asList(series);
        this.colNameRow = colNameRow;
        colHashMap = new HashMap<>(colNameRow.count().getIntValue() + 4);
        rowHashMap = new HashMap<>(series.length + 4);
    }

    public FDataFrame(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList) {
        list = arrayList;
        this.colNameRow = colNameRow;
        colHashMap = new HashMap<>(colNameRow.count().getIntValue() + 4);
        rowHashMap = new HashMap<>(arrayList.size() + 4);
        this.primaryIndex = primaryIndex;
    }

    /**
     * 从本地文件系统中读取一个数据对象，并返回对应数据对象的建造者类。
     *
     * @param file 需要被读取的文件对象。
     * @return 读取之后会返回该数据集对应的一个建造者对象，在该对象中可以对读取操作进行更加详细的设置，
     */
    public static FDataFrameBuilder builder(File file) {
        return new FDataFrameBuilder(file);
    }

    /**
     * 从远程数据库中读取一个数据对象，并返回数据对象对应的建造者类。
     *
     * @param DBC 在连接数据库时需要使用的数据库连接对象。
     * @return 数据库连接设置完毕将会返回一个建造者对象，在该对象中可以对读取数据库操作进行更加详细的设置。
     */
    public static FDataFrameBuilder builder(Connection DBC) {
        return new FDataFrameBuilder(DBC);
    }

    /**
     * 刷新字段数据，在数据集中包含针对行列字段名称构建的索引Hash表，在hash表中的字段时可以进行刷新的，经过刷新之后，原先的字段将不会消失，而是与新字段共同指向同一个数据行，一般来说，不更改行字段的情况下将不会调用该函数。
     * <p>
     * Refresh field data. The data set contains an index hash table built for row and column field names. The fields in the hash table can be refreshed. After refreshing, the original fields will not disappear, but point to the same data row with the new fields. Generally speaking, this function will not be called without changing the row fields.
     */
    FDataFrame refreshField(boolean rr, boolean rc) {
        int index = -1;
        if (rc) {
            for (Cell<?> cell : this.colNameRow) {
                colHashMap.put(cell.toString(), ++index);
                colHashMap.put(cell.getStringValue(), index);
            }
        }
        if (rr) {
            index = -1;
            for (Series cells : this.list) {
                rowHashMap.put(cells.getCell(primaryIndex).getStringValue(), ++index);
            }
        }
        return this;
    }

    /**
     * 获取到当前表中的字段对象。
     *
     * @return 字段对象序列
     */
    @Override
    public Series getFields() {
        return this.colNameRow;
    }

    /**
     * 获取到当前表中的字段信息与表信息。
     * <p>
     * Get the field information and table information in the current table.
     *
     * @return 当前数据表中的所有字段信息与表信息组成的新数据表。
     * <p>
     * A new data table composed of all field information and table information in the current data table.
     */
    @Override
    public DataFrame desc() {
        int length = 4 + this.colNameRow.count().getIntValue();
        // 第一行是所有的列字段
        String[] strings1 = new String[length];
        strings1[0] = "property(By first)";
        strings1[1] = "RC";
        strings1[2] = "CC";
        strings1[3] = "PK";
        // 第二行是所有字段在原数据表中是否为数值类型
        String[] strings2 = new String[length];
        strings2[0] = "isNumber(By first)";
        strings2[1] = "YES";
        strings2[2] = "YES";
        // 第三行是所有字段在原数据表是否为主键
        String[] strings3 = new String[length];
        strings3[0] = "row_is_PrimaryKey";
        if (this.list.size() > 0) {
            // 获取到第一行数据，用于计算列数据情况
            Series cells = this.list.get(0);
            strings2[3] = cells.getCell(primaryIndex).isNumber() ? "YES" : "No";
            int index = 3;
            // 开始将所有列名添加到其中 同时将所有列是否为数值类型添加到其中
            for (Cell<?> cell : this.colNameRow) {
                strings1[++index] = cell.getStringValue();
            }
            index = 3;
            for (Cell<?> cell : cells) {
                strings2[++index] = cell.isNumber() ? "YES" : "No";
            }
            // 开始将所有列主键信息添加到其中。
            for (int i = 1, ok1 = primaryIndex + 3; i < strings3.length; i++) {
                strings3[i] = i == ok1 || i == 2 ? "Yes" : "No";
            }
        }
        // 开始生成数据
        return new FDataFrame(
                FinalSeries.parse(strings1),
                0,
                FinalSeries.parse(strings2),
                FinalSeries.parse(strings3),
                new FinalSeries(
                        new FinalCell<>("valueData_Number"),
                        new FinalCell<>(this.list.size()),
                        this.colNameRow.count()
                )
        ).refreshField(true, true);
    }

    /**
     * 获取到当前表中的指定列字段数据。
     *
     * @param colNames 所有需要被获取的列数据，可以使用 * 代替。
     * @return 查询出指定列数据的DF表。
     */
    @Override
    public DataFrame select(String... colNames) {
        ArrayList<Series> arrayList = new ArrayList<>(list.size() + 4);
        int[] index = new int[colNames.length];
        {
            // 获取到本需要提取的所有列数据
            int count = -1;
            for (String colName : colNames) {
                Integer integer = this.colHashMap.get(colName);
                if (integer != null) {
                    index[++count] = integer;
                } else throw new OperatorOperationException("Unknown fields:" + colName);
            }
        }
        for (Series cells : this.list) {
            // 开始将列数据获取到
            ArrayList<Cell<?>> arrayList1 = new ArrayList<>(colNames.length + 4);
            for (int i : index) {
                arrayList1.add(cells.getCell(i));
            }
            arrayList.add(new FinalSeries(arrayList1.toArray(new Cell[0])));
        }
        // 计算出新的主键索引列
        int pk = 0;
        {
            // 获取到主键列名称
            String s = this.colNameRow.getCell(primaryIndex).toString();
            int index1 = -1;
            // 查看新列中是否包含主键
            for (String colName : colNames) {
                ++index1;
                if (Objects.equals(s, colName)) {
                    // 如果包含，则代表新列的新主键找到了
                    pk = index1;
                    break;
                }
            }
        }
        return new FDataFrame(FieldCell.parse(colNames), pk, arrayList).refreshField(false, true);
    }

    /**
     * 获取到当前表中的指定列字段数据。
     *
     * @param colNames 所有需要被获取的列数据，需要注意的是，在这里不允许使用 * 哦！
     * @return 查询出指定列数据的DF表。
     */
    @Override
    public DataFrame select(FieldCell... colNames) {
        // 将别名尽量转换成为列名称
        String[] res = new String[colNames.length];
        int index = -1;
        for (FieldCell colName : colNames) {
            FieldCell byAs = FieldCell.getByAs(
                    colName.getStringValue()
            );
            if (byAs == null) {
                res[++index] = FieldCell.$(colName.getStringValue()).getStringValue();
            } else {
                res[++index] = byAs.getStringValue();
            }
        }
        return select(res);
    }

    /**
     * 查询当前表中的指定行字段数据。
     *
     * @param rowNames 需要被获取到的数据行的行名称。
     * @return 查询出指定行数据的DF表
     */
    @Override
    public DataFrame selectRow(String... rowNames) {
        ArrayList<Series> arrayList = new ArrayList<>(rowNames.length + 4);
        for (String rowName : rowNames) {
            Integer integer = this.rowHashMap.get(rowName);
            if (integer != null) {
                arrayList.add(this.list.get(integer));
            }
        }
        return new FDataFrame(this.colNameRow, primaryIndex, arrayList.toArray(new Series[0]))
                .refreshField(true, false);
    }

    /**
     * 将指定的符合条件的数据获取到。
     *
     * @param whereClause 条件判断逻辑实现。
     * @return 符合条件的数据系列组合成的新数据表。
     */
    @Override
    public DataFrame where(Condition whereClause) {
        ArrayList<Series> arrayList = new ArrayList<>(this.list.size());
        for (Series cells : this.list) {
            if (whereClause.isComplianceEvents(cells)) arrayList.add(cells);
        }
        return new FDataFrame(this.colNameRow, this.primaryIndex, arrayList.toArray(new Series[0]))
                .refreshField(true, false);
    }

    @Override
    public DataFrame sort(String... colNames) {
        return sort(colNames, true);
    }

    @Override
    public DataFrame sort(String[] colNames, boolean asc) {
        // 生成列对应的索引数值
        int[] indexS = new int[colNames.length];
        {
            int i = -1;
            for (String colName : colNames) {
                indexS[++i] = this.colHashMap.get(colName);
            }
        }
        TreeSet<Series> treeSet;
        if (asc) {
            treeSet = new TreeSet<>((o1, o2) -> {
                for (int index : indexS) {
                    Cell<?> cell1 = o1.getCell(index);
                    Cell<?> cell2 = o2.getCell(index);
                    int res = cell1.isNumber() && cell2.isNumber() ?
                            (int) Math.ceil(cell1.getDoubleValue() - cell2.getDoubleValue()) :
                            cell1.getStringValue().compareTo(cell2.getStringValue());
                    if (res != 0) return res;
                }
                return 0;
            });
        } else {
            treeSet = new TreeSet<>((o1, o2) -> {
                for (int index : indexS) {
                    Cell<?> cell1 = o1.getCell(index), cell2 = o2.getCell(index);
                    int res = cell2.isNumber() && cell1.isNumber() ?
                            (int) Math.ceil(cell2.getDoubleValue() - cell1.getDoubleValue()) :
                            cell2.getStringValue().compareTo(cell1.getStringValue());
                    if (res != 0) return res;
                }
                return 0;
            });
        }
        // 开始重新排序
        treeSet.addAll(this.list);
        return new FDataFrame(this.colNameRow, this.primaryIndex, treeSet.toArray(new Series[0]))
                .refreshField(true, false);
    }

    @Override
    public GroupDataFrameData groupBy(String colName) {
        // 获取到对应列字段的索引数值
        Integer colIndex = this.colHashMap.get(colName);
        if (colIndex != null) {
            // 将这列所有的数据都进行处理
            return new FinalGroupTable(colNameRow, primaryIndex, colIndex, this);
        } else throw new OperatorOperationException("Unknown line: " + colName);
    }

    @Override
    public DataFrame limit(int topN) {
        return limit(0, topN);
    }

    @Override
    public DataFrame limit(int start, int len) {
        if (start + len > this.list.size()) {
            len = this.list.size() - start;
        }
        Series[] series = new Series[len];
        int index = -1;
        List<Series> seriesList = this.list;
        for (int i = start, seriesListSize = seriesList.size(); ++index < len && i < seriesListSize; i++) {
            series[index] = seriesList.get(i);
        }
        return new FDataFrame(this.colNameRow, this.primaryIndex, series)
                .refreshField(true, false);
    }

    @Override
    public DataFrame limit(String startRowName, String EndRowName) {
        Integer integer = this.rowHashMap.get(startRowName);
        if (integer != null) {
            Integer integer1 = this.rowHashMap.get(EndRowName);
            if (integer1 != null) {
                return limit(Math.min(integer, integer1), ASMath.absoluteValue(integer1 - integer) + 1);
            } else throw new OperatorOperationException("Unknown line: " + EndRowName);
        } else throw new OperatorOperationException("Unknown line: " + startRowName);
    }

    @Override
    public DataFrame into_outfile(String outPath) {
        return into_outfile(outPath, ",");
    }

    @Override
    public DataFrame into_outfile(String outPath, String sep) {
        ASIO.writer(new File(outPath), bufferedWriter -> {
                    try {
                        bufferedWriter.write("rowNumber");
                        for (Cell<?> cell : this.colNameRow) {
                            bufferedWriter.write(sep);
                            bufferedWriter.write(cell.getStringValue());
                        }
                        bufferedWriter.newLine();
                        int rowNum = 0;
                        for (Series cells : this.list) {
                            bufferedWriter.write(String.valueOf(++rowNum));
                            for (Cell<?> cell : cells) {
                                bufferedWriter.write(sep);
                                bufferedWriter.write(cell.getStringValue());
                            }
                            bufferedWriter.newLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        return this;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder split = new StringBuilder();
        String s;
        {
            split.append('+');
            stringBuilder.append('|');
            for (Cell<?> cell : this.getFields()) {
                stringBuilder.append('\t').append('\t').append(cell);
                split.append("---------------");
            }
            split.append('+').append('\n');
            s = split.toString();
            stringBuilder.append('\n').append(s);
        }
        for (Series cells : this.list) {
            stringBuilder.append('|');
            for (Cell<?> cell : cells) {
                stringBuilder.append('\t').append('|').append('\t').append(cell.getValue());
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append(s);
        return s + stringBuilder;
    }

    /**
     * 获取到当前Series中包含的数据对象数量。
     * <p>
     * Gets the number of data objects contained in the current Series.
     *
     * @return 当前行列中的单元格数据对象的数量。
     * <p>
     * The number of cell data objects in the current row.
     */
    @Override
    public Cell<Integer> count() {
        return new FinalCell<>(this.list.size());
    }

    /**
     * 获取到当前Series中所有数值类型的数据之和。
     * <p>
     * Get the sum of data of all numeric types in the current Series.
     *
     * @return 当前系列对象中的所有数值类数值对象之和。
     * <p>
     * The sum of all numeric objects in the current series of objects.
     */
    @Override
    public Cell<Double> sum() {
        double res = 0;
        for (Series cells : this.list) {
            res += cells.sum().getValue();
        }
        return new FinalCell<>(res);
    }

    /**
     * 获取到当前Series中所有数值类型的数据平均值。
     * <p>
     * Get the average value of all data types in the current Series.
     *
     * @return 当前系列对象中的所有数值类数值的平均值。
     * <p>
     * The avg of all numeric objects in the current series of objects.
     */
    @Override
    public Cell<Double> avg() {
        double res = 0;
        for (Series cells : this.list) {
            res += cells.avg().getValue();
        }
        return new FinalCell<>(res / this.list.size());
    }

    /**
     * 使用自定义聚合函数的方式对诸多数值进行聚合计算操作。
     * <p>
     * Use the custom aggregate function to aggregate many values.
     *
     * @param transformation 需要用来进行计算的函数对象。
     *                       <p>
     *                       The function object that needs to be used for calculation.
     * @return 计算之后的结果数值。
     * <p>
     * The result value after calculation.
     */
    @Override
    public Series agg(Transformation<List<Series>, Series> transformation) {
        return transformation.function(this.list);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Series> iterator() {
        return this.list.iterator();
    }
}
