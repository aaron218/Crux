package net.newString.crux.core.lang;


/**
 * Created on 2016/7/22 11:16.
 *
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
