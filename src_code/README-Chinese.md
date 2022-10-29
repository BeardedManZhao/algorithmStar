# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志：

* 框架版本：1.1
- 新增单调区间模型预测集成器"IncrementalLearning"
- 在集成器的接口中新增了“runAndReturnValue()”函数，与run函数作用一样，该函数可以返回一个数值，有些集成器中可能会使用到有关数值的运算结果返回。
- 重构距离算法的接口

    1. 在接口中删除了不适用的函数：删除了double getTrueDistance(DoubleVector doubleVector);，距离的计算，有时候需要坐标具体值，坐标的值是无法完全靠向量推断出来的，因此该方法被移除！

    2. 为了更加使得距离算法灵活，在其中添加了如下几个函数
  ```
  double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D);
  double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute);
  double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D);
  double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute);
  double getTrueDistance(int[] ints1, int[] ints2);
  double getTrueDistance(double[] doubles1, double[] doubles2);
  ```
- 更新了一个新算法“JaccardSimilarityCoefficient” 能够针对一个集合进行相似度系数的分析，适合用来比较样本之间的差异。
- 变更软件包“Integrator”的首字母为小写，符合软件包规范。
- 新增Dice Coefficient算法
- 新增HausdorffDistance算法
- 新增单调学习集成器的使用文档
- 在单调学习集成器中，新增了区间步长与递增模式

### 有影响的版本

1.与版本：1.0 的差异

- 删除了"DistanceAlgorithm"接口中的"double getTrueDistance(DoubleVector doubleVector);"
  方法，在1.0版本中该方法还处于可用状态，在1.1版本中由于这分函数的设计非常的不灵活，因此删除，并使用了很多新的函数替代，这些函数是更加灵活的。

- 变更软件包“Integrator”的首字母为小写，符合软件包规范。

<hr>

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/README.md)

### Version update date : 2022-10-29
