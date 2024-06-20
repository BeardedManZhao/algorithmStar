package io.github.beardedManZhao.algorithmStar.utils.filter;

import io.github.beardedManZhao.algorithmStar.utils.Event;

public interface ArrayIntegerEvent extends Event<int[]> {
    /**
     * @param array     被判断或操作的数组
     * @param parameter 操作参数
     * @return 操作结果
     */
    boolean isComplianceEvents(int[] array, Integer... parameter);

    String getError();
}
