package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.FractionFactory;

import zhao.algorithmMagic.operands.fraction.Fraction;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取分数操作数工厂
        final FractionFactory fractionFactory = AlgorithmStar.fractionFactory();
        // 准备分数
        final Fraction parse1 = fractionFactory.parse("1 / 2");
        // 获取分数的实数
        System.out.println(parse1.doubleValue());
    }
}
