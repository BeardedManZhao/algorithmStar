package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.vector.IntegerVector;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 一个整数矩阵，其中维护了一个基元数组，矩阵中基于数组提供了很多转换函数，同时也提供了对维护数组的提取与拷贝函数。
 * <p>
 * An integer matrix, in which a primitive array is maintained, provides many conversion functions based on the array, and also provides extraction and copy functions for the maintained array.
 *
 * @author zhao
 */
public class ColumnIntegerMatrix extends IntegerMatrix {
    private final String[] Field1;
    private final String[] Field2;

    /**
     * 构造一个具有列名属性的整形矩阵
     *
     * @param field1 该矩阵中所对应的列名称
     * @param field2 该矩阵中所对应的行名称
     * @param ints   该矩阵中需要维护的数组
     */
    public ColumnIntegerMatrix(String[] field1, String[] field2, int[]... ints) {
        super(ints);
        if (ints.length > 0) {
            int length = ints[0].length;
            if (field2 != null) {
                if (field2.length == ints.length) {
                    Field2 = field2;
                } else {
                    throw new OperatorOperationException("构造字段矩阵时需要注意字段名的数量与字段数据的列数一一对应！！！ERROR =>  RowField.length = " + field2.length + "\tints.rowCount = " + ints.length);
                }
            } else {
                Field2 = new String[0];
            }
            if (field1 != null) {
                if (field1.length == length) {
                    Field1 = field1;
                } else {
                    throw new OperatorOperationException("构造字段矩阵时需要注意字段名的数量与字段数据的列数一一对应！！！ERROR =>  ColField.length = " + field1.length + "\tints.colCount = " + length);
                }
            } else {
                Field1 = new String[0];
            }
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    /**
     * 构造一个矩阵，矩阵的列数量以矩阵的第一行为准！
     * <p>
     * Construct a matrix, the number of columns of the matrix is based on the first row of the matrix!
     *
     * @param field1 该矩阵中所对应的列名称
     * @param field2 该矩阵中所对应的行名称
     * @param ints   该矩阵中需要维护的数组
     * @return matrix object
     */
    public static ColumnIntegerMatrix parse(String[] field1, String[] field2, int[]... ints) {
        if (ints.length > 0) {
            return new ColumnIntegerMatrix(field1, field2, ints);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    /**
     * @return 该矩阵中所对应的列名称
     */
    public String[] getColFieldNames() {
        return Field1;
    }

    /**
     * @return 该矩阵中对应的行名称
     */
    public String[] getRowFieldNames() {
        return Field2;
    }

    /**
     * @return 将当前的矩阵转换成列名与向量的键值对。
     * <p>
     * Converts the current matrix into a key-value pair of column names and vectors.
     */
    public HashMap<String, IntegerVector> toHashMap() {
        HashMap<String, IntegerVector> hashMap = new HashMap<>(getRowCount() + 10);
        int[][] ints = toIntArrays();
        // 开始添加数据
        for (int i = 0; i < this.Field1.length; i++) {
            // 将当前字段的每一个元素添加到当前字段对应的数组中
            int[] tempCol = new int[ints.length];
            int count = -1;
            for (int[] anInt : ints) {
                tempCol[++count] = anInt[i];
            }
            hashMap.put(this.Field1[i], IntegerVector.parse(tempCol));
        }
        return hashMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] colFieldNames = this.getColFieldNames();
        if (colFieldNames.length != 0) {
            // 添加列字段
            for (String colFieldName : colFieldNames) {
                stringBuilder.append(colFieldName).append('\t');
            }
            if (this.Field2.length != 0) stringBuilder.append("rowColName");
            stringBuilder.append('\n');
        }
        // 添加行字段与行数据
        String[] rowFieldNames = this.getRowFieldNames();
        int[][] ints = this.toIntArrays();
        if (rowFieldNames.length != 0) {
            for (int i = 0; i < ints.length; i++) {
                stringBuilder
                        .append(Arrays.toString(ints[i])).append('\t')
                        .append(rowFieldNames[i]).append('\n');
            }
        } else {
            for (int[] aInt : ints) {
                stringBuilder.append(Arrays.toString(aInt)).append("\n");
            }
        }
        return "------------MatrixStart-----------\n" +
                stringBuilder +
                "------------MatrixEnd------------\n";
    }
}
