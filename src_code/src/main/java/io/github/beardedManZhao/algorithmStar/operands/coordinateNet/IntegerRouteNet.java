package io.github.beardedManZhao.algorithmStar.operands.coordinateNet;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute;

import java.util.*;

/**
 * Java类于 2022/10/19 17:07:38 创建
 * <p>
 * Integer类型的多维线路网，存储的都是Integer类型的多维线路对象，其中的每一个坐标维度可以是任意的，但是需要每一个坐标的维度数量保持一致！
 * <p>
 * The Integer-type multidimensional line network stores all Integer-type multidimensional line objects. Each coordinate dimension can be arbitrary, but the number of dimensions of each coordinate needs to be consistent!
 *
 * @author zhao
 */
public class IntegerRouteNet implements RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> {

    private final HashMap<String, IntegerConsanguinityRoute> integerConsanguinityRouteHashMap = new HashMap<>(0b1010);
    private final HashMap<String, IntegerConsanguinityRoute> integerConsanguinityRoute2DHashMap_SubMark = new HashMap<>(0b1010);
    private final HashMap<String, IntegerConsanguinityRoute> integerConsanguinityRoute2DHashMap_MasterTag = new HashMap<>();

    /**
     * 构造出来一张线路网
     * <p>
     * construct a network of lines
     *
     * @param routeCollection 这张网中的所有线路
     *                        <p>
     *                        All lines in this net
     */
    private IntegerRouteNet(Collection<IntegerConsanguinityRoute> routeCollection) {
        for (IntegerConsanguinityRoute IntegerConsanguinityRoute : routeCollection) {
            addRoute(IntegerConsanguinityRoute);
        }
    }

    private IntegerRouteNet(Map<String, IntegerConsanguinityRoute> map) {
        this.integerConsanguinityRouteHashMap.putAll(map);
    }

    /**
     * 将一个整形的二维线路网构建出来。
     * <p>
     * A shaped two-dimensional line network is constructed.
     *
     * @param routeCollection 整个网络中所有的线路对象。
     *                        <p>
     *                        All line objects in the entire network.
     * @return 整形的二维线路网
     */
    public static IntegerRouteNet parse(Collection<IntegerConsanguinityRoute> routeCollection) {
        return new IntegerRouteNet(routeCollection);
    }

    /**
     * @param IntegerConsanguinityRoute2DS 整个网络中所有的线路对象。
     *                                     <p>
     *                                     All line objects in the entire network.
     * @return Shaped 2D line network
     */
    public static IntegerRouteNet parse(IntegerConsanguinityRoute... IntegerConsanguinityRoute2DS) {
        return new IntegerRouteNet(Arrays.asList(IntegerConsanguinityRoute2DS));
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 将两个网络中的每一个线路进行求和（不保证线路之间的顺序）
     */
    @Override
    public RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> add(RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> value) {
        int routeCount1 = this.getRouteCount();
        int routeCount2 = value.getRouteCount();
        if (routeCount1 == routeCount2) {
            Iterator<IntegerConsanguinityRoute> iterator1 = this.getNetDataSet().iterator();
            Iterator<IntegerConsanguinityRoute> iterator2 = value.getNetDataSet().iterator();
            ArrayList<IntegerConsanguinityRoute> arrayList = new ArrayList<>();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                arrayList.add(iterator1.next().add(iterator2.next()));
            }
            return IntegerRouteNet.parse(arrayList);
        } else {
            throw new OperatorOperationException("在 'IntegerRoute2DNet1 add IntegerRoute2DNet2' 的时候出现了错误，两个线路网中的线路数量不同，因此无法求和。\n" +
                    "There was an error when 'IntegerRoute2DNet1 add IntegerRoute2DNet2', the number of routes in the two route nets is different, so the sum cannot be done. \n" +
                    "Number of lines =>  IntegerRoute2DNet1[" + routeCount1 + "]  IntegerRoute2DNet2[" + routeCount2 + "]");
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * 将两个网络中的每一个线路进行做差（不保证线路之间的顺序）
     */
    @Override
    public RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> diff(RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> value) {
        int routeCount1 = this.getRouteCount();
        int routeCount2 = value.getRouteCount();
        if (routeCount1 == routeCount2) {
            Iterator<IntegerConsanguinityRoute> iterator1 = this.getNetDataSet().iterator();
            Iterator<IntegerConsanguinityRoute> iterator2 = value.getNetDataSet().iterator();
            ArrayList<IntegerConsanguinityRoute> arrayList = new ArrayList<>();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                arrayList.add(iterator1.next().diff(iterator2.next()));
            }
            return IntegerRouteNet.parse(arrayList);
        } else {
            throw new OperatorOperationException("在 'IntegerRoute2DNet1 add IntegerRoute2DNet2' 的时候出现了错误，两个线路网中的线路数量不同，因此无法求和。\n" +
                    "There was an error when 'IntegerRoute2DNet1 add IntegerRoute2DNet2', the number of routes in the two route nets is different, so the sum cannot be done. \n" +
                    "Number of lines =>  IntegerRoute2DNet1[" + routeCount1 + "]  IntegerRoute2DNet2[" + routeCount2 + "]");
        }
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> add(Number value) {
        throw NOT_SUP;
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> diff(Number value) {
        throw NOT_SUP;
    }

    /**
     * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
     * <p>
     * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
     *
     * @return 当前类对应的子类实现数据类型的对象。
     * <p>
     * The subclass corresponding to the current class implements objects of data type.
     */
    @Override
    public RouteNet<IntegerCoordinateMany, IntegerConsanguinityRoute> expand() {
        return this;
    }

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
    @Override
    public boolean containsKeyFromRoute2DHashMap(String RouteName) {
        return this.integerConsanguinityRouteHashMap.containsKey(RouteName) || this.integerConsanguinityRoute2DHashMap_SubMark.containsKey(RouteName) || this.integerConsanguinityRoute2DHashMap_MasterTag.containsKey(RouteName);
    }

    /**
     * @return 路线网络数据集，其中每一个元素都是一条路线
     */
    @Override
    public HashSet<IntegerConsanguinityRoute> getNetDataSet() {
        List<IntegerConsanguinityRoute> arrayList = new ArrayList<>(this.integerConsanguinityRouteHashMap.values());
        arrayList.addAll(this.integerConsanguinityRoute2DHashMap_SubMark.values());
        arrayList.addAll(this.integerConsanguinityRoute2DHashMap_MasterTag.values());
        return new HashSet<>(arrayList);
    }

    /**
     * @param IntegerConsanguinityRoute 需要被添加的网络数据集
     * @return 是否添加成功
     */
    @Override
    public boolean addRoute(IntegerConsanguinityRoute IntegerConsanguinityRoute) {
        this.integerConsanguinityRouteHashMap.put(IntegerConsanguinityRoute.getRouteName(), IntegerConsanguinityRoute);
        return true;
    }

    @Override
    public int getRouteCount() {
        return this.integerConsanguinityRouteHashMap.size() + this.integerConsanguinityRoute2DHashMap_SubMark.size() + this.integerConsanguinityRoute2DHashMap_MasterTag.size();
    }

    /**
     * 添加一条被标记的路线对象,该类实现了绘图集成器的第二般接口,因此该方法拓展出来了一个新功能就是标记路线,您可以在这个方法中添加标记路线.
     * <p>
     * Add a marked route object, this class implements the second general interface of the drawing integrator, so this method extends a new function to mark the route, you can add the marked route in this method.
     *
     * @param integerConsanguinityRoute 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                  A marked 2D route object that can be drawn in a special color in the plotter!
     *                                  有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                  <p>
     *                                  For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addMasterTagRoute(IntegerConsanguinityRoute integerConsanguinityRoute) {
        String s = integerConsanguinityRoute.getRouteName();
        this.integerConsanguinityRoute2DHashMap_MasterTag.put(s, integerConsanguinityRoute);
        this.integerConsanguinityRoute2DHashMap_SubMark.remove(s);
        this.integerConsanguinityRouteHashMap.remove(s);
    }

    /**
     * 添加一条被标记的路线对象,该类实现了绘图集成器的第二般接口,因此该方法拓展出来了一个新功能就是标记路线,您可以在这个方法中添加标记路线.
     * <p>
     * Add a marked route object, this class implements the second general interface of the drawing integrator, so this method extends a new function to mark the route, you can add the marked route in this method.
     *
     * @param integerConsanguinityRoute 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                  A marked 2D route object that can be drawn in a special color in the plotter!
     *                                  有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                  <p>
     *                                  For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addSubMarkRoute(IntegerConsanguinityRoute integerConsanguinityRoute) {
        String s = integerConsanguinityRoute.getRouteName();
        this.integerConsanguinityRoute2DHashMap_SubMark.put(s, integerConsanguinityRoute);
        this.integerConsanguinityRouteHashMap.remove(s);
        this.integerConsanguinityRoute2DHashMap_MasterTag.remove(s);
    }

    /**
     * 从普通集合中获取到一个路线对象。
     * <p>
     * Get a route object from the normal collection.
     *
     * @param RouteName 路线对象的名称
     *                  <p>
     *                  the name of the route object
     * @return 路线对象
     * <p>
     * route object
     */
    public IntegerConsanguinityRoute getRouteFromHashMap(String RouteName) {
        return this.integerConsanguinityRouteHashMap.get(RouteName);
    }

    /**
     * 从SubMark集合中获取到一个路线对象。
     * <p>
     * Get a route object from the Sub Mark collection.
     *
     * @param RouteName 路线对象的名称
     *                  <p>
     *                  the name of the route object
     * @return 路线对象
     * <p>
     * route object
     */
    public IntegerConsanguinityRoute getRouteFromSubMark(String RouteName) {
        return this.integerConsanguinityRoute2DHashMap_SubMark.get(RouteName);
    }

    /**
     * 从MasterTag集合中获取到一个路线对象。
     * <p>
     * Get a route object from the Master Tag collection.
     * <p>
     * Get a route object from the normal collection.
     *
     * @param RouteName 路线对象的名称
     *                  <p>
     *                  the name of the route object
     * @return 路线对象
     * <p>
     * route object
     */
    public IntegerConsanguinityRoute getRouteFromMasterTag(String RouteName) {
        return this.integerConsanguinityRoute2DHashMap_MasterTag.get(RouteName);
    }

    public HashMap<String, IntegerConsanguinityRoute> getIntegerConsanguinityRouteHashMap() {
        return integerConsanguinityRouteHashMap;
    }

    public HashMap<String, IntegerConsanguinityRoute> getIntegerConsanguinityRoute2DHashMap_SubMark() {
        return integerConsanguinityRoute2DHashMap_SubMark;
    }

    public HashMap<String, IntegerConsanguinityRoute> getIntegerConsanguinityRoute2DHashMap_MasterTag() {
        return integerConsanguinityRoute2DHashMap_MasterTag;
    }
}
