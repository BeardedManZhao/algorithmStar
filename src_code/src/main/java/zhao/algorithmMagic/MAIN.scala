package zhao.algorithmMagic

import org.apache.spark.{SparkConf, SparkContext}
import zhao.algorithmMagic.operands.vector.SparkVector

/**
 * 示例代码文件
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    // 获取到Spark上下文对象
    val spark: SparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("zhao").set("spark.testing.memory", "512051201024"))
    // 通过Spark上下文与向量数据集，构建一个SparkVector
    val vector = SparkVector.parse(spark, "C:\\Users\\zhao\\Desktop\\zhao.txt", "，\\s+")
    // 开始打印向量中的所有数据
    vector.toArray.foreach(println)
    println("=====")
    // 开始进行向量之间的求和
    vector.add(vector).toArray.foreach(println)
  }
}
