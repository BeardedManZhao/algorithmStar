package io.github.beardedManZhao.algorithmStar.core;

import io.github.beardedManZhao.algorithmStar.operands.matrix.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 矩阵工厂类
 *
 * @author zhao
 */
public final class MatixFactory {

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param matrixArray 用于构造矩阵的二维数组
     *                    <p>
     *                    2D array for constructing the matrix
     * @return matrix object
     */
    public IntegerMatrix parseMatrix(int[]... matrixArray) {
        return IntegerMatrix.parse(matrixArray);
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param matrixArray 用于构造矩阵的二维数组
     *                    <p>
     *                    2D array for constructing the matrix
     * @return matrix object
     */
    public DoubleMatrix parseMatrix(double[]... matrixArray) {
        return DoubleMatrix.parse(matrixArray);
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param colNames    该矩阵中所对应的列名称
     * @param rowNames    该矩阵中所对应的行名称
     * @param matrixArray 该矩阵中需要维护的数组
     * @return 如果 colNames 和 rowNames 不为 null，则返回 ColumnIntegerMatrix 对象，否则返回 IntegerMatrix 对象
     */
    public IntegerMatrix parseMatrix(String[] colNames, String[] rowNames, int[]... matrixArray) {
        if (colNames != null && rowNames != null) {
            return ColumnIntegerMatrix.parse(colNames, rowNames, matrixArray);
        } else {
            return IntegerMatrix.parse(matrixArray);
        }
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param colNames    该矩阵中所对应的列名称
     * @param rowNames    该矩阵中所对应的行名称
     * @param matrixArray 该矩阵中需要维护的数组
     * @return 如果 colNames 和 rowNames 不为 null，则返回 ColumnIntegerMatrix 对象，否则返回 IntegerMatrix 对象
     */
    public DoubleMatrix parseMatrix(String[] colNames, String[] rowNames, double[]... matrixArray) {
        if (colNames != null && rowNames != null) {
            return ColumnDoubleMatrix.parse(colNames, rowNames, matrixArray);
        } else {
            return DoubleMatrix.parse(matrixArray);
        }
    }

    /**
     * 解析图片
     *
     * @param imageMatrixType 需要使用的图像矩阵类型的 Class
     * @param matrixParam     构造矩阵对象需要使用的所有参数的集合
     * @param typeClasses     参数集合 matrixParam 中的每个参数的类型
     * @param useGrayscale    使用 灰度解析
     * @param <T>             图像矩阵类型对象
     * @return T 对象
     */
    public <T extends ColorMatrix> ColorMatrix parseImage(Class<T> imageMatrixType, Object[] matrixParam, Class<?>[] typeClasses, boolean useGrayscale) {
        // 计算函数名字
        final String funName = useGrayscale ? "parseGrayscale" : "parse";
        try {
            final Method parse = imageMatrixType.getMethod(funName, typeClasses);
            final Object invoke = parse.invoke(null, matrixParam);
            return (ColorMatrix) invoke;
        } catch (NoSuchMethodException | NullPointerException e) {
            throw new UnsupportedOperationException("您提供的 imageMatrixType 类对象中没有能够用于解析 " + Arrays.toString(typeClasses) + " 的" + funName + "函数，您的 matrixArray 类型不能被支持;", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new UnsupportedOperationException("解析操作进行了，但是出现了错误！", e);
        }
    }
}
