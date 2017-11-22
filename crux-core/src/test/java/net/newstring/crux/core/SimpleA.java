package net.newstring.crux.core;

import org.junit.Test;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Created on 2016/8/9 14:03.
 *
 * @author lic
 */
public class SimpleA {


    /**
     *
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> get(){
        return null;
    }


    @Test
    public void pow(){

        long start =  System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
           UUID.randomUUID().toString().replaceAll("-", "");
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

        start =  System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            moveSlash(UUID.randomUUID().toString());
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(moveSlash(UUID.randomUUID().toString()));

        start =  System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            hardLink(UUID.randomUUID().toString());
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(hardLink(UUID.randomUUID().toString()));

        System.out.println(System.currentTimeMillis());

    }


    private String moveSlash(String str){
        char[] chars = str.toCharArray();
        char[] result = new char[32];
        int p = 0;
        for (char aChar : chars) {
            if (aChar != '-') {
                result[p] = aChar;
                p++;
            }
        }
        return new String(result);
    }

    private String hardLink(String str){
        char[] chars = str.toCharArray();
        char[] result = new char[32];
        result[0] = chars[0];
        result[1] = chars[1];
        result[2] = chars[2];
        result[3] = chars[3];
        result[4] = chars[4];
        result[5] = chars[5];
        result[6] = chars[6];
        result[7] = chars[7];
        result[8] = chars[9]; //skip 8
        result[9] = chars[10];
        result[10] = chars[11];
        result[11] = chars[12];
        result[12] = chars[14];//skip 13
        result[13] = chars[15];
        result[14] = chars[16];
        result[15] = chars[17];
        result[16] = chars[19];
        result[17] = chars[20];//ship 18
        result[18] = chars[21];
        result[19] = chars[22];
        result[20] = chars[24];
        result[21] = chars[25];//ship 23
        result[22] = chars[26];
        result[23] = chars[27];
        result[24] = chars[28];
        result[25] = chars[29];
        result[26] = chars[30];
        result[27] = chars[31];
        result[28] = chars[32];
        result[29] = chars[33];
        result[30] = chars[34];
        result[31] = chars[35];
        return new String(result);
    }

}
