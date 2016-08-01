package net.newString.crux.core.model;

import net.newString.crux.core.model.Exception.ModelMapperException;

import java.lang.reflect.*;
import java.util.Date;
import java.util.Map;

/**
 * Created on 2016/7/22 10:50.
 *
 * @author lic
 */
public abstract class ModelMapper {

    private ModelMapper() {
    }

    /**
     * 将一个对象反射成为另外一个对象，属性通过get和set复制。
     * 要求：复制原对象中get方法属性public的方法，要求目标的set属性必须是public。
     * 或者：get和set方法允许为protected，但是此时必须同包
     * @param source          原对象
     * @param destinationType 目标对象类型
     * @param <D>             返回目标对象
     * @return 目标对象
     * @throws ModelMapperException 模型对象转换异常
     */
    @SuppressWarnings("unckecked")
    public static <D> D mapper(Object source, Class<D> destinationType)
            throws ModelMapperException {
        Field[] fields = destinationType.getDeclaredFields();
        Class<?> clazz = source.getClass();

        boolean samePackage = clazz.getPackage().equals(destinationType.getPackage());
        try {
            Constructor<D> constructor = destinationType.getConstructor();
            Object obj = constructor.newInstance();

            for (Field field : fields) {
                String firstL = field.getName().substring(0, 1).toUpperCase();
                String get = "get" + firstL + field.getName().substring(1);
                try {
                    clazz.getMethod(get);//尝试获取方法，如果异常，则抛弃本次获取
                }catch (Exception e){
                    continue;
                }
                try {
                    Method getMethod = clazz.getMethod(get);
                    //如果get方法是public的，同或者虽然是protected 但是与目标类同包 都可以进行反射，否则不可以操作
                    if (!Modifier.isPublic(getMethod.getModifiers()) &&
                            !(Modifier.isProtected(getMethod.getModifiers())) && samePackage) {
                        continue;
                    }
                    Object value = getMethod.invoke(source);
                    String set = "set" + firstL + field.getName().substring(1);
                    Method setMethod = destinationType.getMethod(set, field.getType());
                    //类似于get方法处理，set方法中，也只能set public方法或者同包的protected方法。
                    if (Modifier.isPublic(setMethod.getModifiers()) ||
                            (samePackage && Modifier.isProtected(setMethod.getModifiers()))) {
                        setMethod.invoke(obj, value);
                    } else {
                        throw new ModelMapperException("目标数据类型的Set方法不是public" +
                                "的");
                    }
                } catch (NoSuchMethodException er) {
                    System.out.println(er.getMessage());
                    throw new ModelMapperException(er);
                }
            }
            return (D) obj;
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            throw new ModelMapperException(e);
        }
    }


    /**
     * 将一个对象转化为Map，属性名称转换为key，属性值为value
     *
     * @param source 源对象
     * @return 转化后的对象
     */
    public static Map<String, Object> toMap(Object source) {
        return null;
    }


    /**
     * @param source
     * @param destinationType
     * @return
     */
    public static <D> D fromMap(Map<String, Object> source, Class<D> destinationType) {
        return null;
    }


    /**
     * 深拷贝 从一个对象中拷贝出副本。
     *
     * @param source
     * @return
     */
    public static Object deepClone(Object source) {
        return null;
    }


    public static void main(String[] args) {
        Date b = null;
        try {
            b = mapper(new Date(), Date.class);
        } catch (ModelMapperException modelMapperException) {
            modelMapperException.printStackTrace();
        }
        System.out.println(b);
    }
}
