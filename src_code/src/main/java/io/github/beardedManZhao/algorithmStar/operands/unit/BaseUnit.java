package io.github.beardedManZhao.algorithmStar.operands.unit;

import java.lang.annotation.*;

/**
 * 进制数值单位注解，您可以在这里设置一个进制数值单位的进制数值，以及每个子单位的名字，请确保每个子单位之间的进制数值是个等比数列。
 * <p>
 * Annotations for base numerical units. You can set a base numerical unit and the name of each subunit here. Please ensure that the base numerical values between each subunit are a proportional sequence.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BaseUnit {

    /**
     * @return 当前单位中的每个子单位的名字，其中位权需要按照从大到小排序!
     * <p>
     * The names of each subunit in the current unit need to be sorted in ascending order of their positional weight!
     */
    String[] value();


    /**
     * @return 每个相邻子单位之间的进制数值。
     * <p>
     * 如果只设置了一个元素，则这里代表的就是每个相邻的子单位之间的进制差，例如：["米", "分米", "厘米"] 中的每个单位的进制差都是 10 所以您可以在这里直接写 10;
     * <p>
     * 如果是多个元素就代表指定位置的子单位距离它下一个单位的禁止差 例如：["h","m","s","ms"] 中 进制差为 [60, 60, 1000, 0] 代表的就是下一个单位与当前单位的倍数
     * <p>
     * The decimal value between each adjacent subunit. If only one element is set, it represents the base difference between each adjacent subunit. For example, the base difference for each unit in ["meter", "decimeter", "centimeter"] is 10, so you can directly write 10 here; If there are multiple elements, it represents the forbidden difference between the subunit at the specified position and its next unit. For example, in ["h", "m", "s", "ms"], the decimal difference is [60, 60, 1000, 0], which represents the multiple of the current unit for the next unit
     */
    double[] baseValue() default 10;

    /**
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
    boolean needUnifiedUnit() default true;
}
