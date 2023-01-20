package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.operands.Operands;

import java.util.function.Consumer;

/**
 * 区间型向量对象的抽象接口，其中的向量对象是以区间进行构造出来的，这种向量在存储方面来说是非常小巧的一种向量，因为它只需要知道区间的起始与终止数值。
 * <p>
 * The abstract interface of interval type vector object, in which the vector object is constructed by interval, is a very small vector in terms of storage, because it only needs to know the starting and ending values of the interval.
 *
 * @param <ImplementationType> 实现类类型，用于父类转子类。
 * @param <ElementType>        本类中元素的数据类型。
 * @param <ArrayType>          本类中拷贝出来的数组类型。
 * @param <VectorType>         本类中直接获取到的向量数据对象类型。
 */
public abstract class RangeVector<ImplementationType, ElementType extends Number, ArrayType, VectorType> implements Operands<ImplementationType> {
    protected final int length;
    ArrayType arrayType;

    public RangeVector(ArrayType arrayType, int length) {
        this.arrayType = arrayType;
        this.length = length;
    }

    public abstract ImplementationType expand();

    /**
     * 区间内元素迭代器
     * <p>
     * Element iterator in interval
     *
     * @param action 迭代时的数据处理逻辑，其中的值就是区间中的每一个元素。
     *               <p>
     *               Data processing logic during iteration, where the value is each element in the interval
     */
    public abstract void forEach(Consumer<ElementType> action);

    /**
     * @return 获取到区间型向量中的区间起始数值，同时这个也是向量中的第一个坐标。
     * <p>
     * Get the interval starting value in the interval type vector, and this is also the first coordinate in the vector.
     */
    public abstract ElementType getRangeStart();

    /**
     * @return 获取到区间型向量中的区间终止数值，同时这个也是向量中的最后一个坐标。
     * <p>
     * Get the interval termination value in the interval vector, which is also the last coordinate in the vector.
     */
    public abstract ElementType getRangeEnd();

    /**
     * @return 获取到区间性向量中的所有元素的求和结果数值，是区间中所有元素的和。
     * <p>
     * Get the sum result value of all elements in the interval vector, which is the sum of all elements in the interval.
     */
    public abstract ElementType getRangeSum();

    /**
     * 将本区间的向量转换成具体向量。
     * <p>
     * Convert the vectors in this interval into concrete vectors.
     *
     * @return 本向量转换成功之后会返回一个向量对象。
     * <p>
     * A vector object will be returned after the vector conversion is successful.
     */
    public abstract VectorType toVector();

    /**
     * @return 本向量中的数组对象，需要注意的是，本向量中的数组是推断出来的，因此您可以直接修改。
     * <p>
     * The array objects in this vector should be noted that the array in this vector is inferred, so you can modify it directly.
     */
    protected abstract ArrayType copyToNewArray();

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
    public abstract VectorType shuffle(long seed);

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * waiting to be realized
     */
    public abstract double moduleLength();

    /**
     * @return 此区间型向量中的数据长度
     * <p>
     * The length of data in this interval vector
     */
    public final int size() {
        return this.length;
    }
}
