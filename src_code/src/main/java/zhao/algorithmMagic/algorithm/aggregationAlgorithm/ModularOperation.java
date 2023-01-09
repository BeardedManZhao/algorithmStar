package zhao.algorithmMagic.algorithm.aggregationAlgorithm;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.utils.ASClass;

/**
 * 向量模运算组件，在本组件中支持通过聚合一个序列中的所有元素，并计算出元素对应的模长。
 * <p>
 * Vector module operation component, which supports aggregation of all elements in a sequence and calculation of the corresponding module length of elements.
 *
 * @author zhao
 */
public class ModularOperation extends BatchAggregation {


    protected ModularOperation(String name) {
        super(name);
    }

    /**
     * 获取到该算法的类对象。
     * <p>
     * Get the class object of the algorithm.
     *
     * @param Name 该算法的名称
     * @return 算法类对象
     * @throws TargetNotRealizedException 当您传入的算法名称对应的组件不能被成功提取的时候会抛出异常
     *                                    <p>
     *                                    An exception will be thrown when the component corresponding to the algorithm name you passed in cannot be successfully extracted
     */
    public static ModularOperation getInstance(String Name) {
        if (OperationAlgorithmManager.containsAlgorithmName(Name)) {
            OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(Name);
            if (operationAlgorithm instanceof ModularOperation) {
                return ASClass.transform(operationAlgorithm);
            } else {
                throw new TargetNotRealizedException("您提取的[" + Name + "]算法被找到了，但是它不属于ModularOperation类型，请您为这个算法重新定义一个名称。\n" +
                        "The [" + Name + "] algorithm you ParameterCombination has been found, but it does not belong to the ModularOperation type. Please redefine a name for this algorithm.");
            }
        } else {
            ModularOperation ModularOperation = new ModularOperation(Name);
            OperationAlgorithmManager.getInstance().register(ModularOperation);
            return ModularOperation;
        }
    }

    /**
     * 将一个向量中的数据进行聚合，并返回聚合之后的所有数据结果。
     * <p>
     * Aggregates the data in a vector and returns all data results after aggregation.
     *
     * @param integerVector 需要被聚合的向量对象，向量中的所有元素将会被按照指定的逻辑进行聚合操作。
     *                      <p>
     *                      For vector objects to be aggregated, all elements in the vector will be aggregated according to the specified logic.
     * @return 向量中所有元素的聚合结果数值
     */
    @Override
    public int calculation(IntegerVector integerVector) {
        logger.info(INT_VECTOR_FUNCTION_LOG);
        return integerVector.moduleLength();
    }

    /**
     * 将一个向量中的数据进行聚合，并返回聚合之后的所有数据结果。
     * <p>
     * Aggregates the data in a vector and returns all data results after aggregation.
     *
     * @param doubleVector 需要被聚合的向量对象，向量中的所有元素将会被按照指定的逻辑进行聚合操作。
     *                     <p>
     *                     For vector objects to be aggregated, all elements in the vector will be aggregated according to the specified logic.
     * @return 向量中所有元素的聚合结果数值
     */
    @Override
    public double calculation(DoubleVector doubleVector) {
        logger.info(DOUBLE_VECTOR_FUNCTION_LOG);
        return doubleVector.moduleLength();
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param doubles 需要被聚合的所有元素组成的数组
     *                <p>
     *                An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    @Override
    public double calculation(double... doubles) {
        logger.info(DOUBLE_FUNCTION_LOG);
        return calculation(DoubleVector.parse(doubles));
    }

    /**
     * 计算函数，将某个数组中的所有元素按照某个规则进行聚合
     * <p>
     * Compute function to aggregate all elements in an array according to a certain rule
     *
     * @param ints 需要被聚合的所有元素组成的数组
     *             <p>
     *             An array of all elements to be aggregated
     * @return 一组数据被聚合之后的新结果
     */
    @Override
    public int calculation(int... ints) {
        logger.info(INT_FUNCTION_LOG);
        return calculation(IntegerVector.parse(ints));
    }
}
