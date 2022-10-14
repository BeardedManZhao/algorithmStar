package zhao.algorithmMagic.algorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.IntegerCoordinates;
import zhao.algorithmMagic.utils.ASClass;

/**
 * Java类于 2022/10/14 18:21:36 创建
 * <p>
 * 汉明距离（Hamming Distance）是应用于数据传输差错控制编码的距离度量方式，它表示两个（相同长度）字符串对应位不同的数量。对两个字符串进行异或运算，并统计结果为1的个数，那么这个数就是汉明距离。我们也可以将汉明距离理解为两个等长字符串之间将其中一个变为另外一个所需要作的最小替换次数。
 * <p>
 * Hamming Distance is a distance metric applied to data transmission error control coding, which represents the number of different bits corresponding to two (same length) strings. Perform XOR operation on two strings, and count the number of 1s, then this number is the Hamming distance. We can also understand the Hamming distance as the minimum number of substitutions required to change one of the strings into the other between two strings of equal length.
 *
 * @author zhao
 */
public class HammingDistance<I extends IntegerCoordinates<?>, D extends FloatingPointCoordinates<?>> implements OperationAlgorithm {
    protected final Logger logger;
    protected final String AlgorithmName;

    protected HammingDistance() {
        this.AlgorithmName = "EuclideanMetric";
        this.logger = Logger.getLogger("EuclideanMetric");
    }

    protected HammingDistance(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = Logger.getLogger(algorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static <II extends IntegerCoordinates<?>, DD extends FloatingPointCoordinates<?>> HammingDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof EuclideanMetric<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EuclideanMetric类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the Cosine Distance type. Please redefine a name for this algorithm.");
            }
        } else {
            HammingDistance<II, DD> euclideanMetric = new HammingDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(euclideanMetric);
            return euclideanMetric;

        }
    }

    /**
     * 汉明度量就是将字符串的每一个字符进行差异的比较，异或运算能够在遇到不同字符的时候会返回 1，同时异或是对位操作，性能更加优秀！
     * <p>
     * Hamming metric is to compare the difference of each character of the string. The XOR operation can return 1 when encountering different characters, and the XOR operation is performed at the same time, and the performance is better!
     *
     * @param str1 被比较的字符串1
     * @param str2 被比较的字符串2
     * @return 两个字符串之间的差异！
     */
    public int getMinimumNumberOfReplacements(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        if (length1 == length2) {
            int res = 0;
            for (int i = 0; i < length1; i++) {
                // 如果当前两个字符不一致就代表有一处不同
                if ((str1.charAt(i) ^ str2.charAt(i)) > 1) {
                    res++;
                }
            }
            return res;
        } else {
            throw new OperatorOperationException("'getMinimumNumberOfReplacements(String str1, String str2)'只能用于相同长度的字符串。\n" +
                    "'getMinimumNumberOfReplacements(String str1, String str2)' can only be used for strings of the same length.");
        }
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
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
