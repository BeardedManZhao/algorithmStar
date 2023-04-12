package zhao.algorithmMagic.operands.table;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.io.InputComponent;
import zhao.algorithmMagic.utils.ASIO;
import zhao.algorithmMagic.utils.transformation.Transformation;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 一个所有元素都为单例元素的 DF 数据对象，该对象的使用与 FDF 完全一致，其在创建的时候会需要一些时间在 Hash 表中查询内存已知的单元对象，创建完毕之后，内存占用很小。
 * <p>
 * A DF data object with all elements being singleton elements, whose usage is completely consistent with FDF, requires some time to query memory aware unit objects in the Hash table during creation. After creation, the memory usage is very small.
 *
 * @author 赵凌宇
 * 2023/4/12 18:40
 */
public class SFDataFrame extends FDataFrame {

    public SFDataFrame(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList, HashMap<String, Integer> rowHashMap, HashMap<String, Integer> colHashMap) {
        super(colNameRow, primaryIndex, arrayList, rowHashMap, colHashMap);
    }

    /**
     * 从本地文件系统中读取一个数据对象，并返回对应数据对象的建造者类。
     *
     * @param file 需要被读取的文件对象。
     * @return 读取之后会返回该数据集对应的一个建造者对象，在该对象中可以对读取操作进行更加详细的设置，
     */
    public static FDataFrameBuilder builder(File file) {
        return new SFDataFrameBuilder(file);
    }

    /**
     * 从远程数据库中读取一个数据对象，并返回数据对象对应的建造者类。
     *
     * @param DBC 在连接数据库时需要使用的数据库连接对象。
     * @return 数据库连接设置完毕将会返回一个建造者对象，在该对象中可以对读取数据库操作进行更加详细的设置。
     */
    public static FDataFrameBuilder builder(Connection DBC) {
        return new SFDataFrameBuilder(DBC);
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame builder(InputComponent inputComponent) {
        if (inputComponent.open()) {
            DataFrame dataFrame = inputComponent.getSFDataFrame();
            ASIO.close(inputComponent);
            return dataFrame;
        }
        throw new OperatorOperationException("inputComponent open error!!!");
    }

    /**
     * 使用第三方数据源输入组件进行数据的加载，并获取到对应的DataFrame对象。
     *
     * @param inputComponent 需要使用的第三方数据输入组件对象
     * @return 获取到的DataFrame对象。
     */
    public static DataFrame builder(InputComponent inputComponent, boolean isOC) {
        if (isOC) {
            if (inputComponent.open()) {
                DataFrame dataFrame = inputComponent.getSFDataFrame();
                ASIO.close(inputComponent);
                return dataFrame;
            }
            throw new OperatorOperationException("inputComponent open error!!!");
        } else return inputComponent.getSFDataFrame();
    }

    /**
     * 以当前数据集为基准，添加一列新数据，并将添加列数据之后的 Data Frame 对象返回出来。
     * <p>
     * Based on the current dataset, add a new column of data, and return the Data Frame objects after adding the column data.
     *
     * @param fieldName      需要被添加的列数据所对应的列名称，要求不得与已有的DataFrame字段名称重名！
     *                       <p>
     *                       The column name corresponding to the column data to be added must not duplicate the existing DataFrame field name!
     * @param transformation 在添加的列新数据的过程中，提供一个新数据的生成函数，函数中的参数是每行数据的系列对象，您可以根据行数据生成新数值，也可以根据自己的规则生成新数据。
     *                       <p>
     *                       During the process of adding new data for a column, a new data generation function is provided. The parameters in the function are a series of objects for each row of data. You can generate new values based on the row data or generate new data based on your own rules.
     * @return 添加了列字段与列数据之后的DataFrame对象。
     * <p>
     * The DataFrame object after adding column fields and column data.
     */
    @Override
    public DataFrame insertColGetNew(FieldCell fieldName, Transformation<Series, Cell<?>> transformation) {
        ArrayList<Series> arrayList = new ArrayList<>(this.list.size() + 10);
        for (Series cells : this.list) {
            arrayList.add(FinalSeries.merge(
                    cells, transformation.function(cells)
            ));
        }
        return new SFDataFrame(
                FinalSeries.merge(this.colNameRow, fieldName),
                this.primaryIndex,
                arrayList,
                this.rowHashMap,
                new HashMap<>(this.colHashMap.size() + 1)
        ).refreshField(false, true);
    }

}
