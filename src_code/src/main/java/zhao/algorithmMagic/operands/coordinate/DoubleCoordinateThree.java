package zhao.algorithmMagic.operands.coordinate;

/**
 * Java类于 2022/10/10 12:37:50 创建
 * Double类型的三维坐标，该坐标点的每一个坐标轴数值都是double类型，该坐标点是一个final，如果您需要实现一个属于您自己的坐标，您可以实现"FloatingPointCoordinates"接口。
 * <p>
 * Three-dimensional coordinates of type Double, each axis value of the coordinate point is of type double, the coordinate point is a final, if you need to implement a coordinate of your own, you can implement the "Floating Point Coordinates" interface.
 *
 * @author zhao
 */
public final class DoubleCoordinateThree implements FloatingPointCoordinates<DoubleCoordinateThree>, Coordinate3D<DoubleCoordinateThree, Double> {

    private final double x;
    private final double y;
    private final double z;
    private final String str;

    public DoubleCoordinateThree(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        str = "(" + this.x + ',' + this.y + ',' + this.z + ')';
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return this.str;
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
    public DoubleCoordinateThree add(DoubleCoordinateThree value) {
        return new DoubleCoordinateThree(this.x + value.getX(), this.y + value.getY(), this.z + value.getZ());
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
    public DoubleCoordinateThree diff(DoubleCoordinateThree value) {
        return new DoubleCoordinateThree(this.x - value.getX(), this.y - value.getY(), this.z - value.getZ());
    }

    /**
     * @return 该浮点坐标的维度数量，每一个坐标都有不同的维度，获取到所有维度的数量，有助于定位到坐标点的位置。
     * <p>
     * The number of dimensions of the floating-point coordinate, each coordinate has a different dimension, and obtaining the number of all dimensions is helpful for locating the position of the coordinate point.
     */
    @Override
    public int getNumberOfDimensions() {
        return 3;
    }

    /**
     * @return 该浮点坐标的数组形式，将浮点坐标转换成为一个数组返回出去，当然某些实现类可能会直接将数组作为一个对象的全局直接返回，这样有利于性能。
     * <p>
     * In the array form of the floating point coordinates, the floating point coordinates are converted into an array and returned. Of course, some implementation classes may directly return the array as a global object, which is beneficial to performance.
     */
    @Override
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public DoubleCoordinateThree extend() {
        return this;
    }
}
