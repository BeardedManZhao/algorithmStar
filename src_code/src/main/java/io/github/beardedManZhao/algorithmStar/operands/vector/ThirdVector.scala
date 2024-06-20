package io.github.beardedManZhao.algorithmStar.operands.vector

/**
 * 第三向量特质，是使得本库可以支持第三方计算组件的新方式，本特质主要作为实现类与本库的适配器。
 *
 * The third vector feature is a new way to enable the library to support third-party computing components. This feature is mainly used as an adapter between the implementation class and the library.
 *
 * @tparam ImplementationType 实现类的数据类型  The data type of the implementing class
 * @tparam ElementType        向量中的元素类型  element type in the vector
 *                            该类为抽象，其中包含最基本的定义与类型管控
 *                            <p>
 *                            This class is abstract and contains the most basic definitions and type controls.
 * @tparam ArrayType          从该实现类中拷贝出来的向量对象新数据的数据类型，是从外界获取到数据的一个方式。
 *
 *                            The data type of the vector object's new data copied from the implementation class is a way to obtain data from the outside world.
 * @tparam ThirdVector        第三方计算组件中所维护数据的数据类型，该类型是不需要拷贝的。
 *
 *                            The data type of the data maintained in the third-party computing component, which does not need to be copied.
 */
trait ThirdVector[ImplementationType, ElementType, ArrayType, ThirdVector] extends Vector[SparkVector, Double, Array[Double]] {
  /**
   *
   * @return 第三方向量中所维护的向量序列，通过此函数您可以直接获取到第三方库中的对象。
   *
   *         The vector sequence maintained in the third direction quantity. Through this function, you can directly obtain the objects in the third party library.
   */
  def toThirdArray: ThirdVector
}
