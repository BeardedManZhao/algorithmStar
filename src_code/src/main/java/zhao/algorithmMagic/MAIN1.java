package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.DoubleMatrix;

public class MAIN1 {
    public static void main(String[] args) {
        // 随机生成 9 行 5 列 的矩阵
        DoubleMatrix random = DoubleMatrix.random(5, 9, 22);
        System.out.println(random);
        // 提取出其中从 (0, 1) 到 (4, 5) 的子矩阵对象
        DoubleMatrix mat = random.extractMat(0, 1, 4, 5);
        System.out.println(mat);
    }
}