package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.utils.transformation.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组数据集表，在该表中能够将每一个组对应的数据行展示出来，将每一个分组数据对应的数据集保存下来并进行各种数据处理的计算。
 * <p>
 * The grouping data set table, in which the data row corresponding to each group can be displayed, the data set corresponding to each grouping data can be saved and various data processing calculations can be performed.
 *
 * @author 赵凌宇
 * 2023/3/9 14:58
 */
public class FinalGroupTable implements GroupDataFrameData {

    private final HashMap<String, DataFrame> hashMap;
    private final Cell<?> groupKey;
    private final Series colNameRow;

    /**
     * 构造一个分组表数据，该表用于存储一个分组之后的新数据表，在新数据表张能够进行诸多的按组聚合需求与操作。
     *
     * @param colNameRow   被分组表的字段名称行数据。
     * @param primaryIndex 被分组表的主键字段列索引数值，从0开始计算。
     * @param index        被分组表中的分组字段索引数值，从0开始计算。
     * @param dataFrame    需要被分组的表数据对象。
     */
    public FinalGroupTable(Series colNameRow, int primaryIndex, int index, DataFrame dataFrame) {
        HashMap<String, ArrayList<Series>> hashMap1 = new HashMap<>();
        // 开始按照 index 进行分组
        for (Series cells : dataFrame) {
            String key = cells.getCell(index).getStringValue();
            ArrayList<Series> arrayLists = hashMap1.get(key);
            if (arrayLists == null) {
                arrayLists = new ArrayList<>();
                arrayLists.add(cells);
                hashMap1.put(key, arrayLists);
            } else hashMap1.get(key).add(cells);
        }
        hashMap = new HashMap<>();
        hashMap1.forEach((key, value) -> {
            hashMap.put(key, new FDataFrame(colNameRow, primaryIndex, value.toArray(new Series[0])));
            value.clear();
        });
        groupKey = colNameRow.getCell(index);
        this.colNameRow = colNameRow;
    }

    /**
     * 获取到当前D中包含的数据对象数量。
     * <p>
     * Gets the number of data objects contained in the current D.
     *
     * @return 当前行列中的单元格数据对象的数量。
     * <p>
     * The number of cell data objects in the current row.
     */
    @Override
    public FDataFrame count() {
        Series[] series = new Series[hashMap.size()];
        int index = -1;
        for (Map.Entry<String, DataFrame> entry : this.hashMap.entrySet()) {
            series[++index] = new FinalSeries(
                    new FinalCell<>(entry.getKey()), entry.getValue().count()
            );
        }
        return new FDataFrame(new FinalSeries(this.groupKey, new FinalCell<>("count()")), 0, series);
    }

    /**
     * 获取到当前D中所有数值类型的数据之和。
     * <p>
     * Get the sum of data of all numeric types in the current D.
     *
     * @return 当前系列对象中的所有数值类数值对象之和。
     * <p>
     * The sum of all numeric objects in the current D of objects.
     */
    @Override
    public FDataFrame sum() {
        Series[] series = new Series[hashMap.size()];
        int index = -1;
        for (Map.Entry<String, DataFrame> entry : this.hashMap.entrySet()) {
            series[++index] = new FinalSeries(
                    new FinalCell<>(entry.getKey()), entry.getValue().sum()
            );
        }
        return new FDataFrame(new FinalSeries(this.groupKey, new FinalCell<>("sum()")), 0, series);
    }

    /**
     * 获取到当前D中所有数值类型的数据平均值。
     * <p>
     * Get the average value of all data types in the current D.
     *
     * @return 当前系列对象中的所有数值类数值的平均值。
     * <p>
     * The avg of all numeric objects in the current D of objects.
     */
    @Override
    public FDataFrame avg() {
        Series[] series = new Series[hashMap.size()];
        int index = -1;
        for (Map.Entry<String, DataFrame> entry : this.hashMap.entrySet()) {
            series[++index] = new FinalSeries(
                    new FinalCell<>(entry.getKey()), entry.getValue().avg()
            );
        }
        return new FDataFrame(new FinalSeries(this.groupKey, new FinalCell<>("avg()")), 0, series);
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
    public FDataFrame agg(Transformation<List<Series>, Series> transformation) {
        Series[] series = new Series[hashMap.size()];
        int index = -1;
        for (Map.Entry<String, DataFrame> entry : this.hashMap.entrySet()) {
            series[++index] = entry.getValue().agg(transformation);
        }
        return new FDataFrame(this.colNameRow, 0, series);
    }
}
