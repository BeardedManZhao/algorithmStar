package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.io.OutputComponent;

import java.io.File;

/**
 * saveMat 是一种可以以结构化或非结构化数据的方式将数据保存到指定目录中的对象，所有支持保存操作的矩都应实现此接口对象。
 * <p>
 * SaveMat is an object that can save data to a specified directory in the form of structured data. All moments that support saving operations should implement this interface object.
 *
 * @author 赵凌宇
 * 2023/3/6 8:50
 */
public interface SaveMatrix {

    /**
     * 将矩阵对象使用不同的处理方式保存到指定的路径中。
     * <p>
     * Save the matrix object to the specified path using different processing methods.
     *
     * @param path 目标文件所在路径。
     *             <p>
     *             Directory path to save.
     */
    void save(String path);

    /**
     * 将矩阵使用指定分隔符保存到文件系统的指定路径的文件中。
     * <p>
     * Save the matrix to a file in the specified path of the file system using the specified separator.
     *
     * @param path 需要保存的目录路径。
     *             <p>
     *             Directory path to save.
     * @param sep  保存时使用的分隔符。
     *             <p>
     *             Separator used when saving.
     */
    void save(String path, char sep);

    /**
     * 将矩阵使用指定分隔符保存到文件系统的指定路径的文件中。
     * <p>
     * Save the matrix to a file in the specified path of the file system using the specified separator.
     *
     * @param path 需要保存的目录路径。
     *             <p>
     *             Directory path to save.
     * @param sep  保存时使用的分隔符。
     *             <p>
     *             Separator used when saving.
     */
    void save(File path, char sep);

    /**
     * 将对象交由第三方数据输出组件进行数据的输出。
     * <p>
     * Submit the object to a third-party data output component for data output.
     *
     * @param outputComponent 第三方数据输出设备对象实现。
     *                        <p>
     *                        Implementation of third-party data output device objects.
     */
    void save(OutputComponent outputComponent);
}
