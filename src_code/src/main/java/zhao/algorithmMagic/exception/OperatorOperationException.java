package zhao.algorithmMagic.exception;

/**
 * 操作符运算错误，当操作符与操作符之间的运算发生错误的时候会抛出该异常。
 */
public class OperatorOperationException extends AlgorithmMagicException {

    private static final String TAG = "OperandConversionException_操作符运算错误：\n";

    public OperatorOperationException() {
        super();
    }

    public OperatorOperationException(String message) {
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
