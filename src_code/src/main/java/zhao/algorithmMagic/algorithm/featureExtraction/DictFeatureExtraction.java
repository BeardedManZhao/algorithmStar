package zhao.algorithmMagic.algorithm.featureExtraction;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.utils.ASClass;

import java.util.HashMap;

/**
 * 字典特征提取，将一些数据按照字典提取的方式将特征提取出来，并构造成为一个向量。
 * <p>
 * Dictionary feature extraction is to extract features from some data in the way of dictionary extraction and construct it into a vector.
 *
 * @author zhao
 */
public class DictFeatureExtraction extends StringArrayFeature<ColumnIntegerMatrix> {
    protected final static String COLUMN_INTEGER_MATRIX_EXTRACT_STRING_DATA = "ColumnIntegerMatrix extract(String[] data)";
    protected final static String COLUMN_INTEGER_MATRIX_EXTRACT_SPARSE_STRING_DATA = "ColumnIntegerMatrix extractHashMap(String[] data)";

    protected DictFeatureExtraction(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static DictFeatureExtraction getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof DictFeatureExtraction) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于DictFeatureExtraction类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the DictFeatureExtraction type. Please redefine a name for this algorithm.");
            }
        } else {
            DictFeatureExtraction DictFeatureExtraction = new DictFeatureExtraction(Name);
            OperationAlgorithmManager.getInstance().register(DictFeatureExtraction);
            return DictFeatureExtraction;
        }
    }

    /**
     * 将很多字符串组合起来进行特征向量的提取，其中的每一个词都是一个特征值。
     * <p>
     * Combine many strings to extract feature vectors, and each word is a feature value.
     *
     * @param data 需要被进行特征提取的数据，数据类型是字符串组成的数组。
     * @return 提取之后的结果对象，是包含字段的一种矩阵，同时其返回的矩阵可以直接转换为HashMap进行Map类的方式调用
     * <p>
     * <p>
     * The extracted result object is a matrix containing fields. At the same time, the returned matrix can be directly converted into HashMap for map class calls
     */
    @Override
    public ColumnIntegerMatrix extract(String[] data) {
        logger.info(COLUMN_INTEGER_MATRIX_EXTRACT_STRING_DATA);
        // 首先将所有的字符串对应的值打上列编号
        HashMap<String, Integer> integerHashMap = getIndexFromMap(data.length + 16, data);
        // 然后开始为每一个元素进行向量构建
        int[][] res = new int[data.length][integerHashMap.size()];
        {
            int count = -1;
            for (String datum : data) {
                // 将当前元素对应的索引位置变更为1
                res[++count][integerHashMap.get(datum)] = 1;
            }
        }
        // 按照顺序生成新列字段
        String[] field = new String[integerHashMap.size()];
        integerHashMap.forEach((key, value) -> field[value] = key);
        // 构造矩阵
        return new ColumnIntegerMatrix(field, null, res);
    }

    /**
     * 以稀疏矩阵的方式将结果提供出来，这种方式对于内存的消耗来说是比较小的，不过需要提前将字段顺序设置好。
     *
     * @param field 本次矩阵中的字段设置
     * @param data  本次需要被进行字典特征提取的数据组
     * @return 提取之后的稀疏矩阵结果，是一个二维数组，其中每一个数组中包含三个元素，第一个是值本身，第二，三个是值的横纵坐标。
     */
    public double[][] extractSparse(String[] field, String[] data) {
        logger.info(COLUMN_INTEGER_MATRIX_EXTRACT_SPARSE_STRING_DATA);
        HashMap<String, Integer> hashMap = getIndexFromMap(field.length + 16, data);
        // 然后开始构造坐标点
        int i = data.length + 1;
        double[][] doubles = new double[i][3];
        int count = -1;
        for (String datum : data) {
            // 将当前数值对应的字段提供给对应的位置（如果没有对应位置将默认添加到最后一行）
            doubles[++count][0] = 1;
            doubles[count][1] = hashMap.getOrDefault(datum, i);
            doubles[count][2] = count;
        }
        return doubles;
    }

    /**
     * 为一个数组中的数据创建出来从0开始的编号，并返回对应的Map
     *
     * @param initSize 创建出来的结果初始长度
     * @param data     需要被打编号的所有字段
     * @return 一个key为 data中的每个元素 value为元素对应的出现次数的键值对
     */
    private HashMap<String, Integer> getIndexFromMap(int initSize, String[] data) {
        // 首先按照提供的字端将编号准备好
        HashMap<String, Integer> hashMap = new HashMap<>(initSize);
        for (int length = data.length - 1; length >= 0; length--) {
            hashMap.put(data[length], length);
        }
        return hashMap;
    }
}