package zhao.algorithmMagic.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Java类于 2022/10/15 14:43:22 创建
 * <p>
 * IO工具包
 *
 * @author zhao
 */
public final class ASIO {

    private final static Logger LOGGER = Logger.getLogger("ASIO");

    /**
     * 将一个图片输出到指定的路径
     *
     * @param image   被输出的图片
     * @param outPath 数据输出路径,精确到图片文件
     * @return 结果!
     */
    public static boolean outImage(BufferedImage image, String outPath) {
        try {
            ImageIO.write(image, "JPEG", new File(outPath));
            return true;
        } catch (IOException e) {
            LOGGER.error(e);
            return false;
        }
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
            LOGGER.error(e);
            return false;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

}
