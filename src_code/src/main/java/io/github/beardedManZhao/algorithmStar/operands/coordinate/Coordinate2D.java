package io.github.beardedManZhao.algorithmStar.operands.coordinate;

/**
 * 二维坐标接口，所有的二维坐标的父类。
 * <p>
 * Two-dimensional coordinate interface, the parent class of all two-dimensional coordinates.
 *
 * @param <value> 坐标存储的数值类型
 */
public interface Coordinate2D<ImplementType, value> extends Coordinate<ImplementType> {

    /**
     * @return X轴的数值
     * <p>
     * The value of the X axis
     */
    value getX();

    /**
     * @return Y轴的数值
     * <p>
     * The value of the Y axis
     */
    value getY();

}
