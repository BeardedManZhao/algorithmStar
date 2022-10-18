package zhao.algorithmMagic.operands.coordinateNet;

import zhao.algorithmMagic.Integrator.launcher.Route2DDrawingLauncher;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;

import java.util.*;

/**
 * Java类于 2022/10/16 17:44:21 创建
 * <p>
 * 整形二维坐标路线网，其中的所有路线网皆是2D的，该类已经实现了2D绘图的启动器，您可以将该类直接提供给Route2DDrawingIntegrator进行计算绘图。
 * <p>
 * Reshape the 2D coordinate route network, in which all route networks are 2D, this class has implemented a 2D drawing starter, you can directly provide this class to the Route 2D Drawing Integrator for computational drawing.
 *
 * @author zhao
 */
public class IntegerRoute2DNet implements RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D>, Route2DDrawingLauncher {

    private final LinkedHashMap<String, IntegerConsanguinityRoute2D> integerConsanguinityRoute2DHashMap = new LinkedHashMap<>(0b1010);

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

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
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
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> diff(RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> value) {
        final int size1 = this.integerConsanguinityRoute2DHashMap.size();
        final int size2 = value.getRouteCount();
        if (size1 == size2) {
            final Iterator<IntegerConsanguinityRoute2D> iterator1 = this.integerConsanguinityRoute2DHashMap.values().iterator();
            final Iterator<IntegerConsanguinityRoute2D> iterator2 = value.getNetDataSet().iterator();
            final ArrayList<IntegerConsanguinityRoute2D> res = new ArrayList<>();
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
        return this.integerConsanguinityRoute2DHashMap.containsKey(RouteName);
    }

    /**
     * @return 路线网络数据集，其中每一个元素都是一条路线
     */
    @Override
    public HashSet<IntegerConsanguinityRoute2D> getNetDataSet() {
        return new LinkedHashSet<>(this.integerConsanguinityRoute2DHashMap.values());
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
        return this.integerConsanguinityRoute2DHashMap.size();
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
}
