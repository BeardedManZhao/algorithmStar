package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到矩阵对象
        final DoubleMatrix matrix1 = DoubleMatrix.parse(
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 10, 11, 12}
        );
        final DoubleMatrix matrix2 = DoubleMatrix.parse(matrix1.toArrays().clone());
        // 将矩阵对象叠加三次 封装成为矩阵空间对象
        final DoubleMatrixSpace space1 = DoubleMatrixSpace.parse(matrix1, matrix1, matrix1);
        final DoubleMatrixSpace space2 = DoubleMatrixSpace.parse(matrix2, matrix2, matrix2);
        // 计算内积
        final Double aDouble = space1.innerProduct(space2);
        // 计算外积
        final DoubleMatrixSpace multiply = space1.multiply(space2);
        System.out.println(aDouble);
        System.out.println(multiply);
    }
}