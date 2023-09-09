# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.24 - 1.25
* 更新版本号。
* 移除 RouteNet 接口中“不支持操作异常”针对外部项目的依赖，避免出现下面所列出的异常信息，此版本中无需获取“javax.ws.rs”的第三方依赖。

```
Exception in thread "main" java.lang.NoClassDefFoundError: javax/ws/rs/NotSupportedException
	at zhao.algorithmMagic.MAIN1.main(MAIN1.java:38)
Caused by: java.lang.ClassNotFoundException: javax.ws.rs.NotSupportedException
	at java.net.URLClassLoader.findClass(URLClassLoader.java:387)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	... 1 more
```

* 新增 Graphx 类，能够在基于线路网络类的前提下通过节点与边表进行图的构造，后期将会对此类新增诸多的操作函数。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.Graph;
import zhao.algorithmMagic.operands.table.SingletonCell;


public class MAIN1 {
    public static void main(String[] args) {
        // 首先创建出两个节点的坐标
        final IntegerCoordinateTwo c1 = new IntegerCoordinateTwo(-10, 2);
        final IntegerCoordinateTwo c2 = new IntegerCoordinateTwo(20, 20);
        // 然后创建两个节点数据的表
        final Graph.GraphNodeDF nodeDF = Graph.GraphNodeDF.create(
                Graph.GraphNodeSeries.create(c1, SingletonCell.$("zhao"), SingletonCell.$("20")),
                Graph.GraphNodeSeries.create(c2, SingletonCell.$("TY"), SingletonCell.$("22"))
        );
        // 然后创建出两个节点之间的边
        final Graph.GraphEdgeDF edgeDF = Graph.GraphEdgeDF.create(
                // C1 <- 前任 -> C2 代表 C1的前任是C2  C2的前任是C1
                Graph.GraphEdgeSeries.create(c1, c2, SingletonCell.$("前任"))
        );
        // 最后创建图对象
        final Graph parse = Graph.create(nodeDF, edgeDF);

        // 开始绘制图 首先准备线路绘图器
        final Route2DDrawingIntegrator draw = new Route2DDrawingIntegrator("draw", parse);
        if (draw.setImageOutPath("C:\\Users\\zhao\\Desktop\\fsdownload\\res.jpg").run()) {
            System.out.println("ok!!!");
        }
    }
}
```

* 针对 Graph 类，我们可以通过 get 函数获取到 node 和 edge 的映射表。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.Graph;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class MAIN1 {
    public static void main(String[] args) {
        // 首先创建出两个节点的坐标
        final IntegerCoordinateTwo c1 = new IntegerCoordinateTwo(-10, 2);
        final IntegerCoordinateTwo c2 = new IntegerCoordinateTwo(20, 20);
        // 然后创建图对象
        final Graph parse = Graph.create(
                // 创建两个节点数据的表
                Graph.GraphNodeDF.create(
                        Graph.GraphNodeSeries.create(
                                c1, SingletonCell.$("zhao"), SingletonCell.$("20")
                        ),
                        Graph.GraphNodeSeries.create(
                                c2, SingletonCell.$("TY"), SingletonCell.$("22")
                        )
                ),
                // 创建出两个节点之间的边
                Graph.GraphEdgeDF.create(
                        // C1 <- 前任 -> C2 代表 C1的前任是C2  C2的前任是C1
                        Graph.GraphEdgeSeries.create(c1, c2, SingletonCell.$("前任"))
                )
        );
        // 获取到图对象中的所有节点数据映射表
        final HashMap<String, Graph.GraphNodeSeries> nodes = parse.getNodes();
        // 查看图对象中的所有边数据
        final HashSet<IntegerConsanguinityRoute2D> edges = parse.getEdges();
        // 我们在这里打印其中的信息 由于
        edges.forEach(edge -> {
            // 通过坐标名称 从节点映射表中 获取到起始节点与终止节点的数据Series
            final Graph.GraphNodeSeries start = nodes.get(edge.getStartingCoordinateName());
            final Graph.GraphNodeSeries end = nodes.get(edge.getEndPointCoordinateName());
            System.out.println(
                    "起始点：" + edge.getStartingCoordinate() +
                            // 由于上面构建节点Series的时候 第一个是坐标 第二个是名字 第三个是age
                            // 所以在这里也是相同的格式
                            "\t名称:" + start.getCell(1).getValue() + '\n' +
                            "终止点：" + edge.getEndPointCoordinate() + "\t名称:" + end.getCell(1)
            );
        });
    }
}
```

### Version update date : xx xx-xx-xx