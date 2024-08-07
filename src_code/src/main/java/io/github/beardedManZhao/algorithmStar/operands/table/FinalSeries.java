package io.github.beardedManZhao.algorithmStar.operands.table;

import io.github.beardedManZhao.algorithmStar.SerialVersionUID;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.Event;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;

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

    private static final long serialVersionUID = SerialVersionUID.FINAL_SERIES.getNum(); // 自定义序列化版本号

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
        ASMath.filterDouble(this, event, arrayList);
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
        ASMath.filterInteger(this, event, arrayList);
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
        ASMath.filterNumber(this, event, arrayList);
        return new FinalSeries(arrayList.toArray(new Cell<?>[0]));
    }

    @Override
    public Stream<Cell<?>> toStream() {
        return Arrays.stream(this.cells);
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
    public Series add(Series value) {
        Cell<?>[] cells1 = this.cells, cells2 = value.toArray();
        if (cells1.length != cells2.length) {
            throw new OperatorOperationException("Add Error:请确保两个Series的维度相同\n" +
                    "Please ensure that the dimensions of both Series are the same");
        }
        return new FinalSeries(ASMath.add(cells1, cells2));
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
    public Series diff(Series value) {
        Cell<?>[] cells1 = this.cells, cells2 = value.toArray();
        if (cells1.length != cells2.length) {
            throw new OperatorOperationException("Diff Error:请确保两个Series的维度相同\n" +
                    "Please ensure that the dimensions of both Series are the same");
        }
        return new FinalSeries(ASMath.diff(cells1, cells2));
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
    public Series add(Number value) {
        Cell<?>[] clone = new Cell[this.cells.length];
        double v = value.doubleValue();
        int index = -1;
        for (Cell<?> cell : this) {
            clone[++index] = cell.add(v);
        }
        return new FinalSeries(clone);
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
    public Series diff(Number value) {
        Cell<?>[] clone = new Cell[this.cells.length];
        double v = value.doubleValue();
        int index = -1;
        for (Cell<?> cell : this) {
            clone[++index] = cell.diff(v);
        }
        return new FinalSeries(clone);
    }

    /**
     * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
     * <p>
     * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
     *
     * @return 当前类对应的子类实现数据类型的对象。
     * <p>
     * The subclass corresponding to the current class implements objects of data type.
     */
    @Override
    public Series expand() {
        return this;
    }
}
