package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;

public final class MAIN1 {
    public static void main(String[] args) {
        // 手动创建一个 3 通道的图像整形矩阵
        IntegerMatrixSpace parse1 = IntegerMatrixSpace.parse(
                // Red
                IntegerMatrix.parse(new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}),
                // Green
                IntegerMatrix.parse(new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}),
                // Blue
                IntegerMatrix.parse(new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3})
        );
//        // 创建一个权重矩阵
//        IntegerMatrixSpace parse2 = IntegerMatrixSpace.parse(
//                // Red
//                IntegerMatrix.parse(new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{1, 1, 1}),
//                // Green
//                IntegerMatrix.parse(new int[]{2, 2, 2}, new int[]{2, 2, 2}, new int[]{2, 2, 2}),
//                // Blue
//                IntegerMatrix.parse(new int[]{3, 3, 3}, new int[]{3, 3, 3}, new int[]{3, 3, 3})
//        );
        // 创建一个权重矩阵
        IntegerMatrixSpace parse2 = IntegerMatrixSpace.parse(
                // Red
                IntegerMatrix.parse(new int[]{1, 1, 2}, new int[]{1, 1, 2}),
                // Green
                IntegerMatrix.parse(new int[]{2, 2, 2}, new int[]{2, 2, 2}),
                // Blue
                IntegerMatrix.parse(new int[]{3, 3, 2}, new int[]{3, 3, 2})
        );

        System.out.println(parse1.get(0));
        System.out.println(parse2.get(0));
        // 使用权重开始进行卷积
        IntegerMatrix ints = parse1.foldingAndSum(3, 2, parse2);
        System.out.println(ints);
    }
}