package io.github.beardedManZhao.algorithmStar.utils;

import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;

import java.io.BufferedReader;
import java.io.IOException;
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
     * 检查字符串是否符合数值类型
     *
     * @param data 需要被检查的字符串
     * @return 检查之后的结果数值。
     */
    public static boolean checkIsNum(String data) {
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if ((c < 48 && c != '.') || c > 57) return false;
        }
        return true;
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
     * 将一个字符串数据按照指定的字符进行拆分，拆分出很多数据。
     *
     * @param data 需要被拆分的字符串数据
     * @param c    分隔符
     * @return 拆分之后的字符串数据
     */
    public static String[] splitByChar(String data, char c) {
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int length = data.length();
        for (int i = 0; i < length; i++) {
            char at = data.charAt(i);
            if (at == c) {
                if (stringBuilder.length() != 0) {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else {
                stringBuilder.append(at);
            }
        }
        if (stringBuilder.length() != 0) {
            arrayList.add(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
        return arrayList.toArray(new String[0]);
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
     * 替换一个字符串中首个出现的字符 old 变为新字符
     *
     * @param s       需要被替换的字符串
     * @param old     需要被替换的字符
     * @param newChar 替换之后的字符串
     * @return 替换之后的新字符串
     */
    public static String replaceCharFirst(String s, char old, char newChar) {
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == old) {
                charArray[i] = newChar;
                return new String(charArray);
            }
        }
        return s;
    }

    /**
     * 将一个数组转换成为makeDown中的一行字符串形式的数据
     *
     * @param ints 需要被转换的字符串
     * @return 转换之后的字符串数据
     */
    public static String arrayToMarkdownStr(int[] ints) {
        StringBuilder stringBuilder = new StringBuilder(ints.length << 1);
        for (int anInt : ints) {
            stringBuilder.append(anInt).append('\t');
        }
        return stringBuilder.toString();
    }

    /**
     * 将一个数组转换成为makeDown中的一行字符串形式的数据
     *
     * @param ints 需要被转换的字符串
     * @return 转换之后的字符串数据
     */
    public static String arrayToMarkdownStr(double[] ints) {
        StringBuilder stringBuilder = new StringBuilder(ints.length << 1);
        for (double anInt : ints) {
            stringBuilder.append(anInt).append('\t');
        }
        return stringBuilder.toString();
    }

    /**
     * 将数据流中的数据转换成为整形数值矩阵对象。
     *
     * @param bufferedReader 包含数据的字符输入流
     * @param length         需要解析的行数量
     * @param sep            解析时需要使用的分隔符。
     * @return 经过解析之后返回的结果二维数组对象。
     */
    public static int[][] strToIntMat(BufferedReader bufferedReader, int length, char sep) {
        try {
            int[][] lines = new int[length][];
            int index = -1;
            while (bufferedReader.ready()) {
                String[] strings = ASStr.splitByChar(bufferedReader.readLine(), sep);
                int[] row = new int[strings.length];
                int index1 = -1;
                for (String s : strings) {
                    row[++index1] = Integer.parseInt(s);
                }
                lines[++index] = row;
            }
            return lines;
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将数据流中的数据转换成为整形数值矩阵对象。
     *
     * @param bufferedReader 包含数据的字符输入流
     * @param length         需要解析的行数量
     * @param sep            解析时需要使用的分隔符。
     * @return 经过解析之后返回的结果二维数组对象。
     */
    public static double[][] strToDoubleMat(BufferedReader bufferedReader, int length, char sep) {
        try {
            double[][] lines = new double[length][];
            int index = -1;
            while (bufferedReader.ready()) {
                String[] strings = ASStr.splitByChar(bufferedReader.readLine(), sep);
                double[] row = new double[strings.length];
                int index1 = -1;
                for (String s : strings) {
                    row[++index1] = Integer.parseInt(s);
                }
                lines[++index] = row;
            }
            return lines;
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将数据流中的数据转换成为整形数值矩阵对象。
     *
     * @param bufferedReader 包含数据的字符输入流
     * @param sep            解析时需要使用的分隔符。
     * @return 经过解析之后返回的结果二维数组对象。
     */
    public static int[][] strToIntMat(BufferedReader bufferedReader, char sep) {
        try {
            ArrayList<int[]> lines = new ArrayList<>();
            while (bufferedReader.ready()) {
                String[] strings = ASStr.splitByChar(bufferedReader.readLine(), sep);
                int[] row = new int[strings.length];
                int index1 = -1;
                for (String s : strings) {
                    row[++index1] = Integer.parseInt(s);
                }
                lines.add(row);
            }
            return lines.toArray(new int[0][]);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }

    /**
     * 将数据流中的数据转换成为整形数值矩阵对象。
     *
     * @param bufferedReader 包含数据的字符输入流
     * @param sep            解析时需要使用的分隔符。
     * @return 经过解析之后返回的结果二维数组对象。
     */
    public static double[][] strToDoubleMat(BufferedReader bufferedReader, char sep) {
        ArrayList<double[]> lines = new ArrayList<>();
        try {
            while (bufferedReader.ready()) {
                String[] strings = ASStr.splitByChar(bufferedReader.readLine(), sep);
                double[] row = new double[strings.length];
                int index1 = -1;
                for (String s : strings) {
                    row[++index1] = Integer.parseInt(s);
                }
                lines.add(row);
            }
            return lines.toArray(new double[0][]);
        } catch (IOException e) {
            throw new OperatorOperationException(e);
        }
    }
/*

    /**
     * 将一个数组直接反转
     *
     * @param doubles 需要被反转的数组
     * @return 反转之后的数组的结果
     * /
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
*/
}
