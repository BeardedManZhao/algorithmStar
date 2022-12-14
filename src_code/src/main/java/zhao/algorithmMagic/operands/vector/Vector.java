package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.operands.Operands;

/**
 * 向量的抽象类，其中包含一个向量的数据容器，以及向量的基本运算函数，这个向量中数值的类型等待实现，具体请参阅API说明。
 * <p>
 * The abstract class of the vector, which contains a data container of the vector, and the basic operation function of the vector. The type of the value in the vector is waiting to be implemented. For details, please refer to the API description.
 *
 * @param <ImplementationType> 实现类的数据类型  The data type of the implementing class
 * @param <ElementType>        向量中的元素类型  element type in the vector
 *                             该类为抽象，其中包含最基本的定义与类型管控
 *                             <p>
 *                             This class is abstract and contains the most basic definitions and type controls.
 */
public abstract class Vector<ImplementationType, ElementType> implements Operands<ImplementationType> {

    /**
     * 构造一个空向量，注意，如果您使用了此方法，那么您要在子类中调用"setVectorArrayPacking"
     * <p>
     * Constructs an empty vector, note that if you use this method then you call "set Vector Array" in your subclass
     */
    protected Vector() {
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * waiting to be realized
     */
    public abstract ElementType moduleLength();

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    public abstract ImplementationType multiply(ImplementationType vector);

    /**
     * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
     * <p>
     * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
     *
     * @param vector 第二个被计算的向量对象
     *               <p>
     *               the second computed vector object
     * @return 两个向量的内积
     * waiting to be realized
     */
    public abstract ElementType innerProduct(ImplementationType vector);

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    public abstract ImplementationType expand();

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     * <p>
     * 注意 该方法在大部分情况下返回的通常都是源数组，不允许更改，只能作为只读变量。
     */
    public abstract double[] toArray();

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    public abstract double[] CopyToNewArray();

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    public abstract int getNumberOfDimensions();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "This is a vector";
    }
}
