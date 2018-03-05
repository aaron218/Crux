package net.newstring.crux.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.TreeSet;

/**
 * JedisClusterUtils
 *
 * @author lic
 * @date 2018/3/3
 */
public abstract class JedisClusterUtils {

    public static TreeSet<String> keys(JedisCluster jedisCluster,String pattern){
        TreeSet<String> keys = new TreeSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for(String k : clusterNodes.keySet()){
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch(Exception e){
            } finally{
                connection.close();//用完一定要close这个链接！！！
            }
        }
        return keys;
    }
}
