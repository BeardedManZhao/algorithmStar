package zhao.algorithmMagic.core.model;

import org.jetbrains.annotations.NotNull;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateTwo;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.table.*;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

/**
 * @author 赵凌宇
 * 2023/4/17 20:01
 */
public  final class TarImageClassification extends ArrayList<KeyValue<String, ColorMatrix>> implements ASModel<String, ColorMatrix, HashMap<String, ArrayList<Integer>>> {

    /**
     * 本模型所依赖的第三方模型
     */
    public final static SingleTargetContour SINGLE_TARGET_CONTOUR = (SingleTargetContour) ASModel.SINGLE_TARGET_CONTOUR.clone();
    Function<String, ArrayList<Integer>> newArrayL = s -> new ArrayList<>();

    TarImageClassification() {

    }

    private static void run3(FDataFrame select, KeyValue<String, ColorMatrix> stringColorMatrixKeyValue, ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> entries) {
        String[] row = new String[entries.size() + 1];
        row[0] = stringColorMatrixKeyValue.getKey();
        int index = 0;
        for (Map.Entry<Double, IntegerCoordinateTwo> entry : entries) {
            row[++index] = String.valueOf(entry.getKey());
        }
        select.insert(SingletonSeries.parse(row));
    }

    /**
     * 针对模型进行设置。
     * <p>
     * Set up for the model.
     *
     * @param key   模型中配置的项目编号，一般情况下在实现类中都有提供静态参数编号。
     *              <p>
     *              The project number configured in the model is usually provided with a static parameter number in the implementation class.
     * @param value 模型中配置项的具体数据，其可以是任何类型的单元格对象。
     *              <p>
     */
    @Override
    public void setArg(String key, @NotNull Cell<?> value) {
        Object value1 = value.getValue();
        if (value1 instanceof ColorMatrix) {
            this.add(new KeyValue<>(key, (ColorMatrix) value1));
        } else {
            throw new OperatorOperationException("The parameter value represents the matrix of [" + key + "] and should be of type zhao.algorithmMagic.operands.matrix.ColorMatrix.");
        }
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public HashMap<String, ArrayList<Integer>> function(ColorMatrix... input) {
        // 构建 DF 对象 的列
        String[] field = getStrings(input);
        FDataFrame select = SFDataFrame.select(
                FieldCell.parse(field), 0
        );
        // 将当前集合中的所有图像与所有目标样本矩阵对象进行模板匹配，获取到结果模板对象
        for (KeyValue<String, ColorMatrix> stringColorMatrixKeyValue : this) {
            // 计算出所有图像在当前目标类别样本中的距离
            run1(select, stringColorMatrixKeyValue, input);
        }
        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        // 提取出每一列中的最大的数值对应的行对象 这里的图像索引就是列名
        int indexCol = -1;
        for (int i = 1; i < field.length; i++) {
            ++indexCol;
            Series select1 = select.select(field[i]);
            // 将当前列中的最小数值对应的行名称提取出来
            int index = -1, minIndex = 0, minValue = Integer.MAX_VALUE;
            for (Cell<?> cell : select1.toArray()) {
                if (cell.isNumber()) {
                    ++index;
                    int intValue = cell.getIntValue();
                    if (intValue < minValue) {
                        minIndex = index;
                        minValue = intValue;
                    }
                }
            }
            // 获取到指定的行名称 以及该类对应的图像容器
            String type = select.selectRow(minIndex).getCell(0).toString();
            ArrayList<Integer> colorMatrices = hashMap.computeIfAbsent(type, newArrayL);
            // 将当前图像索引添加到类别容器中
            colorMatrices.add(indexCol);
        }
        return hashMap;
    }

    private void run1(FDataFrame select, KeyValue<String, ColorMatrix> stringColorMatrixKeyValue, ColorMatrix[] input) {
        SINGLE_TARGET_CONTOUR.setArg(SingleTargetContour.TARGET, new FinalCell<>(stringColorMatrixKeyValue.getValue()));
        ArrayList<Map.Entry<Double, IntegerCoordinateTwo>> entries = SINGLE_TARGET_CONTOUR.functionGetC(input);
        run3(select, stringColorMatrixKeyValue, entries);
    }

    @NotNull
    private String[] getStrings(ColorMatrix[] input) {
        String[] field = new String[input.length + 1];
        field[0] = "类别\\度量值";
        for (int i = 0; i < input.length; ) {
            int i1 = i++;
            field[i] = String.valueOf(i1);
        }
        return field;
    }

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    @Override
    public HashMap<String, ArrayList<Integer>> functionConcurrency(ColorMatrix[] input) {
        // 构建 DF 对象 的列
        String[] field = getStrings(input);
        FDataFrame select = SFDataFrame.select(
                FieldCell.parse(field), 0
        );
        // 将当前集合中的所有图像与所有目标样本矩阵对象进行模板匹配，获取到结果模板对象
        CountDownLatch countDownLatch = new CountDownLatch(this.size());
        for (KeyValue<String, ColorMatrix> stringColorMatrixKeyValue : this) {
            new Thread(() -> {
                // 计算出所有图像在当前目标类别样本中的距离
                run1(select, stringColorMatrixKeyValue, input);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new OperatorOperationException("You cannot perform concurrent calculations!!!!!", e);
        }
        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        // 提取出每一列中的最大的数值对应的行对象 这里的图像索引就是列名
        int indexCol = -1;
        for (int i = 1; i < field.length; i++) {
            ++indexCol;
            // 将当前列中的最小数值对应的行名称提取出来
            int index = -1, minIndex = 0, minValue = Integer.MAX_VALUE;
            for (Cell<?> cell : select.select(field[i]).toArray()) {
                if (cell.isNumber()) {
                    ++index;
                    int intValue = cell.getIntValue();
                    if (intValue < minValue) {
                        minIndex = index;
                        minValue = intValue;
                    }
                }
            }
            // 获取到指定的行名称 以及该类对应的图像容器
            String type = select.selectRow(minIndex).getCell(0).toString();
            ArrayList<Integer> colorMatrices = hashMap.computeIfAbsent(type, newArrayL);
            // 将当前图像索引添加到类别容器中
            colorMatrices.add(indexCol);
        }
        return hashMap;
    }
}
