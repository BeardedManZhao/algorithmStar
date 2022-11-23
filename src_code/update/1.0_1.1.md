# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) Algorithm Star-MachineBrain

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README-Chinese.md)
- knowledge base(Coming soon)
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194832492-f8c184c1-55e8-4f16-943a-34b99ac751d4.png"/>
  </a>

### Update log:

* Framework version: 1.1

- New monotone interval model prediction integrator "IncrementalLearning"
- The function "runAndReturnValue()" is added to the interface of the integrator. Like the run function, this function
  can return a value. Some integrators may use the operation result return of related values.
- Interface of reconstruction distance algorithm

    1. The inapplicable functions are deleted in the interface: double getTrueDistance (DoubleVector doubleVector) is
       deleted;, The calculation of distance sometimes requires specific values of coordinates. The values of
       coordinates cannot be inferred completely from vectors, so this method is removed!

    2. To make the distance algorithm more flexible, the following functions are added

  ```
  double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D);
  double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute);
  double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D);
  double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute);
  double getTrueDistance(int[] ints1, int[] ints2);
  double getTrueDistance(double[] doubles1, double[] doubles2);
  ```
- A new algorithm "JackardSimilarityCoefficient" has been updated to analyze the similarity coefficient of a set, which
  is suitable for comparing the differences between samples.
- The first letter of the changed software package "Integrator" is lowercase, which conforms to the software package
  specification.
- New Dice Coefficientalgorithm
- Added Hausdorff Distance algorithm
- New usage document of monotone learning integrator
- In the monotone learning integrator, interval step size and increasing mode are added

### Affected version

1. Difference from version: 1.0

   -The "double getTrueDistance(DoubleVector doubleVector);" in the "DistanceAlgorithm" interface is deleted In version
   1.0, this method is still available. In version 1.1, because the design of this sub function is very inflexible, it
   is deleted and replaced by many new functions. These functions are more flexible.

   -The first letter of the changed software package "Integrator" is lowercase, which conforms to the software package
   specification.

<hr>

- 切换到 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README-Chinese.md)

### Version update date : 2022-10-29
