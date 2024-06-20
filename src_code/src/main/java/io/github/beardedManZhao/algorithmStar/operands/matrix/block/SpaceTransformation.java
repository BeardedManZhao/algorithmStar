package io.github.beardedManZhao.algorithmStar.operands.matrix.block;

import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;

/**
 * 空间转换函数接口，此接口具有直接修改原矩阵的能力，因此不能够被外界轻易的实例化。
 * <p>
 * The space conversion function interface has the ability to directly modify the original matrix, so it cannot be easily instantiated by the outside world.
 *
 * @param <InputType>  数据输入数据类型，其代表lambda形参位置的数据类型。
 *                     <p>
 *                     Data input data type, which represents the data type of lambda formal parameter position.
 * @param <OutputType> 数据输出数据类型，其代表lambda函数返回的数据类型。
 *                     <p>
 *                     Data output data type, which represents the data type returned by lambda function.
 */
interface SpaceTransformation<InputType, OutputType> extends Transformation<InputType, OutputType> {

}
