package zhao.algorithmMagic.algorithm.distanceAlgorithm;

public interface DistanceAlgorithm_C extends DistanceAlgorithm {
    /**
     * 使用C编译出的动态库的方式计算两个样本之间的真实距离，并将计算结果返回，如果没有DLL库该函数将抛出异常。
     * <p>
     * Use the dynamic library compiled by C to calculate the real distance between two samples and return the calculation result. If there is no DLL library, the function will throw an exception.
     *
     * @param doubles1 数组序列1
     * @param doubles2 数组序列2
     * @return ...
     */
    double getTrueDistance_C(double[] doubles1, double[] doubles2);

    /**
     * 使用C编译出的动态库的方式计算两个样本之间的真实距离，并将计算结果返回，如果没有DLL库该函数将抛出异常。
     * <p>
     * Use the dynamic library compiled by C to calculate the real distance between two samples and return the calculation result. If there is no DLL library, the function will throw an exception.
     *
     * @param ints1 数组序列1
     * @param ints2 数组序列2
     * @return ...
     */
    double getTrueDistance_C(int[] ints1, int[] ints2);
}
