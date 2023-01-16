package zhao.algorithmMagic.algorithm.classificationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * KNN 近邻算法，通过与自己指定的数据样本进行距离计算而决定处自己的类别！在这里的k 代表的是在数据样本组中的索引最近距离，例如 k=7 代表当前未知类型的数据样本与 前后各7个数据样本进行度量计算，并推断类型。
 * <p>
 * KNN nearest neighbor algorithm determines its own category by calculating the distance from the data sample you specify! Here, k represents the nearest index distance in the data sample group. For example, k=7 represents the current unknown type of data sample and 7 data samples before and after the measurement calculation, and infers the type.
 *
 * @author zhao
 */
public class KnnClassification extends DistanceClassification implements NoSampleClassification {

    private final static String UnknownCategory = "?";
    protected int k = 5;

    protected KnnClassification(String name) {
        super(name);
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static KnnClassification getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof KnnClassification) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 KnnClassification 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the KnnClassification type. Please redefine a name for this algorithm.");
            }
        } else {
            KnnClassification KnnClassification = new KnnClassification(Name);
            OperationAlgorithmManager.getInstance().register(KnnClassification);
            return KnnClassification;
        }
    }

    /**
     * @return KNN 算法的搜索范围参数。该参数代表了待分类样本中的搜索范围，范围越大，结果数值越精确
     * <p>
     * The search scope parameter of the algorithm. This parameter represents the search range in the samples to be classified. The larger the range, the more accurate the result value
     */
    public int k() {
        return k;
    }

    /**
     * @param k 设置的新的算法搜索参数。如果不设置，默认为5
     *          <p>
     *          Set the new algorithm search parameters for. If not set, the default is 5
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys 指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *             <p>
     *             Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param ints 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *             <p>
     *             The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<IntegerVector>> classification(String[] keys, int[][] ints) {
        if (keys.length != ints.length)
            throw new OperatorOperationException("您传入的类别数量与待分类特征数据数量要保持一致！！！\nThe number of categories you pass in should be consistent with the number of characteristic data to be classified!!!\n" +
                    "Number of categories = " + keys.length + "\tCharacteristic number = " + ints.length);
        final HashMap<String, ArrayList<IntegerVector>> hashMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            // 如果当前类别是已知的就不进行操作
            if (UnknownCategory.equals(key)) {
                /*
                   如果是未知的就判断周边特征与当前特征的距离
                   并获取到周边k个特征结果数值中最小的一个特征结果数值
                   并将其作为目标值
                 */
                // 获取到坐标的需要被判断的左闭右开区间
                final int leftS = i - k, rightE = i + k;
                // 开始迭代左右两边的值 找到最小的度量对应的索引
                int MIN_index = -1;
                final int[] ints1 = ints[i];
                {
                    double MIN = Double.MAX_VALUE;
                    // 找到左边相邻的最小距离特征索引
                    for (int i1 = Math.max(leftS, 0); i1 < Math.min(ints.length, rightE); i1++) {
                        if (UnknownCategory.equals(keys[i1])) continue;
                        double trueDistance = distanceAlgorithm.getTrueDistance(ints1, ints[i1]);
                        if (MIN > trueDistance) {
                            MIN_index = i1;
                            MIN = trueDistance;
                        }
                    }
                }
                // 找到之后最近的值开始进行结果添加
                String key1 = keys[MIN_index];
                ArrayList<IntegerVector> integerMatrices = hashMap.get(key1);
                if (integerMatrices == null) {
                    integerMatrices = new ArrayList<>(ints.length + 8);
                    integerMatrices.add(IntegerVector.parse(ints1));
                    hashMap.put(key1, integerMatrices);
                } else {
                    integerMatrices.add(IntegerVector.parse(ints1));
                }
            }
        }
        return hashMap;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys    指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                <p>
     *                Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param doubles 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                <p>
     *                The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<DoubleVector>> classification(String[] keys, double[][] doubles) {
        if (keys.length != doubles.length)
            throw new OperatorOperationException("您传入的类别数量与待分类特征数据数量要保持一致！！！\nThe number of categories you pass in should be consistent with the number of characteristic data to be classified!!!\n" +
                    "Number of categories = " + keys.length + "\tCharacteristic number = " + doubles.length);
        final HashMap<String, ArrayList<DoubleVector>> hashMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            // 如果当前类别是已知的就不进行操作
            if (UnknownCategory.equals(key)) {
                /*
                   如果是未知的就判断周边特征与当前特征的距离
                   并获取到周边k个特征结果数值中最小的一个特征结果数值
                   并将其作为目标值
                 */
                // 获取到坐标的需要被判断的左闭右开区间
                final int leftS = i - k, rightE = i + k;
                // 开始迭代左右两边的值 找到最小的度量对应的索引
                int MIN_index = -1;
                final double[] doubles1 = doubles[i];
                {
                    double MIN = Double.MAX_VALUE;
                    // 找到左边相邻的最小距离特征索引
                    for (int i1 = Math.max(leftS, 0); i1 < Math.min(doubles.length, rightE); ++i1) {
                        if ("?".equals(keys[i1])) continue;
                        double trueDistance = distanceAlgorithm.getTrueDistance(doubles1, doubles[i1]);
                        if (MIN > trueDistance) {
                            MIN_index = i1;
                            MIN = trueDistance;
                        }
                    }
                }
                // 找到之后最近的值开始进行结果添加
                String key1 = keys[MIN_index];
                ArrayList<DoubleVector> integerMatrices = hashMap.get(key1);
                if (integerMatrices == null) {
                    integerMatrices = new ArrayList<>(doubles.length + 8);
                    integerMatrices.add(DoubleVector.parse(doubles1));
                    hashMap.put(key1, integerMatrices);
                } else {
                    integerMatrices.add(DoubleVector.parse(doubles1));
                }
            }
        }
        return hashMap;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys 指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *             <p>
     *             Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param ints 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *             <p>
     *             The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<IntegerVector>> classification(String[] keys, IntegerMatrix ints) {
        return classification(keys, ints.toArrays());
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys    指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                <p>
     *                Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param doubles 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                <p>
     *                The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<DoubleVector>> classification(String[] keys, DoubleMatrix doubles) {
        return classification(keys, doubles.toArrays());
    }
}
