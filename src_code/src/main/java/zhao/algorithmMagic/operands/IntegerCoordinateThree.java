package zhao.algorithmMagic.operands;

/**
 * Java类于 2022/10/10 11:51:38 创建
 *
 * @author 4
 */
public class IntegerCoordinateThree implements Operands<IntegerCoordinateThree> {

    private final int x;
    private final int y;
    private final int z;

    /**
     * 实例化一个坐标
     *
     * @param x 坐标的横轴
     * @param y 坐标的竖轴
     * @param z 坐标的纵轴
     */
    public IntegerCoordinateThree(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
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
    public IntegerCoordinateThree add(IntegerCoordinateThree value) {
        return new IntegerCoordinateThree(this.x + value.x, this.y + value.y, z);
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
    public IntegerCoordinateThree diff(IntegerCoordinateThree value) {
        return new IntegerCoordinateThree(this.x - value.x, this.y - value.y, z);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }
}
