package zhao.algorithmMagic.Integrator.launcher;

/**
 * 启动器是集成器需要的类型，集成器运行的就是一个启动器，实现了该启动器的类都可以集成到不同的集成器中，集成器需要的启动器类型可能不同，具体需要查阅集成器的文档！
 * <p>
 * The launcher is the type required by the integrator. The integrator runs a launcher. All classes that implement the launcher can be integrated into different integrators. The type of launcher required by the integrator may be different. For details, please refer to the integrator. 's documentation!
 *
 * @param <ImplementationType> 该启动器的类型
 *                             <p>
 *                             the type of launcher
 */
public interface Launcher<ImplementationType> {
    /**
     * @return 启动器的子类实现，用来显示转换到子类，一般只需要在这里进行一个this的返回即可
     * <p>
     * The subclass implementation of the launcher is used to display the conversion to the subclass. Generally, only a "this" return is required here.
     */
    ImplementationType expand();

}