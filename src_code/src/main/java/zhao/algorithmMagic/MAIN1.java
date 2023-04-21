package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;
import zhao.algorithmMagic.operands.table.SingletonCell;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {

        // 构建 X 数据
        DoubleVector[] X = {
                DoubleVector.parse(100,50,50),
                DoubleVector.parse(50,50,50),
                DoubleVector.parse(50,100,50),
                // 最后一行是初始权重数据
                DoubleVector.parse(20, 18, 18)
        };
        // 构建 Y 数据
        double[] Y = {300, 200, 250, 350};

        // 将 线性神经网络模型获取到
        LinearNeuralNetwork linearNeuralNetwork = ASModel.LINEAR_NEURAL_NETWORK;
        // 设置学习率
        linearNeuralNetwork.setArg(LinearNeuralNetwork.LEARNING_RATE, SingletonCell.$(0.02));
        // 设置每一个数据样本的训练次数 为 1024
        linearNeuralNetwork.setArg(LinearNeuralNetwork.LEARN_COUNT, SingletonCell.$(1024));
        // 设置当前神经网络中神经元的激活函数
        linearNeuralNetwork.setArg(LinearNeuralNetwork.PERCEPTRON, SingletonCell.$(Perceptron.parse(ActivationFunction.RELU)));
        // 设置当前神经网络中的目标数值
        linearNeuralNetwork.setArg(LinearNeuralNetwork.TARGET, SingletonCell.$(Y));

        // 开始训练 在这里传递进需要被学习的数据 并获取到模型
        NumberModel numberModel = linearNeuralNetwork.function(X);
        System.out.println(numberModel);
        // 这里直接调用模型 预测 x1 = 200   x2 = 100  x3 = 50 时候的结果  期望数值是 550
        Double function = numberModel.function(new Double[]{200.0, 100.0, 50.0});
        System.out.println(function);
    }
}