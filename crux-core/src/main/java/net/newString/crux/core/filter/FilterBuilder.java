package net.newString.crux.core.filter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created on 2016/8/3 18:17.
 *
 * @author lic
 */
public abstract class FilterBuilder {
    private FilterBuilder() {
    }

    /**
     * 将任何一个接受对象并返回boolean值的方法反射为一个Filter, 该方法可以是静态的但是必须是public的
     * <br> 使用此方法获取Filter时，必须严格注意反射的方法接受的参数和返回，否则doFilter会抛出异常
     * @param method 待反射方法
     * @return 新建的Filter
     */
    public Filter getFilterByReflect(Method method){
        if(method == null){
            return null;
        }
        if(!Modifier.isPublic(method.getModifiers())){
            return null;
        }
        return new BaseFilter() {
            private static final long serialVersionUID = -3755463729663974L;

            @Override
            public boolean doFilter(Object object) throws CruxFilterException {
                if(method.getReturnType().equals(Boolean.class)){
                    try {
                        Object obj =  method.invoke(object);
                        if(obj instanceof Boolean){
                            return (Boolean) obj;
                        }else{
                            throw new CruxFilterException("Method return Object is not Boolean!");
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new CruxFilterException(e);
                    }
                }
                throw new CruxFilterException("Method return Type is not Boolean!");
            }

            @Override
            public Filter copyOne() {
                return getFilterByReflect(method);
            }
        };
    }


    /**
     * 中国身份证号检验Filter 符合条件的身份证号将会通过Filter
     */
    public class ChinaSFZH_Filter extends BaseFilter{
        private static final long serialVersionUID = -9051269103139158590L;

        @Override
        public boolean doFilter(Object object) {
            return false;
        }

        @Override
        public Filter copyOne() {
            return new ChinaSFZH_Filter();
        }
    }
}
