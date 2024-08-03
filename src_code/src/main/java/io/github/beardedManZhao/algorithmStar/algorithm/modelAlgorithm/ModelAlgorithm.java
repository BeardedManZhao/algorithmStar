package io.github.beardedManZhao.algorithmStar.algorithm.modelAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模型推测计算组件，每一个模型推测计算组件都应实现与本抽象，抽象中包含了最基本的函数，其函数计算逻辑等待实现。
 */
public abstract class ModelAlgorithm implements OperationAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected ModelAlgorithm(String AlgorithmName) {
        this.logger = LoggerFactory.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
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
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex   目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                      <p>
     *                      Target value index, the column marked by this index will be the target value of this calculation!
     * @param integerMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                      <p>
     *                      An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public abstract double[] modelInference(int targetIndex, IntegerMatrix integerMatrix);

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex  目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                     <p>
     *                     Target value index, the column marked by this index will be the target value of this calculation!
     * @param doubleMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                     <p>
     *                     An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public abstract double[] modelInference(int targetIndex, DoubleMatrix doubleMatrix);

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex 目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                    <p>
     *                    Target value index, the column marked by this index will be the target value of this calculation!
     * @param ints        需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                    <p>
     *                    An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public double[] modelInference(int targetIndex, int[][] ints) {
        return modelInference(targetIndex, IntegerMatrix.parse(ints));
    }

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex 目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                    <p>
     *                    Target value index, the column marked by this index will be the target value of this calculation!
     * @param doubles     需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                    <p>
     *                    An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public double[] modelInference(int targetIndex, double[][] doubles) {
        return modelInference(targetIndex, DoubleMatrix.parse(doubles));
    }
}
