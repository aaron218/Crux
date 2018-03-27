package net.newstring.crux.study.spider;

import java.lang.management.*;

/**
 * MBean
 *
 * @author lic
 * @date 2018/3/26
 */
public class MBean {

    public static void main(String[] args) {
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        ClassLoadingMXBean mxBean = ManagementFactory.getClassLoadingMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        System.out.println(bean.toString());
    }
}
