package zhao.algorithmMagic.core;

import java.io.File;

/**
 * AS动态库类，其中包含一个动态库路径，通过该类中的配置与操作可以实现动态库的调控需求。
 * <p>
 * The AS dynamic library class contains a dynamic library path. The configuration and operation in this class can realize the control requirements of the dynamic library.
 * <p>
 * 值得注意的是，直接调用DLL函数可以增强函数的功能性，但是不一定会增强函数的性能，如果在某些地方不需要使用到DLL库，您可以调用 unUseC 函数，如果在某些地方需要使用到DLL库中的强大功能，您可以调用 useC 函数。
 * <p>
 * It is worth noting that calling the DLL function directly can enhance the functionality of the function, but it does not necessarily enhance the performance of the function. If you do not need to use the DLL library in some places, you can call the UnUseC function. If you need to use the powerful functions in the DLL library in some places, you can call the useC function.
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
            useC();
        }
    }

    /**
     * 判断是否支持使用dll动态库文件。
     * <p>
     * Determine whether the use of dll dynamic library files is supported.
     *
     * @return 如果返回true，代表支持动态库。
     * <p>
     * If true is returned, it means dynamic libraries are supported.
     */
    public static boolean isUseC() {
        return isUseC;
    }

    /**
     * 终止DLL库的使用，终止之后，机器学习库中的所有计算函数将替换为Java的函数调用，某些强大的函数功能会有些损失，您可以与 useC 函数配合使用，在需要使用强大功能的时候启动C库的使用。
     * <p>
     * Terminate the use of DLL library. After termination, all calculation functions in machine learning library will be replaced by Java function calls. Some powerful function functions will be lost. You can use it with useC function to start the use of C library when you need to use powerful functions.
     */
    public static void unUseC() {
        if (isUseC()) isUseC = false;
    }

    /**
     * 启动DLL库的使用，在启动之后，机器学习中能够使用C库计算的函数将使用C库计算，某些函数的性能可能有些损失，函数的功能性将大大增强，您可以与 unUseC 函数配合使用，在不需要使用C库的时候终止C库的使用。
     * <p>
     * Start the use of DLL library. After startup, the functions that can be calculated using C library in machine learning will be calculated using C library. The performance of some functions may be lost, and the functionality of functions will be greatly enhanced. You can work with UnUseC function to terminate the use of C library when it is not needed.
     */
    public static void useC() {
        if (!isUseC()) isUseC = true;
    }
}
