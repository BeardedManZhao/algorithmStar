package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.utils.ASClass;

import java.util.HashMap;

/**
 * 单例单元格对象，其中存储的数据是可以复用了，其在内部使用了一个 Hash 表来负责所有单元数据的单一维护。
 * <p>
 * Single instance cell objects, where the stored data can be reused, use a Hash table internally to handle the single maintenance of all unit data.
 *
 * @author 赵凌宇
 * 2023/4/12 17:50
 */
public final class SingletonCell<valueType> extends FinalCell<valueType> {

    /**
     * 单例哈希维护
     */
    private final static HashMap<String, Cell<?>> SING_HASH_MAP = new HashMap<>();

    /**
     * key数据
     */
    private final String key;

    private SingletonCell(String string) {
        super(string);
        key = string;
        SING_HASH_MAP.put(key, this);
    }

    private SingletonCell(valueType valueType) {
        super(valueType);
        key = valueType.toString();
        SING_HASH_MAP.put(key, this);
    }

    private SingletonCell(String string, boolean isNumber) {
        super(string, isNumber);
        key = string;
        SING_HASH_MAP.put(key, this);
    }

    /**
     * 将一个单元格获取到，该函数接收一个字符串，根据字符串匹配不同的数据类型。
     * <p>
     * Retrieve a cell, which receives a string and matches different data types based on the string.
     *
     * @param string 需要被匹配的字符串对象。
     *               <p>
     *               The string object that needs to be matched.
     * @return 获取到的单例单元格对象，该对象在内存中是唯一的。
     * <p>
     * The obtained singleton cell object is unique in memory.
     */
    public static Cell<?> $(String string) {
        Cell<?> singletonCell = SING_HASH_MAP.get(string);
        if (singletonCell != null) {
            // 代表当前内存中存储了这个数据，直接提取
            return singletonCell;
        }
        // 直接创建
        return new SingletonCell<>(string);
    }

    /**
     * 将一个单元格获取到，该函数接收一个字符串，根据字符串匹配不同的数据类型。
     * <p>
     * Retrieve a cell, which receives a string and matches different data types based on the string.
     *
     * @param string      需要被匹配的字符串对象。
     *                    <p>
     *                    The string object that needs to be matched.
     * @param <valueType> 当前单元格中要接收的数据类型。
     *                    <p>
     *                    The type of data to receive in the current cell.
     * @return 获取到的单例单元格对象，该对象在内存中是唯一的。
     * <p>
     * The obtained singleton cell object is unique in memory.
     */
    public static <valueType> Cell<valueType> $(valueType string) {
        Cell<?> singletonCell = SING_HASH_MAP.get(string.toString());
        if (singletonCell != null) {
            // 代表当前内存中存储了这个数据，直接提取
            return ASClass.transform(singletonCell);
        }
        // 直接创建
        return new SingletonCell<>(string);
    }

    /**
     * 将一个字符串类型的单元格获取到，该函数接收一个字符串，返回一个单例唯一的字符串单元格。
     * <p>
     * Retrieve a string type cell, which receives a string and returns a single instance unique string cell.
     *
     * @param String 需要封装的字符串对象。
     *               <p>
     *               String object that needs to be encapsulated.
     * @return 获取到的字符串类型的单元格对象，该对象在内存中是唯一的。
     * <p>
     * The obtained singleton cell object is unique in memory.
     */
    public static Cell<String> $_String(String String) {
        Cell<?> cell = SING_HASH_MAP.get(String);
        if (cell != null) return ASClass.transform(cell);
        else return new SingletonCell<>(String, false);
    }
}
