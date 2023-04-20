package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.operands.table.Cell;
import zhao.algorithmMagic.utils.ASClass;
import zhao.algorithmMagic.utils.ASIO;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;

/**
 * AS库中的模型对象，模型中包含一个函数，通过该函数可以实现诸多的处理操作，由该接口实现的模型将可以不受限制的传递给 AlgorithmStar 对象去执行，目前已经实现了一些基本模型框架。
 * <p>
 * The model object in the AS library contains a function that enables various processing operations. The model implemented by this interface can be passed unrestricted to the AlgorithmStar object for execution. Currently, some basic model frameworks have been implemented.
 *
 * @param <K> 当前模型能够接收的配置名称数据类型。
 * @param <I> 当前模型能够接收的操作数输入类型。
 *            <p>
 *            The input types of operands that the current model can accept.
 * @param <O> 当前模型能够接收的操作数输出类型。
 *            <p>
 *            The type of operand output that the current model can receive.
 */
public interface ASModel<K, I, O> extends Serializable {

    /**
     * 单目标矩形轮廓识别绘制模型，该模型接收一个识别目标的数据样本，并将其在图像中的位置识别出来，具有单线程与多线程两种使用方式。
     */
    SingleTargetContour SINGLE_TARGET_CONTOUR = new SingleTargetContour();

    /**
     * 基于目标数据样本的有监督的图像分类模型，支持单线程与所现场两种计算方式。
     */
    TarImageClassification TAR_IMAGE_CLASSIFICATION = new TarImageClassification();

    /**
     * 单神经元的神经网络线性回归训练
     */
    LinearNeuralNetwork LINEAR_NEURAL_NETWORK = new LinearNeuralNetwork();

    /**
     * 针对模型进行设置。
     * <p>
     * Set up for the model.
     *
     * @param key   模型中配置的项目编号，一般情况下在实现类中都有提供静态参数编号。
     *              <p>
     *              The project number configured in the model is usually provided with a static parameter number in the implementation class.
     * @param value 模型中配置项的具体数据，其可以是任何类型的单元格对象。
     *              <p>
     *              The specific data of configuration items in the model can be any type of cell object.
     */
    void setArg(K key, @Nonnull Cell<?> value);

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    O function(I[] input);

    /**
     * 启动模型，将其中的操作数进行计算操作。
     * <p>
     * Start the model and calculate the operands in it.
     *
     * @param input 需要被计算的所有操作数对象。
     *              <p>
     *              All operand objects that need to be calculated.
     * @return 计算之后的结果数据，数据可以是任何类型。
     * <p>
     * The calculated result data can be of any type.
     */
    O functionConcurrency(I[] input);

    final class Utils {
        /**
         * 参数丢失异常抛出
         *
         * @param argName 参数名称
         */
        static void throwArgEqNull(String argName) {
            throw new OperatorOperationException(
                    "Calculation error, parameter [" + argName + "] cannot be empty. Please call the function: setArg(" + argName + ", SingletonCell.$(\"value\"))"
            );
        }

        /**
         * 参数数据类型异常抛出
         *
         * @param argName 参数名称
         * @param type    类型名称
         */
        static OperatorOperationException throwArgTypeERR(String argName, String type) {
            return new OperatorOperationException(
                    "The numerical type of parameter [" + argName + "] is incorrect, it should be of type [" + type + "]"
            );
        }

        /**
         * 将模型输出到指定的文件对象中。
         * <p>
         * The model cannot be saved to the local disk normally.
         *
         * @param file    指定的文件对象。
         *                <p>
         *                The specified file object.
         * @param asModel 需要被保存的模型对象。
         *                <p>
         *                The model object that needs to be saved.
         */
        public static void write(File file, ASModel<?, ?, ?> asModel) {
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(asModel);
            } catch (IOException e) {
                throw new OperatorOperationException("The model was not successfully saved to disk.", e);
            } finally {
                ASIO.close(objectOutputStream);
            }
        }

        /**
         * 将磁盘中的模型重新读取到内存中，并将其封装成为模型对象。
         * <p>
         * The model from the disk is re-read into memory and encapsulated as a model object.
         *
         * @param file 模型配置文件对象。
         * @param <k>  当前模型能够接收的配置名称数据类型。
         * @param <i>  当前模型能够接收的操作数输入类型。
         *             <p>
         *             The input types of operands that the current model can accept.
         * @param <o>  当前模型能够接收的操作数输出类型。
         *             <p>
         *             The type of operand output that the current model can receive.
         * @return 从磁盘中读取到的模型对象。
         * <p>
         * The model object read from the disk.
         */
        public static <k, i, o> ASModel<k, i, o> read(File file) {
            ObjectInputStream objectInputStream = null;
            ASModel<k, i, o> asModel1;
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(file));
                asModel1 = ASClass.transform(objectInputStream.readObject());
            } catch (IOException e) {
                throw new OperatorOperationException(e);
            } catch (ClassNotFoundException e) {
                throw new OperatorOperationException("The similarity you specified for reading does not exist in the runtime, possibly due to a lack of dependencies.", e);
            } finally {
                ASIO.close(objectInputStream);
            }
            return asModel1;
        }

        /**
         * 将磁盘中的模型重新读取到内存中，并将其封装成为模型对象。
         * <p>
         * The model from the disk is re-read into memory and encapsulated as a model object.
         *
         * @param file 模型配置文件对象。
         * @param <k>  当前模型能够接收的配置名称数据类型。
         * @param <i>  当前模型能够接收的操作数输入类型。
         *             <p>
         *             The input types of operands that the current model can accept.
         * @param <o>  当前模型能够接收的操作数输出类型。
         *             <p>
         *             The type of operand output that the current model can receive.
         * @return 从磁盘中读取到的模型对象。
         * <p>
         * The model object read from the disk.
         */
        public static <k, i, o> ASModel<k, i, o> read(URL file) {
            ObjectInputStream objectInputStream = null;
            ASModel<k, i, o> asModel1;
            try {
                objectInputStream = new ObjectInputStream(file.openStream());
                asModel1 = ASClass.transform(objectInputStream.readObject());
            } catch (IOException e) {
                throw new OperatorOperationException(e);
            } catch (ClassNotFoundException e) {
                throw new OperatorOperationException("The similarity you specified for reading does not exist in the runtime, possibly due to a lack of dependencies.", e);
            } finally {
                ASIO.close(objectInputStream);
            }
            return asModel1;
        }
    }

}
