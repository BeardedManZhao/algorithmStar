package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.Vector;
import zhao.algorithmMagic.utils.ASClass;

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
public class CosineDistance<V extends Vector<?, ?>> implements DistanceAlgorithm {
    protected final Logger logger;
    protected final String AlgorithmName;

    protected CosineDistance() {
        this.AlgorithmName = "CosineDistance";
        this.logger = Logger.getLogger("CosineDistance");
    }

    protected CosineDistance(String AlgorithmName) {
        this.logger = Logger.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static <V extends Vector<?, ?>> CosineDistance<V> getInstance(String Name) {
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
     * 使用一个向量计算真实距离，具体实现请参阅 api node
     * <p>
     * Use a vector to calculate the true distance, see the api node for the specific implementation
     *
     * @param doubleVector 被计算的向量
     *                     <p>
     *                     Calculated vector
     * @return 该向量与同等长度0填充向量的夹角余弦值，不推荐使用，您应该使用"getCos"
     * @deprecated 余弦距离中是两个向量之间的计算，因此该方法的调用会产生一些意外的结果，请您调用"getCos"
     */
    @Override
    @Deprecated
    public double getTrueDistance(DoubleVector doubleVector) {
        Double aDouble = doubleVector.innerProduct(DoubleVector.parse(new double[doubleVector.getNumberOfDimensions()]));
        logger.info(aDouble + " / ( " + doubleVector + " * " + doubleVector + " )");
        return aDouble / (doubleVector.moduleLength() * 0);
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
    public double getCos(Vector<V, Double> vector1, Vector<V, Double> vector2) {
        Double aDouble = vector1.innerProduct(vector2.expand());
        logger.info(aDouble + " / ( " + vector1 + " * " + vector2 + " )");
        return aDouble / (vector1.moduleLength() * vector2.moduleLength());
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
    public double getCosineFunctionVariable(Vector<V, Double> vector1, Vector<V, Double> vector2) {
        double cos = getCos(vector1, vector2);
        logger.info("ArcCos(Cos(" + cos + "))");
        return Math.acos(cos);
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
    public double getAngularDegree(Vector<V, Double> vector1, Vector<V, Double> vector2) {
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
}