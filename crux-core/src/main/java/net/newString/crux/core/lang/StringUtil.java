package net.newString.crux.core.lang;

import net.newString.crux.core.stable;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aaron on 6/20/2015.
 * 字符串处理工具，包括一些数值转换，同时包括一部分对Object的处理
 */
@stable("lic")
public class StringUtil {
    public static final String BJX = "李王张刘陈杨赵黄周吴徐孙胡朱高林何郭马罗\n" +
            "梁宋郑谢韩唐冯于董萧程曹袁邓许傅沈曾彭吕\n" +
            "苏卢蒋蔡贾丁魏薛叶阎余潘杜戴夏钟汪田任姜\n" +
            "范方石姚谭廖邹熊金陆郝孔白崔康毛邱秦江史\n" +
            "顾侯邵孟龙万段雷钱汤尹黎易常武乔贺赖龚文\n" +
            "庞樊兰殷施陶洪翟安颜倪严牛温芦季俞章鲁葛\n" +
            "伍韦申尤毕聂丛焦向柳邢骆岳齐尚梅莫庄辛管\n" +
            "祝左涂谷祁时舒耿牟卜路詹关苗凌费纪靳盛童\n" +
            "欧甄项曲成游阳裴席卫查屈鲍位覃霍翁隋植甘\n" +
            "景蒲单包司柏宁柯阮桂闵欧阳解强柴华车冉房边\n" +
            "辜吉饶刁瞿戚丘古米池滕晋苑邬臧畅宫来缪苟\n" +
            "全褚廉简娄盖符奚木穆党燕郎邸冀谈姬屠连郜\n" +
            "晏栾郁商蒙计喻揭窦迟宇敖糜鄢冷卓花仇艾蓝\n" +
            "都巩稽井练仲乐虞卞封竺冼原官衣楚佟栗匡宗\n" +
            "应台巫鞠僧桑荆谌银扬明沙薄伏岑习胥保和蔺";
    static Log log = LogFactory.getLog(StringUtil.class);
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 判断是否为合法的日期时间字符串
     * 日期转换异常则返回false
     * <br>一般推荐使用 {@link StringUtil#nullToDate(Object, String)} 来处理
     *
     * @param str_input
     * @return boolean;符合为true,不符合为false
     */
    @stable
    public static boolean isDate(String str_input, String rDateFormat) {
        if (str_input != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
            formatter.setLenient(false);
            try {
                formatter.format(formatter.parse(str_input));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 将对象转换为日期，将对象转成String然后按照给定的日期格式花字符串给出日期
     * 本方法会产生临时对象 大量吞吐量调用时不提倡 应该使用DateUtil中的方法
     * 失败时会打印StackTrace并写入日志
     *
     * @param _input      输入的对象
     * @param rDateFormat 日期格式化字符串
     * @return 构造的对象或者null
     */
    @stable
    public static Date nullToDate(Object _input, String rDateFormat) {
        if (!isEmptyStr(_input)) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
                formatter.setLenient(false);
                return formatter.parse(null2Str(_input));
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("nullToDate:: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 是否空字符串 方法中调用了trim再进行判定
     * <br><b>注意：与String自带的isEmpty行为不同</b>
     * 对于null 空字符串 trim后为空的字符串，均返回true
     *
     * @param str 待判定字符串
     * @return 是否为空字符串
     */
    @stable
    public static boolean isEmptyStr(String str) {
        if (str == null || str.trim().equals("") || str.trim().length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断对象toString后是否为空字符串，内部调用{@link StringUtil#isEmptyStr(String)}
     * <br>对于空对象和{@link StringUtil#isEmptyStr(String)}中为true的，返回true
     *
     * @param obj 待判定对象
     * @return 是否解析为空字符串的对象
     */
    @stable
    public static boolean isEmptyStr(Object obj) {
        if (obj == null || isEmptyStr(obj.toString()))
            return true;
        else
            return false;
    }

    /**
     * 是否非空字符串 实际上是StringUtil#isEmptyStr(String)取反
     *
     * @param str 待判断字符串
     * @return 是否为非空字符串
     * @see StringUtil#isEmptyStr(String)
     */
    @stable
    public static boolean isNotEmptyStr(String str) {
        return !isEmptyStr(str);
    }

    /**
     * 是否非空字符串 实际上是 StringUtil#isEmptyStr(Object)取反
     *
     * @param obj 待判断Object
     * @return 是否解析为非空字符串的对象
     * @see StringUtil#isEmptyStr(Object)
     */
    @stable
    public static boolean isNotEmptyStr(Object obj) {
        return !isEmptyStr(obj);
    }

    /**
     * 将空对象解析为""字符串（空字符串），其他情况调用对象的toString方法
     * 该方法不会有null返回 对象为null的时候返回"" 防止转换中抛出 NullPointer
     *
     * @param value 待处理对象
     * @return 处理后String 不会为null
     */
    @stable
    public static String null2Str(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * 将对象解析为字符串，调用toString方法
     * 当对对象为null的时候返回null 从而防止转换中抛出 NullPointer
     *
     * @param value
     * @return
     */
    @stable
    public static String obj2Str(Object value) {
        return value == null ? null : value.toString();
    }

    /**
     * 将null解析为""字符串，其他情况返回该字符串trim后的值
     * 该方法不会有null返回
     *
     * @param value 待处理字符串
     * @return 处理后String 不会为null
     */
    @stable
    public static String null2Str(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * 将对象转换为Long，空对象转换为0L 空字符串也转换为0L
     * 仅用于特殊情况，因为会覆盖原先的null情况
     *
     * @param value 待处理对象
     * @return Long对象，不会为null
     */
    @stable
    public static Long nullToLong(Object value) {
        return value == null || isEmptyStr(value) ? 0L : stringToLong(value.toString().trim());
    }

    /**
     * 将对象转换成Long，空对象 空字符串以及无法转换的对象转换成null
     *
     * @param value
     * @return Long对象
     */
    @stable
    public static Long objToLong(Object value) {
        return value == null || isEmptyStr(value) ? null : stringToLong(value.toString().trim());
    }

    /**
     * 将对象转换成Integer,null 空字符串转换成 0
     * 仅在特殊情况下使用，因为会覆盖原来的null值
     *
     * @param value 待转换对象
     * @return Integer值 或者0
     */
    @stable
    public static Integer nullToInteger(Object value) {
        return value == null || isEmptyStr(value) ? 0 : stringToInteger(value.toString());
    }

    /**
     * 将对象转换成Integer,null 空字符串转换成 null
     *
     * @param value 待转换对象
     * @return Integer值 或者null
     */
    @stable
    public static Integer objToInteger(Object value) {
        return value == null || isEmptyStr(value) ? null : stringToInteger(value.toString());
    }

    /**
     * 对象转换为double 空对象或者无法转换的情况返回0值的Double
     * 特殊情况下使用 会覆盖原来的null情况
     *
     * @param value 待转换对象
     * @return Double 或者 0值Double
     */
    @stable
    public static Double nullToDouble(Object value) {
        Double d = new Double(0);
        try {
            if (value != null && !StringUtil.isEmptyStr(value.toString())) {
                d = Double.parseDouble(String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
            d = new Double(0);
        }
        return d;
    }

    /**
     * 对象转换为double 空对象或者无法转换的情况返回null
     * 特殊情况下使用 会覆盖原来的null情况
     *
     * @param value 待转换对象
     * @return Double 或者 0值Double
     */
    @stable
    public static Double objToDouble(Object value) {
        if (isEmptyStr(value)) {
            return null;
        }
        Double d = new Double(0);
        try {
            d = Double.parseDouble(String.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("objToDouble :" + value.toString());
            return null;
        }
        return d;
    }

    /**
     * 对象转换为Float 空对象或者无法转换的情况返回0值的 Float
     * 特殊情况下使用 会覆盖原来的null情况
     *
     * @param value 待转换对象
     * @return Float 或者 0值 Float
     */
    @stable
    public static Float nullToFloat(Object value) {
        Float f = new Float(0);
        try {
            if (value != null && !StringUtil.isEmptyStr(value.toString())) {
                f = Float.parseFloat(String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
            f = new Float(0);
        }
        return f;
    }

    /**
     * 对象转换为 Float 空对象或者无法转换的情况返回null
     * 特殊情况下使用 会覆盖原来的null情况
     *
     * @param value 待转换对象
     * @return Float 或者 0值 Float
     */
    @stable
    public static Float objToFloat(Object value) {
        if (isEmptyStr(value)) {
            return null;
        }
        Float f = new Float(0);
        try {
            f = Float.parseFloat(String.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("objToFloat :" + value.toString());
            return null;
        }
        return f;
    }

    /**
     * 对象转换为布尔值， 1 true 转换为true，其余转换为false
     *
     * @param value 待转换对象
     * @return 布尔值
     */
    @stable
    public static Boolean nullToBoolean(Object value) {
        if (isEmptyStr(value))
            return false;
        if ("1".equals(value.toString().trim())
                || "true".equalsIgnoreCase(value.toString().trim()))
            return true;
        return false;
    }

    /**
     * 对象转换为布尔值，空字符串等转换为null
     * 1 true ==>true
     * 0 false ==> false
     * 其他的均为null
     *
     * @param value 待转换对象
     * @return 布尔值或者null
     */
    @stable
    public static Boolean objToBoolean(Object value) {
        if (isEmptyStr(value))
            return null;
        if ("1".equals(value.toString().trim())
                || "true".equalsIgnoreCase(value.toString().trim()))
            return true;
        if ("0".equals(value.toString().trim())
                || "false".equalsIgnoreCase(value.toString().trim()))
            return true;

        return null;
    }

    /**
     * 字符串转换为Long 空字符串，无法转换的字符串为null
     * 内嵌trim处理
     *
     * @param value 待处理字符串
     * @return Long对象
     */
    @stable
    public static Long stringToLong(String value) {
        Long l;
        value = null2Str(value);
        if ("".equals(value)) {
            return null;
        } else {
            try {
                l = Long.valueOf(value);
            } catch (Exception e) {
                return null;
            }
        }
        return l;
    }

    /**
     * 字符串转换为Integer，空字符串，无法转换的情况为null
     * 内嵌trim处理
     *
     * @param value 待处理字符串
     * @return Integer对象
     */
    @stable
    public static Integer stringToInteger(String value) {
        Integer l;
        value = null2Str(value);
        if ("".equals(value)) {
            return null;
        } else {
            try {
                l = Integer.valueOf(value);
            } catch (Exception e) {
                return null;
            }
        }
        return l;
    }

    /**
     * 将字符串转换成字符串List，按照给定的spliter
     *
     * @param value     待处理字符串
     * @param splitChar spliter
     * @return 字符串List，按照Spliter切分
     */
    @stable
    public static List<String> strToStrArray(String value, String splitChar) {
        if (value == null || "".equals(value)) {
            return null;
        }
        List<String> ls = new ArrayList<String>();
        String[] ids = value.split(splitChar);
        for (int i = 0; i < ids.length; i++) {
            try {
                if (!StringUtil.isEmptyStr(ids[i])) {
                    ls.add(ids[i].trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return ls;
    }

    /**
     * 将字符串List转换为单个字符串，按照Spliter连接
     *
     * @param list      待处理字符串List
     * @param splitChar 连接用Spliter
     * @return 单一字符串，其中每个数值用Spliter连接
     */
    @stable
    public static String strArrayToString(List<String> list, String splitChar) {
        if (list == null || list.size() == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            try {
                String value = it.next();
                sb.append(value);
                if (it.hasNext()) {
                    sb.append(splitChar);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return sb.toString();
    }

    /**
     * 将Set转换为String 类似于List转换
     *
     * @param set       待处理Set
     * @param splitChar 连接用Spliter
     * @return 单一字符串
     */
    @stable
    public static String strSetToString(Set<String> set, String splitChar) {
        if (set == null || set.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer("");
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            try {
                String value = it.next();
                sb.append(value);
                if (it.hasNext()) {
                    sb.append(splitChar);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return sb.toString();
    }

    /***
     * 将字符串List转换成Long List
     *
     * @param strList 字符串List
     * @return List(Long)
     */
    @stable
    public static List<Long> strListToLongList(List<String> strList) {
        List<Long> longList = new ArrayList<Long>();
        if (strList == null || strList.size() == 0) return longList;

        for (String str : strList) {
            try {
                longList.add(Long.valueOf(str));
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("strListToLongList convert Exception @:" + str);
                continue;
            }
        }
        return longList;
    }

    /**
     * 转换List到SQL接受的字符串，实际上和利用Spliter连接类似，建议使用{@link StringUtil#strArrayToString(List, String)}
     *
     * @param strList 字符串List
     * @return 处理后字符串
     */
    @stable
    public static String strListToSQL(List<String> strList) {
        StringBuffer sb = new StringBuffer();
        if (strList == null || strList.size() == 0) return sb.toString();
        boolean isNotFirst = false;
        for (String str : strList) {
            if (isNotFirst) sb.append(",");
            sb.append(String.format("'%s'", StringUtil.null2Str(str)));
            isNotFirst = true;
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否布尔值 尝试转换为布尔值
     * 但返回的并不是实际布尔值
     *
     * @param value 待判断字符串
     * @return 是否为布尔值
     */
    @stable
    public static boolean isBoolean(String value) {
        try {
            Boolean b = Boolean.parseBoolean(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 清除特殊字符，清除字符串中的换行 制表 空格 单引号字符，对字符串的改动较大，一般无法回溯。
     * 只用于特殊情况 null、空字符串返回""
     *
     * @param str 待处理字符串
     * @return 清理后字符串，不返回null
     */
    @stable
    public static String getescapeText(String str) {
        if (str == null)
            return ("");
        if (str.equals(""))
            return ("");
        // 建立一个StringBuffer来处理输入数据
        StringBuffer buf = new StringBuffer(str.length() + 6);
        char ch = '\n';
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (ch == '\r') {
                buf.append("");
            } else if (ch == '\n') {
                buf.append("");
            } else if (ch == '\t') {
                buf.append("");
            } else if (ch == ' ') {
                buf.append("");
            } else if (ch == '\'') {
                buf.append("");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 判定字符串是否只包含英文大小写字符和数字
     * ASCII码表第48～57号为0～9十个阿拉伯数字；65～90号为26个大写英文字母，97～122号为26个小写英文字母
     *
     * @param source 待判定字符串
     * @return 是否只包含英文和数字
     */
    @stable
    public static boolean isCharAndNum(String source) {
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
            if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123))
                continue;
            else
                return false;
        }
        return true;
    }

    /**
     * 清除所有特殊字符，只保留中英文字符和数字  使用patternMatcher，有性能问题，不建议使用
     * 清除中英文的标点符号等
     *
     * @param str 待处理字符串
     * @return 处理后字符串
     */
    @stable
    public static String getEscapeText(String str) {
        try {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清除所有特殊字符，只保留中英文字符和数字，以及中文标点符号
     * 使用patternMatcher，有性能问题，不建议使用
     *
     * @param str 待处理字符串
     * @return 处理后字符串
     */
    @stable
    public static String getZWEscapeText(String str) {
        try {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~@#￥%……&*（）——+|{}]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串中是否包含除中英文字符和数字外的特殊字符，包含返回true
     *
     * @param str 待判定字符串
     * @return 是否满足条件
     */
    @stable
    public static boolean haveEscapeText(String str) {
        if (str.replaceAll("[\u4e00-\u9fa5]*[+]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否包含中文和&符号
     * 使用[一-龥]（[\u4e00-\u9fa5]）判定
     *
     * @param str 待判定字符串
     * @return 判定结果
     */
    @stable
    public static boolean isContainChinese(String str) {
        if (str == null || "".equals(str))
            return false;

        String regEx = "[\u4e00-\u9fa5]|&";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 根据转义列表对字符串进行转义(escape)。
     *
     * @param source        待转义的字符串
     * @param escapeCharMap 转义列表
     * @return 转义后的字符串
     */
    @stable
    public static String escapeCharacter(String source, HashMap escapeCharMap) {
        if (source == null || source.length() == 0) {
            return source;
        }
        if (escapeCharMap.size() == 0) {
            return source;
        }
        StringBuffer sb = new StringBuffer(source.length() + 100);
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first();
             c != StringCharacterIterator.DONE;
             c = sci.next()) {
            String character = String.valueOf(c);
            if (escapeCharMap.containsKey(character)) {
                character = (String) escapeCharMap.get(character);
            }
            sb.append(character);
        }
        return sb.toString();
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    @stable
    public static String byteToHexString(byte[] tmp) {
        if (tmp == null) {
            throw new NullPointerException();
        }
        int len = tmp.length;
        char str[] = new char[len * 2];
        int i = 0;
        for (byte b : tmp) {
            str[i * 2] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            str[i * 2 + 1] = hexDigits[b & 0xf]; // 取字节中低 4 位的数字转换
            i++;
        }
        return new String(str);
    }

    /**
     * 得到一个Long值的指定长度的字符串形式 参考{@link StringUtil#getStringByAppointLen(String, int, boolean)}
     *
     * @param l   Long类型数据
     * @param len 长度
     * @return 处理后字符串
     */
    @stable
    public static String getStringByAppointLen(Long l, int len) {
        return getStringByAppointLen(l.toString(), len, true);
    }

    /**
     * 得到一个Integer值的指定长度的字符串形式 参考{@link StringUtil#getStringByAppointLen(String, int, boolean)}
     *
     * @param i   Integer
     * @param len 长度
     * @return 处理后字符串
     */
    @stable
    public static String getStringByAppointLen(Integer i, int len) {
        if (i == null) {
            return null;
        }
        return getStringByAppointLen(i.toString(), len, true);
    }

    /**
     * 得到一个String值的指定长度的字符串形式
     * NOTE:	不足的前面添'0'
     *
     * @param s       原始字符串
     * @param len     处理后长度
     * @param cutHead 当s的长度大于len时，截取方式：true,截掉头部；否则从截掉尾部
     *                例如getStringByAppointLen("12345",3,true) ---> "345"
     * @return 处理后字符串
     */
    @stable
    public static String getStringByAppointLen(String s, int len, boolean cutHead) {
        if (s == null || len <= 0) {
            return null;
        }
        if (len > s.length()) {
            int size = len - s.length();
            StringBuffer sb = new StringBuffer();
            while (size-- > 0) {
                sb.append("0");
            }
            sb.append(s);
            return sb.toString();
        } else if (len == s.length()) {
            return s;
        } else {
            if (cutHead) {
                return s.substring(s.length() - len, s.length());
            } else {
                return s.substring(0, len);
            }
        }
    }

    /**
     * 将字符串转换到布尔值对象 空字符串返回null
     * 1 true 转换为true，其他情况转换为false
     * 无法转换，空字符串返回null
     *
     * @param str 待处理字符串
     * @return 布尔值或者null
     */
    @stable
    public static Boolean string2Boolean(String str) {
        if (isEmptyStr(str)) {
            return null;
        }
        try {
            if ("0".equals(str))
                return Boolean.FALSE;
            else if ("1".equals(str))
                return Boolean.TRUE;
            else if ("false".equalsIgnoreCase(str))
                return Boolean.FALSE;
            else if ("true".equalsIgnoreCase(str))
                return Boolean.TRUE;
        } catch (Exception e) {
            return null;
        }
        return Boolean.FALSE;
    }

    /**
     * 获取一个char的byte值，只能是"0123456789ABCDEF"范围内 暂时仅在内部使用
     *
     * @param c char
     * @return byte值
     */
    @stable
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    /////////////////////////////////////////以下是重新封装的代码，一般封装自第三方库，修改了部分逻辑

    /**
     * 将包含16进制信息的字符串转换成byte数组
     *
     * @param hexString 十六进制类型字符串（只包含"0123456789ABCDEF"）
     * @return byte数组
     */
    @stable
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 按照默认值将对象转换为Int，如果转换失败，则返回默认值。默认值为null则为0
     * <br>
     * 参考{@link NumberUtils#toInt(String, int)}
     *
     * @param value        待转换对象
     * @param defaultValue 默认值
     * @return 返回值
     */
    @stable
    public static Integer toInt(Object value, Integer defaultValue) {
        if (defaultValue == null) {
            defaultValue = 0;
        }
        return NumberUtils.toInt(obj2Str(value), defaultValue);
    }

    /////////////////////////////////////////////////////以下是未完成或者不明确行为的代码，代码只有参考意义，暂时全部设置为private
    private static String hexStringToString(String hex, String charset) {
        StringBuffer sbuf = new StringBuffer();
        try {
            StringBuffer subhex = new StringBuffer();
            int i = 0;
            while (i < hex.length()) {
                if (hex.charAt(i) != '%') {
                    if (subhex.length() > 0) {
                        sbuf.append(new String(hexStringToBytes(subhex
                                .toString()), charset));
                        subhex.delete(0, subhex.length());
                    }
                    sbuf.append(hex.charAt(i));
                    i++;

                } else {
                    subhex.append(hex.subSequence(i + 1, i + 3));
                    i = i + 3;
                }
            }
            if (subhex.length() > 0) {
                sbuf.append(new String(hexStringToBytes(subhex.toString()),
                        charset));
                subhex.delete(0, subhex.length());
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return sbuf.toString();
    }

    /**
     * 16进制转化
     *
     * @param value
     * @return
     */
    private static String toHexValue(String value) {
        StringBuffer sb = new StringBuffer();
        byte[] bytes = value.getBytes();

        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);

            if (hex.length() < 2) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 二进制转化
     *
     * @param value
     * @return
     */
    private static String toBinaryValue(String value) {
        StringBuffer sb = new StringBuffer();
        byte[] bytes = value.getBytes();
        for (byte b : bytes) {
            String binary = Integer.toBinaryString(b & 0xFF);
            while (binary.length() < 8) {
                binary = '0' + binary;
            }
            sb.append(binary);
        }

        return sb.toString();
    }

    private static String getPercent(int finishedCount, int allCount) {
        float finishedCountF = finishedCount;
        float allCountF = allCount;
        float percentF = finishedCountF / allCountF * 100;
        String percent = String.valueOf(percentF);
        percent = (percent.split("\\."))[0];
        while (percent.length() < 3) {
            percent = " " + percent;
        }
        return percent;
    }

    private static Boolean includeIpAddress(String ip, String ips) {
        if (ips == null || "".equals(ips)) return false;
        String[] ipSplit = ips.split(",");
        for (int i = 0; i < ipSplit.length; i++) {
            if (ipSplit[i].equals(ip))
                return true;
        }
        return false;
    }
}
