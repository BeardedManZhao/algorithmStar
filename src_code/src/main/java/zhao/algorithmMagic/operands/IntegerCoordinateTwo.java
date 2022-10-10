package zhao.algorithmMagic.operands;

/**
 * Java类于 2022/10/10 11:51:38 创建
 *
 * @author 4
 */
public class IntegerCoordinateTwo implements Operands<IntegerCoordinateTwo> {

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
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
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
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerCoordinateTwo diff(IntegerCoordinateTwo value) {
        return new IntegerCoordinateTwo(this.x - value.x, this.y - value.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
