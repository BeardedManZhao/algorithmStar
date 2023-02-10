package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;

import java.util.*;

/**
 * 一个整数矩阵，其中维护了一个基元数组，矩阵中基于数组提供了很多转换函数，同时也提供了对维护数组的提取与拷贝函数。
 * <p>
 * integer matrix, in which a primitive array is maintained, provides many conversion functions based on the array, and also provides extraction and copy functions for the maintained array.
 *
 * @author zhao
 */
public class IntegerMatrix extends NumberMatrix<IntegerMatrix, Integer, int[], int[][]> {

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
        super(ints.length, ints[0].length, ints);
    }

    /**
     * 指定行列值的方式拷贝出一个矩阵
     *
     * @param rowCount 矩阵行数
     * @param colCount 矩阵列数
     * @param ints     矩阵数据
     */
    protected IntegerMatrix(int rowCount, int colCount, int[][] ints) {
        super(rowCount, colCount, ints);
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
     * 根据一个文件中的数据获取到对应的整形的矩阵数据对象，目前支持通过图片与文本文件两种方式获取到整形矩阵。
     *
     * @param inputString 需要被读取的文本文件或图像文件
     * @return 构建出来的结果数据对象
     */
    public static IntegerMatrix parse(String inputString) {
        return IntegerMatrix.parse(ASIO.parseImageGetArray(inputString));
    }

    /**
     * 使用稀疏矩阵的数据构造一个完整的矩阵对象。
     * <p>
     * Use the data of sparse matrix to construct a complete matrix object.
     *
     * @param ints 稀疏矩阵数值，是一个二维数组，其中每一个数组中包含三个元素，第一个是值本身，第二，三个是值的横纵坐标。
     * @return 由稀疏矩阵所构造出来的矩阵对象
     */
    public static IntegerMatrix sparse(int[]... ints) {
        if (ints.length > 0) {
            if (ints[0].length == 3) {
                // 获取到最大的行列数值
                int rowMax = Integer.MIN_VALUE;
                int colMax = Integer.MIN_VALUE;
                for (int[] anInt : ints) {
                    int rowNum = anInt[2];
                    int colNum = anInt[1];
                    // 获取最大横坐标
                    if (rowMax < rowNum) rowMax = rowNum;
                    // 获取最大列坐标
                    if (colMax < colNum) colMax = colNum;
                }
                // 开始进行矩阵构造
                int[][] res = new int[rowMax + 1][colMax + 1];
                for (int[] anInt : ints) {
                    res[anInt[1]][anInt[2]] = anInt[0];
                }
                return new IntegerMatrix(res);
            } else {
                throw new OperatorOperationException("The array you pass should conform to the representation: Array (data, x, y).");
            }
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty.");
        }
    }

    /**
     * 将一个矩阵对象复制拷贝出来一个新的矩阵对象。
     * <p>
     * Copy a matrix object to a new matrix object.
     *
     * @param matrix 需要被拷贝的矩阵对象，该对象中的数据将会是数据源。
     *               <p>
     *               The data in the matrix object to be copied will be the data source.
     * @param copy   如果设置为true，那么代表使用深拷贝的方式将矩阵拷贝出来一份，反之则是使用浅拷贝。
     *               <p>
     *               If it is set to true, it means that a copy of the matrix will be made using the deep copy method, and vice versa.
     * @return 按照深拷贝或浅拷贝的方式，将integerMatrix 复制出一份，并返回到外界。
     * <p>
     * Make a copy of the integerMatrix and return it to the outside world in the form of deep copy or light copy.
     */
    public static IntegerMatrix parse(IntegerMatrix matrix, boolean copy) {
        return new IntegerMatrix(matrix.getRowCount(), matrix.getColCount(), copy ? matrix.copyToNewArrays() : matrix.toArrays());
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param integerVectors 用于构造矩阵的二维数组
     *                       <p>
     *                       2D array for constructing the matrix
     * @return matrix object
     */
    public static IntegerMatrix parse(IntegerVector... integerVectors) {
        if (integerVectors.length > 0) {
            int[][] res = new int[integerVectors.length][];
            int count = -1;
            for (IntegerVector integerVector : integerVectors) {
                res[++count] = integerVector.toArray();
            }
            return new IntegerMatrix(res);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    protected static void ex(double thresholdLeft, double thresholdRight, int[][] ints, int[] mid, ArrayList<int[]> res) {
        if (ASDynamicLibrary.isUseC()) {
            for (int[] anInt : ints) {
                double num = ASMath.correlationCoefficient_C(anInt, mid, mid.length);
                if (num >= thresholdLeft || num <= thresholdRight) {
                    // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                    int[] res1 = new int[anInt.length];
                    System.arraycopy(anInt, 0, res1, 0, anInt.length);
                    res.add(res1);
                }
                res.add(anInt);
            }
        } else {
            for (int[] anInt : ints) {
                double num = ASMath.correlationCoefficient(anInt, mid);
                if (num >= thresholdLeft || num <= thresholdRight) {
                    // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                    int[] res1 = new int[anInt.length];
                    System.arraycopy(anInt, 0, res1, 0, anInt.length);
                    res.add(res1);
                }
                res.add(anInt);
            }
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
                int[] ints1 = this.toArray();
                int[] ints2 = value.toArray();
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
                int[] ints1 = this.toArray();
                int[] ints2 = value.toArray();
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
        return toArrays()[row][col];
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
            int[] ints = toArray();
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
                doubles[this.RowPointer] = ASMath.CrossMultiplication(this.toArray(), matrix.toArray(), newLength);
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
                int[] ints = this.toArray();
                int[] ints1 = matrix.toArray();
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
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return getRowCount() * getColCount();
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
    public IntegerMatrix shuffle(long seed) {
        return IntegerMatrix.parse(ASMath.shuffle(this.copyToNewArrays(), seed, false));
    }

    @Override
    public String toString() {
        int[][] ints1 = this.toArrays();
        StringBuilder stringBuilder = new StringBuilder(ints1.length << 2);
        for (int[] ints : ints1) {
            stringBuilder.append(Arrays.toString(ints)).append("\n");
        }
        return "------------MatrixStart-----------\n" +
                stringBuilder +
                "------------MatrixEnd------------\n";
    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public int[] toArray() {
        return toArrays()[RowPointer];
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public int[] copyToNewArray() {
        int[] src = toArray();
        int[] res = new int[src.length];
        System.arraycopy(src, 0, res, 0, res.length);
        return res;
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
    public int[][] copyToNewArrays() {
        int[][] src = toArrays();
        int[][] res = new int[getRowCount()][getColCount()];
        ASClass.array2DCopy(src, res);
        return res;
    }

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
    public int[] getArrayByRowIndex(int index) {
        int[] doubles = toArrays()[index];
        int[] res = new int[doubles.length];
        System.arraycopy(doubles, 0, res, 0, doubles.length);
        return res;
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
    public int[] getArrayByColIndex(int index) {
        int count = -1;
        int[] res = new int[getRowCount()];
        for (int[] ints : toArrays()) {
            res[++count] = ints[index];
        }
        return res;
    }

    /**
     * 去除冗余特征维度，将当前矩阵中的每一个维度都进行方差或无向差计算，并将过于稳定的冗余特征去除。
     * <p>
     * Remove redundant feature dimensions, calculate variance or undirected difference of each dimension in the current matrix, and remove redundant features that are too stable.
     *
     * @param threshold 冗余去除阈值，代表去除的百分比，这个值应是一个小于1的数值，例如设置为0.4 代表去除掉冗余程度倒序排行中，最后40% 的维度。
     *                  <p>
     *                  Redundancy removal threshold, which represents the percentage of removal, should be a value less than 1. For example, set to 0.4 to remove the last 40% of the dimensions in the reverse order of redundancy.
     * @return 去除冗余特征维度之后的新矩阵
     * <p>
     * New matrix after removing redundant feature dimensions
     */
    @Override
    public IntegerMatrix featureSelection(double threshold) {
        if (threshold >= 1) throw Matrix.OPERATOR_OPERATION_EXCEPTION;
        // 计算出本次要去除的维度数量
        int num = (int) (getRowCount() * threshold);
        if (num <= 0) {
            return IntegerMatrix.parse(copyToNewArrays());
        } else {
            // 计算出本次剩余的维度数量
            num = getRowCount() - num;
            // 准备好一个排序集合，存储所有的离散值结果与数组
            TreeMap<Double, int[]> treeMap = new TreeMap<>(Comparator.reverseOrder());
            // 将每一个维度的向量的方差计算出来
            for (int[] ints : this.toArrays()) {
                // 计算出离散值，并将离散值与当前数组添加到集合中
                treeMap.put(ASMath.undirectedDifference(ints), ints);
            }
            // 开始获取到前 num 个数组
            int index = -1;
            int[][] res = new int[num][getColCount()];
            for (int[] value : treeMap.values()) {
                System.arraycopy(value, 0, res[++index], 0, value.length);
                --num;
                if (num == 0) break;
            }
            return IntegerMatrix.parse(res);
        }
    }

    /**
     * 删除与目标索引维度相关的所有行维度，并返回新矩阵对象。
     * <p>
     * Delete all row dimensions related to the target index dimension and return a new matrix object.
     *
     * @param index          需要被作为相关系数中心点的行编号。
     *                       <p>
     *                       The row number to be used as the center point of the correlation coefficient.
     * @param thresholdLeft  相关系数阈值，需要被删除的相关系数阈值区间左边界。
     *                       <p>
     *                       The correlation coefficient threshold is the left boundary of the correlation coefficient threshold interval to be deleted.
     * @param thresholdRight 相关系数阈值，需要被删除的相关系数阈值区间右边界。
     *                       <p>
     *                       The correlation coefficient threshold is the right boundary of the correlation coefficient threshold interval to be deleted.
     * @return 进行了相关维度删除之后构造出来的新矩阵
     * <p>
     * The new matrix constructed after deleting relevant dimensions
     */
    @Override
    public IntegerMatrix deleteRelatedDimensions(int index, double thresholdLeft, double thresholdRight) {
        if (index >= 0 && index < getRowCount()) {
            int[][] ints = toArrays();
            // 获取到当前的相关系数中心序列
            int[] mid = ints[index];
            ArrayList<int[]> res = new ArrayList<>();
            // 开始进行计算
            ex(thresholdLeft, thresholdRight, ints, mid, res);
            return IntegerMatrix.parse(res.toArray(new int[0][]));
        } else {
            return IntegerMatrix.parse(copyToNewArrays());
        }
    }

    /**
     * 将数据所维护的数组左移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the left n positions and get the result value
     *
     * @param n    被左移的次数，该数值应取值于 [0, getRowCount]
     *             <p>
     *             The number of times it is moved to the left. The value should be [0, getRowCount]
     * @param copy 本次左移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this left shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public IntegerMatrix leftShift(int n, boolean copy) {
        if (copy) {
            int[][] copyToNewArrays = this.copyToNewArrays();
            for (int[] copyToNewArray : copyToNewArrays) {
                ASMath.leftShiftNv(copyToNewArray, n);
            }
            return IntegerMatrix.parse(copyToNewArrays);
        } else {
            int[][] ints = this.toArrays();
            for (int[] anInt : ints) {
                ASMath.leftShift(anInt, n);
            }
            return this;
        }
    }

    /**
     * 将数据所维护的数组右移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the right n positions and get the result value
     *
     * @param n    被右移的次数，该数值应取值于 [0, getRowCount]
     *             <p>
     *             The number of times it is moved to the right. The value should be [0, getRowCount]
     * @param copy 本次右移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this right shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public IntegerMatrix rightShift(int n, boolean copy) {
        if (copy) {
            int[][] copyToNewArrays = this.copyToNewArrays();
            for (int[] copyToNewArray : copyToNewArrays) {
                ASMath.rightShift(copyToNewArray, n);
            }
            return IntegerMatrix.parse(copyToNewArrays);
        } else {
            int[][] ints = this.toArrays();
            for (int[] anInt : ints) {
                ASMath.rightShift(anInt, n);
            }
            return this;
        }
    }

    /**
     * 将当前对象中的元素从左向右的方向进行元素索引为宗旨的反转，实现更多的效果。
     * <p>
     * Invert the element index of the current object from left to right to achieve more results.
     *
     * @param isCopy 如果设置为true 代表反转操作会作用到一个新数组中，并不会更改源数组中的元素位置。反之则是直接更改源数组。
     *               <p>
     *               If set to true, the inversion operation will be applied to a new array, and the position of the elements in the source array will not be changed. On the contrary, you can directly change the source array.
     * @return 被反转之后的对象，该对象的数据类型与函数调用者是一致的。
     * <p>
     * The data type of the reversed object is the same as that of the function caller.
     */
    @Override
    public IntegerMatrix reverseLR(boolean isCopy) {
        if (isCopy) {
            int[][] ints1 = this.copyToNewArrays();
            for (int[] ints : ints1) {
                ASMath.arrayReverse(ints);
            }
            return IntegerMatrix.parse(ints1);
        } else {
            for (int[] ints : this.toArrays()) {
                ASMath.arrayReverse(ints);
            }
            return this;
        }
    }

    /**
     * 将当前对象中的元素从上向下的方向进行元素索引为宗旨的反转，实现更多的效果。
     * <p>
     * Invert the element index of the current object from Above to below to achieve more results.
     *
     * @param isCopy 如果设置为true 代表反转操作会作用到一个新数组中，并不会更改源数组中的元素位置。反之则是直接更改源数组。
     *               <p>
     *               If set to true, the inversion operation will be applied to a new array, and the position of the elements in the source array will not be changed. On the contrary, you can directly change the source array.
     * @return 被反转之后的对象，该对象的数据类型与函数调用者是一致的。
     * <p>
     * The data type of the reversed object is the same as that of the function caller.
     */
    @Override
    public IntegerMatrix reverseBT(boolean isCopy) {
        if (isCopy) {
            return IntegerMatrix.parse(this.copyToNewArrays());
        } else {
            ASMath.arrayReverse(this.toArrays());
            return this;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<int[]> iterator() {
        return Arrays.stream(this.toArrays()).iterator();
    }
}
