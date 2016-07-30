package net.newString.crux.core.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2016/7/22 10:50.
 *
 * @author lic
 */
public class ModelMapper {

    private ModelMapper() {
    }


    public static <D> D mapper(Object source, Class<D> destinationType) {
        Field[] fields = destinationType.getDeclaredFields();

        Method[] methods = destinationType.getDeclaredMethods();



        Class<?> clazz = source.getClass();
        try {
            Constructor<D> constructor = destinationType.getConstructor();
            Object obj = constructor.newInstance();

            for(Field field : fields){
                String firstL = field.getName().substring(0,1).toUpperCase();
                String get = "get"+firstL+field.getName().substring(1);
                try {
                    Method getMethod = clazz.getMethod(get);
                    Object value  = getMethod.invoke(source);
                    String set = ""+firstL+field.getName().substring(1);
                    Method setMethod = destinationType.getMethod(set, field.getType());
                    setMethod.invoke(obj, value);
                }catch (NoSuchMethodException er){
                    System.out.println(er.getMessage());
                    continue;
                }
            }
            return (D) obj;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 将一个对象转化为Map，属性名称转换为key，属性值为value
     * @param source 源对象
     * @return 转化后的对象
     */
    public static Map<String, Object> toMap(Object source) {
        return null;
    }


    /**
     *
     * @param source
     * @param destinationType
     * @return
     */
    public static <D> D fromMap(Map<String,Object> source,Class<D> destinationType){
        return null;
    }


    /**
     * 深拷贝 从一个对象中拷贝出副本。
     * @param source
     * @return
     */
    public static  Object deepClone(Object source){
        return null;
    }


    public static void main(String[] args) {
        Date b = mapper(new Date(),Date.class);
        System.out.println(b);
    }
}
