package zhao.algorithmMagic.algorithm.classificationAlgorithm;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 无样本数据分类计算组件的抽象接口，其中包含了无样本的数据分类计算函数。
 * <p>
 * The abstract interface of the non-sample data classification calculation component, which contains the non-sample data classification calculation function.
 */
public interface NoSampleClassification {

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
    HashMap<String, ArrayList<IntegerVector>> classification(String[] keys, int[]... ints);

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
    HashMap<String, ArrayList<DoubleVector>> classification(String[] keys, double[]... doubles);

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
    HashMap<String, ArrayList<IntegerVector>> classification(String[] keys, IntegerMatrix ints);

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
    HashMap<String, ArrayList<DoubleVector>> classification(String[] keys, DoubleMatrix doubles);
}
