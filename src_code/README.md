# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/Zhao-develop/src_code/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1. 24-1. 25

* Update version number.

* Remove the dependency on external projects for the "unsupported operation exception" in the RouteNet interface to
  avoid the exception information listed below. In this version, it is not necessary to obtain third-party dependencies
  for"javax. ws. rs".

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

* Add Graphx class, which can construct graphs through nodes and edge tables based on circuit network classes. In the
  future, many operation functions will be added to this class.

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

### Version update date : xx xx-xx-xx