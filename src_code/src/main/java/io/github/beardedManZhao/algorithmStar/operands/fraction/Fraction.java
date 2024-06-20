package io.github.beardedManZhao.algorithmStar.operands.fraction;

import io.github.beardedManZhao.algorithmStar.operands.Operands;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.ASStr;

import java.util.Arrays;

/**
 * 分数 操作数类型 在这里支持分母与分子之间的计算操作
 * <p>
 * The fraction operand type supports calculation operations between denominators and numerators here
 *
 * @author zhao
 */
public class Fraction extends Number implements Operands<Fraction> {

    /**
     * 分子分母变量
     */
    private final double molecule, denominator;

    /**
     * 构造一个分子与分母对象
     * <p>
     * Construct a fraction object
     *
     * @param molecule    分子数值
     *                    <p>
     *                    The molecule of the fraction
     * @param denominator 分母数值
     *                    <p>
     *                    The denominator of the fraction
     */
    protected Fraction(double molecule, double denominator) {
        this.molecule = molecule;
        this.denominator = denominator;
    }

    /**
     * 解析出来一个分数对象
     * <p>
     * Parse out a score object
     *
     * @param molecule    分子的数值
     *                    <p>
     *                    The molecule of the fraction
     * @param denominator 分母的数值
     *                    <p>
     *                    The denominator of the fraction
     * @return 分数对象
     * <p>
     * Parse out a score object
     */
    public static Fraction parse(double molecule, double denominator) {
        return new Fraction(molecule, denominator);
    }

    /**
     * 解析出来一个分数对象
     * <p>
     * Parse out a score object
     *
     * @param fraction 分数的字符串 要求其中具有 ‘/’
     *                 <p>
     *                 The fraction string must contain ‘/’
     * @return 解析出来的分数对象
     * <p>
     * Parse out a score object
     */
    public static Fraction parse(String fraction) {
        final String[] strings = ASStr.splitByChar(fraction, '/');
        if (strings.length != 2) {
            throw new IllegalArgumentException("The fraction string is illegal:" + Arrays.toString(strings));
        }
        return parse(Double.parseDouble(strings[0].trim()), Double.parseDouble(strings[1].trim()));
    }

    /**
     * 解析出来一个分数对象 您可以不传递分母，在这里则是会将分母置为 1
     * <p>
     * You can parse out a fraction object without passing the denominator, where the denominator will be set to 1
     *
     * @param molecule 分子的数值
     *                 <p>
     *                 Numeric values of molecules
     * @return 解析出来的分数对象
     * <p>
     * Parse out a score object
     */
    public static Fraction parse(double molecule) {
        return parse(molecule, 1);
    }

    /**
     * 针对当前的分数进行通分计算操作，计算之后返回一个新的分数对象，请注意，如果您提供的新的分母通分值不是旧分母的倍数，则分子会变成小数
     *
     * @param new_denominator 通分之后的新的分母的数值
     * @return 通分之后的结果
     */
    public Fraction cf(double new_denominator) {
        if (new_denominator == 0) {
            throw new IllegalArgumentException("The denominator cannot be zero");
        } else if (new_denominator == denominator) {
            return this;
        } else {
            // 新分子 = 旧分子 * 新分母 / 旧分母
            final double new_molecule = getMolecule() * (new_denominator / getDenominator());
            return parse(new_molecule, new_denominator);
        }
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
    public Fraction add(Fraction value) {
        // 先进行通分
        final Fraction fraction = value.cf(getDenominator());
        // 最后进行计算
        return Fraction.parse(getMolecule() + fraction.getMolecule(), getDenominator());
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
    public Fraction diff(Fraction value) {
        // 先进行通分
        final Fraction fraction = value.cf(getDenominator());
        // 最后进行计算
        return Fraction.parse(getMolecule() - fraction.getMolecule(), getDenominator());
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
    public Fraction add(Number value) {
        return add(Fraction.parse(value.doubleValue()));
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
    public Fraction diff(Number value) {
        return diff(Fraction.parse(value.doubleValue()));
    }

    /**
     * 将当前的对象乘以一个分数对象
     * <p>
     * Multiply the current object by a fraction object
     *
     * @param fraction 要乘的分数对象
     *                 <p>
     *                 The fraction object to be multiplied
     * @return 经过乘法计算之后的新的分数对象
     * <p>
     * The new fraction object after the multiplication calculation
     */
    public Fraction multiply(Fraction fraction) {
        return Fraction.parse(getMolecule() * fraction.getMolecule(), getDenominator() * fraction.getDenominator());
    }

    /**
     * 将当前对象除以一个分数对象
     * <p>
     * Divide the current object by a fraction object
     *
     * @param fraction 要除的分数对象
     *                 <p>
     *                 The fraction object to be divided
     * @return 经过除法计算之后的新的分数对象
     * <p>
     * The new fraction object after the division calculation
     */
    public Fraction divide(Fraction fraction) {
        return multiply(fraction.reversal());
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
    public Fraction expand() {
        return this;
    }

    /**
     * @return 分子的数值
     * <p>
     * The number of the molecule
     */
    public double getMolecule() {
        return molecule;
    }

    /**
     * @return 分母的数值
     * <p>
     * The number of the denominator
     */
    public double getDenominator() {
        return denominator;
    }

    /**
     * 简化分数 对于分数进行约分操作，实现简化分数的功能。
     *
     * @return 简化后的分数  Simplified fraction
     */
    public Fraction simplify() {
        // 首先计算分母与分子的倍数
        if (getDenominator() == 1) {
            return this;
        } else {
            // 然后计算分子与分母的最大公约数
            final double gcd = ASMath.gcd(getMolecule(), getDenominator());
            // 最后进行计算
            return Fraction.parse(getMolecule() / gcd, getDenominator() / gcd);
        }
    }

    /**
     * 反转分数 反转分数中的分子和分母
     *
     * @return 反转成功的分子和分母
     */
    public Fraction reversal() {
        return Fraction.parse(this.getDenominator(), this.getMolecule());
    }

    @Override
    public String toString() {
        return "(" + getMolecule() + '/' + getDenominator() + ')';
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
        return (int) doubleValue();
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
        return (long) doubleValue();
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
        return (float) doubleValue();
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
        return getMolecule() / getDenominator();
    }
}
