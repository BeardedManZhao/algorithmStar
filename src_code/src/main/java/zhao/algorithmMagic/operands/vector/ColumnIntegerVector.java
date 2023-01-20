package zhao.algorithmMagic.operands.vector;

import java.util.HashMap;
import java.util.Random;

/**
 * 向量中的每一个维度的值都有对应的名称，该向量对象支持列名设置
 * <p>
 * The value of each dimension in the vector has a corresponding name. The vector object supports column name setting
 *
 * @author zhao
 */
public class ColumnIntegerVector extends IntegerVector {

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
    public ColumnIntegerVector(String vectorName, String[] field, int[] vectorArray) {
        super(vectorArray);
        this.vectorName = vectorName;
        this.Field = field;
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
    public static ColumnIntegerVector parse(String vectorName, String[] field, int... vectorArray) {
        return new ColumnIntegerVector(vectorName, field, vectorArray);
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
    public HashMap<String, Integer> toHashMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(this.Field.length + 10);
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
    public IntegerVector shuffle(long seed) {
        if (this.Field.length == 0) {
            return super.shuffle(seed);
        } else {
            Random random = new Random();
            random.setSeed(seed);
            int[] res = this.copyToNewArray();
            String[] clone = this.Field.clone();
            int maxIndex = res.length - 1;
            for (int i = 0; i < res.length; i++) {
                int i1 = random.nextInt(maxIndex);
                String tempS = clone[i1];
                clone[i1] = clone[i];
                clone[i] = tempS;
                int temp = res[i1];
                res[i1] = res[i];
                res[i] = temp;
            }
            return ColumnIntegerVector.parse(vectorName(), clone, res);
        }
    }
}
