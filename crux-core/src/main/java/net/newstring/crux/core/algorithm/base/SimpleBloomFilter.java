package net.newstring.crux.core.algorithm.base;

import net.newstring.crux.core.algorithm.BloomFilter;

/**
 * 最简bloomfilter实现，用于较小的数据量
 */
public class SimpleBloomFilter<T> implements BloomFilter<T>{

    public boolean contains(T value){
        return false;
    }
}
