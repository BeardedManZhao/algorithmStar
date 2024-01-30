package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.utils.dataContainer.KeyValue;

/**
 * 重量单位数值，在这里可以直接使用重量相关的单位！
 * <p>
 * Weight unit value, weight related units can be directly used here!
 *
 * @author zhao
 */
@BaseUnit(value = {
        "t（吨）", "kg（千克）", "g（克）", "mg（毫克）", "ug（微克）", "ng（纳克）", "pg（皮克）", "fg（飞克）"
}, baseValue = 1000)
public class WeightValue extends BaseValue {

    protected WeightValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue) {
        super(valueNumber, c, baseNameKeyValue);
    }

    /**
     * @param valueNumber 需要被解析的数值
     *                    <p>
     *                    Value that needs to be parsed
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    public static BaseValue parse(double valueNumber) {
        return parse(valueNumber, null);
    }

    /**
     * @param valueNumber      需要被解析的数值
     *                         <p>
     *                         Value that needs to be parsed
     * @param baseNameKeyValue 单位的键值对，如果您需要指定数值的单位，您可以在这里进行指定，如果您不需要可以直接设置为 null，请注意 如果您不设置为null 此操作将不会对数值进行任何化简.
     *                         <p>
     *                         The key value pairs of units. If you need to specify the unit of a numerical value, you can specify it here. If you don't need it, you can directly set it to null. Please note that if you don't set it to null, this operation will not simplify the numerical value in any way.
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    protected static BaseValue parse(double valueNumber, KeyValue<Integer, String> baseNameKeyValue) {
        return new BaseValue(valueNumber, WeightValue.class, baseNameKeyValue);
    }
}
