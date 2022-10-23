package zhao.algorithmMagic.utils.dataContainer;

/**
 * Java类于 2022/10/17 09:33:16 创建
 *
 * @author 4
 */
public class TwoNameAndValue extends NameAndValue {
    public final String Name2;

    public TwoNameAndValue(String name, String name2, double value) {
        super(name, value);
        this.Name2 = name2;
    }
}
