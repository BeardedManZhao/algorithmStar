package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 颜色矩阵对象，其中存储的每一个元素都是一个 Color 对象，适合用来进行图像的绘制等工作。
 * <p>
 * The color matrix object, in which each element stored is a Color object, is suitable for image rendering and other work.
 *
 * @author 赵凌宇
 * 2023/2/9 20:59
 */
public class ColorMatrix extends Matrix<ColorMatrix, Color, Color[], Color[], Color[][]> {

    public final static int WHITE_NUM = 0xffffff;
    private boolean isGrayscale;

    /**
     * 构造一个指定行列数据的矩阵对象，其中的行指针最大值将会默认使用行数量。
     * <p>
     * Construct a matrix object with specified row and column data. The maximum value of row pointer will use the number of rows by default.
     *
     * @param rowCount    矩阵中的行数量
     *                    <p>
     *                    the number of rows in the matrix
     * @param colCount    矩阵中的列数量
     *                    <p>
     *                    the number of cols in the matrix
     * @param colors      该矩阵对象中的二维数组对象。
     * @param isGrayscale 如果设置为true 代表此图像是灰度图像
     *                    <p>
     *                    If set to true, this image is grayscale image
     */
    protected ColorMatrix(int rowCount, int colCount, Color[][] colors, boolean isGrayscale) {
        super(rowCount, colCount, colors);
        this.isGrayscale = isGrayscale;
    }

    /**
     * 构造一个指定行列数据的矩阵对象，该函数中支持根据需求指定行指针的最大值。
     * <p>
     * Construct a matrix object with specified row and column data. The maximum value of row pointer will use the number of rows by default.
     *
     * @param rowCount               矩阵中的行数量
     *                               <p>
     *                               the number of rows in the matrix
     * @param colCount               矩阵中的列数量
     *                               <p>
     *                               the number of cols in the matrix
     * @param maximumRowPointerCount 行指针的最大值，需要注意的是，行指针使用索引定位，因此从0开始计数！
     *                               <p>
     *                               The maximum value of the row pointer. It should be noted that the row pointer uses index positioning, so counting starts from 0!
     * @param colors                 该矩阵对象中的二维数组对象。
     * @param isGrayscale            如果设置为true 代表此图像是灰度图像
     *                               <p>
     *                               If set to true, this image is grayscale image
     */
    protected ColorMatrix(int rowCount, int colCount, int maximumRowPointerCount, Color[][] colors, boolean isGrayscale) {
        super(rowCount, colCount, maximumRowPointerCount, colors);
        this.isGrayscale = isGrayscale;
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
    public static ColorMatrix parse(Color[]... ints) {
        if (ints.length > 0) {
            return new ColorMatrix(ints.length, ints[0].length, ints, false);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param inputString 要读取的目标图像文件路径。
     *                    <p>
     *                    The target image file path to read.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parse(String inputString) {
        return ColorMatrix.parse(ASIO.parseImageGetColorArray(inputString));
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的灰度整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param inputString 要读取的目标图像文件路径。
     *                    <p>
     *                    The target image file path to read.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parseGrayscale(String inputString) {
        Color[][] colors = ASIO.parseImageGetColorArray(inputString);
        for (Color[] color : colors) {
            int count = -1;
            for (Color color1 : color) {
                int avg = (int) ASMath.avg(color1.getRed(), color1.getGreen(), color1.getBlue());
                color[++count] = new Color(avg, avg, avg, color1.getAlpha());
            }
        }
        return new ColorMatrix(colors.length, colors[0].length, colors, true);
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
    public ColorMatrix add(ColorMatrix value) {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
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
    public ColorMatrix diff(ColorMatrix value) {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
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
    public Color get(int row, int col) {
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
    public ColorMatrix transpose() {
        // 转置之后的矩阵行数是转置之前的列数
        int colCount = getColCount();
        // 转置之后的矩阵列数是转置之前的行数
        int rowCount = getRowCount();
        Color[][] res = new Color[colCount][rowCount];
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

    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public Color[] toArray() {
        return toArrays()[RowPointer];
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
    public Color moduleLength() {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
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
    public ColorMatrix multiply(ColorMatrix vector) {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
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
    public Color innerProduct(ColorMatrix vector) {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public ColorMatrix expand() {
        return this;
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public final Color[] copyToNewArray() {
        Color[] colors = this.toArray();
        Color[] res = new Color[colors.length];
        System.arraycopy(colors, 0, res, 0, colors.length);
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
    public ColorMatrix shuffle(long seed) {
        return ColorMatrix.parse(
                ASMath.shuffle(this.copyToNewArrays(), seed, false)
        );
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
    public Color[][] copyToNewArrays() {
        Color[][] colors = this.toArrays();
        Color[][] res = new Color[colors.length][colors[0].length];
        System.arraycopy(colors, 0, res, 0, colors.length);
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
    protected Color[] getArrayByRowIndex(int index) {
        return toArrays()[index];
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
    protected Color[] getArrayByColIndex(int index) {
        Color[] res = new Color[this.getRowCount()];
        int count = -1;
        for (Color[] colors : this.toArrays()) {
            res[++count] = colors[index];
        }
        return res;
    }

    /**
     * 将矩阵中的所有像素颜色反转，并生成一个新像素矩阵对象！
     * <p>
     * Invert all pixel colors in the matrix and generate a new pixel matrix object!
     *
     * @param isCopy 如果设置为true 代表反转操作会作用到一个新数组中，并不会更改源数组中的元素位置。反之则是直接更改源数组。
     *               <p>
     *               If set to true, the inversion operation will be applied to a new array, and the position of the elements in the source array will not be changed. On the contrary, you can directly change the source array.
     * @return 颜色数值反转之的新像素矩阵对象。
     * <p>
     * A new pixel matrix object whose color values are reversed.
     */
    public ColorMatrix colorReversal(boolean isCopy) {
        if (isCopy) {
            Color[][] colors = this.copyToNewArrays();
            for (Color[] nowRow : colors) {
                for (int i = 0; i < nowRow.length; i++) {
                    nowRow[i] = new Color(~(nowRow[i].getRGB()) & WHITE_NUM);
                }
            }
            return ColorMatrix.parse(colors);
        } else {
            for (Color[] colors : this.toArrays()) {
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = new Color(~(colors[i].getRGB()) & WHITE_NUM);
                }
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
    public ColorMatrix reverseLR(boolean isCopy) {
        if (isCopy) {
            Color[][] ints1 = this.copyToNewArrays();
            for (Color[] ints : ints1) {
                ASMath.arrayReverse(ints);
            }
            return ColorMatrix.parse(ints1);
        } else {
            for (Color[] ints : this.toArrays()) {
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
    public ColorMatrix reverseBT(boolean isCopy) {
        if (isCopy) {
            return ColorMatrix.parse(ASMath.arrayReverse(this.copyToNewArrays()));
        } else {
            ASMath.arrayReverse(this.toArrays());
            return this;
        }
    }

    /**
     * 将图像进行模糊处理
     *
     * @param isCopy 如果设置为true 代表拷贝一份新矩阵然后再模糊计算
     * @return 方框模糊处理过的矩阵对象
     */
    public ColorMatrix boxBlur(boolean isCopy) {
        if (isCopy) {
            return ColorMatrix.parse(ASMath.boxBlur(this.copyToNewArrays()));
        } else {
            System.arraycopy(ASMath.boxBlur(this.toArrays()), 0, this.toArrays(), 0, this.getRowCount());
            return this;
        }
    }

    /**
     * 将矩阵中指定行列坐标的像素点进行修改。
     * <p>
     * Modify the pixel points of the specified row and column coordinates in the matrix.
     *
     * @param x        需要被修改的像素的横坐标。
     *                 <p>
     *                 The abscissa of the pixel to be modified.
     * @param y        需要被修改的像素点的纵坐标。
     *                 <p>
     *                 The ordinate of the pixel to be modified
     * @param newColor 需要将指定坐标点修改成为哪个颜色Color对象。
     *                 <p>
     *                 You need to change the specified coordinate point to which color object.
     */
    public void setPixels(int x, int y, Color newColor) {
        this.toArrays()[y][x] = newColor;
        if (this.isGrayscale) {
            // 如果在变更之前是灰度图像，就需要进一个判断，看下新像素是否影响了灰度
            int red = newColor.getRed();
            if (red != newColor.getGreen() || red != newColor.getBlue()) {
                // 影响了灰度，因此图像不属于灰度图了
                isGrayscale = false;
            }
        }
    }

    /**
     * 将矩阵中指定的颜值RGB数值 加上 对应的数值，并生成或返回一个转换之后的矩阵对象。
     * <p>
     * Add the RGB value of the face value specified in the matrix to the corresponding value, and generate or return a converted matrix object.
     *
     * @param isCopy 如果设置为true 代表再进行数据砖混的时候返回一个新的矩阵对象。
     *               <p>
     *               If it is set to true, a new matrix object will be returned when the data is bricked again.
     * @param R      Red颜色数值需要被增加的数值。
     *               <p>
     *               Red color value needs to be added.
     * @param G      Green颜色数值需要被增加的数值。
     *               <p>
     *               Green color value needs to be increased.
     * @param B      Blue颜色数值需要被增加的数值。
     *               <p>
     *               The value that the Blue color value needs to be increased.
     */
    public ColorMatrix addColor(boolean isCopy, int R, int G, int B) {
        if (isCopy) {
            Color[][] colors = new Color[this.getRowCount()][this.getColCount()];
            int y = -1;
            for (Color[] toArray : this.toArrays()) {
                int x = -1;
                Color[] row = colors[++y];
                for (Color color : toArray) {
                    row[++x] = new Color(
                            Math.min(color.getRed() + R, 0xff),
                            Math.min(color.getGreen() + G, 0xff),
                            Math.min(color.getBlue() + B, 0xff)
                    );
                }
            }
            return ColorMatrix.parse(colors);
        } else {
            this.isGrayscale = false;
            for (Color[] colors : this.toArrays()) {
                int x = -1;
                for (Color color : colors) {
                    colors[++x] = new Color(
                            Math.min(color.getRed() + R, 0xff),
                            Math.min(color.getGreen() + G, 0xff),
                            Math.min(color.getBlue() + B, 0xff)
                    );
                }
            }
            return this;
        }
    }

    /**
     * 将矩阵中指定的颜值RGB数值 加上 对应的数值，并生成或返回一个转换之后的矩阵对象。
     * <p>
     * Add the RGB value of the face value specified in the matrix to the corresponding value, and generate or return a converted matrix object.
     *
     * @param isCopy 如果设置为true 代表再进行数据砖混的时候返回一个新的矩阵对象。
     *               <p>
     *               If it is set to true, a new matrix object will be returned when the data is bricked again.
     * @param R      Red颜色数值需要被增加的数值。
     *               <p>
     *               Red color value needs to be added.
     * @param G      Green颜色数值需要被增加的数值。
     *               <p>
     *               Green color value needs to be increased.
     * @param B      Blue颜色数值需要被增加的数值。
     *               <p>
     *               The value that the Blue color value needs to be increased.
     */
    public ColorMatrix subColor(boolean isCopy, int R, int G, int B) {
        if (isCopy) {
            Color[][] colors = new Color[this.getRowCount()][this.getColCount()];
            int y = -1;
            for (Color[] toArray : this.toArrays()) {
                int x = -1;
                Color[] row = colors[++y];
                for (Color color : toArray) {
                    row[++x] = new Color(
                            Math.max(color.getRed() - R, 0),
                            Math.max(color.getGreen() - G, 0),
                            Math.max(color.getBlue() - B, 0)
                    );
                }
            }
            return ColorMatrix.parse(colors);
        } else {
            this.isGrayscale = false;
            for (Color[] colors : this.toArrays()) {
                int x = -1;
                for (Color color : colors) {
                    colors[++x] = new Color(
                            Math.max(color.getRed() - R, 0),
                            Math.max(color.getGreen() - G, 0),
                            Math.max(color.getBlue() - B, 0)
                    );
                }
            }
            return this;
        }
    }

    /**
     * 将图像矩阵转换至整形矩阵中，此方法将会使得矩阵中存储的图像矩阵中每一个像素的所有色彩值与透明度
     * <p>
     * Convert the image matrix to the shaping matrix. This method will make all the color values and transparency of each pixel in the image matrix stored in the matrix
     *
     * @return 转换之后的整形矩阵对象，需要注意，如果颜色矩阵中的
     * <p>
     * The converted shape matrix object needs to be noted that if the
     */
    public IntegerMatrix toIntegerMatrix() {
        int[][] res = new int[this.getRowCount()][this.getColCount()];
        int y = -1;
        if (this.isGrayscale) {
            for (Color[] colors : this.toArrays()) {
                int[] row = res[++y];
                int x = -1;
                for (Color color : colors) {
                    row[++x] = ASMath.grayTORGB(color.getGreen());
                }
            }
        } else {
            for (Color[] colors : this.toArrays()) {
                int[] row = res[++y];
                int x = -1;
                for (Color color : colors) {
                    row[++x] = ASMath.rgbaTOIntRGBA(color.getRed(), color.getGreen(), color.getBlue());
                }
            }
        }
        return IntegerMatrix.parse(res);
    }

    /**
     * 强制将图像色彩模式进行反转，通常用于将一个普通图像转换成灰度图像，需要注意的是，此方法并不会改变原像素矩阵中的数值。
     * <p>
     * Forced reversal of image color mode is usually used to convert an ordinary image into a grayscale image. It should be noted that this method does not change the value in the original pixel matrix.
     * <p>
     * 该函数会将当前的色彩模式反转，例如灰度到有色，有色到灰度之间的转换。
     */
    public void forcedColorChange() {
        this.isGrayscale = !this.isGrayscale;
    }

    /**
     * 获取到图像当前的色彩模式布尔值。
     *
     * @return 如果返回 true 代表当前图像是一个灰度图像。
     */
    public final boolean isGrayscale() {
        return isGrayscale;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Color[]> iterator() {
        return Arrays.stream(this.toArrays()).iterator();
    }
}
