package zhao.algorithmMagic;

import zhao.algorithmMagic.integrator.HashClassificationIntegrator;
import zhao.algorithmMagic.integrator.launcher.HashClassificationLauncher;
import zhao.algorithmMagic.utils.dataContainer.SetAndValue;

import java.util.*;

public class MAIN1 {
    public static void main(String[] args) {
        // 定义三份数据，有不同的数据特征
        SetAndValue<String> stringSetAndValue1 = new SetAndValue<>("兔子", "会动", "有毛");
        SetAndValue<String> stringSetAndValue2 = new SetAndValue<>("仓鼠", "会动", "有毛");
        SetAndValue<String> stringSetAndValue3 = new SetAndValue<>("赵", "会动", "有毛", "会玩手机");
        SetAndValue<String> stringSetAndValue4 = new SetAndValue<>("杨", "会动", "有毛", "会玩手机", "是高中生");
        // 定义三个类别
        HashMap<String, HashSet<String>> hashMap = new HashMap<>();
        hashMap.put("人类", new HashSet<>(Arrays.asList("会动", "有毛", "会玩手机")));
        hashMap.put("奴隶", new HashSet<>(Arrays.asList("有毛", "会玩手机", "是高中生")));
        hashMap.put("动物", new HashSet<>(Arrays.asList("会动", "有毛")));

        // 实现分类启动器
        HashClassificationLauncher<String> hashClassificationLauncher = new HashClassificationLauncher<String>() {
            @Override
            public HashClassificationLauncher<String> expand() {
                return this;
            }

            // 配置类别特征数据集
            @Override
            public HashMap<String, HashSet<String>> LoadCategoryLabels() {
                return hashMap;
            }

            // 是否使用多分类模式 如果为true 代表允许某一个数据属于多个类别
            @Override
            public boolean MultiClassificationMode() {
                return true;
            }

            // 配置需要被分类的数据集
            @Override
            public List<SetAndValue<String>> CategorizedData() {
                return Arrays.asList(stringSetAndValue1, stringSetAndValue2, stringSetAndValue3, stringSetAndValue4);
            }
        };

        // 将启动器装载到集成器中
        HashClassificationIntegrator<String> stringHashClassificationIntegrator = new HashClassificationIntegrator<>("H", hashClassificationLauncher);
        // 开始进行分类
        HashMap<String, List<String>> stringListHashMap = stringHashClassificationIntegrator.runAndReturnValueSet();
        // 打印所有的类别的数据
        for (String s : hashMap.keySet()) {
            System.out.println(s + " 类别  ->  " + stringListHashMap.get(s));
        }
    }
}
