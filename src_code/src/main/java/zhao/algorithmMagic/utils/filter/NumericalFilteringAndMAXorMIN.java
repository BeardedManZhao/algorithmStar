package zhao.algorithmMagic.utils.filter;

/**
 * 数值过滤器，专用于进行数值过滤而产生的过滤器
 *
 * @author zhao
 */
public abstract class NumericalFilteringAndMAXorMIN extends NumericalFiltering {
    public final boolean isMax;

    protected NumericalFilteringAndMAXorMIN(boolean isMax) {
        this.isMax = isMax;
    }

    @Override
    public abstract boolean isComplianceEvents(Number v);

    public abstract boolean isComplianceEvents(int v);

    public abstract boolean isComplianceEvents(double v);
}
