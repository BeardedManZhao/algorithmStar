package io.github.beardedManZhao.algorithmStar.algorithm.modelAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.algorithm.distanceAlgorithm.DistanceAlgorithm;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASMath;
import io.github.beardedManZhao.algorithmStar.utils.DependentAlgorithmNameLibrary;

/**
 * 多元线性回归计算组件，其能够计算多元线性回归计算函数，能够实现强大的模型推导功能。
 * <p>
 * Multiple linear regression calculation component, which can calculate multiple linear regression calculation function, and can realize powerful model deduction function.
 *
 * @author zhao
 */
public final class MultipleLinearRegression extends ModelAlgorithm {

    private final static DistanceAlgorithm DISTANCE_ALGORITHM = DependentAlgorithmNameLibrary.EUCLIDEAN_METRIC;

    private float learningRate = 0.5f;

    private MultipleLinearRegression(String AlgorithmName) {
        super(AlgorithmName);
    }

    /**
     * 获取到该算法的类对象，
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     */
    public static MultipleLinearRegression getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof MultipleLinearRegression) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于 MultipleLinearRegression 类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you extracted has been found, but it does not belong to the MultipleLinearRegression type. Please redefine a name for this algorithm.");
            }
        } else {
            MultipleLinearRegression LinearRegression = new MultipleLinearRegression(Name);
            OperationAlgorithmManager.getInstance().register(LinearRegression);
            return LinearRegression;
        }
    }

    private static double[] hFunction(int targetIndex, DoubleMatrix doubleMatrix, double[] weight) {
        double[] tempRes = new double[doubleMatrix.getRowCount()];
        int count = -1;
        for (double[] doubles : doubleMatrix.toArrays()) {
            // 将当前行与权重值进行计算，并将结果提供给数组
            double rowRes = 0;
            int count1 = -1;
            for (int i = 0; i < doubles.length; i++) {
                if (i != targetIndex) {
                    rowRes += weight[++count1] * doubles[i];
                }
            }
            tempRes[++count] = rowRes;
        }
        return tempRes;
    }

    private static int[] hFunction(int targetIndex, IntegerMatrix integerMatrix, double[] weight) {
        int[] tempRes = new int[integerMatrix.getRowCount()];
        int count = -1;
        for (int[] doubles : integerMatrix.toArrays()) {
            // 将当前行与权重值进行计算，并将结果提供给数组
            int rowRes = 0, count1 = -1;
            for (int i = 0; i < doubles.length; i++) {
                if (i != targetIndex) {
                    rowRes += weight[++count1] * doubles[i];
                }
            }
            tempRes[++count] = rowRes;
        }
        return tempRes;
    }

    /**
     * 设置多元线性回归需要使用的学习率数值，其是百分比的小数表现形式，取值范围为[0.1, 1]。
     * <p>
     * Set the learning rate value to be used in multiple linear regression, which is the decimal representation of percentage, and the value range is [0.1, 1].
     *
     * @param learningRate 在多元线性回归是需要使用的学习率数值，取值范围为[0.1, 1].
     *                     <p>
     *                     The learning rate value to be used in multiple linear regression is [0.1, 1].
     */
    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
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
        return modelInference(targetIndex, integerMatrix, false);
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
        return modelInference(targetIndex, doubleMatrix, false);
    }

    /**
     * 自动生成权重数值，并根据自动生成的权重数值进行数据模型的计算。
     * <p>
     * Automatically generate the weight value, and calculate the data model according to the automatically generated weight value.
     *
     * @param targetIndex   目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                      <p>
     *                      Target value index, the column marked by this index will be the target value of this calculation!
     * @param integerMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                      <p>
     *                      An array of all data rows to be calculated, where each array represents one row of data.
     * @param by_Line       如果设置为true，则将会按照每一行生成权重值
     *                      <p>
     *                      If set to true, weight values will be generated according to each row
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     * @throws ArrayIndexOutOfBoundsException 您的目标索引设置出现错误时抛出。
     */
    public double[] modelInference(int targetIndex, IntegerMatrix integerMatrix, boolean by_Line) {
        if (by_Line) {
            // 如果是按行，那么后面的计算函数就不适用了，先进行一个转置
            integerMatrix = integerMatrix.transpose();
            return modelInference(targetIndex, integerMatrix, ASMath.generateWeight(targetIndex, integerMatrix, true), false);
        } else {
            // 如果是按列，那么后面的计算函数使用，直接进行计算即可
            return modelInference(targetIndex, integerMatrix, ASMath.generateWeight(targetIndex, integerMatrix, false), false);
        }
    }

    /**
     * 自动生成权重数值，并根据自动生成的权重数值进行数据模型的计算。
     * <p>
     * Automatically generate the weight value, and calculate the data model according to the automatically generated weight value.
     *
     * @param targetIndex  目标值索引，该索引所标记的列将会作为本次计算的目标值！
     *                     <p>
     *                     Target value index, the column marked by this index will be the target value of this calculation!
     * @param doubleMatrix 需要被计算的所有数据行组成的数组，其中每一个数组代表一行数据。
     *                     <p>
     *                     An array of all data rows to be calculated, where each array represents one row of data.
     * @param by_Line      如果设置为true，则将会按照每一行生成权重值
     *                     <p>
     *                     If set to true, weight values will be generated according to each row
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组。
     * <p>
     * An array composed of the inference results of the parameters to be inferred during each calculation.
     * @throws ArrayIndexOutOfBoundsException 您的目标索引设置出现错误时抛出。
     */
    public double[] modelInference(int targetIndex, DoubleMatrix doubleMatrix, boolean by_Line) {
        if (by_Line) {
            // 如果是按行，那么后面的计算函数就不适用了，先进行一个转置
            doubleMatrix = doubleMatrix.transpose();
            return modelInference(targetIndex, doubleMatrix, ASMath.generateWeight(targetIndex, doubleMatrix, true), false);
        } else {
            // 如果是按列，那么后面的计算函数使用，直接进行计算即可
            return modelInference(targetIndex, doubleMatrix, ASMath.generateWeight(targetIndex, doubleMatrix, false), false);
        }
    }

    /**
     * 通过多元线性回归模型与梯度下降的方式找到最适合的数据模型。
     * <p>
     * Find the most suitable data model through multiple linear regression model and gradient descent.
     *
     * @param targetIndex   目标值索引值，以该值为基准找到列数据，并将此列数据作为目标值基准。
     *                      <p>
     *                      Target value index value, find the column data based on this value, and use this column data as the target value benchmark.
     * @param integerMatrix 需要被计算的所有行特征向量组成的矩阵数据
     *                      <p>
     *                      Matrix data composed of all row eigenvectors to be calculated
     * @param weight        计算时，每一个非目标字段的回归系数，作为计算回归模型中的权重值。
     *                      <p>
     *                      During calculation, the regression coefficient of each non-target field is used as the weight value in the regression model.
     * @param isCopy        如果设置为true，那么在计算时会将传递的权重参数拷贝一份，确保其中的修改操作不会作用于形参。
     *                      <p>
     *                      If set to true, a copy of the passed weight parameter will be made during calculation to ensure that the modification operation will not affect the formal parameters.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组，其中最后一个元素是整个线性回归模型中的偏置数值，剩余所有数值属于线性模型中的权重参数。
     * <p>
     * An array composed of the inference results of the parameters to be inferred in each calculation. The last element is the bias value in the whole linear regression model, and all the remaining values belong to the weight parameters in the linear model.
     */
    public double[] modelInference(int targetIndex, IntegerMatrix integerMatrix, double[] weight, boolean isCopy) {
        {
            int i1 = integerMatrix.getColCount() - 1;
            if (targetIndex < 0 || targetIndex > i1) {
                throw new OperatorOperationException("您给定的目标值索引不正确，在此索引下没有对应的数据。\nThe target value index you have given is incorrect. There is no corresponding data under this index.");
            }
            if (weight.length != i1) {
                throw new OperatorOperationException(
                        "您给定的权重参数数量不正确，因为权重需要与每一列进行对应，因此权重的数量需要与特征列数量一致。\nThe number of weight parameters you have given is incorrect, because the weight needs to correspond to each column, so the number of weights needs to be consistent with the number of characteristic columns.\n" +
                                "According to your reshaping matrix, you need [" + i1 + "] features, but now you have only [" + weight.length + "]."
                );
            }
        }
        // 准备结果数组，其中存储的都是每一列的权重值
        double[] tempWeight;
        if (isCopy) {
            tempWeight = new double[weight.length];
            System.arraycopy(weight, 0, tempWeight, 0, weight.length);
        } else {
            tempWeight = weight;
        }
        // 首先将目标结果向量获取到
        int[] intVector = integerMatrix.getArrayByColIndex(targetIndex);
        // 然后开始使用给定的权重数组计算出每一行的结果，并将其封装成向量
        // 计算出相差距离（损失函数）
        double disValue = DISTANCE_ALGORITHM.getTrueDistance(intVector, hFunction(targetIndex, integerMatrix, tempWeight));
        // 记录当前损失函数的权重
        double[] disVector = new double[tempWeight.length];
        System.arraycopy(tempWeight, 0, disVector, 0, disVector.length);
        // 开始进行梯度下降，不断的与上一次进行比较
        while (true) {
            // 进行梯度下降
            double[] doubles = ASMath.gradientDescent(tempWeight, tempWeight.length, learningRate, disValue);
            // 计算出当前损失函数值的是否小于上一次损失函数的值，如果小于则尝试本次损失函数可以再小一些，如果不是就直接将上一次的数组返回出去
            double temp = DISTANCE_ALGORITHM.getTrueDistance(intVector, hFunction(targetIndex, integerMatrix, doubles));
            if (temp < disValue) {
                // 将当前的数组更新，然后计算下一批
                disValue = temp;
                System.arraycopy(tempWeight, 0, disVector, 0, disVector.length);
            } else {
                // 将当前的最后一个回归系数计算出来
                double[] res = new double[disVector.length + 1];
                System.arraycopy(disVector, 0, res, 0, disVector.length);
                res[disVector.length] = ASMath.avg(intVector) - ASMath.avg(hFunction(targetIndex, integerMatrix, disVector));
                return res;
            }
        }
    }

    /**
     * 通过多元线性回归模型与梯度下降的方式找到最适合的数据模型。
     * <p>
     * Find the most suitable data model through multiple linear regression model and gradient descent.
     *
     * @param targetIndex  目标值索引值，以该值为基准找到列数据，并将此列数据作为目标值基准。
     *                     <p>
     *                     Target value index value, find the column data based on this value, and use this column data as the target value benchmark.
     * @param doubleMatrix 需要被计算的所有行特征向量组成的矩阵数据
     *                     <p>
     *                     Matrix data composed of all row eigenvectors to be calculated
     * @param weight       计算时，每一个非目标字段的回归系数，作为计算回归模型中的权重值。
     *                     <p>
     *                     During calculation, the regression coefficient of each non-target field is used as the weight value in the regression model.
     * @param isCopy       如果设置为true，那么在计算时会将传递的权重参数拷贝一份，确保其中的修改操作不会作用于形参。
     *                     <p>
     *                     If set to true, a copy of the passed weight parameter will be made during calculation to ensure that the modification operation will not affect the formal parameters.
     * @return 每一个计算时的待推断参数的推断结果组成的一个数组，其中最后一个元素是整个线性回归模型中的偏置数值，剩余所有数值属于线性模型中的权重参数。
     * <p>
     * An array composed of the inference results of the parameters to be inferred in each calculation. The last element is the bias value in the whole linear regression model, and all the remaining values belong to the weight parameters in the linear model.
     */
    public double[] modelInference(int targetIndex, DoubleMatrix doubleMatrix, double[] weight, boolean isCopy) {
        {
            int i1 = doubleMatrix.getColCount() - 1;
            if (targetIndex < 0 || targetIndex > i1) {
                throw new OperatorOperationException("您给定的目标值索引不正确，在此索引下没有对应的数据。\nThe target value index you have given is incorrect. There is no corresponding data under this index.");
            }

            if (weight.length != i1) {
                throw new OperatorOperationException(
                        "您给定的权重参数数量不正确，因为权重需要与每一列进行对应，因此权重的数量需要与特征列数量一致。\nThe number of weight parameters you have given is incorrect, because the weight needs to correspond to each column, so the number of weights needs to be consistent with the number of characteristic columns.\n" +
                                "According to your double matrix, you need [" + i1 + "] features, but now you have only [" + weight.length + "]."
                );
            }
        }
        // 准备结果数组，其中存储的都是每一列的权重值
        double[] tempWeight;
        if (isCopy) {
            tempWeight = new double[weight.length];
            System.arraycopy(weight, 0, tempWeight, 0, weight.length);
        } else {
            tempWeight = weight;
        }
        // 首先将目标结果向量获取到
        double[] doubleVector = doubleMatrix.getArrayByColIndex(targetIndex);
        // 然后开始使用给定的权重数组计算出每一行的结果，并将其封装成向量
        // 计算出相差距离（损失函数）
        double disValue = DISTANCE_ALGORITHM.getTrueDistance(doubleVector, hFunction(targetIndex, doubleMatrix, tempWeight));
        // 记录当前损失函数的权重
        double[] disVector = new double[tempWeight.length];
        System.arraycopy(tempWeight, 0, disVector, 0, disVector.length);
        // 开始进行梯度下降，不断的与上一次进行比较
        while (true) {
            // 进行梯度下降
            double[] doubles = ASMath.gradientDescent(tempWeight, tempWeight.length, learningRate, disValue);
            // 计算出当前损失函数值的是否小于上一次损失函数的值，如果小于则尝试本次损失函数可以再小一些，如果不是就直接将上一次的数组返回出去
            double temp = DISTANCE_ALGORITHM.getTrueDistance(doubleVector, hFunction(targetIndex, doubleMatrix, doubles));
            if (temp < disValue) {
                // 将当前的数组更新，然后计算下一批
                disValue = temp;
                System.arraycopy(tempWeight, 0, disVector, 0, disVector.length);
            } else {
                // 将当前的最后一个回归系数计算出来
                int i = disVector.length;
                double[] res = new double[i + 1];
                System.arraycopy(disVector, 0, res, 0, disVector.length);
                res[i] = ASMath.avg(doubleVector) - ASMath.avg(hFunction(targetIndex, doubleMatrix, disVector));
                return res;
            }
        }
    }
}
