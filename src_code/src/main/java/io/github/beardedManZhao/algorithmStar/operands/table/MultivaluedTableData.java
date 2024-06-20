package io.github.beardedManZhao.algorithmStar.operands.table;

import io.github.beardedManZhao.algorithmStar.utils.Event;

/**
 * 多值函数 表数据对象，在其中包含诸多的多数值返回函数
 *
 * @author 赵凌宇
 * 2023/3/7 22:24
 */
public interface MultivaluedTableData {

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
    Series filterDouble(Event<Double> event);

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
    Series filterInteger(Event<Integer> event);

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
    Series filter(Event<Cell<?>> event);
}
