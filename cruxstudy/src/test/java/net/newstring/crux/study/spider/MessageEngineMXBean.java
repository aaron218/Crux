package net.newstring.crux.study.spider;

/**
 * MessageEngineMXBean
 *
 * @author lic
 * @date 2018/3/26
 */
public interface MessageEngineMXBean {

    //结束程序。
    public void stop();
    //查看程序是否暂停。
    public boolean isPaused();
    //暂停程序或者继续程序。
    public void pause(boolean pause);
    public Message getMessage();
    //修改message
    public void changeMessage(Message m);
}
