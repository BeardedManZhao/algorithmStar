package zhao.algorithmMagic.io;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.utils.ASClass;

import java.util.HashMap;

/**
 * HDFS数据输出设备的建造者类，通过该类建造出来HDFS数据输出示例。
 * <p>
 * The builder class of HDFS data output devices, through which HDFS data output examples are constructed.
 *
 * @author 赵凌宇
 * 2023/4/6 20:01
 */
public class OutputHDFSBuilder extends HashMap<String, Cell<?>> implements OutputBuilder {

    /**
     * HDFS文件系统对象
     */
    public final static String FILE_SYSTEM = "FS";

    /**
     * 数据加载分隔符
     */
    public final static String SEP = "sep";

    /**
     * 数据输出路径
     */
    public final static String OUT_PATH = "outPath";

    /**
     * 数据输出时的格式，目前支持列表如下所示
     * 文本数据：csv
     * 图像数据：Java支持的所有图像格式
     */
    public final static String FORMAT = "format";

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
        return new OutputHDFS(
                (FileSystem) this.get(FILE_SYSTEM).getValue(),
                (Path) this.get(OUT_PATH).getValue(),
                this.get(FORMAT).toString(),
                ASClass.<Cell<?>, Cell<Character>>transform(this.getOrDefault(SEP, new FinalCell<>(','))).getValue()
        );
    }
}
