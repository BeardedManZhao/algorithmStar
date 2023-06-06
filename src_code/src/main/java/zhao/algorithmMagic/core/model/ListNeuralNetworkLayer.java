package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.util.ArrayList;

/**
 * 神经网络层类，在该类中能够手动的添加不同的神经元，以及设置参数。
 * <p>
 * Neural network layer class, in which different neurons can be manually added and parameters can be set.
 *
 * @author 赵凌宇
 * 2023/4/19 18:55
 */
public class ListNeuralNetworkLayer extends ArrayList<Perceptron> implements NeuralNetworkLayer {


    /**
     * 构造出当前神经网络层。
     *
     * @param weight 当前神经网络层的初始权重对象。
     * @deprecated 神经网络中已不再管理权重，将权重管理任务交由当前层的所有感知机进行。
     */
    @Deprecated
    public ListNeuralNetworkLayer(DoubleVector... weight) {

    }

    /**
     * 构造出当前神经网络层。
     */
    public ListNeuralNetworkLayer() {
    }

    /**
     * 向当前神经网络层中添加一个感知机神经元对象。
     * <p>
     * Add a perceptron neuron object to the current neural network layer.
     *
     * @param perceptron 需要被添加的感知机神经元对象。
     *                   <p>
     *                   The perceptron neuron object to be added.
     */
    @Override
    public void addPerceptron(Perceptron perceptron) {
        this.add(perceptron);
    }

    /**
     * 将当前的神经网络层进行前向传播计算，获取到当前神经网络的输出向量。
     * <p>
     * Perform forward propagation calculation on the current neural network layer to obtain the output vector of the current neural network.
     *
     * @param doubleVector 需要被计算的向量对象，其将会与当前层的权重向量进行内积计算，并传递给激活函数进行计算。
     *                     <p>
     *                     The vector object to be calculated will be inner product calculated with the weight vector of the current layer and transferred to the activation function for calculation.
     * @return 计算出来的当前神经网络层前向传播的结果向量。
     * <p>
     * The calculated result vector of the forward propagation of the current neural network layer.
     */
    @Override
    public DoubleVector forward(DoubleVector doubleVector) {
        // 计算内积 并传递给激活函数
        double[] inner = new double[this.size()];
        int index = -1;
        for (Perceptron perceptron : this) {
            inner[++index] = perceptron.function(doubleVector).getDoubleValue();
        }
        // 返回结果
        return DoubleVector.parse(inner);
    }

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
    @Override
    public DoubleVector backForward(DoubleVector doubleVector) {
        double[] loss = doubleVector.toArray();
        // 构建结果向量容器 容器中的元素数量与神经元个数一致
        double[] res = new double[this.size()];
        int index = -1;
        for (Perceptron perceptron : this) {
            res[++index] = perceptron.backFunction(loss[0]).getDoubleValue();
        }
        return DoubleVector.parse(res);
    }
}
