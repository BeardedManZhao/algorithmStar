package io.github.beardedManZhao.algorithmStar.operands.matrix.block;

import io.github.beardedManZhao.algorithmStar.SerialVersionUID;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;

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

    public final static Transformation<double[][], DoubleMatrix> CREATE_MAP_T = DoubleMatrix::parse;
    public final static Transformation<double[][][], DoubleMatrix[]> CREATE_MAP_INIT = ints -> new DoubleMatrix[ints.length];

    private static final long serialVersionUID = SerialVersionUID.DoubleMatrixSpace.getNum();

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
     * 使用填充的方式生成一个矩阵空间对象，其能够非常方便的将矩阵中的数值统一填充。
     *
     * @param value   需要被用于填充给矩阵空间对象的数值。
     * @param useCopy 在进行层叠加的时候是否使用深拷贝进行。
     * @param size    [层数, 行数, 列数]。
     * @return 填充好的新的矩阵空间对象。
     */
    public static DoubleMatrixSpace fill(double value, boolean useCopy, int... size) {
        if (size.length == 3) {
            final DoubleMatrix fill = DoubleMatrix.fill(value, size[1], size[2]);
            DoubleMatrix[] doubleMatrices = new DoubleMatrix[size[0]];
            doubleMatrices[0] = fill;
            for (int i = 1; i < doubleMatrices.length; i++) {
                doubleMatrices[i] = DoubleMatrix.parse(fill, useCopy);
            }
            return DoubleMatrixSpace.parse(doubleMatrices);
        } else {
            throw new OperatorOperationException("您需要在 fill 函数结尾处指定 size 参数，其分别是[层数, 行数, 列数]。\nYou need to specify the size parameter at the end of the fill function, which is [number of layers, rows, columns].\n" +
                    "Example => DoubleMatrixSpace.fill(" + value + ", 3, 20, 30)");
        }
    }

    /**
     * 提供子类类型数据，构造出当前子类的实例化对象，用于父类在不知道子类数据类型的情况下，创建子类矩阵。
     * <p>
     * Provide subclass type data, construct an instantiated object of the current subclass, and use it for the parent class to create a subclass matrix without knowing the subclass data type.
     *
     * @param arrayType 构造一个新的与当前数据类型一致的矩阵对象。
     *                  <p>
     *                  Construct a new matrix object that is consistent with the current data type.
     * @return 该类的实现类对象，用于拓展该接口的子类。
     * <p>
     * The implementation class object of this class is used to extend the subclasses of this interface.
     */
    @Override
    public DoubleMatrixSpace expand(DoubleMatrix[] arrayType) {
        return parse(arrayType);
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
     * 按照本矩阵空间的创建方式创建出一个新的矩阵对象，该函数通常用于父类需要子类帮助创建同类型的参数的场景。
     * <p>
     * Create a new matrix object according to the creation method of this matrix space, which is usually used in scenarios where the parent class needs the help of subclasses to create parameters of the same type.
     *
     * @param layer 新矩阵矩阵空间的层数。
     *              <p>
     *              The number of layers in the new matrix space.
     * @param row   新创建的矩阵空间中每个矩阵的行数。
     *              <p>
     *              The number of rows for each matrix in the newly created matrix space.
     * @param col   新创建的矩阵空间中每个矩阵的列数。
     *              <p>
     *              The number of columns for each matrix in the newly created matrix space.
     * @return 创建出来的矩阵空间对象.
     * <p>
     * The created matrix space object
     */
    @Override
    protected DoubleMatrixSpace create(int layer, int row, int col) {
        return DoubleMatrixSpace.parse(
                ASClass.map(
                        CREATE_MAP_T, CREATE_MAP_INIT, new double[layer][row][col]
                )
        );
    }

    /**
     * 按照本矩阵空间的创建方式创建出一个新的矩阵对象，该函数通常用于父类需要子类帮助创建同类型的参数的场景。
     * <p>
     * Create a new matrix object according to the creation method of this matrix space, which is usually used in scenarios where the parent class needs the help of subclasses to create parameters of the same type.
     *
     * @param data 新矩阵空间对象中的元素。
     *             <p>
     *             Elements in the new matrix space object.
     * @return 创建出来的新的矩阵空间对象。
     * <p>
     * Create a new matrix space object.
     */
    @Override
    protected DoubleMatrixSpace create(double[][][] data) {
        return DoubleMatrixSpace.parse(
                ASClass.map(
                        CREATE_MAP_T,
                        CREATE_MAP_INIT,
                        data
                )
        );
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
     * @return 当前对象或类的序列化数值，相同类型的情况下该数值是相同的。
     * <p>
     * The serialized value of the current object or class, which is the same for the same type.
     */
    @Override
    public long getSerialVersionUID() {
        return serialVersionUID;
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
     * 针对矩阵操作数的形状进行重新设定，使得矩阵中的数据维度的更改能够更加友好。
     * <p>
     * Reset the shape of the matrix operands to make changes to the data dimensions in the matrix more user-friendly.
     *
     * @param shape 需要被重新设置的新维度信息，其中包含2个维度信息，第一个代表矩阵的行数量，第二个代表矩阵的列数量。
     *              <p>
     *              The new dimension information that needs to be reset includes two dimensions: the first represents the number of rows in the matrix, and the second represents the number of columns in the matrix.
     * @return 重设之后的新矩阵对象。
     * <p>
     * The new matrix object after resetting.
     */
    @Override
    public DoubleMatrixSpace reShape(int... shape) {
        return this.create(
                ASClass.reShape(this, shape)
        );
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
     * @param value 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    @Override
    public DoubleMatrixSpace multiply(DoubleMatrixSpace value) {
        if (this.getNumberOfDimensions() < value.getNumberOfDimensions()) {
            // 获取到最小层与最大层
            int length1 = this.getNumberOfDimensions();
            int length2 = value.getNumberOfDimensions();
            DoubleMatrix[] doubleMatrixBlock = new DoubleMatrix[length2];
            int count = -1;
            // 开始进行计算合并
            for (int i = 0; i < length1; i++) {
                doubleMatrixBlock[++count] = this.get(i).multiply(value.get(i), true);
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
                doubleMatrixBlock[++count] = this.get(i).multiply(value.get(i), true);
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
        final int numberOfDimensions1 = this.getNumberOfDimensions();
        final int numberOfDimensions2 = vector.getNumberOfDimensions();
        double res = 0;
        if (numberOfDimensions1 == numberOfDimensions2) {
            final Iterator<DoubleMatrix> iterator1 = vector.iterator();
            final Iterator<DoubleMatrix> iterator2 = this.iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                res += iterator1.next().innerProduct(iterator2.next());
            }
            return res;
        } else {
            throw new OperatorOperationException("计算内积时的两个矩阵空间的矩阵层数不一致，因此无法计算矩阵空间内积。\nWhen calculating the inner product, the number of matrix layers in the two matrix spaces is inconsistent, so it is not possible to calculate the inner product in the matrix space.");
        }
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
