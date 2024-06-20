package zhao.algorithmMagic.algorithm.distanceAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.HashMap;

/**
 * 豪斯多夫距离（Hausdorff Distance）是在度量空间中任意两个集合之间定义的一种距离。
 * <p>
 * Hausdorff Distance is a distance defined between any two sets in the metric space.
 *
 * @param <I> 该算法能够处理的整数坐标类型。
 *            <p>
 *            The integer coordinate type that the algorithm can handle.
 * @param <D> 该算法能够处理的浮点坐标类型。
 *            <p>
 *            The Float coordinate type that the algorithm can handle.
 */
public class HausdorffDistance<I extends IntegerCoordinates<I> & Coordinate<I>, D extends FloatingPointCoordinates<?>> implements DistanceAlgorithm {

    protected final String AlgorithmName;

    protected HausdorffDistance() {
        this.AlgorithmName = "HausdorffDistance";
    }

    protected HausdorffDistance(String AlgorithmName) {
        this.AlgorithmName = AlgorithmName;
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @param <II> 该算法用来处理的整形坐标是什么数据类型
     *             <p>
     *             What data type is the integer coordinate used by this algorithm?
     * @param <DD> 该算法用来处理的浮点坐标是什么数据类型
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static <II extends IntegerCoordinates<II> & Coordinate<II>, DD extends FloatingPointCoordinates<?>> HausdorffDistance<II, DD> getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof HausdorffDistance<?, ?>) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于HausdorffDistance类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the HausdorffDistance type. Please redefine a name for this algorithm.");
            }
        } else {
            HausdorffDistance<II, DD> HausdorffDistance = new HausdorffDistance<>(Name);
            OperationAlgorithmManager.getInstance().register(HausdorffDistance);
            return HausdorffDistance;
        }
    }

    /**
     * 获取到两个坐标点之间的豪斯多夫距离，也是两点之间的最短距离。
     * <p>
     * Obtain the Chebyshev distance between two coordinate points, which is also the shortest distance between two points.
     *
     * @param integerCoordinate1 起始坐标点
     *                           <p>
     *                           starting point
     * @param integerCoordinate2 终止坐标点
     *                           <p>
     *                           end point
     * @return 两个坐标之间的最短距离
     * <p>
     * shortest distance between two coordinates
     */
    public double getTrueDistance(IntegerCoordinates<I> integerCoordinate1, IntegerCoordinates<I> integerCoordinate2) {
        return getTrueDistance(integerCoordinate1.toArray(), integerCoordinate2.toArray());
    }

    /**
     * 获取到两个坐标点之间的豪斯多夫距离，也是两点之间的最短距离。
     * <p>
     * Obtain the Chebyshev distance between two coordinate points, which is also the shortest distance between two points.
     *
     * @param iFloatingPointCoordinates1 起始坐标点
     *                                   <p>
     *                                   starting point
     * @param iFloatingPointCoordinates2 终止坐标点
     *                                   <p>
     *                                   end point
     * @return 两个坐标之间的最短距离
     * <p>
     * shortest distance between two coordinates
     */
    public double getTrueDistance(FloatingPointCoordinates<D> iFloatingPointCoordinates1, FloatingPointCoordinates<D> iFloatingPointCoordinates2) {
        return getTrueDistance(iFloatingPointCoordinates1.toArray(), iFloatingPointCoordinates2.toArray());
    }

    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    @Override
    public boolean init() {
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getTrueDistance(DoubleConsanguinityRoute doubleConsanguinityRoute) {
        return getTrueDistance(doubleConsanguinityRoute.getStartingCoordinate().toArray(), doubleConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    /**
     * 计算两个坐标之间的单向豪斯多夫距离。
     * <p>
     * Calculate the one-way Hausdorff distance between two coordinates.
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return 从 double1 坐标到 double2 坐标之间的豪斯多夫距离。
     * <p>
     * Hausdorff distance from the double1 coordinate to the double2 coordinate.
     */
    @Override
    public double getTrueDistance(double[] doubles1, double[] doubles2) {
        if (doubles1.length == doubles2.length) {
            // 构建一个集合，用来存储每一个维度与另一坐标点所有维度的差值的最大数值
            HashMap<Double, Double> hashMap = new HashMap<>(doubles1.length);
            for (double a : doubles1) {
                for (double b : doubles2) {
                    // a点每一个坐标轴数值，各与b每一个坐标轴数值做差（距离），同时比较差值大小
                    double b1 = a - b;
                    // 如果当前的较大，就讲当前的值提供给该a的当前坐标轴，这样就算出来了 a坐标每一个轴与b坐标每一个轴差值的最大数值
                    if (hashMap.getOrDefault(a, 0.0) < b1) {
                        hashMap.put(a, b1);
                    }
                }
            }
            // 在最大差值数值集合中挑选出最大的数值，这个数值就是 A -> B的单向豪斯多夫距离
            double res = 0;
            for (Double value : hashMap.values()) {
                if (res < value) {
                    res = value;
                }
            }
            return res;
        } else {
            throw new OperatorOperationException("在您使用 c1 与 c2 进行豪斯多夫距离的获取时，发生了异常。\n" +
                    "When you use c1 and c2 to obtain the Hausdorff distance, an exception occurs.\n" +
                    "Number of dimensions => c1[" + doubles1.length + "]  c2[" + doubles2.length + "]");
        }
    }

    /**
     * 计算两个坐标之间的单向豪斯多夫距离。
     * <p>
     * Calculate the one-way Hausdorff distance between two coordinates.
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return 从 ints1 坐标到 ints2 坐标之间的豪斯多夫距离。
     * <p>
     * Hausdorff distance from the double1 coordinate to the double2 coordinate.
     */
    @Override
    public double getTrueDistance(int[] ints1, int[] ints2) {
        if (ints1.length == ints2.length) {
            // 构建一个集合，用来存储每一个维度与另一坐标点所有维度的差值的最大数值
            HashMap<Integer, Integer> hashMap = new HashMap<>(ints1.length);
            for (int a : ints1) {
                for (int b : ints2) {
                    // a点每一个坐标轴数值，各与b每一个坐标轴数值做差（距离），同时比较差值大小
                    int b1 = a - b;
                    // 如果当前的较大，就讲当前的值提供给该a的当前坐标轴，这样就算出来了 a坐标每一个轴与b坐标每一个轴差值的最大数值
                    if (hashMap.getOrDefault(a, 0) < b1) {
                        hashMap.put(a, b1);
                    }
                }
            }
            // 在最大差值数值集合中挑选出最大的数值，这个数值就是 A -> B的单向豪斯多夫距离
            double res = 0;
            for (int value : hashMap.values()) {
                if (res < value) {
                    res = value;
                }
            }
            return res;
        } else {
            throw new OperatorOperationException("在您使用 c1 与 c2 进行豪斯多夫距离的获取时，发生了异常。\n" +
                    "When you use c1 and c2 to obtain the Hausdorff distance, an exception occurs.\n" +
                    "Number of dimensions => c1[" + ints2.length + "]  c2[" + ints2.length + "]");
        }
    }

    @Override
    public double getTrueDistance(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        return getTrueDistance(doubleConsanguinityRoute2D.getStartingCoordinate().toArray(), doubleConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }

    @Override
    public double getTrueDistance(IntegerConsanguinityRoute integerConsanguinityRoute) {
        return getTrueDistance(integerConsanguinityRoute.getStartingCoordinate().toArray(), integerConsanguinityRoute.getEndPointCoordinate().toArray());
    }

    @Override
    public double getTrueDistance(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return getTrueDistance(integerConsanguinityRoute2D.getStartingCoordinate().toArray(), integerConsanguinityRoute2D.getEndPointCoordinate().toArray());
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param integerMatrix1 需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @param matrix2        需要被进行计算的矩阵对象。
     *                       <p>
     *                       The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(IntegerMatrix integerMatrix1, IntegerMatrix matrix2) {
        DistanceAlgorithm.checkMat(integerMatrix1, matrix2);
        int[][] mat2 = matrix2.toArrays();
        int[] res = new int[integerMatrix1.getNumberOfDimensions()];
        for (int[] ints : integerMatrix1) {
            for (int anInt : ints) {
                // 将 1 矩阵中的所有坐标点与 2 矩阵中的所有坐标点的距离的最大值计算出来
                int max = 0;
                for (int[] ints1 : mat2) {
                    for (int i : ints1) {
                        max = Math.max(anInt - i, max);
                    }
                }
            }
        }
        // 获取到 res 中的最大数值并返回
        return res[ASMath.findMaxIndex(res)];
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param matrix1 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @param matrix2 需要被进行计算的矩阵对象。
     *                <p>
     *                The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    @Override
    public double getTrueDistance(DoubleMatrix matrix1, DoubleMatrix matrix2) {
        DistanceAlgorithm.checkMat(matrix1, matrix2);
        double[][] mat2 = matrix2.toArrays();
        double[] res = new double[matrix1.getNumberOfDimensions()];
        for (double[] ints : matrix1) {
            for (double anInt : ints) {
                // 将 1 矩阵中的所有坐标点与 2 矩阵中的所有坐标点的距离的最大值计算出来
                double max = 0;
                for (double[] ints1 : mat2) {
                    for (double i : ints1) {
                        max = Math.max(anInt - i, max);
                    }
                }
            }
        }
        // 获取到 res 中的最大数值并返回
        return res[ASMath.findMaxIndex(res)];
    }
}
