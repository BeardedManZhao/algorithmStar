package zhao.algorithmMagic.operands.coordinate;

import zhao.algorithmMagic.exception.OperatorOperationException;

import java.util.Arrays;

/**
 * Java类于 2022/10/10 12:51:29 创建
 * <p>
 * 整数类型的多维坐标，该坐标点的每一个坐标轴数值都是整数类型，该坐标点是一个final，如果您需要实现一个属于您自己的坐标，您可以实现"IntegerCoordinates"接口。
 * <p>
 * Many-dimensional coordinates of integer type, each axis value of the coordinate point is of integer type, the coordinate point is a final, if you need to implement your own coordinates, you can implement the "Integer Coordinates" interface.
 *
 * @author 4
 */
public final class IntegerCoordinateMany implements IntegerCoordinates<IntegerCoordinateMany>, Coordinate<IntegerCoordinateMany> {

    private final int[] coordinate;

    /**
     * @param coordinate 一个多维坐标
     */
    public IntegerCoordinateMany(int... coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * @return 该坐标点的维度数量，一般情况下，只有相同维度数量的两个坐标才能进行加减运算！
     */
    @Override
    public int getNumberOfDimensions() {
        return this.coordinate.length;
    }

    @Override
    public int[] toArray() {
        return coordinate;
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口成为其子类，这里一般只需要返回实现类对象即可。
     * <p>
     * The implementation class object of this class is used to extend the interface to become its subclass. Generally, only the implementation class object needs to be returned here.
     */
    @Override
    public IntegerCoordinateMany extend() {
        return this;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @apiNote 两个坐标之间的每一个轴的数据之和
     * <p>
     * Sum of data for each axis between two coordinates
     */
    @Override
    public IntegerCoordinateMany add(IntegerCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            int[] res = new int[this.getNumberOfDimensions()];
            int[] coordinate = value.toArray();
            for (int n = 0; n < this.coordinate.length; n++) {
                res[n] = this.coordinate[n] + coordinate[n];
            }
            return new IntegerCoordinateMany(res);
        } else {
            throw new OperatorOperationException("'DoubleCoordinateMany1 add DoubleCoordinateMany2' 的时候发生了错误，两个坐标的维度数量不同！\n" +
                    "An error occurred when 'DoubleCoordinateMany1 add DoubleCoordinateMany2', the two coordinates have different number of dimensions!\n" +
                    "number of dimensions => DoubleCoordinateMany1:[" + this.coordinate.length + "]\tDoubleCoordinateMany1:[" + value.coordinate.length + "]");
        }
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * @apiNote 两个坐标之间的每一个轴的数据之和
     * <p>
     * diff of data for each axis between two coordinates
     */
    @Override
    public IntegerCoordinateMany diff(IntegerCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            int[] res = new int[this.getNumberOfDimensions()];
            int[] coordinate = value.toArray();
            for (int n = 0; n < this.coordinate.length; n++) {
                res[n] = this.coordinate[n] - coordinate[n];
            }
            return new IntegerCoordinateMany(res);
        } else {
            throw new OperatorOperationException("'DoubleCoordinateMany1 diff DoubleCoordinateMany2' 的时候发生了错误，两个坐标的维度数量不同！\n" +
                    "An error occurred when 'DoubleCoordinateMany1 add DoubleCoordinateMany2', the two coordinates have different number of dimensions!\n" +
                    "number of dimensions => DoubleCoordinateMany1:[" + this.coordinate.length + "]\tDoubleCoordinateMany1:[" + value.coordinate.length + "]");
        }
    }

    @Override
    public String toString() {
        return "(IntegerCoordinateMany=" + Arrays.toString(coordinate) + ")";
    }
}
