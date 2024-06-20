package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;

import java.io.InputStream;
import java.util.HashMap;

/**
 * 对象数据输入组件。
 *
 * @author zhao
 */
public class InputObjectBuilder extends HashMap<String, Cell<?>> implements InputBuilder {
    public final static String IN_STREAM = "0xfff";

    /**
     * 添加数据输入描述，不同的组件有不同的配置属性，具体可以参阅实现类。
     * <p>
     * Add data input descriptions, and different components have different configuration properties. Please refer to the implementation class for details.
     *
     * @param key   属性名称
     *              <p>
     *              Attribute Name.
     * @param value 属性数值
     *              <p>
     *              Attribute Value.
     * @return 链式调用，继续构建
     * <p>
     * Chain call, continue building.
     */
    @Override
    public InputBuilder addInputArg(String key, Cell<?> value) {
        this.put(key, value);
        return this;
    }

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    @Override
    public InputComponent create() {
        final Object value = this.get(IN_STREAM).getValue();
        if (value instanceof InputStream) {
            return new InputObject((InputStream) value);
        }
        throw new OperatorOperationException("您提供的 ‘OUT_STREAM’ 参数应为一个包装这 InputStream 对象的单元格。");
    }
}
