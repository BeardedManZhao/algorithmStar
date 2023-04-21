package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.*;

import java.io.File;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) {
        // 从磁盘中加载刚刚保存的模型
        ASModel<Integer, Double, Double> asModel = ASModel.Utils.read(new File("C:\\Users\\Liming\\Desktop\\fsdownload\\MytModel.as"));
        // 构造一组 自变量数据
        Double[] doubles = {150.0, 100.0, 100.0, 100.0};
        // 计算这组数据在模型中计算后如果，查看是否达到预测效果  预期结果是 500
        // 因为刚刚的训练数据中 数据的公共公式为 f(x) = x1 * 2 + x2 * 1 + x2 * 1
        Double function = asModel.function(doubles);
        System.out.println(function);
        // 打印模型公式
        System.out.println(asModel);
    }
}