package zhao.algorithmMagic.exception;

/**
 * Java类于 2022/10/9 22:38:18 创建
 * <p>
 * 算法没有找到异常
 *
 * @author 4
 */
public class OperationAlgorithmNotFound extends AlgorithmMagicException {

    public OperationAlgorithmNotFound() {
        super();
    }

    public OperationAlgorithmNotFound(String message) {
        super(message);
    }
}
