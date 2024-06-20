package io.github.beardedManZhao.algorithmStar.exception;

/**
 * 当检查函数中发生检查出来的错误的时候会抛出此异常。
 * <p>
 * This exception is thrown when an error is detected in the check function.
 *
 * @author zhao
 */
public class CheckException extends AlgorithmMagicException {

    public CheckException(String message) {
        super(message);
    }
}
