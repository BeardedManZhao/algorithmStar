package zhao.algorithmMagic.lntegrator.launcher;

import zhao.algorithmMagic.lntegrator.Route2DDrawingIntegrator;

import java.awt.*;

/**
 * Java类于 2022/10/16 12:38:20 创建
 * <p>
 * 路线绘图 2D 集成器，实现此集成器的类，可以给予给Route2DDrawingIntegrator去进行后期处理，生成需要的图像。
 * <p>
 * The route drawing 2D integrator, the class that implements this integrator, can be given to Route2DDrawingIntegrator for post-processing to generate the required images.
 *
 * @author zhao
 */
public interface Route2DDrawingLauncher2 extends Route2DDrawingLauncher {

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
    void AdditionalTasks1(Graphics2D graphics2D, Route2DDrawingIntegrator route2DDrawingIntegrator);

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
    void AdditionalTasks2(Graphics2D graphics2D, Route2DDrawingIntegrator route2DDrawingIntegrator);
}
