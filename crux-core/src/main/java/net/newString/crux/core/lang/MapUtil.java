package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

import java.util.Map;

/**
 * Created by aaron on 10/19/2015.
 * 获取Map中的数据，这里的Map是指所有实现Map接口的对象
 * 建议只对Map[String，Object]形式使用，除非表示可用，其他形式下可能会出错
 * <br>（本工具组一般用于从Mongo Solr等典型的Map系统中获取数据）
 */
@stable("lic")
public class MapUtil {
    /**
     * 判定一个对象是不是Map类型，使用instanceof 方法只为简化编码
     *
     * @param object 待判定对象
     * @return 是否是Map
     */
    public static boolean isMap(Object object) {
        return object instanceof Map;
    }

    /**
     * 判断对象中是否有该字段 对于非Map以及其他错误，返回false
     *
     * @param object 待处理对象
     * @param field  字段名
     * @return 是否存在 或者Object不为Map时返回false
     */
    public static boolean hasField(Object object, String field) {
        if (!isMap(object)) {
            return false;
        }
        return ((Map) object).get(field) == null;
    }

    /**
     * 从Map对象中获取Int值，使用{@link StringUtil#objToInteger(Object)}转换 错误情况返回null
     *
     * @param key
     * @param object
     * @return
     */
    public static Integer getIntValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return ObjectUtil.getIntValue(((Map) object).get(key));
    }

    public static String getStrValue(Object object, String key) {
        if (!isMap(object))
            return null;
        return StringUtil.obj2Str(((Map) object).get(key));
    }

    public static Long getLongValue(Object object, String key) {
        if (!isMap(object))
            return null;
        return StringUtil.objToLong(((Map) object).get(key));
    }

    public static Boolean getBoolValue(Object object, String key) {
        if (!isMap(object))
            return null;
        return StringUtil.objToBoolean(((Map) object).get(key));
    }

    public static Double getDoubleValue(Object object, String key) {
        if (!isMap(object))
            return null;
        return StringUtil.objToDouble(((Map) object).get(key));
    }

    public static Float getFloatValue(Object object, String key) {
        if (!isMap(object))
            return null;
        return StringUtil.objToFloat(((Map) object).get(key));
    }
}
