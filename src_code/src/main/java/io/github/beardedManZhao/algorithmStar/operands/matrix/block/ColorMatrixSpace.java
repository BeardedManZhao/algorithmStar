package io.github.beardedManZhao.algorithmStar.operands.matrix.block;

import io.github.beardedManZhao.algorithmStar.SerialVersionUID;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix;
import io.github.beardedManZhao.algorithmStar.operands.matrix.IntegerMatrix;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.transformation.Transformation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

import static io.github.beardedManZhao.algorithmStar.operands.matrix.ColorMatrix.WHITE;

/**
 * 图像矩阵空间类，在该类中存储的是三层图像矩阵对象，其能够进行强大的多颜色通道的提取以及多图像矩阵并行处理操作。
 * <p>
 * The image matrix space class stores three-layer image matrix objects, which can perform powerful multicolor channel extraction and parallel processing of multiple image matrices.
 *
 * @author 赵凌宇
 * 2023/4/16 20:58
 */
public class ColorMatrixSpace extends MatrixSpace<ColorMatrixSpace, Color, Color[][], ColorMatrix> {

    public final static Transformation<Color[][], ColorMatrix> CREATE_MAP_T = ColorMatrix::parse;
    public final static Transformation<Color[][][], ColorMatrix[]> CREATE_MAP_INIT = ints -> new ColorMatrix[ints.length];

    private static final long serialVersionUID = SerialVersionUID.ColorMatrixSpace.getNum();


    /**
     * 构造一个空的矩阵，指定其矩阵的行列数
     * <p>
     * Constructs an empty matrix, specifying the number of rows and columns of its matrix
     *
     * @param rowCount      矩阵中的行数量
     *                      <p>
     *                      the number of rows in the matrix
     * @param colCount      矩阵中的列数量
     *                      <p>
     * @param colorMatrices 该矩阵对象中的二维数组对象。
     */
    protected ColorMatrixSpace(int rowCount, int colCount, ColorMatrix[] colorMatrices) {
        super(rowCount, colCount, colorMatrices);
    }

    /**
     * 通过诸多图像矩阵对象叠加合并成为一个图像矩阵空间对象。
     * <p>
     * By overlaying and merging multiple image matrix objects into one image matrix spatial object.
     *
     * @param colorMatrices 需要被进行叠加合并的图像矩阵对象，至少需要一个图像矩阵。
     *                      <p>
     *                      The image matrix object that needs to be superimposed and merged requires at least one image matrix.
     * @return 所有图像矩形对象叠加成的图像空间，其能够针对所有图像进行统一的函数操作，能够通过该对象操作所有的图像矩阵。
     * <p>
     * The image space formed by overlaying all image rectangular objects, which can perform unified function operations on all images and manipulate all image matrices through this object.
     */
    public static ColorMatrixSpace parse(ColorMatrix... colorMatrices) {
        if (colorMatrices.length == 0)
            throw new OperatorOperationException("Passing empty data is not allowed in matrix space.");
        return new ColorMatrixSpace(colorMatrices[0].getRowCount(), colorMatrices[0].getColCount(), colorMatrices);
    }

    /**
     * 提供子类类型数据，构造出当前子类的实例化对象，用于父类在不知道子类数据类型的情况下，创建子类矩阵。
     * <p>
     * Provide subclass type data, construct an instantiated object of the current subclass, and use it for the parent class to create a subclass matrix without knowing the subclass data type.
     *
     * @param arrayType 构造一个新的与当前数据类型一致的矩阵对象。
     *                  <p>
     *                  Construct a new matrix object that is consistent with the current data type.
     * @return 该类的实现类对象，用于拓展该接口的子类。
     * <p>
     * The implementation class object of this class is used to extend the subclasses of this interface.
     */
    @Override
    public ColorMatrixSpace expand(ColorMatrix[] arrayType) {
        return parse(arrayType);
    }

    /**
     * 获取到矩阵中指定坐标点的数值
     *
     * @param row 行编号 从0开始
     *            <p>
     *            Line number starts from 0
     * @param col 列编号 从0开始
     *            <p>
     *            Column number starts from 0
     * @return 矩阵中指定行列坐标的数值
     * <p>
     * The value of the specified row and column coordinates in the matrix
     */
    @Override
    public Color get(int row, int col) {
        return get(Math.max(RowPointer, 0), row, col);
    }

    /**
     * 刷新操作数对象的所有字段
     */
    @Override
    protected void reFresh() {

    }

    /**
     * @return 将本对象中存储的向量序列的数组直接返回，注意，这里返回的是一个正在被维护的数组，因此建议保证返回值作为只读变量使用。
     * <p>
     * Return the array of vector sequences stored in this object directly. Note that the returned value is an array being maintained. Therefore, it is recommended to ensure that the returned value is used as a read-only variable.
     */
    @Override
    public Color[][] toArray() {
        return toMatrix().toArrays();
    }

    /**
     * 计算该向量的模长，具体实现请参阅api说明
     * <p>
     * Calculate the modulo length of the vector, please refer to the api node for the specific implementation
     *
     * @return 向量的模长
     * waiting to be realized
     */
    @Override
    public Color moduleLength() {
        return toMatrix().moduleLength();
    }

    /**
     * 将两个操作数进行求和的方法，具体用法请参阅API说明。
     * <p>
     * The method for summing two operands, please refer to the API description for specific usage.
     *
     * @param value 被求和的参数  Parameters to be summed
     * @return 求和之后的数值  the value after the sum
     * <p>
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public ColorMatrixSpace add(ColorMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        ColorMatrix[] colorMatrices2 = value.toArrays();
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.add(colorMatrices2[index]);
        }
        return parse(colorMatrices0);
    }

    /**
     * 在两个操作数之间做差的方法，具体用法请参阅API说明。
     * <p>
     * The method of making a difference between two operands, please refer to the API description for specific usage.
     *
     * @param value 被做差的参数（被减数）  The parameter to be subtracted (minuend)
     * @return 差异数值  difference value
     * There is no description for the super interface, please refer to the subclass documentation
     */
    @Override
    public ColorMatrixSpace diff(ColorMatrixSpace value) {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        ColorMatrix[] colorMatrices2 = value.toArrays();
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.diff(colorMatrices2[index]);
        }
        return parse(colorMatrices0);
    }

    /**
     * 在两个向量对象之间进行计算的函数，自从1.13版本开始支持该函数的调用，该函数中的计算并不会产生一个新的向量，而是将计算操作作用于原操作数中
     * <p>
     * The function that calculates between two vector objects supports the call of this function since version 1.13. The calculation in this function will not generate a new vector, but will apply the calculation operation to the original operand
     *
     * @param value        与当前向量一起进行计算的另一个向量对象。
     *                     <p>
     *                     Another vector object that is evaluated with the current vector.
     * @param ModifyCaller 计算操作作用对象的设置，该参数如果为true，那么计算时针对向量序列的修改操作将会直接作用到调用函数的向量中，反之将会作用到被操作数中。
     *                     <p>
     *                     The setting of the calculation operation action object. If this parameter is true, the modification of the vector sequence during calculation will directly affect the vector of the calling function, and vice versa.
     * @return 两个向量经过了按维度的减法计算之后，被修改的向量对象
     */
    @Override
    public ColorMatrixSpace diffAbs(ColorMatrixSpace value, boolean ModifyCaller) {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        ColorMatrix[] colorMatrices2 = value.toArrays();
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.diffAbs(colorMatrices2[index], ModifyCaller);
        }
        return parse(colorMatrices0);
    }

    /**
     * 两个向量相乘，同时也是两个向量的外积，具体实现请参阅api说明
     * <p>
     * The multiplication of two vectors is also the outer product of the two vectors. For the specific implementation, please refer to the api description.
     *
     * @param vector 被做乘的向量
     * @return 向量的外积
     * waiting to be realized
     */
    @Override
    public ColorMatrixSpace multiply(ColorMatrixSpace vector) {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        ColorMatrix[] colorMatrices2 = vector.toArrays();
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.multiply(colorMatrices2[index]);
        }
        return parse(colorMatrices0);
    }

    /**
     * 计算两个向量的内积，也称之为数量积，具体实现请参阅api说明
     * <p>
     * Calculate the inner product of two vectors, also known as the quantity product, please refer to the api node for the specific implementation
     *
     * @param vector 第二个被计算的向量对象
     *               <p>
     *               the second computed vector object
     * @return 两个向量的内积
     * waiting to be realized
     */
    @Override
    public Color innerProduct(ColorMatrixSpace vector) {
        throw new UnsupportedOperationException("The color matrix object does not support the operation of \"addition\", \"subtraction\" and \"multiplication\", because the calculation of such operations on the color object is not necessary!");
    }

    /**
     * 将现有矩阵的转置矩阵获取到
     * <p>
     * Get the transpose of an existing matrix into
     *
     * @return 矩阵转置之后的新矩阵
     * <p>
     * new matrix after matrix transpose
     */
    @Override
    public ColorMatrixSpace transpose() {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.transpose();
        }
        return parse(colorMatrices0);
    }

    /**
     * 按照本矩阵空间的创建方式创建出一个新的矩阵对象，该函数通常用于父类需要子类帮助创建同类型的参数的场景。
     * <p>
     * Create a new matrix object according to the creation method of this matrix space, which is usually used in scenarios where the parent class needs the help of subclasses to create parameters of the same type.
     *
     * @param layer 新矩阵矩阵空间的层数。
     *              <p>
     *              The number of layers in the new matrix space.
     * @param row   新创建的矩阵空间中每个矩阵的行数。
     *              <p>
     *              The number of rows for each matrix in the newly created matrix space.
     * @param col   新创建的矩阵空间中每个矩阵的列数。
     *              <p>
     *              The number of columns for each matrix in the newly created matrix space.
     * @return 创建出来的矩阵空间对象.
     * <p>
     * The created matrix space object
     */
    @Override
    protected ColorMatrixSpace create(int layer, int row, int col) {
        return ColorMatrixSpace.parse(
                ASClass.map(
                        CREATE_MAP_T, CREATE_MAP_INIT, new Color[layer][row][col]
                )
        );
    }

    /**
     * 按照本矩阵空间的创建方式创建出一个新的矩阵对象，该函数通常用于父类需要子类帮助创建同类型的参数的场景。
     * <p>
     * Create a new matrix object according to the creation method of this matrix space, which is usually used in scenarios where the parent class needs the help of subclasses to create parameters of the same type.
     *
     * @param data 新矩阵空间对象中的元素。
     *             <p>
     *             Elements in the new matrix space object.
     * @return 创建出来的新的矩阵空间对象。
     * <p>
     * Create a new matrix space object.
     */
    @Override
    protected ColorMatrixSpace create(Color[][][] data) {
        return null;
    }

    /**
     * @return 该类的实现类对象，用于拓展该接口的子类
     */
    @Override
    public ColorMatrixSpace expand() {
        return this;
    }

    /**
     * @return 将本对象中存储的向量序列数组拷贝到一个新数组并将新数组返回，这里返回的是一个新数组，支持修改等操作。
     * <p>
     * Copy the vector sequence array stored in this object to a new array and return the new array. Here, a new array is returned, which supports modification and other operations.
     */
    @Override
    public Color[][] copyToNewArray() {
        return toMatrix().toArrays();
    }

    /**
     * 将本对象中的所有数据进行洗牌打乱，随机分布数据行的排列。
     * <p>
     * Shuffle all the data in this object and randomly distribute the arrangement of data rows.
     *
     * @param seed 打乱算法中所需要的随机种子。
     *             <p>
     *             Disrupt random seeds required in the algorithm.
     * @return 打乱之后的对象。
     * <p>
     * Objects after disruption.
     */
    @Override
    public ColorMatrixSpace shuffle(long seed) {
        int length = this.getNumberOfDimensions();
        ColorMatrix[] colorMatrices0 = new ColorMatrix[length];
        int index = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            colorMatrices0[++index] = colorMatrix.shuffle(seed);
        }
        return parse(colorMatrices0);
    }

    /**
     * @return 当前对象或类的序列化数值，相同类型的情况下该数值是相同的。
     * <p>
     * The serialized value of the current object or class, which is the same for the same type.
     */
    @Override
    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 该方法将会获取到矩阵中的二维数组，值得注意的是，在该函数中获取到的数组是一个新的数组，不会有任何的关系。
     * <p>
     * This method will obtain the two-dimensional array in the matrix. It is worth noting that the array obtained in this function is a new array and will not have any relationship.
     *
     * @return 当前矩阵对象中的二维数组的深拷贝新数组，支持修改！！
     * <p>
     * The deep copy new array of the two-dimensional array in the current matrix object supports modification!!
     */
    @Override
    public ColorMatrix[] copyToNewArrays() {
        ColorMatrix[] colors = this.toArrays();
        ColorMatrix[] res = new ColorMatrix[colors.length];
        int index = -1;
        for (ColorMatrix color : colors) {
            res[++index] = ColorMatrix.parse(color.copyToNewArrays());
        }
        return res;
    }

    /**
     * 针对矩阵操作数的形状进行重新设定，使得矩阵中的数据维度的更改能够更加友好。
     * <p>
     * Reset the shape of the matrix operands to make changes to the data dimensions in the matrix more user-friendly.
     *
     * @param shape 需要被重新设置的新维度信息，其中包含2个维度信息，第一个代表矩阵的行数量，第二个代表矩阵的列数量。
     *              <p>
     *              The new dimension information that needs to be reset includes two dimensions: the first represents the number of rows in the matrix, and the second represents the number of columns in the matrix.
     * @return 重设之后的新矩阵对象。
     * <p>
     * The new matrix object after resetting.
     */
    @Override
    public ColorMatrixSpace reShape(int... shape) {
        return this.create(
                ASClass.reShape(
                        this,
                        (Transformation<int[], Color[][][]>) ints -> new Color[ints[0]][ints[1]][ints[2]],
                        shape
                )
        );
    }

    /**
     * 将图像矩阵展示出来，使得在矩阵的图像数据能够被展示出来。
     * <p>
     * Display the image matrix so that the image data in the matrix can be displayed.
     *
     * @param title  展示图像时，图像窗口对应的标题。
     *               <p>
     *               The title corresponding to the image window when displaying the image.
     * @param width  展示图像时，图像窗口的宽度。
     *               <p>
     *               The width of the image window when displaying the image.
     * @param height 展示图象时，图像窗口的高度。
     *               <p>
     *               The height of the image window when displaying the image.
     */
    public final void show(String title, int width, int height) {
        int colCount = this.getColCount();
        int rowCount = this.getRowCount();
        boolean needCorrection = colCount != width || rowCount != height;
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(width, height);
        jFrame.setVisible(true);
        jFrame.setTitle(title);
        jFrame.setBackground(WHITE);
        jFrame.setResizable(true);
        ImageIcon imageIcon = new ImageIcon(jFrame.createVolatileImage(colCount, rowCount * this.getNumberOfDimensions()));
        Image image = imageIcon.getImage();
        Graphics graphics = image.getGraphics();
        // 开始绘制图形
        int yc = -1;
        for (ColorMatrix colorMatrix : this.toArrays()) {
            for (Color[] colors : colorMatrix.toArrays()) {
                ++yc;
                int xc = -1;
                for (Color color : colors) {
                    graphics.setColor(color);
                    graphics.fillRect(++xc, yc, 1, 1);
                }
            }
        }
        JLabel label = new JLabel(imageIcon);//往一个标签中加入图片
        label.setVisible(true);
        label.setBounds(0, 0, jFrame.getWidth(), jFrame.getHeight());//设置标签位置大小，记得大小要和窗口一样大
        if (needCorrection) {
            imageIcon.setImage(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));//图片自适应窗口大小
        }
        jFrame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板
        jFrame.add(label);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<ColorMatrix> iterator() {
        return Arrays.stream(this.toArrays()).iterator();
    }

    /**
     * 将当前的图像空间对象转换成为整形矩阵对象，整形矩阵对象支持更多的数值类计算操作，同时也支持卷积计算。
     * <p>
     * Convert the current image space object into an integer matrix object, which supports more numerical computation operations as well as convolutional computation.
     *
     * @param rgbNum 转换规则，其中应保存着每一层矩阵对象中的颜色通道数值，当第0个索引为红色个闫妮色通道的时候，获取到的空间中第一层矩阵就是红色通道的颜色矩阵对象。
     *               <p>
     *               Conversion rules, which should store the color channel values in each layer of matrix objects. When the 0th index is a red channel, the first layer of matrix in the obtained space is the color matrix object of the red channel.
     * @return 由当前图像空间中所有层矩阵对象的对应颜色数值提取到的整形矩阵空间对象。
     * <p>
     * An integer matrix space object extracted from the corresponding color values of all layer matrix objects in the current image space.
     */
    public IntegerMatrixSpace toIntMatrixSpace(int... rgbNum) {
        IntegerMatrix[] colorMatrices = new IntegerMatrix[rgbNum.length];
        int index = -1;
        for (int rgb : rgbNum) {
            colorMatrices[++index] = this.get(index).getChannel(rgb);
        }
        return IntegerMatrixSpace.parse(colorMatrices);
    }
}
