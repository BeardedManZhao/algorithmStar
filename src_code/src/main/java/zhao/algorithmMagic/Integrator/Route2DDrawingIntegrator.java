package zhao.algorithmMagic.Integrator;

import zhao.algorithmMagic.algorithm.OperationAlgorithm;
import zhao.algorithmMagic.algorithm.OperationAlgorithmManager;
import zhao.algorithmMagic.algorithm.generatingAlgorithm.GeneratingAlgorithm;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.exception.TargetNotRealizedException;
import zhao.algorithmMagic.operands.coordinate.DoubleCoordinateMany;
import zhao.algorithmMagic.operands.route.DoubleConsanguinityRoute;
import zhao.algorithmMagic.utils.ASIO;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Java类于 2022/10/15 14:01:16 创建
 * <p>
 * 路线2维绘图集成器,您可以在这里实现有关路线绘图的操作
 * <p>
 * 请注意,该类的使用由内部自动去绑定对应的算法,您需要在该类示例化的时候去指定线路算法的名称! 一定要是拓展了" DoubleConsanguinityRoute"或其本身的算法名称!!!
 * <p>
 * Route 2D drawing integrator, you can implement operations related to route drawing here, please note that the use of this class is automatically bound to the corresponding algorithm internally, you need to specify the name of the route algorithm when the class is instantiated !
 * <p>
 * Please note that the use of this class is automatically bound to the corresponding algorithm internally, you need to specify the name of the route algorithm when the class is instantiated! Be sure to extend "DoubleConsanguinityRoute" or its own algorithm name!!!
 *
 * @author zhao
 */
public class Route2DDrawingIntegrator implements AlgorithmIntegrator<Route2DDrawingIntegrator> {

    private final String IntegratorName;
    private final GeneratingAlgorithm generatingAlgorithm;
    private String imageOutPath = "image.jpg";
    private int imageWidth;
    private int imageHeight;
    private int discreteThreshold;
    private Color textColor = new Color(0xfffff);
    private Color backColor = new Color(0x333232, false);

    public Route2DDrawingIntegrator(String GeneratingAlgorithmName, String integratorName) {
        IntegratorName = integratorName;
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(GeneratingAlgorithmName);
        if (operationAlgorithm instanceof GeneratingAlgorithm) {
            this.generatingAlgorithm = (GeneratingAlgorithm) operationAlgorithm;
            if (!generatingAlgorithm.isSupportDrawing()) {
                throw new OperatorOperationException("您的算法[" + GeneratingAlgorithmName + "]不支持图像绘制,因此本路线绘制器的构造无法顺利完成,请您使用支持图像构造的算法!\n" +
                        "Your algorithm[" + GeneratingAlgorithmName + "] does not support image drawing, so the construction of this route drawer cannot be completed successfully, please use an algorithm that supports image construction!");
            }
        } else {
            throw new TargetNotRealizedException("您提取的[" + GeneratingAlgorithmName + "]算法被找到了，但是它不属于 GeneratingAlgorithm 类型，请您为这个算法重新定义一个名称。\n" +
                    "The [" + GeneratingAlgorithmName + "] algorithm you ParameterCombination has been found, but it does not belong to the GeneratingAlgorithm type. Please redefine a name for this algorithm.");
        }
    }

    /**
     * @return 该集成器的子类身份, 用于父类子类之间的互相转换
     */
    @Override
    public Route2DDrawingIntegrator expand() {
        return this;
    }

    @Override
    public String getIntegratorName() {
        return this.IntegratorName;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * 设置集成器输出图片的宽度
     *
     * @param imageWidth The width of the integrator output image
     * @return chain programming
     */
    public Route2DDrawingIntegrator setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * 设置集成器输出图片的高度
     *
     * @param imageHeight the height of the integrator output image
     * @return chain programming
     */
    public Route2DDrawingIntegrator setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public int getDiscreteThreshold() {
        return discreteThreshold;
    }

    /**
     * 设置离散阈值,该阈值越大,路线显示越清晰,该阈值可以扩大整个2维空间的显示,默认是 1
     * <p>
     * Set the discrete threshold, the larger the threshold, the clearer the route display, the threshold can expand the display of the entire 2-dimensional space, the default is 1
     *
     * @param discreteThreshold 新的离散阈值
     * @return 链式
     */
    public Route2DDrawingIntegrator setDiscreteThreshold(int discreteThreshold) {
        this.discreteThreshold = discreteThreshold;
        return this;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    @Override
    public boolean run() {
        // 创建一个图片
        BufferedImage image = new BufferedImage(this.imageWidth, this.imageHeight, BufferedImage.TYPE_INT_RGB);
        // 准备绘图工具
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(textColor);
        g.setBackground(backColor);
        g.clearRect(0, 0, this.imageWidth, this.imageHeight);
        g.translate(this.imageWidth >> 1, this.imageHeight >> 1);
        // 迭代每一个坐标点
        for (DoubleConsanguinityRoute value : this.generatingAlgorithm.AcquireImageDataSet().values()) {
            // 获取一个路线的起始坐标与终止坐标对象
            drawARoute(g, value.getStartingCoordinateName(), value.getEndPointCoordinateName(), value.getStartingCoordinate(), value.getEndPointCoordinate());
        }
        // 将图片对象输出到指定的路径下!
        ASIO.outImage(image, this.imageOutPath);
        return true;
    }

    /**
     * 绘制一个路线
     *
     * @param graphics2D         绘制所需要的画笔对象
     * @param startName          该线起始坐标的名称
     * @param endName            该线的终止坐标名称
     * @param startingCoordinate 起始坐标对象
     * @param endPointCoordinate 终止坐标对象
     */
    private void drawARoute(Graphics2D graphics2D, String startName, String endName, DoubleCoordinateMany startingCoordinate, DoubleCoordinateMany endPointCoordinate) {
        // 开始绘制
        double[] doubles1 = startingCoordinate.toArray();
        double[] doubles2 = endPointCoordinate.toArray();
        int startX = (int) doubles1[0] << this.discreteThreshold;
        int startY = -(int) doubles1[1] << this.discreteThreshold;
        int endX = (int) doubles2[0] << this.discreteThreshold;
        int endY = -(int) doubles2[1] << this.discreteThreshold;
        graphics2D.drawLine(startX, startY, endX, endY);
        graphics2D.drawString(startName, startX + 1, startY + 1);
        graphics2D.drawString(endName, endX + 1, endY + 1);
    }

    public String getImageOutPath() {
        return imageOutPath;
    }

    /**
     * 设置图片输出路径.
     * <p>
     * Set the image output path.
     *
     * @param imageOutPath Set the image output path.
     */
    public Route2DDrawingIntegrator setImageOutPath(String imageOutPath) {
        this.imageOutPath = imageOutPath;
        return this;
    }
}
