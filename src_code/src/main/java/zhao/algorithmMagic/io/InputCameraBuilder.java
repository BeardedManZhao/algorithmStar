package zhao.algorithmMagic.io;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.operands.table.FinalCell;

import java.util.HashMap;

/**
 * 摄像头设备输入建造者对象
 *
 * @author 赵凌宇
 * 2023/4/5 14:28
 */
public class InputCameraBuilder extends HashMap<String, Cell<?>> implements InputBuilder {

    /**
     * 拍照时需要使用的摄像头索引，索引对应的value应是一个数值或字符串，如果时数值将当作索引处理，如果时字符串将当作摄像头名称处理。
     */
    public final static String Camera_Index = "Camera";

    /**
     * 拍照时需要使用的图像拍照格式，格式对应的值应是一个字符串。
     */
    public final static String Image_Format = "ImageFormat";

    /**
     * 图像拍照大小尺寸对象，格式对应的值可以直接是 WebcamResolution 类的属性。
     */
    public final static String CUSTOM_VIEW_SIZES = "CustomViewSizes";

    public WebcamResolution webcamResolution = WebcamResolution.VGA;

    /**
     * 添加数据输入描述，不同的组件有不同的配置属性，具体可以参阅实现类。
     * <p>
     * Add data input descriptions, and different components have different configuration properties. Please refer to the implementation class for details.
     *
     * @param key   属性名称
     *              <p>
     *              Attribute Name.
     * @param value 属性数值
     *              <p>
     *              Attribute Value.
     * @return 链式调用，继续构建
     * <p>
     * Chain call, continue building.
     */
    @Override
    public InputBuilder addInputArg(String key, FinalCell<?> value) {
        if (CUSTOM_VIEW_SIZES.equals(key)) {
            return this.setViewSize(WebcamResolution.valueOf(value.toString()));
        }
        this.put(key, value);
        return this;
    }

    /**
     * @param WebcamResolution 图像拍照大小尺寸对象
     * @return 链式调用，继续构建
     * <p>
     * Chain call, continue building.
     */
    public InputBuilder setViewSize(WebcamResolution WebcamResolution) {
        this.webcamResolution = WebcamResolution;
        return this;
    }

    /**
     * 将所需的对象构建出来并获取到对应的输入设备对象。
     *
     * @return 输入设备对象。
     */
    @Override
    public InputComponent create() {
        Cell<?> cell = this.get(Camera_Index);
        Webcam webcam;
        if (cell != null) {
            if (cell.isNumber()) {
                webcam = Webcam.getWebcams().get(cell.getIntValue());
            } else {
                String s = cell.toString();
                if ("def".equals(s)) webcam = Webcam.getDefault();
                else webcam = Webcam.getWebcamByName(s);
            }
            webcam.setViewSize(this.webcamResolution.getSize());
            return new InputCamera(webcam, this.getOrDefault(Image_Format, new FinalCell<>("JPG")).toString());
        } else {
            throw new OperatorOperationException("请您配置摄像头的名称或摄像头索引。\nPlease configure the name or camera index of the camera.");
        }
    }
}
