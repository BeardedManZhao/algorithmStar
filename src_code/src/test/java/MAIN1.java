import io.github.beardedManZhao.algorithmStar.algorithm.featureExtraction.DictFeatureExtraction;
import io.github.beardedManZhao.algorithmStar.core.AlgorithmStar;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColumnIntegerMatrix;

/**
 * 示例代码文件
 */
public class MAIN1 {
    public static void main(String[] args) {
        // 准备几个所谓的类别
        final String[] sentences = new String[]{
                "zhao", "zhao", "asdasd", "zhao1", "zhao123", "zhao4"
        };

        // 获取到特征处理计算组件 这里使用的是 词频向量特征提取组件
        final DictFeatureExtraction dictFeatureExtraction = DictFeatureExtraction.getInstance("DictFeatureExtraction");

        // 构建一个 AS 库计算对象
        // TODO 需要注意是 在这里我们需要设置一下第二个泛型的类型
        //  第二个泛型代表的就是 词频处理之后返回的操作数的类型 ColumnIntegerMatrix 是 dictFeatureExtraction 对应的类型
        final AlgorithmStar<?, ColumnIntegerMatrix> instance = AlgorithmStar.getInstance();
        // 将组件和需要被处理的数据提供给计算对象 TODO 这里获取到的类型就是 ColumnIntegerMatrix
        final ColumnIntegerMatrix extract = instance.extract(dictFeatureExtraction, sentences);

        System.out.println(extract);
    }
}