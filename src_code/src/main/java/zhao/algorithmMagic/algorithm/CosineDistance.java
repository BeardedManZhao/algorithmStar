package zhao.algorithmMagic.algorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.operands.DoubleVector;

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
 * @author 4
 */
public class CosineDistance implements OperationAlgorithm<CosineDistance> {
    protected final Logger logger;
    protected final String AlgorithmName;

    private CosineDistance() {
        logger = Logger.getLogger("CosineDistance");
        this.AlgorithmName = "CosineDistance";
        this.init();
    }

    public CosineDistance(String AlgorithmName) {
        this.logger = Logger.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
        this.init();
    }

    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return AlgorithmName;
    }

    /**
     * @return 该算法的具体实现类，您可以通过该函数将算法从抽象转换为一个具体的实现
     * <p>
     * The concrete implementation class of the algorithm, through which you can convert the algorithm from an abstract to a concrete implementation
     */
    @Override
    public CosineDistance extract() {
        return this;
    }

    /**
     * 获取到两个向量夹角的余弦值
     * <p>
     * Get the cosine of the angle between two vectors
     *
     * @param doubleVector1 浮点向量1
     * @param doubleVector2 浮点向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getCos(DoubleVector doubleVector1, DoubleVector doubleVector2) {
        Double aDouble = doubleVector1.innerProduct(doubleVector2);
        logger.info(aDouble + " / ( " + doubleVector1 + " * " + doubleVector2 + " )");
        return aDouble / (doubleVector1.moduleLength() * doubleVector2.moduleLength());
    }

    /**
     * 获取到两个向量夹角的余弦函数变量数值，"cosX" 中的 "X"
     * <p>
     * Get the cosine function variable value of the angle between two vectors, "X" in "cosX"
     *
     * @param doubleVector1 浮点向量1
     * @param doubleVector2 浮点向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getCosineFunctionVariable(DoubleVector doubleVector1, DoubleVector doubleVector2) {
        double cos = getCos(doubleVector1, doubleVector2);
        logger.info("ArcCos(Cos(" + cos + "))");
        return Math.acos(cos);
    }

    /**
     * 获取到两个向量夹角的角度
     * <p>
     * Get the angle between two vectors
     *
     * @param doubleVector1 浮点向量1
     * @param doubleVector2 浮点向量2
     * @return 夹角的余弦值，double类型。
     * <p>
     * Cosine of the included angle, double type.
     */
    public double getAngularDegree(DoubleVector doubleVector1, DoubleVector doubleVector2) {
        double v = Math.toDegrees(getCosineFunctionVariable(doubleVector1, doubleVector2));
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
        if (OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }
}
