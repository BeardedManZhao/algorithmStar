package zhao.algorithmMagic.operands.vector

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import zhao.algorithmMagic.exception.OperatorOperationException
import zhao.algorithmMagic.utils.ASMath

/**
 * Spark向量对象，通过该类可以将Spark的API接入到本框架中，能够很好的对接到分布式内存计算技术
 *
 * Spark vector object. Through this class, you can connect Spark's API to this framework, which can be well connected to the distributed memory computing technology
 *
 * @param sparkContext Spark上下文对象
 * @param vector       Spark的vector对象
 */
final class SparkVector(sparkContext: SparkContext, vector: org.apache.spark.mllib.linalg.Vector)
  extends
    zhao.algorithmMagic.operands.vector.ThirdVector[SparkVector, Double, Array[Double], org.apache.spark.mllib.linalg.Vector] {

  private val size: Int = vector.size

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
    val vectorArray1 = this.copyToNewArray()
    val vectorArray2 = vector.copyToNewArray()
    val length1 = vectorArray1.length
    val length2 = vectorArray2.length
    if (length1 == length2) SparkVector.parse(sparkContext, ASMath.CrossMultiplication(vectorArray1, vectorArray2))
    else throw new OperatorOperationException("'DoubleVector1 multiply DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" + "When 'DoubleVector1 multiply DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]")
  }

  /**
   *
   * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
   *
   *         Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
   */
  override def copyToNewArray(): Array[Double] = vector.toArray

  /**
   * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
   * <p>
   * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
   *
   * @param v 第二个被计算的向量对象
   *          <p>
   *          the second computed vector object
   * @return 两个向量的内积
   *         waiting to be realized
   */
  override def innerProduct(v: SparkVector): Double = this.toThirdArray.dot(v.toThirdArray)

  /**
   *
   * @return 第三方向量中所维护的向量序列，通过此函数您可以直接获取到第三方库中的对象。
   *
   *         The vector sequence maintained in the third direction quantity. Through this function, you can directly obtain the objects in the third party library.
   */
  override def toThirdArray: org.apache.spark.mllib.linalg.Vector = this.vector

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
      val doubles1 = copyToNewArray()
      val doubles2 = copyToNewArray()
      for (i <- 0 until numberOfDimensions1) {
        res(i) = doubles1(i) + doubles2(i)
      }
      SparkVector.parse(sparkContext, res)
    }
    else throw new OperatorOperationException("'DoubleVector1 add DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" + "When 'DoubleVector1 add DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]")
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
      val doubles1 = this.copyToNewArray()
      val doubles2 = value.copyToNewArray()
      for (i <- 0 until numberOfDimensions1) {
        res(i) = doubles1(i) - doubles2(i)
      }
      SparkVector.parse(sparkContext, res)
    }
    else throw new OperatorOperationException("'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" + "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]")
  }

  /**
   * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
   * <p>
   * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
   *
   * @param seed 打乱算法中所需要的随机种子。
   *             <p>
   *             Disrupt random seeds required in the algorithm.
   * @return 打乱之后的对象。
   *         <p>
   *         Objects after disruption.
   */
  override def shuffle(seed: Long): SparkVector = SparkVector.parse(sparkContext, ASMath.shuffle(this.copyToNewArray(), seed, false))

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
  override def add(value: Number): SparkVector = {
    val dimensions = this.getNumberOfDimensions
    val doubles = this.copyToNewArray()
    val v = value.doubleValue()
    for (i <- 0 until dimensions) {
      doubles(i) -= v
    }
    SparkVector.parse(sparkContext, doubles)
  }

  /**
   * 在两个操作数之间做差的方法，具体用法请参阅API说明。
   * <p>
   * The method of making a difference between two operands, please refer to the API description for specific usage.
   *
   * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
   * @return 差异数值  difference value
   *         There is no description for the super interface, please refer to the subclass documentation
   */
  override def diff(value: Number): SparkVector = {
    val dimensions = this.getNumberOfDimensions
    val doubles = this.copyToNewArray()
    val v = value.doubleValue()
    for (i <- 0 until dimensions) {
      doubles(i) += v
    }
    SparkVector.parse(sparkContext, doubles)
  }

  /**
   * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
   *
   * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
   *
   * @return 当前类对应的子类实现数据类型的对象。
   *
   *         The subclass corresponding to the current class implements objects of data type.
   */
  override def expand(): SparkVector = this
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
        .map(_.toDouble)
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