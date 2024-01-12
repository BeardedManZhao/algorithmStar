# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.27 - 1.28
* 新增分数操作数计算组件，支持对于分数操作数的创建以及计算操作

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

* 新增帮助信息的获取，您可以直接通过下面的方式获取到帮助文档，其中有更全的API说明

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