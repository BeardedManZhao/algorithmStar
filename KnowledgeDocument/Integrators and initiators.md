# Integrators and initiators-Chinese

- 切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrators%20and%20initiators-Chinese.md)

## introduce

In order to simplify the development of algorithm use, an integrator and a launcher are provided in the framework. The
integrator will include some automatic use of algorithms, and some requirements that have been implemented internally,
such as the display on the home page, through 2D route drawing The integrator, after analyzing the connection between
people through an algorithm, outputs an image.

## Integrator

The integrator is for the internal use of the algorithm, which aims to simplify the user's calling process for the
algorithm, and can also help the user to completely isolate the mutual matching between the algorithms. The parent class
of each integrator is "Algorithm Integrator", and each Integrators should provide a starter interface, and after waiting
for the user to implement the interface, the integrator can be used!

The following is the flow chart of the operation of the integrator!
![image](https://user-images.githubusercontent.com/113756063/196027438-45762036-4fa3-4e7c-89da-08ebb398ff88.png)

## Launcher

The role of the initiator is the adapter between each object and the integrator. At the same time, the strong coupling
between the decoupling algorithm and the integrator is used to store the data objects required by the integrator to
start operations. Is the implementation of a initiator complicated? You need to look at the integrator corresponding to
the launcher. If the integrator has less data dependence on the launch object, the implementation of the launcher will
be simple, and vice versa.

The following will use the example of "Derive the relationship between people" to demonstrate the starter and the
integrator

- Implemented the name of the initiator interface algorithm to get to the integrator and execute

```java
import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinatenet2D;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Builder coordinates (2D)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);

        // Get route objects between people (2D)
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D1 = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D2 = DoubleConsanguinityRoute2D.parse("A -> C", A, C);

        // Get the coordinate network generation algorithm. This object is the class that implements the starter interface, and it is also an algorithm itself.法
        ZhaoCoordinatenet2D zhaoCoordinatenet2D = ZhaoCoordinatenet2D.getInstance("Z");
        zhaoCoordinatenet2D.addRoute(doubleConsanguinityRoute2D1);
        zhaoCoordinatenet2D.addRoute(doubleConsanguinityRoute2D2);

        // Get the 2D Drawing Integrator The name of the integrator and the name of the launcher are specified here! The initiator is our algorithm
        Route2DDrawingIntegrator route2DDrawingIntegrator = new Route2DDrawingIntegrator("2DDrawingIntegrator", "Z");
        // run
        route2DDrawingIntegrator.run();
    }
}
```

- The object that implements the starter interface is passed as parameter to get to the integrator and execute it

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
        // Builder coordinates (2D)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);

        // Get route objects between people (2D)
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D1 = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D doubleConsanguinityRoute2D2 = DoubleConsanguinityRoute2D.parse("A -> C", A, C);

        // Instantiate a launcher for 2D drawing
        Route2DDrawingLauncher route2DDrawingLauncher = new Route2DDrawingLauncher() {
            // What is returned here is each line that needs to be drawn
            @Override
            public HashMap<String, DoubleConsanguinityRoute2D> AcquireImageDataSet() {
                HashMap<String, DoubleConsanguinityRoute2D> hashMap = new HashMap<>();
                hashMap.put("A -> B", doubleConsanguinityRoute2D1);
                hashMap.put("A -> C", doubleConsanguinityRoute2D2);
                return hashMap;
            }

            // Returned here is whether the integrator is allowed to run, that is, to determine whether the integrator can run
            @Override
            public boolean isSupportDrawing() {
                return true;
            }

            // Just return this here
            @Override
            public Route2DDrawingLauncher expand() {
                return this;
            }
        };

        // Get the 2D drawing integrator. The name of the integrator and the object parameters of the launcher are specified here
        Route2DDrawingIntegrator route2DDrawingIntegrator = new Route2DDrawingIntegrator("2DDrawingIntegrator", route2DDrawingLauncher);
        // run
        route2DDrawingIntegrator.run();
    }
}

```

- 切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrators%20and%20initiators-Chinese.md)
