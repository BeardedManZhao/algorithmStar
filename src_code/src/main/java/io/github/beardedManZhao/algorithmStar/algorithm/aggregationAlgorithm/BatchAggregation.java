package io.github.beardedManZhao.algorithmStar.algorithm.aggregationAlgorithm;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.operands.vector.IntegerVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 批聚合算法的抽象类，在该类中包含这处理多组数据的聚合计算的函数。
 * <p>
 * The abstract class of batch aggregation algorithm, which contains the aggregation calculation function for processing multiple groups of data.
 *
 * @author zhao
 */
@SuppressWarnings("all")
public abstract class BatchAggregation implements AggregationAlgorithm {

    protected final static String INT_FUNCTION_LOG = "run function => int calculation(int... doubles)";
    protected final static String DOUBLE_FUNCTION_LOG = "run function => double calculation(double... doubles)";
    protected final static String INTS_FUNCTION_LOG = "run function => int[] calculation(boolean Multithreading, int[]... ints)";
    protected final static String DOUBLES_FUNCTION_LOG = "run function => double[] calculation(boolean Multithreading, double[]... doubles)";
    protected final static String INT_VECTOR_FUNCTION_LOG = "run function => int calculation(IntegerVector integerVector)";
    protected final static String DOUBLE_VECTOR_FUNCTION_LOG = "run function => double calculation(DoubleVector doubleVector)";
    protected final static String INTS_VECTOR_FUNCTION_LOG = "run function => int[] calculation(boolean Multithreading, IntegerVector... integerVector)";
    protected final static String DOUBLES_VECTOR_FUNCTION_LOG = "run function => double[] calculation(boolean Multithreading, DoubleVector... doubleVector)";
    protected final Logger logger;
    private final String Name;

    protected BatchAggregation(String name) {
        Name = name;
        logger = LoggerFactory.getLogger(name);
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
    public int calculation(IntegerVector integerVector) {
        logger.info(INT_VECTOR_FUNCTION_LOG);
        return calculation(integerVector.toArray());
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
    public double calculation(DoubleVector doubleVector) {
        logger.info(DOUBLE_VECTOR_FUNCTION_LOG);
        return calculation(doubleVector.toArray());
    }

    /**
     * 将一个向量中的数据进行聚合，并返回聚合之后的所有数据结果。
     * <p>
     * Aggregates the data in a vector and returns all data results after aggregation.
     *
     * @param integerVector  需要被聚合的向量对象，向量中的所有元素将会被按照指定的逻辑进行聚合操作。
     *                       <p>
     *                       For vector objects to be aggregated, all elements in the vector will be aggregated according to the specified logic.
     * @param Multithreading 如果设置为true，代表本次聚合使用多线程聚合，如果使用这种方式进行聚合，在聚合算法复杂的情况下，性能提升会很明显
     *                       <p>
     *                       If it is set to true, it means that this aggregation uses multithreaded aggregation. If this method is used for aggregation, the performance will be significantly improved when the aggregation algorithm is complex
     * @return 向量中所有元素的聚合结果数值
     */
    public int[] calculation(boolean Multithreading, IntegerVector... integerVector) {
        logger.info(INTS_VECTOR_FUNCTION_LOG);
        final int[] res = new int[integerVector.length];
        if (Multithreading) {
            final CountDownLatch countDownLatch = new CountDownLatch(integerVector.length);
            for (int i = 0; i < integerVector.length; i++) {
                final int finalI = i;
                new Thread(() -> {
                    res[finalI] = calculation(integerVector[finalI]);
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new OperatorOperationException("并发计算数据发生错误，原本计划的等待计算然后返回结果，但是现在等待过程被中断了\nAn error occurred in the concurrent calculation data. The originally planned wait for the calculation and then return the result. But Now the waiting process has been interrupted", e);
            }
        } else {
            int count = -1;
            for (IntegerVector vector : integerVector) {
                res[++count] = calculation(vector);
            }
        }
        return res;
    }

    /**
     * 将一个向量中的数据进行聚合，并返回聚合之后的所有数据结果。
     * <p>
     * Aggregates the data in a vector and returns all data results after aggregation.
     *
     * @param doubleVector   需要被聚合的向量对象，向量中的所有元素将会被按照指定的逻辑进行聚合操作。
     *                       <p>
     *                       For vector objects to be aggregated, all elements in the vector will be aggregated according to the specified logic.
     * @param Multithreading 如果设置为true，代表本次聚合使用多线程聚合，如果使用这种方式进行聚合，在聚合算法复杂的情况下，性能提升会很明显
     *                       <p>
     *                       If it is set to true, it means that this aggregation uses multithreaded aggregation.
     *                       If this method is used for aggregation, the performance will be significantly improved when the aggregation algorithm is complex
     * @return 向量中所有元素的聚合结果数值
     */
    public double[] calculation(boolean Multithreading, DoubleVector... doubleVector) {
        logger.info(DOUBLES_VECTOR_FUNCTION_LOG);
        final double[] res = new double[doubleVector.length];
        if (Multithreading) {
            final CountDownLatch countDownLatch = new CountDownLatch(doubleVector.length);
            for (int i = 0; i < doubleVector.length; i++) {
                final int finalI = i;
                new Thread(() -> {
                    res[finalI] = calculation(doubleVector[finalI]);
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new OperatorOperationException("并发计算数据发生错误，原本计划的等待计算然后返回结果，但是现在等待过程被中断了\nAn error occurred in the concurrent calculation data. The originally planned wait for the calculation and then return the result. But Now the waiting process has been interrupted", e);
            }
        } else {
            int count = -1;
            for (DoubleVector vector : doubleVector) {
                res[++count] = calculation(vector);
            }
        }
        return res;
    }

    /**
     * 多将组数据一起进行聚合，每一组数据聚合之后的结果会作为结果数组中的一个元素。
     * <p>
     * Multiple groups of data are aggregated together, and the result of each group of data aggregation will be used as an element in the result array.
     *
     * @param Multithreading 如果设置为true，代表本次聚合使用多线程聚合，如果使用这种方式进行聚合，在聚合算法复杂的情况下，性能提升会很明显
     *                       <p>
     *                       If it is set to true, it means that this aggregation uses multithreaded aggregation. If this method is used for aggregation, the performance will be significantly improved when the aggregation algorithm is complex
     * @param doubles        需要被聚合的所有组的数据，其中每个参数都是一个数组，每一个数组所有元素的聚合结果会提供到结果数组中。
     *                       <p>
     *                       The data of all groups to be aggregated. Each parameter is an array, and the aggregation results of all elements of each array will be provided to the result array.
     * @return 每一组数据聚合之后的新数组的结果数值。
     * <p>
     * The result value of the new array after aggregation of each group of data.
     */
    public double[] calculation(boolean Multithreading, double[]... doubles) {
        logger.info(DOUBLES_FUNCTION_LOG);
        double[] res = new double[doubles.length];
        if (Multithreading) {
            final CountDownLatch countDownLatch = new CountDownLatch(doubles.length);
            for (int i = 0; i < doubles.length; i++) {
                final int finalI = i;
                new Thread(() -> {
                    res[finalI] = calculation(doubles[finalI]);
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new OperatorOperationException("并发计算数据发生错误，原本计划的等待计算然后返回结果，但是现在等待过程被中断了\nAn error occurred in the concurrent calculation data. The originally planned wait for the calculation and then return the result. But Now the waiting process has been interrupted", e);
            }
        } else {
            int count = -1;
            for (double[] aDouble : doubles) {
                res[++count] = calculation(aDouble);
            }
        }
        return res;
    }


    /**
     * 多将组数据一起进行聚合，每一组数据聚合之后的结果会作为结果数组中的一个元素。
     * <p>
     * Multiple groups of data are aggregated together, and the result of each group of data aggregation will be used as an element in the result array.
     *
     * @param Multithreading 如果设置为true，代表本次聚合使用多线程聚合，如果使用这种方式进行聚合，在聚合算法复杂的情况下，性能提升会很明显
     *                       <p>
     *                       If it is set to true, it means that this aggregation uses multithreaded aggregation. If this method is used for aggregation, the performance will be significantly improved when the aggregation algorithm is complex
     * @param ints           需要被聚合的所有组的数据，其中每个参数都是一个数组，每一个数组所有元素的聚合结果会提供到结果数组中。
     *                       <p>
     *                       The data of all groups to be aggregated. Each parameter is an array, and the aggregation results of all elements of each array will be provided to the result array.
     * @return 每一组数据聚合之后的新数组的结果数值。
     * <p>
     * The result value of the new array after aggregation of each group of data.
     */
    public int[] calculation(boolean Multithreading, int[]... ints) {
        logger.info(INTS_FUNCTION_LOG);
        int[] res = new int[ints.length];
        if (Multithreading) {
            final CountDownLatch countDownLatch = new CountDownLatch(ints.length);
            for (int i = 0; i < ints.length; i++) {
                final int finalI = i;
                new Thread(() -> {
                    res[finalI] = calculation(ints[finalI]);
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new OperatorOperationException("并发计算数据发生错误，原本计划的等待计算然后返回结果，但是现在等待过程被中断了\nAn error occurred in the concurrent calculation data. The originally planned wait for the calculation and then return the result. But Now the waiting process has been interrupted", e);
            }
        } else {
            int count = -1;
            for (int[] aDouble : ints) {
                res[++count] = calculation(aDouble);
            }
        }
        return res;
    }

    /**
     * @return 该算法组件的名称，也是一个识别码，在获取算法的时候您可以通过该名称获取到算法对象
     * <p>
     * The name of the algorithm component is also an identification code. You can obtain the algorithm object through this name when obtaining the algorithm.
     */
    @Override
    public String getAlgorithmName() {
        return this.Name;
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
        if (!OperationAlgorithmManager.containsAlgorithmName(this.getAlgorithmName())) {
            OperationAlgorithmManager.getInstance().register(this);
            return true;
        } else {
            return false;
        }
    }
}
