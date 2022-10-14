package zhao.algorithmMagic.utils;

/**
 * Java类于 2022/10/11 19:04:22 创建
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
        for (char c1 : StringToBeCounted.toCharArray()) {
            if (c1 == c) ++res;
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
     * @param str 被转化的字符串
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

    public static final class DataPackage {
        char aCharFromString;
        String string;

        public DataPackage(String string, char aCharFromString) {
            this.aCharFromString = aCharFromString;
            this.string = string;
        }
    }
}
