package io.github.beardedManZhao.algorithmStar.core.model;

import org.jetbrains.annotations.NotNull;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.table.Cell;
import io.github.beardedManZhao.algorithmStar.operands.table.SingletonCell;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.DependentAlgorithmNameLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 单目标矩阵轮廓绘制模型，在该模型中，能够快速的将所有图像中最符合target的位置提取出来，然后绘制到新图像。
 *
 * @author 赵凌宇
 * 2023/4/17 17:45
 */
public final class SingleTargetContour extends HashMap<Integer, Cell<?>> implements ASModel<Integer, ColorMatrix, ColorMatrix[]> {

    /**
     * 目标样本特性图
     */
    public final static int TARGET = 1;

    /**
     * 局部二值化操作进行时需要使用的偏置数值
     */
    public final static int BINARY_BIAS_VALUE = 2;

    /**
     * 局部二值化操作进行时需要使用的子矩阵数量
     */
    public final static int BINARY_LOCAL_NUMBER = 3;

    /**
     * 用于计算的颜色通道编码
     */
    public final static int COLOR_CHANNEL = 4;

    /**
     * 二值化时大于阈值的颜色
     */
    public final static int TRUE_COLOR = 5;

    /**
     * 二值化时小于阈值的颜色
     */
    public final static int FALSE_COLOR = -TRUE_COLOR;

    /**
     * 是否需要进行二值化操作
     */
    public final static int isBinary = 6;

    /**
     * 模板匹配时要使用的卷积步长。
     */
    public final static int step = 7;

    /**
     * 绘制的矩形轮廓颜色
     */
    public final static int OUTLINE_COLOR = 8;

    SingleTargetContour() {
    }

    @Override
    public void setArg(Integer key, @NotNull Cell<?> value) {
        this.put(key, value);
    }

    @Override
    public ColorMatrix[] function(ColorMatrix... input) {
        ColorMatrix target;
        int tarRC, tarCC;
        int Binary_bias_value, Binary_local_number, trueColor, falseColor;
        {
            Cell<?> cell1 = this.get(TARGET);
            // 判断是否为 null
            if (cell1 == null) Utils.throwArgEqNull(String.valueOf(TARGET));
            // 判断类型是否正确
            if (cell1.getValue() instanceof ColorMatrix) {
                target = ASClass.transform(cell1.getValue());
                tarRC = target.getRowCount();
                tarCC = target.getColCount();
            } else
                throw Utils.throwArgTypeERR(String.valueOf(TARGET), "Cell<zhao.algorithmMagic.operands.matrix.ColorMatrix>");
        }
        byte mode = (byte) this.getOrDefault(COLOR_CHANNEL, SingletonCell.$(ColorMatrix._G_)).getIntValue();
        // 开始进行轮廓绘制
        ColorMatrix[] res = new ColorMatrix[input.length];
        int index = -1;
        // 判断是否需要进行二值化操作
        Cell<?> cell = this.get(isBinary);
        if (cell != null && "true".equals(cell.toString())) {
            // 需要进行二值化
            Binary_bias_value = this.getOrDefault(BINARY_BIAS_VALUE, SingletonCell.$(0)).getIntValue();
            Binary_local_number = this.getOrDefault(BINARY_LOCAL_NUMBER, SingletonCell.$(10)).getIntValue();
            if (this.getOrDefault(TRUE_COLOR, SingletonCell.$(0xffffff)).getValue() instanceof Integer) {
                trueColor = this.getOrDefault(TRUE_COLOR, SingletonCell.$(0xffffff)).getIntValue();
                falseColor = this.getOrDefault(FALSE_COLOR, SingletonCell.$(0)).getIntValue();
            } else {
                throw ASModel.Utils.throwArgTypeERR("trueColor", "Cell<java.lang.Integer>");
            }
            for (ColorMatrix colorMatrix : input) {
                res[++index] = run1(Binary_bias_value, Binary_local_number, trueColor, falseColor, target, tarRC, tarCC, mode, colorMatrix);
            }
        } else {
            // 如果不需要进行二值化
            for (ColorMatrix colorMatrix : input) {
                res[++index] = run2(target, tarRC, tarCC, mode, colorMatrix);
            }
        }
        return res;
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public ColorMatrix[] functionConcurrency(ColorMatrix[] input) {
        ColorMatrix target;
        int tarRC, tarCC;
        int Binary_bias_value, Binary_local_number, trueColor, falseColor;
        {
            Cell<?> cell1 = this.get(TARGET);
            // 判断是否为 null
            if (cell1 == null) Utils.throwArgEqNull(String.valueOf(TARGET));
            // 判断类型是否正确
            Object value = cell1.getValue();
            if (value instanceof ColorMatrix) {
                target = ASClass.transform(value);
                tarRC = target.getRowCount();
                tarCC = target.getColCount();
            } else
                throw Utils.throwArgTypeERR(String.valueOf(TARGET), "Cell<zhao.algorithmMagic.operands.matrix.ColorMatrix>");
        }
        ColorMatrix target1 = target;
        int tarRC1 = tarRC, tarCC1 = tarCC;
        byte mode = (byte) this.getOrDefault(COLOR_CHANNEL, SingletonCell.$(ColorMatrix._G_)).getIntValue();
        // 开始进行轮廓绘制
        ColorMatrix[] res = new ColorMatrix[input.length];
        // 准备线程监控
        CountDownLatch countDownLatch = new CountDownLatch(input.length);
        int index = -1;
        // 判断是否需要进行二值化操作
        Cell<?> cell = this.get(isBinary);
        if (cell != null && "true".equals(cell.toString())) {
            // 需要进行二值化
            Binary_bias_value = this.getOrDefault(BINARY_BIAS_VALUE, SingletonCell.$(0)).getIntValue();
            Binary_local_number = this.getOrDefault(BINARY_LOCAL_NUMBER, SingletonCell.$(10)).getIntValue();
            trueColor = this.getOrDefault(TRUE_COLOR, SingletonCell.$(0xffffff)).getIntValue();
            falseColor = this.getOrDefault(FALSE_COLOR, SingletonCell.$(0)).getIntValue();
            for (ColorMatrix colorMatrix : input) {
                int finalIndex = ++index;
                new Thread(() -> {
                    res[finalIndex] = run1(Binary_bias_value, Binary_local_number, trueColor, falseColor, target1, tarRC1, tarCC1, mode, colorMatrix);
                    countDownLatch.countDown();
                }).start();
            }
        } else {
            // 如果不需要进行二值化
            for (ColorMatrix colorMatrix : input) {
                int finalIndex = ++index;
                new Thread(() -> {
                    res[finalIndex] = run2(target1, tarRC1, tarCC1, mode, colorMatrix);
                    countDownLatch.countDown();
                }).start();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new OperatorOperationException("You cannot perform concurrent calculations!!!!!", e);
        }
        return res;
    }

    public ArrayList<Entry<Double, IntegerCoordinateTwo>> functionGetC(ColorMatrix... input) {
        ColorMatrix target;
        int Binary_bias_value, Binary_local_number, trueColor, falseColor;
        {
            Cell<?> cell1 = this.get(TARGET);
            // 判断是否为 null
            if (cell1 == null) Utils.throwArgEqNull(String.valueOf(TARGET));
            // 判断类型是否正确
            if (cell1.getValue() instanceof ColorMatrix) {
                target = ASClass.transform(cell1.getValue());
            } else
                throw Utils.throwArgTypeERR(String.valueOf(TARGET), "Cell<zhao.algorithmMagic.operands.matrix.ColorMatrix>");
        }
        byte mode = (byte) this.getOrDefault(COLOR_CHANNEL, SingletonCell.$(ColorMatrix._G_)).getIntValue();
        // 开始进行轮廓绘制
        ArrayList<Entry<Double, IntegerCoordinateTwo>> res = new ArrayList<>(input.length + 2);
        // 判断是否需要进行二值化操作
        Cell<?> cell = this.get(isBinary);
        if (cell != null && "true".equals(cell.toString())) {
            // 需要进行二值化
            Binary_bias_value = this.getOrDefault(BINARY_BIAS_VALUE, SingletonCell.$(0)).getIntValue();
            Binary_local_number = this.getOrDefault(BINARY_LOCAL_NUMBER, SingletonCell.$(10)).getIntValue();
            trueColor = this.getOrDefault(TRUE_COLOR, SingletonCell.$(0xffffff)).getIntValue();
            falseColor = this.getOrDefault(FALSE_COLOR, SingletonCell.$(0)).getIntValue();
            for (ColorMatrix colorMatrix : input) {
                colorMatrix.localBinary(
                        mode,
                        Binary_local_number,
                        trueColor, falseColor,
                        Binary_bias_value
                );
                res.add(colorMatrix.templateMatching(
                        DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                        target, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                        false
                ));
            }
        } else {
            // 如果不需要进行二值化
            for (ColorMatrix colorMatrix : input) {
                res.add(colorMatrix.templateMatching(
                        DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                        target, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                        false
                ));
            }
        }
        return res;
    }
/*

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     * /
    public final ArrayList<Entry<Double, IntegerCoordinateTwo>> functionConcurrencyGetC(ColorMatrix[] input) {
        ColorMatrix target;
        int Binary_bias_value, Binary_local_number, trueColor, falseColor;
        {
            Cell<?> cell1 = this.get(TARGET);
            // 判断是否为 null
            if (cell1 == null) Utils.throwArgEqNull(String.valueOf(TARGET));
            // 判断类型是否正确
            Object value = cell1.getValue();
            if (value instanceof ColorMatrix) {
                target = ASClass.transform(value);
            } else
                throw Utils.throwArgTypeERR(String.valueOf(TARGET), "Cell<zhao.algorithmMagic.operands.matrix.ColorMatrix>");
        }
        byte mode = (byte) this.getOrDefault(COLOR_CHANNEL, SingletonCell.$(ColorMatrix._G_)).getIntValue();
        // 开始进行轮廓绘制
        ArrayList<Entry<Double, IntegerCoordinateTwo>> res = new ArrayList<>(input.length + 2);
        // 判断是否需要进行二值化操作
        Cell<?> cell = this.get(isBinary);
        if (cell != null && "true".equals(cell.toString())) {
            // 需要进行二值化
            Binary_bias_value = this.getOrDefault(BINARY_BIAS_VALUE, SingletonCell.$(0)).getIntValue();
            Binary_local_number = this.getOrDefault(BINARY_LOCAL_NUMBER, SingletonCell.$(10)).getIntValue();
            trueColor = this.getOrDefault(TRUE_COLOR, SingletonCell.$(0xffffff)).getIntValue();
            falseColor = this.getOrDefault(FALSE_COLOR, SingletonCell.$(0)).getIntValue();
            for (ColorMatrix colorMatrix : input) {
                new Thread(() -> {
                    colorMatrix.localBinary(
                            mode,
                            Binary_local_number,
                            trueColor, falseColor,
                            Binary_bias_value
                    );
                    res.add(colorMatrix.templateMatching(
                            DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                            target, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                            false
                    ));
                }).start();
            }
        } else {
            // 如果不需要进行二值化
            for (ColorMatrix colorMatrix : input) {
                new Thread(() -> res.add(colorMatrix.templateMatching(
                        DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                        target, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                        false
                ))).start();
            }
        }
        return res;
    }
*/

    private ColorMatrix run2(ColorMatrix target1, int tarRC1, int tarCC1, byte mode, ColorMatrix colorMatrix) {
        IntegerCoordinateTwo value = colorMatrix.templateMatching(
                DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                target1, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                false
        ).getValue();
        ColorMatrix newCM = ColorMatrix.parse(colorMatrix.copyToNewArrays());
        newCM.drawRectangle(
                value,
                new IntegerCoordinateTwo(value.getX() + tarCC1, value.getY() + tarRC1),
                ASClass.transform(this.getOrDefault(OUTLINE_COLOR, SingletonCell.$(ColorMatrix.WHITE)).getValue()));
        return newCM;
    }

    private ColorMatrix run1(int binary_bias_value, int binary_local_number, int trueColor, int falseColor, ColorMatrix target1, int tarRC1, int tarCC1, byte mode, ColorMatrix colorMatrix) {
        ColorMatrix newCM = ColorMatrix.parse(colorMatrix.copyToNewArrays());
        colorMatrix.localBinary(
                mode,
                binary_local_number,
                trueColor, falseColor,
                binary_bias_value
        );
        IntegerCoordinateTwo value = colorMatrix.templateMatching(
                DependentAlgorithmNameLibrary.MANHATTAN_DISTANCE,
                target1, mode, this.getOrDefault(step, SingletonCell.$(10)).getIntValue(),
                false
        ).getValue();
        newCM.drawRectangle(
                value,
                new IntegerCoordinateTwo(value.getX() + tarCC1, value.getY() + tarRC1),
                ASClass.transform(this.getOrDefault(OUTLINE_COLOR, SingletonCell.$(ColorMatrix.WHITE)).getValue()));
        return newCM;
    }
}
