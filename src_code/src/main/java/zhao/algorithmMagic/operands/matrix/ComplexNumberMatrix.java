package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.ComplexNumber;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;

/**
 * Java类于 2022/10/12 17:32:56 创建
 * <p>
 * 复数矩阵，该矩阵中的所有序列都是复数，您可以在这里进行有关复数的矩阵运算操作！
 * <p>
 * A matrix of complex numbers, where all sequences in this matrix are complex numbers, where you can perform matrix operations on complex numbers!
 *
 * @author zhao
 */
public class ComplexNumberMatrix extends Matrix<ComplexNumberMatrix, ComplexNumber, ComplexNumber[], ComplexNumber[][]> {

    private final String matrixStr;

    protected ComplexNumberMatrix(ComplexNumber[][] complexNumbers) {
        super(complexNumbers.length, complexNumbers[0].length, complexNumbers);
        final StringBuilder stringBuilder = new StringBuilder();
        int rowPointer = this.RowPointer;
        PointerReset();
        while (MovePointerDown()) {
            stringBuilder.append(Arrays.toString(toComplexNumberArray())).append("\n");
        }
        PointerReset(rowPointer);
        this.matrixStr = "------------ComplexNumberMatrixStart-----------\n" + stringBuilder + "------------ComplexNumberMatrixEnd------------\n";
    }

    public static ComplexNumberMatrix parse(ComplexNumber[]... complexNumbers) {
        return new ComplexNumberMatrix(complexNumbers);
    }

    public static ComplexNumberMatrix parse(String[]... complexNumberStrings) {
        ComplexNumber[][] complexNumbers = new ComplexNumber[complexNumberStrings.length][complexNumberStrings[0].length];
        for (int row = 0; row < complexNumberStrings.length; row++) {
            for (int col = 0; col < complexNumbers.length; col++) {
                complexNumbers[row][col] = ComplexNumber.parse(complexNumberStrings[row][col]);
            }
        }
        return new ComplexNumberMatrix(complexNumbers);
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
    public ComplexNumberMatrix transpose() {
        // 转置之后的矩阵行数是转置之前的列数
        int colCount = getColCount();
        // 转置之后的矩阵列数是转置之前的行数
        int rowCount = getRowCount();
        ComplexNumber[][] complexNumbers = new ComplexNumber[colCount][rowCount];
        // row 就是转置之后的矩阵行编号，转置之前的列编号
        for (int row = 0; row < colCount; row++) {
            // col 就是转置之后的矩阵列编号，转置之前的行编号
            for (int col = 0; col < rowCount; col++) {
                complexNumbers[row][col] = get(col, row);
            }
        }
        return parse(complexNumbers);
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     *
     * @param row 行编号 从0开始
     * @param col 列编号 从0开始
     * @return 矩阵中的数值
     */
    public ComplexNumber get(int row, int col) {
        return toArrays()[row][col];
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 将两个复数矩阵进行求和
     * <p>
     * sum two complex matrices
     */
    @Override
    public ComplexNumberMatrix add(ComplexNumberMatrix value) {
        int rowCount1 = this.getRowCount();
        int rowCount2 = value.getRowCount();
        int colCount1 = this.getColCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            ComplexNumber[][] complexNumbers = new ComplexNumber[rowCount1][colCount1];
            int rowPointer = this.RowPointer;
            int rowPointer1 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                ComplexNumber[] line = new ComplexNumber[colCount1];
                ComplexNumber[] complexNumbers1 = this.toComplexNumberArray();
                ComplexNumber[] complexNumbers2 = value.toComplexNumberArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = complexNumbers1[i].add(complexNumbers2[i]);
                }
                complexNumbers[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer;
            value.RowPointer = rowPointer1;
            return parse(complexNumbers);
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
     * 将两个复数矩阵进行做差
     * <p>
     * difference between two complex matrices
     */
    @Override
    public ComplexNumberMatrix diff(ComplexNumberMatrix value) {
        final int rowCount1 = this.getRowCount();
        final int rowCount2 = value.getRowCount();
        final int colCount1 = this.getColCount();
        final int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            ComplexNumber[][] complexNumbers = new ComplexNumber[rowCount1][colCount1];
            int rowPointer = this.RowPointer;
            int rowPointer1 = value.RowPointer;
            while (this.MovePointerDown() && value.MovePointerDown()) {
                ComplexNumber[] line = new ComplexNumber[colCount1];
                ComplexNumber[] complexNumbers1 = this.toComplexNumberArray();
                ComplexNumber[] complexNumbers2 = value.toComplexNumberArray();
                for (int i = 0; i < colCount1; i++) {
                    line[i] = complexNumbers1[i].diff(complexNumbers2[i]);
                }
                complexNumbers[this.RowPointer] = line;
            }
            this.RowPointer = rowPointer;
            value.RowPointer = rowPointer1;
            return parse(complexNumbers);
        } else {
            throw new OperatorOperationException("您在'DoubleMatrix1 add DoubleMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'DoubleMatrix1 add DoubleMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "DoubleMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nDoubleMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 计算该矩阵的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 复数矩阵的模长
     * 计算出来该矩阵的模长，可以理解为该矩阵本身也是一个向量。
     * <p>
     * After calculating the modulus length of the matrix, it can be understood that the matrix itself is also a vector.
     */
    @Override
    public ComplexNumber moduleLength() {
        int rowPointer = this.RowPointer;
        ComplexNumber complexNumber = ComplexNumber.parse(0, 0);
        PointerReset();
        while (this.MovePointerDown()) {
            ComplexNumber[] complexNumbers = toComplexNumberArray();
            for (ComplexNumber v : complexNumbers) {
                complexNumber = complexNumber.add(v);
            }
        }
        this.RowPointer = rowPointer;
        return complexNumber;
    }

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param matrix 被做乘的向量
     * @return 向量的外积
     * 矩阵每一个向量的外积
     */
    @Override
    public ComplexNumberMatrix multiply(ComplexNumberMatrix matrix) {
        int rowCount1 = this.getRowCount();
        int colCount1 = this.getColCount();
        int rowCount2 = matrix.getRowCount();
        int colCount2 = matrix.getColCount();
        int newLength = (colCount1 - 1) * colCount2;
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            ComplexNumber[][] complexNumbers = new ComplexNumber[rowCount1][newLength];
            int rowPointer1 = this.RowPointer;
            int rowPointer2 = matrix.RowPointer;
            // 迭代每一行
            while (this.MovePointerDown() && matrix.MovePointerDown()) {
                complexNumbers[this.RowPointer] = ASMath.CrossMultiplication(this.toComplexNumberArray(), matrix.toComplexNumberArray(), newLength);
            }
            this.RowPointer = rowPointer1;
            matrix.RowPointer = rowPointer2;
            return new ComplexNumberMatrix(complexNumbers);
        } else {
            throw new OperatorOperationException("您在'ComplexNumberMatrix1 multiply ComplexNumberMatrix2'的时候发生了错误，原因是两个矩阵的行列数不一致！\n" +
                    "You have an error in 'ComplexNumberMatrix1 multiply ComplexNumberMatrix2' because the number of rows and columns of the two matrices is inconsistent!\n" +
                    "ComplexNumberMatrix1 =>  rowCount = [" + rowCount1 + "]   colCount = [" + colCount1 + "]\nComplexNumberMatrix2 =>  rowCount = [" + rowCount2 + "]   colCount = [" + colCount2 + "]");
        }
    }

    /**
     * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
     * <p>
     * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
     *
     * @param vector 第二个被计算的向量对象
     *               <p>
     *               the second computed vector object
     * @return 两个向量的内积
     * 矩阵每一个向量的内积
     */
    @Override
    public ComplexNumber innerProduct(ComplexNumberMatrix vector) {
        ComplexNumber res = new ComplexNumber(0, 0);
        for (ComplexNumber[] complexNumber : toArrays()) {
            for (ComplexNumber number : complexNumber) {
                res = res.add(number);
            }
        }
        return res;
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public ComplexNumberMatrix expand() {
        return this;
    }

    /**
     * @return 返回矩阵种当前指针的复数数组
     */
    public ComplexNumber[] toComplexNumberArray() {
        if (this.RowPointer == -1) {
            return this.toArrays()[0];
        } else {
            return this.toArrays()[this.RowPointer];
        }
    }

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return this.getRowCount() * this.getColCount();
    }

    /**
     * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
     * <p>
     * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
     *
     * @param seed 打乱算法中所需要的随机种子。
     *             <p>
     *             Disrupt random seeds required in the algorithm.
     * @return 打乱之后的对象。
     * <p>
     * Objects after disruption.
     */
    @Override
    public ComplexNumberMatrix shuffle(long seed) {
        return ComplexNumberMatrix.parse(
                ASMath.shuffle(this.copyToNewArrays(), seed, false)
        );
    }

    @Override
    public String toString() {
        return this.matrixStr;
    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public ComplexNumber[] toArray() {
        return toArrays()[RowPointer];
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public ComplexNumber[] copyToNewArray() {
        ComplexNumber[] complexNumbers = new ComplexNumber[this.getColCount()];
        System.arraycopy(toArray(), 0, complexNumbers, 0, complexNumbers.length);
        return complexNumbers;
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
    @Override
    public ComplexNumber[][] copyToNewArrays() {
        ComplexNumber[][] complexNumbers = new ComplexNumber[this.getRowCount()][this.getColCount()];
        ASClass.array2DCopy(toArrays(), complexNumbers);
        return complexNumbers;
    }

    /**
     * 对复数矩阵进行共轭运算，矩阵中的每一个复数都将会被共轭。
     * <p>
     * Conjugate the complex number matrix, each complex number in the matrix will be conjugated.
     *
     * @return 被共轭运算之后的复数矩阵。
     * <p>
     * The complex matrix after being conjugated.
     */
    public ComplexNumberMatrix conjugate() {
        ComplexNumber[][] complexNumbers = new ComplexNumber[getRowCount()][getColCount()];
        for (int row = 0; row < this.getRowCount(); row++) {
            ComplexNumber[] complexNumber = this.toArrays()[row];
            for (int col = 0; col < complexNumber.length; col++) {
                complexNumbers[row][col] = complexNumber[col].conjugate();
            }
        }
        return parse(complexNumbers);
    }

    @Override
    protected void reFresh() {
        this.PointerReset();
    }

}
