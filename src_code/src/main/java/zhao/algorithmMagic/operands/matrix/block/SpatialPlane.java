package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;

/**
 * 矩阵空间面转换器，其提供的所有转换函数可以保证将矩阵空间构造的6个面获取到。
 * <p>
 * The matrix space face converter, which provides all conversion functions, can ensure that the six faces constructed in the matrix space can be obtained.
 */
public final class SpatialPlane {

    /* ***********************
     *  IntegerMatrix
     * ***********************/

    /**
     * 最前面层矩阵的转换函数
     */
    public final static SpaceTransformation<IntegerMatrix[], IntegerMatrix> INTEGER_MATRIX_FRONT = matrixTypes -> matrixTypes[0];
    /**
     * 最后面层矩阵的转换函数
     */
    public final static SpaceTransformation<IntegerMatrix[], IntegerMatrix> INTEGER_MATRIX_AFTER = matrixTypes -> matrixTypes[matrixTypes.length - 1];

    /**
     * 最上面矩阵的转换函数
     */
    public final static SpaceTransformation<IntegerMatrix[], IntegerMatrix> INTEGER_MATRIX_UPPER = integerMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        int[][] res = new int[integerMatrices.length][];
        // 开始将每一层矩阵中的最上面的向量获取到，构建新矩阵
        int count = -1;
        for (IntegerMatrix integerMatrix : integerMatrices) {
            res[++count] = integerMatrix.toArrays()[0];
        }
        // 返回新矩阵
        return IntegerMatrix.parse(res);
    };

    /**
     * 最下面矩阵的转换函数
     */
    public final static SpaceTransformation<IntegerMatrix[], IntegerMatrix> INTEGER_MATRIX_BELOW = integerMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        int[][] res = new int[integerMatrices.length][];
        // 开始将每一层矩阵中的最下面的向量获取到，构建新矩阵
        int count = -1;
        for (IntegerMatrix integerMatrix : integerMatrices) {
            res[++count] = integerMatrix.toArrays()[integerMatrix.getRowCount() - 1];
        }
        // 返回新矩阵
        return IntegerMatrix.parse(res);
    };

    /* ***********************
     *  DoubleMatrix
     * ***********************/

    /**
     * 最前面层矩阵的转换函数
     */
    public final static SpaceTransformation<DoubleMatrix[], DoubleMatrix> DOUBLE_MATRIX_FRONT = matrixTypes -> matrixTypes[0];
    /**
     * 最后面层矩阵的转换函数
     */
    public final static SpaceTransformation<DoubleMatrix[], DoubleMatrix> DOUBLE_MATRIX_AFTER = matrixTypes -> matrixTypes[matrixTypes.length - 1];

    /**
     * 最上面矩阵的转换函数
     */
    public final static SpaceTransformation<DoubleMatrix[], DoubleMatrix> DOUBLE_MATRIX_UPPER = doubleMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        double[][] res = new double[doubleMatrices.length][];
        // 开始将每一层矩阵中的最上面的向量获取到，构建新矩阵
        int count = -1;
        for (DoubleMatrix doubleMatrix : doubleMatrices) {
            res[++count] = doubleMatrix.toArrays()[0];
        }
        // 返回新矩阵
        return DoubleMatrix.parse(res);
    };

    /**
     * 最下面矩阵的转换函数
     */
    public final static SpaceTransformation<DoubleMatrix[], DoubleMatrix> DOUBLE_MATRIX_BELOW = doubleMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        double[][] res = new double[doubleMatrices.length][];
        // 开始将每一层矩阵中的最下面的向量获取到，构建新矩阵
        int count = -1;
        for (DoubleMatrix doubleMatrix : doubleMatrices) {
            res[++count] = doubleMatrix.toArrays()[doubleMatrix.getRowCount() - 1];
        }
        // 返回新矩阵
        return DoubleMatrix.parse(res);
    };
/*

    /* ***********************
     *  ComplexNumberMatrix
     * *********************** /

    /**
     * 最前面层矩阵的转换函数
     * /
    public final static SpaceTransformation<ComplexNumberMatrix[], ComplexNumberMatrix> ComplexNumberMatrix_MATRIX_FRONT = matrixTypes -> matrixTypes[0];
    /**
     * 最后面层矩阵的转换函数
     * /
    public final static SpaceTransformation<ComplexNumberMatrix[], ComplexNumberMatrix> ComplexNumberMatrix_MATRIX_AFTER = matrixTypes -> matrixTypes[matrixTypes.length - 1];

    /**
     * 最上面矩阵的转换函数
     * /
    public final static SpaceTransformation<ComplexNumberMatrix[], ComplexNumberMatrix> ComplexNumberMatrix_MATRIX_UPPER = complexNumberMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        ComplexNumber[][] res = new ComplexNumber[complexNumberMatrices.length][];
        // 开始将每一层矩阵中的最上面的向量获取到，构建新矩阵
        int count = -1;
        for (ComplexNumberMatrix matrix : complexNumberMatrices) {
            res[++count] = matrix.toArrays()[0];
        }
        // 返回新矩阵
        return ComplexNumberMatrix.parse(res);
    };

    /**
     * 最下面矩阵的转换函数
     * /
    public final static SpaceTransformation<ComplexNumberMatrix[], ComplexNumberMatrix> ComplexNumberMatrix_MATRIX_BELOW = complexNumberMatrices -> {
        // 构建出一个临时数据，用来获取到所有的上面数据
        ComplexNumber[][] res = new ComplexNumber[complexNumberMatrices.length][];
        // 开始将每一层矩阵中的最下面的向量获取到，构建新矩阵
        int count = -1;
        for (ComplexNumberMatrix matrix : complexNumberMatrices) {
            res[++count] = matrix.toArrays()[matrix.getRowCount() - 1];
        }
        // 返回新矩阵
        return ComplexNumberMatrix.parse(res);
    };
*/

}
