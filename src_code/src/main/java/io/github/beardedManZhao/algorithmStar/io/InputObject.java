package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.DataFrame;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * 对象数据输入组件，也是反序列化组件。
 * <p>
 * The object data input component is also a deserialization component.
 *
 * @author zhao
 */
public class InputObject implements InputComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger("InputObject");

    private final InputStream inputStream;
    private ObjectInputStream ois;

    public InputObject(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static InputObjectBuilder builder() {
        return new InputObjectBuilder();
    }

    /**
     * 启动数据输入组件
     *
     * @return 如果启动成功返回true
     */
    @Override
    public boolean open() {
        LOGGER.info("InputObject.open()");
        try {
            this.ois = new ObjectInputStream(this.inputStream);
        } catch (IOException e) {
            LOGGER.error("InputObject.open() error!!!", e);
        }
        return this.isOpen();
    }

    /**
     * @return 如果组件已经启动了，在这里返回true。
     * <p>
     * If the component has already started, return true here.
     */
    @Override
    public boolean isOpen() {
        LOGGER.info("InputHDFS.isOpen()");
        return this.ois != null;
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
        LOGGER.info("InputObject.getByteArray()");
        try {
            return (byte[]) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new OperatorOperationException(e);
        }
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
        LOGGER.info("InputObject.getInt2Array()");
        try {
            return (int[][]) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new OperatorOperationException(e);
        }
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
        LOGGER.info("InputObject.getDouble2Array()");
        try {
            return (double[][]) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new OperatorOperationException(e);
        }
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
        LOGGER.info("InputObject.getDataFrame()");
        try {
            return (DataFrame) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new OperatorOperationException(e);
        }
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
        LOGGER.info("InputObject.getSFDataFrame()");
        return this.getDataFrame();
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
        LOGGER.info("InputObject.getInputStream()");
        return this.ois;
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
        LOGGER.info("InputObject.getBufferedImage()");
        try {
            return (BufferedImage) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        LOGGER.info("InputObject.close()");
        ASIO.close(this.ois);
        this.ois = null;
    }
}
