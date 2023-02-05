# ![image](https://user-images.githubusercontent.com/113756063/194830221-abe24fcc-484b-4769-b3b7-ec6d8138f436.png) 算法之星-机器大脑

- Switch to [English Document](https://github.com/BeardedManZhao/algorithmStar/blob/main/src_code/update/1.14_1.15.md)
- knowledge base
  <a href="https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/knowledge%20base-Chinese.md">
  <img src = "https://user-images.githubusercontent.com/113756063/194838003-7ad14dac-b38c-4b57-a942-ba58f00baaf7.png"/>
  </a>

### 更新日志

* 框架版本：1.14 - x.xx
* 新增随机森林计算组件，随机森林相较于决策树，可以让工作变的轻松，但计算量很大，因为森林中有很多决策树。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.algorithm.schemeAlgorithm.RandomForest;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

public class MAIN1 {
    public static void main(String[] args) {
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new int[]{1, 1, 1},
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0},
                new int[]{1, 1, 9},
                new int[]{0, 1, 0},
                new int[]{2, 1, 0},
                new int[]{0, 1, 0},
                new int[]{1, 4, 0},
                new int[]{0, 1, 1},
                new int[]{5, 1, 1},
                new int[]{0, 1, 0},
                new int[]{7, 0, 1},
                new int[]{1, 1, 1},
                new int[]{7, 0, 3},
                new int[]{7, 10, 0},
                new int[]{5, 1, 2},
                new int[]{5, 1, 2}
        );
        // 构建条件
        ArrayIntegerFiltering arrayIntegerFiltering1 = v -> v[1] == 0;
        ArrayIntegerFiltering arrayIntegerFiltering2 = v -> v[0] == 7;
        ArrayIntegerFiltering arrayIntegerFiltering3 = v -> v[2] == 3;

        // 开始进行决策树结果获取
        DecisionTree decisionTree = DecisionTree.getInstance("DecisionTree");
        IntegerMatrix integerMatrix = decisionTree.decisionAndGet(
                // 设置需要被处理的矩阵对象以及调用熵运算时使用的log底数
                columnDoubleMatrix, 2,
                // 设置运行时的过滤事件
                arrayIntegerFiltering2, arrayIntegerFiltering1, arrayIntegerFiltering3
        );
        System.out.println(integerMatrix);

        // 开始进行随机森林结果获取
        RandomForest randomForest = RandomForest.getInstance("RandomForest");
        IntegerMatrix integerMatrix1 = randomForest.decisionAndGet(
                // 设置需要被处理的矩阵对象以及调用熵运算时使用的log底数，同时设置了森林中的决策树数量3，以及决策树负责的样本量7
                columnDoubleMatrix, 2, 3, 19,
                // 设置运行时的过滤事件
                arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3
        );
        System.out.println(integerMatrix1);
    }
}
```

* 决策计算组件现在实现了方案计算与结果计算的合并，通过函数 decisionAndGet 可以在决策分析的同时获取到结果数值。
* 能够调用一元线性回归计算组件进行模型的推测

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.modelAlgorithm.LinearRegression;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"x", "y"},
                null,
                new int[]{1, 50},
                new int[]{2, 100},
                new int[]{3, 150},
                new int[]{4, 200}
        );
        // 获取到线性回归
        LinearRegression line = LinearRegression.getInstance("line");
        // 开始计算线性回归 计算x 与 y 之间的关系 其中 x 为自变量  y 为因变量
        // 设置自变量的列编号
        line.setFeatureIndex(0);
        // 设置因变量的列编号
        line.setTargetIndex(1);
        // 计算出回归系数与结果值
        double[] doubles = line.modelInference(columnDoubleMatrix);
        // 获取到线性回归计算之后的权重数组，并将权重数组插入到公式打印出来
        System.out.println("数据特征：");
        System.out.println("y = x * " + doubles[0] + " + " + doubles[1]);
    }
}
```

* 优化文档
* 为诸多操作数提供浅拷贝构造函数
* 支持通过稀疏坐标数据组成的数组直接构建矩阵对象

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个二维数组，其中每一个元素代表一个坐标点的位置与值
        int[][] ints = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new int[]{1, 1, 1}, new int[]{2, 2, 1}, new int[]{3, 2, 2}
        };
        // 根据稀疏坐标点描述创建矩阵对象
        IntegerMatrix sparse1 = IntegerMatrix.sparse(ints);
        // 打印矩阵
        System.out.println(sparse1);

        // 浮点矩阵也是可以这样使用的
        // 首先准备稀疏矩阵数据
        double[][] doubles = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new double[]{1, 1, 1}, new double[]{2, 2, 1}, new double[]{3, 2, 2}
        };
        // 创建矩阵
        DoubleMatrix sparse2 = DoubleMatrix.sparse(doubles);
        // 打印矩阵
        System.out.println(sparse2);
    }
}
```

* 矩阵空间的get函数能够获取到每个矩阵空间的不同面

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.SpatialPlane;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个二维数组，其中每一个元素代表一个坐标点的位置与值
        int[][] ints = {
                // 第一个值代表坐标点的数值 后面的两个值代表横纵坐标
                new int[]{1, 1, 1}, new int[]{2, 2, 1}, new int[]{3, 2, 2}
        };
        // 根据稀疏坐标点描述创建矩阵对象
        IntegerMatrix sparse1 = IntegerMatrix.sparse(ints);
        // 将 sparse1 矩阵与 sparse1的转置矩阵叠加成矩阵空间
        IntegerMatrixSpace integerMatrixSpace = IntegerMatrixSpace.parse(sparse1, sparse1.transpose());
        // 打印矩阵空间
        System.out.println(integerMatrixSpace);
        // 获取到矩阵空间中的上方角度矩阵
        System.out.println(integerMatrixSpace.get(SpatialPlane.INTEGER_MATRIX_UPPER));
        // 获取到矩阵空间中的下方角度矩阵
        System.out.println(integerMatrixSpace.get(SpatialPlane.INTEGER_MATRIX_BELOW));
        // 获取到矩阵空间中的前面角度矩阵
        System.out.println(integerMatrixSpace.get(SpatialPlane.INTEGER_MATRIX_FRONT));
        // 获取到矩阵空间中的后面角度矩阵
        System.out.println(integerMatrixSpace.get(SpatialPlane.INTEGER_MATRIX_AFTER));

        // 将矩阵转换成浮点矩阵，然后构建矩阵空间，验证浮点矩阵是否可以适用
        DoubleMatrix sparse2 = DoubleMatrix.sparse(new double[]{1, 1, 1}, new double[]{2, 2, 1}, new double[]{3, 2, 2});
        // 将 sparse1 矩阵与 sparse1的转置矩阵叠加成矩阵空间
        DoubleMatrixSpace doubleMatrixSpace = DoubleMatrixSpace.parse(sparse2, sparse2.transpose());
        // 打印矩阵空间
        System.out.println(doubleMatrixSpace);
        // 获取到矩阵空间中的上方角度矩阵
        System.out.println(doubleMatrixSpace.get(SpatialPlane.DOUBLE_MATRIX_UPPER));
        // 获取到矩阵空间中的下方角度矩阵
        System.out.println(doubleMatrixSpace.get(SpatialPlane.DOUBLE_MATRIX_BELOW));
        // 获取到矩阵空间中的前面角度矩阵
        System.out.println(doubleMatrixSpace.get(SpatialPlane.DOUBLE_MATRIX_FRONT));
        // 获取到矩阵空间中的后面角度矩阵
        System.out.println(doubleMatrixSpace.get(SpatialPlane.DOUBLE_MATRIX_AFTER));
    }
}
```

* 所有向量支持左右移动运算函数，能够将指定向量按照某个方向移动指定位数。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.vector.ColumnDoubleVector;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个向量
        IntegerVector parse1 = IntegerVector.parse(1, 2, 3, 4, 5);
        // 创建源向量左移1的新向量
        System.out.println(parse1.leftShift(1, true));
        // 将源向量左移2
        System.out.println(parse1.leftShift(2, false));
        // 将源向量再次左移2
        System.out.println(parse1.leftShift(2, false));
        // 将源向量再次右移2
        System.out.println(parse1.rightShift(2, false));

        // 创建一个向量
        DoubleVector parse2 = DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0);
        // 创建源向量左移1的新向量
        System.out.println(parse2.leftShift(1, true));
        // 将源向量左移2
        System.out.println(parse2.leftShift(2, false));
        // 将源向量再次左移2
        System.out.println(parse2.leftShift(2, false));
        // 将源向量再次右移2
        System.out.println(parse2.rightShift(2, false));

        // 获取到一个具有字段名称的向量
        String[] strings = {"f1", "f2", "f3", "f4", "f5"};
        ColumnDoubleVector columnDoubleVector = ColumnDoubleVector.parse(
                "浮点向量", strings, 1, 2, 3, 4, 5
        );
        // 将源向量左移动4
        System.out.println(columnDoubleVector.leftShift(4, false));
        // 将源向量右移动4
        System.out.println(columnDoubleVector.rightShift(4, false));
    }
}
```

* 所有矩阵支持左右移动运算函数，能够将指定向量按照某个方向移动指定位数。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个向量
        IntegerVector integerVector1 = IntegerVector.parse(1, 2, 3, 4, 5);
        // 创建出 integerVector1 向量向左位移的新向量 integerVector2
        IntegerVector integerVector2 = integerVector1.leftShift(2, true);
        // 将两个向量组成一个矩阵
        IntegerMatrix parse = IntegerMatrix.parse(integerVector1, integerVector2);
        // 打印矩阵对象
        System.out.println(parse);
        // 将矩阵左位移
        System.out.println(parse.leftShift(2, false));
        // 将矩阵右位移
        System.out.println(parse.rightShift(2, false));
    }
}
```

* 新增K-Means无监督聚类计算算法组件，其能够在不提供样本的情况下计算，前提是样本中各类别的维度数量比较均匀。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KMeans;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.vector.ColumnIntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵，接下来对矩阵中的数据进行KMeans算法聚类
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                new String[]{"说话熟练度", "工具熟练度", "觅食熟练度", "飞翔熟练度"},
                null,
                new int[]{100, 100, 20, 0},
                new int[]{110, 150, 30, 0},
                new int[]{120, 130, 30, 0},
                new int[]{10, 1, 100, 100},
                new int[]{10, 0, 80, 0},
                new int[]{0, 0, 90, 50},
                new int[]{0, 10, 70, 0},
                new int[]{11, 11, 80, 0}
        );
        // 获取到KMeans计算组件
        KMeans kMeans = KMeans.getInstance("KMeans");
        // 设置随机种子
        kMeans.setSeed(2048);
        // 设置随机打乱时不需要拷贝 TODO 这个也是默认的模式
        kMeans.setCopy(false);
        // 打印矩阵数据
        System.out.println(parse);
        // 开始进行聚类 并获取结果集合 其中key是空间名称 value是类别数据对象
        HashMap<String, ArrayList<ColumnIntegerVector>> classification = kMeans.classification(
                new String[]{"标签1", "标签2"}, // 这里设置K个空间的名称
                parse
        );
        // 打印聚类结果
        for (ArrayList<ColumnIntegerVector> value : classification.values()) {
            // 打印集合中的所有数据对象，由于分类的是带有字段的矩阵，因此每一个向量的类别也可通过字段获取
            System.out.print(value.get(0).vectorName());
            System.out.print('\t');
            System.out.println(value);
        }
    }
}
```

* 修正集成器中因为日志门面导致找不到指定类的问题，目前已将集成器中的日志更换为log4j2
* 矩阵对象支持了Java原生的迭代模式，实现了Java的迭代接口。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

import java.util.Arrays;

public final class MAIN1 {
    public static void main(String[] args) {
        IntegerMatrix parse1 = IntegerMatrix.parse(
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 1, 40, 1, 1, 60, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        );
        // 创建一个矩阵空间
        IntegerMatrixSpace parse = IntegerMatrixSpace.parse(parse1, parse1.shuffle(22));
        // 使用迭代器迭代矩阵空间对象
        for (IntegerMatrix integerMatrix : parse) {
            System.out.println(integerMatrix);
        }
        // 获取到其中的第二个矩阵空间对象
        if (parse.hasNext()) {
            IntegerMatrix integerMatrix = parse.toMatrix();
            // 对矩阵对象使用迭代
            for (int[] anInt : integerMatrix) {
                System.out.println(Arrays.toString(anInt));
            }
        }
    }
}
```

* 所有的向量与矩阵对象已经容许序列化操作，能够将数据在网络与磁盘之间进行传输

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;

import java.io.*;
import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) throws IOException {
        // 获取到特征矩阵
        String[] strings = {"本年累计金额", "本月金额"};
        ColumnDoubleMatrix parse = ColumnDoubleMatrix.parse(
                strings,
                new String[]{"N1", "N2", "N3", "N4", "N5", "N6", "N7"},
                new double[]{100, 10},
                new double[]{200, 20},
                new double[]{300, 30},
                new double[]{400, 40},
                new double[]{500, 50},
                new double[]{600, 60},
                new double[]{700, 70}
        );
        // 使用增强for打印矩阵中的元素
        for (double[] res : parse) {
            System.out.println(Arrays.toString(res));
        }
        // 将矩阵对象序列化成字节数据输出到文件中
        File file = new File("C:\\Users\\zhao\\Desktop\\out\\ColumnDoubleMatrix.data");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(parse);
        objectOutputStream.flush();
        objectOutputStream.close();

        System.out.println("+==================================================+");

        // 将矩阵对象重新读取进来
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            ColumnDoubleMatrix columnDoubleMatrix = (ColumnDoubleMatrix) objectInputStream.readObject();
            for (double[] doubles : columnDoubleMatrix) {
                System.out.println(Arrays.toString(doubles));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

* 重写决策树中的字符串结果执行函数，让它变得重要起来，它不在返回一个简单的字符串过程，而是一个流程图代码，同时将此函数更改为静态函数。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.DecisionTree;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.util.ArrayList;

public class MAIN1 {
    public static void main(String[] args) {
        ColumnIntegerMatrix columnDoubleMatrix = ColumnIntegerMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new int[]{1, 1, 1},
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0},
                new int[]{1, 1, 0},
                new int[]{1, 1, 12},
                new int[]{0, 1, 0}
        );
        System.out.println(columnDoubleMatrix);
        // 构建一些事件过滤器
        // 有钱选项为1
        ArrayIntegerFiltering arrayIntegerFiltering1 = v -> v[2] == 1;
        // 身高选项为1
        ArrayIntegerFiltering arrayIntegerFiltering2 = v -> v[1] == 1;
        // 颜值选项为1
        ArrayIntegerFiltering arrayIntegerFiltering3 = v -> v[0] == 1;
        System.out.println(arrayIntegerFiltering1);
        System.out.println(arrayIntegerFiltering2);
        System.out.println(arrayIntegerFiltering3);
        // 获取到决策树
        DecisionTree d = DecisionTree.getInstance("d");
        // 设置精准模式
        d.setAccurate(true);
        // 设置中心字段索引
        d.setGroupIndex(0);
        // 可以通过实例化的对象直接获取到结果，这种方式能够快速的将结果返回
        System.out.println(d.decisionAndGet(columnDoubleMatrix, 2, arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3));
        // 也可以仅仅让决策树计算最优方案，并返回优化顺序后的方案列表
        ArrayList<ArrayIntegerFiltering> decision = d.decision(columnDoubleMatrix, arrayIntegerFiltering1, arrayIntegerFiltering2, arrayIntegerFiltering3);
        // 将最优方案传递给决策树执行，并接收返回的结果 TODO 您也可以使用该方案去做足够多的事情
        String s1 = DecisionTree.executeGetString(columnDoubleMatrix.toArrays(), decision); // 1.15版本此函数成为了静态函数
        // 将最有方案传递给决策树执行，并获取到详细的结果
        String s2 = DecisionTree.executeGetString(columnDoubleMatrix.toArrays(), decision, false, true); // 1.15版本此函数成为了静态函数
        System.out.println(s1);
        System.out.println(s2);
    }
}
```

```mermaid
graph TB
AllData -- zhao.algorithmMagic.MAIN1$$Lambda$3/10069385-103e736=true --> TrueData1[Node No.1<br>Remaining quantity = 5<br>Remaining percentage = 55.55555555555556%<br>]
AllData -. zhao.algorithmMagic.MAIN1$$Lambda$3/10069385-103e736=false .-> FalseData1[Node No.1<br>Removal quantity = 4<br>Removal percentage = 44.44444444444444%<br>]
TrueData1 -- zhao.algorithmMagic.MAIN1$$Lambda$1/8468976-372a00=true --> TrueData2[Node No.2<br>Remaining quantity = 2<br>Remaining percentage = 40.0%<br>]
TrueData1 -. zhao.algorithmMagic.MAIN1$$Lambda$1/8468976-372a00=false .-> FalseData2[Node No.2<br>Removal quantity = 3<br>Removal percentage = 60.0%<br>]
TrueData2 -- zhao.algorithmMagic.MAIN1$$Lambda$2/26887603-dd8dc3=true --> TrueData3[Node No.3<br>Remaining quantity = 1<br>Remaining percentage = 50.0%<br>]
TrueData2 -. zhao.algorithmMagic.MAIN1$$Lambda$2/26887603-dd8dc3=false .-> FalseData3[Node No.3<br>Removal quantity = 1<br>Removal percentage = 50.0%<br>]
```

```mermaid
graph TB
AllData -- zhao.algorithmMagic.MAIN1$$Lambda$3/10069385-103e736=true --> TrueData1[1	1	1	<br>1	0	1	<br>1	0	0	<br>1	1	0	<br>1	1	12	<br>]
AllData -. zhao.algorithmMagic.MAIN1$$Lambda$3/10069385-103e736=false .-> FalseData1[0	1	0	<br>0	0	0	<br>0	1	0	<br>0	1	0	<br>]
TrueData1 -- zhao.algorithmMagic.MAIN1$$Lambda$1/8468976-372a00=true --> TrueData2[1	1	1	<br>1	0	1	<br>]
TrueData1 -. zhao.algorithmMagic.MAIN1$$Lambda$1/8468976-372a00=false .-> FalseData2[1	0	0	<br>1	1	0	<br>1	1	12	<br>]
TrueData2 -- zhao.algorithmMagic.MAIN1$$Lambda$2/26887603-dd8dc3=true --> TrueData3[1	1	1	<br>]
TrueData2 -. zhao.algorithmMagic.MAIN1$$Lambda$2/26887603-dd8dc3=false .-> FalseData3[1	0	1	<br>]
```

* 为随机森林计算组件提供决策思维图的生成函数，其能够像决策树那样将每种决策方案的树结合起来。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.schemeAlgorithm.RandomForest;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;

import java.util.ArrayList;
import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) {
        ColumnDoubleMatrix columnDoubleMatrix = ColumnDoubleMatrix.parse(
                new String[]{"颜值", "身高", "有钱"},
                null,
                new double[]{1, 1, 1},
                new double[]{1, 0, 1},
                new double[]{0, 1, 0},
                new double[]{0, 0, 0},
                new double[]{0, 1, 0},
                new double[]{1, 0, 0},
                new double[]{1, 1, 0},
                new double[]{1, 1, 12},
                new double[]{0, 1, 0}
        );
        // 构建一些事件过滤器
        // 有钱选项为1
        ArrayDoubleFiltering arrayDoubleFiltering1 = v -> v[2] == 1;
        // 身高选项为1
        ArrayDoubleFiltering arrayDoubleFiltering2 = v -> v[1] == 1;
        // 颜值选项为1
        ArrayDoubleFiltering arrayDoubleFiltering3 = v -> v[0] == 1;
        System.out.println(arrayDoubleFiltering1);
        System.out.println(arrayDoubleFiltering2);
        System.out.println(arrayDoubleFiltering3);
        // 使用随机森林计算组件进行决策思维图绘制
        String s = RandomForest.executeGetString(
                columnDoubleMatrix.toArrays(),
                new ArrayList<>(Arrays.asList(arrayDoubleFiltering1, arrayDoubleFiltering2, arrayDoubleFiltering3)),
                false, false, 22, 3, 3);
        System.out.println(s);
    }
}
```

```mermaid
graph TB

root == double Tree ==> 0AllData
0AllData -- zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=true --> 0TrueData1[Double True Node No.1<br>Remaining quantity = 2<br>Remaining percentage = 66.66666666666666%<br>]
0AllData -. zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=false .-> 0FalseData1[Double False Node No.1<br>Removal quantity = 1<br>Removal percentage = 33.33333333333334%<br>]
0TrueData1 -- zhao.algorithmMagic.MAIN1$$Lambda$2/10069385-103e736=true --> 0TrueData2[Double True Node No.2<br>Remaining quantity = 1<br>Remaining percentage = 50.0%<br>]
0TrueData1 -. zhao.algorithmMagic.MAIN1$$Lambda$2/10069385-103e736=false .-> 0FalseData2[Double False Node No.2<br>Removal quantity = 1<br>Removal percentage = 50.0%<br>]
0TrueData2 -- zhao.algorithmMagic.MAIN1$$Lambda$3/3615232-8db2f2=true --> 0TrueData3[Double True Node No.3<br>Remaining quantity = 1<br>Remaining percentage = 100.0%<br>]

root == double Tree ==> 1AllData
1AllData -- zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=true --> 1TrueData1[Double True Node No.1<br>Remaining quantity = 1<br>Remaining percentage = 33.33333333333333%<br>]
1AllData -. zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=false .-> 1FalseData1[Double False Node No.1<br>Removal quantity = 2<br>Removal percentage = 66.66666666666667%<br>]
1TrueData1 -- zhao.algorithmMagic.MAIN1$$Lambda$2/10069385-103e736=true --> 1TrueData2[Double True Node No.2<br>Remaining quantity = 1<br>Remaining percentage = 100.0%<br>]
1TrueData2 -- zhao.algorithmMagic.MAIN1$$Lambda$3/3615232-8db2f2=true --> 1TrueData3[Double True Node No.3<br>Remaining quantity = 1<br>Remaining percentage = 100.0%<br>]

root == double Tree ==> 2AllData
2AllData -- zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=true --> 2TrueData1[Double True Node No.1<br>Remaining quantity = 1<br>Remaining percentage = 33.33333333333333%<br>]
2AllData -. zhao.algorithmMagic.MAIN1$$Lambda$1/13257035-dd8dc3=false .-> 2FalseData1[Double False Node No.1<br>Removal quantity = 2<br>Removal percentage = 66.66666666666667%<br>]
2TrueData1 -- zhao.algorithmMagic.MAIN1$$Lambda$2/10069385-103e736=true --> 2TrueData2[Double True Node No.2<br>Remaining quantity = 1<br>Remaining percentage = 100.0%<br>]
2TrueData2 -- zhao.algorithmMagic.MAIN1$$Lambda$3/3615232-8db2f2=true --> 2TrueData3[Double True Node No.3<br>Remaining quantity = 1<br>Remaining percentage = 100.0%<br>]

```

* 为封装了巨大计算量的函数进行DLL动态库计算支持，可以通过DLL库调用这类函数，降低计算时的代价。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        int[][] ints = {
                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 2, 1, 40, 1, 1, 60, 1, 1},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
        };
        // 准备一个矩阵 其中存储的是鸟的数据样本
        IntegerMatrix parse1 = ColumnIntegerMatrix.parse(
                new String[]{"1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d"}, // 样本来源地区编号
                new String[]{"羽毛", "字段占位", "羽毛的颜色", "种族"}, // 样本统计的三种维度
                ints
        );
        // 加载动态库目录 TODO 动态库加载之后，在遇到能够使用DLL动态库进行计算的组件时将会使用DLL计算
        ASDynamicLibrary.addDllDir(new File("xx\\xx\\xx\\xx\\AsLib"));
        // 开始进行特征清洗 去除掉与其中第4行 正相关系数区间达到 [0.8, 1] 的维度
        IntegerMatrix integerMatrix = parse1.deleteRelatedDimensions(3, 0.8, 1);
        System.out.println(integerMatrix);
        // 如果不是很希望它使用DLL库计算，您可以关闭掉DLL的使用
        ASDynamicLibrary.unUseC();
        IntegerMatrix integerMatrix1 = parse1.deleteRelatedDimensions(3, 0.8, 1);
        System.out.println(integerMatrix1);
    }
}
```

### Version update date : XX XX-XX-XX