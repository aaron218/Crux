package net.newString.crux.core.lang;

/**
 * Created by aaron on 10/15/2015.
 * 产生唯一的数据，这里唯一的定义是在特定设备上的任意一个JVM上同时运行，产生的数据是唯一的。
 * 如果是多节点运行，则可以通过参数设定产生全局唯一的字符串
 * 字符串是全大写的，不包括小写字符，以适应大小写无关和相关的系统
 * <br>
 * 参照Mongo的Id生成规范，这里的默认生成ID规范中包括了一部分JVM信息。其中有：创建时间，JVM的PID
 */
public class IDUtil {
    private static final char[] chars = new char[32];

    public String getUniqueId() {
        return null;
    }


}
