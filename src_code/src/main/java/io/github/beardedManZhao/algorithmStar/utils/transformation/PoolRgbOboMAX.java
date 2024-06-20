package io.github.beardedManZhao.algorithmStar.utils.transformation;

import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;

import java.awt.*;

/**
 * 最大池化操作实现类
 *
 * @author 赵凌宇
 * 2023/4/27 20:42
 */
public class PoolRgbOboMAX implements Transformation<ColorMatrix, Color> {
    /**
     * 、
     *
     * @param colorMatrix 来自内部的待转换数据。
     *                    Data to be converted from inside.
     * @return 转换之后的数据。
     * <p>
     * Data after conversion.
     */
    @Override
    public Color function(ColorMatrix colorMatrix) {
        int maxR = 0, maxG = maxR, maxB = maxG;
        for (Color[] matrix : colorMatrix) {
            for (Color color : matrix) {
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if (red > maxR) maxR = red;
                if (green < maxG) maxG = green;
                if (blue < maxB) maxB = blue;
            }
        }
        return new Color(maxR, maxG, maxB);
    }
}
