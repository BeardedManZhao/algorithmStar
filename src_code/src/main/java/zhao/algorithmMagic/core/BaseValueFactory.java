package zhao.algorithmMagic.core;

import zhao.algorithmMagic.operands.unit.BaseValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 基础数值工厂类，其中包含一个解析方法，可以用此类创建出指定类型的单位数值。
 *
 * @author zhao
 */
public class BaseValueFactory {

    final Method parseM1;

    public BaseValueFactory(Class<? extends BaseValue> baseValueClass) {
        try {
            this.parseM1 = baseValueClass.getMethod("parse", double.class);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("请在 " + baseValueClass.getTypeName() + " 中实现一个 public static BaseValue parse(double valueNumber) 函数!!!");
        }
    }

    /**
     * @param number 需要被解析的数值
     *               <p>
     *               Value that needs to be parsed
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    public BaseValue parse(double number) {
        try {
            return (BaseValue) parseM1.invoke(null, number);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
