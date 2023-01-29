package zhao.algorithmMagic.algorithm.classificationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.ColumnDoubleVector;
import zhao.algorithmMagic.operands.vector.ColumnIntegerVector;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.*;

/**
 * KMeans无监督学习聚类算法计算组件，其具有强大无监督学习功能。
 * <p>
 * KMeans unsupervised learning to cluster algorithm calculation component, which has powerful unsupervised learning function.
 *
 * @author zhao
 */
public class KMeans extends DistanceClassification implements NoSampleClassification {

    protected final Random random = new Random();
    protected boolean copy = false;

    protected KMeans(String name) {
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
    public static KMeans getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof KMeans) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 KMeans 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the KMeans type. Please redefine a name for this algorithm.");
            }
        } else {
            KMeans KMeans = new KMeans(Name);
            OperationAlgorithmManager.getInstance().register(KMeans);
            return KMeans;
        }
    }

    /**
     * 随机挑选K个空间的中心点时的随机种子的设置函数，在进行K个空间内中心点的样本设置的时候会进行随机打乱，然后将前K个行数据作为K个空间的中心点
     * <p>
     * The random seed setting function when randomly selecting the center points in K spaces will be randomly scrambled when setting the samples of the center points in K spaces, and then the first K rows of data will be used as the center points in K spaces
     *
     * @param seed 随机种子数值。
     *             <p>
     *             Random seed value.
     */
    public final void setSeed(int seed) {
        this.random.setSeed(seed);
    }

    /**
     * 在进行随机打乱样本的时候，随机打乱操作是否要作用于实参操作数，以便减少冗余内存占用。
     * <p>
     * When randomly scrambling samples, whether the random scrambling operation should act on the real parameter operands in order to reduce the redundant memory occupation.
     *
     * @param copy 如果设置为true 代表不作用于实参，而是拷贝出一个新数组，反之则直接作用与实参对象上。
     *             <p>
     *             If set to true, it will not affect the actual parameter, but will copy a new array, otherwise it will directly affect the actual parameter object.
     */
    public final void setCopy(boolean copy) {
        this.copy = copy;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys 指定的一些数据标签，其长度在计算组件中会作为K，其元素代表在K个空间中对应的数据标签名称。
     *             <p>
     *             The length of some specified data labels will be treated as K in the calculation component, and its elements represent the corresponding data label names in K spaces.
     * @param ints 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *             <p>
     *             The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<IntegerVector>> classification(String[] keys, int[]... ints) {
        int length = ints.length;
        if (keys.length >= length) {
            throw new OperatorOperationException("您设置的空间数值过小，针对您的数据样本，您应满足 keys.length < " + length +
                    "\nThe space value you set is too small. For your data sample, you should meet the requirements of : keys.length < " + length);
        }
        // 使用行数据浅拷贝同时进行随机打乱 前keys.length个元素
        int[][] shuffle = copy ?
                ASMath.shuffleFunction(this.random, ints.length, ASClass.array2DCopy(ints, new int[ints.length][]), keys.length) :
                ASMath.shuffleFunction(this.random, ints.length, ints, keys.length);
        // 将每一个key 以及其样本提供至Map集合中
        HashMap<String, ArrayList<IntegerVector>> hashMap = new HashMap<>(keys.length + 4);
        int index = -1;
        for (String key : keys) {
            hashMap.put(key, new ArrayList<>(Collections.singletonList(IntegerVector.parse(shuffle[++index]))));
        }
        // 继续迭代后面的所有行，计算出其每行距离K个空间中的最小距离对应的key
        int maxLength = length - 1;
        Set<Map.Entry<String, ArrayList<IntegerVector>>> entries = hashMap.entrySet();
        while (index < maxLength) {
            int[] vector = shuffle[++index];
            double MinDis = Double.MAX_VALUE;
            String MinKey = null;
            IntegerVector integerVector = IntegerVector.parse(vector);
            // 在这里计算出当前行距离K个空间中心点的最小距离对应的中心的数值
            for (Map.Entry<String, ArrayList<IntegerVector>> entry : entries) {
                String s = entry.getKey();
                IntegerVector core = entry.getValue().get(0);
                // 计算距离进行最小值获取
                double trueDistance = distanceAlgorithm.getTrueDistance(core.toArray(), integerVector.toArray());
                if (trueDistance < MinDis) {
                    MinDis = trueDistance;
                    MinKey = s;
                }
            }
            // 如果有最小值就直接将数据提供至对应集合
            if (MinKey != null) {
                hashMap.get(MinKey).add(integerVector);
            }
        }
        return hashMap;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys    指定的一些数据标签，其长度在计算组件中会作为K，其元素代表在K个空间中对应的数据标签名称。
     *                <p>
     *                The length of some specified data labels will be treated as K in the calculation component, and its elements represent the corresponding data label names in K spaces.
     * @param doubles 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                <p>
     *                The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<DoubleVector>> classification(String[] keys, double[]... doubles) {
        int length = doubles.length;
        if (keys.length >= length) {
            throw new OperatorOperationException("您设置的空间数值过小，针对您的数据样本，您应满足 keys.length < " + length +
                    "\nThe space value you set is too small. For your data sample, you should meet the requirements of : keys.length < " + length);
        }
        // 使用行数据浅拷贝同时进行随机打乱 前keys.length个元素
        double[][] shuffle = copy ?
                ASMath.shuffleFunction(this.random, doubles.length, ASClass.array2DCopy(doubles, new double[doubles.length][]), keys.length) :
                ASMath.shuffleFunction(this.random, doubles.length, doubles, keys.length);
        // 将每一个key 以及其样本提供至Map集合中
        HashMap<String, ArrayList<DoubleVector>> hashMap = new HashMap<>(keys.length + 4);
        int index = -1;
        for (String key : keys) {
            hashMap.put(key, new ArrayList<>(Collections.singletonList(DoubleVector.parse(shuffle[++index]))));
        }
        // 继续迭代后面的所有行，计算出其每行距离K个空间中的最小距离对应的key
        int maxLength = length - 1;
        Set<Map.Entry<String, ArrayList<DoubleVector>>> entries = hashMap.entrySet();
        while (index < maxLength) {
            double[] vector = shuffle[++index];
            double MinDis = Double.MAX_VALUE;
            String MinKey = null;
            DoubleVector integerVector = DoubleVector.parse(vector);
            // 在这里计算出当前行距离K个空间中心点的最小距离对应的中心的数值
            for (Map.Entry<String, ArrayList<DoubleVector>> entry : entries) {
                String s = entry.getKey();
                DoubleVector core = entry.getValue().get(0);
                // 计算距离进行最小值获取
                double trueDistance = distanceAlgorithm.getTrueDistance(core.toArray(), integerVector.toArray());
                if (trueDistance < MinDis) {
                    MinDis = trueDistance;
                    MinKey = s;
                }
            }
            // 如果有最小值就直接将数据提供至对应集合
            if (MinKey != null) {
                hashMap.get(MinKey).add(integerVector);
            }
        }
        return hashMap;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys 指定的一些数据标签，其长度在计算组件中会作为K，其元素代表在K个空间中对应的数据标签名称。
     *             <p>
     *             The length of some specified data labels will be treated as K in the calculation component, and its elements represent the corresponding data label names in K spaces.
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
     * @param keys    指定的一些数据标签，其长度在计算组件中会作为K，其元素代表在K个空间中对应的数据标签名称。
     *                <p>
     *                The length of some specified data labels will be treated as K in the calculation component, and its elements represent the corresponding data label names in K spaces.
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

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys                指定的一些数据标签，其长度在计算组件中会作为K，其元素代表在K个空间中对应的数据标签名称。
     *                            <p>
     *                            The length of some specified data labels will be treated as K in the calculation component, and its elements represent the corresponding data label names in K spaces.
     * @param columnIntegerMatrix 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                            <p>
     *                            The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<ColumnIntegerVector>> classification(String[] keys, ColumnIntegerMatrix columnIntegerMatrix) {
        int length = columnIntegerMatrix.getRowCount();
        if (keys.length >= length) {
            throw new OperatorOperationException("您设置的空间数值过小，针对您的数据样本，您应满足 keys.length < " + length +
                    "\nThe space value you set is too small. For your data sample, you should meet the requirements of : keys.length < " + length);
        }
        // 随机打乱
        int[][] shuffle = columnIntegerMatrix.shuffle(random, this.copy, keys.length).toArrays();
        // 将每一个key 以及其样本提供至Map集合中
        HashMap<String, ArrayList<ColumnIntegerVector>> hashMap = new HashMap<>(keys.length + 4);
        int index = -1;
        String[] rowFieldNames = columnIntegerMatrix.getColFieldNames();
        for (String key : keys) {
            hashMap.put(key, new ArrayList<>(Collections.singletonList(ColumnIntegerVector.parse(
                    key, rowFieldNames,
                    shuffle[++index]
            ))));
        }
        // 继续迭代后面的所有行，计算出其每行距离K个空间中的最小距离对应的key
        int maxLength = length - 1;
        Set<Map.Entry<String, ArrayList<ColumnIntegerVector>>> entries = hashMap.entrySet();
        while (index < maxLength) {
            int[] vector = shuffle[++index];
            double MinDis = Double.MAX_VALUE;
            String MinKey = null;
            IntegerVector integerVector = IntegerVector.parse(vector);
            // 在这里计算出当前行距离K个空间中心点的最小距离对应的中心的数值
            for (Map.Entry<String, ArrayList<ColumnIntegerVector>> entry : entries) {
                String s = entry.getKey();
                IntegerVector core = entry.getValue().get(0);
                // 计算距离进行最小值获取
                double trueDistance = distanceAlgorithm.getTrueDistance(core.toArray(), integerVector.toArray());
                if (trueDistance < MinDis) {
                    MinDis = trueDistance;
                    MinKey = s;
                }
            }
            // 如果有最小值就直接将数据提供至对应集合
            if (MinKey != null) {
                hashMap.get(MinKey).add(ColumnIntegerVector.parse(MinKey, rowFieldNames, integerVector));
            }
        }
        return hashMap;
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param keys               指定的一些数据类别，按照索引与 columnDoubleMatrix 参数一一对应，其中如果为 ? 代表是未知类别
     *                           <p>
     *                           Some specified data categories correspond to the columnDoubleMatrix parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param columnDoubleMatrix 指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                           <p>
     *                           The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    @Override
    public HashMap<String, ArrayList<ColumnDoubleVector>> classification(String[] keys, ColumnDoubleMatrix columnDoubleMatrix) {
        int length = columnDoubleMatrix.getRowCount();
        if (keys.length >= length) {
            throw new OperatorOperationException("您设置的空间数值过小，针对您的数据样本，您应满足 keys.length < " + length +
                    "\nThe space value you set is too small. For your data sample, you should meet the requirements of : keys.length < " + length);
        }
        // 随机打乱
        double[][] shuffle = columnDoubleMatrix.shuffle(random, this.copy, keys.length).toArrays();
        // 将每一个key 以及其样本提供至Map集合中
        HashMap<String, ArrayList<ColumnDoubleVector>> hashMap = new HashMap<>(keys.length + 4);
        int index = -1;
        String[] rowFieldNames = columnDoubleMatrix.getColFieldNames();
        for (String key : keys) {
            hashMap.put(key, new ArrayList<>(Collections.singletonList(ColumnDoubleVector.parse(
                    key, rowFieldNames,
                    shuffle[++index]
            ))));
        }
        // 继续迭代后面的所有行，计算出其每行距离K个空间中的最小距离对应的key
        int maxLength = length - 1;
        Set<Map.Entry<String, ArrayList<ColumnDoubleVector>>> entries = hashMap.entrySet();
        while (index < maxLength) {
            double[] vector = shuffle[++index];
            double MinDis = Double.MAX_VALUE;
            String MinKey = null;
            DoubleVector integerVector = DoubleVector.parse(vector);
            // 在这里计算出当前行距离K个空间中心点的最小距离对应的中心的数值
            for (Map.Entry<String, ArrayList<ColumnDoubleVector>> entry : entries) {
                String s = entry.getKey();
                DoubleVector core = entry.getValue().get(0);
                // 计算距离进行最小值获取
                double trueDistance = distanceAlgorithm.getTrueDistance(core.toArray(), integerVector.toArray());
                if (trueDistance < MinDis) {
                    MinDis = trueDistance;
                    MinKey = s;
                }
            }
            // 如果有最小值就直接将数据提供至对应集合
            if (MinKey != null) {
                hashMap.get(MinKey).add(ColumnDoubleVector.parse(MinKey, rowFieldNames, integerVector));
            }
        }
        return hashMap;
    }
}
