package io.github.beardedManZhao.algorithmStar.operands.unit;

import io.github.beardedManZhao.algorithmStar.core.BaseValueFactory;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

/**
 * 时间单位数值类，支持时分秒的运算
 * <p>
 * Time unit numerical class, supporting operations of hours, minutes, and seconds
 */
@BaseUnit(value = {
        "天", "时", "分", "秒", "毫秒"
}, baseValue = {24, 60, 60, 1000, 1})
public class DateValue extends BaseValue {

    public DateValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue, BaseValueFactory baseValueFactoryClass) {
        super(valueNumber, c, baseNameKeyValue, baseValueFactoryClass);
    }

    /**
     * @param valueNumber           需要被解析的数值
     *                              <p>
     *                              Value that needs to be parsed
     * @param baseValueFactoryClass 单位数值构造工厂类
     *                              <p>
     *                              Unit Value Construction Factory Class
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    public static BaseValue parse(double valueNumber, BaseValueFactory baseValueFactoryClass) {
        return parse(valueNumber, null, baseValueFactoryClass);
    }

    /**
     * @param valueNumber           需要被解析的数值
     *                              <p>
     *                              Value that needs to be parsed
     * @param baseNameKeyValue      单位的键值对，如果您需要指定数值的单位，您可以在这里进行指定，如果您不需要可以直接设置为 null，请注意 如果您不设置为null 此操作将不会对数值进行任何化简.
     *                              <p>
     *                              The key value pairs of units. If you need to specify the unit of a numerical value, you can specify it here. If you don't need it, you can directly set it to null. Please note that if you don't set it to null, this operation will not simplify the numerical value in any way.
     * @param baseValueFactoryClass 单位数值构造工厂类
     *                              <p>
     *                              Unit Value Construction Factory Class
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    protected static BaseValue parse(double valueNumber, KeyValue<Integer, String> baseNameKeyValue, BaseValueFactory baseValueFactoryClass) {
        return new BaseValue(valueNumber, DateValue.class, baseNameKeyValue, baseValueFactoryClass);
    }
}
