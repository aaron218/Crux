package net.newString.crux.core.CN;

/**
 * 中文的数量转换，包括繁体数字、汉字数字 等
 *
 * @author lic
 */
public class CNNumberUtil {

    /**
     * 一般汉字数字
     */
    protected static final String[] simplifiedNumber = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿"};
    /**
     * 大写(繁体)汉字数字
     */
    protected static final String[] traditionalNumber = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "万", "亿"};


    private static String fullFromat(final Long value, final String[] format) {
        if (value == null) {
            return null;
        }
        if (value == 0) {
            return format[0];
        }
        if (value < 100000) { //小于十万直接用十万内处理方法
            return fiveStepConvertSimple(value, format);
        }
        long v = value;
        long head = v / 10000;
        String val = fiveStepConvertSimple(v % 100000, format);
        val = format[13] + val;
        v = head;
        val = fiveStepConvertSimple(v % 100000, format) + val;
        head = v / 10000;
        if (head <= 0) {
            return val;
        }
        val = format[14] + val;
        v = head;
        val = fiveStepConvertSimple(v % 100000, format) + val;
        head = v/10000;
        return null;
    }


    /**
     * 处理10万内的数字情况，也用于全量处理的拼接
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
        if ((source / 100000) >= 1) {
            return null;
        }
        if (source / 10000 >= 1 && source / 10000 <= 10) {
            sb.append(format[source / 10000]).append(format[13]);
        }
        source = source % 10000;
        boolean notPutZero = true;
        if (source / 1000 >= 1 && source / 1000 < 10) {
            sb.append(format[source / 1000]).append(format[12]);
        } else if (source / 1000 == 0) {//
            sb.append(format[0]);
            notPutZero = false;
        }
        source = source % 1000;
        if (source / 100 >= 1 && source / 100 < 10) {
            sb.append(format[source / 100]).append(format[11]);
            notPutZero = true;
        } else if (source / 100 == 0 && notPutZero) {
            sb.append(format[0]);
            notPutZero = false;
        }
        source = source % 100;
        if (source / 10 >= 1 && source / 10 < 10) {
            sb.append(format[source / 10]).append(format[10]);
        } else if (source / 10 == 0 && notPutZero) {
            sb.append(format[0]);
        }
        source = source % 10;
        if (source >= 1 && source < 10) {
            sb.append(format[source]);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(fiveStepConvertSimple(12005L, simplifiedNumber));
        System.out.println(fiveStepConvertSimple(12005L, traditionalNumber));
    }
}
