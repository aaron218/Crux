package net.newstring.crux.study.spider;

import javax.management.*;

/**
 * MessageEngine
 *
 * @author lic
 * @date 2018/3/26
 */
public class MessageEngine extends NotificationBroadcasterSupport implements MessageEngineMXBean {
    private long sequenceNumber = 1;

    public MessageEngine() {
        addNotificationListener(new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                System.out.println("*** Handling new notification ***");
                System.out.println("Message: " + notification.getMessage());
                System.out.println("Seq: " + notification.getSequenceNumber());
                System.out.println("*********************************");
            }
        }, null, null);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public void pause(boolean pause) {
        Notification n = new AttributeChangeNotification(this,
                sequenceNumber++, System.currentTimeMillis(),
                "Pause changed", "Paused", "boolean",
                Echo.pause, pause);
        Echo.pause = pause;
        this.sendNotification(n);
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public void changeMessage(Message m) {

    }

    // 规定可以发送的Notification Type，不在Type list中的Notification不会被发送。
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }
}