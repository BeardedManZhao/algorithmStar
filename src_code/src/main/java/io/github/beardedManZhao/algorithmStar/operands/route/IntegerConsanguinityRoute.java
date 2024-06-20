package io.github.beardedManZhao.algorithmStar.operands.route;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.DistanceAlgorithm;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateMany;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;

/**
 * Java类于 2022/10/15 09:49:48 创建
 * <p>
 * 血亲坐标，记录了两个坐标之间的始末关系，提供两个坐标之间距离与向量的计算。
 * <p>
 * The blood relative coordinates, which record the beginning and end relationship between the two coordinates, provide the calculation of the distance and the vector between the two coordinates.
 *
 * @author zhao
 */
public class IntegerConsanguinityRoute implements NameRoute<IntegerConsanguinityRoute, IntegerCoordinateMany> {

    private final String StartingCoordinateName;
    private final String EndPointCoordinateName;
    private final String RouteName;
    private final IntegerCoordinateMany StartingCoordinate;
    private final IntegerCoordinateMany EndPointCoordinate;

    protected IntegerConsanguinityRoute(String startingCoordinateName, String endPointCoordinateName, IntegerCoordinateMany startingCoordinate, IntegerCoordinateMany endPointCoordinate) {
        double numberOfDimensions1 = startingCoordinate.getNumberOfDimensions();
        double numberOfDimensions2 = endPointCoordinate.getNumberOfDimensions();
        if (numberOfDimensions1 == numberOfDimensions2) {
            StartingCoordinate = startingCoordinate;
            EndPointCoordinate = endPointCoordinate;
            this.RouteName = startingCoordinateName + " -> " + endPointCoordinateName;
            StartingCoordinateName = startingCoordinateName;
            EndPointCoordinateName = endPointCoordinateName;
        } else {
            throw new OperatorOperationException("您在构造血亲坐标的时候发生了异常，具有血亲的起始坐标与终止坐标的维度数量不一致！\n" +
                    "An exception occurred when you constructed the blood relative coordinates, the number of dimensions of the starting coordinates with blood relatives and the ending coordinates are inconsistent!\n" +
                    "Dimensions of two coordinates => startingCoordinate[" + numberOfDimensions1 + "]  endPointCoordinate[" + numberOfDimensions2 + "]");
        }
    }

    /**
     * 通过一个已经构造出来的线路对象构造出来新的线路对象，其中的数据直接浅拷贝于源路线对象。
     * <p>
     * A new route object is constructed from a constructed route object, and the data in it is directly and shallowly copied to the source route object.
     *
     * @param integerConsanguinityRoute 源路线对象，作为拷贝的来源。
     */
    protected IntegerConsanguinityRoute(IntegerConsanguinityRoute integerConsanguinityRoute) {
        this.StartingCoordinate = integerConsanguinityRoute.StartingCoordinate;
        this.EndPointCoordinate = integerConsanguinityRoute.EndPointCoordinate;
        this.StartingCoordinateName = integerConsanguinityRoute.StartingCoordinateName;
        this.EndPointCoordinateName = integerConsanguinityRoute.EndPointCoordinateName;
        this.RouteName = integerConsanguinityRoute.RouteName;
    }

    public static IntegerConsanguinityRoute parse(IntegerConsanguinityRoute integerConsanguinityRoute) {
        return new IntegerConsanguinityRoute(integerConsanguinityRoute);
    }

    /**
     * 构建一个血亲坐标
     *
     * @param CoordinatePath     坐标路径 描述坐标起始点的一个字符串
     * @param startingCoordinate 起始坐标对象
     * @param endPointCoordinate 终止坐标对象
     * @return 血亲坐标
     */
    public static IntegerConsanguinityRoute parse(String CoordinatePath, IntegerCoordinateMany startingCoordinate, IntegerCoordinateMany endPointCoordinate) {
        String[] split = PATH_SEPARATOR.split(CoordinatePath);
        if (split.length == 2) {
            return new IntegerConsanguinityRoute(split[0].trim(), split[1].trim(), startingCoordinate, endPointCoordinate);
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
     * 获取到线路的字符串表现形式
     * <p>
     * Get the string representation of the line
     *
     * @return 线路的字符窜名称。
     * <p>
     * The character name of the line..
     */
    @Override
    public String getRouteName() {
        return this.RouteName;
    }

    /**
     * @return 起始坐标对象
     */
    public IntegerCoordinateMany getStartingCoordinate() {
        return StartingCoordinate;
    }

    /**
     * @return 终止坐标对象
     */
    public IntegerCoordinateMany getEndPointCoordinate() {
        return EndPointCoordinate;
    }

    /**
     * @return 两坐标之间的向量
     */
    public DoubleVector toDoubleVector() {
        int[] ints1 = StartingCoordinate.toArray();
        int[] ints2 = EndPointCoordinate.toArray();
        double[] res = new double[ints1.length];
        for (int i = 0; i < ints1.length; i++) {
            res[i] = ints2[i] - ints1[i];
        }
        return DoubleVector.parse(res);
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
            return ((DistanceAlgorithm) operationAlgorithm).getTrueDistance(this);
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
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public IntegerConsanguinityRoute expand() {
        return this;
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * 这里就是将两个血亲坐标的起始坐标进行对应的加法.
     */
    @Override
    public IntegerConsanguinityRoute add(IntegerConsanguinityRoute value) {
        return IntegerConsanguinityRoute.parse(
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
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public IntegerConsanguinityRoute diff(IntegerConsanguinityRoute value) {
        return IntegerConsanguinityRoute.parse(
                this.StartingCoordinateName + " - " + value.StartingCoordinateName + " -> " + this.EndPointCoordinateName + " - " + value.EndPointCoordinateName,
                this.StartingCoordinate.diff(value.StartingCoordinate), this.EndPointCoordinate.diff(value.EndPointCoordinate)
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
    public IntegerConsanguinityRoute add(Number value) {
        return new IntegerConsanguinityRoute(
                this.StartingCoordinateName,
                this.EndPointCoordinateName,
                this.StartingCoordinate.add(value),
                this.EndPointCoordinate.add(value)
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
    public IntegerConsanguinityRoute diff(Number value) {
        return new IntegerConsanguinityRoute(
                this.StartingCoordinateName,
                this.EndPointCoordinateName,
                this.StartingCoordinate.diff(value),
                this.EndPointCoordinate.diff(value)
        );
    }

    @Override
    public String toString() {
        return this.StartingCoordinateName + StartingCoordinate + " -> " + this.EndPointCoordinateName + EndPointCoordinate;
    }
}
