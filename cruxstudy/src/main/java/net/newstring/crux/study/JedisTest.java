package net.newstring.crux.study;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * JedisTest
 *
 * @author lic
 * @date 2018/3/3
 */
public class JedisTest {

    public static void main(String[] args) {

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();

        jedisClusterNodes.add(new HostAndPort("192.168.16.131", 9000));
        jedisClusterNodes.add(new HostAndPort("192.168.16.131", 9001));
        jedisClusterNodes.add(new HostAndPort("192.168.16.131", 9002));

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
//        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes,5000,5,new JedisPoolConfig());


//        jedisCluster.set("test_key_value_0001","testValue");
//
//        String s = jedisCluster.get("test_key_value_0001");
//        System.out.println(s);

        jedisCluster.del("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120");
        jedisCluster.lpush("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120","AAA");
        jedisCluster.lpush("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120","BBB");
        jedisCluster.lpush("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120","CCC");
        jedisCluster.lpush("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120","fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120");

        List<String> test001 = jedisCluster.lrange("spiluxio_valSample_fagaiwei__100303__www.zjdpc.gov.cn__1515049880699__20180104151120", 0, 2);
        System.out.println("列表数据数据量："+test001.size());


        test001.forEach(System.out::println);

        TreeSet<String> keys = JedisClusterUtils.keys(jedisCluster, "spiluxio_valSample_fagaiwei*");

        System.out.println(keys.size());


        System.exit(0);
    }
}
