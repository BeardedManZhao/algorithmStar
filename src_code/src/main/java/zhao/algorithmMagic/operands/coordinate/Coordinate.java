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
     * @return 该坐标的维度
     */
    int getNumberOfDimensions();
}
