package zhao.algorithmMagic.operands.coordinateNet;

import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.route.IntegerConsanguinityRoute2D;
import zhao.algorithmMagic.operands.table.*;
import zhao.algorithmMagic.utils.ASClass;

import java.util.*;

/**
 * 图对象，在这里包含很多的数据，其中的数据以图的形式存在。
 * <p>
 * The graph object contains a lot of data here, and the data exists in the form of a graph.
 *
 * @author zhao
 */
public class Graph extends IntegerRoute2DNet {

    private final HashMap<String, GraphNodeSeries> hashMap = new HashMap<>();

    /**
     * 构造出来一张线路网
     * <p>
     * construct a network of lines
     *
     * @param edge 这张网中的所有线路
     *             <p>
     *             All lines in this net
     */
    protected Graph(Iterator<GraphNodeSeries> nodeIter, Collection<IntegerConsanguinityRoute2D> edge) {
        super(edge);
        // 在这里迭代每一个节点数据并构建节点映射表
        while (nodeIter.hasNext()) {
            final GraphNodeSeries next = nodeIter.next();
            hashMap.put(next.coordinate_0.toString(), next);
        }
    }

    /**
     * 通过指定的节点表 以及 边表来构造出一个图对象。
     *
     * @param nodeIter 节点表中的数据 其应包含每个节点的坐标 以及其对应的更多信息
     * @param edgeIter 边表中的数据 其应该包含每个节点与节点之间的边的数据。
     * @return 图对象
     */
    public static Graph create(GraphNodeDF nodeIter, GraphEdgeDF edgeIter) {
        return Graph.create(
                nodeIter.iterator(),
                edgeIter.iterator()
        );
    }

    /**
     * 通过指定的节点表 以及 边表来构造出一个图对象。
     * 需要注意的是 这里的迭代器中的每一个元素都应该是 GraphNodeSeries 以及 GraphEdgeSeries。
     *
     * @param nodeIter 节点表中的数据 其应包含每个节点的坐标 以及其对应的更多信息
     * @param edgeIter 边表中的数据 其应该包含每个节点与节点之间的边的数据。
     * @return 图对象
     */
    public static Graph create(Iterator<Series> nodeIter, Iterator<Series> edgeIter) {
        List<IntegerConsanguinityRoute2D> queue = new LinkedList<>();
        // 在这里迭代每一个边数据并构建边容器
        while (edgeIter.hasNext()) {
            final Series next = edgeIter.next();
            final IntegerCoordinateTwo cell1;
            final IntegerCoordinateTwo cell2;
            cell1 = (IntegerCoordinateTwo) next.getCell(0).getValue();
            cell2 = (IntegerCoordinateTwo) next.getCell(1).getValue();
            queue.add(IntegerConsanguinityRoute2D.parse(
                    cell1.toString() + " -> " + cell2.toString(),
                    cell1, cell2)
            );
        }
        return new Graph(ASClass.transform(nodeIter), queue);
    }

    public Collection<GraphNodeSeries> getNodes() {
        return hashMap.values();
    }

    /**
     * 图中的节点数据对象，其中包含的数据专用于图对象操作，该类专用于图对象数据存储功能
     * <p>
     * Graph data objects, which contain data dedicated to graph object operations and are dedicated to graph object data storage functions
     */
    public static class GraphNodeSeries extends SingletonSeries {
        IntegerCoordinateTwo coordinate_0;

        protected GraphNodeSeries(IntegerCoordinateTwo coordinateTwoCell, Cell<?>... cells) {
            super(
                    new FinalCell<>(coordinateTwoCell),
                    new FinalCell<>(cells)
            );
            this.coordinate_0 = coordinateTwoCell;
        }

        /**
         * 创建出一个节点数据行。
         *
         * @param coordinateTwoCell 当前节点在图中的坐标。
         * @param cells             当前节点对应的其它属性数据。
         * @return 一个节点对应的数据行 其中第一个单元格是节点的坐标。
         */
        public static GraphNodeSeries create(IntegerCoordinateTwo coordinateTwoCell, Cell<?>... cells) {
            return new GraphNodeSeries(coordinateTwoCell, cells);
        }
    }

    /**
     * 图中的边数据对象，其中包含的数据专用于图对象操作，该类专用于图对象数据存储功能
     * <p>
     * Graph data objects, which contain data dedicated to graph object operations and are dedicated to graph object data storage functions
     */
    public final static class GraphEdgeSeries extends SingletonSeries {
        IntegerCoordinateTwo coordinate_0, coordinate_1;

        private GraphEdgeSeries(IntegerCoordinateTwo start, IntegerCoordinateTwo End, Cell<?>... cells) {
            super(
                    new FinalCell<>(start),
                    new FinalCell<>(End),
                    new FinalCell<>(cells)
            );
            this.coordinate_0 = start;
            this.coordinate_1 = End;
        }

        /**
         * 创建出一个节点数据行。
         *
         * @param start 当前边起始端点在图中的坐标。
         * @param End   当前边结束端点在图中的坐标。
         * @param cells 当前节点对应的其它属性数据。
         * @return 一个边对应的数据行 其中第一和二个单元格是边的两个端点的坐标。
         */
        public static GraphEdgeSeries create(IntegerCoordinateTwo start, IntegerCoordinateTwo End, Cell<?>... cells) {
            return new GraphEdgeSeries(start, End, cells);
        }
    }

    /**
     * 图中的节点表数据对象
     */
    public static class GraphNodeDF extends FDataFrame {

        private final static SingletonSeries COL_1 = SingletonSeries.parse(
                "Coordinate", "Data(Series)"
        );

        protected GraphNodeDF(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList) {
            super(colNameRow, primaryIndex, arrayList);
        }

        /**
         * 构建出来一个节点表数据对象
         *
         * @param graphNodeSeries 所有的节点数据行
         * @return 节点表数据对象
         */
        public static GraphNodeDF create(GraphNodeSeries... graphNodeSeries) {
            final ArrayList<Series> objects = new ArrayList<>(Arrays.asList(graphNodeSeries));
            return new GraphNodeDF(
                    COL_1, 0, objects
            );
        }

    }

    /**
     * 图中的边表数据对象
     */
    public final static class GraphEdgeDF extends GraphNodeDF {
        private final static SingletonSeries COL_2 = SingletonSeries.parse(
                "Coordinate(Start)", "Coordinate(End)", "Data(Series)"
        );

        private GraphEdgeDF(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList) {
            super(colNameRow, primaryIndex, arrayList);
        }

        /**
         * 构建出来一个边表数据对象
         *
         * @param graphEdgeSeries 所有的边数据行
         * @return 边表数据对象
         */
        public static GraphEdgeDF create(GraphEdgeSeries... graphEdgeSeries) {
            final ArrayList<Series> objects = new ArrayList<>(Arrays.asList(graphEdgeSeries));
            return new GraphEdgeDF(
                    COL_2, 0, objects
            );
        }
    }
}
