package zhao.algorithmMagic.operands.unit;

import java.lang.annotation.*;


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
