package io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASStr;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 词频文本特征提取，将一些数据按照词拆分并统计，获取到每一个词在计算时出现的次数.
 * <p>
 * Word frequency text feature extraction, split some data according to words and count them to get the number of occurrences of each word in the calculation
 *
 * @author zhao
 */
public class WordFrequency extends StringArrayFeature<ColumnIntegerMatrix> {

    /**
     * 停用词列表，您可以直接操作该列表，在该列表中添加的所有词将会作为停用词，统计的时候将忽略该集合中的所有词
     * <p>
     * Deactivated word list. You can operate the list directly. All words added in the list will be used as deactivated words. All words in the collection will be ignored during statistics
     */
    public final static HashSet<String> stopWordSet = new HashSet<>();
    /**
     * 特征提取方案，将某些标点符号与不可见字符作为特征切分符，这是一个有序的数组，用于切分时的二分查找切分。
     */
    protected final static char[] SPLIT_CHARS_GENERAL_PUNCTUATION = {' ', '\t', '\n', '\"', ',', '.', ':', ';', '?'};

    public WordFrequency(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static WordFrequency getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof WordFrequency) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于WordFrequency类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the WordFrequency type. Please redefine a name for this algorithm.");
            }
        } else {
            WordFrequency WordFrequency = new WordFrequency(Name);
            OperationAlgorithmManager.getInstance().register(WordFrequency);
            return WordFrequency;
        }
    }

    /**
     * 将很多字符串组合起来进行特征向量的提取，其中的每一个词都是一个特征值。
     * <p>
     * Combine many strings to extract feature vectors, and each word is a feature value.
     *
     * @param data 需要被进行特征提取的数据，数据类型是具有列字段的向量对象。
     * @return 提取之后的结果对象，类型是具有列字段的向量对象，其中包含将数据替换成为Map的函数等。
     */
    @Override
    public ColumnIntegerMatrix extract(String[] data) {
        return extract(data, true, true);
    }

    /**
     * 将很多字符串组合起来进行特征向量的提取，其中的每一个词都是一个特征值。
     * <p>
     * Combine many strings to extract feature vectors, and each word is a feature value.
     *
     * @param data             需要被进行特征提取的数据，数据类型是具有列字段的向量对象。
     * @param IncludeRowFields 在本次特征提取中是否需要添加行字段。
     * @param IncludeColFields 在本次特征提取中是否需要添加列字段。
     * @return 根据形参设置而计算出来的结果矩阵对象。
     */
    public ColumnIntegerMatrix extract(String[] data, boolean IncludeRowFields, boolean IncludeColFields) {
        HashMap<String, int[]> stringHashMap = extractHashMap(data);
        return ColumnIntegerMatrix.parse(
                IncludeColFields ? data : null,
                IncludeRowFields ? stringHashMap.keySet().toArray(new String[0]) : null,
                stringHashMap.values().toArray(new int[0][])
        );
    }

    /**
     * 将一些文本进行词频向量提取，并返回一个集合数据，其中包含每一个词的出现次数
     * <p>
     * Extract the word frequency vector of some text and return a set of data, including the number of occurrences of each word
     *
     * @param data 需要被进行词频提取的所有字符串组成的数组，每一个字符串会单独进行提取，单独进行计算。
     *             <p>
     *             An array of all strings that need to be extracted for word frequency. Each string will be extracted and calculated separately.
     * @return 一个map集合，其中的键值对，键代表的是被统计的词，值代表的是词频数值，值是一个数组，代表不同句子的统计结果。
     * <p>
     * A map set, in which the key value pair represents the word being counted, the value represents the word frequency value, and the value is an array representing the statistical results of different sentences.
     */
    public HashMap<String, int[]> extractHashMap(String[] data) {
        if (data == null) return new HashMap<>();
        HashMap<String, int[]> hashMap = new HashMap<>(data.length << 1);
        // 首先将所有的词频计算出来
        for (int i = 0; i < data.length; i++) {
            for (String s : ASStr.splitBySortChars(data[i], SPLIT_CHARS_GENERAL_PUNCTUATION, stopWordSet)) {
                int[] orDefault = hashMap.get(s);
                if (orDefault == null) {
                    int[] ints = new int[data.length];
                    ints[i] = 1;
                    orDefault = ints;
                } else {
                    orDefault[i] += 1;
                }
                hashMap.put(s, orDefault);
            }
        }
        return hashMap;
    }
}
