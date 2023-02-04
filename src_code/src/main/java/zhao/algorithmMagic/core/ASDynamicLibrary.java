package zhao.algorithmMagic.core;

import java.io.File;

/**
 * AS动态库类，其中包含一个动态库路径，通过该类中的配置与操作可以实现动态库的调控需求。
 * <p>
 * The AS dynamic library class contains a dynamic library path. The configuration and operation in this class can realize the control requirements of the dynamic library.
 *
 * @author zhao
 */
public final class ASDynamicLibrary {
    static boolean isUseC = false;

    /**
     * 将一个DLL目录加载到机器学习库中
     * <p>
     * Load a DLL directory into the machine learning library
     *
     * @param dirFile dll库目录对象
     *                <p>
     *                Dll library directory object
     */
    public static void addDllDir(File dirFile) {
        addDllFiles(dirFile.listFiles(File::isFile));
    }

    /**
     * 将很多DLL文件加载到机器学习库中。
     * <p>
     * Load many DLL files into the machine learning library.
     *
     * @param files 需要被加载的DLL文件对象组成的数组，该数组不能为null 同时元素数量也不能为0
     *              <p>
     *              An array of DLL file objects to be loaded. The array cannot be null and the number of elements cannot be 0
     */
    public static void addDllFiles(File[] files) {
        if (files != null && files.length != 0) {
            for (File file : files) {
                System.load(file.getPath());
            }
            isUseC = true;
        }
    }

    /**
     * 判断是否支持使用dll动态库文件。
     *
     * Determine whether the use of dll dynamic library files is supported.
     * @return 如果返回true，代表支持动态库。
     *
     * If true is returned, it means dynamic libraries are supported.
     */
    public static boolean isUseC() {
        return isUseC;
    }
}
