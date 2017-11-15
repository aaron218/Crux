package net.newstring.crux.core.lang;

import java.lang.reflect.Field;

/**
 * Created by Aaron on 2016/12/9.
 */
public class UnsafeUtil {
    volatile static sun.misc.Unsafe unsafe;

    private UnsafeUtil() {
    }

    public static sun.misc.Unsafe getUnsafeInstance(){
        if(unsafe == null){
            synchronized (CollectionUtil.class){
                if(unsafe ==null){
                    try {
                        Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                        f.setAccessible(true);
                        unsafe = (sun.misc.Unsafe)f.get(null);
                        return unsafe;
                    } catch (Exception e) {
                        //nothing
                    }
                }
            }
        }
        return unsafe;
    }




}
