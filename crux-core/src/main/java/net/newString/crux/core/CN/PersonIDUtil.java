package net.newString.crux.core.CN;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aaron on 10/16/2015.
 * 身份证相关工具 包括了一个搜索内嵌配置文件身份证号前6位转换为省市县的功能模块
 */
public class PersonIDUtil {
    private static final char[] code = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'}; // 11个
    private static final int[] factor = {0, 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7}; // 18个;
    private static Map<String,String> IDHeadMap = new ConcurrentHashMap<>();
    /**
     * 转换15位身份证号码到18位 不验证15位证件号的合法性
     * 转换算法：首先在15位号码的第六位后面加上19，然后将这个17位的号码按照生成规范产生第18位
     * @param personIDCode 待处理的15位号码
     * @return 产生的18位证件号码
     */
    public static String fixPersonIDCode(String personIDCode) {
        if (personIDCode == null || personIDCode.trim().length() != 15) {
            return personIDCode;
        }
        String id17 = personIDCode.substring(0, 6) + "19"
                + personIDCode.substring(6, 15); // 15为身份证补\'19\'
        return put18thValue(id17);
    }

    /**
     * 补足身份证号码最后一位。同时也是身份证号码最后一位产生器，可用于证件号码校验
     * @param value17 17位身份证号码
     * @return 补足后的18位号码或者null(不满足条件)
     */
    public static String put18thValue(String value17){
        if(value17==null || value17.length()!=17){
            return null;
        }
        if(!NumberUtils.isDigits(value17)){
            return null;
        }
        int[] idcd = new int[18];
        int j;
        int remainder;
        for (int i = 1; i < 18; i++) {
            j = 17 - i;
            idcd[i] = Integer.parseInt(value17.substring(j, j + 1));
        }
        int sum = 0;
        for (int i = 1; i < 18; i++) {
            sum = sum + idcd[i] * factor[i];
        }
        remainder = sum % 11;
        String lastCheckBit = String.valueOf(code[remainder]);
        return value17 + lastCheckBit;
    }

    /**
     * 验证是否是合法的18位证件号码 如果不满足18位和只包含数字与X(或x，不推荐)则返回false
     * @param id 待处理身份证号
     * @return 是否符合条件
     */
    public static boolean validID18(String id){
        if(id==null){
            return false;
        }
        if(id.length()!=18){
            return false;
        }
        if(!NumberUtils.isDigits(id.substring(0,17))){
            return false;
        }
        if(id.equals(put18thValue(id.substring(0, 17)))){
            return true;
        }else if(id.toUpperCase().equals(put18thValue(id.substring(0,17)))){
            return true;
        }
        return false;
    }

    @Deprecated //具体实现方案待定 目前无完备数据
    private static String getHeadCodeValue(String code){
        if(IDHeadMap==null || IDHeadMap.size()==0){  //使用数据结构减少文件读取
            Properties prop = new Properties();
            InputStream in = PersonIDUtil.class.getResourceAsStream("/crux-core-personID.properties");
            try {
                prop.load(in);
                for(Object str:prop.keySet()){
                    IDHeadMap.put(str.toString(),new String(prop.get(str).toString().getBytes("ISO-8859-1"),"gbk"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return IDHeadMap.get(code);
    }

}
