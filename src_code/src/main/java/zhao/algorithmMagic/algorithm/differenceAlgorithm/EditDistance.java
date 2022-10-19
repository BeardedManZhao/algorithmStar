package zhao.algorithmMagic.algorithm.differenceAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.utils.ASClass;

/**
 * Java类于 2022/10/18 16:09:52 创建
 * <p>
 * 两个字符串之间的编辑距离,又称Levenshtein距离，是指两个字串之间，由一个转成另一个所需的最少编辑操作次数。许可的编辑操作包括将一个字符替换成另一个字符，插入一个字符，删除一个字符。一般来说，编辑距离越小，两个串的相似度越大。
 * <p>
 * The edit distance between two strings, also known as the Levenshtein distance, refers to the minimum number of editing operations required to convert two strings from one to the other. Permitted editing operations include replacing one character with another, inserting a character, and deleting a character. In general, the smaller the edit distance, the greater the similarity between the two strings.
 *
 * @author zhao
 */
public class EditDistance implements DifferenceAlgorithm<String> {


    protected final Logger logger;
    protected final String AlgorithmName;

    protected EditDistance() {
        this.AlgorithmName = "EditDistance";
        this.logger = Logger.getLogger("EditDistance");
    }

    protected EditDistance(String algorithmName) {
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
    public static EditDistance getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof EditDistance) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于EditDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the EditDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            EditDistance EditDistance = new EditDistance(Name);
            OperationAlgorithmManager.getInstance().register(EditDistance);
            return EditDistance;

        }
    }

    public int getMinimumNumberOfEdits(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        if (length1 == 0) {
            return length2;
        } else if (length2 == 0) {
            return length1;
        } else if (str1.equals(str2)) {
            return 0;
        }
        int res = 0;
        // 上面三种情况已经判断完毕，当都不能满足的时候，那么就要一个个的判断字符了！下面判断的就是两个字符的倒数第二个字符是否相等
        // 如果不相等的话，那么代表需要进行一次替换
        if (str1.charAt(length1 - 1) != str2.charAt(length2 - 1)) {
            res = 1;
        }
        // 现在我们的这个函数已经具备了计算两个字符串的不同情况的条件，因为字符串在这里会进行缩小字符范围并判断，所以可以开始递归了
        // 当str2进行删除字符的操作时（或str1新增字符），需要多少次和str1（或str2）相同
        int a = getMinimumNumberOfEdits(str1, str2.substring(0, length2 - 1)) + 1;
        // 当str1进行删除字符的操作时（或str2新增字符），需要多少次和str2（或str1）相同
        int b = getMinimumNumberOfEdits(str1.substring(0, length1 - 1), str2) + 1;
        // 当两个字符串同时缩短时（替换）
        int c = getMinimumNumberOfEdits(str1.substring(0, length1 - 1), str2.substring(0, length2 - 1)) + res;
        // 返回三种操作中最小的数值
        return Math.min(Math.min(a, b), c);
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
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两个事物之间从差异系数百分比
     * <p>
     * Calculate the percentage difference from the coefficient of difference between two things
     *
     * @param value1 差异参数1
     * @param value2 差异参数2
     * @return 差异系数
     * <p>
     * 这里是将两个字符串之间的最小编辑次数。除以两个字符串长度的乘积
     * <p>
     * Here is the minimum number of edits to place between two strings. Divide by the product of the lengths of the two strings
     */
    @Override
    public double getDifferenceRatio(String value1, String value2) {
        return 1 - (getMinimumNumberOfEdits(value1, value2) / (double) value1.length() * value2.length());
    }
}
