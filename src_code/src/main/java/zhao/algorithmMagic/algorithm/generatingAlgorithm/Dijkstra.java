package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.DistanceAlgorithm;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.EuclideanMetric;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRoute2DNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.NameRoute;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.DependentAlgorithmNameLibrary;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Java类于 2022/10/17 08:07:54 创建
 * <p>
 * 无方向的测地算法，用来计算某一个点与其周围点之间的最短距离，在该算法中是无方向的计算，如果您需要有方向计算，请使用该类的子类“DirectionalDijkstra”。
 * <p>
 * Undirected geodesic algorithm is used to calculate the shortest distance between a point and its surrounding points. In this algorithm, it is an undirected calculation. If you need a directional calculation, please use the subclass "Directional Dijkstra" of this class. .
 *
 * @param <RouteType>      该算法中能够计算的坐标网络类型
 * @param <CoordinateType> 该算法中能够计算的坐标数据类型
 * @author zhao
 */
public class Dijkstra<RouteType extends NameRoute<?, ?>, CoordinateType extends Coordinate<?>> implements GeneratingAlgorithm {

    protected final Logger logger;
    protected final String AlgorithmName;
    // 线路记载容器
    protected final HashMap<String, DoubleConsanguinityRoute2D> doubleConsanguinityRoute2DHashMap = new HashMap<>();
    // 相对关系记载容器，key代表一个坐标，value代表与该坐标相连接的所有坐标名称以及两坐标之间的距离
    protected final LinkedHashMap<String, HashMap<String, Double>> hashMap = new LinkedHashMap<>();
    protected DistanceAlgorithm distanceAlgorithm = EuclideanMetric.getInstance(DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC_NAME);

    protected Dijkstra() {
        this.AlgorithmName = "CosineDistance";
        this.logger = Logger.getLogger("CosineDistance");
    }

    protected Dijkstra(String AlgorithmName) {
        this.logger = Logger.getLogger(AlgorithmName);
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static <RouteType extends NameRoute<?, ?>, CoordinateType extends Coordinate<?>> Dijkstra<RouteType, CoordinateType> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof Dijkstra<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 Dijkstra 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the Dijkstra type. Please redefine a name for this algorithm.");
            }
        } else {
            Dijkstra<RouteType, CoordinateType> zhaoCoordinateNet = new Dijkstra<>(Name);
            OperationAlgorithmManager.getInstance().register(zhaoCoordinateNet);
            return zhaoCoordinateNet;
        }
    }

    public DistanceAlgorithm getDistanceAlgorithm() {
        return distanceAlgorithm;
    }

    public Dijkstra<RouteType, CoordinateType> setDistanceAlgorithm(DistanceAlgorithm distanceAlgorithm) {
        this.distanceAlgorithm = distanceAlgorithm;
        return this;
    }

    public Dijkstra<RouteType, CoordinateType> setDistanceAlgorithm(String distanceAlgorithmName) {
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(distanceAlgorithmName);
        if (operationAlgorithm instanceof DistanceAlgorithm) {
            return this.setDistanceAlgorithm((DistanceAlgorithm) operationAlgorithm);
        } else {
            throw new TargetNotRealizedException("您需要的[" + distanceAlgorithmName + "]算法被找到了，但是它不属于DistanceAlgorithm的子类，无法用于两坐标之间的距离计算!\n" +
                    "您需要的[" + distanceAlgorithmName + "]算法被找到了，但是它不属于DistanceAlgorithm的子类，无法用于两坐标之间的距离计算!\n");
        }
    }

    /**
     * 添加很多路线到计算网络中
     *
     * @param routes 需要被计算的所有路线
     */
    public void addRoutes(DoubleConsanguinityRoute2D... routes) {
        for (DoubleConsanguinityRoute2D route : routes) {
            addRoute(route);
        }
    }

    /**
     * 添加一个路线到计算网络中
     *
     * @param route 需要被计算的一个路线，请注意，在无方向的测地中，您添加的线路方向将不再生效！而是在两者之间连接一条线路。
     */
    public void addRoute(DoubleConsanguinityRoute2D route) {
        // 获取该线路的起始点名称
        String startingCoordinateName = route.getStartingCoordinateName();
        // 获取该线路的终止点名称
        String endPointCoordinateName = route.getEndPointCoordinateName();
        // 获取起始点到终止点的名称
        String SEName = startingCoordinateName + " -> " + endPointCoordinateName;
        // 获取终止点到起始点的名称
        String ESName = endPointCoordinateName + " -> " + startingCoordinateName;
        // 计算该线路的始末坐标距离
        double trueDistance = this.distanceAlgorithm.getTrueDistance(route.toDoubleVector());
        logger.info("Insert " + SEName + " AND " + ESName + "=> " + trueDistance);
        // 第一轮分配起始点的周边
        extracted(startingCoordinateName, endPointCoordinateName, trueDistance);
        // 第二轮分配终止点的周边，因为该起始点属于终止点的周边
        extracted(endPointCoordinateName, startingCoordinateName, trueDistance);
        this.doubleConsanguinityRoute2DHashMap.put(SEName, route);
        this.doubleConsanguinityRoute2DHashMap.put(ESName, route);
    }

    /**
     * 将终止点添加到起始点的周边集合中，在线路网对这两点进行一个连接。
     * <p>
     * Add the end point to the perimeter set of the start point, and make a connection between the two points in the line network.
     *
     * @param startingCoordinateName 起始点名称
     * @param endPointCoordinateName 终止点名称
     * @param trueDistance           两点之间的距离
     * @apiNote 会自动的在线路集合中查找两点之间的线路对象，并建立联系！
     * <p>
     * It will automatically find the line object between two points in the line collection and establish a connection!
     */
    protected void extracted(String startingCoordinateName, String endPointCoordinateName, double trueDistance) {
        HashMap<String, Double> startDoubleHashMap = this.hashMap.get(startingCoordinateName);
        if (startDoubleHashMap != null) {
            startDoubleHashMap.put(endPointCoordinateName, trueDistance);
        } else {
            HashMap<String, Double> hashMap1 = new HashMap<>();
            hashMap1.put(endPointCoordinateName, trueDistance);
            this.hashMap.put(startingCoordinateName, hashMap1);
        }
    }

    /**
     * 获取到以"CentralCoordinateName"为中心，与其相邻所有点与它的最短路径
     * <p>
     * is the center, and all points adjacent to it and its shortest path
     *
     * @param CentralCoordinateName 中心计算点
     *                              <p>
     *                              center calculation point
     * @return 这是一个标记好的线路，所有的相邻线路将被标记为副标签，最近的路线将会被标记为主标签，其余路线将被标记为普通路线。
     * <p>
     * This is a marked route, all adjacent routes will be marked as secondary labels, the nearest route will be marked as primary label, and the remaining routes will be marked as normal routes.
     */
    public DoubleRoute2DNet getShortestPath(String CentralCoordinateName) {
        DoubleRoute2DNet doubleRoute2DNet = DoubleRoute2DNet.parse();
        // 先将所有的线路添加到网络中
        for (DoubleConsanguinityRoute2D value : this.doubleConsanguinityRoute2DHashMap.values()) {
            doubleRoute2DNet.addRoute(value);
        }
        HashMap<String, Double> hashMap = this.hashMap.get(CentralCoordinateName);
        if (hashMap != null) {
            // 这里就是与中心点所有直接连接的坐标，在这里将他们添加到线路网中, 同时进行最小距离的筛选
            double dis = Integer.MAX_VALUE;
            for (String end : hashMap.keySet()) {
                String routeName = CentralCoordinateName + " -> " + end;
                // 将所有路线添加到线路网的副标记中
                doubleRoute2DNet.addSubMarkRoute(this.doubleConsanguinityRoute2DHashMap.get(routeName));
                // 将当前路线的距离获取到
                Double aDouble = hashMap.get(end);
                // 进行比较,最后会晒选出来最小的距离
                if (aDouble < dis) {
                    dis = aDouble;
                }
            }
            // 找到最小距离对应的结束点是谁, 如果找到了就将该路线添加到网的主标记中，并将线路返回出去
            for (String end : hashMap.keySet()) {
                if (hashMap.get(end) == dis) {
                    doubleRoute2DNet.addMasterTagRoute(doubleConsanguinityRoute2DHashMap.get(CentralCoordinateName + " -> " + end));
                    return doubleRoute2DNet;
                }
            }
            return doubleRoute2DNet;
        } else {
            throw new OperatorOperationException("无法获取到以[" + CentralCoordinateName + "]坐标为中心的空间，该坐标似乎并没有以合适的条件存在于您提供的所有线路中！！！\n" +
                    "Could not get the space centered on the [" + CentralCoordinateName + "] coordinate, That coordinate doesn't seem to exist in the right conditions for all the lines you've provided! ! !");
        }
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    @Override
    public boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        this.hashMap.clear();
        this.doubleConsanguinityRoute2DHashMap.clear();
    }
}
