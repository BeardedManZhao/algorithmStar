# 集成器与启动器

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrators%20and%20initiators.md)

## 介绍

为了简化算法使用的开发，在框架中有提供集成器与启动器，集成器中会包含一些算法的自动使用，内部就是已经实现好的某些需求，例如主页中的展示，通过2维路线绘图集成器，将人与人之间的联系通过算法分析了出来之后，进行了一个图像的输出。

## 集成器

集成器中就是针对算法的内部使用，旨在简化用户对于算法的调用过程，也可以帮助用户完全的隔离算法之间的互相搭配，每一个集成器的父类是“AlgorithmIntegrator”，同时每一个集成器都应提供一个启动器接口，等待用户实现该接口之后，就可以使用该集成器！

下面是集成器的运行流程图！
![image](https://user-images.githubusercontent.com/113756063/196027432-47439ec3-622e-4bd9-9524-abe97cde928e.png)

## 启动器

启动器的角色就是各个对象与集成器之间的适配器，同时解耦算法与集成对象之间的强耦合，运行中用来存储集成器启动需要的数据对象等操作，一个启动器的实现是否复杂需要看启动器对应的集成器，如果集成器对于启动对象的数据依赖较少，那么启动器的实现会很简单，反之也是会繁琐。

下面将使用“推导人与人之间的关系图”这个案例，演示启动器与集成器

- 实现了启动器接口算法的名称来获取到集成器并执行

```java
import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建人员坐标(二维)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);

        // 获取人与人之间的路线对象(二维)
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D1 = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D2 = DoubleConsanguinityRoute2D.parse("A -> C", A, C);

        // 获取坐标网生成算法，这个对象就是实现了启动器接口的类，同时它本身还是一个算法
        ZhaoCoordinateNet2D zhaoCoordinatenet2D = ZhaoCoordinateNet2D.getInstance("Z");
        zhaoCoordinatenet2D.addRoute(doubleConsanguinityRoute2D1);
        zhaoCoordinatenet2D.addRoute(doubleConsanguinityRoute2D2);

        // 获取到2维绘图集成器 这里指定了集成器的名称和启动器的名称！启动器就是我们的那个算法
        Route2DDrawingIntegrator route2DDrawingIntegrator = new Route2DDrawingIntegrator("2DDrawingIntegrator", "Z");
        // 运行
        route2DDrawingIntegrator.run();
    }
}
```

- 实现了启动器接口的对象传参来获取到集成器并执行

```java
import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.Integrator.launcher.Route2DDrawingLauncher;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

import java.util.HashMap;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建人员坐标(二维)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);

        // 获取人与人之间的路线对象(二维)
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D1 = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D2 = DoubleConsanguinityRoute2D.parse("A -> C", A, C);

        // 实例化一个2维绘图的启动器
        Route2DDrawingLauncher route2DDrawingLauncher = new Route2DDrawingLauncher() {
            // 这里返回的就是每一条需要被绘制的线
            @Override
            public HashMap<String, DoubleConsanguinityRoute2D> AcquireImageDataSet() {
                HashMap<String, DoubleConsanguinityRoute2D> hashMap = new HashMap<>();
                hashMap.put("A -> B", doubleConsanguinityRoute2D1);
                hashMap.put("A -> C", doubleConsanguinityRoute2D2);
                return hashMap;
            }

            // 这里返回的是否允许运行集成器，也就是判断集成器是否能够运行
            @Override
            public boolean isSupportDrawing() {
                return true;
            }

            // 这里返回 this 就好
            @Override
            public Route2DDrawingLauncher expand() {
                return this;
            }
        };

        // 获取到2维绘图集成器 这里指定了集成器的名称和启动器的对象参数
        Route2DDrawingIntegrator route2DDrawingIntegrator = new Route2DDrawingIntegrator("2DDrawingIntegrator", route2DDrawingLauncher);
        // 运行
        route2DDrawingIntegrator.run();
    }
}

```

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrators%20and%20initiators.md)
