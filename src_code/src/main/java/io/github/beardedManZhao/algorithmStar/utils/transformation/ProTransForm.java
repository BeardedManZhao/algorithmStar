package io.github.beardedManZhao.algorithmStar.utils.transformation;

import io.github.beardedManZhao.algorithmStar.operands.table.Cell;

import java.util.HashMap;

/**
 * 带参数的数据转换接口类，其中在接受一个需要被转换的对象之后还需要传递一个配置参数项。
 * <p>
 * A data conversion interface class with parameters, in which a configuration parameter item needs to be passed after receiving an object that needs to be converted.
 *
 * @author 赵凌宇
 * 2023/4/5 9:05
 */
public interface ProTransForm<InputType, OutputType> {

    /**
     * @param inputType 来自内部的待转换数据。
     *                  Data to be converted from inside.
     * @param value     转换时需要使用的参数列表，其中key是参数名称，value是参数数值的单元格对象，可以封装任意类型的数据，一般情况下，如果不需要传递参数可以直接传递null。
     *                  <p>
     *                  The list of parameters that need to be used during conversion, where key is the parameter name and value is the cell object of the parameter value, which can encapsulate any type of data. Generally, if no parameters need to be passed, null can be passed directly.
     * @return 转换之后的数据。
     * <p>
     * Data after conversion.
     */
    OutputType function(InputType inputType, HashMap<String, Cell<?>> value);
}
