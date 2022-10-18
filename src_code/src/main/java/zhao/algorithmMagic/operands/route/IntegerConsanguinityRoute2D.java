package zhao.algorithmMagic.operands.route;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.DistanceAlgorithm;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.vector.DoubleVector;

/**
 * Java类于 2022/10/16 12:56:44 创建
 * <p>
 * 血亲2维坐标路线，相较于多维坐标而言，这种坐标在计算时的性能更好，即使多维坐标也是两个维度的情况。
 * <p>
 * The 2-dimensional coordinate route of blood relatives has better performance in calculation than multidimensional coordinates, even if multidimensional coordinates are two-dimensional.
 *
 * @author zhao
 */
public class IntegerConsanguinityRoute2D implements Route2D<IntegerConsanguinityRoute2D, IntegerCoordinateTwo>, NameRoute<IntegerConsanguinityRoute2D, IntegerCoordinateTwo> {

    private final String StartingCoordinateName;
    private final String EndPointCoordinateName;
    private final IntegerCoordinateTwo StartingCoordinate;
    private final IntegerCoordinateTwo EndPointCoordinate;
    private final DoubleVector doubleVector;

    protected IntegerConsanguinityRoute2D(String startingCoordinateName, String endPointCoordinateName, IntegerCoordinateTwo startingCoordinate, IntegerCoordinateTwo endPointCoordinate) {
        double numberOfDimensions1 = startingCoordinate.getNumberOfDimensions();
        double numberOfDimensions2 = endPointCoordinate.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            StartingCoordinate = startingCoordinate;
            EndPointCoordinate = endPointCoordinate;
            StartingCoordinateName = startingCoordinateName;
            EndPointCoordinateName = endPointCoordinateName;
            doubleVector = DoubleVector.parse(this.EndPointCoordinate.getX() - this.StartingCoordinate.getX(), this.EndPointCoordinate.getY() - this.StartingCoordinate.getY());
        } else {
            throw new OperatorOperationException("您在构造血亲坐标的时候发生了异常，具有血亲的起始坐标与终止坐标的维度数量不一致！\n" +
                    "An exception occurred when you constructed the blood relative coordinates, the number of dimensions of the starting coordinates with blood relatives and the ending coordinates are inconsistent!\n" +
                    "Dimensions of two coordinates => startingCoordinate[" + numberOfDimensions1 + "]  endPointCoordinate[" + numberOfDimensions2 + "]");
        }
    }

    /**
     * 构建一个血亲坐标
     *
     * @param CoordinatePath     坐标路径 描述坐标起始点的一个字符串
     * @param startingCoordinate 起始坐标对象
     * @param endPointCoordinate 终止坐标对象
     * @return 血亲坐标
     */
    public static IntegerConsanguinityRoute2D parse(String CoordinatePath, IntegerCoordinateTwo startingCoordinate, IntegerCoordinateTwo endPointCoordinate) {
        String[] split = CoordinatePath.split("\\s*->\\s*");
        if (split.length == 2) {
            return new IntegerConsanguinityRoute2D(split[0].trim(), split[1].trim(), startingCoordinate, endPointCoordinate);
        } else {
            throw new OperatorOperationException("您传入的坐标路径无法被成功解析哦！请按照[a -> b]格式进行CoordinatePath的设置！");
        }
    }

    /**
     * @return 起始坐标的名字
     */
    public String getStartingCoordinateName() {
        return StartingCoordinateName;
    }

    /**
     * @return 终止坐标的名字
     */
    public String getEndPointCoordinateName() {
        return EndPointCoordinateName;
    }

    /**
     * @return 起始坐标对象
     */
    public IntegerCoordinateTwo getStartingCoordinate() {
        return StartingCoordinate;
    }

    /**
     * @return 终止坐标对象
     */
    public IntegerCoordinateTwo getEndPointCoordinate() {
        return EndPointCoordinate;
    }

    /**
     * @return 两坐标之间的向量
     */
    public DoubleVector toDoubleVector() {
        return doubleVector;
    }

    /**
     * 使用某一个算法来计算该血缘坐标组成的向量的距离,具体计算逻辑与您传递的算法名称有关系!
     * <p>
     * Use a certain algorithm to calculate the distance of the vector composed of the bloodline coordinates. The specific calculation logic is related to the name of the algorithm you pass!
     *
     * @param algorithmName 计算时需要使用的算法组件.
     *                      <p>
     *                      Algorithm components to use when computing.
     * @return 血亲起始坐标组成的向量, 在不同算法中的距离
     * A vector composed of the starting coordinates of blood relatives, the distance in different algorithms.
     */
    public double getAlgorithmDistance(String algorithmName) {
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(algorithmName);
        if (operationAlgorithm instanceof DistanceAlgorithm) {
            return ((DistanceAlgorithm) operationAlgorithm).getTrueDistance(doubleVector);
        } else {
            throw new OperatorOperationException("您在血亲坐标中使用算法[" + algorithmName + "]的时候发生了异常，您提取的不属于距离算法！或者该算法没有注册!\n" +
                    "An exception occurred when you used the algorithm [" + algorithmName + "] in the blood relative coordinates, and what you extracted does not belong to the distance algorithm! Or the algorithm is not registered!");
        }
    }

    /**
     * @return 该血亲坐标的维度数量，每一个坐标都有不同的维度，获取到所有维度的数量，有助于定位到坐标点的位置。
     * <p>
     * The number of dimensions of the blood relative coordinates, each coordinate has a different dimension, and obtaining the number of all dimensions is helpful for locating the position of the coordinate point.
     */
    @Override
    public int getNumberOfDimensions() {
        return this.StartingCoordinate.getNumberOfDimensions();
    }

    /**
     * @return 子类实现, 用于在父类与子类之间进行转换的一个显示函数, 直接返回this 即可
     * <p>
     * Subclass implementation, a display function used to convert between parent class and subclass, just return this directly
     */
    @Override
    public IntegerConsanguinityRoute2D expand() {
        return null;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * @apiNote 这里就是将两个血亲坐标的起始坐标进行对应的加法.
     */
    @Override
    public IntegerConsanguinityRoute2D add(IntegerConsanguinityRoute2D value) {
        return IntegerConsanguinityRoute2D.parse(
                this.StartingCoordinateName + " + " + value.StartingCoordinateName + " -> " + this.EndPointCoordinateName + " + " + value.EndPointCoordinateName,
                this.StartingCoordinate.add(value.StartingCoordinate), this.EndPointCoordinate.add(value.EndPointCoordinate)
        );
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
    public IntegerConsanguinityRoute2D diff(IntegerConsanguinityRoute2D value) {
        return IntegerConsanguinityRoute2D.parse(
                this.StartingCoordinateName + " - " + value.StartingCoordinateName + " -> " + this.EndPointCoordinateName + " - " + value.EndPointCoordinateName,
                this.StartingCoordinate.diff(value.StartingCoordinate), this.EndPointCoordinate.diff(value.EndPointCoordinate)
        );
    }

    @Override
    public String toString() {
        return this.StartingCoordinateName + StartingCoordinate + " -> " + this.EndPointCoordinateName + EndPointCoordinate;
    }
}
