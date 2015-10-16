package net.newString.crux.core.lang;


import net.newString.crux.core.stable;

import java.io.*;

/**
 * byte数组和位操作的工具 参考价具体业务和特殊需求使用，使用前先阅读相应的代码 勿滥用
 * <p/>
 * codeReview @ 20150701
 */
@stable("lic")
public class ByteUtil {

    //1字节整数

    /**
     * 将一个字节数组首个字节转换为short类型 半长short
     *
     * @param b 字节数组
     * @return short数据 半长
     */
    @stable
    public static short byte2sShort(byte b[]) {
        if (b.length < 1)
            return 0;
        return (short) (b[0] & 0xff);
    }

    /**
     * 将字节数组中指定的字节转换为半长short
     *
     * @param b      字节数组
     * @param offset 偏移量
     * @return 半长short
     */
    @stable
    public static short byte2sShort(byte b[], int offset) {
        if (offset > b.length - 1)
            return 0;
        else
            return (short) (b[offset] & 0xff);
    }


    //2字节整数

    /**
     * 将字节数组前两个字节转成short
     *
     * @param b 字节数组
     * @return short
     */
    @stable
    public static short byte2short(byte b[]) {
        if (b.length < 2)
            return 0;

        return (short) ((b[1] & 0xff) | (b[0] & 0xff) << 8);
    }

    /**
     * 将字节数组指定位置后面两位字节转换成short offset=0时相当于 {@linkplain ByteUtil#byte2short 首部转换为short}
     *
     * @param b      字节数组
     * @param offset 偏移量 最大为 b.length - 3
     * @return short
     */
    @stable
    public static short byte2short(byte b[], int offset) {
        if (offset > b.length - 2)
            return 0;
        else if (offset == (b.length - 1))
            return (short) (b[offset] & 0xff);
        else
            return (short) ((b[offset + 1] & 0xff) | (b[offset] & 0xff) << 8);
    }


    //4字节整数

    /**
     * 从字节数组前四个位置取出字节，封装成int数字
     *
     * @param b 字节数组
     * @return int
     */
    @stable
    public static int byte2int(byte b[]) {
        if (b.length < 4)
            return 0;
        return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
                | (b[0] & 0xff) << 24;
    }

    /**
     * 从字节数组指定位置获取4个字节，封装成int数字
     *
     * @param b      字节数组
     * @param offset 偏移量 最大为 b.length - 5
     * @return int
     */
    @stable
    public static int byte2int(byte b[], int offset) {
        if (offset > b.length - 4) return 0;
        return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8
                | (b[offset + 1] & 0xff) << 16 | (b[offset] & 0xff) << 24;
    }

    //wchar,只有2个字节长度

    /**
     * 返回wchar iso-10646-ucs-2字符集（也即UTF-16）
     *
     * @param b 字节数组
     * @return UTF-16的字符串
     */
    @stable
    public static String byte2wchar(byte[] b) {
        if (b == null || b.length < 2)
            return "";
        try {
            return new String(b, 0, 2, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, 0, 2);
        }
    }

    /**
     * 按照偏移量 返回wchar iso-10646-ucs-2字符集（也即UTF-16）
     *
     * @param b      字节数组
     * @param offset 偏移量
     * @return wchar
     */
    @stable
    public static String byte2wchar(byte[] b, int offset) {
        if (b == null || b.length < offset + 2)
            return "";
        try {
            return new String(b, offset, 2, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, offset, 2);
        }
    }

    /**
     * 从偏移量开始获取8个字节封装成float
     *
     * @param b      字节数组
     * @param offset 偏移量
     * @return float
     */
    @stable
    public static float byte2float(byte b[], int offset) {
        if (offset > b.length - 8) return 0;
        int h = byte2int(b, offset);
        int l = byte2int(b, offset + 4);
        return Float.parseFloat(h + "." + l);
    }


    //String,长度放在第一个字节内

    /**
     * 返回字符串，字符串长度在第一个字节内 （超限不处理）
     *
     * @param b 字节数组
     * @return 字符串 UTF-16
     */
    @stable
    public static String byte2string(byte[] b) {
        if (b == null || b.length == 0 || (b[0] == 0))
            return "";
        byte len = b[0];
        if (len <= 0)
            return "";
        try {
            return new String(b, 1, (int) len, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, 1, (int) len);
        }
    }

    /**
     * 返回偏移量后一位开始的字符串，字符串长度在偏移量定义的字节内 （超限不处理）
     *
     * @param b      字节数组
     * @param offset 偏移量
     * @return 字符串 UTF-16
     */
    @stable
    public static String byte2string(byte[] b, int offset) {
        if (b == null || b.length == 0 || b.length == offset
                || (b[offset] == 0))
            return "";

        byte len = b[offset];
        if (len <= 0)
            return "";
        try {
            return new String(b, offset + 1, (int) len, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, offset + 1, (int) len);
        }
    }


    //LString,长度放在前两个字节内

    /**
     * 返回字符串，字符串长度在前两个字节内 （超限不处理）
     *
     * @param b 字节数组
     * @return 字符串 UTF-16
     */
    @stable
    public static String byte2lstring(byte[] b) {
        if (b == null || b.length < 2)
            return "";
        short len = byte2short(b);
        if (len <= 0)
            return "";
        try {
            return new String(b, 2, (int) len, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, 2, (int) len);
        }
    }

    /**
     * 返回偏移量后两位开始的字符串，字符串长度在偏移量定义两个的字节内 （超限不处理）
     *
     * @param b      字节数组
     * @param offset 偏移量
     * @return 字符串 UTF-16
     */
    @stable
    public static String byte2lstring(byte[] b, int offset) {
        if (b == null || b.length < offset + 2)
            return "";
        short len = byte2short(b, offset);
        if (len <= 0)
            return "";
        try {
            return new String(b, offset + 2, (int) len, "iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(b, offset + 2, (int) len);
        }
    }


    //=================================================

    /**
     * short转换为两字节
     *
     * @param n short
     * @return 两字节
     */
    @stable
    public static byte[] short2byte(int n) {
        byte b[] = new byte[2];
        b[0] = (byte) (n >> 8);
        b[1] = (byte) n;
        return b;
    }

    /**
     * 将short写入偏移量定义位数开始的两个字节内
     *
     * @param n      short
     * @param buf    待装载的字节数组
     * @param offset 偏移量
     */
    @stable
    public static void short2byte(short n, byte buf[], int offset) {
        if (offset > buf.length - 1) return;
        else if (offset == buf.length - 1) buf[offset] = (byte) n;
        else {
            buf[offset] = (byte) (n >> 8);
            buf[offset + 1] = (byte) n;
        }
    }

    /**
     * 将int转换为4字节数组
     *
     * @param n int
     * @return 4字节数组
     */
    @stable
    public static byte[] int2byte(int n) {
        byte b[] = new byte[4];
        b[0] = (byte) (n >> 24);
        b[1] = (byte) (n >> 16);
        b[2] = (byte) (n >> 8);
        b[3] = (byte) n;
        return b;
    }

    /**
     * 将int装载到偏移量定义位数开始的四个字节中
     *
     * @param n      int数值
     * @param buf    待装载字节数组
     * @param offset 偏移量
     */
    @stable
    public static void int2byte(int n, byte buf[], int offset) {
        buf[offset] = (byte) (n >> 24);
        buf[offset + 1] = (byte) (n >> 16);
        buf[offset + 2] = (byte) (n >> 8);
        buf[offset + 3] = (byte) n;
    }

    /**
     * 将float转换到8位字节数组，前四位是整数部分，后四位是小数部分
     *
     * @param f float
     * @return 字节数组
     */
    @stable
    public static byte[] float2byte(float f) {
        byte b[] = new byte[8];
        String[] sf = (f + "").split(".");
        int2byte(Integer.parseInt(sf[0]), b, 0);
        int2byte(Integer.parseInt(sf[1]), b, 4);
        return b;
    }

    /**
     * 将一个char转换为字节数组
     *
     * @param value char
     * @return 字节数组
     */
    @stable
    public static byte[] wchar2byte(char value) {
        byte[] bytes;
        try {
            bytes = new String(new char[]{value})
                    .getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = new byte[]{0, 0};
        }
        return bytes;
    }

    /**
     * @param value 字符串
     * @return 字节数组
     */
    @stable
    public static byte[] string2byte(String value) {
        if (value == null || value.trim().length() == 0) return new byte[]{0};
        byte[] data;
        try {
            data = value.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = value.getBytes();
        }
        byte len = (byte) data.length;
        byte[] bytes = new byte[len + 1];
        bytes[0] = len;
        System.arraycopy(data, 0, bytes, 1, len);
        return bytes;
    }

    /**
     * 将字符串装载到字节数组
     *
     * @param value  字符串
     * @param bytes  字节数组
     * @param offset 偏移量
     * @return 成功返回实际长度
     */
    @stable
    public static int string2byte(String value, byte[] bytes, int offset) {
        if (value == null || value.trim().length() == 0) {
            bytes[offset] = 0;
            return 1;
        }
        byte[] data;
        try {
            data = value.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = value.getBytes();
        }
        byte len = (byte) data.length;

        bytes[offset] = len;
        System.arraycopy(data, 0, bytes, offset + 1, len);
        return len + 1;
    }

    /**
     * 将较长的字符串转换为字节数组 数组前两位是字符串长度
     *
     * @param value 字符串
     * @return 字节数组
     */
    @stable
    public static byte[] lstring2byte(String value) {
        if (value == null || value.trim().length() == 0) {
            return new byte[]{0, 0};
        }
        byte[] data;
        try {
            data = value.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = value.getBytes();
        }
        short len = (short) data.length;
        byte[] lenBytes = short2byte(len);
        byte[] bytes = new byte[len + 2];
        System.arraycopy(lenBytes, 0, bytes, 0, 2);
        System.arraycopy(data, 0, bytes, 2, len);
        return bytes;
    }

    /**
     * 将字符串按照偏移量装载进字节数组
     *
     * @param value  字符串
     * @param bytes  字节数组
     * @param offset 偏移量
     * @return 成功返回实际长度
     */
    @stable
    public static int lstring2byte(String value, byte[] bytes, int offset) {
        if (value == null || value.trim().length() == 0) {
            bytes[offset] = 0;
            bytes[offset + 1] = 0;
            return 2;
        }
        byte[] data;
        try {
            data = value.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = value.getBytes();
        }
        short len = (short) data.length;
        byte[] lenBytes = short2byte(len);

        System.arraycopy(lenBytes, 0, bytes, offset, 2);
        System.arraycopy(data, 0, bytes, offset + 2, len);
        return len + 2;
    }

    /**
     * 将一个比特值发送到一个字节的特定位
     *
     * @param b   原字节
     * @param pos 偏移量
     * @param bit 比特
     * @return 返回字节
     */
    @stable
    public static byte putBit(byte b, int pos, boolean bit) {
        byte a = 0;
        if (bit) a = 1;
        a = (byte) (a << pos - 1);
        return (byte) (b | a);

    }

    /**
     * 从一个字节中取出某一位的值
     *
     * @param b   字节
     * @param pos 位置
     * @return 用于标识的字节
     */
    @stable
    public static byte getBit(byte b, int pos) {
        return (byte) ((b >> (pos - 1)) & 1);
    }

    //=========================================================

    /**
     * 将字节数组作为字符串打印出来
     *
     * @param bytes 字节数组
     * @return 字符串，{}包围，英文逗号分割
     */
    @stable
    public static String printBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (byte bt : bytes) {
            sb.append(bt).append(", ");
        }
        sb.append("}");
        return sb.toString();
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

}
