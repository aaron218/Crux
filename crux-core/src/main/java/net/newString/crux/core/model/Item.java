package net.newString.crux.core.model;

import net.newString.crux.core.lang.ByteUtil;
import net.newString.crux.core.lang.BytesConvert;
import net.newString.crux.core.model.Exception.ModelConvertException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created on 2016/7/22 11:06.
 *
 * @author lic
 */
public class Item extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 2629197375524473962L;

    /**
     * 将对象自身转为byte数组，用于持久化存储
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
     * @param bytes 待转换数组
     * @return 结果 或者抛出异常
     * @throws ModelConvertException
     */
    public static Item fromBytes(byte[] bytes) throws ModelConvertException{
        try {
            Object obj = BytesConvert.byteToObject(bytes);
            if(obj instanceof Item){
                return (Item) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ModelConvertException(e);
        }
        throw new ModelConvertException("ModelConvertException:" +
                "\n\tThe Object convert from bytes is not an instance of Item!");
    }
}
