package zhao.algorithmMagic.utils;

import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRoute2DNet;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRoute2DNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

import java.util.ArrayList;

/**
 * Java类于 2022/10/11 11:04:20 创建
 *
 * @author 4
 */
public final class ASClass {
    /**
     * 将一个数据类型进行强制转换，支持泛型转换，需要注意的是，有可能会抛出异常!!!
     *
     * @param input    需要被转换的数据类型
     * @param <Input>  需要被转换的数据类型
     * @param <Output> 目标数据类型
     * @return 转换结果
     * @throws TargetNotRealizedException 转换时出现的异常！
     */
    @SuppressWarnings("unchecked")
    public static <Input, Output> Output transform(Input input) {
        try {
            return (Output) input;
        } catch (ClassCastException c) {
            String s = input.toString();
            TargetNotRealizedException targetNotRealizedException = new TargetNotRealizedException(
                    "在尝试进行强制转换的时候发生了异常！转换失败的对象：[" + s + "]\nAn exception occurred while trying to cast! Converting failed object: [" + s + "]"
            );
            targetNotRealizedException.setStackTrace(c.getStackTrace());
            throw targetNotRealizedException;
        }
    }

    /**
     * Double类型的二维线路，转换到 整形的二维线路
     * <p>
     * 2D line of type Double, converted to 2D line of type
     *
     * @param doubleConsanguinityRoute2D 需要被转换的Double线路
     *                                   <p>
     *                                   Double line that needs to be converted
     * @return 转换之后的整形线路
     * <p>
     * Shaped line after conversion
     */
    public static IntegerConsanguinityRoute2D DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(String NewName, DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        DoubleCoordinateTwo startingCoordinate = doubleConsanguinityRoute2D.getStartingCoordinate();
        DoubleCoordinateTwo endPointCoordinate = doubleConsanguinityRoute2D.getEndPointCoordinate();
        String s = doubleConsanguinityRoute2D.getStartingCoordinateName() + " -> " + doubleConsanguinityRoute2D.getEndPointCoordinateName();
        return IntegerConsanguinityRoute2D.parse(s,
                new IntegerCoordinateTwo(startingCoordinate.getX().intValue(), startingCoordinate.getY().intValue()),
                new IntegerCoordinateTwo(endPointCoordinate.getX().intValue(), endPointCoordinate.getY().intValue()));
    }

    /**
     * Double类型的二维线路，转换到 整形的二维线路
     * <p>
     * 2D line of type Double, converted to 2D line of type
     *
     * @param doubleConsanguinityRoute2D 需要被转换的Double线路
     *                                   <p>
     *                                   Double line that needs to be converted
     * @return 转换之后的整形线路
     * <p>
     * Shaped line after conversion
     */
    public static IntegerConsanguinityRoute2D DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(DoubleConsanguinityRoute2D doubleConsanguinityRoute2D) {
        String s = doubleConsanguinityRoute2D.getStartingCoordinateName() + " -> " + doubleConsanguinityRoute2D.getEndPointCoordinateName();
        return DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(s, doubleConsanguinityRoute2D);
    }

    public static IntegerRoute2DNet DoubleRoute2DNet_To_IntegerRoute2DNet(DoubleRoute2DNet doubleRoute2DNet) {
        ArrayList<IntegerConsanguinityRoute2D> arrayList = new ArrayList<>();
        for (DoubleConsanguinityRoute2D doubleConsanguinityRoute2D : doubleRoute2DNet.getNetDataSet()) {
            arrayList.add(DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(doubleConsanguinityRoute2D));
        }
        return IntegerRoute2DNet.parse(arrayList);
    }
}
