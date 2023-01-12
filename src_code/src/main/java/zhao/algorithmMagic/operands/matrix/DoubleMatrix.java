package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/11 20:02:06 创建
 * <p>
 * 一个浮点矩阵，其中维护了一个基元数组，矩阵中基于数组提供了很多转换函数，同时也提供了对维护数组的提取与拷贝函数。
 * <p>
 * A floating point matrix, in which a primitive array is maintained, provides many conversion functions based on the array, and also provides extraction and copy functions for the maintained array.
 *
 * @author zhao
 */
public class DoubleMatrix extends Matrix<DoubleMatrix, Double> {

    private final double[][] VectorArrayPrimitive;
    private final String matrixStr;

    /**
     * 构造一个矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param doubles 矩阵中的元素数值，该数值将会被直接应用到本类中，因此在外界请勿更改。
     *                <p>
     *                The element value in the matrix will be directly applied to this class, so do not change it outside.
     */
    protected DoubleMatrix(double[]... doubles) {
        super(doubles.length, doubles[0].length);
        this.VectorArrayPrimitive = doubles;
        matrixStr = super.toString();
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param doubles 用于构造矩阵的二维数组
     *                <p>
     *                2D array for constructing the matrix
     * @return matrix object
     */
    public static DoubleMatrix parse(double[]... doubles) {
        if (doubles.length > 0) {
            return new DoubleMatrix(doubles);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     *
     * @param row 行编号 从0开始
     * @param col 列编号 从0开始
     * @return 矩阵中的数值
     */
    @Override
    public Double get(int row, int col) {
        return VectorArrayPrimitive[row][col];
    }

    /**
     * 将现有矩阵的转置矩阵获取到
     * <p>
     * Get the transpose of an existing matrix into
     *
     * @return 矩阵转置之后的新矩阵
     * <p>
     * new matrix after matrix transpose
     * TODO
     */
    @Override
    public DoubleMatrix transpose() {
        // 转置之后的矩阵行数是转置之前的列数
        int colCount = getColCount();
        // 转置之后的矩阵列数是转置之前的行数
        int rowCount = getRowCount();
        double[][] res = new double[colCount][rowCount];
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
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 将两个不同的矩阵对象进行求和，如果是相同的矩阵对象，请先调用"clone"进行克隆！
     * <p>
     * Sum two different matrix objects, if it is the same matrix object, please call "clone" first to clone!
     */
    @Override
    public DoubleMatrix add(DoubleMatrix value) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = value.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            double[][] doubles = new double[rowCount1][colCount1];
            int rowPointer = this.RowPointer;
            int rowPointer1 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                double[] line = new double[colCount1];
                double[] doubles1 = this.toDoubleArray();
                double[] doubles2 = value.toDoubleArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = doubles1[i] + doubles2[i];
                }
                doubles[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer;
            value.RowPointer = rowPointer1;
            return parse(doubles);
        } else {
            throw new OperatorOperationException("您在'DoubleMatrix1 add DoubleMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'DoubleMatrix1 add DoubleMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "DoubleMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nDoubleMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * 将两个不同的矩阵对象进行做差，如果是相同的矩阵对象，请先调用"clone"进行克隆！因为同一个矩阵中进行运算的时候会使用到行指针，该指针如果被同时操纵，会导致数据混乱。
     * <p>
     * Differentiate two different matrix objects. If they are the same matrix object, please call "clone" first to clone! Because the row pointer is used when performing operations in the same matrix, if the pointer is manipulated at the same time, it will cause data confusion.
     */
    @Override
    public DoubleMatrix diff(DoubleMatrix value) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = value.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            double[][] doubles = new double[rowCount1][colCount1];
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                double[] line = new double[colCount1];
                double[] doubles1 = toDoubleArray();
                double[] doubles2 = value.toDoubleArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = doubles1[i] - doubles2[i];
                }
                doubles[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer1;
            value.RowPointer = rowPointer2;
            return parse(doubles);
        } else {
            throw new OperatorOperationException("您在'DoubleMatrix1 diff DoubleMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'DoubleMatrix1 diff DoubleMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "DoubleMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nDoubleMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 计算该矩阵的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 矩阵的模长
     * 矩阵的模长
     */
    @Override
    public Double moduleLength() {
        double res = 0;
        int rowPointer = this.RowPointer;
        PointerReset();
        while (this.MovePointerDown()) {
            double[] doubles1 = toDoubleArray();
            for (double v : doubles1) {
                res += ASMath.Power2(v);
            }
        }
        this.RowPointer = rowPointer;
        return res;
    }

    /**
     * 两个矩阵相乘，同时也是两个矩阵的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param matrix 被做乘的矩阵
     * @return 矩阵的外积
     * 矩阵的外积
     */
    @Override
    public DoubleMatrix multiply(DoubleMatrix matrix) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = matrix.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = matrix.getColCount();
        int newLength = (colCount1 - 1) * colCount2;
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            double[][] doubles = new double[rowCount1][newLength];
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = matrix.RowPointer;
            // 迭代每一行
            while (this.MovePointerDown() && matrix.MovePointerDown()) {
                doubles[this.RowPointer] = ASMath.CrossMultiplication(this.toDoubleArray(), matrix.toDoubleArray(), newLength);
            }
            this.RowPointer = rowPointer1;
            matrix.RowPointer = rowPointer2;
            return new DoubleMatrix(doubles).PointerReset();
        } else {
            throw new OperatorOperationException("您在'DoubleMatrix1 multiply DoubleMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'DoubleMatrix1 multiply DoubleMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "DoubleMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nDoubleMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 计算两个矩阵的内积，也称之为数量积，具体实现请参阅api说明
     * <p>
     * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
     *
     * @param matrix 第二个被计算的矩阵对象
     *               <p>
     *               the second computed vector object
     * @return 两个矩阵的内积
     * 两个矩阵的内积运算函数。
     * <p>
     * The inner product operator function for two matrices.
     */
    @Override
    public Double innerProduct(DoubleMatrix matrix) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = matrix.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = matrix.getColCount();
        if (rowCount1 == rowCount2) {
            double res = 0;
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = matrix.RowPointer;
            while (this.MovePointerDown() && matrix.MovePointerDown()) {
                double[] doubles1 = this.toDoubleArray();
                double[] doubles2 = matrix.toDoubleArray();
                for (int i = 0; i < doubles1.length; i++) {
                    res += doubles1[i] * doubles2[i];
                }
            }
            this.RowPointer = rowPointer1;
            matrix.RowPointer = rowPointer2;
            return res;
        } else {
            throw new OperatorOperationException("您在'DoubleMatrix1 innerProduct DoubleMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'DoubleMatrix1 innerProduct DoubleMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "DoubleMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nDoubleMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public DoubleMatrix expand() {
        return this;
    }

    /**
     * @return 获取到本矩阵中的某一行数据，按照行指针获取，通过调整外界行指针的变化，来获取到对应行数据，需要注意的是，该函数获取到的数据矩阵对象中正在使用的，如果返回值被更改，那么会导致一些不可意料的情况发生。
     * <p>
     * Get the data of a certain row in this matrix according to the row pointer, and get the data of the corresponding row by adjusting the changes of the external row pointer. Note that if the return value of the data matrix object obtained by this function is being used, it will lead to some unexpected situations.
     */
    public double[] toDoubleArray() {
        return this.VectorArrayPrimitive[super.RowPointer];
    }

    /**
     * @return 获取到本矩阵中的所有数据，需要注意的是，该函数获取到的数据矩阵对象中正在使用的，如果返回值被更改，那么会导致一些不可意料的情况发生。
     * <p>
     * Get all the data in this matrix. Note that if the return value of the data matrix object obtained by this function is changed, some unexpected situations will occur.
     */
    public double[][] toDoubleArrays() {
        return this.VectorArrayPrimitive;
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    public double[] CopyToNewDoubleArray() {
        final double[] res = new double[this.getColCount()];
        System.arraycopy(this.toDoubleArray(), 0, res, 0, res.length);
        return res;
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
        return new int[0];
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    @Override
    public int[] CopyToNewIntArray() {
        return new int[0];
    }

    /**
     * @return 返回该矩阵中所有行数据的数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改。
     * <p>
     * Returns the array form of all row data in the matrix. Since it is copied, it will not generate any dependency, so it supports modification.
     */
    public double[][] CopyToNewDoubleArrays() {
        final double[][] res = new double[this.getRowCount()][this.getColCount()];
        ASClass.array2DCopy(this.VectorArrayPrimitive, res);
        return res;
    }

    /**
     * @return 获取到本矩阵中的所有数据，需要注意的是，该函数获取到的数据矩阵对象中正在使用的，如果返回值被更改，那么会导致一些不可意料的情况发生。
     * <p>
     * Get all the data in this matrix. Note that if the return value of the data matrix object obtained by this function is changed, some unexpected situations will occur.
     */
    @Override
    public int[][] toIntArrays() {
        int[][] res = new int[this.getRowCount()][this.getColCount()];
        for (int i = 0; i < this.toDoubleArrays().length; i++) {
            res[i] = ASClass.DoubleArray_To_IntArray(this.VectorArrayPrimitive[i]);
        }
        return res;
    }

    /**
     * @return 返回该矩阵中所有行数据的数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改。
     * <p>
     * Returns the array form of all row data in the matrix. Since it is copied, it will not generate any dependency, so it supports modification.
     */
    @Override
    public int[][] CopyToNewIntArrays() {
        return toIntArrays();
    }

    /**
     * @return 矩阵中包含的维度数量，这里是矩阵的行列之积
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return getRowCount() * getColCount();
    }

    @Override
    public String toString() {
        return this.matrixStr;
    }

    @Override
    protected void reFresh() {
        this.PointerReset();
    }
}
