package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        CosineDistance<DoubleVector> cosineDistance = CosineDistance.getInstance("zhao");
        DoubleVector parse1 = DoubleVector.parse(1, 2, 3, 4, 5);
        DoubleVector parse2 = DoubleVector.parse(10, 20, 30, 40, 50);
        System.out.println(parse1 + " " + parse2);
        System.out.println(parse1.moduleLength() + " " + parse2.moduleLength());
        System.out.println(cosineDistance.getTrueDistance(parse1, parse2));
    }
}