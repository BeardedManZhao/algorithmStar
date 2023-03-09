package zhao.algorithmMagic.operands.table;

import scala.Array;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 赵凌宇
 * @date 2023/3/9 14:58
 */
public class FinalGroupTable implements GroupDataFrameData{

    private final HashMap<String, DataFrame> hashMap;

    public FinalGroupTable(Series colNameRow, int primaryIndex, int index, DataFrame dataFrame) {
        HashMap<String, ArrayList<Series>> hashMap1 = new HashMap<>();
        // 开始按照 index 进行分组
        for (Series cells : dataFrame) {
            String key = cells.getCell(index).getStringValue();
            ArrayList<Series> arrayLists = hashMap1.get(key);
            if (arrayLists == null){
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }
}
