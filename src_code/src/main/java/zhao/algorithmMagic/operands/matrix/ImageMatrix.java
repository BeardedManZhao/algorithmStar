package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASIO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 针对 ColorMatrix 矩阵对象的封装类，其是Color矩阵的子类，具有更加强大的性能。
 * <p>
 * Encapsulation class for ColorMatrix matrix objects, which is a subclass of the Color matrix and has more powerful performance.
 *
 * @author 赵凌宇
 * 2023/6/12 12:14
 */
public class ImageMatrix extends ColorMatrix implements ImageObserver {
    private final BufferedImage image;

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
     */
    protected ImageMatrix(int rowCount, int colCount, Color[][] colors, boolean isGrayscale) {
        super(rowCount, colCount, colors, isGrayscale);
        BufferedImage bufferedImage = new BufferedImage(
                colCount, rowCount, isGrayscale ? BufferedImage.TYPE_USHORT_GRAY : BufferedImage.TYPE_INT_RGB
        );
        super.drawToImage(bufferedImage);
        this.image = bufferedImage;
    }

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
     *                    <p>
     *                    The two-dimensional array object in this matrix object.
     * @param image       当前矩阵对象中需要存储的 image 对象。
     *                    <p>
     *                    The image object that needs to be stored in the current matrix object.
     * @param isGrayscale 如果设置为true 代表此图像是灰度图像。
     *                    <p>
     *                    If set to true, this image is Grayscale.
     */
    protected ImageMatrix(int rowCount, int colCount, Color[][] colors, BufferedImage image, boolean isGrayscale) {
        super(rowCount, colCount, colors, isGrayscale);
        this.image = image;
    }

    /**
     * 以 灰度图的方式创建出一个灰度图像矩阵对象。
     *
     * @param image 需要被创建的图像对象。
     * @return 创建出来的图像矩阵对象（灰度）。
     */
    public static ImageMatrix parseGrayscale(BufferedImage image) {
        Color[][] colors = ASIO.parseImageGetColorArray(image);
        ColorMatrix colorMatrix = ColorMatrix.GrayscaleColors(colors);
        return new ImageMatrix(image.getWidth(), image.getHeight(), colorMatrix.toArrays(), true);
    }

    /**
     * 、以 彩色图的方式创建出一个图像矩阵对象。
     *
     * @param image 需要被创建的图像对象。
     * @return 创建出来的图像矩阵对象（彩色）
     */
    public static ImageMatrix parse(BufferedImage image) {
        return new ImageMatrix(
                image.getWidth(), image.getHeight(),
                ASIO.parseImageGetColorArray(image), image, false
        );
    }

    /**
     * 以 灰度图的方式创建出一个灰度图像矩阵对象。
     *
     * @param colors 需要被创建的图像对象对应的像素数组。
     *               <p>
     *               The pixel array corresponding to the image object that needs to be created.
     * @return 创建出来的图像矩阵对象（灰度）。
     */
    public static ImageMatrix parseGrayscale(Color[][] colors) {
        if (colors.length > 0) {
            ColorMatrix colorMatrix = ColorMatrix.GrayscaleColors(colors);
            return new ImageMatrix(colorMatrix.getColCount(), colorMatrix.getRowCount(), colorMatrix.toArrays(), true);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    /**
     * 以 彩色图的方式创建出一个图像矩阵对象。
     *
     * @param colors 需要被创建的图像对象对应的像素数组。
     *               <p>
     *               The pixel array corresponding to the image object that needs to be created.
     * @return 创建出来的图像矩阵对象（彩色）
     */
    public static ImageMatrix parse(Color[][] colors) {
        if (colors.length > 0) {
            return new ImageMatrix(colors.length, colors[0].length, colors, false);
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
    public static ImageMatrix parse(String inputString, int... v) {
        if (v.length != 0) {
            return parse(ColorMatrix.parse(inputString, v).toArrays());
        }
        try {
            BufferedImage read = ImageIO.read(new File(inputString));
            return parse(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public static ImageMatrix parseGrayscale(String inputString) {
        return parse(ColorMatrix.parseGrayscale(inputString).toArrays());
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
    public static ImageMatrix parse(URL url, int... v) {
        if (v.length != 0) {
            return parse(ColorMatrix.parse(url, v).toArrays());
        }
        try {
            BufferedImage read = ImageIO.read(url);
            return parse(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将图像URL解析，并获取对应的图像矩阵
     *
     * @param url 需要被解析的URL对象
     * @return URL对象所对应的图像矩阵。
     */
    public static ImageMatrix parseGrayscale(URL url) {
        return parse(ColorMatrix.parseGrayscale(url).toArrays());
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
     */
    @Override
    public void set(int row, int col, Color color) {
        super.set(row, col, color);
        this.image.setRGB(row, col, color.getRGB());
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public ImageMatrix expand() {
        return this;
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
        return parse(this.image.getSubimage(x1, y1, x2, y2));
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
        return parse(super.extractImageSrc(y1, y2).toArrays());
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
    @Override
    public void drawToImage(Image image) {
        Graphics graphics = image.getGraphics();
        graphics.drawImage(this.image, 0, 0, this);
    }

    /**
     * 使用克隆的方式创建出一个新的矩阵对象。
     *
     * @param colorMatrix 需要被克隆的原矩阵对象。
     * @param isCopy      克隆操作中的元素是否使用深拷贝
     * @return 克隆操作成功之后的新矩阵对象。
     */
    @Override
    public ImageMatrix clone(ColorMatrix colorMatrix, boolean isCopy) {
        return new ImageMatrix(
                colorMatrix.getRowCount(), colorMatrix.getColCount(),
                isCopy ? colorMatrix.copyToNewArrays() : colorMatrix.toArrays(),
                isCopy ? (BufferedImage) ((ImageMatrix) colorMatrix).toImage() : ((ImageMatrix) colorMatrix).copyToNewImage(),
                colorMatrix.isGrayscale()
        );
    }

    /**
     * 将当前图像的尺寸进行缩放，使得图像能够在不改变自身样貌的前提下将尺寸更改。
     * <p>
     * Scales the size of the current image so that the image can be resized without changing its appearance.
     *
     * @param newWidth 缩放之后的矩阵尺寸数值 - 宽。
     *                 <p>
     *                 The matrix size value after scaling - width.
     * @param newHigh  缩放之后的矩阵尺寸数值 - 高。
     *                 <p>
     *                 The matrix size value after scaling - high.
     * @return 缩放之后的新的图像矩阵对象。
     * <p>
     * The new image matrix object after scaling.
     */
    public ImageMatrix reSize(int newWidth, int newHigh) {
        Image scaledInstance = this.image.getScaledInstance(newWidth, newHigh, Image.SCALE_FAST);
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHigh, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(scaledInstance, 0, 0, this);
        return parse(bufferedImage);
    }

    /**
     * 将当前的图像矩阵对象转换成为 Image 对象。
     * <p>
     * Convert the current image matrix object into an Image object.
     *
     * @return 当前矩阵对象中存储的 Java Image 对象，需要注意的是，此返回的数值不支持修改操作。
     * <p>
     * The Java Image object stored in the front matrix object should be noted that this object does not support modification operations.
     */
    public final Image toImage() {
        return this.image;
    }

    /**
     * 以拷贝的方式获取到本矩阵中的 Image 对象，并将次对象返回出去。
     * <p>
     * Obtain the Image object in this matrix by copying and return the secondary object.
     *
     * @return 拷贝方式获取到的 Image 对象，该对象支持修改，其与源矩阵无任何关联。
     * <p>
     * The Image object obtained by copying method supports modification and has no association with the source matrix.
     */
    public final BufferedImage copyToNewImage() {
        BufferedImage bufferedImage = new BufferedImage(this.getColCount(), this.getRowCount(), BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().drawImage(this.image, 0, 0, this);
        return bufferedImage;
    }

    /**
     * This method is called when information about an image which was
     * previously requested using an asynchronous interface becomes
     * available.  Asynchronous interfaces are method calls such as
     * getWidth(ImageObserver) and drawImage(img, x, y, ImageObserver)
     * which take an ImageObserver object as an argument.  Those methods
     * register the caller as interested either in information about
     * the overall image itself (in the case of getWidth(ImageObserver))
     * or about an output version of an image (in the case of the
     * drawImage(img, x, y, [w, h,] ImageObserver) call).
     *
     * <p>This method
     * should return true if further updates are needed or false if the
     * required information has been acquired.  The image which was being
     * tracked is passed in using the img argument.  Various constants
     * are combined to form the infoflags argument which indicates what
     * information about the image is now available.  The interpretation
     * of the x, y, width, and height arguments depends on the contents
     * of the infoflags argument.
     * <p>
     * The <code>infoflags</code> argument should be the bitwise inclusive
     * <b>OR</b> of the following flags: <code>WIDTH</code>,
     * <code>HEIGHT</code>, <code>PROPERTIES</code>, <code>SOMEBITS</code>,
     * <code>FRAMEBITS</code>, <code>ALLBITS</code>, <code>ERROR</code>,
     * <code>ABORT</code>.
     *
     * @param img       the image being observed.
     * @param infoflags the bitwise inclusive OR of the following
     *                  flags:  <code>WIDTH</code>, <code>HEIGHT</code>,
     *                  <code>PROPERTIES</code>, <code>SOMEBITS</code>,
     *                  <code>FRAMEBITS</code>, <code>ALLBITS</code>,
     *                  <code>ERROR</code>, <code>ABORT</code>.
     * @param x         the <i>x</i> coordinate.
     * @param y         the <i>y</i> coordinate.
     * @param width     the width.
     * @param height    the height.
     * @return <code>false</code> if the infoflags indicate that the
     * image is completely loaded; <code>true</code> otherwise.
     * @see #WIDTH
     * @see #HEIGHT
     * @see #PROPERTIES
     * @see #SOMEBITS
     * @see #FRAMEBITS
     * @see #ALLBITS
     * @see #ERROR
     * @see #ABORT
     * @see Image#getWidth
     * @see Image#getHeight
     * @see Graphics#drawImage
     */
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}
