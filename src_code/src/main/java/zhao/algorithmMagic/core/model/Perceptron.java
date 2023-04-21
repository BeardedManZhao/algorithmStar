package zhao.algorithmMagic.core.model;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;

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
    int biasNum = 0;

    Perceptron(ActivationFunction function) {
        FUNCTION = function;
    }

    public static Perceptron parse(ActivationFunction FUNCTION) {
        return new Perceptron(FUNCTION);
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
        if (key == BIAS) {
            if (value.isNumber()) {
                biasNum = value.getIntValue();
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
        return this.function(this.FUNCTION.function(input[0].innerProduct(input[1])));
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
    public Cell<Double> function(DoubleVector[] input) {
        return this.function(this.FUNCTION.function(input[0].innerProduct(input[1])));
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
        return SingletonCell.$(inner + biasNum);
    }
}
