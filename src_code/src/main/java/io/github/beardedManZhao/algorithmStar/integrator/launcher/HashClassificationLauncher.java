package io.github.beardedManZhao.algorithmStar.integrator.launcher;

import io.github.beardedManZhao.algorithmStar.utils.dataContainer.SetAndValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 分类集成器的启动器 该启动器中需要提供给集成器的函数有 类别与特征集合 分类模式 被分类的数据集，您需要对该启动器的方法以此实现，才能将该启动器提供给集成器去运行。
 * <p>
 * Classifies the initiator of the integrator. The functions that the initiator needs to provide to the integrator include the data sets classified by category and feature set classification mode. You need to implement this method for the initiator in order to provide the initiator to the integrator for operation.
 *
 * @param <VALUE> 数据本身的数据类型，在该启动器中，最小的数据单位是一个数据容器，而数据容器中有一个泛型字段，字段存储的是真正被分类的数据，在这里的泛型标识就是容器中数据字段的数据类型
 *                <p>
 *                The data type of the data itself. In this initiator, the smallest data unit is a data container, and there is a generic field in the data container. The field stores the really classified data. The generic ID here is the data type of the data field in the container
 */
public interface HashClassificationLauncher<VALUE> extends io.github.beardedManZhao.algorithmStar.integrator.launcher.Launcher<HashClassificationLauncher<VALUE>> {

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
