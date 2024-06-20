package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;

import java.io.Serializable;

/**
 * 神经网络层类，该类中包含一层神经网络的所有感知机，同时支持当前神经网络层的结果向量获取核反向传播。
 * <p>
 * Neural network layer class, which includes all the perceptron of a layer of neural network, and supports the backpropagation of the result vector acquisition core of the current neural network layer.
 *
 * @author 赵凌宇
 * 2023/4/19 18:25
 */
public interface NeuralNetworkLayer extends Serializable {

    /**
     * 向当前神经网络层中添加一个感知机神经元对象。
     * <p>
     * Add a perceptron neuron object to the current neural network layer.
     *
     * @param perceptron 需要被添加的感知机神经元对象。
     *                   <p>
     *                   The perceptron neuron object to be added.
     */
    void addPerceptron(Perceptron perceptron);

    /**
     * 将当前的神经网络层进行前向传播计算，获取到当前神经网络的输出向量。
     * <p>
     * Perform forward propagation calculation on the current neural network layer to obtain the output vector of the current neural network.
     *
     * @param doubleVector 当前神经网络层的输入数据操作数对象。
     *                     <p>
     *                     The input data operand object of the current neural network layer.
     * @return 计算出来的当前神经网络层前向传播的结果向量。
     * <p>
     * The calculated result vector of the forward propagation of the current neural network layer.
     */
    DoubleVector forward(DoubleVector doubleVector);

    /**
     * 将当前的神经网络层进行反向求导计算，获取当前神经网络反向输出向量。
     * <p>
     * Perform reverse differentiation calculation on the current neural network layer to obtain the reverse output vector of the current neural network.
     *
     * @param doubleVector 当前神经网络层的反向传播数据输入操作数对象。
     *                     <p>
     *                     The backpropagation data input operand object of the current neural network layer.
     * @return 计算出来的当前神经网络层反向传播的导数集。
     * <p>
     * The calculated derivative set of the current neural network layer backpropagation.
     */
    DoubleVector backForward(DoubleVector doubleVector);
}
