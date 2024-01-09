# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:
* Framework version: 1.27 - 1.28
* New component for calculating fraction operands, supporting the creation and calculation of fraction operands

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.FractionFactory;
import zhao.algorithmMagic.operands.fraction.Fraction;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取分数操作数工厂
        final FractionFactory fractionFactory = AlgorithmStar.fractionFactory();
        // 准备三个操作数
        final Fraction parse1 = fractionFactory.parse(1, 2);
        final Fraction parse2 = fractionFactory.parse(2);
        final Fraction parse3 = fractionFactory.parse("1 / 2");

        System.out.println("打印三个分数");
        System.out.println(parse1);
        System.out.println(parse2);
        System.out.println(parse3);

        System.out.println("全部通分 分别将分母变为 10 5 1 在这里我们将第一个分数保存一下 稍后用于约分");
        final Fraction cf1 = parse1.cf(10);
        System.out.println(cf1);
        System.out.println(parse2.cf(5));
        System.out.println(parse3.cf(1));

        System.out.println("将被通分的 cf1 进行约分 不出意外的话 这里打印的值为 1 / 2");
        System.out.println(cf1.simplify());

        System.out.println("在这里将 parse1 以及 parse2 进行加减乘除计算");
        System.out.println(parse1.add(parse2));
        System.out.println(parse1.diff(parse2));
        System.out.println(parse1.multiply(parse2));
        System.out.println(parse1.divide(parse2));

        System.out.println("在这里将 parse1 做为Java中的数值类型进行使用");
        System.out.println(parse1.doubleValue());
    }
}
```

* To obtain new help information, you can directly obtain the help document through the following methods, which
  includes more comprehensive API instructions

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.HelpFactory;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取帮助信息工厂类
        final HelpFactory helpFactory = AlgorithmStar.helpFactory();
        // 下载帮助文档 到 C:\Users\zhao\Desktop\fsdownload 目录中
        final String path = helpFactory.saveHelpFile(
                HelpFactory.ALL,
                "C:\\Users\\zhao\\Desktop\\fsdownload"
        );
        System.out.println("文件已保存到：" + path);
    }
}
```

### Version update date : 2024-01-09