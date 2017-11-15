package net.newstring.crux.core.tools.model;

import net.newstring.crux.core.lang.BytesConvert;
import net.newstring.crux.core.lang.StringUtil;
import net.newstring.crux.core.tools.model.Exception.ModelConvertException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/7/22 11:06.
 * 核心的基本数据类型，在其他包和其他项目中使用，用于转换出通用数据 基于{@linkplain Map Map&lt;String, Object&gt;}
 * @author lic
 */
public class CruxItem extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 2629197375524473962L;
    private String descriptor;

    /**
     * 将对象自身转为byte数组，用于持久化存储
     *
     * @return bytes
     * @throws ModelConvertException
     */
    public byte[] toBytes() throws ModelConvertException {
        try {
            return BytesConvert.objToByte(this);
        } catch (IOException e) {
            throw new ModelConvertException(e);
        }
    }

    /**
     * 从byte数组中获得这个对象 静态方法 返回转换出的对象或者抛出异常
     * <br>只能转换{@link CruxItem} 和 {@linkplain Map Map&lt;String, Object&gt;}类型
     *
     * @param bytes 待转换数组
     * @return 结果 或者抛出异常
     * @throws ModelConvertException 异常
     */
    public static CruxItem fromBytes(byte[] bytes) throws ModelConvertException {
        try {
            Object obj = BytesConvert.byteToObject(bytes);
            if (obj instanceof CruxItem) {
                return (CruxItem) obj;
            }
            if (obj instanceof Map) {
                CruxItem cruxItem = new CruxItem();
                cruxItem.putAll((Map<String, Object>) obj);
                return cruxItem;
            }
        } catch (Exception e) {
            throw new ModelConvertException(e);
        }
        throw new ModelConvertException("ModelConvertException:" +
                "\n\tThe Object convert from bytes is not an instance of CruxItem!");
    }

    /**
     * 从Map中载入对象 必须要传入一个描述符key 并且其对应的数据必须有值
     *
     * @param map   待处理Map
     * @param descriptorKey 主键key
     * @return 对象或null
     */
    public static CruxItem fromMap(Map<?, ?> map, String descriptorKey) {
        if (StringUtil.isEmptyStr(descriptorKey) || map == null || map.size() == 0) {
            return null;
        }
        CruxItem item = new CruxItem();
        if (map.get(descriptorKey) != null) {
            item.setDescriptor(map.get(descriptorKey).toString());
        } else {
            return null;
        }
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() instanceof String) {
                item.put((String) entry.getKey(), entry.getValue());
            } else {
                item.put(entry.getKey().toString(), entry.getValue());
            }
        }
        return item;
    }

    /**
     * 从Map中载入对象  descriptor直接设置为null
     * @param map  待处理Map
     * @return 对象或null
     */
    public static CruxItem fromMap(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        CruxItem item = new CruxItem();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() instanceof String) {
                item.put((String) entry.getKey(), entry.getValue());
            } else {
                item.put(entry.getKey().toString(), entry.getValue());
            }
        }
        return item;
    }

    /**
     * descriptor 可能会为null
     *
     * @return descriptor 描述符
     */
    public String getDescriptor() {
        return descriptor;
    }

    public CruxItem setDescriptor(String descriptor) {
        this.descriptor = descriptor;
        return this;
    }
}
