package zhao.algorithmMagic.operands.coordinate;

/**
 * 三维坐标接口，所有的三维坐标的父类。
 * <p>
 * Three-dimensional coordinate interface, the parent class of all three-dimensional coordinates.
 *
 * @param <ImplementType> 左边的实现类类型
 * @param <value>         坐标存储的数值类型
 */
public interface Coordinate3D<ImplementType, value> extends Coordinate2D<ImplementType, value> {
    /**
     * @return Z轴的数值
     * <p>
     * The value of the Z axis
     */
    value getZ();

}
