package zhao.algorithmMagic.utils;

/**
 * Java类于 2022/10/11 19:04:22 创建
 * <p>
 * 字符串工具类
 *
 * @author 4
 */
public final class ASStr {

    /**
     * 统计某个字符出现的次数
     *
     * @param StringToBeCounted 被统计的字符串
     * @param c                 被计数的字符
     * @return 统计某个字符出现的次数
     */
    public static int count(String StringToBeCounted, char c) {
        int res = 0;
        for (int length = StringToBeCounted.length() - 1; length >= 0; length--) {
            if (StringToBeCounted.charAt(length) == c) ++res;
        }
        return res;
    }

    /**
     * 统计某个字符出现的次数
     *
     * @param StringToBeCounted 被统计的字符串
     * @param s                 被计数的字符
     * @return 统计某个字符出现的次数
     */
    public static int count(String StringToBeCounted, String s) {
        int res = 0;
        int length2 = s.length();
        int length1 = StringToBeCounted.length() + length2 - 1;
        for (int i = 0; i < length1; i += length2) {
            if (StringToBeCounted.substring(i, i + length2).equals(s)) ++res;
        }
        return res;
    }

    /**
     * 将一个字符串映射为一个数值数组
     *
     * @param str    被转化的字符串
     * @param mapper 转化的时候，字符串的映射逻辑实现。
     *               <p>
     *               When converting, the mapping logic of strings is implemented.
     * @return 字符串映射之后的数组
     */
    public static double[] toDoubleArray(String str, Mapper<DataPackage, Double> mapper) {
        int length = str.length();
        double[] v = new double[length];
        for (int i = 0; i < length; i++) {
            v[i] = mapper.run(new DataPackage(str, str.charAt(i)));
        }
        return v;
    }

    /**
     * 将一个字符串的词频向量获取到
     *
     * @param str 被计算的字符串
     * @return 词频数组
     */
    public static double[] toWordFrequencyDoubleArray(String str) {
        return toDoubleArray(str, dataPackage -> (double) count(dataPackage.string, dataPackage.aCharFromString));
    }

    /**
     * 判断一个字符中是否包含某个字符，或某些字符
     *
     * @param str1 被判断的字符串
     * @param c    判断包含的所有字符串
     * @return 是否包含该字符
     */
    public static boolean contains(String str1, char... c) {
        if (c.length < 1) {
            return false;
        } else if (c.length == 1) {
            final char c2 = c[0];
            for (char c1 : str1.toCharArray()) {
                if (c1 == c2) {
                    return true;
                }
            }
        } else {
            for (char c1 : str1.toCharArray()) {
                for (char c2 : c) {
                    if (c1 == c2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将一个数组直接反转
     *
     * @param doubles 需要被反转的数组
     * @return 反转之后的数组的结果
     */
    public static double[] ArrayReverse(double... doubles) {
        int back = doubles.length - 1;
        int length = doubles.length >> 1;
        for (int i = 0; i < length; ++i) {
            double aDouble2 = doubles[back];
            doubles[back--] = doubles[i];
            doubles[i] = aDouble2;
        }
        return doubles;
    }

    public static final class DataPackage {
        final char aCharFromString;
        final String string;

        public DataPackage(String string, char aCharFromString) {
            this.aCharFromString = aCharFromString;
            this.string = string;
        }
    }
}
