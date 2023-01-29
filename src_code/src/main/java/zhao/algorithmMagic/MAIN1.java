package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.classificationAlgorithm.KMeans;
import zhao.algorithmMagic.operands.matrix.ColumnDoubleMatrix;
import zhao.algorithmMagic.operands.vector.ColumnDoubleVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MAIN1 {
    public static void main(String[] args) throws IOException {
        System.out.println("这里是将数据处理并输出到外界的一个示例");
        // 加载一份CSV数据中的特征成为矩阵
        ArrayList<double[]> arrayList = new ArrayList<>();
        {
            Pattern compile = Pattern.compile("\\s*?,\\s*?");
            BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\要导入的利润表.csv"));
            while (bufferedReader.ready()) {
                // 获取到一行数据中指定的字段特征数值
                double[] line = new double[2];
                String[] split = compile.split(bufferedReader.readLine());
                try {
                    line[0] = Double.parseDouble(split[2]);
                    line[1] = Double.parseDouble(split[3]);
                    arrayList.add(line);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                }
            }
            bufferedReader.close();
        }
        // 获取到特征矩阵 并进行特征选择，去除掉无用维度
        String[] strings = {"本年累计金额", "本月金额"};
        ColumnDoubleMatrix parse = ColumnDoubleMatrix.parse(
                strings,
                arrayList.stream().map(String::valueOf).toArray(String[]::new),
                arrayList.toArray(new double[0][])
        ).featureSelection(0.7);

        // 打印矩阵
        System.out.println(parse);
        // 获取到KMeans计算组件
        KMeans kMeans = KMeans.getInstance("KMeans");
        // 设置随机种子
        kMeans.setSeed(1024);
        // 设置随机打乱时不需要拷贝 TODO 这个也是默认的模式
        kMeans.setCopy(false);
        // 打印矩阵数据
        System.out.println(parse);
        // 开始进行聚类 并获取结果集合 其中key是空间名称 value是类别数据对象
        HashMap<String, ArrayList<ColumnDoubleVector>> classification = kMeans.classification(new String[]{"标签1", "标签2"}, parse);
        // 打印聚类结果
        for (Map.Entry<String, ArrayList<ColumnDoubleVector>> entry : classification.entrySet()) {
            System.out.println(entry.getKey());
            for (ColumnDoubleVector columnDoubleVector : entry.getValue()) {
                System.out.println(columnDoubleVector);
            }
            System.out.println("==================");
        }
        // 打印源数组中的数据
        System.out.println(parse);
    }
}