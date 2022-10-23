package zhao.algorithmMagic.utils;

import zhao.algorithmMagic.operands.ComplexNumber;

import java.util.HashSet;
import java.util.Set;

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
     * 计算一个数值的阶乘
     *
     * @param d 被计算数值
     * @return 阶乘结果
     */
    public static double Factorial(double d) {
        if (d <= 1) {
            return d;
        } else {
            return d * Factorial(d - 1);
        }
    }

    /**
     * 计算一个数值的阶乘
     *
     * @param i 被计算数值
     * @return 阶乘结果
     */
    public static int Factorial(int i) {
        if (i <= 1) {
            return i;
        } else {
            return i * Factorial(i - 1);
        }
    }

    /**
     * 计算一个数值的阶乘
     *
     * @param i               被计算数值
     * @param FactorialNumber 数值停止阶乘的数值
     * @return 阶乘结果
     */
    public static int Factorial(int i, long FactorialNumber) {
        int res = i;
        for (int i1 = i - 1; i1 > 1 && res <= FactorialNumber; i1--) {
            res *= i;
        }
        return res;
    }

    /**
     * 在n个元素的集合中，取出m个元素，这种取法的组合数量
     *
     * @param n 数据样本的总数
     * @param m 要提取的数据样本数量，不同数量的组合在样本中有很多方式，可以计算出来，m个数据样本在n个数据样本之内的组合
     * @return 排列组合中的具体组合数量。
     */
    public static double CombinationNumber(int n, int m) {
        if (m <= n) {
            return Factorial(n) / ((double) Factorial(m)) * Factorial(n - m);
        } else {
            return 0b11111111111111111111111111111111;
        }
    }

    /**
     * E(X)  = ⁿ∑₁ (probability(Xn) * frequency(Xn in X))
     *
     * @param event 期望运算中有，所使用到的事件条件，为true的时候，代表真事件。
     * @param X     需要计算期望的数列。
     * @return X序列的期望
     */
    public static double Expectation(double[] X, DoubleEvent event) {
        double res = 0;
        for (double Xn : X) {
            // 序列中 Xn 这个数值的出现频率
            int frequency = frequency(Xn, X);
            // 计算出 Xn 在所有整个序列中的概率，真假条件由外界传入
            double probability = probability(X, event) / X.length;
            System.out.println("E(X)  = ⁿ∑₁ (probability(Xn) * frequency(Xn in X)) = ⁿ∑₁ " + probability + " * " + frequency + ")");
            res += probability * frequency;
        }
        return res;
    }

    /**
     * 计算一个数值在一个序列中的概率
     *
     * @param v       被计算的数值
     * @param event   结果为真的条件
     * @param <value> 数值所在序列
     * @return value 中 满足 event 条件的 v 的概率
     */
    public static <value> double probability(value[] v, Event<value> event) {
        int ok = 0;
        for (value value : v) {
            if (event.isComplianceEvents(value)) {
                ok += 1;
            }
        }
        return ok / (double) v.length;
    }

    /**
     * 计算一个数值在一个序列中的概率
     *
     * @param v     被计算的数值
     * @param event 结果为真的条件
     * @return value 中 满足 event 条件的 v 的概率
     */
    public static double probability(double[] v, DoubleEvent event) {
        int ok = 0;
        for (double value : v) {
            if (event.isComplianceEvents(value)) {
                ok += 1;
            }
        }
        return ok / (double) v.length;
    }

    /**
     * 统计一个数值在序列中出现的次数
     * <p>
     * Count the number of times a number appears in a sequence
     *
     * @param value 被统计的数值
     * @param Array 数值所处的序列
     * @return Count the number of times a number appears in a sequence
     */
    public static int frequency(double value, double[] Array) {
        int res = 0;
        for (double v : Array) {
            if (v == value) res++;
        }
        return res;
    }

    /**
     * 计算一个数值的阶乘
     *
     * @param i              被计算的数值
     * @param factorialCount 阶乘的次数，该参数可以对阶乘进行一个限制，用于概率计算等操作
     * @return 阶乘结果
     */
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
     * 对一个数值进行绝对值运算
     *
     * @param num 需要被取绝对值的数值
     * @return num的绝对值
     */
    public static int absoluteValue(int num) {
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
        HashSet<ElementType> hashSet = new HashSet<>(set1.size() + set2.size());
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
     * 将一个数组排序
     *
     * @param doubles 需要排序的数组
     * @param des     排序之后的数组
     * @return 排序之后的数组，注意这里的数组就是传入的数组
     */
    public static double[] sort(boolean des, double... doubles) {
        if (des) {
            for (int i = 0; i < doubles.length; i++) {
                for (int i1 = 0; i1 < doubles.length - i - 1; i1++) {
                    if (doubles[i1] > doubles[i1 + 1]) {
                        double temp = doubles[i1 + 1];
                        doubles[i1 + 1] = doubles[i1];
                        doubles[i1] = temp;
                    }
                }
            }
        } else {
            for (int i = 0; i < doubles.length; i++) {
                for (int i1 = 0; i1 < doubles.length - i - 1; i1++) {
                    if (doubles[i1] < doubles[i1 + 1]) {
                        double temp = doubles[i1 + 1];
                        doubles[i1 + 1] = doubles[i1];
                        doubles[i1] = temp;
                    }
                }
            }
        }
        return doubles;
    }

    /**
     * 计算出两个字符串的并集
     *
     * @param value1 被计算的字符串1
     * @param value2 被计算的字符串2
     * @return 两个字符串的并集
     */
    public static String Union(String value1, String value2) {
        final StringBuilder stringBuilder = new StringBuilder(value1);
        for (char c : value2.toCharArray()) {
            if (!ASStr.contains(value1, c)) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

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
}
