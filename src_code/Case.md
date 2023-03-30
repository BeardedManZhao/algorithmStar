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

### 描绘出图像边缘

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取一张图像的像素矩阵
        ColorMatrix colors = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test1.bmp");
        // 将图像拷贝一份出来
        ColorMatrix parse1 = ColorMatrix.parse(colors.copyToNewArrays());
        // 将 parse1 进行二值化 // 请注意阈值
        parse1.globalBinary(ColorMatrix._G_, 60, 0, 0xffffff);
        // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
        ColorMatrix parse2 = parse1.erode(2, 2, true, Color.BLACK);
        // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
        parse1.erode(2, 2, false, Color.WHITE);
        // 查看临时结果
        parse1.show("image1");
        parse2.show("image2");
        // 将两个矩阵进行做差运算，并查看做差之后的图像
        ColorMatrix resImage1 = parse1.diff(parse2);
        resImage1.show("轮廓图像");
        // 将轮廓绘制到原图像中 并查看绘制之后的图像
        ColorMatrix resImage2 = colors.agg(resImage1, ColorMatrix.COLOR_SUM_REGULATE);
        // 查看结果数据
        resImage2.show("绘制之后的结果图像");
    }
}
```

### 图像矩阵轮廓检测

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.RectangleMatrix;

import java.awt.*;

public class MAIN1 {
    public static void main(String[] args) {
        ColorMatrix resImage1, parse1;
        {
            // 获取一张图像的像素矩阵
            ColorMatrix colors = ColorMatrix.parse("C:\\Users\\Liming\\Desktop\\fsdownload\\test3.bmp");
            // 将图像拷贝一份出来
            parse1 = ColorMatrix.parse(colors.copyToNewArrays());
            // 将结果二值化
            parse1.globalBinary(ColorMatrix._G_, 150, 0xffffff, 0);
            // 将 parse1 矩阵腐蚀，然后将腐蚀的结果获取到
            ColorMatrix parse2 = parse1.erode(3, 3, true, Color.BLACK);
            // 将 parse1 矩阵中的白色作为腐蚀背景色（膨胀）
            parse1.erode(2, 2, false, Color.WHITE);
            // 查看临时结果
            parse1.show("image1");
            parse2.show("image2");
            // 将两个矩阵进行做差运算，并查看做差之后的图像
            resImage1 = parse1.diff(parse2);
            resImage1.show("轮廓图像");
        }
        // 开始提取白色轮廓线内的面积
        System.out.print("轮廓内面积 = ");
        System.out.println(resImage1.contourArea(ColorMatrix.WHITE));
        // 将轮廓线转化成矩形轮廓
        ColorMatrix parse = RectangleMatrix.parse(resImage1, Color.GREEN);
        parse.show("矩形轮廓");
        System.out.print(parse);
        // 将矩形轮廓添加到原矩阵 这里的规则是，如果颜色数值为 0xff000000 就不合并
        ColorMatrix res = parse1.agg(parse, (inputType1, inputType2) -> inputType2.getRGB() == 0xff000000 ? inputType1 : inputType2);
        res.show("结果");
    }
}
```

## 数据计算类案例

## 机器学习类案例
