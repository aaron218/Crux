package net.newstring.crux.study.plugin;


import java.util.UUID;

/**
 * UUIDTest
 *
 * @author lic
 * @date 2018/3/22
 */
public class UUIDTest {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(UUID.fromString(UUID.randomUUID().toString()).toString().replaceAll("-", ""));
        }
        System.out.println(UUID.fromString(UUID.randomUUID().toString()).version());
    }
}
