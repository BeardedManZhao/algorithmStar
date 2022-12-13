package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;

/**
 * Java类于 2022/10/20 14:58:56 创建
 *
 * @author 4
 */
public class IntegerVector extends ASVector<IntegerVector, Integer> {
    int[] VectorArrayPrimitive;
    private String vectorStr;

    /**
     * 使用初始传参的方式构建出来一个向量
     * <p>
     * Construct a vector using the initial parameter pass method.
     *
     * @param vectorArray 向量中的数值序列集合
     *                    <p>
     *                    collection of numeric sequences in a vector
     */
    public IntegerVector(int[] vectorArray) {
        this.VectorArrayPrimitive = vectorArray;
        this.vectorStr = Arrays.toString(vectorArray);
    }

    /**
     * 使用多维始末坐标构建出来一个证书向量！
     *
     * @param startIntegerCoordinateMany 起始点整数多维坐标
     * @param endIntegerCoordinateMany   终止点整数多维坐标
     * @return 一个整数向量
     */
    public static IntegerVector parse(IntegerCoordinateMany startIntegerCoordinateMany, IntegerCoordinateMany endIntegerCoordinateMany) {
        return new IntegerVector(startIntegerCoordinateMany.diff(endIntegerCoordinateMany).toArray());
    }

    public static IntegerVector parse(int... res) {
        return new IntegerVector(res);
    }

    public static IntegerVector parse(double... doubles) {
        return parse(ASClass.DoubleArray_To_IntArray(doubles));
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
    public IntegerVector add(IntegerVector value) {
        int numberOfDimensions1 = this.getNumberOfDimensions();
        int numberOfDimensions2 = value.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            int[] res = new int[numberOfDimensions1];
            double[] doubles1 = this.toArray();
            double[] doubles2 = value.toArray();
            for (int i = 0; i < numberOfDimensions1; i++) {
                res[i] = (int) (doubles1[i] + doubles2[i]);
            }
            return IntegerVector.parse(res);
        } else {
            throw new OperatorOperationException(
                    "'IntegerVector1 add IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + numberOfDimensions1 + "]，IntegerVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'IntegerVector1 add IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + numberOfDimensions1 + "], IntegerVector2=[" + numberOfDimensions2 + "]"
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
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerVector diff(IntegerVector value) {
        int numberOfDimensions1 = this.getNumberOfDimensions();
        int numberOfDimensions2 = value.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            int[] res = new int[numberOfDimensions1];
            double[] doubles1 = this.toArray();
            double[] doubles2 = value.toArray();
            for (int i = 0; i < numberOfDimensions1; i++) {
                res[i] = (int) (doubles1[i] - doubles2[i]);
            }
            return IntegerVector.parse(res);
        } else {
            throw new OperatorOperationException(
                    "'IntegerVector1 diff IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + numberOfDimensions1 + "]，IntegerVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'IntegerVector1 diff IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + numberOfDimensions1 + "], IntegerVector2=[" + numberOfDimensions2 + "]"
            );
        }
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
    public Integer moduleLength() {
        int res = 0;
        for (int value : VectorArrayPrimitive) {
            res += ASMath.Power2(value);
        }
        return (int) Math.sqrt(res);
    }

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    @Override
    public IntegerVector multiply(IntegerVector vector) {
        double[] vectorArray1 = this.toArray();
        double[] vectorArray2 = vector.toArray();
        int length1 = vectorArray1.length;
        int length2 = vectorArray2.length;
        if (length1 == length2) {
            return IntegerVector.parse(ASMath.CrossMultiplication(vectorArray1, vectorArray2));
        } else {
            throw new OperatorOperationException(
                    "'IntegerVector1 multiply IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + length1 + "]，IntegerVector2=[" + length2 + "]\n" +
                            "When 'IntegerVector1 multiply IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + length1 + "], IntegerVector2=[" + length2 + "]"
            );
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
     * waiting to be realized
     */
    @Override
    public Integer innerProduct(IntegerVector vector) {
        double[] doubles1 = this.toArray();
        double[] doubles2 = vector.toArray();
        if (doubles1.length == doubles2.length) {
            int innerProduct = 0b0;
            for (int indexNum = 0b0; indexNum < doubles1.length; indexNum++) {
                innerProduct += doubles1[indexNum] * doubles2[indexNum];
            }
            return innerProduct;
        } else {
            throw new OperatorOperationException(
                    "'IntegerVector1 innerProduct IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + doubles1.length + "]，IntegerVector2=[" + doubles2.length + "]\n" +
                            "When 'IntegerVector1 innerProduct IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + doubles1.length + "], IntegerVector2=[" + doubles2.length + "]"
            );
        }
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public IntegerVector expand() {
        return this;
    }

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     */
    @Override
    public double[] toArray() {
        return ASClass.IntArray_To_DoubleArray(this.getVectorArrayPrimitive());
    }

    /**
     * @return 该对象的向量数组形式，由于是拷贝出来的，不会产生任何依赖关系，因此支持修改
     * <p>
     * The vector array form of the object is copied, which does not generate any dependency, so it supports modification
     */
    @Override
    public double[] CopyToNewArray() {
        return this.toArray();
    }

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return this.VectorArrayPrimitive.length;
    }

    /**
     * @param integerVector 将此向量中的数据复制到另一个相同类型的向量对象中
     */
    public void copyTo(IntegerVector integerVector) {
        if (integerVector.VectorArrayPrimitive.length < this.VectorArrayPrimitive.length) {
            integerVector.VectorArrayPrimitive = new int[this.VectorArrayPrimitive.length];
        }
        System.arraycopy(this.VectorArrayPrimitive, 0, integerVector.VectorArrayPrimitive, 0, this.VectorArrayPrimitive.length);
        integerVector.vectorStr = this.vectorStr;
    }

    public int[] getVectorArrayPrimitive() {
        return VectorArrayPrimitive;
    }

    @Override
    public String toString() {
        return this.vectorStr;
    }

    /**
     * 在两个向量对象之间进行计算的函数，自从1.13版本开始支持该函数的调用，该函数中的计算并不会产生一个新的向量，而是将计算操作作用于原操作数中
     * <p>
     * The function that calculates between two vector objects supports the call of this function since version 1.13. The calculation in this function will not generate a new vector, but will apply the calculation operation to the original operand
     *
     * @param value        与当前向量一起进行计算的另一个向量对象。
     *                     <p>
     *                     Another vector object that is evaluated with the current vector.
     * @param ModifyCaller 计算操作作用对象的设置，该参数如果为true，那么计算时针对向量序列的修改操作将会直接作用到调用函数的向量中，反之将会作用到被操作数中。
     *                     <p>
     *                     The setting of the calculation operation action object. If this parameter is true, the modification of the vector sequence during calculation will directly affect the vector of the calling function, and vice versa.
     * @return 两个向量经过了按维度求和计算之后，被修改的向量对象
     */
    @Override
    public IntegerVector add(IntegerVector value, boolean ModifyCaller) {
        int[] doubles2 = value.VectorArrayPrimitive;
        if (this.VectorArrayPrimitive.length == doubles2.length) {
            StringBuilder stringBuilder = new StringBuilder(this.vectorStr.length() + 16);
            if (ModifyCaller) {
                for (int i = 0; i < this.VectorArrayPrimitive.length; i++) {
                    this.VectorArrayPrimitive[i] += doubles2[i];
                    stringBuilder.append(this.VectorArrayPrimitive[i]).append(',');
                }
                this.vectorStr = stringBuilder.toString();
                return this;
            } else {
                for (int i = 0; i < doubles2.length; i++) {
                    doubles2[i] += this.VectorArrayPrimitive[i];
                    stringBuilder.append(doubles2[i]).append(',');
                }
                this.vectorStr = stringBuilder.toString();
                return value;
            }
        } else {
            int numberOfDimensions1 = this.VectorArrayPrimitive.length;
            int numberOfDimensions2 = doubles2.length;
            throw new OperatorOperationException(
                    "'IntegerVector1 add IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + numberOfDimensions1 + "]，IntegerVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'IntegerVector1 add IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + numberOfDimensions1 + "], IntegerVector2=[" + numberOfDimensions2 + "]"
            );
        }
    }

    /**
     * 在两个向量对象之间进行计算的函数，自从1.13版本开始支持该函数的调用，该函数中的计算并不会产生一个新的向量，而是将计算操作作用于原操作数中
     * <p>
     * The function that calculates between two vector objects supports the call of this function since version 1.13. The calculation in this function will not generate a new vector, but will apply the calculation operation to the original operand
     *
     * @param value        与当前向量一起进行计算的另一个向量对象。
     *                     <p>
     *                     Another vector object that is evaluated with the current vector.
     * @param ModifyCaller 计算操作作用对象的设置，该参数如果为true，那么计算时针对向量序列的修改操作将会直接作用到调用函数的向量中，反之将会作用到被操作数中。
     *                     <p>
     *                     The setting of the calculation operation action object. If this parameter is true, the modification of the vector sequence during calculation will directly affect the vector of the calling function, and vice versa.
     * @return 两个向量经过了按维度的减法计算之后，被修改的向量对象
     */
    @Override
    public IntegerVector diff(IntegerVector value, boolean ModifyCaller) {
        int[] doubles2 = value.VectorArrayPrimitive;
        if (this.VectorArrayPrimitive.length == doubles2.length) {
            StringBuilder stringBuilder = new StringBuilder(this.vectorStr.length() + 16);
            if (ModifyCaller) {
                for (int i = 0; i < this.VectorArrayPrimitive.length; i++) {
                    this.VectorArrayPrimitive[i] -= doubles2[i];
                    stringBuilder.append(this.VectorArrayPrimitive[i]).append(',');
                }
                this.vectorStr = stringBuilder.toString();
                return this;
            } else {
                for (int i = 0; i < doubles2.length; i++) {
                    doubles2[i] = this.VectorArrayPrimitive[i] - doubles2[i];
                    stringBuilder.append(doubles2[i]).append(',');
                }
                this.vectorStr = stringBuilder.toString();
                return value;
            }
        } else {
            int numberOfDimensions1 = this.VectorArrayPrimitive.length;
            int numberOfDimensions2 = doubles2.length;
            throw new OperatorOperationException(
                    "'IntegerVector1 diff IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + numberOfDimensions1 + "]，IntegerVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'IntegerVector1 diff IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + numberOfDimensions1 + "], IntegerVector2=[" + numberOfDimensions2 + "]"
            );
        }
    }

    /**
     * 在两个向量对象之间进行计算的函数，自从1.13版本开始支持该函数的调用，该函数中的计算并不会产生一个新的向量，而是将计算操作作用于原操作数中
     * <p>
     * The function that calculates between two vector objects supports the call of this function since version 1.13. The calculation in this function will not generate a new vector, but will apply the calculation operation to the original operand
     *
     * @param value        与当前向量一起进行计算的另一个向量对象。
     *                     <p>
     *                     Another vector object that is evaluated with the current vector.
     * @param ModifyCaller 计算操作作用对象的设置，该参数如果为true，那么计算时针对向量序列的修改操作将会直接作用到调用函数的向量中，反之将会作用到被操作数中。
     *                     <p>
     *                     The setting of the calculation operation action object. If this parameter is true, the modification of the vector sequence during calculation will directly affect the vector of the calling function, and vice versa.
     * @return 两个向量经过了外积计算之后，被修改的向量对象
     */
    @Override
    public IntegerVector multiply(IntegerVector value, boolean ModifyCaller) {
        int[] ints2 = value.VectorArrayPrimitive;
        if (this.VectorArrayPrimitive.length == ints2.length) {
            int[] res = new int[ints2.length];
            ASMath.CrossMultiplication(this.VectorArrayPrimitive.length, ints2.length, res, this.VectorArrayPrimitive, ints2);
            if (ModifyCaller) {
                this.VectorArrayPrimitive = res;
                this.vectorStr = Arrays.toString(res);
                return this;
            } else {
                value.VectorArrayPrimitive = res;
                value.vectorStr = Arrays.toString(res);
                return value;
            }
        } else {
            int length1 = this.VectorArrayPrimitive.length;
            int length2 = ints2.length;
            throw new OperatorOperationException(
                    "'IntegerVector1 multiply IntegerVector2' 时，两个'IntegerVector'的向量所包含的数量不同，IntegerVector1=[" + length1 + "]，IntegerVector2=[" + length2 + "]\n" +
                            "When 'IntegerVector1 multiply IntegerVector2', the two vectors of 'IntegerVector' contain different quantities, IntegerVector1=[" + length1 + "], IntegerVector2=[" + length2 + "]"
            );
        }
    }
}
