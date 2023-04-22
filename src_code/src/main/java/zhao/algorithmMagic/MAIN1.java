package zhao.algorithmMagic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.io.InputHDFS;
import zhao.algorithmMagic.io.InputHDFSBuilder;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.io.IOException;
import java.net.URISyntaxException;

public class MAIN1 {

    // 在 main 函数中进行模型的保存和读取以及使用
    public static void main(String[] args) throws URISyntaxException, IOException {
        // 构建 HDFS 数据输入对象
        Path path = new Path("hdfs://192.168.0.141:8020");
        FileSystem fileSystem = path.getFileSystem(new Configuration());
        InputComponent inputComponent = InputHDFS.builder()
                .addInputArg(InputHDFSBuilder.CHAR_SET, SingletonCell.$(','))
                .addInputArg(InputHDFSBuilder.FILE_SYSTEM, new FinalCell<>(fileSystem))
                .addInputArg(InputHDFSBuilder.IN_PATH, SingletonCell.$_String("hdfs://192.168.0.141:8020/myFile/test.csv"))
                .addInputArg(InputHDFSBuilder.FIELD, new FinalCell<>(new String[]{"null"}))
                .create();
        // 从 HDFS 中获取到 Double 矩阵对象
        DoubleMatrix doubleMatrix = DoubleMatrix.parse(inputComponent);
        // 从 HDFS 中获取到 Integer 矩阵对象
        IntegerMatrix integerMatrix = IntegerMatrix.parse(inputComponent);
        // 打印矩阵
        System.out.println(doubleMatrix);
        System.out.println(integerMatrix);
        // 关闭 fileSystem
        fileSystem.close();
    }
}