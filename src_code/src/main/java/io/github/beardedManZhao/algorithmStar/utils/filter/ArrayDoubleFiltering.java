package io.github.beardedManZhao.algorithmStar.utils.filter;

import io.github.beardedManZhao.algorithmStar.utils.Event;

/**
 * 数组过滤器，通过该抽象类的实现，能够过滤掉一些不满足条件的数组
 * <p>
 * Array filter, through the implementation of this abstract class, can filter out some arrays that do not meet the conditions
 *
 * @author zhao
 */
public interface ArrayDoubleFiltering extends Event<double[]> {
    ArrayDoubleFiltering TRUE = v -> true;
}
