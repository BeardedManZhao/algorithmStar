package io.github.beardedManZhao.algorithmStar.exception;

/**
 * Java类于 2022/10/9 17:18:16 创建
 * <p>
 * 需要的类未实现，一般是由父类转子类导致的异常
 *
 * @author 4
 */
public final class TargetNotRealizedException extends AlgorithmMagicException {

    private static final String TAG = "TargetNotRealizedException_转换目标未实现：\n";

    public TargetNotRealizedException() {
        super();
    }

    public TargetNotRealizedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return TAG + super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return TAG + super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public String toString() {
        return TAG + super.toString();
    }
}
