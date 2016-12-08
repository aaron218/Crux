package net.newString.crux.core.tools;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现一个线程安全的LRU cache
 *
 * @author lic
 */
public class CacheLRU<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -8782397547207272211L;
    private final int maxCapacity;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Lock readLock;
    private Lock writeLock;


    public CacheLRU(int maxCapacity) {
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxCapacity = maxCapacity;
        ReadWriteLock globalLock = new ReentrantReadWriteLock();
        readLock = globalLock.readLock();
        writeLock = globalLock.writeLock();
    }


    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public V get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public V remove(Object key) {
        writeLock.lock();
        try {
            return super.remove(key);
        } finally {
            writeLock.unlock();
        }
    }
}
