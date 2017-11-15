package net.newstring.crux.core.algorithm.base;

import net.newstring.crux.core.algorithm.ConsistentHash;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * using JDK MD5 as HashFunction
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SimpleConsistentHash<T> implements ConsistentHash<T> {

    private final HashFunction hashFunction;
    /** 虚拟节点数量
     *
     */
    private final int numberOfReplicas;
    /**
     用来存储虚拟节点hash值 到真实node的映射
     */
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    public SimpleConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    /**
     * 获得一个最近的顺时针节点
     * @param key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * @return
     */
    @Override
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunction.hash((String) key);
        if (!circle.containsKey(hash)) {
            //返回此映射的部分视图，其键大于等于 hash
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    @Override
    public long getSize() {
        return circle.size();
    }

    public static class HashFunction {
        private MessageDigest md5 = null;
        public long hash(String key) {
            if (md5 == null) {
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalStateException("no md5 algorythm found");
                }
            }
            md5.reset();
            md5.update(key.getBytes());
            byte[] bKey = md5.digest();
            long res = ((long) (bKey[3] & 0xFF) << 24)
                    | ((long) (bKey[2] & 0xFF) << 16)
                    | ((long) (bKey[1] & 0xFF) << 8)
                    | (long) (bKey[0] & 0xFF);
            return res & 0xffffffffL;
        }

    }


    public static void main(String[] args) {
        Set<String> nodes = new HashSet<String>();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");
        SimpleConsistentHash<String> consistentHash = new SimpleConsistentHash<>(
                new HashFunction(), 8196, nodes);

        consistentHash.add("D");
        System.out.println(consistentHash.getSize());  //640

        System.out.println(consistentHash.get("test5"));
        consistentHash.add("H");
        System.out.println(consistentHash.get("test5"));
        consistentHash.add("I");
        System.out.println(consistentHash.get("test5"));
        consistentHash.add("Z");
        System.out.println(consistentHash.get("test5"));
        System.out.println(consistentHash.getSize());
    }
}
