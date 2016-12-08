package net.newString.crux.core;

import org.junit.Test;

import java.util.Map;

/**
 * Created by Aaron on 2016/12/8.
 */
public class SystemTest {

    @Test
    public void runtimeTest() {
        Runtime.getRuntime().availableProcessors();

        Map<String, String> env = System.getenv();

        for (Map.Entry<String, String> entry : env.entrySet()) {
            if (entry.getValue() != null)
                System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        Runtime runtime = Runtime.getRuntime();

        System.out.println(runtime.freeMemory());
        System.out.println(runtime.availableProcessors());

        runtime.addShutdownHook(new Down());
        runtime.addShutdownHook(new Down(" new Down"));


    }


    class Down extends Thread {
        String name = "";

        public Down(String name) {
            this.name = name;
        }

        public Down() {
        }

        @Override
        public void run() {
            System.out.println("shutdown" + name);
        }
    }
}
