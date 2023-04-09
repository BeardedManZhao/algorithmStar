package zhao.algorithmMagic.io;

import zhao.algorithmMagic.operands.table.DataFrame;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.InputStream;

/**
 * IO组件对象，该对象是所有数据输入设备的父类接口对象，通过该对象可以实现获取到第三方数据的操作
 * <p>
 * IO component object, which is the parent interface object of all data input devices, through which the operation of obtaining third-party data can be achieved
 *
 * @author 赵凌宇
 * 2023/4/5 13:52
 */
public interface InputComponent extends Closeable {

    /**
     * 启动数据输入组件
     *
     * @return 如果启动成功返回true
     */
    boolean open();

    /**
     * @return 如果组件已经启动了，在这里返回true。
     * <p>
     * If the component has already started, return true here.
     */
    boolean isOpen();

    /**
     * 从数据输入组件中提取出 byte 数组的数据，一般情况下，这里返回的都是一些二进制的数据。
     * <p>
     * Extract the data of the byte array from the data input component. Generally, the returned data here is some binary data.
     *
     * @return byte[] 的数据对象
     */
    byte[] getByteArray();

    /**
     * 从数据输入组件中提取出 int 矩阵数据，一般情况下，这里返回的是一些矩阵元素数据。
     * <p>
     * From the data input component, int matrix data is increasingly generated. Generally, some matrix element data is returned here.
     *
     * @return int[][]
     */
    int[][] getInt2Array();

    /**
     * 从数据输入组件中提取出 int 矩阵数据，一般情况下，这里返回的是一些矩阵元素数据。
     * <p>
     * From the data input component, double matrix data is increasingly generated. Generally, some matrix element data is returned here.
     *
     * @return double[][]
     */
    double[][] getDouble2Array();

    /**
     * 从数据输入组件获取到 DataFrame 对象，该函数有些数据输入组件可能不支持。
     * <p>
     * Retrieve the DataFrame object from the data input component, which may not be supported by some data input components.
     *
     * @return 从数据输入组件中获取到的DataFrame数据封装对象。
     * <p>
     * The DataFrame data encapsulation object obtained from the data input component.
     */
    DataFrame getDataFrame();

    /**
     * 从数据输入组件中提取出 数据流 对象。
     * <p>
     * Extract the data flow object from the data input component.
     *
     * @return 数据输入流对象
     */
    InputStream getInputStream();

    /**
     * 从数据输入组件中提取出 图像缓存 对象，需要注意的是，该操作在有些情况下可能不被支持。
     * <p>
     * Extracting image cache objects from the data input component, it should be noted that this operation may not be supported in some cases.
     *
     * @return 图像缓存对象。
     */
    BufferedImage getBufferedImage();
}
