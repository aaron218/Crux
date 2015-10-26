package net.newString.crux.core.lang;

public class ObjectUtil {

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
