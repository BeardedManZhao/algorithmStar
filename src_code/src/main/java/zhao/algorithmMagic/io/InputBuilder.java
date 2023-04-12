package zhao.algorithmMagic.io;

import zhao.algorithmMagic.operands.table.Cell;

/**
 * 数据输入组件生成器，其本身作为所有数据输入设备对象的建造者对象。
 * <p>
 * The data input component generator itself serves as the creator object for all data input device objects.
 *
 * @author 赵凌宇
 * 2023/4/5 13:54
 */
public interface InputBuilder {

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
    InputBuilder addInputArg(String key, Cell<?> value);

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    InputComponent create();

}
