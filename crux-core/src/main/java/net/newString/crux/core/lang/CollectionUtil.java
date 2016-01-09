package net.newString.crux.core.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 10/16/2015.
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
