package io.github.beardedManZhao.algorithmStar.integrator.launcher;

import io.github.beardedManZhao.algorithmStar.operands.matrix.Matrix;

/**
 * 矩阵绘图集成器，其转为矩阵对象定制，能够将大部分函数配置成与矩阵可以适配的功能，减少了实现启动器的步骤.
 * <p>
 * Matrix drawing integrator, which is converted to matrix object customization, can configure most functions as functions that can adapt to the matrix, reducing the steps to implement the starter.
 *
 * @param <T> 需要提供给集成器的矩阵对象数据类型。
 *            2023/2/9 13:59
 * @author 赵凌宇
 */
public final class ImageRenderingMarLauncher<T extends Matrix<?, ?, ?, ?, ?>> implements ImageRenderingLauncher<T> {

    private final T integerMatrix;
    private final String outPath;
    private final int pixelMagnification;

    /**
     * @param Matrix             需要被绘制的矩阵对象
     * @param outPath            绘制之后的输出目录
     * @param pixelMagnification 绘制时使用的像素倍率
     */
    public ImageRenderingMarLauncher(T Matrix, String outPath, int pixelMagnification) {
        this.integerMatrix = Matrix;
        this.outPath = outPath;
        this.pixelMagnification = pixelMagnification;
    }

    public ImageRenderingMarLauncher(T Matrix, String outPath) {
        this(Matrix, outPath, 10);
    }

    @Override
    public T returnMatrix() {
        return this.integerMatrix;
    }

    @Override
    public int returnImageWeight() {
        return this.integerMatrix.getColCount();
    }

    @Override
    public int returnImageHeight() {
        return this.integerMatrix.getRowCount();
    }

    @Override
    public int pixelMagnification() {
        return pixelMagnification;
    }

    @Override
    public boolean isWriteNumber() {
        return false;
    }

    @Override
    public String returnOutPath() {
        return this.outPath;
    }

    /**
     * @return 启动器的子类实现，用来显示转换到子类，一般只需要在这里进行一个this的返回即可
     * <p>
     * The subclass implementation of the launcher is used to display the conversion to the subclass. Generally, only a "this" return is required here.
     */
    @Override
    public ImageRenderingLauncher<T> expand() {
        return this;
    }
}
