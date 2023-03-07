package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;

import java.util.Arrays;

public class MAIN1 {
    public static void main(String[] args) throws InterruptedException {
        // 准备一份数据
        double[][] doubles = new double[][]{
                new double[]{1, 'z', 1, 19},
                new double[]{2, 't', 0, 20},
                new double[]{3, 'y', 0, 17}
        };
        // 创建出带有字段名称的矩阵对象
        ColumnDoubleMatrix parse = ColumnDoubleMatrix.parse(
                new String[]{"id", "name", "sex", "age"},
                new String[]{"zhao", "tang", "yang"},
                doubles
        );
        // 打印出矩阵对象
        System.out.println(parse);
        // 获取到其中的 zhao 行的信息
        System.out.println(Arrays.toString(parse.getArrayByRowName("zhao")));
        // 获取到其中的 age  列的信息
        System.out.println(Arrays.toString(parse.getArrayByColName("age")));
        // 将数据保存到文件系统
        parse.save("C:\\Users\\Liming\\Desktop\\fsdownload\\res.csv");
    }
}
