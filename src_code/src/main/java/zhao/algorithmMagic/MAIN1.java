package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.unit.BaseValue;

public class MAIN1 {

    public static void main(String[] args) {
        // 在这里我们获取到的就是单位数值的工厂类 在这里的函数参数是工厂要构造的单位数值的类型
        // 请确保您在这里提供的单位数值类具有 @BaseUnit 注解和 parse 函数
        final BaseValueFactory baseValueFactory = AlgorithmStar.baseValueFactory(BaseValue.class);
        // 使用工厂类 将 1024 转换成为一个单位数值
        final BaseValue parse1 = baseValueFactory.parse(2);
        // 使用工厂类 将 1024000 转换成为一个单位数值
        final BaseValue parse2 = baseValueFactory.parse(1024);
        // 计算加减乘除结果
        System.out.println(parse2.add(parse1));
        System.out.println(parse2.diff(parse1));
        System.out.println(parse2.multiply(parse1));
        System.out.println(parse2.divide(parse1));
        System.out.println(parse2.divide(parse1.multiply(parse1)));
    }
}