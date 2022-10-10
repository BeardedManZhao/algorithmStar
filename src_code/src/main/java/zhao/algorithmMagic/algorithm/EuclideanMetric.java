package zhao.algorithmMagic.algorithm;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.operands.*;
import zhao.algorithmMagic.utils.ASMath;

/**
 * Java类于 2022/10/10 11:32:08 创建
 * <p>
 * 欧几里得度量（euclidean metric）（也称欧氏距离）是一个通常采用的距离定义，指在m维空间中两个点之间的真实距离，或者向量的自然长度（即该点到原点的距离）。
 * <p>
 * 在二维和三维空间中的欧氏距离就是两点之间的实际距离。
 * <p>
 * Euclidean metric (also called Euclidean distance) is a commonly used definition of distance, which refers to the true distance between two points in m-dimensional space, or the natural length of a vector (that is, the point to the origin) the distance.
 * <p>
 * Euclidean distance in 2D and 3D space is the actual distance between two points.
 *
 * @author LingYuZhao
 */
public class EuclideanMetric implements OperationAlgorithm<EuclideanMetric> {
    protected final Logger logger;
    protected final String AlgorithmName;

    public EuclideanMetric(String algorithmName) {
        this.AlgorithmName = algorithmName;
        this.logger = Logger.getLogger(algorithmName);
        this.init();
    }

    /**
     * @return 该算法组件的名称，也可有是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component, or an identification code, you can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.AlgorithmName;
    }

    /**
     * @return 该算法的具体实现类，您可以通过该函数将算法从抽象转换为一个具体的实现
     * <p>
     * The concrete implementation class of the algorithm, through which you can convert the algorithm from an abstract to a concrete implementation
     */
    @Override
    public EuclideanMetric extract() {
        return this;
    }

    /**
     * 算法模块的初始化方法，在这里您可以进行组件的初始化方法，当初始化成功之后，该算法就可以处于就绪的状态，一般这里就是将自己添加到算法管理类中
     * <p>
     * The initialization method of the algorithm module, here you can perform the initialization method of the component, when the initialization is successful, the algorithm can be in a ready state, generally here is to add yourself to the algorithm management class
     *
     * @return 初始化成功或失败。
     * <p>
     * Initialization succeeded or failed.
     */
    @Override
    public boolean init() {
        if (OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算从自身坐标开始到原点的欧氏距离
     * <p>
     * Calculate the euclidean distance from own coordinates to the origin
     *
     * @param integerCoordinateTwo 二维坐标对象，其中坐标系为 "(x, y)"形式。
     *                             <p>
     *                             A two-dimensional coordinate object, where the coordinate system is of the form "(x, y)".
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(IntegerCoordinateTwo integerCoordinateTwo) {
        int vx = integerCoordinateTwo.getX();
        int vy = integerCoordinateTwo.getY();
        logger.info("√(" + vx + "² + " + vy + "²)");
        return Math.sqrt(ASMath.Power2(vx) + ASMath.Power2(vy));
    }

    /**
     * 计算从自身坐标开始到原点的欧氏距离
     * <p>
     * Calculate the euclidean distance from own coordinates to the origin
     *
     * @param integerCoordinateThree 三维坐标对象，其中坐标系为 "(x, y, z)"形式。
     *                               <p>
     *                               A three-dimensional coordinate object, where the coordinate system is of the form "(x, y, z)".
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(IntegerCoordinateThree integerCoordinateThree) {
        int vx = integerCoordinateThree.getX();
        int vy = integerCoordinateThree.getY();
        int vz = integerCoordinateThree.getZ();
        logger.info("√(" + vx + "² + " + vy + "² + " + vz + "²)");
        return Math.sqrt(ASMath.Power2(vx) + ASMath.Power2(vy) + ASMath.Power2(vz));
    }

    /**
     * 计算从自身坐标开始到原点的欧氏距离
     * <p>
     * Calculate the euclidean distance from own coordinates to the origin
     *
     * @param doubleCoordinateTwo 二维坐标对象，其中坐标系为 "(x, y)"形式。
     *                            <p>
     *                            A two-dimensional coordinate object, where the coordinate system is of the form "(x, y)".
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(DoubleCoordinateTwo doubleCoordinateTwo) {
        double vx = doubleCoordinateTwo.getX();
        double vy = doubleCoordinateTwo.getY();
        logger.info("√(" + vx + "² + " + vy + "²)");
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * 计算从自身坐标开始到原点的欧式距离
     *
     * @param doubleCoordinateThree 三维坐标对象，其中坐标系为 "(x, y, z)"形式。
     *                              <p>
     *                              A three-dimensional coordinate object, where the coordinate system is of the form "(x, y, z)".
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(DoubleCoordinateThree doubleCoordinateThree) {
        double vx = doubleCoordinateThree.getX();
        double vy = doubleCoordinateThree.getY();
        double vz = doubleCoordinateThree.getZ();
        logger.info("√(" + vx + "² + " + vy + "² + " + vz + "²)");
        return Math.sqrt(vx * vx + vy * vy + vz * vz);
    }

    /**
     * 二维平面之内，计算两个点之间的真实欧式距离
     *
     * @param integerCoordinateTwo1 欧式距离的起始二维坐标
     *                              <p>
     *                              The starting 2D coordinates of the Euclidean distance
     * @param integerCoordinateTwo2 欧式距离的终点二维坐标
     *                              <p>
     *                              2D coordinates of the end point of the Euclidean distance
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(IntegerCoordinateTwo integerCoordinateTwo1, IntegerCoordinateTwo integerCoordinateTwo2) {
        int vx1 = integerCoordinateTwo1.getX();
        int vy1 = integerCoordinateTwo1.getY();
        int vx2 = integerCoordinateTwo2.getX();
        int vy2 = integerCoordinateTwo2.getY();
        logger.info("√((" + vx1 + " - " + vx2 + ")² + (" + vy1 + " - " + vy2 + ")²)");
        return Math.sqrt(ASMath.Power2(vx1 - vx2) + ASMath.Power2(vy1 - vy2));
    }

    /**
     * 二维平面之内，计算两个点之间的真实欧式距离
     *
     * @param doubleCoordinateTwo1 欧式距离的起始二维坐标
     *                             <p>
     *                             The starting 2D coordinates of the Euclidean distance
     * @param doubleCoordinateTwo2 欧式距离的终点二维坐标
     *                             <p>
     *                             * 2D coordinates of the end point of the Euclidean distance
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(DoubleCoordinateTwo doubleCoordinateTwo1, DoubleCoordinateTwo doubleCoordinateTwo2) {
        double vx1 = doubleCoordinateTwo1.getX();
        double vy1 = doubleCoordinateTwo1.getY();
        double vx2 = doubleCoordinateTwo2.getX();
        double vy2 = doubleCoordinateTwo2.getY();
        logger.info("√((" + vx1 + " - " + vx2 + ")² + (" + vy1 + " - " + vy2 + ")²)");
        return Math.sqrt(vx1 * vx2 + vy1 * vy2);
    }

    /**
     * 三维平面之内，计算两个点之间的真实欧式距离
     *
     * @param integerCoordinateThree1 欧式距离的起始三维坐标
     *                                <p>
     *                                The starting 3D coordinates of the Euclidean distance
     * @param integerCoordinateThree2 欧式距离的终止三维坐标
     *                                <p>
     *                                Terminating 3D coordinate of Euclidean distance
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(IntegerCoordinateThree integerCoordinateThree1, IntegerCoordinateThree integerCoordinateThree2) {
        int vx1 = integerCoordinateThree1.getX();
        int vy1 = integerCoordinateThree1.getY();
        int vz1 = integerCoordinateThree1.getZ();
        int vx2 = integerCoordinateThree2.getX();
        int vy2 = integerCoordinateThree2.getY();
        int vz2 = integerCoordinateThree2.getZ();
        logger.info("√((" + vx1 + " - " + vx2 + ")² + (" + vy1 + " - " + vy2 + ")² + (" + vz1 + " - " + vz2 + ")²)");
        return Math.sqrt(
                ASMath.Power2(
                        vx1 - vx2
                ) + ASMath.Power2(
                        vy1 - vy2
                ) + ASMath.Power2(
                        vz1 - vz2
                )
        );
    }

    /**
     * 三维之内，计算两个点之间的真实欧式距离
     *
     * @param doubleCoordinateThree1 欧式距离的起始三维坐标
     *                               <p>
     *                               The starting 3D coordinates of the Euclidean distance
     * @param doubleCoordinateThree2 欧式距离的终止三维坐标
     *                               <p>
     *                               Terminating 3D coordinate of Euclidean distance
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(DoubleCoordinateThree doubleCoordinateThree1, DoubleCoordinateThree doubleCoordinateThree2) {
        double vx1 = doubleCoordinateThree1.getX();
        double vy1 = doubleCoordinateThree1.getY();
        double vz1 = doubleCoordinateThree1.getZ();
        double vx2 = doubleCoordinateThree2.getX();
        double vy2 = doubleCoordinateThree2.getY();
        double vz2 = doubleCoordinateThree2.getZ();
        logger.info("√((" + vx1 + " - " + vx2 + ")² + (" + vy1 + " - " + vy2 + ")² + (" + vz1 + " - " + vz2 + ")²)");
        return Math.sqrt(Math.pow(vx1 - vx2, 2) + Math.pow(vy1 - vy2, 2) + Math.pow(vz1 - vz2, 2));
    }

    /**
     * 多维空间之中，自身坐标点到原点的距离，这里是多维空间，所以是每一个维度的坐标点都会被进行2次方
     * <p>
     * In the multidimensional space, the distance from its own coordinate point to the origin, here is a multidimensional space, so the coordinate points of each dimension will be squared
     *
     * @param doubleCoordinateMany 一个被计算的多维度坐标，其中的坐标点所处空间位置的坐标描述是无数个的
     *                             <p>
     *                             A multidimensional coordinate, in which the coordinate description of the spatial position of the coordinate point is infinite
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(DoubleCoordinateMany doubleCoordinateMany) {
        double res = 0;
        logger.info("√ ⁿ∑₁( coordinate² )");
        for (double d : doubleCoordinateMany.getCoordinate()) {
            res += d * d;
        }
        return Math.sqrt(res);
    }

    /**
     * 多维空间之中，自身坐标点到原点的距离，这里是多维空间，所以是每一个维度的坐标点都会被进行2次方
     * <p>
     * In the multidimensional space, the distance from its own coordinate point to the origin, here is a multidimensional space, so the coordinate points of each dimension will be squared
     *
     * @param integerCoordinateMany 一个被计算的多维度坐标，其中的坐标点所处空间位置的坐标描述是无数个的
     *                              <p>
     *                              A multidimensional coordinate, in which the coordinate description of the spatial position of the coordinate point is infinite
     * @return Euclidean distance from its own coordinates to the origin
     */
    public double getTrueDistance(IntegerCoordinateMany integerCoordinateMany) {
        int res = 0;
        logger.info("√ ⁿ∑₁( coordinate² )");
        for (int d : integerCoordinateMany.getCoordinate()) {
            res += ASMath.Power2(d);
        }
        return Math.sqrt(res);
    }

    /**
     * 多维空间之中，计算两个整形多维坐标之间的欧式距离。
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two integer multidimensional coordinates.
     *
     * @param integerCoordinateMany1 整形坐标1
     * @param integerCoordinateMany2 整形坐标2
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(IntegerCoordinateMany integerCoordinateMany1, IntegerCoordinateMany integerCoordinateMany2) {
        IntegerCoordinateMany diff = integerCoordinateMany1.diff(integerCoordinateMany2);
        logger.info("√ ⁿ∑₁( " + integerCoordinateMany1 + " - " + integerCoordinateMany2 + ").map(d -> d²)");
        int res = 0;
        for (int i : diff.getCoordinate()) {
            res += ASMath.Power2(i);
        }
        return Math.sqrt(res);
    }

    /**
     * 多维空间之中，两个浮点坐标之间的距离
     * <p>
     * In a multidimensional space, compute the Euclidean distance between two double multidimensional coordinates.
     *
     * @param doubleCoordinateMany1 整形坐标1
     * @param doubleCoordinateMany2 整形坐标2
     * @return True Euclidean distance between two points
     */
    public double getTrueDistance(DoubleCoordinateMany doubleCoordinateMany1, DoubleCoordinateMany doubleCoordinateMany2) {
        DoubleCoordinateMany diff = doubleCoordinateMany1.diff(doubleCoordinateMany2);
        logger.info("√ ⁿ∑₁( " + doubleCoordinateMany1 + " - " + doubleCoordinateMany2 + ").map(d -> d²)");
        int res = 0;
        for (double i : diff.getCoordinate()) {
            res += i * i;
        }
        return Math.sqrt(res);
    }
}
