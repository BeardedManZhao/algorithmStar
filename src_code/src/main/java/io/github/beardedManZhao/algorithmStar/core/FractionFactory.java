package io.github.beardedManZhao.algorithmStar.core;

import io.github.beardedManZhao.algorithmStar.operands.fraction.Fraction;

/**
 * 分数操作数构建工厂 在这里进行分数操作数的创建与计算操作
 *
 * @author zhao
 */
public class FractionFactory {

    /**
     * 解析出来一个分数对象 您可以不传递分母，在这里则是会将分母置为 1
     * <p>
     * You can parse out a fraction object without passing the denominator, where the denominator will be set to 1
     *
     * @param molecule    分子的数值
     *                    <p>
     *                    Numeric values of molecules
     * @param denominator 分母的数值
     *                    <p>
     *                    Numeric values of denominators
     * @return 解析出来的分数对象
     * <p>
     * Parse out a score object
     */
    public Fraction parse(double molecule, double denominator) {
        return Fraction.parse(molecule, denominator);
    }

    /**
     * 解析出来一个分数对象
     * <p>
     * Parse out a score object
     *
     * @param molecule 分数的字符串 要求其中具有 ‘/’
     *                 <p>
     *                 The fraction string must contain ‘/’
     * @return 解析出来的分数对象
     * <p>
     * Parse out a score object
     */
    public Fraction parse(double molecule) {
        return Fraction.parse(molecule);
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
    public Fraction parse(String fraction) {
        return Fraction.parse(fraction);
    }
}
