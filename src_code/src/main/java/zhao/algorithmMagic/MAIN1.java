package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.EuclideanMetric;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateThree;
import zhao.algorithmMagic.operands.coordinate.IntegerCoordinateThree;

public class MAIN1 {
    public static void main(String[] args) {
        EuclideanMetric<IntegerCoordinateThree, DoubleCoordinateThree> zhao = EuclideanMetric.getInstance("zhao");
        System.out.println(
                zhao.getTrueDistance(
                        new DoubleCoordinateThree(1, 2, 3), new DoubleCoordinateThree(1, 2, 3)
                )
        );
    }
}