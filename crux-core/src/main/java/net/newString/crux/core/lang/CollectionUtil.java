package net.newString.crux.core.lang;

import net.newString.crux.core.annotation.stable;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created on 2016/8/4 17:08.
 *
 * @author lic
 */
@stable
public class CollectionUtil {

    volatile static Unsafe unsafe;

    /**
     * 判断一个collection 是否为空或者是否为null
     *
     * @param collection 待判断coll
     * @return 结果
     */
    @stable
    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 从Collection中随机的获取一个数据
     * @param collection 待处理数据集合
     * @param <T> 类型 由collection指定
     * @return 随机获取的数据或者null
     */
    public static <T> T getRandomOne(final Collection<T> collection) {
        if (collection == null || collection.size() <= 0) {
            return null;
        }
        int i = collection.size();
        int rand = RandomUtil.getRandomIntValue(i);
        Iterator<T> it = collection.iterator();
        while (rand-- > 0) {
            it.next();
        }
        return it.next();
    }

    public static Unsafe getUnsafe() {
        if(unsafe == null){
            synchronized (CollectionUtil.class){
                if(unsafe ==null){
                    try {
                        Field f = Unsafe.class.getDeclaredField("theUnsafe");
                        f.setAccessible(true);
                        unsafe = (Unsafe)f.get(null);
                        return unsafe;
                    } catch (Exception e) {
                        //nothing
                    }
                }
            }
        }
        return unsafe;
    }
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();

        Unsafe unsafe = getUnsafe();
        int resize = unsafe.addressSize();

        System.out.println("addressSize:"+resize);
        System.out.println("pageSize:"+unsafe.pageSize());
       // unsafe.park(true,10000L);
        //unsafe.unpark(atomicInteger);
        long oneHundred = 100;
        long size = 3;
        /*
         * 调用allocateMemory分配内存
         */
        long memoryAddress = unsafe.allocateMemory(size);
        System.out.println("Address:"+memoryAddress);
        /*
         * 将100写入到内存中
         */
        unsafe.putAddress(memoryAddress, oneHundred);

        /*
         * 内存中读取数据
         */
        long readValue = unsafe.getAddress(memoryAddress);

        Object object = new Object();
        unsafe.getObjectVolatile(object,memoryAddress);
        System.out.println(object.getClass().getCanonicalName());

        AtomicReference<String> atomicReference = new AtomicReference<>();
        atomicReference.lazySet("");
        unsafe.reallocateMemory(memoryAddress,oneHundred);
        System.out.println("Val : " + readValue);

    }

}
