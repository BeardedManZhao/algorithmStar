package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.utils.ASMath;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵对象，其中包含一些相关联的数据，本次要求将与年龄相关联的数据全部删掉
        ColumnDoubleMatrix columnDoubleMatrix = ColumnDoubleMatrix.parse(
                new String[]{"人员编号", "人员年龄", "人员工资(k)", "幸福指数"},
                new String[]{"N1", "N2", "N3", "N4", "N5"},
                new double[]{1, 25, 14, 16},
                new double[]{2, 45, 12, 10},
                new double[]{3, 33, 13, 12},
                new double[]{4, 42, 16, 17},
                new double[]{5, 25, 12, 10}
        );
        System.out.println(columnDoubleMatrix);
        // 获取信息熵
        double v1 = ASMath.informationEntropy(columnDoubleMatrix.toArrays(), 2, 2, v -> v[1] > 30);
        double v2 = ASMath.informationEntropy(columnDoubleMatrix.toArrays(), 2, 2, v -> v[1] > 42);
        // 比较信息熵，其中的 v2 较小，因此v2 的事件消除的信息量更大
        System.out.println(v1);
        System.out.println(v2);
    }
}
