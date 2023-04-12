package zhao.algorithmMagic.io;

import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.utils.ASClass;

import java.util.HashMap;

/**
 * 数据流输入设备对象建造者类，在该类中您可以针对数据流的数据输入操作进行一些配置，以获取到数据流中的数据对象。
 * <p>
 * The data flow input device object creator class allows you to configure the data input operations of the data flow to obtain the data objects in the data flow.
 */
public class InputByStreamBuilder extends HashMap<String, Cell<?>> implements InputBuilder {

    /**
     * 数据输入流
     */
    public final static String INPUT_STREAM = "ism";

    /**
     * 结构化数据下需要被读取的行
     */
    public final static String ROW_LEN = "rc";

    /**
     * DF对象读取中需要读取的行
     */
    public final static String PK = "pk";

    /**
     * 数据输入编码
     */
    public final static String CHARSET = "cs";

    /**
     * 结构化数据下需要使用的分隔符
     */
    public final static String SEP = "sep";

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
        return new InputByStream(
                ASClass.transform(this.get(INPUT_STREAM)),
                ASClass.transform(this.get(CHARSET)),
                this.getOrDefault(ROW_LEN, new FinalCell<>(10)).getIntValue(),
                this.getOrDefault(PK, new FinalCell<>(0)).getIntValue(),
                ASClass.<Cell<?>, Cell<Character>>transform(this.getOrDefault(SEP, new FinalCell<>(','))).getValue()
        );
    }
}
