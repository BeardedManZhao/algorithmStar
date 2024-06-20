package io.github.beardedManZhao.algorithmStar.utils.filter;

import io.github.beardedManZhao.algorithmStar.utils.Event;

/**
 * 数值过滤器，专用于进行数值过滤而产生的过滤器
 *
 * @author zhao
 */
public abstract class NumericalFiltering implements Event<Number> {
    @Override
    public abstract boolean isComplianceEvents(Number v);

    public abstract boolean isComplianceEvents(int v);

    public abstract boolean isComplianceEvents(double v);
}
