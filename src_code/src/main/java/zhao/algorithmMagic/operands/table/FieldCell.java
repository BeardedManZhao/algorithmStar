package zhao.algorithmMagic.operands.table;

import java.util.HashMap;

/**
 * 字段名称单元格数据对象，在该对象中您可直接将字段中的诸多信息展示出来，同时提供了别名与函数等操作。
 * <p>
 * Field name cell data object, in which you can directly display a lot of information in the field, and provide operations such as aliases and functions.
 *
 * @author zhao
 */
public class FieldCell extends FinalCell<String> {

    private final static HashMap<String, FieldCell> ASName = new HashMap<>();
    private String AS;

    private FieldCell(String string, boolean isNumber) {
        super(string, isNumber);
        AS = string;
    }

    /**
     * 构造获取提取出一个列名称字段对象。
     *
     * @param colName 需要被提取的列名称对象
     * @return 提取或创建出来的列名称对象。
     */
    public static FieldCell $(String colName) {
        FieldCell fieldCell = ASName.get(colName);
        if (fieldCell != null) {
            return fieldCell;
        }
        FieldCell fieldCell1 = new FieldCell(colName, false);
        ASName.put(colName, fieldCell1);
        return fieldCell1;
    }

    public static Series parse(String[] fields) {
        FieldCell[] fieldCells = new FieldCell[fields.length];
        int index = -1;
        for (String field : fields) {
            fieldCells[++index] = FieldCell.$(field);
        }
        return new FinalSeries(fieldCells);
    }

    public static FieldCell getByAs(String ASNameStr) {
        return ASName.get(ASNameStr);
    }

    public FieldCell as(String string) {
        ASName.put(string, this);
        this.AS = string;
        return this;
    }

    @Override
    public String toString() {
        return this.AS;
    }
}
