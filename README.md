# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/README-Chinese.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

Various algorithms, vector computing, coordinate computing, spatial coordinates and other functions are included. The
API is simple to operate, and machine learning and mathematics, medicine, artificial intelligence and other fields are
highly practical.

### Maven dependency

You can integrate Arithmetic Star into your project through maven, and the configuration of Maven is shown below. You
can add it to your maven project, or you can download it from Releases and manually integrate it into your project.

```xml

<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>algorithmStar</artifactId>
        <version>1.15</version>
    </dependency>
</dependencies>
```

## Use API examples

### Feature calculation algorithm component

Feature computing components are often used in feature engineering, and are the main force of computing in machine
learning tasks. Measurement algorithms, classification algorithms, etc. can use this component computing method. The
feature engineering calculation in Arithmetic Star is component-based calculation. You can directly obtain component
objects and call component functions, or use Arithmetic Star's portal class for feature engineering.

Next is a simple example of feature engineering calculation using algorithmic star portal class

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.EuclideanMetric;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.algorithm.featureExtraction.WordFrequency;
import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

public final class MAIN1 {
    public static void main(String[] args) {
        // Prepare an array of two texts, including two string statements that need to be processed into feature vectors.
        String[] data = {
                "Good evening, dear, don't forget the agreement between us. It's 9:00 tomorrow morning.",
                "Good morning, dear, don't forget the agreement between us, at 9:00 in the morning."
        };
        AlgorithmStar<Object, ColumnIntegerMatrix> algorithmStar = AlgorithmStar.getInstance();
        // Start feature extraction, and a matrix will be returned after the word frequency vector is successfully extracted
        ColumnIntegerMatrix word = algorithmStar.extract(WordFrequency.getInstance("word"), data);
        // Print the generated vector matrix
        System.out.println(word);
        // Start to obtain the vector corresponding to the two sentences in the matrix
        int[] arrayByColIndex1 = word.getArrayByColIndex(0);
        int[] arrayByRowIndex2 = word.getArrayByColIndex(1);
        // Start to calculate the German distance between two vectors
        double res = algorithmStar.getTrueDistance(EuclideanMetric.getInstance("e"), arrayByColIndex1, arrayByRowIndex2);
        System.out.println(res);
        // Start to calculate the Manhattan distance between two vectors
        double res1 = algorithmStar.getTrueDistance(ManhattanDistance.getInstance("man"), arrayByColIndex1, arrayByRowIndex2);
        System.out.println(res1);
        AlgorithmStar.clear();
    }
}
```

- run results

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

### Route generation integrator

Here is an example of the use of "ZhaoCoordinateNet2D", which shows the analysis and prediction of the relationship
between people. In the "ZhaoCoordinateNet" algorithm, the relationship network of people is analyzed, and at the same
time, the network analysis and mapping can be performed.

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.Route2DDrawingIntegrator;
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

### AsLib

The directory contains the DLL dynamic library file and the source code corresponding to the library file that the
Algorithm Star machine learning library depends on when it runs. In order to achieve better compatibility in the machine
learning library, the C compiled DLL is introduced. Users can load the DLL into the machine learning library before the
AS library runs.

### KnowledgeDocument

The knowledge base file archive is used for the storage task of ASMB series knowledge documents. You can access it
directly through the documents on the home page. There is no need to enter here directly. It is messy internally.

### src_code

The source code storage directory of ASMB, where you can view the relevant source code of ASMB. Of course, here is the
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
