package net.newstring.crux.core.algorithm;

/**
 * Created by Aaron on 2016/12/8.
 */
public interface ConsistentHash<T> {

    T get(Object key);

    long getSize();


}
