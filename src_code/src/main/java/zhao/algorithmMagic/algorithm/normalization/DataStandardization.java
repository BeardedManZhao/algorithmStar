package zhao.algorithmMagic.algorithm.normalization;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * Java类于 2022/10/13 12:26:22 创建
 * <p>
 * 数据标准化算法的抽象类，通过此类进行数据的各种标准化算法。
 * <p>
 * An abstract class for data normalization algorithms, through which various data normalization algorithms are performed.
 *
 * @author zhao
 */
public abstract class DataStandardization implements OperationAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected DataStandardization() {
        this.AlgorithmName = "EuclideanMetric";
        this.logger = Logger.getLogger("EuclideanMetric");
    }

    protected DataStandardization(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = Logger.getLogger(algorithmName);
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param v 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *          <p>
     *          The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract FloatingPointCoordinates<DoubleCoordinateMany> NormalizedSequence(DoubleCoordinateMany v);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param v 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *          <p>
     *          The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract IntegerCoordinates<IntegerCoordinateMany> NormalizedSequence(IntegerCoordinateMany v);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param doubleVector 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                     <p>
     *                     The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract DoubleVector NormalizedSequence(DoubleVector doubleVector);

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
