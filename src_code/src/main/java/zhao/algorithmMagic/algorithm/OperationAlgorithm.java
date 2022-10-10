package zhao.algorithmMagic.algorithm;

/**
 * 运算算法接口，是所有算法的抽象，具体实现请您查阅api node
 * <p>
 * The operation algorithm interface is the abstraction of all algorithms. For the specific implementation, please refer to the api node
 *
 * @param <Algorithm> 实现类的类型，这里用于数据算法获取到之后的转换
 */
public interface OperationAlgorithm<Algorithm> {
    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    String getAlgorithmName();

    /**
     * @return 该算法的具体实现类，您可以通过该函数将算法从抽象转换为一个具体的实现
     * <p>
     * The concrete implementation class of the algorithm, through which you can convert the algorithm from an abstract to a concrete implementation
     */
    Algorithm extract();

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    boolean init();
}
