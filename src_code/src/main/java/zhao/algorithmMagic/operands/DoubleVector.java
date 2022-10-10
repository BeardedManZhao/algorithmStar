package zhao.algorithmMagic.operands;

import zhao.algorithmMagic.exception.AlgorithmMagicException;
import zhao.algorithmMagic.exception.OperatorOperationException;

import java.util.Arrays;

/**
 * Java类于 2022/10/9 18:50:31 创建
 *
 * @author 4
 */
public class DoubleVector extends Vector<DoubleVector, Double> {

    private final boolean UsePrimitiveType;
    private double[] doubles;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     */
    public DoubleVector(Double[] vectorArray) {
        super(vectorArray);
        UsePrimitiveType = false;
    }

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     */
    public DoubleVector(double[] vectorArray) {
        this.doubles = vectorArray;
        UsePrimitiveType = true;
    }

    /**
     * 创建一个空向量，由您来指定该向量的数据是否使用基元
     * <p>
     * Creates an empty vector, and it is up to you to specify whether the data of the vector uses primitives
     *
     * @param UsePrimitiveType 是否使用使用基元类型  whether to use primitive types
     */
    public DoubleVector(boolean UsePrimitiveType) {
        this.UsePrimitiveType = UsePrimitiveType;
    }

    protected DoubleVector() {
        UsePrimitiveType = false;
    }

    /**
     * 使用包装Double序列构建出一个向量
     * <p>
     * Constructs a vector using the wrapped Double sequence
     *
     * @param doubles 包装序列数组  wrapping an array of sequences
     * @return DoubleVector
     */
    public static DoubleVector parse(Double[] doubles) {
        return new DoubleVector(doubles);
    }

    /**
     * 使用基元Double序列构建出一个向量
     * <p>
     * Constructs a vector using the primitive Double sequence
     *
     * @param doubles 基元序列数组  primitive sequence array
     * @return DoubleVector
     */
    public static DoubleVector parse(double... doubles) {
        return new DoubleVector(doubles);
    }

    /**
     * 使用坐标构成的线计算出来向量
     *
     * @param startDoubleCoordinateTwo 向量的起始坐标
     * @param endDoubleCoordinateTwo   向量的终止坐标
     * @return 向量
     */
    public static DoubleVector parse(DoubleCoordinateTwo startDoubleCoordinateTwo, DoubleCoordinateTwo endDoubleCoordinateTwo) {
        return DoubleVector.parse(endDoubleCoordinateTwo.getX() - startDoubleCoordinateTwo.getX(), endDoubleCoordinateTwo.getY() - startDoubleCoordinateTwo.getY());
    }

    /**
     * @return 向量数据容器的数组形式，调用此方法，您可以获取到该向量中的数值。
     * <p>
     * 如果您使用的是基元类型的，那么该方法将会返回null，您可以使用"getVectorPrimitiveArray()"作为代替。
     * <p>
     * The array form of the vector data container, you can get the values in the vector by calling this method.
     * <p>
     * If you are using a primitive type then this method will return null, you can use "getVectorPrimitiveArray()" instead.
     */
    @Override
    public Double[] getVectorArray() {
        if (isUsePrimitiveType()) {
            return null;
        } else {
            return super.getVectorArray();
        }
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
    @Override
    protected void setVectorArray(Double[] vectorArray) {
        if (isUsePrimitiveType()) {
            this.doubles = new double[vectorArray.length];
            for (int n = 0; n < doubles.length; n++) {
                doubles[n] = vectorArray[n];
            }
        } else {
            super.setVectorArray(vectorArray);
        }
    }

    /**
     * @return 向量数据容器的数组形式，调用此方法，您可以获取到该向量中的数值。
     * <p>
     * 如果您使用的是包装类型的，那么该方法会返回null，您可以使用"getVectorArray()"作为代替。
     * <p>
     * The array form of the vector data container, you can get the values in the vector by calling this method.
     * <p>
     * If you are using a wrapper type then this method will return null, you can use "get Vector Array()" instead.
     */
    public double[] getVectorPrimitiveArray() {
        if (isUsePrimitiveType()) {
            return this.doubles;
        } else {
            return null;
        }
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @apiNote 这里按照向量的坐标将每一项进行了求和，实现了向量的相加。
     * <p>
     * Here, each item is summed according to the coordinates of the vector, and the addition of the vector is realized.
     */
    @Override
    public DoubleVector add(DoubleVector value) {
        return getDoubleVectorAdd(getVectorArray(), this.doubles, value.getVectorArray(), value.doubles);
    }

    private DoubleVector getDoubleVectorAdd(Double[] vectorArray1, double[] vectorArray11, Double[] vectorArray2, double[] vectorArray21) {
        int length1 = vectorArray1 == null ? vectorArray11.length : vectorArray1.length;
        int length2 = vectorArray2 == null ? vectorArray21.length : vectorArray2.length;
        if (length1 == length2) {
            double[] doubles = new double[length1];
            if (vectorArray11 == null && vectorArray21 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray1[indexNum] + vectorArray2[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray1 == null && vectorArray2 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray11[indexNum] + vectorArray21[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray11 == null && vectorArray2 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray1[indexNum] + vectorArray21[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray1 == null && vectorArray21 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray11[indexNum] + vectorArray2[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else {
                throw new OperatorOperationException("'DoubleVector1 diff DoubleVector2' 时，两个向量的序列数值不明确，基元类型与包装类型混乱, isUsePrimitiveType = [" + isUsePrimitiveType() + "]\n" +
                        "When 'DoubleVector1 diff DoubleVector2', the sequence value of two vectors is ambiguous, and the primitive type and the wrapper type are confused, isUsePrimitiveType = [" + isUsePrimitiveType() + "]"
                );
            }
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 add DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" +
                            "When 'DoubleVector1 add DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]"
            );
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * @apiNote 这里按照向量的坐标将每一项进行了做差，实现了向量的相减法。
     * <p>
     * Here, each item is subtracted according to the coordinates of the vector, and the subtraction method of the vector is realized.
     */
    @Override
    public DoubleVector diff(DoubleVector value) {
        return getDoubleVectorDiff(getVectorArray(), this.doubles, value.getVectorArray(), value.doubles);
    }

    private DoubleVector getDoubleVectorDiff(Double[] vectorArray1, double[] vectorArray11, Double[] vectorArray2, double[] vectorArray21) {
        int length1 = vectorArray1 == null ? vectorArray11.length : vectorArray1.length;
        int length2 = vectorArray2 == null ? vectorArray21.length : vectorArray2.length;
        if (length1 == length2) {
            double[] doubles = new double[length1];
            if (vectorArray11 == null && vectorArray21 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray1[indexNum] - vectorArray2[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray1 == null && vectorArray2 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray11[indexNum] - vectorArray21[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray11 == null && vectorArray2 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray1[indexNum] - vectorArray21[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else if (vectorArray1 == null && vectorArray21 == null) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    doubles[indexNum] = vectorArray11[indexNum] - vectorArray2[indexNum];
                }
                return DoubleVector.parse(doubles);
            } else {
                throw new OperatorOperationException("'DoubleVector1 diff DoubleVector2' 时，两个向量的序列数值不明确，基元类型与包装类型混乱, isUsePrimitiveType = [" + isUsePrimitiveType() + "]\n" +
                        "When 'DoubleVector1 diff DoubleVector2', the sequence value of two vectors is ambiguous, and the primitive type and the wrapper type are confused, isUsePrimitiveType = [" + isUsePrimitiveType() + "]"
                );
            }
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" +
                            "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]"
            );
        }
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * @apiNote waiting to be realized
     */
    @Override
    public Double moduleLength() {
        double res = 0;
        if (isUsePrimitiveType()) {
            for (Double value : doubles) {
                res += value * value;
            }
        } else {
            for (double value : getVectorArray()) {
                res += value * value;
            }
        }
        return Math.sqrt(res);
    }

    /**
     * 两个向量相乘，同时也是两个向量的外积，向量乘积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积（除去方向）
     * <p>
     * Outer product of vectors (minus direction)
     * @apiNote waiting to be realized
     */
    @Override
    public Vector<DoubleVector, Double> multiply(DoubleVector vector) {
        int length1 = this.isUsePrimitiveType() ? this.doubles.length : getVectorArray().length;
        int length2 = this.isUsePrimitiveType() ? vector.doubles.length : getVectorArray().length;
        if (length1 == length2) {
            int now = 0;
            double[] multiplyVector = new double[(length1 - 1) * length2];
            if (this.UsePrimitiveType && vector.isUsePrimitiveType()) {
                for (int n1 = 0; n1 < length1; n1++) {
                    for (int n2 = 0; n2 < length1; n2++) {
                        if (n2 != n1) {
                            multiplyVector[now] = this.doubles[n1] * vector.doubles[n2];
                            now++;
                        }
                    }
                }
            } else if (this.isUsePrimitiveType() && !vector.isUsePrimitiveType()) {
                for (int n1 = 0; n1 < length1; n1++) {
                    for (int n2 = 0; n2 < length1; n2++) {
                        if (n2 != n1) {
                            multiplyVector[now] = this.doubles[n1] * vector.getVectorArray()[n2];
                            now++;
                        }
                    }
                }
            } else if (!this.isUsePrimitiveType() && vector.isUsePrimitiveType()) {
                for (int n1 = 0; n1 < length1; n1++) {
                    for (int n2 = 0; n2 < length1; n2++) {
                        if (n2 != n1) {
                            multiplyVector[now] = this.getVectorArray()[n1] * vector.doubles[n2];
                            now++;
                        }
                    }
                }
            } else if (!this.isUsePrimitiveType() && !vector.isUsePrimitiveType()) {
                for (int n1 = 0; n1 < length1; n1++) {
                    for (int n2 = 0; n2 < length1; n2++) {
                        if (n2 != n1) {
                            multiplyVector[now] = this.getVectorArray()[n1] * vector.getVectorArray()[n2];
                            now++;
                        }
                    }
                }
            } else {
                throw new OperatorOperationException("'DoubleVector1 multiply DoubleVector2' 时，两个向量的序列数值不明确，基元类型与包装类型混乱, isUsePrimitiveType = [" + isUsePrimitiveType() + "]\n" +
                        "When 'DoubleVector1 multiply DoubleVector2', the sequence value of two vectors is ambiguous, and the primitive type and the wrapper type are confused, isUsePrimitiveType = [" + isUsePrimitiveType() + "]"
                );
            }
            return DoubleVector.parse(multiplyVector);
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 multiply DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" +
                            "When 'DoubleVector1 multiply DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]"
            );
        }
    }

    /**
     * 计算两个向量的夹角
     * <p>
     * Calculate the angle between two vectors
     *
     * @param vector 另一个向量  another vector
     * @return 夹角的cos值  cos value of included angle
     */
    public double includedAngleCos(DoubleVector vector) {
        try {
            return this.innerProduct(vector) / (this.moduleLength() * vector.moduleLength());
        } catch (OperatorOperationException e) {
            throw AlgorithmMagicException.getAlgorithmMagicExceptionAndSetStackTrace(new OperatorOperationException("'DoubleVector1 includedAngle DoubleVector2' An error occurred"), e.getStackTrace());
        }
    }

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
    @Override
    public Double innerProduct(DoubleVector vector) {
        int length1 = this.isUsePrimitiveType() ? this.doubles.length : getVectorArray().length;
        int length2 = this.isUsePrimitiveType() ? vector.doubles.length : getVectorArray().length;
        if (length1 == length2) {
            double innerProduct = 0b0;
            if (this.isUsePrimitiveType() && vector.isUsePrimitiveType()) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    innerProduct += this.doubles[indexNum] * vector.doubles[indexNum];
                }
            } else if (this.isUsePrimitiveType() && !vector.isUsePrimitiveType()) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    innerProduct += this.doubles[indexNum] * vector.getVectorArray()[indexNum];
                }
            } else if (!this.isUsePrimitiveType() && vector.isUsePrimitiveType()) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    innerProduct += this.getVectorArray()[indexNum] * vector.doubles[indexNum];
                }
            } else if (!this.isUsePrimitiveType() && !vector.isUsePrimitiveType()) {
                for (int indexNum = 0b0; indexNum < length1; indexNum++) {
                    innerProduct += this.getVectorArray()[indexNum] * vector.getVectorArray()[indexNum];
                }
            } else {
                throw new OperatorOperationException("'DoubleVector1 innerProduct DoubleVector2' 时，两个向量的序列数值不明确，基元类型与包装类型混乱, isUsePrimitiveType = [" + isUsePrimitiveType() + "\t" + isUsePrimitiveType() + "]\n" +
                        "When 'DoubleVector1 innerProduct DoubleVector2', the sequence value of two vectors is ambiguous, and the primitive type and the wrapper type are confused, isUsePrimitiveType = [" + isUsePrimitiveType() + "\t" + isUsePrimitiveType() + "]"
                );
            }
            return innerProduct;
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 innerProduct DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" +
                            "When 'DoubleVector1 innerProduct DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]"
            );
        }
    }

    /**
     * 向量的数乘运算，将向量延伸/缩短为原来的x倍
     * <p>
     * Vector multiplication operation, shortening the vector extension to x times the original
     *
     * @param x 倍数 multiple
     * @apiNote waiting to be realized
     */
    @Override
    public Double scalarMultiplication(Double x) {
        return null;
    }

    /**
     * @return 是否使用基元类型，基元类型能更好地降低内存占用，如果您不使用基元，将会启动父类的数据容器
     * <p>
     * Whether to use primitive types, primitive types can better reduce memory usage, if you do not use primitives, the data container of the parent class will be started
     */
    public boolean isUsePrimitiveType() {
        return UsePrimitiveType;
    }

    /**
     * 向量数据转移，您可以通过这个方法，将向量中的所有数值拷贝到其它的向量容器
     * <p>
     * Vector type conversion, you can customize the type of the vector, please note: the sequence numeric data type inside your vector cannot be changed!
     *
     * @param doubleVector 被转换成的目标向量
     *                     <p>
     *                     The target vector type to be converted into.
     */
    public void copyTo(DoubleVector doubleVector) {
        if (this.isUsePrimitiveType()) {
            doubleVector.doubles = this.doubles;
        } else {
            doubleVector.setVectorArray(this.getVectorArray());
        }
    }

    @Override
    public String toString() {
        return "DoubleVector{" +
                "UsePrimitiveType=" + UsePrimitiveType +
                (isUsePrimitiveType() ?
                        ", Vector=" + Arrays.toString(doubles) + '}' :
                        ", Vector=" + Arrays.toString(getVectorArray()) + '}');
    }
}
