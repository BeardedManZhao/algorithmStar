package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.ChebyshevDistance;
import zhao.algorithmMagic.operands.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.IntegerCoordinates;

/**
 * Java类于 2022/10/11 10:23:53 创建
 *
 * @author 4
 */
public class MAIN1 {
    public static void main(String[] args) throws InterruptedException {
        // 构建两个三维坐标
        DoubleCoordinateThree doubleCoordinateThree1 = new DoubleCoordinateThree(1, 1, 3);
        DoubleCoordinateThree doubleCoordinateThree2 = new DoubleCoordinateThree(1, 2, 5);
        // 计算这俩坐标的切比雪夫距离
        ChebyshevDistance<IntegerCoordinates<?>, DoubleCoordinateThree> c = ChebyshevDistance.getInstance("c");
        System.out.println("结果 => " + c.getTrueDistance(doubleCoordinateThree1, doubleCoordinateThree2));
    }
}
