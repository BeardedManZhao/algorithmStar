package zhao.algorithmMagic;

import zhao.algorithmMagic.io.InputCamera;
import zhao.algorithmMagic.io.InputCameraBuilder;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.SingletonCell;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取到相机设备数据输入组件
        InputComponent jpeg = InputCamera.builder()
                .addInputArg(InputCameraBuilder.Camera_Index, SingletonCell.$(0))
                .addInputArg(InputCameraBuilder.Image_Format, SingletonCell.$_String("JPEG"))
                .create();
        // 读取一张图
        ColorMatrix colorMatrix = ColorMatrix.parse(jpeg);
        // 显示原图
        colorMatrix.show("原图");
        // 开始池化 在新版本中 新增了均值池化与分通道均值池化两种模式
        // 首先是均值池化 指定 3x3 的局部池化方案
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_MEAN).show("均值池化");
        // 然后是分通道池化
        colorMatrix.pooling(3, 3, ColorMatrix.POOL_RGB_OBO_MEAN).show("分通道均值池化");
    }
}