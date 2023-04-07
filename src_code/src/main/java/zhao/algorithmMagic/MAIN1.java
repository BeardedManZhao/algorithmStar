package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.ManhattanDistance;
import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.awt.*;
import java.util.Map;


public class MAIN1 {
    public static void main(String[] args) throws InterruptedException {
        // 获取到摄像头输入设备
        InputComponent inputComponent = InputCamera.builder()
                // 要使用的摄像头的名字 索引 或def默认，我们这里使用的是 def 代表使用默认摄像头
                .addInputArg(InputCameraBuilder.Camera_Index, new FinalCell<>("0"))
                // 要使用的拍摄图像格式
                .addInputArg(InputCameraBuilder.Image_Format, new FinalCell<>("JPG"))
                // 图像尺寸 这里的数值是 WebcamResolution 枚举类的属性字段 VGA
                .addInputArg(InputCameraBuilder.CUSTOM_VIEW_SIZES, new FinalCell<>("VGA"))
                .create();

        ColorMatrix colorMatrix1, colorMatrix2;
        colorMatrix1 = ColorMatrix.parse("C:\\Users\\zhao\\Desktop\\fsdownload\\YB.bmp");
        int count = 0;
        while (++count < 5) {
            colorMatrix2 = ColorMatrix.parse(inputComponent);
            ColorMatrix temp = ColorMatrix.parse(colorMatrix2.copyToNewArrays());
            // 开始二值化
            colorMatrix1.localBinary(ColorMatrix._G_, 10, 0xffffff, 0, 1);
            temp.localBinary(ColorMatrix._G_, 5, 0xffffff, 0, 0);
            // 开始进行模板匹配 并返回最匹配的结果数值，在这里返回的就是所有匹配的结果数据，key为匹配系数  value为匹配结果
            Map.Entry<Double, IntegerCoordinateTwo> matching = temp.templateMatching(
                    ManhattanDistance.getInstance("MAN"),
                    colorMatrix1,
                    ColorMatrix._G_,
                    10,
                    false
            );
            // 开始进行绘制 在这里首先获取到坐标数据
            IntegerCoordinateTwo coordinateTwo = matching.getValue();
            System.out.print("匹配系数 = ");
            System.out.println(matching.getKey());
            colorMatrix2.drawRectangle(
                    coordinateTwo,
                    new IntegerCoordinateTwo(coordinateTwo.getX() + colorMatrix1.getColCount(), coordinateTwo.getY() + colorMatrix1.getRowCount()),
                    Color.MAGENTA
            );
            colorMatrix2.show("识别结果");
            Thread.sleep(1024);
        }
    }
}