package io.github.beardedManZhao.algorithmStar.utils.filter;

import io.github.beardedManZhao.algorithmStar.utils.Event;

public interface ArrayDoubleEvent extends Event<double[]> {
    /**
     * @param array     被判断或操作的数组
     * @param parameter 操作参数
     * @return 操作结果
     */
    boolean isComplianceEvents(double[] array, Integer... parameter);

    String getError();
}
