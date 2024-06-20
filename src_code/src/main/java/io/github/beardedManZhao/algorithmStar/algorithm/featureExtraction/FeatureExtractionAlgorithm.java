package io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;

/**
 * 特征提取算法接口抽象类，在其中能够接收数据，并将数据按照指定的方式转换成为不同的特征向量或矩阵。
 * <p>
 * Feature extraction algorithm interface abstract class, in which data can be received and converted into different feature vectors or matrices in a specified way.
 *
 * @param <paramType>  该特征提取组件能够提取的数据所属类型
 * @param <returnType> 该特征提取组件在调用提取函数之后返回的数据所属类型
 * @author zhao
 */
public interface FeatureExtractionAlgorithm<returnType, paramType> extends OperationAlgorithm {

    /**
     * 将很多字符串组合起来进行特征向量的提取，其中的每一个词都是一个特征值。
     * <p>
     * Combine many strings to extract feature vectors, and each word is a feature value.
     *
     * @param data 需要被进行特征提取的数据，数据类型是不一定的，要看实现类的处理方式
     * @return 提取之后的结果对象，类型不确定的，要看实现类的处理方式
     */
    returnType extract(paramType data);

}
