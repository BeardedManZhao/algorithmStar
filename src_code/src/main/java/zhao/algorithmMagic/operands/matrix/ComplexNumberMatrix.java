package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.ComplexNumber;
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
public class ComplexNumberMatrix extends Matrix<ComplexNumberMatrix, ComplexNumber> {

    private final ComplexNumber[][] complexNumbers;

    protected ComplexNumberMatrix(ComplexNumber[][] complexNumbers) {
        super(complexNumbers.length, complexNumbers[0].length, false);
        this.complexNumbers = complexNumbers;
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
        return this.complexNumbers[row][col];
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
        for (ComplexNumber[] complexNumber : this.complexNumbers) {
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
     * @return 是否使用基元类型，基元类型能更好地降低内存占用，如果您不使用基元，将会启动父类的数据容器
     * <p>
     * Whether to use primitive types, primitive types can better reduce memory usage, if you do not use primitives, the data container of the parent class will be started
     * <p>
     * 本复数矩阵中，不存在基元类型！因此本发放返回为false
     * <p>
     * In this complex matrix, there is no primitive type! So this release returns false
     */
    @Override
    public boolean isUsePrimitiveType() {
        return false;
    }

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     * 请注意这里返回的数值是所有复数的实部 虚部，格式为：a.b1(结尾处的1可能是近似值)，如果您需要使用指针获取到复数数组，请使用"toComplexNumberArray"。
     * <p>
     * Please note that the values returned here are the real and imaginary parts of all complex numbers in the format: a.b1 (the 1 at the end may be an approximation), if you need to use a pointer to get an array of complex numbers, please use "toComplexNumberArray".
     */
    @Override
    public double[] toArray() {
        ComplexNumber[] complexNumbers = toComplexNumberArray();
        double[] res = new double[complexNumbers.length];
        for (int i = 0; i < res.length; i++) {
            ComplexNumber complexNumber = complexNumbers[i];
            // 获取到虚部的数值
            int imaginary = complexNumber.getImaginary();
            // 计算出来虚部小数点后的位数，然后根据位数构建一个阈值
            int length = String.valueOf(imaginary).length();
            double len = length < 1 ? 1 : Math.pow(10, length);
            // 根据阈值生成小数点后的显示样式
            res[i] = complexNumber.getReal() + (imaginary / len) + (1 / ((len) * 10));
        }
        return res;
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    @Override
    public double[] CopyToNewArray() {
        return toArray();
    }

    /**
     * @return 返回矩阵种当前指针的复数数组
     */
    public ComplexNumber[] toComplexNumberArray() {
        if (this.RowPointer == -1) {
            return this.complexNumbers[0];
        } else {
            return this.complexNumbers[this.RowPointer];
        }
    }

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return this.complexNumbers.length * this.complexNumbers[0].length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int rowPointer = this.RowPointer;
        PointerReset();
        while (MovePointerDown()) {
            stringBuilder.append(Arrays.toString(toComplexNumberArray())).append("\n");
        }
        PointerReset(rowPointer);
        return "------------ComplexNumberMatrixStart-----------\n" +
                stringBuilder +
                "------------ComplexNumberMatrixEnd------------\n";
    }

    /**
     * @return 向量数据容器的数组形式，调用此方法，您将可以获取到该向量中的数值
     * <p>
     * The array form of the vector data container, call this method, you will get the value in the vector
     */
    @Override
    public ComplexNumber[] getVectorArrayPacking() {
        return toComplexNumberArray();
    }

    /**
     * 对向量数据进行基本的设置
     * <p>
     * Make basic settings for vector data
     *
     * @param vectorArray 向量数据容器的数组形式
     *                    <p>
     *                    Array form of vector data container
     * @deprecated 在本复数矩阵中，有关复数信息的传递应在构造函数中进行
     * <p>
     * In this complex matrix, the passing of information about complex numbers should be done in the constructor
     */
    @Override
    @Deprecated()
    protected void setVectorArrayPacking(ComplexNumber[] vectorArray) {
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
        for (int row = 0; row < this.complexNumbers.length; row++) {
            ComplexNumber[] complexNumber = this.complexNumbers[row];
            for (int col = 0; col < complexNumber.length; col++) {
                complexNumbers[row][col] = complexNumber[col].conjugate();
            }
        }
        return parse(complexNumbers);
    }
}
