package zhao.algorithmMagic.utils;

/**
 * Java类于 2022/10/14 12:25:56 创建
 *
 * @author 4
 */
public abstract class DoubleEvent implements Event<Double> {

    public boolean isComplianceEvents(double v) {
        return isComplianceEvents(Double.valueOf(v));
    }

    @Override
    public abstract boolean isComplianceEvents(Double v);
}
