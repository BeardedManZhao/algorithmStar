package zhao.algorithmMagic;

import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.core.ASDynamicLibrary;

import java.io.File;

public class MAIN1 {
    public static void main(String[] args) {
        // 加载动态库目录 TODO 动态库加载之后，在遇到能够使用DLL动态库进行计算的组件时将会使用DLL计算
        ASDynamicLibrary.addDllDir(new File("D:\\MyGitHub\\algorithmStar\\AsLib"));
        // 获取到 algorithmStar 包下载URL
        System.out.println(OperationAlgorithmManager.getAlgorithmStarUrl());
    }
}