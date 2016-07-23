package net.newString.crux.core.lang;


import net.newString.crux.core.stable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by aaron on 6/23/2015.
 * 日期操作工具集
 */
@stable("lic")
public abstract class DateUtil {
    public static final String DATE_YYYYMMDD_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.S"; //标准输出包括毫秒数
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_HHMM_PATTERN = "HH:mm";
    public static final String TIME_HHMM_PATTERN2 = "HHmm";
    public static final String DATE_TIME_NO_HORI_PATTERN = "yyyyMMdd HH:mm:ss";
    public static final String DATE_TIME_NO_SPACE_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_TIME_NO_SPACE_PATTERN_Mills = "yyyyMMddHHmmssS";
    public static final String DATE_TIME_NO_SPACE_PATTERN_Dot_Mills = "yyyyMMddHHmmss.S";
    public static final String DATE_TIME_PLAYBILL_PATTERN = "yyyyMMdd HH:mm";
    public static final String DATE_ENGLISH_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";  //CST时间

    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_TIME_MS_PATTERN);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(DATE_YYYYMMDD_PATTERN);
    public static final SimpleDateFormat HHmm = new SimpleDateFormat(TIME_HHMM_PATTERN);
    public static final SimpleDateFormat HHmm2 = new SimpleDateFormat(TIME_HHMM_PATTERN2);
    public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat(DATE_TIME_NO_HORI_PATTERN);
    public static final SimpleDateFormat yyyyMMddHHmmssFile = new SimpleDateFormat(DATE_TIME_NO_SPACE_PATTERN);
    public static final SimpleDateFormat yyyyMMddHHmmssDotMills = new SimpleDateFormat(DATE_TIME_NO_SPACE_PATTERN_Dot_Mills);
    public static final SimpleDateFormat yyyyMMddHHmmssMills = new SimpleDateFormat(DATE_TIME_NO_SPACE_PATTERN_Mills);
    public static final SimpleDateFormat PLAYBILL_TIME_PATTERN = new SimpleDateFormat(DATE_TIME_PLAYBILL_PATTERN);
    public static final SimpleDateFormat ENGLISH_SDF = new SimpleDateFormat(DATE_ENGLISH_FORMAT, Locale.ENGLISH);
    /**
     * 计算时间的起始时间
     */
    // private static Log log = LogFactory.getLog(DateUtil.class);
    private static Map<String, SimpleDateFormat> patternFormatMap;


    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    public static Map<String, SimpleDateFormat> getInstance() {
        if (patternFormatMap == null) {
            patternFormatMap = new HashMap<String, SimpleDateFormat>();
            patternFormatMap.put(DATE_TIME_MS_PATTERN, timeFormat);
            patternFormatMap.put(DATE_TIME_PATTERN, dateFormat);
            patternFormatMap.put(DATE_YYYYMMDD_PATTERN, yyyyMMdd);
            patternFormatMap.put(TIME_HHMM_PATTERN, HHmm);
            patternFormatMap.put(TIME_HHMM_PATTERN2, HHmm2);
            patternFormatMap.put(DATE_TIME_NO_HORI_PATTERN, yyyyMMdd_HHmmss);
            patternFormatMap.put(DATE_TIME_NO_SPACE_PATTERN, yyyyMMddHHmmssFile);
            patternFormatMap.put(DATE_TIME_NO_SPACE_PATTERN_Mills, yyyyMMddHHmmssMills);
            patternFormatMap.put(DATE_TIME_NO_SPACE_PATTERN_Dot_Mills, yyyyMMddHHmmssDotMills);
            patternFormatMap.put(DATE_TIME_PLAYBILL_PATTERN, PLAYBILL_TIME_PATTERN);
            patternFormatMap.put(DATE_ENGLISH_FORMAT, ENGLISH_SDF);
        }
        return patternFormatMap;
    }

    /**
     * 根据传入的Pattern转换日期
     *
     * @param pattern 传入的pattern字符串，可以直接使用Util的字符
     * @param date    待转换日期
     * @return 转换日期
     */
    @stable
    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat sdf = DateUtil.getInstance().get(pattern);

        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            DateUtil.getInstance().put(pattern, sdf);
        }
        return sdf.format(date);
    }

    /**
     * 将字符串转换成Date对象，如果无法转换，将返回null
     *
     * @param pattern date pattern
     * @param dateStr 日期字符串
     * @return Date对象
     */
    @stable
    public static Date parseDate(String pattern, String dateStr) {
        SimpleDateFormat sdf = DateUtil.getInstance().get(pattern);
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            DateUtil.getInstance().put(pattern, sdf);
        }
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前时间，按照传入的pattern来返回
     *
     * @param pattern 传入的pattern 推荐直接使用工具包的静态对象
     * @return 当前时间的转换值
     */
    @stable
    public static String getNowDate(final String pattern) {
        return formatDate(pattern, new Date());
    }

    /**
     * 获取当前时间，按照默认的 无空格方式产生
     * 格式 yyyyMMddHHmmss (20150101115959)
     *
     * @return 返回的时间字符串
     */
    @stable
    public static String getNowTime() {
        return getNowDate(DATE_TIME_NO_SPACE_PATTERN);
    }

    /**
     * 获取当前时间，按照默认的 无空格方式产生 包括毫秒数
     * 格式（20150101123652512）= 2015-01-01 12:36:52.512
     *
     * @return 返回的时间字符串
     */
    @stable
    public static String getNowMillsTime() {
        return getNowDate(DATE_TIME_NO_SPACE_PATTERN_Mills);
    }

    /**
     * 获取当前时间，按照默认的 无空格方式产生 包括毫秒数 点号分割毫秒数
     * 格式（20150101123652.512）= 2015-01-01 12:36:52.512
     *
     * @return 返回的时间字符串
     */
    @stable
    public static String getNowDotMillsTime() {
        return getNowDate(DATE_TIME_NO_SPACE_PATTERN_Dot_Mills);
    }

    /**
     * 使用nanoTime获取的精确毫秒数（误差远小于1ms）
     * 注意：只用于时间差，不能用于直接转换为时间
     * 使用abs方法确保数值为正值，但是对于计时基准存在跨度的情况会出现误差（极罕见发生）
     *
     * @return 毫秒数
     */
    @stable
    public static Long getPreciseMills() {
        return Math.abs(System.nanoTime() / 1000000L);
    }

    /**
     * 使用nanoTime获取的精确微秒数（误差约小于微秒级）
     * 注意：只用于时间差，不能用于直接转换为时间
     * 使用abs方法确保数值为正值，但是对于计时基准存在跨度的情况会出现误差（极罕见发生）
     *
     * @return 微秒数
     */
    @stable
    public static Long getMicroSeconds() {
        return Math.abs(System.nanoTime() / 1000L);
    }

    /**
     * 使用nanoTime获取的精确纳秒数（误差约处于微秒级或约数十纳秒）本方法一般建议使用最高精度为微秒级的方法即可
     * 注意：只用于时间差，不能用于直接转换为时间
     * 使用abs方法确保数值为正值，但是对于计时基准存在跨度的情况会出现误差（极罕见发生）
     *
     * @return 纳秒数
     */
    @stable
    public static Long getNanoSeconds() {
        return Math.abs(System.nanoTime());
    }

    /**
     * 将Date类型转换为 yyyyMMddHHmmssS 形式的Long数据 毫秒数为3位 比SimpleDateFormat速度快
     * <br>使用拼接处理快速转换而非dateFormat 即 不处理时区问题
     * <br>该方法内部使用了不建议使用的JDK方法
     * @param date 待处理数据
     * @return 返回Long 或者 null
     */
    @stable
    public static Long formatDateToMillsLong(final Date date) {
        if (date == null) {
            return null;
        }
        long value = (long) date.getSeconds();
        value += (long) date.getMinutes() * 100L;
        value += (long) date.getHours() * 10000L;
        value += (long) date.getDay() * 1000000L;
        value += (long) (date.getMonth() + 1) * 100000000L;
        value += (long) (date.getYear() + 1900) * 10000000000L;
        value = value * 1000 + date.getTime() % 1000;
        return value;
    }

    /**
     * 将Date类型转换为 yyyyMMddHHmmss 形式的Long数据 比SimpleDateFormat速度快
     * <br>使用拼接处理快速转换而非dateFormat 即 不处理时区问题
     * <br>该方法内部使用了不建议使用的JDK方法
     * @param date 待处理数据
     * @return 返回Long 或者 null
     */
    @stable
    public static Long formatDateToLong(final Date date) {
        if (date == null) {
            return null;
        }
        long value = (long) date.getSeconds();
        value += (long) date.getMinutes() * 100L;
        value += (long) date.getHours() * 10000L;
        value += (long) date.getDay() * 1000000L;
        value += (long) (date.getMonth() + 1) * 100000000L;
        value += (long) (date.getYear() + 1900) * 10000000000L;
        return value;
    }

    /**
     * 将Date类型转换为yyyyMMdd形式的Long数据 比SimpleDateFormat速度快
     * <br>使用拼接处理快速转换而非dateFormat 即 不处理时区问题
     * <br>该方法内部使用了不建议使用的JDK方法
     * @param date 待处理数据
     * @return 返回Long 或者 null
     */
    @stable
    public static Long formatDateToDayLong(final Date date) {
        if (date == null) {
            return null;
        }
        long value =  (long) date.getDay() ;
        value += (long) (date.getMonth() + 1) * 100L;
        value += (long) (date.getYear() + 1900) * 10000L;
        return value;
    }


}
