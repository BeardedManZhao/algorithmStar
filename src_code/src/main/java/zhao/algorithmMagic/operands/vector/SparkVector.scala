package zhao.algorithmMagic.operands.vector

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import zhao.algorithmMagic.exception.OperatorOperationException
import zhao.algorithmMagic.utils.{ASMath, ASStr}

class SparkVector(sparkContext: SparkContext, vector: org.apache.spark.mllib.linalg.Vector) extends Vector[SparkVector, Double] {

  private final val size: Int = vector.size

  /**
   * 计算该向量的模长，具体实现请参阅api说明
   * <p>
   * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
   *
   * @return 向量的模长
   *         waiting to be realized
   */
  override def moduleLength(): Double = {
    var res = this.vector.apply(0)
    for (i <- 1 until this.size) {
      res = res + this.vector.apply(i)
    }
    res
  }

  /**
   * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
   * <p>
   * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
   *
   * @param vector 被做乘的向量
   * @return 向量的外积
   *         waiting to be realized
   */
  override def multiply(vector: SparkVector): SparkVector = {
    val vectorArray1 = this.toArray
    val vectorArray2 = vector.toArray
    val length1 = vectorArray1.length
    val length2 = vectorArray2.length
    if (length1 == length2) SparkVector.parse(sparkContext, ASMath.CrossMultiplication(vectorArray1, vectorArray2))
    else throw new OperatorOperationException("'DoubleVector1 multiply DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" + "When 'DoubleVector1 multiply DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]")
  }

  /**
   * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
   * <p>
   * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
   *
   * @param vector 第二个被计算的向量对象
   *               <p>
   *               the second computed vector object
   * @return 两个向量的内积
   *         waiting to be realized
   */
  override def innerProduct(vector: SparkVector): Double = {
    val doubles1: Array[Double] = this.toArray
    val doubles2: Array[Double] = vector.toArray
    if (doubles1.length == doubles2.length) {
      var innerProduct: Double = 0
      for (indexNum <- doubles1.indices) {
        innerProduct += doubles1(indexNum) * doubles2(indexNum)
      }
      innerProduct
    }
    else throw new OperatorOperationException("'DoubleVector1 innerProduct DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + doubles1.length + "]，DoubleVector2=[" + doubles2.length + "]\n" + "When 'DoubleVector1 innerProduct DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + doubles1.length + "], DoubleVector2=[" + doubles2.length + "]")
  }

  /**
   * @return 该类的实现类对象，用于拓展该接口的子类
   */
  override def expand(): SparkVector = this

  /**
   * @return 是否使用基元类型，基元类型能更好地降低内存占用，如果您不使用基元，将会启动父类的数据容器
   *         <p>
   *         Whether to use primitive types, primitive types can better reduce memory usage, if you do not use primitives, the data container of the parent class will be started
   */
  override def isUsePrimitiveType: Boolean = false

  /**
   * 将两个操作数进行求和的方法，具体用法请参阅API说明。
   * <p>
   * The method for summing two operands, please refer to the API description for specific usage.
   *
   * @param value 被求和的参数  Parameters to be summed
   * @return 求和之后的数值  the value after the sum
   *         <p>
   *         There is no description for the super interface, please refer to the subclass documentation
   */
  override def add(value: SparkVector): SparkVector = {
    val numberOfDimensions1 = this.getNumberOfDimensions
    val numberOfDimensions2 = value.getNumberOfDimensions
    if (numberOfDimensions1 == numberOfDimensions2) {
      val res = new Array[Double](numberOfDimensions1)
      val doubles1 = this.toArray
      val doubles2 = value.toArray
      for (i <- 0 until numberOfDimensions1) {
        res(i) = doubles1(i) + doubles2(i)
      }
      SparkVector.parse(sparkContext, res)
    }
    else throw new OperatorOperationException("'DoubleVector1 add DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" + "When 'DoubleVector1 add DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]")
  }

  /**
   * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
   *         <p>
   *         Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
   */
  override def toArray: Array[Double] = {
    vector.toArray
  }

  /**
   * @return 向量中包含的维度数量
   *         <p>
   *         the number of dimensions contained in the vector
   */
  override def getNumberOfDimensions: Int = size

  /**
   * 在两个操作数之间做差的方法，具体用法请参阅API说明。
   * <p>
   * The method of making a difference between two operands, please refer to the API description for specific usage.
   *
   * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
   * @return 差异数值  difference value
   *         There is no description for the super interface, please refer to the subclass documentation
   */
  override def diff(value: SparkVector): SparkVector = {
    val numberOfDimensions1 = this.getNumberOfDimensions
    val numberOfDimensions2 = value.getNumberOfDimensions
    if (numberOfDimensions1 == numberOfDimensions2) {
      val res = new Array[Double](numberOfDimensions1)
      val doubles1 = this.toArray
      val doubles2 = value.toArray
      for (i <- 0 until numberOfDimensions1) {
        res(i) = doubles1(i) - doubles2(i)
      }
      SparkVector.parse(sparkContext, res)
    }
    else throw new OperatorOperationException("'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" + "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]")
  }
}

object SparkVector {
  /**
   * 将一个Spark向量获取到，通过一个文件中的向量数据构建向量集
   *
   * @param sparkContext Spark上下文对象，用于诸多的计算操作
   * @param textFilePath 包含向量数据的文件
   * @param splitRex     分割正则，在一个数据文件中有很多向量的元素数据，每一个元素之间的分分割正则表达式
   * @return SparkVector
   */
  def parse(sparkContext: SparkContext, textFilePath: String, splitRex: String): SparkVector = {
    parse(sparkContext,
      sparkContext.textFile(textFilePath)
        .flatMap(data => data.split(splitRex))
        .map(ASStr.stringToDouble)
    )
  }

  /**
   * 将一个Spark向量获取到，通过一个RDD集合构建向量集
   *
   * @param sparkContext Spark上下文对象，用于诸多的计算操作
   * @param rdd          需要被构建成为一个向量的集合，其中的每一个元素都是向量的一个维度元素
   * @return SparkVector
   */
  def parse(sparkContext: SparkContext, rdd: RDD[Double]): SparkVector = {
    new SparkVector(sparkContext, org.apache.spark.mllib.linalg.Vectors.dense(rdd.collect()))
  }

  /**
   * 将一个Spark向量获取到，通过一个double的数组构建向量集
   *
   * @param sparkContext spark上下文对象，用于诸多的计算操作
   * @param doubles      需要被构建成为一个向量的集合，其中的每一个元素都是向量的一个维度元素
   * @return SparkVector
   */
  def parse(sparkContext: SparkContext, doubles: Array[Double]): SparkVector = {
    new SparkVector(sparkContext, org.apache.spark.mllib.linalg.Vectors.dense(doubles))
  }
}