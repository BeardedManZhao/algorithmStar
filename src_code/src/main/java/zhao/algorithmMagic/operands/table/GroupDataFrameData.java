package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.utils.transformation.Transformation;

import java.util.List;

/**
 * 聚合表数据对象，该对象是 table 组件中所有支持多数值聚合的数据对象的父类，该对象包含诸多的计算函数以及自定义聚合函数。
 * <p>
 * Aggregate table data object, which is the parent class of all data objects that support multi-value aggregation in the table component. This object contains many calculation functions and user-defined aggregate functions.
 *
 * @author 赵凌宇
 * 2023/3/7 22:09
 */
public interface GroupDataFrameData {

    /**
     * 获取到当前D中包含的数据对象数量。
     * <p>
     * Gets the number of data objects contained in the current D.
     *
     * @return 当前行列中的单元格数据对象的数量。
     * <p>
     * The number of cell data objects in the current row.
     */
    FDataFrame count();

    /**
     * 获取到当前D中所有数值类型的数据之和。
     * <p>
     * Get the sum of data of all numeric types in the current D.
     *
     * @return 当前系列对象中的所有数值类数值对象之和。
     * <p>
     * The sum of all numeric objects in the current D of objects.
     */
    FDataFrame sum();

    /**
     * 获取到当前D中所有数值类型的数据平均值。
     * <p>
     * Get the average value of all data types in the current D.
     *
     * @return 当前系列对象中的所有数值类数值的平均值。
     * <p>
     * The avg of all numeric objects in the current D of objects.
     */
    FDataFrame avg();

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
    FDataFrame agg(Transformation<List<Series>, Series> transformation);
}
