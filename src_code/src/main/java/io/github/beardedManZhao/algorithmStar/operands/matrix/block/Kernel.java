package io.github.beardedManZhao.algorithmStar.operands.matrix.block;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.DoubleMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;

/**
 * AS库中的内置卷积核类，其中能够获取到各种内置的卷积核对象。
 * <p>
 * The built-in convolutional kernel class in the AS library, where various built-in convolutional kernel objects can be obtained.
 */
public enum Kernel {

    /**
     * 均值卷积核，其能够按照给定的尺寸生成指定的卷积核，其生成的卷积核将能够平均整个局部像素点。
     * <p>
     * The mean convolution kernel can generate a specified convolution kernel according to a given size, and the generated convolution kernel will be able to average the entire local pixel points.
     */
    AVG {
        @Override
        public DoubleMatrixSpace getKernel(boolean useCopy, int... size) {
            Kernel.checkSize(3, size);
            // 计算出卷积核中的每个元素的均值数值 x = 1 / wh
            double x = 1 / (double) (size[1] * size[0]);
            // 开始进行填充操作
            return DoubleMatrixSpace.fill(x, useCopy, size);
        }
    },
    /**
     * 边缘检测卷积核，此卷积核着重突出纵向的边缘信息，其将会生成一个左右两个极端边界的卷积核
     * <p>
     * Edge detection convolutional kernel, which focuses on highlighting vertical edge information, will generate a convolutional kernel with two extreme boundaries on the left and right sides
     */
    SobelX {
        /**
         * 获取到当前卷积核对应的矩阵空间对象，可直接提供给卷积计算函数使用。
         * <p>
         * Obtain the matrix space object corresponding to the current convolutional kernel, which can be directly provided to the convolutional calculation function for use.
         *
         * @param useCopy 在叠加不同层矩阵的时候是否使用深拷贝操作进行叠加，卷积核是有很多层的，其中每一层代表的是一个颜色通道。
         *                <p>
         *                When stacking different layers of matrices, whether to use deep copy operation for stacking? Convolutional kernels have many layers, with each layer representing a color channel.
         * @param size    卷积核的尺寸 其中第一个元素是核的层数，第二个元素是核矩阵长度 第二个元素是核矩阵宽度 第三个元素是偏置项。
         *                <p>
         *                The size of convolutional kernels, where the first element is the number of layers of the kernel, the second element is the length of the kernel matrix, the second element is the width of the kernel matrix, and the third element is the bias term.
         * @return 对应的卷积核，这是一个矩阵空间对象，其中包含很多层，一般情况下，每一层对应的都是一个颜色通道的卷积核。
         */
        @Override
        public DoubleMatrixSpace getKernel(boolean useCopy, int... size) {
            Kernel.checkSize(1, size);
            // 构造一个矩阵 作为卷积核
            DoubleMatrix w_lay = DoubleMatrix.parse(
                    new double[]{-1, 0, 1},
                    new double[]{-2, 0, 2},
                    new double[]{-1, 0, 1}
            );
            // 将三个矩阵叠加成为一个矩阵空间 作为 RGB 三个层的卷积核
            DoubleMatrix[] res = new DoubleMatrix[size[0]];
            ASClass.matToArray(useCopy, res, w_lay);
            return DoubleMatrixSpace.parse(res);
        }
    },
    /**
     * 边缘检测卷积核，此卷积核着重突出横向的边缘信息，其将会生成一个左右两个极端边界的卷积核
     * <p>
     * Edge detection convolutional kernel, which focuses on highlighting vertical edge information, will generate a convolutional kernel with two extreme boundaries on the left and right sides
     */
    SobelY {
        /**
         * 获取到当前卷积核对应的矩阵空间对象，可直接提供给卷积计算函数使用。
         * <p>
         * Obtain the matrix space object corresponding to the current convolutional kernel, which can be directly provided to the convolutional calculation function for use.
         *
         * @param useCopy 在叠加不同层矩阵的时候是否使用深拷贝操作进行叠加，卷积核是有很多层的，其中每一层代表的是一个颜色通道。
         *                <p>
         *                When stacking different layers of matrices, whether to use deep copy operation for stacking? Convolutional kernels have many layers, with each layer representing a color channel.
         * @param size    卷积核的尺寸 其中第一个元素是核的层数，第二个元素是核矩阵长度 第二个元素是核矩阵宽度 第三个元素是偏置项。
         *                <p>
         *                The size of convolutional kernels, where the first element is the number of layers of the kernel, the second element is the length of the kernel matrix, the second element is the width of the kernel matrix, and the third element is the bias term.
         * @return 对应的卷积核，这是一个矩阵空间对象，其中包含很多层，一般情况下，每一层对应的都是一个颜色通道的卷积核。
         */
        @Override
        public DoubleMatrixSpace getKernel(boolean useCopy, int... size) {
            Kernel.checkSize(1, size);
            // 构造一个矩阵 作为卷积核
            DoubleMatrix w_lay = DoubleMatrix.parse(
                    new double[][]{
                            {-1, -2, -1},
                            {0, 0, 0},
                            {1, 2, 1}
                    }
            );
            // 将三个矩阵叠加成为一个矩阵空间 作为 RGB 三个层的卷积核
            DoubleMatrix[] res = new DoubleMatrix[size[0]];
            ASClass.matToArray(useCopy, res, w_lay);
            return DoubleMatrixSpace.parse(res);
        }
    };

    /**
     * 检查卷积核尺寸数值是否正确。
     * <p>
     * Check if the convolutional kernel size value is correct.
     *
     * @param length 被检查的size 参数的期望长度。
     *               <p>
     *               The expected length of the size parameter being checked.
     * @param size   被检查的 size 参数。
     *               <p>
     *               The size parameter being checked.
     */
    private static void checkSize(int length, int... size) {
        // 计算出缺失的层
        int layer = length - size.length;
        if (layer > 0) {
            throw new OperatorOperationException("size 参数不合法，其中的元素个数与预期不一致。\nsize 参数不合法，其中的元素个数与预期不一致。\n" +
                    "Expected number of elements = " + length + "\nfind you number of elements = " + size.length + "\nNumber of missing elements = " + layer);
        }
    }

    /**
     * 获取到当前卷积核对应的矩阵空间对象，可直接提供给卷积计算函数使用。
     * <p>
     * Obtain the matrix space object corresponding to the current convolutional kernel, which can be directly provided to the convolutional calculation function for use.
     *
     * @param useCopy 在叠加不同层矩阵的时候是否使用深拷贝操作进行叠加，卷积核是有很多层的，其中每一层代表的是一个颜色通道。
     *                <p>
     *                When stacking different layers of matrices, whether to use deep copy operation for stacking? Convolutional kernels have many layers, with each layer representing a color channel.
     * @param size    卷积核的尺寸 其中第一个元素是核的层数，第二个元素是核矩阵长度 第二个元素是核矩阵宽度 第三个元素是偏置项。
     *                <p>
     *                The size of convolutional kernels, where the first element is the number of layers of the kernel, the second element is the length of the kernel matrix, the second element is the width of the kernel matrix, and the third element is the bias term.
     * @return 对应的卷积核，这是一个矩阵空间对象，其中包含很多层，一般情况下，每一层对应的都是一个颜色通道的卷积核。
     */
    public abstract DoubleMatrixSpace getKernel(boolean useCopy, int... size);

}
