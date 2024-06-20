package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;

/**
 * 池化+二值化操作任务
 *
 * @author 赵凌宇
 * 2023/4/27 20:36
 */
public class PoolBinaryTfTask implements Transformation<ColorMatrix, ColorMatrix> {

    private final int poolWidth, poolHeight, colorBoundary, trueColor, falseColor;
    private final byte colorC;
    private final boolean isBinary;


    /**
     * 实例化除池化+二值化的task
     *
     * @param poolWidth     池化核宽度
     * @param poolHeight    池化核高度
     * @param isBinary      是否需要二值化操作
     * @param colorBoundary 二值化边界阈值
     * @param trueColor     大于阈值的结果
     * @param falseColor    小于阈值的结果
     * @param colorC        通道数
     */
    public PoolBinaryTfTask(int poolWidth, int poolHeight, boolean isBinary, int colorBoundary, int trueColor, int falseColor, byte colorC) {
        this.poolWidth = poolWidth;
        this.poolHeight = poolHeight;
        this.colorBoundary = colorBoundary;
        this.trueColor = trueColor;
        this.falseColor = falseColor;
        this.colorC = colorC;
        this.isBinary = isBinary;
    }

    /**
     * @param colorMatrix 来自内部的待转换数据。
     *                    Data to be converted from inside.
     * @return 转换之后的数据。
     * <p>
     * Data after conversion.
     */
    @Override
    public ColorMatrix function(ColorMatrix colorMatrix) {
        ColorMatrix pooling = colorMatrix.pooling(poolWidth, poolHeight, ColorMatrix.POOL_RGB_OBO_MAX);
        if (this.isBinary) {
            pooling.globalBinary(colorC, colorBoundary, trueColor, falseColor);
        }
        return pooling;
    }
}
