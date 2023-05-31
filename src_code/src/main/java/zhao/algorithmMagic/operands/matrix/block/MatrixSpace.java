package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.Matrix;
import zhao.algorithmMagic.operands.vector.Vector;
import zhao.algorithmMagic.utils.ASClass;

/**
 * 矩阵块抽象类，其中维护了一个矩阵数组，数组中每一个元素都属于一个矩阵对象。
 * <p>
 * Matrix block abstract class, which maintains a matrix array. Each element in the array belongs to a matrix object.
 *
 * @param <ImplementationType> 矩阵块实现类数据类型。
 *                             <p>
 *                             Matrix block implementation class data type.
 * @param <ElementType>        矩阵块中，每一层矩阵对象中的每一个元素类型。
 *                             <p>
 *                             Each element type in each layer of matrix object in the matrix block。
 * @param <ArrayType>          矩阵块中每一个矩阵内的行向量的数组类型。
 *                             <p>
 *                             The array type of row vectors in each matrix in the matrix block.
 * @param <MatrixType>         矩阵快中，每一层矩阵中的矩阵类型。
 *                             <p>
 *                             Matrix fast, the matrix type in each layer of matrix.
 */
public abstract class MatrixSpace<ImplementationType extends Matrix<?, ?, ?, ?, ?>, ElementType, ArrayType, MatrixType
        extends Matrix<?, ElementType, ?, ?, ?>>
        extends Matrix<ImplementationType, ElementType, ArrayType, MatrixType, MatrixType[]> {
    private final int layer;

    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount    矩阵中的行数量
     *                    <p>
     *                    the number of rows in the matrix
     * @param colCount    矩阵中的列数量
     *                    <p>
     * @param matrixTypes 该矩阵对象中的二维数组对象。
     */
    protected MatrixSpace(int rowCount, int colCount, MatrixType[] matrixTypes) {
        super(rowCount, colCount, matrixTypes.length - 2, matrixTypes);
        this.layer = matrixTypes.length;
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
    public abstract ImplementationType expand(MatrixType[] arrayType);

    /**
     * @return 向量中包含的维度数量，在这里代表的是矩阵块中的矩阵层数。
     * <p>
     * The number of dimensions contained in the vector represents the number of matrix layers in the matrix block.
     */
    @Override
    public final int getNumberOfDimensions() {
        return this.layer;
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     * <p>
     * Get the value of the specified coordinate point in the matrix
     *
     * @param layer 空间中的矩阵层编号 从0开始
     *              <p>
     *              The matrix layer number in the space starts from 0
     * @param row   行编号 从0开始
     *              <p>
     *              Line number starts from 0
     * @param col   列编号 从0开始
     *              <p>
     *              Column number starts from 0
     * @return 矩阵中指定行列坐标的数值
     * <p>
     * The value of the specified row and column coordinates in the matrix
     */
    public final ElementType get(int layer, int row, int col) {
        return this.get(layer).get(row, col);
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     *
     * @param layer 矩阵块中的层编号，一个矩阵块是由很多矩阵组合成的，在这些矩阵中，每一层矩阵都有一个编号，这个编号也就是层编号。
     * @return 矩阵中的数值
     */
    public final MatrixType get(int layer) {
        return this.toArrays()[layer];
    }

    /**
     * 通过指定的空间转换函数获取到指定的矩阵空间不同的面所组成的新矩阵。
     * <p>
     * Obtain a new matrix composed of different faces in the specified matrix space through the specified space conversion function.
     *
     * @param spaceTransformation 指定的矩阵面所对应的空间转换函数接口。
     *                            <p>
     *                            The space conversion function interface corresponding to the specified matrix face.
     * @return 指定的矩阵面多组成的新矩阵对象。
     * <p>
     * A new matrix object composed of multiple specified matrix faces.
     */
    public final MatrixType get(SpaceTransformation<MatrixType[], MatrixType> spaceTransformation) {
        return spaceTransformation.function(toArrays());
    }

    /**
     * @return 获取到矩阵中当前行指针指向的矩阵对象。
     * <p>
     * Gets the matrix object pointed to by the current row pointer in the matrix.
     */
    public final MatrixType toMatrix() {
        return this.toArrays()[RowPointer];
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
    public abstract ImplementationType transpose();

    /**
     * 获取到指定索引编号的行数组
     * <p>
     * Get the row array with the specified index
     *
     * @param index 指定的行目标索引值
     *              <p>
     *              Specified row index number
     * @return 一个包含当前行元素的新数组，是支持修改的。
     * <p>
     * A new array containing the elements of the current row supports modification.
     */
    @Override
    protected ArrayType getArrayByRowIndex(int index) {
        throw new UnsupportedOperationException("请您不要在矩阵空间中调用 'getArrayByRowIndex' 方法，您需要先将矩阵从空间中获取到。\n" +
                "Please do not call the 'getArrayByRowIndex' method in the matrix space. You need to get the matrix from the space first.");
    }

    /**
     * 获取到指定索引编号的列数组
     * <p>
     * Get the col array with the specified index
     *
     * @param index 指定的列目标索引值
     *              <p>
     *              Specified col index number
     * @return 一个包含当前列元素的新数组，是支持修改的。
     * <p>
     * A new array containing the elements of the current col supports modification.
     */
    @Override
    protected ArrayType getArrayByColIndex(int index) {
        throw new UnsupportedOperationException("请您不要在矩阵空间中调用 'getArrayByColIndex' 方法，您需要先将矩阵从空间中获取到。\n" +
                "Please do not call the 'getArrayByColIndex' method in the matrix space. You need to get the matrix from the space first.");
    }

    /**
     * 将当前矩阵中的所有行拷贝到目标数组当中，需要确保目标数组的长度大于当前矩阵中的行数量。
     * <p>
     * To copy all rows from the current matrix into the target array, it is necessary to ensure that the length of the target array is greater than the number of rows in the current matrix.
     *
     * @param array 需要存储当前矩阵对象中所有行元素向量的数组。
     *              <p>
     *              An array that needs to store all row element vectors in the current matrix object.
     * @return 拷贝之后的数组对象。
     * <p>
     * The array object after copying.
     */
    @Override
    public final Vector<?, ?, ArrayType>[] toVectors(Vector<?, ?, ArrayType>[] array) {
        throw new UnsupportedOperationException("Matrix space does not support conversion to vector arrays.");
    }

    /**
     * 将当前矩阵中的所有元素进行扁平化操作，获取到扁平化之后的数组对象。
     * <p>
     * Flatten all elements in the current matrix to obtain the flattened array object.
     *
     * @return 将当前矩阵中每行元素进行扁平化之后的结果。
     * <p>
     * The result of flattening each row of elements in the current matrix.
     */
    @Override
    public final ArrayType flatten() {
        throw new OperatorOperationException("Matrix space does not support flattening operations.");
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
    public ImplementationType add(Number value) {
        MatrixType[] matrixTypes = this.copyToNewArrays();
        int index = -1;
        for (MatrixType matrixType : matrixTypes) {
            matrixTypes[++index] = ASClass.transform(matrixType.add(value));
        }
        return this.expand(matrixTypes);
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
    public ImplementationType diff(Number value) {
        MatrixType[] matrixTypes = this.copyToNewArrays();
        int index = -1;
        for (MatrixType matrixType : matrixTypes) {
            matrixTypes[++index] = ASClass.transform(matrixType.diff(value));
        }
        return this.expand(matrixTypes);
    }
}
