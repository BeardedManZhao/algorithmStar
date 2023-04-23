package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 浮点矩阵块数据对象，其中每一层矩阵由整数矩阵组成，是针对矩阵复合的有效解决方案。
 * <p>
 * Double matrix block data object, in which each layer of matrix is composed of integer matrix, is an effective solution for matrix composition.
 *
 * @author zhao
 */
public class DoubleMatrixSpace extends MatrixSpace<DoubleMatrixSpace, Double, double[][], DoubleMatrix> {
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
    protected DoubleMatrixSpace(int rowCount, int colCount, DoubleMatrix[] integerMatrices) {
        super(rowCount, colCount, integerMatrices);
    }

    public static DoubleMatrixSpace parse(DoubleMatrix... doubleMatrices) {
        if (doubleMatrices.length != 0) {
            int back_Row = doubleMatrices[0].getRowCount();
            int back_Col = doubleMatrices[0].getColCount();
            for (DoubleMatrix doubleMatrix : doubleMatrices) {
                int rowCount1 = doubleMatrix.getRowCount();
                int colCount1 = doubleMatrix.getColCount();
                if (back_Row != rowCount1 || back_Col != colCount1) {
                    throw new OperatorOperationException(
                            "发生了错误，您构造一个矩阵块的时候，需要传递很多行列相等的矩阵对象。\nAn error occurred. When you construct a matrix block, you need to pass many matrix objects with equal rows and columns.\n" +
                                    "=> expect: " + "row=" + back_Row + "\tcol=" + back_Col + "but now: row=" + rowCount1 + "\tcol=" + colCount1);
                }
            }
            return new DoubleMatrixSpace(back_Row, back_Col, doubleMatrices);
        }
        return new DoubleMatrixSpace(0, 0, doubleMatrices);
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
    public DoubleMatrixSpace transpose() {
        DoubleMatrix[] integerMatrices1 = toArrays();
        DoubleMatrix[] integerMatrices2 = new DoubleMatrix[integerMatrices1.length];
        int count = -1;
        for (DoubleMatrix doubleMatrix : integerMatrices1) {
            integerMatrices2[++count] = doubleMatrix.transpose();
        }
        return parse(integerMatrices2);
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
    @Override
    public Double get(int row, int col) {
        return get(Math.max(RowPointer, 0), row, col);
    }

    /**
     * 刷新操作数对象的所有字段
     */
    @Override
    protected void reFresh() {

    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public final double[][] toArray() {
        return toMatrix().toArrays();
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
    public Double moduleLength() {
        return toMatrix().moduleLength();
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public DoubleMatrixSpace expand() {
        return this;
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public final double[][] copyToNewArray() {
        double[][] doubles = this.toArray();
        return ASClass.array2DCopy(doubles, new double[doubles.length][]);
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
    public DoubleMatrixSpace shuffle(long seed) {
        return DoubleMatrixSpace.parse(ASMath.shuffle(toArrays(), seed, true));
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
    public DoubleMatrix[] copyToNewArrays() {
        DoubleMatrix[] doubleMatrices1 = toArrays();
        DoubleMatrix[] doubleMatrices2 = new DoubleMatrix[doubleMatrices1.length];
        System.arraycopy(doubleMatrices1, 0, doubleMatrices2, 0, doubleMatrices2.length);
        return doubleMatrices2;
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
    public DoubleMatrixSpace add(DoubleMatrixSpace value) {
        if (this.getNumberOfDimensions() < value.getNumberOfDimensions()) {
            // 获取到最小层与最大层
            int length1 = this.getNumberOfDimensions();
            int length2 = value.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).add(value.get(i));
            }
            // 将剩余数据合并
            int length3 = value.getNumberOfDimensions() - count;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = value.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
        } else {
            // 获取到最小层与最大层
            int length1 = value.getNumberOfDimensions();
            int length2 = this.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).add(value.get(i));
            }
            // 将剩余数据合并
            int length3 = this.getNumberOfDimensions() - count - 1;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = this.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
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
    public DoubleMatrixSpace diff(DoubleMatrixSpace value) {
        if (this.getNumberOfDimensions() < value.getNumberOfDimensions()) {
            // 获取到最小层与最大层
            int length1 = this.getNumberOfDimensions();
            int length2 = value.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).diff(value.get(i));
            }
            // 将剩余数据合并
            int length3 = value.getNumberOfDimensions() - count;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = value.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
        } else {
            // 获取到最小层与最大层
            int length1 = value.getNumberOfDimensions();
            int length2 = this.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).diff(value.get(i));
            }
            // 将剩余数据合并
            int length3 = this.getNumberOfDimensions() - count - 1;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = this.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
        }
    }

    /**
     * 在两个向量对象之间进行计算的函数，自从1.13版本开始支持该函数的调用，该函数中的计算并不会产生一个新的向量，而是将计算操作作用于原操作数中
     * <p>
     * The function that calculates between two vector objects supports the call of this function since version 1.13. The calculation in this function will not generate a new vector, but will apply the calculation operation to the original operand
     *
     * @param value        与当前向量一起进行计算的另一个向量对象。
     *                     <p>
     *                     Another vector object that is evaluated with the current vector.
     * @param ModifyCaller 计算操作作用对象的设置，该参数如果为true，那么计算时针对向量序列的修改操作将会直接作用到调用函数的向量中，反之将会作用到被操作数中。
     *                     <p>
     *                     The setting of the calculation operation action object. If this parameter is true, the modification of the vector sequence during calculation will directly affect the vector of the calling function, and vice versa.
     * @return 两个向量经过了按维度的减法计算之后，被修改的向量对象
     */
    @Override
    public DoubleMatrixSpace diffAbs(DoubleMatrixSpace value, boolean ModifyCaller) {
        if (this.getNumberOfDimensions() < value.getNumberOfDimensions()) {
            // 获取到最小层与最大层
            int length1 = this.getNumberOfDimensions();
            int length2 = value.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).diffAbs(value.get(i), ModifyCaller);
            }
            // 将剩余数据合并
            int length3 = value.getNumberOfDimensions() - count;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = value.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
        } else {
            // 获取到最小层与最大层
            int length1 = value.getNumberOfDimensions();
            int length2 = this.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).diffAbs(value.get(i), ModifyCaller);
            }
            // 将剩余数据合并
            int length3 = this.getNumberOfDimensions() - count - 1;
            for (int i = 0; i < length3; i++) {
                doubleMatrixBlock[++count] = this.get(i);
            }
            // 返回结果
            return new DoubleMatrixSpace(this.getRowCount(), this.getColCount(), doubleMatrixBlock);
        }
    }


    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    @Override
    public DoubleMatrixSpace multiply(DoubleMatrixSpace vector) {
        return null;
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
     * waiting to be realized
     */
    @Override
    public Double innerProduct(DoubleMatrixSpace vector) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        for (DoubleMatrix doubleMatrix : toArrays()) {
            stringBuilder.append(doubleMatrix);
        }
        return stringBuilder.toString();
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<DoubleMatrix> iterator() {
        return Arrays.stream(this.toArrays()).iterator();
    }
}
