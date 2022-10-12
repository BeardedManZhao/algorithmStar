package zhao.algorithmMagic.operands;

/**
 * 整数型坐标接口，所有的整数型坐标的统一接口，您应对该接口的函数进行实现
 * <p>
 * Integer coordinate interface, a unified interface for all integer coordinates, you should implement the functions of this interface
 *
 * @param <ImplementationType> 实现类的类型
 */
public interface IntegerCoordinates<ImplementationType extends Operands<?>> extends Operands<ImplementationType> {
    /**
     * @return 该浮点坐标的维度数量，每一个坐标都有不同的维度，获取到所有维度的数量，有助于定位到坐标点的位置。
     * <p>
     * The number of dimensions of the floating-point coordinate, each coordinate has a different dimension, and obtaining the number of all dimensions is helpful for locating the position of the coordinate point.
     */
    int getNumberOfDimensions();

    /**
     * @return 该浮点坐标的数组形式，将浮点坐标转换成为一个数组返回出去，当然某些实现类可能会直接将数组作为一个对象的全局直接返回，这样有利于性能。
     * <p>
     * In the array form of the floating point coordinates, the floating point coordinates are converted into an array and returned. Of course, some implementation classes may directly return the array as a global object, which is beneficial to performance.
     */
    int[] toArray();

    /**
     * @return 该类的实现类对象，用于拓展该接口成为其子类，这里一般只需要返回实现类对象即可。
     * <p>
     * The implementation class object of this class is used to expand the interface to become its subclass. Generally, only the implementation class object needs to be returned here.
     */
    ImplementationType expand();
}
