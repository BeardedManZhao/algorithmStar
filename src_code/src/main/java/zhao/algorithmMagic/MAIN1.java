package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.normalization.LinearNormalization;
import zhao.algorithmMagic.algorithm.normalization.Z_ScoreNormalization;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;

public class MAIN1 {
    public static void main(String[] args) {
        // 准备一个向量对象
        IntegerVector parse1 = IntegerVector.parse(15, 9, 8, 7, 6, 7, 8, 9, 10);
        DoubleVector parse2 = DoubleVector.parse(10.5, 9, 8, 7, 6, 7, 8, 9, 10);
        // 获取到数据预处理组件 线性归一化 并设置其归一化的最大值与最小值
        LinearNormalization line = LinearNormalization
                .getInstance("line")
                .setMin(-5).setMax(5);
        // 获取到数据预处理组件，序列标准化
        Z_ScoreNormalization z_score = Z_ScoreNormalization.getInstance("z_Score");
        // 打印序列线性归一化的结果向量
        System.out.println(line.pretreatment(parse1));
        System.out.println(line.pretreatment(parse2));
        // 打印线性诡异话的结果向量
        System.out.println(z_score.pretreatment(parse1));
        System.out.println(z_score.pretreatment(parse2));
    }
}