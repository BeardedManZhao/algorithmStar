# AS机器学习库DLL动态库

AS机器学习库支持针对C/C++所编译出来的DLL动态库的函数进行调用，在这里就是内置了相关的依赖管理组件的C++源代码

## 使用方式

机器学习库中已经包含了编译好的DLL动态库，目前在 algorithmStar\AsLib
目录中存储，其支持在64位的JVM下运行，如果您需要32位的JVM，可以将此源代码进行一个32位的构建，其包含了以C/C++实现的函数逻辑，为机器学习库中的强大的功能进一步的做了升级。

### 使用示例

将AS 机器学习库的DLL库目录中所有的动态库复制到您想要作为AS库依赖的目录中，然后将包含DLL文件的目录在 ASDynamicLibrary 类中进行注册，就可以实现DLL库的调用。

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

- 运行结果

```
2.0
动态库已经成功禁用！
```