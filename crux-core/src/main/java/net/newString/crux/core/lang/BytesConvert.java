package net.newString.crux.core.lang;

import net.newString.crux.core.CruxCoreException;

import java.io.*;

/**
 *
 * 字节码信息的转换，与{@linkplain ByteUtil 二进制基本工具}不同，侧重于数据转换、类型检验、简单的序列化等操作
 * 而非数据提取和基本二进制操作
 * @author lic
 */
public abstract class BytesConvert {

    private BytesConvert() {
    }

    /**
     * 将对象转换成Byte，注意，对象以及其组合属性应该均为基本属性或者实现序列化的接口
     *
     * @param obj 传入对象
     * @return byte数组
     * @throws IOException
     */
    public static byte[] objToByte(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        objectOutputStream.close();
        return bytes;
    }

    /***
     * 将Byte数组转换成对象 注意可能部分属性为不可序列化导致对象不完全可用
     *
     * @param bytes 传入字符串
     * @return 对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object object = objectInputStream.readObject();
        inputStream.close();
        objectInputStream.close();
        return object;
    }

    /**
     * 将byte数组转换成指定类型的对象，如果传入的类型为空，则返回null
     * @param bytes 待处理byte数组
     * @param clazz 目标类型
     * @param <T> 目标类型模板类
     * @return 目标结果或者null
     * @throws CruxCoreException 再封装的异常
     */
    public static <T> T byteToClass(byte[] bytes,Class<T> clazz) throws CruxCoreException{
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            Object object = objectInputStream.readObject();
            inputStream.close();
            objectInputStream.close();
            @SuppressWarnings("unckecked")
            T t = (T) object;
            return t;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
           throw new CruxCoreException(e);
        }
    }
}
