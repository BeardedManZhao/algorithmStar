package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.coordinateNet.Graph;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.table.SingletonCell;

import java.util.Collection;
import java.util.HashMap;

public class MAIN1 {
    public static void main(String[] args) {
//        System.out.println(OperationAlgorithmManager.VERSION);
//        if (args.length > 0) {
//            ASDynamicLibrary.addDllDir(new File(args[0]));
//            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
//        } else {
//            System.out.println("感谢您的使用。");
//        }

        // 新版本 1.25 正在开发中....

        // 首先创建出两个节点的坐标
        final IntegerCoordinateTwo c1 = new IntegerCoordinateTwo(-10, 2), c2 = new IntegerCoordinateTwo(20, 20);
        // 然后创建图对象
        final Graph parse = Graph.create(
                // 创建两个节点数据的表
                Graph.GraphNodeDF.create(
                        Graph.GraphNodeSeries.create(c1, SingletonCell.$("zhao"), SingletonCell.$("20")),
                        Graph.GraphNodeSeries.create(c2, SingletonCell.$("TY"), SingletonCell.$("22"))
                ),
                // 创建出两个节点之间的边
                Graph.GraphEdgeDF.create(
                        // C1 <- 前任 -> C2 代表 C1的前任是C2  C2的前任是C1
                        Graph.GraphEdgeSeries.create(c1, c2, SingletonCell.$("前任"))
                )
        );
        // TODO 获取到图对象中的所有节点数据映射表
        final HashMap<String, Graph.GraphNodeSeries> nodes = parse.getNodes();
        // TODO 获取到图对象中的所有边数据映射表
        final HashMap<String, Graph.GraphEdgeSeries> edges = parse.getEdges();
        // TODO 获取到图中的所有边数据线路对象
        final Collection<IntegerConsanguinityRoute2D> edgesRoute = parse.getEdgesRoute();
        // 我们在这里打印其中的每个线路的信息 TODO 我们将通过线路中的数据从映射表中取出详细数据
        /*
        *    nodes映射表中的数据
        * +----------+------------+
        * |    key   |   value    |
        * +----------+------------+
        * | (-10,2)  |  zhao      |
        * | (20,20)  |  TY        |
        * +----------+------------+
        *
        *
        *   edges映射表中的数据
        *  +----------------------------------+------------------------------------+
        *  |    key                           |                           value    |
        *  +----------------------------------+------------------------------------+
        *  | (-10,2)(-10,2) -> (20,20)(20,20) |  series [(-10,2), (20,20), 前任]    |
        *  +----------------------------------+------------------- ----------------+
        * */
        edgesRoute.forEach(edge -> {
            // 通过坐标名称 从节点映射表中 获取到起始节点与终止节点的数据Series
            final Graph.GraphNodeSeries start = nodes.get(edge.getStartingCoordinateName());
            final Graph.GraphNodeSeries end = nodes.get(edge.getEndPointCoordinateName());
            System.out.println(
                    "当前线路：" + edge + '\n' +
                    "起始点：" + edge.getStartingCoordinate() +
                            // 由于上面构建节点Series的时候 第一个是坐标 第二个是名字 第三个是age
                            // 所以在这里也是相同的格式
                            "\t名称:" + start.getCell(1).getValue() + '\n' +
                            "终止点：" + edge.getEndPointCoordinate() + "\t名称:" + end.getCell(1) + '\n' +
                            "两个点之间的关系: " + edges.get(edge.toString())
            );
        });
    }
}
