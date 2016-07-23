package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

import java.math.BigDecimal;

public class ObjectUtil {


    /**
     * 从对象中解析出 Double 值 通过类型操作而非字符串操作获取 无法转换的则返回 null
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     *
     * @param localObject 源对象
     * @return 结果
     */
    @stable
    public static Double toDouble(final Object localObject){
        return toDouble(localObject,null);
    }

    /**
     * 从对象中解析出 Float 值 通过类型操作而非字符串操作获取 无法转换的则返回 null
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     *
     * @param localObject 源对象
     * @return 结果
     */
    @stable
    public static Float toFloat(final Object localObject){
        return toFloat(localObject,null);
    }

    /**
     * 从对象中解析出 Long 值 通过类型操作而非字符串操作获取 无法转换的则返回 null
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     *
     * @param localObject 源对象
     * @return long 或 null
     */
    @stable
    public static Long toLong(final Object localObject){
        return toLong(localObject,null);
    }

    /**
     * 从对象中解析出 Integer 值 通过类型操作而非字符串操作获取 无法转换的则返回 null
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     *
     * @param localObject 源对象
     * @return int 或 null
     */
    @stable
    public static Integer toInteger(final Object localObject){
        return toInteger(localObject,null);
    }

    /**
     * 对象转换为字符串 原始对象为null的转换为默认值
     * @param localObject 待处理对象
     * @param defaultVal 默认值
     * @return 结果字符串
     */
    @stable
    public static String toString(Object localObject,String defaultVal){
        if(localObject==null){
            return defaultVal;
        }
        if(localObject instanceof String){
            return (String) localObject;
        }
        return  localObject.toString();
    }

    /**
     * 从对象中解析出 double 值 通过类型操作而非字符串操作获取 无法转换的则返回defaultVal
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     * 如果传入的数据不属于JVM数据类型，则会进行{@link Double#parseDouble}转换
     *
     * @param localObject 待处理对象
     * @return Int 或 defaultVal
     */
    @stable
    public static Double toDouble(final Object localObject,final Double defaultVal) {
        if (localObject == null) {
            return defaultVal;
        } else {
            Double localValue;
            if (localObject instanceof Long) {
                localValue = (double) (Long) localObject;
            } else if (localObject instanceof Double) {
                localValue = ((Double) localObject);
            } else if (localObject instanceof Float) {
                localValue = (double) (Float) localObject;
            } else if (localObject instanceof Integer) {
                localValue = (double) (Integer) localObject;
            } else if (localObject instanceof BigDecimal) {
                localValue = ((BigDecimal) localObject).doubleValue();
            } else {
                try {
                    if (localObject instanceof String) {
                        localValue = Double.parseDouble((String) localObject);
                    } else {
                        localValue = Double.parseDouble(localObject.toString());
                    }
                } catch (Exception e) {
                    return defaultVal;
                }
            }
            return localValue;
        }
    }

    /**
     * 从对象中解析出 float 值 通过类型操作而非字符串操作获取 无法转换的则返回 defaultVal
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     * 如果传入的数据不属于JVM数据类型，则会进行{@link Float#parseFloat}转换
     *
     * @param localObject 待处理对象
     * @return Int 或 defaultVal
     */
    @stable
    public static Float toFloat(final Object localObject,final Float defaultVal) {
        if (localObject == null) {
            return defaultVal;
        } else {
            Float localValue;
            if (localObject instanceof Long) {
                localValue = (float) (Long) localObject;
            } else if (localObject instanceof Double) {
                localValue = ((Double) localObject).floatValue();
            } else if (localObject instanceof Float) {
                localValue = ((Float) localObject);
            } else if (localObject instanceof Integer) {
                localValue = (float) (Integer) localObject;
            } else if (localObject instanceof BigDecimal) {
                localValue = ((BigDecimal) localObject).floatValue();
            } else {
                try {
                    if (localObject instanceof String) {
                        localValue = Float.parseFloat((String) localObject);
                    } else {
                        localValue = Float.parseFloat(localObject.toString());
                    }
                } catch (Exception e) {
                    return defaultVal;
                }
            }
            return localValue;
        }
    }

    /**
     * 从对象中解析出 int 值 通过类型操作而非字符串操作获取 无法转换的则返回 defaultVal
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     * 如果传入的数据不属于JVM数据类型，则会进行{@link Integer#parseInt}转换
     *
     * @param localObject 待处理对象
     * @return Int 或 defaultVal
     */
    @stable
    public static Integer toInteger(final Object localObject,final Integer defaultVal) {
        if (localObject == null) {
            return defaultVal;
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
            } else if (localObject instanceof BigDecimal) {
                localIntValue = ((BigDecimal) localObject).intValue();
            } else {
                try {
                    localIntValue = Integer.parseInt(localObject.toString());
                } catch (Exception e) {
                    return defaultVal;
                }
            }
            return localIntValue;
        }
    }

    /**
     * 从对象中解析出 Long 值 通过类型操作而非字符串操作获取 无法转换的则返回 defaultVal
     * 如果数据转换超出数值上限，将会丢失超出部分的数值
     * 如果传入的数据不属于JVM数据类型，则会进行{@link Long#parseLong}转换
     *
     * @param localObject 待处理对象
     * @return Long 或 defaultVal
     */
    @stable
    public static Long toLong(final Object localObject,final Long defaultVal) {
        if (localObject == null) {
            return defaultVal;
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
            } else if (localObject instanceof BigDecimal) {
                localLongValue = ((BigDecimal) localObject).longValue();
            } else {
                try {
                    if (localObject instanceof String) {
                        localLongValue = Long.parseLong((String) localObject);
                    } else {
                        localLongValue = Long.parseLong(localObject.toString());
                    }
                } catch (Exception e) {
                    return defaultVal;
                }
            }
            return localLongValue;
        }
    }


}
