package zhao.algorithmMagic

import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator
import zhao.algorithmMagic.algorithm.LingYuZhaoCoordinateNet
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute

/**
 * 示例代码文件
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    // 构建人员坐标(二维)
    val A = new DoubleCoordinateMany(10, 10)
    val B = new DoubleCoordinateMany(-10, 4)
    val C = new DoubleCoordinateMany(1, 0)
    val E = new DoubleCoordinateMany(6, 1)
    val Z = new DoubleCoordinateMany(1, 21)

    // 获取关系网络,该算法是我实现出来,用于推断人员关系网的
    val lingYuZhaoCoordinateNet = LingYuZhaoCoordinateNet.getInstance("Z")
    // 将所有人员的关系添加到关系网络中
    lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> B", A, B))
    lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> C", A, C))
    lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("E -> Z", E, Z))
    lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> Z", A, Z))
    lingYuZhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("B -> Z", B, Z))

    // 使用2维路线绘制集成器,输出上面所有人员之间的关系网络图片
    val a = new Route2DDrawingIntegrator("Z", "A")
    // 设置图片输出路径
    a.setImageOutPath("D:\\out\\image.jpg").setImageWidth // 设置图片宽度
    1000.setImageHeight // 设置图片高度
    1000.setDiscreteThreshold // 设置离散阈值,用来放大微小的变化
    4.run // 运行集成器!

  }
}
