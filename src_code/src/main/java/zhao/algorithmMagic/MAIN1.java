package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.DoubleMatrix;

/**
 * Java类于 2022/10/11 10:23:53 创建
 *
 * @author 4
 */
public class MAIN1 {
    public static void main(String[] args) {
        DoubleMatrix pares = DoubleMatrix.parse(new double[]{1, 2, 3, 10}, new double[]{4, 5, 6, 20}, new double[]{7, 8, 9, 30, 50});
        System.out.println(pares.transpose());
    }
}
