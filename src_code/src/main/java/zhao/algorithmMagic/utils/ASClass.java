package zhao.algorithmMagic.utils;

import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateTwo;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRoute2DNet;
import zhao.algorithmMagic.operands.coordinateNet.DoubleRouteNet;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRoute2DNet;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRouteNet;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute2D;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

import java.util.ArrayList;
import java.util.HashSet;

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
        return IntegerConsanguinityRoute2D.parse(NewName,
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
        return DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(
                doubleConsanguinityRoute2D.getStartingCoordinateName() + " -> " + doubleConsanguinityRoute2D.getEndPointCoordinateName(),
                doubleConsanguinityRoute2D);
    }


    /**
     * 整形的二维线路 转换到 double类型的二维线路
     * <p>
     * reshaped 2D line Convert to 2D line of type double
     *
     * @param integerConsanguinityRoute2D 需要被转换的线路
     *                                    <p>
     *                                    Lines that need to be converted
     * @return 转换之后的double线路
     * <p>
     * double line after conversion
     */
    public static DoubleConsanguinityRoute2D IntegerConsanguinityRoute2D_To_DoubleConsanguinityRoute2D(IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        return IntegerConsanguinityRoute2D_To_DoubleConsanguinityRoute2D(
                integerConsanguinityRoute2D.getStartingCoordinateName() + " -> " + integerConsanguinityRoute2D.getEndPointCoordinateName(),
                integerConsanguinityRoute2D);
    }

    /**
     * 整形的二维线路 转换到 double类型的二维线路
     * <p>
     * reshaped 2D line Convert to 2D line of type double
     *
     * @param NewName                     转换之后线路的路径，如果您在外界已经生成了路径，您可以直接使用改参数，省去了程序内部的冗余运算
     *                                    <p>
     *                                    The path of the line after conversion, if you have already generated the path in the outside world, you can directly use the modified parameter to save the redundant operation inside the program
     * @param integerConsanguinityRoute2D 需要被转换的线路
     *                                    <p>
     *                                    Lines that need to be converted
     * @return 转换之后的double线路
     * <p>
     * double line after conversion
     */
    public static DoubleConsanguinityRoute2D IntegerConsanguinityRoute2D_To_DoubleConsanguinityRoute2D(String NewName, IntegerConsanguinityRoute2D integerConsanguinityRoute2D) {
        IntegerCoordinateTwo startingCoordinate = integerConsanguinityRoute2D.getStartingCoordinate();
        IntegerCoordinateTwo endPointCoordinate = integerConsanguinityRoute2D.getEndPointCoordinate();
        return DoubleConsanguinityRoute2D.parse(NewName,
                new DoubleCoordinateTwo(startingCoordinate.getX(), startingCoordinate.getY()),
                new DoubleCoordinateTwo(endPointCoordinate.getX(), endPointCoordinate.getY()));
    }


    /**
     * 将一个整形的多维线路转换为double类型的多维线路。
     *
     * @param integerConsanguinityRoute 需要被转换的线路对象
     * @return 转换之后的double多维线路
     */
    public static DoubleConsanguinityRoute IntegerConsanguinityRoute_To_DoubleConsanguinityRoute(IntegerConsanguinityRoute integerConsanguinityRoute) {
        return IntegerConsanguinityRoute_To_DoubleConsanguinityRoute(
                integerConsanguinityRoute.getStartingCoordinateName() + " -> " + integerConsanguinityRoute.getEndPointCoordinateName(),
                integerConsanguinityRoute
        );
    }


    /**
     * 将一个整形的多维线路转换为double类型的多维线路。
     *
     * @param NewName                   线路的名称
     * @param integerConsanguinityRoute 需要被转换的线路对象
     * @return 转换之后的double多维线路
     */
    public static DoubleConsanguinityRoute IntegerConsanguinityRoute_To_DoubleConsanguinityRoute(String NewName, IntegerConsanguinityRoute integerConsanguinityRoute) {
        return DoubleConsanguinityRoute.parse(NewName,
                new DoubleCoordinateMany(integerConsanguinityRoute.getStartingCoordinate().toArray()),
                new DoubleCoordinateMany(integerConsanguinityRoute.getEndPointCoordinate().toArray())
        );
    }

    /**
     * double类型的2维网络 转换到 整形的2维网络
     * <p>
     * 2D network of type double is converted to a 2D network of integer type
     *
     * @param doubleRoute2DNet 需要被转换的线路网对象
     *                         <p>
     *                         The line net object that needs to be converted
     * @return 转换之后的线路网
     * <p>
     * Line network after conversion
     */
    public static IntegerRoute2DNet DoubleRoute2DNet_To_IntegerRoute2DNet(DoubleRoute2DNet doubleRoute2DNet) {
        HashSet<DoubleConsanguinityRoute2D> netDataSet = doubleRoute2DNet.getNetDataSet();
        ArrayList<IntegerConsanguinityRoute2D> arrayList = new ArrayList<>(netDataSet.size());
        for (DoubleConsanguinityRoute2D doubleConsanguinityRoute2D : netDataSet) {
            arrayList.add(DoubleConsanguinityRoute2D_To_IntegerConsanguinityRoute2D(doubleConsanguinityRoute2D));
        }
        return IntegerRoute2DNet.parse(arrayList);
    }

    /**
     * 整数类型的2维网络，咋混换到 浮点类型的二维网络。
     * <p>
     * The 2D network of integer type is mixed into the 2D network of floating point type.
     *
     * @param doubleRoute2DNet 需要被准换的线路网对象
     *                         <p>
     *                         The line net object that needs to be converted
     * @return 转换之后的线路网
     * <p>
     * Line network after conversion
     */
    public static DoubleRoute2DNet IntegerRoute2DNet_To_DoubleRoute2DNet(IntegerRoute2DNet doubleRoute2DNet) {
        HashSet<IntegerConsanguinityRoute2D> netDataSet = doubleRoute2DNet.getNetDataSet();
        ArrayList<DoubleConsanguinityRoute2D> arrayList = new ArrayList<>(netDataSet.size());
        for (IntegerConsanguinityRoute2D doubleConsanguinityRoute2D : netDataSet) {
            arrayList.add(IntegerConsanguinityRoute2D_To_DoubleConsanguinityRoute2D(doubleConsanguinityRoute2D));
        }
        return DoubleRoute2DNet.parse(arrayList);
    }

    public static DoubleRouteNet IntegerRouteNet_To_DoubleRouteNet(IntegerRouteNet integerRouteNet) {
        HashSet<IntegerConsanguinityRoute> netDataSet = integerRouteNet.getNetDataSet();
        ArrayList<DoubleConsanguinityRoute> arrayList = new ArrayList<>(netDataSet.size());
        for (IntegerConsanguinityRoute integerConsanguinityRoute : netDataSet) {
            arrayList.add(DoubleConsanguinityRoute.parse(
                            integerConsanguinityRoute.getStartingCoordinateName() + " -> " + integerConsanguinityRoute.getEndPointCoordinateName(),
                            new DoubleCoordinateMany(integerConsanguinityRoute.getStartingCoordinate().toArray()),
                            new DoubleCoordinateMany(integerConsanguinityRoute.getEndPointCoordinate().toArray())
                    )
            );
        }
        return DoubleRouteNet.parse(arrayList);
    }
}
