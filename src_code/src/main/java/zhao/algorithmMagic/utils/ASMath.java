package zhao.algorithmMagic.utils;

/**
 * Java类于 2022/10/10 15:30:49 创建
 * 数学工具包
 *
 * @author 4
 */
public final class ASMath {
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
}
