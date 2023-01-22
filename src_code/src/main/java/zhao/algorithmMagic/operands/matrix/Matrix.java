package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.vector.ASVector;

import java.util.Arrays;

/**
 * Java类于 2022/10/11 18:14:54 创建
 * <p>
 * 矩阵的抽象类，矩阵属于一个向量，也可以理解其是一个向量组
 *
 * @param <ImplementationType> 实现类的数据类型  The data type of the implementing class
 * @param <ElementType>        向量中的元素类型  element type in the vector
 * @param <ArrayType>          矩阵中的行向量数组类型
 * @param <ArraysType>         矩阵转换成功之后的数组类型
 * @author zhao
 * 该类为抽象，其中包含最基本的定义与类型管控
 * <p>
 * This class is abstract and contains the most basic definitions and type controls.
 */
public abstract class Matrix<ImplementationType extends Matrix<?, ?, ?, ?>, ElementType, ArrayType, ArraysType> extends ASVector<ImplementationType, ElementType, ArrayType> {

    protected final static OperatorOperationException OPERATOR_OPERATION_EXCEPTION = new OperatorOperationException("您不能将所有的维度都去掉！！！！\nYou cannot remove all dimensions!!!!");
    private final int rowCount;
    private final int colCount;
    private final int MaximumRowPointerCount;
    private final byte MinimumRowPointerCount = 0b11111111111111111111111111111111;
    private final ArraysType arraysType;
    protected boolean Unlock = true;
    protected int RowPointer = MinimumRowPointerCount;

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount   矩阵中的行数量
     *                   <p>
     *                   the number of rows in the matrix
     * @param colCount   矩阵中的列数量
     *                   <p>
     * @param arraysType 该矩阵对象中的二维数组对象。
     */
    protected Matrix(int rowCount, int colCount, ArraysType arraysType) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.MaximumRowPointerCount = rowCount - 0b10;
        this.arraysType = arraysType;
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
     * 获取到矩阵中指定坐标点的数值
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
    public abstract ElementType get(int row, int col);

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

    /**
     * 行指针是获取到某一行向量的关键组件，您必须要通过操作行指针，来获取到每一行的序列
     * <p>
     * The row pointer is the key component to get a row vector, you must get the sequence of each row by manipulating the row pointer
     *
     * @return 行指针是否下移成功，如果没有成功返回false，代表不能够继续移动了
     * <p>
     * Whether the row pointer is moved down successfully, if it does not return false, it means that it cannot continue to move.
     */
    final boolean MovePointerDown() {
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
    final boolean MovePointerUp() {
        if (this.RowPointer >= MinimumRowPointerCount) {
            this.RowPointer--;
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
    final ImplementationType PointerReset() {
        return PointerReset(-1);
    }


    /**
     * 重置行指针，使行指针恢复到 n
     * <p>
     * Reset the row pointer, so that the row pointer returns to 0
     *
     * @return 链式 chain
     */
    final ImplementationType PointerReset(int n) {
        this.RowPointer = n;
        return this.expand();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int rowPointer = this.RowPointer;
        PointerReset();
        while (MovePointerDown()) {
            stringBuilder.append(Arrays.toString((Object[]) this.toArray())).append("\n");
        }
        PointerReset(rowPointer);
        return "------------MatrixStart-----------\n" +
                stringBuilder +
                "------------MatrixEnd------------\n";
    }

    /**
     * @param value 与当前向量一起进行计算的另一个向量对象。
     *              <p>
     *              Another vector object that is evaluated with the current vector.
     * @param lock  如果设置为true 代表在计算的时候进行锁机制的判断，可以有效避免行指针混乱。
     *              <p>
     *              If it is set to true, it means that the lock mechanism is judged during calculation, which can effectively avoid confusion of row pointers.
     * @return 两个矩阵按坐标求和之后的新矩阵。
     */
    @Override
    public ImplementationType add(ImplementationType value, boolean lock) {
        if (lock) {
            if (this.Unlock && value.Unlock) {
                this.Unlock = false;
                value.Unlock = false;
                ImplementationType res = this.add(value);
                this.Unlock = true;
                value.Unlock = true;
                return res;
            } else {
                throw new OperatorOperationException("您不能将一个正在进行计算的矩阵提供给其它线程进行计算，因为矩阵计算依赖行指针，行指针是共用的。\n" +
                        "You cannot provide a matrix being calculated to other threads for calculation, because matrix calculation depends on row pointers, which are shared.");
            }
        }
        return this.add(value);
    }

    /**
     * @param value 与当前向量一起进行计算的另一个向量对象。
     *              <p>
     *              Another vector object that is evaluated with the current vector.
     * @param lock  如果设置为true 代表在计算的时候进行锁机制的判断，可以有效避免行指针混乱。
     *              <p>
     *              If it is set to true, it means that the lock mechanism is judged during calculation, which can effectively avoid confusion of row pointers.
     * @return 两个矩阵按坐标求差之后的新矩阵。
     */
    @Override
    public ImplementationType diff(ImplementationType value, boolean lock) {
        if (lock) {
            if (this.Unlock && value.Unlock) {
                this.Unlock = false;
                value.Unlock = false;
                ImplementationType res = this.diff(value);
                this.Unlock = true;
                value.Unlock = true;
                return res;
            } else {
                throw new OperatorOperationException("您不能将一个正在进行计算的矩阵提供给其它线程进行计算，因为矩阵计算依赖行指针，行指针是共用的。\n" +
                        "You cannot provide a matrix being calculated to other threads for calculation, because matrix calculation depends on row pointers, which are shared.");
            }
        }
        return this.diff(value);
    }

    /**
     * @param value 与当前向量一起进行计算的另一个向量对象。
     *              <p>
     *              Another vector object that is evaluated with the current vector.
     * @param lock  如果设置为true 代表在计算的时候进行锁机制的判断，可以有效避免行指针混乱。
     *              <p>
     *              If it is set to true, it means that the lock mechanism is judged during calculation, which can effectively avoid confusion of row pointers.
     * @return 两个矩阵按行进行外积计算之后的新矩阵。
     */
    @Override
    public ImplementationType multiply(ImplementationType value, boolean lock) {
        if (lock) {
            if (this.Unlock && value.Unlock) {
                this.Unlock = false;
                value.Unlock = false;
                ImplementationType res = this.multiply(value);
                this.Unlock = true;
                value.Unlock = true;
                return res;
            } else {
                throw new OperatorOperationException("您不能将一个正在进行计算的矩阵提供给其它线程进行计算，因为矩阵计算依赖行指针，行指针是共用的。\n" +
                        "You cannot provide a matrix being calculated to other threads for calculation, because matrix calculation depends on row pointers, which are shared.");
            }
        }
        return this.multiply(value);
    }

    /**
     * @param value 与当前向量一起进行计算的另一个向量对象。
     *              <p>
     *              Another vector object that is evaluated with the current vector.
     * @param lock  如果设置为true 代表在计算的时候进行锁机制的判断，可以有效避免行指针混乱。
     *              <p>
     *              If it is set to true, it means that the lock mechanism is judged during calculation, which can effectively avoid confusion of row pointers.
     * @return 两个矩阵按行进行计算之后的内积数值
     */
    public ElementType innerProduct(ImplementationType value, boolean lock) {
        if (lock) {
            if (this.Unlock && value.Unlock) {
                this.Unlock = false;
                value.Unlock = false;
                ElementType res = this.innerProduct(value);
                this.Unlock = true;
                value.Unlock = true;
                return res;
            } else {
                throw new OperatorOperationException("您不能将一个正在进行计算的矩阵提供给其它线程进行计算，因为矩阵计算依赖行指针，行指针是共用的。\n" +
                        "You cannot provide a matrix being calculated to other threads for calculation, because matrix calculation depends on row pointers, which are shared.");
            }
        }
        return this.innerProduct(value);
    }

    /**
     * @return 当前矩阵对象是否被其他的线程所占用，如果返回false，就代表当前矩阵对象可以参与多线程的计算工作
     */
    public final boolean isUnlock() {
        return Unlock;
    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public abstract ArrayType toArray();

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public abstract ArrayType copyToNewArray();

    /**
     * 该方法将会获取到矩阵中的二维数组，注意，与toArray一样返回的是正在被维护的数组对象，建议作为只读变量使用。
     * <p>
     * This method will get the two-dimensional array in the matrix. Note that the array object being maintained is returned as the same as toArray. It is recommended to use it as a read-only variable.
     *
     * @return 当前矩阵对象中正在被维护的二维数组，如果修改该值，那么将会导致原矩阵对象中的数据发生变化。
     * <p>
     * If the value of the two-dimensional array being maintained in the current matrix object is modified, the data in the original matrix object will change.
     */
    public final ArraysType toArrays() {
        return this.arraysType;
    }

    /**
     * 该方法将会获取到矩阵中的二维数组，值得注意的是，在该函数中获取到的数组是一个新的数组，不会有任何的关系。
     * <p>
     * This method will obtain the two-dimensional array in the matrix. It is worth noting that the array obtained in this function is a new array and will not have any relationship.
     *
     * @return 当前矩阵对象中的二维数组的深拷贝新数组，支持修改！！
     * <p>
     * The deep copy new array of the two-dimensional array in the current matrix object supports modification!!
     */
    public abstract ArraysType copyToNewArrays();
}
