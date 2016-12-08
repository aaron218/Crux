package net.newString.crux.core.CN;

import net.newString.crux.core.lang.NumberUtil;
import net.newString.crux.core.stable;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aaron on 10/16/2015.
 * 身份证相关工具 包括了一个搜索内嵌配置文件身份证号前6位转换为省市县的功能模块
 */
public class CNPersonIDUtil {
    private static final char[] code = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'}; // 11个
    private static final int[] factor = {0, 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7}; // 18个;
    private static Map<String, String> IDHeadMap = new ConcurrentHashMap<>();
    /**
     * 转换15位身份证号码到18位 不验证15位证件号的合法性
     * 转换算法：首先在15位号码的第六位后面加上19，然后将这个17位的号码按照生成规范产生第18位
     *
     * @param personIDCode 待处理的15位号码
     * @return 产生的18位证件号码
     */
    public static String fixPersonIDCode(String personIDCode) {
        if (personIDCode == null || personIDCode.trim().length() != 15) {
            return personIDCode;
        }
        String id17 = personIDCode.substring(0, 6) + "19"
                + personIDCode.substring(6, 15); // 15位身份证补\'19\'
        return put18thValue(id17);
    }

    /**
     * 补足身份证号码最后一位。同时也是身份证号码最后一位产生器，可用于证件号码校验
     *
     * @param value17 17位身份证号码
     * @return 补足后的18位号码或者null(不满足条件)
     */
    public static String put18thValue(final String value17) {
        if (value17 == null || value17.length() != 17) {
            return null;
        }
        if (!NumberUtil.isDigits(value17)) {
            return null;
        }
        int[] id = new int[18];
        int j;
        int remainder;
        for (int i = 1; i < 18; i++) {
            j = 17 - i;
            id[i] = Integer.parseInt(value17.substring(j, j + 1));
        }
        int sum = 0;
        for (int i = 1; i < 18; i++) {
            sum = sum + id[i] * factor[i];
        }
        remainder = sum % 11;
        String lastCheckBit = String.valueOf(code[remainder]);
        return value17 + lastCheckBit;
    }

    /**
     * 验证是否是合法的18位证件号码 如果不满足18位和只包含数字与X(或x，不推荐)则返回false
     *
     * @param id 待处理身份证号
     * @return 是否符合条件
     */
    public static boolean validID18(final String id) {
        if (id == null) {
            return false;
        }
        if (id.length() != 18) {
            return false;
        }
        if (!NumberUtil.isDigits(id.substring(0, 17))) {
            return false;
        }
        if (id.equals(put18thValue(id.substring(0, 17)))) {//ID与产生的ID相同
            return true;
        } else if (id.toUpperCase().equals(put18thValue(id.substring(0, 17)))) {//ID大写与产生的ID相同（适应小写x）
            return true;
        } else if (id.substring(18).equals("*") && new String(id).replace("*", "X").equals(put18thValue(id.substring(0, 17)))) {
            return true; //处理末位是星号的情况，在部分单据中证件号码可能用*替代X
        }
        return false;
    }


    /**
     * 快速检查是否是正确的身份证号 不验证校验位，也不验证内部各个数字的合法性
     * 15位必须全部是数字，18位的前17位必须是数字，最后一位是X或x或* 不可以0开头
     *
     * @param id 待验证身份证号
     * @return 是否合法
     */
    public static boolean validIDSimple(final String id) {
        if (id == null || !(id.length() == 15 || id.length() == 18)) {
            return false;
        }
        if (id.contains("000000")) {//号码中不允许6个0连续出现
            return false;
        }
        if(id.startsWith("0")){
            return false;
        }
        if (id.length() == 15 && NumberUtil.isDigits(id)) {
            return true;
        }
        if (id.length() == 18) {
            if (NumberUtil.isDigits(id)) {
                return true;
            } else if (NumberUtil.isDigits(id.substring(0, 17))) {
                return id.endsWith("X") || id.endsWith("x") || id.endsWith("*");
            }
        }
        return false;
    }

    /**
     * 获取身份证号码钱6位对应的地区信息参考值
     * <br>错误或者未定义的数字码将会返回null
     *
     * @param code 6位数字码
     * @return 对应地区名称或null
     */
    @stable
    public static Optional<String> getHeadCodeValue(final String code) {
        if (IDHeadMap == null || IDHeadMap.size() == 0) {  //使用数据结构减少文件读取
            Properties prop = new Properties();
            InputStream in = CNPersonIDUtil.class.getResourceAsStream("/crux-core-ssxqDM.properties");
            try {
                prop.load(in);
                for (Object str : prop.keySet()) {
                    IDHeadMap.put(str.toString(), new String(prop.get(str).toString().getBytes("ISO-8859-1"), "utf-8"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (code == null || code.length() == 0 || IDHeadMap.get(code)==null) {
            return Optional.empty();
        }
        return Optional.of(IDHeadMap.get(code));
    }

}
