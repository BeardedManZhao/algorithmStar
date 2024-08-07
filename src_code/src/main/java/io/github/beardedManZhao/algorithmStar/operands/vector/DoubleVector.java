package io.github.beardedManZhao.algorithmStar.operands.vector;

import io.github.beardedManZhao.algorithmStar.SerialVersionUID;
import io.github.beardedManZhao.algorithmStar.core.ASDynamicLibrary;
import io.github.beardedManZhao.algorithmStar.exception.AlgorithmMagicException;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateThree;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.DoubleCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;

import java.util.Arrays;
import java.util.Random;

/**
 * Java类于 2022/10/9 18:50:31 创建
 * <p>
 * 浮点向量,其中的每一个序列元素都是Double类型的数值
 * <p>
 * A floating-point vector, where each sequence element is a value of type Double
 *
 * @author zhao
 */
public class DoubleVector extends ASVector<DoubleVector, Double, double[]> {
    private static final long serialVersionUID = SerialVersionUID.DOUBLE_VECTOR.getNum();
    protected double[] VectorArrayPrimitive;
    private String vectorStr;
    private double moduleLength;

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
        this.VectorArrayPrimitive = vectorArray;
        reFresh();
    }

    protected DoubleVector(double[] vectorArrayPrimitive, String vectorStr, double moduleLength) {
        VectorArrayPrimitive = vectorArrayPrimitive;
        this.vectorStr = vectorStr;
        this.moduleLength = moduleLength;
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
        return new DoubleVector(Arrays.stream(doubles).mapToDouble(Double::doubleValue).toArray());
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
     * 使用坐标构成的线计算出来向量
     *
     * @param startDoubleCoordinateThree 向量的起始坐标
     * @param endDoubleCoordinateThree   向量的终止坐标
     * @return 向量
     */
    public static DoubleVector parse(DoubleCoordinateThree startDoubleCoordinateThree, DoubleCoordinateThree endDoubleCoordinateThree) {
        return DoubleVector.parse(endDoubleCoordinateThree.getX() - startDoubleCoordinateThree.getX(), endDoubleCoordinateThree.getY() - startDoubleCoordinateThree.getY());
    }

    /**
     * 使用坐标构成的线计算出来向量
     *
     * @param startDoubleCoordinateMany 向量的起始坐标
     * @param endDoubleCoordinateMany   向量的终止坐标
     * @return 向量
     */
    public static DoubleVector parse(DoubleCoordinateMany startDoubleCoordinateMany, DoubleCoordinateMany endDoubleCoordinateMany) {
        return DoubleVector.parse((endDoubleCoordinateMany.diff(startDoubleCoordinateMany.extend())).toArray());
    }

    /**
     * 将矩阵中的所有数据扁平化成为一个数组。
     * <p>
     * Flatten all data in the matrix into an array.
     *
     * @param doubleMatrix 需要被解析的矩阵对象。
     * @return 将矩阵中所有行扁平化之后的数组对象。
     * <p>
     * The array object after flattening all rows in the matrix
     */
    public static DoubleVector parse(DoubleMatrix doubleMatrix) {
        double[] res = new double[doubleMatrix.getNumberOfDimensions()];
        int count = -1;
        for (double[] ints : doubleMatrix.toArrays()) {
            for (double anInt : ints) {
                res[++count] = anInt;
            }
        }
        return parse(res);
    }

    public static DoubleVector parse(int[] flatten) {
        double[] res = new double[flatten.length];
        int count = -1;
        for (int i : flatten) {
            res[++count] = i;
        }
        return parse(res);
    }

    /**
     * 使用随机生成的方式创建出来一个向量对象。
     * <p>
     * Create a vector object using random generation.
     *
     * @param width    需要随机生成的长度。
     *                 <p>
     *                 The length that needs to be randomly generated.
     * @param random   生成向量时需要使用的随机数据生成器对象。
     *                 <p>
     *                 The random data generator object that needs to be used when generating vectors.
     * @param useFloat 浮点参数指定项，如果指定为true 代表随机生成操作中将会随机生成浮点数，反之则是整数。
     *                 <p>
     *                 The specified item of floating point parameter, if it is specified as true, means that Floating-point arithmetic number will be randomly generated in random generation operation, otherwise it is an integer.
     * @return 随机生成的向量对象。
     * <p>
     * Randomly generated vector objects.
     */
    public static DoubleVector random(int width, Random random, boolean useFloat) {
        double[] row = new double[width];
        if (useFloat) {
            for (int x = 0; x < width; x++) {
                row[x] = random.nextDouble();
            }
        } else {
            for (int x = 0; x < width; x++) {
                row[x] = random.nextInt();
            }
        }
        return parse(row);
    }

    /**
     * 使用随机生成的方式创建出来一个向量对象。
     * <p>
     * Create a vector object using random generation.
     *
     * @param width  需要随机生成的长度。
     *               <p>
     *               The length that needs to be randomly generated.
     * @param random 生成向量时需要使用的随机数据生成器对象。
     *               <p>
     *               The random data generator object that needs to be used when generating vectors.
     * @return 随机生成的向量对象。
     * <p>
     * Randomly generated vector objects.
     */
    public static DoubleVector random(int width, Random random) {
        return random(width, random, false);
    }

    /**
     * 将向量的字段进行刷新
     */
    @Override
    protected void reFresh() {
        double tempM = 0;
        StringBuilder stringBuilder = new StringBuilder(this.VectorArrayPrimitive.length << 1);
        stringBuilder.append('[');
        for (double v : this.VectorArrayPrimitive) {
            tempM += v * v; // 刷新模长
            stringBuilder.append(' ').append(v); // 刷新字符串形式
        }
        stringBuilder.append(' ').append(']');
        this.moduleLength = Math.sqrt(tempM);
        this.vectorStr = stringBuilder.toString();
    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public final double[] toArray() {
        return this.VectorArrayPrimitive;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 这里按照向量的坐标将每一项进行了求和，实现了向量的相加。
     * <p>
     * Here, each item is summed according to the coordinates of the vector, and the addition of the vector is realized.
     */
    @Override
    public DoubleVector add(DoubleVector value) {
        int numberOfDimensions1 = this.getNumberOfDimensions();
        int numberOfDimensions2 = value.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            double[] res = new double[numberOfDimensions1];
            double[] doubles1 = this.toArray();
            double[] doubles2 = value.toArray();
            for (int i = 0; i < numberOfDimensions1; i++) {
                res[i] = doubles1[i] + doubles2[i];
            }
            return DoubleVector.parse(res);
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 add DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'DoubleVector1 add DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]"
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
     * 这里按照向量的坐标将每一项进行了做差，实现了向量的相减法。
     * <p>
     * Here, each item is subtracted according to the coordinates of the vector, and the subtraction method of the vector is realized.
     */
    @Override
    public DoubleVector diff(DoubleVector value) {
        int numberOfDimensions1 = this.getNumberOfDimensions();
        int numberOfDimensions2 = value.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            double[] res = new double[numberOfDimensions1];
            double[] doubles1 = this.toArray();
            double[] doubles2 = value.toArray();
            for (int i = 0; i < numberOfDimensions1; i++) {
                res[i] = doubles1[i] - doubles2[i];
            }
            return DoubleVector.parse(res);
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]"
            );
        }
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
    public DoubleVector add(Number value) {
        double[] res = this.copyToNewArray();
        double v = value.doubleValue();
        for (int i = 0; i < res.length; i++) {
            res[i] += v;
        }
        return DoubleVector.parse(res);
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
    public DoubleVector diff(Number value) {
        double[] res = this.copyToNewArray();
        double v = value.doubleValue();
        for (int i = 0; i < res.length; i++) {
            res[i] -= v;
        }
        return DoubleVector.parse(res);
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * 向量的模长计算函数。
     */
    @Override
    public Double moduleLength() {
        return this.moduleLength;
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
     * 向量的外积计算函数。
     */
    @Override
    public DoubleVector multiply(DoubleVector vector) {
        double[] vectorArray1 = this.toArray();
        double[] vectorArray2 = vector.toArray();
        int length1 = vectorArray1.length;
        int length2 = vectorArray2.length;
        if (length1 == length2) {
            return DoubleVector.parse(ASMath.CrossMultiplication(vectorArray1, vectorArray2));
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
     * 向量的内积计算函数。
     */
    @Override
    public Double innerProduct(DoubleVector vector) {
        double[] doubles1 = this.toArray();
        double[] doubles2 = vector.toArray();
        if (doubles1.length == doubles2.length) {
            double innerProduct = 0b0;
            for (int indexNum = 0b0; indexNum < doubles1.length; indexNum++) {
                innerProduct += doubles1[indexNum] * doubles2[indexNum];
            }
            return innerProduct;
        } else {
            throw new OperatorOperationException(
                    "'DoubleVector1 innerProduct DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + doubles1.length + "]，DoubleVector2=[" + doubles2.length + "]\n" +
                            "When 'DoubleVector1 innerProduct DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + doubles1.length + "], DoubleVector2=[" + doubles2.length + "]"
            );
        }
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public DoubleVector expand() {
        return this;
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public double[] copyToNewArray() {
        double[] res = new double[this.VectorArrayPrimitive.length];
        System.arraycopy(toArray(), 0, res, 0, this.VectorArrayPrimitive.length);
        return res;
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
        return DoubleVector.parse(ASMath.shuffle(this.toArray(), seed, true));
    }

    /**
     * @return 当前对象或类的序列化数值，相同类型的情况下该数值是相同的。
     * <p>
     * The serialized value of the current object or class, which is the same for the same type.
     */
    @Override
    public long getSerialVersionUID() {
        return serialVersionUID;
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
    public DoubleVector add(DoubleVector value, boolean ModifyCaller) {
        double[] doubles1 = this.VectorArrayPrimitive;
        double[] doubles2 = value.VectorArrayPrimitive;
        if (doubles1.length == doubles2.length) {
            if (ModifyCaller) {
                for (int i = 0; i < doubles1.length; i++) {
                    doubles1[i] += doubles2[i];
                }
                this.reFresh();
                return this;
            } else {
                for (int i = 0; i < doubles2.length; i++) {
                    doubles2[i] += doubles1[i];
                }
                value.reFresh();
                return value;
            }
        } else {
            int numberOfDimensions1 = doubles1.length;
            int numberOfDimensions2 = doubles2.length;
            throw new OperatorOperationException(
                    "'DoubleVector1 add DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'DoubleVector1 add DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]"
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
    public DoubleVector diff(DoubleVector value, boolean ModifyCaller) {
        double[] doubles1 = this.VectorArrayPrimitive;
        double[] doubles2 = value.VectorArrayPrimitive;
        if (doubles1.length == doubles2.length) {
            if (ModifyCaller) {
                for (int i = 0; i < doubles1.length; i++) {
                    doubles1[i] -= doubles2[i];
                }
                this.reFresh();
                return this;
            } else {
                for (int i = 0; i < doubles2.length; i++) {
                    doubles2[i] = doubles1[i] - doubles2[i];
                }
                value.reFresh();
                return value;
            }
        } else {
            int numberOfDimensions1 = doubles1.length;
            int numberOfDimensions2 = doubles2.length;
            throw new OperatorOperationException(
                    "'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]"
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
    public DoubleVector diffAbs(DoubleVector value, boolean ModifyCaller) {
        double[] doubles1 = this.VectorArrayPrimitive;
        double[] doubles2 = value.VectorArrayPrimitive;
        if (doubles1.length == doubles2.length) {
            if (ModifyCaller) {
                for (int i = 0; i < doubles1.length; i++) {
                    doubles1[i] = ASMath.absoluteValue(doubles1[i] - doubles2[i]);
                }
                this.reFresh();
                return this;
            } else {
                for (int i = 0; i < doubles2.length; i++) {
                    doubles2[i] = ASMath.absoluteValue(doubles1[i] - doubles2[i]);
                }
                value.reFresh();
                return value;
            }
        } else {
            int numberOfDimensions1 = doubles1.length;
            int numberOfDimensions2 = doubles2.length;
            throw new OperatorOperationException(
                    "'DoubleVector1 diff DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + numberOfDimensions1 + "]，DoubleVector2=[" + numberOfDimensions2 + "]\n" +
                            "When 'DoubleVector1 diff DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + numberOfDimensions1 + "], DoubleVector2=[" + numberOfDimensions2 + "]"
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
    public DoubleVector multiply(DoubleVector value, boolean ModifyCaller) {
        double[] doubles1 = this.VectorArrayPrimitive;
        double[] doubles2 = value.VectorArrayPrimitive;
        if (doubles1.length == doubles2.length) {
            double[] res = new double[doubles1.length];
            if (ASDynamicLibrary.isUseC()) {
                ASMath.CrossMultiplication_C(doubles1.length, doubles2.length, res, res.length, doubles1, doubles2);
            } else {
                ASMath.CrossMultiplication(doubles1.length, doubles2.length, res, doubles1, doubles2);
            }
            if (ModifyCaller) {
                this.VectorArrayPrimitive = res;
                this.reFresh();
                return this;
            } else {
                value.VectorArrayPrimitive = res;
                value.reFresh();
                return value;
            }
        } else {
            int length1 = doubles1.length;
            int length2 = doubles2.length;
            throw new OperatorOperationException(
                    "'DoubleVector1 multiply DoubleVector2' 时，两个'DoubleVector'的向量所包含的数量不同，DoubleVector1=[" + length1 + "]，DoubleVector2=[" + length2 + "]\n" +
                            "When 'DoubleVector1 multiply DoubleVector2', the two vectors of 'DoubleVector' contain different quantities, DoubleVector1=[" + length1 + "], DoubleVector2=[" + length2 + "]"
            );
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
    public DoubleVector leftShift(int n, boolean copy) {
        if (copy) {
            double[] ints = toArray();
            if (n < 0) {
                try {
                    return (DoubleVector) this.clone();
                } catch (CloneNotSupportedException e) {
                    return DoubleVector.parse(copyToNewArray());
                }
            } else if (ints.length == 0 || n >= ints.length) return DoubleVector.parse(new double[ints.length]);
            else return DoubleVector.parse(ASMath.leftShiftNv(copyToNewArray(), n));
        } else {
            ASMath.leftShift(toArray(), n);
            reFresh();
            return this;
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
            double[] ints = toArray();
            if (n < 0) {
                try {
                    return (DoubleVector) this.clone();
                } catch (CloneNotSupportedException e) {
                    return DoubleVector.parse(copyToNewArray());
                }
            } else if (ints.length == 0 || n >= ints.length) return DoubleVector.parse(new double[ints.length]);
            else return DoubleVector.parse(ASMath.rightShiftNv(copyToNewArray(), n));
        } else {
            ASMath.rightShift(toArray(), n);
            reFresh();
            return this;
        }
    }

    /**
     * 将当前对象中的元素从左向右的方向进行元素索引为宗旨的反转，实现更多的效果。
     * <p>
     * Invert the element index of the current object from left to right to achieve more results.
     *
     * @param isCopy 如果设置为true 代表反转操作会作用到一个新数组中，并不会更改源数组中的元素位置。反之则是直接更改源数组。
     *               <p>
     *               If set to true, the inversion operation will be applied to a new array, and the position of the elements in the source array will not be changed. On the contrary, you can directly change the source array.
     * @return 被反转之后的对象，该对象的数据类型与函数调用者是一致的。
     * <p>
     * The data type of the reversed object is the same as that of the function caller.
     */
    @Override
    public DoubleVector reverseLR(boolean isCopy) {
        if (isCopy) {
            return DoubleVector.parse(ASMath.arrayReverse(this.copyToNewArray()));
        } else {
            ASMath.arrayReverse(this.toArray());
            reFresh();
            return this;
        }
    }
}
