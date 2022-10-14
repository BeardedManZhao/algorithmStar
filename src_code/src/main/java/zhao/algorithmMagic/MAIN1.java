package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.HammingDistance;
import zhao.algorithmMagic.operands.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.IntegerCoordinateMany;

/**
 * Java类于 2022/10/11 10:23:53 创建
 *
 * @author 4
 */
public class MAIN1 {
    public static void main(String[] args) {
        HammingDistance<IntegerCoordinateMany, DoubleCoordinateMany> h = HammingDistance.getInstance("h");
        System.out.println(h.getMinimumNumberOfReplacements("zhao", "zha1"));
    }
}
