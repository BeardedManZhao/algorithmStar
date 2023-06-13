package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

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
}
