package zhao.algorithmMagic.operands;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.ASStr;

/**
 * Java类于 2022/10/11 18:33:09 创建
 *
 * @author 4
 */
public class Imaginary implements Operands<Imaginary> {

    private final int real;
    private final int imaginary;

    /**
     * 构建出来一个复数
     *
     * @param real      复数的实部
     * @param imaginary 复数的虚部
     */
    public Imaginary(int real, int imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static Imaginary parse(int real, int imaginary) {
        return new Imaginary(real, imaginary);
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
    public static Imaginary parse(String s) {
        String[] split = s.split("\\s*[" + ASMath.MATHEMATICAL_SYMBOLS_PLUS + "|" + ASMath.MATHEMATICAL_SYMBOLS_SUBTRACT + "]\\s*");
        String a = split[0];
        String bi = split[1];
        // 运算符前后各有一位 && 运算符后包含一个”i“
        if (split.length == 2 && ASStr.count(bi, "i") == 1) {
            int bi1 = Integer.parseInt(bi.substring(0, bi.length() - 1));
            return parse(Integer.parseInt(a), s.contains("-") ? -bi1 : bi1);
        } else {
            throw new OperatorOperationException("[" + s + "]似乎不是一个正确的复数形式哦！正确的复数形式应为：[a + bi] [a - bi]");
        }
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
     * @apiNote 两个复数的实部之和作为新复数的实部，两个复数的虚部之和作为新复数的虚部。
     * <p>
     * The sum of the real parts of two complex numbers is used as the real part of the new complex number, and the sum of the imaginary parts of the two complex numbers is used as the imaginary part of the new complex number.
     */
    @Override
    public Imaginary add(Imaginary value) {
        return new Imaginary(this.getReal() + value.getReal(), this.getImaginary() + value.getImaginary());
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * @apiNote 两个复数的实部之差作为新复数的实部，两个复数的虚部之差作为新复数的虚部。
     */
    @Override
    public Imaginary diff(Imaginary value) {
        return new Imaginary(this.getReal() - value.getReal(), this.getImaginary() - value.getImaginary());
    }

    /**
     * 在两个操作数之间做乘的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * @apiNote (a + bi)(c + di) = (ac-bd)+(bc+ad)i = ac+adi+bci+bdi²
     */
    public Imaginary multiply(Imaginary value) {
        // (ac－bd)+(bc+ad)i
        int a = this.getReal();
        int b = this.getImaginary();
        int c = value.getReal();
        int d = value.getImaginary();
        return new Imaginary((a * c) - (b * d), (b * c) + (a * d));
    }

    @Override
    public String toString() {
        return real + (imaginary >= 0 ? " + " + imaginary : " - " + -imaginary) + "i";
    }
}
