package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.integrator.iauncher.Route2DDrawingLauncher;
import zhao.algorithmMagic.integrator.iauncher.Route2DDrawingLauncher2;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.RouteNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Java类于 2022/10/16 13:50:50 创建
 * <p>
 * 2维坐标关系网络生成算法，该算法支持在Route2DDrawingIntegrator中进行图像的绘制，因为该算法已经实现了它的启动器！
 * <p>
 * A 2D Coordinate Network Generation Algorithm that supports the drawing of images in the Route 2 D Drawing integrator because the algorithm already implements its launcher!
 *
 * @author zhao
 * <p>
 * 推荐使用IntegerConsanguinityRoute2D类型，因为在生成联系的时候，重要的不是数值，而是两者之间的关系，所以两者之间的精度不需要太高，最终考虑到性能，会使用整形进行数据保存！
 * <p>
 * It is recommended to use the Integer Consanguinity Route 2 D type, because when generating a connection, the important thing is not the value, but the relationship between the two, so the precision between the two does not need to be too high. In the end, considering the performance, an integer will be used. Carry out data saving!
 */
public class ZhaoCoordinateNet2D implements GeneratingAlgorithm2D, Route2DDrawingLauncher2 {

    protected final Logger logger;
    protected final String AlgorithmName;
    private final HashMap<String, IntegerConsanguinityRoute2D> stringDoubleConsanguinityCoordinateHashMap = new HashMap<>();
    private final HashMap<String, IntegerConsanguinityRoute2D> GenerateLineRoute2DHashMap = new HashMap<>();
    private Color AddLineColor = new Color(0xfffff);
    private Color GenerateLineColor = new Color(0xD0A708);

    protected ZhaoCoordinateNet2D() {
        this.AlgorithmName = "ZhaoCoordinateNet2D";
        this.logger = Logger.getLogger("ZhaoCoordinateNet2D");
    }

    protected ZhaoCoordinateNet2D(String AlgorithmName) {
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
    public static ZhaoCoordinateNet2D getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ZhaoCoordinateNet2D) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 ZhaoCoordinateNet2D 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ZhaoCoordinateNet type. Please redefine a name for this algorithm.");
            }
        } else {
            ZhaoCoordinateNet2D zhaoCoordinateNet = new ZhaoCoordinateNet2D(Name);
            OperationAlgorithmManager.getInstance().register(zhaoCoordinateNet);
            return zhaoCoordinateNet;
        }
    }

    public Color getAddLineColor() {
        return AddLineColor;
    }

    /**
     * 当您向该算法中添加了一个线路的时候，该线路会被标记到为您添加的，因此在该类被传递给绘图集成器的时候，可以有一种特有的颜色去做到这个效果！
     * <p>
     * When you add a wire to the algorithm, the wire is marked as added for you, so when the class is passed to the drawing integrator, it can have a unique color to do this!
     *
     * @param addLineColor 您添加的线路在图中的颜色
     *                     <p>
     *                     The color of the lines you added in the diagram
     * @return chain call
     */
    public ZhaoCoordinateNet2D setAddLineColor(Color addLineColor) {
        AddLineColor = addLineColor;
        return this;
    }

    public Color getGenerateLineColor() {
        return GenerateLineColor;
    }

    /**
     * 当该算法推断出来一条新的路线的时候，这个路线会被生成，如果您需要将其存放到集成器中的话，那么生成的路线将会使用特有的颜色！
     * <p>
     * When the algorithm deduces a new route, the route will be generated, and if you need to store it in the integrator, the generated route will use a unique color!
     *
     * @param generateLineColor 您添加的线路在图中的颜色
     *                          <p>
     *                          The color of the lines you added in the diagram
     * @return chain call
     */
    public ZhaoCoordinateNet2D setGenerateLineColor(Color generateLineColor) {
        GenerateLineColor = generateLineColor;
        return this;
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
     * 添加一个路线网到坐标网中，同样算法会计算坐标之间的联系与依赖
     *
     * @param routeNet         一个等待分析的坐标网络
     * @param isMultithreading 是否使用多线程的方式加载，因为分析一个坐标网络的时间可能需要很久，您可以设置为true，来让程序后台自己分析，反之则需要等待
     */
    public void addRouteNet(RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> routeNet, boolean isMultithreading) {
        if (isMultithreading) {
            logger.info("Multithreading Analyze network....");
            new Thread(() -> addRouteNet(routeNet), "ZhaoCoordinateNet2D").start();
        } else {
            logger.info("Analyzing network using single thread....");
            addRouteNet(routeNet);
        }
    }

    /**
     * 添加一个路线网到坐标网中，算法会计算坐标之间的联系与依赖，如果您需要更快的速度，您可以在形参最后加入一个true.
     * <p>
     * Add a route net to the coordinate net, the algorithm will calculate the connection and dependencies between the coordinates, if you need faster speed, you can add a true at the end of the formal parameter.
     *
     * @param routeNet 需要分析的线路网络
     *                 <p>
     *                 Line network to be analyzed
     */
    public void addRouteNet(RouteNet<IntegerCoordinateTwo, IntegerConsanguinityRoute2D> routeNet) {
        HashSet<IntegerConsanguinityRoute2D> netDataSet = routeNet.getNetDataSet();
        for (IntegerConsanguinityRoute2D integerConsanguinityRoute2D : netDataSet) {
            logger.info("Analyze relationships : " + integerConsanguinityRoute2D);
            addRoute(integerConsanguinityRoute2D);
        }
    }


    /**
     * 添加一条路线到坐标网中,我的算法会自动计算坐标之间的联系与依赖,构建出响应的依赖坐标网,坐标网中的关系记录由Hash存储,查询效率高,但是生成网络的速度较慢!
     * <p>
     * Add a route to the coordinate network, my algorithm will automatically calculate the relationship and dependencies between the coordinates, and build a corresponding dependent coordinate network. The relationship records in the coordinate network are stored by Hash, and the query efficiency is high, but the speed of generating the network is relatively high. slow!
     *
     * @param integerConsanguinityRoute2D 血亲路线对象
     */
    @Override
    public void addRoute(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        final String startingCoordinateName = integerConsanguinityRoute2D.getStartingCoordinateName();
        final String endPointCoordinateName = integerConsanguinityRoute2D.getEndPointCoordinateName();
        // 判断该路线的结束点 是否有和其它点的依赖关系,如果有就在两点血亲坐标之间建立新血亲
        List<IntegerConsanguinityRoute2D> start = getConsanguinity(integerConsanguinityRoute2D);
        for (IntegerConsanguinityRoute2D consanguinityRoute2D : start) {
            String startingCoordinateName1 = consanguinityRoute2D.getStartingCoordinateName();
            if (startingCoordinateName1.equals(startingCoordinateName)) {
                // 如果是起始点有关联,就生成一个新血亲坐标,旧结束 -> 新结束
                String s = consanguinityRoute2D.getEndPointCoordinateName() + " -> " + endPointCoordinateName;
                logger.info("Generate Coordinate Path :  " + s);
                IntegerConsanguinityRoute2D parse = IntegerConsanguinityRoute2D.parse(s, consanguinityRoute2D.getEndPointCoordinate(), integerConsanguinityRoute2D.getEndPointCoordinate());
                GenerateLineRoute2DHashMap.put(s, parse);
            } else {
                // 如果是结束点有关联,就生成一个新血亲坐标,旧起始 -> 新起始
                String s = startingCoordinateName1 + " -> " + startingCoordinateName;
                logger.info("Generate Coordinate Path :  " + s);
                IntegerConsanguinityRoute2D parse = IntegerConsanguinityRoute2D.parse(s, consanguinityRoute2D.getStartingCoordinate(), integerConsanguinityRoute2D.getStartingCoordinate());
                GenerateLineRoute2DHashMap.put(s, parse);
            }
        }
        String s = startingCoordinateName + " -> " + endPointCoordinateName;
        logger.info("Insert a Coordinate Path :  " + s);
        stringDoubleConsanguinityCoordinateHashMap.put(s, integerConsanguinityRoute2D);
    }

    /**
     * 添加一条路线到坐标网中,我的算法会自动计算坐标之间的联系与依赖,构建出响应的依赖坐标网,坐标网中的关系记录由Hash存储,查询效率高,但是生成网络的速度较慢!
     * <p>
     * Add a route to the coordinate network, my algorithm will automatically calculate the relationship and dependencies between the coordinates, and build a corresponding dependent coordinate network. The relationship records in the coordinate network are stored by Hash, and the query efficiency is high, but the speed of generating the network is relatively high. slow!
     *
     * @param doubleConsanguinityRoute2D 血亲路线对象
     */
    @Override
    public void addRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        addRoute(ASClass.DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(doubleConsanguinityRoute2D));
    }

    /**
     * @param integerConsanguinityRoute2D 需要被分析的中心路线，所有与该路线相关的其它路线将会被查找与分析！
     *                                    <p>
     *                                    The central route that needs to be analyzed, all other routes related to this route will be searched and analyzed!
     * @return 所有和该点有关的血亲坐标
     * <p>
     * All blood relative coordinates related to this point
     */
    public List<IntegerConsanguinityRoute2D> getConsanguinity(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        ArrayList<IntegerConsanguinityRoute2D> arrayList = new ArrayList<>();
        for (IntegerConsanguinityRoute2D value : stringDoubleConsanguinityCoordinateHashMap.values()) {
            if (value.getStartingCoordinateName().equals(integerConsanguinityRoute2D.getStartingCoordinateName()) || value.getEndPointCoordinateName().equals(integerConsanguinityRoute2D.getEndPointCoordinateName())) {
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
    public IntegerConsanguinityRoute2D get(String CoordinatePath) {
        IntegerConsanguinityRoute2D integerConsanguinityRoute2D = this.stringDoubleConsanguinityCoordinateHashMap.get(CoordinatePath);
        if (integerConsanguinityRoute2D != null) {
            return integerConsanguinityRoute2D;
        } else {
            return this.GenerateLineRoute2DHashMap.get(CoordinatePath);
        }
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
        this.GenerateLineRoute2DHashMap.clear();
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
        graphics2D.setColor(this.GenerateLineColor);
        for (IntegerConsanguinityRoute2D value : this.GenerateLineRoute2DHashMap.values()) {
            route2DDrawingIntegrator.drawARoute(graphics2D, value.getStartingCoordinateName(), value.getEndPointCoordinateName(), value.getStartingCoordinate(), value.getEndPointCoordinate());
        }
        graphics2D.setColor(this.AddLineColor);
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
}
