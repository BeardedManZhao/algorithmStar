package zhao.algorithmMagic.utils.filter;

import zhao.algorithmMagic.utils.Event;

public interface ArrayIntegerEvent extends Event<int[]> {
    /**
     * @param array     被判断或操作的数组
     * @param parameter 操作参数
     * @return 操作结果
     */
    boolean isComplianceEvents(int[] array, Integer... parameter);

    String getError();
}
