package zhao.algorithmMagic.core.model.dataSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhao.algorithmMagic.operands.matrix.block.IntegerMatrixSpace;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 数据集共享函数类包。
 *
 * @author zhao
 */
public final class Share {

    public final static Logger LOGGER = LoggerFactory.getLogger("ASDataSet");

    /**
     * 字母数据集的初始权重
     */
    public final static List<KeyValue<String, String>> INIT_LETTER_WEIGHT = Arrays.asList(
            new KeyValue<>("A", "https://user-images.githubusercontent.com/113756063/234247437-32e5b5ff-0baf-4637-805c-27472f07fd17.jpg"),
            new KeyValue<>("B", "https://user-images.githubusercontent.com/113756063/235287575-fcd9f3a9-7be7-4528-8190-8c9e54cef7fd.jpg"),
            new KeyValue<>("C", "https://user-images.githubusercontent.com/113756063/235287578-29acf0f9-df03-4a62-8455-ed7a56f8f35c.jpg"),
            new KeyValue<>("X", "https://user-images.githubusercontent.com/113756063/234247472-1df7f892-89b5-467f-9d8d-eb397b92c6ce.jpg"),
            new KeyValue<>("Y", "https://user-images.githubusercontent.com/113756063/234247497-0a329b5d-a15d-451f-abf7-abdc1655b77d.jpg"),
            new KeyValue<>("Z", "https://user-images.githubusercontent.com/113756063/235150408-233cc477-c97d-48a1-b39e-ff9a497ea9ff.jpg")
    );

    /**
     * 字母数据集的初始类别对的名称
     */
    public final static String[] INIT_LETTER_NAMES = new String[INIT_LETTER_WEIGHT.size()];

    /**
     * 不同字母类别对应着其所有训练数据的映射集合
     */
    public final static HashMap<String, String[]> INIT_LETTER_NAME_X = new HashMap<>();

    static {
        int index = -1;
        for (KeyValue<String, String> stringStringKeyValue : INIT_LETTER_WEIGHT) {
            INIT_LETTER_NAMES[++index] = stringStringKeyValue.getKey();
        }

        INIT_LETTER_NAME_X.put("A", new String[]{
                "https://user-images.githubusercontent.com/113756063/234247437-32e5b5ff-0baf-4637-805c-27472f07fd17.jpg",
                "https://user-images.githubusercontent.com/113756063/234247630-46319338-b8e6-47bf-9918-4b734e665cf9.jpg"
        });

        INIT_LETTER_NAME_X.put("B", new String[]{
                "https://user-images.githubusercontent.com/113756063/235287575-fcd9f3a9-7be7-4528-8190-8c9e54cef7fd.jpg",
                "https://user-images.githubusercontent.com/113756063/235287584-90f2361b-0292-41cc-9469-6d4a64258c8d.jpg",
        });

        INIT_LETTER_NAME_X.put("C", new String[]{
                "https://user-images.githubusercontent.com/113756063/235287578-29acf0f9-df03-4a62-8455-ed7a56f8f35c.jpg",
                "https://user-images.githubusercontent.com/113756063/235335944-9e7b1a84-9b44-40d1-b943-30fc290b11ac.jpg",
                "https://user-images.githubusercontent.com/113756063/235287616-446108e1-1e1c-4416-991b-3b0cbb888d5b.jpg",
        });

        INIT_LETTER_NAME_X.put("X", new String[]{
                "https://user-images.githubusercontent.com/113756063/234247472-1df7f892-89b5-467f-9d8d-eb397b92c6ce.jpg",
                "https://user-images.githubusercontent.com/113756063/235150686-c4b84e78-1b24-409d-a26f-6a860ed104d8.jpg",
                "https://user-images.githubusercontent.com/113756063/234247550-01777cee-493a-420f-8665-da31e60a1cbe.jpg",
        });

        INIT_LETTER_NAME_X.put("Y", new String[]{
                "https://user-images.githubusercontent.com/113756063/234247497-0a329b5d-a15d-451f-abf7-abdc1655b77d.jpg",
                "https://user-images.githubusercontent.com/113756063/235150464-9d082c41-ae06-4ee7-a680-59e62cd55b10.jpg",
        });

        INIT_LETTER_NAME_X.put("Z", new String[]{
                "https://user-images.githubusercontent.com/113756063/235150408-233cc477-c97d-48a1-b39e-ff9a497ea9ff.jpg",
                "https://user-images.githubusercontent.com/113756063/235150989-e71036bd-f8bb-43b9-b566-e3131827d7ac.jpg",
                "https://user-images.githubusercontent.com/113756063/235335680-05f44579-9ea6-407b-b301-bf8774a68b2a.jpg"
        });

    }


    /**
     * 获取到训练的时候使用的图像样本。
     */
    public static IntegerMatrixSpace[] getData(int w, int h, String... urls) throws MalformedURLException {
        IntegerMatrixSpace[] integerMatrixSpaces = new IntegerMatrixSpace[urls.length];
        int index = -1;
        for (String url : urls) {
            LOGGER.info("downLoad train image => " + url);
            integerMatrixSpaces[++index] = IntegerMatrixSpace.parse(new URL(url), w, h);
        }
        return integerMatrixSpaces;
    }

    /**
     * 获取到训练的时候使用的图像样本。
     */
    public static IntegerMatrixSpace[] getData(int w, int h, String[]... urls) throws MalformedURLException {
        IntegerMatrixSpace[] integerMatrixSpaces = new IntegerMatrixSpace[urls.length * urls[0].length];
        int index = -1;
        for (String[] url : urls) {
            for (String url1 : url) {
                LOGGER.info("downLoad train image => " + url1);
                integerMatrixSpaces[++index] = IntegerMatrixSpace.parse(new URL(url1), w, h);
            }
        }
        return integerMatrixSpaces;
    }

    /**
     * 获取到权重数据样本
     */
    public static List<KeyValue<String, IntegerMatrixSpace>> getImageWeight(int w, int h, List<KeyValue<String, String>> nameAndUrl) throws MalformedURLException {
        ArrayList<KeyValue<String, IntegerMatrixSpace>> list = new ArrayList<>(nameAndUrl.size() + 2);
        for (KeyValue<String, String> value : nameAndUrl) {
            LOGGER.info("downLoad category image => category[" + value.getKey() + "]\tURL = " + value.getValue());
            list.add(new KeyValue<>(value.getKey(), IntegerMatrixSpace.parse(new URL(value.getValue()), w, h)));
        }
        return list;
    }
}
