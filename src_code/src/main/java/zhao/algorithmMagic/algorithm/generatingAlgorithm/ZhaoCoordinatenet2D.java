package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.Integrator.launcher.Route2DDrawingLauncher;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Java类于 2022/10/16 13:50:50 创建
 * <p>
 * 2维坐标关系网络生成算法，该算法支持在Route2DDrawingIntegrator中进行图像的绘制，因为该算法已经实现了它的启动器！
 * <p>
 * A 2D Coordinate Network Generation Algorithm that supports the drawing of images in the Route 2 D Drawing Integrator because the algorithm already implements its launcher!
 *
 * @author zhao
 */
public class ZhaoCoordinatenet2D implements GeneratingAlgorithm, Route2DDrawingLauncher {

    protected final Logger logger;
    protected final String AlgorithmName;
    private final HashMap<String, DoubleConsanguinityRoute2D> stringDoubleConsanguinityCoordinateHashMap = new HashMap<>();

    protected ZhaoCoordinatenet2D() {
        this.AlgorithmName = "CosineDistance";
        this.logger = Logger.getLogger("CosineDistance");
    }

    protected ZhaoCoordinatenet2D(String AlgorithmName) {
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
    public static ZhaoCoordinatenet2D getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ZhaoCoordinatenet2D) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 ZhaoCoordinateNet2D 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ZhaoCoordinateNet type. Please redefine a name for this algorithm.");
            }
        } else {
            ZhaoCoordinatenet2D zhaoCoordinateNet = new ZhaoCoordinatenet2D(Name);
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
     * @param doubleConsanguinityRoute2D 血亲路线对象
     */
    public void addRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        String startingCoordinateName = doubleConsanguinityRoute2D.getStartingCoordinateName();
        String endPointCoordinateName = doubleConsanguinityRoute2D.getEndPointCoordinateName();
        // 判断该路线的结束点 是否有和其它点的依赖关系,如果有就在两点血亲坐标之间建立新血亲
        List<DoubleConsanguinityRoute2D> start = getConsanguinity(doubleConsanguinityRoute2D);
        for (DoubleConsanguinityRoute2D consanguinityRoute2D : start) {
            String startingCoordinateName1 = consanguinityRoute2D.getStartingCoordinateName();
            if (startingCoordinateName1.equals(startingCoordinateName)) {
                // 如果是起始点有关联,就生成一个新血亲坐标,旧结束 -> 新结束
                String s = consanguinityRoute2D.getEndPointCoordinateName() + " -> " + endPointCoordinateName;
                logger.info("Generate Coordinate Path :  " + s);
                stringDoubleConsanguinityCoordinateHashMap.put(s, DoubleConsanguinityRoute2D.parse(s, consanguinityRoute2D.getEndPointCoordinate(), doubleConsanguinityRoute2D.getEndPointCoordinate()));
            } else {
                // 如果是结束点有关联,就生成一个新血亲坐标,旧起始 -> 新起始
                String s = consanguinityRoute2D.getStartingCoordinateName() + " -> " + startingCoordinateName;
                logger.info("Generate Coordinate Path :  " + s);
                stringDoubleConsanguinityCoordinateHashMap.put(s, DoubleConsanguinityRoute2D.parse(s, consanguinityRoute2D.getStartingCoordinate(), doubleConsanguinityRoute2D.getStartingCoordinate()));
            }
        }
        stringDoubleConsanguinityCoordinateHashMap.put(startingCoordinateName + " -> " + endPointCoordinateName, doubleConsanguinityRoute2D);
    }

    /**
     * @return 所有和该点有关的血亲坐标
     * <p>
     * All blood relative coordinates related to this point
     */
    public List<DoubleConsanguinityRoute2D> getConsanguinity(DoubleConsanguinityRoute2D DoubleConsanguinityRoute2D2D) {
        ArrayList<DoubleConsanguinityRoute2D> arrayList = new ArrayList<>();
        String startingCoordinateName = DoubleConsanguinityRoute2D2D.getStartingCoordinateName();
        String endPointCoordinateName = DoubleConsanguinityRoute2D2D.getEndPointCoordinateName();
        for (DoubleConsanguinityRoute2D value : stringDoubleConsanguinityCoordinateHashMap.values()) {
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
    public DoubleConsanguinityRoute2D get(String CoordinatePath) {
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
    public HashMap<String, DoubleConsanguinityRoute2D> AcquireImageDataSet() {
        return this.stringDoubleConsanguinityCoordinateHashMap;
    }

    /**
     * 如果您在这里设置支持绘图,那么请您在"AcquireImageDataSet"中进行数据集的返回,如果您设置的是false,那么您的"AcquireImageDataSet"只需要返回一个null即可.
     *
     * @return 您的算法是否支持绘图!
     */
    @Override
    public boolean isSupportDrawing() {
        return true;
    }
}
