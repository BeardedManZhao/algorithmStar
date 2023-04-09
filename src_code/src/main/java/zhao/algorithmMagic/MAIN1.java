package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.core.ASDynamicLibrary;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        System.out.println(OperationAlgorithmManager.VERSION);
        if (args.length > 0) {
            ASDynamicLibrary.addDllDir(new File(args[0]));
            System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
        } else {
            System.out.println("感谢您的使用。");
        }
    }
}
