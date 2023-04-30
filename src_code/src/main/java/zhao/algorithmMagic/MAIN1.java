package zhao.algorithmMagic;

import zhao.algorithmMagic.core.model.ASModel;
import zhao.algorithmMagic.core.model.ClassificationModel;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("请指定需要被识别的图像：");
        }
        ClassificationModel<IntegerMatrixSpace> model = ASClass.transform(
                ASModel.Utils.read(new File("MyModel.as"))
        );
        // 提供一个新图 开始进行测试
        IntegerMatrixSpace parse1 = IntegerMatrixSpace.parse(args[0], 91, 87);
        // 放到模型中 获取到结果
        KeyValue<String[], DoubleVector[]> function = model.function(new IntegerMatrixSpace[]{parse1});
        // 提取结果向量
        DoubleVector[] value = function.getValue();
        // 由于被分类的图像对象只有一个，因此直接查看 0 索引的数据就好 这里是一个向量，其中每一个索引代表对应索引的类别得分值
        DoubleVector doubleVector = value[0];
        System.out.println(doubleVector);
        // 查看向量中不同维度对应的类别
        String[] key = function.getKey();
        int index = -1;
        for (double v : doubleVector.toArray()) {
            System.out.println(key[++index] + "\t===>\t" + v);
        }
        // 根据索引查看当前图像分类得分最大值对应的类别
        System.out.println("当前图像属于 " + key[ASMath.findMaxIndex(doubleVector.toArray())]);
    }
}