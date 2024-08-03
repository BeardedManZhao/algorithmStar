package io.github.beardedManZhao.algorithmStar.core;

import io.github.beardedManZhao.algorithmStar.operands.ComplexNumber;
import io.github.beardedManZhao.algorithmStar.operands.matrix.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 矩阵工厂类
 *
 * @author zhao
 */
public final class MatrixFactory {

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
    public ComplexNumberMatrix parseMatrix(ComplexNumber[]... matrixArray) {
        return ComplexNumberMatrix.parse(matrixArray);
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
    public ComplexNumberMatrix parseComplexNumberMatrix(String[]... matrixArray) {
        return ComplexNumberMatrix.parse(matrixArray);
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
     * 使用稀疏矩阵的数据构造一个完整的矩阵对象。
     * <p>
     * Use the data of sparse matrix to construct a complete matrix object.
     *
     * @param matrixArray 稀疏矩阵数值，是一个二维数组，其中每一个数组中包含三个元素，第一个是值本身，第二，三个是值的横纵坐标。
     * @return 由稀疏矩阵所构造出来的矩阵对象
     */
    public IntegerMatrix sparseMatrix(int[]... matrixArray) {
        return IntegerMatrix.sparse(matrixArray);
    }

    /**
     * 使用稀疏矩阵的数据构造一个完整的矩阵对象。
     * <p>
     * Use the data of sparse matrix to construct a complete matrix object.
     *
     * @param matrixArray 稀疏矩阵数值，是一个二维数组，其中每一个数组中包含三个元素，第一个是值本身，第二，三个是值的横纵坐标。
     * @return 由稀疏矩阵所构造出来的矩阵对象
     */
    public DoubleMatrix sparseMatrix(double[]... matrixArray) {
        return DoubleMatrix.sparse(matrixArray);
    }

    /**
     * 将数组填充到一个指定长宽的矩阵对象中。
     * <p>
     * Fill the array into a specified length and width matrix object.
     *
     * @param value 填充数组时需要使用的元素数据。
     *              <p>
     *              The element data required to fill the array.
     * @param row   填充数组时，被填充矩阵的行数量。
     *              <p>
     *              When filling an array, the number of rows to be filled in the matrix.
     * @param col   填充数组时，被填充矩阵的列数量。
     *              <p>
     *              When filling an array, the number of columns in the filled matrix.
     * @return 填充之后的新数据矩阵对象。
     * <p>
     * The new data matrix object after filling.
     */
    public IntegerMatrix fill(int value, int row, int col) {
        return IntegerMatrix.fill(value, row, col);
    }

    /**
     * 将数组填充到一个指定长宽的矩阵对象中。
     * <p>
     * Fill the array into a specified length and width matrix object.
     *
     * @param value 填充数组时需要使用的元素数据。
     *              <p>
     *              The element data required to fill the array.
     * @param row   填充数组时，被填充矩阵的行数量。
     *              <p>
     *              When filling an array, the number of rows to be filled in the matrix.
     * @param col   填充数组时，被填充矩阵的列数量。
     *              <p>
     *              When filling an array, the number of columns in the filled matrix.
     * @return 填充之后的新数据矩阵对象。
     * <p>
     * The new data matrix object after filling.
     */
    public DoubleMatrix fill(double value, int row, int col) {
        return DoubleMatrix.fill(value, row, col);
    }

    /**
     * 随机生成一个 double 矩阵对象，在矩阵中存储的就是根据随机种子，随机产生的数值元素。
     * <p>
     * An double matrix object is randomly generated, and the numerical elements stored in the matrix are randomly generated according to the random seed.
     *
     * @param width    要生成的矩阵对象的宽度。
     *                 <p>
     *                 The width of the matrix object to be generated.
     * @param height   要生成的矩阵对象的高度。
     *                 <p>
     *                 The height of the matrix object to be generated.
     * @param randSeed 生成矩阵元素的时候需要使用的随机种子数值。
     *                 <p>
     *                 The random seed value to be used when generating matrix elements.
     * @return 随机生成的指定行列数量的矩阵对象。
     * <p>
     * A randomly generated matrix object with a specified number of rows and columns.
     */
    public DoubleMatrix randomGetDouble(int width, int height, int randSeed) {
        return DoubleMatrix.random(width, height, randSeed);
    }

    /**
     * 随机生成一个 double 矩阵对象，在矩阵中存储的就是根据随机种子，随机产生的数值元素。
     * <p>
     * An double matrix object is randomly generated, and the numerical elements stored in the matrix are randomly generated according to the random seed.
     *
     * @param width    要生成的矩阵对象的宽度。
     *                 <p>
     *                 The width of the matrix object to be generated.
     * @param height   要生成的矩阵对象的高度。
     *                 <p>
     *                 The height of the matrix object to be generated.
     * @param randSeed 生成矩阵元素的时候需要使用的随机种子数值。
     *                 <p>
     *                 The random seed value to be used when generating matrix elements.
     * @return 随机生成的指定行列数量的矩阵对象。
     * <p>
     * A randomly generated matrix object with a specified number of rows and columns.
     */
    public IntegerMatrix randomGetInt(int width, int height, int randSeed) {
        return IntegerMatrix.random(width, height, randSeed);
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
