package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.util.Random;

/**
 * 线性随机神经网络训练模型，在该模型进行训练的时候，训练次数将不会与数据样本数量进行乘积计算，而是随机从所有的数据中随机进行提取，训练次数与设置的次数一致，其损失了精度，提高了数据样本的兼容性与性能。
 * <p>
 * For the linear stochastic neural network training model, when the model is trained, the number of training times will not be multiplied with the number of data samples, but will be randomly extracted from all data. The number of training times is consistent with the set number, which has lost precision and improved the compatibility and performance of data samples.
 *
 * @author zhao
 */
public class LSNeuralNetwork extends LNeuralNetwork {

    private final Random random = new Random();

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public NumberModel function(DoubleVector... input) {
        // 构建偏置项与最大索引数值
        double bias = 0;
        int maxIndex = input.length - 1;
        // 构建权重
        DoubleVector doubleVector = input[maxIndex];
        double[] W = doubleVector.toArray();
        // 获取到神经网络层
        ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer();
        // 添加神经元
        this.perceptron.FUNCTION.setLearnR(this.learningRate);
        listNeuralNetworkLayer.addPerceptron(this.perceptron);
        // 开始训练
        for (int i = 0; i < this.learnCount; i++) {
            // 生成当前索引
            int index = random.nextInt(maxIndex);
            // 将 X 进行前向传播，获取到临时 Y 向量
            DoubleVector tempY = listNeuralNetworkLayer.forward(input[index]);
            // 将 临时结果与真实结果之间的损失函数计算出来
            double tempYNum = tempY.toArray()[0];
            double loss = tempYNum - this.Y[index];
            // 根据 损失函数 反向求偏导，获取梯度数值
            double v = this.learningRate * (listNeuralNetworkLayer.backForward(DoubleVector.parse(tempYNum)).toArray()[0] * (loss * this.learningRate));
            // 更新参数
            int wi = -1;
            for (double w : W) {
                W[++wi] = w - v;
            }
        }
        // 生成训练好的模型
        return getNumberModel(input[0].toArray().length, W, bias);
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param consumer 运行时附加任务处理器，其中的key是损失函数，value是训练出来的权重。
     * @param input    需要被计算的所有操作数对象。
     *                 <p>
     *                 All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public NumberModel function(TaskConsumer consumer, DoubleVector... input) {
        // 构建偏置项与最大索引数值
        double bias = 0;
        int maxIndex = input.length - 1;
        // 构建权重
        DoubleVector doubleVector = input[maxIndex];
        double[] W = doubleVector.toArray();
        // 获取到神经网络层
        final ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer();
        // 设置激活函数以及添加神经元
        this.perceptron.FUNCTION.setLearnR(this.learningRate);
        listNeuralNetworkLayer.addPerceptron(this.perceptron);
        // 开始训练
        for (int i = 0; i < this.learnCount; i++) {
            // 生成当前索引
            int index = random.nextInt(maxIndex);
            // 将 X 进行前向传播，获取到临时 Y 向量
            DoubleVector tempY = listNeuralNetworkLayer.forward(input[index]);
            // 将 临时结果与真实结果之间的损失函数计算出来
            double tempYNum = tempY.toArray()[0];
            double loss = tempYNum - this.Y[index];
            // 根据 损失函数 反向求偏导，获取梯度数值
            double gs = listNeuralNetworkLayer.backForward(DoubleVector.parse(loss)).toArray()[0];
            double v = this.learningRate * (gs * (loss * this.learningRate));
            consumer.accept(loss, gs, W);
            // 更新参数
            int wi = -1;
            for (double w : W) {
                W[++wi] = w - v;
            }
        }
        // 生成训练好的模型
        return getNumberModel(input[0].toArray().length, W, bias);
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public NumberModel functionConcurrency(DoubleVector[] input) {
        return this.function(input);
    }
}
