# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.29 - 1.30
* 新增单位数值类操作数，此操作数可以实现基本计算，同时还可以实现带有单位的计算效果，其还具有单位转换操作，且允许自定义单位!!!
    * 内置了 BaseValue 类，其可以实现加减乘除运算，同时还支持单位数值的转换，可以直接使用

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.unit.BaseValue;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 构建一个用来创建 BaseValue.class 的工厂类 TODO 这里的类型可以是其它的 但是要确保是 BaseValue 的子类
        final BaseValueFactory baseValueFactory = AlgorithmStar.baseValueFactory(BaseValue.class);
        // 获取到数值
        final BaseValue number_1 = baseValueFactory.parse(200);
        System.out.println("number_1 = " + number_1);
        final BaseValue number_2 = baseValueFactory.parse(1024);
        System.out.println("number_2 = " + number_2);

        // 基本的运算演示
        System.out.println("============");
        System.out.println(number_2 + " + " + number_1 + " = " + number_1.add(number_2));
        System.out.println(number_2 + " + " + 2 + number_2.getNowBase().getValue() + " = " + number_2.add(2));

        System.out.println("============");
        System.out.println(number_2 + " - " + number_1 + " = " + number_1.diff(number_2));
        System.out.println(number_2 + " - " + 2 + number_2.getNowBase().getValue() + " = " + number_2.diff(2));

        System.out.println("============");
        System.out.println(number_2 + " * " + number_1 + " = " + number_1.multiply(number_2));
        System.out.println(number_2 + " * " + 2 + number_2.getNowBase().getValue() + " = " + number_2.multiply(2));

        System.out.println("============");
        System.out.println(number_2 + " / " + number_1 + " = " + number_1.divide(number_2));
        System.out.println(number_2 + " / " + 2 + number_2.getNowBase().getValue() + " = " + number_2.divide(2));

        // 将 1.024千 转换为 10.24百 和 0.1024 万
        System.out.println("============");
        System.out.println(number_2.switchUnits("百"));
        System.out.println(number_2.switchUnits("万"));
    }
}

```

* 如果需要自定义单位，可以直接继承 BaseValue 类，然后标注 BaseUnit 注解即可，下面是一个基本的实例（下面的类也已经加入到了AS库）
    * 继承 BaseValue 并重写静态的 parse 函数
    * 标注 BaseUnit 注解 在其中设置单位 与 进制值

```java
package zhao.algorithmMagic.operands.unit;

import zhao.algorithmMagic.utils.dataContainer.KeyValue;

/**
 * 重量单位数值，在这里可以直接使用重量相关的单位！
 *
 * Weight unit value, weight related units can be directly used here!
 * @author zhao
 */
@BaseUnit(value = {
        "t（吨）", "kg（千克）", "g（克）", "mg（毫克）", "ug（微克）", "ng（纳克）", "pg（皮克）", "fg（飞克）"
}, baseValue = 1000)
public class WeightValue extends BaseValue {

    protected WeightValue(double valueNumber, Class<? extends BaseValue> c, KeyValue<Integer, String> baseNameKeyValue) {
        super(valueNumber, c, baseNameKeyValue);
    }

    /**
     * @param valueNumber 需要被解析的数值
     *                    <p>
     *                    Value that needs to be parsed
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    public static BaseValue parse(double valueNumber) {
        return parse(valueNumber, null);
    }

    /**
     * @param valueNumber      需要被解析的数值
     *                         <p>
     *                         Value that needs to be parsed
     * @param baseNameKeyValue 单位的键值对，如果您需要指定数值的单位，您可以在这里进行指定，如果您不需要可以直接设置为 null，请注意 如果您不设置为null 此操作将不会对数值进行任何化简.
     *                         <p>
     *                         The key value pairs of units. If you need to specify the unit of a numerical value, you can specify it here. If you don't need it, you can directly set it to null. Please note that if you don't set it to null, this operation will not simplify the numerical value in any way.
     * @return 解析之后的单位数值对象
     * <p>
     * Parsed Unit Value Object
     */
    protected static BaseValue parse(double valueNumber, KeyValue<Integer, String> baseNameKeyValue) {
        return new BaseValue(valueNumber, WeightValue.class, baseNameKeyValue);
    }
}

```

然后就可以进行使用啦

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.BaseValueFactory;
import zhao.algorithmMagic.operands.unit.BaseValue;
import zhao.algorithmMagic.operands.unit.WeightValue;

import java.net.MalformedURLException;

public class MAIN1 {
    public static void main(String[] args) {
        // 构建一个用来创建 WeightValue.class 的工厂类 TODO 这里的类型可以是其它的 但是要确保是 BaseValue 的子类
        final BaseValueFactory baseValueFactory = AlgorithmStar.baseValueFactory(WeightValue.class);
        // 获取到数值
        final BaseValue number_1 = baseValueFactory.parse(200);
        System.out.println("number_1 = " + number_1);
        // 将第一个的单位转换为 克
        number_1.switchUnitsNotChange("g（克）");
        System.out.println("number_1 = " + number_1);
        // 获取到第二个数值
        final BaseValue number_2 = baseValueFactory.parse(102.4);
        // 将第二个转换为 毫克
        number_2.switchUnitsNotChange("mg（毫克）");
        System.out.println("number_2 = " + number_2);
        // 进行一个计算
        System.out.println("number_1 + number_2 = " + number_1.add(number_2));
    }
}

```

* 内置的单位数值类

| 类路径                                           | 名称      | 加入版本  | 支持计算 |
|-----------------------------------------------|---------|-------|------|
| zhao.algorithmMagic.operands.unit.BaseValue   | 基础单位数值类 | v1.30 | yes  |
| zhao.algorithmMagic.operands.unit.DataValue   | 数据单位数值类 | v1.30 | yes  |
| zhao.algorithmMagic.operands.unit.VolumeValue | 容量单位数值类 | v1.30 | yes  |
| zhao.algorithmMagic.operands.unit.WeightValue | 重量单位数值类 | v1.30 | yes  |
| zhao.algorithmMagic.operands.unit.DateValue   | 时间单位数值类 | v1.30 | yes  |


### Version update date : 2024-01-30