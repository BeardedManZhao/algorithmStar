package zhao.algorithmMagic.io;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.table.DataFrame;

import java.io.Closeable;

/**
 * 数据输出设备类，此类中包含数据输出逻辑实现，可以通过此接口直接将数据输出到第三方平台。
 * <p>
 * Data output device class, which includes data output logic implementation, can directly output data to third-party platforms through this interface.
 *
 * @author 赵凌宇
 * 2023/4/6 19:35
 */
public interface OutputComponent extends Closeable {

    /**
     * 启动数据输出组件.
     * <p>
     * Start data output component.
     *
     * @return 如果启动成功返回true
     */
    boolean open();

    /**
     * @return 如果组件已经启动了，在这里返回true.
     * <p>
     * If the component has already started, return true here
     */
    boolean isOpen();

    /**
     * 将一份二进制数据输出。
     * <p>
     * Output a binary data.
     *
     * @param data 需要被输出的二进制数据包。
     *             <p>
     *             The binary data package that needs to be output.
     */
    void writeByteArray(byte[] data);

    /**
     * 输出一个 整形 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    void writeMat(ColumnIntegerMatrix matrix);

    /**
     * 输出一个 double类型的 矩阵对象
     *
     * @param matrix 需要被输出的矩阵
     */
    void writeMat(ColumnDoubleMatrix matrix);

    /**
     * 将图像矩阵所包含的图像直接输出到目标。
     * <p>
     * Directly output the images contained in the image matrix to the target.
     *
     * @param colorMatrix 需要被输出的图像矩阵对象。
     *                    <p>
     *                    The image matrix object that needs to be output.
     */
    void writeImage(ColorMatrix colorMatrix);

    /**
     * 将一个 DataFrame 中的数据按照数据输出组件进行输出.
     * <p>
     * Output the data in a DataFrame according to the data output component.
     *
     * @param dataFrame 需要被输出的数据对象
     */
    void writeDataFrame(DataFrame dataFrame);
}
