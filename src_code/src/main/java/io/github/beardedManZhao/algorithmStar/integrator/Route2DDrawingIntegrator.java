package io.github.beardedManZhao.algorithmStar.integrator;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithm;
import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import io.github.beardedManZhao.algorithmStar.exception.OperatorOperationException;
import io.github.beardedManZhao.algorithmStar.exception.TargetNotRealizedException;
import io.github.beardedManZhao.algorithmStar.integrator.launcher.Route2DDrawingLauncher;
import io.github.beardedManZhao.algorithmStar.integrator.launcher.Route2DDrawingLauncher2;
import io.github.beardedManZhao.algorithmStar.operands.coordinate.IntegerCoordinateTwo;
import io.github.beardedManZhao.algorithmStar.operands.route.IntegerConsanguinityRoute2D;
import io.github.beardedManZhao.algorithmStar.utils.ASClass;
import io.github.beardedManZhao.algorithmStar.utils.ASIO;

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
public final class Route2DDrawingIntegrator implements AlgorithmIntegrator<Route2DDrawingIntegrator> {

    private final String IntegratorName;
    private final Route2DDrawingLauncher route2DDrawingStarter;
    private String imageOutPath = "image.jpg";
    private int imageWidth = 0b11001000;
    private int imageHeight = 0b11001000;
    private int discreteThreshold;
    private Color textColor = new Color(0xfffff);
    private Color backColor = new Color(0x333232, false);

    /**
     * 通过名称获取到启动器，这种方式适用于已经实现了启动器的算法，它会按照以下步骤执行
     * 1.前往管理者，提取指定的算法
     * 2.检查算法是否属于该集成器的启动器
     * 3.检查启动器是否允许或支持绘制图像
     * 4.将算法以启动器的身份加载到集成器
     *
     * @param integratorName        集成器的名称
     * @param AlgorithmLauncherName 实现了启动器的算法名称
     */
    public Route2DDrawingIntegrator(String integratorName, String AlgorithmLauncherName) {
        IntegratorName = integratorName;
        OperationAlgorithm operationAlgorithm = OperationAlgorithmManager.getInstance().get(AlgorithmLauncherName);
        if (operationAlgorithm instanceof Route2DDrawingLauncher) {
            this.route2DDrawingStarter = ASClass.transform(operationAlgorithm);
            if (!route2DDrawingStarter.isSupportDrawing()) {
                throw new OperatorOperationException("您的启动器[" + AlgorithmLauncherName + "]不支持图像绘制,因此本路线绘制器的构造无法顺利完成,请您使用支持图像构造的算法!\n" +
                        "Your algorithm[" + AlgorithmLauncherName + "] does not support image drawing, so the construction of this route drawer cannot be completed successfully, please use an algorithm that supports image construction!");
            }
        } else {
            throw new TargetNotRealizedException("您提取的[" + AlgorithmLauncherName + "]算法被找到了，但是它没有实现 Route2DDrawingLauncher ，您如果想要使用该算法绘制图像，那么您要保证它实现了启动器。\n" +
                    "The [" + AlgorithmLauncherName + "] algorithm you ParameterCombination has been found, But it doesn't implement Route2DDrawingLauncher , if you want to use this algorithm to draw images then you have to make sure it implements a launcher");
        }
    }

    public Route2DDrawingIntegrator(String integratorName, Route2DDrawingLauncher route2DDrawingLauncher) {
        this.IntegratorName = integratorName;
        this.route2DDrawingStarter = route2DDrawingLauncher;
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
     * Set the discrete threshold, the larger the threshold, the clearer the route display, the threshold can extend the display of the entire 2-dimensional space, the default is 1
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

    /**
     * 运行绘图集成器
     *
     * @return 运行如果没有发生错误 这里将会返回true
     * <p>
     * If no error occurs when running, true will be returned here
     */
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
        // 集成第二版接口
        run1(g);
        // 迭代每一个坐标点
        for (IntegerConsanguinityRoute2D value : this.route2DDrawingStarter.AcquireImageDataSet().values()) {
            // 获取一个路线的起始坐标与终止坐标对象
            drawARoute(g, value.getStartingCoordinateName(), value.getEndPointCoordinateName(), value.getStartingCoordinate(), value.getEndPointCoordinate());
        }
        // 集成第二版接口
        run2(g);
        // 将图片对象输出到指定的路径下!
        if (!ASIO.outImage(image, this.imageOutPath)) {
            throw new OperatorOperationException("图片输出失败,请检查您的路径是否正确,或者您的图片是否被占用,或者您的图片是否超出了您的磁盘空间。\n" +
                    "Image output failed, please check whether your path is correct, or whether your image is occupied, or whether your image exceeds your disk space.");
        }
        return true;
    }

    /**
     * @return 运行该集成器, 并返回运行结果，该函数是带参数的运行，当运行成功之后会返回一个double类型的参数。参数大于0，代表运行成功！
     * <p>
     * Run the integrator and return the result of the operation. The function is run with parameters. When the operation is successful, it will return a double type parameter. If the parameter is greater than 0, it means the operation is successful!
     */
    @Override
    public double runAndReturnValue() {
        if (run()) {
            return 0b1;
        } else {
            return 0b11111111111111111111111111111111;
        }
    }

    /**
     * 附加任务函数执行题,为了弥补绘图器不够灵活的缺陷,2022年10月16日新增了一个附加任务接口,该接口将会在旧接口的任务执行之前调用
     *
     * @param graphics2D 画笔对象
     */
    private void run1(Graphics2D graphics2D) {
        // 第二版接口的附加任务启动
        if (this.route2DDrawingStarter instanceof Route2DDrawingLauncher2) {
            Route2DDrawingLauncher2 dDrawingStarter = (Route2DDrawingLauncher2) this.route2DDrawingStarter;
            dDrawingStarter.AdditionalTasks1(graphics2D, this);
        }
    }

    /**
     * 附加任务函数执行题,为了弥补绘图器不够灵活的缺陷,2022年10月16日新增了一个附加任务接口,该接口将会在旧接口的任务执行之后调用
     *
     * @param graphics2D 画笔对象
     */
    private void run2(Graphics2D graphics2D) {
        // 第二版接口的附加任务启动
        if (this.route2DDrawingStarter instanceof Route2DDrawingLauncher2) {
            Route2DDrawingLauncher2 dDrawingStarter = (Route2DDrawingLauncher2) this.route2DDrawingStarter;
            dDrawingStarter.AdditionalTasks2(graphics2D, this);
        }
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
    public void drawARoute(Graphics2D graphics2D, String startName, String endName, IntegerCoordinateTwo startingCoordinate, IntegerCoordinateTwo endPointCoordinate) {
        // 开始绘制
        int startX = startingCoordinate.getX() << this.discreteThreshold;
        int startY = -startingCoordinate.getY() << this.discreteThreshold;
        int endX = endPointCoordinate.getX() << this.discreteThreshold;
        int endY = -endPointCoordinate.getY() << this.discreteThreshold;
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
     * @return chain call
     */
    public Route2DDrawingIntegrator setImageOutPath(String imageOutPath) {
        this.imageOutPath = imageOutPath;
        return this;
    }
}
