package net.newString.crux.core.lang;

import sun.misc.Unsafe;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/7 0007.
 * 测试
 */
@Deprecated
public class MemoryUtil {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

    private static Unsafe getUnsafe() {
        Field f = null;
        Unsafe unsafe = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (f != null) {
            f.setAccessible(true);
            try {
                unsafe = (Unsafe) f.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return unsafe;
    }

    public static long sizeOf(Object o) {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            maxSize += offset;
        }
        return ((maxSize / 8) + 1) * 8;   // padding
    }

    static class testClass {
        // private String name;
        private Long longValue;
        private Double dub;
        private Integer intValue;
        private List<String> test;
        private Map<String, String> map;
    }
}
