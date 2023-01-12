package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;

/**
 * 一个整数矩阵，其中维护了一个基元数组，矩阵中基于数组提供了很多转换函数，同时也提供了对维护数组的提取与拷贝函数。
 * <p>
 * integer matrix, in which a primitive array is maintained, provides many conversion functions based on the array, and also provides extraction and copy functions for the maintained array.
 *
 * @author zhao
 */
public class IntegerMatrix extends Matrix<IntegerMatrix, Integer> {

    private final int[][] VectorArrayPrimitive;

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param ints 矩阵中的元素数值，该数值将会被直接应用到本类中，因此在外界请勿更改。
     *             <p>
     *             The element value in the matrix will be directly applied to this class, so do not change it outside.
     */
    protected IntegerMatrix(int[]... ints) {
        super(ints.length, ints[0].length);
        this.VectorArrayPrimitive = ints;
    }


    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param ints 用于构造矩阵的二维数组
     *             <p>
     *             2D array for constructing the matrix
     * @return matrix object
     */
    public static IntegerMatrix parse(int[]... ints) {
        if (ints.length > 0) {
            return new IntegerMatrix(ints);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
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
    public IntegerMatrix add(IntegerMatrix value) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = value.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            int[][] ints = new int[rowCount1][colCount1];
            int rowPointer = this.RowPointer;
            int rowPointer1 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                int[] line = new int[colCount1];
                int[] ints1 = this.toIntArray();
                int[] ints2 = value.toIntArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = ints1[i] + ints2[i];
                }
                ints[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer;
            value.RowPointer = rowPointer1;
            return parse(ints);
        } else {
            throw new OperatorOperationException("您在'intMatrix1 add intMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'intMatrix1 add intMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "intMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nintMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerMatrix diff(IntegerMatrix value) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = value.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            int[][] ints = new int[rowCount1][colCount1];
            int rowPointer = this.RowPointer;
            int rowPointer1 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                int[] line = new int[colCount1];
                int[] ints1 = this.toIntArray();
                int[] ints2 = value.toIntArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = ints1[i] - ints2[i];
                }
                ints[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer;
            value.RowPointer = rowPointer1;
            return parse(ints);
        } else {
            throw new OperatorOperationException("您在'intMatrix1 diff intMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'intMatrix1 diff intMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "intMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nintMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    @Override
    public Integer get(int row, int col) {
        return this.VectorArrayPrimitive[row][col];
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
    @Override
    public IntegerMatrix transpose() {
        // 转置之后的矩阵行数是转置之前的列数
        int colCount = getColCount();
        // 转置之后的矩阵列数是转置之前的行数
        int rowCount = getRowCount();
        int[][] res = new int[colCount][rowCount];
        // row 就是转置之后的矩阵行编号，转置之前的列编号
        for (int row = 0; row < colCount; row++) {
            // col 就是转置之后的矩阵列编号，转置之前的行编号
            for (int col = 0; col < rowCount; col++) {
                res[row][col] = get(col, row);
            }
        }
        return parse(res);
    }

    /**
     * @return 获取到本矩阵中的所有数据，需要注意的是，该函数获取到的数据矩阵对象中正在使用的，如果返回值被更改，那么会导致一些不可意料的情况发生。
     * <p>
     * Get all the data in this matrix. Note that if the return value of the data matrix object obtained by this function is changed, some unexpected situations will occur.
     */
    @Override
    public double[][] toDoubleArrays() {
        double[][] res = new double[this.getRowCount()][this.getColCount()];
        for (int i = 0; i < this.toIntArrays().length; i++) {
            res[i] = ASClass.IntArray_To_DoubleArray(this.VectorArrayPrimitive[i]);
        }
        return res;
    }

    /**
     * @return 返回该矩阵中所有行数据的数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改。
     * <p>
     * Returns the array form of all row data in the matrix. Since it is copied, it will not generate any dependency, so it supports modification.
     */
    @Override
    public double[][] CopyToNewDoubleArrays() {
        return toDoubleArrays();
    }

    /**
     * @return 获取到本矩阵中的所有数据，需要注意的是，该函数获取到的数据矩阵对象中正在使用的，如果返回值被更改，那么会导致一些不可意料的情况发生。
     * <p>
     * Get all the data in this matrix. Note that if the return value of the data matrix object obtained by this function is changed, some unexpected situations will occur.
     */
    @Override
    public int[][] toIntArrays() {
        return this.VectorArrayPrimitive;
    }

    /**
     * @return 返回该矩阵中所有行数据的数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改。
     * <p>
     * Returns the array form of all row data in the matrix. Since it is copied, it will not generate any dependency, so it supports modification.
     */
    @Override
    public int[][] CopyToNewIntArrays() {
        final int[][] res = new int[this.getRowCount()][this.getColCount()];
        ASClass.array2DCopy(this.VectorArrayPrimitive, res);
        return res;
    }

    /**
     * 刷新操作数对象的所有字段
     */
    @Override
    protected void reFresh() {
        this.PointerReset();
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * waiting to be realized
     */
    @Override
    public Integer moduleLength() {
        int res = 0;
        int rowPointer = this.RowPointer;
        PointerReset();
        while (this.MovePointerDown()) {
            int[] ints = toIntArray();
            for (int v : ints) {
                res += ASMath.Power2(v);
            }
        }
        this.RowPointer = rowPointer;
        return res;
    }

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param matrix 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    @Override
    public IntegerMatrix multiply(IntegerMatrix matrix) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = matrix.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = matrix.getColCount();
        int newLength = (colCount1 - 1) * colCount2;
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            int[][] doubles = new int[rowCount1][newLength];
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = matrix.RowPointer;
            // 迭代每一行
            while (this.MovePointerDown() && matrix.MovePointerDown()) {
                doubles[this.RowPointer] = ASMath.CrossMultiplication(this.toIntArray(), matrix.toIntArray(), newLength);
            }
            this.RowPointer = rowPointer1;
            matrix.RowPointer = rowPointer2;
            return new IntegerMatrix(doubles).PointerReset();
        } else {
            throw new OperatorOperationException("您在'IntegerMatrix1 multiply IntegerMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'IntegerMatrix1 multiply IntegerMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "IntegerMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nIntegerMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
     * <p>
     * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
     *
     * @param matrix 第二个被计算的向量对象
     *               <p>
     *               the second computed matrix object
     * @return 两个向量的内积
     * waiting to be realized
     */
    @Override
    public Integer innerProduct(IntegerMatrix matrix) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = matrix.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = matrix.getColCount();
        if (rowCount1 == rowCount2) {
            int res = 0;
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = matrix.RowPointer;
            while (this.MovePointerDown() && matrix.MovePointerDown()) {
                int[] ints = this.toIntArray();
                int[] ints1 = matrix.toIntArray();
                for (int i = 0; i < ints.length; i++) {
                    res += ints[i] * ints1[i];
                }
            }
            this.RowPointer = rowPointer1;
            matrix.RowPointer = rowPointer2;
            return res;
        } else {
            throw new OperatorOperationException("您在'IntegerMatrix1 innerProduct IntegerMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'IntegerMatrix1 innerProduct IntegerMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "IntegerMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nIntegerMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public IntegerMatrix expand() {
        return this;
    }

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     * <p>
     * 注意 该方法在大部分情况下返回的通常都是源数组，不允许更改，只能作为只读变量。
     */
    @Override
    public double[] toDoubleArray() {
        return ASClass.IntArray_To_DoubleArray(toIntArray());
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    @Override
    public double[] CopyToNewDoubleArray() {
        return toDoubleArray();
    }

    /**
     * @return 不论是基元还是包装，都返回一个基元的整形数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     * <p>
     * 注意 该方法在大部分情况下返回的通常都是源数组，不允许更改，只能作为只读变量。
     */
    @Override
    public int[] toIntArray() {
        return this.VectorArrayPrimitive[RowPointer];
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    @Override
    public int[] CopyToNewIntArray() {
        final int[] res = new int[this.getColCount()];
        System.arraycopy(this.toIntArray(), 0, res, 0, res.length);
        return res;
    }

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return getRowCount() * getColCount();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.VectorArrayPrimitive.length << 2);
        for (int[] ints : this.VectorArrayPrimitive) {
            stringBuilder.append(Arrays.toString(ints)).append("\n");
        }
        return "------------MatrixStart-----------\n" +
                stringBuilder +
                "------------MatrixEnd------------\n";
    }
}
