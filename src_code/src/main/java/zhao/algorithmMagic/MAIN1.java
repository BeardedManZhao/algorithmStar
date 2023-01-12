package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.featureExtraction.DictFeatureExtraction;
import zhao.algorithmMagic.algorithm.featureExtraction.WordFrequency;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;

public final class MAIN1 {
    public static void main(String[] args) {
        // 获取到字典特征提取组件
        DictFeatureExtraction dict = DictFeatureExtraction.getInstance("dict");
        // 构造一个需要被提取的数组
        String[] strings = {
                "cat", "dog", "turtle", "fish", "cat"
        };
        // 开始提取特征矩阵
        ColumnIntegerMatrix extract = dict.extract(strings);
        // 打印矩阵
        System.out.println(extract);
        // 打印矩阵的hashMap形式
        extract.toHashMap().forEach((key, value) -> System.out.println(value.toString() + '\t' + key));

        System.out.println("================================================");

        // 获取到词频特征提取组件
        WordFrequency word = WordFrequency.getInstance("word");
        // 构建一些被统计的文本
        String[] data = {
                "I love you, Because you are beautiful.",
                "I need you. Because I'm trapped"
        };
        // 开始统计
        ColumnIntegerMatrix extract1 = word.extract(data);
        // 打印结果
        System.out.println(extract1);
    }
}
