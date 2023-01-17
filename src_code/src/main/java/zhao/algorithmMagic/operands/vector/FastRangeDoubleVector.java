package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASMath;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 快速区间型浮点向量对象，其中并没有存储很多的向量数值，但是却有着强大计算功能，能够实现轻量级的向量计算操作。
 * <p>
 * Fast interval-type floating point vector object, which does not store many vector values, has powerful computing functions and can implement lightweight vector computing operations.
 *
 * @author zhao
 */
public class FastRangeDoubleVector extends RangeVector<FastRangeDoubleVector, Double, double[], DoubleVector> {

    protected final double moduleLength;

    protected FastRangeDoubleVector(double[] doubles) {
        super(doubles, (int) Math.ceil(1 + doubles[1] - doubles[0]));
        AtomicReference<Double> tempModuleLength = new AtomicReference<>((double) 0);
        forEach(integer -> tempModuleLength.updateAndGet(v -> v + integer * integer));
        this.moduleLength = Math.sqrt(tempModuleLength.get());
    }

    public static FastRangeDoubleVector parse(double start, double end) {
        if (start < end) {
            return new FastRangeDoubleVector(new double[]{start, end});
        } else {
            throw new OperatorOperationException("您传入的区间不正确哦！！区间左边界的值应小于区间右边界的值!!!\nThe interval you entered is incorrect!! The value of the left boundary of the interval should be less than the value of the right boundary of the interval!!!\n" +
                    "left = " + start + '\t' + "right = " + end);
        }
    }

    @Override
    public FastRangeDoubleVector expand() {
        return this;
    }

    /**
     * 区间内元素迭代器
     * <p>
     * Element iterator in interval
     *
     * @param action 迭代时的数据处理逻辑，其中的值就是区间中的每一个元素。
     *               <p>
     *               Data processing logic during iteration, where the value is each element in the interval
     */
    @Override
    public void forEach(Consumer<Double> action) {
        double rangeStart = this.getRangeStart() - 1;
        final double rangeEnd = this.getRangeEnd();
        while (rangeEnd > rangeStart) {
            action.accept(++rangeStart);
        }
    }

    /**
     * @return 获取到区间型向量中的区间起始数值，同时这个也是向量中的第一个坐标。
     * <p>
     * Get the interval starting value in the interval type vector, and this is also the first coordinate in the vector.
     */
    @Override
    public Double getRangeStart() {
        return super.arrayType[0];
    }

    /**
     * @return 获取到区间型向量中的区间终止数值，同时这个也是向量中的最后一个坐标。
     * <p>
     * Get the interval termination value in the interval vector, which is also the last coordinate in the vector.
     */
    @Override
    public Double getRangeEnd() {
        return super.arrayType[1];
    }

    /**
     * @return 获取到区间性向量中的所有元素的求和结果数值，是区间中所有元素的和。
     * <p>
     * Get the sum result value of all elements in the interval vector, which is the sum of all elements in the interval.
     */
    @Override
    public Double getRangeSum() {
        return ASMath.sumOfRange(this.getRangeStart(), this.getRangeEnd());
    }

    /**
     * 将本区间的向量转换成具体向量。
     * <p>
     * Convert the vectors in this interval into concrete vectors.
     *
     * @return 本向量转换成功之后会返回一个向量对象。
     * <p>
     * A vector object will be returned after the vector conversion is successful.
     */
    @Override
    public DoubleVector toVector() {
        return DoubleVector.parse(copyToNewArray());
    }

    /**
     * @return 本向量中的数组对象，需要注意的是，本向量中的数组是推断出来的，因此您可以直接修改。
     * <p>
     * The array objects in this vector should be noted that the array in this vector is inferred, so you can modify it directly.
     */
    @Override
    public double[] copyToNewArray() {
        double[] res = new double[size()];
        double rangeStart = this.getRangeStart() - 1;
        final double rangeEnd = this.getRangeEnd();
        int count = -1;
        while (rangeEnd > rangeStart) {
            res[++count] = ++rangeStart;
        }
        return res;
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * waiting to be realized
     */
    @Override
    public double moduleLength() {
        return this.moduleLength;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public FastRangeDoubleVector add(FastRangeDoubleVector value) {
        return FastRangeDoubleVector.parse(this.getRangeStart() + value.getRangeStart(), this.getRangeEnd() + value.getRangeEnd());
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public FastRangeDoubleVector diff(FastRangeDoubleVector value) {
        return FastRangeDoubleVector.parse(this.getRangeStart() - value.getRangeStart(), this.getRangeEnd() - value.getRangeEnd());
    }
}
