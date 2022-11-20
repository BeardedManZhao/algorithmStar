package zhao.algorithmMagic.lntegrator;

/**
 * 算法继承器,是针对每一个算法的实际应用模块,这里有着针对算法的调用逻辑封装,您不需要去自己实现算法的应用.
 * <p>
 * The algorithm inheritor is the actual application module for each algorithm. There is a logical encapsulation of the calling logic for the algorithm, and you do not need to implement the application of the algorithm yourself.
 *
 * @param <AlgorithmType> 算法实现类型
 */
public interface AlgorithmIntegrator<AlgorithmType> {

    /**
     * @return 该集成器的子类身份, 用于父类子类之间的互相转换
     */
    AlgorithmType expand();

    /**
     * @return 该集成器的名称
     * <p>
     * the name of the integrator
     */
    String getIntegratorName();

    /**
     * @return 运行该集成器, 并返回运行结果
     * <p>
     * Run the integrator and return the result
     */
    boolean run();

    /**
     * @return 运行该集成器, 并返回运行结果，该函数是带参数的运行，当运行成功之后会返回一个double类型的参数。
     * <p>
     * Run the integrator and return the result of the operation. The function is run with parameters. When the operation is successful, it will return a double type parameter.
     */
    double runAndReturnValue();
}
