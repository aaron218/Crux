package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

import java.util.Map;

/**
 * Created by aaron on 10/19/2015.
 * ��ȡMap�е����ݣ������Map��ָ����ʵ��Map�ӿڵĶ���
 * ����ֻ��Map[String��Object]��ʽʹ�ã����Ǳ�ʾ���ã�������ʽ�¿��ܻ����
 * <br>����������һ�����ڴ�Mongo Solr�ȵ��͵�Mapϵͳ�л�ȡ���ݣ�
 */
@stable("lic")
public class MapUtil {
    /**
     * �ж�һ�������ǲ���Map���ͣ�ʹ��instanceof ����ֻΪ�򻯱���
     *
     * @param object ���ж�����
     * @return �Ƿ���Map
     */
    public static boolean isMap(Object object) {
        return object instanceof Map;
    }

    /**
     * �ж϶������Ƿ��и��ֶ� ���ڷ�Map�Լ��������󣬷���false
     *
     * @param object ���������
     * @param field  �ֶ���
     * @return �Ƿ���� ����Object��ΪMapʱ����false
     */
    public static boolean hasField(Object object, String field) {
        if (!isMap(object)) {
            return false;
        }
        return ((Map) object).get(field) == null;
    }

    /**
     * ��Map�����л�ȡIntֵ��ʹ��{@link StringUtil#objToInteger(Object)}ת�� �����������null
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
