package zhao.algorithmMagic.operands.route;

import zhao.algorithmMagic.operands.coordinate.Coordinate;

public interface NameRoute<ImplementationType, CoordinateType extends Coordinate<?>> extends Route<ImplementationType, CoordinateType> {
    /**
     * @return 起始坐标的名字
     */
    String getStartingCoordinateName();

    /**
     * @return 终止坐标的名字
     */
    String getEndPointCoordinateName();


    /**
     * 获取到线路的字符串表现形式
     * <p>
     * Get the string representation of the line
     *
     * @return 线路的字符窜名称。
     * <p>
     * The character name of the line.
     */
    String getRouteName();
}
