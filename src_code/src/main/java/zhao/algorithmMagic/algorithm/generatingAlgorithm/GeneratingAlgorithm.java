package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;

import java.util.HashMap;

/**
 * 生成算法接口,一般生成算法都应该配置一个图数据返回器,可以从该方法中获取到生成的图像数据集合,能够被外界去绘制
 * <p>
 * Generation algorithm interface. Generally, the generation algorithm should be configured with a graph data returner. The generated image data set can be obtained from this method and can be drawn by the outside world.
 *
 * @author zhao
 */
public interface GeneratingAlgorithm extends OperationAlgorithm {
    /**
     * 获取到图像数据集,如果您的生成算法支持图像生成,那么就请在这里设置对应的图像数据集
     * <p>
     * Get the image dataset. If your generation algorithm supports image generation, please set the corresponding image dataset here.
     *
     * @return 图像数据集  image dataset
     */
    HashMap<String, DoubleConsanguinityRoute> AcquireImageDataSet();

    /**
     * 如果您在这里设置支持绘图,那么请您在"AcquireImageDataSet"中进行数据集的返回,如果您设置的是false,那么您的"AcquireImageDataSet"只需要返回一个null即可.
     *
     * @return 您的算法是否支持绘图!
     */
    boolean isSupportDrawing();
}
