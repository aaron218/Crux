package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

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
public abstract class StringUtil {

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 判断是否为合法的日期时间字符串
     * 日期转换异常则返回false
     *
     * @param str_input 输入数据
     * @return boolean;符合为true,不符合为false
     */
    @stable
    public static boolean isDateStr(String str_input, String rDateFormat) {
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
     * 是否空字符串 方法中调用了trim再进行判定
     * <br><b>注意：与String自带的isEmpty行为不同</b>
     * 对于null 空字符串 trim后为空的字符串，均返回true
     *
     * @param str 待判定字符串
     * @return 是否为空字符串
     */
    @stable
    public static boolean isEmptyStr(String str) {
        return str == null || str.length() == 0
                || str.trim().length() == 0 || str.trim().equals("");
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
        return obj == null || isEmptyStr(obj.toString());
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
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * 将对象解析为字符串，如果是String则直接转换，否则调用toString方法
     * 当对对象为null的时候返回null 从而防止转换中抛出 NullPointer
     *
     * @param value 待处理对象
     * @return 获取的字符串
     */
    @stable
    public static String obj2Str(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
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
     * @param value    待处理字符串
     * @param splitter spliter
     * @return 字符串List，按照Spliter切分
     */
    @stable
    public static List<String> strToStrArray(String value, String splitter) {
        if (value == null || "".equals(value)) {
            return null;
        }
        List<String> ls = new ArrayList<>();
        String[] ids = value.split(splitter);
        for (String id : ids) {
            try {
                if (!StringUtil.isEmptyStr(id)) {
                    ls.add(id.trim());
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return ls;
    }

    /**
     * 将字符串List转换为单个字符串，按照Spliter连接
     *
     * @param list     待处理字符串List
     * @param splitter 连接用Spliter
     * @return 单一字符串，其中每个数值用Spliter连接
     */
    @stable
    public static String strArrayToString(List<String> list, String splitter) {
        if (list == null || list.size() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            try {
                String value = it.next();
                sb.append(value);
                if (it.hasNext()) {
                    sb.append(splitter);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 将Set转换为String 类似于List转换
     *
     * @param set      待处理Set
     * @param splitter 连接用Spliter
     * @return 单一字符串
     */
    @stable
    public static String strSetToString(Set<String> set, String splitter) {
        if (set == null || set.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            try {
                String value = it.next();
                sb.append(value);
                if (it.hasNext()) {
                    sb.append(splitter);
                }
            } catch (Exception e) {
                //e.printStackTrace();
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
                //e.printStackTrace();
            }
        }
        return longList;
    }

    /**
     * 转换List到SQL接受的字符串，实际上和利用Splitter连接类似，建议使用{@link StringUtil#strArrayToString(List, String)}
     *
     * @param strList 字符串List
     * @return 处理后字符串
     */
    @stable
    public static String strListToSQL(List<String> strList) {
        StringBuilder sb = new StringBuilder();
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
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 清除特殊字符，清除字符串中的换行 制表 空格 单引号字符
     *
     * @param str 待处理字符串
     * @return 清理后字符串，不返回null
     */
    @stable
    public static String getEscapeText(final String str) {
        if (str == null)
            return null;
        if (str.equals(""))
            return null;
        // 建立一个StringBuffer来处理输入数据
        StringBuilder buf = new StringBuilder(str.length() + 6);
        char ch;
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
        if (source == null) {
            return false;
        }
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 是否包含中文
     * 使用[一-龥]（[\u4e00-\u9fa5]）判定
     *
     * @param str 待判定字符串
     * @return 判定结果
     */
    @stable
    public static boolean isContainChinese(String str) {
        if (str == null || "".equals(str))
            return false;
        String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 根据转义列表对字符串进行转义(escape)。 只能转义字符
     *
     * @param source        待转义的字符串
     * @param escapeCharMap 转义列表
     * @return 转义后的字符串
     */
    @stable
    public static String escapeCharacter(String source, HashMap<Character, String> escapeCharMap) {
        if (source == null || source.length() == 0) {
            return source;
        }
        if (escapeCharMap.size() == 0) {
            return source;
        }
        StringBuilder sb = new StringBuilder(source.length());
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first();
             c != StringCharacterIterator.DONE;
             c = sci.next()) {
            Character character = c;
            if (escapeCharMap.containsKey(character)) {
                sb.append(escapeCharMap.get(character));
            } else {
                sb.append(character);
            }
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
     * 得到一个Integer值的指定长度的字符串形式
     * 参考{@link StringUtil#getStringByAppointLen(String, int, boolean)}
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
            StringBuilder sb = new StringBuilder();
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
     * 字符串数据高亮 实际使用replaceAll处理
     *
     * @param source 源数据
     * @param find   匹配数据
     * @param pre    高亮头部tag
     * @param post   高亮尾部tag
     * @return 高亮后的字符串
     */
    @stable
    public static String highlightString(final String source, final String find, final String pre, final String post) {
        if (source == null) {
            return null;
        }
        if (find == null || source.length() < find.length()) {
            return source;
        }
        if (!source.contains(find)) {
            return source;
        }
        String copy = source + "";
        return copy.replaceAll(find, pre + find + post);
    }

    /**
     * 将数字字符转成实际的int(0-9) 无法转换的情况返回null
     *
     * @param ch 待处理字符
     * @return 返回数字
     */
    @stable
    public static Integer getCharVal(Character ch) {
        if (ch == null || !Character.isDigit(ch)) {
            return null;
        }
        return Character.digit(ch, 10);
    }

    /**
     * 反序一个字符串 内部使用StringBuilder来实现。如果输入为null 返回为null;
     *
     * @param str 待处理字符串
     * @return 处理后字符串
     */
    @stable
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    /////////////////////////////////////////////////////以下是未完成的代码，代码只有参考意义，暂时全部设置为private
    private String hexStringToString(String hex, String charset) {
        StringBuilder sbuf = new StringBuilder();
        try {
            StringBuilder subhex = new StringBuilder();
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

}
