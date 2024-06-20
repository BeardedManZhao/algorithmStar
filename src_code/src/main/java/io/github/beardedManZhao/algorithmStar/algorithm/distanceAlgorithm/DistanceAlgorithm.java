package io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.Matrix;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;

/**
 * 距离算法的接口,计算两点之间的具体坐标距离
 * <p>
 * The interface of the distance algorithm, which calculates the specific coordinate distance between two points
 */
public interface DistanceAlgorithm extends OperationAlgorithm {

    /**
     * 在度量计算算法组件中，能够支持矩阵对象的运算，但是在运算之前需要先对矩阵中的数据进行一个检查，确保其不会发生错误。
     * <p>
     * In the metric calculation algorithm component, operations on matrix objects can be supported, but before operations, it is necessary to check the data in the matrix to ensure that no errors occur.
     *
     * @param Matrix1 需要被计算的矩阵对象，待检查的矩阵对象。
     *                <p>
     *                The matrix object to be calculated and the matrix object to be checked.
     * @param Matrix2 需要被计算的矩阵对象，待检查的矩阵对象。
     *                <p>
     *                The matrix object to be calculated and the matrix object to be checked.
     */
    static void checkMat(Matrix<?, ?, ?, ?, ?> Matrix1, Matrix<?, ?, ?, ?, ?> Matrix2) {
        check(Matrix1.getRowCount(), Matrix1.getColCount(), Matrix2.getRowCount(), Matrix2.getColCount());
    }

    static void check(int rowCount, int colCount, int rowCount3, int colCount3) {
        if (rowCount != rowCount3)
            throw new OperatorOperationException("矩阵对象中的行数量不一致，不可计算\nThe number of rows in the matrix object is inconsistent and cannot be calculated.ERROR => row1 = " +
                    rowCount + "\trow2 = " + rowCount3);
        if (colCount != colCount3)
            throw new OperatorOperationException("矩阵对象中的列数量不一致，不可计算\nThe number of cols in the matrix object is inconsistent and cannot be calculated.ERROR => col1 = " +
                    colCount + "\tcol2 = " + colCount3);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute 需要被计算的路线对象
     *                                 <p>
     *                                 The route object that needs to be calculated
     * @return ...
     */
    double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute);

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return ...
     */
    double getTrueDistance(double[] doubles1, double[] doubles2);

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return ...
     */
    double getTrueDistance(int[] ints1, int[] ints2);

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param doubleConsanguinityRoute2D 需要被计算的路线对象
     *                                   <p>
     *                                   The route object that needs to be calculated
     * @return ...
     */
    double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D);

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute 需要被计算的路线对象
     *                                  <p>
     *                                  The route object that needs to be calculated
     * @return ...
     */
    double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute);

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param integerConsanguinityRoute2D 需要被计算的路线对象
     *                                    <p>
     *                                    The route object that needs to be calculated
     * @return ...
     */
    double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D);

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param matrix1 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @param matrix2 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    double getTrueDistance(IntegerMatrix matrix1, IntegerMatrix matrix2);

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param matrix1 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @param matrix2 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    double getTrueDistance(DoubleMatrix matrix1, DoubleMatrix matrix2);
}
