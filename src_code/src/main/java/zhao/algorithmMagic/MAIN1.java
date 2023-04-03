package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MAIN1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        HashMap<Integer, ColorMatrix> hashMap = new HashMap<>(0b1100);
        // 将样本读取进来
        {
            int count = 0;
            for (File file : Objects.requireNonNull(new File("C:\\Users\\Liming\\Desktop\\fsdownload\\number").listFiles())) {
                ColorMatrix parse = ColorMatrix.parse(file.getPath());
                parse.globalBinary(ColorMatrix._G_, 200, 0xffffff, 0);
                hashMap.put(++count, parse);
            }
        }
        // 将需要被进行数字提取的图像获取到
        ColorMatrix parse = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test4.bmp");
        // 将原矩阵复制一份，稍后用于结果展示
        ColorMatrix parse1 = ColorMatrix.parse(parse.copyToNewArrays());
        // 将需要被处理的图像进行二值化，使其颜色不会干扰匹配
        parse.localBinary(ColorMatrix._G_, 4, 0xffffff, 0, -15);
        parse.show("二值化结果");
        // 在这里获取到 4 数值对应的样本
        ColorMatrix temp = hashMap.get(4);
        temp.show("模板数据");
        // 开始进行模板匹配操作，该操作将会返回所有度量系数小于 3500 的子图像左上角坐标
        ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> matching = parse.templateMatching(
                ManhattanDistance.getInstance("MAN"),
                temp,
                ColorMatrix._G_,
                1,
                v -> {
                    System.out.println(v);
                    return v < 3500;
                }
        );
        // 将所有的轮廓绘制到用于展示的矩阵对象上。
        for (Map.Entry<Double, IntegerCoordinateTwo> doubleIntegerCoordinateTwoEntry : matching) {
            IntegerCoordinateTwo value = doubleIntegerCoordinateTwoEntry.getValue();
            parse1.drawRectangle(
                    value,
                    new IntegerCoordinateTwo(value.getX() + temp.getColCount() - 1, value.getY() + temp.getRowCount() - 1),
                    Color.MAGENTA
            );
        }
        // 查看结果
        parse1.show("res");
    }
}