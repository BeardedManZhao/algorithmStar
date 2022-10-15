# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

各种算法、向量计算、坐标计算、空间坐标等功能皆含于内，API操作简单，机器学习与数学、医学、人工智能等领域具有很高的实用性。

### Maven 依赖

您可以通过maven将算术之星（ASMB）集成到您的项目中，maven的配置如下所示。您可以将其添加到maven项目中，也可以从Releases下载并手动将其集成到项目中。

```
Waiting
```

### API使用示例

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.algorithm.LingYuZhaoCoordinateNet;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建人员坐标(二维)
        DoubleCoordinateMany A = new DoubleCoordinateMany(10, 10);
        DoubleCoordinateMany B = new DoubleCoordinateMany(-10, 4);
        DoubleCoordinateMany C = new DoubleCoordinateMany(1, 0);
        DoubleCoordinateMany E = new DoubleCoordinateMany(6, 1);
        DoubleCoordinateMany Z = new DoubleCoordinateMany(1, 21);

        // 获取关系网络,该算法是我实现出来,用于推断人员关系网的,这里的名称您可以自定义,需要注意的是下面集成器的实例化需要您将该名称传进去
        LingYuZhaoCoordinateNet lingYuZhaoCoordinateNet = LingYuZhaoCoordinateNet.getInstance("Z");
        // 将人员的关系添加到关系网络中,请注意,该算法的关系网络已经包含了您的数据,所以您在下面集成其中一定要传入相同名称,以便集成器能获取到您算法中的临时网格数据
        lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> B", A, B));
        lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> C", A, C));
        lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("E -> Z", E, Z));
        lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> Z", A, Z));
        lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("B -> Z", B, Z));

        // 使用2维路线绘制集成器,输出上面所有人员之间的关系网络图片
        Route2DDrawingIntegrator a = new Route2DDrawingIntegrator("Z", "A");
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
        lingYuZhaoCoordinateNet.clear();
    }
}

```
- 运行之后产生的关系网络图片
  ![]()

### KnowledgeDocument

知识库文件归档用于ASMB系列知识文档的存储库，您可以通过主页上的文档直接访问它，这里不需要直接进去目录访问呢，因为目录内部比较乱。

### src_code

ASMB的源码存放目录，在这里可以查看ASMBr的相关源码。当然这里是最新的源代码，你可以用它来编译，这样你就可以获得最新的版本。注意：最新版本往往不稳定，建议您使用已经发布很久的版本！

### README-Chinese.md

ASMB 主页文档的中文版。您可以在主页默认页面上切换语言以访问此文件。

### README.md

ASMB 主页文档的默认版本。您可以在主页上直接访问此文件！

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/README.md)

<hr>

#### date:2022-10-10
