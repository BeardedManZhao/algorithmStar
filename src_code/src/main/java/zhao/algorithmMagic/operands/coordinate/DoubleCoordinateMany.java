package zhao.algorithmMagic.operands.coordinate;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASClass;

/**
 * Java类于 2022/10/10 12:51:29 创建
 * Double类型的多维坐标，该坐标点的每一个坐标轴数值都是double类型，该坐标点是一个final，如果您需要实现一个属于您自己的坐标，您可以实现"FloatingPointCoordinates"接口。
 * <p>
 * Many-dimensional coordinates of type Double, each axis value of the coordinate point is of type double, the coordinate point is a final, if you need to implement a coordinate of your own, you can implement the "Floating Point Coordinates" interface.
 *
 * @author zhao
 */
public final class DoubleCoordinateMany implements FloatingPointCoordinates<DoubleCoordinateMany> {

    private final double[] coordinate;
    private final String str;

    /**
     * @param coordinate 一个多维坐标
     */
    public DoubleCoordinateMany(double... coordinate) {
        this.coordinate = coordinate;
        str = "(" + this.coordinate[0] + ",...," + this.coordinate[this.coordinate.length - 1] + ')';
    }

    public DoubleCoordinateMany(int[] coordinate) {
        this.coordinate = ASClass.IntArray_To_DoubleArray(coordinate);
        str = "(" + this.coordinate[0] + ",...," + this.coordinate[this.coordinate.length - 1] + ')';
    }

    /**
     * @return 该坐标点的维度数量，一般情况下，只有相同维度数量的两个坐标才能进行加减运算！
     */
    @Override
    public int getNumberOfDimensions() {
        return this.coordinate.length;
    }

    @Override
    public double[] toArray() {
        return coordinate;
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public DoubleCoordinateMany extend() {
        return this;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * 两个坐标之间的每一个轴的数据之和
     * <p>
     * Sum of data for each axis between two coordinates
     */
    @Override
    public DoubleCoordinateMany add(DoubleCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            double[] res = new double[this.getNumberOfDimensions()];
            double[] coordinate = value.toArray();
            for (int n = 0; n < this.coordinate.length; n++) {
                res[n] = this.coordinate[n] + coordinate[n];
            }
            return new DoubleCoordinateMany(res);
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
     * <p>
     * 两个坐标之间的每一个轴的数据之和
     * <p>
     * diff of data for each axis between two coordinates
     */
    @Override
    public DoubleCoordinateMany diff(DoubleCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            double[] res = new double[this.getNumberOfDimensions()];
            double[] coordinate = value.toArray();
            for (int n = 0; n < this.coordinate.length; n++) {
                res[n] = this.coordinate[n] - coordinate[n];
            }
            return new DoubleCoordinateMany(res);
        } else {
            throw new OperatorOperationException("'DoubleCoordinateMany1 diff DoubleCoordinateMany2' 的时候发生了错误，两个坐标的维度数量不同！\n" +
                    "An error occurred when 'DoubleCoordinateMany1 add DoubleCoordinateMany2', the two coordinates have different number of dimensions!\n" +
                    "number of dimensions => DoubleCoordinateMany1:[" + this.coordinate.length + "]\tDoubleCoordinateMany1:[" + value.coordinate.length + "]");
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
    public DoubleCoordinateMany add(Number value) {
        double[] res = new double[this.getNumberOfDimensions()];
        double v = value.doubleValue();
        for (int n = 0; n < this.coordinate.length; n++) {
            res[n] = this.coordinate[n] + v;
        }
        return new DoubleCoordinateMany(res);
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
    public DoubleCoordinateMany diff(Number value) {
        double[] res = new double[this.getNumberOfDimensions()];
        double v = value.doubleValue();
        for (int n = 0; n < this.coordinate.length; n++) {
            res[n] = this.coordinate[n] - v;
        }
        return new DoubleCoordinateMany(res);
    }

    /**
     * 将当前对象转换成为其子类实现，其具有强大的类型拓展效果，能够实现父类到子类的转换操作。
     * <p>
     * Transforming the current object into its subclass implementation has a powerful type extension effect, enabling the conversion operation from parent class to subclass.
     *
     * @return 当前类对应的子类实现数据类型的对象。
     * <p>
     * The subclass corresponding to the current class implements objects of data type.
     */
    @Override
    public DoubleCoordinateMany expand() {
        return this;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
