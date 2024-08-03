package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.block.DoubleMatrixSpace;
import io.github.beardedManZhao.algorithmStar.operands.matrix.block.IntegerMatrixSpace;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 卷积神经网络计算模型，该模型能够接收很多图像矩阵，并对其进行卷积与分类操作，实现对图的分类操作。
 *
 * @author 赵凌宇
 * 2023/4/25 11:21
 */
public final class SingleLayerCNNModel implements ASModel<Integer, IntegerMatrixSpace, ClassificationModel<IntegerMatrixSpace>> {

    public final static int COLOR_CHANNEL = 2;
    public final static int Activation_Function = 3;
    public final static int KERNEL = 4;
    public final static int TRANSFORMATION = 5;
    public final static int LEARN_COUNT = 6;
    public final static int LEARNING_RATE = 7;
    private static final long serialVersionUID = -8785883488441048941L;

    // 准备随机数对象
    private final Random random = new Random();
    // 设置权重
    private final ArrayList<KeyValue<String, DoubleVector>> W1 = new ArrayList<>();
    // 准备损失函数计算组件
    LossFunction lossFunction = LossFunction.MSE;
    // 卷积核的宽高 样本的宽高 以及 需要被提取的颜色通道
    private int kw = 0, kh = 0, ww = -1, wh = -1, colorChannel = ColorMatrix._G_;
    // 训练次数 与 学习率
    private int learnCount = 100;
    private float learningRate = 0.2f;
    // 神经元的激活函数
    private ActivationFunction activationFunction = ActivationFunction.RELU;
    // 卷积核
    private DoubleMatrixSpace kernel;
    // 图像卷积与进入神经网络之间的附加中间计算任务逻辑实现，其中的参数是图像的卷积结果，一般情况下，这里是池化或二值化的操作。
    private Transformation<ColorMatrix, ColorMatrix> transformation = colorMatrix -> colorMatrix;
    // 设置所有类别的目标数值，用于计算损失
    private DoubleVector[] target;

    @NotNull
    private static ClassificationModel<IntegerMatrixSpace> getModel(ListNeuralNetworkLayer perceptron, Transformation<ColorMatrix, ColorMatrix> transformation, int kw, int kh, int ww, int wh, int colorChannel, DoubleMatrixSpace kernel) {
        return new ClassificationModel<IntegerMatrixSpace>() {

            final ListNeuralNetworkLayer lnn = perceptron;
            final Transformation<ColorMatrix, ColorMatrix> tf = transformation;
            // 类别
            final String[] names = new String[lnn.size()];
            // 卷积核的宽高 样本的宽高 以及 需要被提取的颜色通道
            private final int kw1 = kw, kh1 = kh, ww1 = ww, wh1 = wh, cc = colorChannel;
            // 卷积核
            private final DoubleMatrixSpace kernel1 = kernel;

            {
                int index = -1;
                // 先构造对应的类别名称
                for (Perceptron perceptron : lnn) {
                    names[++index] = perceptron.getName();
                }
            }

            /**
             * 针对模型进行设置。
             * <p>
             * Set up for the model.
             *
             * @param key   模型中配置的项目编号，一般情况下在实现类中都有提供静态参数编号。
             *              <p>
             *              The project number configured in the model is usually provided with a static parameter number in the implementation class.
             * @param value 模型中配置项的具体数据，其可以是任何类型的单元格对象。
             *              <p>
             */
            @Override
            public void setArg(Integer key, @NotNull Cell<?> value) {

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
            public KeyValue<String[], DoubleVector[]> function(IntegerMatrixSpace... input) {
                DoubleVector[] X = new DoubleVector[input.length];
                int index = -1;
                for (IntegerMatrixSpace integerMatrices : input) {
                    if (integerMatrices.getColCount() == this.ww1 && integerMatrices.getRowCount() == this.wh1) {
                        X[++index] = lnn.forward(
                                DoubleVector.parse(
                                        this.tf.function(
                                                integerMatrices.foldingAndSumRGB(this.kw1, this.kh1, this.kernel1)
                                        ).getChannel(this.cc).flatten()
                                )
                        );
                    } else {
                        throw new OperatorOperationException("The image matrix you provided cannot be used for the current model. The current model supports w * h = [" + this.ww1 + " * " + this.wh1 + "]");
                    }
                }
                return new KeyValue<>(this.names, X);
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
            public KeyValue<String[], DoubleVector[]> functionConcurrency(IntegerMatrixSpace... input) {
                DoubleVector[] X = new DoubleVector[input.length];
                final int[] index = {-1};
                CountDownLatch countDownLatch = new CountDownLatch(input.length);
                for (IntegerMatrixSpace integerMatrices : input) {
                    if (integerMatrices.getColCount() == this.ww1 && integerMatrices.getRowCount() == this.wh1) {
                        new Thread(() -> {
                            X[++index[0]] = lnn.forward(
                                    DoubleVector.parse(
                                            this.tf.function(
                                                    integerMatrices.foldingAndSumRGB(this.kw1, this.kh1, this.kernel1)
                                            ).getChannel(this.cc).flatten()
                                    )
                            );
                            countDownLatch.countDown();
                        }).start();
                    } else {
                        throw new OperatorOperationException("The image matrix you provided cannot be used for the current model. The current model supports w * h = [" + this.ww1 + " * " + this.wh1 + "]");
                    }
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return new KeyValue<>(this.names, X);
            }
        };
    }

    public void setLossFunction(LossFunction lossFunction) {
        this.lossFunction = lossFunction;
    }

    /**
     * 为当前卷积神经网络模型进行一个卷积核的设置。
     *
     * @param kernel 需要使用的卷积核对象，这是一个矩阵空间对象，其中分别包含 R G B 三个通道的颜色矩阵，为三个矩阵叠加而成。
     */
    public void setKernel(DoubleMatrixSpace kernel) {
        this.kernel = kernel;
        this.kh = kernel.getRowCount();
        this.kw = kernel.getColCount();
    }

    /**
     * 设置卷积神经网络计算的时候需要计算的颜色通道。
     *
     * @param colorChannel 目标颜色通道编号。
     */
    public void setColorChannel(int colorChannel) {
        this.colorChannel = colorChannel;
    }

    /**
     * 设置卷积神经网络计算的时候需要使用的激活函数。
     *
     * @param activationFunction 激活函数实现类对象。
     */
    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    /**
     * @param transformation 设置 CNN 过程中，卷积结束之后的附加任务，该任务将会提供给所有的样本与目标数据使用，如果有使用附加任务的需求，请先调用此函数。
     *                       <p>
     *                       During the CNN setup process, the additional task after the convolution is completed will be provided to all samples and target data for use. If there is a need to use the additional task, please call this function first.
     */
    public void setTransformation(Transformation<ColorMatrix, ColorMatrix> transformation) {
        this.transformation = transformation;
    }

    /**
     * 设置学习训练次数
     *
     * @param learnCount 该参数将代表模型训练次数。
     */
    public void setLearnCount(int learnCount) {
        this.learnCount = learnCount;
    }

    /**
     * 设置目标数据与其对应的初始权重，权重将会被不断的调整，最终使得结果更加贴近目标数据。
     *
     * @param target 目标数据向量组，其中每一个向量对应一个权重数据
     * @param w1     权重数据组，其中每一个权重对应一个目标数据组
     */
    public void setWeight(DoubleVector[] target, List<KeyValue<String, IntegerMatrixSpace>> w1) {
        if (target.length == w1.size()) {
            this.W1.clear();
            int w = -1, h = -1;
            for (KeyValue<String, IntegerMatrixSpace> kv : w1) {
                IntegerMatrixSpace value = kv.getValue();
                if (w == -1) {
                    w = value.getColCount();
                    h = value.getRowCount();
                } else if (w != value.getColCount() || h != value.getRowCount()) {
                    throw new OperatorOperationException("Please provide ya with consistent length and width.");
                }
                this.W1.add(new KeyValue<>(kv.getKey(), DoubleVector.parse(
                                this.transformation.function(
                                        value.foldingAndSumRGB(this.kw, this.kh, this.kernel)
                                ).getChannel(this.colorChannel).flatten())
                        )
                );
            }
            this.ww = w;
            this.wh = h;
            this.target = target;
        } else {
            throw new OperatorOperationException("You should ensure that the quantity of target data is consistent with the weight data.");
        }
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }

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
            case COLOR_CHANNEL:
                this.setColorChannel(value.getIntValue());
                break;
            case Activation_Function:
                if (value.getValue() instanceof String) {
                    this.setActivationFunction(ActivationFunction.valueOf(value.getStringValue()));
                } else if (value.getValue() instanceof ActivationFunction) {
                    this.setActivationFunction((ActivationFunction) value.getValue());
                } else
                    throw new OperatorOperationException("setActivationFunction((ActivationFunction or String) value.getValue()) error !!!");
                break;
            case KERNEL:
                this.setKernel((DoubleMatrixSpace) value.getValue());
                break;
            case TRANSFORMATION:
                this.setTransformation(ASClass.transform(value.getValue()));
                break;
            case LEARN_COUNT:
                this.setLearnCount(value.getIntValue());
            case LEARNING_RATE:
                this.setLearningRate((float) value.getDoubleValue());
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
    public ClassificationModel<IntegerMatrixSpace> function(IntegerMatrixSpace... input) {
        return function(SingleLayerCNNModel.TaskConsumer.VOID, input);
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
    public ClassificationModel<IntegerMatrixSpace> function(SingleLayerCNNModel.TaskConsumer consumer, IntegerMatrixSpace... input) {
        // 将输入的图像进行卷积和附加任务的处理
        // 构建 X 向量 是等待分类的训练向量
        DoubleVector[] X1 = new DoubleVector[input.length];
        int index1 = -1;
        for (IntegerMatrixSpace integerMatrices : input) {
            X1[++index1] = DoubleVector.parse(
                    this.transformation.function(
                            integerMatrices.foldingAndSumRGB(this.kw, this.kh, this.kernel)
                    ).getChannel(this.colorChannel).flatten()
            );
        }
        ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer();
        // 将所有的初始权重添加到神经元中
        for (KeyValue<String, DoubleVector> doubleVector : W1) {
            listNeuralNetworkLayer.addPerceptron(Perceptron.parse(doubleVector.getKey(), this.activationFunction, doubleVector.getValue()));
        }
        // 开始训练
        for (int i = 0; i < this.learnCount; i++) {
            // 获取到当前的样本与目标编号 并获取到对应的权重和样本集
            int id = random.nextInt(index1);
            // 开始前向传播
            DoubleVector forward = listNeuralNetworkLayer.forward(X1[id]);
            // 开始计算损失
            double loss = lossFunction.function(forward.toArray(), target[id].toArray());
            // 开始反向传播
            DoubleVector doubleVector = listNeuralNetworkLayer.backForward(DoubleVector.parse(loss));
            double[] doubles1 = doubleVector.toArray();
            consumer.accept(loss, doubles1, W1);
            // 开始计算梯度
            int index2 = -1;
            for (double v : doubles1) {
                // 调整当前神经元的权重
                double gs = learningRate * v;
                double[] doubles = W1.get(++index2).getValue().toArray();
                for (int i1 = 0; i1 < doubles.length; i1++) {
                    doubles[i1] -= gs;
                }
            }
        }
        // 生成模型
        return getModel(listNeuralNetworkLayer, transformation, kw, kh, ww, wh, colorChannel, kernel);
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
    public ClassificationModel<IntegerMatrixSpace> functionConcurrency(IntegerMatrixSpace[] input) {
        return this.function(input);
    }


    /**
     * 训练过程附加任务。
     */
    public interface TaskConsumer extends Serializable {

        SingleLayerCNNModel.TaskConsumer VOID = VoidTask.VOID_TASK;

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
         */
        void accept(double loss, double[] g, List<KeyValue<String, DoubleVector>> weight);
    }

}
