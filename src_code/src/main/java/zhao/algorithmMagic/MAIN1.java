package zhao.algorithmMagic;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.lntegrator.Route2DDrawingIntegrator;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.IntegerRoute2DNet;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        // 加载dll动态库
        ASDynamicLibrary.addDllDir(new File("D:\\MyGitHub\\algorithmStar\\AsLib"));
        // 建立名称
        final String zsStr = "鼠", cnStr = "牛", yhStr = "虎", mtStr = "兔";
        final String clStr = "龙", ssStr = "蛇", wmStr = "马", wyStr = "羊";
        final String shStr = "猴", yjStr = "鸡", xgStr = "狗", hzStr = "猪";
        String p = " -> ";
        // 建立坐标
        IntegerCoordinateTwo zs = new IntegerCoordinateTwo(-24, 0);
        IntegerCoordinateTwo cn = new IntegerCoordinateTwo(-20, 3);
        IntegerCoordinateTwo yh = new IntegerCoordinateTwo(-16, 6);
        IntegerCoordinateTwo mt = new IntegerCoordinateTwo(-12, 9);
        IntegerCoordinateTwo cl = new IntegerCoordinateTwo(-8, 0);
        IntegerCoordinateTwo ss = new IntegerCoordinateTwo(-4, 3);
        IntegerCoordinateTwo wm = new IntegerCoordinateTwo(0, 6);
        IntegerCoordinateTwo wy = new IntegerCoordinateTwo(4, 9);
        IntegerCoordinateTwo sh = new IntegerCoordinateTwo(8, 0);
        IntegerCoordinateTwo yj = new IntegerCoordinateTwo(12, 3);
        IntegerCoordinateTwo xg = new IntegerCoordinateTwo(16, 6);
        IntegerCoordinateTwo hz = new IntegerCoordinateTwo(20, 9);
        // 创建关系路线图
        IntegerConsanguinityRoute2D parse1 = IntegerConsanguinityRoute2D.parse(zsStr + p + wmStr, zs, wm);
        IntegerConsanguinityRoute2D parse2 = IntegerConsanguinityRoute2D.parse(cnStr + p + wyStr, cn, wy);
        IntegerConsanguinityRoute2D parse3 = IntegerConsanguinityRoute2D.parse(yhStr + p + shStr, yh, sh);
        IntegerConsanguinityRoute2D parse4 = IntegerConsanguinityRoute2D.parse(mtStr + p + yjStr, mt, yj);
        IntegerConsanguinityRoute2D parse5 = IntegerConsanguinityRoute2D.parse(clStr + p + xgStr, cl, xg);
        IntegerConsanguinityRoute2D parse6 = IntegerConsanguinityRoute2D.parse(ssStr + p + hzStr, ss, hz);
        // 创建一个整形坐标网络对象
        IntegerRoute2DNet integerRoute2DNet = IntegerRoute2DNet.parse(
                parse1, parse2, parse3, parse4,
                parse5, parse6
        );
        // 获取到绘图集成器 将坐标网络绘制出来
        Route2DDrawingIntegrator zhao = new Route2DDrawingIntegrator("zhao", integerRoute2DNet);
        boolean run = zhao.setDiscreteThreshold(4).setImageWidth(1024).setImageHeight(512)
                .setImageOutPath("C:\\Users\\zhao\\Desktop\\out\\Test.jpg").run();
        if (run) {
            System.out.println("ok!!!!");
        }
    }
}