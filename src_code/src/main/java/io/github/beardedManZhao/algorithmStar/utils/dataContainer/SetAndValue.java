package io.github.beardedManZhao.algorithmStar.utils.dataContainer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class SetAndValue<VALUE> {
    private final VALUE value;
    private final Set<String> stringSet;

    public SetAndValue(VALUE value, Set<String> stringSet) {
        this.value = value;
        this.stringSet = stringSet;
    }

    public SetAndValue(VALUE value, String... strings) {
        this.value = value;
        this.stringSet = new HashSet<>(Arrays.asList(strings));
    }

    public VALUE getValue() {
        return value;
    }

    public Set<String> getStringSet() {
        return stringSet;
    }
}
