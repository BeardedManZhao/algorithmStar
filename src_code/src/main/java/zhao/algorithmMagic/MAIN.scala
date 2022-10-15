package zhao.algorithmMagic

import zhao.algorithmMagic.Integrator.Route2DDrawingIntegrator
import zhao.algorithmMagic.algorithm.generatingAlgorithm.ZhaoCoordinateNet
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

    // 获取关系网络,该算法是我实现出来,用于推断人员关系网的,这里的名称您可以自定义,需要注意的是下面集成器的实例化需要您将该名称传进去
    // 将所有人员的关系添加到关系网络中
    val zhaoCoordinateNet = ZhaoCoordinateNet.getInstance("Z")
    // 将人员的关系添加到关系网络中,请注意,该算法的关系网络已经包含了您的数据,所以您在下面集成其中一定要传入相同名称,以便集成器能获取到您算法中的临时网格数据
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> B", A, B))
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> C", A, C))
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("E -> Z", E, Z))
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("A -> Z", A, Z))
    zhaoCoordinateNet.addRoute(DoubleConsanguinityRoute.parse("B -> Z", B, Z))

    // 使用2维路线绘制集成器,输出上面所有人员之间的关系网络图片
    val r: Route2DDrawingIntegrator = new Route2DDrawingIntegrator("Z", "A")
    // 设置图片输出路径
    r.setImageOutPath("D:\\out\\image.jpg")
      .setImageWidth(1000) // 设置图片宽度
      .setImageHeight(1000) // 设置图片高度
      .setDiscreteThreshold(3) // 设置离散阈值,用来放大微小的变化
      .run // 运行集成器!

    // 清理关系网络中的数据
    zhaoCoordinateNet.clear()
  }
}
