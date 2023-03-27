# AS 机器学习库案例集

## 机器视觉类案例

### 获取图像边缘线

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取一张图像的像素矩阵
        ColorMatrix parse1 = ColorMatrix.parseGrayscale("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 将 parse1 进行二值化
        parse1.localBinary(ColorMatrix._G_, 30, 0, 0xffffff, 1);
        // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
        ColorMatrix parse2 = parse1.erode(3, 3, true, Color.BLACK);
        // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
        parse1.erode(3, 3, false, Color.WHITE);
        // 查看临时结果
        parse1.show("image1");
        parse2.show("image2");
        // 将两个矩阵进行做差运算，并查看做差之后的图像
        (parse1.diff(parse2)).show("结果图像");
    }
}
```

## 数据计算类案例

## 机器学习类案例
