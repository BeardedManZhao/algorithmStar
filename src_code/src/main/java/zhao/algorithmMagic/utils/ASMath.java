package zhao.algorithmMagic.utils;

import zhao.algorithmMagic.operands.ComplexNumber;

/**
 * Java类于 2022/10/10 15:30:49 创建
 * <p>
 * 数学工具包，被算法之星使用，当然您也可以使用其中的函数
 * <p>
 * Math Toolkit, used by Algorithm Star, and of course you can also use the functions in it
 *
 * @author 赵凌宇
 */
public final class ASMath {

    public static final String MATHEMATICAL_SYMBOLS_PLUS = "+";
    public static final String MATHEMATICAL_SYMBOLS_SUBTRACT = "-";

    /**
     * 将一个序列的方差计算出来
     *
     * @param doubles 被计算的序列
     * @return 方差数值
     */
    public static double variance(double[] doubles) {
        // 每个样本值与全体样本值的平均数之差
        double avg = avg(doubles);
        double[] temp = new double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            // 将每一个数值与平均数之差的平方计算出来
            temp[i] = Power2(doubles[i] - avg);
        }
        // 返回平均数
        return avg(temp);
    }

    /**
     * 将一个序列的方差计算出来
     *
     * @param ints 被计算的序列
     * @return 方差数值
     */
    public static double variance(int[] ints) {
        // 每个样本值与全体样本值的平均数之差
        int avg = avg(ints);
        double[] temp = new double[ints.length];
        for (int i = 0; i < ints.length; i++) {
            // 将每一个数值与平均数之差的平方计算出来
            temp[i] = Power2(ints[i] - avg);
        }
        // 返回平均数
        return avg(temp);
    }

    /**
     * 计算出来一各序列的平均值
     *
     * @param doubles 被计算的序列
     * @return 均值
     */
    public static double avg(double... doubles) {
        double res = 0;
        for (double aDouble : doubles) {
            res += aDouble;
        }
        return res / doubles.length;
    }

    /**
     * 计算出来一各序列的平均值
     *
     * @param ints 被计算的序列
     * @return 均值
     */
    public static int avg(int... ints) {
        int res = 0;
        for (int aDouble : ints) {
            res += aDouble;
        }
        return res / ints.length;
    }

    /**
     * 对一个整数进行平方计算
     * <p>
     * square an integer
     *
     * @param d 整数
     * @return d的二次方
     */
    public static int Power2(int d) {
        int nd = (d < 0 ? -d : d);
        int i = nd >> 1;
        return d - (i << 1) == 0 ? nd << i : d * d;
    }

    /**
     * 对一个小数进行平方计算
     *
     * @param d 小数
     * @return d的二次方
     */
    public static double Power2(double d) {
        if (d - (int) d == 0) {
            return Power2((int) d);
        } else {
            return d * d;
        }
    }

    /**
     * 对一个数值进行绝对值运算
     *
     * @param num 需要被取绝对值的数值
     * @return num的绝对值
     */
    public static double absoluteValue(double num) {
        return num > 0 ? num : -num;
    }

    /**
     * 对一个数值进行取反
     *
     * @param num 被取反的数值
     * @return 取反结果
     */
    public static double reversal(double num) {
        return -num;
    }

    /**
     * 对一些数值进行取反
     *
     * @param doubles 被取反的数值
     * @return 取反结果
     */
    public static double[] reversal(double... doubles) {
        double[] doubles1 = new double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles1[i] = -doubles[i];
        }
        return doubles1;
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     *
     * @param doubles1 叉乘操作数组
     * @param doubles2 被叉乘的操作数组
     * @return 叉乘结果数组
     */
    public static double[] CrossMultiplication(double[] doubles1, double[] doubles2) {
        double[] res = new double[(doubles1.length - 1) * doubles1.length];
        CrossMultiplication(doubles1.length, doubles2.length, res, doubles1, doubles2);
        return res;
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     * <p>
     * Calculates the cross product of two arrays and returns the result
     *
     * @param doubles1  叉乘操作数组
     *                  <p>
     *                  Array of cross product operations
     * @param doubles2  被叉乘的操作数组
     *                  <p>
     *                  Array of operations to be cross-multiplied
     * @param newLength 结果数组的长度，如果您在外界已经将长度计算出来了的话，那么您可以手动指定叉乘结果的长度，这样框架就不会去重复计算了！
     *                  <p>
     *                  The length of the result array, if you have calculated the length in the outside world, then you can manually specify the length of the cross-multiplication result, so that the framework will not repeat the calculation!
     * @return 叉乘结果数组
     * <p>
     * cross-multiply result array
     */
    public static double[] CrossMultiplication(double[] doubles1, double[] doubles2, int newLength) {
        double[] res = new double[newLength];
        CrossMultiplication(doubles1.length, doubles2.length, res, doubles1, doubles2);
        return res;
    }

    public static ComplexNumber[] CrossMultiplication(ComplexNumber[] complexNumber1, ComplexNumber[] complexNumber2, int newLength) {
        return CrossMultiplication(complexNumber1.length, complexNumber2.length, complexNumber1, complexNumber2, newLength);
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     * <p>
     * Calculates the cross product of two arrays and returns the result
     *
     * @param complexNumber1 叉乘操作数组
     *                       <p>
     *                       Array of cross product operations
     * @param complexNumber2 被叉乘的操作数组
     *                       <p>
     *                       Array of operations to be cross-multiplied
     * @param newLength      结果数组的长度，如果您在外界已经将长度计算出来了的话，那么您可以手动指定叉乘结果的长度，这样框架就不会去重复计算了！
     *                       <p>
     *                       The length of the result array, if you have calculated the length in the outside world, then you can manually specify the length of the cross-multiplication result, so that the framework will not repeat the calculation!
     * @return 叉乘结果数组
     * <p>
     * cross-multiply result array
     */
    private static ComplexNumber[] CrossMultiplication(int colCount1, int colCount2, ComplexNumber[] complexNumber1, ComplexNumber[] complexNumber2, int newLength) {
        ComplexNumber[] ResultsContainer = new ComplexNumber[newLength];
        int now = 0; // 结果数组的索引，用于依次添加叉乘结果
        // 迭代矩阵1该行的每一个元素
        for (int i = 0; i < colCount1; i++) {
            // 迭代矩阵2该行的每一个元素
            for (int ii = 0; ii < colCount2; ii++) {
                if (ii != i) {
                    ResultsContainer[now] = complexNumber1[i].multiply(complexNumber2[ii]);
                    now++;
                }
            }
        }
        return ResultsContainer;
    }

    /**
     * 叉乘结果计算并拷贝到指定的数组中
     *
     * @param colCount1        操作数，数组1的长度
     * @param colCount2        被操作数，数组2的长度
     * @param ResultsContainer 叉乘结果的数据容器
     * @param doubles1         操作数，数组1
     * @param doubles2         被操作数，数组2
     */
    private static void CrossMultiplication(int colCount1, int colCount2, double[] ResultsContainer, double[] doubles1, double[] doubles2) {
        int now = 0; // 结果数组的索引，用于依次添加叉乘结果
        // 迭代矩阵1该行的每一个元素
        for (int i = 0; i < colCount1; i++) {
            // 迭代矩阵2该行的每一个元素
            for (int ii = 0; ii < colCount2; ii++) {
                if (ii != i) {
                    ResultsContainer[now] = doubles1[i] * doubles2[ii];
                    now++;
                }
            }
        }
    }
}
