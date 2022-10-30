package zhao.algorithmMagic.integrator;

import org.apache.log4j.Logger;
import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.integrator.launcher.HashClassificationLauncher;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.dataContainer.SetAndValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HashClassificationIntegrator<VALUE> implements AlgorithmIntegrator<HashClassificationIntegrator<VALUE>> {

    private final Logger logger;
    private final String IntegratorName;
    private final HashClassificationLauncher<VALUE> hashClassificationLauncher;

    public HashClassificationIntegrator(String integratorName, HashClassificationLauncher<VALUE> HashClassificationLauncher) {
        IntegratorName = integratorName;
        this.logger = Logger.getLogger(integratorName);
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
        logger = Logger.getLogger(integratorName);
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

    @Override
    public boolean run() {
        return runAndReturnValueSet() != null;
    }

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

    @Override
    public double runAndReturnValue() {
        return runAndReturnValueSet().size();
    }
}
