package io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.DistanceAlgorithm;
import io.github.beardedManZhao.algorithmStar.utils.DependentAlgorithmNameLibrary;

/**
 * 通过距离算法实现分类的算法抽象，实现于该类将能够支持通过距离进行分类的方式。
 * <p>
 * The over-distance algorithm realizes the algorithm abstraction of classification. The implementation of this class will be able to support the way of classification by distance.
 *
 * @author zhao
 */
public abstract class DistanceClassification implements ClassificationAlgorithm {
    protected final Logger logger;
    private final String Name;
    DistanceAlgorithm distanceAlgorithm = DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC;

    protected DistanceClassification(String name) {
        Name = name;
        logger = LoggerFactory.getLogger(name);
    }

    /**
     * 设置本组件中的分类算法使用哪个组件
     * <p>
     * Set which component is used by the classification algorithm in this component
     *
     * @param distanceAlgorithm 本组件进行分类时，要使用的分类算法组件。
     *                          <p>
     *                          The classification algorithm component to be used when classifying this component.
     */
    public final void setDistanceAlgorithm(DistanceAlgorithm distanceAlgorithm) {
        this.distanceAlgorithm = distanceAlgorithm;
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public final String getAlgorithmName() {
        return this.Name;
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
    public final boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当前的分类中距离计算组件
     *
     * @return 距离计算组件对象
     */
    public final DistanceAlgorithm distanceAlgorithm() {
        return distanceAlgorithm;
    }
}
