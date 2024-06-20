package io.github.beardedManZhao.algorithmStar.core;

import io.github.beardedManZhao.algorithmStar.operands.ComplexNumber;

/**
 * 复数 创建工厂
 *
 * @author zhao
 */
public final class ComplexNumberFactory {

    /**
     * 通过公式，构建一个复数
     * <p>
     * By formula, construct a complex number
     *
     * @param complexNumberStr 复数的字符串，会被解析，您传入的字符串应为"[a + bi] 或者 [a - bi]"这个形式。
     *                         <p>
     *                         Plural strings will be parsed, and the string you pass should be in the form "[a + bi] or [a - bi]".
     * @return 复数对象
     * <p>
     * plural object
     */
    public ComplexNumber parse(String complexNumberStr) {
        return ComplexNumber.parse(complexNumberStr);
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
    public ComplexNumber parse(double real, double imaginary) {
        return ComplexNumber.parse(real, imaginary);
    }
}
