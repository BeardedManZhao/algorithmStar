package zhao.algorithmMagic.lntegrator.launcher;

import zhao.algorithmMagic.utils.dataContainer.SetAndValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 分类集成器的启动器 该启动器中需要提供给集成器的函数有 类别与特征集合 分类模式 被分类的数据集
 *
 * @param <VALUE> 数据本身是什么类型的数据
 */
public interface HashClassificationLauncher<VALUE> extends zhao.algorithmMagic.lntegrator.launcher.Launcher<HashClassificationLauncher<VALUE>> {

    /**
     * 将标签与类别提供给分类集成器，在这里您应该返回一个 key为类别名称  value 为类别包含特征的集合
     * <p>
     * Provide labels and categories to the taxonomy integrator, where you should return a set with key as the category name value as the category containing the features
     *
     * @return key为类别名称  value 为类别包含特征的集合 的哈希集合。
     * <p>
     * Key is the category name value is the hash set of the collection containing the features of the category.
     */
    HashMap<String, HashSet<String>> LoadCategoryLabels();

    /**
     * @return 一个数据属于能否多个类别，true代表允许属于多个类别
     * <p>
     * Can a data belong to more than one category, true means allow to belong to more than one category
     */
    boolean MultiClassificationMode();

    /**
     * @return 被分类的数据，这里使用的是一个数据容器，在该容器中，有数据的名字 数据的特征集合 数据本身 三个字段。
     * <p>
     * The categorized data, which is used here as a data container, contains three fields of the feature set data itself with the data's name data.
     */
    List<SetAndValue<VALUE>> CategorizedData();
}
