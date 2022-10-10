package zhao.algorithmMagic

import zhao.algorithmMagic.algorithm.EuclideanMetric
import zhao.algorithmMagic.operands.IntegerCoordinateMany

/**
 * ScalaObject于 2022/10/9 21:04:13 创建
 *
 * @author 4
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    // 构建两个向量
    //    val vector1 = DoubleVector.parse(Array[java.lang.Double](1, 2, 3, 4, 60))
    //    val vector2 = DoubleVector.parse(10, 21, 32, 43, 30)
    //    // 计算模长
    //    println(vector1.moduleLength(), vector2.moduleLength())
    //    // 计算求和
    //    println((vector1 add vector2).toString)
    //    // 计算求差
    //    println((vector1 diff vector2).toString)
    //    // 计算乘积（向量积）
    //    println(vector1 multiply vector2)
    //    // 计算内积（数量积）
    //    println(vector1 innerProduct vector2)
    //    // 计算相似度
    //    val distance = new CosineDistance("cos")
    //    // 向量之间夹角的余弦值，同时也可以作为一个相似度
    //    println(distance.getCos(vector1, vector2))

    // 构建一个坐标，计算其到原点的距离
    val two = new IntegerCoordinateMany(1, 2, 4)
    val metric = new EuclideanMetric("E")
    println(metric.getTrueDistance(two, new IntegerCoordinateMany(1, 2, 3)))
  }
}
