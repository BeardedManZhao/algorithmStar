package zhao.algorithmMagic.operands.matrix.block;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;

import java.awt.*;
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
     * @return 构建出来的矩阵空间对象，其中空间有很多层矩阵，每一层矩阵都是图像的一个通道。
     */
    public static IntegerMatrixSpace parse(String inputString) {
        return parse(ASIO.parseImageGetArrays(inputString));
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

    @Override
    public IntegerMatrix[] copyToNewArrays() {
        IntegerMatrix[] integerMatrix1 = toArrays();
        IntegerMatrix[] integerMatrix2 = new IntegerMatrix[integerMatrix1.length];
        System.arraycopy(integerMatrix1, 0, integerMatrix2, 0, integerMatrix2.length);
        return integerMatrix2;
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
     * 对矩阵空间进行卷积计算，在卷积计算的时候会产生出一个更小的特征矩阵。
     *
     * @param width  矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     * @param height 矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
     * @return 矩阵空间卷积结果特征图，保持三通道的格式返回。
     */
    public IntegerMatrixSpace folding(int width, int height, IntegerMatrixSpace weightMat) {
        int layouts = this.getNumberOfDimensions();
        {
            if (width != weightMat.getColCount() || height != weightMat.getRowCount()) {
                // 代表权重的宽高与截取小区域的宽高不一致
                throw new OperatorOperationException("请您确保权重权重的宽高与截取小区域的宽高保持一致\nPlease ensure that the width and height of the weight weight are consistent with the width and height of the intercepted small area");
            }
            if (weightMat.getNumberOfDimensions() != layouts) {
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
     * @param width  矩阵进行卷积运算的时的子图像宽度，最好选择能够被矩阵的列数整除的数值。
     * @param height 矩阵进行卷积运算时的子图像高度，最好选中能够被矩阵的行数整除的数值。
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

    public final ColorMatrix foldingAndSumRGB(int width, int height, IntegerMatrixSpace weightMat) {
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
