package zhao.algorithmMagic.operands;

/**
 * Row and column name operands
 * 具有行列名称的操作数对象，常用于矩阵对象中，根据行列名称获取到数据。
 * <p>
 * Operand objects with row and column names are commonly used in matrix objects to obtain data according to the row and column names.
 */
public interface RCNOperands<ArrayType> {
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
    ArrayType getArrayByRowName(String name);

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
    ArrayType getArrayByColName(String name);
}
