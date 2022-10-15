package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.operands.Operands;

/**
 * 向量的抽象类，其中包含一个向量的数据容器，以及向量的基本运算函数，这个向量中数值的类型等待实现，具体请参阅API说明。
 * <p>
 * The abstract class of the vector, which contains a data container of the vector, and the basic operation function of the vector. The type of the value in the vector is waiting to be implemented. For details, please refer to the API description.
 *
 * @param <ImplementationType> 实现类的数据类型  The data type of the implementing class
 * @param <ElementType>        向量中的元素类型  element type in the vector
 * @apiNote 该类为抽象，其中包含最基本的定义与类型管控
 * <p>
 * This class is abstract and contains the most basic definitions and type controls.
 */
public abstract class Vector<ImplementationType, ElementType> implements Operands<ImplementationType> {

    /**
     * 向量数据容器，其中就是向量的数据，子类应对该类进行操作。
     * <p>
     * Vector data container, which is the data of the vector, and subclasses should operate on this class.
     */
    private ElementType[] VectorArrayPacking;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorArrayPacking 向量中的数值序列集合
     *                           <p>
     *                           collection of numeric sequences in a vector
     */
    public Vector(ElementType[] vectorArrayPacking) {
        VectorArrayPacking = vectorArrayPacking;
    }

    /**
     * 构造一个空向量，注意，如果您使用了此方法，那么您要在子类中调用"setVectorArrayPacking"
     * <p>
     * Constructs an empty vector, note that if you use this method then you call "set Vector Array" in your subclass
     */
    protected Vector() {
    }

    /**
     * @return 向量数据容器的数组形式，调用此方法，您将可以获取到该向量中的数值
     * <p>
     * The array form of the vector data container, call this method, you will get the value in the vector
     */
    public ElementType[] getVectorArrayPacking() {
        return VectorArrayPacking;
    }

    /**
     * 对向量数据进行基本的设置
     * <p>
     * Make basic settings for vector data
     *
     * @param vectorArray 向量数据容器的数组形式
     *                    <p>
     *                    Array form of vector data container
     */
    protected void setVectorArrayPacking(ElementType[] vectorArray) {
        this.VectorArrayPacking = vectorArray;
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * @apiNote waiting to be realized
     */
    public abstract ElementType moduleLength();

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积
     * @apiNote waiting to be realized
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
     * @apiNote waiting to be realized
     */
    public abstract ElementType innerProduct(ImplementationType vector);

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    public abstract ImplementationType expand();

    /**
     * @return 是否使用基元类型，基元类型能更好地降低内存占用，如果您不使用基元，将会启动父类的数据容器
     * <p>
     * Whether to use primitive types, primitive types can better reduce memory usage, if you do not use primitives, the data container of the parent class will be started
     */
    public abstract boolean isUsePrimitiveType();

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     */
    public abstract double[] toArray();

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

}
