package net.newstring.crux.study.spider;

/**
 * enmu
 *
 * @author lic
 * @date 2018/3/27
 */
public class enmu {


    public static void main(String[] args) {
        for (STR str : STR.values()) {
            System.out.println(str+":"+str.ordinal()+":"+str.name());
        }
    }

    enum STR {
        TEST,value,Number1,Ping
    }
}
