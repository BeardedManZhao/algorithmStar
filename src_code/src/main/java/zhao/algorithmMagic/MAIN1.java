package zhao.algorithmMagic;

import zhao.algorithmMagic.core.AlgorithmStar;
import zhao.algorithmMagic.core.HelpFactory;

public class MAIN1 {
    public static void main(String[] args) {
        // 获取帮助信息工厂类
        final HelpFactory helpFactory = AlgorithmStar.helpFactory();
        // 下载帮助文档 到 C:\Users\zhao\Desktop\fsdownload 目录中
        System.out.println("帮助文档保存到：" + helpFactory.saveHelpFile(HelpFactory.ALL, "C:\\Users"));
    }
}
