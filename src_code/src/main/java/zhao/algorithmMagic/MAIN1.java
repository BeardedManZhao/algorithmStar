package zhao.algorithmMagic;

import zhao.algorithmMagic.operands.table.*;

public class MAIN1 {

    public static void println(Cell<?> zhao1, Cell<?> zhao2) {
        System.out.print("zhao1的数据 = " + zhao1);
        System.out.print("\tzhao2的数据 = " + zhao2);
        System.out.println("\t是否为同一内存 = " + (zhao1 == zhao2));
    }

    public static void main(String[] args) {
        // 手动创建出一个 SDF 对象
        final DataFrame dataFrame = SFDataFrame.select(
                SingletonSeries.parse("id", "name"), 1
        );
        // 添加一行数据 并查看
        dataFrame.insert("1", "zhao").show();
        // 使用 单例单元格 创建字符串 zhao
        // TODO 注：$_String 与 $ 函数的作用差不多，不过就是不会隐式转换为数值类型
        final Cell<?> zhao1 = SingletonCell.$_String("zhao");
        // 从 df 中查询出 zhao
        final Cell<?> zhao2 = dataFrame.select("name").getCell(0);
        // 查看两者的数据 以及 是否为同一个内存空间的数据
        println(zhao1, zhao2);

        // 调用单例池清理函数 TODO 这个时候 单例池中的数据将会被清理
        SingletonCell.clearSHM();
        // 因此这个时候再创建 zhao 就不能通过但历史获取到原先的数据对象了，而是一个新对象
        final Cell<?> zhao3 = SingletonCell.$("zhao");
        // 再一次创建字符串 zhao 然后查看两者的关系
        println(zhao3, zhao2);
    }
}