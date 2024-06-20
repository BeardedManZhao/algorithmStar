package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.operands.vector.DoubleVector;
import io.github.beardedManZhao.algorithmStar.utils.dataContainer.KeyValue;

/**
 * @param <v> 该分类模型能够接收的输入数据对象，一般情况下这里是一个向量
 *            2023/4/27 11:16
 * @author 赵凌宇
 */
public abstract class ClassificationModel<v> implements ASModel<Integer, v, KeyValue<String[], DoubleVector[]>> {

}
