package io.github.beardedManZhao.algorithmStar.operands.matrix.block;

import io.github.beardedManZhao.algorithmStar.SerialVersionUID;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 整数矩阵块数据对象，其中每一层矩阵由整数矩阵组成，是针对矩阵复合的有效解决方案。
 * <p>
 * Integer matrix block data object, in which each layer of matrix is composed of integer matrix, is an effective solution for matrix composition.
 *
 * @author zhao
 */
public class IntegerMatrixSpace extends MatrixSpace<IntegerMatrixSpace, Integer, int[][], IntegerMatrix> {

    public final static Transformation<int[][], IntegerMatrix> CREATE_MAP_T = IntegerMatrix::parse;
    public final static Transformation<int[][][], IntegerMatrix[]> CREATE_MAP_INIT = ints -> new IntegerMatrix[ints.length];

    private static final long serialVersionUID = SerialVersionUID.IntegerMatrixSpace.getNum();

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
    protected IntegerMatrixSpace(int rowCount, int colCount, IntegerMatrix[] integerMatrices) {
        super(rowCount, colCount, integerMatrices);
    }

    public static IntegerMatrixSpace parse(IntegerMatrix... integerMatrices) {
        if (integerMatrices.length != 0) {
            int back_Row = integerMatrices[0].getRowCount();
            int back_Col = integerMatrices[0].getColCount();
            for (IntegerMatrix integerMatrix : integerMatrices) {
                int rowCount1 = integerMatrix.getRowCount();
                int colCount1 = integerMatrix.getColCount();
                if (back_Row != rowCount1 || back_Col != colCount1) {
                    throw new OperatorOperationException(
                            "发生了错误，您构造一个矩阵块的时候，需要传递很多行列相等的矩阵对象。\nAn error occurred. When you construct a matrix block, you need to pass many matrix objects with equal rows and columns.\n" +
                                    "=> expect : " + "row=" + back_Row + "\tcol=" + back_Col + "\n=> but now: row=" + rowCount1 + "\tcol=" + colCount1);
                }
            }
            return new IntegerMatrixSpace(back_Row, back_Col, integerMatrices);
        }
        return new IntegerMatrixSpace(0, 0, integerMatrices);
    }

    /**
     * 根据一个文件中的数据获取到对应的整形的矩阵数据对象，目前支持通过图片获取到对应的像素整形矩阵。
     *
     * @param inputString 需要被读取的文本文件或图像文件
     * @param wh          额外可选参数，其中代表读取进来之后进行变换的宽高。
     * @return 构建出来的矩阵空间对象，其中空间有很多层矩阵，每一层矩阵都是图像的一个通道。
     */
    public static IntegerMatrixSpace parse(String inputString, int... wh) {
        return parse(ASIO.parseImageGetArrays(inputString, wh));
    }

    /**
     * 根据一个文件中的数据获取到对应的整形的矩阵数据对象，目前支持通过图片获取到对应的像素整形矩阵。
     *
     * @param inputString 需要被读取的文本文件或图像文件
     * @param wh          额外可选参数，其中代表读取进来之后进行变换的宽高。
     * @return 构建出来的矩阵空间对象，其中空间有很多层矩阵，每一层矩阵都是图像的一个通道。
     */
    public static IntegerMatrixSpace parse(URL inputString, int... wh) {
        try {
            if (wh.length == 2) {
                return parse(ASIO.parseImageGetArrays(ImageIO.read(inputString), wh[0], wh[1]));
            } else {
                return parse(ASIO.parseImageGetArrays(ImageIO.read(inputString)));
            }
        } catch (IOException e) {
            throw new OperatorOperationException(e);
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
    public IntegerMatrixSpace expand(IntegerMatrix[] arrayType) {
        return parse(arrayType);
    }

    @Override
    public Integer get(int row, int col) {
        return get(Math.max(RowPointer, 0), row, col);
    }

    @Override
    protected void reFresh() {

    }

    @Override
    public final int[][] toArray() {
        return toMatrix().toArrays();
    }

    @Override
    public Integer moduleLength() {
        return toMatrix().moduleLength();
    }

    @Override
    public IntegerMatrixSpace expand() {
        return this;
    }

    @Override
    public final int[][] copyToNewArray() {
        return this.toMatrix().toArrays();
    }

    @Override
    public IntegerMatrixSpace shuffle(long seed) {
        return IntegerMatrixSpace.parse(ASMath.shuffle(toArrays(), seed, true));
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

    @Override
    public IntegerMatrix[] copyToNewArrays() {
        IntegerMatrix[] integerMatrix1 = toArrays();
        IntegerMatrix[] integerMatrix2 = new IntegerMatrix[integerMatrix1.length];
        System.arraycopy(integerMatrix1, 0, integerMatrix2, 0, integerMatrix2.length);
        return integerMatrix2;
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
    public IntegerMatrixSpace reShape(int... shape) {
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
    public IntegerMatrixSpace add(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).add(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixSpace diff(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).diff(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
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
    public IntegerMatrixSpace diffAbs(IntegerMatrixSpace value, boolean ModifyCaller) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).diffAbs(value.get(i), ModifyCaller);
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public IntegerMatrixSpace multiply(IntegerMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        IntegerMatrix[] integerMatrices = new IntegerMatrix[length];
        int count = -1;
        // 开始进行计算合并
        for (int i = 0; i < length; i++) {
            integerMatrices[++count] = this.get(i).multiply(value.get(i));
        }
        // 返回结果
        return new IntegerMatrixSpace(this.getRowCount(), this.getColCount(), integerMatrices);
    }

    @Override
    public Integer innerProduct(IntegerMatrixSpace value) {
        // 获取到维度长度
        int length1 = this.getNumberOfDimensions();
        int res = 0;
        // 开始进行计算
        IntegerMatrix[] integerMatrices1 = this.toArrays();
        IntegerMatrix[] integerMatrices2 = value.toArrays();
        for (int i = 0; i < length1; i++) {
            res += integerMatrices1[i].innerProduct(integerMatrices2[i]);
        }
        return res;
    }

    @Override
    public IntegerMatrixSpace transpose() {
        IntegerMatrix[] integerMatrices1 = toArrays();
        IntegerMatrix[] integerMatrices2 = new IntegerMatrix[integerMatrices1.length];
        int count = -1;
        for (IntegerMatrix integerMatrix : integerMatrices1) {
            integerMatrices2[++count] = integerMatrix.transpose();
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
    protected IntegerMatrixSpace create(int layer, int row, int col) {
        return IntegerMatrixSpace.parse(
                ASClass.map(
                        CREATE_MAP_T,
                        CREATE_MAP_INIT,
                        new int[layer][row][col]
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
    protected IntegerMatrixSpace create(int[][][] data) {
        return IntegerMatrixSpace.parse(
                ASClass.map(
                        CREATE_MAP_T,
                        CREATE_MAP_INIT,
                        data
                )
        );
    }

    /**
     * 对矩阵空间进行卷积计算，在卷积计算的时候会产生出一个更小的特征矩阵。
     *
     * @param width     矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     * @param height    矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
     * @param weightMat 在进行卷积计算时需要的卷积核矩阵对象，该对象的宽高应与卷积函数的形参一致。
     *                  <p>
     *                  The convolution kernel matrix object required for convolution computation, whose width and height should be consistent with the formal parameters of the convolution function.
     * @return 矩阵空间卷积结果特征图，保持三通道的格式返回。
     */
    public IntegerMatrixSpace folding(int width, int height, IntegerMatrixSpace weightMat) {
        int layouts = this.getNumberOfDimensions();
        checkAllDimensions(width, height, layouts, weightMat.getColCount(), weightMat.getRowCount(), weightMat.getNumberOfDimensions());
        // 一切都合法，开始进行卷积 获取到所有通道的图像与其权重的内积。
        // 准备结果数据容器
        IntegerMatrix[] result = new IntegerMatrix[layouts];
        for (int i = 0; i < layouts; i++) {
            IntegerMatrix now = this.get(i);
            IntegerMatrix nowWeight = weightMat.get(i);
            // 这张图像中需要被提取的坐标 以及最大坐标
            int colCount = now.getColCount();
            int rowCount = now.getRowCount();
            int row = 0, col = 0, maxRow = rowCount - row, maxCol = colCount - col;
            // 图像中的下一层坐标点 以及结果数据容器的坐标标记
            int nextRow = height - 1, nextCol = width - 1, resRow = 0, resCol = 0;
            int[][] temp = new int[Math.min(maxRow, rowCount - nextRow)][Math.min(maxCol, colCount - nextCol)];
            while (resRow < maxRow && nextRow < rowCount) {
                while (resCol < maxCol && nextCol < colCount) {
                    // 提取小区域图像 并与 权重进行卷积计算 并存储结果数据
                    temp[resRow][resCol++] = now.extractMat(col, row, nextCol, nextRow).innerProduct(nowWeight);
                    // 移动一列
                    col++;
                    nextCol++;
                }
                // 移动一行
                resRow++;
                row++;
                nextRow++;
                // 复位 x 轴
                resCol = 0;
                col = 0;
                nextCol = width - 1;
            }
            // 将这一层矩阵赋予结果矩阵
            result[i] = IntegerMatrix.parse(temp);
        }
        // 返回结果三维矩阵
        return IntegerMatrixSpace.parse(result);
    }

    /**
     * 对矩阵空间进行卷积计算，在卷积计算的时候会产生出一个更小的特征矩阵。
     *
     * @param width     矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     *                  <p>
     *                  When performing convolution operations on a matrix, it is best to choose a value that can be evenly divided by the number of columns in the matrix.
     * @param height    矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
     *                  <p>
     *                  When performing convolution operations on a matrix, it is best to select a value that can be evenly divided by the number of rows in the matrix.
     * @param weightMat 在进行卷积计算时需要的卷积核矩阵对象，该对象的宽高应与卷积函数的形参一致。
     *                  <p>
     *                  The convolution kernel matrix object required for convolution computation, whose width and height should be consistent with the formal parameters of the convolution function.
     * @return 矩阵空间卷积结果特征图，保持三通道的格式返回。
     */
    public IntegerMatrixSpace folding(int width, int height, DoubleMatrixSpace weightMat) {
        int layouts = this.getNumberOfDimensions();
        checkAllDimensions(width, height, layouts, weightMat.getColCount(), weightMat.getRowCount(), weightMat.getNumberOfDimensions());
        // 一切都合法，开始进行卷积 获取到所有通道的图像与其权重的内积。
        // 准备结果数据容器
        IntegerMatrix[] result = new IntegerMatrix[layouts];
        int i = -1;
        while (++i < layouts) {
            IntegerMatrix now = this.get(i);
            DoubleMatrix nowWeight = weightMat.get(i);
            // 这张图像中需要被提取的坐标 以及最大坐标
            int colCount = now.getColCount(), rowCount = now.getRowCount(), row = 0, col = 0, maxRow = rowCount - row, maxCol = colCount - col;
            // 图像中的下一层坐标点 以及结果数据容器的坐标标记
            int nextRow = height - 1, nextCol = width - 1;
            int[][] temp = new int[Math.min(maxRow, rowCount - nextRow)][Math.min(maxCol, colCount - nextCol)];
            int resRow = 0, resCol = 0;
            while (!(resRow >= maxRow || nextRow >= rowCount)) {
                while (!(resCol >= maxCol || nextCol >= colCount)) {
                    // 提取小区域图像 并与 权重进行卷积计算 并存储结果数据
                    temp[resRow][resCol++] = now.extractMat(col, row, nextCol, nextRow).innerProduct(nowWeight);
                    // 移动一列
                    col++;
                    nextCol++;
                }
                // 移动一行
                resRow++;
                row++;
                nextRow++;
                // 复位 x 轴
                resCol = 0;
                col = 0;
                nextCol = width - 1;
            }
            // 将这一层矩阵赋予结果矩阵
            result[i] = IntegerMatrix.parse(temp);
        }
        // 返回结果三维矩阵
        return IntegerMatrixSpace.parse(result);
    }

    /**
     * 对矩阵空间进行卷积计算，在卷积计算的时候会产生出一个更小的特征矩阵。
     *
     * @param width  矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     *               <p>
     *               When performing convolution operations on a matrix, it is best to choose a value that can be evenly divided by the number of columns in the matrix.
     * @param height 矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
     *               <p>
     *               When performing convolution operations on a matrix, it is best to select a value that can be evenly divided by the number of rows in the matrix.
     * @param kernel 矩阵卷积操作进行时要使用的卷积核。
     *               <p>
     *               The convolution kernel to use when performing matrix convolution operations.
     * @return 矩阵空间卷积结果特征图，保持三通道的格式返回。
     */
    public ColorMatrix foldingAndSumRGB(int width, int height, Kernel kernel) {
        return this.foldingAndSumRGB(width, height, kernel.getKernel(false, this.getNumberOfDimensions(), width, height));
    }

    /**
     * 检查当亲对象中的所有维度
     *
     * @param width              被检查的宽度
     * @param height             被检查的高度
     * @param layouts            被检查的层数
     * @param colCount2          标准宽度
     * @param rowCount2          标准高度
     * @param numberOfDimensions 标准层数
     */
    public void checkAllDimensions(int width, int height, int layouts, int colCount2, int rowCount2, int numberOfDimensions) {
        if (width != colCount2 || height != rowCount2) {
            // 代表权重的宽高与截取小区域的宽高不一致
            throw new OperatorOperationException("请您确保权重权重的宽高与截取小区域的宽高保持一致\nPlease ensure that the width and height of the weight weight are consistent with the width and height of the intercepted small area");
        }
        if (numberOfDimensions != layouts) {
            // 代表通道数量不相同
            throw new OperatorOperationException("在进行矩阵空间卷积时发生了错误，提供的特征矩阵空间的通道数量与本通道的矩阵通道数量不一致，因此无法进行计算。\nAn error occurred during the convolution of matrix space. The number of channels in the provided characteristic matrix space is inconsistent with the number of matrix channels in this channel, so it cannot be calculated.");
        }
        if (width < 0 || width > this.getColCount()) {
            // 代表宽度不合法
            throw new OperatorOperationException("在进行空间卷积的死后发生了错误，提供二点特征矩阵图宽度不合法!!!\nAn error occurred after the death of spatial convolution. It is illegal to provide the width of two-point characteristic matrix!!!\nERROR => " +
                    width);
        }
        if (height < 0 || height > this.getRowCount()) {
            // 代表宽度不合法
            throw new OperatorOperationException("在进行空间卷积的死后发生了错误，提供二点特征矩阵图高度不合法!!!\nAn error occurred after the death of spatial convolution. It is illegal to provide the height of two-point characteristic matrix!!!\nERROR => " +
                    height);
        }
    }

    /**
     * 对矩阵空间进行卷积计算，在卷积计算的时候会产生出一个更小的特征矩阵。
     *
     * @param width     矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     * @param height    矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
     * @param weightMat 在进行卷积计算时需要的卷积核矩阵对象，该对象的宽高应与卷积函数的形参一致。
     *                  <p>
     *                  The convolution kernel matrix object required for convolution computation, whose width and height should be consistent with the formal parameters of the convolution function.
     * @return 矩阵空间卷积结果特征图，以三原色通道之和的方式返回一个矩阵。
     */
    public final IntegerMatrix foldingAndSum(int width, int height, IntegerMatrixSpace weightMat) {
        IntegerMatrix res = null;
        for (IntegerMatrix ints : folding(width, height, weightMat)) {
            if (res == null) {
                res = ints;
                continue;
            }
            res = res.add(ints);
        }
        return res;
    }

    /**
     * 将三个颜色通道分别进行与之对应的卷积计算操作，并将卷积结果进行颜色通道的求和。
     * <p>
     * Perform corresponding convolution calculations on the three color channels, and sum the convolution results for the color channels.
     *
     * @param width     卷积子矩阵的宽度。
     *                  <p>
     *                  The width of the convolutional sub-matrix.
     * @param height    卷积子矩阵的高度。
     *                  <p>
     *                  The height of the convolutional sub-matrix.
     * @param weightMat 卷积核对象。
     *                  <p>
     *                  Convolutional kernel object.
     * @return 卷积计算结果，其是三个颜色通道卷积结果矩阵的合并图像矩阵对象。
     * <p>
     * The convolution calculation result is a merged image matrix object of three color channel convolution result matrices.
     */
    public final ColorMatrix foldingAndSumRGB(int width, int height, IntegerMatrixSpace weightMat) {
        if (this.getNumberOfDimensions() != 3) {
            throw new OperatorOperationException("合并RGB图像矩阵失败，您的矩阵空间没有3层通道。");
        }
        IntegerMatrixSpace folding = folding(width, height, weightMat);
        // 合并三层通道的色彩数值
        int[][] redMat = folding.get(0).toArrays(), greenMat = folding.get(1).toArrays(), blueMat = folding.get(2).toArrays();
        Color[][] colors = new Color[folding.getRowCount()][folding.getColCount()];
        int y = -1;
        for (Color[] color : colors) {
            int[] r = redMat[++y];
            int[] g = greenMat[y];
            int[] b = blueMat[y];
            int i = 0;
            while (i < color.length) {
                color[i] = new Color(
                        ASMath.regularTricolor(r[i]), ASMath.regularTricolor(g[i]), ASMath.regularTricolor(b[i])
                );
                i++;
            }
        }
        return ColorMatrix.parse(colors);
    }

    /**
     * 将三个颜色通道分别进行与之对应的卷积计算操作，并将卷积结果进行颜色通道的求和。
     * <p>
     * Perform corresponding convolution calculations on the three color channels, and sum the convolution results for the color channels.
     *
     * @param width     卷积子矩阵的宽度。
     *                  <p>
     *                  The width of the convolutional sub-matrix.
     * @param height    卷积子矩阵的高度。
     *                  <p>
     *                  The height of the convolutional sub-matrix.
     * @param weightMat 卷积核对象。
     *                  <p>
     *                  Convolutional kernel object.
     * @return 卷积计算结果，其是三个颜色通道卷积结果矩阵的合并图像矩阵对象。
     * <p>
     * The convolution calculation result is a merged image matrix object of three color channel convolution result matrices.
     */
    public final ColorMatrix foldingAndSumRGB(int width, int height, DoubleMatrixSpace weightMat) {
        if (this.getNumberOfDimensions() != 3) {
            throw new OperatorOperationException("合并RGB图像矩阵失败，您的矩阵空间没有3层通道。");
        }
        IntegerMatrixSpace folding = folding(width, height, weightMat);
        // 合并三层通道的色彩数值
        int[][] redMat = folding.get(0).toArrays();
        int[][] greenMat = folding.get(1).toArrays();
        int[][] blueMat = folding.get(2).toArrays();
        Color[][] colors = new Color[folding.getRowCount()][folding.getColCount()];
        int y = -1;
        for (Color[] color : colors) {
            int[] r = redMat[++y];
            int[] g = greenMat[y];
            int[] b = blueMat[y];
            for (int i = 0; i < color.length; i++) {
                color[i] = new Color(
                        ASMath.regularTricolor(r[i]), ASMath.regularTricolor(g[i]), ASMath.regularTricolor(b[i])
                );
            }
        }
        return ColorMatrix.parse(colors);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        for (IntegerMatrix integerMatrix : toArrays()) {
            stringBuilder.append(integerMatrix);
        }
        return stringBuilder.toString();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<IntegerMatrix> iterator() {
        return Arrays.stream(this.toArrays()).iterator();
    }
}
