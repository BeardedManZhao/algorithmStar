package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnDoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.table.DataFrame;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 打印机设备数据输出组件，在该组件中能够数据直接提供给打印机并于打印机中输出图像或其它数据对象。
 * <p>
 * The printer device data output component, in which data can be directly provided to the printer and output images or other data objects in the printer.
 *
 * @author zhao
 */
public final class PrintServiceOutput implements OutputComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger("PrintServiceOutput");
    private final PrintService printService;
    private final DocFlavor flavor;
    private final PrintRequestAttributeSet printRequestAttributeSet;
    private final String HTML_TABLE_NAME;
    HashDocAttributeSet hashDocAttributeSet = new HashDocAttributeSet();
    DocPrintJob printJob;

    PrintServiceOutput(final Cell<DocFlavor> flavor, final Cell<HashPrintRequestAttributeSet> attributes, final Cell<String> pName, final Cell<String> HTML_TABLE_NAME) {
        if (flavor == null || attributes == null || pName == null) {
            throw new OperatorOperationException("The parameter in [final Cell<DocFlavor> flavor, final Cell<HashPrintRequestAttributeSet> attributes, final Cell<String> pName] cannot be null!!!!");
        }
        this.flavor = flavor.getValue();
        this.printRequestAttributeSet = attributes.getValue();
        this.HTML_TABLE_NAME = HTML_TABLE_NAME == null ? "algorithmStar" : HTML_TABLE_NAME.toString();
        String pn = pName.toString();
        for (PrintService service : PrintServiceLookup.lookupPrintServices(this.flavor, this.printRequestAttributeSet)) {
            String name = service.getName();
            LOGGER.info("find " + name);
            if (pn.equals(name)) {
                printService = service;
                LOGGER.info("ok -> " + name);
                return;
            }
        }
        throw new OperatorOperationException("not find PrintServer " + pName);
    }

    public static OutputBuilder builder() {
        return new PrintServiceOutputBuilder();
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
        LOGGER.info("open()");
        printJob = printService.createPrintJob();
        return true;
    }

    /**
     * @return 如果组件已经启动了，在这里返回true.
     * <p>
     * If the component has already started, return true here
     */
    @Override
    public boolean isOpen() {
        LOGGER.info("isOpen()");
        return printJob != null;
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
        LOGGER.info("writeByteArray(byte[] data)");
        Doc doc = new SimpleDoc(data, flavor, hashDocAttributeSet);
        try {
            printJob.print(doc, printRequestAttributeSet);
        } catch (PrintException e) {
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
        LOGGER.info("writeMat(ColumnIntegerMatrix matrix)");
        throw new OperatorOperationException("暂不支持矩阵数值对象的输出。");
    }

    /**
     * 输出一个 double类型的 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    @Override
    public void writeMat(ColumnDoubleMatrix matrix) {
        LOGGER.info("writeMat(ColumnDoubleMatrix matrix)");
        throw new OperatorOperationException("暂不支持矩阵数值对象的输出。");
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
        LOGGER.info("writeImage(ColorMatrix colorMatrix)");
        BufferedImage image = new BufferedImage(colorMatrix.getColCount(), colorMatrix.getRowCount(), BufferedImage.TYPE_INT_RGB);
        colorMatrix.drawToImage(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (DocFlavor.BYTE_ARRAY.JPEG.equals(this.flavor)) {
                ImageIO.write(image, "JPG", byteArrayOutputStream);
            } else if (DocFlavor.BYTE_ARRAY.PNG.equals(this.flavor)) {
                ImageIO.write(image, "PNG", byteArrayOutputStream);
            } else {
                LOGGER.error("不支持格式[" + this.flavor + "]，目前支持 JPG PNG");
            }
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ASIO.close(byteArrayOutputStream);
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
        LOGGER.info("writeDataFrame(DataFrame dataFrame)");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        dataFrame.into_outHTMLStream(bufferedWriter, HTML_TABLE_NAME);
        ASIO.close(bufferedWriter);
        ASIO.close(outputStreamWriter);
        Doc doc = new SimpleDoc(byteArrayOutputStream.toByteArray(), flavor, new HashDocAttributeSet());
        ASIO.close(byteArrayOutputStream);
        try {
            printJob.print(doc, printRequestAttributeSet);
        } catch (PrintException e) {
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
        LOGGER.info("close()");
    }
}
