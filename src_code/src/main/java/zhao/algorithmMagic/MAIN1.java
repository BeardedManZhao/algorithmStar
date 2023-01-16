package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KnnClassification;
import zhao.algorithmMagic.algorithm.classificationAlgorithm.UDFDistanceClassification;
import zhao.algorithmMagic.operands.matrix.ColumnIntegerMatrix;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.ArrayList;
import java.util.HashMap;

public final class MAIN1 {
    public static void main(String[] args) {
//        // 准备一个矩阵 其中存储的是鸟的数据样本
//        ColumnIntegerMatrix parse1 = ColumnIntegerMatrix.parse(
//                new String[]{"1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d"}, // 样本来源地区编号
//                new String[]{"羽毛", "羽毛的颜色", "种族"}, // 样本统计的三种维度
//                new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1},
//                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
//                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}
//        );
//        // 开始进行特征选择 去除掉其中的 40% 的维度
//        ColumnIntegerMatrix integerMatrix1 = parse1.featureSelection(0.4);
//        System.out.println(Arrays.toString(integerMatrix1.getArrayByRowName("种族")));
//        System.out.println(Arrays.toString(integerMatrix1.getArrayByColName("1d")));
//
//        // 准备一个新矩阵
//        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
//                null,
//                new String[]{"用户编号", "购买物品数量", "购买时间", "物品类别"},
//                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
//                new int[]{10, 10, 10, 20, 10, 20, 20, 23},
//                new int[]{2023, 2022, 2021, 2022, 2021, 2022, 2022, 2023},
//                new int[]{4, 5, 3, 2, 1, 7, 8, 6}
//        );
//        System.out.println(parse);
//        // 开始将矩阵中的数据进行与购买时间相关系的维度删掉
//        ColumnIntegerMatrix columnIntegerMatrix = parse.deleteRelatedDimensions(2, 0.8, 1);
//        System.out.println(columnIntegerMatrix);

//        int[] ints1 = {0, 1, 1, 1, 0, 300, 100};
//        int[] ints2 = {1, 1, 0, 0, 1, 30, 10};
//        // 准备一个矩阵，其中行字段名就是目标类别，列字段就是目标特征
//        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
//                new String[]{"有羽毛", "是群居生物", "是自相残杀的生物", "会使用工具", "可爱", "骂街熟练度", "数学能力"},
//                new String[]{"人类", "小鸟", "未知生物1", "未知生物2", "未知生物3", "可爱生物3", "蠢萌人类"},
//                ints1, ints2,
//                new int[]{1, 1, 0, 0, 0, 0, 0},
//                new int[]{1, 1, 0, 0, 2, 26, 1},
//                new int[]{1, 1, 1, 0, 0, 0, 0},
//                new int[]{0, 1, 0, 1, 1, 200, 50},
//                new int[]{0, 1, 0, 1, 1, 200, 1}
//        );
//        // 先打印一下需要分类的特征矩阵
//        System.out.println(parse);
//        // 开始构建类别样本
//        HashMap<String, int[]> hashMap = new HashMap<>();
//        hashMap.put("人类", ints1);
//        hashMap.put("小鸟", ints2);
//        // 开始计算类别，先获取到KNN计算组件
//        UDFDistanceClassification knn = UDFDistanceClassification.getInstance("udf");
//        HashMap<String, ArrayList<IntegerVector>> classification = knn.classification(parse, hashMap);
//        // 开始打印类别
//        classification.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));

        // TODO 使用自定义距离分类计算组件进行分类
        int[] ints1 = {101, 10};
        int[] ints2 = {1, 81};
        int[] ints3 = {101, 81};
        // 准备一个矩阵，其中行字段名就是目标类别，列字段就是目标特征
        ColumnIntegerMatrix parse = ColumnIntegerMatrix.parse(
                new String[]{"打人", "接吻"},
                new String[]{"动作", "爱情", "爱情动作", "未知1", "未知2"},
                ints1, ints2, ints3,
                new int[]{18, 90},
                new int[]{181, 90}
        );
        // 先打印一下需要分类的特征矩阵
        System.out.println(parse);
        // 开始构建类别样本
        HashMap<String, int[]> hashMap = new HashMap<>();
        hashMap.put("动作", ints1);
        hashMap.put("爱情", ints2);
        hashMap.put("爱情动作", ints3);
        // 开始计算类别，先获取到KNN计算组件
        UDFDistanceClassification udf = UDFDistanceClassification.getInstance("udf");
        HashMap<String, ArrayList<IntegerVector>> classification = udf.classification(parse, hashMap);
        // 开始打印类别
        classification.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));


        // TODO 使用KNN近邻计算组件进行分类
        KnnClassification knn = KnnClassification.getInstance("knn");
        HashMap<String, ArrayList<IntegerVector>> classification1 = knn.classification(
                // 这里代表类别名称 ? 是没有确定的类别
                new String[]{"动作", "?", "爱情", "?", "爱情动作"},
                // 这里是所有需要被确定的类别数据行
                // 值得注意的是，需要保证类别名称数量和数据行数量一致
                new int[][]{
                        // 其中的第一列代表打斗次数 第二列代表接吻次数
                        new int[]{101, 10},
                        new int[]{18, 90},
                        new int[]{1, 81},
                        new int[]{181, 90},
                        new int[]{101, 81}
                }
        );
        // 开始打印类别
        classification1.forEach((key, value) -> System.out.println("类别：" + key + '\t' + value));
    }
}
