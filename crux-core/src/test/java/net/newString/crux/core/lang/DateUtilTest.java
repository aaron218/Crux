package net.newString.crux.core.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aaron on 10/16/2015.
 * 时间日期工具集
 */
public class DateUtilTest {

    @Test
    public void testGetInstance() throws Exception {

    }

    @Test
    public void testFormatDate() throws Exception {

    }

    @Test
    public void testParseDate() throws Exception {

    }

    @Test
    public void testGetNowDate() throws Exception {

    }

    @Test
    public void testGetNowTime() throws Exception {

    }

    @Test
    public void testGetNowMillsTime() throws Exception {

    }

    @Test
    public void testGetNowDotMillsTime() throws Exception {

    }

    @Test
    public void testGetPreciseMills() throws Exception {

    }

    @Test
    public void testGetMicroSeconds() throws Exception {
        Assert.assertNotEquals(DateUtil.getMicroSeconds(), null);
    }

    @Test
    public void testGetNanoSeconds() throws Exception {
        long nano_1 = DateUtil.getNanoSeconds();
        long nano2 = DateUtil.getNanoSeconds();
        Assert.assertNotEquals(nano_1, nano2);//nano time must not equal
    }
}