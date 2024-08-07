package io.github.beardedManZhao.algorithmStar.operands;

/**
 * 被计算的参数对象 AlgorithmMagic 中的操作数接口，所有的操作数均来于此类，具体的介绍请参阅API说明
 * <p>
 * The operand interface in the calculated parameter object AlgorithmMagic. All operands come from this class. For details, please refer to the API description
 *
 * @param <ImplementationType> 操作数的实现类型，也就是运算时需要的类型，只有相同数据类型的两个操作数才可以进行运算
 *                             <p>
 *                             There is no description for the super interface, please refer to the subclass documentation
 * @author 赵凌宇
 */
public interface Operands<ImplementationType> extends Cloneable {

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
    ImplementationType add(ImplementationType value);

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    ImplementationType diff(ImplementationType value);

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
    ImplementationType add(Number value);

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    ImplementationType diff(Number value);

    /**
     * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
     * <p>
     * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
     *
     * @return 当前类对应的子类实现数据类型的对象。
     * <p>
     * The subclass corresponding to the current class implements objects of data type.
     */
    ImplementationType expand();
}
