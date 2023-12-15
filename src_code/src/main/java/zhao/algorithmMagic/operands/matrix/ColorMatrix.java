package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.SerialVersionUID;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.DistanceAlgorithm;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.integrator.ImageRenderingIntegrator;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.io.OutputComponent;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.block.ColorMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.vector.Vector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.filter.DoubleFiltering;
import zhao.algorithmMagic.utils.transformation.ManyTrans;
import zhao.algorithmMagic.utils.transformation.PoolRgbOboMAX;
import zhao.algorithmMagic.utils.transformation.ProTransForm;
import zhao.algorithmMagic.utils.transformation.Transformation;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

/**
 * 颜色矩阵对象，其中存储的每一个元素都是一个 Color 对象，适合用来进行图像的绘制等工作。
 * <p>
 * The color matrix object, in which each element stored is a Color object, is suitable for image rendering and other work.
 *
 * @author 赵凌宇
 * 2023/2/9 20:59
 */
public class ColorMatrix extends Matrix<ColorMatrix, Color, Color[], Color[], Color[][]> implements SaveMatrix {

    /**
     * 红色通道编码，用于定位颜色通道的数值常量
     */
    public final static byte _R_ = 0b10000;
    /**
     * 绿色通道编码，用于定位颜色通道的数值常量
     */
    public final static byte _G_ = 0b1000;
    /**
     * 蓝色通道编码，用于定位颜色通道的数值常量
     */
    public final static byte _B_ = 0;
    /**
     * 黑白色RGB常量数值
     */
    public final static int WHITE_NUM = 0xffffff;
    public final static Color WHITE = new Color(WHITE_NUM);
    public final static short SINGLE_CHANNEL_MAXIMUM = 0xff;
    /**
     * 颜色数值求和计算方案，在该方案中的处理逻辑为如果数值超出范围，则取0或255，如果数值没有超过范围则取数值本身，实现颜色数值的高效限制的计算方案。
     * <p>
     * The processing logic in the color value summation calculation scheme is to take 0 or 255 if the value exceeds the range, and take the value itself if the value does not exceed the range, achieving an efficient calculation scheme for limiting color values.
     */
    public final static ManyTrans<Color, Color> COLOR_SUM_REGULATE = (inputType1, inputType2) -> new Color(
            ASMath.regularTricolor(inputType1.getRed() + inputType2.getRed()),
            ASMath.regularTricolor(inputType1.getGreen() + inputType2.getGreen()),
            ASMath.regularTricolor(inputType1.getBlue() + inputType2.getGreen())
    );
    /**
     * 颜色数值求和计算方案，在该方案中的处理逻辑为：如果x为求和后的越界数值，则取 x % 256 的结果作为颜色数值。
     * <p>
     * The processing logic in the color value summation calculation scheme is: if x is the out-of-boundary value after the summation, the result of x% 256 is taken as the color value.
     */
    public final static ManyTrans<Color, Color> COLOR_SUM_REMAINDER = (inputType1, inputType2) -> {
        int red = inputType1.getRed() + inputType2.getRed();
        int green = inputType1.getGreen() + inputType2.getGreen();
        int blue = inputType1.getBlue() + inputType2.getGreen();
        return new Color(
                red - (red >> _G_ << _G_),
                green - (green >> _G_ << _G_),
                blue - (blue >> _G_ << _G_)
        );
    };
    /**
     * 颜色数值求差计算方案，在该方案中的处理逻辑为如果数值超出范围，则取0或255，如果数值没有超过范围则取数值本身，实现颜色数值的高效限制的计算方案。
     * <p>
     * The processing logic in the color value difference calculation scheme is to take 0 or 255 if the value exceeds the range, and take the value itself if the value does not exceed the range, achieving an efficient calculation scheme for limiting color values.
     */
    public final static ManyTrans<Color, Color> COLOR_DIFF_REGULATE = (inputType1, inputType2) -> new Color(
            ASMath.regularTricolor(inputType1.getRed() - inputType2.getRed()),
            ASMath.regularTricolor(inputType1.getGreen() - inputType2.getGreen()),
            ASMath.regularTricolor(inputType1.getBlue() - inputType2.getGreen())
    );
    /**
     * 颜色数值求差计算方案，在该方案中的处理逻辑为：如果x为求和后的越界数值，则取 x % 256 的结果作为颜色数值。
     * <p>
     * The processing logic in the color value difference calculation scheme is: if x is the out-of-boundary value after the sum, the result of x% 256 is taken as the color value.
     */
    public final static ManyTrans<Color, Color> COLOR_DIFF_REMAINDER = (inputType1, inputType2) -> {
        int red = inputType1.getRed() - inputType2.getRed();
        int green = inputType1.getGreen() - inputType2.getGreen();
        int blue = inputType1.getBlue() - inputType2.getGreen();
        return new Color(
                red - (red >> _G_ << _G_),
                green - (green >> _G_ << _G_),
                blue - (blue >> _G_ << _G_)
        );
    };
    /**
     * 颜色数值求差计算方案，在该方案中的处理逻辑为如果数值超出范围，则取0或255，如果数值没有超过范围则取数值本身，实现颜色数值的高效限制的计算方案。
     * <p>
     * The processing logic in the color value difference calculation scheme is to take 0 or 255 if the value exceeds the range, and take the value itself if the value does not exceed the range, achieving an efficient calculation scheme for limiting color values.
     */
    public final static ManyTrans<Color, Color> COLOR_DIFF_ABS = (inputType1, inputType2) -> new Color(
            ASMath.absoluteValue(inputType1.getRed() - inputType2.getRed()),
            ASMath.absoluteValue(inputType1.getGreen() - inputType2.getGreen()),
            ASMath.absoluteValue(inputType1.getBlue() - inputType2.getGreen())
    );
    /**
     * 颜色数值求积计算方案，在该方案中的处理逻辑为如果数值超出范围，则取0或255，如果数值没有超过范围则取数值本身，实现颜色数值的高效限制的计算方案。
     * <p>
     * The processing logic in the color value quadrature calculation scheme is to take 0 or 255 if the value exceeds the range, and take the value itself if the value does not exceed the range, achieving an efficient calculation scheme for limiting color values.
     */
    public final static ManyTrans<Color, Color> COLOR_MULTIPLY_REGULATE = (inputType1, inputType2) -> new Color(
            ASMath.regularTricolor(inputType1.getRed() * inputType2.getRed()),
            ASMath.regularTricolor(inputType1.getGreen() * inputType2.getGreen()),
            ASMath.regularTricolor(inputType1.getBlue() * inputType2.getGreen())
    );
    /**
     * 颜色数值求积计算方案，在该方案中的才处理逻辑为如果数值超出范围，则取当前数值 % 256 的结果数值作为当前颜色数值。
     * <p>
     * The processing logic in the color value quadrature calculation scheme is to take 0 or 255 if the value exceeds the range, and take the value itself if the value does not exceed the range, achieving an efficient calculation scheme for limiting color values.
     */
    public final static ManyTrans<Color, Color> COLOR_MULTIPLY_REMAINDER = (inputType1, inputType2) -> {
        int red = inputType1.getRed() + inputType2.getRed();
        int green = inputType1.getGreen() + inputType2.getGreen();
        int blue = inputType1.getBlue() + inputType2.getGreen();
        return new Color(
                red - (red >> _G_ << _G_),
                green - (green >> _G_ << _G_),
                blue - (blue >> _G_ << _G_)
        );
    };
    /**
     * 将矩阵中所有坐标点计算为： 当前坐标点 = 右坐标 - 左坐标
     */
    public final static Consumer<Color[][]> CALCULATE_GRADIENT_RL = colors -> {
        if (colors[0].length <= 2) return;
        for (Color[] color : colors) {
            Color left = color[0], right = color[2];
            color[0] = color[1];
            for (int i = 1, maxI = color.length - 2; i < maxI; ) {
                Color now = right;
                color[i] = COLOR_DIFF_REGULATE.function(right, left);
                left = now;
                right = color[++i];
            }
        }
    };
    /**
     * 将矩阵中所有坐标点计算为： 当前坐标点 = 下坐标 - 上坐标
     */
    public final static Consumer<Color[][]> CALCULATE_GRADIENT_LH = colors -> {
        if (colors.length <= 2) return;
        Color[] back = colors[0], next = colors[2];
        for (int i = 1, maxI = colors.length - 2; i < maxI; ) {
            Color[] now1 = colors[i].clone();
            Color[] now2 = next;
            for (int index = 0; index < now2.length; index++) {
                now2[index] = COLOR_DIFF_REGULATE.function(next[index], back[index]);
            }
            back = now1;
            next = colors[++i];
        }
    };
    /**
     * 将矩阵中所有坐标点计算为： 当前坐标点 = 右坐标 - 左坐标
     */
    public final static Consumer<Color[][]> CALCULATE_GRADIENT_RL_ABS = colors -> {
        if (colors[0].length <= 2) return;
        for (Color[] color : colors) {
            Color left = color[0], right = color[2];
            color[0] = color[1];
            for (int i = 1, maxI = color.length - 2; i < maxI; ) {
                Color now = right;
                color[i] = COLOR_DIFF_ABS.function(right, left);
                left = now;
                right = color[++i];
            }
        }
    };
    /**
     * 将矩阵中所有坐标点计算为： 当前坐标点 = 下坐标 - 上坐标
     */
    public final static Consumer<Color[][]> CALCULATE_GRADIENT_LH_ABS = colors -> {
        if (colors.length <= 2) return;
        Color[] back = colors[0], next = colors[2];
        for (int i = 1, maxI = colors.length - 2; i < maxI; ) {
            Color[] now1 = colors[i].clone();
            Color[] now2 = next;
            for (int index = 0; index < now2.length; index++) {
                now2[index] = COLOR_DIFF_ABS.function(next[index], back[index]);
            }
            back = now1;
            next = colors[++i];
        }
    };
    /**
     * 矩阵左右反转操作，其参数是可选的，如果您想要传递参数，请按照如下方式传递。
     * key = "isCopy"   value = boolean 数值   代表反转操作是否需要拷贝出新矩阵，如果不需要则在原矩阵中进行反转。
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> REVERSE_LR = (colorMatrix, value) -> {
        if (value != null) {
            Cell<?> isCopy = value.get("isCopy");
            if (isCopy != null) {
                return colorMatrix.reverseLR(Boolean.parseBoolean(isCopy.toString()));
            }
        }
        return colorMatrix.reverseLR(true);
    };
    /**
     * 矩阵左右反转操作，其参数是可选的，如果您想要传递参数，请按照如下方式传递。
     * key = "isCopy"   value = boolean 数值
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> REVERSE_BT = (colorMatrix, value) -> {
        if (value != null) {
            Cell<?> isCopy = value.get("isCopy");
            if (isCopy != null) {
                return colorMatrix.reverseBT(Boolean.parseBoolean(isCopy.toString()));
            }
        }
        return colorMatrix.reverseBT(true);
    };
    /**
     * 矩阵按照左右的方向进行拉伸操作，使得图像变宽，需要传递参数 times。
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> SLIT_LR = (colorMatrix, value) -> {
        if (value == null || !value.containsKey("times")) {
            throw new OperatorOperationException("矩阵拉伸操作需要您传递配置项 times 其代表每一个像素在原矩阵中的横向拉伸倍数。\nThe matrix stretching operation requires you to pass the configuration item times, which represents the horizontal stretching multiple of each pixel in the original matrix.");
        }
        // 获取到横向拉伸倍数
        int times = value.get("times").getIntValue();
        // 开始拉伸
        Color[][] res = new Color[colorMatrix.getRowCount()][colorMatrix.getColCount() * times];
        int index = -1;
        for (Color[] colors : colorMatrix.toArrays()) {
            Color[] re = res[++index];
            int x1 = 0, x2 = times;
            for (Color color : colors) {
                // 在这里拉伸一个像素为原来的 times 倍宽
                for (int x = x1; x < x2; x++) {
                    re[x] = color;
                }
                x1 += times;
                x2 += times;
            }
        }
        return ColorMatrix.parse(res);
    };
    /**
     * 矩阵按照上下的方向进行拉伸操作，使得图像变宽，需要传递参数 times。
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> SLIT_BT = (colorMatrix, value) -> {
        if (value == null || !value.containsKey("times")) {
            throw new OperatorOperationException("矩阵拉伸操作需要您传递配置项 times 其代表每一个像素在原矩阵中的纵向拉伸倍数。\nThe matrix stretching operation requires you to pass the configuration item times, which represents the vertical stretching multiple of each pixel in the original matrix.");
        }
        // 获取到横向拉伸倍数
        int times = value.get("times").getIntValue();
        // 开始拉伸
        Color[][] res = new Color[colorMatrix.getRowCount() * times][];
        int x1 = 0, x2 = times;
        for (Color[] colors : colorMatrix.toArrays()) {
            // 在这里将行拉伸 times 倍
            for (int i = x1; i < x2; i++) {
                res[i] = colors.clone();
            }
            x1 += times;
            x2 += times;
        }
        return ColorMatrix.parse(res);
    };
    /**
     * 矩阵缩放计算实现，会将矩阵按照左右的方向进行缩小操作，使得图像变窄，需要传递参数 times。
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> SCALE_LR = (colorMatrix, value) -> {
        if (value == null || !value.containsKey("times")) {
            throw new OperatorOperationException("矩阵缩放操作需要您传递配置项 times 其代表每一个像素在原矩阵中的横向缩放倍数。\nThe matrix scaling operation requires you to pass the configuration item times, which represents the vertical scaling factor of each pixel in the original matrix.");
        }
        // 获取到横向缩放倍数
        int times = value.get("times").getIntValue();
        // 开始缩放
        int resColCount = colorMatrix.getColCount() / times;
        Color[][] res = new Color[colorMatrix.getRowCount()][resColCount];
        int y = -1;
        for (Color[] color : colorMatrix.toArrays()) {
            Color[] re = res[++y];
            for (int x = 0, x1 = 0; x1 < resColCount; x += times, x1++) {
                re[x1] = color[x];
            }
        }
        return ColorMatrix.parse(res);
    };
    /**
     * 矩阵缩放计算实现，会将矩阵按照上下的方向进行缩小操作，使得图像变扁，需要传递参数 times。
     */
    public final static ProTransForm<ColorMatrix, ColorMatrix> SCALE_BT = (colorMatrix, value) -> {
        if (value == null || !value.containsKey("times")) {
            throw new OperatorOperationException("矩阵缩放操作需要您传递配置项 times 其代表每一个像素在原矩阵中的纵向缩放倍数。\nThe matrix scaling operation requires you to pass the configuration item times, which represents the vertical scaling factor of each pixel in the original matrix.");
        }
        // 获取到纵向缩放倍数
        int times = value.get("times").getIntValue();
        // 开始缩放
        int resRowCount = colorMatrix.getRowCount() / times;
        Color[][] colors = colorMatrix.toArrays();
        Color[][] res = new Color[resRowCount][colorMatrix.getColCount()];
        for (int y = 0, y1 = 0; y1 < resRowCount; y += times, y1++) {
            res[y1] = colors[y].clone();
        }
        return ColorMatrix.parse(res);
    };
    /**
     * 池化逻辑实现，将当前矩阵内的所有像素中 R G B 颜色数值的最大值逐个单独提取出来，并获取新颜色。
     * <p>
     * The pooling logic implementation extracts the maximum values of R, G, and B color values from all pixels in the current matrix individually, and obtains new colors.
     */
    public final static Transformation<ColorMatrix, Color> POOL_RGB_OBO_MAX = new PoolRgbOboMAX();
    /**
     * 池化逻辑实现，将当前矩阵内的所有像素中 RGB 颜色数值的最大值直接提取出来，成为新颜色。
     * <p>
     * The pooling logic implementation directly extracts the maximum value of RGB color values from all pixels in the current matrix, becoming a new color.
     */
    public final static Transformation<ColorMatrix, Color> POOL_RGB_MAX = colorMatrix -> {
        int maxNum = 0;
        Color maxColor = Color.BLACK;
        for (Color[] matrix : colorMatrix) {
            for (Color color : matrix) {
                int rgb = color.getRGB();
                if (maxNum < rgb) {
                    maxColor = color;
                    maxNum = rgb;
                }
            }
        }
        return maxColor;
    };
    /**
     * 池化逻辑实现，将当前矩阵内的所有像素中 R G B 三个通道的颜色数值的均值逐个计算出来，并根据结果获取新颜色。
     * <p>
     * The pooling logic implementation calculates the average color values of the R, G, and B channels in all pixels in the current matrix one by one, and obtains new colors based on the results.
     */
    public final static Transformation<ColorMatrix, Color> POOL_RGB_OBO_MEAN = colorMatrix -> {
        int mean_r = 0, mean_g = 0, mean_b = 0;
        for (Color[] matrix : colorMatrix) {
            for (Color color : matrix) {
                mean_r += color.getRed();
                mean_g += color.getGreen();
                mean_b += color.getBlue();
            }
        }
        int size = colorMatrix.getNumberOfDimensions();
        return new Color(mean_r / size, mean_g / size, mean_b / size);
    };
    /**
     * 池化逻辑实现，将当前矩阵内的所有像素中 RGB 颜色数值的均值逐个计算出来，并根据结果获取新颜色。
     * <p>
     * The pooling logic implementation calculates the average color values of the R, G, and B channels in all pixels in the current matrix one by one, and obtains new colors based on the results.
     */
    public final static Transformation<ColorMatrix, Color> POOL_RGB_MEAN = colorMatrix -> {
        int mean = 0;
        for (Color[] matrix : colorMatrix) {
            for (Color color : matrix) {
                mean += color.getRGB();
            }
        }
        return new Color(mean / colorMatrix.getNumberOfDimensions());
    };

    private static final long serialVersionUID = SerialVersionUID.ColorMatrix.getNum();
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
     * @param v           矩阵中的所有图像的尺寸参数。
     *                    <p>
     *                    The size parameters of all images in the matrix.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parse(String inputString, int... v) {
        return parse(new File(inputString), v);
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param inputFile 要读取的目标图像文件路径。
     *                  <p>
     *                  The target image file path to read.
     * @param v         矩阵中的所有图像的尺寸参数。
     *                  <p>
     *                  The size parameters of all images in the matrix.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parse(File inputFile, int... v) {
        return parse(ASIO.parseImageGetColorArray(inputFile, v));
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的灰度整形数值。
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
    public static ColorMatrix parseGrayscale(String inputString, int... v) {
        return parseGrayscale(new File(inputString), v);
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的灰度整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param inputFile 要读取的目标图像文件路径。
     *                  <p>
     *                  The target image file path to read.
     * @param v         矩阵中的所有图像的尺寸参数。
     *                  <p>
     *                  The size parameters of all images in the matrix.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parseGrayscale(File inputFile, int... v) {
        return GrayscaleColors(ASIO.parseImageGetColorArray(inputFile, v));
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
     * 将一个图像矩阵中的所有像素转换成为三原色均值，获取到灰度矩阵。
     *
     * @param colors 需要被转换的颜色矩阵（注意，该矩阵将会被修改）
     * @return 转换之后的颜色矩阵
     */
    protected static ColorMatrix GrayscaleColors(Color[][] colors) {
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
     * 获取到文本数据输出逻辑实现对象
     *
     * @param colorMatrix 需要被输出的图像矩形
     * @param sep         输出时需要使用的分隔符
     * @return 一个专用于将图像中的颜色数值按照CSV的格式输出到指定文件中的实现逻辑。
     */
    public static Consumer<BufferedWriter> getSAVE_TEXT(ColorMatrix colorMatrix, char sep) {
        return (stream) -> {
            try {
                int rowCount = 0;
                for (Color[] colors : colorMatrix.toArrays()) {
                    stream.write(String.valueOf(++rowCount));
                    for (Color color : colors) {
                        stream.write(sep);
                        stream.write(String.valueOf(color.getRGB()));
                    }
                    stream.newLine();
                }
            } catch (IOException e) {
                throw new OperatorOperationException("Write data exception!", e);
            }
        };
    }

    /**
     * 将图像以ASCII的形式输出到指定文件中。
     *
     * @param colorMatrix   需要被输出的图像对象
     * @param Mode          需要被输出的颜色通道
     * @param colorBoundary 输出时的颜色边界数值
     * @param imageAscii1   输出时的ASCII
     * @param imageAscii2   输出时的ASCII
     * @return 一个ASCII符号组成的文本图像
     */
    public static Consumer<BufferedWriter> getSAVE_ASCII(ColorMatrix colorMatrix, byte Mode, int colorBoundary, char imageAscii1, char imageAscii2) {
        return bufferedWriter -> {
            try {
                for (Color[] colors : colorMatrix.toArrays()) {
                    for (Color color : colors) {
                        if (((color.getRGB() >> Mode) & 0xFF) > colorBoundary) {
                            bufferedWriter.write(imageAscii1);
                        } else bufferedWriter.write(imageAscii2);
                    }
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new OperatorOperationException("Write data exception!", e);
            }
        };
    }

    /**
     * 使用克隆的方式创建出一个新的矩阵对象。
     *
     * @param colorMatrix 需要被克隆的原矩阵对象。
     * @param isCopy      克隆操作中的元素是否使用深拷贝
     * @return 克隆操作成功之后的新矩阵对象。
     */
    public ColorMatrix clone(ColorMatrix colorMatrix, boolean isCopy) {
        return new ColorMatrix(
                colorMatrix.getColCount(), colorMatrix.getRowCount(),
                isCopy ? colorMatrix.copyToNewArrays() : colorMatrix.toArrays(),
                colorMatrix.isGrayscale
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
    public ColorMatrix add(ColorMatrix value) {
        return agg(value, COLOR_SUM_REMAINDER);
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
        return agg(value, COLOR_DIFF_REMAINDER);
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
        Color[][] colors1 = this.copyToNewArrays();
        int v = value.intValue();
        for (Color[] colors : colors1) {
            int index = -1;
            for (Color color : colors) {
                colors[++index] = new Color(color.getRGB() + v);
            }
        }
        return ColorMatrix.parse(colors1);
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
        Color[][] colors1 = this.copyToNewArrays();
        int v = value.intValue();
        for (Color[] colors : colors1) {
            int index = -1;
            for (Color color : colors) {
                colors[++index] = new Color(color.getRGB() - v);
            }
        }
        return ColorMatrix.parse(colors1);
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
        return agg(value, COLOR_DIFF_ABS);
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
    public ColorMatrix agg(ColorMatrix value, ManyTrans<Color, Color> manyTrans) {
        int rowCount1 = this.getRowCount();
        int colCount1 = this.getColCount();
        int rowCount2 = value.getRowCount();
        int colCount2 = value.getColCount();
        if (rowCount1 == rowCount2 && colCount1 == colCount2) {
            Color[][] res = new Color[rowCount1][colCount1];
            Color[][] that = value.toArrays();
            int y = -1;
            for (Color[] colors1 : this.toArrays()) {
                Color[] resRow = res[++y];
                Color[] colors2 = that[y];
                int x = -1;
                for (Color color : colors1) {
                    resRow[++x] = manyTrans.function(color, colors2[x]);
                }
            }
            return ColorMatrix.parse(res);
        } else {
            throw new OperatorOperationException("图像矩阵在进行加法运算的时候出现了错误，因为图像中的矩阵像素数量不一致！！！\nAn error occurred during the addition operation of the image matrix because the number of matrix pixels in the image is inconsistent!!!\nERROR => mat1.row = " +
                    rowCount1 + "\tmat2.row = " + rowCount2 + "\tmat1.col = " + colCount1 + "\tmat2.col = " + colCount2
            );
        }
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
    public ColorMatrix calculate(Consumer<Color[][]> action, boolean isCopy) {
        if (isCopy) {
            Color[][] colors = this.copyToNewArrays();
            action.accept(colors);
            return ColorMatrix.parse(colors);
        } else {
            action.accept(this.toArrays());
            return this;
        }
    }

    /**
     * 矩阵变换操作函数，在此函数中您可以传递多个矩阵变换模式，实现多种不同的矩阵转换效果，同时还可以自定义矩阵变换时需要使用的逻辑。
     *
     * @param transformation 矩阵变换逻辑。
     * @param value          矩阵中的变换操作计算时需要的其它参数对象。
     * @return 矩阵变换之后返回的新矩阵对象。
     */
    public ColorMatrix converter(ProTransForm<ColorMatrix, ColorMatrix> transformation, HashMap<String, Cell<?>> value) {
        return transformation.function(this, value);
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
     * 将指定的像素设置到指定的坐标上。
     * <p>
     * Set the specified pixel to the specified coordinates.
     *
     * @param row   纵坐标 代表的是 行索引。
     *              <p>
     *              The vertical axis represents the row index.
     * @param col   横坐标 代表的是 列索引。
     *              <p>
     *              The horizontal axis represents the column index.
     * @param color 当前坐标上要覆盖的新颜色对象。
     *              <p>
     *              The new color object to be overlaid on the current coordinate.
     */
    public void set(int row, int col, Color color) {
        this.toArrays()[row][col] = color;
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
    public final int getNumberOfDimensions() {
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
    public Color[][] copyToNewArrays() {
        Color[][] colors = this.toArrays();
        Color[][] res = new Color[colors.length][colors[0].length];
        ASClass.array2DCopy(colors, res);
        return res;
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
    public Vector<?, ?, Color[]>[] toVectors(Vector<?, ?, Color[]>[] array) {
        throw new UnsupportedOperationException("The image matrix currently does not support vector flattening.");
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
        return ColorMatrix.parse(
                ASClass.reShape(
                        this, (Transformation<int[], Color[][]>) ints -> new Color[ints[0]][ints[1]], shape
                )
        );
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
    public Color[] flatten() {
        Color[] res = new Color[this.getNumberOfDimensions()];
        int index = 0;
        // 开始进行元素拷贝
        for (Color[] colors : this) {
            System.arraycopy(colors, 0, res, index, colors.length);
            index += colors.length;
        }
        return res;
    }

    /**
     * 将图像进行模糊处理
     *
     * @param isCopy 如果设置为true 代表拷贝一份新矩阵然后再模糊计算
     * @return 方框模糊处理过的矩阵对象
     */
    public ColorMatrix boxBlur(boolean isCopy) {
        if (this.isGrayscale) {
            if (isCopy) {
                return ColorMatrix.parse(ASMath.boxBlurGrayscale(this.copyToNewArrays()));
            } else {
                System.arraycopy(ASMath.boxBlurGrayscale(this.toArrays()), 0, this.toArrays(), 0, this.getRowCount());
                return this;
            }
        } else {
            if (isCopy) {
                return ColorMatrix.parse(ASMath.boxBlur(this.copyToNewArrays()));
            } else {
                System.arraycopy(ASMath.boxBlur(this.toArrays()), 0, this.toArrays(), 0, this.getRowCount());
                return this;
            }
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
     * @return 经过函数计算之后的图像矩阵对象。
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
                            Math.min(color.getRed() + R, SINGLE_CHANNEL_MAXIMUM),
                            Math.min(color.getGreen() + G, SINGLE_CHANNEL_MAXIMUM),
                            Math.min(color.getBlue() + B, SINGLE_CHANNEL_MAXIMUM)
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
                            Math.min(color.getRed() + R, SINGLE_CHANNEL_MAXIMUM),
                            Math.min(color.getGreen() + G, SINGLE_CHANNEL_MAXIMUM),
                            Math.min(color.getBlue() + B, SINGLE_CHANNEL_MAXIMUM)
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
     * @return 经过函数计算之后的图像矩阵对象。
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
    public final void forcedColorChange() {
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
     * 调整图像整体的光度色彩值，常用于调整图像中的曝光数值。
     * <p>
     * Adjust the overall photometric color value of the image, which is often used to adjust the exposure value in the image.
     *
     * @param dimmingValue 调整时的光度阈值 大于1 表示亮度提高，小于1 表示亮度变暗，常用取值范围为[-1,3]
     *                     <p>
     *                     When adjusting, the brightness threshold is greater than 1, which means the brightness is increased, and less than 1, which means the brightness is darkened. The common value range is [- 1,3]
     */
    public final void dimming(float dimmingValue) {
        // 获取到  平均值 - (平均值 * 亮度系数)  的值，每一个像素点都需要减去该数值
        int red_avg2, green_avg2, blue_avg2;
        {
            float v = dimmingValue - 1;
            // 获取到像素数量
            int size = this.getNumberOfDimensions();
            // 获取到三种颜色的总和
            int red_sum = 0, green_sum = 0, blue_sum = 0;
            for (Color[] colors : this.toArrays()) {
                for (Color color : colors) {
                    red_sum += color.getRed();
                    green_sum += color.getGreen();
                    blue_sum += color.getBlue();
                }
            }
            // 先计算出 整个图片中RGB三种颜色各自的平均值 然后计算出 平均值 - (平均值 * 亮度系数)
            red_avg2 = (int) (-(red_sum / size) * (v));
            green_avg2 = (int) (-(green_sum / size) * (v));
            blue_avg2 = (int) (-(blue_sum / size) * (v));
        }
        // 开始进行图像亮度转换
        for (Color[] colors : this.toArrays()) {
            for (int i = 0, colorsLength = colors.length; i < colorsLength; i++) {
                Color color = colors[i];
                // 在这里进行图像颜色的转换
                colors[i] = new Color(
                        ASMath.regularTricolor(color.getRed() - red_avg2),
                        ASMath.regularTricolor(color.getGreen() - green_avg2),
                        ASMath.regularTricolor(color.getBlue() - blue_avg2)
                );
            }
        }
    }

    /**
     * 调整图像中的图像对比度，该函数将会根据图像的情况来对图像进行数据的处理。
     * <p>
     * Adjust the image contrast in the image. This function will process the image data according to the image situation.
     *
     * @param contrastThreshold 对比度阈值 取值范围为 [-255, 255]
     *                          <p>
     *                          The contrast threshold value range is [- 255, 255]
     */
    public final void contrast(int contrastThreshold) {
        if (this.isGrayscale) {
            // 计算出灰度颜色均值
            int green_avg = 0;
            {
                for (Color[] colors : this.toArrays()) {
                    for (Color color : colors) {
                        green_avg += color.getGreen();
                    }
                }
                green_avg /= this.getNumberOfDimensions();
            }
            for (Color[] colors : this.toArrays()) {
                for (int i = 0, colorsLength = colors.length; i < colorsLength; i++) {
                    int green = colors[i].getGreen();
                    // 查看当前颜色是否为亮色 如果是就提高亮度
                    if (green < green_avg) {
                        // 暗色 继续提高灰度
                        int g = ASMath.regularTricolor(green - contrastThreshold);
                        colors[i] = new Color(g, g, g);
                        continue;
                    }
                    if (green > green_avg) {
                        // 亮色 降低灰度
                        int g = ASMath.regularTricolor(green + contrastThreshold);
                        colors[i] = new Color(g, g, g);
                    }
                }
            }
        } else {
            // 计算出三种颜色均值
            int red_avg = 0, green_avg = 0, blue_avg = 0;
            {
                for (Color[] colors : this.toArrays()) {
                    for (Color color : colors) {
                        red_avg += color.getRed();
                        green_avg += color.getGreen();
                        blue_avg += color.getBlue();
                    }
                }
                int number = this.getNumberOfDimensions();
                red_avg /= number;
                green_avg /= number;
                blue_avg /= number;
            }
            for (Color[] colors : this.toArrays()) {
                for (int i = 0, colorsLength = colors.length; i < colorsLength; i++) {
                    Color color = colors[i];
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    // 查看当前颜色是否为亮色 如果是就提高亮度
                    if (red > red_avg) red += contrastThreshold;
                    else if (red < red_avg) red -= contrastThreshold;
                    if (green > green_avg) green += contrastThreshold;
                    else if (green < green_avg) green -= contrastThreshold;
                    if (blue > blue_avg) blue += contrastThreshold;
                    else if (blue < blue_avg) blue -= contrastThreshold;
                    colors[i] = new Color(
                            ASMath.regularTricolor(red), ASMath.regularTricolor(green), ASMath.regularTricolor(blue)
                    );
                }
            }
        }
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
    public ColorMatrix extractImage(int x1, int y1, int x2, int y2) {
        // 将四个参数进行规整
        x1 = ASMath.regularNumber(this.getColCount() - 1, 0, x1);
        y1 = ASMath.regularNumber(this.getRowCount() - 1, 0, y1);
        x2 = ASMath.regularNumber(this.getColCount() - 1, 0, x2);
        y2 = ASMath.regularNumber(this.getRowCount() - 1, 0, y2);
        if (x1 >= x2 || y1 >= y2) {
            throw new OperatorOperationException("图像提取发生错误，您设置的提取坐标点有误!!!\nAn error occurred in image extraction. The extraction coordinate point you set is incorrect!!!\n" +
                    "ERROR => (" + x1 + ',' + y1 + ") >= (" + x2 + ',' + y2 + ')');
        }
        if (x2 >= this.getColCount() || y2 >= this.getRowCount()) {
            throw new OperatorOperationException("图像提取发生错误，您不能提取不存在于图像中的坐标点\nAn error occurred in image extraction. You cannot extract coordinate points that do not exist in the image\n" +
                    "ERROR => (" + x2 + ',' + y2 + ')');
        }
        Color[][] colors = new Color[y2 - y1 + 1][x2 - x1 + 1];
        Color[][] srcImage = this.toArrays();
        final int maxIndex = srcImage.length - 1;
        for (Color[] color : colors) {
            final int y = ASMath.regularNumber(maxIndex, 0, y1++);
            final int x = ASMath.regularNumber(color.length - 1, 0, x1);
            Color[] row = srcImage[y];
            System.arraycopy(row, x, color, 0, Math.min(color.length, row.length));
        }
        return ColorMatrix.parse(colors);
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
    public ColorMatrix extractImageSrc(int y1, int y2) {
        if (y1 >= y2) {
            throw new OperatorOperationException("图像提取发生错误，您设置的提取坐标点有误!!!\nAn error occurred in image extraction. The extraction coordinate point you set is incorrect!!!\n" +
                    "ERROR => (0," + y1 + ") >= (0," + y2 + ')');
        }
        Color[][] res_colors = new Color[y2 - y1][];
        Color[][] colors1 = this.toArrays();
        int index = -1;
        for (int i = y1; i < y2; i++) {
            res_colors[++index] = colors1[i];
        }
        return ColorMatrix.parse(res_colors);
    }

    /**
     * 合并两个图像对象，会将 colorMatrix 中的图像 从本图像中 (x1,y1) 坐标处开始进行覆盖。
     * <p>
     * Merging two image objects will cover the image in the colorMatrix from the (x1, y1) coordinates in this image.
     *
     * @param colorMatrix 需要合并进来的图像矩阵对象.
     *                    <p>
     *                    The image matrix object to be merged
     * @param x1          合并操作进行时的本图像起始坐标。
     *                    <p>
     *                    The starting coordinate of this image when the merge operation is in progress.
     * @param y1          合并操作进行时的本图像起始坐标。
     *                    <p>
     *                    The starting coordinate of this image when the merge operation is in progress.
     */
    public void merge(ColorMatrix colorMatrix, int x1, int y1) {
        if (y1 < 0 || y1 >= getRowCount()) {
            throw new OperatorOperationException("Illegal coordinate axis value y1 = " + y1);
        }
        if (x1 < 0 || x1 >= getColCount()) {
            throw new OperatorOperationException("Illegal coordinate axis value x1 = " + x1);
        }
        Color[][] colors1 = this.toArrays();
        int colCount = this.getColCount();
        int rowCount = this.getRowCount();
        x1 = Math.min(x1, colCount - 1);
        y1 = Math.min(y1, rowCount - 1);
        int length = Math.min(colorMatrix.getColCount(), colCount);
        for (Color[] colors : colorMatrix.toArrays()) {
            System.arraycopy(colors, 0, colors1[y1++], x1, length);
        }
    }

    /**
     * 将图像矩阵中的指定通道的色彩数值进行规整计算，能够显著提升图像的某些特征，同时可以有效去除图像的无用特征。
     * <p>
     * Regularizing the color values of the specified channels in the image matrix can significantly improve some features of the image, while effectively removing unwanted features from the image.
     *
     * @param Mode          在进行通道色彩的获取的时候，需要指定规整时的颜色通道标准，在指定通道的基础上进行规整，该参数可以直接从 ColorMatrix 类中获取到。
     *                      <p>
     *                      When obtaining channel colors, it is necessary to specify the color channel standard for regularization, which is based on the specified channel. This parameter can be directly obtained from the ColorMatrix class.
     * @param colorBoundary 颜色边界阈值，当被判断的颜色数值达到了阈值，则判断当前坐标颜色为真，返回为假。
     *                      <p>
     *                      The color boundary threshold value. When the judged color value reaches the threshold value, the current coordinate color is judged to be true, and the return value is false.
     * @param trueColor     图像中所有颜色为真的坐标，需要变更为的新颜色对象。
     *                      <p>
     *                      Coordinates where all colors in the image are true and need to be changed to a new color object for.
     * @param falseColor    图像中所有颜色为假的坐标，需要变更为的新颜色对象。
     *                      <p>
     *                      All colors in the image are fake coordinates and need to be changed to a new color object for.
     */
    public void globalBinary(byte Mode, int colorBoundary, int trueColor, int falseColor) {
        if (colorBoundary < 0 || colorBoundary > 0xff) return;
        Color color1 = new Color(trueColor);
        Color color2 = new Color(falseColor);
        for (Color[] colors : this.toArrays()) {
            int index = -1;
            for (Color color : colors) {
                if (color == null || ((color.getRGB() >> Mode) & 0xFF) <= colorBoundary) colors[++index] = color2;
                else colors[++index] = color1;
            }
        }
    }

    /**
     * 基于坐标周边点进行图像二值化的计算，该操作与全局二值化操作做之间最大的差别在于，其中的与阈值进行比对的数值并不是所有坐标点，而是当前坐标点的周边坐标点的对应通道的颜色数值，能够有效的将二值化体现出来。
     * <p>
     * The biggest difference between the calculation of image binary based on coordinate peripheral points and the global binary operation is that the value compared to the threshold value is not all coordinate points, but the color value of the corresponding channel of the peripheral coordinate points of the current coordinate point, which can effectively reflect binary.
     *
     * @param Mode         在进行通道色彩的获取的时候，需要指定规整时的颜色通道标准，在指定通道的基础上进行规整，该参数可以直接从 ColorMatrix 类中获取到。
     *                     <p>
     *                     When obtaining channel colors, it is necessary to specify the color channel standard for regularization, which is based on the specified channel. This parameter can be directly obtained from the ColorMatrix class.
     * @param subImageNum  局部拆分的子图像矩阵数量，该数量将有效实现矩阵的局部提取，并可以对局部进行修改。
     *                     <p>
     *                     The number of sub image matrices that are partially split, which will effectively achieve local extraction of the matrix, and can be modified locally.
     * @param trueColor    图像中所有颜色为真的坐标，需要变更为的新颜色对象。
     *                     <p>
     *                     Coordinates where all colors in the image are true and need to be changed to a new color object for.
     * @param falseColor   图像中所有颜色为假的坐标，需要变更为的新颜色对象。
     *                     <p>
     *                     All colors in the image are fake coordinates and need to be changed to a new color object for.
     * @param polarization 局部颜色数值均值会做为局部二值化的阈值，该参数将会被均值进行加法运算，得出一个新的阈值，该参数是一个可选参数。
     */
    public void localBinary(byte Mode, int subImageNum, int trueColor, int falseColor, int polarization) {
        int rowCount = this.getRowCount();
        if (subImageNum <= 0 || subImageNum > rowCount) return;
        // 根据选择的矩阵块数来计算出图像矩阵的纵坐标
        double sep1 = rowCount / (double) subImageNum;
        int sep = (int) sep1, start = 0, end = sep;
        boolean isInt = sep1 == sep;
        ColorMatrix[] colorMatrices = new ColorMatrix[isInt ? subImageNum : subImageNum + 1];
        for (int i = 0; i < subImageNum; i++) {
            colorMatrices[i] = this.extractImageSrc(start, end);
            start += sep;
            end += sep;
        }
        if (!isInt) {
            colorMatrices[subImageNum] = this.extractImageSrc(start, rowCount - 1);
        }
        // 开始进行数据的处理
        for (ColorMatrix colorMatrix : colorMatrices) {
            colorMatrix.globalBinary(
                    Mode, ((int) colorMatrix.avg(Mode)) + polarization, trueColor, falseColor
            );
        }
    }

    /**
     * 提取出某个颜色通道的所有坐标颜色数值的均值。
     * <p>
     * Extract the mean value of all coordinate color values for a certain color channel.
     *
     * @param colorMode 需要被提取的颜色通道高。
     *                  <p>
     *                  The color channel that needs to be extracted is high.
     * @return 对应颜色通道的所有像素颜色数值的均值。
     * <p>
     * The average value of all pixel color values corresponding to the color channel.
     */
    public double avg(byte colorMode) {
        int sum = 0;
        for (Color[] colors : this.toArrays()) {
            for (Color color : colors) {
                sum += (color.getRGB() >> colorMode) & 0xFF;
            }
        }
        return sum / (double) (this.getNumberOfDimensions());
    }

    /**
     * 计算出轮廓内的面积大小，以像素为单位。
     * <p>
     * Calculate the size of the area within the contour, in pixels.
     *
     * @param contourColor 轮廓线的颜色对象，如果像素的颜色数值与此值一致，则认为其属于边框。
     *                     <p>
     *                     The color object of the outline. If the color value of a pixel matches this value, it is considered to belong to the border.
     * @return 轮廓内的所有像素数量。
     * <p>
     * The number of all pixels within the contour.
     */
    public int contourArea(Color contourColor) {
        int rgb = contourColor.getRGB();
        int res = 0;
        for (Color[] colors : this.toArrays()) {
            boolean isOk = false;
            for (Color color : colors) {
                if (isOk) {
                    ++res;
                    if (color.getRGB() == rgb) {
                        isOk = false;
                    }
                    continue;
                }
                if (color.getRGB() == rgb) {
                    isOk = true;
                    ++res;
                }
            }
        }
        return res;
    }

    /**
     * 图像腐蚀函数，在该函数的处理之下，可以对图像某些不足够的颜色数值进行腐蚀操作，
     *
     * @param width  用于腐蚀的图像卷积核的宽度
     * @param height 用于腐蚀的图像卷积核的高度
     * @param isCopy 如果设置为true 则代表在一个新的矩阵对象中进行腐蚀的操作计算，如果设置为false 则代表在原矩阵的基础上进行腐蚀不会出现新矩阵对象。
     * @return 图像矩阵经过了腐蚀操作之后返回的新矩阵对象。
     */
    public ColorMatrix erode(int width, int height, boolean isCopy) {
        return erode(width, height, isCopy, Color.BLACK);
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
    public ColorMatrix erode(int width, int height, boolean isCopy, Color backColor) {
        // 计算出来最终的图像的长宽
        int rowCount = this.getRowCount() - (this.getRowCount() % height);
        int colCount = this.getColCount() - (this.getColCount() % width);
        int maxRowIndex = rowCount - height - 1;
        int maxColIndex = colCount - width - 1;
        int colorNum = backColor.getRGB();
        ColorMatrix res = isCopy ? ColorMatrix.parse(this.copyToNewArrays()) : this;
        // 开始不断的提取出子矩阵数据
        for (int y = 0; y < maxRowIndex; ) {
            int ey = y + height;
            int ey1 = ey - 1;
            for (int x = 0; x < maxColIndex; ) {
                int ex = x + width;
                // 提取每一行的所有列数据 并判断矩阵中是否有黑色，如果找到了黑色 就将 当前子矩阵中的所有颜色都变更为黑
                ColorMatrix nowSub = res.extractImage(x, y, ex - 1, ey1);
                boolean isR = false;
                for (Color[] colors : nowSub.toArrays()) {
                    for (Color color : colors) {
                        if (color.getRGB() == colorNum) {
                            // 发现了腐蚀色 直接将该子矩阵中的所有颜色更换为腐蚀色
                            isR = true;
                        }
                    }
                    if (isR) {
                        for (Color[] toArray : nowSub.toArrays()) {
                            Arrays.fill(toArray, backColor);
                        }
                        break;
                    }
                }
                // 一行的检测结束，如果这一行中有黑色像素，那么代表子矩阵被替换完毕，进行合并
                if (isR) {
                    // 合并子矩阵到矩阵中
                    res.merge(nowSub, x, y);
                }
                x = ex;
            }
            y = ey;
        }
        return res;
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
    public ColorMatrix append(ColorMatrix colorMatrix, boolean isLR) {
        if (isLR) {
            Color[][] colors1 = this.toArrays();
            Color[][] colors2 = colorMatrix.toArrays();
            if (colors1.length != colors2.length) {
                throw new OperatorOperationException("左右追加的矩阵对象需要保持行数相同！");
            }
            Color[][] res = new Color[colors1.length][this.getColCount() + colorMatrix.getColCount()];
            int index = -1;
            for (Color[] re : res) {
                ASClass.mergeArray(re, colors1[++index], colors2[index]);
            }
            return ColorMatrix.parse(res);
        }
        Color[][] colors1 = this.copyToNewArrays();
        Color[][] colors2 = colorMatrix.copyToNewArrays();
        if (colors1.length != colors2.length) {
            throw new OperatorOperationException("上下追加的矩阵对象需要保持列数相同！");
        }
        Color[][] res = new Color[colors1.length + colors2.length][];
        ASClass.mergeArray(res, colors1, colors2);
        return ColorMatrix.parse(res);
    }

    /**
     * 将图像矩阵中的指定通道的颜色整数数值矩阵获取到。
     *
     * @param colorMode 需要被获取到颜色通道编码。
     * @return 对应颜色通道的颜色数值组成的颜色矩阵对象。
     */
    public final IntegerMatrix getChannel(int colorMode) {
        int[][] res = new int[this.getRowCount()][this.getColCount()];
        int y = -1;
        for (Color[] colors : this.toArrays()) {
            int[] re = res[++y];
            int x = -1;
            for (Color color : colors) {
                re[++x] = (color.getRGB() >> colorMode) & 0xFF;
            }
        }
        return IntegerMatrix.parse(res);
    }

    /**
     * 将图像矩阵中的指定通道的颜色整数数值矩阵获取到。
     *
     * @param colorMode 需要被获取到颜色通道编码。
     * @return 对应颜色通道的颜色数值组成的颜色矩阵对象。
     */
    public final ColorMatrix getColorChannel(int colorMode) {
        Color[][] res = new Color[this.getRowCount()][this.getColCount()];
        int y = -1;
        int diff = colorMode == ColorMatrix._G_ ? 8 : ColorMatrix._B_ - colorMode;
        for (Color[] colors : this.toArrays()) {
            Color[] re = res[++y];
            int x = -1;
            for (Color color : colors) {
                re[++x] = new Color(((color.getRGB() >> colorMode) & 0xFF) << diff);
            }
        }
        return ColorMatrix.parse(res);
    }

    /**
     * 向矩阵中绘制一个矩阵。
     *
     * @param start 矩阵的左上角坐标对象。
     * @param end   矩阵的右下角坐标对象。
     * @param color 绘制的轮廓颜色对象。
     */
    public void drawRectangle(IntegerCoordinateTwo start, IntegerCoordinateTwo end, Color color) {
        Color[][] colors = this.toArrays();
        int x1 = start.getX();
        int x2 = end.getX();
        int y1 = start.getY();
        int y2 = end.getY();
        // 首先将起始终止点的 x 轴变更为 color 颜色（使用y轴获取）
        Color[] color1 = colors[start.getY()];
        Color[] color2 = colors[end.getY()];
        for (int i = x1; i <= x2; i++) {
            color1[i] = color;
            color2[i] = color;
        }
        // 然后将剩余所有点的 y 轴变为 color 颜色（使用x轴获取）
        for (int i = y1; i <= y2; i++) {
            Color[] colorRow = colors[i];
            colorRow[x1] = color;
            colorRow[x2] = color;
        }
    }

    /**
     * 将图像进行模板匹配计算
     *
     * @param distanceAlgorithm 需要使用的度量计算组件。
     *                          <p>
     *                          The metric calculation component that needs to be used.
     * @param template          在进行模板匹配时的被匹配模板。
     *                          <p>
     *                          The template to be matched during template matching.
     * @param channel           在进行计算时，需要被计算的颜色通道。
     *                          <p>
     *                          When calculating, the color channel to be calculated is required.
     * @param sep               图像卷积步长数值。该数值增加，会增强计算的速度与性能，该数值的减小，会增强计算的精确度。
     *                          <p>
     *                          Image convolution step size value. An increase in this value will enhance the speed and performance of the calculation, while a decrease in this value will enhance the accuracy of the calculation.
     * @param isMAX             如果设置为 true 代表取相似度系数最大的子图像坐标作为。
     *                          <p>
     *                          If set to true, the coordinate of the sub image with the largest similarity coefficient is taken as the coordinate.
     * @return 经过计算之后，与当前矩阵对象最相似的子图像的左上角坐标数值对象。
     */
    public final Map.Entry<Double, IntegerCoordinateTwo> templateMatching(DistanceAlgorithm distanceAlgorithm, ColorMatrix template, int channel, int sep, boolean isMAX) {
        // 开始匹配
        IntegerMatrix channel1 = template.getChannel(channel);
        IntegerMatrix channel2 = this.getChannel(channel);
        // 首先提取出矩阵1中相同大小的子矩阵图像
        int rowCount1 = this.getRowCount();
        int colCount1 = this.getColCount();
        int rowCount2 = template.getRowCount();
        int colCount2 = template.getColCount();
        if (rowCount1 < rowCount2 || colCount1 < colCount2) {
            throw new OperatorOperationException("您的模板矩阵尺寸过大!!");
        }
        int[] xy = new int[2];
        double value = isMAX ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (rowCount1 == rowCount2) {
            // 这种情况代表可以使用优化方案
            int ye = rowCount2 - 1;
            for (int y = 0; y < rowCount1; y += sep, ye += sep) {
                double trueDistance = distanceAlgorithm.getTrueDistance(channel2.extractSrcMat(y, ye), channel1);
                if (isMAX) {
                    if (value < trueDistance) {
                        value = trueDistance;
                        xy[1] = y;
                    }
                } else {
                    if (value > trueDistance) {
                        value = trueDistance;
                        xy[1] = y;
                    }
                }
            }
            return new AbstractMap.SimpleEntry<>(value, new IntegerCoordinateTwo(xy[0], xy[1]));
        }
        int ye = rowCount2 - 1;
        for (int y = 0; ye < rowCount1; y += sep, ye += sep) {
            int xe = colCount2 - 1;
            for (int x = 0; xe < colCount1; x += sep, xe += sep) {
                // 计算出相似度
                double trueDistance = distanceAlgorithm.getTrueDistance(channel2.extractMat(x, y, xe, ye), channel1);
                if (isMAX) {
                    if (value < trueDistance) {
                        value = trueDistance;
                        xy[0] = x;
                        xy[1] = y;
                    }
                } else {
                    if (value > trueDistance) {
                        value = trueDistance;
                        xy[0] = x;
                        xy[1] = y;
                    }
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(value, new IntegerCoordinateTwo(xy[0], xy[1]));
    }

    /**
     * @param distanceAlgorithm 需要使用的度量计算组件。
     *                          <p>
     *                          The metric calculation component that needs to be used.
     * @param template          在进行模板匹配时的被匹配模板。
     *                          <p>
     *                          The template to be matched during template matching.
     * @param channel           在进行计算时，需要被计算的颜色通道。
     *                          <p>
     *                          When calculating, the color channel to be calculated is required.
     * @param sep               图像卷积步长数值。该数值增加，会增强计算的速度与性能，该数值的减小，会增强计算的精确度。
     *                          <p>
     *                          Image convolution step size value. An increase in this value will enhance the speed and performance of the calculation, while a decrease in this value will enhance the accuracy of the calculation.
     * @param cFilter           相似系数过滤逻辑实现，在这里您将可以自定义的书写相似度系数的过滤操作，符合的系数对应的信息将会被添加到结果数据中。
     *                          <p>
     *                          The implementation of similarity coefficient filtering logic allows you to customize the filtering operation of writing similarity coefficients, and the information corresponding to the corresponding coefficients will be added to the result data.
     * @return 图像中所有符合过滤条件的子矩阵信息数据对象。
     * <p>
     * All sub matrix information data objects in the image that meet the filtering conditions.
     */
    public final ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> templateMatching(DistanceAlgorithm distanceAlgorithm, ColorMatrix template, int channel, int sep, DoubleFiltering cFilter) {
        // 开始匹配
        IntegerMatrix channel1 = template.getChannel(channel);
        IntegerMatrix channel2 = this.getChannel(channel);
        // 首先提取出矩阵1中相同大小的子矩阵图像
        int rowCount1 = this.getRowCount(), colCount1 = this.getColCount();
        int rowCount2 = template.getRowCount(), colCount2 = template.getColCount();
        if (rowCount1 < rowCount2 || colCount1 < colCount2) {
            throw new OperatorOperationException("您的模板矩阵尺寸过大!!");
        }
        ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> res = new ArrayList<>();
        if (rowCount1 == rowCount2) {
            // 这种情况代表可以使用优化方案
            int ye = rowCount2 - 1;
            for (int y = 0; y < rowCount1; y += sep, ye += sep) {
                double trueDistance = distanceAlgorithm.getTrueDistance(channel2.extractSrcMat(y, ye), channel1);
                if (cFilter.isComplianceEvents(trueDistance))
                    res.add(new AbstractMap.SimpleEntry<>(trueDistance, new IntegerCoordinateTwo(0, y)));
            }
            return res;
        }
        // 这里就代表不能够使用优化方案
        int ye = rowCount2 - 1;
        for (int y = 0; ye < rowCount1; y += sep, ye += sep) {
            int xe = colCount2 - 1;
            for (int x = 0; xe < colCount1; x += sep, xe += sep) {
                // 计算出相似度
                double trueDistance = distanceAlgorithm.getTrueDistance(channel2.extractMat(x, y, xe, ye), channel1);
                if (cFilter.isComplianceEvents(trueDistance))
                    res.add(new AbstractMap.SimpleEntry<>(trueDistance, new IntegerCoordinateTwo(x, y)));
            }
        }
        return res;
    }

    /**
     * 将其中的指定的 RGB 通道提取出来并进行叠加成为一个图像矩阵对象。
     * <p>
     * Extract the specified RGB channels from them and overlay them to form a new image matrix object.
     *
     * @param mode 要提取的通道编号之和。
     * @return 所有被加在一起的颜色通道图像矩阵层叠加成为的新的矩阵空间对象。
     * <p>
     * All the added color channel image matrix layers are superimposed to create a new matrix space object.
     */
    public final ColorMatrixSpace toRGBSpace(int mode) {
        // 0 8 16
        ColorMatrix colorChannel1 = this.getColorChannel(_B_);
        if (mode == (_G_ + _R_)) {
            Color[][] res1 = new Color[this.getRowCount()][this.getColCount()];
            Color[][] res2 = new Color[this.getRowCount()][this.getColCount()];
            int y = -1;
            for (Color[] colors : this.toArrays()) {
                Color[] re1 = res1[++y];
                Color[] re2 = res2[y];
                int x = -1;
                for (Color color : colors) {
                    re1[++x] = new Color(color.getRed(), 0, 0);
                    re2[x] = new Color(0, color.getGreen(), 0);
                }
            }
            return ColorMatrixSpace.parse(
                    parse(res1),
                    parse(res2),
                    colorChannel1
            );
        } else if (mode == _R_ || mode == _G_) {
            return ColorMatrixSpace.parse(this.getColorChannel(mode), colorChannel1);
        } else {
            return ColorMatrixSpace.parse(colorChannel1);
        }
    }

    /**
     * 将当前图像矩阵对象按照一定的规则生成成为一个整形的矩形空间对象。
     * <p>
     * Generate the current image matrix object into a shaped rectangular space object according to certain rules.
     *
     * @param mode 要提取的颜色通道编号列表，例如其中第一个为红色通道，那么生成的结果真行数值矩阵空间对象中的第一层矩阵就是红色个数值对应的矩阵空间对象。
     *             <p>
     *             The list of color channel numbers to be extracted, for example, if the first one is a red channel, the first layer of the matrix in the generated result true row value matrix space object is the matrix space object corresponding to the red values.
     * @return 指定颜色通道的颜色数值矩阵提取出来的
     */
    public final IntegerMatrixSpace toIntRGBSpace(int... mode) {
        IntegerMatrix[] res = new IntegerMatrix[mode.length];
        int index = -1;
        for (int rgb : mode) {
            res[++index] = this.getChannel(rgb);
        }
        return IntegerMatrixSpace.parse(res);
    }

    /**
     * 针对图像矩阵进行池化操作，在池化操作中支持手动指定池化逻辑实现，能够使得池化函数的灵活度大大升高。
     * <p>
     * Pooling operations for image matrices support manual specification of pooling logic implementation, which greatly increases the flexibility of pooling functions.
     *
     * @param width          池化时的子矩阵宽度。
     *                       <p>
     *                       The width of the sub-matrix during pooling.
     * @param height         池化时的子矩阵高度。
     *                       <p>
     *                       The height of the sub-matrix during pooling.
     * @param transformation 图像矩阵的池化逻辑实现.
     *                       <p>
     *                       Implementation of pooling logic for image matrices
     * @return 池化之后的图像矩阵对象。
     * <p>
     * The image matrix object after pooling.
     */
    public final ColorMatrix pooling(int width, int height, Transformation<ColorMatrix, Color> transformation) {
        int rowCount = this.getRowCount();
        int colCount = this.getColCount();
        Color[][] colors = new Color[rowCount / height][colCount / width];
        int y = 0, y1 = height, index1 = -1;
        do {
            int x = 0, x1 = width, index2 = -1;
            Color[] colors1 = colors[++index1];
            do {
                colors1[++index2] = transformation.function(this.extractImage(x, y, x1, y1));
                x = x1;
                x1 += width;
            } while (x1 < colCount);
            y = y1;
            y1 += height;
        } while (y1 < rowCount);
        return parse(colors);
    }

    /**
     * 替换图像矩阵颜色
     *
     * @param oldColor       旧的颜色
     * @param transformation 替换函数，会将需要被替换的颜色 的周围矩阵传递进来，您只需要在这里将新 color 返回出来即可!
     * @param subArea        子矩阵尺寸 假设是 n 则 子矩阵为 nxn
     * @param errorValue     颜色数值的误差值 如果颜色数值与 oldColor 的 rgb 值 差距小于这个误差值 则也会被替换 这样面对一些渐变类的替换操作也是可以得心应手的
     * @param start          开始进行替换的起始坐标点
     * @param isCopy         如果设置为true 则代表在一个新的矩阵对象中进行腐蚀的操作计算，如果设置为false 则代表在原矩阵的基础上进行腐蚀不会出现新矩阵对象。
     * @return 新的图像矩阵
     */
    public final ColorMatrix colorReplace(Color oldColor, Transformation<ColorMatrix, Color> transformation, int subArea, int errorValue, IntegerCoordinateTwo start, boolean isCopy) {
        ColorMatrix colorMatrix = isCopy ? this.clone(this, true) : this;
        final double rgbAvg = ASMath.avg(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue());
        int r = Math.max(subArea >> 1, 4);
        int x = 0, y = 0;
        for (Color[] colors : colorMatrix) {
            if (y < start.getY()) {
                y += 1;
                continue;
            }
            for (Color color : colors) {
                if (x < start.getX()) {
                    x += 1;
                    continue;
                }
                if (ASMath.absoluteValue(ASMath.avg(color.getRed(), color.getGreen(), color.getBlue()) - rgbAvg) < errorValue) {
                    colorMatrix.set(y, x, transformation.function(this.extractImage(x - r, y - r, x + r, y + r)));
                }
                x++;
            }
            y++;
            x = 0;
        }
        return colorMatrix;
    }

    /**
     * 替换图像矩阵颜色
     *
     * @param oldColor       旧的颜色
     * @param transformation 替换函数，会将需要被替换的颜色 的周围矩阵传递进来，您只需要在这里将新 图像矩阵返回出来 矩阵会自动的被合并到原图
     * @param subArea        子矩阵尺寸 假设是 n 则 子矩阵为 nxn
     * @param errorValue     颜色数值的误差值 如果颜色数值与 oldColor 的 rgb 值 差距小于这个误差值 则也会被替换 这样面对一些渐变类的替换操作也是可以得心应手的
     * @param start          开始进行替换的起始坐标点
     * @param isCopy         如果设置为true 则代表在一个新的矩阵对象中进行腐蚀的操作计算，如果设置为false 则代表在原矩阵的基础上进行腐蚀不会出现新矩阵对象。
     * @return 新的图像矩阵
     */
    public final ColorMatrix colorReplaceByBlock(Color oldColor, Transformation<ColorMatrix, ColorMatrix> transformation, int subArea, int errorValue, IntegerCoordinateTwo start, boolean isCopy) {
        ColorMatrix colorMatrix = isCopy ? this.clone(this, true) : this;
        final double rgbAvg = ASMath.avg(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue());
        int r = Math.max(subArea >> 1, 4);
        int x = 0, y = 0;
        for (Color[] colors : colorMatrix) {
            if (y < start.getY()) {
                y += 1;
                continue;
            }
            for (Color color : colors) {
                if (x < start.getX()) {
                    x += 1;
                    continue;
                }
                if (ASMath.absoluteValue(ASMath.avg(color.getRed(), color.getGreen(), color.getBlue()) - rgbAvg) < errorValue) {
                    final int x1 = x - r;
                    final int y1 = y - r;
                    colorMatrix.merge(transformation.function(this.extractImage(x1, y1, x + r, y + r)), Math.max(0, x1), Math.max(0, y1));
                }
                x++;
            }
            y++;
            x = 0;
        }
        return colorMatrix;
    }

    /**
     * 将图像矩阵展示出来，使得在矩阵的图像数据能够被展示出来。
     * <p>
     * Display the image matrix so that the image data in the matrix can be displayed.
     *
     * @param title 展示图像时，图像窗口对应的标题。
     *              <p>
     *              The title corresponding to the image window when displaying the image.
     */
    public final void show(String title) {
        show(title, this.getColCount(), this.getRowCount());
    }

    /**
     * 将图像保存到指定的数据输出路径，如果输出成功将不会发生异常！
     * <p>
     * Save the image to the specified data output path. If the output succeeds, no exception will occur!
     *
     * @param outPath 图像输出路径，需要是一个文件路径的字符串。
     *                <p>
     *                The image output path needs to be a string of file path.
     */
    public final void save(String outPath) {
        if (!ImageRenderingIntegrator.draw(outPath, this.toArrays(), this.getColCount(), this.getRowCount(), 1, false)) {
            throw new OperatorOperationException("图像在保存时发生了错误，可能是写文件错误。\nAn error occurred while saving the image. It may be a writing file error.\nERROR => " +
                    outPath);
        }
    }

    /**
     * 将矩阵使用指定分隔符保存到文件系统的指定路径的文件中。
     * <p>
     * Save the matrix to a file in the specified path of the file system using the specified separator.
     *
     * @param path 需要保存的目录路径。
     *             <p>
     *             Directory path to save.
     * @param sep  保存时使用的分隔符。
     */
    @Override
    public void save(String path, char sep) {
        save(new File(path), sep);
    }

    /**
     * 将矩阵使用指定分隔符保存到文件系统的指定路径的文件中。
     * <p>
     * Save the matrix to a file in the specified path of the file system using the specified separator.
     *
     * @param path 需要保存的目录路径。
     *             <p>
     *             Directory path to save.
     * @param sep  保存时使用的分隔符。
     */
    @Override
    public void save(File path, char sep) {
        ASIO.writer(path, getSAVE_TEXT(this, sep));
    }

    /**
     * 将对象交由第三方数据输出组件进行数据的输出。
     * <p>
     * Submit the object to a third-party data output component for data output.
     *
     * @param outputComponent 第三方数据输出设备对象实现。
     *                        <p>
     *                        Implementation of third-party data output device objects.
     */
    @Override
    public void save(OutputComponent outputComponent) {
        if (!outputComponent.isOpen()) {
            if (!outputComponent.open())
                throw new OperatorOperationException("您的数据输出组件打开失败。\nYour data output component failed to open.");
        }
        outputComponent.writeImage(this);
        ASIO.close(outputComponent);
    }

    /**
     * 将图像的 ASCII 图像输出到指定的目录中。
     *
     * @param path          需要保存的目录路径。
     *                      <p>
     *                      Directory path to save.
     * @param Mode          在进行通道色彩的获取的时候，需要指定规整时的颜色通道标准，在指定通道的基础上进行规整，该参数可以直接从 ColorMatrix 类中获取到。
     *                      <p>
     *                      When obtaining channel colors, it is necessary to specify the color channel standard for regularization, which is based on the specified channel. This parameter can be directly obtained from the ColorMatrix class.
     * @param colorBoundary 颜色边界阈值，当被判断的颜色数值达到了阈值，则判断当前坐标颜色为真，返回为假。
     *                      <p>
     *                      The color boundary threshold value. When the judged color value reaches the threshold value, the current coordinate color is judged to be true, and the return value is false.
     * @param imageAscii1   ASCII 符号 在输出的 ASCII 图像文件中，针对大于阈值的坐标，图像的构成字符对应的ASCII数值。
     *                      <p>
     *                      ASCII Symbol In the output ASCII image file, the ASCII value corresponding to the constituent characters of the image for coordinates greater than the threshold value.
     * @param imageAscii2   ASCII 符号 在输出的 ASCII 图像文件中，针对小于阈值的坐标，图像的构成字符对应的ASCII数值。
     *                      <p>
     *                      ASCII Symbol In the output ASCII image file, the ASCII value corresponding to the constituent characters of the image for coordinates that are less than the threshold value.
     */
    public void save(File path, byte Mode, int colorBoundary, char imageAscii1, char imageAscii2) {
        ASIO.writer(path, getSAVE_ASCII(this, Mode, colorBoundary, imageAscii1, imageAscii2));
    }

    /**
     * 将图像矩阵展示出来，使得在矩阵的图像数据能够被展示出来。
     * <p>
     * Display the image matrix so that the image data in the matrix can be displayed.
     *
     * @param title  展示图像时，图像窗口对应的标题。
     *               <p>
     *               The title corresponding to the image window when displaying the image.
     * @param width  展示图像时，图像窗口的宽度。
     *               <p>
     *               The width of the image window when displaying the image.
     * @param height 展示图象时，图像窗口的高度。
     *               <p>
     *               The height of the image window when displaying the image.
     */
    public final void show(String title, int width, int height) {
        int colCount = this.getColCount();
        int rowCount = this.getRowCount();
        boolean needCorrection = colCount != width || rowCount != height;
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(width, height);
        jFrame.setVisible(true);
        jFrame.setTitle(title);
        jFrame.setBackground(WHITE);
        jFrame.setResizable(true);
        ImageIcon imageIcon = new ImageIcon(jFrame.createVolatileImage(colCount, rowCount));
        Image image = imageIcon.getImage();
        drawToImage(image);
        JLabel label = new JLabel(imageIcon);//往一个标签中加入图片
        label.setVisible(true);
        label.setBounds(0, 0, jFrame.getWidth(), jFrame.getHeight());//设置标签位置大小，记得大小要和窗口一样大
        if (needCorrection) {
            imageIcon.setImage(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));//图片自适应窗口大小
        }
        jFrame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板
        jFrame.add(label);
    }

    /**
     * 将当前图像矩阵中的图像绘制到 image 对象中。
     * <p>
     * Draw the image from the current image matrix into the image object.
     *
     * @param image 需要被绘制的图像对象。
     *              <p>
     *              The image object that needs to be drawn.
     */
    public void drawToImage(Image image) {
        Graphics graphics = image.getGraphics();
        // 开始绘制图形
        int yc = -1;
        for (Color[] colors : this.toArrays()) {
            ++yc;
            int xc = -1;
            for (Color color : colors) {
                graphics.setColor(color);
                graphics.fillRect(++xc, yc, 1, 1);
            }
        }
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
