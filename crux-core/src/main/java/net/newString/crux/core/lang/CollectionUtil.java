package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

import java.util.Collection;

/**
 * Created on 2016/8/4 17:08.
 *
 * @author lic
 */
@stable
public class CollectionUtil {


    /**
     * 判断一个collection 是否为空或者是否为null
     * @param collection 待判断coll
     * @return 结果
     */
    @stable
    public static boolean isEmpty(final Collection collection){
        return collection==null || collection.size()==0;
    }
}
