import io.github.beardedManZhao.algorithmStar.core.AlgorithmStar;
import io.github.beardedManZhao.algorithmStar.core.MatrixFactory;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ComplexNumberMatrix;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        MatrixFactory matrixFactory = AlgorithmStar.matrixFactory();
        ComplexNumberMatrix complexNumbers = matrixFactory.parseComplexNumberMatrix(
                new String[]{
                        "1 + 2i", "2 + 4i", "3 + 5i", "4 + 6i", "5 + 7i"
                },
                new String[]{
                        "1 + 2i", "2 + 4i", "3 + 5i", "4 + 6i", "5 + 7i"
                }
        );
        System.out.println(complexNumbers);
    }
}