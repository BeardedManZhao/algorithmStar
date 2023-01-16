package zhao.algorithmMagic.algorithm.classificationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * UDFDistance分类计算组件，在其中提供了分类函数，您可以采用任意一种距离计算组件进行距离的计算。
 * <p>
 * UDFDistance classification calculation component, which provides classification function. You can use any distance calculation component to calculate the distance.
 *
 * @author zhao
 */
public class UDFDistanceClassification extends DistanceClassification implements SampleClassification {

    protected UDFDistanceClassification(String name) {
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
    public static UDFDistanceClassification getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof UDFDistanceClassification) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 UDFDistanceClassification 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the UDFDistanceClassification type. Please redefine a name for this algorithm.");
            }
        } else {
            UDFDistanceClassification UDFDistanceClassification = new UDFDistanceClassification(Name);
            OperationAlgorithmManager.getInstance().register(UDFDistanceClassification);
            return UDFDistanceClassification;
        }
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param data           需要被计算的特征数据组成的矩阵。
     *                       <p>
     *                       Matrix composed of characteristic data to be calculated.
     * @param categorySample 本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                       <p>
     *                       The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    @Override
    public HashMap<String, ArrayList<DoubleVector>> classification(double[][] data, Map<String, double[]> categorySample) {
        Set<String> stringSet = categorySample.keySet();
        HashMap<String, ArrayList<DoubleVector>> hashMap = new HashMap<>(categorySample.size() + 16);
        // 开始进行分类，迭代每一个data中的元素，并将其存储与类别样本中的数据进行比较
        for (double[] datum : data) {
            String MIN_KEY = null;
            double[] MIN_VALUE = null;
            double MIN = Double.MAX_VALUE;
            for (String key : stringSet) {
                // 获取到最小距离的key
                double[] doubles = categorySample.get(key);
                if (doubles != null) {
                    double trueDistance = distanceAlgorithm.getTrueDistance(datum, doubles);
                    if (trueDistance < MIN) {
                        MIN_KEY = key;
                        MIN_VALUE = datum;
                        MIN = trueDistance;
                    }
                }
            }
            // 将最小的序列添加到类别对应的value中
            if (MIN_KEY != null) {
                ArrayList<DoubleVector> doubleVectors = hashMap.get(MIN_KEY);
                if (doubleVectors == null) {
                    doubleVectors = new ArrayList<>();
                    doubleVectors.add(DoubleVector.parse(MIN_VALUE));
                    hashMap.put(MIN_KEY, doubleVectors);
                } else {
                    doubleVectors.add(DoubleVector.parse(MIN_VALUE));
                }
            }
        }
        return hashMap;
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param data           需要被计算的特征数据组成的矩阵。
     *                       <p>
     *                       Matrix composed of characteristic data to be calculated.
     * @param categorySample 本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                       <p>
     *                       The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    @Override
    public HashMap<String, ArrayList<IntegerVector>> classification(int[][] data, Map<String, int[]> categorySample) {
        Set<String> stringSet = categorySample.keySet();
        HashMap<String, ArrayList<IntegerVector>> hashMap = new HashMap<>(categorySample.size() + 16);
        // 开始进行分类，迭代每一个data中的元素，并将其存储与类别样本中的数据进行比较
        for (int[] datum : data) {
            String MIN_KEY = null;
            int[] MIN_VALUE = null;
            double MIN = Double.MAX_VALUE;
            // 获取到最小距离的key
            for (String key : stringSet) {
                int[] doubles = categorySample.get(key);
                if (doubles != null) {
                    double trueDistance = distanceAlgorithm.getTrueDistance(datum, doubles);
                    if (trueDistance < MIN) {
                        MIN_KEY = key;
                        MIN_VALUE = datum;
                        MIN = trueDistance;
                    }
                }
            }
            // 将最小的序列添加到类别对应的value中
            if (MIN_KEY != null) {
                ArrayList<IntegerVector> doubleVectors = hashMap.get(MIN_KEY);
                if (doubleVectors == null) {
                    doubleVectors = new ArrayList<>();
                    doubleVectors.add(IntegerVector.parse(MIN_VALUE));
                    hashMap.put(MIN_KEY, doubleVectors);
                } else {
                    doubleVectors.add(IntegerVector.parse(MIN_VALUE));
                }
            }
        }
        return hashMap;
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param data           需要被计算的特征数据组成的矩阵。
     *                       <p>
     *                       Matrix composed of characteristic data to be calculated.
     * @param categorySample 本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                       <p>
     *                       The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    @Override
    public HashMap<String, ArrayList<DoubleVector>> classification(DoubleMatrix data, Map<String, double[]> categorySample) {
        return classification(data.toArrays(), categorySample);
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param data           需要被计算的特征数据组成的矩阵。
     *                       <p>
     *                       Matrix composed of characteristic data to be calculated.
     * @param categorySample 本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                       <p>
     *                       The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                       {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    @Override
    public HashMap<String, ArrayList<IntegerVector>> classification(IntegerMatrix data, Map<String, int[]> categorySample) {
        return classification(data.toArrays(), categorySample);
    }
}
