package io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.Vector;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;

/**
 * Java类于 2022/10/9 22:51:01 创建
 * 余弦距离(Cosine Distance)也可以叫余弦相似度。 几何中夹角余弦可用来衡量两个向量方向的差异，机器学习中借用这一概念来衡量样本向量之间的差异。
 * <p>
 * 相比距离度量，余弦相似度更加注重两个向量在方向上的差异，而非距离或长度上。
 * <p>
 * Cosine Distance can also be called cosine similarity. The cosine of the included angle in geometry can be used to measure the difference in the direction of two vectors, and this concept is borrowed in machine learning to measure the difference between sample vectors.
 * <p>
 * Compared with distance measures, cosine similarity pays more attention to the difference in direction of two vectors, rather than distance or length.
 *
 * @param <V> 参与本算法运算的类对象类型
 *            <p>
 *            The class object type that participates in the operation of this algorithm
 * @author zhao
 */
public class CosineDistance<V extends Vector<?, ?, ?>> implements DistanceAlgorithm {
    protected final String AlgorithmName;

    protected CosineDistance() {
        this.AlgorithmName = "CosineDistance";
    }

    protected CosineDistance(String AlgorithmName) {
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象
     *
     * @param Name 该算法的名称
     * @param <V>  您希望使用这个算法进行哪一种向量的计算
     *             <p>
     *             What kind of vector calculation do you want to use this algorithm for
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static <V extends Vector<?, ?, ?>> CosineDistance<V> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof CosineDistance) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于CosineDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
            }
        } else {
            CosineDistance<V> cosineDistance = new CosineDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(cosineDistance);
            return cosineDistance;
        }
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return AlgorithmName;
    }

    /**
     * 获取到两个向量夹角的余弦值
     * <p>
     * Get the cosine of the angle between two vectors
     *
     * @param vector1 向量1
     * @param vector2 向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getCos(Vector<V, Double, double[]> vector1, Vector<V, Double, double[]> vector2) {
        return vector1.innerProduct(vector2.expand()) / (vector1.moduleLength() * vector2.moduleLength());
    }

    /**
     * 获取到两个向量夹角的余弦函数变量数值，"cosX" 中的 "X"
     * <p>
     * Get the cosine function variable value of the angle between two vectors, "X" in "cosX"
     *
     * @param vector1 浮点向量1
     * @param vector2 浮点向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getCosineFunctionVariable(Vector<V, Double, double[]> vector1, Vector<V, Double, double[]> vector2) {
        return Math.acos(getCos(vector1, vector2));
    }

    /**
     * 获取到两个向量夹角的角度
     * <p>
     * Get the angle between two vectors
     *
     * @param vector1 浮点向量1
     * @param vector2 浮点向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getAngularDegree(Vector<V, Double, double[]> vector1, Vector<V, Double, double[]> vector2) {
        double v = Math.toDegrees(getCosineFunctionVariable(vector1, vector2));
        return v >= 180 ? 180 : v;
    }

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    @Override
    public boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算一个路线的起始点到0点的向量与终止点到0点的向量。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute 需要被计算的路线对象
     *                                 <p>
     *                                 The route object that needs to be calculated
     * @return 始末到0点所构成的向量的夹角余弦值
     */
    @Override
    public double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        return getTrueDistance(doubleConsanguinityRoute.getStartingCoordinate().toArray(), doubleConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    /**
     * 获取两个序列之间的向量夹角余弦距离
     * <p>
     * Obtain the cosine distance of the vector angle between two sequences
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return 这里的两个数组会被作为两个向量拿去计算。
     */
    @Override
    public double getTrueDistance(double[] doubles1, double[] doubles2) {
        return getTrueDistance(DoubleVector.parse(doubles1), DoubleVector.parse(doubles2));
    }

    /**
     * 获取两个序列之间的向量夹角余弦距离
     * <p>
     * Obtain the cosine distance of the vector angle between two sequences
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return 整数计算的余弦距离精度不如浮点向量，如有精度需求，建议使用浮点向量哦！
     */
    @Override
    public double getTrueDistance(int[] ints1, int[] ints2) {
        return getTrueDistance(IntegerVector.parse(ints1), IntegerVector.parse(ints2));
    }

    /**
     * 获取两个序列之间的向量夹角余弦距离
     * <p>
     * Obtain the cosine distance of the vector angle between two sequences
     *
     * @param doubleVector1 向量对象1
     * @param doubleVector2 向量对象2
     * @return 两个向量之间的余弦距离系数
     */
    public double getTrueDistance(DoubleVector doubleVector1, DoubleVector doubleVector2) {
        return doubleVector1.innerProduct(doubleVector2) / (doubleVector1.moduleLength() * doubleVector2.moduleLength());
    }

    /**
     * 获取两个序列之间的向量夹角余弦距离
     * <p>
     * Obtain the cosine distance of the vector angle between two sequences
     *
     * @param integerVector1 向量对象1
     * @param integerVector2 向量对象2
     * @return 两个向量之间的余弦距离系数
     */
    public double getTrueDistance(IntegerVector integerVector1, IntegerVector integerVector2) {
        return (double) integerVector1.innerProduct(integerVector2) / (integerVector1.moduleLength() * integerVector2.moduleLength());
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute2D 需要被计算的路线对象
     *                                   <p>
     *                                   The route object that needs to be calculated
     * @return ...
     */
    @Override
    public double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        return getTrueDistance(doubleConsanguinityRoute2D.getStartingCoordinate().toArray(), doubleConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute 需要被计算的路线对象
     *                                  <p>
     *                                  The route object that needs to be calculated
     * @return ...
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute) {
        return getTrueDistance(integerConsanguinityRoute.getStartingCoordinate().toArray(), integerConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute2D 需要被计算的路线对象
     *                                    <p>
     *                                    The route object that needs to be calculated
     * @return ...
     */
    @Override
    public double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return getTrueDistance(integerConsanguinityRoute2D.getStartingCoordinate().toArray(), integerConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param integerMatrix1 需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @param matrix2        需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(IntegerMatrix integerMatrix1, IntegerMatrix matrix2) {
        return integerMatrix1.innerProduct(matrix2) / (double) (integerMatrix1.moduleLength() * matrix2.moduleLength());
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param matrix1 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @param matrix2 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(DoubleMatrix matrix1, DoubleMatrix matrix2) {
        return matrix1.innerProduct(matrix2) / (matrix1.moduleLength() * matrix2.moduleLength());
    }
}
