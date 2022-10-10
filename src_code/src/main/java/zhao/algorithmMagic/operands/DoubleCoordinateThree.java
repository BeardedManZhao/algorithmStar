package zhao.algorithmMagic.operands;

/**
 * Java类于 2022/10/10 12:37:50 创建
 * Double类型的三维坐标
 *
 * @author 4
 */
public class DoubleCoordinateThree implements Operands<DoubleCoordinateThree> {

    private final double x;
    private final double y;
    private final double z;

    public DoubleCoordinateThree(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
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
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public DoubleCoordinateThree diff(DoubleCoordinateThree value) {
        return new DoubleCoordinateThree(this.x - value.getX(), this.y - value.getY(), this.z - value.getZ());
    }
}
