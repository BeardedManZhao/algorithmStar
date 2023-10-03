package zhao.algorithmMagic;

/**
 * 序列化ID编号枚举类，从 1.25 版本开始实现统一序列化ID，实现跨版本序列化数据传输。
 * <p>
 * Serializing ID number enumeration classes, gradually implementing unified serialization of IDs starting from version 1.25, and achieving cross version serialization data transmission.
 */
public enum SerialVersionUID {

    FINAL_DATA_FRAME {
        @Override
        public long getNum() {
            return -4920746365198459100L;
        }
    },
    SINGLETON_FINAL_DATA_FRAME {
        @Override
        public long getNum() {
            return FINAL_DATA_FRAME.getNum() + SUBCLASS_CAPACITY;
        }
    },
    GraphNodeDF {
        /**
         * @return 唯一的统一的序列号。
         */
        @Override
        public long getNum() {
            return FINAL_DATA_FRAME.getNum() + (SUBCLASS_CAPACITY << 1);
        }
    },
    GraphEdgeDF {
        /**
         * @return 唯一的统一的序列号。
         */
        @Override
        public long getNum() {
            return 0;
        }
    },
    FINAL_SERIES {
        @Override
        public long getNum() {
            return 1846247159227732521L;
        }
    },
    SINGLETON_SERIES {
        @Override
        public long getNum() {
            return 2846247159227764522L;
        }
    },
    GRAPH_NODE_SERIES {
        /**
         * @return 唯一的统一的序列号。
         */
        @Override
        public long getNum() {
            return SINGLETON_SERIES.getNum() + SUBCLASS_CAPACITY;
        }
    },
    GRAPH_EDGE_SERIES {
        /**
         * @return 唯一的统一的序列号。
         */
        @Override
        public long getNum() {
            return SINGLETON_SERIES.getNum() + (SUBCLASS_CAPACITY << 1);
        }
    },
    FINAL_CELL {
        @Override
        public long getNum() {
            return -5865157582568079765L;
        }
    },
    SINGLETON_CELL {
        @Override
        public long getNum() {
            return FINAL_CELL.getNum() + SUBCLASS_CAPACITY;
        }
    },
    FieldCell {
        /**
         * @return 唯一的统一的序列号。
         */
        @Override
        public long getNum() {
            return SINGLETON_CELL.getNum() + (SUBCLASS_CAPACITY << 1);
        }
    },
    GRAPH {
        @Override
        public long getNum() {
            return -1010101010101010101L;
        }
    },
    ;

    /**
     * 每个子类会分配到指定此数量的名额，在序列号的规则中，
     * 假设 A 是 B C D 三个类的父类
     * 子类B的序列号 = 父类A + 此数值 × 1
     * 子类C的序列号 = 父类A + 此数值 × 2
     * 子类D的序列号 = 父类A + 此数值 × 4
     */
    private final static int SUBCLASS_CAPACITY = 100;

    /**
     * @return 唯一的统一的序列号。
     */
    public abstract long getNum();
}
