package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.Event;
import zhao.algorithmMagic.utils.transformation.Transformation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * 数据系列对象，在该对象中能够进行多个单元格参数的获取。
 * <p>
 * Data series object, in which multiple cell parameters can be obtained.
 *
 * @author 赵凌宇
 * 2023/3/7 22:01
 */
public class FinalSeries implements Series {
    private final Cell<?>[] cells;
    private final Cell<Integer> length;

    public FinalSeries(Cell<?>... cells) {
        if (cells != null) {
            this.cells = cells;
            this.length = new FinalCell<>(cells.length);
        } else {
            throw new OperatorOperationException("FinalSeries cannot be null!!!");
        }
    }

    /**
     * 将一个Series 和很多个单元格进行数据合并
     *
     * @param finalSeries 需要被合并的Series对象
     * @param cells       合需要被合并的所有单元格
     * @return 合并之后的数据对象
     */
    public static FinalSeries merge(Series finalSeries, Cell<?>... cells) {
        Cell<?>[] cells1 = finalSeries.toArray();
        int length1 = cells1.length;
        Cell<?>[] res = new Cell[length1 + cells.length];
        ASClass.mergeArray(res, cells1, cells);
        return new FinalSeries(res);
    }

    public static FinalSeries parse(String... arr) {
        return new FinalSeries(
                Arrays.stream(arr).map(FinalCell::new)
                        .toArray((IntFunction<Cell<?>[]>) value -> new Cell[arr.length])
        );
    }

    public static <T> FinalSeries parse(T[] arr) {
        return new FinalSeries(
                Arrays.stream(arr).map(FinalCell::new)
                        .toArray((IntFunction<Cell<?>[]>) value -> new Cell[arr.length])
        );
    }

    /**
     * @param index 需要获取的目标单元格的在列表中的索引编号。
     *              <p>
     *              The index number of the target cell in the list that needs to be obtained.
     * @return 单元格的数据对象。
     * <p>
     * The data object of the cell.
     */
    @Override
    public Cell<?> getCell(int index) {
        return cells[index];
    }

    /**
     * 将一个单元格对象设置成为某个单元格的新数值。
     *
     * @param index 需要设置的单元格索引位置。
     * @param cell  需要设置成的新单元格数据对象。
     */
    @Override
    public void setCell(int index, Cell<?> cell) {
        this.cells[index] = cell;
    }

    /**
     * 将每一个单元格的数组形式的数据直接获取到。
     *
     * @return 一行数据中所有单元格构成的数组
     */
    @Override
    public Cell<?>[] toArray() {
        return this.cells;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    @Nonnull
    public Iterator<Cell<?>> iterator() {
        return Arrays.stream(this.cells).iterator();
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
        return length;
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
        for (Cell<?> cell : this.cells) {
            if (cell.isNumber()) res += ((Number) cell.getValue()).doubleValue();
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
        Series filter = filter(Event.NUM_TRUE);
        for (Cell<?> cell : filter) {
            res += cell.getDoubleValue();
        }
        return new FinalCell<>(res / filter.count().getValue());
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
    public Cell<?> agg(Transformation<Cell<?>[], Cell<?>> transformation) {
        return transformation.function(this.cells);
    }

    @Override
    public String toString() {
        return Arrays.toString(cells);
    }

    /**
     * 过滤函数，其接受一个过滤函数实现逻辑，并将符合条件的数值进行数据的过滤计算。
     * <p>
     * Filter function, which accepts a filter function to implement logic, and performs data filtering calculation for qualified values.
     *
     * @param event 在过滤函数时需要使用的函数对象。
     *              <p>
     *              Function object to be used when filtering functions.
     * @return 经过了过滤函数的处理之后的数值系列对象。
     * <p>
     * 经过了过滤函数的处理之后的数值系列对象。
     */
    @Override
    public Series filterDouble(Event<Double> event) {
        ArrayList<Cell<?>> arrayList = new ArrayList<>();
        for (Cell<?> cell : this.cells) {
            if (cell.isNumber() && event.isComplianceEvents(((Number) cell.getValue()).doubleValue())) {
                arrayList.add(cell);
            }
        }
        return new FinalSeries(arrayList.toArray(new Cell<?>[0]));
    }

    /**
     * 过滤函数，其接受一个过滤函数实现逻辑，并将符合条件的数值进行数据的过滤计算。
     * <p>
     * Filter function, which accepts a filter function to implement logic, and performs data filtering calculation for qualified values.
     *
     * @param event 在过滤函数时需要使用的函数对象。
     *              <p>
     *              Function object to be used when filtering functions.
     * @return 经过了过滤函数的处理之后的数值系列对象。
     * <p>
     * 经过了过滤函数的处理之后的数值系列对象。
     */
    @Override
    public Series filterInteger(Event<Integer> event) {
        ArrayList<Cell<?>> arrayList = new ArrayList<>();
        for (Cell<?> cell : this.cells) {
            if (cell.isNumber() && event.isComplianceEvents(((Number) cell.getValue()).intValue())) {
                arrayList.add(cell);
            }
        }
        return new FinalSeries(arrayList.toArray(new Cell<?>[0]));
    }

    /**
     * 过滤函数，其接受一个过滤函数实现逻辑，并将符合条件的数值进行数据的过滤计算。
     * <p>
     * Filter function, which accepts a filter function to implement logic, and performs data filtering calculation for qualified values.
     *
     * @param event 在过滤函数时需要使用的函数对象。
     *              <p>
     *              Function object to be used when filtering functions.
     * @return 经过了过滤函数的处理之后的数值系列对象。
     * <p>
     * 经过了过滤函数的处理之后的数值系列对象。
     */
    @Override
    public Series filter(Event<Cell<?>> event) {
        ArrayList<Cell<?>> arrayList = new ArrayList<>();
        for (Cell<?> cell : this.cells) {
            if (cell.isNumber() && event.isComplianceEvents(cell)) {
                arrayList.add(cell);
            }
        }
        return new FinalSeries(arrayList.toArray(new Cell<?>[0]));
    }

    @Override
    public Stream<Cell<?>> toStream() {
        return Arrays.stream(this.cells);
    }
}
