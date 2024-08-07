package io.github.beardedManZhao.algorithmStar.algorithm.normalization;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.FloatingPointCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinates;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        this.AlgorithmName = "DataStandardization";
        this.logger = LoggerFactory.getLogger("DataStandardization");
    }

    protected DataStandardization(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = LoggerFactory.getLogger(algorithmName);
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
    public abstract FloatingPointCoordinates<DoubleCoordinateMany> pretreatment(DoubleCoordinateMany v);

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
    public abstract IntegerCoordinates<IntegerCoordinateMany> pretreatment(IntegerCoordinateMany v);

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
    public abstract DoubleVector pretreatment(DoubleVector doubleVector);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param integerVector 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                      <p>
     *                      The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract IntegerVector pretreatment(IntegerVector integerVector);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param doubleMatrix 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                     <p>
     *                     The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract DoubleMatrix pretreatment(DoubleMatrix doubleMatrix);

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param integerMatrix 需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                      <p>
     *                      The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public abstract IntegerMatrix pretreatment(IntegerMatrix integerMatrix);

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
