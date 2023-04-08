package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.operands.Operands;

import java.io.Serializable;
import java.util.Date;

/**
 * 单元格数据类型，该数据类型被 Multi-type 矩阵所使用，并进行相对应的处理与操作。
 *
 * @author 赵凌宇
 * 2023/3/7 21:32
 */
public interface Cell<valueType> extends Serializable, Operands<Cell<?>> {

    /**
     * @return 获取到当前单元格中的数据是否属于数值类型，如果返回 true 代表当前单元格中存储的是一个数值类型的数据。
     * <p>
     * Gets whether the data in the current cell is of numerical type. If true is returned, it means that the data stored in the current cell is of numerical type.
     */
    boolean isNumber();

    /**
     * @return 当前单元格中存储的数据数值，需要注意的是该函数返回的是一个浅拷贝的数值。
     * <p>
     * The data value stored in the current cell. It should be noted that this function returns a shallow copy of the value.
     */
    valueType getValue();

    /**
     * @return 当前单元格中存储的整数数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The integer numeric type stored in the current cell. This operation will not return the original object.
     */
    int getIntValue();

    /**
     * @return 当前单元格中存储的整数数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The integer numeric type stored in the current cell. This operation will not return the original object.
     */
    long getLongValue();

    /**
     * @return 当前单元格中存储的Double数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The Double numeric type stored in the current cell. This operation will not return the original object.
     */
    double getDoubleValue();

    /**
     * @return 当前单元格中存储的 Date 数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The Date numeric type stored in the current cell. This operation will not return the original object.
     */
    Date getDate();

    /**
     * @return 当前单元格中存储的字符串数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The String numeric type stored in the current cell. This operation will not return the original object.
     */
    String getStringValue();
}
