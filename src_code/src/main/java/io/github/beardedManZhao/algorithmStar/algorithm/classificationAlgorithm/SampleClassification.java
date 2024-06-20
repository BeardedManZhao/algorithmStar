package io.github.beardedManZhao.algorithmStar.algorithm.classificationAlgorithm;

import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 样本分类计算组件的统一抽象接口，其中包含通过类别的标准来确定所有待分类的特征类别。
 * <p>
 * The unified abstract interface of the sample classification calculation component, which includes the determination of all feature categories to be classified through the category criteria.
 *
 * @author zhao
 */
public interface SampleClassification {

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
    HashMap<String, ArrayList<DoubleVector>> classification(double[][] data, Map<String, double[]> categorySample);

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
    HashMap<String, ArrayList<IntegerVector>> classification(int[][] data, Map<String, int[]> categorySample);

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
    HashMap<String, ArrayList<DoubleVector>> classification(DoubleMatrix data, Map<String, double[]> categorySample);

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
    HashMap<String, ArrayList<IntegerVector>> classification(IntegerMatrix data, Map<String, int[]> categorySample);
}
