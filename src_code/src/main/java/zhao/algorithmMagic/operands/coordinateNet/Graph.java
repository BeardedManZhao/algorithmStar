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

    private final HashMap<String, GraphNodeSeries> nodes_hashMap = new HashMap<>();
    private final HashMap<String, GraphEdgeSeries> edges_hashMap;

    /**
     * 构造出来一张线路网
     * <p>
     * construct a network of lines
     *
     * @param nodeIter      这张网中所有的节点Series 组成的迭代器。
     * @param edge          这张网中的所有线路
     *                      <p>
     *                      All lines in this net
     * @param edges_hashMap 这张网中要保存的边数据映射表。
     */
    protected Graph(Iterator<GraphNodeSeries> nodeIter, Collection<IntegerConsanguinityRoute2D> edge, HashMap<String, GraphEdgeSeries> edges_hashMap) {
        super(edge);
        // 在这里迭代每一个节点数据并构建节点映射表
        while (nodeIter.hasNext()) {
            final GraphNodeSeries next = nodeIter.next();
            nodes_hashMap.put(next.coordinate_0.toString(), next);
        }
        this.edges_hashMap = edges_hashMap;
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
        HashMap<String, GraphEdgeSeries> edges_hashMap = new HashMap<>();
        // 在这里迭代每一个边数据并构建边容器
        while (edgeIter.hasNext()) {
            final Series next = edgeIter.next();
            final IntegerCoordinateTwo cell1;
            final IntegerCoordinateTwo cell2;
            cell1 = (IntegerCoordinateTwo) next.getCell(0).getValue();
            cell2 = (IntegerCoordinateTwo) next.getCell(1).getValue();
            final IntegerConsanguinityRoute2D parse = IntegerConsanguinityRoute2D.parse(
                    cell1.toString() + " -> " + cell2.toString(),
                    cell1, cell2);
            queue.add(parse);
            edges_hashMap.put(parse.toString(), (GraphEdgeSeries) next);
        }
        return new Graph(ASClass.transform(nodeIter), queue, edges_hashMap);
    }

    /**
     * @return 当前图中已经包含的所有节点对象数据。
     * <p>
     * All node object data already included in the current graph.
     */
    public HashMap<String, GraphNodeSeries> getNodes() {
        return ASClass.transform(this.nodes_hashMap.clone());
    }

    /**
     * @return 当前图中已经包含的所有边对象数据。
     * <p>
     * All edge object data already included in the current graph.
     */
    public HashMap<String, GraphEdgeSeries> getEdges() {
        return ASClass.transform(this.edges_hashMap.clone());
    }

    /**
     * @return 当前图中已经包含的所有边对象数据，值得注意的是这里返回的集合中的每一个元素就是一个线路对象，其中的起始与终止坐标点就是代表的边的两个端点。
     * <p>
     * All edge object data already included in the current graph is worth noting that each element in the set returned here is a line object, where the starting and ending coordinate points represent the two endpoints of the edge.
     */
    public Collection<IntegerConsanguinityRoute2D> getEdgesRoute() {
        return super.getNetDataSet();
    }

    /**
     * 图中的节点数据对象，其中包含的数据专用于图对象操作，该类专用于图对象数据存储功能
     * <p>
     * Graph data objects, which contain data dedicated to graph object operations and are dedicated to graph object data storage functions
     */
    public static class GraphNodeSeries extends SingletonSeries {
        IntegerCoordinateTwo coordinate_0;

        /**
         * @param coordinateTwoCell 当前节点数据行中的坐标。
         *                          <p>
         *                          The coordinates in the current node data row.
         * @param cells             当前节点数据行中的所有数据配置。
         *                          <p>
         *                          All data configurations in the current node data row.
         */
        protected GraphNodeSeries(IntegerCoordinateTwo coordinateTwoCell, Cell<?>... cells) {
            super(
                    ASClass.mergeArray(
                            new Cell[cells.length + 1],
                            new FinalCell<>(coordinateTwoCell), cells
                    )
            );
            this.coordinate_0 = coordinateTwoCell;
        }

        /**
         * @param cells 当前节点数据行中的所有数据配置。
         *              <p>
         *              All data configurations in the current node data row.
         */
        protected GraphNodeSeries(Cell<?>... cells) {
            super(ASClass.check(cells, kv -> {
                final Cell<?>[] key = kv.getKey();
                final StringBuilder sb = kv.getValue();
                if (key.length >= 1) {
                    return true;
                } else {
                    sb.append("您提供的数据格式有错误，请按照如下格式来进行参数传递。\nThe data format you provided is incorrect. Please pass the parameters in the following format.\n" +
                            "\tformat => [GraphEdgeSeries(IntegerCoordinateTwo, property1, property2, ......)]");
                    return false;
                }
            }));
            this.coordinate_0 = (IntegerCoordinateTwo) cells[0].getValue();
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

        /**
         * 创建出一个节点数据行。
         *
         * @param coordinateTwoCell 当前节点在图中的坐标。
         * @param cells             当前节点对应的其它属性数据。
         * @return 一个节点对应的数据行 其中第一个单元格是节点的坐标。
         */
        public static GraphNodeSeries create(IntegerCoordinateTwo coordinateTwoCell, String... cells) {
            final ArrayList<Cell<?>> objects = new ArrayList<>(cells.length + 2);
            objects.add(SingletonCell.$(coordinateTwoCell));
            for (String cell : cells) {
                objects.add(SingletonCell.$_String(cell));
            }
            return new GraphNodeSeries(objects.toArray(new Cell<?>[0]));
        }
    }

    /**
     * 图中的边数据对象，其中包含的数据专用于图对象操作，该类专用于图对象数据存储功能
     * <p>
     * Graph data objects, which contain data dedicated to graph object operations and are dedicated to graph object data storage functions
     */
    public final static class GraphEdgeSeries extends SingletonSeries {
        IntegerCoordinateTwo coordinate_0, coordinate_1;

        /**
         * @param start 当前边中的起始端点对象。
         *              <p>
         *              The starting endpoint object in the current edge.
         * @param End   当前边中的终止端点对象。
         *              <p>
         *              The terminating endpoint object in the current edge.
         * @param cells 当前节点数据行中的所有数据配置。
         *              <p>
         *              All data configurations in the current node data row.
         */
        private GraphEdgeSeries(IntegerCoordinateTwo start, IntegerCoordinateTwo End, Cell<?>... cells) {
            super(
                    ASClass.mergeArray(
                            new Cell[cells.length + 2],
                            new FinalCell[]{new FinalCell<>(start),
                                    new FinalCell<>(End)},
                            cells
                    )
            );
            this.coordinate_0 = start;
            this.coordinate_1 = End;
        }

        /**
         * @param cells 当前节点数据行中的所有数据配置。
         *              <p>
         *              All data configurations in the current node data row.
         */
        private GraphEdgeSeries(Cell<?>... cells) {
            super(ASClass.check(cells, kv -> {
                final Cell<?>[] key = kv.getKey();
                final StringBuilder sb = kv.getValue();
                if (key.length >= 2) {
                    return true;
                } else {
                    sb.append("您提供的数据格式有错误，请按照如下格式来进行参数传递。\nThe data format you provided is incorrect. Please pass the parameters in the following format.\n" +
                            "\tformat => [GraphEdgeSeries(start, end, property1, property2, ......)]");
                    return false;
                }
            }));
            this.coordinate_0 = (IntegerCoordinateTwo) cells[0].getValue();
            this.coordinate_0 = (IntegerCoordinateTwo) cells[1].getValue();
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

        /**
         * 创建出一个节点数据行。
         *
         * @param start 当前边起始端点在图中的坐标。
         * @param End   当前边结束端点在图中的坐标。
         * @param cells 当前节点对应的其它属性数据。
         * @return 一个节点对应的数据行 其中第一个单元格是节点的坐标。
         */
        public static GraphEdgeSeries create(IntegerCoordinateTwo start, IntegerCoordinateTwo End, String... cells) {
            final ArrayList<Cell<?>> objects = new ArrayList<>(cells.length + 4);
            objects.add(SingletonCell.$(start));
            objects.add(SingletonCell.$(End));
            for (String cell : cells) {
                objects.add(SingletonCell.$_String(cell));
            }
            return new GraphEdgeSeries(objects.toArray(new Cell<?>[0]));
        }
    }

    /**
     * 图中的节点表数据对象
     */
    public static class GraphNodeDF extends FDataFrame {

        private final static SingletonSeries COL_1 = SingletonSeries.parse(
                "Coordinate", "Data(Series)"
        );

        /**
         * @param colNameRow   当前 DF 对象中的列名行。
         * @param primaryIndex 当前 DF 对象中的主键索引数值。
         * @param arrayList    当前 DF 对象中的数据行。
         */
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
            return new GraphNodeDF(
                    COL_1, 0, new ArrayList<>(Arrays.asList(graphNodeSeries))
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

        /**
         * @param colNameRow   当前 DF 对象中的列名行。
         * @param primaryIndex 当前 DF 对象中的主键索引数值。
         * @param arrayList    当前 DF 对象中的数据行。
         */
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
            return new GraphEdgeDF(
                    COL_2, 0, new ArrayList<>(Arrays.asList(graphEdgeSeries))
            );
        }
    }
}
