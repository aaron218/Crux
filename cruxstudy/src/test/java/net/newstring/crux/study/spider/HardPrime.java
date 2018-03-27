package net.newstring.crux.study.spider;

import java.util.ArrayList;
import java.util.List;

/**
 * HardPrime
 * 硬计算质数
 *
 * @author lic
 * @date 2018/3/27
 */
public class HardPrime {

    public static void main(String[] args) {
        List<Long> prime = new ArrayList<>(5000);
        prime.add(2L);
        prime.add(3L);
        Long trace = 1L;
//        while (trace < Long.MAX_VALUE)
        long start = System.currentTimeMillis();
        while (trace < 10000) {
            trace++;
            boolean flag = true;
            for (Long val : prime) {
                if (trace % val == 0) {
                    flag = false;
                    break;
                }
            }
            if(flag){
//                System.out.println("got prime:"+trace);
                prime.add(trace);
            }
        }
        System.out.println("prime size:"+prime.size());
        System.out.println("using mills:"+(System.currentTimeMillis()-start));
    }
}
