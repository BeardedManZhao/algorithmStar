package zhao.algorithmMagic.operands;

import java.util.Arrays;

/**
 * Java类于 2022/10/11 18:14:54 创建
 * <p>
 * 矩阵的抽象类，矩阵属于一个向量，也可以理解其是一个向量组
 *
 * @author 4
 */
public abstract class Matrix<ImplementationType, ElementType> extends Vector<ImplementationType, ElementType> {

    protected final boolean UsePrimitiveType;
    private final int rowCount;
    private final int colCount;
    private final int MaximumRowPointerCount;
    protected int RowPointer = -1;

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount         矩阵中的行数量
     *                         <p>
     *                         the number of rows in the matrix
     * @param colCount         矩阵中的列数量
     *                         <p>
     * @param usePrimitiveType 矩阵中是否使用了基元类型
     */
    protected Matrix(int rowCount, int colCount, boolean usePrimitiveType) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.MaximumRowPointerCount = rowCount - 2;
        UsePrimitiveType = usePrimitiveType;
    }

    /**
     * @return 矩阵的行数量
     * <p>
     * the number of rows in the matrix
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @return 矩阵的列数量
     * <p>
     * the number of columns in the matrix
     */
    public int getColCount() {
        return colCount;
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
    abstract ImplementationType transpose();

    /**
     * 行指针是获取到某一行向量的关键组件，您必须要通过操作行指针，来获取到每一行的序列
     * <p>
     * The row pointer is the key component to get a row vector, you must get the sequence of each row by manipulating the row pointer
     *
     * @return 行指针是否下移成功，如果没有成功返回false，代表不能够继续移动了
     * <p>
     * Whether the row pointer is moved down successfully, if it does not return false, it means that it cannot continue to move.
     */
    boolean MovePointerDown() {
        if (this.RowPointer <= MaximumRowPointerCount) {
            this.RowPointer++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 行指针是获取到某一行向量的关键组件，您必须要通过操作行指针，来获取到每一行的序列
     * <p>
     * The row pointer is the key component to get a row vector, you must get the sequence of each row by manipulating the row pointer
     *
     * @return 行指针是否上移成功，如果没有成功返回false，代表不能够继续移动了
     * <p>
     * Whether the row pointer is moved up successfully, if it does not return false, it means that it cannot continue to move.
     */
    boolean MovePointerUp() {
        if (this.RowPointer <= MaximumRowPointerCount) {
            this.RowPointer++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重置行指针，使行指针恢复到 0
     * <p>
     * Reset the row pointer, so that the row pointer returns to 0
     *
     * @return 链式 chain
     */
    ImplementationType PointerReset() {
        return PointerReset(-1);
    }


    /**
     * 重置行指针，使行指针恢复到 n
     * <p>
     * Reset the row pointer, so that the row pointer returns to 0
     *
     * @return 链式 chain
     */
    ImplementationType PointerReset(int n) {
        this.RowPointer = n;
        return this.expand();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int rowPointer = this.RowPointer;
        PointerReset();
        while (MovePointerDown()) {
            stringBuilder.append(Arrays.toString(toArray())).append("\n");
        }
        PointerReset(rowPointer);
        return "------------MatrixStart-----------\n" +
                stringBuilder +
                "------------MatrixEnd------------\n";
    }

}
