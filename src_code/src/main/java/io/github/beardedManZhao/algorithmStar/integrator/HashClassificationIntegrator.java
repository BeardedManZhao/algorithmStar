package io.github.beardedManZhao.algorithmStar.integrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.integrator.launcher.HashClassificationLauncher;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.SetAndValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 分类集合集成器 ，采用特征匹配的方式进行数据的分类运算，需要实现启动器”HashClassificationLauncher“，在启动器中您可以设置类别的特征集合 与 不同数据的特征集合。
 * <p>
 * The classification set integrator uses feature matching to perform data classification operations. The initiator "HashClassificationLauncher" needs to be implemented. In the initiator, you can set the feature sets of categories and different data.
 *
 * @param <VALUE> 您需要分类的数据每一个元素的数据类型。
 *                <p>
 *                You need to classify the data type of each element of the data.
 */
public final class HashClassificationIntegrator<VALUE> implements AlgorithmIntegrator<HashClassificationIntegrator<VALUE>> {

    private final Logger logger;
    private final String IntegratorName;
    private final HashClassificationLauncher<VALUE> hashClassificationLauncher;

    /**
     * 将启动器实现类直接配置到集成器中
     *
     * @param integratorName             该集成器的名称
     * @param HashClassificationLauncher 实现了启动器的对象。
     */
    public HashClassificationIntegrator(String integratorName, HashClassificationLauncher<VALUE> HashClassificationLauncher) {
        IntegratorName = integratorName;
        this.logger = LoggerFactory.getLogger(integratorName);
        this.hashClassificationLauncher = HashClassificationLauncher;
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
    public HashClassificationIntegrator(String integratorName, String AlgorithmLauncherName) {
        IntegratorName = integratorName;
        logger = LoggerFactory.getLogger(integratorName);
        logger.info("+======================================= << " + this.IntegratorName + " >> started =============================================+");
        logger.info("+--------------------------------------- << Extract the algorithm required by the integrator >> ---------------------------------------+");
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(AlgorithmLauncherName);
        if (operationAlgorithm instanceof HashClassificationLauncher<?>) {
            this.hashClassificationLauncher = ASClass.transform(operationAlgorithm);
        } else {
            throw new TargetNotRealizedException("您提取的[" + AlgorithmLauncherName + "]算法被找到了，但是它没有实现 HashClassificationLauncher ，您如果想要使用该算法进行模型的预测学习，那么您要保证它实现了启动器。\n" +
                    "The [" + AlgorithmLauncherName + "] algorithm you extracted was found, but it does not implement HashClassificationLauncher . If you want to use this algorithm for predictive learning of your model, you must ensure that it implements a launcher. \n");
        }
    }

    @Override
    public HashClassificationIntegrator<VALUE> expand() {
        return this;
    }

    @Override
    public String getIntegratorName() {
        return this.IntegratorName;
    }

    /**
     * @return 运行集成器之后产生的数据是否为null
     * <p>
     * Whether the data generated after running the integrator is null
     */
    @Override
    public boolean run() {
        return runAndReturnValueSet() != null;
    }

    /**
     * 运行该集成器 同时返回一个分类的数据集，该方法是分类集成器特有的方法。
     *
     * @return 返回一个集成器分类之后的数据集合。
     */
    public HashMap<String, List<VALUE>> runAndReturnValueSet() {
        // 获取到所有的待分类数据集
        List<SetAndValue<VALUE>> setAndValues = this.hashClassificationLauncher.CategorizedData();
        // 获取到所有的类别与特征集
        HashMap<String, HashSet<String>> stringHashSetHashMap = this.hashClassificationLauncher.LoadCategoryLabels();
        // 构建结果集
        HashMap<String, List<VALUE>> res = new HashMap<>(0b11000);
        // 判断是否使用单分类
        if (this.hashClassificationLauncher.MultiClassificationMode()) {
            // 如果是使用多分类，那么就执行如下操作
            for (SetAndValue<VALUE> setAndValue : setAndValues) {
                // 判断当下的数据特征 属于哪个类别
                for (String s : stringHashSetHashMap.keySet()) {
                    // 判断当下的数据特征中是否包含该类别的所有特征
                    // 如果包含，代表当前的数据属于 s 类别 将当前数据增加到该列表的数据集合中
                    if (setAndValue.getStringSet().containsAll(stringHashSetHashMap.get(s))) {
                        VALUE value = setAndValue.getValue();
                        logger.info("MultiClassification >>> Data [" + value + "] belongs to category = [" + s + "]");
                        if (res.containsKey(s)) {
                            res.get(s).add(value);
                        } else {
                            List<VALUE> objects = new ArrayList<>();
                            objects.add(value);
                            res.put(s, objects);
                        }
                    }
                }
            }
        } else {
            // 如果是使用单分类，那么就执行如下操作
            for (SetAndValue<VALUE> setAndValue : setAndValues) {
                // 判断当下的数据特征 属于哪个类别
                for (String s : stringHashSetHashMap.keySet()) {
                    // 判断当下的数据特征中是否包含该类别的所有特征
                    // 如果包含，代表当前的数据属于 s 类别 将当前数据增加到该列表的数据集合中
                    if (setAndValue.getStringSet().containsAll(stringHashSetHashMap.get(s))) {
                        VALUE value = setAndValue.getValue();
                        logger.info("Single category >>> Data [" + value + "] belongs to category = [" + s + "]");
                        if (res.containsKey(s)) {
                            res.get(s).add(value);
                        } else {
                            List<VALUE> objects = new ArrayList<>();
                            objects.add(value);
                            res.put(s, objects);
                        }
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
     * @return 运行分类算法之后产生的数据集的数量。
     * <p>
     * The number of data sets generated after running the classification algorithm.
     */
    @Override
    public double runAndReturnValue() {
        return runAndReturnValueSet().size();
    }
}
