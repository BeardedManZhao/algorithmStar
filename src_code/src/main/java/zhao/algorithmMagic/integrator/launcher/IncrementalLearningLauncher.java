package zhao.algorithmMagic.integrator.launcher;

/**
 * 递增学习启动器，您可以在其中配置您的数据基础模型，集成器会使用您的这个模型进行数据的训练。
 * <p>
 * Incremental learning initiator, where you can configure your data basic model, and the integrator will use your model for data training.
 */
public interface IncrementalLearningLauncher extends zhao.algorithmMagic.integrator.launcher.Launcher<zhao.algorithmMagic.integrator.launcher.Launcher<?>> {

    /**
     * 模型中的计算逻辑，您在这里需要对结果与其中的公式传入一个计算逻辑，这个逻辑也就是您的模型
     * <p>
     * The calculation logic in the model, you need to pass in a calculation logic for the result and the formula in it, this logic is also your model
     *
     * @param Yn 模型中的计算结果，一般就是数据集中代表结果的数值.
     *           <p>
     *           The calculation results in the model are generally the values representing the results in the data set.
     * @param Xn 模型中的参数，是影响数据计算结果的因素，但是其是一个来于数据集中的固定值，由该值得出Yn
     *           <p>
     *           The parameters in the model are factors that affect the results of the data calculation, but they are a fixed value from the data set, and Yn is derived from this value
     * @param $  也可以说是θ 结果与变量之间的权重参数，是计算结果(Yn)与模型参数(Xn)的一个联系的关键，该参数的分配将会是一个区间，您可以通过设置区间来固定在进行机器学习时，该值的范围越大，查找的范围越大，但是计算量也就越大，最终您可以在集成器中获取到一个合理的该数值。
     *           <p>
     *           It can also be said that it is the weight parameter between the θ result and the variable, and it is the key to the connection between the calculation result (Yn) and the model parameter (Xn). When doing machine learning, the larger the range of this value, the larger the search range, but the larger the amount of calculation, and finally you can get a reasonable value in the integrator.
     * @return 被您的模型所计算出来的一个新结果数值，该结果数值距离结果序列中的值(Yn)差距越大，代表当前模型越不使用，反之也是这样。
     * <p>
     * A new result value calculated by your model. The larger the difference between the result value and the value (Yn) in the result sequence, the less the current model will use, and vice versa.
     */
    double run(double Yn, double Xn, double $);
}
