# AS machine learning library DLL dynamic library

The AS machine learning library supports calling the functions of the DLL dynamic library compiled by C/C++. Here is the C++source code with relevant dependency management components built in.

## Dynamic library usage

The machine learning library already contains the compiled DLL dynamic library, which is currently stored in the algorithmStar  AsLib directory. It supports running under a 64-bit JVM. If you need a 32-bit JVM, you can build this source code in a 32-bit way, which contains the functional logic implemented in C/C++, and further upgrades the powerful functions in the machine learning library.

### Use example

Copy all the dynamic libraries in the DLL library directory of the AS machine learning library to the directory you want to rely on as the AS library, and then register the directory containing the DLL file in the ASDynamicLibrary class to realize the call of the DLL library.

```java
import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.utils.ASMath;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        // 将动态库目录进行加载，AS机器学习库将会把目录下的所有DLL文件加载进来
        ASDynamicLibrary.addDllDir(new File("D:\\MyGithub\\algorithmStar\\AsLib"));
        // 判断是否启用了DLL动态库
        if (ASDynamicLibrary.isUseC()) {
            // 调用C语言实现好的数学函数
            double avg_c = ASMath.avg_C(1, 2, 3, 4, 5, 6);
            System.out.println(avg_c);
        } else {
            System.out.println("动态库加载没有成功！");
        }
        // 动态库可以手动关闭，需要注意的是这个释放DLL方式并不会直接释放，而是不去使用DLL库，库还是可以使用的
        // 该操作的本质是禁用本地的DLL库，在AS库中将会优先使用Java的实现函数。
        ASDynamicLibrary.unUseC();
        if (ASDynamicLibrary.isUseC()) {
            System.out.println("动态库没有成功禁用！");
        } else {
            System.out.println("动态库已经成功禁用！");
        }
    }
}
```
- Operation results
```
2.0
动态库已经成功禁用！
```