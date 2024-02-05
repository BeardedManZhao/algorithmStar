package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.unit.BaseValue;
import zhao.algorithmMagic.operands.unit.DateValue;

public class MAIN1 {

    public static void main(String[] args) {
        // 在这里我们获取到的就是单位数值的工厂类 在这里的函数参数是工厂要构造的单位数值的类型
        // 请确保您在这里提供的单位数值类具有 @BaseUnit 注解和 parse 函数
        // 内置的单位数值类都是有 @BaseUnit 注解和 parse 函数的，如果您有自定义单位数值的需求需要注意
        final BaseValueFactory baseValueFactory = AlgorithmStar.baseValueFactory(DateValue.class);
        final BaseValue parse1 = baseValueFactory.parse(2000);
        // 使用工厂类 准备一个时间对象 这里的单位是毫秒开始的 所以这个数值是 1.024 秒
        final BaseValue parse2 = baseValueFactory.parse(1024);
        // 在这里我们再构建一个 1 天
        final BaseValue parse3 = baseValueFactory.parse(24 * 60 * 60 * 1000);
        System.out.println(parse3);

        // 打印结果
        System.out.println(parse2);
        // 计算 1.024 秒 / 2
        System.out.println(parse2.divide(2));
        // 计算 1.024 秒 / 2000 毫秒
        System.out.println(parse2.divide(parse1));
        // 计算 1天 * 2
        System.out.println(parse3.multiply(2));
    }
}