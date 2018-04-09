package net.newstring.crux.study.spider;

import org.junit.Test;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * DateTest
 *
 * @author lic
 * @date 2018/3/28
 */
public class DateTest {

    @Test
    public void LocalDate(){
        LocalDate localDate  = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        //包括时区转换
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(8)));
        System.out.println(date);
    }

    public enum TestVal implements Serializable{

    }
}
