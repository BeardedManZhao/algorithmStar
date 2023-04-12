package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.io.OutputComponent;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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

    protected final List<Series> list;
    protected final Series colNameRow;
    protected final int primaryIndex;
    protected final HashMap<String, Integer> colHashMap;
    protected final HashMap<String, Integer> rowHashMap;

    public FDataFrame(Series colNameRow, int primaryIndex, HashMap<String, Integer> colHashMap, HashMap<String, Integer> rowHashMap, Series... series) {
        this.primaryIndex = primaryIndex;
        list = Arrays.asList(series);
        this.colNameRow = colNameRow;
        this.colHashMap = colHashMap;
        this.rowHashMap = rowHashMap;
    }

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

    public FDataFrame(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList, HashMap<String, Integer> rowHashMap, HashMap<String, Integer> colHashMap) {
        this.primaryIndex = primaryIndex;
        list = arrayList;
        this.colNameRow = colNameRow;
        this.colHashMap = colHashMap;
        this.rowHashMap = rowHashMap;
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
     * 创建出一个空的DF对象
     *
     * @param colNameRow DF中的字段序列。
     * @param pk         主键列的索引数值
     * @return 空的DF对象。
     */
    public static FDataFrame select(Series colNameRow, int pk) {
        return new FDataFrame(
                colNameRow, pk, new ArrayList<>()
        ).refreshField(false, true);
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame builder(InputComponent inputComponent) {
        if (inputComponent.open()) {
            DataFrame dataFrame = inputComponent.getDataFrame();
            ASIO.close(inputComponent);
            return dataFrame;
        }
        throw new OperatorOperationException("inputComponent open error!!!");
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame builder(InputComponent inputComponent, boolean isOC) {
        if (isOC) {
            if (inputComponent.open()) {
                DataFrame dataFrame = inputComponent.getDataFrame();
                ASIO.close(inputComponent);
                return dataFrame;
            }
            throw new OperatorOperationException("inputComponent open error!!!");
        } else return inputComponent.getDataFrame();
    }

    /**
     * 刷新字段数据，在数据集中包含针对行列字段名称构建的索引Hash表，在hash表中的字段时可以进行刷新的，经过刷新之后，原先的字段将不会消失，而是与新字段共同指向同一个数据行，一般来说，不更改行字段的情况下将不会调用该函数。
     * <p>
     * Refresh field data. The data set contains an index hash table built for row and column field names. The fields in the hash table can be refreshed. After refreshing, the original fields will not disappear, but point to the same data row with the new fields. Generally speaking, this function will not be called without changing the row fields.
     */
    @Override
    public FDataFrame refreshField(boolean rr, boolean rc) {
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
                rowHashMap.put(cells.getCell(primaryIndex).toString(), ++index);
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
        strings3[0] = "col_is_PrimaryKey";
        // 第四行是所有字段的别名
        String[] strings4 = new String[length];
        strings4[0] = "Alias of this field";
        strings4[1] = strings1[1];
        strings4[2] = strings1[2];
        strings4[3] = strings1[3];
        if (this.list.size() > 0) {
            // 获取到第一行数据，用于计算列数据情况
            Series cells = this.list.get(0);
            strings2[3] = cells.getCell(primaryIndex).isNumber() ? "YES" : "No";
            int index = 3;
            // 开始将所有列名添加到其中 同时将所有列是否为数值类型添加到其中
            for (Cell<?> cell : this.colNameRow) {
                strings1[++index] = cell.getStringValue();
                strings4[index] = cell.toString();
            }
            index = 3;
            for (Cell<?> cell : cells) {
                strings2[++index] = cell.isNumber() ? "YES" : "No";
            }
            // 开始将所有列主键信息添加到其中。
            for (int i = 1, ok1 = primaryIndex + 4; i < strings3.length; i++) {
                strings3[i] = i == ok1 || i == 3 ? "Yes" : "No";
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
                ),
                FinalSeries.parse(strings4)
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
        if (colNames.length == 1 && "*".equals(colNames[0])) {
            return this;
        }
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
            arrayList.add(new FinalSeries(ASClass.CollToArray(new Cell[arrayList1.size()], arrayList1)));
        }
        // 计算出新的主键索引列
        int pk = 0;
        {
            // 获取到主键列名称
            FieldCell s = (FieldCell) this.colNameRow.getCell(primaryIndex);
            String s1 = s.getStringValue();
            String s2 = s.toString();
            int index1 = -1;
            // 查看新列中是否包含主键
            for (String colName : colNames) {
                ++index1;
                if (s1.equals(colName) || (s2.equals(colName))) {
                    // 如果包含，则代表新列的新主键找到了
                    pk = index1;
                    break;
                }
            }
        }
        // 开始查看是否有刷新行主键必要
        {
            String primaryName = colNames[pk];
            Cell<?> cell = this.colNameRow.getCell(primaryIndex);
            if (cell.getStringValue().equals(primaryName) || (cell.toString().equals(primaryName))) {
                // 如果是这种情况就代表行索引不需要更新
                return new FDataFrame(
                        FieldCell.parse(colNames), pk, arrayList,
                        this.rowHashMap, new HashMap<>(colNames.length + 1)
                )
                        .refreshField(false, true);
            }
        }
        return new FDataFrame(FieldCell.parse(colNames), pk, arrayList)
                .refreshField(true, true);
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
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colName 所有需要被获取的列数据，可以使用 * 代替。
     *                <p>
     *                All column data to be obtained can be replaced by *.
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    @Override
    public Series select(String colName) {
        Integer integer = this.colHashMap.get(colName);
        if (integer != null) {
            ArrayList<Cell<?>> arrayList1 = new ArrayList<>(this.list.size() + 4);
            for (Series cells : this.list) {
                arrayList1.add(cells.getCell(integer));
            }
            return new SingletonSeries(ASClass.CollToArray(new Cell[arrayList1.size()], arrayList1));
        } else throw new OperatorOperationException("Unknown fields:" + colName);
    }

    /**
     * 获取到当前表中的指定列字段数据。
     * <p>
     * Gets the specified column field data in the current table.
     *
     * @param colName 所有需要被获取的列数据，需要注意的是，在这里不允许使用 * 哦！
     *                <p>
     *                For all column data to be obtained, please note that * is not allowed here!
     * @return 查询出指定列数据的DF表。
     * <p>
     * Find the DF table of the specified column data.
     */
    @Override
    public Series select(FieldCell colName) {
        FieldCell byAs = FieldCell.getByAs(
                colName.getStringValue()
        );
        if (byAs == null) {
            return select(FieldCell.$(colName.getStringValue()).getStringValue());
        } else {
            return select(byAs.getStringValue());
        }
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
        return new FDataFrame(
                this.colNameRow, primaryIndex,
                new HashMap<>(arrayList.size() + 4), this.colHashMap,
                ASClass.CollToArray(new Series[arrayList.size()], arrayList)
        )
                .refreshField(true, false);
    }

    /**
     * 将指定的符合条件的数据获取到，使用全表扫描。
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
        return new FDataFrame(this.colNameRow, this.primaryIndex, arrayList)
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
        return new FDataFrame(
                this.colNameRow, this.primaryIndex,
                this.colHashMap, this.rowHashMap, ASClass.CollToArray(new Series[treeSet.size()], treeSet)
        )
                .refreshField(false, false);
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

    /**
     * 将符合条件的数据行按照指定的列字段分组 并返回分组之后的新结果数据集。
     *
     * @param colName     需要被用来进行分组的字段数据
     * @param whereClause 在分组的时候进行一次 where 的过滤操作
     * @return 分组之后的数据对象
     */
    @Override
    public GroupDataFrameData groupBy(String colName, Condition whereClause) {
        // 获取到对应列字段的索引数值
        Integer colIndex = this.colHashMap.get(colName);
        if (colIndex != null) {
            // 将这列所有的数据都进行处理
            return new FinalGroupTable(colNameRow, primaryIndex, colIndex, this, whereClause);
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
        return new FDataFrame(this.colNameRow, this.primaryIndex, this.colHashMap, new HashMap<>(), series)
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

    /**
     * 将一个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    @Override
    public DataFrame insert(Series rowSeries) {
        this.list.add(rowSeries);
        this.rowHashMap.put(rowSeries.getCell(this.primaryIndex).toString(), this.list.size() - 1);
        return this;
    }

    /**
     * 将多个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    @Override
    public DataFrame insert(Series... rowSeries) {
        int startLen = this.list.size();
        this.list.addAll(Arrays.asList(rowSeries));
        // 增量更新行索引
        for (int i = startLen, count = 0; count < rowSeries.length; count++, i++) {
            this.rowHashMap.put(
                    rowSeries[count].getCell(this.primaryIndex).toString(),
                    i
            );
        }
        return this;
    }

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
    @Override
    public DataFrame insertColGetNew(FieldCell fieldName, Transformation<Series, Cell<?>> transformation) {
        ArrayList<Series> arrayList = new ArrayList<>(this.list.size() + 10);
        for (Series cells : this.list) {
            arrayList.add(FinalSeries.merge(
                    cells, transformation.function(cells)
            ));
        }
        return new FDataFrame(
                SingletonSeries.merge(this.colNameRow, fieldName),
                this.primaryIndex,
                arrayList,
                this.rowHashMap,
                new HashMap<>(this.colHashMap.size() + 1)
        ).refreshField(false, true);
    }

    /**
     * 将一列字段对应的所有数据按照指定的函数进行更新。
     * <p>
     * Update all data corresponding to a column of fields according to the specified function.
     *
     * @param fieldCell 需要被提取的列字段名称。
     *                  <p>
     *                  The name of the column field to be extracted.
     * @return 更新之后的DF数据对象。
     * <p>
     * DF data object after update.
     */
    @Override
    public DataFrame updateCol(FieldCell fieldCell, Transformation<Cell<?>, Cell<?>> transformation) {
        Integer integer = this.colHashMap.get(fieldCell.toString());
        if (integer != null) {
            for (Series cells : this.list) {
                cells.setCell(integer, transformation.function(cells.getCell(integer)));
            }
        }
        return this;
    }

    /**
     * 将一行字段对应的所有数据按照指定的函数进行更新。
     * <p>
     * Update all data corresponding to a column of fields according to the specified function.
     *
     * @param rowName 需要被提取的列字段名称。
     *                <p>
     *                The name of the column field to be extracted.
     * @return 更新之后的DF数据对象。
     * <p>
     * DF data object after update.
     */
    @Override
    public DataFrame updateRow(String rowName, Transformation<Cell<?>, Cell<?>> transformation) {
        Integer integer = this.rowHashMap.get(rowName);
        if (integer != null) {
            Series cells = this.list.get(integer);
            int index = -1;
            for (Cell<?> cell : cells) {
                if (++index == primaryIndex) {
                    Cell<?> function = transformation.function(cell);
                    this.rowHashMap.put(function.toString(), integer);
                    cells.setCell(index, function);
                } else cells.setCell(index, transformation.function(cell));
            }
        }
        return this;
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
     * 将计算结果以 HTML 表格的格式输出到指定目录的文本文件中。
     *
     * @param outPath   输出的目标文件所在的路径
     * @param tableName 输出的HTML中的表名称。
     * @return 输出之后会返回数据集本身，不会终止调用
     */
    @Override
    public DataFrame into_outHtml(String outPath, String tableName) {
        final Series colNameRow = this.colNameRow;
        List<Series> list = this.list;
        ASIO.writer(
                new File(outPath), bufferedWriter -> {
                    try {
                        bufferedWriter.write("<!DOCTYPE html>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<html lang=\"zh\">");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<style>\n\n    #" + tableName + "  {\n        /*表的背景颜色*/\n        background: black;\n        /* 表中的单元格文字居中*/\ntext-align: center;    }\n\n" +
                                "    .r0 {\n        /* 表格中的第一种数据行背景颜色 */\n        background: beige;\n    }\n" +
                                "\n    .r1 {\n        /* 表格中的第二种数据行背景颜色 */\n        background: bisque;\n    }\n" +
                                "    #colHead{\n        /* 表格中的字段头字体大小设置 */\nfont-size: 18px;\n}</style>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<head>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<meta charset=\"UTF-8\"><title>");
                        bufferedWriter.write(tableName);
                        bufferedWriter.write("</title>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("</head>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<body>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<table id=\"" + tableName + "\">\n<thead>");
                        bufferedWriter.newLine();
                        int rowClass = 0;
                        bufferedWriter.write("<tr id=\"colHead\" class=\"r0\">");
                        for (Cell<?> cell : colNameRow) {
                            bufferedWriter.write("<td>");
                            bufferedWriter.newLine();
                            bufferedWriter.write(cell.toString());
                            bufferedWriter.newLine();
                            bufferedWriter.write("</td>");
                        }
                        bufferedWriter.write("</tr></thead>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("<tbody id=\"tableData\">");

                        for (Series cells : list) {
                            bufferedWriter.write("<tr class=\"r" + ((++rowClass) - (rowClass >> 1 << 1)) + "\" id=\"" + cells.getCell(primaryIndex) + "\">");
                            for (Cell<?> cell : cells) {
                                bufferedWriter.write("<td>");
                                bufferedWriter.newLine();
                                bufferedWriter.write(cell.toString());
                                bufferedWriter.newLine();
                                bufferedWriter.write("</td>");
                                bufferedWriter.newLine();
                            }
                            bufferedWriter.write("</tr>");
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.write("</tbody></table>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("</html>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        return this;
    }

    /**
     * 将计算结果提供给指定的数据输出组件进行数据的输出操作，
     *
     * @param outputComponent 输出数据需要使用的数据输出组件
     * @return 输出之后会返回数据集本身，不会终止调用。
     * <p>
     * After output, the data set itself will be returned and the call will not be terminated.
     */
    @Override
    public DataFrame into_outComponent(OutputComponent outputComponent) {
        if (outputComponent.open()) {
            outputComponent.writeDataFrame(this);
            ASIO.close(outputComponent);
        } else throw new OperatorOperationException("into_outComponent(OutputComponent outputComponent) error!!!");
        return this;
    }

    /**
     * 默认方式查看 DF 数据对象中的数据。
     * <p>
     * The default method is to view data in DF data objects.
     */
    @Override
    public void show() {
        this.show(System.out);
    }

    /**
     * 直接在输出流中将图表中的数据展示出来，相较于 toString 该函数性能更加优秀。
     *
     * @param bufferedWriter 需要使用的数据输出流。
     */
    @Override
    public void show(BufferedWriter bufferedWriter) {
        StringBuilder split = new StringBuilder();
        StringBuilder field = new StringBuilder();
        String s;
        try {
            s = getFieldRowStr(split, field);
            bufferedWriter.append('\n')
                    .append(s)
                    .append(field)
                    .append('\n')
                    .write(s);
            for (Series cells : this.list) {
                bufferedWriter.write("│\t\t");
                for (Cell<?> cell : cells) {
                    bufferedWriter
                            .append(cell.getValue().toString())
                            .write("\t│\t");
                }
                bufferedWriter.write('\n');
            }
            bufferedWriter.append(s);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    public final String getFieldRowStr(StringBuilder split, StringBuilder field) {
        String s;
        split.append('├');
        field.append("│\t\t");
        for (Cell<?> cell : this.getFields()) {
            field.append(cell.toString());
            field.append("\t│\t");
            split.append("─────────────────");
        }
        split.append('┤').append('\n');
        s = split.toString();
        return s;
    }

    /**
     * 直接在输出流中将图表中的数据展示出来，相较于 toString 该函数性能更加优秀。
     *
     * @param printStream 需要使用的数据输出流。
     */
    @Override
    public void show(PrintStream printStream) {
        StringBuilder split = new StringBuilder();
        StringBuilder field = new StringBuilder();
        String s;
        {
            s = getFieldRowStr(split, field);
            printStream.append('\n')
                    .append(s)
                    .append(field)
                    .append('\n')
                    .append(s);
        }
        for (Series cells : this.list) {
            printStream.append("│\t\t");
            for (Cell<?> cell : cells) {
                printStream
                        .append(cell.getValue().toString())
                        .append("\t│\t");
            }
            printStream.append('\n');
        }
        printStream.append(s);
    }

    /**
     * 将DF对象中的所有数据转换成为一个list容器。
     * <p>
     * Converts all data in a DF object into a list container.
     *
     * @return 返回一个包含所有行系列的list容器，在这里的容器是浅拷贝出来的，不会有过多的冗余占用。
     * <p>
     * Returns a list container that contains all row series. Here, the container is lightly copied, without excessive redundancy.
     */
    @Override
    public List<Series> toList() {
        return this.list;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder split = new StringBuilder();
        String s;
        {
            split.append('├');
            stringBuilder.append("│\t\t");
            for (Cell<?> cell : this.getFields()) {
                stringBuilder.append(cell).append("\t│\t");
                split.append("─────────────────");
            }
            split.append('┤').append('\n');
            s = split.toString();
            stringBuilder.append('\n').append(s);
        }
        for (Series cells : this.list) {
            stringBuilder.append("│\t\t");
            for (Cell<?> cell : cells) {
                stringBuilder
                        .append(cell.getValue())
                        .append("\t│\t");
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

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public DataFrame add(DataFrame value) {
        ArrayList<Series> arrayList = new ArrayList<>(this.list.size() + 3);
        List<Series> seriesList1 = this.list;
        List<Series> seriesList2 = value.toList();
        int size1 = seriesList1.size();
        int i = 0;
        for (int seriesListSize = Math.min(size1, seriesList2.size()); i < seriesListSize; i++) {
            arrayList.add(seriesList1.get(i).add(seriesList2.get(i)));
        }
        return new FDataFrame(
                this.colNameRow, this.primaryIndex, arrayList,
                new HashMap<>(), this.colHashMap
        ).refreshField(true, false);
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public DataFrame diff(DataFrame value) {
        ArrayList<Series> arrayList = new ArrayList<>(this.list.size() + 2);
        List<Series> seriesList1 = this.list;
        List<Series> seriesList2 = value.toList();
        int size1 = seriesList1.size();
        int i = 0;
        for (int seriesListSize = Math.min(size1, seriesList2.size()); i < seriesListSize; i++) {
            arrayList.add(seriesList1.get(i).diff(seriesList2.get(i)));
        }
        return new FDataFrame(
                this.colNameRow, this.primaryIndex, arrayList,
                new HashMap<>(), this.colHashMap
        ).refreshField(true, false);
    }
}
