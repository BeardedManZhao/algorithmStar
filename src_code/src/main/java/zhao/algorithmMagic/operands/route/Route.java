package zhao.algorithmMagic.operands.route;

import zhao.algorithmMagic.operands.Operands;
import zhao.algorithmMagic.operands.coordinate.Coordinate;
import zhao.algorithmMagic.operands.vector.DoubleVector;

import java.util.regex.Pattern;

/**
 * 路线接口,路线代表从一个坐标到另一个坐标的数据集合,其中不仅仅包含两个坐标之间的距离,它可以包含两个坐标的任意关系,也可以包含两个坐标之间的联系.
 * <p>
 * Route interface, a route represents a collection of data from one coordinate to another, which not only contains the distance between two coordinates, it can contain any relationship between the two coordinates, and can also contain the connection between the two coordinates.
 *
 * @param <ImplementationType> 实现类型,告知父类自己子类的实现类型,有助于进行类型的转换,灵活性更大.
 *                             <p>
 *                             The implementation type tells the parent class the implementation type of its own subclass, which is helpful for type conversion and more flexible.
 * @param <CoordinateType>     坐标类型,代表始末坐标的实现类.
 *                             <p>
 *                             Coordinate type, representing the implementation class of the start and end coordinates.
 */
public interface Route<ImplementationType, CoordinateType extends Coordinate<?>> extends Operands<ImplementationType> {

    Pattern PATH_SEPARATOR = Pattern.compile("\\s*?->\\s*?");

    /**
     * @return 起始坐标对象
     * <p>
     * starting coordinate object
     */
    CoordinateType getStartingCoordinate();

    /**
     * @return 终止坐标对象
     * <p>
     * end coordinate object
     */
    CoordinateType getEndPointCoordinate();

    /**
     * @return 两坐标之间的向量
     * <p>
     * vector between two coordinates
     */
    DoubleVector toDoubleVector();

    /**
     * @return 始末坐标的维度数量, 用于将判断两个坐标是否符合要求
     * <p>
     * The number of dimensions of the starting and ending coordinates, which is used to judge whether the two coordinates meet the requirements
     */
    int getNumberOfDimensions();

    /**
     * @return 子类实现, 用于在父类与子类之间进行转换的一个显示函数, 直接返回this 即可
     * <p>
     * Subclass implementation, a display function used to convert between parent class and subclass, just return this directly
     */
    ImplementationType expand();
}
