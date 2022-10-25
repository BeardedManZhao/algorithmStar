package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.IncrementalLearning;
import zhao.algorithmMagic.integrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.integrator.iauncher.IncrementalLearningLauncher;
import zhao.algorithmMagic.integrator.iauncher.Launcher;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRoute2DNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // TODO 预测一个模型
        // 配置工资和年龄的向量序列
        DoubleVector age = DoubleVector.parse(18, 19, 20, 30, 50);
        DoubleVector money = DoubleVector.parse(8000, 10000, 10000, 15000, 500);

        // 获取到集成器
        IncrementalLearning a = new IncrementalLearning("A", new IncrementalLearningLauncher() {
            @Override
            public double run(double Yn, double Xn, double $) {
                return $ * Xn;
            }

            @Override
            public Launcher<?> expand() {
                return this;
            }
        });

        // 设置集成器的θ回归区间
        a.setStartingValue(1).setTerminationValue(100);
        // 计算出来工资与年龄的关系
        System.out.println("工资 约为 年龄 * " + a.run(money, age));


        // TODO 绘制一个三角形
        // 构建三角形中的三个点
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(-3, 1);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(1, 1);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(2, 4);
        // 构建三点之间的路线
        DoubleConsanguinityRoute2D AB = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D BC = DoubleConsanguinityRoute2D.parse("B -> C", B, C);
        DoubleConsanguinityRoute2D CA = DoubleConsanguinityRoute2D.parse("C -> A", C, A);
        // 准备一个网络对象，这个网络同时还是二维绘图的启动器
        DoubleRoute2DNet parse = DoubleRoute2DNet.parse(AB, BC, CA);
        // 准备一个绘图集成器
        Route2DDrawingIntegrator r = new Route2DDrawingIntegrator("r", parse);
        // 运行集成器
        if (r.setImageHeight(500).setImageWidth(500).setDiscreteThreshold(5).setImageOutPath("D:/out/image2.jpg").run()) {
            System.out.println("ok!!!");
        } else {
            System.out.println("error");
        }
    }
}
