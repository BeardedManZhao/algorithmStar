package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.operands.matrix.Matrix;

/**
 * 矩阵块抽象类，其中维护了一个矩阵数组，数组中每一个元素都属于一个矩阵对象。
 * <p>
 * Matrix block abstract class, which maintains a matrix array. Each element in the array belongs to a matrix object.
 *
 * @param <ImplementationType> 矩阵块实现类数据类型。
 *                             <p>
 *                             Matrix block implementation class data type.
 * @param <ElementType>        矩阵块中，每一层矩阵对象中的每一个元素类型。
 *                             <p>
 *                             Each element type in each layer of matrix object in the matrix block。
 * @param <ArrayType>          矩阵块中每一个矩阵内的行向量的数组类型。
 *                             <p>
 *                             The array type of row vectors in each matrix in the matrix block.
 * @param <MatrixType>         矩阵快中，每一层矩阵中的矩阵类型。
 *                             <p>
 *                             Matrix fast, the matrix type in each layer of matrix.
 */
public abstract class MatrixBlock<ImplementationType extends Matrix<?, ?, ?, ?>, ElementType, ArrayType, MatrixType
        extends Matrix<?, ElementType, ?, ?>>
        extends Matrix<ImplementationType, ElementType, ArrayType, MatrixType[]> {
    private final int layer;

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount    矩阵中的行数量
     *                    <p>
     *                    the number of rows in the matrix
     * @param colCount    矩阵中的列数量
     *                    <p>
     * @param matrixTypes 该矩阵对象中的二维数组对象。
     */
    protected MatrixBlock(int rowCount, int colCount, MatrixType[] matrixTypes) {
        super(rowCount, colCount, matrixTypes);
        this.layer = matrixTypes.length;
    }


    /**
     * @return 向量中包含的维度数量，在这里代表的是矩阵块中的矩阵层数。
     * <p>
     * The number of dimensions contained in the vector represents the number of matrix layers in the matrix block.
     */
    @Override
    public final int getNumberOfDimensions() {
        return this.layer;
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     * <p>
     * Get the value of the specified coordinate point in the matrix
     *
     * @param row 行编号 从0开始
     *            <p>
     *            Line number starts from 0
     * @param col 列编号 从0开始
     *            <p>
     *            Column number starts from 0
     * @return 矩阵中指定行列坐标的数值
     * <p>
     * The value of the specified row and column coordinates in the matrix
     */
    public final ElementType get(int layer, int row, int col) {
        return this.get(layer).get(row, col);
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     *
     * @param layer 矩阵块中的层编号，一个矩阵块是由很多矩阵组合成的，在这些矩阵中，每一层矩阵都有一个编号，这个编号也就是层编号。
     * @return 矩阵中的数值
     */
    public final MatrixType get(int layer) {
        return this.toArrays()[layer];
    }

    /**
     * @return 获取到矩阵中当前行指针指向的矩阵对象。
     * <p>
     * Gets the matrix object pointed to by the current row pointer in the matrix.
     */
    public final MatrixType toMatrix() {
        return this.toArrays()[Math.max(RowPointer, 0)];
    }

    /**
     * 将现有矩阵的转置矩阵获取到
     * <p>
     * Get the transpose of an existing matrix into
     *
     * @return 矩阵转置之后的新矩阵
     * <p>
     * new matrix after matrix transpose
     */
    public abstract ImplementationType transpose();
}
