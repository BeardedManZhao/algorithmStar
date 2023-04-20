package zhao.algorithmMagic.core.model;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.operands.table.Cell;

/**
 * 数学计算模型抽象，该模型通常是一个训练好的模型，用于深度学习中的结果封装，通过神经网络可训练出该类的实现。
 * <p>
 * The mathematical calculation model is abstract, which is usually a trained model used for encapsulating results in deep learning. The implementation of this class can be trained through neural networks.
 *
 * @author 赵凌宇
 * 2023/4/20 13:05
 */
public abstract class NumberModel implements ASModel<Integer, Double, Double> {
    /**
     * 手动指定偏置项数值的 key
     */
    public final static int BIAS = 0;

    protected double biasNum;

    public NumberModel(double biasNum) {
        this.biasNum = biasNum;
    }

    /**
     * @return 当前模型接收的参数的维度，需要注意的是，接收的参数必须要与当前参数一致。
     */
    public abstract int getDimension();

    /**
     * 检查维度数量是否一致
     *
     * @param doubles 需要被检查的参数
     */
    public void checkDimension(Double[] doubles) {
        int okDimension = getDimension();
        if (doubles.length != okDimension) {
            throw new UnsupportedOperationException(
                    "The current model does not support the calculation of the [" + doubles.length + "] dimension, only the calculation of the [" + okDimension + "] dimension is supported"
            );
        }
    }

    @Override
    public void setArg(Integer key, @NotNull Cell<?> value) {
        if (key == BIAS) biasNum = value.getIntValue();
    }
}
