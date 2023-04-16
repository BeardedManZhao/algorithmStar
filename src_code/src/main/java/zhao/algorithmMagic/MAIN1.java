package zhao.algorithmMagic;

import zhao.algorithmMagic.io.OutputComponent;
import zhao.algorithmMagic.io.PrintServiceOutput;
import zhao.algorithmMagic.io.PrintServiceOutputBuilder;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.FinalCell;

import javax.print.DocFlavor;
import javax.print.attribute.HashPrintRequestAttributeSet;


public class MAIN1 {
    public static void main(String[] args) {
        // 获取到需要被输出的图像矩阵
        ColorMatrix colorMatrix = ColorMatrix.parse("C:\\Users\\zhao\\Pictures\\Screenshots\\屏幕截图_20230116_163341.png");
        // 获取到打印机设备数据输出对象
        HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
        OutputComponent outputComponent = PrintServiceOutput.builder()
                // 设置打印的数据的格式 这里是数组的JPG图像
                .addOutputArg(PrintServiceOutputBuilder.DOC_FLAVOR, new FinalCell<>(DocFlavor.BYTE_ARRAY.JPEG))
                // 设置打印的属性配置集合
                .addOutputArg(PrintServiceOutputBuilder.ATTR, new FinalCell<>(hashPrintRequestAttributeSet))
                // 设置打印机的名称
                .addOutputArg(PrintServiceOutputBuilder.PRINT_SERVER_NAME, new FinalCell<>("HPFF15E0 (HP DeskJet 2700 series)"))
                // 创建出打印机设备数据输入对象
                .create();
        // 开始查看图像并将图像输出
        colorMatrix.show("image", 1024, 512);
        colorMatrix.save(outputComponent);
    }
}