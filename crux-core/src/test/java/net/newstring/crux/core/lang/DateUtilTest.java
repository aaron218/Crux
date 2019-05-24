package net.newstring.crux.core.lang;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author : cheng.lc
 * @version : 1.0
 * Create Date Time: 2019/5/24 17:04
 * Update Date Time:
 */
public class DateUtilTest {

    @Before
    public void info(){
        System.out.println("test:"+DateUtilTest.class.getCanonicalName()+" start..");
    }

    @Test
    public void formatDate() {
        Date date = new Date();
        String formatDate = DateUtil.formatDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN, date);
        Date parseDate = DateUtil.parseDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN, formatDate);
        Assert.assertEquals(date,parseDate);
    }

    @Test
    public void parseDate() {
        Date date = new Date();
        String formatDate = DateUtil.formatDate(DateUtil.DATE_TIME_PATTERN, date);
        Date parseDate = DateUtil.parseDate(DateUtil.DATE_TIME_PATTERN, formatDate);
        String formatDate2 = DateUtil.formatDate(DateUtil.DATE_TIME_PATTERN, parseDate);
        Assert.assertEquals(formatDate,formatDate2);
    }

    @Test
    public void getNowDate() {
        String nowDate = DateUtil.getNowDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN);
        Assert.assertNotNull(nowDate);
    }

    @Test
    public void getNowTime() {
    }

    @Test
    public void getNowMillsTime() {
    }

    @Test
    public void getNowDotMillsTime() {
    }

    @Test
    public void getPreciseMills() {
    }

    @Test
    public void getMicroSeconds() {
    }

    @Test
    public void getNanoSeconds() {
    }

    @Test
    public void formatDateToMillsLong() {
    }

    @Test
    public void formatDateToLong() {
    }

    @Test
    public void formatDateToDayLong() {
    }

    @Test
    public void formatDataToTimeStr() {
    }

    @Test
    public void formatDataToDayStr() {
    }

    @Test
    public void formatDataToMonthStr() {
    }
}