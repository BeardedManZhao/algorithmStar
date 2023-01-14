package zhao.algorithmMagic.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
            Arrays.sort(c);
            for (char c1 : str1.toCharArray()) {
                if (search(c, c1) != -1) return true;
            }
        }
        return false;
    }

    /**
     * 二分查找，查找到目标值所在的索引值
     *
     * @param chars  需要被查找的有序字符串
     * @param target 目标字符
     * @return 目标字符在nums中的索引数值，如果不存在则返回 -1
     */
    public static int search(char[] chars, char target) {
        if (chars[0] != target) {
            // 获取到索引
            int start = 0, end = chars.length;
            int backStart = -1, backEnd = -1;
            // 准备一个循环标记
            while (start < end && (start != backStart || end != backEnd)) {
                backStart = start;
                backEnd = end;
                int mod = start + ((end - start) >> 1);
                // 判断当前数值是否大于根节点的数值
                char c = chars[mod];
                if (target > c) {
                    // 如果大于，代表target 存在于根节点的右边，因此将[start, mod - 1]的索引区间舍弃
                    start = mod;
                } else if (target < c) {
                    // 如果小于，代表在根节点左边，因此将[mod , end] 舍弃
                    end = mod + 1;
                } else {
                    // 如果等于，直接将当前节点索引返回
                    return mod;
                }
            }
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 将一个字符串按照某个连续的字符进行拆分。
     *
     * @param data  需要被拆分的字符串数据
     * @param chars 连续拆分字符组 其中的所有字符都会作为切分符 要求是有序的哦！！
     * @return 字符串按照字符拆分之后的字符串数组
     */
    public static String[] splitBySortChars(String data, char[] chars) {
        ArrayList<String> arrayList = checkAndSB(data);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length(); ++i) {
            char c1 = data.charAt(i);
            if (search(chars, c1) != -1) {
                int length = stringBuilder.length();
                if (length != 0) {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.delete(0, length);
                }
            } else {
                stringBuilder.append(c1);
            }
        }
        if (stringBuilder.length() != 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList.toArray(new String[0]);
    }

    /**
     * 将一个字符串按照某个连续的字符进行拆分。
     *
     * @param data     需要被拆分的字符串数据
     * @param chars    连续拆分字符组 其中的所有字符都会作为切分符 要求是有序的哦！！
     * @param stopWord 通用词列表，在本次
     * @return 字符串按照字符拆分之后的字符串数组
     */
    public static String[] splitBySortChars(String data, char[] chars, HashSet<String> stopWord) {
        if (stopWord == null || stopWord.size() == 0) {
            return splitBySortChars(data, chars);
        } else {
            ArrayList<String> arrayList = checkAndSB(data);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < data.length(); ++i) {
                char c1 = data.charAt(i);
                if (search(chars, c1) != -1) {
                    int length = stringBuilder.length();
                    if (length != 0) {
                        String s = stringBuilder.toString();
                        if (!stopWord.contains(s)) {
                            arrayList.add(s);
                            stringBuilder.delete(0, length);
                        }
                    }
                } else {
                    stringBuilder.append(c1);
                }
            }
            if (stringBuilder.length() != 0) {
                String s = stringBuilder.toString();
                if (!stopWord.contains(s)) {
                    arrayList.add(s);
                }
            }
            return arrayList.toArray(new String[0]);
        }
    }

    /**
     * 检查并构造出一个大小适合的字符串建造者对象
     *
     * @param data 目标字符串
     * @return 字符串建造者
     */
    private static ArrayList<String> checkAndSB(String data) {
        int i1 = data.length() >> 1;
        ArrayList<String> arrayList;
        if (i1 <= 10) {
            arrayList = new ArrayList<>();
        } else {
            arrayList = new ArrayList<>(i1);
        }
        return arrayList;
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
}
