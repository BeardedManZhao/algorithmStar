package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.util.HashMap;

/**
 * 单位对象的接口，针对某个数值的度量单位的所有父类
 * <p>
 * The interface of a unit object, targeting all parent classes of a measurement unit for a certain numerical value
 */
public interface BaseUnitObj {

    /**
     * 单位对象缓冲池，实现单例设计需要
     */
    HashMap<String[], TempBaseData> TEMP_BASE_DATA_HASH_MAP = new HashMap<>();

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
        final TempBaseData tempBaseData = TEMP_BASE_DATA_HASH_MAP.get(value);
        if (tempBaseData != null) {
            return tempBaseData;
        }
        final double[] doubles = new double[value.length];
        double number = 1, base = baseUnit.baseValue();
        HashMap<String, Integer> hashMap = new HashMap<>();
        // 构建位权
        int length = value.length - 1;
        hashMap.put(value[length], length);
        doubles[length--] = 0;

        for (; length >= 0; length--) {
            doubles[length] = number *= base;
            hashMap.put(value[length], length);
        }
        return new TempBaseData(base, doubles, value, hashMap);
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
    BaseValue switchBaseName(BaseValue baseValue, KeyValue<Integer, String> targetUnit);

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
    BaseValue switchBaseName(BaseValue baseValue, String targetUnit);

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
    int getIndexByBaseName(String baseName);

    /**
     * @return 当前单位对象中的每个子单位之间的进制差倍数
     * <p>
     * The decimal difference multiple between each subunit in the current unit object
     * 例如：m/s = 1000  进制差就是 1000
     */
    double getBaseValue();

    /**
     * @return 当前单位对象中的每个子单位的名称，在一个单位组中会有很多的子单位，例如时间单位中的 小时和分钟，这里返回的是一个 字符串数组，位权按照从大到小的顺序迭代。
     * <p>
     * The name of each subunit in the current unit object. In a unit group, there will be many subunits, such as hours and minutes in time units. Here, a string array is returned, and bit weights are iterated in ascending order.
     * 例如：["s","m"] 中 s 的等级比 m 高
     */
    String[] getBaseName();

    /**
     * @param value 需要被计算的输入数值。
     *              <p>
     *              The input values that need to be calculated.
     * @return 根据输入的数值返回能够容纳此数值的单位名称
     * <p>
     * Return the unit name that can contain the input value
     */
    KeyValue<Integer, String> getBaseNameByValue(double value);

    /**
     * @return 当前单位对象中的每个子单位的位权，位权按照从大到小的顺序迭代。
     * <p>
     * The bit weight of each subunit in the current unit object. Bit weight is iterated in ascending order.
     * 例如：["s","m"] 中 s 的位权为 1000，m 的位权为 1
     */
    double[] getBaseWeight();

    /**
     * 位权单位元数据存储类
     */
    final class TempBaseData {
        private final HashMap<String, Integer> hashMap;
        private final double baseValue;
        private final double[] baseWeights;
        private final String[] baseNames;

        /**
         * 构造函数
         *
         * @param baseValue   进制数值
         * @param baseWeights 位权数组
         * @param baseNames   子单位名称
         * @param hashMap     子单位哈希索引
         */
        public TempBaseData(double baseValue, double[] baseWeights, String[] baseNames, HashMap<String, Integer> hashMap) {
            this.baseValue = baseValue;
            this.baseWeights = baseWeights;
            this.baseNames = baseNames;
            this.hashMap = hashMap;
        }

        public HashMap<String, Integer> getHashMap() {
            return hashMap;
        }

        public double getBaseValue() {
            return baseValue;
        }

        public double[] getBaseWeights() {
            return baseWeights;
        }

        public String[] getBaseNames() {
            return baseNames;
        }
    }

}
