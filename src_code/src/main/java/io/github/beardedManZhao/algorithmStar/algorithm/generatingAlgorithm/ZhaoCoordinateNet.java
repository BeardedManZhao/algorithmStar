package io.github.beardedManZhao.algorithmStar.algorithm.generatingAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.route.DoubleConsanguinityRoute;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Java类于 2022/10/15 09:31:48 创建
 * <p>
 * 坐标网生成算法,是我自己的实现,该算法会将所有有关联的坐标进行连接,快速生成一张网,非常适用于人与人之间的关系推导.
 * <p>
 * The coordinate network generation algorithm is my own implementation. This algorithm will connect all related coordinates to quickly generate a network。
 *
 * @author zhao
 */
public class ZhaoCoordinateNet implements GeneratingAlgorithm {

    protected final String AlgorithmName;
    private final HashMap<String, DoubleConsanguinityRoute> stringDoubleConsanguinityCoordinateHashMap = new HashMap<>();

    protected ZhaoCoordinateNet() {
        this.AlgorithmName = "ZhaoCoordinateNet";
    }

    protected ZhaoCoordinateNet(String AlgorithmName) {
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
    public static ZhaoCoordinateNet getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ZhaoCoordinateNet) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 ZhaoCoordinateNet 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ZhaoCoordinateNet type. Please redefine a name for this algorithm.");
            }
        } else {
            ZhaoCoordinateNet zhaoCoordinateNet = new ZhaoCoordinateNet(Name);
            OperationAlgorithmManager.getInstance().register(zhaoCoordinateNet);
            return zhaoCoordinateNet;
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
     * 添加一条路线到坐标网中,我的算法会自动计算坐标之间的联系与依赖,构建出响应的依赖坐标网,坐标网中的关系记录由Hash存储,查询效率高,但是生成网络的速度较慢!
     * <p>
     * Add a route to the coordinate network, my algorithm will automatically calculate the relationship and dependencies between the coordinates, and build a corresponding dependent coordinate network. The relationship records in the coordinate network are stored by Hash, and the query efficiency is high, but the speed of generating the network is relatively high. slow!
     *
     * @param doubleConsanguinityRoute 血亲路线对象
     */
    public void addRoute(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        String startingCoordinateName = doubleConsanguinityRoute.getStartingCoordinateName();
        String endPointCoordinateName = doubleConsanguinityRoute.getEndPointCoordinateName();
        // 判断该路线的结束点 是否有和其它点的依赖关系,如果有就在两点血亲坐标之间建立新血亲
        List<DoubleConsanguinityRoute> start = getConsanguinity(doubleConsanguinityRoute);
        for (DoubleConsanguinityRoute consanguinityCoordinate : start) {
            String startingCoordinateName1 = consanguinityCoordinate.getStartingCoordinateName();
            if (startingCoordinateName1.equals(startingCoordinateName)) {
                // 如果是起始点有关联,就生成一个新血亲坐标,旧结束 -> 新结束
                String s = consanguinityCoordinate.getEndPointCoordinateName() + " -> " + endPointCoordinateName;
                stringDoubleConsanguinityCoordinateHashMap.put(s, DoubleConsanguinityRoute.parse(s, consanguinityCoordinate.getEndPointCoordinate(), doubleConsanguinityRoute.getEndPointCoordinate()));
            } else {
                // 如果是结束点有关联,就生成一个新血亲坐标,旧起始 -> 新起始
                String s = consanguinityCoordinate.getStartingCoordinateName() + " -> " + startingCoordinateName;
                stringDoubleConsanguinityCoordinateHashMap.put(s, DoubleConsanguinityRoute.parse(s, consanguinityCoordinate.getStartingCoordinate(), doubleConsanguinityRoute.getStartingCoordinate()));
            }
        }
        stringDoubleConsanguinityCoordinateHashMap.put(startingCoordinateName + " -> " + endPointCoordinateName, doubleConsanguinityRoute);
    }

    /**
     * @param doubleConsanguinityRoute 需要被运算的中心点
     * @return 所有和该点有关的血亲坐标
     * <p>
     * All blood relative coordinates related to this point
     */
    public List<DoubleConsanguinityRoute> getConsanguinity(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        ArrayList<DoubleConsanguinityRoute> arrayList = new ArrayList<>();
        String startingCoordinateName = doubleConsanguinityRoute.getStartingCoordinateName();
        String endPointCoordinateName = doubleConsanguinityRoute.getEndPointCoordinateName();
        for (DoubleConsanguinityRoute value : stringDoubleConsanguinityCoordinateHashMap.values()) {
            if (value.getStartingCoordinateName().equals(startingCoordinateName) || value.getEndPointCoordinateName().equals(endPointCoordinateName)) {
                arrayList.add(value);
            }
        }
        return arrayList;
    }

    /**
     * 获取到该网格中指定路径的血亲坐标对象
     *
     * @param CoordinatePath 指定的双点路径
     * @return 两点的血亲坐标, 如果没有返回null
     */
    public DoubleConsanguinityRoute get(String CoordinatePath) {
        return this.stringDoubleConsanguinityCoordinateHashMap.get(CoordinatePath);
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

    /**
     * 清理关系网络,调用此方法将可以清理一个网络中的所有坐标数据
     * <p>
     * Clean up the relational network, calling this method will clean up all coordinate data in a network
     */
    @Override
    public void clear() {
        this.stringDoubleConsanguinityCoordinateHashMap.clear();
    }
}
