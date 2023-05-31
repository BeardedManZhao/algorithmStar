package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到整形与浮点矩阵对象
        DoubleMatrix doubleMatrix = DoubleMatrix.parse(
                new double[]{1.5, 2.5, 3.5},
                new double[]{3.5, 4.5, 5.5},
                new double[]{7.5, 8.5, 9.5}
        );

        IntegerMatrix integerMatrix = IntegerMatrix.parse(
                new int[]{1, 2, 3},
                new int[]{3, 4, 5},
                new int[]{7, 8, 9}
        );

        // 获取到两个向量
        IntegerVector integerVector = IntegerVector.parse(10, 20, 30);
        DoubleVector doubleVector = DoubleVector.parse(10.5, 20.5, 30.5);

        // 使用矩阵与向量之间进行计算
        System.out.println(doubleMatrix.add(doubleVector));
        System.out.println(integerMatrix.add(integerVector));

        // 矩阵与数值之间的计算
        System.out.println(doubleMatrix.add(10));
        System.out.println(integerMatrix.add(10));
        System.out.println(doubleMatrix.diff(10));
        System.out.println(integerMatrix.diff(10));

        // 向量与数值之间的计算
        System.out.println(doubleVector.add(10));
        System.out.println(integerVector.add(10));
        System.out.println(doubleVector.diff(10));
        System.out.println(integerVector.diff(10));

        // 矩阵空间与数值之间的计算
        DoubleMatrixSpace parse = DoubleMatrixSpace.parse(doubleMatrix, doubleMatrix, doubleMatrix);
        System.out.println(parse.diff(10));
    }
}