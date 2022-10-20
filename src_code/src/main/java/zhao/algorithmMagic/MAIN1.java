package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // Builder coordinates (2D)
        DoubleCoordinateTwo A = new DoubleCoordinateTwo(10, 10);
        DoubleCoordinateTwo B = new DoubleCoordinateTwo(-10, 4);
        DoubleCoordinateTwo C = new DoubleCoordinateTwo(1, 0);
        DoubleCoordinateTwo E = new DoubleCoordinateTwo(6, 1);
        DoubleCoordinateTwo Z = new DoubleCoordinateTwo(1, 21);

        DoubleConsanguinityRoute2D parse = DoubleConsanguinityRoute2D.parse("A -> B", A, B);
        DoubleConsanguinityRoute2D parse1 = DoubleConsanguinityRoute2D.parse("A -> C", A, C);
        DoubleConsanguinityRoute2D parse2 = DoubleConsanguinityRoute2D.parse("A -> Z", A, Z);
    }
}