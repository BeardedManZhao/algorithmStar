package io.github.beardedManZhao.algorithmStar.operands.unit;

import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 匹配单位类，此类能够根据一个类中的注解自动生成合适的类对象。
 * <p>
 * Matching unit classes, which can automatically generate appropriate class objects based on annotations in a class.
 *
 * @author zhao
 */
public class MatchBaseUnit implements BaseUnitObj {

    protected final static HashMap<Class<?>, BaseUnitObj> CLASS_BASE_UNIT_OBJ_HASH_MAP = new HashMap<>();

    protected final BaseUnitObj.TempBaseData tempBaseData;

    protected MatchBaseUnit(TempBaseData tempBaseData) {
        this.tempBaseData = tempBaseData;
    }

    /**
     * 根据类获取单位对象
     * <p>
     * Obtain unit object based on class
     *
     * @param c 支持单位获取的类，需要确保此类被 BaseUnit 注解
     *          <p>
     *          Support units to obtain classes, ensuring that these classes are annotated by BaseUnit
     * @return 此类对应的单位对象
     * <p>
     * Corresponding unit objects of this type
     */
    public static BaseUnitObj parse(Class<? extends BaseValue> c) {
        final BaseUnitObj baseUnitObj = CLASS_BASE_UNIT_OBJ_HASH_MAP.get(c);
        if (baseUnitObj != null) {
            return baseUnitObj;
        }
        final BaseUnit annotation = c.getAnnotation(BaseUnit.class);
        if (annotation != null) {
            final double[] doubles = annotation.baseValue();
            if (doubles.length == 0) {
                throw new UnsupportedOperationException("You must set the baseValue attribute in @BaseUnit, which can be 1 element or >=2 elements, but should not be 0 elements!!!");
            }
            final boolean b = doubles.length != 1;
            if (b) {
                doubles[doubles.length - 1] = 1;
            }
            final MatchBaseUnit matchBaseUnit = b ? new MatchBaseGroupUnit(MatchBaseGroupUnit.parse(annotation), doubles) : new MatchBaseUnit(BaseUnitObj.parse(annotation));
            CLASS_BASE_UNIT_OBJ_HASH_MAP.put(c, matchBaseUnit);
            return matchBaseUnit;
        }
        throw new UnsupportedOperationException("The BaseUnit annotation is not used in class " + c.getTypeName() + ", so automatic unit class retrieval is not possible");
    }

    /**
     * @return 当前单位对象中的每个子单位之间的进制差倍数
     * <p>
     * The decimal difference multiple between each subunit in the current unit object
     * 例如：m/s = 1000  进制差就是 1000
     */
    @Override
    public double getBaseValue() {
        return tempBaseData.getBaseValue();
    }

    /**
     * @return 当前单位对象中的每个子单位的名称，在一个单位组中会有很多的子单位，例如时间单位中的 小时和分钟，这里返回的是一个 字符串数组，位权按照从大到小的顺序迭代。
     * <p>
     * The name of each subunit in the current unit object. In a unit group, there will be many subunits, such as hours and minutes in time units. Here, a string array is returned, and bit weights are iterated in ascending order.
     * 例如：["s","m"] 中 s 的等级比 m 高
     */
    @Override
    public String[] getBaseName() {
        return tempBaseData.getBaseNames().clone();
    }

    /**
     * @param value 需要被计算的输入数值。
     *              <p>
     *              The input values that need to be calculated.
     * @return 根据输入的数值返回能够容纳此数值的单位名称 key是名字 value 是位权的索引位
     * <p>
     * Return the unit name that can contain the input value
     */
    @Override
    public KeyValue<Integer, String> getBaseNameByValue(double value) {
        int index = 0;
        for (double v : this.tempBaseData.getBaseWeights()) {
            if (value >= v) {
                return new KeyValue<>(index, this.getBaseName()[index]);
            }
            ++index;
        }
        return new KeyValue<>(--index, tempBaseData.getBaseNames()[index]);
    }

    /**
     * @return 当前单位对象中的每个子单位的位权，位权按照从大到小的顺序迭代。
     * <p>
     * The bit weight of each subunit in the current unit object. Bit weight is iterated in ascending order.
     * 例如：["s","m"] 中 s 的位权为 1000，m 的位权为 1
     */
    @Override
    public double[] getBaseWeight() {
        return this.tempBaseData.getBaseWeights().clone();
    }

    /**
     * @return 当前单位对象中的每个子单位的进制差，位权按照从大到小的顺序迭代。
     * <p>
     * The bit weight of each subunit in the current unit object. Bit weight is iterated in ascending order.
     * 例如：["h","m","s","ms"] 中 进制差为 [60, 60, 1000, 0] 代表的就是下一个单位举例当前单位的倍数
     */
    @Override
    public double[] getBaseDiff() {
        final double[] doubles = new double[this.tempBaseData.getBaseNames().length];
        Arrays.fill(doubles, this.getBaseValue());
        doubles[doubles.length - 1] = 1;
        return doubles;
    }

    /**
     * 如果需要设置为 true；Set to true if necessary
     *
     * @return 在数学中，乘法运算需要注意两个方面：数字和单位。
     * <p>
     * 当数字相乘时，可以直接进行乘法运算。例如，2升乘以2升，数字部分是2乘以2，结果是4。
     * <p>
     * 当单位相乘时，需要先统一单位，然后再进行乘法运算。例如，2米乘以2米，单位是米，可以直接相乘得到4平方米。但如果单位不同，比如2米乘以2厘米，需要先统一单位，把2厘米转换为米（即0.01米），然后再相乘得到0.04平方米。
     * <p>
     * 因此，在进行乘法运算时，需要先判断数字和单位是否一致，如果单位不一致需要先统一单位再进行计算。
     * <p>
     * In mathematics, multiplication operations require attention to two aspects: numbers and units.
     * <p>
     * When multiplying numbers, multiplication can be performed directly. For example, multiplying 2 liters by 2 liters, the numerical part is 2 times 2, and the result is 4.
     * <p>
     * When multiplying units, it is necessary to first unify the units before performing multiplication operations. For example, multiplying 2 meters by 2 meters in meters can directly yield 4 square meters. But if the units are different, such as multiplying 2 meters by 2 centimeters, it is necessary to first unify the units, convert 2 centimeters to meters (i.e. 0.01 meters), and then multiply them to obtain 0.04 square meters.
     * <p>
     * Therefore, when performing multiplication operations, it is necessary to first check whether the numbers and units are consistent. If the units are not consistent, it is necessary to first unify the units before calculating.
     */
    @Override
    public boolean isNeedUnifiedUnit() {
        return this.tempBaseData.isNeedUnifiedUnit();
    }

    /**
     * 将一个具有指定单位的数值转换为另一个具有指定单位的数值
     * <p>
     * Convert a numerical value with a specified unit to another numerical value with a specified unit
     *
     * @param baseValue  需要被转换的数值对象
     *                   <p>
     *                   Numerical objects that need to be converted
     * @param targetUnit 期望切换到的目标单位
     *
     *                   <p>
     *                   Expected target unit to switch to
     * @return 计算出能够容纳此数值的单位名称
     * <p>
     * Calculate the unit name that can accommodate this value
     */
    @Override
    public BaseValue switchBaseName(BaseValue baseValue, KeyValue<Integer, String> targetUnit) {
        double value = baseValue.doubleValue();
        final KeyValue<Integer, String> nowBase = baseValue.getNowBase();
        // 首先获取到当前的单位对应的索引名称
        final HashMap<String, Integer> hashMap = this.tempBaseData.getHashMap();
        final String value1 = targetUnit.getValue();
        if (!hashMap.containsKey(value1)) {
            throw new UnsupportedOperationException("The current unit object did not find any sub units [" + value1 + "]. The units you can use are:" + hashMap.keySet());
        }
        double target = this.getBaseValue();
        // 判断升降级位权
        final int i1 = targetUnit.getKey() - nowBase.getKey();
        final int i2 = ASMath.absoluteValue(i1);

        if (i1 > 0) {
            // 目标的索引大 目标的位权 低 开始降级位权
            value *= Math.pow(target, i2);
        } else {
            // 开始升级位权
            value /= Math.pow(target, i2);
        }

        return new BaseValue(value, baseValue.getBaseValueFactoryClass(), baseValue.baseUnitObj, targetUnit);
    }

    /**
     * 将一个具有指定单位的数值转换为另一个具有指定单位的数值
     * <p>
     * Convert a numerical value with a specified unit to another numerical value with a specified unit
     *
     * @param baseValue  需要被转换的数值对象
     *                   <p>
     *                   Numerical objects that need to be converted
     * @param targetUnit 期望切换到的目标单位
     *
     *                   <p>
     *                   Expected target unit to switch to
     * @return 计算出能够容纳此数值的单位名称
     * <p>
     * Calculate the unit name that can accommodate this value
     */
    @Override
    public BaseValue switchBaseName(BaseValue baseValue, String targetUnit) {
        return switchBaseName(baseValue, new KeyValue<>(this.tempBaseData.getHashMap().get(targetUnit), targetUnit));
    }

    /**
     * 根据单位名称获取位权值对应索引位
     * <p>
     * Obtain bit weights corresponding to index bits based on unit name
     *
     * @param baseName 单位名称
     *                 <p>
     *                 Unit Name
     * @return 当前单位对象中的每个子单位之间的位权值对应索引位
     * <p>
     * The bit weight between each subunit in the current unit object corresponds to the index bit
     */
    @Override
    public int getIndexByBaseName(String baseName) {
        final Integer integer = this.tempBaseData.getHashMap().get(baseName);
        if (integer == null) {
            throw new UnsupportedOperationException("The current unit object did not find any sub units [" + baseName + "]. The units you can use are:" + this.tempBaseData.getHashMap().keySet());
        }
        return integer;
    }
}
