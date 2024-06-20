package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.operands.table.FinalCell;

/**
 * 数据输出组件建造者类，由该类建造出有效的数据输出组件对象。
 * <p>
 * The data output component builder class, which constructs effective data output component objects.
 *
 * @author 赵凌宇
 * 2023/4/6 19:32
 */
public interface OutputBuilder {
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
    OutputBuilder addOutputArg(String key, FinalCell<?> value);

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    OutputComponent create();
}
