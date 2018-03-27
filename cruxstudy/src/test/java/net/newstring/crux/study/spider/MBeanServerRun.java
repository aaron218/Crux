package net.newstring.crux.study.spider;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * MBeanServerRun
 *
 * @author lic
 * @date 2018/3/26
 */
public class MBeanServerRun {


    public static Message msg = new Message();
    public static boolean running = true;
    public static boolean pause = false;

    public static void main(String[] args) {
        new MessageEngineAgent().start();

        while (running) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!pause) msg.echo();
        }

    }


    public static class MessageEngineAgent {
        public void start() {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            try {
                ObjectName mxbeanName = new ObjectName("com.example:type=MessageEngine");
                MessageEngineMXBean mxbean = new MessageEngine();
                mbs.registerMBean(mxbean, mxbeanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


