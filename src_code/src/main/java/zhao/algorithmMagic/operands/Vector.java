package zhao.algorithmMagic.operands;

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
    private ElementType[] VectorArray;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     */
    public Vector(ElementType[] vectorArray) {
        VectorArray = vectorArray;
    }

    /**
     * 构造一个空向量，注意，如果您使用了此方法，那么您要在子类中调用"setVectorArray"
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
    public ElementType[] getVectorArray() {
        return VectorArray;
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
    protected void setVectorArray(ElementType[] vectorArray) {
        this.VectorArray = vectorArray;
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
    public abstract Vector<ImplementationType, ElementType> multiply(ImplementationType vector);

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
     * 向量的数乘运算，将向量延伸/缩短为原来的x倍
     * <p>
     * Vector multiplication operation, shortening the vector extension to x times the original
     *
     * @param x 倍数 multiple
     * @apiNote waiting to be realized
     */
    public abstract ElementType scalarMultiplication(ElementType x);
}
