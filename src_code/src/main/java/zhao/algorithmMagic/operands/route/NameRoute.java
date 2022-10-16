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
}
