package zhao.algorithmMagic.lntegrator;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.lntegrator.launcher.IncrementalLearningLauncher;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateMany;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

import java.util.HashMap;

/**
 * Java类于 2022/10/21 12:25:33 创建
 * <p>
 * 单调区间，机器学习集成器，该集成器用于模型参数的推断，针对具有一个θ的模型，该集成器可以实现在一定的θ递增区间内查找到该数值的最佳结果。至于模型的设置，您应实现启动器，在启动器中，可以进行有关模型的配置.
 * <p>
 * Monotonic interval, machine learning ensemble, the ensemble is used for model parameter inference, for a model with a θ, the ensemble can achieve the best result of finding the value within a certain θ increasing interval. As for the model's settings, you should implement a launcher, where the configuration about the model can be made.
 *
 * @author zhao
 */
public class IncrementalLearning implements AlgorithmIntegrator<IncrementalLearning> {

    private final Logger logger;
    private final String IntegratorName;
    private final IncrementalLearningLauncher incrementalLearning;
    private double StartingValue = -10;
    private double TerminationValue = 10;
    private double EqualDifferenceOrEquivalentRatio = 1;
    private boolean useEquivalentRatio = false;
    private DoubleVector doubleVector1;
    private DoubleVector doubleVector2;
    private double tempRes = 0b11111111111111111111111111111111;

    public IncrementalLearning(String integratorName, IncrementalLearningLauncher incrementalLearningLauncher) {
        IntegratorName = integratorName;
        logger = Logger.getLogger(integratorName);
        logger.info("+======================================= << " + this.IntegratorName + " >> started =============================================+");
        this.incrementalLearning = incrementalLearningLauncher;
    }

    /**
     * 通过名称获取到启动器，这种方式适用于已经实现了启动器的算法，它会按照以下步骤执行
     * 1.前往管理者，提取指定的算法
     * 2.检查算法是否属于该集成器的启动器
     * 3.检查启动器是否允许或支持绘制图像
     * 4.将算法以启动器的身份加载到集成器
     *
     * @param integratorName        集成器的名称
     * @param AlgorithmLauncherName 实现了启动器的算法名称
     */
    public IncrementalLearning(String integratorName, String AlgorithmLauncherName) {
        IntegratorName = integratorName;
        logger = Logger.getLogger(integratorName);
        logger.info("+======================================= << " + this.IntegratorName + " >> started =============================================+");
        logger.info("+--------------------------------------- << Extract the algorithm required by the integrator >> ---------------------------------------+");
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(AlgorithmLauncherName);
        if (operationAlgorithm instanceof IncrementalLearningLauncher) {
            this.incrementalLearning = ASClass.transform(operationAlgorithm);
        } else {
            throw new TargetNotRealizedException("您提取的[" + AlgorithmLauncherName + "]算法被找到了，但是它没有实现 IncrementalLearningLauncher ，您如果想要使用该算法进行模型的预测学习，那么您要保证它实现了启动器。\n" +
                    "The [" + AlgorithmLauncherName + "] algorithm you extracted was found, but it does not implement IncrementalLearningLauncher . If you want to use this algorithm for predictive learning of your model, you must ensure that it implements a launcher. \n");
        }
    }

    public double getStartingValue() {
        return StartingValue;
    }

    /**
     * 设置θ的单调区间起始数值，θ会在一个固定的区间内进数值的递增或递减，该区间的设置范围越准，数值会精确。
     * <p>
     * Set the starting value of the monotonic interval of θ. θ will increase or decrease the value in a fixed interval. The more accurate the setting range of the interval is, the more accurate the value will be.
     *
     * @param startingValue 区间的起始数值
     *                      <p>
     *                      the starting value of the interval
     * @return chain
     */
    public IncrementalLearning setStartingValue(double startingValue) {
        StartingValue = startingValue;
        return this;
    }

    public double getTerminationValue() {
        return TerminationValue;
    }

    /**
     * 设置θ的单调区间终止数值，θ会在一个固定的区间内进数值的递增或递减，该区间的设置范围越准，数值会精确。
     * <p>
     * Set the Termination value of the monotonic interval of θ. θ will increase or decrease the value in a fixed interval. The more accurate the setting range of the interval is, the more accurate the value will be.
     *
     * @param terminationValue 区间的终止数值
     *                         <p>
     *                         the Termination value of the interval
     * @return chain
     */
    public IncrementalLearning setTerminationValue(double terminationValue) {
        TerminationValue = terminationValue;
        return this;
    }

    /**
     * 设置结果集向量，该向量中的每一个元素都是 doubleVector2的对应位置的结果
     *
     * @param doubleVector1 结果集向量
     * @return 链式编程
     */
    public IncrementalLearning setDoubleVector1(DoubleVector doubleVector1) {
        this.doubleVector1 = doubleVector1;
        return this;
    }

    /**
     * 设置模型数据集向量，该向量中的每一个元素都是 doubleVector2的对应位置的自变量
     *
     * @param doubleVector2 变量集向量
     * @return 链式编程
     */
    public IncrementalLearning setDoubleVector2(DoubleVector doubleVector2) {
        this.doubleVector2 = doubleVector2;
        return this;
    }

    /**
     * @return 单调递增的等差或等比
     */
    public double getEqualDifferenceOrEquivalentRatio() {
        return EqualDifferenceOrEquivalentRatio;
    }

    /**
     * @return 使用的递增模式是等差递增还是等比递增，true代表使用等比递增。
     * <p>
     * Whether the increment mode used is equal difference increment or equal ratio increment, true means equal ratio increment.
     */
    public boolean isUseEquivalentRatio() {
        return useEquivalentRatio;
    }

    /**
     * @param useEquivalentRatio               设置您要使用的递增模式是等差递增还是等比递增，true代表使用等比递增。
     *                                         <p>
     *                                         Whether the increment mode used is equal difference increment or equal ratio increment, true means equal ratio increment.
     * @param equalDifferenceOrEquivalentRatio 设置您的递增等差/等比数值是多少，请注意，如果您设置的是等比递增，最好不要使用1作为等比数值，因为这样的话无意义，最终的数值很可能还是 1。
     *                                         <p>
     *                                         Set your incremental difference/ratio value. Note that if you are setting an equal ratio increment, it is best not to use 1 as the equiratio value, because it does not make sense, and the final value is probably 1.
     * @return 链式
     */
    public IncrementalLearning setIncrementalParameter(boolean useEquivalentRatio, double equalDifferenceOrEquivalentRatio) {
        this.useEquivalentRatio = useEquivalentRatio;
        this.EqualDifferenceOrEquivalentRatio = equalDifferenceOrEquivalentRatio;
        return this;
    }

    /**
     * 为两个序列构建一个模型，初始模型由用户进行设定 Yn = incrementalLearning.run(Yn, θ, Xn) 其中Y代表序列1 X代表序列2  n代表序列的项数，也就是说，第一个序列为结果序列，第二个序列为变量，θ就是两者之间的一个模型关系！
     * <p>
     * Build a model for two sequences, the initial model is set by the user Yn = incrementalLearning.run(Yn, θ, Xn) where Y represents sequence 1 X represents sequence 2 n represents the number of items in the sequence, that is, the first The sequence is the result sequence, the second sequence is the variable, and θ is a model relationship between the two!
     *
     * @param doubles1 序列1 也就是Y 其中的每一项都是X每一项与θ乘积的结果值
     * @param doubles2 序列2 也就是X 其中的每一项与θ做乘法，都获取到了最终结果值，结果值在Y中
     * @return θ的学习过程集合，是一个哈希集合，其中的key就是真正的结果数值，其中的value就是该结果数值的模型计算结果集合，模型计算结果同样也是一个哈希映射集合，其中的key就是计算时的θ，value就是计算之后的模型结果值，您可以使用这个数据集中的value 和Yn进行对比，比较出来更加相似的结果数值，那么该结果数值的key就是模型中的θ，可以达到比较精确的位置。
     * <p>
     * The learning process set of return θ is a hash set, in which the key is the real result value, and the value is the model calculation result set of the result value, and the model calculation result is also a hash map set, in which the key It is the θ during calculation, and value is the model result value after calculation. You can use the value and Yn in this data set to compare, and compare the result value that is more similar, then the key of the result value is θ in the model, which can be achieved more precise location.
     */
    public HashMap<Double, HashMap<Double, Double>> BuildingLearningSequences(double[] doubles1, double[] doubles2) {
        logger.info("+ BuildingLearningSequences················θStartingValue = " + this.StartingValue + "  θTerminationValue = " + this.TerminationValue);
        if (doubles1.length == doubles2.length) {
            int $D = (int) ASMath.absoluteValue(this.StartingValue - this.TerminationValue);
            // 构建一个集合，用于存储每一个结果值的数据学习集合，其中的key是
            HashMap<Double, HashMap<Double, Double>> hashMap = new HashMap<>(doubles1.length);
            // 迭代出来结果值，每一个结果值都与其变量建立不同θ下的关系
            for (int i = 0; i < doubles1.length; i++) {
                double Yn = doubles1[i];
                // 将Yn的不同θ的结果集保存到 Yn 的训练数据哈希集合中
                hashMap.put(Yn, getDoubleDoubleHashMap($D, Yn, doubles2[i]));
            }
            return hashMap;
        } else {
            throw new OperatorOperationException("训练数据集有错误，您的传入的数据集无法进行模型的构建，因为两个参数的维度不同！\n" +
                    "There is an error in the training dataset, your incoming dataset cannot be modeled because the dimensions of the two parameters are different!\n" +
                    "number of dimensions => Result data sequence(Y)[" + doubles1.length + "]  Variable data sequence(X)[" + doubles2.length + "]");
        }
    }

    /**
     * 为两个序列构建一个模型，初始模型由用户进行设定 Yn = incrementalLearning.run(Yn, θ, Xn) 其中Y代表序列1 X代表序列2  n代表序列的项数，也就是说，第一个序列为结果序列，第二个序列为变量，θ就是两者之间的一个模型关系！
     * <p>
     * Build a model for two sequences, the initial model is set by the user Yn = incrementalLearning.run(Yn, θ, Xn) where Y represents sequence 1 X represents sequence 2 n represents the number of items in the sequence, that is, the first The sequence is the result sequence, the second sequence is the variable, and θ is a model relationship between the two!
     *
     * @param doubles1 序列1 也就是Y 其中的每一项都是X每一项与θ乘积的结果值
     * @param doubles2 序列2 也就是X 其中的每一项与θ做乘法，都获取到了最终结果值，结果值在Y中
     * @return θ的学习过程集合，是一个哈希集合，其中的key就是真正的结果数值，其中的value就是该结果数值的模型计算结果集合，模型计算结果同样也是一个哈希映射集合，其中的key就是计算时的θ，value就是计算之后的模型结果值，您可以使用这个数据集中的value 和Yn进行对比，比较出来更加相似的结果数值，那么该结果数值的key就是模型中的θ，可以达到比较精确的位置。
     * <p>
     * The learning process set of return θ is a hash set, in which the key is the real result value, and the value is the model calculation result set of the result value, and the model calculation result is also a hash map set, in which the key It is the θ during calculation, and value is the model result value after calculation. You can use the value and Yn in this data set to compare, and compare the result value that is more similar, then the key of the result value is θ in the model, which can be achieved more precise location.
     */
    public HashMap<Integer, HashMap<Double, Double>> BuildingLearningSequences(int[] doubles1, int[] doubles2) {
        logger.info("+ BuildingLearningSequences················θStartingValue = " + this.StartingValue + "  θTerminationValue = " + this.TerminationValue);
        if (doubles1.length == doubles2.length) {
            int $D = (int) ASMath.absoluteValue(this.StartingValue - this.TerminationValue);
            // 构建一个集合，用于存储每一个结果值的数据学习集合，其中的key是
            HashMap<Integer, HashMap<Double, Double>> hashMap = new HashMap<>(doubles1.length);
            // 迭代出来结果值，每一个结果值都与其变量建立不同θ下的关系
            for (int i = 0; i < doubles1.length; i++) {
                int Yn = doubles1[i];
                // 将Yn的不同θ的结果集保存到 Yn 的训练数据哈希集合中
                hashMap.put(Yn, getDoubleDoubleHashMap($D, Yn, doubles2[i]));
            }
            return hashMap;
        } else {
            throw new OperatorOperationException("训练数据集有错误，您的传入的数据集无法进行模型的构建，因为两个参数的维度不同！\n" +
                    "There is an error in the training dataset, your incoming dataset cannot be modeled because the dimensions of the two parameters are different!\n" +
                    "number of dimensions => Result data sequence(Y)[" + doubles1.length + "]  Variable data sequence(X)[" + doubles2.length + "]");
        }
    }

    /**
     * 计算不同 θ 下的结果数值，并将其 θ 与 其结果数值存储到一个集合中
     * <p>
     * Calculate the resulting value at different θ and store its θ and its resulting value in a set
     *
     * @param $D 集合的长度，这里是采取的θ范围区间内的项数。
     *           <p>
     *           The length of the set, here is the number of items in the range of theta taken.
     * @param Yn 结果数值，与Xn已一同提供给启动器中的run抽象，由用户去实现自己的模型。
     *           <p>
     *           The resulting value, along with Xn, has been provided to the run abstraction in the launcher, and it is up to the user to implement their own model.
     * @param Xn 模型中的一个参数。
     *           <p>
     *           A parameter in the model.
     * @return Yn Xn 在 不同θ 的情况下，由启动器中的run返回的不同数值的计算结果集合，该集合可以用来找到最合适的θ参数。
     * <p>
     * Yn Xn In the case of different θ, the set of calculation results of different values returned by the run in the starter, this set can be used to find the most suitable θ parameter.
     */
    private HashMap<Double, Double> getDoubleDoubleHashMap(int $D, double Yn, double Xn) {
        // 创建出来一个机器学习结果数据集容器，key为θ的数值，value 为θXn的结果值
        final HashMap<Double, Double> resHash = new HashMap<>($D);
        // 开始使用不同的θ参数进行数据的结果值计算
        if (!this.useEquivalentRatio) {
            // 如果是使用的等差序列递增
            for (double $ = this.StartingValue; $ <= this.TerminationValue; $ += this.EqualDifferenceOrEquivalentRatio) {
                resHash.put($, this.incrementalLearning.run(Yn, Xn, $));
            }
        } else {
            // 如果是使用的等比序列递增
            for (double $ = this.StartingValue; $ <= this.TerminationValue; $ *= this.EqualDifferenceOrEquivalentRatio) {
                resHash.put($, this.incrementalLearning.run(Yn, Xn, $));
            }
        }
        return resHash;
    }

    /**
     * 从机器学习数据集合中提取出来最相似的θ参数，并将此θ返回出去，一般来说，该参数在您的模型中是最适合的。
     * <p>
     * Extract the most similar theta parameter from the machine learning dataset and return this theta, which in general is the best fit in your model.
     *
     * @param hashMap 机器学习数据集合，该集合可以通过BuildingLearningSequences函数获取到。
     *                <p>
     *                A collection of machine learning data, which can be obtained through the Building Learning Sequences function.
     * @return 在您的数据模型中，最合适的θ参数。
     * <p>
     * The most appropriate theta parameter in your data model.
     */
    public double CalculateTheMostSimilarParametersD(HashMap<Double, HashMap<Double, Double>> hashMap) {
        // 构建一个所有数据最相似参数的数据合集
        final double[] resValues = new double[hashMap.size()];
        int now = 0;
        // 迭代每一个数据集中的Yn
        for (double AYn : hashMap.keySet()) {
            final HashMap<Double, Double> doubleDoubleHashMap = hashMap.get(AYn);
            double res = 0;
            double MinimumGapParameter = Integer.MAX_VALUE;
            // 迭代该Yn下，每一个模型中的θ
            for (Double $ : doubleDoubleHashMap.keySet()) {
                // 获取到该θ下的Yn，并使用θYn 与 AYn 进行差异的判断
                double d = ASMath.absoluteValue(doubleDoubleHashMap.get($) - AYn);
                // 如果该差异比上一个要小，那么就将该差异对应的数值存储到结果空间，同时将该差异参数记录
                if (MinimumGapParameter > d) {
                    MinimumGapParameter = d;
                    res = $;
                }
            }
            // 将当前计算出来的当前参数最佳θ存储到参数集合中
            resValues[now] = res;
            now++;
            logger.info("+ A value of θ is calculated as: " + res);
        }
        // 返回差距最小的θ数据集的平均数
        return ASMath.avg(resValues);
    }

    /**
     * 从机器学习数据集合中提取出来最相似的θ参数，并将此θ返回出去，一般来说，该参数在您的模型中是最适合的。
     * <p>
     * Extract the most similar theta parameter from the machine learning dataset and return this theta, which in general is the best fit in your model.
     *
     * @param hashMap 机器学习数据集合，该集合可以通过BuildingLearningSequences函数获取到。
     *                <p>
     *                A collection of machine learning data, which can be obtained through the Building Learning Sequences function.
     * @return 在您的数据模型中，最合适的θ参数。
     * <p>
     * The most appropriate theta parameter in your data model.
     */
    private double CalculateTheMostSimilarParametersI(HashMap<Integer, HashMap<Double, Double>> hashMap) {
        // 构建一个所有数据最相似参数的数据合集
        final double[] resValues = new double[hashMap.size()];
        int now = 0;
        // 迭代每一个数据集中的Yn
        for (int AYn : hashMap.keySet()) {
            final HashMap<Double, Double> doubleDoubleHashMap = hashMap.get(AYn);
            double res = 0;
            double MinimumGapParameter = Integer.MAX_VALUE;
            // 迭代该Yn下，每一个模型中的θ
            for (Double $ : doubleDoubleHashMap.keySet()) {
                // 获取到该θ下的Yn，并使用θYn 与 AYn 进行差异的判断
                double d = ASMath.absoluteValue(doubleDoubleHashMap.get($) - AYn);
                // 如果该差异比上一个要小，那么就将该差异对应的数值存储到结果空间，同时将该差异参数记录
                if (MinimumGapParameter > d) {
                    MinimumGapParameter = d;
                    res = $;
                }
            }
            logger.info("+ A value of θ is calculated as: " + res);
            // 将当前计算出来的当前参数最佳θ存储到参数集合中
            resValues[now] = res;
            now++;
        }
        // 返回差距最小的θ数据集的平均数
        return ASMath.avg(resValues);
    }

    /**
     * 对两个向量样本进行学习训练，一般来说第一个向量都是第二个向量每一项在公式中计算的结果，公式模型的撰写在启动器中，您应实现一个符合您业务的启动器，在其中书写计算的模型公式。
     * <p>
     * Learn and train two vector samples. Generally speaking, the first vector is the result of calculating each item of the second vector in the formula. The formula model is written in the launcher. You should implement a launch that suits your business.  in which the calculated model formulas are written.
     *
     * @param doubleVector1 训练向量1
     * @param doubleVector2 训练向量2
     * @return 您传入的启动器中应有一个θ参与计算，它是公式中的一个未知参数，集成器的目的也是在一定的单调区间中推导出来此参数的数值。推导规则就是更符合您所传入的模型逻辑。
     * <p>
     * The initiator you pass in should have a θ involved in the calculation, which is an unknown parameter in the formula, and the purpose of the integrator is to derive the value of this parameter in a certain monotonic interval. Inference rules are more in line with the model logic you pass in.
     */
    public double run(DoubleVector doubleVector1, DoubleVector doubleVector2) {
        return CalculateTheMostSimilarParametersD(BuildingLearningSequences(doubleVector1.toDoubleArray(), doubleVector2.toDoubleArray()));
    }

    /**
     * 对两个向量样本进行学习训练，一般来说第一个向量都是第二个向量每一项在公式中计算的结果，公式模型的撰写在启动器中，您应实现一个符合您业务的启动器，在其中书写计算的模型公式。
     * <p>
     * Learn and train two vector samples. Generally speaking, the first vector is the result of calculating each item of the second vector in the formula. The formula model is written in the launcher. You should implement a launch that suits your business.  in which the calculated model formulas are written.
     *
     * @param integerVector1 训练向量1
     * @param integerVector2 训练向量2
     * @return 您传入的启动器中应有一个θ参与计算，它是公式中的一个未知参数，集成器的目的也是在一定的单调区间中推导出来此参数的数值。推导规则就是更符合您所传入的模型逻辑。
     * <p>
     * The initiator you pass in should have a θ involved in the calculation, which is an unknown parameter in the formula, and the purpose of the integrator is to derive the value of this parameter in a certain monotonic interval. Inference rules are more in line with the model logic you pass in.
     */
    public double run(IntegerVector integerVector1, IntegerVector integerVector2) {
        return CalculateTheMostSimilarParametersD(BuildingLearningSequences(integerVector1.toDoubleArray(), integerVector2.toDoubleArray()));
    }

    /**
     * 对两个多维坐标样本进行学习训练，具体规则与向量的训练机制相差不大。同样您应该实现一个启动器，在其中书写符合您业务的模型公式。
     * <p>
     * For learning and training two multidimensional coordinate samples, the specific rules are not much different from the training mechanism of vectors. Also, you should implement a starter where you write model formulas that fit your business.
     *
     * @param doubleCoordinateMany1 训练数据样本坐标1
     * @param doubleCoordinateMany2 训练数据样本坐标2
     * @return 您传入的启动器中应有一个θ参与计算，它是公式中的一个未知参数，集成器的目的也是在一定的单调区间中推导出来此参数的数值。推导规则就是更符合您所传入的模型逻辑。
     * <p>
     * The initiator you pass in should have a θ involved in the calculation, which is an unknown parameter in the formula, and the purpose of the integrator is to derive the value of this parameter in a certain monotonic interval. Inference rules are more in line with the model logic you pass in.
     */
    public double run(DoubleCoordinateMany doubleCoordinateMany1, DoubleCoordinateMany doubleCoordinateMany2) {
        return CalculateTheMostSimilarParametersD(BuildingLearningSequences(doubleCoordinateMany1.toArray(), doubleCoordinateMany2.toArray()));
    }

    /**
     * 对两个多维坐标样本进行学习训练，具体规则与向量的训练机制相差不大。同样您应该实现一个启动器，在其中书写符合您业务的模型公式。
     * <p>
     * For learning and training two multidimensional coordinate samples, the specific rules are not much different from the training mechanism of vectors. Also, you should implement a starter where you write model formulas that fit your business.
     *
     * @param integerCoordinateMany1 训练数据样本坐标1
     * @param integerCoordinateMany2 训练数据样本坐标2
     * @return 您传入的启动器中应有一个θ参与计算，它是公式中的一个未知参数，集成器的目的也是在一定的单调区间中推导出来此参数的数值。推导规则就是更符合您所传入的模型逻辑。
     * <p>
     * The initiator you pass in should have a θ involved in the calculation, which is an unknown parameter in the formula, and the purpose of the integrator is to derive the value of this parameter in a certain monotonic interval. Inference rules are more in line with the model logic you pass in.
     */
    public double run(IntegerCoordinateMany integerCoordinateMany1, IntegerCoordinateMany integerCoordinateMany2) {
        return CalculateTheMostSimilarParametersI(BuildingLearningSequences(integerCoordinateMany1.toArray(), integerCoordinateMany2.toArray()));
    }

    /**
     * @return 该集成器的子类身份, 用于父类子类之间的互相转换
     */
    @Override
    public IncrementalLearning expand() {
        return this;
    }

    /**
     * @return 该集成器的名称
     * <p>
     * the name of the integrator
     */
    @Override
    public String getIntegratorName() {
        return this.IntegratorName;
    }

    /**
     * @return 运行该集成器, 并返回运行结果，注意，来自父类的run，需要您调用setDoubleVector
     * <p>
     * Run the integrator and return the result
     */
    @Override
    public boolean run() {
        if (this.doubleVector1 != null && this.doubleVector2 != null) {
            if (this.tempRes != -1) {
                this.tempRes = -1;
            }
            this.tempRes = run(this.doubleVector1, this.doubleVector2);
            logger.info("+======================================= << " + this.IntegratorName + " >> stopped =============================================+");
            return true;
        } else {
            logger.error("您使用的是递增学习集成器的父类方法，在该方法运行之前，您应该调用setDoubleVector 为待计算的向量进行配置");
            logger.error("+ You are using the superclass method of the incremental learning integrator, before the method runs, you should call setDoubleVector to configure the vector to be computed");
            return false;
        }
    }

    /**
     * @return 运行该集成器, 并返回运行结果，该函数是带参数的运行，当运行成功之后会返回一个double类型的参数。该参数就是两个向量之中模型的最佳θ数值。
     * <p>
     * Run the integrator and return the result of the operation. The function is run with parameters. When the operation is successful, it will return a double type parameter.
     */
    @Override
    public double runAndReturnValue() {
        if (run()) {
            return this.tempRes;
        } else {
            this.tempRes = -1;
            return -1;
        }
    }
}
