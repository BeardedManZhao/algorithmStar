package io.github.beardedManZhao.algorithmStar.operands.coordinate;

/**
 * Java类于 2022/10/10 11:51:38 创建
 * <p>
 * 整数类型的三维坐标，该坐标点的每一个坐标轴数值都是整数类型，该坐标点是一个final，如果您需要实现一个属于您自己的坐标，您可以实现"IntegerCoordinates"接口。
 * <p>
 * Three-dimensional coordinates of integer type, each axis value of the coordinate point is of integer type, the coordinate point is a final, if you need to implement your own coordinates, you can implement the "Integer Coordinates" interface.
 *
 * @author zhao
 */
public final class IntegerCoordinateThree implements IntegerCoordinates<IntegerCoordinateThree>, Coordinate<IntegerCoordinateThree> {

    private final int[] coordinate;
    private final String str;

    /**
     * 实例化一个坐标
     *
     * @param x 坐标的横轴
     * @param y 坐标的竖轴
     * @param z 坐标的纵轴
     */
    public IntegerCoordinateThree(int x, int y, int z) {
        this.coordinate = new int[]{x, y, z};
        str = "(" + this.coordinate[0] + "," + this.coordinate[1] + "," + this.coordinate[2] + ")";
    }

    public int getX() {
        return this.coordinate[0];
    }

    public int getY() {
        return this.coordinate[1];
    }

    public int getZ() {
        return this.coordinate[2];
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
    public IntegerCoordinateThree add(IntegerCoordinateThree value) {
        return new IntegerCoordinateThree(
                this.coordinate[0] + value.coordinate[0],
                this.coordinate[1] + value.coordinate[1],
                this.coordinate[2] + value.coordinate[2]
        );
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * <p>
     * 两个坐标之间的每一个轴的数据之差
     * <p>
     * diff of data for each axis between two coordinates
     */
    @Override
    public IntegerCoordinateThree diff(IntegerCoordinateThree value) {
        return new IntegerCoordinateThree(
                this.coordinate[0] - value.coordinate[0],
                this.coordinate[1] - value.coordinate[1],
                this.coordinate[2] - value.coordinate[2]
        );
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
    public IntegerCoordinateThree add(Number value) {
        int v = value.intValue();
        return new IntegerCoordinateThree(
                this.coordinate[0] + v,
                this.coordinate[1] + v,
                this.coordinate[2] + v
        );
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
    public IntegerCoordinateThree diff(Number value) {
        int v = value.intValue();
        return new IntegerCoordinateThree(
                this.coordinate[0] - v,
                this.coordinate[1] - v,
                this.coordinate[2] - v
        );
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
    public IntegerCoordinateThree expand() {
        return this;
    }

    @Override
    public String toString() {
        return this.str;
    }

    /**
     * @return 该浮点坐标的维度数量，每一个坐标都有不同的维度，获取到所有维度的数量，有助于定位到坐标点的位置。
     * <p>
     * The number of dimensions of the floating-point coordinate, each coordinate has a different dimension, and obtaining the number of all dimensions is helpful for locating the position of the coordinate point.
     */
    @Override
    public int getNumberOfDimensions() {
        return 0b11;
    }

    /**
     * @return 该浮点坐标的数组形式，将浮点坐标转换成为一个数组返回出去，当然某些实现类可能会直接将数组作为一个对象的全局直接返回，这样有利于性能。
     * <p>
     * In the array form of the floating point coordinates, the floating point coordinates are converted into an array and returned. Of course, some implementation classes may directly return the array as a global object, which is beneficial to performance.
     */
    @Override
    public int[] toArray() {
        return this.coordinate;
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口成为其子类，这里一般只需要返回实现类对象即可。
     * <p>
     * The implementation class object of this class is used to extend the interface to become its subclass. Generally, only the implementation class object needs to be returned here.
     */
    @Override
    public IntegerCoordinateThree extend() {
        return this;
    }
}
