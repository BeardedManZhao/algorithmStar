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
public class IntegerMatrixBlock extends MatrixBlock<IntegerMatrixBlock, Integer, int[], IntegerMatrix> {

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
    protected IntegerMatrixBlock(int rowCount, int colCount, IntegerMatrix[] integerMatrices) {
        super(rowCount, colCount, integerMatrices);
    }

    public static IntegerMatrixBlock parse(IntegerMatrix... integerMatrices) {
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
            return new IntegerMatrixBlock(back_Row, back_Col, integerMatrices);
        }
        return new IntegerMatrixBlock(0, 0, integerMatrices);
    }

    @Override
    public Integer get(int row, int col) {
        return get(Math.max(RowPointer, 0), row, col);
    }

    @Override
    protected void reFresh() {

    }

    @Override
    public int[] toArray() {
        return toMatrix().toArray();
    }

    @Override
    public Integer moduleLength() {
        return toMatrix().moduleLength();
    }

    @Override
    public IntegerMatrixBlock expand() {
        return this;
    }

    @Override
    public int[] copyToNewArray() {
        int[] temp = toArray();
        int[] res = new int[temp.length];
        System.arraycopy(temp, 0, res, 0, res.length);
        return res;
    }

    @Override
    public IntegerMatrixBlock shuffle(long seed) {
        return IntegerMatrixBlock.parse(ASMath.shuffle(toArrays(), seed, true));
    }

    @Override
    public IntegerMatrix[] copyToNewArrays() {
        IntegerMatrix[] integerMatrix1 = toArrays();
        IntegerMatrix[] integerMatrix2 = new IntegerMatrix[integerMatrix1.length];
        System.arraycopy(integerMatrix1, 0, integerMatrix2, 0, integerMatrix2.length);
        return integerMatrix2;
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
    public IntegerMatrixBlock add(IntegerMatrixBlock value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).add(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixBlock(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixBlock diff(IntegerMatrixBlock value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).diff(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixBlock(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixBlock multiply(IntegerMatrixBlock value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).multiply(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixBlock(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public Integer innerProduct(IntegerMatrixBlock value) {
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
    public IntegerMatrixBlock transpose() {
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
