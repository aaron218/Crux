package net.newString.crux.core.lang;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aaron on 10/16/2015.
 *
 *
 */
public class MD5Test {

    @Test
    public void testGetMD5ofStr() throws Exception {
        MD5 md5 = new MD5();
        //System.out.println(md5.getMD5ofStr("12345"));
        assertNotEquals(md5.getMD5ofStr("12345"),null);
    }

    @Test
    public void testGetMD5ofByteArray() throws Exception {
        MD5 md5 = new MD5();
        assertNotEquals(md5.getMD5ofByteArray("12345").length,0);
    }
}