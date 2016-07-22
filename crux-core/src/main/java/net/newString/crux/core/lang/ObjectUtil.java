package net.newString.crux.core.lang;

public class ObjectUtil {

    /**
     * 从对象中解析出int值 通过类型操作而非字符串操作获取
     * @param localObject 待处理对象
     * @return Int 或 null
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
     * 从对象中解析出 long 值 通过类型操作而非字符串操作获取
     * @param localObject 待处理对象
     * @return Long 或 null
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
