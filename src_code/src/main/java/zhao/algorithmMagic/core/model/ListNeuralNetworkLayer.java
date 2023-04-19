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
public class ListNeuralNetworkLayer implements NeuralNetworkLayer {

    private final ArrayList<Perceptron> arrayList = new ArrayList<>();
    private final DoubleVector weight;

    /**
     * 构造出当前神经网络层。
     *
     * @param weight 当前神经网络层的初始权重对象。
     */
    public ListNeuralNetworkLayer(DoubleVector weight) {
        this.weight = weight;
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
        if (perceptron != null) {
            this.arrayList.add(perceptron);
        }
    }

    /**
     * 将当前的神经网络层进行前向传播计算，获取到当前神经网络的输出向量。
     * <p>
     * Perform forward propagation calculation on the current neural network layer to obtain the output vector of the current neural network.
     *
     * @param doubleVector@return 计算出来的当前神经网络层前向传播的结果向量。
     *                            <p>
     *                            The calculated result vector of the forward propagation of the current neural network layer.
     */
    @Override
    public DoubleVector forward(DoubleVector doubleVector) {
        // 首先计算出来内积数值。
        double inner = doubleVector.innerProduct(weight);
        // 构建结果向量容器 容器中的元素数量与神经元个数一致
        double[] res = new double[this.arrayList.size()];
        int index = -1;
        for (Perceptron perceptron : this.arrayList) {
            res[++index] = perceptron.function(inner).getValue();
        }
        return DoubleVector.parse(res);
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
        // 首先计算出来内积数值。
        double inner = doubleVector.innerProduct(weight);
        // 构建结果向量容器 容器中的元素数量与神经元个数一致
        double[] res = new double[this.arrayList.size()];
        int index = -1;
        for (Perceptron perceptron : this.arrayList) {
            res[++index] = perceptron.FUNCTION.derivativeFunction(inner);
        }
        return DoubleVector.parse(res);
    }
}
