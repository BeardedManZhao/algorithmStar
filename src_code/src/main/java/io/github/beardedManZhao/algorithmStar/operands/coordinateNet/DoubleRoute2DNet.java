package io.github.beardedManZhao.algorithmStar.operands.coordinateNet;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.integrator.Route2DDrawingIntegrator;
import io.github.beardedManZhao.algorithmStar.integrator.launcher.Route2DDrawingLauncher2;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;

import java.awt.*;
import java.util.*;

/**
 * Java类于 2022/10/16 17:44:21 创建
 * <p>
 * double二维坐标路线网，其中的所有路线网皆是2D的，该类已经实现了2D绘图的启动器，您可以将该类直接提供给Route2DDrawingIntegrator进行计算绘图。
 * <p>
 * double two-dimensional coordinate route network, all route networks are 2D, this class has implemented a 2D drawing starter, you can directly provide this class to the Route 2 D Drawing integrator for computational drawing.
 *
 * @author zhao
 * <p>
 * 底层是使用double存储数据的，性能略差，但是精度较高。
 * <p>
 * The bottom layer uses double to store data, the performance is slightly worse, but the precision is higher.
 */
public class DoubleRoute2DNet implements RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D>, Route2DDrawingLauncher2 {
    private final HashMap<String, DoubleConsanguinityRoute2D> doubleConsanguinityRoute2DHashMap = new HashMap<>(0b1010);
    private final HashMap<String, DoubleConsanguinityRoute2D> doubleConsanguinityRoute2DHashMap_SubMark = new HashMap<>(0b1010);
    private final HashMap<String, DoubleConsanguinityRoute2D> doubleConsanguinityRoute2DHashMap_MasterTag = new HashMap<>();
    private Color MasterTagColor = new Color(0xC97CFD);
    private Color SubMarkColor = new Color(0xE8CF67);

    /**
     * 构造出来一张线路网
     * <p>
     * construct a network of lines
     *
     * @param route2DCollection 这张网中的所有线路
     *                          <p>
     *                          All lines in this net
     */
    private DoubleRoute2DNet(Collection<DoubleConsanguinityRoute2D> route2DCollection) {
        for (DoubleConsanguinityRoute2D DoubleConsanguinityRoute2D : route2DCollection) {
            addRoute(DoubleConsanguinityRoute2D);
        }
    }

    private DoubleRoute2DNet(Map<String, DoubleConsanguinityRoute2D> map) {
        this.doubleConsanguinityRoute2DHashMap.putAll(map);
    }

    /**
     * 将一个整形的二维线路网构建出来。
     * <p>
     * A shaped two-dimensional line network is constructed.
     *
     * @param route2DCollection 整个网络中所有的线路对象。
     *                          <p>
     *                          All line objects in the entire network.
     * @return 整形的二维线路网
     */
    public static DoubleRoute2DNet parse(Collection<DoubleConsanguinityRoute2D> route2DCollection) {
        return new DoubleRoute2DNet(route2DCollection);
    }

    /**
     * @param doubleConsanguinityRoute2DS 整个网络中所有的线路对象。
     *                                    <p>
     *                                    All line objects in the entire network.
     * @return Shaped 2D line network
     */
    public static DoubleRoute2DNet parse(DoubleConsanguinityRoute2D... doubleConsanguinityRoute2DS) {
        return new DoubleRoute2DNet(Arrays.asList(doubleConsanguinityRoute2DS));
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
        return this.doubleConsanguinityRoute2DHashMap.containsKey(RouteName) || this.doubleConsanguinityRoute2DHashMap_SubMark.containsKey(RouteName) || this.doubleConsanguinityRoute2DHashMap_MasterTag.containsKey(RouteName);
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @deprecated 将两个网络中的每一个线路进行求和（不保证线路之间的顺序）
     * <p>
     * Sums each line in both networks (order between lines is not guaranteed)
     */
    @Override
    public RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> add(RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> value) {
        final int size1 = this.getRouteCount();
        final int size2 = value.getRouteCount();
        if (size1 == size2) {
            final Iterator<DoubleConsanguinityRoute2D> iterator1 = this.doubleConsanguinityRoute2DHashMap.values().iterator();
            final Iterator<DoubleConsanguinityRoute2D> iterator2 = value.getNetDataSet().iterator();
            final ArrayList<DoubleConsanguinityRoute2D> res = new ArrayList<>();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                res.add(iterator1.next().add(iterator2.next()));
            }
            return DoubleRoute2DNet.parse(res);
        } else {
            throw new OperatorOperationException("在 'IntegerRoute2DNet1 add IntegerRoute2DNet2' 的时候出现了错误，两个线路网中的线路数量不同，因此无法求和。\n" +
                    "There was an error when 'IntegerRoute2DNet1 add IntegerRoute2DNet2', the number of routes in the two route nets is different, so the sum cannot be done. \n" +
                    "Number of lines =>  IntegerRoute2DNet1[" + size1 + "]  IntegerRoute2DNet2[" + size2 + "]");
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * @deprecated 将两个网络中的每一个线路进行做差（不保证线路之间的顺序）
     * <p>
     * Diff each line in both networks (order between lines is not guaranteed)
     */
    @Override
    public RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> diff(RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> value) {
        final int size1 = this.getRouteCount();
        final int size2 = value.getRouteCount();
        if (size1 == size2) {
            final Iterator<DoubleConsanguinityRoute2D> iterator1 = this.doubleConsanguinityRoute2DHashMap.values().iterator();
            final Iterator<DoubleConsanguinityRoute2D> iterator2 = value.getNetDataSet().iterator();
            final ArrayList<DoubleConsanguinityRoute2D> res = new ArrayList<>();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                res.add(iterator1.next().diff(iterator2.next()));
            }
            return DoubleRoute2DNet.parse(res);
        } else {
            throw new OperatorOperationException("在 'IntegerRoute2DNet1 add IntegerRoute2DNet2' 的时候出现了错误，两个线路网中的线路数量不同，因此无法求和。\n" +
                    "There was an error when 'IntegerRoute2DNet1 add IntegerRoute2DNet2', the number of routes in the two route nets is different, so the sum cannot be done. \n" +
                    "Number of lines =>  IntegerRoute2DNet1[" + size1 + "]  IntegerRoute2DNet2[" + size2 + "]");
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
    public RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> add(Number value) {
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
    public RouteNet<DoubleCoordinateTwo, DoubleConsanguinityRoute2D> diff(Number value) {
        throw NOT_SUP;
    }

    public Color getMasterTagColor() {
        return MasterTagColor;
    }

    public void setMasterTagColor(Color masterTagColor) {
        this.MasterTagColor = masterTagColor;
    }

    public Color getSubMarkColor() {
        return SubMarkColor;
    }

    public void setSubMarkColor(Color subMarkColor) {
        SubMarkColor = subMarkColor;
    }

    /**
     * 添加一条被标记的路线对象,该类实现了绘图集成器的第二般接口,因此该方法拓展出来了一个新功能就是标记路线,您可以在这个方法中添加标记路线.
     * <p>
     * Add a marked route object, this class implements the second general interface of the drawing integrator, so this method extends a new function to mark the route, you can add the marked route in this method.
     *
     * @param doubleConsanguinityRoute2D 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                   A marked 2D route object that can be drawn in a special color in the plotter!
     *                                   <p>
     *                                   有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                   <p>
     *                                   For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addSubMarkRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        String s = doubleConsanguinityRoute2D.getRouteName();
        this.doubleConsanguinityRoute2DHashMap_SubMark.put(s, doubleConsanguinityRoute2D);
        this.doubleConsanguinityRoute2DHashMap.remove(s);
        this.doubleConsanguinityRoute2DHashMap_MasterTag.remove(s);
    }

    /**
     * 添加一条被标记的路线对象,该类实现了绘图集成器的第二般接口,因此该方法拓展出来了一个新功能就是标记路线,您可以在这个方法中添加标记路线.
     * <p>
     * Add a marked route object, this class implements the second general interface of the drawing integrator, so this method extends a new function to mark the route, you can add the marked route in this method.
     *
     * @param doubleConsanguinityRoute2D 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                   A marked 2D route object that can be drawn in a special color in the plotter!
     *                                   <p>
     *                                   有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                   <p>
     *                                   For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addMasterTagRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        String s = doubleConsanguinityRoute2D.getRouteName();
        this.doubleConsanguinityRoute2DHashMap_MasterTag.put(s, doubleConsanguinityRoute2D);
        this.doubleConsanguinityRoute2DHashMap_SubMark.remove(s);
        this.doubleConsanguinityRoute2DHashMap.remove(s);
    }

    /**
     * @return 路线网络数据集，其中每一个元素都是一条路线
     */
    @Override
    public HashSet<DoubleConsanguinityRoute2D> getNetDataSet() {
        ArrayList<DoubleConsanguinityRoute2D> values = new ArrayList<>(this.doubleConsanguinityRoute2DHashMap.values());
        values.addAll(this.doubleConsanguinityRoute2DHashMap_MasterTag.values());
        values.addAll(this.doubleConsanguinityRoute2DHashMap_SubMark.values());
        return new HashSet<>(values);
    }

    /**
     * @param doubleConsanguinityRoute2D 需要被添加的网络数据集
     * @return 是否添加成功
     */
    @Override
    public boolean addRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        this.doubleConsanguinityRoute2DHashMap.put(doubleConsanguinityRoute2D.getRouteName(), doubleConsanguinityRoute2D);
        return true;
    }

    @Override
    public int getRouteCount() {
        return this.doubleConsanguinityRoute2DHashMap.size() + this.doubleConsanguinityRoute2DHashMap_SubMark.size() + this.doubleConsanguinityRoute2DHashMap_MasterTag.size();
    }

    /**
     * @return 启动器的子类实现，用来显示转换到子类，一般只需要在这里进行一个this的返回即可
     * <p>
     * The subclass implementation of the launcher is used to display the conversion to the subclass. Generally, only a "this" return is required here.
     */
    @Override
    public DoubleRoute2DNet expand() {
        return this;
    }

    /**
     * 获取到图像数据集,如果您的生成算法支持图像生成,那么就请在这里设置对应的图像数据集
     * <p>
     * Get the image dataset. If your generation algorithm supports image generation, please set the corresponding image dataset here.
     *
     * @return 图像数据集  image dataset
     */
    @Override
    public HashMap<String, IntegerConsanguinityRoute2D> AcquireImageDataSet() {
        HashMap<String, IntegerConsanguinityRoute2D> hashMap = new HashMap<>(this.doubleConsanguinityRoute2DHashMap.size());
        for (String s : this.doubleConsanguinityRoute2DHashMap.keySet()) {
            hashMap.put(s, ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(this.doubleConsanguinityRoute2DHashMap.get(s)));
        }
        return hashMap;
    }

    /**
     * 如果您在这里设置支持绘图,那么请您在"AcquireImageDataSet"中进行数据集的返回,如果您设置的是false,那么您的"AcquireImageDataSet"只需要返回一个null即可.
     * <p>
     * If you set support for drawing here, please return the data set in "Acquire Image Data Set", if you set it to false, then your "Acquire Image Data Set" only needs to return a null.
     *
     * @return 您的算法是否支持或者允许绘图!
     * <p>
     * Does your algorithm support or allow drawing!
     */
    @Override
    public boolean isSupportDrawing() {
        return true;
    }

    /**
     * 附加任务函数执行题,为了弥补绘图器不够灵活的缺陷,2022年10月16日新增了一个附加任务接口,该接口将会在旧接口的任务执行之前调用
     * <p>
     * Additional task function execution question, in order to make up for the inflexibility of the plotter, an additional task interface was added on October 16, 2022, which will be called before the task execution of the old interface
     *
     * @param graphics2D               绘图时的画笔对象,由绘图器传递,您可以在这里准备绘图器的更多设置与操作.
     * @param route2DDrawingIntegrator 绘图集成器对象,您可以再附加任务中对集成器进行灵活操作!
     *                                 <p>
     *                                 Drawing integrator object, you can flexibly operate the integrator in additional tasks!
     *                                 <p>
     *                                 第二版启动器接口中的特有函数, 允许用户在实现2维绘图接口的时候获取到绘图笔对象, 用户将此接口当作父类去使用, 绘图器会自动分析您的接口版本.
     *                                 <p>
     *                                 The unique function in the second version of the launcher interface allows the user to obtain the drawing pen object when implementing the 2D drawing interface. The user uses this interface as a parent class, and the drawer will automatically analyze your interface version.
     */
    @Override
    public void AdditionalTasks1(Graphics2D graphics2D, Route2DDrawingIntegrator route2DDrawingIntegrator) {
    }

    /**
     * 附加任务函数执行题,为了弥补绘图器不够灵活的缺陷,2022年10月16日新增了一个附加任务接口,该接口将会在旧接口的任务执行完调用.
     * <p>
     * Additional task function execution question, in order to make up for the inflexibility of the plotter, an additional task interface was added on October 16, 2022, which will be called after the task execution of the old interface.
     *
     * @param graphics2D               绘图时的画笔对象,由绘图器传递,您可以在这里准备绘图器的更多设置与操作.
     * @param route2DDrawingIntegrator 绘图集成器对象,您可以再附加任务中对集成器进行灵活操作!
     *                                 <p>
     *                                 Drawing integrator object, you can flexibly operate the integrator in additional tasks!
     *                                 <p>
     *                                 第二版启动器接口中的特有函数, 允许用户在实现2维绘图接口的时候获取到绘图笔对象, 用户将此接口当作父类去使用, 绘图器会自动分析您的接口版本.
     *                                 <p>
     *                                 The unique function in the second version of the launcher interface allows the user to obtain the drawing pen object when implementing the 2D drawing interface. The user uses this interface as a parent class, and the drawer will automatically analyze your interface version.
     */
    @Override
    public void AdditionalTasks2(Graphics2D graphics2D, Route2DDrawingIntegrator route2DDrawingIntegrator) {
        Color color = graphics2D.getColor();
        graphics2D.setColor(this.SubMarkColor);
        for (DoubleConsanguinityRoute2D value : this.doubleConsanguinityRoute2DHashMap_SubMark.values()) {
            IntegerConsanguinityRoute2D integerConsanguinityRoute2D = ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(value);
            route2DDrawingIntegrator.drawARoute(
                    graphics2D, value.getStartingCoordinateName(), value.getEndPointCoordinateName()
                    , integerConsanguinityRoute2D.getStartingCoordinate(), integerConsanguinityRoute2D.getEndPointCoordinate()
            );
        }
        graphics2D.setColor(this.MasterTagColor);
        for (DoubleConsanguinityRoute2D value : this.doubleConsanguinityRoute2DHashMap_MasterTag.values()) {
            IntegerConsanguinityRoute2D integerConsanguinityRoute2D = ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(value);
            route2DDrawingIntegrator.drawARoute(
                    graphics2D, value.getStartingCoordinateName(), value.getEndPointCoordinateName()
                    , integerConsanguinityRoute2D.getStartingCoordinate(), integerConsanguinityRoute2D.getEndPointCoordinate()
            );
        }
        graphics2D.setColor(color);
    }

    public HashMap<String, DoubleConsanguinityRoute2D> getDoubleConsanguinityRoute2DHashMap_SubMark() {
        return doubleConsanguinityRoute2DHashMap_SubMark;
    }

    public HashMap<String, DoubleConsanguinityRoute2D> getDoubleConsanguinityRoute2DHashMap_MasterTag() {
        return doubleConsanguinityRoute2DHashMap_MasterTag;
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
    public DoubleConsanguinityRoute2D getRouteFromHashMap(String RouteName) {
        return this.doubleConsanguinityRoute2DHashMap.get(RouteName);
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
    public DoubleConsanguinityRoute2D getRouteFromSubMark(String RouteName) {
        return this.doubleConsanguinityRoute2DHashMap_SubMark.get(RouteName);
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
    public DoubleConsanguinityRoute2D getRouteFromMasterTag(String RouteName) {
        return this.doubleConsanguinityRoute2DHashMap_MasterTag.get(RouteName);
    }

    public HashMap<String, DoubleConsanguinityRoute2D> getDoubleConsanguinityRoute2DHashMap() {
        return doubleConsanguinityRoute2DHashMap;
    }
}
