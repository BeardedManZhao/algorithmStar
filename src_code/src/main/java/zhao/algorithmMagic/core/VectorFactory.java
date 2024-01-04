package zhao.algorithmMagic.core;

import zhao.algorithmMagic.operands.vector.*;

/**
 * 向量工厂类
 *
 * @author zhao
 */
public final class VectorFactory {
    /**
     * 根据数组数据构造向量，需要注意的是 数组对象会直接赋值给向量对象，所以请不要修改数组对象
     * <p>
     * Constructing vectors based on array data, it should be noted that array objects will be directly assigned to vector objects, so please do not modify array objects
     *
     * @param res 需要被解析为向量的数组，请确保其中的元素不会被修改，否则会影响向量对象，两者处于直接依赖关系
     *            <p>
     *            An array that needs to be parsed as a vector, please ensure that its elements are not modified, otherwise it will affect the vector object, and the two are in a direct dependency relationship
     * @return 解析后的向量对象
     * <p>
     * Parsed vector object
     */
    public IntegerVector parseVector(int... res) {
        return IntegerVector.parse(res);
    }


    /**
     * 根据数组数据构造向量，需要注意的是 数组对象会直接赋值给向量对象，所以请不要修改数组对象
     * <p>
     * Constructing vectors based on array data, it should be noted that array objects will be directly assigned to vector objects, so please do not modify array objects
     *
     * @param res 需要被解析为向量的数组，请确保其中的元素不会被修改，否则会影响向量对象，两者处于直接依赖关系
     *            <p>
     *            An array that needs to be parsed as a vector, please ensure that its elements are not modified, otherwise it will affect the vector object, and the two are in a direct dependency relationship
     * @return 解析后的向量对象
     * <p>
     * Parsed vector object
     */
    public DoubleVector parseVector(double... res) {
        return DoubleVector.parse(res);
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
     * @return 如果 vectorName 以及 field 不为null 会返回一个带有列名字段的向量对象 反之直接返回普通的向量对象
     * <p>
     * If the vectorName and field are not null, a vector object with a column name field will be returned. Otherwise, a regular vector object will be directly returned
     */
    public IntegerVector parseVector(String vectorName, String[] field, int... vectorArray) {
        if (vectorName != null && field != null) {
            return ColumnIntegerVector.parse(vectorName, field, vectorArray);
        } else {
            return parseVector(vectorArray);
        }
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
     * @return 如果 vectorName 以及 field 不为null 会返回一个带有列名字段的向量对象 反之直接返回普通的向量对象
     * <p>
     * If the vectorName and field are not null, a vector object with a column name field will be returned. Otherwise, a regular vector object will be directly returned
     */
    public DoubleVector parseVector(String vectorName, String[] field, double... vectorArray) {
        if (vectorName != null && field != null) {
            return ColumnDoubleVector.parse(vectorName, field, vectorArray);
        } else {
            return parseVector(vectorArray);
        }
    }

    /**
     * 根据起始和结束值构造一个范围向量
     * <p>
     * Construct a range vector based on the start and end value
     *
     * @param start 起始值
     * @param end   结束值
     * @return 解析后的向量对象
     * <p>
     * Parsed vector object
     */
    public FastRangeIntegerVector parseRangeVector(int start, int end) {
        return FastRangeIntegerVector.parse(start, end);
    }

    /**
     * 根据起始和结束值构造一个范围向量
     * <p>
     * Construct a range vector based on the start and end value
     *
     * @param start 起始值
     * @param end   结束值
     * @return 解析后的向量对象
     * <p>
     * Parsed vector object
     */
    public FastRangeDoubleVector parseRangeVector(double start, double end) {
        return FastRangeDoubleVector.parse(start, end);
    }
}
