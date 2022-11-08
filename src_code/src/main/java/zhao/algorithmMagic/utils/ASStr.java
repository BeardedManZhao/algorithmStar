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
            for (char c1 : str1.toCharArray()) {
                if (c1 == c[0]) {
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
     * 将一个字符串的子字符串提取出来
     *
     * @param string                需要被提取的字符串
     * @param PositiveSequenceFirst 正序开始的第一个字符
     * @param ReverseOrderFirst     倒叙开始的第一个字符
     * @return 正第一个字符与倒第一字符之间的字符串数据
     */
    public static String getSubinterval(String string, char PositiveSequenceFirst, char ReverseOrderFirst) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        int start = 0;
        int end = 0;
        // 正向迭代所有字符
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == PositiveSequenceFirst) {
                // 如果遇到正向首字符就将此字符记录
                start = i;
                break;
            }
        }
        // 逆向迭代所有字符
        for (int i = chars.length - 1; i >= 0; --i) {
            if (chars[i] == ReverseOrderFirst) {
                // 如果遇到逆向首字符就将此字符记录
                end = i;
                break;
            }
        }
        // 返回左右开区间内的字符串
        for (int i = start + 1; i < end; ++i) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 将一个字符串转换为浮点数值
     *
     * @param s 需要被转换的字符串
     * @return 字符串转换的浮点数值
     */
    public static double stringToDouble(String s) {
        int floatRes = 0;
        int intRes = 0;
        boolean isInt = true;
        for (char c : s.trim().toCharArray()) {
            if (c != '.' && c != ' ') {
                // 如果当前不是小数点符号 就直接对数值进行位分配
                if (isInt) {
                    // 如果当前不是小数点符号 就直接将数值归为整数
                    intRes = ASMath.tenfold(intRes) + charToInteger(c);
                } else {
                    // 如果是小数点 就直接将数值归为小数
                    floatRes = ASMath.tenfold(floatRes) + charToInteger(c);
                }
            } else if (c == '.') {
                // 如果是小数点 就切换添加状态
                isInt = false;
            }
        }
        // 计算出来小数点的位数
        int count = floatRes - ASMath.tenfold(ASMath.DividebyTen(floatRes));
        // 计算出来数值本身
        double res = intRes + floatRes / (double) ASMath.PowerOfTen(10, count - 1);
        // 判断是否为负数，如果不是负数直接返回值
        return s.charAt(0) == '-' ? -res : res;
    }

    /**
     * 将一个数值字符传换成一个数值
     *
     * @param c 需要被转换的字符
     * @return 转换之后的数值
     */
    public static int charToInteger(char c) {
        switch (c) {
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case '0':
                return 0;
            default:
                throw new RuntimeException("您在进行字符与数值之间的转换时，由于字符的不正确导致无法成功转换，错误字符：" + c +
                        "\nWhen you are converting characters to numeric values, the conversion cannot be successful due to incorrect characters. Error characters:" + c);
        }
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
