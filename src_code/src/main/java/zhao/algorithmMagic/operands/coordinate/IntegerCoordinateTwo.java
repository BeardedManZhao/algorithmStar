package zhao.algorithmMagic.operands.coordinate;

/**
 * Java类于 2022/10/10 11:51:38 创建
 * <p>
 * 整数类型的二维坐标，该坐标点的每一个坐标轴数值都是整数类型，该坐标点是一个final，如果您需要实现一个属于您自己的坐标，您可以实现"IntegerCoordinates"接口。
 * <p>
 * Two-dimensional coordinates of integer type, each axis value of the coordinate point is of integer type, the coordinate point is a final, if you need to implement your own coordinates, you can implement the "Integer Coordinates" interface.
 *
 * @author zhao
 */
public final class IntegerCoordinateTwo implements IntegerCoordinates<IntegerCoordinateTwo> {

    private final int x;
    private final int y;

    /**
     * 实例化一个坐标
     *
     * @param x 坐标的横轴
     * @param y 坐标的纵轴
     */
    public IntegerCoordinateTwo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
    public IntegerCoordinateTwo add(IntegerCoordinateTwo value) {
        return new IntegerCoordinateTwo(this.x + value.x, this.y + value.y);
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
    public IntegerCoordinateTwo diff(IntegerCoordinateTwo value) {
        return new IntegerCoordinateTwo(this.x - value.x, this.y - value.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    /**
     * @return 该浮点坐标的维度数量，每一个坐标都有不同的维度，获取到所有维度的数量，有助于定位到坐标点的位置。
     * <p>
     * The number of dimensions of the floating-point coordinate, each coordinate has a different dimension, and obtaining the number of all dimensions is helpful for locating the position of the coordinate point.
     */
    @Override
    public int getNumberOfDimensions() {
        return 0b10;
    }

    /**
     * @return 该浮点坐标的数组形式，将浮点坐标转换成为一个数组返回出去，当然某些实现类可能会直接将数组作为一个对象的全局直接返回，这样有利于性能。
     * <p>
     * In the array form of the floating point coordinates, the floating point coordinates are converted into an array and returned. Of course, some implementation classes may directly return the array as a global object, which is beneficial to performance.
     */
    @Override
    public int[] toArray() {
        return new int[]{x, y};
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口成为其子类，这里一般只需要返回实现类对象即可。
     * <p>
     * The implementation class object of this class is used to expand the interface to become its subclass. Generally, only the implementation class object needs to be returned here.
     */
    @Override
    public IntegerCoordinateTwo expand() {
        return this;
    }
}
