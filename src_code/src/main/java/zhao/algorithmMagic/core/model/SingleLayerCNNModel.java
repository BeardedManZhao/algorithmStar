package zhao.algorithmMagic.core.model;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 卷积神经网络计算模型，该模型能够接收很多图像矩阵，并对其进行卷积与分类操作，实现对图的分类操作。
 *
 * @author 赵凌宇
 * 2023/4/25 11:21
 */
public final class SingleLayerCNNModel extends ListNeuralNetworkLayer implements ASModel<Integer, IntegerMatrixSpace, HashMap<Perceptron, ArrayList<IntegerMatrixSpace>>> {

    public final static int COLOR_CHANNEL = 2, Activation_Function = 3, KERNEL = 4, TRANSFORMATION = 5;


    // 卷积核的宽高 以及 需要被提取的颜色通道
    private int kw = 0, kh = 0, colorChannel = ColorMatrix._G_;

    // 神经元的激活函数
    private ActivationFunction activationFunction = ActivationFunction.RELU;

    // 卷积核
    private DoubleMatrixSpace kernel;

    // 图像卷积与进入神经网络之间的附加中间计算任务逻辑实现，其中的参数是图像的卷积结果，一般情况下，这里是池化或二值化的操作。
    private Transformation<ColorMatrix, ColorMatrix> transformation = colorMatrix -> colorMatrix;

    private static void classification(HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> hashMap, IntegerMatrixSpace integerMatrices, Perceptron perceptron) {
        ArrayList<IntegerMatrixSpace> integerMatrixSpaces = hashMap.get(perceptron);
        if (integerMatrixSpaces != null) {
            integerMatrixSpaces.add(integerMatrices);
        } else {
            ArrayList<IntegerMatrixSpace> objects = new ArrayList<>();
            objects.add(integerMatrices);
            hashMap.put(perceptron, objects);
        }
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

    /***
     * 添加一分类目标，并对其进行名称的设置。
     * @param targetName 当前目标的名称。
     * @param colorMatrix 当前目标对应的图像矩阵空间对象。
     */
    public void addTarget(String targetName, IntegerMatrixSpace colorMatrix) {
        if (this.kernel == null) {
            throw new OperatorOperationException("Please set the convolution kernel first before adding the target.");
        }
        super.addPerceptron(Perceptron.parse(
                        targetName, this.activationFunction,
                        this.transformation.function(colorMatrix.foldingAndSumRGB(this.kw, this.kh, kernel))
                                .getChannel(this.colorChannel)
                                .flatten()
                )
        );
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
    public HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> function(IntegerMatrixSpace... input) {
        HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> hashMap = new HashMap<>();
        for (IntegerMatrixSpace integerMatrices : input) {
            // 将当前的图像进行卷积 然后传递给神经网络计算
            double[] doubles = super.forward(
                    DoubleVector.parse(
                            this.transformation.function(
                                    integerMatrices.foldingAndSumRGB(this.kw, this.kh, kernel)
                            ).getChannel(ColorMatrix._G_).flatten()
                    )
            ).toArray();
            // 最终获取到最大得分值对应的类别，生成结果包裹。
            Perceptron perceptron = this.get(ASMath.findMaxIndex(doubles));
            classification(hashMap, integerMatrices, perceptron);
        }
        return hashMap;
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
    public HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> functionConcurrency(IntegerMatrixSpace... input) {
        HashMap<Perceptron, ArrayList<IntegerMatrixSpace>> hashMap = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(input.length);
        for (IntegerMatrixSpace integerMatrices : input) {
            new Thread(() -> {
                // 将当前的图像进行卷积 然后传递给神经网络计算
                // 最终获取到最大得分值对应的类别，生成结果包裹。
                Perceptron perceptron = this.get(ASMath.findMaxIndex(
                        super.forward(
                                DoubleVector.parse(
                                        this.transformation.function(
                                                integerMatrices.foldingAndSumRGB(this.kw, this.kh, kernel)
                                        ).getChannel(ColorMatrix._G_).flatten()
                                )
                        ).toArray()
                ));
                classification(hashMap, integerMatrices, perceptron);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
