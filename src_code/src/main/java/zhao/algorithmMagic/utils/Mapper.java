package zhao.algorithmMagic.utils;

/**
 * 数据映射函数接口
 *
 * @param <Input>  输入的数据类型
 * @param <Output> 输出的数据类型
 */
public interface Mapper<Input, Output> {
    Output run(Input input);
}
