package zhao.algorithmMagic.operands;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASMath;

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
public class ComplexNumber implements Operands<ComplexNumber> {

    protected final static Pattern ADD_SUBTRACT_SEPARATOR = Pattern.compile("[" + ASMath.MATHEMATICAL_SYMBOLS_PLUS + ASMath.MATHEMATICAL_SYMBOLS_SUBTRACT + "]\\s*?");
    private final int real;
    private final int imaginary;
    private final String expression;

    /**
     * 通过复数的实部数值与虚部数值，构建出来一个复数对象
     * <p>
     * A complex object is constructed by the real part value and imaginary part value of the complex number
     *
     * @param real      复数的实部
     * @param imaginary 复数的虚部
     */
    public ComplexNumber(int real, int imaginary) {
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
    protected ComplexNumber(int real, int imaginary, String expression) {
        this.real = real;
        this.imaginary = imaginary;
        this.expression = expression;
    }

    public static ComplexNumber parse(int real, int imaginary) {
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
                int bi1 = Integer.parseInt(bi.substring(0, length - 1).trim());
                return new ComplexNumber(Integer.parseInt(a.trim()), trim.charAt(a.length()) == '-' ? -bi1 : bi1, s);
            }
        }
        throw new OperatorOperationException("[" + s + "]似乎不是一个正确的复数形式哦！正确的复数形式应为：[a + bi] or [a - bi]");
    }

    public int getReal() {
        return real;
    }

    public int getImaginary() {
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
        int a = this.getReal();
        int b = this.getImaginary();
        int c = value.getReal();
        int d = value.getImaginary();
        return new ComplexNumber((a * c) - (b * d), (b * c) + (a * d));
    }

    /**
     * 在两个操作数之间做除的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被除的参数（被除数）坟墓  Parameter to be divided (dividend)
     * @return 结果数值  result value
     * <p>
     * (ac + bd) / (c² + d²) + (bc - ad) / (c² + d²) i
     */
    public ComplexNumber divide(ComplexNumber value) {
        int a = this.getReal();
        int b = this.getImaginary();
        int c = value.getReal();
        int d = value.getImaginary();
        int SumCD = ASMath.Power2(c) + ASMath.Power2(d);
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
}
