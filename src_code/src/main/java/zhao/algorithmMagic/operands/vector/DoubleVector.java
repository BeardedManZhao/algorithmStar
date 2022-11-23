package zhao.algorithmMagic.operands.vector;

import zhao.algorithmMagic.exception.AlgorithmMagicException;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.utils.ASMath;

import java.util.Arrays;

/**
 * Java类于 2022/10/9 18:50:31 创建
 * <p>
 * 浮点向量,其中的每一个序列元素都是Double类型的数值
 * <p>
 * A floating-point vector, where each sequence element is a value of type Double
 *
 * @author zhao
 */
public class DoubleVector extends Vector<DoubleVector, Double> {

    private final boolean UsePrimitiveType;
    private double[] VectorArrayPrimitive;

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
        this.VectorArrayPrimitive = vectorArray;
        UsePrimitiveType = true;
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
     * @return 向量数据容器的数组形式，调用此方法，您可以获取到该向量中的数值。
     * <p>
     * 如果您使用的是基元类型的，那么该方法将会返回null，您可以使用"getVectorPrimitiveArray()"作为代替。
     * <p>
     * The array form of the vector data container, you can get the values in the vector by calling this method.
     * <p>
     * If you are using a primitive type then this method will return null, you can use "getVectorPrimitiveArray()" instead.
     */
    @Override
    public Double[] getVectorArrayPacking() {
        if (isUsePrimitiveType()) {
            return null;
        } else {
            return super.getVectorArrayPacking();
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
    protected void setVectorArrayPacking(Double[] vectorArray) {
        if (isUsePrimitiveType()) {
            this.VectorArrayPrimitive = new double[vectorArray.length];
            for (int n = 0; n < VectorArrayPrimitive.length; n++) {
                VectorArrayPrimitive[n] = vectorArray[n];
            }
        } else {
            super.setVectorArrayPacking(vectorArray);
        }
    }

    /**
     * @return 向量数据容器的数组形式，调用此方法，您可以获取到该向量中的数值。
     * <p>
     * 如果您使用的是包装类型的，那么该方法会返回null，您可以使用"getVectorArrayPacking()"作为代替。
     * <p>
     * The array form of the vector data container, you can get the values in the vector by calling this method.
     * <p>
     * If you are using a wrapper type then this method will return null, you can use "get Vector Array()" instead.
     */
    public double[] getVectorPrimitiveArray() {
        if (isUsePrimitiveType()) {
            return this.VectorArrayPrimitive;
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
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * 向量的模长计算函数。
     */
    @Override
    public Double moduleLength() {
        double res = 0;
        if (isUsePrimitiveType()) {
            for (double value : VectorArrayPrimitive) {
                res += value * value;
            }
        } else {
            for (double value : super.getVectorArrayPacking()) {
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
     * @return 是否使用基元类型，基元类型能更好地降低内存占用，如果您不使用基元，将会启动父类的数据容器
     * <p>
     * Whether to use primitive types, primitive types can better reduce memory usage, if you do not use primitives, the data container of the parent class will be started
     */
    @Override
    public boolean isUsePrimitiveType() {
        return UsePrimitiveType;
    }

    /**
     * @return 不论是基元还是包装，都返回一个基元的浮点数组，该方法是万能的，始终都会返回出来一个真正的向量数组！
     * <p>
     * Both primitives and wrappers return a floating-point array of primitives. This method is omnipotent and will always return a true vector array!
     */
    @Override
    public double[] toArray() {
        if (isUsePrimitiveType()) {
            double[] vectorPrimitiveArray = this.getVectorPrimitiveArray();
            return vectorPrimitiveArray != null ? vectorPrimitiveArray : new double[]{0x1.b7cdfd9d7bdbbp-34};
        } else {
            double[] doubles = new double[getNumberOfDimensions()];
            Double[] vectorArray = this.getVectorArrayPacking();
            if (vectorArray != null) {
                for (int n = 0; n < doubles.length; n++) {
                    doubles[n] = vectorArray[n];
                }
                return doubles;
            } else {
                return new double[]{0x1.b7cdfd9d7bdbbp-34};
            }
        }
    }

    /**
     * @return 向量中包含的维度数量
     * <p>
     * the number of dimensions contained in the vector
     */
    @Override
    public int getNumberOfDimensions() {
        return isUsePrimitiveType() ? this.VectorArrayPrimitive.length : super.getVectorArrayPacking().length;
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
            doubleVector.VectorArrayPrimitive = this.VectorArrayPrimitive;
        } else {
            doubleVector.setVectorArrayPacking(this.getVectorArrayPacking());
        }
    }

    @Override
    public String toString() {
        return "DoubleVector{" +
                "UsePrimitiveType=" + UsePrimitiveType +
                (isUsePrimitiveType() ?
                        ", Vector=" + Arrays.toString(VectorArrayPrimitive) + '}' :
                        ", Vector=" + Arrays.toString(getVectorArrayPacking()) + '}');
    }
}
