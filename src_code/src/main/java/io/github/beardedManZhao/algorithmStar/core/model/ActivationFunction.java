package io.github.beardedManZhao.algorithmStar.core.model;

import io.github.beardedManZhao.algorithmStar.utils.ASMath;

/**
 * 激活函数枚举类，其中每一个枚举选项都属于一个激活函数，每种激活函数中包含前向传播以及反向求导函数的实现。
 * <p>
 * Activation function enumeration class, where each enumeration option belongs to an activation function, and each activation function includes the implementation of forward propagation and reverse derivation functions.
 *
 * @author 赵凌宇
 * 2023/4/19 14:10
 */
public enum ActivationFunction {

    None {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return x;
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return x;
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
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

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
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

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
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

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
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return 1 / (1 + Math.pow(Math.E, x));
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
    },
    ELU {
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
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return x > 0 ? x : this.learningRate * (Math.pow(Math.E, x) - 1);
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return x > 0 ? 1 : this.learningRate * Math.pow(Math.E, x);
        }
    },
    TANH {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {
        }

        /**
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return Math.tanh(x);
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            return 1 - Math.pow(function(x), 2);
        }
    },
    SIGMOID {
        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         */
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         */
        @Override
        public void setYVector(double... value) {

        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         */
        @Override
        public double function(double x) {
            return 1 / (1 + Math.pow(Math.E, -x));
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         */
        @Override
        public double derivativeFunction(double x) {
            double eP = function(x);
            return eP / (1 - eP);
        }
    };
/*
    SOFTMAX {

        private double denominator = 100;

        /**
         * 设置学习率
         *
         * @param value 学习率具体的数值。
         * /
        @Override
        public void setLearnR(double value) {

        }

        /**
         * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
         *
         * @param value 神经网络计算得到的结果数据。
         * /
        @Override
        public void setYVector(double... value) {
            this.denominator = 0;
            for (double v : value) {
                this.denominator += Math.exp(v);
            }
        }

        /**
         * 激活函数正向传播计算函数
         *
         * @param x 函数形参
         * @return 激活函数正向传播计算的结果。
         * /
        @Override
        public double function(double x) {
            System.out.println(Math.exp(x) + " / " + this.denominator);
            return Math.exp(x) / this.denominator;
        }

        /**
         * 激活函数反向求导数的计算函数
         *
         * @param x 函数形参
         * @return 激活函数反向传播的导数的数值。
         * /
        @Override
        public double derivativeFunction(double x) {
            return 0;
        }
    };
*/

    /**
     * 设置学习率
     *
     * @param value 学习率具体的数值。
     */
    public abstract void setLearnR(double value);

    /**
     * 设置神经网络的输出结果，在这里要将所有的结果提供给激活函数。
     *
     * @param value 神经网络计算得到的结果数据。
     */
    public abstract void setYVector(double... value);


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
