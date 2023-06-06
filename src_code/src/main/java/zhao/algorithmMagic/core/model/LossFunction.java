package zhao.algorithmMagic.core.model;

import zhao.algorithmMagic.utils.ASMath;

/**
 * @author 赵凌宇
 * 2023/4/27 9:33
 */
public enum LossFunction {

    MAE {
        /**
         * 使用当前损失函数计算出指定的损失数值。
         *
         * @param tempY 由模型生成的数值
         * @param tarY  目标数值
         * @return 当两个被计算的目标值之间的维度一致的时候，会按照自己固有的方式返回两个目标样本之间的损失数值。
         */
        @Override
        public double function(double[] tempY, double... tarY) {
            double sum = 0;
            int index = -1;
            for (double v : tempY) {
                sum += ASMath.absoluteValue(v - tarY[++index]);
            }
            return sum / tempY.length;
        }

        /**
         * 当前损失函数的导函数计算。
         *
         * @param loss 需要被计算导数的损失
         * @return 导数
         */
        @Override
        public double derivativeFunction(double loss) {
            return 0;
        }
    }, MSE {
        /**
         * 使用当前损失函数计算出指定的损失数值。
         *
         * @param tempY 由模型生成的数值
         * @param tarY  目标数值
         * @return 当两个被计算的目标值之间的维度一致的时候，会按照自己固有的方式返回两个目标样本之间的损失数值。
         */
        @Override
        public double function(double[] tempY, double... tarY) {
            double sum = 0;
            int index = -1;
            for (double v : tempY) {
                sum += ASMath.Power2(v - tarY[++index]);
            }
            return sum / tempY.length;
        }

        /**
         * 当前损失函数的导函数计算。
         *
         * @param loss 需要被计算导数的损失
         * @return 导数
         */
        @Override
        public double derivativeFunction(double loss) {
            return 0;
        }
    };

    /**
     * 使用当前损失函数计算出指定的损失数值。
     *
     * @param tempY 由模型生成的数值
     * @param tarY  目标数值
     * @return 当两个被计算的目标值之间的维度一致的时候，会按照自己固有的方式返回两个目标样本之间的损失数值。
     */
    public abstract double function(double[] tempY, double... tarY);

    /**
     * 当前损失函数的导函数计算。
     *
     * @param loss 需要被计算导数的损失
     * @return 导数
     */
    public abstract double derivativeFunction(double loss);
}
