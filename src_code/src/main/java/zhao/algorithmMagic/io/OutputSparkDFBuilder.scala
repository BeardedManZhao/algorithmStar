package zhao.algorithmMagic.io

/**
 * spark DF 对象的数据输出类。
 */
class OutputSparkDFBuilder extends OutputBuilder {
  private var sparkSession: SparkSession = _
  private var tableName = TABLE_NAME

  /**
   * 添加数据输入描述，不同的组件有不同的配置属性，具体可以参阅实现类。
   * <p>
   * Add data input descriptions, and different components have different configuration properties. Please refer to the implementation class for details.
   *
   * @param key   属性名称
   *              <p>
   *              Attribute Name.
   * @param value 属性数值
   *              <p>
   *              Attribute Value.
   * @return 链式调用，继续构建
   *         <p>
   *         Chain call, continue building.
   */
  override def addOutputArg(key: String, value: FinalCell[_]): OutputBuilder = {
    if (TABLE_NAME == key) this.tableName = value.toString
    else if (SPARK_SESSION == key) this.sparkSession = value.getValue.asInstanceOf[SparkSession]
    this
  }

  /**
   * 将所需的对象构建出来并获取到对应的输入设备对象。
   *
   * @return 输入设备对象。
   */
  override def create = new OutputSparkDF(this.sparkSession, this.tableName)
}

object OutputSparkDFBuilder {
  final val TABLE_NAME = "TBN"
  final val SPARK_SESSION = "SS"
}
