package io.github.beardedManZhao.algorithmStar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Java类于 2022/10/15 14:43:22 创建
 * <p>
 * IO工具包
 *
 * @author zhao
 */
public final class ASIO {

    private final static Logger LOGGER = LoggerFactory.getLogger("ASIO");

    /**
     * 将一个图片输出到指定的路径
     *
     * @param image   被输出的图片
     * @param outPath 数据输出路径,精确到图片文件
     * @return 结果!
     */
    public static boolean outImage(BufferedImage image, String outPath) {
        try {
            ImageIO.write(image, outPath.substring(outPath.lastIndexOf(".") + 1).toUpperCase(), new File(outPath));
            return true;
        } catch (IOException e) {
            LOGGER.error("ERROR", e);
            return false;
        }
    }

    /**
     * 写文本数据的工具函数，该函数将有效的管理数据流。
     *
     * @param path      文本数据输出路径
     * @param voidTrans 文本数据输出使用输出逻辑
     */
    public static void writer(File path, Consumer<BufferedWriter> voidTrans) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));
            voidTrans.accept(bufferedWriter);
        } catch (IOException e) {
            throw new OperatorOperationException("The target file may be a directory, or the data stream cannot be opened because it does not have write permission.", e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写数据的工具函数，该函数将有效的管理数据流。
     *
     * @param outputStream 文本数据输出流
     * @param voidTrans    文本数据输出使用输出流
     */
    public static void writer(OutputStream outputStream, Consumer<BufferedWriter> voidTrans) {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        voidTrans.accept(bufferedWriter);
        ASIO.close(bufferedWriter);
    }

    /**
     * 将一个byte数组输出到指定的路径
     *
     * @param path                  数据输出路径
     * @param byteArrayOutputStream 包含需要输出的数据流,注意,不会被自动关闭!!!!
     * @return 输出是否成功!
     */
    public static boolean outByteArray(String path, ByteArrayOutputStream byteArrayOutputStream) {
        return outByteArray(path, byteArrayOutputStream.toByteArray());
    }

    /**
     * 将一个byte数组输出到指定的路径
     *
     * @param path 数据输出路径
     * @param d    byte数组
     * @return 输出是否成功!
     */
    public static boolean outByteArray(String path, byte[] d) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            bufferedOutputStream.write(d);
            return true;
        } catch (IOException e) {
            LOGGER.error("ERROR", e);
            return false;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    LOGGER.error("ERROR", e);
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("ERROR", e);
                }
            }
        }
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputFile 需要被读取的图像文件路径
     * @param v         读取图像文件，需要被规整为的尺寸，通过这个参数的设置，可以使用指定的尺寸读图像文件。
     * @return 读取成功之后返回的整形矩阵
     */
    public static int[][] parseImageGetArray(String inputFile, int... v) {
        try {
            if (v.length == 2) {
                return parseImageGetArray(new File(inputFile), v[0], v[1]);
            } else {
                return parseImageGetArray(new File(inputFile));
            }
        } catch (IOException e) {
            throw new OperatorOperationException("尝试获取您所给定路径图像的像素矩阵时发生了错误\nAn error occurred while trying to get the pixel matrix of the path image you gave\nERROR => " +
                    inputFile
            );
        }
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputString 需要被读取的图像文件路径
     * @param v           矩阵中的所有图像的尺寸参数。
     *                    <p>
     *                    The size parameters of all images in the matrix.
     * @return 读取成功之后返回的整形矩阵
     */
    public static Color[][] parseImageGetColorArray(String inputString, int... v) {
        return parseImageGetColorArray(new File(inputString), v);
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputString 需要被读取的图像文件路径
     * @param v           矩阵中的所有图像的尺寸参数。
     *                    <p>
     *                    The size parameters of all images in the matrix.
     * @return 读取成功之后返回的整形矩阵
     */
    public static Color[][] parseImageGetColorArray(File inputString, int... v) {
        try {
            if (v.length == 2) {
                return parseImageGetColorArray(inputString, v[0], v[1]);
            } else {
                return parseImageGetColorArray(inputString);
            }
        } catch (IOException e) {
            throw new OperatorOperationException("尝试获取您所给定路径图像的像素矩阵时发生了错误\nAn error occurred while trying to get the pixel matrix of the path image you gave\nERROR => " +
                    inputString, e
            );
        }
    }

    /**
     * 通过一个URL获取到图像数据，并将其解析成为矩阵对象。
     *
     * @param imageUrl 需要被获取的图像对象，请注意，确保此URL是一个图像文件的URL
     * @param v        额外可选参数，其中代表读取进来之后进行变换的宽高。
     * @return 解析之后的结果数据。
     */
    public static Color[][] parseURLGetColorArray(URL imageUrl, int... v) {
        try {
            InputStream inputStream = imageUrl.openStream();
            Color[][] colors;
            if (v.length == 2) {
                colors = parseImageGetColorArray(ImageIO.read(inputStream), v[0], v[1]);
            } else {
                colors = parseImageGetColorArray(ImageIO.read(inputStream));
            }
            inputStream.close();
            return colors;
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将一个图像矩阵解析成为矩阵空间对象。
     *
     * @param inputString 需要被解析的文件对象。
     * @param v           额外可选参数，其中代表读取进来之后进行变换的宽高。
     * @return 经过计算之后的图像矩阵对象。
     */
    public static IntegerMatrix[] parseImageGetArrays(String inputString, int... v) {
        try {
            if (v.length == 2) {
                return parseImageGetArrays(ImageIO.read(new File(inputString)), v[0], v[1]);
            } else {
                return parseImageGetArrays(ImageIO.read(new File(inputString)));
            }
        } catch (IOException e) {
            throw new OperatorOperationException("尝试获取您所给定路径图像的像素矩阵时发生了错误\nAn error occurred while trying to get the pixel matrix of the path image you gave\nERROR => " +
                    inputString
            );
        }
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputFile 需要被读取的图像文件路径
     * @return 读取成功之后返回的整形矩阵
     * @throws IOException 解析图像矩阵发生错误的时候抛出该异常
     */
    public static Color[][] parseImageGetColorArray(File inputFile) throws IOException {
        return parseImageGetColorArray(ImageIO.read(inputFile));
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputFile 需要被读取的图像文件路径
     * @param width     读取进来之后采用的宽度，将会按照此宽度进行图像变换操作。
     *                  <p>
     *                  The width used after reading will be used for image transformation operations based on this width.
     * @param height    读取进来之后采用的高度，将会按照此高度进行图像变换操纵。
     *                  <p>
     *                  The height used after reading will be used for image transformation and manipulation according to this height.
     * @return 读取成功之后返回的整形矩阵
     * @throws IOException 解析图像矩阵发生错误的时候抛出该异常
     */
    public static Color[][] parseImageGetColorArray(File inputFile, int width, int height) throws IOException {
        return parseImageGetColorArray(ImageIO.read(inputFile), width, height);
    }


    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param image  需要被读取的图像缓冲对象
     * @param width  读取进来之后采用的宽度，将会按照此宽度进行图像变换操作。
     *               <p>
     *               The width used after reading will be used for image transformation operations based on this width.
     * @param height 读取进来之后采用的高度，将会按照此高度进行图像变换操纵。
     *               <p>
     *               The height used after reading will be used for image transformation and manipulation according to this height.
     * @return 读取成功之后返回的整形矩阵
     */
    public static Color[][] parseImageGetColorArray(BufferedImage image, int width, int height) {
        return parseImageGetColorArray(ASClass.ImageTOBuffer(
                image.getScaledInstance(width, height, Image.SCALE_FAST),
                width, height
        ));
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param image 需要被读取的图像缓冲对象
     * @return 读取成功之后返回的整形矩阵
     */
    public static Color[][] parseImageGetColorArray(BufferedImage image) {
        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            final byte[] pixels = ((DataBufferByte) dataBuffer)
                    .getData();
            if (pixels.length == 0) {
                throw new OperatorOperationException("您不能读取一个不包含任何像素的图像，请您重新切换被读取的图像文件!!!\nYou cannot read an image that does not contain any pixels. Please switch the read image file again!!!");
            }
            final int width = image.getWidth(), height = image.getHeight();
            final boolean hasAlphaChannel = image.getAlphaRaster() != null;
            Color[][] result = new Color[height][width];
            if (hasAlphaChannel) {
                final int pixelLength = 4;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    result[row][col] = new Color(
                            ((int) pixels[pixel + 3] & 0xff), // red
                            ((int) pixels[pixel + 2] & 0xff), // green
                            (int) pixels[pixel + 1] & 0xff, // blue
                            ((int) pixels[pixel] & 0xff)// alpha
                    );
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            } else {
                final int pixelLength = 3;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    result[row][col] = new Color(
                            ((int) pixels[pixel + 2] & 0xff), // red
                            ((int) pixels[pixel + 1] & 0xff), // green
                            (int) pixels[pixel] & 0xff, // blue
                            0xff // alpha
                    );
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            }
            return result;
        } else {
            final int[] pixels = ((DataBufferInt) dataBuffer).getData();
            if (pixels.length == 0) {
                throw new OperatorOperationException("您不能读取一个不包含任何像素的图像，请您重新切换被读取的图像文件!!!\nYou cannot read an image that does not contain any pixels. Please switch the read image file again!!!");
            }
            final int width = image.getWidth(), height = image.getHeight();
            Color[][] result = new Color[height][width];
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel++) {
                int pixel1 = pixels[pixel];
                result[row][col] = new Color(
                        (pixel1 >> ColorMatrix._B_) & 0xFF,
                        (pixel1 >> ColorMatrix._G_) & 0xFF,
                        (pixel1 >> ColorMatrix._R_) & 0xFF
                );
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
            return result;
        }
    }


    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputFile 需要被读取的图像文件路径
     * @return 读取成功之后返回的整形矩阵
     * @throws IOException 解析图像矩阵发生错误的时候抛出该异常
     */
    public static int[][] parseImageGetArray(File inputFile) throws IOException {
        return parseImageGetArray(ImageIO.read(inputFile));
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形二维矩阵获取到！
     *
     * @param inputFile 需要被读取的图像文件路径
     * @param width     读取进来之后采用的宽度，将会按照此宽度进行图像变换操作。
     *                  <p>
     *                  The width used after reading will be used for image transformation operations based on this width.
     * @param height    读取进来之后采用的高度，将会按照此高度进行图像变换操纵。
     *                  <p>
     *                  The height used after reading will be used for image transformation and manipulation according to this height.
     * @return 读取成功之后返回的整形矩阵
     * @throws IOException 解析图像矩阵发生错误的时候抛出该异常
     */
    public static int[][] parseImageGetArray(File inputFile, int width, int height) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        return parseImageGetArray(image, width, height);
    }

    public static int[][] parseImageGetArray(BufferedImage image) {
        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            final byte[] pixels = ((DataBufferByte) dataBuffer).getData();
            final int width = image.getWidth(), height = image.getHeight();
            final boolean hasAlphaChannel = image.getAlphaRaster() != null;
            int[][] result = new int[height][width];
            if (hasAlphaChannel) {
                final int pixelLength = 4;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    int argb = (((int) pixels[pixel] & 0xff) << 24);
                    argb += ((int) pixels[pixel + 1] & 0xff); // blue
                    argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                    argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                    result[row][col] = argb;
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            } else {
                final int pixelLength = 3;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    int argb = 0;
                    argb -= 16777216; // 255 alpha
                    argb += ((int) pixels[pixel] & 0xff); // blue
                    argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                    argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                    result[row][col] = argb;
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            }
            return result;
        } else {
            final int[] pixels = ((DataBufferInt) dataBuffer).getData();
            if (pixels.length == 0) {
                throw new OperatorOperationException("您不能读取一个不包含任何像素的图像，请您重新切换被读取的图像文件!!!\nYou cannot read an image that does not contain any pixels. Please switch the read image file again!!!");
            }
            final int width = image.getWidth(), height = image.getHeight();
            int[][] result = new int[height][width];
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel++) {
                int pixel1 = pixels[pixel];
                result[row][col] = ASMath.rgbaTOIntRGBA(
                        (pixel1 >> ColorMatrix._B_) & 0xFF,
                        (pixel1 >> ColorMatrix._G_) & 0xFF,
                        (pixel1 >> ColorMatrix._R_) & 0xFF
                );
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
            return result;
        }
    }

    /**
     * 指定长宽的方式解析像
     *
     * @param image  需要被解析的图像对象。
     *               <p>
     *               The image object that needs to be parsed.
     * @param width  读取进来之后采用的宽度，将会按照此宽度进行图像变换操作。
     *               <p>
     *               The width used after reading will be used for image transformation operations based on this width.
     * @param height 读取进来之后采用的高度，将会按照此高度进行图像变换操纵。
     *               <p>
     *               The height used after reading will be used for image transformation and manipulation according to this height.
     * @return 解析出来的结果图像对应的 RGB 矩阵。
     */
    public static int[][] parseImageGetArray(BufferedImage image, int width, int height) {
        return parseImageGetArray(ASClass.ImageTOBuffer(
                image.getScaledInstance(width, height, Image.SCALE_FAST),
                width, height
        ));
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形三维矩阵空间获取到！
     *
     * @param image  需要被读取的图像文件路径
     * @param width  获取图像的尺寸-宽
     * @param height 获取图像的尺寸-高
     * @return 读取成功之后返回的整形矩阵空间，其中每一层代表图形的一层颜色通道。
     */
    public static IntegerMatrix[] parseImageGetArrays(BufferedImage image, int width, int height) {
        return parseImageGetArrays(ASClass.ImageTOBuffer(
                image.getScaledInstance(width, height, Image.SCALE_FAST),
                width, height
        ));
    }

    /**
     * 将一个图片中的所有像素RGB值所构成的整形三维矩阵空间获取到！
     *
     * @param image 需要被读取的图像文件路径
     * @return 读取成功之后返回的整形矩阵空间，其中每一层代表图形的一层颜色通道。
     */
    public static IntegerMatrix[] parseImageGetArrays(BufferedImage image) {
        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            final byte[] pixels = ((DataBufferByte) dataBuffer).getData();
            final int width = image.getWidth(), height = image.getHeight();
            int[][] RMat = new int[height][width];
            int[][] GMat = new int[height][width];
            int[][] BMat = new int[height][width];
            final boolean hasAlphaChannel = image.getAlphaRaster() != null;
            if (hasAlphaChannel) {
                final int pixelLength = 4;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    RMat[row][col] = ((int) pixels[pixel + 3] & 0xff);
                    GMat[row][col] = ((int) pixels[pixel + 2] & 0xff);
                    BMat[row][col] = (int) pixels[pixel + 1] & 0xff;
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            } else {
                final int pixelLength = 3;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                    RMat[row][col] = ((int) pixels[pixel + 2] & 0xff);
                    GMat[row][col] = ((int) pixels[pixel + 1] & 0xff);
                    BMat[row][col] = (int) pixels[pixel] & 0xff;
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }
            }
            return new IntegerMatrix[]{
                    IntegerMatrix.parse(RMat), IntegerMatrix.parse(GMat), IntegerMatrix.parse(BMat)
            };
        } else {
            final int[] pixels = ((DataBufferInt) dataBuffer).getData();
            if (pixels.length == 0) {
                throw new OperatorOperationException("您不能读取一个不包含任何像素的图像，请您重新切换被读取的图像文件!!!\nYou cannot read an image that does not contain any pixels. Please switch the read image file again!!!");
            }
            final int width = image.getWidth(), height = image.getHeight();
            int[][] R = new int[height][width], G = new int[height][width], B = new int[height][width];
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel++) {
                int pixel1 = pixels[pixel];
                R[row][col] = (pixel1 >> ColorMatrix._B_) & 0xFF;
                G[row][col] = (pixel1 >> ColorMatrix._G_) & 0xFF;
                B[row][col] = (pixel1 >> ColorMatrix._R_) & 0xFF;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
            return new IntegerMatrix[]{
                    IntegerMatrix.parse(R), IntegerMatrix.parse(G), IntegerMatrix.parse(B)
            };
        }
    }

    /**
     * @param closeable 需要被关闭的对象。
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {

            }
        }
    }
}
