package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.distanceAlgorithm.CosineDistance;
import zhao.algorithmMagic.operands.vector.DoubleVector;
import zhao.algorithmMagic.operands.vector.IntegerVector;
import zhao.algorithmMagic.operands.vector.Vector;

public class MAIN1 {
    public static void main(String[] args) {
        // 打开日志输出 TODO 这是可选的，如果您不想要无用的计算日志，您可以直接在这里设置为false或是不进行该参数的设置，该参数默认为false
        OperationAlgorithmManager.PrintCalculationComponentLog = true;
        CosineDistance<Vector<DoubleVector, IntegerVector>> zhao = CosineDistance.getInstance("zhao");
        double trueDistance = zhao.getTrueDistance(DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0), DoubleVector.parse(1.0, 2.0, 3.0, 4.0, 5.0));
        System.out.println(trueDistance);
    }
}