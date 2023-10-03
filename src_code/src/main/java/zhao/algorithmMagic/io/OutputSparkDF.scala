package zhao.algorithmMagic.io

import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import zhao.algorithmMagic.exception.OperatorOperationException
import zhao.algorithmMagic.operands.matrix.{ColorMatrix, ColumnDoubleMatrix, ColumnIntegerMatrix}
import zhao.algorithmMagic.operands.table.DataFrame

import scala.collection.mutable
import scala.jdk.CollectionConverters.{asScalaBufferConverter, iterableAsScalaIterableConverter}

/**
 * 将 AS 的 DaraFrame 数据对象 转换成为 Spark DataFrame 对象的设备输出对象。
 *
 * @param sparkSession 输出目标对应的 spark会话对象。
 * @param table        表的名称
 * @author zhao
 */
class OutputSparkDF(sparkSession: SparkSession, table: String) extends OutputComponent {

  /**
   * 启动数据输出组件.
   * <p>
   * Start data output component.
   *
   * @return 如果启动成功返回true
   */
  override def open(): Boolean = this.isOpen

  /**
   * @return 如果组件已经启动了，在这里返回true.
   *         <p>
   *         If the component has already started, return true here
   */
  override def isOpen: Boolean = this.sparkSession != null

  /**
   * 将一份二进制数据输出。
   * <p>
   * Output a binary data.
   *
   * @param data 需要被输出的二进制数据包。
   *             <p>
   *             The binary data package that needs to be output.
   */
  override def writeByteArray(data: Array[Byte]): Unit = throw new OperatorOperationException("暂不支持字节数组对象的输出。")


  /**
   * 输出一个 整形 矩阵对象
   *
   * @param matrix 需要被输出的矩阵
   */
  override def writeMat(matrix: ColumnIntegerMatrix): Unit = throw new OperatorOperationException("暂不支持矩阵数值对象的输出。")


  /**
   * 输出一个 double类型的 矩阵对象
   *
   * @param matrix 需要被输出的矩阵
   */
  override def writeMat(matrix: ColumnDoubleMatrix): Unit = throw new OperatorOperationException("暂不支持矩阵数值对象的输出。")


  /**
   * 将图像矩阵所包含的图像直接输出到目标。
   * <p>
   * Directly output the images contained in the image matrix to the target.
   *
   * @param colorMatrix 需要被输出的图像矩阵对象。
   *                    <p>
   *                    The image matrix object that needs to be output.
   */
  override def writeImage(colorMatrix: ColorMatrix): Unit = throw new OperatorOperationException("暂不支持矩阵数值对象的输出。")

  /**
   * 将一个 DataFrame 中的数据按照数据输出组件进行输出.
   * <p>
   * Output the data in a DataFrame according to the data output component.
   *
   * @param dataFrame 需要被输出的数据对象
   */
  override def writeDataFrame(dataFrame: DataFrame): Unit = {
    val fields1 = dataFrame.getFields
    val fields2 = mutable.ListBuffer[StructField]()
    fields1.forEach(cell => fields2.append(StructField(
      cell.toString,
      if (cell.isNumber) DoubleType else StringType,
      nullable = true
    )))
    val value = sparkSession.sparkContext.makeRDD(
      dataFrame.toList.asScala
    ).map(line => Row.fromSeq(line.asScala.map(_.toString).toSeq))
    sparkSession.createDataFrame(value, StructType(fields2)).createTempView(this.table)
  }

  override def close(): Unit = {}
}

object OutputSparkDF {


  def builder() = new OutputSparkDFBuilder()

}
