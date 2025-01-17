package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.*;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import io.github.beardedManZhao.algorithmStar.utils.ASStr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 从一个数据输入流中获取数据的实现类，该类中封装了一系列通过数据输入流获取到数据的操作。
 * <p>
 * An implementation class that obtains data from a data input stream, encapsulating a series of operations to obtain data through the data input stream.
 *
 * @author zhao
 */
public final class InputByStream implements InputComponent {

    private final InputStream inputStream;
    private final Scanner scanner;
    private final Charset charSet;
    private final int rowLength;
    private final int pk;
    private final char sep;

    /**
     * @param inputStream 需要被读取的数据输入流对象
     * @param charSet     数据输入分隔符，结构化数据需要使用此参数
     * @param rowLength   数据输入行数，结构化数据需要使用此参数
     * @param pk          数据主键，DF数据输入需要使用此参数
     * @param sep         数据输入分隔符，结构化数据输入需要此参数
     */
    public InputByStream(Cell<InputStream> inputStream, Cell<Charset> charSet, int rowLength, int pk, char sep) {
        if (inputStream == null || charSet == null) {
            throw new OperatorOperationException("The parameter in [Cell<InputStream> inputStream, Cell<Charset> charSet] cannot be null!!!!");
        }
        this.inputStream = inputStream.getValue();
        scanner = new Scanner(this.inputStream);
        this.charSet = Charset.forName(charSet.toString());
        this.rowLength = rowLength;
        this.pk = pk;
        this.sep = sep;
    }

    public static InputBuilder builder() {
        return new InputByStreamBuilder();
    }

    /**
     * 启动数据输入组件
     *
     * @return 如果启动成功返回true
     */
    @Override
    public boolean open() {
        return isOpen();
    }

    /**
     * @return 如果组件已经启动了，在这里返回true。
     * <p>
     * If the component has already started, return true here.
     */
    @Override
    public boolean isOpen() {
        return true;
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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while (scanner.hasNext()) {
                byteArrayOutputStream.write(scanner.nextLine().getBytes(charSet));
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
        int[][] res = new int[this.rowLength][];
        int index = -1;
        while (++index < this.rowLength) {
            String[] strings = ASStr.splitByChar(scanner.nextLine(), sep);
            int[] row = new int[strings.length];
            int index_2 = -1;
            for (String string : strings) {
                row[++index_2] = Integer.parseInt(string);
            }
            res[index] = row;
        }
        return res;
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
        double[][] res = new double[this.rowLength][];
        int index = -1;
        while (++index < this.rowLength) {
            String[] strings = ASStr.splitByChar(scanner.nextLine(), sep);
            double[] row = new double[strings.length];
            int index_2 = -1;
            for (String string : strings) {
                row[++index_2] = Double.parseDouble(string);
            }
            res[index] = row;
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
        DataFrame select = FDataFrame.select(
                FieldCell.parse(ASStr.splitByChar(scanner.nextLine(), sep)), pk
        );
        int index = -1;
        while (++index < this.rowLength) {
            select.insert(FinalSeries.parse(ASStr.splitByChar(scanner.nextLine(), sep)));
        }
        return select;
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
        DataFrame select = SFDataFrame.select(
                FieldCell.parse(ASStr.splitByChar(scanner.nextLine(), sep)), pk
        );
        int index = -1;
        while (++index < this.rowLength) {
            select.insert(SingletonSeries.parse(ASStr.splitByChar(scanner.nextLine(), sep)));
        }
        return select;
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
        return inputStream;
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
        try {
            return ImageIO.read(inputStream);
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
        ASIO.close(this.scanner);
    }
}
