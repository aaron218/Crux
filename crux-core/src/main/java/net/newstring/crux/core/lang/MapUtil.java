package net.newstring.crux.core.lang;

import net.newstring.crux.core.annotation.stable;

import java.util.Map;

/**
 */
@stable("lic")
public class MapUtil {
    /**
     */
    public static boolean isMap(Object object) {
        return object instanceof Map;
    }

    /**
     */
    public static boolean hasField(Object object, String field) {
        if (!isMap(object)) {
            return false;
        }
        return ((Map) object).get(field) == null;
    }

    /**
     * @param key
     * @param object
     * @return
     */
    public static Integer getIntValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return ObjectUtil.toInteger(((Map) object).get(key));
    }

    public static String getStrValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return StringUtil.obj2Str(((Map) object).get(key));
    }

    public static Long getLongValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return ObjectUtil.toLong(((Map) object).get(key));
    }

    public static Double getDoubleValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return ObjectUtil.toDouble(((Map) object).get(key));
    }

    public static Float getFloatValue(Object object, String key) {
        if (!isMap(object)) {
            return null;
        }
        return ObjectUtil.toFloat(((Map) object).get(key));
    }
}
