package zhao.algorithmMagic.algorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.exception.OperationAlgorithmNotFound;

import java.util.HashMap;

/**
 * Java类于 2022/10/9 22:26:04 创建
 * 算法管理类，您可以通过该类，获取到对应的算法操作对象，该类是全局唯一的静态.
 * <p>
 * Algorithm management class, you can get the corresponding algorithm operation object through this class, this class is globally unique static.
 *
 * @author zhao
 */
public final class OperationAlgorithmManager implements OperationAlgorithm {
    /**
     * 算法对象存档集合，您可以将算法对象存储到该集合中
     * <p>
     * An archive collection of algorithm objects into which you can store algorithm objects
     */
    private final static HashMap<String, OperationAlgorithm> STRING_OPERATION_ALGORITHM_HASH_MAP = new HashMap<>();
    private final static Logger logger = Logger.getLogger("OperationAlgorithmManager");
    private final static OperationAlgorithmManager OPERATION_ALGORITHM_MANAGER = new OperationAlgorithmManager();

    private OperationAlgorithmManager() {
    }

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
        logger.info("register OperationAlgorithm:" + operationAlgorithm.getAlgorithmName());
        STRING_OPERATION_ALGORITHM_HASH_MAP.put(operationAlgorithm.getAlgorithmName(), operationAlgorithm);
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
            logger.info("register OperationAlgorithm:" + algorithmName);
            return operationAlgorithm;
        } else {
            String s = "没有找到名为[" + algorithmName + "]的算法\tNo name was found[" + algorithmName + "]algorithm";
            OperationAlgorithmNotFound operationAlgorithmNotFound = new OperationAlgorithmNotFound(s);
            logger.error(s, operationAlgorithmNotFound);
            throw operationAlgorithmNotFound;
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
