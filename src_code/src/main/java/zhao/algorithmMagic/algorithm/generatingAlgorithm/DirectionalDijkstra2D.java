package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRoute2DNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;

/**
 * Java类于 2022/10/18 10:17:45 创建
 * <p>
 * 有方向的迪杰斯特拉距离，是定向迪杰斯特拉的子类实现，在这个里面，所有的路线方向都将成为计算变量之一，您可以通过setForward来设置方向的正与反。
 * <p>
 * The directional Dijkstra distance is a subclass of directional Dijkstra. In this, all the route directions will become one of the calculation variables. You can set the forward and reverse of the direction through set Forward .
 *
 * @author zhao
 */
public class DirectionalDijkstra2D extends Dijkstra2D {

    private boolean forward = true;

    public DirectionalDijkstra2D() {
    }

    public DirectionalDijkstra2D(String AlgorithmName) {
        super(AlgorithmName);
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
    public static DirectionalDijkstra2D getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof DirectionalDijkstra2D) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 DirectionalDijkstra2D 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the DirectionalDijkstra2D type. Please redefine a name for this algorithm.");
            }
        } else {
            DirectionalDijkstra2D zhaoCoordinateNet = new DirectionalDijkstra2D(Name);
            OperationAlgorithmManager.getInstance().register(zhaoCoordinateNet);
            return zhaoCoordinateNet;
        }
    }

    public boolean isForward() {
        return forward;
    }

    /**
     * 设置是否要使用正方向来进行路径的构造与查找，如果设置为true，那么在调用""的时候会从起始点开始查找终止点，反之就是从终止点开始查找起始点。
     *
     * @param forward true代表使用正方向
     * @return chain call
     */
    public DirectionalDijkstra2D setForward(boolean forward) {
        this.forward = forward;
        return this;
    }

    /**
     * 添加一个路线到计算网络中
     *
     * @param route 需要被计算的一个路线
     */
    @Override
    public void addRoute(DoubleConsanguinityRoute2D route) {
        // 获取该线路的起始点名称
        String startingCoordinateName = route.getStartingCoordinateName();
        // 获取该线路的终止点名称
        String endPointCoordinateName = route.getEndPointCoordinateName();
        // 计算该线路的始末坐标距离
        double trueDistance = this.distanceAlgorithm.getTrueDistance(route);
        if (isForward()) {
            // 获取起始点到终止点的名称
            String SEName = startingCoordinateName + " -> " + endPointCoordinateName;
            logger.info("Insert " + SEName + " => " + trueDistance);
            extracted(startingCoordinateName, endPointCoordinateName, trueDistance);
            this.doubleConsanguinityRoute2DHashMap.put(SEName, route);
        } else {
            // 获取起始点到终止点的名称
            String ESName = endPointCoordinateName + " -> " + startingCoordinateName;
            logger.info("Insert " + ESName + " => " + trueDistance);
            extracted(endPointCoordinateName, startingCoordinateName, trueDistance);
            this.doubleConsanguinityRoute2DHashMap.put(ESName, route);
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
    @Override
    public DoubleRoute2DNet getShortestPath(String CentralCoordinateName) {
        try {
            return super.getShortestPath(CentralCoordinateName);
        } catch (OperatorOperationException operatorOperationException) {
            throw new OperatorOperationException("无法获取到以[" + CentralCoordinateName + "]坐标为中心的空间，该坐标似乎并没有以" + (!forward ? "终止点" : "起始点") + "的身份存在于您提供的所有线路中！！！\n" +
                    "Could not get the space centered on the [" + CentralCoordinateName + "] coordinate, That coordinate doesn't seem to exist as " + (!forward ? "ending point" : "starting point") + " on all the lines you provide! ! !");
        }
    }
}
