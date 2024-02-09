package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.util.HashMap;

/**
 * 匹配单位类，此类能够根据一个类中的注解自动生成合适的类对象。
 * <p>
 * Matching unit classes, which can automatically generate appropriate class objects based on annotations in a class.
 *
 * @author zhao
 */
public class MatchBaseGroupUnit extends MatchBaseUnit {

    protected final double[] baseDiff;


    protected MatchBaseGroupUnit(TempBaseData tempBaseData, double[] baseDiff) {
        super(tempBaseData);
        final int length = super.tempBaseData.getBaseNames().length;
        if (length != baseDiff.length) {
            throw new UnsupportedOperationException("The length of the base difference array you provided is [" + baseDiff.length + "]. It is expected to be [" + length + "] because each element in the array corresponds to a base digit");
        }
        this.baseDiff = baseDiff;
    }


    /**
     * 根据单位注解创建出一个单位对象。
     * <p>
     * The decimal value between each adjacent subunit. Create a unit object based on the unit annotation.
     *
     * @param baseUnit 需要被解析的单位注解。
     *                 <p>
     *                 Unit annotations that need to be parsed.
     * @return 创建出来的单位对象。
     * <p>
     * The unit object created.
     */
    static TempBaseData parse(BaseUnit baseUnit) {
        final String[] value = baseUnit.value();
        final TempBaseData tempBaseData = TEMP_BASE_DATA_HASH_MAP.get(baseUnit);
        if (tempBaseData != null) {
            return tempBaseData;
        }
        final double[] doubles = new double[value.length];
        double number = 1;
        final double[] doubles1 = baseUnit.baseValue();
        HashMap<String, Integer> hashMap = new HashMap<>();
        // 构建位权
        int length = value.length - 1;
        hashMap.put(value[length], length);
        doubles[length--] = 0;

        for (; length >= 0; length--) {
            doubles[length] = number *= doubles1[length];
            hashMap.put(value[length], length);
        }

        final TempBaseData tempBaseData1 = new TempBaseData(doubles[doubles.length - 2], doubles, value, hashMap, baseUnit.needUnifiedUnit());
        TEMP_BASE_DATA_HASH_MAP.put(baseUnit, tempBaseData1);
        return tempBaseData1;
    }

    /**
     * @return 当前单位对象中的每个子单位的进制差，位权按照从大到小的顺序迭代。
     * <p>
     * The bit weight of each subunit in the current unit object. Bit weight is iterated in ascending order.
     * 例如：["h","m","s","ms"] 中 进制差为 [60, 60, 1000, 0] 代表的就是下一个单位举例当前单位的倍数
     */
    @Override
    public double[] getBaseDiff() {
        return this.baseDiff.clone();
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
        final HashMap<String, Integer> hashMap = super.tempBaseData.getHashMap();
        final String value1 = targetUnit.getValue();
        if (!hashMap.containsKey(value1)) {
            throw new UnsupportedOperationException("The current unit object did not find any sub units [" + value1 + "]. The units you can use are:" + hashMap.keySet());
        }
        final double[] baseDiff = this.baseDiff;
        // 判断升降级位权
        final Integer i1 = nowBase.getKey();
        final Integer i2 = targetUnit.getKey();

        if (i2 > i1) {
            // 目标的索引大 目标的位权 低 开始降级位权
            for (int i = i1; i <= i2; i++) {
                value *= baseDiff[i];
            }
        } else {
            // 开始升级位权
            for (int i = i1; i >= i2; i--) {
                value /= baseDiff[i];
            }
        }

        return new BaseValue(value, baseValue.getBaseValueFactoryClass(), baseValue.baseUnitObj, targetUnit);
    }
}
