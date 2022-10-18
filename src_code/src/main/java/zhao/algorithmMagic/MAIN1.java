package zhao.algorithmMagic;

import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet2D;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 构建人员坐标(二维)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);
        DoubleCoordinateTwo E = new DoubleCoordinateTwo(6, 1);
        DoubleCoordinateTwo Z = new DoubleCoordinateTwo(1, 21);

        // 获取关系网络,该算法是我实现出来,用于推断人员关系网的,这里的名称您可以自定义,需要注意的是下面集成器的实例化需要您将该名称传进去
        ZhaoCoordinateNet2D zhaoCoordinateNet = ZhaoCoordinateNet2D.getInstance("Z");
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> B", A, B)); // Representing A takes the initiative to know B
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> C", A, C));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("E -> Z", E, Z));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("A -> Z", A, Z));
        zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute2D.parse("B -> Z", B, Z));

        // 使用2维路线绘制集成器,输出上面所有人员之间的关系网络图片
        Route2DDrawingIntegrator a = new Route2DDrawingIntegrator("A", "Z");
        // 设置图片输出路径
        a.setImageOutPath("D:\\out\\image.jpg")
                // 设置图片宽度
                .setImageWidth(1000)
                // 设置图片高度
                .setImageHeight(1000)
                // 设置离散阈值,用来放大微小的变化
                .setDiscreteThreshold(4)
                // 运行集成器!
                .run();

        // 清理关系网络中的数据
        zhaoCoordinateNet.clear();
    }
}