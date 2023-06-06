package zhao.algorithmMagic.utils.dataContainer;

import java.io.Serializable;

/**
 * @author 赵凌宇
 * 2023/4/17 20:24
 */
public final class KeyValue<k, v> implements Serializable {
    private final k key;
    private final v value;

    public KeyValue(k key, v value) {
        this.key = key;
        this.value = value;
    }

    public k getKey() {
        return key;
    }

    public v getValue() {
        return value;
    }
}
