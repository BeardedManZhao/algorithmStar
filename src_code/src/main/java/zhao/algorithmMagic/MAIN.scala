package zhao.algorithmMagic

import zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance
import zhao.algorithmMagic.operands.vector.DoubleVector

/**
 * 示例代码文件
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    val cosine: CosineDistance[DoubleVector] = CosineDistance.getInstance("cos")
    println(DoubleVector.parse(8.0, 1.0).moduleLength())
    println(cosine.getTrueDistance(Array(1, 2, 3, 40), Array(1, 2, 3, 4)))
  }
}
