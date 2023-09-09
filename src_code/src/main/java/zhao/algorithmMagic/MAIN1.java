package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.Graph;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class MAIN1 {
    public static void main(String[] args) {
        System.out.println(OperationAlgorithmManager.VERSION);
        if (args.length > 0) {
            ASDynamicLibrary.addDllDir(new File(args[0]));
            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
        } else {
            System.out.println("感谢您的使用。");
        }

        // 新版本 1.25 正在开发中....

        // 首先创建出两个节点的坐标
        final IntegerCoordinateTwo c1 = new IntegerCoordinateTwo(-10, 2);
        final IntegerCoordinateTwo c2 = new IntegerCoordinateTwo(20, 20);
        // 然后创建图对象
        final Graph parse = Graph.create(
                // 创建两个节点数据的表
                Graph.GraphNodeDF.create(
                        Graph.GraphNodeSeries.create(
                                c1, SingletonCell.$("zhao"), SingletonCell.$("20")
                        ),
                        Graph.GraphNodeSeries.create(
                                c2, SingletonCell.$("TY"), SingletonCell.$("22")
                        )
                ),
                // 创建出两个节点之间的边
                Graph.GraphEdgeDF.create(
                        // C1 <- 前任 -> C2 代表 C1的前任是C2  C2的前任是C1
                        Graph.GraphEdgeSeries.create(c1, c2, SingletonCell.$("前任"))
                )
        );
        // 获取到图对象中的所有节点数据映射表
        final HashMap<String, Graph.GraphNodeSeries> nodes = parse.getNodes();
        // 查看图对象中的所有边数据
        final HashSet<IntegerConsanguinityRoute2D> edges = parse.getEdges();
        // 我们在这里打印其中的信息 由于
        edges.forEach(edge -> {
            // 通过坐标名称 从节点映射表中 获取到起始节点与终止节点的数据Series
            final Graph.GraphNodeSeries start = nodes.get(edge.getStartingCoordinateName());
            final Graph.GraphNodeSeries end = nodes.get(edge.getEndPointCoordinateName());
            System.out.println(
                    "起始点：" + edge.getStartingCoordinate() +
                            // 由于上面构建节点Series的时候 第一个是坐标 第二个是名字 第三个是age
                            // 所以在这里也是相同的格式
                            "\t名称:" + start.getCell(1).getValue() + '\n' +
                            "终止点：" + edge.getEndPointCoordinate() + "\t名称:" + end.getCell(1)
            );
        });
    }
}
