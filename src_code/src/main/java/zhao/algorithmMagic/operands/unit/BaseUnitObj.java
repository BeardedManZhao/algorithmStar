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
    HashMap<BaseUnit, TempBaseData> TEMP_BASE_DATA_HASH_MAP = new HashMap<>();

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
        double number = 1, base = baseUnit.baseValue()[0];
        HashMap<String, Integer> hashMap = new HashMap<>();
        // 构建位权
        int length = value.length - 1;
        hashMap.put(value[length], length);
        doubles[length--] = 0;

        for (; length >= 0; length--) {
            doubles[length] = number *= base;
            hashMap.put(value[length], length);
        }
        final TempBaseData tempBaseData1 = new TempBaseData(base, doubles, value, hashMap, baseUnit.needUnifiedUnit());
        TEMP_BASE_DATA_HASH_MAP.put(baseUnit, tempBaseData1);
        return tempBaseData1;
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
     * @return 当前单位对象中的每个子单位的进制差，位权按照从大到小的顺序迭代。
     * <p>
     * The bit weight of each subunit in the current unit object. Bit weight is iterated in ascending order.
     * 例如：["h","m","s","ms"] 中 进制差为 [60, 60, 1000, 0] 代表的就是下一个单位举例当前单位的倍数
     */
    double[] getBaseDiff();

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
     * <p>
     * In mathematics, multiplication operations require attention to two aspects: numbers and units.
     * <p>
     * When multiplying numbers, multiplication can be performed directly. For example, multiplying 2 liters by 2 liters, the numerical part is 2 times 2, and the result is 4.
     * <p>
     * When multiplying units, it is necessary to first unify the units before performing multiplication operations. For example, multiplying 2 meters by 2 meters in meters can directly yield 4 square meters. But if the units are different, such as multiplying 2 meters by 2 centimeters, it is necessary to first unify the units, convert 2 centimeters to meters (i.e. 0.01 meters), and then multiply them to obtain 0.04 square meters.
     * <p>
     * Therefore, when performing multiplication operations, it is necessary to first check whether the numbers and units are consistent. If the units are not consistent, it is necessary to first unify the units before calculating.
     */
    boolean isNeedUnifiedUnit();

    /**
     * 位权单位元数据存储类
     */
    final class TempBaseData {
        private final HashMap<String, Integer> hashMap;
        private final boolean needUnifiedUnit;
        private final double baseValue;
        private final double[] baseWeights;
        private final String[] baseNames;

        /**
         * 构造函数
         *
         * @param baseValue       进制数值
         * @param baseWeights     位权数组
         * @param baseNames       子单位名称
         * @param hashMap         子单位哈希索引
         * @param needUnifiedUnit 计算乘除的时候是否需要将单位统一
         */
        public TempBaseData(double baseValue, double[] baseWeights, String[] baseNames, HashMap<String, Integer> hashMap, boolean needUnifiedUnit) {
            this.baseValue = baseValue;
            this.baseWeights = baseWeights;
            this.baseNames = baseNames;
            this.hashMap = hashMap;
            this.needUnifiedUnit = needUnifiedUnit;
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

        public boolean isNeedUnifiedUnit() {
            return needUnifiedUnit;
        }
    }

}
