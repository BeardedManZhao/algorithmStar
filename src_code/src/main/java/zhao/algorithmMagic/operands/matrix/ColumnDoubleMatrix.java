package zhao.algorithmMagic.operands.matrix;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.RCNOperands;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.utils.ASMath;
import zhao.algorithmMagic.utils.dataContainer.IntegerAndDoubles;

import java.util.*;

/**
 * 带有字段名称设置的矩阵对象，在此对象中，每个维度的数值都有与之相对应的字段描述。
 * <p>
 * A matrix object with field name settings. In this object, the numerical value of each dimension has its corresponding field description.
 *
 * @author zhao
 */
public class ColumnDoubleMatrix extends DoubleMatrix implements RCNOperands<DoubleVector, double[]> {
    private final String[] Field1;
    private final String[] Field2;

    /**
     * 构造一个具有列名属性的整形矩阵
     *
     * @param colNames 该矩阵中所对应的列名称
     * @param rowNames 该矩阵中所对应的行名称
     * @param ints     该矩阵中需要维护的数组
     */
    public ColumnDoubleMatrix(String[] colNames, String[] rowNames, double[]... ints) {
        super(ints);
        if (ints.length > 0) {
            int length = ints[0].length;
            if (rowNames != null && rowNames.length > 0) {
                if (rowNames.length == ints.length) {
                    Field2 = rowNames;
                } else {
                    throw new OperatorOperationException("构造字段矩阵时需要注意字段名的数量与字段数据的列数一一对应！！！ERROR =>  RowField.length = " + rowNames.length + "\tints.rowCount = " + ints.length);
                }
            } else {
                Field2 = new String[0];
            }
            if (colNames != null && colNames.length > 0) {
                if (colNames.length == length) {
                    Field1 = colNames;
                } else {
                    throw new OperatorOperationException("构造字段矩阵时需要注意字段名的数量与字段数据的列数一一对应！！！ERROR =>  ColField.length = " + colNames.length + "\tints.colCount = " + length);
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
     * @param colNames 该矩阵中所对应的列名称
     * @param rowNames 该矩阵中所对应的行名称
     * @param ints     该矩阵中需要维护的数组
     * @return matrix object
     */
    public static ColumnDoubleMatrix parse(String[] colNames, String[] rowNames, double[]... ints) {
        if (ints.length > 0) {
            return new ColumnDoubleMatrix(colNames, rowNames, ints);
        } else {
            throw new OperatorOperationException("The array of construction matrix cannot be empty");
        }
    }

    protected static void deleteRelatedFunction(double thresholdLeft, double thresholdRight, double[][] ints, double[] mid, ArrayList<double[]> res, ArrayList<String> res_f2, String[] field2) {
        if (ASDynamicLibrary.isUseC()) {
            for (int i = 0; i < ints.length; i++) {
                double[] anInt = ints[i];
                double num = ASMath.correlationCoefficient_C(anInt, mid, anInt.length);
                if (num < thresholdLeft || num > thresholdRight) {
                    // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                    double[] res1 = new double[anInt.length];
                    System.arraycopy(anInt, 0, res1, 0, anInt.length);
                    res_f2.add(field2[i]);
                    res.add(res1);
                }
            }
        } else {
            for (int i = 0; i < ints.length; i++) {
                double[] anInt = ints[i];
                double num = ASMath.correlationCoefficient(anInt, mid);
                if (num < thresholdLeft || num > thresholdRight) {
                    // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                    double[] res1 = new double[anInt.length];
                    System.arraycopy(anInt, 0, res1, 0, anInt.length);
                    res_f2.add(field2[i]);
                    res.add(res1);
                }
            }
        }
    }

    protected static void ex(Random random1, double[][] res, String[] rowNames, int maxIndex, int i) {
        int i1 = random1.nextInt(maxIndex);
        double[] temp = res[i];
        res[i] = res[i1];
        res[i1] = temp;
        String tempS = rowNames[i];
        rowNames[i] = rowNames[i1];
        rowNames[i1] = tempS;
    }

    /**
     * @return 该矩阵中所对应的列名称
     */
    public String[] getColFieldNames() {
        return Field1.clone();
    }

    /**
     * @return 该矩阵中对应的行名称
     */
    public String[] getRowFieldNames() {
        return Field2.clone();
    }

    @Override
    public HashMap<String, DoubleVector> toHashMap() {
        HashMap<String, DoubleVector> hashMap = new HashMap<>(getRowCount() + 10);
        double[][] ints = toArrays();
        // 开始添加数据
        for (int i = 0; i < this.Field1.length; i++) {
            // 将当前字段的每一个元素添加到当前字段对应的数组中
            double[] tempCol = new double[ints.length];
            int count = -1;
            for (double[] anInt : ints) {
                tempCol[++count] = anInt[i];
            }
            hashMap.put(this.Field1[i], DoubleVector.parse(tempCol));
        }
        return hashMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        {
            String[] colFieldNames = this.getColFieldNames();
            if (colFieldNames.length != 0) {
                // 添加列字段
                for (String colFieldName : colFieldNames) {
                    stringBuilder.append(colFieldName).append('\t');
                }
                if (this.Field2.length != 0) {
                    stringBuilder.append("rowColName\n");
                } else stringBuilder.append('\n');
            }
            // 添加行字段与行数据
            String[] rowFieldNames = this.getRowFieldNames();
            double[][] doubles = this.toArrays();
            if (rowFieldNames.length != 0) {
                for (int i = 0; i < doubles.length; i++) {
                    stringBuilder
                            .append(Arrays.toString(doubles[i])).append('\t')
                            .append(rowFieldNames[i]).append('\n');
                }
            } else {
                for (double[] doubles1 : doubles) {
                    stringBuilder.append(Arrays.toString(doubles1)).append("\n");
                }
            }
        }
        return "------------DoubleMatrixStart-----------\n" +
                stringBuilder +
                "------------DoubleMatrixEnd------------\n";
    }

    /**
     * 去除冗余特征维度，将当前矩阵中的每一个维度都进行方差或无向差计算，并将过于稳定的冗余特征去除。
     * <p>
     * Remove redundant feature dimensions, calculate variance or undirected difference of each dimension in the current matrix, and remove redundant features that are too stable.
     *
     * @param threshold 冗余去除阈值，代表去除的百分比，这个值应是一个小于1的数值，例如设置为0.4 代表去除掉冗余程度倒序排行中，最后40% 的维度。
     *                  <p>
     *                  Redundancy removal threshold, which represents the percentage of removal, should be a value less than 1. For example, set to 0.4 to remove the last 40% of the dimensions in the reverse order of redundancy.
     * @return 去除冗余特征维度之后的新矩阵
     * <p>
     * New matrix after removing redundant feature dimensions
     */
    @Override
    public ColumnDoubleMatrix featureSelection(double threshold) {
        if (threshold >= 1) throw Matrix.OPERATOR_OPERATION_EXCEPTION;
        // 计算出本次要去除的维度数量
        int num = (int) (getRowCount() * threshold);
        if (num <= 0) {
            return ColumnDoubleMatrix.parse(getColFieldNames(), getRowFieldNames(), copyToNewArrays());
        } else {
            // 计算出本次剩余的维度数量
            num = getRowCount() - num;
            // 准备好一个排序集合，存储所有的离散值结果与数组
            TreeMap<Double, IntegerAndDoubles> treeMap = new TreeMap<>(Comparator.reverseOrder());
            // 将每一个维度的向量的方差计算出来
            int count = -1;
            for (double[] ints : this.toArrays()) {
                // 计算出离散值，并将离散值与当前行编号以及当前数组添加到集合中
                treeMap.put(ASMath.undirectedDifference(ints), new IntegerAndDoubles(++count, ints));
            }
            // 开始获取到前 num 个数组
            int index = -1;
            // 构建列与数据的存储控件
            String[] rowNames = this.Field2.length == 0 ? null : new String[num];
            double[][] res = new double[num][getColCount()];
            if (rowNames == null) {
                for (IntegerAndDoubles value : treeMap.values()) {
                    System.arraycopy(value.doubles, 0, res[++index], 0, value.doubles.length);
                    --num;
                    if (num == 0) break;
                }
            } else {
                for (IntegerAndDoubles value : treeMap.values()) {
                    System.arraycopy(value.doubles, 0, res[++index], 0, value.doubles.length);
                    rowNames[index] = this.Field2[value.anInt];
                    --num;
                    if (num == 0) break;
                }
            }
            return ColumnDoubleMatrix.parse(getColFieldNames(), rowNames, res);
        }
    }

    /**
     * 删除与目标索引维度相关的所有行维度，并返回新矩阵对象。
     * <p>
     * Delete all row dimensions related to the target index dimension and return a new matrix object.
     *
     * @param index          需要被作为相关系数中心点的行编号。
     *                       <p>
     *                       The row number to be used as the center point of the correlation coefficient.
     * @param thresholdLeft  相关系数阈值，需要被删除的相关系数阈值区间左边界。
     *                       <p>
     *                       The correlation coefficient threshold is the left boundary of the correlation coefficient threshold interval to be deleted.
     * @param thresholdRight 相关系数阈值，需要被删除的相关系数阈值区间右边界。
     *                       <p>
     *                       The correlation coefficient threshold is the right boundary of the correlation coefficient threshold interval to be deleted.
     * @return 进行了相关维度删除之后构造出来的新矩阵
     * <p>
     * The new matrix constructed after deleting relevant dimensions
     */
    @Override
    public ColumnDoubleMatrix deleteRelatedDimensions(int index, double thresholdLeft, double thresholdRight) {
        int rowCount = getRowCount();
        if (index >= 0 && index < rowCount) {
            double[][] ints = toArrays();
            // 获取到当前的相关系数中心序列
            double[] mid = ints[index];
            boolean b1 = this.Field1.length != 0;
            boolean b2 = this.Field2.length != 0;
            ArrayList<double[]> res = new ArrayList<>(rowCount);
            ArrayList<String> res_f2 = new ArrayList<>(b2 ? this.Field2.length : 16);
            // 开始进行计算
            if (b1 && b2) {
                deleteRelatedFunction(thresholdLeft, thresholdRight, ints, mid, res, res_f2, Field2);
                return ColumnDoubleMatrix.parse(
                        this.Field1.clone(), res_f2.toArray(new String[0]), res.toArray(new double[0][])
                );
            } else if (b2) {
                // 说明第 1 字段不需要添加数据
                deleteRelatedFunction(thresholdLeft, thresholdRight, ints, mid, res, res_f2, Field2);
                return ColumnDoubleMatrix.parse(
                        null, res_f2.toArray(new String[0]), res.toArray(new double[0][])
                );
            } else if (b1) {
                // 说明第二字段不需要加数据
                if (ASDynamicLibrary.isUseC()) {
                    for (double[] anInt : ints) {
                        double num = ASMath.correlationCoefficient_C(anInt, mid, anInt.length);
                        if (num < thresholdLeft || num > thresholdRight) {
                            // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                            double[] res1 = new double[anInt.length];
                            System.arraycopy(anInt, 0, res1, 0, anInt.length);
                            res.add(res1);
                        }
                    }
                } else {
                    for (double[] anInt : ints) {
                        double num = ASMath.correlationCoefficient(anInt, mid);
                        if (num < thresholdLeft || num > thresholdRight) {
                            // 这个情况代表是不符合删除区间的，也就是不需要被删除的
                            double[] res1 = new double[anInt.length];
                            System.arraycopy(anInt, 0, res1, 0, anInt.length);
                            res.add(res1);
                        }
                    }
                }
                return ColumnDoubleMatrix.parse(
                        this.Field1.clone(), null, res.toArray(new double[0][])
                );
            } else {
                // 说明都不需要字段名数据
                DoubleMatrix.ex(thresholdLeft, thresholdRight, ints, mid, res);
                return ColumnDoubleMatrix.parse(
                        null, null, res.toArray(new double[0][])
                );
            }
        } else {
            return ColumnDoubleMatrix.parse(this.Field1.clone(), this.Field2.clone(), copyToNewArrays());
        }
    }

    /**
     * 获取到指定名称的行数组
     * <p>
     * Get the row array with the specified name
     *
     * @param name 指定的行目标名称
     *             <p>
     *             Specified row target name
     * @return 一个包含当前行元素的新数组，是支持修改的。
     * <p>
     * A new array containing the elements of the current row supports modification.
     */
    @Override
    public double[] getArrayByRowName(String name) {
        int index = 0;
        for (String s : this.Field2) {
            if (s.equals(name)) {
                return getArrayByRowIndex(index);
            }
            ++index;
        }
        return new double[0];
    }

    /**
     * 获取到指定名称的列数组
     * <p>
     * Get the col array with the specified name
     *
     * @param name 指定的列目标名称
     *             <p>
     *             Specified col target name
     * @return 一个包含当前列元素的新数组，是支持修改的。
     * <p>
     * A new array containing the elements of the current col supports modification.
     */
    @Override
    public double[] getArrayByColName(String name) {
        int index = 0;
        for (String s : this.Field1) {
            if (s.equals(name)) {
                return getArrayByColIndex(index);
            }
            ++index;
        }
        return new double[0];
    }

    /**
     * 将现有矩阵的转置矩阵获取到
     * <p>
     * Get the transpose of an existing matrix into
     *
     * @return 矩阵转置之后的新矩阵
     * <p>
     * new matrix after matrix transpose
     */
    @Override
    public ColumnDoubleMatrix transpose() {
        return ColumnDoubleMatrix.parse(
                this.Field2.clone(),
                this.Field1.clone(),
                super.transpose().toArrays()
        );
    }

    /**
     * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
     * <p>
     * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
     *
     * @param seed 打乱算法中所需要的随机种子。
     *             <p>
     *             Disrupt random seeds required in the algorithm.
     * @return 打乱之后的对象。
     * <p>
     * Objects after disruption.
     */
    @Override
    public ColumnDoubleMatrix shuffle(long seed) {
        // 行是否无字段
        if (this.Field2.length == 0) {
            return ColumnDoubleMatrix.parse(
                    this.Field1.length == 0 ? null : this.Field1.clone(),
                    null,
                    ASMath.shuffle(this.copyToNewArrays(), seed, false));
        } else {
            // 带着行一起迭代
            double[][] res = this.copyToNewArrays();
            String[] rowNames = this.Field2.clone();
            // 生成随机数对象
            Random random = new Random();
            int maxIndex = res.length - 1;
            random.setSeed(seed);
            for (int i = 0; i < res.length; i++) {
                ex(random, res, rowNames, maxIndex, i);
            }
            return ColumnDoubleMatrix.parse(this.Field1.clone(), rowNames, res);
        }
    }

    /**
     * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
     * <p>
     * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
     *
     * @param random1 打乱算法中所需要的随机种子。
     *                <p>
     *                Disrupt random seeds required in the algorithm.
     * @param copy    打乱时是否需要产生一个新矩阵对象，与当前对象完全脱离关系。
     *                <p>
     *                Whether it is necessary to generate a new matrix object when disrupting, which is completely separated from the current object.
     * @param length  打乱时注重的打乱次数，最终打乱会导致最多 length * 2 个元素发生位置变化。
     *                <p>
     *                The number of disruptions that should be paid attention to when disrupting, and the final disruption will result in the position change of up to 2 elements of length *.
     * @return 打乱之后的对象。
     * <p>
     * Objects after disruption.
     */
    public ColumnDoubleMatrix shuffle(Random random1, boolean copy, int length) {
        // 行是否无字段
        if (this.Field2.length == 0) {
            return ColumnDoubleMatrix.parse(
                    this.Field1.length == 0 ? null : this.Field1.clone(),
                    null,
                    ASMath.shuffleFunction(random1, this.getRowCount(), copy ? this.copyToNewArrays() : this.toArrays(), length));
        } else {
            // 带着行一起迭代
            double[][] res;
            String[] rowNames;
            if (copy) {
                res = this.copyToNewArrays();
                rowNames = this.Field2.clone();
            } else {
                res = this.toArrays();
                rowNames = this.Field2;
            }
            // 生成随机数对象
            int maxIndex = res.length - 1;
            for (int i = 0; i < length; i++) {
                ex(random1, res, rowNames, maxIndex, i);
            }
            return ColumnDoubleMatrix.parse(this.Field1.clone(), rowNames, res);
        }
    }

    /**
     * 将数据所维护的数组左移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the left n positions and get the result value
     *
     * @param n    被左移的次数，该数值应取值于 [0, getRowCount]
     *             <p>
     *             The number of times it is moved to the left. The value should be [0, getRowCount]
     * @param copy 本次左移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this left shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public ColumnDoubleMatrix leftShift(int n, boolean copy) {
        if (copy) {
            return ColumnDoubleMatrix.parse(
                    this.Field1.length == 0 ? null : this.Field1.clone(),
                    this.Field2.length == 0 ? null : ASMath.leftShift(this.Field2.clone(), n),
                    ASMath.leftShift(this.copyToNewArrays(), n)
            );
        } else {
            if (this.Field2.length != 0) {
                ASMath.leftShift(this.Field2, n);
            }
            ASMath.leftShift(this.toArrays(), n);
            return this;
        }
    }

    /**
     * 将数据所维护的数组右移n个位置，并获取到结果数值
     * <p>
     * Move the array maintained by the data to the right n positions and get the result value
     *
     * @param n    被右移的次数，该数值应取值于 [0, getRowCount]
     *             <p>
     *             The number of times it is moved to the right. The value should be [0, getRowCount]
     * @param copy 本次右移的作用参数，如果设置为true，代表本次位移会创建出一个新的数组，于当前数组毫无关联。
     *             <p>
     *             If the action parameter of this right shift is set to true, it means that this shift will create a new array, which has no association with the current array.
     * @return 位移之后的AS操作数对象，其类型与调用者数据类型一致。
     * <p>
     * The AS operand object after displacement has the same type as the caller data type.
     */
    @Override
    public ColumnDoubleMatrix rightShift(int n, boolean copy) {
        if (copy) {
            return ColumnDoubleMatrix.parse(
                    this.Field1.length == 0 ? null : this.Field1.clone(),
                    this.Field2.length == 0 ? null : ASMath.rightShift(this.Field2.clone(), n),
                    ASMath.rightShift(this.copyToNewArrays(), n)
            );
        } else {
            if (this.Field2.length != 0) {
                ASMath.rightShift(this.Field2, n);
            }
            ASMath.rightShift(this.toArrays(), n);
            return this;
        }
    }
}
