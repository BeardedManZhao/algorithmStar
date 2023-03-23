package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASStr;

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
}
