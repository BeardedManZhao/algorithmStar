package zhao.algorithmMagic.operands.coordinateNet;

import zhao.algorithmMagic.operands.Operands;
import zhao.algorithmMagic.operands.coordinate.Coordinate;

import javax.ws.rs.NotSupportedException;
import java.io.Serializable;
import java.util.HashSet;

/**
 * 路线网络对象，其中包含很多路线，各个路线之间会构建成为一张网络。
 * <p>
 * A route network object, which contains many routes, and a network is constructed between each route.
 *
 * @param <CoordinateType> 路线中的坐标类型
 *                         <p>
 *                         Coordinate type in route
 * @param <RouteType>      路线类型
 *                         <p>
 *                         route type
 */
public interface RouteNet<CoordinateType extends Coordinate<?>, RouteType extends zhao.algorithmMagic.operands.route.Route<RouteType, CoordinateType>>
        extends Operands<RouteNet<CoordinateType, RouteType>>, Serializable {

    RuntimeException NOT_SUP = new NotSupportedException("针对线路网络操作数数据类型，不支持多功能计算操作。\nMultifunctional calculation operations are not supported for line network operand data types.");

    /**
     * 判断一条线路是否存在于线路网中。
     * <p>
     * Determines whether a line exists in the line network.
     *
     * @param RouteName 线路名称
     *                  <p>
     *                  line name
     * @return true代表在线路网中存在
     * <p>
     * true means exists in the line network
     */
    boolean containsKeyFromRoute2DHashMap(String RouteName);

    /**
     * @return 路线网络数据集，其中每一个元素都是一条路线
     */
    HashSet<RouteType> getNetDataSet();

    /**
     * @param routeType 需要被添加的网络数据集
     * @return 是否添加成功
     */
    boolean addRoute(RouteType routeType);

    int getRouteCount();
}
