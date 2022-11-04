# Integrator-Monotonic Learning

- 切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrator%20Monotonic%20Learning-Chinese.md)

## introduce

Created in version 1.1

The integrator is applicable to model calculation. Through a large number of data samples, it can automatically perform
monotonic incremental operation with the set initial model and incremental interval. In version 1.1, when the integrator
is put into use, it needs to first implement the initiator interface corresponding to the integrator. The main thing in
the initiator interface is to set the initial model, The model variables included in the model will iterate over the
calculation results in the monotonically increasing interval, and save the results to a set. The result set is used to
match its correct results, take the most similar result value, and obtain the model variable value corresponding to the
value through the hash index.

## Example of use

The use of the integrator requires an initiator named "IncrementalLearningLauncher", which is an interface, in which the
main task is to set the logic of the initial model, through which to check the similarity of the result values, and
finally obtain the most appropriate θ Numerical value, to build the most appropriate model.

```java
import zhao.algorithmMagic.integrator.IncrementalLearning;
import zhao.algorithmMagic.integrator.iauncher.IncrementalLearningLauncher;
import zhao.algorithmMagic.integrator.iauncher.Launcher;
import zhao.algorithmMagic.operands.vector.DoubleVector;

public class MAIN1 {
    public static void main(String[] args) {
        // Configure the vector sequence of salary and age. This time, we will find the relationship between money and age
        DoubleVector age = DoubleVector.parse(18, 19, 20, 30, 50);
        DoubleVector money = DoubleVector.parse(18000, 19000, 20000, 30000, 50000);

        // Get to Integrator
        IncrementalLearning incrementalLearning = new IncrementalLearning("A", new IncrementalLearningLauncher() {
            // Set the initial model. The model here is money (Yn)= θ ($) * age (Xn) θ Is the protagonist of monotonic computation
            @Override
            public double run(double Yn, double Xn, double $) {
                return $ * Xn;
            }

            @Override
            public Launcher<?> expand() {
                return this;
            }
        });

        incrementalLearning
                // Set the θ Increasing range of
                .setStartingValue(1).setTerminationValue(10000)
                // Set the monotone increment parameter of the integrator. The first parameter is the increment mode (use equal difference increment (false) or equal proportion increment (true)). The second parameter is the increment step.
                // In monotonous learning θ Add or multiply the incremental step value for incremental learning
                .setIncrementalParameter(true, 10);

        // Calculate the relationship between salary and age
        System.out.println("工资 约为 年龄 * " + incrementalLearning.run(money, age));
    }
}
```

<hr>

- 切换至 [中文文档](https://github.com/BeardedManZhao/algorithmStar/blob/main/KnowledgeDocument/Integrator%20Monotonic%20Learning-Chinese.md)
