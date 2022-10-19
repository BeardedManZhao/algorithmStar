package zhao.algorithmMagic.algorithm.generatingAlgorithm;

import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

/**
 * 生成算法接口,主要就是对一个坐标网进行分析并得出需要的结果！
 * <p>
 * To generate an algorithm interface, the main thing is to analyze a coordinate network and get the required results!
 *
 * @author zhao
 */
public interface GeneratingAlgorithm2D extends GeneratingAlgorithm {
    /**
     * 添加一个需要被算法处理的线路。
     * <p>
     * Add a route that needs to be processed by the algorithm.
     *
     * @param integerConsanguinityRoute2D 需要被算法处理的线路
     */
    void addRoute(IntegerConsanguinityRoute2D integerConsanguinityRoute2D);

    /**
     * 添加一个需要被算法处理的线路。
     * <p>
     * Add a route that needs to be processed by the algorithm.
     *
     * @param doubleConsanguinityRoute2D 需要被算法处理的线路
     */
    void addRoute(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D);
}
