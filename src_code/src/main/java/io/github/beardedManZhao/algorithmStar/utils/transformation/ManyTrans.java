package io.github.beardedManZhao.algorithmStar.utils.transformation;

/**
 * 转换函数接口，针对需要进行lambda设计的一切转换函数，都可以将此接接口作为形参数据类型。
 * <p>
 * The conversion function interface can be used as the formal parameter data type for all conversion functions requiring lambda design.
 *
 * @param <InputType>  数据输入数据类型，其代表lambda形参位置的数据类型。
 *                     <p>
 *                     Data input data type, which represents the data type of lambda formal parameter position.
 * @param <OutputType> 数据输出数据类型，其代表lambda函数返回的数据类型。
 *                     <p>
 *                     Data output data type, which represents the data type returned by lambda function.
 */
public interface ManyTrans<InputType, OutputType> {
    /**
     * 、
     *
     * @param inputType1 来自内部的待转换数据。
     *                   Data to be converted from inside.
     * @param inputType2 来自内部的待转换数据。
     *                   Data to be converted from inside.
     * @return 转换之后的数据。
     * <p>
     * Data after conversion.
     */
    OutputType function(InputType inputType1, InputType inputType2);
}
