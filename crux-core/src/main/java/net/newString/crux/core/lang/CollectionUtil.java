package net.newString.crux.core.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 10/16/2015.
 * 对包括Collection List等在内的对象进行操作的工具，注意，所有返回的对象默认非线程安全
 */
public class CollectionUtil {


    public static List arrayToList(Object[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List list = new ArrayList<>();
        try {
            for (Object obj : array) {
                if (obj != null) {
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }
}
