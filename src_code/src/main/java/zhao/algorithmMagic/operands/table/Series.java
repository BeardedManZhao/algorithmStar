package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.operands.Operands;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * 在table中的单元行列或列数据对象，是很多cell对象的存储类对象。
 *
 * @author 赵凌宇
 * 2023/3/7 21:42
 */
public interface Series extends Iterable<Cell<?>>, AggSeriesData, MultivaluedTableData, Serializable, Operands<Series> {

    /**
     * @param index 需要获取的目标单元格的在列表中的索引编号。
     *              <p>
     *              The index number of the target cell in the list that needs to be obtained.
     * @return 单元格的数据对象。
     * <p>
     * The data object of the cell.
     */
    Cell<?> getCell(int index);

    /**
     * 将一个单元格对象设置成为某个单元格的新数值。
     *
     * @param index 需要设置的单元格索引位置。
     * @param cell  需要设置成的新单元格数据对象。
     */
    void setCell(int index, Cell<?> cell);

    /**
     * 将每一个单元格的数组形式的数据直接获取到。
     *
     * @return 一行数据中所有单元格构成的数组
     */
    Cell<?>[] toArray();

    /**
     * 获取到当前系列中所有单元格对象的Stream对象。
     *
     * @return 系列单元格中的元素封装至Java的Stream中。
     */
    Stream<Cell<?>> toStream();
}
