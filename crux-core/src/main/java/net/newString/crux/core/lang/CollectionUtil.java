package net.newString.crux.core.lang;

import net.newString.crux.core.stable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 2016/8/4 17:08.
 *
 * @author lic
 */
@stable
public class CollectionUtil {


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






    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        System.out.println(getRandomOne(list));
        System.out.println(getRandomOne(list));
        System.out.println(getRandomOne(list));
        System.out.println(getRandomOne(list));
        System.out.println(getRandomOne(list));
    }
}
