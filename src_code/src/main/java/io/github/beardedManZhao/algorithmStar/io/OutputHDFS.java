package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.integrator.ImageRenderingIntegrator;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnDoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.table.DataFrame;
import io.github.beardedManZhao.algorithmStar.operands.table.Series;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * HDFS 平台数据输出设备类，在该类示例化出来的对象中能够直接将数据输出到HDFS文件系统中。
 * <p>
 * HDFS platform data output device class, which can directly output data to the HDFS file system in the instantiated objects of this class.
 *
 * @author 赵凌宇
 * 2023/4/6 20:01
 */
public final class OutputHDFS implements OutputComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger("OutputHDFS");


    private final FileSystem fileSystem;
    private final Path outputPath;
    private final String format;
    private final char sep;
    private boolean isOpen = false;
    private FSDataOutputStream fsDataOutputStream;
    private BufferedWriter bufferedWriter;

    /**
     * @param fileSystem HDFS 文件系统对象。
     *                   <p>
     *                   HDFS File System Objects.
     * @param outputPath HDFS 文件数据输出目录。
     *                   <p>
     *                   HDFS file data output directory.
     * @param format     HDFS 文件数据输出格式。
     *                   <p>
     *                   HDFS file data output format.
     * @param sep        文件数据输出时需要使用的分隔符。
     *                   <p>
     *                   Delimiters required for file data output.
     */
    public OutputHDFS(FileSystem fileSystem, Path outputPath, String format, char sep) {
        if (fileSystem == null || outputPath == null || format == null) {
            throw new OperatorOperationException("The parameter in [FileSystem fileSystem, Path outputPath, String format] cannot be null!!!!");
        }
        this.fileSystem = fileSystem;
        this.outputPath = outputPath;
        this.format = format;
        this.sep = sep;
    }

    public static OutputBuilder builder() {
        return new OutputHDFSBuilder();
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
        try {
            LOGGER.info("OutputHDFS.open()");
            fsDataOutputStream = fileSystem.create(outputPath);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream));
            this.isOpen = true;
            return true;
        } catch (IOException e) {
            LOGGER.error("OutputHDFS.open() error!!!", e);
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
        return this.isOpen;
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
        LOGGER.info("OutputHDFS.writeByteArray(byte[] data)");
        try {
            fsDataOutputStream.write(data);
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
        int rowCount = -1;
        try {
            // 输出列
            bufferedWriter.write("colName");
            String[] rowFieldNames = matrix.getRowFieldNames();
            for (String colName : matrix.getColFieldNames()) {
                bufferedWriter.write(sep);
                bufferedWriter.write(colName);
            }
            bufferedWriter.newLine();
            for (int[] ints : matrix.toArrays()) {
                bufferedWriter.write(rowFieldNames[++rowCount]);
                for (int aInt : ints) {
                    bufferedWriter.write(sep);
                    bufferedWriter.write(String.valueOf(aInt));
                }
            }
        } catch (IOException e) {
            throw new OperatorOperationException("Write data exception!", e);
        }
    }

    /**
     * 输出一个 double类型的 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    @Override
    public void writeMat(ColumnDoubleMatrix matrix) {
        int rowCount = -1;
        try {
            // 输出列
            bufferedWriter.write("colName");
            String[] rowFieldNames = matrix.getRowFieldNames();
            for (String colName : matrix.getColFieldNames()) {
                bufferedWriter.write(sep);
                bufferedWriter.write(colName);
            }
            bufferedWriter.newLine();
            for (double[] ints : matrix.toArrays()) {
                bufferedWriter.write(rowFieldNames[++rowCount]);
                for (double aInt : ints) {
                    bufferedWriter.write(sep);
                    bufferedWriter.write(String.valueOf(aInt));
                }
            }
        } catch (IOException e) {
            throw new OperatorOperationException("Write data exception!", e);
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
        LOGGER.info("OutputHDFS.writeImage(ColorMatrix colorMatrix)");
        BufferedImage bufferedImage = ImageRenderingIntegrator.drawBufferedImage(
                colorMatrix.toArrays(), colorMatrix.getColCount(), colorMatrix.getRowCount(), 1, false
        );
        try {
            ImageIO.write(bufferedImage, format, fsDataOutputStream);
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
        LOGGER.info("OutputHDFS.writeDataFrame(DataFrame dataFrame)");
        try {
            bufferedWriter.write("rowNumber");
            for (Cell<?> cell : dataFrame.getFields()) {
                bufferedWriter.write(sep);
                bufferedWriter.write(cell.getStringValue());
            }
            bufferedWriter.newLine();
            int count = 0;
            for (Series cells : dataFrame) {
                bufferedWriter.write(String.valueOf(++count));
                for (Cell<?> cell : cells) {
                    bufferedWriter.write(sep);
                    bufferedWriter.write(cell.toString());
                }
                bufferedWriter.newLine();
            }
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
        LOGGER.info("OutputHDFS.close()");
        ASIO.close(this.bufferedWriter);
        ASIO.close(this.fsDataOutputStream);
    }
}
