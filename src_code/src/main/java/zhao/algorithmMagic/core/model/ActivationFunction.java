package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.exception.OperatorOperationException;
import zhao.algorithmMagic.utils.ASMath;

/**
 * 激活函数枚举类，其中每一个枚举选项都属于一个激活函数，每种激活函数中包含前向传播以及反向求导函数的实现。
 * <p>
 * Activation function enumeration class, where each enumeration option belongs to a activation function, and each activation function includes the implementation of forward propagation and reverse derivation functions.
 *
 * @author 赵凌宇
 * 2023/4/19 14:10
 */
public enum ActivationFunction {

    None {
        private final OperatorOperationException OPERATOR_OPERATION_EXCEPTION = new OperatorOperationException("Please select a activation function.");

        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            throw OPERATOR_OPERATION_EXCEPTION;
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            throw OPERATOR_OPERATION_EXCEPTION;
        }
    },
    RELU {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return x < 0 ? 0 : x;
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return x < 0 ? 0 : 1;
        }
    },
    LEAKY_RE_LU {

        private double learningRate = 0.01;

        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {
            this.learningRate = value;
        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return x < 0 ? 0 : x;
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return x < 0 ? learningRate * x : 1;
        }
    },
    SOFT_SIGN {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return x / (1 + ASMath.absoluteValue(x));
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return x / ASMath.Power2(1 + ASMath.absoluteValue(x));
        }
    },
    SIG_MOD {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return 1 + 1 / (1 + Math.pow(Math.E, -x));
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            double fun = function(x);
            return fun * (1 - fun);
        }
    };

    /**
     * 设置学习率
     *
     * @param value 学习率具体的数值。
     */
    public abstract void setLearnR(double value);

    /**
     * 激活函数正向传播计算函数
     *
     * @param x 函数形参
     * @return 激活函数正向传播计算的结果。
     */
    public abstract double function(double x);

    /**
     * 激活函数反向求导数的计算函数
     *
     * @param x 函数形参
     * @return 激活函数反向传播的导数的数值。
     */
    public abstract double derivativeFunction(double x);
}
