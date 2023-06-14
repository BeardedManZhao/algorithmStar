package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.transformation.ManyTrans;
import zhao.algorithmMagic.utils.transformation.ProTransForm;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Hash Color 矩阵对象，通过此类创建出来的能够实现强大的哈希映射，内存占用将大大减少。
 * <p>
 * Hash Color matrix objects created through this class can achieve powerful hash mapping, greatly reducing memory usage.
 *
 * @author 赵凌宇
 * 2023/6/13 9:35
 */
public class HashColorMatrix extends ColorMatrix {

    private final static HashMap<Integer, Color> COLOR_HASH_MAP = new HashMap<>();

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
     */
    protected HashColorMatrix(int rowCount, int colCount, Color[][] colors, boolean isGrayscale) {
        super(rowCount, colCount, hashArr(colors), isGrayscale);
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param colors 用于构造矩阵的二维数组
     *               <p>
     *               2D array for constructing the matrix
     * @return matrix object
     */
    public static HashColorMatrix parse(Color[]... colors) {
        if (colors.length > 0) {
            // 对矩阵数组使用 Hash 映射表
            return new HashColorMatrix(colors.length, colors[0].length, colors, false);
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
     * @param v           矩阵中的所有图像的尺寸参数。
     *                    <p>
     *                    The size parameters of all images in the matrix.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parse(String inputString, int... v) {
        return parse(ASIO.parseImageGetColorArray(inputString, v));
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
        return GrayscaleColors(ASIO.parseImageGetColorArray(inputString));
    }

    /**
     * 将一个具有三层矩阵的矩阵空间转换成为一个图像矩阵对象。
     * <p>
     * Convert a matrix space with a three-layer matrix into an image matrix object.
     *
     * @param integerMatrixSpace 需要被转换的数据矩阵，其中的三层矩阵分别为RGB通道对应的RGB矩阵对象。
     *                           <p>
     *                           The data matrix to be converted, the three layers of which are RGB matrix objects corresponding to RGB channels.
     * @return 将三层矩阵的矩阵空间合并在一起的一个矩阵图像对象。
     * <p>
     * A matrix image object that combines the matrix space of a three-layer matrix.
     */
    public static ColorMatrix parse(IntegerMatrixSpace integerMatrixSpace) {
        int n = integerMatrixSpace.getNumberOfDimensions();
        if (n != 3) {
            throw new OperatorOperationException("将矩阵空间转换成为图像矩阵时发生错误，需要3个通道，但是实际通道数为：" + n);
        }
        Color[][] colors = new Color[integerMatrixSpace.getRowCount()][integerMatrixSpace.getColCount()];
        {        // 开始合并
            IntegerMatrix RMat = integerMatrixSpace.get(0);
            IntegerMatrix GMat = integerMatrixSpace.get(1);
            IntegerMatrix BMat = integerMatrixSpace.get(2);
            int y = -1;
            for (Color[] color : colors) {
                int[] reds = RMat.toArrays()[++y];
                int[] greens = GMat.toArrays()[y];
                int[] blues = BMat.toArrays()[y];
                for (int i = 0; i < color.length; i++) {
                    color[i] = new Color(
                            ASMath.rgbaTOIntRGBA(reds[i], greens[i], blues[i])
                    );
                }
            }
        }
        return parse(colors);
    }

    /**
     * 以灰度图像矩阵的方式将整形矩阵中的每一个元素赋值到颜色矩阵的三通道中。
     * <p>
     * Each element in the shaping matrix is assigned to the three channels of the color matrix in the form of gray image matrix.
     *
     * @param integerMatrix 需要被转换的图像色彩整形矩阵对象。
     *                      <p>
     *                      The image color shaping matrix object to be converted.
     * @param isGrayscale   如果设置为 true 则代表读取的时候直接将整形矩阵作为欸一个灰度图像进行读取，只会将一个G通道的颜色值提供给每一个像素。 入宫设置为 false 则代表正在读取的时候直接将整形矩阵中的参数作为一个RGB色度值进行转换。
     *                      <p>
     *                      If set to true, it means that the shaping matrix is directly read as a grayscale image when reading, and only the color value of one G channel is provided to each pixel. If the setting of "entering the palace" is false, it means that the parameters in the shaping matrix are directly converted as an RGB chroma value when reading.
     * @return 转换之后的图像矩阵对象。
     * <p>
     * The converted image matrix object.
     */
    public static ColorMatrix parse(IntegerMatrix integerMatrix, boolean isGrayscale) {
        Color[][] colors = new Color[integerMatrix.getRowCount()][integerMatrix.getColCount()];
        int y = -1;
        if (isGrayscale) {
            for (int[] ints : integerMatrix.toArrays()) {
                Color[] row = colors[++y];
                for (int i = 0; i < ints.length; i++) {
                    int anInt = ASMath.regularTricolor(ints[i]);
                    row[i] = new Color(anInt, anInt, anInt);
                }
            }
            return parse(colors);
        }
        for (int[] ints : integerMatrix.toArrays()) {
            Color[] row = colors[++y];
            for (int i = 0; i < ints.length; i++) {
                row[i] = new Color(ints[i]);
            }
        }
        return parse(colors);
    }

    /**
     * 将图像URL解析，并获取对应的图像矩阵
     *
     * @param url 需要被解析的URL对象
     * @param v   矩阵中的所有图像的尺寸参数。
     *            <p>
     *            The size parameters of all images in the matrix.
     * @return URL对象所对应的图像矩阵。
     */
    public static ColorMatrix parse(URL url, int... v) {
        return parse(ASIO.parseURLGetColorArray(url, v));
    }


    /**
     * 将图像URL解析，并获取对应的图像矩阵
     *
     * @param url 需要被解析的URL对象
     * @return URL对象所对应的图像矩阵。
     */
    public static ColorMatrix parseGrayscale(URL url) {
        return GrayscaleColors(ASIO.parseURLGetColorArray(url));
    }

    /**
     * 使用组件将一个图像数据提取，并获取对应的图像矩阵。
     *
     * @param inputComponent 能够被提取出图像矩阵的数据组件。
     * @return 从组件中提取出来的图像矩阵对象。
     */
    public static ColorMatrix parse(InputComponent inputComponent) {
        return parse(inputComponent, true);
    }

    /**
     * 使用组件将一个图像数据提取，并获取对应的图像矩阵。
     *
     * @param inputComponent 能够被提取出图像矩阵的数据组件。
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
     * @return 从组件中提取出来的图像矩阵对象。
     */
    public static ColorMatrix parse(InputComponent inputComponent, boolean isOC) {
        boolean isOk;
        if (!inputComponent.isOpen() && isOC) {
            isOk = inputComponent.open();
        } else {
            isOk = true;
        }
        if (isOk) {
            // 开始进行数据提取
            ColorMatrix parse = parse(ASIO.parseImageGetColorArray(inputComponent.getBufferedImage()));
            if (isOC) ASIO.close(inputComponent);
            return parse;
        } else throw new OperatorOperationException("Unable to open your inputComponent.");
    }

    /**
     * 生成一个随机图像矩阵，其中的三个通道颜色均为随机数值，无任何规律。
     *
     * @param width    要生成的图像矩阵的宽度。
     * @param height   要生成的图像矩阵的高度。
     * @param randSeed 生成图像时需要使用的随机种子。
     * @return 按照设置的随机种子生成的随机图像矩阵对象。
     */
    public static ColorMatrix random(int width, int height, int randSeed) {
        Random random = new Random(randSeed);
        Color[][] res = new Color[height][];
        for (int y = 0; y < height; y++) {
            Color[] row = new Color[width];
            for (int x = 0; x < width; x++) {
                row[x] = new Color(
                        random.nextInt(255),
                        random.nextInt(255),
                        random.nextInt(255)
                );
            }
            res[y] = row;
        }
        return parse(res);
    }

    /**
     * @return 当前 HASH 缓冲池中已经缓冲的像素个数。
     * <p>
     * The number of pixels that have already been buffered in the current HASH buffer pool.
     */
    public static int getHashColorLength() {
        return COLOR_HASH_MAP.size();
    }

    /**
     * 将一个数组对象在此处进行哈希表的映射操作，实现哈希映射。
     * <p>
     * Map an array object to a Hash table here to implement hash mapping.
     *
     * @param colors 需要被哈希映射的数组对象，其会被直接操作。
     *               <p>
     *               Array objects that need to be hashed will be directly manipulated.
     * @return colors 映射之后的对象。
     * <p>
     * The object after mapping.
     */
    public static Color[][] hashArr(Color[][] colors) {
        for (Color[] color : colors) {
            int index = -1;
            for (Color value : color) {
                ++index;
                int rgb = value.getRGB();
                Color color1 = COLOR_HASH_MAP.get(rgb);
                if (color1 != null) {
                    color[index] = color1;
                } else {
                    COLOR_HASH_MAP.put(rgb, value);
                }
            }
        }
        return colors;
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
        ColorMatrix add = super.add(value);
        hashArr(add.toArrays());
        return clone(add, false);
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
        ColorMatrix diff = super.diff(value);
        hashArr(diff.toArrays());
        return clone(diff, false);
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
    public ColorMatrix add(Number value) {
        ColorMatrix add = super.add(value);
        hashArr(add.toArrays());
        return clone(add, false);
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
    public ColorMatrix diff(Number value) {
        ColorMatrix diff = super.diff(value);
        hashArr(diff.toArrays());
        return clone(diff, false);
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
    public ColorMatrix diffAbs(ColorMatrix value, boolean ModifyCaller) {
        ColorMatrix colorMatrix = super.diffAbs(value, ModifyCaller);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
    }

    /**
     * 将两个图像矩阵按照一定的规则进行计算操作，返回结果数值
     *
     * @param value     被求和的参数  Parameters to be summed
     * @param manyTrans 两个操作数之间的求和逻辑实现，不同的实现有不同的结果效果。
     *                  <p>
     *                  The summation logic between two operands is implemented, and different implementations have different results.
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public ColorMatrix agg(ColorMatrix value, ManyTrans<Color, Color> manyTrans) {
        ColorMatrix agg = super.agg(value, manyTrans);
        hashArr(agg.toArrays());
        return clone(agg, false);
    }

    /**
     * 在矩阵内部发生计算，使得矩阵可以满足我们的需求与计算操作。
     * <p>
     * Computing occurs within the matrix, enabling the matrix to meet our needs and computational operations.
     *
     * @param action 计算实现逻辑，支持自定义也支持直接使用内置实现。
     *               <p>
     *               Computational implementation logic supports customization and direct use of built-in implementations.
     * @param isCopy 计算操作是否要在拷贝之后的数组中执行，如果设置为 true 代表计算操作将发生于新数据中，不会影响原矩阵对象。
     *               <p>
     *               Whether the calculation operation should be performed in the copied array. If set to true, it means that the calculation operation will occur in the new data and will not affect the original matrix object.
     * @return 计算之后的矩阵对象。
     * <p>
     * The calculated matrix object.
     */
    @Override
    public ColorMatrix calculate(Consumer<Color[][]> action, boolean isCopy) {
        ColorMatrix calculate = super.calculate(action, isCopy);
        hashArr(calculate.toArrays());
        return clone(calculate, false);
    }

    /**
     * 矩阵变换操作函数，在此函数中您可以传递多个矩阵变换模式，实现多种不同的矩阵转换效果，同时还可以自定义矩阵变换时需要使用的逻辑。
     *
     * @param transformation 矩阵变换逻辑。
     * @param value          矩阵中的变换操作计算时需要的其它参数对象。
     * @return 矩阵变换之后返回的新矩阵对象。
     */
    @Override
    public ColorMatrix converter(ProTransForm<ColorMatrix, ColorMatrix> transformation, HashMap<String, Cell<?>> value) {
        ColorMatrix converter = super.converter(transformation, value);
        hashArr(converter.toArrays());
        return clone(converter, false);
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
        ColorMatrix transpose = super.transpose();
        hashArr(transpose.toArrays());
        return clone(transpose, false);
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
        ColorMatrix multiply = super.multiply(vector);
        hashArr(multiply.toArrays());
        return clone(multiply, false);
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public HashColorMatrix expand() {
        return this;
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
    @Override
    public ColorMatrix colorReversal(boolean isCopy) {
        ColorMatrix colorMatrix = super.colorReversal(isCopy);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
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
        ColorMatrix colorMatrix = super.reverseLR(isCopy);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
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
        ColorMatrix colorMatrix = super.reverseBT(isCopy);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
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
    public ColorMatrix reShape(int... shape) {
        ColorMatrix colorMatrix = super.reShape(shape);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
    }

    /**
     * 将图像进行模糊处理
     *
     * @param isCopy 如果设置为true 代表拷贝一份新矩阵然后再模糊计算
     * @return 方框模糊处理过的矩阵对象
     */
    @Override
    public ColorMatrix boxBlur(boolean isCopy) {
        ColorMatrix colorMatrix = super.boxBlur(isCopy);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
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
     * @return 经过函数计算之后的图像矩阵对象。
     */
    @Override
    public ColorMatrix addColor(boolean isCopy, int R, int G, int B) {
        ColorMatrix colorMatrix = super.addColor(isCopy, R, G, B);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
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
     * @return 经过函数计算之后的图像矩阵对象。
     */
    @Override
    public ColorMatrix subColor(boolean isCopy, int R, int G, int B) {
        ColorMatrix colorMatrix = super.subColor(isCopy, R, G, B);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
    }

    /**
     * 从图像矩阵中截取出一块空间，并返回这一块空间对应的图像矩阵，注意：这块空间与原图像直接没有任何关系，您可以使用 merge 函数将这块空间合并到原图像。
     * <p>
     * Intercept a space from the image matrix and return the image matrix corresponding to this space. Note: this space has no direct relationship with the original image. You can use the merge function to merge this space into the original image.
     *
     * @param x1 原图像矩阵中的提取起始x坐标
     *           <p>
     *           Extracting the starting x coordinate in the original image matrix
     * @param y1 原图像矩阵中的提取起始y坐标
     *           <p>
     *           Extraction of starting y coordinate in the original image matrix
     * @param x2 原图像矩阵中的提取终止y坐标
     *           <p>
     *           Extraction termination y coordinate in the original image matrix
     * @param y2 原图像矩阵中的提取终止y坐标
     *           <p>
     *           Extraction termination y coordinate in the original image matrix
     * @return 从原图像中提取出来的一块图像矩阵对象。
     * <p>
     * An image matrix object extracted from the original image.
     */
    @Override
    public ColorMatrix extractImage(int x1, int y1, int x2, int y2) {
        ColorMatrix colorMatrix = super.extractImage(x1, y1, x2, y2);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
    }

    /**
     * 使用浅拷贝的方式将照片的多行向量提取出来并构成一个新的矩阵对象，形成一个新的图像矩阵。
     * <p>
     * Using a shallow copy method, the multiline vectors of a photo are extracted and formed into a new matrix object, forming a new image matrix.
     *
     * @param y1 提取图像的起始纵坐标轴点。
     *           <p>
     *           Extract the starting ordinate axis point of the image.
     * @param y2 提取图像的终止纵坐标轴点。
     *           <p>
     *           Extract the ending ordinate axis point of the image.
     * @return 原矩阵中的 y1 y2 的局部像素勾成的新图像矩阵对象。
     * <p>
     * The local pixels of y1 and y2 in the original matrix form a new image matrix object.
     */
    @Override
    public ColorMatrix extractImageSrc(int y1, int y2) {
        ColorMatrix colorMatrix = super.extractImageSrc(y1, y2);
        hashArr(colorMatrix.toArrays());
        return clone(colorMatrix, false);
    }

    /**
     * 图像腐蚀函数，在该函数的处理之下，可以对图像某些不足够的颜色数值进行腐蚀操作，
     *
     * @param width  用于腐蚀的图像卷积核的宽度
     * @param height 用于腐蚀的图像卷积核的高度
     * @param isCopy 如果设置为true 则代表在一个新的矩阵对象中进行腐蚀的操作计算，如果设置为false 则代表在原矩阵的基础上进行腐蚀不会出现新矩阵对象。
     * @return 图像矩阵经过了腐蚀操作之后返回的新矩阵对象。
     */
    @Override
    public ColorMatrix erode(int width, int height, boolean isCopy) {
        ColorMatrix erode = super.erode(width, height, isCopy);
        hashArr(erode.toArrays());
        return clone(erode, false);
    }

    /**
     * 图像腐蚀函数，在该函数的处理之下，可以对图像某些不足够的颜色数值进行腐蚀操作，
     *
     * @param width     用于腐蚀的图像卷积核的宽度
     * @param height    用于腐蚀的图像卷积核的高度
     * @param isCopy    如果设置为true 则代表在一个新的矩阵对象中进行腐蚀的操作计算，如果设置为false 则代表在原矩阵的基础上进行腐蚀不会出现新矩阵对象。
     * @param backColor 图像矩阵中的背景颜色数据对像，在该对象中有很多的颜色，您需要指定一种颜色作为图像矩阵中的腐蚀者身份。
     * @return 图像矩阵经过了腐蚀操作之后返回的新矩阵对象。
     */
    @Override
    public ColorMatrix erode(int width, int height, boolean isCopy, Color backColor) {
        ColorMatrix erode = super.erode(width, height, isCopy, backColor);
        hashArr(erode.toArrays());
        return clone(erode, false);
    }

    /**
     * 浅拷贝的方式获取到两个矩阵上下或左右合并之后产生的新矩阵对象，需要注意的是，该操作不会链接矩阵，更不会更改调用方的的数据。
     * <p>
     * The shallow copy method obtains a new matrix object generated by merging two matrices up and down or left and right. Note that this operation does not link the matrix, nor does it change the caller's data.
     *
     * @param colorMatrix 需要被追加的颜色矩阵对象。
     *                    <p>
     *                    The color matrix object that needs to be appended.
     * @param isLR        如果设置为 true 代表使用左右追加的方式进行追加， 如果设置为 false 代表使用上下追加的方式进行追加。
     *                    <p>
     *                    If set to true, it means appending using the left and right appending method. If set to false, it means appending using the up and down appending method.
     * @return 追加之后的矩阵对象，该矩阵对象中的行与原矩阵是无关联的。
     * <p>
     * The appended matrix object has no rows associated with the original matrix.
     */
    @Override
    public ColorMatrix append(ColorMatrix colorMatrix, boolean isLR) {
        ColorMatrix append = super.append(colorMatrix, isLR);
        hashArr(append.toArrays());
        return clone(append, false);
    }

    /**
     * 使用克隆的方式创建出一个新的矩阵对象。
     *
     * @param colorMatrix 需要被克隆的原矩阵对象。
     * @param isCopy      克隆操作中的元素是否使用深拷贝
     * @return 克隆操作成功之后的新矩阵对象。
     */
    @Override
    public HashColorMatrix clone(ColorMatrix colorMatrix, boolean isCopy) {
        return new HashColorMatrix(
                colorMatrix.getColCount(), colorMatrix.getRowCount(),
                isCopy ? colorMatrix.copyToNewArrays() : colorMatrix.toArrays(),
                colorMatrix.isGrayscale()
        );
    }
}
