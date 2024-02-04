package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

/**
 * 数据进制单位，可以实现数据单位相关的计算
 * <p>
 * Data unit, which can achieve calculations related to data units
 *
 * @author zhao
 */
@BaseUnit(value = {
        "YB", "ZB", "EB", "TB", "GB", "MB", "KB", "B"
}, baseValue = 1024)
public class DataValue extends BaseValue {


    public DataValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue, BaseValueFactory baseValueFactoryClass) {
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
        return new BaseValue(valueNumber, DataValue.class, baseNameKeyValue, baseValueFactoryClass);
    }
}
