package io.github.beardedManZhao.algorithmStar.utils;

import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.DistanceAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.EuclideanMetric;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.ManhattanDistance;

import java.util.HashSet;

/**
 * Java类于 2022/10/13 17:32:11 创建
 * <p>
 * 在该框架中，有一些预制的算法由于处于被依赖的情况，会先一步存在于算法管理类中，这里就是那些算法在库中的名称，其中的数据皆为常量，如非必要请勿更改！
 * <p>
 * In this framework, some prefabricated algorithms will exist in the algorithm management class first because they are dependent. Here are the names of those algorithms in the library, and the data in them are all constants. Do not change them unless necessary!
 *
 * @author zhao
 */
public final class DependentAlgorithmNameLibrary {
    public static final String EUCLIDEAN_METRIC_NAME = "EuclideanMetric";
    public static final String MANHATTAN_DISTANCE_NAME = "ManhattanDistance";
    public static final DistanceAlgorithm EUCLIDEAN_METRIC = EuclideanMetric.getInstance(DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC_NAME);
    public static final DistanceAlgorithm MANHATTAN_DISTANCE = ManhattanDistance.getInstance(MANHATTAN_DISTANCE_NAME);
    private final static HashSet<String> DEPENDENT_ALGORITHM_NAME_ARRAY = new HashSet<>();

    static {
        DEPENDENT_ALGORITHM_NAME_ARRAY.add(EUCLIDEAN_METRIC_NAME);
        DEPENDENT_ALGORITHM_NAME_ARRAY.add(MANHATTAN_DISTANCE_NAME);
    }

    /**
     * 判断一个算法是否属于依赖，如果属于返回true，遇到这样的算法，您可以按常使用，但是请勿删除！
     * <p>
     * Determine whether an algorithm is a dependency. If it is, it returns true. If you encounter such an algorithm, you can use it as usual, but do not delete it!
     *
     * @param name 算法名称
     *             <p>
     *             Algorithm name
     * @return 算法是否属于依赖
     * <p>
     * whether an algorithm is a dependency.
     */
    public static boolean isPrefabricated(String name) {
        return DEPENDENT_ALGORITHM_NAME_ARRAY.contains(name);
    }
}
