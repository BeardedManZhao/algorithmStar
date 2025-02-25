package io.github.beardedManZhao.algorithmStar.algorithm;

import io.github.beardedManZhao.algorithmStar.exception.OperationAlgorithmManagementException;
import io.github.beardedManZhao.algorithmStar.exception.OperationAlgorithmNotFound;
import io.github.beardedManZhao.algorithmStar.utils.DependentAlgorithmNameLibrary;

import java.util.HashMap;

/**
 * Java类于 2022/10/9 22:26:04 创建
 * 算法管理类，您可以通过该类，获取到对应的算法操作对象，该类是全局唯一的静态.
 * <p>
 * Algorithm management class, you can get the corresponding algorithm operation object through this class, this class is globally unique static.
 *
 * @author LingYuZhao
 */
public final class OperationAlgorithmManager implements OperationAlgorithm {
    /**
     * AS  version
     */
    public final static float VERSION = 1.44f;

    /**
     * 计算组件的日志打印开关，当此处值为false的时候，计算组件中的日志将不会被打印，logger也不会被调用，一般来说，这里为了减少冗余的字符串实例化操作，会设置为false，当需要调试的时候才需要打开此处的数值。
     * <p>
     * The log printing switch of the calculation component. When the value here is false, the log in the calculation component will not be printed and the logger will not be called. Generally speaking, in order to reduce redundant string instantiation operations, the value here will be set to false. You need to turn on the value here only when debugging is required.
     *
     * @deprecated 1.44 以及以后的版本中 将不会使用日志功能，此配置将于 1.45 版本中正式移除。
     */
    @Deprecated
    public static final boolean PrintCalculationComponentLog = false;
    /**
     * 算法对象存档集合，您可以将算法对象存储到该集合中
     * <p>
     * An archive collection of algorithm objects into which you can store algorithm objects
     */
    private final static HashMap<String, OperationAlgorithm> STRING_OPERATION_ALGORITHM_HASH_MAP = new HashMap<>();
    private final static OperationAlgorithmManager OPERATION_ALGORITHM_MANAGER = new OperationAlgorithmManager();


    private OperationAlgorithmManager() {
    }

    /**
     * 获取到 Algorithm 的包下载url
     *
     * @return AlgorithmStar 的详细信息URL，该URL是一个https链接，其对应AlgorithmStar的托管网站，您可以在这里查询到更多信息！
     * <p>
     * The detailed information URL of AlgorithmStar. This URL is a https link, which corresponds to the hosting website of AlgorithmStar. You can find more information here!
     */
    public native static String getAlgorithmStarUrl();

    /**
     * @return 管理者的对象，通过此方法获取全局唯一的管理者对象。
     * <p>
     * The object of the manager, the globally unique manager object is obtained through this method.
     */
    public static OperationAlgorithmManager getInstance() {
        return OPERATION_ALGORITHM_MANAGER;
    }

    /**
     * 判断指定的算法组件的被注册情况
     * Determine the registration status of the specified algorithm component
     * <p>
     * Determine the registration status of the specified algorithm component
     *
     * @param algorithmName 算法组件的名称  the name of the algorithm component
     * @return 指定的算法组件被注册情况。如果被注册过，这里会返回true
     * <p>
     * The specified algorithm component is registered. If registered, this will return true
     */
    public static boolean containsAlgorithmName(String algorithmName) {
        return STRING_OPERATION_ALGORITHM_HASH_MAP.containsKey(algorithmName);
    }

    /**
     * 将一个组件注册到算法管理类中。
     * <p>
     * Register a component to the algorithm management class.
     *
     * @param operationAlgorithm 需要注册的算法对象
     *                           <p>
     *                           The algorithm object that needs to be registered
     */
    public void register(OperationAlgorithm operationAlgorithm) {
        STRING_OPERATION_ALGORITHM_HASH_MAP.put(operationAlgorithm.getAlgorithmName(), operationAlgorithm);
    }

    /**
     * 取消一个算法的注册
     * <p>
     * unregister an algorithm
     *
     * @param algorithmName 需要取注册的算法名称
     *                      <p>
     *                      The name of the algorithm that needs to be registered
     * @return 是否取消注册成功！ 如果没有成功返回 falas 错误原因："It seems that the algorithm you want to unregister has never been registered in the management class, and cannot be found in the management: [algorithmName]
     * <p>
     * Whether the cancellation of registration is successful!
     */
    public boolean unRegister(String algorithmName) {
        if (DependentAlgorithmNameLibrary.isPrefabricated(algorithmName)) {
            throw new OperationAlgorithmManagementException(
                    "您想要取消注册的算法属于其它算法的依赖，因此为了程序的安全不予执行！如果您一定要删除，请使用管理类的'Unloading(algorithmName)'"
            );
        } else {
            return Unloading(algorithmName);
        }
    }

    /**
     * 卸载一个算法，该方法将不会提供依赖检查，不建议在程序中直接调用。
     *
     * @param algorithmName 需要被卸载的算法名称
     * @return 卸载是否成功！如果未成功，那么您要卸载的方法可能没有被注册过！
     */
    public boolean Unloading(String algorithmName) {
        return STRING_OPERATION_ALGORITHM_HASH_MAP.remove(algorithmName) != null;
    }

    /**
     * 通过算法名称，获取到一个算法对象
     *
     * @param algorithmName 算法的名称，以注册时的为准
     * @return 算法的父类对象，不要忘记调用extract进行一个父子的转换哦！
     * @throws OperationAlgorithmNotFound 没有找到指定的算法时会抛出
     */
    public OperationAlgorithm get(String algorithmName) {
        OperationAlgorithm operationAlgorithm = STRING_OPERATION_ALGORITHM_HASH_MAP.get(algorithmName);
        if (operationAlgorithm != null) {
            return operationAlgorithm;
        } else {
            throw new OperationAlgorithmNotFound("没有找到名为[" + algorithmName + "]的算法\tNo name was found[" + algorithmName + "]algorithm");
        }
    }

    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return "OperationAlgorithmManager";
    }

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    @Override
    public boolean init() {
        return false;
    }
}
