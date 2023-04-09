package zhao.algorithmMagic.operands.matrix;

import java.awt.*;
import java.util.Arrays;

/**
 * 图像边缘矩阵对象，该对象专用于针对图像边缘对象进行一些特有的操作，在这里您可以直接执行其中的边缘计算函数，更好的在图像边缘计算中提供良好的效果。
 * <p>
 * Image edge matrix object, which is specially used to carry out some special operations on image edge objects. Here you can directly execute the edge computing function to better provide good results in image edge computing.
 *
 * @author 赵凌宇
 * 2023/3/30 16:53
 */
public class RectangleMatrix extends ColorMatrix {

    private final int outlineWidthL;
    private final int outlineHeightR;
    private final int outlineWidthR;
    private final int outlineHeightL;

    /**
     * 构造一个指定行列数据的矩阵对象，其中的行指针最大值将会默认使用行数量。
     * <p>
     * Construct a matrix object with specified row and column data. The maximum value of row pointer will use the number of rows by default.
     *
     * @param rowCount       矩阵中的行数量
     *                       <p>
     *                       the number of rows in the matrix
     * @param colCount       矩阵中的列数量
     *                       <p>
     *                       the number of cols in the matrix
     * @param colors         该矩阵对象中的二维数组对象。
     * @param isGrayscale    如果设置为true 代表此图像是灰度图像
     * @param outlineWidthL  矩形的起始宽度索引
     * @param outlineWidthR  矩形的终止宽度索引
     * @param outlineHeightL 矩形的起始高度索引
     * @param outlineHeightR 矩形的终止高度索引
     */
    protected RectangleMatrix(int rowCount, int colCount, Color[][] colors, boolean isGrayscale, int outlineWidthL, int outlineHeightR, int outlineWidthR, int outlineHeightL) {
        super(rowCount, colCount, colors, isGrayscale);
        this.outlineWidthL = outlineWidthL;
        this.outlineHeightR = outlineHeightR;
        this.outlineWidthR = outlineWidthR;
        this.outlineHeightL = outlineHeightL;
    }

    /**
     * 将一个图像矩阵中的轮廓对象获取到
     *
     * @param colorMatrix 需要被提取的图像矩阵对象
     * @param rectColor   矩阵对象的轮廓颜色对象
     * @return 提取处理的图像轮廓对象
     */
    public static RectangleMatrix parse(ColorMatrix colorMatrix, Color rectColor) {
        // 将轮廓的高度与宽度计算出来
        int outlineWidthL = colorMatrix.getColCount() - 1;
        int outlineHeightL = colorMatrix.getRowCount() - 1;
        int outlineHeightR = 0;
        int outlineWidthR = 0;
        // 获取到起始与终止索引
        boolean isH = false;
        boolean isW = false;
        int rc = -1;
        for (Color[] colors : colorMatrix.toArrays()) {
            ++rc;
            boolean isRw = false;
            int cc = -1;
            boolean isOk = false;
            for (Color color : colors) {
                ++cc;
                // 先判断宽
                if (color.getRGB() != 0xff000000) {
                    isOk = true;
                    if (!isRw && cc < outlineWidthL) {
                        outlineWidthL = cc;
                        outlineWidthR = cc;
                        isW = true;
                        isRw = true;
                    }
                    if (isW) {
                        if (cc > outlineWidthR) outlineWidthR = cc;
                    }
                }
            }
            // 再判断高
            if (isOk) {
                if (isH) {
                    if (rc > outlineHeightR) outlineHeightR = rc;
                } else if (rc < outlineHeightL) {
                    outlineHeightL = rc;
                    outlineHeightR = rc;
                    isH = true;
                }
            }
        }
        // 按照宽高生成一个矩阵
        Color[][] colors = new Color[colorMatrix.getRowCount()][colorMatrix.getColCount()];
        for (Color[] color : colors) {
            Arrays.fill(color, Color.BLACK);
        }
        Color[] row = new Color[colorMatrix.getColCount()];
        Arrays.fill(row, Color.BLACK);
        for (int i = outlineWidthL; i <= outlineWidthR; i++) {
            row[i] = rectColor;
        }
        colors[outlineHeightL] = row;
        colors[outlineHeightR] = row;
        for (int i = outlineHeightL, colorsLength = outlineHeightR; i <= colorsLength; i++) {
            Color[] color = colors[i];
            color[outlineWidthL] = rectColor;
            color[outlineWidthR] = rectColor;
        }
        return new RectangleMatrix(
                colorMatrix.getRowCount(),
                colorMatrix.getColCount(),
                colors,
                false,
                outlineWidthL, outlineHeightL,
                outlineWidthR, outlineHeightR
        );
    }

    public int getOutlineWidthL() {
        return outlineWidthL;
    }

    public int getOutlineHeightR() {
        return outlineHeightR;
    }

    public int getOutlineWidthR() {
        return outlineWidthR;
    }

    public int getOutlineHeightL() {
        return outlineHeightL;
    }

    @Override
    public String toString() {
        return "RectangleMatrix{" +
                "outlineWidthL=" + getOutlineWidthL() +
                ", outlineWidthR=" + getOutlineWidthR() +
                ", outlineHeightL=" + getOutlineHeightL() +
                ", outlineHeightR=" + getOutlineHeightR() +
                '}';
    }
}
