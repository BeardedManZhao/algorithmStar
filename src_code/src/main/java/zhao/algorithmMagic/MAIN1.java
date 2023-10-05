package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.vector.ColumnIntegerVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.io.IOException;

public class MAIN1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println(OperationAlgorithmManager.VERSION);
//        if (args.length > 0) {
//            ASDynamicLibrary.addDllDir(new File(args[0]));
//            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
//        } else {
//            System.out.println("感谢您的使用。");
//        }


        // 创建一个向量对象
        final IntegerVector integerVector = IntegerVector.parse(1, 2, 3, 4);
        // 创建一个带有列与行名的矩阵对象
        final ColumnIntegerVector columnIntegerVector = ColumnIntegerVector.parse(
                "向量名称", new String[]{"age", "num"}, integerVector
        );
        // 查看两者的序列号
        System.out.println(integerVector.getSerialVersionUID());
        System.out.println(columnIntegerVector.getSerialVersionUID());
    }
}
