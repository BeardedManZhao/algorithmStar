package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.operands.Operands;

import java.io.Serializable;

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
 * @param <ArrayType>          实现类中所维护的向量数组类型，是从实现类中获取到本对象中数据的类型。
 *                             <p>
 *                             The vector array type maintained in the implementation class is the type of data in this object obtained from the implementation class.
 */
public abstract class Vector<ImplementationType, ElementType, ArrayType> implements Operands<ImplementationType>, Serializable {

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
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    protected abstract ArrayType copyToNewArray();

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    public abstract int getNumberOfDimensions();

    /**
     * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
     * <p>
     * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
     *
     * @param seed 打乱算法中所需要的随机种子。
     *             <p>
     *             Disrupt random seeds required in the algorithm.
     * @return 打乱之后的对象。
     * <p>
     * Objects after disruption.
     */
    public abstract ImplementationType shuffle(long seed);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "This is a vector";
    }
}
