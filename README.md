# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/README-Chinese.md)
- knowledge base(Coming soon)
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

Various algorithms, vector computing, coordinate computing, spatial coordinates and other functions are included. The
API is simple to operate, and machine learning and mathematics, medicine, artificial intelligence and other fields are
highly practical.

### Maven dependency

You can integrate Arithmetic Star into your project through maven, and the configuration of Maven is shown below. You
can add it to your maven project, or you can download it from Releases and manually integrate it into your project.

```
Waiting
```

### Use API examples

这里是""的使用示例,展示的是人与人之间的关系分析与预测,在"ZhaoCoordinateNet"算法中, 人的关系网络被分析了出来, 同时

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
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
        DoubleCoordinateTwo E = new DoubleCoordinateTwo(6, 1);
        DoubleCoordinateTwo Z = new DoubleCoordinateTwo(1, 21);

        /*
         Get the relationship network. This algorithm is implemented by me to infer the relationship network of people.
         You can customize the name here. It should be noted that the instantiation of the integrator below requires you to pass the name in.
         */
        ZhaoCoordinateNet2D zhaoCoordinateNet = ZhaoCoordinateNet2D.getInstance("Z");

        /*
         Add the relationship of people to the relationship network, please note that the relationship network of the algorithm already contains your data,
         so you must pass the same name in the integration below, so that the integrator can get the temporary network in your algorithm grid data
         */
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> B", A, B)); // Representing A takes the initiative to know B
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> C", A, C));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("E -> Z", E, Z));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> Z", A, Z));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("B -> Z", B, Z));

        // Use a 2-dimensional route drawing integrator to output a picture of the relationship network between all the people above
        Route2DDrawingIntegrator a = new Route2DDrawingIntegrator("A", "Z");
        // Set image output path
        a.setImageOutPath("D:\\out\\image.jpg")
                // set image width
                .setImageWidth(1000)
                // set image height
                .setImageHeight(1000)
                // Set discrete thresholds to amplify small changes
                .setDiscreteThreshold(4)
                // Run the integrator!
                .run();

        // Clean data in relational network
        zhaoCoordinateNet.clear();
    }
}
```

- The relationship network picture generated after running
  ![image](https://user-images.githubusercontent.com/113756063/196412140-8b81979d-ecc1-4774-9cbe-df8a89c19c1c.png)

# directory structure

### KnowledgeDocument

The knowledge base file archive is used for the storage task of ASMB series knowledge documents. You can access it
directly through the documents on the home page. There is no need to enter here directly. It is messy internally.

### src_code

The source code storage directory of ASMB, where you can view the relevant source code of ASMBr. Of course, here is the
latest source code, which you can use to compile, so that you can obtain the latest version.

Note: The latest version is often unstable, so we recommend you to use the version that has been released for a long
time!

### README-Chinese.md

The Chinese version of the ASMB homepage document. You can switch languages on the homepage default page to access this
file.

### README.md

The default version of the ASMB homepage document. You can directly access this file on the homepage!

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/README-Chinese.md)

<hr>

#### date:2022-10-10
