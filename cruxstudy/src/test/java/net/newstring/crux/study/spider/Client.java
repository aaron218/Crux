package net.newstring.crux.study.spider;

import com.sun.deploy.util.SessionState;

import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Client
 *
 * @author lic
 * @date 2018/3/26
 */
public class Client implements NotificationListener {

    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        // 如果agent不做配置的话，默认jndi path为jmxrmi
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection server = jmxc.getMBeanServerConnection();
        ObjectName mbeanName = new ObjectName("com.example:type=MessageEngine");
        // 订阅Notification
        server.addNotificationListener(mbeanName, this, null, null);

        // 访问paused属性。
        boolean paused = (Boolean)server.getAttribute(mbeanName, "Paused");
        System.out.println(paused);
        if (!paused) {
            server.invoke(mbeanName, "pause", new Object[]{true}, new String[]{"boolean"});
        }
        // 构建一个jmx.Message类型实例。
        CompositeType msgType = new CompositeType("jmx.Message", "Message Class Name",
                new String[]{"title","body", "by"},
                new String[]{"title","body", "by"},
                new OpenType[]{SimpleType.STRING,SimpleType.STRING,SimpleType.STRING});
        CompositeData msgData = new CompositeDataSupport(msgType,
                new String[]{"title","body","by"},
                new Object[]{"Hello", "This is a new message.", "xpbug"});
        // 调用changeMessage方法
        server.invoke(mbeanName, "changeMessage", new Object[]{msgData}, new String[]{CompositeData.class.getName()});
        server.invoke(mbeanName, "pause", new Object[]{false}, new String[]{"boolean"});

        // 访问修改后的Message属性。
        msgData = (CompositeData)server.getAttribute(mbeanName, "Message");
        System.out.println("The message changes to:");
        System.out.println(msgData.get("title"));
        System.out.println(msgData.get("body"));
        System.out.println(msgData.get("by"));

        Thread.sleep(1000*10);
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("*** Handling new notification ***");
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Seq: " + notification.getSequenceNumber());
        System.out.println("*********************************");
    }
}