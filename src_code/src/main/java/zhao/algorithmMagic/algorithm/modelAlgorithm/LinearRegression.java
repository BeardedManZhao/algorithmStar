package zhao.algorithmMagic.algorithm.modelAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;

/**
 * 线性回归计算组件，其能够接收一个初始线性模型，并将初始线性模型中的回归系数不断完善，然后返回最为准确的回归系数。
 * <p>
 * The linear regression calculation component can receive an initial linear model, continuously improve the regression coefficient in the initial linear model, and then return the most accurate regression coefficient.
 *
 * @author zhao
 */
public final class LinearRegression extends ModelAlgorithm {

    private int featureIndex = 0;
    private int targetIndex = 0;

    private LinearRegression(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static LinearRegression getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof LinearRegression) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于LinearRegression类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the LinearRegression type. Please redefine a name for this algorithm.");
            }
        } else {
            LinearRegression LinearRegression = new LinearRegression(Name);
            OperationAlgorithmManager.getInstance().register(LinearRegression);
            return LinearRegression;
        }
    }

    public void setFeatureIndex(int featureIndex) {
        this.featureIndex = featureIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param integerMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                      <p>
     *                      An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public double[] modelInference(IntegerMatrix integerMatrix) {
        return modelInference(targetIndex, integerMatrix);
    }

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param doubleMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                     <p>
     *                     An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    public double[] modelInference(DoubleMatrix doubleMatrix) {
        return modelInference(targetIndex, doubleMatrix);
    }

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex   目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                      <p>
     *                      Target value index, the column marked by this index will be the target value of this calculation!
     * @param integerMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                      <p>
     *                      An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    @Override
    public double[] modelInference(int targetIndex, IntegerMatrix integerMatrix) {
        return ASMath.linearRegression(integerMatrix.getArrayByColIndex(featureIndex), integerMatrix.getArrayByColIndex(targetIndex));
    }

    /**
     * 通过给定的一个模型，不断修正模型中的参数或其它方式，最终返回在最接近样本本身时所有参数组成的数组
     * <p>
     * Through a given model, continuously modify the parameters in the model or other ways, and finally return the array of all parameters when they are closest to the sample itself.
     *
     * @param targetIndex  目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                     <p>
     *                     Target value index, the column marked by this index will be the target value of this calculation!
     * @param doubleMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                     <p>
     *                     An array of all data rows to be calculated, where each array represents one row of data.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     */
    @Override
    public double[] modelInference(int targetIndex, DoubleMatrix doubleMatrix) {
        return ASMath.linearRegression(doubleMatrix.getArrayByColIndex(featureIndex), doubleMatrix.getArrayByColIndex(targetIndex));
    }

}