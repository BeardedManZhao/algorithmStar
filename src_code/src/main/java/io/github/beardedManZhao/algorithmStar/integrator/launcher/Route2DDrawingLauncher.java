package io.github.beardedManZhao.algorithmStar.integrator.launcher;

import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;

import java.util.HashMap;

/**
 * Java类于 2022/10/16 12:38:20 创建
 * <p>
 * 路线绘图 2D 集成器，实现此集成器的类，可以给予给Route2DDrawingIntegrator去进行后期处理，生成需要的图像。
 * <p>
 * The route drawing 2D integrator, the class that implements this integrator, can be given to Route2DDrawingIntegrator for post-processing to generate the required images.
 *
 * @author zhao
 */
public interface Route2DDrawingLauncher extends Launcher<Route2DDrawingLauncher> {

    /**
     * 获取到图像数据集,如果您的生成算法支持图像生成,那么就请在这里设置对应的图像数据集
     * <p>
     * Get the image dataset. If your generation algorithm supports image generation, please set the corresponding image dataset here.
     *
     * @return 图像数据集  image dataset
     */
    HashMap<String, IntegerConsanguinityRoute2D> AcquireImageDataSet();

    /**
     * 如果您在这里设置支持绘图,那么请您在"AcquireImageDataSet"中进行数据集的返回,如果您设置的是false,那么您的"AcquireImageDataSet"只需要返回一个null即可.
     * <p>
     * If you set support for drawing here, please return the data set in "Acquire Image Data Set", if you set it to false, then your "Acquire Image Data Set" only needs to return a null.
     *
     * @return 您的算法是否支持或者允许绘图!
     * <p>
     * Does your algorithm support or allow drawing!
     */
    boolean isSupportDrawing();
}
