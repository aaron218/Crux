package net.newString.crux.core.lang;

/**
 * Created by aaron on 10/19/2015.
 * 对象操作工具组
 */
public class ObjectUtil {


    /**
     * 对象转换成Integer 无法执行的情况返回null
     *
     * @param localObject 待处理对象
     * @return Integer值或者null
     */
    public static Integer getIntValue(Object localObject) {
        if (localObject == null) {
            return null;
        } else {
            Integer localIntValue;
            if (localObject instanceof Long) {
                localIntValue = ((Long) localObject).intValue();
            } else if (localObject instanceof Double) {
                localIntValue = ((Double) localObject).intValue();
            } else if (localObject instanceof Float) {
                localIntValue = ((Float) localObject).intValue();
            } else if (localObject instanceof Integer) {
                localIntValue = (Integer) localObject;
            } else {
                try {
                    localIntValue = Integer.parseInt(localObject.toString());
                } catch (Exception e) {
                    return null;
                }
            }
            return localIntValue;
        }
    }

    /**
     * 对象转换成Long对象 无法执行情况返回null
     *
     * @param localObject 待处理对象
     * @return 转换结果
     */
    public static Long getLongValue(Object localObject) {
        if (localObject == null) {
            return null;
        } else {
            Long localLongValue;
            if (localObject instanceof Long) {
                localLongValue = (Long) localObject;
            } else if (localObject instanceof Double) {
                localLongValue = ((Double) localObject).longValue();
            } else if (localObject instanceof Float) {
                localLongValue = ((Float) localObject).longValue();
            } else if (localObject instanceof Integer) {
                localLongValue = ((Integer) localObject).longValue();
            } else {
                try {
                    localLongValue = Long.parseLong(localObject.toString());
                } catch (Exception e) {
                    return null;
                }
            }

            return localLongValue;
        }

    }


}
