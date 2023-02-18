package zhao.algorithmMagic.utils;

import zhao.algorithmMagic.core.ASDynamicLibrary;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.ComplexNumber;
import zhao.algorithmMagic.operands.matrix.DoubleMatrix;
import zhao.algorithmMagic.operands.matrix.IntegerMatrix;
import zhao.algorithmMagic.operands.matrix.block.DoubleMatrixSpace;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.utils.filter.ArrayDoubleFiltering;
import zhao.algorithmMagic.utils.filter.ArrayIntegerFiltering;
import zhao.algorithmMagic.utils.filter.NumericalFiltering;

import java.awt.*;
import java.util.*;

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
    public static final double THE_INDEX_OF_LOG_IS_2 = Math.log(2);

    /**
     * 计算一个序列的最大值与最小值
     *
     * @param doubles 被计算的序列
     * @return 计算结果，第一个参数是最大值，第二个参数是最小值
     */
    public static double[] MaxAndMin(double[] doubles) {
        // 计算最大值最小值
        double max = doubles[0];
        double min = doubles[0];
        for (int i = 1; i < doubles.length; i++) {
            max = Math.max(max, doubles[i]);
            min = Math.min(min, doubles[i]);
        }
        return new double[]{max, min};
    }

    /**
     * 计算一个序列的最大值与最小值
     *
     * @param doubles 被计算的序列
     * @return 计算结果，第一个参数是最大值，第二个参数是最小值
     */
    public static int[] MaxAndMin(int[] doubles) {
        // 计算最大值最小值
        int max = doubles[0];
        int min = doubles[0];
        for (int i = 1; i < doubles.length; i++) {
            max = Math.max(max, doubles[i]);
            min = Math.min(min, doubles[i]);
        }
        return new int[]{max, min};
    }


    /**
     * 计算一个序列中的最大值或最小值
     *
     * @param doubles            需要被计算的序列
     * @param isMax              如果为true，代表计算的是最大值
     * @param numericalFiltering 取极值时的数值过滤器
     * @return 序列中所有元素的极值
     */
    public static double MaxOrMin(double[] doubles, boolean isMax, NumericalFiltering numericalFiltering) {
        // 计算最大值最小值
        double res = doubles[0];
        if (isMax) {
            for (double aDouble : doubles) {
                if (numericalFiltering.isComplianceEvents(aDouble)) {
                    if (res < aDouble) {
                        res = aDouble;
                    }
                }
            }
        } else {
            for (double aDouble : doubles) {
                if (numericalFiltering.isComplianceEvents(aDouble)) {
                    if (res > aDouble) {
                        res = aDouble;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 计算一个序列的最大值与最小值
     *
     * @param ints               被计算的序列
     * @param isMax              如果为true，代表取出序列中的最大值，反之则是最小值。
     * @param numericalFiltering 数据过滤器。
     * @return 计算结果，第一个参数是最大值，第二个参数是最小值
     */
    public static int MaxOrMin(int[] ints, boolean isMax, NumericalFiltering numericalFiltering) {
        // 计算最大值最小值
        int res = ints[0];
        if (isMax) {
            for (int aDouble : ints) {
                if (numericalFiltering.isComplianceEvents(aDouble)) {
                    if (res < aDouble) {
                        res = aDouble;
                    }
                }
            }
        } else {
            for (int aDouble : ints) {
                if (numericalFiltering.isComplianceEvents(aDouble)) {
                    if (res > aDouble) {
                        res = aDouble;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 将一个序列的方差计算出来
     *
     * @param doubles 被计算的序列
     * @return 方差数值
     */
    public static double variance(double[] doubles) {
        // 每个样本值与全体样本值的平均数之差
        double avg = avg(doubles);
        double sum = 0;
        for (double aDouble : doubles) {
            // 将每一个数值与平均数之差的平方计算出来，并进行求和
            sum += Power2(aDouble - avg);
        }
        // 返回方差
        return sum / doubles.length;
    }

/*
    /**
     * 计算一个数值的阶乘
     *
     * @param i              被计算的数值
     * @param factorialCount 阶乘的次数，该参数可以对阶乘进行一个限制，用于概率计算等操作
     * @return 阶乘结果
     * /
    public static int Factorial(int i, int factorialCount) {
        int res = 1;
        int count = 0;
        while (count < factorialCount && i > 1) {
            res *= i;
            i -= 1;
            count++;
        }
        return res;
    }
*/

    /**
     * 将一个序列的方差计算出来
     *
     * @param ints 被计算的序列
     * @return 方差数值
     */
    public static double variance(int[] ints) {
        // 每个样本值与全体样本值的平均数之差
        double avg = avg(ints);
        double sum = 0;
        for (int anInt : ints) {
            // 将每一个数值与平均数之差的平方计算出来
            sum += Power2(anInt - avg);
        }
        // 返回平均数
        return sum / ints.length;
    }

    /**
     * 将一个序列的方差计算出来
     *
     * @param ints 被计算的序列
     * @return 方差数值
     */
    public static double undirectedDifference(int[] ints) {
        // 每个样本值与全体样本值的平均数之差
        double avg = avg(ints);
        double sum = 0;
        for (int anInt : ints) {
            // 将每一个数值与平均数之差的平方计算出来
            sum += absoluteValue(anInt - avg);
        }
        // 返回平均数
        return sum / ints.length;
    }

    /**
     * 无向差用于描述一组数据的稳定性，与方差计算区别在于方差使用平方，无向差使用绝对值，避免了数值过大，数值溢出的问题。
     *
     * @param doubles 需要被计算无向差的序列
     * @return 无向差越大，代表稳定性越差。
     */
    public static double undirectedDifference(double[] doubles) {
        // 每个样本值与全体样本值的平均数之差
        double avg = avg(doubles);
        double sum = 0;
        for (double anInt : doubles) {
            // 将每一个数值与平均数之差的平方计算出来
            sum += absoluteValue(anInt - avg);
        }
        // 返回平均数
        return sum / doubles.length;
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
    public static double avg(int... ints) {
        int res = 0;
        for (int aDouble : ints) {
            res += aDouble;
        }
        return res / (double) ints.length;
    }

    /**
     * 使用C编译的DLL库的方式，计算出一个序列数组的均值。
     *
     * @param length  需要被计算的长度
     * @param doubles 需要被计算的序列对象
     * @return 均值
     */
    public static native double avg_C(int length, double... doubles);

    /**
     * 使用C编译的DLL库的方式，计算出一个序列数组的均值。
     *
     * @param length  需要被计算的长度
     * @param doubles 需要被计算的序列对象
     * @return 均值
     */
    public static native double avg_C(int length, int... doubles);

    /**
     * 对一个整数进行平方计算
     * <p>
     * square an integer
     *
     * @param d 整数
     * @return d的二次方
     */
    public static int Power2(int d) {
        return d == 2 ? d << 1 : d * d;
    }

    /**
     * 对一个小数进行平方计算
     *
     * @param d 小数
     * @return d的二次方
     */
    public static double Power2(double d) {
        return d * d;
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
     * 对一个数值进行绝对值运算
     *
     * @param num 需要被取绝对值的数值
     * @return num的绝对值
     */
    public static int absoluteValue(int num) {
        return num > 0 ? num : -num;
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     *
     * @param doubles1 叉乘操作数组
     * @param doubles2 被叉乘的操作数组
     * @return 叉乘结果数组
     */
    public static double[] CrossMultiplication(double[] doubles1, double[] doubles2) {
        return CrossMultiplication(doubles1, doubles2, (doubles2.length - 1) * doubles1.length);
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     *
     * @param ints1 叉乘操作数组
     * @param ints2 被叉乘的操作数组
     * @return 叉乘结果数组
     */
    public static int[] CrossMultiplication(int[] ints1, int[] ints2) {
        return CrossMultiplication(ints1, ints2, (ints2.length - 1) * ints1.length);
    }

    /**
     * 计算两个数组的叉乘，并返回结果
     *
     * @param ints1     叉乘操作数组
     * @param ints2     被叉乘的操作数组
     * @param newLength 新数组的长度，该参数的提供是为了在外界可以减少重复计算新数组长度的操作。
     * @return 叉乘结果数组
     */
    public static int[] CrossMultiplication(int[] ints1, int[] ints2, int newLength) {
        int[] res = new int[newLength];
        if (ASDynamicLibrary.isUseC()) {
            CrossMultiplication_C(ints1.length, ints2.length, res, newLength, ints1, ints2);
        } else {
            CrossMultiplication(ints1.length, ints2.length, res, ints1, ints2);
        }
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
        if (ASDynamicLibrary.isUseC()) {
            CrossMultiplication_C(doubles1.length, doubles2.length, res, newLength, doubles1, doubles2);
        } else {
            CrossMultiplication(doubles1.length, doubles2.length, res, doubles1, doubles2);
        }
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
    public static void CrossMultiplication(int colCount1, int colCount2, double[] ResultsContainer, double[] doubles1, double[] doubles2) {
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

    /**
     * 叉乘结果计算并拷贝到指定的数组中
     *
     * @param colCount1        操作数，数组1的长度
     * @param colCount2        被操作数，数组2的长度
     * @param ResultsContainer 叉乘结果的数据容器
     * @param doubles1         操作数，数组1
     * @param doubles2         被操作数，数组2
     */
    public static void CrossMultiplication(int colCount1, int colCount2, int[] ResultsContainer, int[] doubles1, int[] doubles2) {
        int now = 0b11111111111111111111111111111111;
        for (int i = 0; i < colCount1; i++) {
            // 迭代矩阵2该行的每一个元素
            for (int ii = 0; ii < colCount2; ii++) {
                if (ii != i) {
                    ResultsContainer[++now] = doubles1[i] * doubles2[ii];
                }
            }
        }
    }

    /**
     * 叉乘结果计算并拷贝到指定的数组中
     *
     * @param colCount1        操作数，数组1的长度
     * @param colCount2        被操作数，数组2的长度
     * @param ResultsContainer 叉乘结果的数据容器
     * @param length           叉乘结果数据容器中的可用空间
     * @param doubles1         操作数，数组1
     * @param doubles2         被操作数，数组2
     */
    public static native void CrossMultiplication_C(int colCount1, int colCount2, int[] ResultsContainer, int length, int[] doubles1, int[] doubles2);

    /**
     * 叉乘结果计算并拷贝到指定的数组中
     *
     * @param colCount1        操作数，数组1的长度
     * @param colCount2        被操作数，数组2的长度
     * @param ResultsContainer 叉乘结果的数据容器
     * @param length           叉乘结果数据容器中的可用空间
     * @param doubles1         操作数，数组1
     * @param doubles2         被操作数，数组2
     */
    public static native void CrossMultiplication_C(int colCount1, int colCount2, double[] ResultsContainer, int length, double[] doubles1, double[] doubles2);

    /**
     * 取得两个集合的并集
     * <p>
     * get the union of two sets
     *
     * @param set1          集合1
     * @param set2          集合2
     * @param <ElementType> 集合中的元素类型
     *                      <p>
     *                      the type of elements in the collection
     * @return 两个集合的并集。
     * <p>
     * get the union of two sets
     */
    public static <ElementType> Set<ElementType> Union(Set<ElementType> set1, Set<ElementType> set2) {
        HashSet<ElementType> hashSet = new HashSet<>(set1.size() + set2.size() + 0x10);
        hashSet.addAll(set1);
        hashSet.addAll(set2);
        return hashSet;
    }

    /**
     * 取得两个集合的交集
     * <p>
     * get the intersection of two sets
     *
     * @param set1          集合1
     * @param set2          集合2
     * @param <ElementType> 集合中的元素类型
     * @return 两个集合的交集。
     * <p>
     * get the intersection of two sets
     */
    public static <ElementType> Set<ElementType> intersection(Set<ElementType> set1, Set<ElementType> set2) {
        final HashSet<ElementType> hashSet = (HashSet<ElementType>) Union(set1, set2);
        hashSet.removeIf(elementType -> !set1.contains(elementType) || !set2.contains(elementType));
        return hashSet;
    }

    /**
     * 减少内部计算的获取交集集合
     *
     * @param set1          集合1
     * @param set2          集合2
     * @param hashSet       提前计算好的两个集合的并集
     * @param <ElementType> 集合中的元素类型
     * @return 两个集合的交集
     */
    public static <ElementType> Set<ElementType> intersection(Set<ElementType> set1, Set<ElementType> set2, Set<ElementType> hashSet) {
        final HashSet<ElementType> hashSet1 = new HashSet<>(hashSet);
        hashSet1.removeIf(elementType -> !set1.contains(elementType) || !set2.contains(elementType));
        return hashSet1;
    }

    /**
     * 计算出两个字符串的并集
     *
     * @param value1 被计算的字符串1
     * @param value2 被计算的字符串2
     * @return 两个字符串的并集
     */
    public static String Union(String value1, String value2) {
        final StringBuilder stringBuilder = new StringBuilder(value1.length() + value2.length() + 16);
        stringBuilder.append(value1);
        for (char c : value2.toCharArray()) {
            if (!ASStr.contains(value1, c)) stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
/*

    /**
     * 将一个数组排序
     *
     * @param doubles 需要排序的数组
     * @param des     排序之后的数组
     * @return 排序之后的数组，注意这里的数组就是传入的数组
     * /
    public static double[] sort(boolean des, double... doubles) {
        Arrays.sort(doubles);
        if (!des) {
            return ASStr.ArrayReverse(doubles);
        }
        return doubles;
    }
*/

    /**
     * 计算两个字符串的交集
     *
     * @param value1 被计算字符串1
     * @param value2 被计算字符串2
     * @return 两个字符串之间的交集
     */
    public static String intersection(String value1, String value2) {
        return intersection(value1, value2, Union(value1, value2));
    }

    /**
     * 计算两个字符串的交集，节省了并集的计算，交集运算中，使用了并集计算
     *
     * @param value1 被计算字符串1
     * @param value2 被计算字符串2
     * @param v1Uv2  两个字符串的并集
     * @return 两个字符串之间的交集
     */
    public static String intersection(String value1, String value2, String v1Uv2) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (char c : v1Uv2.toCharArray()) {
            if (ASStr.contains(value1, c) && ASStr.contains(value2, c)) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将区间内的所有数值进行累加
     *
     * @param start 区间起始数值
     * @param end   区间终止数值
     * @return 区间内所有数值的累加结果
     */
    public static int sumOfRange(int start, int end) {
        if (start == end) {
            return start;
        }
        return ((start + end) * ((end - start) + 1)) >> 1;
    }

    /**
     * 将区间内的所有数值进行累加
     *
     * @param start 区间起始数值
     * @param end   区间终止数值
     * @return 区间内所有数值的累加结果
     */
    public static double sumOfRange(double start, double end) {
        if (start == end) {
            return start;
        }
        return ((start + end) * ((end - start) + 1)) / 2;
    }

    /**
     * 计算两个序列之间的相关系数
     *
     * @param doubles1 被计算的序列1
     * @param doubles2 被计算的序列2
     * @return 序列1与序列2之间的相关系数，如果为负数则代表负相关，反之代表正相关，系数绝对值越接近1 数据联系就越高。
     * <p>
     * The correlation coefficient between sequence 1 and sequence 2. If it is negative, it represents negative correlation.
     * If it is negative, it represents positive correlation.
     * The closer the absolute value of the coefficient is to 1, the higher the data connection will be.
     */
    public static double correlationCoefficient(int[] doubles1, int[] doubles2) {
        int n = doubles1.length;
        if (n == doubles2.length) {
            int Sx = 0; // 第一个序列的总和
            int Sy = 0; // 第二个序列的总和
            int Sxy = 0; // 双序列元素之乘积的总和
            int SxF2 = 0; // 第一个序列的平方总和
            int SyF2 = 0; // 第二个序列的平方总和
            // 开始计算
            for (int i = 0; i < n; i++) {
                int x = doubles1[i];
                int y = doubles2[i];
                Sx += x;
                Sy += y;
                Sxy += x * y;
                SxF2 += x * x;
                SyF2 += y * y;
            }
            return ((n * Sxy) - (Sx * Sy)) / (Math.sqrt(n * SxF2 - (Sx * Sx)) * Math.sqrt(n * SyF2 - (Sy * Sy)));
        } else {
            throw new OperatorOperationException("计算相关系数时的两个序列中所包含的元素数量应相等哦！\nWhen calculating the correlation coefficient, the number of elements contained in the two sequences should be equal!");
        }
    }

    /**
     * 计算两个序列之间的相关系数
     *
     * @param doubles1 被计算的序列1
     * @param doubles2 被计算的序列2
     * @return 序列1与序列2之间的相关系数，如果为负数则代表负相关，反之代表正相关，系数绝对值越接近1 数据联系就越高。
     * <p>
     * The correlation coefficient between sequence 1 and sequence 2. If it is negative, it represents negative correlation. If it is negative, it represents positive correlation. The closer the absolute value of the coefficient is to 1, the higher the data connection will be.
     */
    public static double correlationCoefficient(double[] doubles1, double[] doubles2) {
        int n = doubles1.length;
        if (n == doubles2.length) {
            double Sx = 0; // 第一个序列的总和
            double Sy = 0; // 第二个序列的总和
            double Sxy = 0; // 双序列元素之乘积的总和
            double SxF2 = 0; // 第一个序列的平方总和
            double SyF2 = 0; // 第二个序列的平方总和
            // 开始计算
            for (int i = 0; i < n; i++) {
                double x = doubles1[i];
                double y = doubles2[i];
                Sx += x;
                Sy += y;
                Sxy += x * y;
                SxF2 += x * x;
                SyF2 += y * y;
            }
            return ((n * Sxy) - (Sx * Sy)) / (Math.sqrt(n * SxF2 - (Sx * Sx)) * Math.sqrt(n * SyF2 - (Sy * Sy)));
        } else {
            throw new OperatorOperationException("计算相关系数时的两个序列中所包含的元素数量应相等哦！\nWhen calculating the correlation coefficient, the number of elements contained in the two sequences should be equal!");
        }
    }

    /**
     * 计算两个序列之间的相关系数
     *
     * @param ints1  被计算的序列1
     * @param ints2  被计算的序列2
     * @param length 两个序列数组中的元素数量
     * @return 序列1与序列2之间的相关系数，如果为负数则代表负相关，反之代表正相关，系数绝对值越接近1 数据联系就越高。
     * <p>
     * The correlation coefficient between sequence 1 and sequence 2. If it is negative, it represents negative correlation. If it is negative, it represents positive correlation. The closer the absolute value of the coefficient is to 1, the higher the data connection will be.
     */
    public static native double correlationCoefficient_C(int[] ints1, int[] ints2, int length);

    /**
     * 计算两个序列之间的相关系数
     *
     * @param doubles1 被计算的序列1
     * @param doubles2 被计算的序列2
     * @param length   两个序列数组中的元素数量
     * @return 序列1与序列2之间的相关系数，如果为负数则代表负相关，反之代表正相关，系数绝对值越接近1 数据联系就越高。
     * <p>
     * The correlation coefficient between sequence 1 and sequence 2. If it is negative, it represents negative correlation. If it is negative, it represents positive correlation. The closer the absolute value of the coefficient is to 1, the higher the data connection will be.
     */
    public static native double correlationCoefficient_C(double[] doubles1, double[] doubles2, int length);

    /**
     * 将一个数组打乱
     *
     * @param doubles 需要被打乱的数组对象，需要注意的是，打乱操作就发生在本身对象中。
     * @param Seed    打乱时的随机种子
     * @param copy    如果设置为true，代表本次随机打乱操作需要作用于新数组
     * @return 打乱之后的数组对象。
     */
    public static double[] shuffle(double[] doubles, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            double[] res = new double[doubles.length];
            System.arraycopy(doubles, 0, res, 0, doubles.length);
            return shuffleFunction(random, doubles.length - 1, res);
        } else {
            return shuffleFunction(random, doubles.length - 1, doubles);
        }
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @return 打乱之后的数组对象。
     */
    private static double[] shuffleFunction(Random random, int maxIndex, double[] res) {
        for (int i = 0; i < res.length; i++) {
            int i1 = random.nextInt(maxIndex);
            double temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将一个数组打乱
     *
     * @param doubles 需要被打乱的数组对象，需要注意的是，打乱操作就发生在本身对象中。
     * @param Seed    打乱时的随机种子
     * @param copy    如果设置为true，代表本次随机打乱操作需要作用于新数组
     * @return 打乱之后的数组对象。
     */
    public static int[] shuffle(int[] doubles, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            int[] res = new int[doubles.length];
            System.arraycopy(doubles, 0, res, 0, doubles.length);
            return shuffleFunction(random, doubles.length - 1, res);
        } else {
            return shuffleFunction(random, doubles.length - 1, doubles);
        }
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @return 打乱之后的数组对象。
     */
    private static int[] shuffleFunction(Random random, int maxIndex, int[] res) {
        for (int i = 0; i < res.length; i++) {
            int i1 = random.nextInt(maxIndex);
            int temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将2维数组按照行打乱顺序。
     *
     * @param ints 需要被打乱的二维数组
     * @param Seed 洗牌打乱时的随即种子
     * @param copy 是否使用拷贝新数组的方式进行打乱
     * @return 打乱之后的数组对象，是否为新数组需要看copy函数的值
     */
    public static int[][] shuffle(int[][] ints, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            int[][] res = ints.clone();
            return shuffleFunction(random, ints.length - 1, res, res.length);
        } else {
            return shuffleFunction(random, ints.length - 1, ints, ints.length);
        }
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @param length   需要被打乱的随机次数
     * @return 打乱之后的数组对象。
     */
    public static int[][] shuffleFunction(Random random, int maxIndex, int[][] res, int length) {
        for (int i = 0; i < length; i++) {
            int i1 = random.nextInt(maxIndex);
            int[] temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将2维数组按照行打乱顺序。
     *
     * @param doubles 需要被打乱的二维数组
     * @param Seed    洗牌打乱时的随即种子
     * @param copy    是否使用拷贝新数组的方式进行打乱
     * @return 打乱之后的数组对象，是否为新数组需要看copy函数的值
     */
    public static double[][] shuffle(double[][] doubles, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            double[][] res = doubles.clone();
            return shuffleFunction(random, doubles.length - 1, res, res.length);
        } else {
            return shuffleFunction(random, doubles.length - 1, doubles, doubles.length);
        }
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @param length   需要进行随机打乱的次数
     * @return 打乱之后的数组对象。
     */
    public static double[][] shuffleFunction(Random random, int maxIndex, double[][] res, int length) {
        for (int i = 0; i < length; i++) {
            int i1 = random.nextInt(maxIndex);
            double[] temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将2维数组按照行打乱顺序。
     *
     * @param complexNumbers 需要被打乱的二维数组
     * @param Seed           洗牌打乱时的随即种子
     * @param copy           是否使用拷贝新数组的方式进行打乱
     * @return 打乱之后的数组对象，是否为新数组需要看copy函数的值
     */
    public static ComplexNumber[][] shuffle(ComplexNumber[][] complexNumbers, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            ComplexNumber[][] res = new ComplexNumber[complexNumbers.length][];
            ASClass.array2DCopy(complexNumbers, res);
            return shuffleFunction(random, complexNumbers.length - 1, res);
        } else {
            return shuffleFunction(random, complexNumbers.length - 1, complexNumbers);
        }
    }

    /**
     * 将任意类型的数组打乱顺序
     *
     * @param ts   需要被打乱的二维数组
     * @param Seed 洗牌打乱时的随即种子
     * @param copy 是否使用拷贝新数组的方式进行打乱
     * @param <T>  需要被随机打乱的数组中，每一个元素的数据类型
     * @return 打乱之后的数组对象，是否为新数组需要看copy函数的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] shuffle(T[] ts, final long Seed, final boolean copy) {
        Random random = new Random();
        random.setSeed(Seed);
        if (copy) {
            Object[] res = new Object[ts.length];
            System.arraycopy(ts, 0, res, 0, res.length);
            return (T[]) shuffleFunction(random, ts.length - 1, res);
        } else {
            return shuffleFunction(random, ts.length - 1, ts);
        }
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @param <T>      需要被随机打乱的数组中，每一个元素的数据类型
     * @return 打乱之后的数组对象。
     */
    public static <T> T[] shuffleFunction(Random random, int maxIndex, T[] res) {
        for (int i = 0; i < res.length; i++) {
            int i1 = random.nextInt(maxIndex);
            T temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将一个数组中的所有元素打乱顺序，其中被打乱顺序的是数组本身，打乱时不会返回一个新的数组。
     *
     * @param random   打乱数组时使用的随机数生成器。
     * @param maxIndex 打乱数组时，随机索引的最大值。
     * @param res      需要被打乱的数组对象，该对象本身会被打乱。
     * @return 打乱之后的数组对象。
     */
    public static ComplexNumber[][] shuffleFunction(Random random, int maxIndex, ComplexNumber[][] res) {
        for (int i = 0; i < res.length; i++) {
            int i1 = random.nextInt(maxIndex);
            ComplexNumber[] temp = res[i1];
            res[i1] = res[i];
            res[i] = temp;
        }
        return res;
    }

    /**
     * 将一个矩阵中的数据按照行使用随机算法打乱，打乱之后会生成一个新矩阵，从新矩阵中获取到layer行元素，然后再一次打乱，直到原矩阵中的数据遍历结束。
     *
     * @param doubleMatrix 需要被随机打乱的原矩阵对象
     * @param seed         打乱数据的时候使用的随机种子
     * @param layer        矩阵快中所包含的矩阵层数
     * @param rowOfLayer   每一层包含多少行数据
     * @return 一个由doubleMatrix拆分出来的layer个子矩阵所组成的矩阵块。
     */
    public static DoubleMatrixSpace shuffleAndSplit(double[][] doubleMatrix, long seed, int layer, int rowOfLayer) {
        Random random = new Random();
        random.setSeed(seed);
        // 构造子矩阵容器
        int count = -1, max = layer - 1;
        DoubleMatrix[] doubleMatrices = new DoubleMatrix[layer];
        // 获取到矩阵中的数组对象
        final int max1 = rowOfLayer - 1;
        while (count != max) {
            // 开始进行迭代，依次获取到矩阵中的rowOfLayer个元素，直到层数满足layer
            double[][] temp = new double[rowOfLayer][];
            int count1 = -1;
            for (int i = 0; i <= max1; i++) {
                temp[++count1] = doubleMatrix[i];
            }
            doubleMatrices[++count] = DoubleMatrix.parse(temp);
            // 重新打乱
            shuffleFunction(random, doubleMatrix.length, doubleMatrix, rowOfLayer);
            random.setSeed(seed = (seed << 1) - 2);
        }
        return DoubleMatrixSpace.parse(doubleMatrices);
    }

    /**
     * 将一个矩阵中的数据按照行使用随机算法打乱，打乱之后会生成一个新矩阵，从新矩阵中获取到layer行元素，然后再一次打乱，直到原矩阵中的数据遍历结束。
     *
     * @param integerMatrix 需要被随机打乱的原矩阵对象
     * @param seed          打乱数据的时候使用的随机种子
     * @param layer         矩阵快中所包含的矩阵层数
     * @param rowOfLayer    每一层包含多少行数据
     * @return 一个由doubleMatrix拆分出来的layer个子矩阵所组成的矩阵块。
     */
    public static IntegerMatrixSpace shuffleAndSplit(int[][] integerMatrix, long seed, int layer, int rowOfLayer) {
        Random random = new Random();
        random.setSeed(seed);
        // 构造子矩阵容器
        int count = -1, max = layer - 1;
        IntegerMatrix[] doubleMatrices = new IntegerMatrix[layer];
        // 获取到矩阵中的数组对象
        final int max1 = rowOfLayer - 1;
        while (count != max) {
            // 开始进行迭代，依次获取到矩阵中的rowOfLayer个元素，直到层数满足layer
            int[][] temp = new int[rowOfLayer][];
            int count1 = -1;
            for (int i = 0; i <= max1; i++) {
                temp[++count1] = integerMatrix[i];
            }
            doubleMatrices[++count] = IntegerMatrix.parse(temp);
            // 重新打乱
            shuffleFunction(random, integerMatrix.length, integerMatrix, rowOfLayer);
            random.setSeed(seed = (seed << 1) - 2);
        }
        return IntegerMatrixSpace.parse(doubleMatrices);
    }

    /**
     * 计算log的数值
     *
     * @param base log中的底数
     * @param x    log中的指数
     * @return log 以 base为底，x为指数的结果数值
     */
    public static double log(double base, double x) {
        if (base == 2) return Math.log(x) / THE_INDEX_OF_LOG_IS_2;
        else return Math.log(x) / Math.log(base);
    }

    /**
     * 一个样本中，先按照某个组进行样本切分，并计算出某个过滤条件下的熵。
     * <p>
     * In a sample, first segment the sample according to a certain group, and calculate the entropy under a certain filtering condition.
     *
     * @param ints                   需要被计算的所有样本特征向量，其中按照行数据划分，需要注意的是，在计算的时候，将会把整个特征作为一个组进行计算，不会细分小类别！！！
     *                               <p>
     *                               All sample feature vectors that need to be calculated are divided according to row data. It should be noted that the whole feature will be calculated as a group during calculation, and will not be subdivided into small categories!!!
     * @param logBase                信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                               <p>
     *                               The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param arrayIntegerFiltering1 行数据过滤器，在这里代表一个事件，计算的时候会将样本传递给该过滤器的函数，只需要实现其中的真事件逻辑即可。
     *                               <p>
     *                               The row data filter, which represents an event here, will pass the sample to the function of the filter during calculation, and only need to implement the true event logic.
     * @return 整个数据样本中 在事件 arrayIntegerFiltering1 的过滤下，所计算出来的信息熵。
     * <p>
     * The information entropy calculated in the whole data sample under the filtering of the event arrayIntegerFiltering1.
     * <p>
     * H(arrayIntegerFiltering1)
     */
    public static double entropy(int[][] ints, int logBase, ArrayIntegerFiltering arrayIntegerFiltering1) {
        int trueNum = 0, count = 0;
        // 首先获取到当前符合样本条件的所有样本数据
        for (int[] anInt : ints) {
            // 进行真假统计
            if (arrayIntegerFiltering1.isComplianceEvents(anInt)) {
                ++trueNum;
            }
            ++count;
        }
        // 获取到组内真假概率
        double pTrue = trueNum / (double) count;
        // 返回信息熵
        return -(pTrue * log(logBase, pTrue));
    }

    /**
     * 一个样本中，先按照某个组进行样本切分，并计算出某个过滤条件下的熵。
     * <p>
     * In a sample, first segment the sample according to a certain group, and calculate the entropy under a certain filtering condition.
     *
     * @param ints                 需要被计算的所有样本特征向量，其中按照行数据划分，需要注意的是，在计算的时候，将会把整个特征作为一个组进行计算，不会细分小类别！！！
     *                             <p>
     *                             All sample feature vectors that need to be calculated are divided according to row data. It should be noted that the whole feature will be calculated as a group during calculation, and will not be subdivided into small categories!!!
     * @param logBase              信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                             <p>
     *                             The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param arrayDoubleFiltering 行数据过滤器，在这里代表一个事件，计算的时候会将样本传递给该过滤器的函数，如果您希望计算出条件熵，那么只需要实现其中的真事件逻辑即可。
     *                             <p>
     *                             The row data filter, which represents an event here, will pass the sample to the function of the filter during calculation, and only need to implement the true event logic.
     * @return 整个数据样本中 在事件 arrayDoubleFiltering 的过滤下，所计算出来的信息熵。
     * <p>
     * The information entropy calculated in the whole data sample under the filtering of the event arrayDoubleFiltering.
     * <p>
     * H(arrayDoubleFiltering)
     */
    public static double entropy(double[][] ints, int logBase, ArrayDoubleFiltering arrayDoubleFiltering) {
        int trueNum = 0, count = 0;
        // 首先获取到当前符合样本条件的所有样本数据
        for (double[] anInt : ints) {
            // 进行真假统计
            if (arrayDoubleFiltering.isComplianceEvents(anInt)) {
                ++trueNum;
            }
            ++count;
        }
        // 获取到组内真假概率
        double pTrue = trueNum / (double) count;
        // 返回信息熵
        return -(pTrue * log(logBase, pTrue));
    }

    /**
     * 计算出一个数据样本中的条件熵，同时删除不符合事件函数的所有行数据。
     * <p>
     * Calculate the conditional entropy in a data sample, and delete all row data that do not meet the event function.
     *
     * @param arrayList            需要被计算的数据样本，注意 该样本将会被修改，其中的假事件行数据会被删除
     *                             <p>
     *                             The data sample to be calculated. Note that the sample will be modified and the false event line data will be deleted
     * @param logBase              信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                             <p>
     *                             The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param arrayDoubleFiltering 行数据过滤器，在这里代表一个事件，计算的时候会将样本传递给该过滤器的函数，如果您希望计算出条件熵，那么只需要实现其中的真事件逻辑即可。
     *                             <p>
     *                             The row data filter, which represents an event here, will pass the sample to the function of the filter during calculation, and only need to implement the true event logic.
     * @return 整个数据样本中 在事件 arrayDoubleFiltering 的过滤下，所计算出来的信息熵。
     * <p>
     * The information entropy calculated in the whole data sample under the filtering of the event arrayDoubleFiltering.
     */
    public static double entropyAndDelete(ArrayList<double[]> arrayList, int logBase, ArrayDoubleFiltering arrayDoubleFiltering) {
        int trueNum = 0, count = 0;
        ArrayList<double[]> deleteList = new ArrayList<>();
        // 首先获取到当前符合样本条件的所有样本数据
        for (double[] anInt : arrayList) {
            // 进行真假统计
            if (arrayDoubleFiltering.isComplianceEvents(anInt)) {
                ++trueNum;
            } else deleteList.add(anInt);
            ++count;
        }
        // 删除所有为假事件的行数据
        arrayList.removeAll(deleteList);
        // 获取到组内真假概率
        double pTrue = trueNum / (double) count;
        // 返回信息熵
        return -(pTrue * log(logBase, pTrue));
    }

    /**
     * 计算出一个数据样本中的条件熵，同时删除不符合事件函数的所有行数据。
     * <p>
     * Calculate the conditional entropy in a data sample, and delete all row data that do not meet the event function.
     *
     * @param arrayList             需要被计算的数据样本，注意 该样本将会被修改，其中的假事件行数据会被删除
     *                              <p>
     *                              The data sample to be calculated. Note that the sample will be modified and the false event line data will be deleted
     * @param logBase               信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                              <p>
     *                              The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param arrayIntegerFiltering 行数据过滤器，在这里代表一个事件，计算的时候会将样本传递给该过滤器的函数，如果您希望计算出条件熵，那么只需要实现其中的真事件逻辑即可。
     *                              <p>
     *                              The row data filter, which represents an event here, will pass the sample to the function of the filter during calculation, and only need to implement the true event logic.
     * @return 整个数据样本中 在事件 arrayIntegerFiltering 的过滤下，所计算出来的信息熵。
     * <p>
     * The information entropy calculated in the whole data sample under the filtering of the event arrayIntegerFiltering.
     */
    public static double entropyAndDelete(ArrayList<int[]> arrayList, int logBase, ArrayIntegerFiltering arrayIntegerFiltering) {
        int trueNum = 0, count = 0;
        ArrayList<int[]> deleteList = new ArrayList<>();
        // 首先获取到当前符合样本条件的所有样本数据
        for (int[] anInt : arrayList) {
            // 进行真假统计
            if (arrayIntegerFiltering.isComplianceEvents(anInt)) {
                ++trueNum;
            } else deleteList.add(anInt);
            ++count;
        }
        // 删除所有为假事件的行数据
        arrayList.removeAll(deleteList);
        // 获取到组内真假概率
        double pTrue = trueNum / (double) count;
        // 返回信息熵
        return -(pTrue * log(logBase, pTrue));
    }

    /**
     * 按照某一列进行分组，并计算出该数据样本的信息熵
     * <p>
     * Group according to a column and calculate the information entropy of the data sample
     *
     * @param ints       需要被计算的所有数据行组成的数据样本
     * @param logBase    信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                   <p>
     *                   The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param groupIndex 需要被分组的字段。
     *                   <p>
     *                   Fields to be grouped.
     * @return 数据样本中按照 groupIndex 这一字段分组计算出来的信息熵数值。
     * <p>
     * The information entropy value in the data sample calculated by grouping according to the groupIndex field.
     */
    public static double entropy(double[][] ints, int logBase, int groupIndex) {
        HashMap<Double, Integer> hashMap = new HashMap<>();
        for (double[] anInt : ints) {
            double group = anInt[groupIndex];
            hashMap.put(group, hashMap.getOrDefault(group, 0) + 1);
        }
        double length = ints.length;
        double res = 0;
        for (int value : hashMap.values()) {
            double p = value / length;
            res -= (p * log(logBase, p));
        }
        return res;
    }

    /**
     * 按照某一列进行分组，并计算出该数据样本的信息熵
     * <p>
     * Group according to a column and calculate the information entropy of the data sample
     *
     * @param ints       需要被计算的所有数据行组成的数据样本
     * @param logBase    信息熵的对数底数，其会作为在信息熵计算过程中的一个参数，一般取值为2。
     *                   <p>
     *                   The logarithmic base of information entropy, which will be used as a parameter in the calculation of information entropy, is generally 2.
     * @param groupIndex 需要被分组的字段。
     *                   <p>
     *                   Fields to be grouped.
     * @return 数据样本中按照 groupIndex 这一字段分组计算出来的信息熵数值。
     * <p>
     * The information entropy value in the data sample calculated by grouping according to the groupIndex field.
     */
    public static double entropy(int[][] ints, int logBase, int groupIndex) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int[] anInt : ints) {
            int group = anInt[groupIndex];
            hashMap.put(group, hashMap.getOrDefault(group, 0) + 1);
        }
        double res = 0;
        double length = ints.length;
        for (int value : hashMap.values()) {
            double p = value / length;
            res -= (p * log(logBase, p));
        }
        return res;
    }

    /**
     * 计算两个样本之间的一元线性回归模型系数。
     *
     * @param features 模型中的自变量数值组成的数组
     * @param targets  模型中的因变量数值组成的数组
     * @return 一个包含回归系数与线性偏置值的数组。
     */
    public static double[] linearRegression(int[] features, int[] targets) {
        int n = 0;
        int sum_features = 0, sum_targets = 0;
        while (n < features.length) {
            sum_features += features[n];
            sum_targets += targets[n++];
        }
        double avg_features = sum_features / (double) n, avg_targets = sum_targets / (double) n;
        double xx_bar = 0, xy_bar = 0;
        int i = 0;
        while (i < n) {
            double f_avg_diff = features[i] - avg_features;
            xx_bar += Power2(f_avg_diff);
            xy_bar += (f_avg_diff) * (targets[i++] - avg_targets);
        }
        // 将 回归系数 与 偏置值 封装起来。
        double regressionCoefficient = xy_bar / xx_bar;
        return new double[]{regressionCoefficient, avg_targets - regressionCoefficient * avg_features};
    }

    /**
     * 计算两个样本之间的一元线性回归模型系数。
     *
     * @param features 模型中的自变量数值组成的数组
     * @param targets  模型中的因变量数值组成的数组
     * @return 一个包含回归系数与线性偏置值的数组。
     */
    public static double[] linearRegression(double[] features, double[] targets) {
        int n = 0;
        double sum_features = 0.0, sum_targets = 0.0;
        while (n < features.length) {
            sum_features += features[n];
            sum_targets += targets[n++];
        }
        double avg_features = sum_features / n, avg_targets = sum_targets / n;
        double xx_bar = 0.0, xy_bar = 0.0;
        for (int i = 0; i < n; i++) {
            double f_avg_diff = features[i] - avg_features;
            xx_bar += Power2(f_avg_diff);
            xy_bar += (f_avg_diff) * (targets[i] - avg_targets);
        }
        // 将 回归系数 与 偏置值 封装起来。
        double regressionCoefficient = xy_bar / xx_bar;
        return new double[]{regressionCoefficient, avg_targets - regressionCoefficient * avg_features};
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static int[] leftShift(int[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, 0);
        } else {
            leftShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组左移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行左移操作的函数
     * @param n         左移位的数值
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static int[] leftShiftNv(int[] arrayType, int n) {
        int diff = arrayType.length - n;
        // 将每一个不会溢出的元素都向前移动n位
        System.arraycopy(arrayType, n, arrayType, 0, diff);
        for (int i = diff; i < arrayType.length; i++) {
            // 将剩余的所有元素赋值0
            arrayType[i] = 0;
        }
        return arrayType;
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static double[] leftShift(double[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, 0);
        } else {
            leftShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组左移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行左移操作的函数
     * @param n         左移位的数值
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static double[] leftShiftNv(double[] arrayType, int n) {
        int diff = arrayType.length - n;
        // 将每一个不会溢出的元素都向前移动n位
        System.arraycopy(arrayType, n, arrayType, 0, diff);
        for (int i = diff; i < arrayType.length; i++) {
            // 将剩余的所有元素赋值0
            arrayType[i] = 0.0;
        }
        return arrayType;
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @param <data>    需要被左移数组中每一个元素的数据类型。
     *                  <p>
     *                  The data type of each element in the array needs to be shifted left.
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static <data> data[] leftShift(data[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, null);
        } else {
            return leftShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组左移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行左移操作的函数
     * @param n         左移位的数值
     * @param <data>    需要被左移数组中每一个元素的数据类型。
     *                  <p>
     *                  The data type of each element in the array needs to be shifted left.
     * @return 左移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the left shift is the formal parameter object itself.
     */
    public static <data> data[] leftShiftNv(data[] arrayType, int n) {
        int diff = arrayType.length - n;
        // 将每一个不会溢出的元素都向前移动n位
        System.arraycopy(arrayType, n, arrayType, 0, diff);
        for (int i = diff; i < arrayType.length; i++) {
            // 将剩余的所有元素赋值 null
            arrayType[i] = null;
        }
        return arrayType;
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static int[] rightShift(int[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, 0);
        } else {
            rightShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组左移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行左移操作的函数
     * @param n         左移位的数值
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static int[] rightShiftNv(int[] arrayType, int n) {
        // 将每一个不会溢出的元素向后移动n位
        System.arraycopy(arrayType, 0, arrayType, n, arrayType.length - n);
        // 将前面的所有剩余位赋予 0
        for (int i = 0; i < n; i++) {
            arrayType[i] = 0;
        }
        return arrayType;
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static double[] rightShift(double[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, 0);
        } else {
            rightShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组右移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行右移操作的函数
     * @param n         左移位的数值
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static double[] rightShiftNv(double[] arrayType, int n) {
        // 将每一个不会溢出的元素向后移动n位
        System.arraycopy(arrayType, 0, arrayType, n, arrayType.length - n);
        // 将前面的所有剩余位赋予 0
        for (int i = 0; i < n; i++) {
            arrayType[i] = 0;
        }
        return arrayType;
    }

    /**
     * 不创建新数组的情况下，在原数组上进行修改，并像左整体移动n位的函数。
     * <p>
     * Without creating a new array, modify it on the original array and move it n bits as a whole.
     *
     * @param arrayType 需要被进行左移操作的数组
     *                  <p>
     *                  The Array of left-shift operations to be performed
     * @param n         需要被进行左移操作的数量
     *                  <p>
     *                  The number of left-shift operations to be performed
     * @param <data>    需要被左移数组中每一个元素的数据类型。
     *                  <p>
     *                  The data type of each element in the array needs to be shifted left.
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static <data> data[] rightShift(data[] arrayType, int n) {
        if (n < 0) return arrayType;
        if (n >= arrayType.length) {
            Arrays.fill(arrayType, 0);
        } else {
            return rightShiftNv(arrayType, n);
        }
        return arrayType;
    }

    /**
     * 无校验的数组左移函数，其直接作用于形参数组对象所在的内存空间，该函数的调用需要在外界有校验的条件下进行调用性能会更佳，否则将会带来异常！
     *
     * @param arrayType 需要被进行左移操作的函数
     * @param n         左移位的数值
     * @param <data>    需要被左移数组中每一个元素的数据类型。
     *                  <p>
     *                  The data type of each element in the array needs to be shifted left.
     * @return 右移之后的数组，该数组就是形参对象本身。
     * <p>
     * The array after the right shift is the formal parameter object itself.
     */
    public static <data> data[] rightShiftNv(data[] arrayType, int n) {
        // 将每一个不会溢出的元素向后移动n位
        System.arraycopy(arrayType, 0, arrayType, n, arrayType.length - n);
        // 将前面的所有剩余位赋予 0
        for (int i = 0; i < n; i++) {
            arrayType[i] = null;
        }
        return arrayType;
    }

    /**
     * 找到一个数组中的最大值，并将值作为函数的返回值
     *
     * @param arr 需要被计算的数组
     * @return arr数组中的最大值对应的索引
     */
    public static int findMaxIndex(int[] arr) {
        int res = 0;
        int maxIndex = arr.length - 1;
        {
            boolean isOk = false;
            int left = 0, right = maxIndex;
            while (left < right) {
                int mid = (right + left) >> 1;
                int mv = arr[mid];
                int next = arr[mid + 1];
                // 峰值在右侧
                if (mv < next) left = mid;
                else if (mv > next) {
                    if (arr[mid - 1] > mv) {
                        // 峰值在左侧
                        right = mid;
                    } else {
                        res = mid;
                        isOk = true;
                        break;
                    }
                }
            }
            if (!isOk) {
                res = arr[left] > arr[right] ? left : right;
            }
        }
        int valueIndex = 0;
        int value = arr[valueIndex];
        {
            int i = arr[maxIndex];
            if (value < i) {
                valueIndex = maxIndex;
                value = i;
            }
        }
        if (value > arr[res]) return valueIndex;
        else return res;
    }

    /**
     * 找到一个数组中的最大值，并将值作为函数的返回值
     *
     * @param arr 需要被计算的数组
     * @return arr数组中的最大值对应的索引
     */
    public static int findMaxIndex(double[] arr) {
        int res = 0;
        int maxIndex = arr.length - 1;
        {
            boolean isOk = false;
            int left = 0, right = maxIndex, backL = -1, backR = -1;
            while (left < right && (backL != left || backR != right)) {
                backL = left;
                backR = right;
                int mid = (right + left) >> 1;
                double mv = arr[mid];
                double next = arr[mid + 1];
                // 峰值在右侧
                if (mv < next) left = mid;
                else if (mv > next) {
                    if (arr[mid - 1] > mv) {
                        // 峰值在左侧
                        right = mid;
                    } else {
                        res = mid;
                        isOk = true;
                        break;
                    }
                }
            }
            if (!isOk) {
                res = arr[left] > arr[right] ? left : right;
            }
        }
        int valueIndex = 0;
        double value = arr[valueIndex];
        {
            double i = arr[maxIndex];
            if (value < i) {
                valueIndex = maxIndex;
                value = i;
            }
        }
        if (value > arr[res]) return valueIndex;
        else return res;
    }

    /**
     * 找到一个数组中的最小值，并将值作为函数的返回值
     *
     * @param arr 需要被计算的数组
     * @return arr数组中的最大值对应的索引
     */
    public static int findMinIndex(int[] arr) {
        int res = 0;
        int maxIndex = arr.length - 1;
        if (maxIndex > 1) {
            boolean isOk = false;
            int left = 0, right = maxIndex;
            while (left < right) {
                int mid = (right + left) >> 1;
                int mv = arr[mid];
                int next = arr[mid + 1];
                // 谷底值在右侧
                if (mv > next) left = mid;
                else if (mv < next) {
                    // 谷底值在左侧
                    if (arr[mid - 1] < mv) right = mid;
                    else {
                        res = mid;
                        isOk = true;
                        break;
                    }
                }
            }
            if (!isOk) {
                res = arr[left] < arr[right] ? left : right;
            }
        }
        int valueIndex = 0;
        int value = arr[valueIndex];
        {
            int i = arr[maxIndex];
            if (value > i) {
                valueIndex = maxIndex;
                value = i;
            }
        }
        if (value < arr[res]) return valueIndex;
        else return res;
    }

    /**
     * 找到一个数组中的最小值，并将值作为函数的返回值
     *
     * @param arr 需要被计算的数组
     * @return arr数组中的最大值对应的索引
     */
    public static int findMinIndex(double[] arr) {
        int res = 0;
        int maxIndex = arr.length - 1;
        if (maxIndex > 1) {
            boolean isOk = false;
            int left = 0, right = maxIndex;
            while (left < right) {
                int mid = (right + left) >> 1;
                double mv = arr[mid];
                double next = arr[mid + 1];
                if (mv > next) left = mid;
                else if (mv < next) {
                    if (arr[mid - 1] < mv) right = mid;
                    else {
                        res = mid;
                        isOk = true;
                        break;
                    }
                }
            }
            if (!isOk) {
                res = arr[left] < arr[right] ? left : right;
            }
        }
        int valueIndex = 0;
        double value = arr[valueIndex];
        {
            double i = arr[maxIndex];
            if (value > i) {
                valueIndex = maxIndex;
                value = i;
            }
        }
        if (value < arr[res]) return valueIndex;
        else return res;
    }

    /**
     * 对一份数据生成权重数组，其权重值为当前列所有数值的平均值。
     *
     * @param targetIndex   目标值，也是需要被排除在外的字段编号，如果设置为-1，代表不排除任何数值编号。
     * @param integerMatrix 需要被进行权重数组生成的矩阵数组矩阵
     * @param ByLine        如果设置为true，则将会按照每一行生成权重值
     * @return 生成了权重数值的数组，其每一个元素代表了一列的数值
     */
    public static double[] generateWeight(int targetIndex, IntegerMatrix integerMatrix, boolean ByLine) {
        double[] weight;
        int[][] arrays = ByLine ? integerMatrix.toArrays() : integerMatrix.transpose().toArrays();
        weight = new double[arrays.length - 1];
        int count = -1;
        for (int i = 0; i < arrays.length; i++) {
            if (i == targetIndex) continue;
            weight[++count] = ASMath.avg(arrays[i]);
        }
        return weight;
    }

    /**
     * 对一份数据生成权重数组。
     *
     * @param targetIndex  目标值，也是需要被排除在外的字段编号，如果设置为-1，代表不排除任何数值编号。
     * @param doubleMatrix 需要被进行权重数组生成的矩阵数组矩阵
     * @param ByLine       如果设置为true，则将会按照每一行生成权重值
     * @return 生成了权重数值的数组，其每一个元素代表了一列的数值
     */
    public static double[] generateWeight(int targetIndex, DoubleMatrix doubleMatrix, boolean ByLine) {
        double[] weight;
        double[][] arrays = ByLine ? doubleMatrix.toArrays() : doubleMatrix.transpose().toArrays();
        weight = new double[arrays.length - 1];
        int count = -1;
        int i = 0;
        while (i < arrays.length) {
            if (i == targetIndex) continue;
            weight[++count] = ASMath.avg(arrays[i++]);
        }
        return weight;
    }

    /**
     * 将一个权重值进行一次梯度下降
     *
     * @param weight       需要被进行梯度下降的权重值，该权重值会变化
     * @param length       需要下降的前 length 个元素
     * @param learningRate 梯度下降时需要的学习率
     * @param lossFunction 损失函数的代价数值 也是要进梯度下降的目标，需要将此数值降低到最小
     * @return 更改之后的权重值
     */
    public static double[] gradientDescent(double[] weight, int length, double learningRate, double lossFunction) {
        for (int i = 0; i < length; i++) {
            double v = weight[i];
            weight[i] = v - learningRate * (lossFunction / v);
        }
        return weight;
    }

    /**
     * 将一个任意类型的数组反转
     *
     * @param arr 需要被反转的函数，注意反转操作会作用于该数组对象上。
     * @param <T> 数组中的元素类型
     * @return 被反转之后的数组对象
     */
    public static <T> T[] arrayReverse(T[] arr) {
        for (int left = 0, right = arr.length - 1; left < right; left++, right--) {
            T temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        return arr;
    }

    /**
     * 将一个任意类型的数组反转
     *
     * @param arr 需要被反转的函数，注意反转操作会作用于该数组对象上。
     * @return 被反转之后的数组对象
     */
    public static int[] arrayReverse(int[] arr) {
        for (int left = 0, right = arr.length - 1; left < right; left++, right--) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        return arr;
    }

    /**
     * 将一个任意类型的数组反转
     *
     * @param arr 需要被反转的函数，注意反转操作会作用于该数组对象上。
     * @return 被反转之后的数组对象
     */
    public static double[] arrayReverse(double[] arr) {
        for (int left = 0, right = arr.length - 1; left < right; left++, right--) {
            double temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        return arr;
    }

    /**
     * 矩阵方框模糊算法
     *
     * @param mat 需要被模糊的矩阵对象
     * @return 模糊之后的新矩阵对象
     */
    public static Color[][] boxBlur(Color[][] mat) {
        int y = -1;
        Color[][] res = new Color[mat.length][mat[0].length];
        for (Color[] doubles : mat) {
            Color[] nowRes = res[++y];
            for (int x = 0; x < doubles.length; x++) {
                // 获取到周围点的平均值
                if (y == 0) {
                    Color[] next = mat[y + 1];
                    int nextIndex = x + 1;
                    // 代表是第一行
                    if (x == 0) {
                        boxBlurAVG1(doubles, nowRes, x, next, nextIndex);
                    } else if (x == doubles.length - 1) {
                        Color leftColor = doubles[x - 1];
                        Color lowLeftColor = next[x - 1];
                        Color lowColor = next[x];
                        // 代表是最后一列 获取到左与下的avg
                        nowRes[x] = new Color(
                                (int) ASMath.avg(
                                        leftColor.getRed(), lowLeftColor.getRed(), lowColor.getRed()
                                ),
                                (int) ASMath.avg(
                                        leftColor.getGreen(), lowLeftColor.getGreen(), lowColor.getGreen()
                                ),
                                (int) ASMath.avg(
                                        leftColor.getBlue(), lowLeftColor.getBlue(), lowColor.getBlue()
                                )
                        );
                    } else {
                        // 仅仅是第一行 因此只需要将当前的值更改为左右下的平均值
                        Color leftColor = doubles[nextIndex];
                        Color lowLeftColor = next[nextIndex];
                        Color lowRightColor = next[x - 1];
                        Color rightColor = doubles[x - 1];
                        nowRes[x] = new Color(
                                (int) ASMath.avg(
                                        leftColor.getRed(), rightColor.getRed(), lowLeftColor.getRed(), next[x].getRed(), lowRightColor.getRed()
                                ),
                                (int) ASMath.avg(
                                        leftColor.getGreen(), rightColor.getGreen(), lowLeftColor.getGreen(), next[x].getGreen(), lowRightColor.getGreen()
                                ),
                                (int) ASMath.avg(
                                        leftColor.getBlue(), rightColor.getBlue(), lowLeftColor.getBlue(), next[x].getBlue(), lowRightColor.getBlue()
                                )
                        );
                    }
                } else if (y == mat.length - 1) {
                    Color[] back = mat[y - 1];
                    int backIndex = x - 1;
                    // 代表最后一行
                    if (x == 0) {
                        // 代表是第一列 获取右与上的avg
                        Color rightColor = doubles[x + 1];
                        Color upRightColor = back[x + 1];
                        Color upColor = back[x];
                        nowRes[x] = new Color(
                                (int) ASMath.avg(
                                        rightColor.getRed(), upRightColor.getRed(), upColor.getRed()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getGreen(), upRightColor.getGreen(), upColor.getGreen()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getBlue(), upRightColor.getBlue(), upColor.getBlue()
                                )
                        );
                    } else if (x == doubles.length - 1) {
                        // 代表是最后一列 获取到左与上的avg
                        boxBlurAVG1(doubles, nowRes, x, back, backIndex);
                    } else {
                        // 仅仅是第一行 因此只需要将当前的值更改为左右上的平均值
                        Color rightColor = doubles[x + 1];
                        Color leftColor = back[x + 1];
                        nowRes[x] = new Color(
                                (int) ASMath.avg(
                                        rightColor.getRed(), leftColor.getRed(), back[x].getRed(), back[backIndex].getRed()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getGreen(), leftColor.getGreen(), back[x].getGreen(), back[backIndex].getGreen()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getBlue(), leftColor.getBlue(), back[x].getBlue(), back[backIndex].getBlue()
                                )
                        );
                    }
                } else {
                    // 代表是中间的行
                    Color[] next = mat[y + 1];
                    Color[] back = mat[y - 1];
                    int nextIndex = x + 1;
                    int backIndex = x - 1;
                    if (x == 0) {
                        // 代表是第一列 获取右与上下的avg
                        boxBlurAVG2(doubles, nowRes, x, next, back, nextIndex);
                    } else if (x == doubles.length - 1) {
                        // 代表是最后一列 获取到左与上下的avg
                        boxBlurAVG2(doubles, nowRes, x, next, back, backIndex);
                    } else {
                        // 仅仅是第一行 因此只需要将当前的值更改为左右上下的平均值
                        Color rightColor = doubles[nextIndex];
                        Color lowRightColor = next[nextIndex];
                        Color upRightColor = back[nextIndex];
                        Color leftColor = doubles[backIndex];
                        Color lowLeftColor = next[backIndex];
                        Color upLeftColor = back[backIndex];
                        Color lowColor = next[x];
                        Color upColor = back[x];
                        nowRes[x] = new Color(
                                (int) ASMath.avg(
                                        rightColor.getRed(), leftColor.getRed(),
                                        lowLeftColor.getRed(), lowColor.getRed(), lowRightColor.getRed(),
                                        upLeftColor.getRed(), upColor.getRed(), upRightColor.getRed()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getGreen(), leftColor.getGreen(),
                                        lowLeftColor.getGreen(), lowColor.getGreen(), lowRightColor.getGreen(),
                                        upLeftColor.getGreen(), upColor.getGreen(), upRightColor.getGreen()
                                ),
                                (int) ASMath.avg(
                                        rightColor.getBlue(), leftColor.getBlue(),
                                        lowLeftColor.getBlue(), lowColor.getBlue(), lowRightColor.getBlue(),
                                        upLeftColor.getBlue(), upColor.getBlue(), upRightColor.getBlue()
                                )
                        );
                    }
                }
            }
        }
        return res;
    }

    private static void boxBlurAVG2(Color[] doubles, Color[] nowRes, int x, Color[] next, Color[] back, int nextIndex) {
        Color rightColor = doubles[nextIndex];
        Color lowRightColor = next[nextIndex];
        Color upRightColor = back[nextIndex];
        Color lowColor = next[x];
        Color upColor = back[x];
        nowRes[x] = new Color(
                (int) ASMath.avg(
                        rightColor.getRed(), lowRightColor.getRed(), lowColor.getRed(), upRightColor.getRed(), upColor.getRed()
                ),
                (int) ASMath.avg(
                        rightColor.getGreen(), lowRightColor.getGreen(), lowColor.getGreen(), upRightColor.getGreen(), upColor.getGreen()
                ),
                (int) ASMath.avg(
                        rightColor.getBlue(), lowRightColor.getBlue(), lowColor.getBlue(), upRightColor.getBlue(), upColor.getBlue()
                )
        );
    }

    private static void boxBlurAVG1(Color[] doubles, Color[] nowRes, int x, Color[] back, int backIndex) {
        Color leftColor = doubles[backIndex];
        Color upLeftColor = back[backIndex];
        Color upColor = back[x];
        nowRes[x] = new Color(
                (int) ASMath.avg(
                        leftColor.getRed(), upLeftColor.getRed(), upColor.getRed()
                ),
                (int) ASMath.avg(
                        leftColor.getGreen(), upLeftColor.getGreen(), upColor.getGreen()
                ),
                (int) ASMath.avg(
                        leftColor.getBlue(), upLeftColor.getBlue(), upColor.getBlue()
                )
        );
    }

    /**
     * 将RGBA的四个数值转换成一个数值形式的 ARGB
     *
     * @param red   红色像素值
     * @param green 绿色像素值
     * @param blue  蓝色像素值
     * @return ARGB颜色值
     */
    public static int rgbaTOIntRGBA(int red, int green, int blue) {
        return (((red << 0b1000) + green) << 0b1000) + blue;
    }

    /**
     * 将一个灰度值与一个透明度转换成为数值形式的 ARGB
     *
     * @param gray 需要被转换的灰度数值
     * @return ARGB颜色值
     */
    public static int grayTORGB(int gray) {
        return (((gray << 0b1000) + gray) << 0b1000) + gray;
    }
}
