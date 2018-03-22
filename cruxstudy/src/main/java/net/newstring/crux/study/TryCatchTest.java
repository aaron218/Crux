package net.newstring.crux.study;

import java.util.UUID;

/**
 * TryCatchTest
 *
 * @author lic
 * @date 2018/3/6
 */
public class TryCatchTest {

    public static void main(String[] args) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        for (int i = 0; i < 1000000; i++) {
            UUID.randomUUID().toString();
        }
        System.out.println("without try-catch block:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            try {
                UUID.randomUUID().toString();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("in try-catch block:" + (System.currentTimeMillis() - start));

    }
}
