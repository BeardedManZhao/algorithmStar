package zhao.algorithmMagic.operands;

import zhao.algorithmMagic.exception.OperatorOperationException;

/**
 * Java类于 2022/10/10 12:51:29 创建
 *
 * @author 4
 */
public class IntegerCoordinateMany implements Operands<IntegerCoordinateMany> {

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
    public int getNumberOfDimensions() {
        return this.coordinate.length;
    }

    public int[] getCoordinate() {
        return coordinate;
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
    public IntegerCoordinateMany add(IntegerCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            int[] res = new int[this.getNumberOfDimensions()];
            int[] coordinate = value.getCoordinate();
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
     * @apiNote There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerCoordinateMany diff(IntegerCoordinateMany value) {
        if (this.getNumberOfDimensions() == value.getNumberOfDimensions()) {
            int[] res = new int[this.getNumberOfDimensions()];
            int[] coordinate = value.getCoordinate();
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
}
