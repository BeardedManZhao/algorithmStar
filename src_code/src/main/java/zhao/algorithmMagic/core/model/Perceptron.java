package zhao.algorithmMagic.core.model;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;

/**
 * 感知机类，该类实现了AS模型类，其由线性函数与激活函数组成，其接收线性函数中的权重矩阵与数据矩阵对象。
 * <p>
 * Perceptron class, which implements the AS model class, consists of linear function and activation function, and receives the weight matrix and data matrix objects in the linear function.
 *
 * @author 赵凌宇
 * 2023/4/19 13:59
 */
public final class Perceptron implements ASModel<Integer, DoubleMatrix, Cell<Double>> {

    public static final int BIAS = 1;
    final ActivationFunction FUNCTION;
    private final String name;
    private final DoubleVector weight;
    double biasNum = 0;

    Perceptron(String name, ActivationFunction function, DoubleVector weight) {
        this.name = name;
        FUNCTION = function;
        this.weight = weight;
    }

    /**
     * 提供一个激活函数，生成对应的感知机神经元对象。
     *
     * @param FUNCTION 激活函数实现对象
     * @param weight   当前神经元感知机中的权重向量
     * @return 感知机
     */
    public static Perceptron parse(ActivationFunction FUNCTION, int[] weight) {
        return new Perceptron(FUNCTION.name(), FUNCTION, DoubleVector.parse(ASClass.IntArray_To_DoubleArray(weight)));
    }

    /**
     * 提供一个激活函数，生成对应的感知机神经元对象。
     *
     * @param FUNCTION 激活函数实现对象
     * @param weight   当前神经元感知机中的权重向量
     * @return 感知机
     */
    public static Perceptron parse(String name, ActivationFunction FUNCTION, int[] weight) {
        return new Perceptron(name, FUNCTION, DoubleVector.parse(ASClass.IntArray_To_DoubleArray(weight)));
    }

    /**
     * 提供一个激活函数，生成对应的感知机神经元对象。
     *
     * @param FUNCTION 激活函数实现对象
     * @param weight   当前神经元感知机中的权重向量
     * @return 感知机
     */
    public static Perceptron parse(String name, ActivationFunction FUNCTION, double[] weight) {
        return new Perceptron(name, FUNCTION, DoubleVector.parse(weight));
    }

    /**
     * 提供一个激活函数，生成对应的感知机神经元对象。
     *
     * @param FUNCTION 激活函数实现对象
     * @param weight   当前神经元感知机中的权重向量
     * @return 感知机
     */
    public static Perceptron parse(ActivationFunction FUNCTION, DoubleVector weight) {
        return new Perceptron(FUNCTION.name(), FUNCTION, weight);
    }

    /**
     * 提供一个激活函数，生成对应的感知机神经元对象。
     *
     * @param FUNCTION 激活函数实现对象
     * @param weight   当前神经元感知机中的权重向量
     * @return 感知机
     */
    public static Perceptron parse(String name, ActivationFunction FUNCTION, DoubleVector weight) {
        return new Perceptron(name, FUNCTION, weight);
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
        if (key == BIAS) {
            if (value.isNumber()) {
                biasNum = value.getDoubleValue();
            }
        }
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象，这里是一个需要被计算的矩阵以及一个权重矩阵。
     *              <p>
     *              All operand objects that need to be calculated, here is a matrix that needs to be calculated and a weight matrix.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public Cell<Double> function(DoubleMatrix[] input) {
        return this.function(this.FUNCTION.function(input[0].innerProduct(input[1])) + biasNum);
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象，这里是一个来自与上一层网络的向量对象。
     *              <p>
     *              All operand objects that need to be calculated, here is a vector object from the previous layer of the network.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    public Cell<Double> function(DoubleVector input) {
        this.FUNCTION.setYVector(input.toArray());
        return this.returnCell(this.FUNCTION.function(input.innerProduct(weight) + biasNum));
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象，这里是一个来自与上一层网络的向量对象。
     *              <p>
     *              All operand objects that need to be calculated, here is a vector object from the previous layer of the network.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    public Cell<Double> function(IntegerVector input) {
        return this.returnCell(this.FUNCTION.function(input.innerProduct(weight) + biasNum));
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象，这里是一个需要被计算的矩阵以及一个权重矩阵。
     *              <p>
     *              All operand objects that need to be calculated, here is a matrix that needs to be calculated and a weight matrix.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public Cell<Double> functionConcurrency(DoubleMatrix[] input) {
        return this.function(input);
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param inner 计算出来的矩阵内积，该数值将会被重复使用。
     *              <p>
     *              The calculated inner product of the matrix will be reused.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    public Cell<Double> function(double inner) {
        return returnCell(this.FUNCTION.function(inner + biasNum));
    }

    /**
     * 使用当前感知机对象进行反向计算，获取到其反向结果。
     * <p>
     * Use the current perceptron object for reverse calculation to obtain its reverse result.
     *
     * @param x 需要被反向计算的数值，function 函数的结果值。
     *          <p>
     *          The value of x that needs to be calculated in reverse, the result value of the function function.
     * @return 经过反向传播计算之后的结果数值。
     * <p>
     * The numerical value of the result after backpropagation calculation.
     */
    public Cell<Double> backFunction(double x) {
        double sum = 0;
        double[] doubles = this.weight.toArray();
        for (double v : doubles) {
            sum += v;
        }
        return returnCell(this.FUNCTION.derivativeFunction(x) * (sum / doubles.length));
    }

    /**
     * 将计算好的结果使用 CELL 单元格进行封装。
     *
     * @param value 需要被封装的数值
     * @return 封装好的结果对象。
     */
    private Cell<Double> returnCell(double value) {
        return SingletonCell.$(value);
    }

    /**
     * @return 当前感知机的名称。
     */
    public String getName() {
        return name;
    }
}
