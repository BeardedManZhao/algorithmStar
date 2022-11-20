# 哈希集合分类集成器

- 切换至 [English document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Hash%20Classification%20Integrator.md)

## 介绍

创建于 1.2 版本

针对具有明确的类别规划与明确特征的数据集进行分类的集成器，适用于大批量的数据分类操作，集成器在运行时会分析类别的特征与每一个数据的特征。

- 首先集成器会将所有的类别名称以Hash存储，并将该Hash与一个集合进行关联，被关联的集合中包含的都是该类别的特征。

- 迭代所有包含特征的数据，将每一个数据的特征进行子集匹配，如果当前A数据的子集中与类别AK的特征集合匹配的上，则将当前的数据A，归纳到类别AK的Hash值对应的集合中。

## Example of use

在该集成器中运行之后会返回一个数据集，数据集是一个HashMap，其中的key就是类别，value就是该类别下的每一个数据所组成的集合。

该集成器的运行有两种模式，分别是单类别模式与多类别模式，两种模式的配置在启动器中进行配置，该集成器所对应的启动器为“HashClassificationLauncher”。

### Single category mode

首先展示的是单类别启动器的实现，以及哈希分类集成器的使用示例。

- 单类别模式中，在启动器里配置的待匹配数据集的每一个元素，只会属于一个类别，纵使其有多个符合的类别特征，也只会符合一个。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.HashClassificationIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.HashClassificationLauncher;
import zhao.algorithmMagic.utils.dataContainer.SetAndValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MAIN1 {
    public static void main(String[] args) {
        // 定义三份数据，有不同的数据特征
        SetAndValue<String> stringSetAndValue1 = new SetAndValue<>("兔子", "会动", "有毛");
        SetAndValue<String> stringSetAndValue2 = new SetAndValue<>("仓鼠", "会动", "有毛");
        SetAndValue<String> stringSetAndValue3 = new SetAndValue<>("Tom", "会动", "有毛", "会玩手机");
        // 定义两个类别
        HashMap<String, HashSet<String>> hashMap = new HashMap<>();
        hashMap.put("人类", new HashSet<>(Arrays.asList("会动", "有毛", "会玩手机")));
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
                return false;
            }

            // 配置需要被分类的数据集
            @Override
            public List<SetAndValue<String>> CategorizedData() {
                return Arrays.asList(stringSetAndValue1, stringSetAndValue2, stringSetAndValue3);
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
```

- 运行结果展示

```
[INFO][H][22-11-05:09]]Single category >>> Data [兔子] belongs to category = [动物]
[INFO][H][22-11-05:09]]Single category >>> Data [仓鼠] belongs to category = [动物]
[INFO][H][22-11-05:09]]Single category >>> Data [Tom] belongs to category = [人类]
人类 类别  ->  [Tom]
动物 类别  ->  [兔子, 仓鼠]
```

### Multi category mode

这里展示的是多类别启动器的实现，以及哈希分类集成器的使用示例。

- 多类别模式中，如果一个数据A的特征集合，包含AK与AS两种类别的特征集合，那么A数据属于AK同时也属于AS。

```java
package zhao.algorithmMagic;

import zhao.algorithmMagic.lntegrator.HashClassificationIntegrator;
import zhao.algorithmMagic.lntegrator.launcher.HashClassificationLauncher;
import zhao.algorithmMagic.utils.dataContainer.SetAndValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MAIN1 {
    public static void main(String[] args) {
        // 定义三份数据，有不同的数据特征
        SetAndValue<String> stringSetAndValue1 = new SetAndValue<>("兔子", "会动", "有毛");
        SetAndValue<String> stringSetAndValue2 = new SetAndValue<>("仓鼠", "会动", "有毛");
        SetAndValue<String> stringSetAndValue3 = new SetAndValue<>("Tom", "会动", "有毛", "会玩手机");
        // 定义三个类别
        HashMap<String, HashSet<String>> hashMap = new HashMap<>();
        hashMap.put("人类", new HashSet<>(Arrays.asList("会动", "有毛", "会玩手机")));
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
                return Arrays.asList(stringSetAndValue1, stringSetAndValue2, stringSetAndValue3);
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
```

- 运行结果展示

```
[INFO][H][22-11-05:09]]MultiClassification >>> Data [兔子] belongs to category = [动物]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [仓鼠] belongs to category = [动物]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [Tom] belongs to category = [人类]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [Tom] belongs to category = [动物]
人类 类别  ->  [Tom]
动物 类别  ->  [兔子, 仓鼠, Tom]
```

<hr>

- 切换至 [English document](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Hash%20Classification%20Integrator.md)
