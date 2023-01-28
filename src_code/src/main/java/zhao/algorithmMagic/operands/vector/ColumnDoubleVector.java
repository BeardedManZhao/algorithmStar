package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * 向量中的每一个维度的值都有对应的名称，该向量对象支持列名设置
 * <p>
 * The value of each dimension in the vector has a corresponding name. The vector object supports column name setting
 *
 * @author zhao
 */
public class ColumnDoubleVector extends DoubleVector {
    private final String[] Field;
    private final String vectorName;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorName  本向量的名称，如果在进行向量使用的时候需要一个名称，那么您可以使用该方式。
     * @param field       向量中的每一个维度的名称
     * @param vectorArray 向量中的数值序列集合
     */
    public ColumnDoubleVector(String vectorName, String[] field, double[] vectorArray) {
        super(vectorArray);
        this.vectorName = vectorName;
        this.Field = field;
    }

    protected ColumnDoubleVector(double[] vectorArrayPrimitive, String vectorStr, double moduleLength, String[] field, String vectorName) {
        super(vectorArrayPrimitive, vectorStr, moduleLength);
        Field = field;
        this.vectorName = vectorName;
    }

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorName  向量名称，在该对象中，您可以为向量起一个名称。
     *                    <p>
     *                    Vector name. In this object, you can give a name to the vector.
     * @param field       向量中的每一个维度的名称。
     *                    <p>
     *                    The name of each dimension in the vector.
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     * @return 带有列名字段的向量对象
     * <p>
     * Vector object with column name field
     */
    public static ColumnDoubleVector parse(String vectorName, String[] field, double... vectorArray) {
        return new ColumnDoubleVector(vectorName, field, vectorArray);
    }

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorName   向量名称，在该对象中，您可以为向量起一个名称。
     *                     <p>
     *                     Vector name. In this object, you can give a name to the vector.
     * @param field        向量中的每一个维度的名称。
     *                     <p>
     *                     The name of each dimension in the vector.
     * @param doubleVector 向量中的数值序列集合
     *                     <p>
     *                     collection of numeric sequences in a vector
     * @return 带有列名字段的向量对象
     * <p>
     * Vector object with column name field
     */
    public static ColumnDoubleVector parse(String vectorName, String[] field, DoubleVector doubleVector) {
        return new ColumnDoubleVector(doubleVector.toArray(), doubleVector.toString(), doubleVector.moduleLength(), field, vectorName);
    }

    /**
     * @return 该向量中每一个维度所对应的列名称组成的数组。
     * <p>
     * An array of column names corresponding to each dimension in the vector.
     */
    public String[] getFieldNames() {
        return Field;
    }

    /**
     * @return 将当前的向量转换成列名与向量的键值对。
     * <p>
     * Converts the current vector into a key-value pair of column names and vectors.
     */
    public HashMap<String, Double> toHashMap() {
        HashMap<String, Double> hashMap = new HashMap<>(this.Field.length + 10);
        for (int i = 0; i < this.Field.length; i++) {
            hashMap.put(this.Field[i], this.VectorArrayPrimitive[i]);
        }
        return hashMap;
    }

    /**
     * 向量的名称，在本对象中，一个向量是支持使用一个名称进行标记的。
     * <p>
     * The name of a vector. In this object, a vector supports marking with a name.
     *
     * @return 该向量的名称。
     * <p>
     * The name of the vector.
     */
    public String vectorName() {
        return vectorName;
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
     * <p>
     * Objects after disruption.
     */
    @Override
    public DoubleVector shuffle(long seed) {
        if (this.Field.length == 0) {
            return super.shuffle(seed);
        } else {
            Random random = new Random();
            random.setSeed(seed);
            double[] res = this.copyToNewArray();
            String[] clone = this.Field.clone();
            int maxIndex = res.length - 1;
            for (int i = 0; i < res.length; i++) {
                int i1 = random.nextInt(maxIndex);
                String tempS = clone[i1];
                clone[i1] = clone[i];
                clone[i] = tempS;
                double temp = res[i1];
                res[i1] = res[i];
                res[i] = temp;
            }
            return ColumnDoubleVector.parse(vectorName(), clone, res);
        }
    }

    /**
     * 将数据所维护的数组左移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the left n positions and get the result value
     *
     * @param n    被左移的次数，该数值应取值于 [0, getNumberOfDimensions]
     *             <p>
     *             The number of times it is moved to the left. The value should be [0, getNumberOfDimensions]
     * @param copy 本次左移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this left shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public ColumnDoubleVector leftShift(int n, boolean copy) {
        if (copy) {
            String[] fieldNames = this.getFieldNames().clone();
            return ColumnDoubleVector.parse(
                    this.vectorName,
                    ASMath.leftShift(fieldNames, n),
                    ASMath.leftShiftNv(this.copyToNewArray(), n)
            );
        } else {
            return ColumnDoubleVector.parse(
                    this.vectorName,
                    ASMath.leftShift(this.getFieldNames(), n),
                    ASMath.leftShiftNv(this.toArray(), n)
            );
        }
    }

    /**
     * 将数据所维护的数组右移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the right n positions and get the result value
     *
     * @param n    被右移的次数，该数值应取值于 [0, getNumberOfDimensions]
     *             <p>
     *             The number of times it is moved to the right. The value should be [0, getNumberOfDimensions]
     * @param copy 本次右移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this right shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public DoubleVector rightShift(int n, boolean copy) {
        if (copy) {
            String[] fieldNames = this.getFieldNames().clone();
            double[] doubles = this.copyToNewArray();
            return ColumnDoubleVector.parse(
                    this.vectorName,
                    ASMath.rightShift(fieldNames, n),
                    ASMath.rightShift(doubles, n)
            );
        } else {
            return ColumnDoubleVector.parse(
                    this.vectorName,
                    ASMath.rightShift(this.getFieldNames(), n),
                    ASMath.rightShift(this.toArray(), n)
            );
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.Field) + super.toString();
    }
}
