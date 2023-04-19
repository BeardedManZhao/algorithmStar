package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ActivationFunction;
import zhao.algorithmMagic.core.model.ListNeuralNetworkLayer;
import zhao.algorithmMagic.core.model.Perceptron;
import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.net.MalformedURLException;


public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws MalformedURLException {
        // 构建x向量
        DoubleVector parseX = DoubleVector.parse(1, 2, 3, 4, 5);
        // 构建y向量
        DoubleVector parseY = DoubleVector.parse(10, 20, 30, 40, 50);
        // 构建基础权重作神经元的计算
        DoubleVector parseW = DoubleVector.parse(1, 1, 1, 1, 1);
        // 获取到神经网络层
        ListNeuralNetworkLayer listNeuralNetworkLayer = new ListNeuralNetworkLayer(parseW);
        // 构造一个神经元感知机
        Perceptron relu = Perceptron.parse(ActivationFunction.RELU);
        // 添加一些神经元 这里添加 relu 激活函数的神经元
        listNeuralNetworkLayer.addPerceptron(relu);
        listNeuralNetworkLayer.addPerceptron(relu);
        listNeuralNetworkLayer.addPerceptron(relu);
        listNeuralNetworkLayer.addPerceptron(relu);
        listNeuralNetworkLayer.addPerceptron(relu);
        // 开始进行计算
        DoubleVector forward = listNeuralNetworkLayer.forward(parseX);
        System.out.println(forward);
        // 计算损失函数 这里是与目标数值进行做差
        DoubleVector loss = forward.diffAbs(parseY, false);
        // 根据损失函数进行反向传播 获取到梯度方向
        DoubleVector doubleVector = listNeuralNetworkLayer.backForward(loss);
        // 打印梯度方向
        System.out.println(doubleVector);
        // 根据梯度核学习率逐步更新权重参数
        // TODO 等待实现
    }
}