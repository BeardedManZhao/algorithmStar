package zhao.algorithmMagic.integrator;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.integrator.launcher.ImageRenderingLauncher;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASIO;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 图像绘制集成器，专用于将矩阵转换成为图像数据，并将图像输出到目标文件中的集成器。
 * <p>
 * The image rendering integrator is designed to convert the matrix into image data and output the image to the target file.
 *
 * @author 赵凌宇
 * 2023/2/9 11:59
 */
public final class ImageRenderingIntegrator implements AlgorithmIntegrator<ImageRenderingIntegrator> {

    private final String IntegratorName;
    private final ImageRenderingLauncher<?> imageRenderingLauncher;

    /**
     * 将启动器实现类直接配置到集成器中
     *
     * @param integratorName         该集成器的名称
     * @param imageRenderingLauncher 图像绘制集成器所需要的启动器实现类
     */
    public ImageRenderingIntegrator(String integratorName, ImageRenderingLauncher<?> imageRenderingLauncher) {
        IntegratorName = integratorName;
        this.imageRenderingLauncher = imageRenderingLauncher;
    }

    /**
     * 将一张矩阵对象中的每个元素作为图像中的演示数值。并绘制出图像。
     *
     * @param outFilePath        图像绘制的输出路径
     * @param ints               需要被用来作为图像输出的像素矩阵对象
     * @param imageWidth         需要绘制图像的宽度
     * @param imageHeight        需要绘制图像的高度
     * @param pixelMagnification 矩阵中一个元素代表的像素块倍率，也是一个像素块大小
     * @param writeNumber        如果设置为true，代表输出图片的时候将颜色编号打上去
     * @return 如果返回true 代表绘制成功！
     */
    public static boolean draw(String outFilePath, int[][] ints, int imageWidth, int imageHeight, int pixelMagnification, boolean writeNumber) {
        // 创建一个图片
        int width = imageWidth * pixelMagnification;
        int height = imageHeight * pixelMagnification;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        // 准备坐标
        if (writeNumber) {
            int x1 = 0, y1 = 0;
            for (int y = 0; y1 < imageHeight && y < height; ) {
                int nextY = y + pixelMagnification;
                for (int x = 0; x1 < imageWidth && x < width; x += pixelMagnification) {
                    int colorNum = ints[y1][x1++];
                    graphics2D.setColor(new Color(colorNum));
                    graphics2D.fillRect(x, y, pixelMagnification, pixelMagnification);
                    graphics2D.setColor(new Color(~(colorNum) & 0xfffffff));
                    graphics2D.drawString(x1 + "," + y1 + " = " + colorNum, x, nextY);
                }
                y = nextY;
                ++y1;
                x1 = 0;
            }
        } else {
            int x1 = 0, y1 = 0;
            for (int y = 0; y1 < imageHeight && y < height; y += pixelMagnification) {
                for (int x = 0; x1 < imageWidth && x < width; x += pixelMagnification) {
                    graphics2D.setColor(new Color(ints[y1][x1++]));
                    graphics2D.fillRect(x, y, pixelMagnification, pixelMagnification);
                }
                ++y1;
                x1 = 0;
            }
        }
        // 输出图片
        return ASIO.outImage(image, outFilePath);
    }

    /**
     * 将一张矩阵对象中的每个元素作为图像中的演示数值。并绘制出图像。
     *
     * @param outFilePath        图像绘制的输出路径
     * @param colors             需要被用来作为图像输出的像素矩阵对象
     * @param imageWidth         需要绘制图像的宽度
     * @param imageHeight        需要绘制图像的高度
     * @param pixelMagnification 矩阵中一个元素代表的像素块倍率，也是一个像素块大小
     * @param writeNumber        如果设置为true，代表输出图片的时候将颜色编号打上去
     * @return 如果返回true 代表绘制成功！
     */
    public static boolean draw(String outFilePath, Color[][] colors, int imageWidth, int imageHeight, int pixelMagnification, boolean writeNumber) {
        BufferedImage image = drawBufferedImage(colors, imageWidth, imageHeight, pixelMagnification, writeNumber);
        // 输出图片
        return ASIO.outImage(image, outFilePath);
    }

    /**
     * @param colors             需要被用来作为图像输出的像素矩阵对象
     * @param imageWidth         需要绘制图像的宽度
     * @param imageHeight        需要绘制图像的高度
     * @param pixelMagnification 矩阵中一个元素代表的像素块倍率，也是一个像素块大小
     * @param writeNumber        如果设置为true，代表输出图片的时候将颜色编号打上去
     * @return 如果返回true 代表绘制成功！
     */
    public static BufferedImage drawBufferedImage(Color[][] colors, int imageWidth, int imageHeight, int pixelMagnification, boolean writeNumber) {
        // 创建一个图片
        int width = imageWidth * pixelMagnification;
        int height = imageHeight * pixelMagnification;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        // 准备坐标
        if (writeNumber) {
            int x1 = 0, y1 = 0;
            for (int y = 0; y1 < imageHeight && y < height; ) {
                int nextY = y + pixelMagnification;
                for (int x = 0; x1 < imageWidth && x < width; x += pixelMagnification) {
                    Color color = colors[y1][x1++];
                    graphics2D.setColor(color);
                    graphics2D.fillRect(x, y, pixelMagnification, pixelMagnification);
                    graphics2D.setColor(new Color(~(color.getRGB()) & 0xfffffff));
                    graphics2D.drawString(x1 + "," + y1 + " = " + color, x, nextY);
                }
                y = nextY;
                ++y1;
                x1 = 0;
            }
        } else {
            int x1 = 0, y1 = 0;
            for (int y = 0; y1 < imageHeight && y < height; y += pixelMagnification) {
                for (int x = 0; x1 < imageWidth && x < width; x += pixelMagnification) {
                    graphics2D.setColor(colors[y1][x1++]);
                    graphics2D.fillRect(x, y, pixelMagnification, pixelMagnification);
                }
                ++y1;
                x1 = 0;
            }
        }
        return image;
    }

    @Override
    public ImageRenderingIntegrator expand() {
        return this;
    }

    @Override
    public String getIntegratorName() {
        return this.IntegratorName;
    }

    @Override
    public boolean run() {
        Object o = this.imageRenderingLauncher.returnMatrix();
        if (o instanceof ColorMatrix) {
            return draw(
                    this.imageRenderingLauncher.returnOutPath(), ((ColorMatrix) o).toArrays(),
                    this.imageRenderingLauncher.returnImageWeight(), this.imageRenderingLauncher.returnImageHeight(),
                    this.imageRenderingLauncher.pixelMagnification(), this.imageRenderingLauncher.isWriteNumber()
            );
        } else if (o instanceof IntegerMatrix) {
            return draw(
                    this.imageRenderingLauncher.returnOutPath(), ((IntegerMatrix) o).toArrays(),
                    this.imageRenderingLauncher.returnImageWeight(), this.imageRenderingLauncher.returnImageHeight(),
                    this.imageRenderingLauncher.pixelMagnification(), this.imageRenderingLauncher.isWriteNumber()
            );
        } else {
            throw new OperatorOperationException("不支持您传递的矩阵对象的图像绘制，请检查其是否为颜色矩阵或整形矩阵对象！");
        }
    }

    @Override
    public double runAndReturnValue() {
        return run() ? 1.0 : 0.0;
    }
}
