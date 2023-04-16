package zhao.algorithmMagic.io;

import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.utils.ASClass;

import java.util.HashMap;


/**
 * 打印机设备数据输出组件对象的建造者类，由该类建造出对应的打印机设备输出类。
 * <p>
 * The creator class of the printer device data output component object, which constructs the corresponding printer device output class.
 *
 * @author zhao
 */
public class PrintServiceOutputBuilder extends HashMap<String, Cell<?>> implements OutputBuilder {

    /**
     * HTML 中的表名称
     */
    public final static String HTML_TABLE_NAME = "HTN";

    /**
     * 打印机的打印格式
     */
    public final static String DOC_FLAVOR = "DocF";

    /**
     * 打印请求配置信息集合
     */
    public final static String ATTR = "attr";

    /**
     * 打印服务设备名称
     */
    public final static String PRINT_SERVER_NAME = "PSN";

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
    public OutputBuilder addOutputArg(String key, FinalCell<?> value) {
        this.put(key, value);
        return this;
    }

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    @Override
    public OutputComponent create() {
        return new PrintServiceOutput(
                ASClass.transform(this.get(DOC_FLAVOR)),
                ASClass.transform(this.get(ATTR)),
                ASClass.transform(this.get(PRINT_SERVER_NAME)),
                ASClass.transform(this.get(HTML_TABLE_NAME))
        );
    }
}
