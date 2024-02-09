package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.Operands;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

/**
 * 单位数据类型，此数据类型支持单位相关的转换或基本计算。
 * <p>
 * Unit data type, which supports unit related conversions or basic calculations.
 *
 * @author zhao
 */
@BaseUnit(value = {
        "亿", "千万", "百万", "十万", "万", "千", "百", "十", "个"
}, needUnifiedUnit = false)
public class BaseValue extends Number implements Operands<BaseValue> {

    private final static Class<? extends BaseValue> BaseValueClass = BaseValue.class;
    protected final double valueNumber;
    protected final BaseValueFactory baseValueFactoryClass;
    protected final BaseUnitObj baseUnitObj;
    protected double srcValue;
    protected KeyValue<Integer, String> baseNameKeyValue;

    public BaseValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue, BaseValueFactory baseValueFactoryClass) {
        this(valueNumber, baseValueFactoryClass, MatchBaseUnit.parse(c), baseNameKeyValue);
    }

    protected BaseValue(double valueNumber, BaseValueFactory baseValueFactoryClass, BaseUnitObj baseUnitObj, KeyValue<Integer, String> baseNameKeyValue) {
        this.baseValueFactoryClass = baseValueFactoryClass;
        final boolean b = baseNameKeyValue == null;
        srcValue = valueNumber;
        this.baseNameKeyValue = b ? baseUnitObj.getBaseNameByValue(valueNumber) : baseNameKeyValue;
        final double[] baseValue = baseUnitObj.getBaseDiff();
        final double[] baseWeight = baseUnitObj.getBaseWeight();
        final double baseValue1 = baseUnitObj.getBaseValue();
        if (b) {
            int index = 0;
            for (double v : baseValue) {
                if (valueNumber >= baseWeight[index++]) {
                    valueNumber /= v;
                }
                if (baseValue1 > valueNumber) {
                    break;
                }
            }
        }
        this.valueNumber = valueNumber;
        this.baseUnitObj = baseUnitObj;
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
        return new BaseValue(valueNumber, BaseValueClass, baseNameKeyValue, baseValueFactoryClass);
    }

    public BaseUnitObj getBaseUnitObj() {
        return this.baseUnitObj;
    }

    /**
     * @return 当前数值正在使用的单位级别
     * <p>
     * The unit level in which the current value is being used
     */
    public KeyValue<Integer, String> getNowBase() {
        return this.baseNameKeyValue;
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
    public BaseValue add(BaseValue value) {
        // 切换被操作数的权值 新值
        final BaseValue nowBase1 = value.switchUnits(this.getNowBase());
        // 直接使用自己和被操作数计算 使用新值的单位
        return new BaseValue(this.doubleValue() + nowBase1.doubleValue(), baseValueFactoryClass, nowBase1.baseUnitObj, nowBase1.getNowBase());
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
    public BaseValue diff(BaseValue value) {
        // 切换被操作数的权值 新值
        final BaseValue nowBase1 = value.switchUnits(this.getNowBase());
        // 直接使用自己和被操作数计算 使用新值的单位
        return new BaseValue(this.doubleValue() - nowBase1.doubleValue(), baseValueFactoryClass, nowBase1.baseUnitObj, nowBase1.getNowBase());
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
    public BaseValue add(Number value) {
        // 直接使用自己和被操作数计算 使用新值的单位
        return new BaseValue(this.doubleValue() + value.doubleValue(), baseValueFactoryClass, this.getBaseUnitObj(), this.getNowBase());
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
    public BaseValue diff(Number value) {
        // 直接使用自己和被操作数计算 使用新值的单位
        return new BaseValue(this.doubleValue() - value.doubleValue(), baseValueFactoryClass, this.getBaseUnitObj(), this.getNowBase());
    }

    /**
     * 对于两个单位数值进行求乘积计算，在这里进行的计算结果会按照被乘数的单位进行数值的化简与单位统一。
     * <p>
     * For the multiplication calculation of two unit values, the calculation results here will be simplified and unified according to the units of the multiplicand.
     *
     * @param value 被操作数的数值对象，如果此处是Java的基本数据类型，则使用操作数的单位直接计算。
     *              <p>
     *              The numerical object of the operand, if this is the basic data type of Java, is calculated directly using the unit of the operand.
     * @return 计算结果，会返回一个新的单位数值对象。
     * <p>
     * The calculation result will return a new unit value object.
     */
    public BaseValue multiply(BaseValue value) {
        if (!this.baseUnitObj.isNeedUnifiedUnit()) {
            // 切换被操作数的权值 新值
            final String[] baseName = this.baseUnitObj.getBaseName();
            final BaseValue nowBase1 = value.switchUnits(baseName[baseName.length - 1]);
            // 直接使用自己和被操作数计算 使用新值的单位
            return new BaseValue(this.doubleValue() * nowBase1.doubleValue(), baseValueFactoryClass, this.baseUnitObj, this.getNowBase());
        }
        // 直接使用自己和被操作数计算 使用新值的单位
        return this.baseValueFactoryClass.parse(this.getSrcValue() * value.getSrcValue());
    }

    /**
     * 对于两个单位数值进行求商计算，在这里进行的计算结果会按照被除数的单位进行数值的化简与单位统一。
     * <p>
     * For the calculation of the quotient of two unit values, the results obtained here will be simplified and the units will be unified according to the units of the dividend.
     *
     * @param value 被操作数的数值对象，如果此处是Java的基本数据类型，则使用操作数的单位直接计算。
     *              <p>
     *              The numerical object of the operand, if this is the basic data type of Java, is calculated directly using the unit of the operand.
     * @return 计算结果，会返回一个新的单位数值对象。
     * <p>
     * The calculation result will return a new unit value object.
     */
    public BaseValue divide(BaseValue value) {
        if (!this.baseUnitObj.isNeedUnifiedUnit()) {
            // 切换被操作数的权值 新值
            final String[] baseName = this.baseUnitObj.getBaseName();
            final BaseValue nowBase1 = value.switchUnits(baseName[baseName.length - 1]);
            // 直接使用自己和被操作数计算 使用新值的单位
            return new BaseValue(this.doubleValue() / nowBase1.doubleValue(), baseValueFactoryClass, this.baseUnitObj, this.getNowBase());
        }
        // 直接使用自己和被操作数计算 使用新值的单位
        return this.baseValueFactoryClass.parse(this.getSrcValue() / value.getSrcValue());
    }

    /**
     * @return 获取到最低位权单位对应的数值，也是原本的数值，没有经过化简的数值。
     * <p>
     * The value corresponding to the lowest weighted unit obtained is also the original value, which has not been simplified.
     */
    public double getSrcValue() {
        return srcValue;
    }

    /**
     * 对于两个单位数值进行求乘积计算，在这里进行的计算结果会按照被乘数的单位进行数值的化简与单位统一。
     * <p>
     * For the multiplication calculation of two unit values, the calculation results here will be simplified and unified according to the units of the multiplicand.
     *
     * @param value 被操作数的数值对象，如果此处是Java的基本数据类型，则使用操作数的单位直接计算。
     *              <p>
     *              The numerical object of the operand, if this is the basic data type of Java, is calculated directly using the unit of the operand.
     * @return 计算结果，会返回一个新的单位数值对象。
     * <p>
     * The calculation result will return a new unit value object.
     */
    public BaseValue multiply(Number value) {
        return new BaseValue(this.doubleValue() * value.doubleValue(), baseValueFactoryClass, this.getBaseUnitObj(), this.getNowBase());
    }

    /**
     * 对于两个单位数值进行求商计算，在这里进行的计算结果会按照被除数的单位进行数值的化简与单位统一。
     * <p>
     * For the calculation of the quotient of two unit values, the results obtained here will be simplified and the units will be unified according to the units of the dividend.
     *
     * @param value 被操作数的数值对象，如果此处是Java的基本数据类型，则使用操作数的单位直接计算。
     *              <p>
     *              The numerical object of the operand, if this is the basic data type of Java, is calculated directly using the unit of the operand.
     * @return 计算结果，会返回一个新的单位数值对象。
     * <p>
     * The calculation result will return a new unit value object.
     */
    public BaseValue divide(Number value) {
        return new BaseValue(this.doubleValue() / value.doubleValue(), baseValueFactoryClass, this.getBaseUnitObj(), this.getNowBase());
    }

    /**
     * 将当前数值进行单位切换操作
     * <p>
     * Convert the current value into units and perform a conversion operation
     *
     * @param targetUnit 需要被切换到的目标单位
     *                   <p>
     *                   The target unit that needs to be switched to
     * @return 返回新的单位数值对象
     * <p>
     * Return a new unit value object
     */
    public BaseValue switchUnits(KeyValue<Integer, String> targetUnit) {
        return baseUnitObj.switchBaseName(this, targetUnit);
    }

    /**
     * 不改变数值的方式，只改变单位度量。
     * <p>
     * Do not change the numerical value, only change the unit measurement.
     *
     * @param targetUnit 需要被切换到的目标单位
     *                   <p>
     *                   The target unit that needs to be switched to
     */
    public void switchUnitsNotChange(KeyValue<Integer, String> targetUnit) {
        this.baseNameKeyValue = targetUnit;
    }

    /**
     * 不改变数值的方式，只改变单位度量。
     * <p>
     * Do not change the numerical value, only change the unit measurement.
     *
     * @param targetUnit 需要被切换到的目标单位
     *                   <p>
     *                   The target unit that needs to be switched to
     */
    public void switchUnitsNotChange(String targetUnit) {
        this.switchUnitsNotChange(new KeyValue<>(this.getBaseUnitObj().getIndexByBaseName(targetUnit), targetUnit));
    }

    /**
     * 将当前数值进行单位且换操作
     * <p>
     * Convert the current value into units and perform a conversion operation
     *
     * @param targetUnit 需要被切换到的目标单位
     *                   <p>
     *                   The target unit that needs to be switched to
     * @return 返回新的单位数值对象
     * <p>
     * Return a new unit value object
     */
    public BaseValue switchUnits(String targetUnit) {
        return this.baseUnitObj.switchBaseName(this, targetUnit);
    }

    /**
     * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
     * <p>
     * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
     *
     * @return 当前类对应的子类实现数据类型的对象。
     * <p>
     * The subclass corresponding to the current class implements objects of data type.
     */
    @Override
    public BaseValue expand() {
        return this;
    }

    /**
     * Returns the value of the specified number as an {@code int},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        return (int) this.doubleValue();
    }

    /**
     * Returns the value of the specified number as a {@code long},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        return (long) this.doubleValue();
    }

    /**
     * Returns the value of the specified number as a {@code float},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        return (float) this.doubleValue();
    }

    /**
     * Returns the value of the specified number as a {@code double},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return this.valueNumber;
    }

    /**
     * @return 是否需要统一单位
     * <p>
     * Whether to unify units
     */
    public boolean isNeedUnifiedUnit() {
        return this.baseUnitObj.isNeedUnifiedUnit();
    }

    /**
     * @return The string representation of the object.
     */
    @Override
    public String toString() {
        return this.doubleValue() + this.getNowBase().getValue();
    }

    /**
     * @return 本单位数值对象对应的工厂类
     * <p>
     * The factory class corresponding to the numerical object of this unit
     */
    public BaseValueFactory getBaseValueFactoryClass() {
        return baseValueFactoryClass;
    }
}
