package io.github.beardedManZhao.algorithmStar.operands;

import io.github.beardedManZhao.algorithmStar.exception.ConversionException;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;

import java.util.regex.Pattern;

/**
 * Java类于 2022/10/11 18:33:09 创建
 * <p>
 * 复数对象，用于进行共轭计算等操作
 * <p>
 * complex number object, used for operations such as conjugation calculations.
 *
 * @author zhao
 */
public class ComplexNumber extends Number implements Operands<ComplexNumber> {

    protected final static Pattern ADD_SUBTRACT_SEPARATOR = Pattern.compile("[" + ASMath.MATHEMATICAL_SYMBOLS_PLUS + ASMath.MATHEMATICAL_SYMBOLS_SUBTRACT + "]\\s*?");
    private final double real;
    private final double imaginary;
    private final String expression;

    /**
     * 通过复数的实部数值与虚部数值，构建出来一个复数对象
     * <p>
     * A complex object is constructed by the real part value and imaginary part value of the complex number
     *
     * @param real      复数的实部
     * @param imaginary 复数的虚部
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        this.expression = real + (imaginary >= 0 ? " + " + imaginary : " - " + -imaginary) + "i";
    }

    /**
     * 通过外界传递的复数的表达式构造出来一个负数对象
     * <p>
     * A negative number object is constructed from the complex number expression passed outside
     *
     * @param real       复数的实部
     * @param imaginary  复数的虚部
     * @param expression 复数的表达式
     */
    protected ComplexNumber(double real, double imaginary, String expression) {
        this.real = real;
        this.imaginary = imaginary;
        this.expression = expression;
    }

    /**
     * 使用实部数值与虚部数值进行复数对象的创建操作。
     * <p>
     * Create complex objects using real and imaginary values.
     *
     * @param real      实数部分的数值。
     *                  <p>
     *                  The numerical value of the real part.
     * @param imaginary 虚部的数值。
     *                  <p>
     *                  The numerical value of the imaginary part.
     * @return 由指定的实部数值与虚部数值解析出来的复数对象。
     * <p>
     * A complex object parsed from the specified real and imaginary values.
     */
    public static ComplexNumber parse(double real, double imaginary) {
        return new ComplexNumber(real, imaginary);
    }

    /**
     * 通过公式，构建一个复数
     * <p>
     * By formula, construct a complex number
     *
     * @param s 复数的字符串，会被解析，您传入的字符串应为"[a + bi] 或者 [a - bi]"这个形式。
     *          <p>
     *          Plural strings will be parsed, and the string you pass should be in the form "[a + bi] or [a - bi]".
     * @return 复数对象
     * <p>
     * plural object
     */
    public static ComplexNumber parse(String s) {
        String trim = s.trim();
        if (trim.charAt(trim.length() - 1) == 'i') {
            String[] split = ADD_SUBTRACT_SEPARATOR.split(trim);
            if (split.length == 2) {
                String a = split[0];
                String bi = split[1];
                int length = bi.length();
                // 运算符前后各有一位 && 运算符后包含一个”i“ && i是最后一位
                double bi1 = Double.parseDouble(bi.substring(0, length - 1).trim());
                return new ComplexNumber(Double.parseDouble(a.trim()), trim.charAt(a.length()) == '-' ? -bi1 : bi1, s);
            }
        }
        throw new OperatorOperationException("[" + s + "]似乎不是一个正确的复数形式哦！正确的复数形式应为：[a + bi] or [a - bi]");
    }

    /**
     * 从对象中提取指定部分的数值。
     * <p>
     * Extract the specified part of the numerical value from the object.
     *
     * @return 提取出对象的是实部数值。
     * <p>
     * The extracted object is a real part value.
     */
    public double getReal() {
        return real;
    }

    /**
     * 从对象中提取指定部分的数值。
     * <p>
     * Extract the specified part of the numerical value from the object.
     *
     * @return 提取出对象的是实部数值。
     * <p>
     * The extracted object is imaginary value.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * 两个复数的实部之和作为新复数的实部，两个复数的虚部之和作为新复数的虚部。
     * <p>
     * The sum of the real parts of two complex numbers is used as the real part of the new complex number, and the sum of the imaginary parts of the two complex numbers is used as the imaginary part of the new complex number.
     */
    @Override
    public ComplexNumber add(ComplexNumber value) {
        return new ComplexNumber(this.getReal() + value.getReal(), this.getImaginary() + value.getImaginary());
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * <p>
     * 两个复数的实部之差作为新复数的实部，两个复数的虚部之差作为新复数的虚部。
     */
    @Override
    public ComplexNumber diff(ComplexNumber value) {
        return new ComplexNumber(this.getReal() - value.getReal(), this.getImaginary() - value.getImaginary());
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
    public ComplexNumber add(Number value) {
        return ComplexNumber.parse(this.real + value.doubleValue(), this.imaginary);
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
    public ComplexNumber diff(Number value) {
        return ComplexNumber.parse(this.real - value.doubleValue(), this.imaginary);
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
    public ComplexNumber expand() {
        return this;
    }

    /**
     * 在两个操作数之间做乘的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 乘积的参数（被乘数） Argument of the product (multiplicand)
     * @return 两个复数的积  difference value
     * <p>
     * (a + bi)(c + di) = (ac-bd)+(bc+ad)i = ac+adi+bci+bdi²
     */
    public ComplexNumber multiply(ComplexNumber value) {
        double a = this.getReal();
        double b = this.getImaginary();
        double c = value.getReal();
        double d = value.getImaginary();
        return new ComplexNumber((a * c) - (b * d), (b * c) + (a * d));
    }

    /**
     * 在两个操作数之间做除的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被除的参数（被除数）复数  Parameter to be divided (dividend)
     * @return 结果数值  result value
     * <p>
     * (ac + bd) / (c² + d²) + (bc - ad) / (c² + d²) i
     */
    public ComplexNumber divide(ComplexNumber value) {
        double a = this.getReal();
        double b = this.getImaginary();
        double c = value.getReal();
        double d = value.getImaginary();
        double SumCD = ASMath.Power2(c) + ASMath.Power2(d);
        return new ComplexNumber(((a * c) + (b * d)) / SumCD, ((b * c) - (a * d)) / SumCD);
    }

    /**
     * @return 该复数对象的共轭复数
     * <p>
     * The conjugate complex of the complex object
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(this.real, -this.imaginary);
    }

    @Override
    public String toString() {
        return this.expression;
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
        if (this.imaginary == 0) {
            return this.real;
        } else {
            throw new ConversionException("无法将一个虚部不为0的复数转换成为实数。\nCannot convert a complex number with an imaginary part other than 0 to a real number.\nERROR => " + this);
        }
    }
}
