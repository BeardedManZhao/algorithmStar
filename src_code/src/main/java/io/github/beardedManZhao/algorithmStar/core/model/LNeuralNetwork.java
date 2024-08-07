package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import org.jetbrains.annotations.NotNull;

/**
 * 线性神经网络学习模型，该模型针对大量数据样本能够自动的生成神经网络进行批量学习操作。最终会返回一个学习之后的数学模型。
 * <p>
 * A linear neural network learning model that can automatically generate neural networks for batch learning operations on a large number of data samples. Ultimately, a learned mathematical model will be returned.
 *
 * @author 赵凌宇
 * 2023/4/20 12:40
 */
public class LNeuralNetwork implements ASModel<Integer, DoubleVector, NumberModel> {
    public final static int TARGET = 0;
    public final static int LEARNING_RATE = 1;
    public final static int LEARN_COUNT = 2;
    public final static int PERCEPTRON = 3;
    public final static int LOSS_PROPORTION = 4;

    // 设置 Y 数值
    protected double[] Y;
    // 设置学习率
    protected double learningRate = 0.5;
    // 设置学习次数
    protected int learnCount = 100;
    // 设置感知机神经元
    protected Perceptron perceptron = null;
    // 设置损失比重
    protected int lossProportion = 10;

    /**
     * 针对模型进行设置。
     * <p>
     * Set up for the model.
     *
     * @param key   模型中配置的项目编号，一般情况下在实现类中都有提供静态参数编号。
     *              <p>
     *              The project number configured in the model is usually provided with a static parameter number in the implementation class.
     * @param value 模型中配置项的具体数据，其可以是任何类型的单元格对象。
     */
    @Override
    public void setArg(Integer key, @NotNull Cell<?> value) {
        switch (key) {
            case TARGET:
                Object value2 = value.getValue();
                if (value2 instanceof double[]) {
                    this.Y = (double[]) value2;
                } else {
                    throw ASModel.Utils.throwArgTypeERR("TARGET", "Cell<double[]>");
                }
                break;
            case LEARNING_RATE:
                this.learningRate = value.getDoubleValue() / 10;
                break;
            case LEARN_COUNT:
                this.learnCount = value.getIntValue();
                break;
            case PERCEPTRON:
                Object value1 = value.getValue();
                if (value1 instanceof Perceptron) {
                    this.perceptron = (Perceptron) value1;
                    break;
                } else {
                    throw ASModel.Utils.throwArgTypeERR("PERCEPTRON", "Cell<zhao.algorithmMagic.core.model.Perceptron>");
                }
            case LOSS_PROPORTION:
                this.lossProportion = value.getIntValue();
                break;
        }
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
    public NumberModel function(DoubleVector... input) {
        return function(TaskConsumer.VOID, input);
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
     * @param weight   初始权重数组
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    public NumberModel function(TaskConsumer consumer, DoubleVector[] input, DoubleVector weight) {
        if (this.perceptron == null) {
            throw new OperatorOperationException("请设置一个神经元。");
        }
        double[] W = weight.toArray();
        // 获取到神经网络层
        ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer();
        // 添加神经元
        this.perceptron.FUNCTION.setLearnR(this.learningRate);
        listNeuralNetworkLayer.addPerceptron(this.perceptron);
        // 构建偏置项
        double bias = 0;
        // 获取到每一组数据进行训练
        for (int i1 = 0, max = input.length - 1; i1 < max; i1++) {
            DoubleVector XX = input[i1];
            double YY = Y[i1];
            // 开始进行神经网络训练
            for (int i = 0; i < this.learnCount; i++) {
                // 将 X 进行前向传播，获取到临时 Y 向量
                DoubleVector tempY = listNeuralNetworkLayer.forward(XX);
                // 将 临时结果与真实结果之间的损失函数计算出来
                double loss = tempY.toArray()[0] - YY;
                // 根据 损失函数 反向求偏导，获取梯度数值
                DoubleVector g = listNeuralNetworkLayer.backForward(DoubleVector.parse(loss));
                double gs = g.toArray()[0], v = this.learningRate * gs;
                consumer.accept(loss, gs, W);
                // 更新参数
                int wi = -1;
                for (double w : W) {
                    W[++wi] = w - v;
                }
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
    public NumberModel function(TaskConsumer consumer, DoubleVector... input) {
        if (this.perceptron == null) {
            throw new OperatorOperationException("请设置一个神经元。");
        }
        // 构建权重
        DoubleVector doubleVector = input[input.length - 1];
        double[] W = doubleVector.toArray();
        // 获取到神经网络层
        ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer();
        // 添加神经元
        this.perceptron.FUNCTION.setLearnR(this.learningRate);
        listNeuralNetworkLayer.addPerceptron(this.perceptron);
        // 构建偏置项
        double bias = 0;
        // 获取到每一组数据进行训练
        for (int i1 = 0, max = input.length - 1; i1 < max; i1++) {
            DoubleVector XX = input[i1];
            double YY = Y[i1];
            // 开始进行神经网络训练
            for (int i = 0; i < this.learnCount; i++) {
                // 将 X 进行前向传播，获取到临时 Y 向量
                DoubleVector tempY = listNeuralNetworkLayer.forward(XX);
                // 将 临时结果与真实结果之间的损失函数计算出来
                double tempYNum = tempY.toArray()[0];
                double loss = tempYNum - YY;
                // 根据 损失函数 反向求偏导，获取梯度数值
                DoubleVector g = listNeuralNetworkLayer.backForward(DoubleVector.parse(loss));
                double gs = g.toArray()[0];
                consumer.accept(loss, gs, W);
                double v = this.learningRate * gs;
                // 更新参数
                int wi = -1;
                for (double w : W) {
                    W[++wi] = w - v;
                }
            }
        }
        // 生成训练好的模型
        return getNumberModel(input[0].toArray().length, W, bias);
    }

    /**
     * 生成数学模型
     *
     * @param length 自变量的元素数量
     * @param W      自变量权重组
     * @param bias   模型偏置数值
     * @return 生成的数学模型
     */
    @NotNull
    protected final NumberModel getNumberModel(int length, double[] W, double bias) {
        return new NumberModel(bias) {

            final DoubleVector wv = DoubleVector.parse(W);

            /**
             * @return 当前模型接收的参数的维度，需要注意的是，接收的参数必须要与当前参数一致。
             */
            @Override
            public int getDimension() {
                return length;
            }

            @Override
            public Double function(Double... input) {
                this.checkDimension(input);
                return wv.innerProduct(DoubleVector.parse(input)) + biasNum;
            }

            @Override
            public Double functionConcurrency(Double... input) {
                throw new UnsupportedOperationException("The mathematical calculation model currently does not support on-site operations");
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder(wv.getNumberOfDimensions() << 1);
                stringBuilder.append("f(x) = ");
                int index = 0;
                for (double v : wv.toArray()) {
                    stringBuilder.append("x")
                            .append(++index)
                            .append(" × ")
                            .append(v)
                            .append(" + ");
                }
                stringBuilder.append(bias);
                return stringBuilder.toString();
            }
        };
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

    /**
     * 训练过程附加任务。
     */
    public interface TaskConsumer {

        TaskConsumer VOID = VoidTask.VOID_TASK;

        /**
         * 任务处理逻辑
         *
         * @param loss   本次调整之后的损失函数。
         *               <p>
         *               The loss function after this adjustment.
         * @param g      本次调整之后的梯度。
         *               <p>
         *               The gradient after this adjustment.
         * @param weight 本次调整之后的权重数值。
         *               <p>
         *               The weight value after this adjustment.
         */
        void accept(double loss, double g, double[] weight);
    }
}