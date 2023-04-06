package zhao.algorithmMagic.io;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.table.*;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.ASStr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * HDFS数据输入组件对象，能够从HDFS平台中获取到文件数据。
 * <p>
 * HDFS data input component object, which can obtain file data from the HDFS platform.
 *
 * @author 赵凌宇
 * 2023/4/6 8:40
 */
public class InputHDFS implements InputComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger("InputHDFS");
    private final FileSystem fileSystem;
    private final Path inputPath;
    private final char sep;
    private final String charset;
    private final Series field;
    private final int pk;
    private FSDataInputStream fsDataInputStream;
    private BufferedReader bufferedReader;
    private boolean isOpen;

    /**
     * @param fileSystem HDFS 文件系统对象
     * @param inputPath  需要被读取的文件路径
     * @param sep        读取时需要使用的分隔符
     * @param charset    读取时需要使用字符集
     * @param field      读取时的表字段名称行
     * @param pk         读取时的表主键对象
     */
    protected InputHDFS(FileSystem fileSystem, Path inputPath, char sep, String charset, String[] field, int pk) {
        if (fileSystem == null || inputPath == null || field == null) {
            throw new OperatorOperationException("The parameter in [FileSystem fileSystem, Path inputPath, String[] field] cannot be null!!!!");
        }
        this.fileSystem = fileSystem;
        this.inputPath = inputPath;
        this.sep = sep;
        this.charset = charset;
        this.field = FieldCell.parse(field);
        this.pk = pk;
        this.isOpen = false;
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
        try {
            LOGGER.info("InputHDFS.open()");
            this.fsDataInputStream = fileSystem.open(this.inputPath);
            this.bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
            return true;
        } catch (IOException e) {
            LOGGER.error("InputHDFS.open() error!!!", e);
            return false;
        }
    }

    /**
     * @return 如果组件已经启动了，在这里返回true
     */
    @Override
    public boolean isOpen() {
        LOGGER.info("InputHDFS.isOpen()");
        return this.isOpen;
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
        LOGGER.info("getByteArray()");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while (bufferedReader.ready()) {
                byteArrayOutputStream.write(bufferedReader.readLine().getBytes(charset));
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        } finally {
            ASIO.close(byteArrayOutputStream);
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
        LOGGER.info("getInt2Array()");
        LOGGER.warn("Not currently supported getInt2Array()!!!");
        return new int[0][];
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
        LOGGER.info("getDouble2Array()");
        LOGGER.warn("Not currently supported getDouble2Array()!!!");
        return new double[0][];
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
        LOGGER.info("getDataFrame()");
        DataFrame select = FDataFrame.select(this.field, pk);
        try {
            while (bufferedReader.ready()) {
                select.insert(FinalSeries.parse(ASStr.splitByChar(bufferedReader.readLine(), sep)));
            }
            return select;
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
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
        LOGGER.info("getInputStream()");
        return this.fsDataInputStream;
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
        LOGGER.info("getBufferedImage()");
        try {
            return ImageIO.read(this.fsDataInputStream);
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
     */
    @Override
    public void close() {
        LOGGER.info("close()");
        ASIO.close(this.bufferedReader);
        isOpen = false;
    }
}
