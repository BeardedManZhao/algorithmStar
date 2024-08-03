package io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有能够处理字符串数组的特征提取计算组件对象的共同抽象类
 * <p>
 * All common abstract classes that can handle the feature extraction of string arrays and calculate component objects
 *
 * @param <returnType> 计算组件在计算之后的返回值对象数据类型
 *                     <p>
 *                     Calculate the return value object data type of the component after calculation
 * @author zhao
 */
public abstract class StringArrayFeature<returnType> implements FeatureExtractionAlgorithm<returnType, String[]> {

    protected final Logger logger;
    protected final String AlgorithmName;

    protected StringArrayFeature() {
        this.AlgorithmName = "StringArrayFeature";
        this.logger = LoggerFactory.getLogger("StringArrayFeature");
    }

    protected StringArrayFeature(String AlgorithmName) {
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
}
