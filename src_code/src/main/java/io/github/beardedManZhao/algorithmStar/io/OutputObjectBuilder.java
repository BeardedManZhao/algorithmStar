package io.github.beardedManZhao.algorithmStar.io;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.table.FinalCell;
import io.github.beardedManZhao.algorithmStar.operands.table.SingletonCell;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 对象输出组件的构建者类，在该类中可以针对输出对象的流进行设置，并构建出对应的数据流对象。
 * <p>
 * The builder class of the object output component, in which the flow of the output object can be set and corresponding data flow objects can be constructed.
 *
 * @author zhao
 */
public class OutputObjectBuilder extends HashMap<String, Cell<?>> implements OutputBuilder {

    public final static String OUT_STREAM = "0xfff";

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
     * 设置操作的文件对应的路径
     *
     * @param path 文件的路径。
     * @return 链式调用
     */
    public OutputBuilder setPath(String path) {
        this.addOutputArg(OUT_STREAM, (FinalCell<?>) SingletonCell.$(path));
        return this;
    }

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    @Override
    public OutputComponent create() {
        final Object value = this.get(OUT_STREAM).getValue();
        if (value instanceof OutputStream) {
            return new OutputObject((OutputStream) value);
        } else if (value instanceof String) {
            try {
                return new OutputObject(new FileOutputStream((String) value));
            } catch (FileNotFoundException e) {
                throw new UnsupportedOperationException(e);
            }
        }
        throw new OperatorOperationException("您提供的 ‘OUT_STREAM’ 参数应为一个包装这 OutputStream 对象的单元格。");
    }
}
