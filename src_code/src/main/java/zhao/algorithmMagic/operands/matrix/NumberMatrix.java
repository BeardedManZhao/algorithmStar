package zhao.algorithmMagic.operands.matrix;

/**
 * 数值矩阵，其中的每一个元素都应是一个数值类型的对象，数值矩阵支持维度选择等函数。
 * <p>
 * Numeric matrix, in which each element should be an object of numerical type. Numeric matrix supports functions such as dimension selection.
 *
 * @author zhao
 */
public abstract class NumberMatrix<ImplementationType extends Matrix<?, ?, ?, ?, ?>, ElementType extends Number, ArrayType, ArraysType>
        extends
        Matrix<ImplementationType, ElementType, ArrayType, ArrayType, ArraysType> {
    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount   矩阵中的行数量
     *                   <p>
     *                   the number of rows in the matrix
     * @param colCount   矩阵中的列数量
     *                   <p>
     * @param arraysType 该矩阵对象中的二维数组对象。
     */
    protected NumberMatrix(int rowCount, int colCount, ArraysType arraysType) {
        super(rowCount, colCount, arraysType);
    }

    /**
     * 去除冗余特征维度，将当前矩阵中的每一个维度都进行方差或无向差计算，并将过于稳定的冗余特征去除。
     * <p>
     * Remove redundant feature dimensions, calculate variance or undirected difference of each dimension in the current matrix, and remove redundant features that are too stable.
     *
     * @param threshold 去除阈值，代表去除的维度数量是当前所有维度数量的百分之threshold，小于0.01将认为不进行去除
     *                  <p>
     *                  Removal threshold, which means that the number of dimensions removed is the threshold of the current number of all dimensions. If it is less than 0.01, it will be considered not to be removed
     * @return 去除冗余特征维度之后的新矩阵
     * <p>
     * New matrix after removing redundant feature dimensions
     */
    public abstract ImplementationType featureSelection(double threshold);

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
    public abstract ImplementationType deleteRelatedDimensions(int index, double thresholdLeft, double thresholdRight);

    /**
     * 提取出子矩阵对象
     *
     * @param x1 被提取矩阵在原矩阵中的起始坐标点。
     *           <p>
     *           The starting coordinate point of the extracted matrix in the original matrix.
     * @param y1 被提取矩阵在原矩阵中的起始坐标点。
     *           <p>
     *           The starting coordinate point of the extracted matrix in the original matrix.
     * @param x2 被提取矩阵在原矩阵中的终止坐标点。
     *           <p>
     *           The end coordinate point of the extracted matrix in the original matrix.
     * @param y2 被提取矩阵在原矩阵中的终止坐标点。
     *           <p>
     *           The end coordinate point of the extracted matrix in the original matrix.
     * @return 整形矩阵对象中的子矩阵对象。
     */
    public abstract ImplementationType extractMat(int x1, int y1, int x2, int y2);

    /**
     * 提取出子矩阵对象
     *
     * @param y1 被提取矩阵在原矩阵中的起始坐标点。
     *           <p>
     *           The starting coordinate point of the extracted matrix in the original matrix.
     * @param y2 被提取矩阵在原矩阵中的终止坐标点。
     *           <p>
     *           The end coordinate point of the extracted matrix in the original matrix.
     * @return 整形矩阵对象中的子矩阵对象。
     */
    public abstract ImplementationType extractSrcMat(int y1, int y2);
}
