package zhao.algorithmMagic.lntegrator.launcher;

/**
 * 图像绘制集成器对应的图像绘制启动器，您需要在其中指定图像输出目录与图像矩阵。
 * <p>
 * The image drawing launcher corresponding to the image drawing integrator, where you need to specify the image output directory and image matrix.
 *
 * @param <T> 需要提供给集成器的矩阵对象数据类型。
 *            2023/2/9 12:01
 * @author 赵凌宇
 */
public interface ImageRenderingLauncher<T> extends Launcher<ImageRenderingLauncher<T>> {

    /**
     * 将图像矩阵提供给集成器去使用。
     *
     * @return 一个代表图像中像素数值的矩阵对象，其中的每一个元素应代表一个像素颜色数值！
     * <p>
     * A matrix object representing the pixel value in the image. Each element in the matrix should represent a pixel color value!
     */
    T returnMatrix();

    /**
     * 获取到图片的宽度
     * <p>
     * Get the width of the picture
     *
     * @return 在这里的返回值会被集成器获取到并作为图片的宽度。
     * <p>
     * The return value here will be obtained by the integrator and used as the width of the image.
     */
    int returnImageWeight();

    /**
     * 获取到图片的高度
     * <p>
     * Get the height of the picture
     *
     * @return 在这里的返回值会被集成器获取到并作为图片的高度
     * <p>
     * The return value here will be obtained by the integrator and used as the height of the image.
     */
    int returnImageHeight();

    /**
     * 获取到本次绘制图像中的像素倍率，倍率越大，矩阵中的一个元素所代表的像素范围旧越大。
     * <p>
     * Obtain the pixel magnification in the current drawn image. The larger the magnification, the larger the range of pixels represented by an element in the matrix.
     *
     * @return 在这里的返回值会被集成器获取到并作为图片的像素倍率.
     * <p>
     * The return value here will be obtained by the integrator and used as the pixel magnification of the image.
     */
    int pixelMagnification();

    /**
     * 获取到绘制时的编号模式，在绘制的时候可以为每一个矩阵对应元素的负责范围的位置打上一个编号。
     * <p>
     * Get the numbering mode when drawing. When drawing, you can mark a number for the position of the responsible range of the corresponding element of each matrix.
     *
     * @return 如果设置为true 代表需要进行编号的标记，在输出的图片上会有编号
     * <p>
     * If it is set to true, it means that the mark to be numbered will be numbered on the output picture
     */
    boolean isWriteNumber();

    /**
     * 获取到图像的输出路径。
     * <p>
     * Gets the output path to the image.
     *
     * @return 在这里返回的字符串会被集成器获取到并作为图片的输出路径使用。
     * <p>
     * The string returned here will be obtained by the integrator and used as the output path of the image.
     */
    String returnOutPath();
}
