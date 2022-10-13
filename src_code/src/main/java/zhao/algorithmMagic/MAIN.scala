package zhao.algorithmMagic

/**
 * ScalaObject于 2022/10/9 21:04:13 创建
 *
 * @author 4
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    // 构建两个向量
    //        val vector1 = DoubleVector.parse(Array[java.lang.Double](1, 2, 3, 4, 60))
    //        val vector2 = DoubleVector.parse(10, 21, 32, 43, 30)
    //        // 计算模长
    //        println(vector1.moduleLength(), vector2.moduleLength())
    //        // 计算求和
    //        println((vector1 add vector2).toString)
    //        // 计算求差
    //        println((vector1 diff vector2).toString)
    //        // 计算乘积（向量积）
    //        println(vector1 multiply vector2)
    //        // 计算内积（数量积）
    //        println(vector1 innerProduct vector2)
    //        // 计算相似度
    //        val distance = new CosineDistance("cos")
    //        // 向量之间夹角的余弦值，同时也可以作为一个相似度
    //        println(distance.getCos(vector1, vector2))

    // 优化之前    174 181 195
    // 优化坐标之后 188 171 171
    val ms: Long = new Date().getTime
    val two = new IntegerCoordinateMany(1, 2)
    val two1 = new IntegerCoordinateMany(1, 0)
    val two2 = new IntegerCoordinateMany(0, 0)
    val manhattanDistance: ManhattanDistance[IntegerCoordinateMany, DoubleCoordinateTwo] = ManhattanDistance.getInstance("E")
    val euclideanMetric: EuclideanMetric[IntegerCoordinateMany, DoubleCoordinateTwo] = EuclideanMetric.getInstance("E1")
    println(manhattanDistance.getTrueDistance(two, two1))
    println(manhattanDistance.getTrueDistance(two, two2))
    println(manhattanDistance.getTrueDistance(two1, two2))
    println(euclideanMetric.getTrueDistance(two, two1))
    println(euclideanMetric.getTrueDistance(two, two2))
    println(euclideanMetric.getTrueDistance(two1, two2))
    println("耗用时间：" + (new Date().getTime - ms))
  }
}
