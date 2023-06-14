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

    public SFDataFrame(Series colNameRow, int primaryIndex, ArrayList<Series> arrayList) {
        super(colNameRow, primaryIndex, arrayList);
    }

    /**
     * 创建出一个空的DF对象
     *
     * @param colNameRow DF中的字段序列。
     * @param pk         主键列的索引数值
     * @return 空的DF对象。
     */
    public static FDataFrame select(Series colNameRow, int pk) {
        return new SFDataFrame(
                colNameRow, pk, new ArrayList<>()
        ).refreshField(false, true);
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
     * @param isOC           如果设置为 true 代表数据输入设备对象的打开与关闭交由框架管理，在外界将不需要对组件进行打开或关闭操作，反之则代表框架只使用组件，但不会打开与关闭组件对象。
     *                       <p>
     *                       If set to true, it means that the opening and closing of data input device objects are managed by the framework, and there will be no need to open or close components externally. Conversely, it means that the framework only uses components, but will not open or close component objects.
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
    public DataFrame insertColGetNew(Cell<?> fieldName, Transformation<Series, Cell<?>> transformation) {
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

    /**
     * 将一个数据行插入表中。
     * <p>
     * Insert a data row into the table.
     *
     * @param series 需要被插入的数据行，可以接收多个字符串，如果字符串符合数值规则，则将会被转换成为数值单元格。
     *               <p>
     *               The data row that needs to be inserted can receive multiple strings. If the string conforms to numerical rules, it will be converted into numerical cells.
     * @return 插入数据之后的 DF 数据对象。
     * <p>
     * DF data object after inserting data.
     */
    public DataFrame insert(SingletonSeries series) {
        return super.insert(series);
    }

    /**
     * 将一个数据行插入表中。
     * <p>
     * Insert a data row into the table.
     *
     * @param value 需要被插入的数据行，可以接收多个字符串，如果字符串符合数值规则，则将会被转换成为数值单元格。
     *              <p>
     *              The data row that needs to be inserted can receive multiple strings. If the string conforms to numerical rules, it will be converted into numerical cells.
     * @return 插入数据之后的 DF 数据对象。
     * <p>
     * DF data object after inserting data.
     */
    @Override
    public DataFrame insert(String... value) {
        return super.insert(SingletonSeries.parse(value));
    }

    /**
     * 将多个数据行插入到表中。
     *
     * @param rowSeries 需要被插入的数据行
     * @return 插入之后的数据
     */
    public DataFrame insert(SingletonSeries... rowSeries) {
        return super.insert(rowSeries);
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public DataFrame add(DataFrame value) {
        return super.add(value);
    }
}
