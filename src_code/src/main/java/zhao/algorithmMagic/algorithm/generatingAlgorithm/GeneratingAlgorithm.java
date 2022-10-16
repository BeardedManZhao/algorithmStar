package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;

/**
 * 生成算法接口,一般生成算法都应该配置一个图数据返回器,可以从该方法中获取到生成的图像数据集合,能够被外界去绘制
 * <p>
 * Generation algorithm interface. Generally, the generation algorithm should be configured with a graph data returner. The generated image data set can be obtained from this method and can be drawn by the outside world.
 *
 * @author zhao
 */
public interface GeneratingAlgorithm extends OperationAlgorithm {

    void clear();
}
