package io.github.beardedManZhao.algorithmStar.io;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.table.FinalCell;
import io.github.beardedManZhao.algorithmStar.operands.table.SingletonCell;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;

import java.util.HashMap;

/**
 * HDFS数据输入组件建造者对象，在该对象中能够为HDFS的数据输入组件进行构建。
 * <p>
 * HDFS data input component object, which can obtain file data from the HDFS platform.
 *
 * @author 赵凌宇
 * 2023/4/6 9:18
 */
public class InputHDFSBuilder extends HashMap<String, Cell<?>> implements InputBuilder {

    /**
     * HDFS文件系统对象
     */
    public final static String FILE_SYSTEM = "FS";

    /**
     * 数据加载分隔符
     */
    public final static String SEP = "sep";

    /**
     * 数据输入路径
     */
    public final static String IN_PATH = "inPath";

    /**
     * 数据输入字符集
     */
    public final static String CHAR_SET = "cs";

    /**
     * 数据输入主键
     */
    public final static String PK = "pk";

    /**
     * 数据字符名称
     */
    public final static String FIELD = "field";

    /**
     * 读取的矩阵行数量，在读取矩阵的时候会使用该参数，默认读取全部行。
     */
    public final static String ROW_COUNT = "RC";

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
        return new InputHDFS(
                (FileSystem) this.get(FILE_SYSTEM).getValue(),
                new Path(this.get(IN_PATH).toString()),
                ASClass.<Cell<?>, Cell<Character>>transform(this.getOrDefault(SEP, new FinalCell<>(','))).getValue(),
                this.getOrDefault(CHAR_SET, new FinalCell<>("utf-8")).toString(),
                ASClass.<Cell<?>, Cell<String[]>>transform(this.get(FIELD)),
                this.getOrDefault(PK, new FinalCell<>(0)).getIntValue(),
                this.getOrDefault(ROW_COUNT, SingletonCell.$(-1)).getIntValue()
        );
    }
}
