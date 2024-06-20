# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.30 - 1.31
* 针对单位数值中的减法进行修复，被减数与减数之间的位置进行的矫正
* 针对单位数值中的乘除算法进行升级，您在自定义单位数值的时候可以通过在 `@BaseUnit` 中指定了 `needUnifiedUnit`
  属性，来决定计算乘除的时候是否需要进行单位统一，这有利于区分数值计算和物理计算，物理计算需要保持单位一致
* 针对内置的单位数值进行矫正和升级，对于物理单位和数学单位能够实现各自的计算
* 针对内置单位数值 `DateValue` 进行升级，其可以接收更多的时间单位

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
    // 计算 1.024 秒 / 2000 毫秒 如果 isNeedUnifiedUnit = true 代表的这里会先将 单位统一然后再计算
    // TODO 针对乘除法来说 一般可能不需要带着单位计算哦！
    System.out.println(parse2.divide(parse1));
    // 计算 1天 * 2
    System.out.println(parse3.multiply(2));

    // 查看此单位数值是否需要进行单位统一再计算
    System.out.println(parse2.isNeedUnifiedUnit());
  }
}
```

* 针对单位数值注解 `@BaseUnit` 进行升级，其可以实现自定义单位的进制，也可以像原来一样指定一个通用进制，通用进制会形成一种等比效应。

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

### Version update date : 2024-02-09