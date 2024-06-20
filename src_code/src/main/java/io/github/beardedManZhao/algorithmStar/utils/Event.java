package io.github.beardedManZhao.algorithmStar.utils;

import io.github.beardedManZhao.algorithmStar.operands.table.Cell;

/**
 * 事件函数接口，该事件用于概率等操作，在概率中，可通过该函数得出符合事件的对象数值，然后利用该数值对序列中所有数值的数量进行作商，也是计算概率时候的随机变量。
 * <p>
 * Event function interface, the event is used for operations such as probability. In probability, the value of the object that matches the event can be obtained through this function, and then the number of all values in the sequence can be used to quotient, which is also a random variable when calculating probability. .
 *
 * @param <value> 判断是否符合事件的数据类型
 *                <p>
 *                Determine if it matches the data type of the event
 */
public interface Event<value> {
    /**
     * 过滤数值类型为数值的单元格对象。
     */
    Event<Cell<?>> NUM_TRUE = Cell::isNumber;

    boolean isComplianceEvents(value v);
}
