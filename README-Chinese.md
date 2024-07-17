# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/README.md)
- 知识库
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

AS机器学习库提供了一个针对机器学习各种算法的Java
API，其具有机器视觉与自然语言处理等复杂计算逻辑的封装，通过库可以快速使用各种算法，实现各种效果。AS库中的诸多计算操作是采用的原生实现，能够在没有Java标准库意外依赖就可以实现库函数的运行。

AS库目录有多个版本，如果希望查询不同版本的更新日志以及差异，您可以在这里进行[查阅](https://github.com/BeardedManZhao/algorithmStar/tree/master/src_code/update)。

在本仓库中提供了一个[测试数据集](https://github.com/BeardedManZhao/algorithmStar/blob/master/sourceMaterial.md)，在数据集中包含各种图像等数据文件，您可以通过URL将这些测试数据拉取到本地程序中进行计算。

## 通知

> ⚠️【重要】 1.32 版本和 1.40 版本的内容几乎一致，主要的区别就是包模块的变更， 请注意，我们将在 1.40 版本以及之后的所有版本中
> 重构包名为 `io.github.beardedManZhao.algorithmStar` 这是为了避免在 Java 的诸多依赖中，包名出现冲突的情况~
>
> 为了避免小伙伴们担心由于包更新导致的兼容性问题，因此我们提供了 1.32
> 版本，您可以继续使用旧包名，但是我们强烈建议您使用新版本，因为新版本的包名已经更新为 `io.github.beardedManZhao.algorithmStar`
> ，若您对于修改包名称和更新有什么问题或建议，请及时联系我们！！

## Maven 依赖

您可以通过maven将算术之星（AS-MB）集成到您的项目中，maven的配置如下所示。您可以将其添加到maven项目中，也可以从Releases下载并手动将其集成到项目中。

```xml
<!-- algorithmStar 机器学习与数据计算库程序 maven 坐标 -->
<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>algorithmStar</artifactId>
        <version>1.41</version>
    </dependency>
</dependencies>
```

### AS库的所需依赖

在1.17版本之后，AS库的所有依赖被剥离，更好避免依赖的捆绑问题，减少项目发生冲突的可能性，同时也可以按照开发者的需求使用更加适合的依赖配置项，您可以在这里查看到AS库所依赖的第三方库依赖。

#### 必选依赖项

AS库在进行诸多计算函数的时候会产生一些日志数据，因此AS库的使用需要导入日志依赖项，这个依赖项是必不可少的，请按照如下的方式导入依赖。

```xml

<dependencies>
    <!-- 使用 log4j2 的适配器进行绑定 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j-impl</artifactId>
        <version>2.20.0</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <!-- log4j2 日志门面 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.20.0</version>
        <!--<scope>provided</scope>-->
    </dependency>
    <!-- log4j2 日志实面 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
        <!--<scope>provided</scope>-->
    </dependency>
</dependencies>
```

#### 可选依赖项

AS库在针对数据库，Spark等各种平台对接的时候，需要使用到第三方依赖程序包，这些包是可选的，如果您不需要使用这些功能，您可以不去导入依赖，如果您需要，可以参考下面的配置。

```xml

<dependencies>
    <!-- MySQL数据库连接驱动 如果您需要连接的关系型数据库是其它类型，这里也可以随之修改 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.30</version>
    </dependency>
    <!-- spark 三大模块的依赖程序开发包，如果您需要使用这里也可以选择导入，如果不需要则不导入 -->
    <dependency>
        <groupId>org.apache.spark</groupId>
        <artifactId>spark-core_2.12</artifactId>
        <version>3.4.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.spark</groupId>
        <artifactId>spark-sql_2.12</artifactId>
        <version>3.4.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.spark</groupId>
        <artifactId>spark-mllib_2.12</artifactId>
        <version>3.4.0</version>
    </dependency>

    <!-- 摄像头依赖库，如果您有需要通过摄像头获取数据对象的需求，可以引入本库 -->
    <dependency>
        <groupId>com.github.sarxos</groupId>
        <artifactId>webcam-capture</artifactId>
        <version>0.3.12</version>
    </dependency>

    <!-- HDFS 输入输出设备依赖库，如果您有需要通过HDFS分布式存储平台进行数据读写的需求，可以引入本库 -->
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-client</artifactId>
        <version>3.3.1</version>
    </dependency>

</dependencies>
```

## API使用示例

### 下载所有帮助文档

您可以在加载好 AS 库之后，使用下面的代码将所有帮助文档下载到本地，其中有一些示例代码，引导您进行使用，您可以根据自己的需求进行修改。

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.core.AlgorithmStar;
import io.github.beardedManZhao.algorithmStar.core.HelpFactory;

public class MAIN1 {
  public static void main(String[] args) {
    // 获取帮助信息工厂类
    final HelpFactory helpFactory = AlgorithmStar.helpFactory();
    // 下载帮助文档 到 C:\Users\zhao\Desktop\fsdownload 目录中
    helpFactory.saveHelpFile(HelpFactory.ALL, "C:\\Users\\zhao\\Desktop\\fsdownload");
  }
}
```

### 特征计算算法组件

特征计算组件常用于特征工程，是机器学习任务中计算时的主力军，度量算法，分类算法等皆可以使用这种组件计算方式。在算法之星中的特征工程计算，就是基于组件的计算，您可以直接获取到组件对象，并调用组件函数，也可以使用算术之星的门户类进行特征工程。

接下来就是展示的使用算法之星门户类进行特征工程计算的简单示例.

```java
package io.github.beardedManZhao.algorithmStar;

import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.EuclideanMetric;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.ManhattanDistance;
import io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.WordFrequency;
import io.github.beardedManZhao.algorithmStar.core.AlgorithmStar;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;

public final class MAIN1 {
    public static void main(String[] args) {
        // 准备两个文本组成的数组，其中是两个需要被处理成为特征向量的字符串语句。
        String[] data = {
                "Good evening, dear, don't forget the agreement between us. It's 9:00 tomorrow morning.",
                "Good morning, dear, don't forget the agreement between us, at 9:00 in the morning."
        };
        AlgorithmStar<Object, ColumnIntegerMatrix> algorithmStar = AlgorithmStar.getInstance();
        // 开始进行特征提取，生成词频向量 提取成功之后会返回一个矩阵
        ColumnIntegerMatrix word = algorithmStar.extract(WordFrequency.getInstance("word"), data);
        // 打印生成的向量矩阵
        System.out.println(word);
        // 开始将矩阵中的两句话对应的向量获取到
        int[] arrayByColIndex1 = word.getArrayByColIndex(0);
        int[] arrayByRowIndex2 = word.getArrayByColIndex(1);
        // 开始计算两个向量之间的德式距离
        double res = algorithmStar.getTrueDistance(EuclideanMetric.getInstance("e"), arrayByColIndex1, arrayByRowIndex2);
        System.out.println(res);
        // 开始计算两个向量之间的曼哈顿距离
        double res1 = algorithmStar.getTrueDistance(ManhattanDistance.getInstance("man"), arrayByColIndex1, arrayByRowIndex2);
        System.out.println(res1);
        AlgorithmStar.clear();
    }
}
```

- 运行结果

```
[INFO][OperationAlgorithmManager][23-01-18:01]] : register OperationAlgorithm:word
------------IntegerMatrixStart-----------
Good evening, dear, don't forget the agreement between us. It's 9:00 tomorrow morning.	Good morning, dear, don't forget the agreement between us, at 9:00 in the morning.	rowColName
[1, 1]	00
[1, 1]	agreement
[1, 1]	don't
[0, 1]	in
[1, 0]	tomorrow
[1, 2]	morning
[1, 2]	the
[1, 1]	forget
[0, 1]	at
[1, 0]	It's
[1, 1]	9
[1, 1]	Good
[1, 0]	evening
[1, 1]	dear
[1, 1]	between
[1, 1]	us
------------IntegerMatrixEnd------------

[INFO][OperationAlgorithmManager][23-01-18:01]] : register OperationAlgorithm:e
2.6457513110645907
[INFO][OperationAlgorithmManager][23-01-18:01]] : register OperationAlgorithm:man
7.0

进程已结束,退出代码0
```

### 路线生成集成器

这里是"ZhaoCoordinateNet2D"的使用示例,展示的是人与人之间的关系分析与预测,在"ZhaoCoordinateNet"算法中, 人的关系网络被分析了出来, 同时还可以进行该网络分析绘图的操作。

```java

import io.github.beardedManZhao.algorithmStar.integrator.Route2DDrawingIntegrator;
import io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
  public static void main(String[] args) {
    // 构建人员坐标(二维)
    DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
    DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
    DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);
    DoubleCoordinateTwo E = new DoubleCoordinateTwo(6, 1);
    DoubleCoordinateTwo Z = new DoubleCoordinateTwo(1, 21);

    // 获取关系网络,该算法是我实现出来,用于推断人员关系网的,这里的名称您可以自定义,需要注意的是下面集成器的实例化需要您将该名称传进去
    ZhaoCoordinateNet2D zhaoCoordinateNet = ZhaoCoordinateNet2D.getInstance("Z");
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> B", A, B)); // Representing A takes the initiative to know B
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> C", A, C));
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("E -> Z", E, Z));
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> Z", A, Z));
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("B -> Z", B, Z));

    // 使用2维路线绘制集成器,输出上面所有人员之间的关系网络图片
    Route2DDrawingIntegrator a = new Route2DDrawingIntegrator("A", "Z");
    // 设置图片输出路径
    a.setImageOutPath("D:\\out\\image.jpg")
            // 设置图片宽度
            .setImageWidth(1000)
            // 设置图片高度
            .setImageHeight(1000)
            // 设置离散阈值,用来放大微小的变化
            .setDiscreteThreshold(4)
            // 运行集成器!
            .run();

    // 清理关系网络中的数据
    zhaoCoordinateNet.clear();
  }
}
```

- 运行之后产生的关系网络图片

  ![image](https://user-images.githubusercontent.com/113756063/196412140-8b81979d-ecc1-4774-9cbe-df8a89c19c1c.png)

### AsLib

目录中是 Algorithm Star 机器学习库在运行时所依赖的DLL动态库文件以及库文件对应的源码，在机器学习库中为了能够更好的兼容性能，因此引入了C编译好的DLL，用户可以在AS库运行之前将DLL载入到机器学习库中。

### KnowledgeDocument

知识库文件归档用于AS-MB系列知识文档的存储库，您可以通过主页上的文档直接访问它，这里不需要直接进去目录访问呢，因为目录内部比较乱。

### src_code

AS-MB的源码存放目录，在这里可以查看AS-MB的相关源码。当然这里是最新的源代码，你可以用它来编译，这样你就可以获得最新的版本。注意：最新版本往往不稳定，建议您使用已经发布很久的版本！

### README-Chinese.md

AS-MB 主页文档的中文版。您可以在主页默认页面上切换语言以访问此文件。

### README.md

AS-MB 主页文档的默认版本。您可以在主页上直接访问此文件！

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/README.md)

<hr>

#### date:2022-10-10
