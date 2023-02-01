package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASMath;

/**
 * 整数矩阵块数据对象，其中每一层矩阵由整数矩阵组成，是针对矩阵复合的有效解决方案。
 * <p>
 * Integer matrix block data object, in which each layer of matrix is composed of integer matrix, is an effective solution for matrix composition.
 *
 * @author zhao
 */
public class IntegerMatrixSpace extends MatrixSpace<IntegerMatrixSpace, Integer, int[][], IntegerMatrix> {

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount        矩阵中的行数量
     *                        <p>
     *                        the number of rows in the matrix
     * @param colCount        矩阵中的列数量
     *                        <p>
     * @param integerMatrices 该矩阵对象中的二维数组对象。
     */
    protected IntegerMatrixSpace(int rowCount, int colCount, IntegerMatrix[] integerMatrices) {
        super(rowCount, colCount, integerMatrices);
    }

    public static IntegerMatrixSpace parse(IntegerMatrix... integerMatrices) {
        if (integerMatrices.length != 0) {
            int back_Row = integerMatrices[0].getRowCount();
            int back_Col = integerMatrices[0].getColCount();
            for (IntegerMatrix integerMatrix : integerMatrices) {
                int rowCount1 = integerMatrix.getRowCount();
                int colCount1 = integerMatrix.getColCount();
                if (back_Row != rowCount1 || back_Col != colCount1) {
                    throw new OperatorOperationException(
                            "发生了错误，您构造一个矩阵块的时候，需要传递很多行列相等的矩阵对象。\nAn error occurred. When you construct a matrix block, you need to pass many matrix objects with equal rows and columns.\n" +
                                    "=> expect : " + "row=" + back_Row + "\tcol=" + back_Col + "\n=> but now: row=" + rowCount1 + "\tcol=" + colCount1);
                }
            }
            return new IntegerMatrixSpace(back_Row, back_Col, integerMatrices);
        }
        return new IntegerMatrixSpace(0, 0, integerMatrices);
    }

    @Override
    public Integer get(int row, int col) {
        return get(Math.max(RowPointer, 0), row, col);
    }

    @Override
    protected void reFresh() {

    }

    @Override
    public final int[][] toArray() {
        return toMatrix().toArrays();
    }

    @Override
    public Integer moduleLength() {
        return toMatrix().moduleLength();
    }

    @Override
    public IntegerMatrixSpace expand() {
        return this;
    }

    @Override
    public final int[][] copyToNewArray() {
        return this.toMatrix().toArrays();
    }

    @Override
    public IntegerMatrixSpace shuffle(long seed) {
        return IntegerMatrixSpace.parse(ASMath.shuffle(toArrays(), seed, true));
    }

    @Override
    public IntegerMatrix[] copyToNewArrays() {
        IntegerMatrix[] integerMatrix1 = toArrays();
        IntegerMatrix[] integerMatrix2 = new IntegerMatrix[integerMatrix1.length];
        System.arraycopy(integerMatrix1, 0, integerMatrix2, 0, integerMatrix2.length);
        return integerMatrix2;
    }

    /**
     * 这里应代表行指针所指向的元素，指针就是行数据，行指针的最大索引值为 MaximumRowPointerCount 该参数可以通过特别的构造函数构造出来。
     * <p>
     * This should represent the element that the row pointer points to. The pointer is the row data. The maximum index value of the row pointer is MaximumRowPointerCount. This parameter can be constructed through a special constructor.
     *
     * @return 当hashNext返回true的时候，当前行指针将会返回具体的数值对象，如果hashNext为false的情况下调用此函数，将会发生异常！
     * <p>
     * When hashNext returns true, the current row pointer will return the specific numeric object. If this function is called when hashNext is false, an exception will occur!
     */
    @Override
    public IntegerMatrix next() {
        return toMatrix();
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerMatrixSpace add(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).add(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixSpace diff(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).diff(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixSpace multiply(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).multiply(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public Integer innerProduct(IntegerMatrixSpace value) {
        // 获取到维度长度
        int length1 = this.getNumberOfDimensions();
        int res = 0;
        // 开始进行计算
        IntegerMatrix[] integerMatrices1 = this.toArrays();
        IntegerMatrix[] integerMatrices2 = value.toArrays();
        for (int i = 0; i < length1; i++) {
            res += integerMatrices1[i].innerProduct(integerMatrices2[i]);
        }
        return res;
    }

    @Override
    public IntegerMatrixSpace transpose() {
        IntegerMatrix[] integerMatrices1 = toArrays();
        IntegerMatrix[] integerMatrices2 = new IntegerMatrix[integerMatrices1.length];
        int count = -1;
        for (IntegerMatrix integerMatrix : integerMatrices1) {
            integerMatrices2[++count] = integerMatrix.transpose();
        }
        return parse(integerMatrices2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        for (IntegerMatrix integerMatrix : toArrays()) {
            stringBuilder.append(integerMatrix);
        }
        return stringBuilder.toString();
    }
}
