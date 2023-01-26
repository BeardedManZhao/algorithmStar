package zhao.algorithmMagic.operands.coordinate;

import zhao.algorithmMagic.operands.Operands;

/**
 * 坐标接口,在这里就是一个坐标的统一接口,坐标属于操作数,但是同时坐标也具有维度,所以可以在这里实现维度坐标的创建
 * <p>
 * Coordinate interface, here is a unified interface for coordinates, coordinates belong to operands, but at the same time coordinates also have dimensions, so the creation of dimension coordinates can be realized here
 *
 * @param <ImplementationType> 坐标实现类型,将类型告知父类,有利于父类与子类之间互相转换.
 *                             <p>
 *                             Coordinate implementation type, informing the parent class of the type, which is conducive to the mutual conversion between the parent class and the child class.
 */
public interface Coordinate<ImplementationType> extends Operands<ImplementationType> {

    /**
     * 获取到该坐标对象的维度
     *
     * @return 该坐标的维度，一般就是返回该坐标有多少轴的标志。
     * <p>
     * The dimension of the coordinate is generally a sign of how many axes the coordinate has.
     */
    int getNumberOfDimensions();

    /**
     * 安全的显式子类拓展函数
     *
     * @return 该坐标拓展到子类的函数，通过该函数可以显式并自动的将父类转换成子类对象，不需要考虑类型强转带来的问题等。
     * <p>
     * This coordinate is extended to the function of the subclass. Through this function, the parent class can be explicitly and automatically converted to the subclass object, without considering the problems caused by strong type conversion.
     */
    ImplementationType extend();
}
