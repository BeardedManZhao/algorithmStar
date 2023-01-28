package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KMeans;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.vector.ColumnIntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) {
        // 创建一个矩阵，接下来对矩阵中的数据进行KMeans算法聚类
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                new String[]{"说话熟练度", "工具熟练度", "觅食熟练度", "飞翔熟练度"},
                null,
                new int[]{100, 100, 20, 0},
                new int[]{110, 150, 30, 0},
                new int[]{120, 130, 30, 0},
                new int[]{10, 1, 100, 100},
                new int[]{10, 0, 80, 0},
                new int[]{0, 0, 90, 50},
                new int[]{0, 10, 70, 0},
                new int[]{11, 11, 80, 0}
        );
        // 获取到KMeans计算组件
        KMeans kMeans = KMeans.getInstance("KMeans");
        // 设置随机种子
        kMeans.setSeed(2048);
        // 打印矩阵数据
        System.out.println(parse);
        // 开始进行聚类 并获取结果集合 其中key是空间名称 value是类别数据对象
        HashMap<String, ArrayList<ColumnIntegerVector>> classification = kMeans.classification(
                new String[]{"标签1", "标签2"}, // 这里设置K个空间的名称
                parse
        );
        // 打印聚类结果
        for (ArrayList<ColumnIntegerVector> value : classification.values()) {
            // 打印集合中的所有数据对象，由于分类的是带有字段的矩阵，因此每一个向量的类别也可通过字段获取
            System.out.print(value.get(0).vectorName());
            System.out.print('\t');
            System.out.println(value);
        }
    }
}