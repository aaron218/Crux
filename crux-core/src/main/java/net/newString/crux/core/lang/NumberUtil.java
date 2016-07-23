package net.newString.crux.core.lang;


/**
 * Created on 2016/7/22 11:16.
 * 数量工具
 * 判断和处理数值对象的工具，包括对数值的判断和复杂的数值转换、金钱单位转换、物理量单位转换
 * @author lic
 */
public class NumberUtil {

    public static boolean isDigits(final String str) {
        if (StringUtil.isEmptyStr(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
