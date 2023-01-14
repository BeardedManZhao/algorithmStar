# Hash Classification Integrator

-

切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Hash%20Classification%20Integrator-Chinese.md)

## introduce

Created in version 1.2

An integrator that classifies data sets with clear category planning and characteristics is suitable for mass data
classification operations. The integrator will analyze the characteristics of categories and each data at runtime.

- First, the integrator will store all category names as Hash, and associate the Hash with a collection that contains
  the characteristics of the category.


- Iterating all data containing features, matching subsets of the features of each data. If the subset of the current A
  data matches the feature set of category AK, the current data A is summarized into the set corresponding to the Hash
  value of category AK.

## Example of use

After running in the integrator, a dataset will be returned. The dataset is a HashMap, where key is a category and value
is a collection of each data under the category.

The integrator operates in two modes: single class mode and multi class mode. The configurations of the two modes are
configured in the initiator. The initiator corresponding to the integrator is HashClassificationLauncher.

### Single category mode

First, we will show the implementation of the single category initiator and the use example of the hash classification
integrator.

- In the single category mode, each element of the dataset to be matched configured in the initiator will belong to only
  one category. Even if it has multiple matching category characteristics, it will only match one.

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
        // Define three pieces of data with different data characteristics
        SetAndValue<String> stringSetAndValue1 = new SetAndValue<>("兔子", "会动", "有毛");
        SetAndValue<String> stringSetAndValue2 = new SetAndValue<>("仓鼠", "会动", "有毛");
        SetAndValue<String> stringSetAndValue3 = new SetAndValue<>("Tom", "会动", "有毛", "会玩手机");
        // Define two categories
        HashMap<String, HashSet<String>> hashMap = new HashMap<>();
        hashMap.put("人类", new HashSet<>(Arrays.asList("会动", "有毛", "会玩手机")));
        hashMap.put("动物", new HashSet<>(Arrays.asList("会动", "有毛")));

        // Implement classification initiator
        HashClassificationLauncher<String> hashClassificationLauncher = new HashClassificationLauncher<String>() {
            @Override
            public HashClassificationLauncher<String> expand() {
                return this;
            }

            // Configure Category Characteristic Datasets
            @Override
            public HashMap<String, HashSet<String>> LoadCategoryLabels() {
                return hashMap;
            }

            // Configure the classification mode. If it is true, it means that a certain data is allowed to belong to multiple categories
            @Override
            public boolean MultiClassificationMode() {
                return false;
            }

            // Configure the dataset to be classified
            @Override
            public List<SetAndValue<String>> CategorizedData() {
                return Arrays.asList(stringSetAndValue1, stringSetAndValue2, stringSetAndValue3);
            }
        };

        // Mount the initiator into the integrator
        HashClassificationIntegrator<String> stringHashClassificationIntegrator = new HashClassificationIntegrator<>("H", hashClassificationLauncher);
        // Start sorting
        HashMap<String, List<String>> stringListHashMap = stringHashClassificationIntegrator.runAndReturnValueSet();
        // Print data for all categories
        for (String s : hashMap.keySet()) {
            System.out.println(s + " 类别  ->  " + stringListHashMap.get(s));
        }
    }
}
```

- Display of operation results

```
[INFO][H][22-11-05:09]]Single category >>> Data [兔子] belongs to category = [动物]
[INFO][H][22-11-05:09]]Single category >>> Data [仓鼠] belongs to category = [动物]
[INFO][H][22-11-05:09]]Single category >>> Data [Tom] belongs to category = [人类]
人类 类别  ->  [Tom]
动物 类别  ->  [兔子, 仓鼠]
```

### Multi category mode

The implementation of multiCategory initiators and the use example of hash classification integrators are shown here.

- In the multiCategory mode, if a feature set of data A contains feature sets of AK and AS, then data A belongs to both
  AK and AS.

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
        // Define three pieces of data with different data characteristics
        SetAndValue<String> stringSetAndValue1 = new SetAndValue<>("兔子", "会动", "有毛");
        SetAndValue<String> stringSetAndValue2 = new SetAndValue<>("仓鼠", "会动", "有毛");
        SetAndValue<String> stringSetAndValue3 = new SetAndValue<>("Tom", "会动", "有毛", "会玩手机");
        // Define two categories
        HashMap<String, HashSet<String>> hashMap = new HashMap<>();
        hashMap.put("人类", new HashSet<>(Arrays.asList("会动", "有毛", "会玩手机")));
        hashMap.put("动物", new HashSet<>(Arrays.asList("会动", "有毛")));

        // Implement classification initiator
        HashClassificationLauncher<String> hashClassificationLauncher = new HashClassificationLauncher<String>() {
            @Override
            public HashClassificationLauncher<String> expand() {
                return this;
            }

            // Configure Category Characteristic Datasets
            @Override
            public HashMap<String, HashSet<String>> LoadCategoryLabels() {
                return hashMap;
            }

            // Configure the classification mode. If it is true, it means that a certain data is allowed to belong to multiple categories
            @Override
            public boolean MultiClassificationMode() {
                return true;
            }

            // Configure the dataset to be classified
            @Override
            public List<SetAndValue<String>> CategorizedData() {
                return Arrays.asList(stringSetAndValue1, stringSetAndValue2, stringSetAndValue3);
            }
        };

        // Mount the initiator into the integrator
        HashClassificationIntegrator<String> stringHashClassificationIntegrator = new HashClassificationIntegrator<>("H", hashClassificationLauncher);
        // Start sorting
        HashMap<String, List<String>> stringListHashMap = stringHashClassificationIntegrator.runAndReturnValueSet();
        // Print data for all categories
        for (String s : hashMap.keySet()) {
            System.out.println(s + " 类别  ->  " + stringListHashMap.get(s));
        }
    }
}
```

- Display of operation results

```
[INFO][H][22-11-05:09]]MultiClassification >>> Data [兔子] belongs to category = [动物]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [仓鼠] belongs to category = [动物]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [Tom] belongs to category = [人类]
[INFO][H][22-11-05:09]]MultiClassification >>> Data [Tom] belongs to category = [动物]
人类 类别  ->  [Tom]
动物 类别  ->  [兔子, 仓鼠, Tom]
```

<hr>

-

切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Hash%20Classification%20Integrator-Chinese.md)
