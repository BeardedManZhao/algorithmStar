package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.utils.dataContainer.KeyValue;

/**
 * 体积单位数值类，其中保存了体积相关的单位以及数值管理机制
 *
 * Volume unit numerical class, which stores volume related units and numerical management mechanisms
 * @author zhao
 */
@BaseUnit(value = {
        "m³（立方米）", "dm³（立方分米 or 升）", "cm³（立方厘米 or 毫升）", "mm³（立方毫米）"
}, baseValue = 1000)
public class VolumeValue extends BaseValue{

    public VolumeValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue) {
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
        return new BaseValue(valueNumber, VolumeValue.class, baseNameKeyValue);
    }
}
