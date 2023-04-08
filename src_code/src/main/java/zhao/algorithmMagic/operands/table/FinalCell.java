package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASStr;

import java.util.Date;

/**
 * 不可变数值的单元格对象，该数据类型将可存储一个单元格，并允许其类型是多样的。
 * <p>
 * A cell object with an immutable value. This data type will store a cell and allow its types to be diverse.
 *
 * @author 赵凌宇
 * 2023/3/7 21:39
 */
public class FinalCell<valueType> implements Cell<valueType> {

    final valueType valueType;
    final boolean isNumber;

    @SuppressWarnings("unchecked")
    public FinalCell(String string) {
        if (string.length() == 0) {
            valueType = (valueType) Integer.valueOf(0);
            this.isNumber = true;
            return;
        }
        this.isNumber = ASStr.checkIsNum(string);
        if (this.isNumber) {
            valueType = (valueType) Double.valueOf(string);
        } else {
            valueType = (valueType) string;
        }
    }

    public FinalCell(valueType valueType) {
        boolean isNumber1;
        this.valueType = valueType;
        if (valueType instanceof String) {
            isNumber1 = ASStr.checkIsNum((String) valueType);
        } else isNumber1 = valueType instanceof Number;
        this.isNumber = isNumber1;
    }


    @SuppressWarnings("unchecked")
    protected FinalCell(String string, boolean isNumber) {
        this.valueType = (valueType) string;
        this.isNumber = isNumber;
    }

    /**
     * @return 获取到当前单元格中的数据是否属于数值类型，如果返回 true 代表当前单元格中存储的是一个数值类型的数据。
     * <p>
     * Gets whether the data in the current cell is of numerical type. If true is returned, it means that the data stored in the current cell is of numerical type.
     */
    @Override
    public final boolean isNumber() {
        return isNumber;
    }

    @Override
    public final valueType getValue() {
        return valueType;
    }

    /**
     * @return 当前单元格中存储的整数数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The integer numeric type stored in the current cell. This operation will not return the original object.
     */
    @Override
    public final int getIntValue() {
        if (isNumber()) {
            return ((Number) this.valueType).intValue();
        } else {
            throw new OperatorOperationException("您在尝试将一个非数值的单元格按照整数的方式提取，这是不允许的。\nYou are trying to extract a non-numeric cell as an integer, which is not allowed.");
        }
    }

    /**
     * @return 当前单元格中存储的整数数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The integer numeric type stored in the current cell. This operation will not return the original object.
     */
    @Override
    public final long getLongValue() {
        if (isNumber()) {
            return ((Number) this.valueType).longValue();
        } else {
            throw new OperatorOperationException("您在尝试将一个非数值的单元格按照整数的方式提取，这是不允许的。\nYou are trying to extract a non-numeric cell as an integer, which is not allowed.");
        }
    }

    /**
     * @return 当前单元格中存储的Double数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The Double numeric type stored in the current cell. This operation will not return the original object.
     */
    @Override
    public final double getDoubleValue() {
        if (isNumber()) {
            return ((Number) this.valueType).doubleValue();
        } else {
            throw new OperatorOperationException("您在尝试将一个非数值的单元格按照浮点数的方式提取，这是不允许的。\nYou are trying to extract a non-numeric cell as an float, which is not allowed.");
        }
    }

    /**
     * @return 当前单元格中存储的 Date 数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The Date numeric type stored in the current cell. This operation will not return the original object.
     */
    @Override
    public Date getDate() {
        if (isNumber()) {
            return new Date(this.getLongValue());
        } else {
            if (this.valueType instanceof Date) return (Date) this.valueType;
            else
                throw new OperatorOperationException("您无法从单元格中提取数据，因为其不属于毫秒数值也不属于日期对象。\nYou cannot extract data from a cell because it does not belong to a millisecond value or a date object.");
        }
    }

    /**
     * @return 当前单元格中存储的字符串数值类型，该操作返回的将不是原先的对象。
     * <p>
     * The String numeric type stored in the current cell. This operation will not return the original object.
     */
    @Override
    public final String getStringValue() {
        return this.valueType.toString();
    }

    @Override
    public String toString() {
        return this.getStringValue();
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
    public Cell<?> add(Cell<?> value) {
        if (this.isNumber && value.isNumber())
            return new FinalCell<>(this.getDoubleValue() + value.getDoubleValue());
        else return this;
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
    public Cell<?> diff(Cell<?> value) {
        if (this.isNumber && value.isNumber())
            return new FinalCell<>(this.getDoubleValue() - value.getDoubleValue());
        else return this;
    }
}
