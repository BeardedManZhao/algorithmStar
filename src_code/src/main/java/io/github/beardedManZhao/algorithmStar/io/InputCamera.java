package io.github.beardedManZhao.algorithmStar.io;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamUtils;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.DataFrame;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 摄像头对象，用于从摄像头抓取一张图像。
 *
 * @author 赵凌宇
 * 2023/4/5 13:51
 */
public final class InputCamera implements InputComponent {

    private final Webcam webcam;
    private final String format;

    /**
     * @param webcam 拍照对象
     * @param format 拍照之后的图像格式
     */
    InputCamera(Webcam webcam, String format) {
        this.webcam = webcam;
        this.format = format;
    }

    /**
     * @return 开始构建本数据组件对象。
     * <p>
     * Start building this data component object.
     */
    public static InputBuilder builder() {
        return new InputCameraBuilder();
    }

    /**
     * 启动数据输入组件
     *
     * @return 如果启动成功返回true
     */
    @Override
    public boolean open() {
        return webcam.open();
    }

    /**
     * @return 如果组件已经启动了，在这里返回true
     */
    @Override
    public boolean isOpen() {
        return webcam.isOpen();
    }

    /**
     * 关闭数据输入组件
     */
    @Override
    public void close() {
        webcam.close();
    }

    /**
     * 从数据输入组件中提取出 byte 数组的数据，一般情况下，这里返回的都是一些二进制的数据。
     * <p>
     * Extract the data of the byte array from the data input component. Generally, the returned data here is some binary data.
     *
     * @return byte[] 的数据对象
     */
    @Override
    public byte[] getByteArray() {
        return WebcamUtils.getImageBytes(webcam, format);
    }

    /**
     * 从数据输入组件中提取出 int 矩阵数据，一般情况下，这里返回的是一些矩阵元素数据。
     * <p>
     * From the data input component, int matrix data is increasingly generated. Generally, some matrix element data is returned here.
     *
     * @return int[][]
     */
    @Override
    public int[][] getInt2Array() {
        return ASIO.parseImageGetArray(this.getBufferedImage());
    }

    /**
     * 从数据输入组件中提取出 int 矩阵数据，一般情况下，这里返回的是一些矩阵元素数据。
     * <p>
     * From the data input component, double matrix data is increasingly generated. Generally, some matrix element data is returned here.
     *
     * @return double[][]
     */
    @Override
    public double[][] getDouble2Array() {
        int[][] int2Array = this.getInt2Array();
        double[][] res = new double[int2Array.length][];
        int index = -1;
        for (int[] ints : int2Array) {
            res[++index] = ASClass.IntArray_To_DoubleArray(ints);
        }
        return res;
    }

    /**
     * 从数据输入组件获取到 DataFrame 对象，该函数有些数据输入组件可能不支持。
     * <p>
     * Retrieve the DataFrame object from the data input component, which may not be supported by some data input components.
     *
     * @return 从数据输入组件中获取到的DataFrame数据封装对象。
     * <p>
     * The DataFrame data encapsulation object obtained from the data input component.
     */
    @Override
    public DataFrame getDataFrame() {
        throw new UnsupportedOperationException("相机设备不支持获取到DataFrame对象。\nThe camera device does not support obtaining DataFrame objects.");
    }

    /**
     * 从数据输入组件获取到 DataFrame 对象，该函数有些数据输入组件可能不支持。
     * <p>
     * Retrieve the DataFrame object from the data input component, which may not be supported by some data input components.
     *
     * @return 从数据输入组件中获取到的DataFrame数据封装对象。
     * <p>
     * The DataFrame data encapsulation object obtained from the data input component.
     */
    @Override
    public DataFrame getSFDataFrame() {
        return getDataFrame();
    }

    /**
     * 从数据输入组件中提取出 数据流 对象。
     * <p>
     * Extract the data flow object from the data input component.
     *
     * @return 数据输入流对象
     */
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.getByteArray());
    }

    /**
     * 从数据输入组件中提取出 图像缓存 对象，需要注意的是，该操作在有些情况下可能不被支持。
     * <p>
     * Extracting image cache objects from the data input component, it should be noted that this operation may not be supported in some cases.
     *
     * @return 图像缓存对象。
     */
    @Override
    public BufferedImage getBufferedImage() {
        InputStream inputStream = null;
        try {
            inputStream = this.getInputStream();
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new OperatorOperationException("照相机拍照读取失败.Camera photo reading failed.", e);
        } finally {
            ASIO.close(inputStream);
        }
    }

}
