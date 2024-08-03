package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnDoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.table.DataFrame;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 可重复启停的对象输出组件，该组件可以输出所有支持序列化的对象。
 * <p>
 * Repeatable start stop object output component, which can output all objects that support serialization.
 *
 * @author zhao
 */
public class OutputObject implements OutputComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger("OutputObject");
    private final OutputStream outputStream;
    private ObjectOutputStream oos;

    public OutputObject(OutputStream os) {
        this.outputStream = os;
    }

    public static OutputObjectBuilder builder() {
        return new OutputObjectBuilder();
    }

    /**
     * 启动数据输出组件.
     * <p>
     * Start data output component.
     *
     * @return 如果启动成功返回true
     */
    @Override
    public boolean open() {
        LOGGER.info("OutputHDFS.open()");
        if (this.isOpen()) {
            return true;
        }
        try {
            this.oos = new ObjectOutputStream(this.outputStream);
            return true;
        } catch (IOException e) {
            LOGGER.error("OutputObject.open() error!!!", e);
            return false;
        }
    }

    /**
     * @return 如果组件已经启动了，在这里返回true.
     * <p>
     * If the component has already started, return true here
     */
    @Override
    public boolean isOpen() {
        LOGGER.info("OutputHDFS.isOpen()");
        return oos != null;
    }

    /**
     * 将一份二进制数据输出。
     * <p>
     * Output a binary data.
     *
     * @param data 需要被输出的二进制数据包。
     *             <p>
     *             The binary data package that needs to be output.
     */
    @Override
    public void writeByteArray(byte[] data) {
        LOGGER.info("OutputObject.writeByteArray(byte[] data)");
        try {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 输出一个 整形 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    @Override
    public void writeMat(ColumnIntegerMatrix matrix) {
        try {
            oos.writeObject(matrix);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 输出一个 double类型的 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    @Override
    public void writeMat(ColumnDoubleMatrix matrix) {
        try {
            oos.writeObject(matrix);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将图像矩阵所包含的图像直接输出到目标。
     * <p>
     * Directly output the images contained in the image matrix to the target.
     *
     * @param colorMatrix 需要被输出的图像矩阵对象。
     *                    <p>
     *                    The image matrix object that needs to be output.
     */
    @Override
    public void writeImage(ColorMatrix colorMatrix) {
        LOGGER.info("OutputObject.writeImage(ColorMatrix colorMatrix)");
        try {
            oos.writeObject(colorMatrix);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将一个 DataFrame 中的数据按照数据输出组件进行输出.
     * <p>
     * Output the data in a DataFrame according to the data output component.
     *
     * @param dataFrame 需要被输出的数据对象
     */
    @Override
    public void writeDataFrame(DataFrame dataFrame) {
        LOGGER.info("OutputObject.writeDataFrame(DataFrame dataFrame)");
        try {
            oos.writeObject(dataFrame);
        } catch (IOException e) {
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
        LOGGER.info("OutputObject.close()");
        ASIO.close(this.oos);
        oos = null;
    }
}
