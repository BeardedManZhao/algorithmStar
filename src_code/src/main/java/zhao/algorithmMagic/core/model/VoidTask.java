package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.util.List;

/**
 * 空附加任务，在该任务中，可以有效避免附加任务的执行，其能够被提供给所有神经网络训练模型。
 *
 * @author 赵凌宇
 * 2023/4/27 20:30
 */
public class VoidTask implements SingleLayerCNNModel.TaskConsumer, LNeuralNetwork.TaskConsumer {

    public final static VoidTask VOID_TASK = new VoidTask();

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
    @Override
    public void accept(double loss, double g, double[] weight) {

    }

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
    @Override
    public void accept(double loss, double[] g, List<KeyValue<String, DoubleVector>> weight) {

    }
}
