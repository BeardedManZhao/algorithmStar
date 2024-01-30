package zhao.algorithmMagic.operands.unit;

import java.lang.annotation.*;

/**
 * 进制数值单位注解，您可以在这里设置一个进制数值单位的进制数值，以及每个子单位的名字，请确保每个子单位之间的进制数值是个等比数列。
 *
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
     * The decimal value between each adjacent subunit.
     */
    double baseValue() default 10;
}
