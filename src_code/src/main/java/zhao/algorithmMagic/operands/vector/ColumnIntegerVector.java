package zhao.algorithmMagic.operands.vector;

import java.util.HashMap;

/**
 * 向量中的每一个维度的值都有对应的名称，该向量对象支持列名设置
 * <p>
 * The value of each dimension in the vector has a corresponding name. The vector object supports column name setting
 *
 * @author zhao
 */
public class ColumnIntegerVector extends IntegerVector {

    private final String[] Field;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param field       向量中的每一个维度的名称
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     */
    public ColumnIntegerVector(String[] field, int[] vectorArray) {
        super(vectorArray);
        Field = field;
    }

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param field       向量中的每一个维度的名称
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     * @return 带有列名字段的向量对象
     * <p>
     * Vector object with column name field
     */
    public ColumnIntegerVector parse(String[] field, int... vectorArray) {
        return new ColumnIntegerVector(field, vectorArray);
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
}
