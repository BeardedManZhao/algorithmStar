package zhao.algorithmMagic.exception;

/**
 * Java类于 2022/10/9 17:15:01 创建
 *
 * @author 4
 */
public class ConversionException extends AlgorithmMagicException {

    private static final String TAG = "ConversionException_转换异常：";

    public ConversionException() {
        super();
    }

    public ConversionException(String message) {
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
