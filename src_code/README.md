# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.30 - 1.31
* Fix subtraction in unit values and correct the position between the subtracted and subtracted numbers
* Upgrade the multiplication and division algorithm for unit values. When customizing unit values, you can determine
  whether unit unity is required when calculating multiplication and division by specifying the 'needUnifiedUnit'
  attribute in `@BaseUnit`. This helps to distinguish between numerical and physical calculations, and physical
  calculations need to maintain unit consistency
* Correct and upgrade built-in unit values, enabling separate calculations for physical and mathematical units
* Upgrade for built-in unit value `DateValue`, which can receive more time units

```java
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
```

* Upgrade the annotation `@BaseUnit` for unit numerical values, which can implement custom unit bases or specify a
  universal base as before. Universal base will form a proportional effect.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.unit.BaseValue;
import zhao.algorithmMagic.operands.unit.DateValue;

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

        System.out.println("==============");

        final BaseValueFactory baseValueFactory1 = AlgorithmStar.baseValueFactory(DateValue.class);
        final BaseValue parse11 = baseValueFactory1.parse(100);
        final BaseValue parse12 = baseValueFactory1.parse(10000);
        final BaseValue parse23 = baseValueFactory1.parse(1000 * 60);
        System.out.println(parse11);
        System.out.println(parse12);
        System.out.println(parse23);
        System.out.println(parse12.add(parse11));
        System.out.println(parse12.diff(parse11));
        System.out.println(parse12.multiply(parse11));
        System.out.println(parse12.divide(parse11));

        System.out.println(parse23.divide(parse12));
    }
}
```

### Version update date : 2024-02-05