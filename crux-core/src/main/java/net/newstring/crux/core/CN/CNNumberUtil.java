package net.newstring.crux.core.CN;

import net.newstring.crux.core.annotation.stable;

/**
 * 中文的数量转换，包括繁体数字、汉字数字 等
 *
 * @author lic
 *
 */
@stable(value = "not optimised")
public class CNNumberUtil {

    /**
     * 一般汉字数字
     */
    protected static final String[] simplifiedNumber = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿"};
    /**
     * 大写(繁体)汉字数字
     */
    protected static final String[] traditionalNumber = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "万", "亿"};
    protected static final String[] moneyTail = {"元","角","分","厘"};
    protected static final String dot = "点";

    /**
     * Long数字到中文数字转换 int可以先转成Long
     * @param value 数字
     * @return 中文数字
     */
    private static String toSimplifiedNumder(Long value){
        return fullFromat(value,simplifiedNumber);
    }

    /**
     * Long数字到大写中文数字转换 int可以先转成Long
     * @param value 数字
     * @return 大写中文数字
     */
    private static String toTraditionalNumber(Long value){
        return fullFromat(value,traditionalNumber);
    }

    /**
     * Double 数字到中文数字转换 int可以先转成Long
     * @param value 数字
     * @return 中文数字
     */
    private static String toSimplifiedNumder(Double value){
        return fullFromat(value.longValue(),simplifiedNumber);
    }

    /**
     * Double 数字到大写中文数字转换 int可以先转成Long
     * @param value 数字
     * @return 大写中文数字
     */
    private static String toTraditionalNumber(Double value){
        return fullFromat(value.longValue(),traditionalNumber);
    }


    /**
     * 将double类型的金钱数字转换为中文的金钱数字 小数部分包括角分厘
     * @param value 待处理数据
     * @return 中文数字
     */
    private static String toSimplifiedMoney(Double value){
        if(value==null || value.longValue() > Long.MAX_VALUE){
            return null;
        }

        return null;
    }

    /**
     * 将double类型的金钱数字转换为大写中文的金钱数字 小数部分包括角分厘
     * @param value 待处理数据
     * @return 大写中文数字
     */
    private static String toTraditionalMoney(Double value){
        return null;
    }


    /////////////////////////////////////private methods
    private static String fullFromat(final Long value, final String[] format) {
        if (value == null) {
            return null;
        }
        if (value == 0) {
            return format[0];
        }
        if (value < 10000) { //小于万直接用万内处理方法
            return fiveStepConvertSimple(value, format);
        }
        long v = value;
        int step = 0;
        String val = "";
        String temp;
        while (v > 0) {
            long head = v / 10000;
            temp = fiveStepConvertSimple(v % 10000, format);
            if (!format[0].equals(temp)) {
                if (step == 1) {
                    val = format[13] + val;
                } else if (step == 2) {
                    val = format[14] + val;
                } else if (step == 3) {
                    val = format[13] + format[14] + val;
                } else if (step == 4) {  //long 类型到900亿亿大小
                    val = format[14] + format[14] + val;
                }
                val = temp + val;
            }
            v = head;
            step += 1;
        }
        return val;
    }

    /**
     * 处理1万内的数字情况，也用于全量处理的拼接
     *
     * @param src    源数据
     * @param format 格式化字符串数组
     * @return 结果字符串
     */
    private static String fiveStepConvertSimple(final Long src, final String[] format) {
        if (src == null) {
            return null;
        }
        if (src > Integer.MAX_VALUE) {
            return null;
        }
        if (src == 0) {
            return format[0];
        }
        int source = src.intValue();
        StringBuilder sb = new StringBuilder();
        if ((source / 10000) >= 1) {
            return null;
        }
        source = source % 10000;
        if (source / 1000 >= 1 && source / 1000 < 10) {
            sb.append(format[source / 1000]).append(format[12]);
        }
        source = source % 1000;
        boolean putZero = false;  // 0 采用延迟添加，也即遇到不需要添加的地方才进行判断之前是否要加0
        if (source / 100 >= 1 && source / 100 < 10) {
            sb.append(format[source / 100]).append(format[11]);
        } else if (source / 100 == 0) {
            putZero = true;
        }
        source = source % 100;
        if (source / 10 >= 1 && source / 10 < 10) {
            if (putZero) {
                sb.append(format[0]);
                putZero = false;
            }
            sb.append(format[source / 10]).append(format[10]);
        } else if (source / 10 == 0) {
            putZero = true;
        }
        source = source % 10;
        if (source >= 1 && source < 10) {
            if (putZero) {
                sb.append(format[0]);
            }
            sb.append(format[source]);
        }
        return sb.toString();
    }

}
