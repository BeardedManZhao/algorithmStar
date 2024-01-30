package zhao.algorithmMagic.core;

import zhao.algorithmMagic.algorithm.aggregationAlgorithm.AggregationAlgorithm;
import zhao.algorithmMagic.algorithm.aggregationAlgorithm.RangeAggregation;
import zhao.algorithmMagic.algorithm.aggregationAlgorithm.WeightAggregation;
import zhao.algorithmMagic.algorithm.classificationAlgorithm.NoSampleClassification;
import zhao.algorithmMagic.algorithm.classificationAlgorithm.SampleClassification;
import zhao.algorithmMagic.algorithm.differenceAlgorithm.DifferenceAlgorithm;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.DistanceAlgorithm;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.RangeDistance;
import zhao.algorithmMagic.algorithm.featureExtraction.StringArrayFeature;
import zhao.algorithmMagic.algorithm.normalization.DataStandardization;
import zhao.algorithmMagic.algorithm.normalization.RangeDataStandardization;
import zhao.algorithmMagic.algorithm.probabilisticAlgorithm.ProbabilisticAlgorithm;
import zhao.algorithmMagic.algorithm.schemeAlgorithm.SchemeAlgorithm;
import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.Operands;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.FloatingPointCoordinates;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinates;
import zhao.algorithmMagic.operands.matrix.*;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.table.DataFrame;
import zhao.algorithmMagic.operands.table.DataFrameBuilder;
import zhao.algorithmMagic.operands.table.FDataFrame;
import zhao.algorithmMagic.operands.table.SFDataFrame;
import zhao.algorithmMagic.operands.unit.BaseValue;
import zhao.algorithmMagic.operands.vector.*;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * AlgorithmStar 门户类，在此类中您可以直接与所有的算法组件进行交互！！！具有强大的模块组合功能。
 * <p>
 * AlgorithmStar portal class, in which you can directly interact with all algorithm components!!! It has powerful module combination function.
 *
 * @param <diffValue>     差异算法计算组件能够计算的数据类型
 *                        <p>
 *                        Data types that can be calculated by the difference algorithm calculation component
 * @param <featureReturn> 特征提取函数在提取运算之后返回的数据类型
 *                        <p>
 *                        The data type returned by the feature extraction function after the extraction operation
 * @author 赵凌宇
 */
public final class AlgorithmStar<diffValue, featureReturn> {

    private static AlgorithmStar<?, ?> algorithmStar;

    /**
     * 门户工厂类，在这里您可以创建出各种各样的数据对象，例如在这里创建向量等操作
     * <p>
     * Portal factory class, where you can create various data objects, such as creating vectors and other operations
     *
     * @return 向量工厂类
     * @author 赵凌宇
     */
    public static VectorFactory vectorFactory() {
        return new VectorFactory();
    }

    /**
     * 门户工厂类，在这里您可以创建出各种各样的数据对象，例如在这里创建向量等操作
     * <p>
     * Portal factory class, where you can create various data objects, such as creating vectors and other operations
     *
     * @return 矩阵工厂类
     * @author 赵凌宇
     */
    public static MatrixFactory matrixFactory() {
        return new MatrixFactory();
    }

    /**
     * 门户工厂类，在这里您可以创建出各种各样的数据对象，例如在这里创建向量等操作
     * <p>
     * Portal factory class, where you can create various data objects, such as creating vectors and other operations
     *
     * @return 复数工厂类
     * @author 赵凌宇
     */
    public static ComplexNumberFactory complexNumberFactory() {
        return new ComplexNumberFactory();
    }

    /**
     * 门户工厂类，在这里您可以创建出各种各样的数据对象，例如在这里创建向量等操作
     * <p>
     * Portal factory class, where you can create various data objects, such as creating vectors and other operations
     *
     * @return 分数工厂类
     * @author 赵凌宇
     */
    public static FractionFactory fractionFactory() {
        return new FractionFactory();
    }

    /**
     * 获取到单位数值对象的工厂类对象
     * <p>
     * Obtain the factory class object of the unit value object
     *
     * @param baseValueClass 工厂要建造的单位数值类型，例如：BaseValue.class
     *                       <p>
     *                       The numerical type of unit to be constructed by the factory, for example: BaseValue. class
     * @return 单位数值工厂类，您可以直接通过此工厂获取到数值
     * <p>
     * Unit value factory class, you can directly obtain values through this factory
     */
    public static BaseValueFactory baseValueFactory(Class<? extends BaseValue> baseValueClass) {
        return new BaseValueFactory(baseValueClass);
    }

    /**
     * 获取到库帮助类
     *
     * @return 库帮助类
     */
    public static HelpFactory helpFactory() {
        return new HelpFactory();
    }

    /**
     * 获取到算法之星门户类的单一对象，单一对象是惰性加载的方式，当您首次调用此函数的时候，算法之星门户类将会被创建。
     * <p>
     * Get a single object of the algorithm star portal class. The single object is a lazy loading method. When you first call this function, the algorithm star portal class will be created.
     *
     * @param <diffValue>     算法之星 机器学习库中的差异计算组件能够支持的操作数类型。
     *                        <p>
     *                        The types of operands that can be supported by the difference calculation component in the ALGORITHM STAR machine learning library.
     * @param <featureReturn> 算法之星 机器学习库中的特征提取组件在特征提取之后能够获取到的结果数据类型。
     *                        <p>
     *                        The type of result data that can be obtained by the feature extraction component in the ALGORITHM STAR machine learning library after feature extraction.
     * @return 算法之星门户类，此类是全局唯一的，您可以通过此对象使用各种算法的函数。
     * The algorithm star portal class is globally unique. You can use the functions of various algorithms through this object.
     */
    @SuppressWarnings("unchecked")
    public static <diffValue, featureReturn> AlgorithmStar<diffValue, featureReturn> getInstance() {
        if (algorithmStar == null) {
            algorithmStar = new AlgorithmStar<>();
        }
        return (AlgorithmStar<diffValue, featureReturn>) algorithmStar;
    }

    /**
     * 清理算法之星的单一门户类，能够将当前门户类清理掉，该方法不强制调用，只是在不需要使用门户类的时候，调用此方法可以减少内存中的占用。
     * <p>
     * The single portal class of Cleanup Algorithm Star can clean up the current portal class. This method is not forced to be called, but when the portal class is not needed, calling this method can reduce the memory occupation.
     */
    public static void clear() {
        if (algorithmStar != null) {
            algorithmStar = null;
        }
    }

    /*
     ********************************************************************
     * 距离算法组件计算函数开始分界线
     * Distance algorithm component calculation function start boundary
     ********************************************************************
     */

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param v 用于构造矩阵的二维数组
     *          <p>
     *          2D array for constructing the matrix
     * @return matrix object
     */
    public static IntegerMatrix parseIntMat(int[]... v) {
        return IntegerMatrix.parse(v);
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param v 用于构造矩阵的二维数组
     *          <p>
     *          2D array for constructing the matrix
     * @return matrix object
     */
    public static DoubleMatrix parseDoubleMat(double[]... v) {
        return DoubleMatrix.parse(v);
    }

    /**
     * 从本地文件系统中读取一个数据对象，并返回对应数据对象的建造者类。
     *
     * @param file 需要被读取的文件对象。
     * @return 读取之后会返回该数据集对应的一个建造者对象，在该对象中可以对读取操作进行更加详细的设置，
     */
    public static DataFrameBuilder parseDF(File file) {
        return FDataFrame.builder(file);
    }

    /**
     * 从远程数据库中读取一个数据对象，并返回数据对象对应的建造者类。
     *
     * @param DBC 在连接数据库时需要使用的数据库连接对象。
     * @return 数据库连接设置完毕将会返回一个建造者对象，在该对象中可以对读取数据库操作进行更加详细的设置。
     */
    public static DataFrameBuilder parseDF(Connection DBC) {
        return FDataFrame.builder(DBC);
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame parseDF(InputComponent inputComponent, boolean isOC) {
        return FDataFrame.builder(inputComponent, isOC);
    }

    /**
     * 从本地文件系统中读取一个数据对象，并返回对应数据对象的建造者类。
     *
     * @param file 需要被读取的文件对象。
     * @return 读取之后会返回该数据集对应的一个建造者对象，在该对象中可以对读取操作进行更加详细的设置，
     */
    public static DataFrameBuilder parseSDF(File file) {
        return SFDataFrame.builder(file);
    }

    /**
     * 从远程数据库中读取一个数据对象，并返回数据对象对应的建造者类。
     *
     * @param DBC 在连接数据库时需要使用的数据库连接对象。
     * @return 数据库连接设置完毕将会返回一个建造者对象，在该对象中可以对读取数据库操作进行更加详细的设置。
     */
    public static DataFrameBuilder parseSDF(Connection DBC) {
        return SFDataFrame.builder(DBC);
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame parseSDF(InputComponent inputComponent, boolean isOC) {
        return SFDataFrame.builder(inputComponent, isOC);
    }

    /**
     * 使用组件将一个图像数据提取，并获取对应的图像矩阵。
     *
     * @param inputComponent 能够被提取出图像矩阵的数据组件。
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
     * @return 从组件中提取出来的图像矩阵对象。
     */
    public static ColorMatrix parseImage(InputComponent inputComponent, boolean isOC) {
        return ColorMatrix.parse(inputComponent, isOC);
    }

    /**
     * 将图像URL解析，并获取对应的图像矩阵
     *
     * @param url 需要被解析的URL对象
     * @return URL对象所对应的图像矩阵。
     */
    public static ColorMatrix parseImage(URL url) {
        return ColorMatrix.parse(url);
    }

    /**
     * 将图像URL解析，并获取对应的图像矩阵
     *
     * @param url 需要被解析的URL对象
     * @return URL对象所对应的图像矩阵。
     */
    public static ColorMatrix parseGrayscaleImage(URL url) {
        return ColorMatrix.parseGrayscale(url);
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的灰度整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param imagePath 要读取的目标图像文件路径。
     *                  <p>
     *                  The target image file path to read.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parseImage(String imagePath) {
        return ColorMatrix.parse(imagePath);
    }

    /**
     * 根据图像文件获取到整形矩阵对象，在整形矩阵对象中会包含该图像的每一个像素点对应的灰度整形数值。
     * <p>
     * The reshaping matrix object is obtained from the image file, and the reshaping value corresponding to each pixel of the image will be included in the reshaping matrix object.
     *
     * @param imagePath 要读取的目标图像文件路径。
     *                  <p>
     *                  The target image file path to read.
     * @return 根据图像获取到的矩阵对象。
     * <p>
     * The matrix object obtained from the image.
     */
    public static ColorMatrix parseGrayscaleImage(String imagePath) {
        return ColorMatrix.parseGrayscale(imagePath);
    }

    /**
     * 单线程的方式运行一个模型对象，并获取到该模型计算之后的结果，需要确保模型实现了单线程计算函数。
     * <p>
     * To run a model object in a single threaded manner and obtain the calculated results of the model, it is necessary to ensure that the model implements a single threaded calculation function.
     *
     * @param model 需要运行的模型对象。
     *              <p>
     *              The model object that needs to be run.
     * @param input 模型运行时接收的操作数对象，操作数对象时被运算的矩阵或其它模型。
     *              <p>
     *              The operand object received by the model during runtime, and the matrix or other model that is operated on when the operand object is used.
     * @param <K>   模型对象接收的参数名的数据类型。
     *              <p>
     *              The data type of the parameter name received by the model object.
     * @param <I>   模型对象接收的操作数数据类型，需要确保此类型实现了操作数对象。
     *              <p>
     *              The operand data type received by the model object needs to ensure that this type implements the operand object.
     * @param <O>   模型对象计算之后返回的数据类型。
     *              <p>
     *              The data type returned after model object calculation.
     * @return 经过计算之后，会返回一个数据对象，该对象的类型由不同的模型实现。
     * <p>
     * After calculation, a data object will be returned with a type implemented by different models.
     */
    public static <K, I extends Operands<I>, O> O model(ASModel<K, I, O> model, I[] input) {
        return model.function(input);
    }

    /**
     * 多线程的方式运行一个模型对象，并获取到该模型计算之后的结果，需要确保模型实现了单线程计算函数。
     * <p>
     * To run a model object in a multithreaded manner and obtain the calculated results of the model, it is necessary to ensure that the model implements a single threaded calculation function.
     *
     * @param model 需要运行的模型对象。
     *              <p>
     *              The model object that needs to be run.
     * @param input 模型运行时接收的操作数对象，操作数对象时被运算的矩阵或其它模型。
     *              <p>
     *              The operand object received by the model during runtime, and the matrix or other model that is operated on when the operand object is used.
     * @param <K>   模型对象接收的参数名的数据类型。
     *              <p>
     *              The data type of the parameter name received by the model object.
     * @param <I>   模型对象接收的操作数数据类型，需要确保此类型实现了操作数对象。
     *              <p>
     *              The operand data type received by the model object needs to ensure that this type implements the operand object.
     * @param <O>   模型对象计算之后返回的数据类型。
     *              <p>
     *              The data type returned after model object calculation.
     * @return 经过计算之后，会返回一个数据对象，该对象的类型由不同的模型实现。
     * <p>
     * After calculation, a data object will be returned with a type implemented by different models.
     */
    public static <K, I extends Operands<I>, O> O modelConcurrency(ASModel<K, I, O> model, I[] input) {
        return model.functionConcurrency(input);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param distanceAlgorithm        距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                                 <p>
     *                                 The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param doubleConsanguinityRoute 需要被计算的路线对象
     *                                 <p>
     *                                 The route object that needs to be calculated
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, DoubleConsanguinityRoute doubleConsanguinityRoute) {
        return distanceAlgorithm.getTrueDistance(doubleConsanguinityRoute);
    }

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param distanceAlgorithm 距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                          <p>
     *                          The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param doubles1          数组序列1
     * @param doubles2          数组序列2
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, double[] doubles1, double[] doubles2) {
        return distanceAlgorithm.getTrueDistance(doubles1, doubles2);
    }

    /*
     ********************************************************************
     * 聚合算法组件计算函数开始分界线
     * Aggregation algorithm component calculation function start boundary
     ********************************************************************
     */

    /**
     * 获取两个序列之间的距离
     * <p>
     * Get the Canberra distance between two sequences (note that there is no length check function here, if you need to use this method, please configure the array length check outside)
     *
     * @param distanceAlgorithm 距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                          <p>
     *                          The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param ints1             数组序列1
     * @param ints2             数组序列2
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, int[] ints1, int[] ints2) {
        return distanceAlgorithm.getTrueDistance(ints1, ints2);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param distanceAlgorithm          距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                                   <p>
     *                                   The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param doubleConsanguinityRoute2D 需要被计算的路线对象
     *                                   <p>
     *                                   The route object that needs to be calculated
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        return distanceAlgorithm.getTrueDistance(doubleConsanguinityRoute2D);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param distanceAlgorithm         距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                                  <p>
     *                                  The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param integerConsanguinityRoute 需要被计算的路线对象
     *                                  <p>
     *                                  The route object that needs to be calculated
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, IntegerConsanguinityRoute integerConsanguinityRoute) {
        return distanceAlgorithm.getTrueDistance(integerConsanguinityRoute);
    }

    /**
     * 计算一个路线的起始点与终止点的真实距离。具体的距离实现，需要您查阅算法实现的文档。
     * <p>
     * Calculates the true distance between the start and end points of a route.
     *
     * @param distanceAlgorithm           距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                                    <p>
     *                                    The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param integerConsanguinityRoute2D 需要被计算的路线对象
     *                                    <p>
     *                                    The route object that needs to be calculated
     * @return ...
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return distanceAlgorithm.getTrueDistance(integerConsanguinityRoute2D);
    }

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param distanceAlgorithm 距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                          <p>
     *                          The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param matrix1           需要被进行计算的矩阵对象。
     *                          <p>
     *                          The matrix object that needs to be calculated.
     * @param matrix2           需要被进行计算的矩阵对象。
     *                          <p>
     *                          The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, IntegerMatrix matrix1, IntegerMatrix matrix2) {
        return distanceAlgorithm.getTrueDistance(matrix1, matrix2);
    }


    /*
     ********************************************************************
     * 分类算法组件计算函数开始分界线
     * Classification algorithm component calculation function start boundary
     ********************************************************************
     */

    /**
     * 计算两个矩阵对象之间的距离度量函数，通过该函数可以实现两个矩阵对象度量系数的计算。
     * <p>
     * Calculates the distance metric function between two matrix objects, through which the metric coefficients of two matrix objects can be calculated.
     *
     * @param distanceAlgorithm 距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                          <p>
     *                          The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param matrix1           需要被进行计算的矩阵对象。
     *                          <p>
     *                          The matrix object that needs to be calculated.
     * @param matrix2           需要被进行计算的矩阵对象。
     *                          <p>
     *                          The matrix object that needs to be calculated.
     * @return 计算出来的度量结果系数。
     * <p>
     * The calculated measurement result coefficient.
     */
    public double getTrueDistance(DistanceAlgorithm distanceAlgorithm, DoubleMatrix matrix1, DoubleMatrix matrix2) {
        return distanceAlgorithm.getTrueDistance(matrix1, matrix2);
    }

    /**
     * 计算向量距离原点的距离。
     *
     * @param rangeVector   距离计算组件，是您在调用本次函数之后，函数用来计算的距离计算组件对象。
     *                      <p>
     *                      The distance calculation component is the distance calculation component object that the function uses to calculate after you call this function.
     * @param rangeDistance 需要被计算的向量。
     * @return 计算出来的距离结果数值。
     */
    public double getTrueDistance(RangeDistance rangeDistance, RangeVector<?, ?, ?, ?> rangeVector) {
        return rangeDistance.getTrueDistance(rangeVector);
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param aggregationAlgorithm 聚合计算组件，是本次调用函数之后，函数将要用来计算的聚合计算组件对象。
     *                             <p>
     *                             Aggregate calculation component is the aggregate calculation component object that the function will use to calculate after this call.
     * @param doubles              需要被聚合的所有元素组成的数组
     *                             <p>
     *                             An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    public double calculation(AggregationAlgorithm aggregationAlgorithm, double... doubles) {
        return aggregationAlgorithm.calculation(doubles);
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param aggregationAlgorithm 聚合计算组件，是本次调用函数之后，函数将要用来计算的聚合计算组件对象。
     *                             <p>
     *                             Aggregate calculation component is the aggregate calculation component object that the function will use to calculate after this call.
     * @param doubles              需要被聚合的所有元素组成的数组
     *                             <p>
     *                             An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    public int calculation(AggregationAlgorithm aggregationAlgorithm, int... doubles) {
        return aggregationAlgorithm.calculation(doubles);
    }

    /**
     * 带有权重的方式将一个序列中所有的元素进行聚合计算，并将结果返回
     * <p>
     * The method with weight aggregates all elements in a sequence and returns the results
     *
     * @param weightAggregation 带有权重的聚合计算组件，是本次调用函数之后，函数将要用来计算的聚合计算组件对象。
     *                          <p>
     *                          The aggregation calculation component with weight is the aggregation calculation component object that the function will use to calculate after this call.
     * @param Weight            权重数组，其中每一个元素都代表一个权重数值，在序列中与权重数值的索引相同的元素将会受到权重数值的影响，权重数值的区间不受限制!!!
     *                          <p>
     *                          Weight array, in which each element represents a weight value. Elements with the same index as the weight value in the sequence will be affected by the weight value, and the range of the weight value is unlimited!!!
     * @param doubles           需要被聚合的数据组成的序列，其中每一个元素都是计算时候的一部分。
     *                          <p>
     *                          A sequence of data to be aggregated, in which each element is a part of the calculation.
     * @return 在加权计算的影响下，计算出来的聚合结果。
     * <p>
     * The aggregation result calculated under the influence of weighted calculation.
     */
    public double calculation(WeightAggregation weightAggregation, double[] Weight, double... doubles) {
        return weightAggregation.calculation(Weight, doubles);
    }

    /**
     * 带有权重的方式将一个序列中所有的元素进行聚合计算，并将结果返回
     * <p>
     * The method with weight aggregates all elements in a sequence and returns the results
     *
     * @param weightAggregation 带有权重的聚合计算组件，是本次调用函数之后，函数将要用来计算的聚合计算组件对象。
     *                          <p>
     *                          The aggregation calculation component with weight is the aggregation calculation component object that the function will use to calculate after this call.
     * @param Weight            权重数组，其中每一个元素都代表一个权重数值，在序列中与权重数值的索引相同的元素将会受到权重数值的影响，权重数值的区间不受限制!!!
     *                          <p>
     *                          Weight array, in which each element represents a weight value. Elements with the same index as the weight value in the sequence will be affected by the weight value, and the range of the weight value is unlimited!!!
     * @param ints              需要被聚合的数据组成的序列，其中每一个元素都是计算时候的一部分。
     *                          <p>
     *                          A sequence of data to be aggregated, in which each element is a part of the calculation.
     * @return 在加权计算的影响下，计算出来的聚合结果。
     * <p>
     * The aggregation result calculated under the influence of weighted calculation.
     */
    public int calculation(WeightAggregation weightAggregation, double[] Weight, int... ints) {
        return weightAggregation.calculation(Weight, ints);
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param rangeAggregation 区间型向量聚合计算组件，是本次调用函数之后，函数将要用来计算的聚合计算组件对象。
     *                         <p>
     *                         Interval-type vector aggregation calculation component is the aggregation calculation component object that the function will use to calculate after this call.
     * @param rangeVector      需要被聚合的所有元素组成的数组
     *                         <p>
     *                         An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    public double calculation(RangeAggregation rangeAggregation, RangeVector<?, ?, ?, ?> rangeVector) {
        return rangeAggregation.calculation(rangeVector);
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无样本的分类算法计算组件对象。
     * @param keys                   指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param ints                   指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<IntegerVector>> classification(NoSampleClassification noSampleClassification, String[] keys, int[]... ints) {
        return noSampleClassification.classification(keys, ints);
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无需样本的分类算法，无监督学习的有效操作。
     *                               <p>
     *                               The classification algorithm without samples and the effective operation of unsupervised learning.
     * @param keys                   指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param doubles                指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<DoubleVector>> classification(NoSampleClassification noSampleClassification, String[] keys, double[]... doubles) {
        return noSampleClassification.classification(keys, doubles);
    }

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无需样本的分类算法，无监督学习的有效操作。
     *                               <p>
     *                               The classification algorithm without samples and the effective operation of unsupervised learning.
     * @param keys                   指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param ints                   指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<IntegerVector>> classification(NoSampleClassification noSampleClassification, String[] keys, IntegerMatrix ints) {
        return noSampleClassification.classification(keys, ints);
    }

    /*
     ********************************************************************
     * 差异算法组件计算函数开始分界线                                         *
     * diff algorithm component calculation function start boundary *
     ********************************************************************
     */

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无需样本的分类算法，无监督学习的有效操作。
     *                               <p>
     *                               The classification algorithm without samples and the effective operation of unsupervised learning.
     * @param keys                   指定的一些数据类别，按照索引与 ints 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the ints parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param doubles                指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<DoubleVector>> classification(NoSampleClassification noSampleClassification, String[] keys, DoubleMatrix doubles) {
        return noSampleClassification.classification(keys, doubles);
    }

    /*
     ********************************************************************
     * 特征提取算法组件计算函数开始分界线                                         *
     * FeatureExtraction algorithm component calculation function start boundary *
     ********************************************************************
     */

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无需样本的分类算法，无监督学习的有效操作。
     *                               <p>
     *                               The classification algorithm without samples and the effective operation of unsupervised learning.
     * @param keys                   指定的一些数据类别，按照索引与 columnIntegerMatrix 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the columnIntegerMatrix parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param columnIntegerMatrix    指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<ColumnIntegerVector>> classification(NoSampleClassification noSampleClassification, String[] keys, ColumnIntegerMatrix columnIntegerMatrix) {
        return noSampleClassification.classification(keys, columnIntegerMatrix);
    }

    /*
     ********************************************************************
     * 数据预处理算法组件计算函数开始分界线                                         *
     * Data preprocessing algorithm component calculation function start boundary *
     ********************************************************************
     */

    /**
     * 无样本的距离计算，您在此进行分类，不需要传递很多的数据样本，只需要由实现类按照自己的算法进行类别推断即可。
     * <p>
     * For distance calculation without samples, you can classify here. You don't need to pass a lot of data samples. You only need to infer the category by the implementation class according to its own algorithm.
     *
     * @param noSampleClassification 无需样本的分类算法，无监督学习的有效操作。
     *                               <p>
     *                               The classification algorithm without samples and the effective operation of unsupervised learning.
     * @param keys                   指定的一些数据类别，按照索引与 columnDoubleMatrix 参数一一对应，其中如果为 ? 代表是未知类别
     *                               <p>
     *                               Some specified data categories correspond to the columnDoubleMatrix parameter one by one according to the index, in which, if it is? Represents an unknown category
     * @param columnDoubleMatrix     指定的类别索引对应的数据特征本身，是需要分类的关键对象。
     *                               <p>
     *                               The data feature corresponding to the specified category index is the key object to be classified.
     * @return 分类结果。
     * <p>
     * Classification results.
     */
    public HashMap<String, ArrayList<ColumnDoubleVector>> classification(NoSampleClassification noSampleClassification, String[] keys, ColumnDoubleMatrix columnDoubleMatrix) {
        return noSampleClassification.classification(keys, columnDoubleMatrix);
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param sampleClassification 有样本的分类计算组件，适用于具有标准的分类任务。
     *                             <p>
     *                             Classification calculation components with samples are applicable to classification tasks with standards.
     * @param data                 需要被计算的特征数据组成的矩阵。
     *                             <p>
     *                             Matrix composed of characteristic data to be calculated.
     * @param categorySample       本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                             <p>
     *                             The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    public HashMap<String, ArrayList<DoubleVector>> classification(SampleClassification sampleClassification, double[][] data, Map<String, double[]> categorySample) {
        return sampleClassification.classification(data, categorySample);
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param sampleClassification 有样本的分类计算组件，适用于具有标准的分类任务。
     *                             <p>
     *                             Classification calculation components with samples are applicable to classification tasks with standards.
     * @param data                 需要被计算的特征数据组成的矩阵。
     *                             <p>
     *                             Matrix composed of characteristic data to be calculated.
     * @param categorySample       本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                             <p>
     *                             The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    public HashMap<String, ArrayList<IntegerVector>> classification(SampleClassification sampleClassification, int[][] data, Map<String, int[]> categorySample) {
        return sampleClassification.classification(data, categorySample);
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param sampleClassification 有样本的分类计算组件，适用于具有标准的分类任务。
     *                             <p>
     *                             Classification calculation components with samples are applicable to classification tasks with standards.
     * @param data                 需要被计算的特征数据组成的矩阵。
     *                             <p>
     *                             Matrix composed of characteristic data to be calculated.
     * @param categorySample       本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                             <p>
     *                             The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    public HashMap<String, ArrayList<DoubleVector>> classification(SampleClassification sampleClassification, DoubleMatrix data, Map<String, double[]> categorySample) {
        return sampleClassification.classification(data, categorySample);
    }

    /**
     * 计算一个矩阵中所有行或列的数据类别，并将计算之后的数据类别样本返回出去。
     * <p>
     * Calculate the data categories of all rows or columns in a matrix, and return the calculated data category samples.
     *
     * @param sampleClassification 有样本的分类计算组件，适用于具有标准的分类任务。
     *                             <p>
     *                             Classification calculation components with samples are applicable to classification tasks with standards.
     * @param data                 需要被计算的特征数据组成的矩阵。
     *                             <p>
     *                             Matrix composed of characteristic data to be calculated.
     * @param categorySample       本次类别计算的类别样本，用于区别各类数据样本，其中的key就是类别，value就是数据特征向量序列，例如：
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     *                             <p>
     *                             The category sample of this category calculation is used to distinguish various data samples. The key is the category, and the value is the data feature vector sequence, for example:
     *                             {"person", [1,2,3,4,5]},{"insect", [3, 2, 3, 4, 5]}
     * @return 按照指定类别进行分类的数据，例如：
     * {"person", ["tom的特征向量", "zhao的特征向量"], "insect", ["蜘蛛的特征向量", "蜗牛的特征向量"]}
     * <p>
     * Data classified according to the specified category, for example:
     * {"person", ["Tom's feature vector", "Zhao's feature vector"], "insert", ["spider's feature vector", "snail's feature vector"]}
     */
    public HashMap<String, ArrayList<IntegerVector>> classification(SampleClassification sampleClassification, IntegerMatrix data, Map<String, int[]> categorySample) {
        return sampleClassification.classification(data, categorySample);
    }

    /**
     * 计算两个事物之间从差异系数百分比
     * <p>
     * Calculate the percentage difference from the coefficient of difference between two things
     *
     * @param differenceAlgorithm 距离算法计算组件对象。
     * @param value1              差异参数1
     * @param value2              差异参数2
     * @return 差异系数
     */
    public double getDifferenceRatio(DifferenceAlgorithm<diffValue> differenceAlgorithm, diffValue value1, diffValue value2) {
        return differenceAlgorithm.getDifferenceRatio(value1, value2);
    }

    /**
     * 将很多字符串组合起来进行特征向量的提取，其中的每一个词都是一个特征值。
     * <p>
     * Combine many strings to extract feature vectors, and each word is a feature value.
     *
     * @param stringArrayFeature 字符串特征提取组件对象
     * @param data               需要被进行特征提取的数据，数据类型是不一定的，要看实现类的处理方式
     * @return 提取之后的结果对象，类型不确定的，要看实现类的处理方式
     */
    public featureReturn extract(StringArrayFeature<featureReturn> stringArrayFeature, String... data) {
        return stringArrayFeature.extract(data);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 数据标准化/归一化组件对象
     * @param v                   需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public FloatingPointCoordinates<DoubleCoordinateMany> pretreatment(DataStandardization dataStandardization, DoubleCoordinateMany v) {
        return dataStandardization.pretreatment(v);
    }

    /*
     ********************************************************************
     * 概率算法组件计算函数开始分界线
     * Probability algorithm component calculation function start boundary
     ********************************************************************
     */

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 数据标准化/归一化组件对象
     * @param v                   需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public IntegerCoordinates<IntegerCoordinateMany> pretreatment(DataStandardization dataStandardization, IntegerCoordinateMany v) {
        return dataStandardization.pretreatment(v);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 需要使用的数据预处理算法组件对象。
     * @param integerMatrix       需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public IntegerMatrix pretreatment(DataStandardization dataStandardization, IntegerMatrix integerMatrix) {
        return dataStandardization.pretreatment(integerMatrix);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 需要使用的数据预处理算法组件对象。
     * @param doubleMatrix        需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public DoubleMatrix pretreatment(DataStandardization dataStandardization, DoubleMatrix doubleMatrix) {
        return dataStandardization.pretreatment(doubleMatrix);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 数据标准化/归一化组件对象
     * @param doubleVector        需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public DoubleVector pretreatment(DataStandardization dataStandardization, DoubleVector doubleVector) {
        return dataStandardization.pretreatment(doubleVector);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param dataStandardization 数据标准化/归一化组件对象
     * @param integerVector       需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                            <p>
     *                            The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public IntegerVector pretreatment(DataStandardization dataStandardization, IntegerVector integerVector) {
        return dataStandardization.pretreatment(integerVector);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param rangeDataStandardization 数据预处理算法计算组件对象。
     *                                 <p>
     *                                 algorithms calculate component objects.
     * @param fastRangeIntegerVector   需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                                 <p>
     *                                 The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public IntegerVector pretreatment(RangeDataStandardization rangeDataStandardization, FastRangeIntegerVector fastRangeIntegerVector) {
        return rangeDataStandardization.pretreatment(fastRangeIntegerVector);
    }

    /**
     * 将一个序列进行标准化，具体的标准化有不同的实现
     *
     * @param rangeDataStandardization 数据预处理算法计算组件对象。
     *                                 <p>
     *                                 algorithms calculate component objects.
     * @param fastRangeDoubleVector    需要被标准化的数值，可以是坐标或向量，更多信息需要查阅实现
     *                                 <p>
     *                                 The value to be normalized, which can be a coordinate or a vector. For more information, see the implementation
     * @return v的标准化样式
     * <p>
     * Normalized style of v
     */
    public DoubleVector pretreatment(RangeDataStandardization rangeDataStandardization, FastRangeDoubleVector fastRangeDoubleVector) {
        return rangeDataStandardization.pretreatment(fastRangeDoubleVector);
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param probabilisticAlgorithm 概率计算组件对象。
     *                               <p>
     *                               Probability calculation component object.
     * @param integerMatrix          需要被统计的矩阵对象。
     *                               <p>
     *                               The matrix object to be counted.
     * @param StatisticCondition1    在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2    在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的分子值 与 分母值组合成的数组，其中索引0为分子 1为分母。
     * <p>
     * P (A | B) This is an array of numerator and denominator values in the event probability calculation results, where index 0 is numerator and 1 is denominator.
     */
    public double[] estimateGetFraction(
            ProbabilisticAlgorithm probabilisticAlgorithm,
            IntegerMatrix integerMatrix,
            ArrayIntegerFiltering StatisticCondition1,
            ArrayIntegerFiltering StatisticCondition2
    ) {
        return probabilisticAlgorithm.estimateGetFraction(integerMatrix, StatisticCondition1, StatisticCondition2);
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param probabilisticAlgorithm 概率计算组件对象。
     *                               <p>
     *                               Probability calculation component object.
     * @param doubleMatrix           需要被统计的矩阵对象。
     *                               <p>
     *                               The matrix object to be counted.
     * @param StatisticCondition1    在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2    在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的分子值 与 分母值组合成的数组，其中索引0为分子 1为分母。
     * <p>
     * P (A | B) This is an array of numerator and denominator values in the event probability calculation results, where index 0 is numerator and 1 is denominator.
     */
    public double[] estimateGetFraction(
            ProbabilisticAlgorithm probabilisticAlgorithm,
            DoubleMatrix doubleMatrix,
            ArrayDoubleFiltering StatisticCondition1,
            ArrayDoubleFiltering StatisticCondition2
    ) {
        return probabilisticAlgorithm.estimateGetFraction(doubleMatrix, StatisticCondition1, StatisticCondition2);
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param probabilisticAlgorithm 概率计算组件对象。
     *                               <p>
     *                               Probability calculation component object.
     * @param integerMatrix          需要被统计的矩阵对象。
     *                               <p>
     *                               The matrix object to be counted.
     * @param StatisticCondition1    在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2    在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的概率数值，取值范围为 [0, 1]。
     * <p>
     * P (A | B) is the probability value in the event probability calculation result. The value range is [0,1].
     */
    public double estimate(
            ProbabilisticAlgorithm probabilisticAlgorithm,
            IntegerMatrix integerMatrix,
            ArrayIntegerFiltering StatisticCondition1,
            ArrayIntegerFiltering StatisticCondition2
    ) {
        return probabilisticAlgorithm.estimate(integerMatrix, StatisticCondition1, StatisticCondition2);
    }

    /**
     * 计算一个矩阵中的某些条件限制下的联合概率结果 P(A|B) 其中的分子与分母值！
     *
     * @param probabilisticAlgorithm 概率计算组件对象。
     *                               <p>
     *                               Probability calculation component object.
     * @param doubleMatrix           需要被统计的矩阵对象。
     *                               <p>
     *                               The matrix object to be counted.
     * @param StatisticCondition1    在在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件A的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event A in the joint logic, where each element passed in is a row of data in the sample matrix
     * @param StatisticCondition2    在统计矩阵概率的时候需要传递事件逻辑，用于过滤满足条件的事件数量，计算满足条件的逻辑，因此在计算的时候您需要在此处传递一个数据过滤逻辑，值得一提的是，在计算的时候，该过滤器是针对联合逻辑中的联合事件B的处理逻辑，其中每一个传递进去的元素都是样本矩阵中的一行数据
     *                               <p>
     *                               When calculating the probability of the matrix, you need to pass the event logic, which is used to filter the number of events that meet the conditions and calculate the logic that meets the conditions. Therefore, you need to pass a data filter logic here. It is worth mentioning that when calculating, the filter is the processing logic for the joint event B in the joint logic, where each element passed in is a row of data in the sample matrix
     * @return P(A | B)这个事件概率计算结果中的概率数值，取值范围为 [0, 1]。
     * <p>
     * P (A | B) is the probability value in the event probability calculation result. The value range is [0,1].
     */
    public double estimate(
            ProbabilisticAlgorithm probabilisticAlgorithm,
            DoubleMatrix doubleMatrix,
            ArrayDoubleFiltering StatisticCondition1,
            ArrayDoubleFiltering StatisticCondition2
    ) {
        return probabilisticAlgorithm.estimate(doubleMatrix, StatisticCondition1, StatisticCondition2);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm      决策算法组件对象。
     *                             <p>
     *                             Decision algorithm component object.
     * @param doubleMatrix         当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(SchemeAlgorithm schemeAlgorithm, DoubleMatrix doubleMatrix, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decision(schemeAlgorithm, doubleMatrix.toArrays(), 2, arrayDoubleFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm       决策算法组件对象。
     *                              <p>
     *                              Decision algorithm component object.
     * @param integerMatrix         当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(SchemeAlgorithm schemeAlgorithm, IntegerMatrix integerMatrix, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decision(schemeAlgorithm, integerMatrix.toArrays(), 2, arrayIntegerFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm      决策算法组件对象。
     *                             <p>
     *                             Decision algorithm component object.
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(SchemeAlgorithm schemeAlgorithm, double[][] ints, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return decision(schemeAlgorithm, ints, 2, arrayDoubleFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm       决策算法组件对象。
     *                              <p>
     *                              Decision algorithm component object.
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(SchemeAlgorithm schemeAlgorithm, int[][] ints, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return decision(schemeAlgorithm, ints, 2, arrayIntegerFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm      决策算法组件对象。
     *                             <p>
     *                             Decision algorithm component object.
     * @param ints                 当前需要计算的样本矩阵数组
     *                             <p>
     *                             Sample matrix array to be calculated currently
     * @param logBase              本次决策运算中需要使用的对数底数值
     *                             <p>
     *                             The logarithmic base value to be used in this decision operation
     * @param arrayDoubleFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                             <p>
     *                             The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayDoubleFiltering> decision(SchemeAlgorithm schemeAlgorithm, double[][] ints, int logBase, ArrayDoubleFiltering... arrayDoubleFiltering) {
        return schemeAlgorithm.decision(ints, logBase, arrayDoubleFiltering);
    }

    /**
     * 通过决策树，对传进进来的决策序列重新排列，使其成为最优解。
     * <p>
     * Through the decision tree, the incoming decision sequence is rearranged to become the optimal solution.
     *
     * @param schemeAlgorithm       决策算法组件对象。
     *                              <p>
     *                              Decision algorithm component object.
     * @param ints                  当前需要计算的样本矩阵数组
     *                              <p>
     *                              Sample matrix array to be calculated currently
     * @param logBase               本次决策运算中需要使用的对数底数值
     *                              <p>
     *                              The logarithmic base value to be used in this decision operation
     * @param arrayIntegerFiltering 本次需要被决策树重新排列的决策方案，这是一个数组，其中每一个都是一个事件判断函数的实现，最终会通过算法计算出结果数值
     *                              <p>
     *                              The decision scheme that needs to be rearranged by the decision tree this time is an array, each of which is the implementation of an event judgment function. Finally, the result value will be calculated by the algorithm
     * @return 重新排列过的 ArrayDoubleFiltering 决策方案，其中从0开始进行决策，是效果最好的处理方式。
     * <p>
     * The rearranged ArrayDoubleFiltering decision scheme, in which the decision is made from 0, is the best way to deal with it.
     */
    public ArrayList<ArrayIntegerFiltering> decision(SchemeAlgorithm schemeAlgorithm, int[][] ints, int logBase, ArrayIntegerFiltering... arrayIntegerFiltering) {
        return schemeAlgorithm.decision(ints, logBase, arrayIntegerFiltering);
    }
}
