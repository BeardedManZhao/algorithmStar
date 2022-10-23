package zhao.algorithmMagic.operands.coordinateNet;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.integrator.launcher.Route2DDrawingLauncher;
import zhao.algorithmMagic.integrator.launcher.Route2DDrawingLauncher2;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;

import java.awt.*;
import java.util.*;

/**
 * Java类于 2022/10/16 17:44:21 创建
 * <p>
 * 整形二维坐标路线网，其中的所有路线网皆是2D的，该类已经实现了2D绘图的启动器，您可以将该类直接提供给Route2DDrawingIntegrator进行计算绘图。
 * <p>
 * Reshape the 2D coordinate route network, in which all route networks are 2D, this class has implemented a 2D drawing starter, you can directly provide this class to the Route 2D Drawing integrator for computational drawing.
 *
 * @author zhao
 */
public class IntegerRoute2DNet implements RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D>, Route2DDrawingLauncher2 {

    private final LinkedHashMap<String, IntegerConsanguinityRoute2D> integerConsanguinityRoute2DHashMap = new LinkedHashMap<>(0b1010);
    private final HashMap<String, IntegerConsanguinityRoute2D> integerConsanguinityRoute2DHashMap_SubMark = new HashMap<>(0b1010);
    private final HashMap<String, IntegerConsanguinityRoute2D> integerConsanguinityRoute2DHashMap_MasterTag = new HashMap<>();
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
    private IntegerRoute2DNet(Collection<IntegerConsanguinityRoute2D> route2DCollection) {
        for (IntegerConsanguinityRoute2D integerConsanguinityRoute2D : route2DCollection) {
            addRoute(integerConsanguinityRoute2D);
        }
    }

    private IntegerRoute2DNet(Map<String, IntegerConsanguinityRoute2D> map) {
        this.integerConsanguinityRoute2DHashMap.putAll(map);
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
    public static IntegerRoute2DNet parse(Collection<IntegerConsanguinityRoute2D> route2DCollection) {
        LinkedHashMap<String, IntegerConsanguinityRoute2D> linkedHashMap = new LinkedHashMap<>(route2DCollection.size());
        for (IntegerConsanguinityRoute2D integerConsanguinityRoute2D : route2DCollection) {
            linkedHashMap.put(integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName(), integerConsanguinityRoute2D);
        }
        return new IntegerRoute2DNet(linkedHashMap);
    }

    /**
     * @param integerConsanguinityRoute2DS 整个网络中所有的线路对象。
     *                                     <p>
     *                                     All line objects in the entire network.
     * @return Shaped 2D line network
     */
    public static IntegerRoute2DNet parse(IntegerConsanguinityRoute2D... integerConsanguinityRoute2DS) {
        LinkedHashMap<String, IntegerConsanguinityRoute2D> linkedHashMap = new LinkedHashMap<>(integerConsanguinityRoute2DS.length);
        for (IntegerConsanguinityRoute2D integerConsanguinityRoute2D : integerConsanguinityRoute2DS) {
            linkedHashMap.put(integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName(), integerConsanguinityRoute2D);
        }
        return new IntegerRoute2DNet(linkedHashMap);
    }

    public static IntegerRoute2DNet parse(DoubleConsanguinityRoute2D... doubleConsanguinityRoute2DS) {
        LinkedHashMap<String, IntegerConsanguinityRoute2D> linkedHashMap = new LinkedHashMap<>(doubleConsanguinityRoute2DS.length);
        for (DoubleConsanguinityRoute2D doubleConsanguinityRoute2D : doubleConsanguinityRoute2DS) {
            String s = doubleConsanguinityRoute2D.getStartingCoordinateName() + " -> " + doubleConsanguinityRoute2D.getEndPointCoordinateName();
            linkedHashMap.put(s, ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(s, doubleConsanguinityRoute2D));
        }
        return new IntegerRoute2DNet(linkedHashMap);
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
     * @param integerConsanguinityRoute2D 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                    A marked 2D route object that can be drawn in a special color in the plotter!
     *                                    有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                    <p>
     *                                    For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addMasterTagRoute(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        String s = integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName();
        this.integerConsanguinityRoute2DHashMap_MasterTag.put(s, integerConsanguinityRoute2D);
        this.integerConsanguinityRoute2DHashMap_SubMark.remove(s);
        this.integerConsanguinityRoute2DHashMap.remove(s);
    }

    /**
     * 添加一条被标记的路线对象,该类实现了绘图集成器的第二般接口,因此该方法拓展出来了一个新功能就是标记路线,您可以在这个方法中添加标记路线.
     * <p>
     * Add a marked route object, this class implements the second general interface of the drawing integrator, so this method extends a new function to mark the route, you can add the marked route in this method.
     *
     * @param integerConsanguinityRoute2D 被标记的二维路线对象,该对象可以在绘图器中以特殊颜色绘制出来!
     *                                    A marked 2D route object that can be drawn in a special color in the plotter!
     *                                    有关标记颜色的设置与获取, 请您调用"setSignColor"用于设置!默认是紫色
     *                                    <p>
     *                                    For the setting and obtaining of the sign color, please call "set Sign Color" for viewing!
     */
    public void addSubMarkRoute(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        String s = integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName();
        this.integerConsanguinityRoute2DHashMap_SubMark.put(s, integerConsanguinityRoute2D);
        this.integerConsanguinityRoute2DHashMap.remove(s);
        this.integerConsanguinityRoute2DHashMap_MasterTag.remove(s);
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 将两个路线网中的所有路线按照序列索引位置进行求和，不建议使用，因为索引位置不等于图像中的坐标位置，后面会进行优化与重构
     * <p>
     * Summing all the routes in the two route networks according to the sequence index position is not recommended, because the index position is not equal to the coordinate position in the image, and optimization and reconstruction will be carried out later
     */
    @Override
    public RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> add(RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> value) {
        final int size1 = this.integerConsanguinityRoute2DHashMap.size();
        final int size2 = value.getRouteCount();
        if (size1 == size2) {
            final Iterator<IntegerConsanguinityRoute2D> iterator1 = this.integerConsanguinityRoute2DHashMap.values().iterator();
            final Iterator<IntegerConsanguinityRoute2D> iterator2 = value.getNetDataSet().iterator();
            final ArrayList<IntegerConsanguinityRoute2D> res = new ArrayList<>();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                res.add(iterator1.next().add(iterator2.next()));
            }
            return IntegerRoute2DNet.parse(res);
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
     * 将两个路线网中的所有路线按照序列索引位置进行相减，不建议使用，因为索引位置不等于图像中的坐标位置，后面会进行优化与重构
     * <p>
     * Subtract all routes in the two route networks according to the sequence index position, which is not recommended because the index position is not equal to the coordinate position in the image, and optimization and reconstruction will be carried out later
     */
    @Override
    public RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> diff(RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> value) {
        final int size1 = this.integerConsanguinityRoute2DHashMap.size();
        final int size2 = value.getRouteCount();
        if (size1 == size2) {
            final Iterator<IntegerConsanguinityRoute2D> iterator1 = this.integerConsanguinityRoute2DHashMap.values().iterator();
            final Iterator<IntegerConsanguinityRoute2D> iterator2 = value.getNetDataSet().iterator();
            final ArrayList<IntegerConsanguinityRoute2D> res = new ArrayList<>(size2);
            while (iterator1.hasNext() && iterator2.hasNext()) {
                res.add(iterator1.next().diff(iterator2.next()));
            }
            return IntegerRoute2DNet.parse(res);
        } else {
            throw new OperatorOperationException("在 'IntegerRoute2DNet1 diff IntegerRoute2DNet2' 的时候出现了错误，两个线路网中的线路数量不同，因此无法求和。\n" +
                    "There was an error when 'IntegerRoute2DNet1 diff IntegerRoute2DNet2', the number of routes in the two route nets is different, so the sum cannot be done. \n" +
                    "Number of lines =>  IntegerRoute2DNet1[" + size1 + "]  IntegerRoute2DNet2[" + size2 + "]");
        }
    }

    @Override
    public boolean containsKeyFromRoute2DHashMap(String RouteName) {
        return this.integerConsanguinityRoute2DHashMap.containsKey(RouteName) || this.integerConsanguinityRoute2DHashMap_SubMark.containsKey(RouteName) || this.integerConsanguinityRoute2DHashMap_MasterTag.containsKey(RouteName);
    }

    /**
     * @return 路线网络数据集，其中每一个元素都是一条路线
     */
    @Override
    public HashSet<IntegerConsanguinityRoute2D> getNetDataSet() {
        ArrayList<IntegerConsanguinityRoute2D> values = new ArrayList<>(this.integerConsanguinityRoute2DHashMap.values());
        values.addAll(this.integerConsanguinityRoute2DHashMap_MasterTag.values());
        values.addAll(this.integerConsanguinityRoute2DHashMap_SubMark.values());
        return new HashSet<>(values);
    }

    /**
     * @param integerConsanguinityRoute2D 需要被添加的网络数据集
     * @return 是否添加成功
     */
    @Override
    public boolean addRoute(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        this.integerConsanguinityRoute2DHashMap.put(integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName(), integerConsanguinityRoute2D);
        return true;
    }

    /**
     * @param doubleConsanguinityRoute2D 需要被添加的网络数据集
     * @return 是否添加成功
     */
    public boolean addRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        String s = doubleConsanguinityRoute2D.getStartingCoordinateName() + " -> " + doubleConsanguinityRoute2D.getEndPointCoordinateName();
        this.integerConsanguinityRoute2DHashMap.put(s, ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(s, doubleConsanguinityRoute2D));
        return true;
    }

    @Override
    public int getRouteCount() {
        return this.integerConsanguinityRoute2DHashMap.size() + this.integerConsanguinityRoute2DHashMap_SubMark.size() + this.integerConsanguinityRoute2DHashMap_MasterTag.size();
    }

    /**
     * @return 启动器的子类实现，用来显示转换到子类，一般只需要在这里进行一个this的返回即可
     * <p>
     * The subclass implementation of the launcher is used to display the conversion to the subclass. Generally, only a "this" return is required here.
     */
    @Override
    public Route2DDrawingLauncher expand() {
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
        return this.integerConsanguinityRoute2DHashMap;
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
     *                                 第二版启动器接口中的特有函数, 允许用户在实现2维绘图接口的时候获取到绘图笔对象, 用户将此接口当作父类去使用, 绘图器会自动分析您的接口版本.
     *                                 <p>
     *                                 The unique function in the second version of the launcher interface allows the user to obtain the drawing pen object when implementing the 2D drawing interface. The user uses this interface as a parent class, and the drawer will automatically analyze your interface version.
     */
    @Override
    public void AdditionalTasks2(Graphics2D graphics2D, Route2DDrawingIntegrator route2DDrawingIntegrator) {

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
    public IntegerConsanguinityRoute2D getRouteFromHashMap(String RouteName) {
        return this.integerConsanguinityRoute2DHashMap.get(RouteName);
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
    public IntegerConsanguinityRoute2D getRouteFromSubMark(String RouteName) {
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
    public IntegerConsanguinityRoute2D getRouteFromMasterTag(String RouteName) {
        return this.integerConsanguinityRoute2DHashMap_MasterTag.get(RouteName);
    }

    public LinkedHashMap<String, IntegerConsanguinityRoute2D> getIntegerConsanguinityRoute2DHashMap() {
        return integerConsanguinityRoute2DHashMap;
    }

    public HashMap<String, IntegerConsanguinityRoute2D> getIntegerConsanguinityRoute2DHashMap_SubMark() {
        return integerConsanguinityRoute2DHashMap_SubMark;
    }

    public HashMap<String, IntegerConsanguinityRoute2D> getIntegerConsanguinityRoute2DHashMap_MasterTag() {
        return integerConsanguinityRoute2DHashMap_MasterTag;
    }
}
